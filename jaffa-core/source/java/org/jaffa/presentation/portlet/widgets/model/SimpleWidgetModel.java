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

package org.jaffa.presentation.portlet.widgets.model;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.*;
import org.jaffa.datatypes.Currency;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Defaults;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.Parser;
import org.jaffa.datatypes.exceptions.FormatCurrencyException;
import org.jaffa.datatypes.exceptions.FormatDateOnlyException;
import org.jaffa.datatypes.exceptions.FormatDateTimeException;
import org.jaffa.datatypes.exceptions.FormatDecimalException;
import org.jaffa.datatypes.exceptions.FormatIntegerException;
import org.jaffa.datatypes.exceptions.UnknownDataTypeRuntimeException;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.metadata.StringFieldMetaData;
import org.jaffa.presentation.portlet.widgets.model.exceptions.DataTypeMismatchRuntimeException;

/**
 * This is a base class for the simple widgets.
 * An instance of this class holds the widget value.
 * It has the following optional attributes:
 *  - FieldMetaData: It is used by the Tags for getting mandatory, caseType, dataType, layout, width information for a field.
 *  - Mandatory: It overrides the 'mandatory' attribute of the FieldMetaData
 *  - StringCase: It overrides the 'caseType' attribute of the FieldMetaData
 *
 * A list of allowed values can be specified by using the addOption() method
 * <p>
 * This class may be instantiated with an initial value and a FieldMetaData. These are used to determine the dataType for the IWidgetModel.
 * The setWidgetValue() may be invoked, passing any object. The Controllers usually pass a String instance.
 * The getWidgetValue() will parse the widgetValue, based on the dataType, to an appropriate object.
 * <p>
 * This class has several convenience methods to return different views of the WidgetValue.
 * <p>
 *
 * @todo The optional attributes, viz. FieldMetaData, Mandatory and StringCase, should be deprecated. Instead we should provide suitable attributes on the Tags and/or obtain the relevant information from the RulesEngine.
 */
public class SimpleWidgetModel implements IWidgetModel {
    private static Logger log = Logger.getLogger(SimpleWidgetModel.class);
    
    /**
     * Holds value of property widgetValue.
     */
    private Object m_widgetValue;
    
    /**
     * Holds value of property modelChanged.
     */
    private boolean m_modelChanged;
    
    /**
     * Holds value of property fieldMetaData.
     */
    private FieldMetaData m_fieldMetaData;
    
    /**
     * Holds value of property mandatory.
     */
    private Boolean m_mandatory;
    
    /**
     * Holds value of property stringCase.
     */
    private String m_stringCase;
    
    /**
     * Holds value of property dataType.
     */
    private String m_dataType;
    
    /**
     * Holds value of property layout.
     */
    private String m_layout;
    
    /**
     * Holds a list of allowed values.
     */
    private List m_options;
    
    
    
    /**
     * Constructs a IWidgetModel.
     */
    public SimpleWidgetModel() {
        this(null, null);
    }
    
    /**
     * Constructs a IWidgetModel initialized to the input value
     *
     * @param widgetValue The value for the IWidgetModel.
     * @throws UnknownDataTypeRuntimeException if the input widgetValue is of an unsupported DataType.
     */
    public SimpleWidgetModel(Object widgetValue)
    throws UnknownDataTypeRuntimeException {
        this(widgetValue, null);
    }
    
    /**
     * Creates a model.
     *
     * @param fieldMetaData The fieldMetaData for the IWidgetModel.
     */
    public SimpleWidgetModel(FieldMetaData fieldMetaData) {
        this(null, fieldMetaData);
    }
    
