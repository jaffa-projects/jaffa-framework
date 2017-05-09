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

package org.jaffa.rules.fieldvalidators;

import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.metadata.StringFieldMetaData;
import org.jaffa.datatypes.FieldValidator;

/** This implementation of the IFieldValidator ensures that the field has a valid String value.
 */
public class StringFieldValidator extends AbstractFieldValidator {
    
    private String m_pattern;
    private Integer m_length;
    private String m_caseType;
    private Integer m_minLength;
    
    /** Getter for property pattern.
     * @return Value of property pattern.
     */
    public String getPattern() {
        return m_pattern;
    }
    
    /** Setter for property pattern.
     * @param pattern New value of property pattern.
     */
    public void setPattern(String pattern) {
        m_pattern = pattern;
    }
    
    /** Getter for property length.
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
    
    /** Getter for property caseType.
     * @return Value of property caseType.
     */
    public String getCaseType() {
        return m_caseType;
    }
    
    /** Setter for property caseType.
     * @param caseType New value of property caseType.
     */
    public void setCaseType(String caseType) {
        m_caseType = caseType;
    }
    
    /** Getter for property minLength.
     * @return Value of property minLength.
     */
    public Integer getMinLength() {
        return m_minLength;
    }

    /** Setter for property minLength.
     * @param minLength New value of property minLength.
     */
    public void setMinLength(Integer minLength) {
        m_minLength = minLength;
    }
    
    
    
    /** The RulesEngine will invoke this method to perform the field validation.
     * @throws ValidationException if any validation rule fails.
     * @throws FrameworkException if any framework error occurs.
     */
    public void validate() throws ValidationException, FrameworkException {
        Object value = getValue();
        if (value != null) {
            StringFieldMetaData meta = new StringFieldMetaData(null, getLabelToken(), null,  getPattern(), getLength(), getCaseType(), getMinLength());
            FieldValidator.validateData(value.toString(), meta);
        }
    }
}
