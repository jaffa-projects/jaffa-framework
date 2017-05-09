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

package org.jaffa.util;

import java.io.File;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import org.jaffa.presentation.portlet.widgets.model.TableModel;
import java.util.List;
import java.util.Iterator;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;

/** Routines for sending emails, with Microsoft Excel Attachements
 * Also added emailing of general file attachments
 *
 * @version 1.1
 * @author PaulE
 * @since 1.3
 */
public class EmailHelper {
    private static Logger log = Logger.getLogger(EmailHelper.class);
    
    
    /** Sends an email with an Excel spreadsheet as an attachment
     * @param smtpLocalhost (Optional) Name of host sending message, used in the HELO message for
     * server authentication
     * @param smtpUser (Optional) User name to authenticate to mail server. Not needed if server
     * is an 'open relay'
     * @param smtpPass (Optional) Password to authenticate to mail server. Not needed if server
     * is an 'open relay'
     * @param smtpHost (REQUIRED) name of mail server ie mail.yahoo.com
     * @param from (REQUIRED) from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body, in addition to the attachment
     * @param excelData Data to convert to spread sheet. Mkae sure to specify 'String' as a datatype on columns that
     * excel should not auto format
     * @throws MessagingException Contains error if message could not be sent
     */
    public static void emailExcel(String smtpHost, String smtpLocalhost, String smtpUser, String smtpPass, Boolean sendpartial, String from,
            String[] to, String subject, String bodyText, TableModel excelData)
            throws MessagingException {
        emailExcel(smtpHost, smtpLocalhost, smtpUser, smtpPass, sendpartial, from, to, subject, bodyText, new TableModel[] {excelData});
    }
    
    /** Sends an email with Excel spreadsheet(s) as attachment(s).
     * @param smtpLocalhost (Optional) Name of host sending message, used in the HELO message for
     * server authentication
     * @param smtpUser (Optional) User name to authenticate to mail server. Not needed if server
     * is an 'open relay'
     * @param smtpPass (Optional) Password to authenticate to mail server. Not needed if server
     * is an 'open relay'
     * @param smtpHost (REQUIRED) name of mail server ie mail.yahoo.com
     * @param from (REQUIRED) from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body, in addition to the attachment
     * @param excelDataArray An array of data to convert to spread sheet. Mkae sure to specify 'String' as a datatype on columns that
     * excel should not auto format
     * @throws MessagingException Contains error if message could not be sent
     */
    public static void emailExcel(String smtpHost, String smtpLocalhost, String smtpUser, String smtpPass, Boolean sendpartial, String from,
            String[] to, String subject, String bodyText, TableModel[] excelDataArray)
            throws MessagingException {
        BodyPart[] attachments = new BodyPart[excelDataArray.length];
        for (int i = 0; i < excelDataArray.length; i++) {
            // Create Excel Attachment
            TableModel excelData = excelDataArray[i];
            StringBuffer sb = new StringBuffer();
            convertTableToExcel(excelData, sb);
            
            // Make it an Email Body Part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(sb.toString(),"text/html");
            messageBodyPart.setFileName("excel-file-" + i + ".xls");
            messageBodyPart.setHeader("Content-Type", "application/vnd.ms-excel");
            attachments[i] = messageBodyPart;
        }
        
        // Email excel as attachment(s)
        emailAttachments(smtpHost, smtpLocalhost, smtpUser, smtpPass, sendpartial, from, to, subject, bodyText, attachments);
    }
    
