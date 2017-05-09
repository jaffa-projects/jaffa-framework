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
 * 1.  Redistributions of source code must retain copyright statements and notices.
 *     Redistributions must also contain a copy of this document.
 * 2.  Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 * 3.  The name "JAFFA" must not be used to endorse or promote products derived from
 *     this Software without prior written permission. For written permission,
 *     please contact mail to: jaffagroup@yahoo.com.
 * 4.  Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *     appear in their names without prior written permission.
 * 5.  Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.flexfields;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.log4j.Logger;
import org.jaffa.components.finder.BooleanCriteriaField;
import org.jaffa.components.finder.CriteriaField;
import org.jaffa.components.finder.CurrencyCriteriaField;
import org.jaffa.components.finder.DateOnlyCriteriaField;
import org.jaffa.components.finder.DateTimeCriteriaField;
import org.jaffa.components.finder.DecimalCriteriaField;
import org.jaffa.components.finder.FinderTx;
import org.jaffa.components.finder.IntegerCriteriaField;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.datatypes.Currency;
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.flexfields.domain.FlexFieldMeta;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.rules.IObjectRuleIntrospector;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.util.StringHelper;

/**
 * FlexBean implements the DynaBean interface.
 * <p>
 * It holds the following information when linked to a persistent object:
 *   flexClass: the associated DynaClass
 *   persistentObject: the persistent object for which this bean will hold FlexField instances.
 *   flexFields: the associated FlexField instances.
 * NOTE: For a persistent object, the setter will either set a flex field directly on the
 * associated persistent object, if a domain-mapping is provided, or the flex field will be
 * added to the flexFields property.
 * <p>
 * It holds the following information when linked to a non-persistent object:
 *   flexClass: the associated DynaClass
 *   flexParams: the associated FlexParam instances.
 * NOTE: For a non-persistent object, the setter will always add the flex field to the
 * flexParams property.
 * <p>
 * It is highly recommended to use the instance() method to instantiate this bean.
 */
public class FlexCriteriaBean implements DynaBean {

    private static Logger log = Logger.getLogger(FlexCriteriaBean.class);
    private FlexClass flexClass;
    private Map<String, FlexCriteriaParam> flexCriteriaParams = new TreeMap<String, FlexCriteriaParam>();

    /**
     * Creates a new instance.
     */
    public FlexCriteriaBean() {
    }

    /**
     * Creates a new instance.
     * @param flexClass the associated FlexClass.
     */
    public FlexCriteriaBean(FlexClass flexClass) {
        this.flexClass = flexClass;
    }

