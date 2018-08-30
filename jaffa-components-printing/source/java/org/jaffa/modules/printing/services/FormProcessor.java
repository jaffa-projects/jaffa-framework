/*
 * ===================================================================
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
 * =================================================================== */
package org.jaffa.modules.printing.services;

import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.DomainObjectNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Locale;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.jaffa.modules.printing.domain.*;
import org.jaffa.modules.printing.services.exceptions.DataBeanClassNotFoundException;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.integrationapi.LocaleProvider;
import org.jaffa.integrationimpl.ContextManagerLocaleProvider;
import org.jaffa.persistence.*;
import org.jaffa.security.SecurityManager;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.presentation.portlet.session.LocaleContext;

/**
 * This will produce a form or label based on a request and either mail, print or
 * web publish the result
 */
public class FormProcessor {

    /** Logger reference */
    private final static Logger log = Logger.getLogger(FormProcessor.class);
    private final static String RIGHT_TO_LEFT_FORM_ALTERNATE = "RL";
    private static IFormLocalization formLocalizationHelper;
    private static LocaleProvider localeProvider = new ContextManagerLocaleProvider();

    public static void setFormLocalization(IFormLocalization formLocalization) {
        formLocalizationHelper = formLocalization;
    }

    public static IFormLocalization getFormLocalization() {
        return formLocalizationHelper;
    }