    /** Sends an email with an Excel spreadsheet as an attachment
     * @param smtpLocalhost (Optional) Name of host sending message, used in the HELO message for
     * server authentication
     * @param smtpUser (Optional) User name to authenticate to mail server. Not needed if server
     * is an 'open relay'
     * @param smtpPass (Optional) Password to authenticate to mail server. Not needed if server
     * is an 'open relay'
     * @param smtpHost (REQUIRED) name of mail server ie mail.yahoo.com
     * @param from (REQUIRED) from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body, in addition to the attachment
     * @param files Files to be attached to e-mail
     * @throws MessagingException Contains error if message could not be sent
     */
    public static void emailFiles(String smtpHost, String smtpLocalhost, String smtpUser, String smtpPass, Boolean sendpartial, String from,
            String[] to, String subject, String bodyText, File[] files)
            throws MessagingException {
        BodyPart[] parts = null;
        if (files != null) {
            parts = new MimeBodyPart[files.length];
            
            // Create a part for each attachment
            for(int i=0;i<files.length; i++) {
                parts[i] = new MimeBodyPart();
                FileDataSource fileDataSource = new FileDataSource(files[i]);
                parts[i].setDataHandler(new DataHandler(fileDataSource));
                parts[i].setFileName(fileDataSource.getName());
                parts[i].setDisposition(Part.ATTACHMENT);
            }
        }
        
        // Email it as an attachments
        EmailHelper.emailAttachments(smtpHost, smtpLocalhost, smtpUser, smtpPass, sendpartial, from,
                to, subject, bodyText, parts );
    }
    
    
    /** Sends an email with an Excel spreadsheet as an attachment
     * @param smtpLocalhost (Optional) Name of host sending message, used in the HELO message for
     * server authentication
     * @param smtpUser (Optional) User name to authenticate to mail server. Not needed if server
     * is an 'open relay'
     * @param smtpPass (Optional) Password to authenticate to mail server. Not needed if server
     * is an 'open relay'
     * @param smtpHost (REQUIRED) name of mail server ie mail.yahoo.com
     * @param from (REQUIRED) from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body, in addition to the attachment
     * @param attachments All the extra objects to attched to this e-mail
     * @throws MessagingException Contains error if message could not be sent
     */
    public static void emailAttachments(String smtpHost, String smtpLocalhost, String smtpUser, String smtpPass, Boolean sendpartial, String from,
            String[] to, String subject, String bodyText, BodyPart[] attachments)
            throws MessagingException {
        if(smtpHost==null) throw new MessagingException("smptHost is required!");
        if(from==null) throw new MessagingException("from address is required!");
        if(to==null||to.length==0) throw new MessagingException("to address(es) is required!");
        
        // create some properties and get the default Session
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        if(sendpartial == null || sendpartial){
            props.put("mail.smtp.sendpartial", "true");
        }
        // Some servers need this for authentication
        if(smtpLocalhost!=null)
            props.put("mail.smtp.localhost", smtpLocalhost);
        if(log.isDebugEnabled()) {
            log.debug("Turned on SMTP Debug Output");
            props.put("mail.debug", "true");
        }
        Session session = null;
        // if Authentication is required
        if (smtpUser != null) {
            props.put("mail.smtp.auth", "true");
            session = Session.getInstance(props, new EmailHelperAuthenticator(smtpUser, smtpPass));
        } else {
            session = Session.getInstance(props, null);
        }
        if(log.isDebugEnabled()) {
            log.debug("Turned on SMTP Session Debug Output");
            session.setDebug(true);
        }
        
        // Define message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        for(int i=0;i<to.length; i++) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
        }
        message.setSubject(subject!=null?subject:"No Subject", "utf-8" );
        
        // Create the multi-part
        Multipart multipart = new MimeMultipart();
        
        BodyPart messageBodyPart;
        
        // Create part one, if there is some test
        if(bodyText != null) {
            messageBodyPart = new MimeBodyPart();
            // Fill the message
            if (isHtml(bodyText)) {
            	messageBodyPart.setHeader("Content-Type","text/html; charset=\"utf-8\""); 
            	messageBodyPart.setContent(bodyText, "text/html; charset=utf-8" ); 
            	messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
            }
            else
                messageBodyPart.setText(bodyText);
            
            // Add the first part
            multipart.addBodyPart(messageBodyPart);
        }
        if(attachments!=null)
            for(int i=0;i<attachments.length; i++)
                multipart.addBodyPart(attachments[i]);
        