    // *************************
    // **** IMPLEMENTATION *****
    // *************************
    /**
     * NOTE: This is an unsupported operation for a FlexCriteriaBean instance.
     */
    public boolean contains(String name, String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Return the value of the input property.
     * If the property has a domain-mapping (as determined from the associated FlexProperty, value will
     * be obtained from the persistentObject. Else the value will be obtained from
     * the appropriate FlexField instance.
     * @param name the property name.
     * @return value for the property.
     */
    public Object get(String name) {
        return getset(true, name, null, null);
    }

    /**
     * NOTE: This is an unsupported operation for a FlexCriteriaBean instance.
     */
    public Object get(String name, int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * NOTE: This is an unsupported operation for a FlexCriteriaBean instance.
     */
    public Object get(String name, String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Return the FlexClass instance that describes the set of properties available for this FlexCriteriaBean.
     * @return the FlexClass instance that describes the set of properties available for this FlexCriteriaBean.
     */
    @XmlTransient
    public DynaClass getDynaClass() {
        return flexClass;
    }

    /**
     * NOTE: This is an unsupported operation for a FlexCriteriaBean instance.
     */
    public void remove(String name, String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the value of the input property.
     * If the property has a domain-mapping (as determined from the associated FlexProperty, value will
     * be stamped on the persistentObject. Else the value will be stamped on
     * the appropriate FlexField instance.
     * @param name the property name.
     * @param value the new value.
     */
    public void set(String name, Object value) {
        getset(false, name, value, null);
    }

    /**
     * NOTE: This is an unsupported operation for a FlexCriteriaBean instance.
     */
    public void set(String name, int index, Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * NOTE: This is an unsupported operation for a FlexCriteriaBean instance.
     */
    public void set(String name, String key, Object value) {
        throw new UnsupportedOperationException();
    }

    // ***********************************
    // **** METHODS TO SUPPORT TOOLS *****
    // ***********************************
    /**
     * Sets the FlexClass instance that describes the set of properties available for this FlexCriteriaBean.
     *
     * NOTE: This method will throw a ClassCastException if a non-FlexClass argument is passed.
     * DynaClass is used in the signature so that this property turns up as a valid read+write property via the JavaBeans Introspector.
     * @param flexClass new value for the property flexClass.
     */
    public void setDynaClass(DynaClass flexClass) {
        this.flexClass = (FlexClass) flexClass;
    }

    /**
     * Getter for the property flexCriteriaParams.
     * @return the value of the property flexCriteriaParams.
     */
    public FlexCriteriaParam[] getFlexCriteriaParams() {
        return flexCriteriaParams.values().toArray(new FlexCriteriaParam[flexCriteriaParams.size()]);
    }

    /**
     * Setter for the property flexCriteriaParams.
     * @param flexCriteriaParams new value for the property flexCriteriaParams.
     */
    public void setFlexCriteriaParams(FlexCriteriaParam[] flexCriteriaParams) {
        if (flexCriteriaParams != null) {
            for (FlexCriteriaParam flexCriteriaParam : flexCriteriaParams)
                getset(false, null, null, flexCriteriaParam);
        }
    }

    // *****************************
    // **** ADDITIONAL METHODS *****
    // *****************************
    /**
     * Returns debug info.
     * @return debug info.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        try {
            buf.append("<FlexCriteriaBean");
            if (flexClass != null)
                buf.append(" name='").append(flexClass.getName()).append("' logicalName='").append(flexClass.getLogicalName()).append('\'');
            buf.append('>');
            if (flexClass != null) {
                for (FlexProperty f : flexClass.getDynaProperties()) {
                    buf.append('<').append(f.getName()).append(" logicalName='").append(f.getLogicalName()).append("' value='");
                    Object value = get(f.getName());
                    if (value != null)
                        buf.append(value);
                    buf.append("'/>");
                }
            } else {
                for (FlexCriteriaParam flexCriteriaParam : flexCriteriaParams.values()) {
                    buf.append('<').append(flexCriteriaParam.getName()).append("' value='");
                    if (flexCriteriaParam.getValue() != null)
                        buf.append(flexCriteriaParam.getValue());
                    buf.append("'/>");
                }
            }
            buf.append("</FlexCriteriaBean>");
        } catch (Exception ignore) {
        }
        return buf.toString();
    }

    /**
     * This is the recommended way to instantiate a FlexCriteriaBean.
     * It obtains the appropriate FlexClass.
     * @param object the associated object.
     * @return a FlexCriteriaBean instance.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public static FlexCriteriaBean instance(Object object) throws ApplicationExceptions, FrameworkException {
        if (object instanceof FlexClass)
            return instance((FlexClass) object);
        else {
            FlexClass flexClass = FlexClass.instance(object);
            return instance(flexClass, object);
        }
    }

    /**
     * Creates an instance based on the input FlexClass and clears all the initial values.
     * @param flexClass the associated FlexClass.
     * @return a FlexCriteriaBean instance.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public static FlexCriteriaBean instance(FlexClass flexClass) throws ApplicationExceptions, FrameworkException {
        return instance(flexClass, null);
    }

    /**
     * This is the recommended way to instantiate a FlexCriteriaBean.
     * It obtains the appropriate FlexClass.
     * @param flexClass the associated FlexClass.
     * @param object the associated object.
     * @return a FlexCriteriaBean instance. A null will be returned if the FlexClass has no dyna-properties.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    private static FlexCriteriaBean instance(FlexClass flexClass, Object object) throws ApplicationExceptions, FrameworkException {
        FlexCriteriaBean flexCriteriaBean = null;
        if (flexClass.getDynaProperties() != null && flexClass.getDynaProperties().length > 0) {
            flexCriteriaBean = new FlexCriteriaBean(flexClass);
            // Clear all the changes. This can happen if initialze rules are declared for any of the flex fields.
            flexCriteriaBean.flexCriteriaParams.clear();
        } else {
            if (log.isDebugEnabled())
                log.debug("FlexCriteriaBean will not be instantiated for the FlexClass '" + flexClass.getName() + "', since it has no dyna-properties");
        }
        return flexCriteriaBean;
    }

    /** Gets or Sets a property. */
    private Object getset(boolean get, String name, Object value, FlexCriteriaParam flexCriteriaParam) {
        if (flexCriteriaParam != null) {
            name = flexCriteriaParam.getName();
            value = flexCriteriaParam.getValue();
        }
        FlexProperty flexProperty = flexClass != null ? flexClass.getDynaProperty(name) : null;
        Class dataType = flexProperty != null ? flexProperty.getType() : String.class;
        Properties flexInfo = flexProperty != null ? flexProperty.getFlexInfo() : null;
        String layout = flexInfo != null ? flexInfo.getProperty("layout") : null;
        if (get) {
            if (flexCriteriaParams.containsKey(name))
                value = flexCriteriaParams.get(name).getValue();
            if (value != null)
                value = convertFromStringCriteriaField((StringCriteriaField) value, dataType, layout);
            if (log.isDebugEnabled())
                log.debug("Value of property '" + name + "' is '" + value + '\'');
            return value;
        } else {
            if (flexCriteriaParam == null)
                flexCriteriaParam = new FlexCriteriaParam(name, convertToStringCriteriaField((CriteriaField) value, layout));
            flexCriteriaParams.put(name, flexCriteriaParam);
            if (log.isDebugEnabled())
                log.debug("Value of property '" + name + "' has been set to '" + value + '\'');
            return null;
        }
    }

    /**Converted the input StringCriteriaField to a CriteriaField instance compatible with the input dataType. */
    private CriteriaField convertFromStringCriteriaField(StringCriteriaField criteriaField, Class dataType, String layout) {
        CriteriaField output = criteriaField;
        if (criteriaField != null && dataType != String.class) {
            // Create the value array compatible with the input dataType
            Object[] values = null;
            if (criteriaField.getValues() != null && criteriaField.getValues().length > 0) {
                Collection valuesCol = new LinkedList();
                for (String value : criteriaField.getValues())
                    valuesCol.add(!dataType.isInstance(value) ? DataTypeMapper.instance().map(value, dataType, layout) : value);
                values = valuesCol.toArray((Object[]) Array.newInstance(dataType, valuesCol.size()));
            }

            // Create the appropriate CriteriaField instance
            if (dataType == Boolean.class)
                output = new BooleanCriteriaField(criteriaField.getOperator(), (Boolean[]) values);
            else if (dataType == Currency.class)
                output = new CurrencyCriteriaField(criteriaField.getOperator(), (Currency[]) values);
            else if (dataType == DateOnly.class)
                output = new DateOnlyCriteriaField(criteriaField.getOperator(), (DateOnly[]) values);
            else if (dataType == DateTime.class)
                output = new DateTimeCriteriaField(criteriaField.getOperator(), (DateTime[]) values);
            else if (dataType == Double.class)
                output = new DecimalCriteriaField(criteriaField.getOperator(), (Double[]) values);
            else if (dataType == Long.class)
                output = new IntegerCriteriaField(criteriaField.getOperator(), (Long[]) values);
            else {
                if (log.isDebugEnabled())
                    log.debug("Unsupported datatype '" + dataType.getName() + "' passed to the convertFromStringField routine.");
            }
        }
        return output;
    }

    /**Converted the input CriteriaField instance to a StringCriteriaField. */
    private StringCriteriaField convertToStringCriteriaField(CriteriaField criteriaField, String layout) {
        StringCriteriaField output = null;
        if (criteriaField != null) {
            if (criteriaField instanceof StringCriteriaField)
                output = (StringCriteriaField) criteriaField;
            else {
                // Create an array of String values
                String[] values = null;
                if (criteriaField.returnValuesAsObjectArray() != null && criteriaField.returnValuesAsObjectArray().length > 0) {
                    Collection<String> valuesCol = new LinkedList<String>();
                    for (Object value : criteriaField.returnValuesAsObjectArray())
                        valuesCol.add((String) DataTypeMapper.instance().map(value, String.class, layout));
                    values = valuesCol.toArray(new String[valuesCol.size()]);
                }

                // Create the appropriate CriteriaField instance
                output = new StringCriteriaField(criteriaField.getOperator(), values);
            }
        }
        return output;
    }

    // ****************************
    // **** LIFECYCLE METHODS *****
    // ****************************
    /**
     * Returns the criteria object used for retrieving records.
     * @return the criteria object used for retrieving records.
     */
    public Criteria returnQueryClause(Criteria criteria) throws FrameworkException {
        if (flexClass != null && flexCriteriaParams.size() > 0) {
            String[] keys = findKeys(criteria.getTable());
            for (FlexCriteriaParam param : flexCriteriaParams.values()) {
                FlexProperty flexProperty = flexClass.getDynaProperty(param.getName());
                if (flexProperty != null) {
                    Properties flexInfo = flexProperty.getFlexInfo();
                    String domainMapping = flexInfo != null ? flexInfo.getProperty("domain-mapping") : null;
                    if (domainMapping != null) {
                        FinderTx.addCriteria(param.getValue(), StringHelper.getUpper1(domainMapping), criteria);
                    } else if (keys != null) {
                        if (CriteriaField.RELATIONAL_IS_NULL.equals(param.getValue().getOperator())) {
                            if (log.isDebugEnabled())
                                log.debug("Operator '" + CriteriaField.RELATIONAL_IS_NULL + "' is not supported for a flex criteria field");
                            continue;
                        }
                        Criteria flexCriteria = new Criteria();
                        flexCriteria.setTable(FlexFieldMeta.getName());
                        int i = 0;
                        for (String key : keys)
                            flexCriteria.addInnerCriteria("Key" + ++i, StringHelper.getUpper1(key));
                        flexCriteria.addCriteria(FlexFieldMeta.OBJECT_NAME, flexClass.getLogicalName());
                        flexCriteria.addCriteria(FlexFieldMeta.FIELD_NAME, flexProperty.getLogicalName());
                        FinderTx.addCriteria(param.getValue(), FlexFieldMeta.VALUE, flexCriteria);
                        criteria.addAggregate(flexCriteria);
                    }
                }
            }
        }
        return criteria;
    }

    /** Returns an array of key field names for the input domain class. */
    private String[] findKeys(String domainClassName) throws FrameworkException {
        // Search for the first available class-level primary-key rule
        IObjectRuleIntrospector introspector = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(domainClassName, null);
        String[] keys = introspector.getPrimaryKey();

        // Search for corresponding DomainMeta class, if required
        if (keys == null || keys.length == 0) {
            try {
                FieldMetaData[] keyFields = PersistentHelper.getKeyFields(domainClassName);
                if (keyFields != null) {
                    keys = new String[keyFields.length];
                    for (int i = 0; i < keyFields.length; i++)
                        keys[i] = keyFields[i].getName();
                }
            } catch (Exception e) {
                // do nothing
            }
        }

        return keys != null && keys.length > 0 ? keys : null;
    }
}