    /**
     * Constructs a IWidgetModel initialized to the input value.
     *
     * @param widgetValue The value for the IWidgetModel.
     * @param fieldMetaData The fieldMetaData for the IWidgetModel.
     * @throws UnknownDataTypeRuntimeException if the input widgetValue is of an unsupported DataType.
     * @throws DataTypeMismatchRuntimeException if there is a mismatch in the Datatypes of the inputs.
     */
    public SimpleWidgetModel(Object widgetValue, FieldMetaData fieldMetaData)
    throws UnknownDataTypeRuntimeException, DataTypeMismatchRuntimeException {
        m_widgetValue = widgetValue;
        m_fieldMetaData = fieldMetaData;
        m_dataType = determineDataType(widgetValue, fieldMetaData);
    }
    
    /**
     * Constructs a new IWidgetModel initialized to the contents an existing model.
     *
     * @param existingModel An existing IWidgetModel.
     */
    public SimpleWidgetModel(SimpleWidgetModel existingModel) {
        // this is effectively just cloning the existingModel
        m_widgetValue = existingModel.m_widgetValue;
        m_fieldMetaData = existingModel.m_fieldMetaData;
        m_mandatory = existingModel.m_mandatory;
        m_dataType = existingModel.m_dataType;
        m_layout = existingModel.m_layout;
        m_stringCase = existingModel.m_stringCase;
        m_options = existingModel.m_options != null ? new LinkedList(existingModel.m_options) : null;
    }
    
    
    
    
    
    /*************************/
    /*** GETTERS + SETTERS ***/
    /*************************/
    
    /** Getter for property widgetValue.
     * This will return an object appropriate for the current dataType.
     * @return Value of property widgetValue.
     */
    public Object getWidgetValue() {
        if (m_widgetValue != null && m_dataType != null) {
            if (m_dataType.equals(Defaults.STRING) || m_dataType.equals(Defaults.LONG_STRING) || m_dataType.equals(Defaults.CLOB))
                return getStringValue();
            else if (m_dataType.equals(Defaults.BOOLEAN))
                return getBooleanValue();
            else if (m_dataType.equals(Defaults.DATETIME))
                return getDateTimeValue();
            else if (m_dataType.equals(Defaults.DATEONLY))
                return getDateOnlyValue();
            else if (m_dataType.equals(Defaults.INTEGER))
                return getIntegerValue();
            else if (m_dataType.equals(Defaults.DECIMAL))
                return getDecimalValue();
            else if (m_dataType.equals(Defaults.CURRENCY))
                return getCurrencyValue();
            else if (m_dataType.equals(Defaults.RAW) || m_dataType.equals(Defaults.LONG_RAW) || m_dataType.equals(Defaults.BLOB))
                return getRawValue();
        }
        return m_widgetValue;
    }
    
    /**
     * Setter for property widgetValue.
     * The dataType for the input widgetValue may differ from the original dataType for the IWidgetModel.
     * This will set the dataType for the IWidgetModel, if it was set originally.
     * This will mark the IWidgetModel as modified, if the input is different from the current value.
     *
     * @param widgetValue New value of property widgetValue.
     */
    public void setWidgetValue(Object widgetValue) {
        // Determine the dataType, if required
        if (m_dataType == null)
            m_dataType = determineDataType(widgetValue, null); // No need to pass the fieldMetaData, since it'll be null, else the dataType would've been already determined.
        
        // Force an empty String to null
        if (widgetValue != null && widgetValue instanceof String && ((String) widgetValue).length() == 0)
            widgetValue = null;
        
        // Only update it and flag it if changed
        boolean update = false;
        if ((widgetValue == null && m_widgetValue != null) || (widgetValue != null && m_widgetValue == null)) {
            update = true;
        } else if (widgetValue != null && m_widgetValue != null) {
            if (widgetValue.getClass() == m_widgetValue.getClass()) {
                // Perform straight comparison if the new and old values are of the same class
                if (!widgetValue.equals(m_widgetValue))
                    update = true;
            } else if (m_widgetValue instanceof DateOnly && widgetValue instanceof DateTime) {
                // Convert the new value to DateOnly, since the m_fieldMetaData is most likely to be an instance of DateOnlyFieldMetaData
                widgetValue = DateTime.toDateOnly((DateTime) widgetValue);
                if (!widgetValue.equals(m_widgetValue))
                    update = true;
            } else if (m_widgetValue instanceof DateTime && widgetValue instanceof DateOnly) {
                // Convert the new value to DateTime, since the m_fieldMetaData is most likely to be an instance of DateTimeFieldMetaData
                widgetValue = DateOnly.toDateTime((DateOnly) widgetValue);
                if (!widgetValue.equals(m_widgetValue))
                    update = true;
            } else {
                // Format the 2 objects and then compare.
                String layout = getLayout();
                if (!Formatter.format(widgetValue, layout).equals(Formatter.format(m_widgetValue, layout))) {
                    update = true;
                }
            }
        }
        if (update) {
            m_widgetValue = widgetValue;
            setModelChanged(true);
        }
    }
    
