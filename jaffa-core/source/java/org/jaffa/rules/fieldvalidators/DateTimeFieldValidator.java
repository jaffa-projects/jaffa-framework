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

import java.util.Date;
import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.datatypes.DateTime;
import org.jaffa.metadata.DateTimeFieldMetaData;
import org.jaffa.datatypes.FieldValidator;

/** This implementation of the IFieldValidator ensures that the field has a DateTime value.
 */
public class DateTimeFieldValidator extends AbstractFieldValidator {
    
    private String m_layout = null;
    private DateTime m_minValue = null;
    private DateTime m_maxValue = null;
    
    /** Getter for property layout.
     * @return Value of property layout.
     */
    public String getLayout() {
        return m_layout;
    }
    
    /** Setter for property layout.
     * @param layout New value of property layout.
     */
    public void setLayout(String layout) {
        m_layout = layout;
    }
    
    /** Getter for property minValue.
     * @return Value of property minValue.
     */
    public DateTime getMinValue() {
        return m_minValue;
    }
    
    /** Setter for property minValue.
     * @param minValue New value of property minValue.
     */
    public void setMinValue(DateTime minValue) {
        m_minValue = minValue;
    }
    
    /** Getter for property maxValue.
     * @return Value of property maxValue.
     */
    public DateTime getMaxValue() {
        return m_maxValue;
    }
    
    /** Setter for property maxValue.
     * @param maxValue New value of property maxValue.
     */
    public void setMaxValue(DateTime maxValue) {
        m_maxValue = maxValue;
    }
    
    /** The RulesEngine will invoke this method to perform the field validation.
     * @throws ValidationException if any validation rule fails.
     * @throws FrameworkException if any framework error occurs.
     */
    public void validate() throws ValidationException, FrameworkException {
        Object value = getValue();
        if (value != null) {
            DateTimeFieldMetaData meta = new DateTimeFieldMetaData(null, getLabelToken(), null, getLayout(), getMinValue(), getMaxValue());
            if (value instanceof DateTime)
                FieldValidator.validate((DateTime) value, meta, false);
            else if (value instanceof Date)
                FieldValidator.validate(new DateTime((Date) value), meta, false);
            else
                FieldValidator.validateData(value.toString(), meta);
        }
    }
    
}