    /**
     * Process the FormPrintRequest. This will look at the form definition to use, locate it, build the
     * databean to use, and generate the output form. If follow-on forms are needed these will be generated
     * and merged in. It will then either print or email the generate form.<p>
     * Modified so 'null' DocumentObject will not create blank pages. If no pages are added, nothing will be delivered
     *
     * @param request the object that contains the key to the data, the form to be printed and
     * details about the printer and/or email that should be used for the output
     * @throws org.jaffa.exceptions.FrameworkException thrown if there is an infrastructure error
     * @throws org.jaffa.exceptions.ApplicationExceptions throw if there is an application error that should be repoted to the user
     */
    public static void process(FormPrintRequest request) throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled()) {
            log.debug("Begin processing form print request.  Form name = " + request.getFormName()
                    + ", Form Alternate = " + request.getFormAlternateName() + ", Form Variation = " + request.getVariation() + ", Printer Id = " + request.getPrinterId()
                    + ", Email Address = " + request.getEmailToAddresses() + ", User = " + request.getUserName() + ", Copies = " + request.getPrintCopies());
        }
        Map<IDataBean, DocumentPrintedListener> printedListeners = new HashMap<IDataBean, DocumentPrintedListener>();
        File outFile = null;
        UOW uow = new UOW();
        FormCache cache = new FormCache();
        MultiFormPrintEngine engine = null;
        //Get Locale Country
        Locale localeObject = LocaleContext.getLocale();
        String country = localeObject != null ? localeObject.getCountry() : null;
        String formOutputType = null;

        try {
            // Lookup the printer, to get the output type
            PrinterDefinition printer = null;
            String outputType = null;
            if (request.getPrinterId() != null) {
                printer = PrinterDefinition.findByPK(uow, request.getPrinterId());
                if (printer == null) {
                    log.error("Invalid printer id on form request: Printer = " + request.getPrinterId());
                    throw new ApplicationExceptions(
                            new DomainObjectNotFoundException(PrinterDefinitionMeta.getLabelToken()));
                }
                outputType = printer.getOutputType();
            }

            String formName = request.getFormName();
            String alternateName = request.getFormAlternateName();
            String variation = request.getVariation();
            String[] keyNames = null;
            if (request.getKeys() != null) {
                keyNames = (String[]) request.getKeys().keySet().toArray(new String[]{});
            }
            Object firstDom = null;
            int documentCounter = 0;

            // Main assemble loop if this has follow-on forms
            while (formName != null) {
                FormDefinition formDefinition = findFormDefinition(uow, formName, alternateName, variation, outputType, keyNames);
                if (formDefinition == null) {
                    if (outputType == null) {
                        log.error("Form Not Found. Name=" + request.getFormName() + ", Alt=" + request.getFormAlternateName()
                                + ", Variation=" + request.getVariation() + ", OutputType=" + outputType);
                        throw new ApplicationExceptions(new DomainObjectNotFoundException(FormDefinitionMeta.getLabelToken()));
                    } else {
                        outputType = null;
                        formDefinition = findFormDefinition(uow, formName, alternateName, variation, outputType, keyNames);
                        if (formDefinition == null) {
                            log.error("Form Not Found. Name=" + request.getFormName() + ", Alt=" + request.getFormAlternateName()
                                    + ", Variation=" + request.getVariation() + ", OutputType=" + outputType);
                            throw new ApplicationExceptions(new DomainObjectNotFoundException(FormDefinitionMeta.getLabelToken()));
                        } else {
                            throw new ApplicationExceptions(new ApplicationException("exception.org.jaffa.modules.printing.services.FormProcessor.OutputTypeMismatch") {
                            });
                        }
                    }
                }
                formOutputType = formDefinition.getOutputType();
                // See if we have the DOM for this form already, if not build it
                IDataBean data = cache.lookupForm(formDefinition);
                if (data == null) {
                    // build the form DOM
                    data = buildDataBean(request, formDefinition);
                    cache.addForm(formDefinition, data);

                    // Determine if the form bean specifies a different form alternateName.
                    // This is only done if alternateName has not already been specified by
                    // the FormPrintRequest or as a FollowOnFormAlternate.
                    if (alternateName == null) {
                        if (data.getFormAlternateName() != null) {
                            log.debug("Form " + formName + " switch to alternateName " + data.getFormAlternateName() + " from " + alternateName);
                            alternateName = data.getFormAlternateName();
                            continue;
                        }
                    }
                }

                // Add this form for printing
                if (engine == null) {
                    // set up the engine
                    engine = new MultiFormPrintEngine();
                    // get the form type so the correct factory can be set...
                    try {
                        engine.setEngineType(formDefinition.getFormGroupObject().getFormType());
                    } catch (ValidationException e) {
                        // This should only happen if the FormGroup object is not found
                        throw new ApplicationExceptions(e);
                    }
                }
                String templateName = cache.getTemplate(formDefinition, engine.getEngineType());

                // If data.getDocumentRoot() is an object array, add a document for each entry in the array.
                if (data.getDocumentRoot() != null && data.getDocumentRoot().getClass().isArray()) {
                    Object[] doms = (Object[]) data.getDocumentRoot();
                    for (int i = 0; i < doms.length; i++) {
                        if (firstDom == null) {
                            firstDom = doms[i];
                        }
                        // Only add document if it has data
                        if (doms[i] != null) {
                            documentCounter++;
                            engine.addDocument(templateName, doms[i]);
                        }
                    }
                    if ("<CREATE XML FILE>".equals(formDefinition.getRemarks())) {
                        writeXsdAndXml(doms[0]);
                    }                
                } else { 
                    if (firstDom == null) {
                        firstDom = data.getDocumentRoot();
                    }
                    // Only add document if it has data
                    if (data.getDocumentRoot() != null) {
                        documentCounter++;
                        engine.addDocument(templateName, data.getDocumentRoot());
                    }
                    if ("<CREATE XML FILE>".equals(formDefinition.getRemarks())) {
                        writeXsdAndXml(data.getDocumentRoot());
                    }
                }

                // Get the DocumentPrintedListerers for this DataBean, if they have not already be aquired
                if (!printedListeners.containsKey(data)) {
                    printedListeners.put(data, data.getDocumentPrintedListener());
                }

                // Get the follow-on form so it will be process in the next iteration
                formName = formDefinition.getFollowOnFormName();
                alternateName = formDefinition.getFollowOnFormAlternate();
            }

            if (documentCounter == 0) {
                log.debug("No pages added to engine, dataBeans were null. No output will be generated");
            } else {
                // Set override page size for PDF forms
                if ("PDF".equals(formOutputType)) {
                    String pageSize = printer != null && printer.getScaleToPageSize() != null ? printer.getScaleToPageSize() : null;
                    if (pageSize == null) {
                        pageSize = (String) ContextManagerFactory.instance().getProperty("jaffa.printing.defaultScaleToPageSize" + "." + country);
                    }
                    if (pageSize != null && !"".equals(pageSize)) {
                        engine.setPageSize(pageSize);
                    }
                }
                
                // Generate the output
                engine.generate();

                // Get the generated output
                if (request.getSaveFileName() != null) {
                    outFile = engine.writeForm(new File(request.getSaveFileName()));
                } else {
                    outFile = engine.writeForm();
                }

                if (log.isDebugEnabled()) {
                    log.debug("Created file : " + (outFile == null ? "NULL!!" : outFile.getAbsolutePath()));
                }

                // Send file to printer and email if required
                FormDelivery.deliver(request, outFile, firstDom);
                if (log.isDebugEnabled()) {
                    log.debug("Returned from delivering the form.  Print File: " + (outFile == null ? "NULL" : outFile.getAbsolutePath()));
                }

                // Invoke all the DocumentPrintedListerer
                for (DocumentPrintedListener listener : printedListeners.values()) {
                    if (listener != null) {
                        listener.documentPrinted(new EventObject(request));
                    }
                }
                if (log.isDebugEnabled()) {
                    log.debug("Returned from setting form printed date.  Print File: " + (outFile == null ? "NULL" : outFile.getAbsolutePath()));
                }
            }

        } finally {
            if (uow != null) {
                uow.rollback(); // This UOW should have not been used for updates!!!
            }
            // Clean up the temporary file unless it has been written specifically for web publishing
            if (outFile != null) {
                if (log.isDebugEnabled()) {
                    log.debug("FILE NOT CLEANED UP IN DEBUG MODE: " + outFile.getAbsolutePath());
                } else if (request.getSaveFileName() == null) {
                    if (!outFile.delete()) {
                        log.error("Failed to delete the temporary form file " + outFile.getAbsolutePath());
                    }
                }
            }
        }

    }

    /**
     * Find the Form Definition for the given form name, form alternate,
     * variation, output type, key set and right-to-left format. The elements of the input
     * String array of keys are concatenated into one String key set. The key
     * set must match Form Definition DOM key1, key2, key3, key4, key5 or key6.
     */
    public static FormDefinition findFormDefinition(UOW uow, String formName,
            String alternateName, String variation, String outputType, String[] keys)
            throws FrameworkException {

        FormDefinition form = null;
        String var = null;
        String alternate = null;
        String userLocale = getLocaleProvider().getLocale()!=null ? getLocaleProvider().getLocale().toString() : null; // en_US, ar_OM
        Boolean rtl = Boolean.FALSE;
        if (formLocalizationHelper != null && userLocale != null) {
            rtl = formLocalizationHelper.isLanguageRightToLeft(userLocale);
        }

        String rtlAlternate = null;
        if (alternateName == null || RIGHT_TO_LEFT_FORM_ALTERNATE.equals(alternateName)) {
            rtlAlternate = RIGHT_TO_LEFT_FORM_ALTERNATE;
        } else {
            rtlAlternate = alternateName + "-" + RIGHT_TO_LEFT_FORM_ALTERNATE;
        }

        StringBuilder sBuffer = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                if (sBuffer.length() > 0) {
                    sBuffer.append(';');
                }
                sBuffer.append(keys[i]);
            }
        }
        String keySet = sBuffer.toString();

        // Do up to four queries to find the FormDefinition.
        // Note that a customer variation takes precedence over a right-to-left alternate.
        // Get the right-to-left alternate, customer version of the FormDefinition.
        if (rtl.booleanValue() && variation != null) {
            var = variation;
            alternate = rtlAlternate;
            form = getFormDefinition(uow, formName, alternate, var, outputType, keySet);
        }

        // Get the specified form alternate(could be a right-to-left alternate), customer version of the FormDefinition.
        if (form == null && variation != null) {
            var = variation;
            alternate = alternateName;
            form = getFormDefinition(uow, formName, alternate, var, outputType, keySet);
        }

        // Get the right-to-left alternate, core version of the FormDefinition.
        if (form == null && rtl.booleanValue()) {
            var = null;
            alternate = rtlAlternate;
            form = getFormDefinition(uow, formName, alternate, var, outputType, keySet);
        }

        // Get the specified form alternate(could be a right-to-left alternate), core version of the FormDefinition.
        if (form == null) {
            var = null;
            alternate = alternateName;
            form = getFormDefinition(uow, formName, alternate, var, outputType, keySet);
        }

        return form;
    }
   
    public static FormDefinition getFormDefinition(UOW uow, String formName,
            String alternateName, String variation, String outputType, String keySet)
            throws FrameworkException {

        FormDefinition formDefinition = null;

        if (log.isDebugEnabled()) {
            log.debug("Looking for the Form Definition where the form alternate is " + alternateName + " and the form variation is " + variation);
        }

        Criteria c = new Criteria();
        c.setTable(FormDefinitionMeta.getName());
        c.addCriteria(FormDefinitionMeta.FORM_NAME, formName);

        if (alternateName != null) {
            c.addCriteria(FormDefinitionMeta.FORM_ALTERNATE, alternateName);
        } else {
            c.addCriteria(FormDefinitionMeta.FORM_ALTERNATE, Criteria.RELATIONAL_IS_NULL);
        }

        if (outputType != null) {
            c.addCriteria(FormDefinitionMeta.OUTPUT_TYPE, outputType);
        }

        if (keySet.length() > 0) {
            AtomicCriteria atomic = new AtomicCriteria();
            atomic.addCriteria(FormDefinitionMeta.DOM_KEY1, Criteria.RELATIONAL_EQUALS, keySet);
            atomic.addOrCriteria(FormDefinitionMeta.DOM_KEY2, Criteria.RELATIONAL_EQUALS, keySet);
            atomic.addOrCriteria(FormDefinitionMeta.DOM_KEY3, Criteria.RELATIONAL_EQUALS, keySet);
            atomic.addOrCriteria(FormDefinitionMeta.DOM_KEY4, Criteria.RELATIONAL_EQUALS, keySet);
            atomic.addOrCriteria(FormDefinitionMeta.DOM_KEY5, Criteria.RELATIONAL_EQUALS, keySet);
            atomic.addOrCriteria(FormDefinitionMeta.DOM_KEY6, Criteria.RELATIONAL_EQUALS, keySet);
            c.addAtomic(atomic);
        } else {
            c.addCriteria(FormDefinitionMeta.DOM_KEY1, Criteria.RELATIONAL_IS_NULL);
            c.addCriteria(FormDefinitionMeta.DOM_KEY2, Criteria.RELATIONAL_IS_NULL);
            c.addCriteria(FormDefinitionMeta.DOM_KEY3, Criteria.RELATIONAL_IS_NULL);
            c.addCriteria(FormDefinitionMeta.DOM_KEY4, Criteria.RELATIONAL_IS_NULL);
            c.addCriteria(FormDefinitionMeta.DOM_KEY5, Criteria.RELATIONAL_IS_NULL);
            c.addCriteria(FormDefinitionMeta.DOM_KEY6, Criteria.RELATIONAL_IS_NULL);
        }

        if (variation != null) {
            c.addCriteria(FormDefinitionMeta.FORM_VARIATION, variation);
        } else {
            c.addCriteria(FormDefinitionMeta.FORM_VARIATION, Criteria.RELATIONAL_IS_NULL);
        }

        Collection formDefs = uow.query(c);
        if (formDefs != null && formDefs.size() > 0) {
            formDefinition = (FormDefinition) formDefs.iterator().next();
            log.debug("Found Form Definition: Id " + formDefinition.getFormId() + ", Name " + formDefinition.getFormName()
                    + ", Alternate " + formDefinition.getFormAlternate() + ", Variation " + formDefinition.getFormVariation()
                    + ", keyset " + keySet + ".");
        }

        return formDefinition;
    }

    /** Build the data bean by getting the correct factory, and asking that factory
     * to build the appropriate bean
     */
    private static IDataBean buildDataBean(FormPrintRequest request, FormDefinition form)
            throws FrameworkException, ApplicationExceptions {

        try {
            IDataBeanFactory dbf = AbstractDataBeanFactory.newInstance(form.getDomFactory());
            dbf.setBeanClass(Class.forName(form.getDomClass()));
            String[] keyNames = null;
            if (request.getKeys() != null) {
                keyNames = (String[]) request.getKeys().keySet().toArray(new String[]{});
                for (String keyName : keyNames) {
                    String keyValue = (String) request.getKeys().get(keyName);
                    dbf.setKey(keyName, keyValue);
                    if (log.isDebugEnabled()) {
                        log.debug("Key name is " + keyName + ", Key value is " + keyValue);
                    }
                }
            }
            dbf.setFormName(request.getFormName());
            dbf.setAdditionalDataObject(request.getAdditionalDataObject());
            return dbf.getInstance();
        } catch (ClassNotFoundException e) {
            ApplicationExceptions aes = new ApplicationExceptions();
            aes.add(new DataBeanClassNotFoundException(e.getMessage(), e));
            throw aes;
        } catch (ApplicationException e) {
            ApplicationExceptions aes = new ApplicationExceptions();
            aes.add(e);
            throw aes;
        }
    }
    
    /**
     * Utility method to create XSD and XML files and write them to the temporary folder.
     */
    private static void writeXsdAndXml(Object domObject) {
        if (domObject != null) {
            try {
                JAXBContext context = JAXBContext.newInstance(domObject.getClass());
                PrintXmlUtility utility = new PrintXmlUtility(context);
                File xsdFileOut = File.createTempFile(domObject.getClass().getSimpleName(), ".xsd");
                utility.writeXsd(xsdFileOut.getAbsolutePath());
                File xmlFileOut = File.createTempFile(domObject.getClass().getSimpleName(), ".xml");
                utility.writeXml(domObject, xmlFileOut.getAbsolutePath());
                if (log.isDebugEnabled()) {
                    log.debug("XSD output path is " + xsdFileOut.getAbsolutePath());
                    log.debug("XML output path is " + xmlFileOut.getAbsolutePath());
                }
            } catch (JAXBException e) {
                log.error("Error when creating XSD and XML files. " + e.getMessage());
            } catch (IOException e) {
                log.error("Error when creating XSD and XML files. " + e.getMessage());
            }
        }
    }

    public static LocaleProvider getLocaleProvider() {
        return localeProvider;
    }

    public static void setLocaleProvider(LocaleProvider localeProvider) {
        FormProcessor.localeProvider = localeProvider;
    }
}
