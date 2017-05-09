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
 * 1.    Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.    Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 * 3.    The name "JAFFA" must not be used to endorse or promote products derived from
 *     this Software without prior written permission. For written permission,
 *     please contact mail to: jaffagroup@yahoo.com.
 * 4.    Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *     appear in their names without prior written permission.
 * 5.    Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

import java.io.StringWriter;
import java.util.Map;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.jaffa.datatypes.Parser;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.presentation.portlet.widgets.model.TableModel;
import java.io.File;


/** Used by the e-mail helper to get the details of the email server
 * It reads the configuration from the ContextManager by default but can be
 * injected with values from the AOP rules engine instead.
 * <p>
 * Configuration setting read from the ContextManager are
 * <ul>
 * <li>jaffa.email.smtpHost - (REQUIRED) name of mail server ie mail.yahoo.com
 * <li>jaffa.email.smtpLocalHost - (Optional) Name of host sending message, used in the HELO message for server authentication
 * <li>jaffa.email.smtpUser - (Optional) User name to authenticate to mail server. Not needed if server is an 'open relay'
 * <li>jaffa.email.smtpPassword - (Optional) Password to authenticate to mail server. Not needed if server is an 'open relay'
 * <li>jaffa.email.administrator - (Optional) from email address, used if one is not provided by the calling program. ie admin@jaffa.sf.net
 * </ul>
 *
 * @version 1.1
 * @author PaulE
 */
public class EmailerBean {

    private static final Logger log = Logger.getLogger(EmailerBean.class);

    private String smtpHost;
    private String smtpLocalHost;
    private String smtpUser;
    private String smtpPassword;
    private String administrator;
    private Boolean sendpartial;
    private static VelocityEngine velocityEngine;

