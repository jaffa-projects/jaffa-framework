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

import java.io.Serializable;
import java.util.Properties;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PrintRequest implements Serializable {

    /**
     * Holds value of property documentProperties.
     */
    private Properties m_documentProperties;

    /**
     * Holds value of property emailToAddresses.
     */
    private String m_emailToAddresses;

    /**
     * Holds value of property saveFileName.
     */
    private String m_saveFileName;

    /**
     * Holds value of property printerId.
     */
    private String m_printerId;

    /**
     * Holds value of property printCopies.
     */
    private Integer m_printCopies;

    /**
     * Holds value of property userName.
     */
    private String m_userName;

    /**
     * Holds value of property emailMessage.
     */
    private String m_emailMessage;

    /**
     * Holds value of property emailFromAddress.
     */
    private String m_emailFromAddress;

    /**
     * Holds value of property emailSubject.
     */
    private String m_emailSubject;


    /**
     * Getter for property documentProperties.
     * @return Value of property documentProperties.
     */
    public Properties getDocumentProperties() {
        return m_documentProperties;
    }

    /**
     * Setter for property documentProperties.
     * @param documentProperties New value of property documentProperties.
     */
    public void setDocumentProperties(Properties documentProperties) {
        m_documentProperties = documentProperties;
    }

    /**
     * Getter for property emailToAddresses.
     * @return Value of property emailToAddresses.
     */
    public String getEmailToAddresses() {
        return m_emailToAddresses;
    }

    /**
     * Setter for property emailToAddresses. This is a semi-colon seperated list of
     * email addresses the document should be sent to as an attachment.
     *
     * @param emailToAddresses New value of property emailToAddresses.
     */
    public void setEmailToAddresses(String emailToAddresses) {
        m_emailToAddresses = emailToAddresses;
    }

    /**
     * Getter for property saveFileName.
     * @return Value of property saveFileName.
     */
    public String getSaveFileName() {
        return m_saveFileName;
    }

    /**
     * Setter for property saveFileName. This will be the name of the output file.
     * It should be supplied as a valid URL that can be written to. It is not valid
     * to use protocols such as HTTP, but you can use the extended jaffa protocols
     * such as classpath: and webroot:
     * <p>
     * If no file name is specified, the print request will create a temporary file
     * and delete it when it has finished. If specify the file name. The file will
     * not be deleted, so you can use it after the form has been generated.
     *
     * @param saveFileName New value of property saveFileName.
     */
    public void setSaveFileName(String saveFileName) {
        m_saveFileName = saveFileName;
    }

    /**
     * Getter for property printerId..
     * @return Value of property printerId..
     */
    public String getPrinterId() {
        return m_printerId;
    }

    /**
     * Setter for property printerId. Name of the printer to send the output to.
     * This must not be null, and printCopies must be non-zero for the generated form
     * to print. The printer name must also be a valid local or remote printer.
     * @param printerId New value of property printerId.
     */
    public void setPrinterId(String printerId) {
        m_printerId = printerId;
    }

    /**
     * Getter for property printCopies.
     * @return Value of property printCopies.
     */
    public Integer getPrintCopies() {
        return m_printCopies;
    }

    /**
     * Setter for property printCopies. Number of copied to print. A value of zero
     * will not print anything.
     * @param printCopies New value of property printCopies.
     */
    public void setPrintCopies(Integer printCopies) {
        m_printCopies = printCopies;
    }

    /**
     * Getter for property userName.
     * @return Value of property userName.
     */
    public String getUserName() {
        return m_userName;
    }

    /**
     * Setter for property userName. This name will be stamped on any generated
     * documents.
     * @param userName New value of property userName.
     */
    public void setUserName(String userName) {
        m_userName = userName;
    }

    /**
     * Getter for property emailMessage.
     * @return Value of property emailMessage.
     */
    public String getEmailMessage() {
        return m_emailMessage;
    }

    /**
     * Setter for property emailMessage. If any email addresses have been specified
     * then this value can be set to override the default message that will be used
     * as the body of the email when sending out the generated form as an email
     * attachment.
     *
     * @param emailMessage New value of property emailMessage.
     */
    public void setEmailMessage(String emailMessage) {
        m_emailMessage = emailMessage;
    }

    /**
     * Getter for property emailFromAddress.
     * @return Value of property emailFromAddress.
     */
    public String getEmailFromAddress() {
        return m_emailFromAddress;
    }

    /**
     * Setter for property emailFromAddress.
     * @param emailFromAddress New value of property emailFromAddress.
     */
    public void setEmailFromAddress(String emailFromAddress) {
        m_emailFromAddress = emailFromAddress;
    }

    /**
     * Getter for property emailSubject.
     * @return Value of property emailSubject.
     */
    public String getEmailSubject() {
        return m_emailSubject;
    }

    /**
     * Setter for property emailSubject.
     * @param emailSubject New value of property emailSubject.
     */
    public void setEmailSubject(String emailSubject) {
        m_emailSubject = emailSubject;
    }

}
