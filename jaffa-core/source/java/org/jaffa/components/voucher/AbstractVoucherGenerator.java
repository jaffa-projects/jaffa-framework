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

package org.jaffa.components.voucher;

import java.sql.Connection;
import org.jaffa.persistence.UOW;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

/** This is an abstract implementation of the IVoucherGenerator interface.
 *
 * @author  GautamJ
 */
public abstract class AbstractVoucherGenerator implements IVoucherGenerator {
    
    protected UOW m_uow = null;
    protected Connection m_connection = null;
    protected String m_domainClassName = null;
    protected String m_fieldName = null;
    protected String m_type = null;
    protected String m_labelToken = null;
    protected String m_prefix = null;
    protected Integer m_length = null;
    
    /** The client will invoke this method to generate a voucher.
     * @throws FrameworkException if any framework error occurs.
     * @throws ApplicationExceptions if any application error occurs.
     * @return a unique voucher.
     */
    public abstract String generate() throws FrameworkException, ApplicationExceptions;
    
    
    
    
    /** Getter for the property uow.
     * @return Value of property uow.
     */
    public UOW getUow() {
        return m_uow;
    }
    
    /** Setter for property uow.
     * @param uow New value of property uow.
     */
    public void setUow(UOW uow) {
        m_uow = uow;
    }
    
    /** Getter for the property connection.
     * @return Value of property connection.
     */
    public Connection getConnection() {
        return m_connection;
    }
    
    /** Setter for property connection.
     * @param connection New value of property connection.
     */
    public void setConnection(Connection connection) {
        m_connection = connection;
    }
    
    /** Getter for the property domainClassName.
     * @return Value of property domainClassName.
     */
    public String getDomainClassName() {
        return m_domainClassName;
    }
    
    /** Setter for property domainClassName.
     * @param domainClassName New value of property domainClassName.
     */
    public void setDomainClassName(String domainClassName) {
        m_domainClassName = domainClassName;
    }
    
    /** Getter for the property fieldName.
     * @return Value of property fieldName.
     */
    public String getFieldName() {
        return m_fieldName;
    }
    
    /** Setter for property fieldName.
     * @param fieldName New value of property fieldName.
     */
    public void setFieldName(String fieldName) {
        m_fieldName = fieldName;
    }
    
    /** Getter for the property type.
     * @return Value of property type.
     */
    public String getType() {
        return m_type;
    }
    
    /** Setter for property type.
     * @param type New value of property type.
     */
    public void setType(String type) {
        m_type = type;
    }
    
    /** Getter for the property labelToken.
     * @return Value of property labelToken.
     */
    public String getLabelToken() {
        return m_labelToken;
    }
    
    /** Setter for property labelToken.
     * @param labelToken New value of property labelToken.
     */
    public void setLabelToken(String labelToken) {
        m_labelToken = labelToken;
    }
    
    /** Getter for the property prefix.
     * @return Value of property prefix.
     */
    public String getPrefix() {
        return m_prefix;
    }
    
    /** Setter for property prefix.
     * @param prefix New value of property prefix.
     */
    public void setPrefix(String prefix) {
        m_prefix = prefix;
    }
    
    /** Getter for the property length.
     * @return Value of property length.
     */
    public Integer getLength() {
        return m_length;
    }
    
    /** Setter for property length.
     * @param length New value of property length.
     */
    public void setLength(Integer length) {
        m_length = length;
    }
    
}