    static {
        // Initialize Velocity
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        velocityEngine.setProperty(Velocity.RUNTIME_LOG, System.getProperty("jboss.server.home.dir")+File.separator+"log"+File.separator+"velocity.log");
        velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, true);
        velocityEngine.init();
        velocityEngine.getLog().debug("Velocity Engine Initialized");
    }

    public EmailerBean() {
        smtpHost = nullify( (String) ContextManagerFactory.instance().getProperty("jaffa.email.smtpHost") );
        smtpLocalHost = nullify( (String) ContextManagerFactory.instance().getProperty("jaffa.email.smtpLocalHost") );
        smtpUser = nullify( (String) ContextManagerFactory.instance().getProperty("jaffa.email.smtpUser") );
        smtpPassword = nullify( (String) ContextManagerFactory.instance().getProperty("jaffa.email.smtpPassword") );
        administrator = nullify( (String) ContextManagerFactory.instance().getProperty("jaffa.email.administrator") );
        if(administrator==null)
            administrator = "admin@jaffa.sf.net";
        sendpartial = Parser.parseBoolean((String) ContextManagerFactory.instance().getProperty("jaffa.email.sendpartial"));
    }

    public void setAdministrator(String value) {
        administrator = value;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setSmtpHost(String value) {
        smtpHost = value;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpLocalHost(String value) {
        smtpLocalHost = value;
    }

    public String getSmtpLocalHost() {
        return smtpLocalHost;
    }

    public void setSmtpUser(String value) {
        smtpUser = value;
    }

    public String getSmtpUser() {
        return smtpUser;
    }

    public void setSmtpPassword(String value) {
        smtpPassword = value;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public Boolean getSendPartial() {
        return sendpartial;
    }

    public void setSendPartial(Boolean sendpartial) {
        this.sendpartial = sendpartial;
    }

    /** Sends an email from the administrator
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body, in addition to the attachment
     * excel should not auto format
     * @throws MessagingException Contains error if message could not be sent
     */
    public void sendMail(String[] to, String subject, String bodyText)
    throws MessagingException {
        EmailHelper.emailAttachments(getSmtpHost(), getSmtpLocalHost(), getSmtpUser(), getSmtpPassword(), getSendPartial(),
        administrator, to, subject, bodyText, null);
    }

    /** Sends an email from a specified "from address"
     * @param from from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body, in addition to the attachment
     * excel should not auto format
     * @throws MessagingException Contains error if message could not be sent
     */
    public void sendMail(String from, String[] to, String subject, String bodyText)
    throws MessagingException {
        EmailHelper.emailAttachments(getSmtpHost(), getSmtpLocalHost(), getSmtpUser(), getSmtpPassword(), getSendPartial(),
        from!=null?from:administrator, to, subject, bodyText, null);
    }

    /** Sends an email from a specified "from address", with an Excel Spreadsheet attachment
     * @param from from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body, in addition to the attachment
     * @param excelData Data to convert to spread sheet. Mkae sure to specify 'String' as a datatype on columns that
     * excel should not auto format
     * @throws MessagingException Contains error if message could not be sent
     */
    public void sendMail(String from, String[] to, String subject, String bodyText, TableModel excelData)
    throws MessagingException {
        EmailHelper.emailExcel(getSmtpHost(), getSmtpLocalHost(), getSmtpUser(), getSmtpPassword(), getSendPartial(),
        from!=null?from:administrator, to, subject, bodyText, excelData);
    }

    /** Sends an email with Excel spreadsheet(s) as attachment(s).
     * @param from from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body, in addition to the attachment
     * @param excelDataArray An array of data to convert to spread sheet. Mkae sure to specify 'String' as a datatype on columns that
     * excel should not auto format
     * @throws MessagingException Contains error if message could not be sent
     */
    public void sendMail(String from, String[] to, String subject, String bodyText, TableModel[] excelDataArray)
    throws MessagingException {
        EmailHelper.emailExcel(getSmtpHost(), getSmtpLocalHost(), getSmtpUser(), getSmtpPassword(), getSendPartial(),
        from!=null?from:administrator, to, subject, bodyText, excelDataArray);
    }

    /** Sends an email from a specified "from address", with an list of attached files
     * @param from from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body, in addition to the attachment
     * @param files Files to be attached to e-mail
     * @throws MessagingException Contains error if message could not be sent
     */
    public void sendMail(String from, String[] to, String subject, String bodyText, File[] files)
    throws MessagingException {
        EmailHelper.emailFiles(getSmtpHost(), getSmtpLocalHost(), getSmtpUser(), getSmtpPassword(), getSendPartial(),
        from!=null?from:administrator, to, subject, bodyText, files);
    }

    /** Sends an email from a specified "from address"
     * @param from from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body
     * @throws MessagingException Contains error if message could not be sent
     */
    public void sendMailTemplate(String[] to, String subject, String bodyText, Object data)
    throws MessagingException {
        sendMailTemplate(null, to, subject, bodyText, data);
    }

    /** Sends an email from a specified "from address"
     * @param from from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body
     * @throws MessagingException Contains error if message could not be sent
     */
    public void sendMailTemplate(String from, String[] to, String subject, String bodyText, Object data)
    throws MessagingException {
        sendMailTemplate(from, to, subject, bodyText, data, null);
    }
    
   /**
     * Sends an email with file attachments using a Velocity Template 
     * @param from from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body
     * @param data the Velocity context used for the email
     * @throws MessagingException Contains error if message could not be sent
    */
    public void sendMailTemplateWithFileAttachments(String from, String[] to, String subject, String bodyText, Object data, File[] fileAttachments)
    throws MessagingException {
    	subject = processTemplate(subject,data);
        bodyText = processTemplate(bodyText,data);
        EmailHelper.emailFiles(getSmtpHost(), getSmtpLocalHost(), getSmtpUser(), getSmtpPassword(), getSendPartial(),
        from!=null?from:administrator, to, subject, bodyText, fileAttachments);
    }

    /** Sends an email from a specified "from address"
     * @param from from email ie paul@yahoo.com
     * @param to (REQUIRED) array of strings to send email to ie bob@yahoo.com
     * @param subject Text for the email subject (defaults to 'No Subject' if null)
     * @param bodyText Text for the main mail's body
     * @throws MessagingException Contains error if message could not be sent
     */
    public void sendMailTemplate(String from, String[] to, String subject, String bodyText, Object data, BodyPart[] attachments)
    throws MessagingException {
        subject = processTemplate(subject,data);
        bodyText = processTemplate(bodyText,data);
        EmailHelper.emailAttachments(getSmtpHost(), getSmtpLocalHost(), getSmtpUser(), getSmtpPassword(), getSendPartial(),
        from!=null?from:administrator, to, subject, bodyText, attachments);
    }
    

    /** Expand all tokens in the string and then using the velocity engine process the
     * provided string as a template, where the data object will be available in the template as
     * {data.}, the context is also available at {context.}
     */
    public String processTemplate(String template, Object data)
    throws MessagingException {
        template = MessageHelper.replaceTokens(template);
        if(template==null)
            return "";
        VelocityContext context = new VelocityContext();
        if(data!=null) {
            if (data instanceof Map) {
                for (Object key : ((Map) data).keySet()) {
                    if (key instanceof String)
                        context.put((String) key, ((Map) data).get(key));
                }
            } else
                context.put("data", data);
        } else
            log.warn("Main Context Data Object is null");
        context.put("context", ContextManagerFactory.instance().getThreadContext());
        StringWriter output = new StringWriter();
        try {
            // The small cost of a synchronization block here is _much_ less than the
            // create/finalize overhead.
            synchronized (velocityEngine) {
                velocityEngine.evaluate(context, output, this.getClass().getSimpleName(), template);
            }
        } catch (Exception e) {
            String err = "Velocity Evaluation Error - " + e.getLocalizedMessage();
            log.error(err,e);
            throw new MessagingException(err,e);
        }
        return output.toString();
    }
    
    private String nullify(String s) {
        if(s==null || s.length()==0)
            return null;
        else
            return s;
    }

}