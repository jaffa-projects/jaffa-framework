/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package org.jaffa.modules.printing.services;

import org.jaffa.exceptions.DomainObjectNotFoundException;
import org.jaffa.modules.printing.domain.OutputCommandMeta;
import org.jaffa.modules.printing.domain.PrinterDefinitionMeta;
import org.jaffa.modules.printing.services.exceptions.EmailFailedException;
import org.jaffa.modules.printing.services.exceptions.PrintFailedException;
import org.jaffa.persistence.UOW;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.printing.domain.OutputCommand;
import org.jaffa.modules.printing.domain.PrinterDefinition;
import org.jaffa.modules.printing.domain.PrinterOutputType;
import org.jaffa.util.EmailerBean;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.OsScriptingBean;
import org.jaffa.util.StringHelper;

public class FormDelivery {

    /**
     * Logger reference
     */
    private final static Logger log = Logger.getLogger(FormDelivery.class);

    /**
     * This will deliver a file based on the delivery options in the PrintRequest
     *
     * @param request  The print request detailing how this should be delivered
     * @param document The generated file that should be delivered
     * @param context  A context object that can be used to extract values when generating e-mail
     *                 subject and body information. This object should be a java bean or a map
     * @throws ApplicationExceptions Thrown if any funtional issue ocurred when executing
     * @throws FrameworkException    Thrown if any framework issue ocurred when executing
     */
    public static void deliver(PrintRequest request, File document, Object context)
            throws FrameworkException, ApplicationExceptions {
        // See if it should be printed
        PrinterDefinition printer = null;
        if (request.getPrinterId() != null && request.getPrintCopies() != null && request.getPrintCopies().intValue() > 0)
            sendToPrinter(request.getPrinterId(), request.getPrintCopies().intValue(), document);

        // See if it should be e-mailed
        if (request.getPrinterId() != null) {
            printer = PrinterDefinition.findByPK(null, request.getPrinterId());
        }
        if (request.getEmailToAddresses() != null) {
            sendEmail(request, document, context);
        } else if (printer != null) {
            sendToEmailPrinter(request, printer, document, context);
        }
    }


    private static void sendToEmailPrinter(PrintRequest request, PrinterDefinition printer, File document, Object context) throws FrameworkException, ApplicationExceptions {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        if (printer != null && printer.getRealPrinterName() != null) {
            Matcher matcher = pattern.matcher(printer.getRealPrinterName());
            if (matcher != null && matcher.matches()) {
                request.setEmailToAddresses(printer.getRealPrinterName());
                sendEmail(request, document, context);
            }
        }
    }

    /**
     * Send the generated document to the specified printer
     *
     * @param printerId Printer name, should be defined in PrinterDefinitions
     * @param copies    Number of copies to print
     * @param document  Source file to print
     * @throws ApplicationExceptions Thrown if any funtional issue ocurred when executing
     * @throws FrameworkException    Thrown if any framework issue ocurred when executing
     */
    public static void sendToPrinter(String printerId, int copies, File document)
            throws FrameworkException, ApplicationExceptions {
        UOW uow = new UOW();
        try {

            // Read the printer definition
            PrinterDefinition printer = PrinterDefinition.findByPK(uow, printerId);
            if (printer == null) {
                log.error("Unknown printer in print request : " + printerId);
                throw new ApplicationExceptions(new DomainObjectNotFoundException(PrinterDefinitionMeta.getLabelToken()));
            }
            PrinterOutputType outputType = printer.getPrinterOutputTypeObject();

            // See if this is direct printing
            if (Boolean.TRUE.equals(outputType.getDirectPrinting())) {
                printDirect(printer, document, copies);
            } else {
                // Find an external command line that will print this document
                String commandLine = null;
                String os = System.getProperty("os.name");

                for (OutputCommand command : outputType.getOutputCommandArray()) {
                    if (Pattern.matches(command.getOsPattern(), os)) {
                        commandLine = command.getCommandLine();
                        break;
                    }
                }

                if (commandLine == null) {
                    log.error("Can't find matching command line for Output Type " + outputType.getOutputType() + " on OS " + os);
                    throw new ApplicationExceptions(new DomainObjectNotFoundException(OutputCommandMeta.getLabelToken()));
                }

                // Now try and print the document with the command
                printViaCommand(printer, document, copies, commandLine);
            }

        } catch (ApplicationException e) {
            ApplicationExceptions aes = new ApplicationExceptions();
            aes.add(e);
            throw aes;
        } finally {
            if (uow != null)
                uow.rollback(); // This UOW should have not been used for updates!!!
        }
    }


