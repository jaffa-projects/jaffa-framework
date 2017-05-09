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
import org.jaffa.metadata.DecimalFieldMetaData;
import org.jaffa.datatypes.FieldValidator;

/** This implementation of the IFieldValidator ensures that the field has a Decimal value.
 */
public class DecimalFieldValidator extends AbstractFieldValidator {
    
    private String m_layout = null;
    private Double m_minValue = null;
    private Double m_maxValue = null;
    private Integer m_intSize = null;
    private Integer m_fracSize = null;
    
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
    public Double getMinValue() {
        return m_minValue;
    }
    
    /** Setter for property minValue.
     * @param minValue New value of property minValue.
     */
    public void setMinValue(Double minValue) {
        m_minValue = minValue;
    }
    
    /** Getter for property maxValue.
     * @return Value of property maxValue.
     */
    public Double getMaxValue() {
        return m_maxValue;
    }
    
    /** Setter for property maxValue.
     * @param maxValue New value of property maxValue.
     */
    public void setMaxValue(Double maxValue) {
        m_maxValue = maxValue;
    }
    
    /** Getter for property intSize.
     * @return Value of property intSize.
     */
    public Integer getIntSize() {
        return m_intSize;
    }
    
    /** Setter for property intSize.
     * @param intSize New value of property intSize.
     */
    public void setIntSize(Integer intSize) {
        m_intSize = intSize;
    }
    
    /** Getter for property fracSize.
     * @return Value of property fracSize.
     */
    public Integer getFracSize() {
        return m_fracSize;
    }
    
    /** Setter for property fracSize.
     * @param fracSize New value of property fracSize.
     */
    public void setFracSize(Integer fracSize) {
        m_fracSize = fracSize;
    }
    
    
    /** The RulesEngine will invoke this method to perform the field validation.
     * @throws ValidationException if any validation rule fails.
     * @throws FrameworkException if any framework error occurs.
     */
    public void validate() throws ValidationException, FrameworkException {
        Object value = getValue();
        if (value != null) {
            DecimalFieldMetaData meta = new DecimalFieldMetaData(null, getLabelToken(), null, getLayout(), getMinValue(), getMaxValue(), getIntSize(), getFracSize());
            if (value instanceof Double)
                FieldValidator.validate((Double) value, meta, false);
            else
                FieldValidator.validateData(value.toString(), meta);
        }
    }
    
}