    /** See if model has changed, in the process reset the changed flag.
     * @return true if the model was changed.
     */
    public boolean isModelChanged(){
        boolean modified = m_modelChanged;
        m_modelChanged = false;
        return modified;
    }
    
    /**
     * Setter for property modelChanged.
     * @param modelChanged New value of property modelChanged.
     */
    public void setModelChanged(boolean modelChanged) {
        m_modelChanged = modelChanged;
    }
    
    /**
     * Getter for property mandatory.
     * @return Value of property mandatory.
     */
    public boolean isMandatory() {
        Boolean mandatory = m_mandatory;
        if (mandatory == null && m_fieldMetaData != null && m_fieldMetaData.isMandatory() != null)
            mandatory = m_fieldMetaData.isMandatory();
        return mandatory != null ? mandatory.booleanValue() : false;
    }
    
    /**
     * Get Mandatory value if it has been overridden.
     * Used by the PropertyRuleIntrospectorUsingWidgetModel when wrapping a model.
     */
    public Boolean getMandatoryOverride() {
        return m_mandatory;
    }
    
    /**
     * Setter for property mandatory. This will override the mandatory behaviour
     * of this widget which is normally inherited from the FieldMetaData of from
     * the rules engine. There are many times where in the Form we what a field that
     * is mandatory in the domain object to be displayed as optional. This may be because
     * we default a value into the domain field prior to uow.add() as part of the
     * business logic. In these cases we need to force the field to be optional in the
     * presentation tier.
     * @param mandatory New value of property mandatory.
     */
    public void setMandatory(boolean mandatory) {
        m_mandatory = new Boolean(mandatory);
    }
    
    /** Getter for property stringCase.
     * @return Value of property stringCase.
     */
    public String getStringCase() {
        if (m_stringCase == null && m_fieldMetaData != null && m_fieldMetaData instanceof StringFieldMetaData)
            m_stringCase = ((StringFieldMetaData) m_fieldMetaData).getCaseType();
        return m_stringCase;
    }
    
    /** Setter for property stringCase.
     * @param stringCase New value of property stringCase.
     */
    public void setStringCase(String stringCase) {
        m_stringCase = stringCase;
    }
    