        // Put parts in message
        message.setContent(multipart);
        try {
            // Send message
            Transport.send(message);
        }catch(SendFailedException e){
            if(sendpartial == null || sendpartial)
                log.error("Failed to Send Email to all Recipients",e);
            else
                throw e;
        }
    }
    
    
    
    private static void convertTableToExcel(TableModel excel, StringBuffer out) {
        out.append("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n");
        out.append("xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n");
        out.append("xmlns=\"http://www.w3.org/TR/REC-html40\">\n");
        out.append("\n");
        out.append("<head>\n");
        out.append("<meta http-equiv=Content-Type content=\"text/html; charset=windows-1252\">\n");
        out.append("<meta name=ProgId content=Excel.Sheet>\n");
        out.append("<meta name=Generator content=\"Microsoft Excel 10\">\n");
        out.append("<style>\n");
        out.append("<!--table\n");
        out.append("	{mso-displayed-decimal-separator:\"\\.\";\n");
        out.append("	mso-displayed-thousand-separator:\"\\,\";}\n");
        out.append("@page\n");
        out.append("	{margin:1.0in .75in 1.0in .75in;\n");
        out.append("	mso-header-margin:.5in;\n");
        out.append("	mso-footer-margin:.5in;}\n");
        out.append("tr\n");
        out.append("	{mso-height-source:auto;}\n");
        out.append("col\n");
        out.append("	{mso-width-source:auto;}\n");
        out.append("br\n");
        out.append("	{mso-data-placement:same-cell;}\n");
        out.append(".style0\n");
        out.append("	{mso-number-format:General;\n");
        out.append("	text-align:general;\n");
        out.append("	vertical-align:bottom;\n");
        out.append("	white-space:nowrap;\n");
        out.append("	mso-rotate:0;\n");
        out.append("	mso-background-source:auto;\n");
        out.append("	mso-pattern:auto;\n");
        out.append("	color:windowtext;\n");
        out.append("	font-size:10.0pt;\n");
        out.append("	font-weight:400;\n");
        out.append("	font-style:normal;\n");
        out.append("	text-decoration:none;\n");
        out.append("	font-family:Arial;\n");
        out.append("	mso-generic-font-family:auto;\n");
        out.append("	mso-font-charset:0;\n");
        out.append("	border:none;\n");
        out.append("	mso-protection:locked visible;\n");
        out.append("	mso-style-name:Normal;\n");
        out.append("	mso-style-id:0;}\n");
        out.append("td\n");
        out.append("	{mso-style-parent:style0;\n");
        out.append("	padding-top:1px;\n");
        out.append("	padding-right:1px;\n");
        out.append("	padding-left:1px;\n");
        out.append("	mso-ignore:padding;\n");
        out.append("	color:windowtext;\n");
        out.append("	font-size:10.0pt;\n");
        out.append("	font-weight:400;\n");
        out.append("	font-style:normal;\n");
        out.append("	text-decoration:none;\n");
        out.append("	font-family:Arial;\n");
        out.append("	mso-generic-font-family:auto;\n");
        out.append("	mso-font-charset:0;\n");
        out.append("	mso-number-format:General;\n");
        out.append("	text-align:general;\n");
        out.append("	vertical-align:bottom;\n");
        out.append("	border:none;\n");
        out.append("	mso-background-source:auto;\n");
        out.append("	mso-pattern:auto;\n");
        out.append("	mso-protection:locked visible;\n");
        out.append("	white-space:nowrap;\n");
        out.append("	mso-rotate:0;}\n");
        out.append("-->\n");
        out.append("</style>\n");
        out.append("<!--[if gte mso 9]><xml>\n");
        out.append(" <x:ExcelWorkbook>\n");
        out.append("  <x:ExcelWorksheets>\n");
        out.append("   <x:ExcelWorksheet>\n");
        out.append("    <x:Name>Sheet 1</x:Name>\n");
        out.append("    <x:WorksheetOptions>\n");
        out.append("     <x:Selected/>\n");
        out.append("     <x:ProtectContents>False</x:ProtectContents>\n");
        out.append("     <x:ProtectObjects>False</x:ProtectObjects>\n");
        out.append("     <x:ProtectScenarios>False</x:ProtectScenarios>\n");
        out.append("    </x:WorksheetOptions>\n");
        out.append("   </x:ExcelWorksheet>\n");
        out.append("  </x:ExcelWorksheets>\n");
        out.append("  <x:ProtectStructure>False</x:ProtectStructure>\n");
        out.append("  <x:ProtectWindows>False</x:ProtectWindows>\n");
        out.append(" </x:ExcelWorkbook>\n");
        out.append("</xml><![endif]-->\n");
        out.append("</head>\n");
        out.append("\n");
        out.append("<body link=blue vlink=purple>\n");
        out.append("\n");
        out.append("<table x:str border=0 cellpadding=0 cellspacing=0 style='border-collapse: collapse;table-layout:fixed;'>\n");
        
        List colList = excel.getColumnNames();
        if(colList != null) {
            // Write out column headers
            String[] colName = new String[colList.size()];
            String[] colType = new String[colList.size()];
            out.append("  <tr>\n");
            int i = 0;
            for(Iterator it = colList.iterator(); it.hasNext(); i++) {
                colName[i] = (String) it.next();
                colType[i] = excel.getColumnDataType(colName[i]);
                if(colType[i] == null)
                    colType[i] = "string";
                else
                    colType[i] = colType[i].toLowerCase();
                out.append("    <td x:str='");
                if(colName[i]!=null)
                    out.append(StringHelper.convertToHTML(colName[i]));
                out.append("'>");
                if(colName[i]!=null)
                    out.append(StringHelper.convertToHTML(colName[i]));
                out.append("</td>\n");
            }
            out.append("  </tr>\n");
            
            // Loop for each row...
            List rowList = excel.getRows();
            if(rowList != null) {
                
                for(Iterator it = rowList.iterator(); it.hasNext();) {
                    List row = (List) it.next();
                    out.append("  <tr>\n");
                    i=0;
                    if(row != null)
                        for(Iterator it2 = row.iterator(); it2.hasNext(); i++) {
                        Object value = it2.next();
                        out.append("    <td");
                        if("string".equalsIgnoreCase(colType[i])) {
                            out.append(" x:str='");
                            if(value!=null)
                                out.append(StringHelper.convertToHTML(value.toString()));
                            out.append("'");
                        }
                        out.append(">");
                        if(value!=null)
                            out.append(StringHelper.convertToHTML(value.toString()));
                        out.append("</td>\n");
                        }
                    out.append("  </tr>\n");
                }
            } // rowList
        } // colList
        out.append("</table>\n");
        out.append("\n");
        out.append("</body>\n");
        out.append("\n");
        out.append("</html>\n");
    }
    
    /** Returns true if the input String begins with <HTML> and ends with </HTML>.
     * @param contents The String to test if its HTML.
     * @return true if the input String begins with <HTML> and ends with </HTML>.
     */
    private static boolean isHtml(String contents) {
        if (contents != null) {
            String s = contents.trim().toLowerCase();
            return s.startsWith("<html>") && s.endsWith("</html>");
        } else
            return false;
    }
    
}