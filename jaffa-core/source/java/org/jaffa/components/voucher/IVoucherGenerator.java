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

/** This is the interface for a class generating vouchers.
 * A client will typically obtain an instance from the VoucherGeneratorFactory.
 * It will then set the appropriate properties.
 * And then invoke the generate() method to obtain the voucher.
 * <p>
 * The following are the properties on an implemenation:
 * <ul>
 * <li> uow/connection: If the implemenation uses the database for generating the voucher, then it'll use the input UOW, if passed. Else it'll use the input Connection, if passed. Else it'll create a local UOW for generating the voucher.
 * <li> domainClassName/fieldName: Identifies the property for which the voucher is to be generated. An implementation could externalize the voucher-prefix for each property in some configuration-file, or it may have different voucher-generation logic for the different properties.
 * <li> type: Another discriminator in addition to the domainClassName and fieldName, which could be used by an implementation to provide a different voucher-generation logic.
 * <li> labelToken: If passed, and if any error occurs, the labelToken will be used in the error message.
 * <li> prefix: If passed, the implementation may use the prefix to generate the voucher.
 * <li> length: If passed, the implementation may use the length to generate the voucher.
 * </ul>
 */
public interface IVoucherGenerator {
    
    /** The client will invoke this method to generate a voucher.
     * @throws FrameworkException if any framework error occurs.
     * @throws ApplicationExceptions if any application error occurs.
     * @return a unique voucher.
     */
    public String generate() throws FrameworkException, ApplicationExceptions;
    
    
    
    /** Getter for the property uow.
     * @return Value of property uow.
     */
    public UOW getUow();
    
    /** Setter for property uow.
     * @param uow New value of property uow.
     */
    public void setUow(UOW uow);
    
    /** Getter for the property connection.
     * @return Value of property connection.
     */
    public Connection getConnection();
    
    /** Setter for property connection.
     * @param connection New value of property connection.
     */
    public void setConnection(Connection connection);
    
    /** Getter for the property domainClassName.
     * @return Value of property domainClassName.
     */
    public String getDomainClassName();
    
    /** Setter for property domainClassName.
     * @param domainClassName New value of property domainClassName.
     */
    public void setDomainClassName(String domainClassName);
    
    /** Getter for the property fieldName.
     * @return Value of property fieldName.
     */
    public String getFieldName();
    
    /** Setter for property fieldName.
     * @param fieldName New value of property fieldName.
     */
    public void setFieldName(String fieldName);
    
    /** Getter for the property type.
     * @return Value of property type.
     */
    public String getType();
    
    /** Setter for property type.
     * @param type New value of property type.
     */
    public void setType(String type);
    
    /** Getter for the property labelToken.
     * @return Value of property labelToken.
     */
    public String getLabelToken();
    
    /** Setter for property labelToken.
     * @param labelToken New value of property labelToken.
     */
    public void setLabelToken(String labelToken);
    
    /** Getter for the property prefix.
     * @return Value of property prefix.
     */
    public String getPrefix();
    
    /** Setter for property prefix.
     * @param prefix New value of property prefix.
     */
    public void setPrefix(String prefix);
    
    /** Getter for the property length.
     * @return Value of property length.
     */
    public Integer getLength();
    
    /** Setter for property length.
     * @param length New value of property length.
     */
    public void setLength(Integer length);
    
}