    /**
     * Getter for property layout.
     * @return Value of property layout.
     */
    public String getLayout() {
        if (m_layout == null && m_fieldMetaData != null) {
            // Obtain the layout from the FieldMetaData, if present
            if (log.isDebugEnabled())
                log.debug("getLayout(): Look at FieldMetaData");
            try {
                Method m = m_fieldMetaData.getClass().getMethod("getLayout", (Class[]) null);
                if (m != null && m.getReturnType() == String.class) {
                    m_layout = (String) m.invoke(m_fieldMetaData, (Object[]) null);
                    if (log.isDebugEnabled())
                        log.debug("getLayout(): FieldMetaData Layout = " + m_layout);
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return m_layout;
    }
    
    /** Setter for property layout.
     * @param layout New value of property layout.
     */
    public void setLayout(String layout) {
        m_layout = layout;
    }
    
    
    
    
    
    /*********************/
    /*** GETTERS only ***/
    /*********************/
    
    /**
     * Getter for property fieldMetaData.
     * @return Value of property fieldMetaData.
     */
    public FieldMetaData getFieldMetaData() {
        return m_fieldMetaData;
    }
    
    /**
     * Getter for property dataType.
     * @return Value of property dataType.
     */
    public String getDataType() {
        return m_dataType != null ? m_dataType : Defaults.STRING;
    }
    
    
    
    
    
    /*******************************************************************************************************/
    /*** THE FOLLOWING ARE CONVENIENCE METHODS THAT RETURN DIFFERENT REPRESENTATIONS OF THE WIDGET VALUE ***/
    /*******************************************************************************************************/
    
    /** Returns the WidgetValue as a String object.
     * @return the WidgetValue as a String object.
     */
    public String getStringValue() {
        if (m_widgetValue != null) {
            String value = m_widgetValue instanceof String ? (String) m_widgetValue : (m_widgetValue instanceof Number ? m_widgetValue.toString() : Formatter.format(m_widgetValue, getLayout()));
            String stringCase = getStringCase();
            if (stringCase != null) {
                if (stringCase.equals(FieldMetaData.UPPER_CASE))
                    value = value.toUpperCase();
                else if (stringCase.equals(FieldMetaData.LOWER_CASE))
                    value = value.toLowerCase();
            }
            return value;
        } else
            return null;
    }
    
    /** Returns the WidgetValue as a Boolean.
     * @return the WidgetValue as a Boolean.
     */
    public Boolean getBooleanValue() {
        if (m_widgetValue == null)
            return null;
        else if (m_widgetValue instanceof Boolean)
            return (Boolean) m_widgetValue;
        else if (m_widgetValue instanceof String)
            return Parser.parseBoolean((String) m_widgetValue, getLayout());
        else {
            String layout = getLayout();
            String str = Formatter.format(m_widgetValue, layout);
            return Parser.parseBoolean(str, layout);
        }
    }
    
    /** Returns the WidgetValue as a DateTime object.
     * @return the WidgetValue as a DateTime object.
     */
    public DateTime getDateTimeValue() {
        //Do not use the layout for parsing, since the parse-layout is different from format-layout for dates. Will get weird result in case a 2-digit year is passed
        //We should probably have a different method that returns the parse-layout
        try {
            if (m_widgetValue == null)
                return null;
            else if (m_widgetValue instanceof DateTime)
                return (DateTime) m_widgetValue;
            else if (m_widgetValue instanceof DateOnly)
                return DateOnly.toDateTime((DateOnly) m_widgetValue);
            else if (m_widgetValue instanceof String)
                return Parser.parseDateTime((String) m_widgetValue);
            else {
                String layout = getLayout();
                String str = Formatter.format(m_widgetValue, layout);
                return Parser.parseDateTime(str);
            }
        } catch (FormatDateTimeException e) {
            if (log.isDebugEnabled())
                log.debug("Exception thrown while trying to create a DateTime instance from: " + m_widgetValue + ". A null will be returned", e);
            return null;
        }
    }
    
    /** Returns the WidgetValue as a DateOnly object.
     * @return the WidgetValue as a DateOnly object.
     */
    public DateOnly getDateOnlyValue() {
        //Do not use the layout for parsing, since the parse-layout is different from format-layout for dates. Will get weird result in case a 2-digit year is passed
        //We should probably have a different method that returns the parse-layout
        try {
            if (m_widgetValue == null)
                return null;
            else if (m_widgetValue instanceof DateTime)
                return DateTime.toDateOnly((DateTime) m_widgetValue);
            else if (m_widgetValue instanceof DateOnly)
                return (DateOnly) m_widgetValue;
            else if (m_widgetValue instanceof String)
                return Parser.parseDateOnly((String) m_widgetValue);
            else {
                String layout = getLayout();
                String str = Formatter.format(m_widgetValue, layout);
                return Parser.parseDateOnly(str);
            }
        } catch (FormatDateOnlyException e) {
            if (log.isDebugEnabled())
                log.debug("Exception thrown while trying to create a DateOnly instance from: " + m_widgetValue + ". A null will be returned", e);
            return null;
        }
    }
    
    /** Returns the WidgetValue as a Long.
     * @return the WidgetValue as a Long.
     */
    public Long getIntegerValue() {
        try {
            if (m_widgetValue == null)
                return null;
            else if (m_widgetValue instanceof Long)
                return (Long) m_widgetValue;
            else if (m_widgetValue instanceof Number)
                return new Long(((Number) m_widgetValue).longValue());
            else if (m_widgetValue instanceof String)
                return Parser.parseInteger((String) m_widgetValue, getLayout());
            else {
                String layout = getLayout();
                String str = Formatter.format(m_widgetValue, layout);
                return Parser.parseInteger(str, layout);
            }
        } catch (FormatIntegerException e) {
            if (log.isDebugEnabled())
                log.debug("Exception thrown while trying to create a Long instance from: " + m_widgetValue + ". A null will be returned", e);
            return null;
        }
    }
    
    /** Returns the WidgetValue as a Double.
     * @return the WidgetValue as a Double.
     */
    public Double getDecimalValue() {
        try {
            if (m_widgetValue == null)
                return null;
            else if (m_widgetValue instanceof Double)
                return (Double) m_widgetValue;
            else if (m_widgetValue instanceof Number)
                return new Double(((Number) m_widgetValue).doubleValue());
            else if (m_widgetValue instanceof String)
                return Parser.parseDecimal((String) m_widgetValue, getLayout());
            else {
                String layout = getLayout();
                String str = Formatter.format(m_widgetValue, layout);
                return Parser.parseDecimal(str, layout);
            }
        } catch (FormatDecimalException e) {
            if (log.isDebugEnabled())
                log.debug("Exception thrown while trying to create a Double instance from: " + m_widgetValue + ". A null will be returned", e);
            return null;
        }
    }
    
    /** Returns the WidgetValue as a Currency.
     * @return the WidgetValue as a Currency.
     */
    public Currency getCurrencyValue() {
        try {
            if (m_widgetValue == null)
                return null;
            else if (m_widgetValue instanceof Currency)
                return (Currency) m_widgetValue;
            else if (m_widgetValue instanceof Number)
                return new Currency(((Number) m_widgetValue).doubleValue());
            else if (m_widgetValue instanceof String)
                return Parser.parseCurrency((String) m_widgetValue, getLayout());
            else {
                String layout = getLayout();
                String str = Formatter.format(m_widgetValue, layout);
                return Parser.parseCurrency(str, layout);
            }
        } catch (FormatCurrencyException e) {
            if (log.isDebugEnabled())
                log.debug("Exception thrown while trying to create a Currency instance from: " + m_widgetValue + ". A null will be returned", e);
            return null;
        }
    }
    
    /** Returns the WidgetValue as a byte[].
     * @return the WidgetValue as a byte[].
     */
    public byte[] getRawValue() {
        if (m_widgetValue == null)
            return null;
        else if (m_widgetValue instanceof byte[])
            return (byte[]) m_widgetValue;
        else if (m_widgetValue instanceof String)
            return Parser.parseRaw((String) m_widgetValue);
        else {
            String layout = getLayout();
            String str = Formatter.format(m_widgetValue, layout);
            return Parser.parseRaw(str);
        }
    }
    
    
    
    
    
    /**
     * Determines the dataType based on the input.
     *
     * @param widgetValue The value for the IWidgetModel.
     * @param fieldMetaData The fieldMetaData for the IWidgetModel.
     * @return The dataType.
     * @throws UnknownDataTypeRuntimeException if the input widgetValue is of an unsupported DataType.
     * @throws DataTypeMismatchRuntimeException if there is a mismatch in the Datatypes of the inputs.
     */
    private static String determineDataType(Object widgetValue, FieldMetaData fieldMetaData) {
        String dataType = null;
        if (widgetValue != null) {
            dataType = Defaults.getDataType(widgetValue);
            if(dataType == null) {
                String str = "Unsupported Data Type : " + widgetValue.getClass().getName();
                log.error(str);
                throw new UnknownDataTypeRuntimeException(str);
            } else if (fieldMetaData != null && !fieldMetaData.getDataType().equals(dataType)) {
                String str = "Data Type Mismatch. WidgetValue = " + widgetValue.getClass().getName() + " is '" + dataType + "', FieldMetaData = '" + fieldMetaData.getDataType() + '\'';
                log.error(str);
                throw new DataTypeMismatchRuntimeException(str);
            }
            // Force an empty String to null
            if (widgetValue instanceof String && ((String) widgetValue).length() == 0)
                widgetValue = null;
        } else if (fieldMetaData != null) {
            dataType = fieldMetaData.getDataType();
        }
        return dataType;
    }
    
    
    
    
    /** Adds an Option to the model.
     * Use this method to build a list of allowed values
     * @param label The label of the option.
     * @param value The value of the option.
     */
    public void addOption(String label, Object value) {
        Option option = new Option(label, value, m_fieldMetaData);
        if (m_options == null)
            m_options = new LinkedList();
        else if (m_options.contains(option))
            return;
        m_options.add(option);
    }
    
    /** Returns a List of SimpleWidgetModel.Option objects, for each of the option added to this model.
     * @return a List of SimpleWidgetModel.Option objects, for each of the option added to this model.
     */
    public List getOptions() {
        return m_options;
    }
    
    /** An instance of this class is created for each option added to the SimpleWidgetModel.
     */
    public static class Option extends SimpleWidgetModel implements Comparable {
        
        /** Holds value of property label. */
        private String label;
        
        private Option(String label, Object value, FieldMetaData fieldMetaData) {
            super(value, fieldMetaData);
            this.label = label;
        }
        
        /** Getter for property label.
         * @return Value of property label.
         */
        public String getLabel() {
            return label;
        }
        
        /** Compares this object to another SimpleWidgetModel.Option object.
         * Returns a true if the two objects have the same value.
         * @param obj The other SimpleWidgetModel.Option object.
         * @return true if the two objects have the same value.
         */
        public boolean equals(Object obj) {
            Option anotherOption = (Option) obj;
            if (anotherOption == null)
                return false;
            else {
                String value = getStringValue();
                String anotherValue = anotherOption.getStringValue();
                return value != null ? value.equals(anotherValue) : anotherValue == null;
            }
        }
        
        /** Compares this object with another SimpleWidgetModel.Option object.
         * @param obj the other SimpleWidgetModel.Option object.
         * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
         */
        public int compareTo(java.lang.Object obj) {
            Option anotherOption = (Option) obj;
            if (anotherOption == null)
                return 1;
            else {
                String value = getStringValue();
                String anotherValue = anotherOption.getStringValue();
                return value != null ? (anotherValue != null ? value.compareTo(anotherValue) : 1) : (anotherValue != null ? -1 : 0);
            }
        }
    }
    
    
    public String toString() {
        StringBuffer s=new StringBuffer("SimpleWidgetModel (").append(this.getClass().getName()).append(")");
        if(log.isDebugEnabled())
            s.append("\n  widgetValue = ").append(m_widgetValue)
            .append("\n  modelChanged = ").append(m_modelChanged)
            .append("\n  fieldMetaData = ").append(m_fieldMetaData)
            .append("\n  mandatory = ").append(m_mandatory)
            .append("\n  stringCase = ").append(m_stringCase)
            .append("\n  dataType = ").append(m_dataType)
            .append("\n  layout = ").append(m_layout)
            .append("\n  options = ").append(m_options);
        return s.toString();
    }
    
}
