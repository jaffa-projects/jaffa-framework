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

import org.apache.log4j.Logger;
import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.Criteria;
import java.util.*;
import org.jaffa.datatypes.exceptions.InvalidGenericForeignKeyException;
import org.jaffa.util.BeanHelper;

/**
 * This validates a field against a set of valid values for that field, as listed in a database table
 * For eg. Lets assume that there is database table 'VALID_FIELD_VALUE' which holds a set of valid values for certain fields in the system.
 * The structure of this table 'VALID_FIELD_VALUE' in oracle can be like -
 * TABLE_NAME	NOT NULL VARCHAR2(20)
 * FIELD_NAME	NOT NULL VARCHAR2(30)
 * LEGAL_VALUE	NOT NULL VARCHAR2(20)
 *
 * This table can hold values like -
 * TABLE_NAME      FIELD_NAME      LEGAL_VALUE
 * ==========      ==========      ===========
 * USER            DEPARTMENT      ACCOUNTS
 * USER            DEPARTMENT      ENGINEERING
 * USER            DEPARTMENT      PERSONNEL
 * ITEM            TYPE            CONSUMABLE
 * ITEM            TYPE            NON-CONSUMABLE
 *
 * This way, the table can hold valid values for the different fields
 *
 * The generic implementation provides the ValidFieldValue domain class and its mapping file. Tweak its mapping file to map it to the correct table and fields.
 * Or specify your own domain class.
 * The GenericForeignKeyFieldValidator definition in validators.xml requires the appropriate values to be passed in parameters.
 */
public class GenericForeignKeyFieldValidator extends AbstractFieldValidator {
    
    private static final Logger log = Logger.getLogger(GenericForeignKeyFieldValidator.class);
    private String m_domainClassName;
    private String m_fieldNameForTable;
    private String m_fieldNameForField;
    private String m_fieldNameForValue;
    private String m_tableName;
    private String m_fieldName;
    
    /** Getter for property domainClassName.
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
    
    /** Getter for property fieldNameForTable.
     * @return Value of property fieldNameForTable.
     */
    public String getFieldNameForTable() {
        return m_fieldNameForTable;
    }
    
    /** Setter for property fieldNameForTable.
     * @param fieldNameForTable New value of property fieldNameForTable.
     */
    public void setFieldNameForTable(String fieldNameForTable) {
        m_fieldNameForTable = fieldNameForTable;
    }
    
    /** Getter for property fieldNameForField.
     * @return Value of property fieldNameForField.
     */
    public String getFieldNameForField() {
        return m_fieldNameForField;
    }
    
    /** Setter for property fieldNameForField.
     * @param fieldNameForField New value of property fieldNameForField.
     */
    public void setFieldNameForField(String fieldNameForField) {
        m_fieldNameForField = fieldNameForField;
    }
    
    /** Getter for property fieldNameForValue.
     * @return Value of property fieldNameForValue.
     */
    public String getFieldNameForValue() {
        return m_fieldNameForValue;
    }
    
    /** Setter for property fieldNameForValue.
     * @param fieldNameForValue New value of property fieldNameForValue.
     */
    public void setFieldNameForValue(String fieldNameForValue) {
        m_fieldNameForValue = fieldNameForValue;
    }
    
    /** Getter for property tableName.
     * @return Value of property tableName.
     */
    public String getTableName() {
        return m_tableName;
    }
    
    /** Setter for property tableName.
     * @param tableName New value of property tableName.
     */
    public void setTableName(String tableName) {
        m_tableName = tableName;
    }
    
    /** Getter for property fieldName.
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
    
    /** The RulesEngine will invoke this method to perform the field validation.
     * @throws ValidationException if any validation rule fails.
     * @throws FrameworkException if any framework error occurs.
     */
    public void validate() throws ValidationException, FrameworkException {
        if (getValue() != null) {
            UOW uow = getUow();
            boolean localUow = (uow == null);
            try {
                if (localUow)
                    uow = new UOW();
                
                Criteria c = new Criteria();
                c.setTable(getDomainClassName());
                c.addCriteria(getFieldNameForTable(), getTableName());
                c.addCriteria(getFieldNameForField(), getFieldName());
                c.addCriteria(getFieldNameForValue(), getValue().toString());
                Collection col = uow.query(c);
                if (col.size() == 0) {
                    // Invalid value. Display the list of valid values in the error message
                    StringBuffer validValues = new StringBuffer();
                    c = new Criteria();
                    c.setTable(getDomainClassName());
                    c.addCriteria(getFieldNameForTable(), getTableName());
                    c.addCriteria(getFieldNameForField(), getFieldName());
                    c.addOrderBy(getFieldNameForValue(), Criteria.ORDER_BY_ASC);
                    for (Iterator i = uow.query(c).iterator(); i.hasNext(); ) {
                        try {
                            Object value = BeanHelper.getField(i.next(), getFieldNameForValue());
                            if (validValues.length() > 0)
                                validValues.append(',');
                            validValues.append(value);
                        } catch (Exception e) {
                            // do nothing
                        }
                    }
                    String str = "Generic ForeignKey validation failed for the value '" + getValue() + "' against the table/field - " + getTableName() + '/' + getFieldName() + ". Valid values are " + validValues.toString();
                    log.error(str);
                    throw new InvalidGenericForeignKeyException(getLabelToken(), new Object[] {getTableName(), getFieldName(), validValues.toString()});
                }
            } finally {
                if (localUow && uow != null)
                    uow.rollback();
            }
        }
    }
    
}