    /**
     * Email out the generated document as an attachment
     *
     * @param request  Print Request containing possible email info
     * @param document document to put as the email attachment
     * @param dom      A context object that can be used to extract values when generating e-mail
     *                 subject and body information. This object should be a java bean or a map
     * @throws ApplicationExceptions Thrown if any funtional issue ocurred when executing
     * @throws FrameworkException    Thrown if any framework issue ocurred when executing
     */
    public static void sendEmail(PrintRequest request, File document, Object dom)
            throws FrameworkException, ApplicationExceptions {

        String documentName = null;

        try {
            // Set up parameters
            Object[] params = null;

            if (request instanceof FormPrintRequest) {
                FormPrintRequest fpr = (FormPrintRequest) request;

                params = new Object[]{
                        fpr.getFormName(),
                        fpr.getFormAlternateName(),
                        fpr.getVariation()
                };
                documentName = fpr.getFormName();
            } else {
                // Just use the short class name
                documentName = StringHelper.getShortClassName(request.getClass());
            }

            String fromAddress = request.getEmailFromAddress();

            // Use the resource bundle to get subject and body of this message
            String subject = request.getEmailSubject();
            if (subject == null) {
                String token = "label.Jaffa.Printing.Email." + documentName + ".subject";
                subject = MessageHelper.findMessage(token, params);
            }
            String body = request.getEmailMessage();
            if (body == null) {
                String token = "label.Jaffa.Printing.Email." + documentName + ".body";
                body = MessageHelper.findMessage(token, params);
            }

            //@TODO Pass the databean DOM in and use velocity templating to to token replacement of
            //the subject and body for better messages. Expose request and dom to the template

            EmailerBean eb = new EmailerBean();
            eb.sendMail(fromAddress, StringHelper.parseString(request.getEmailToAddresses(), ";"),
                    subject, body, new File[]{document});

        } catch (MessagingException e) {
            log.error("Failed to send email with attachment " + document.getAbsolutePath(), e);
            throw new ApplicationExceptions(
                    new EmailFailedException(documentName));
        }
    }


    private static void printDirect(PrinterDefinition printer, File document, int copies)
            throws FrameworkException, ApplicationExceptions {

        // @TODO Use Printer APIs to and file to the printer
    }

    /**
     * Example command line: C:\gsview\gsview32.exe "-p{0}" {2} {3} {1}
     */
    private static void printViaCommand(PrinterDefinition printer, File document, int copies, String commandLine)
            throws FrameworkException, ApplicationExceptions {
        // Perform parameter substitution on the command line
        // {0} is the printer name
        // {1} is the filename to print
        // {2} is additional parameter 1
        // {3} is additional parameter 2
        commandLine = StringHelper.replace(commandLine, "{0}", document.getAbsolutePath());
        commandLine = StringHelper.replace(commandLine, "{1}", printer.getRealPrinterName());
        commandLine = StringHelper.replace(commandLine, "{2}", printer.getPrinterOption1() == null ? "" : printer.getPrinterOption1());
        commandLine = StringHelper.replace(commandLine, "{3}", printer.getPrinterOption2() == null ? "" : printer.getPrinterOption2());

        if (log.isDebugEnabled()) {
            log.debug("Print using command line: " + commandLine);
        }

        // Execute the command line using OsScriptingBean
        OsScriptingBean os = new OsScriptingBean();
        os.setCommand(new String[]{commandLine});
        for (int i = 0; i < copies; i++) {
            try {
                if (os.exec() < 0) {
                    // Handle OS error
                    log.error("Error on Command " + commandLine);
                    log.info("Standard Output:\n" + os.getOutput());
                    log.info("Standard Error:\n" + os.getError());
                    throw new PrintFailedException("Command Failed");
                }
                if (log.isDebugEnabled()) {
                    log.debug("Returned from executing print command.");
                }
            } catch (ApplicationException e) {
                log.error("Error on Command " + commandLine, e);
                ApplicationExceptions aes = new ApplicationExceptions();
                aes.add(e);
                throw aes;
            }
        }
    }
}
