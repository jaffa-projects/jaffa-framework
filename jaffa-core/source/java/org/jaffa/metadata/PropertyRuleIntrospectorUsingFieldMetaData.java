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

package org.jaffa.metadata;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Defaults;
import org.jaffa.datatypes.Formatter;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.util.StringHelper;


/**
 * This is a helper class to lookup property-level rules.
 * It should be used as a wrapper around another IPropertyRuleIntrospector instance.
 * This class will delegate calls to the passed IPropertyRuleIntrospector instance.
 * Additionally it'll use the supplied FieldMetaData for additional info where required.
 */
public class PropertyRuleIntrospectorUsingFieldMetaData implements IPropertyRuleIntrospector {

    private static Logger log = Logger.getLogger(PropertyRuleIntrospectorUsingFieldMetaData.class);

    private IPropertyRuleIntrospector m_sourcePropertyRuleIntrospector = null;
    private FieldMetaData m_fieldMetaData = null;

    private Class m_propertyType = null;
    private Integer m_minLength = null;
    private Integer m_maxLength = null;
    private Integer m_maxFracLength = null;
    private Object m_minValue = null;
    private Object m_maxValue = null;
    private String m_caseType = null;
    private String m_label = null;
    private String m_layout = null;
    private String[] m_pattern = null;
    private Boolean m_mandatory = null;

    private boolean m_checkedPropertyType = false;
    private boolean m_checkedMinLength = false;
    private boolean m_checkedMaxLength = false;
    private boolean m_checkedMaxFracLength = false;
    private boolean m_checkedMinValue = false;
    private boolean m_checkedMaxValue = false;
    private boolean m_checkedCaseType = false;
    private boolean m_checkedLabel = false;
    private boolean m_checkedLayout = false;
    private boolean m_checkedPattern = false;

    /**
     * Creates an instance.
     * @param sourcePropertyRuleIntrospector The source introspector.
     * @param fieldMetaData The FieldMetaData instance.
     */
    public PropertyRuleIntrospectorUsingFieldMetaData(IPropertyRuleIntrospector sourcePropertyRuleIntrospector, FieldMetaData fieldMetaData) {
        m_sourcePropertyRuleIntrospector = sourcePropertyRuleIntrospector;
        m_fieldMetaData = fieldMetaData;
    }

    /** Returns a Class object that identifies the declared type for the property.
     * @return a Class object that identifies the declared type for the property.
     */
    public Class getPropertyType() {
        if (!m_checkedPropertyType) {
            m_checkedPropertyType = true;
            if (m_sourcePropertyRuleIntrospector != null)
                m_propertyType = m_sourcePropertyRuleIntrospector.getPropertyType();
            if (m_propertyType == null)
                m_propertyType = getPropertyTypeFromFieldMetaData();
        }
        return m_propertyType;
    }

    /** Returns a true if the property is marked as Hidden.
     * @return a true if the property is marked as Hidden.
     */
    public boolean isHidden() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.isHidden() : false;
    }


    /** Returns a true if the property is marked as ReadOnly.
     * @return a true if the property is marked as ReadOnly.
     */
    public boolean isReadOnly() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.isReadOnly() : false;
    }

    /** Returns the annotation for the property.
     * @return the annotation for the property.
     */
    public String getAnnotation() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getAnnotation() : null;
    }

    /** Returns the audit info for the property.
     * @return the audit info for the property.
     */
    public Properties getAuditInfo() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getAuditInfo() : null;
    }

    /** Returns the flex info for the property.
     * @return the flex info for the property.
     */
    public Properties getFlexInfo() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getFlexInfo() : null;
    }

    /** Returns the foreignkey info for the property.
     * @return the foreignkey info for the property.
     */
    public Properties getForeignKeyInfo() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getForeignKeyInfo() : null;
    }

    /** Returns the hyperlink info for the property.
     * @return the hyperlink info for the property.
     */
    public Properties getHyperlinkInfo() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getHyperlinkInfo() : null;
    }

    /** Returns the info for the property.
     * @return the info for the property.
     */
    public Properties getPropertyInfo() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getPropertyInfo() : null;
    }

    /** Returns the minLength for the property.
     * @return the minLength for the property.
     */
    public Integer getMinLength() {
        if(!m_checkedMinLength) {
            m_checkedMinLength = true;
            if (m_sourcePropertyRuleIntrospector != null)
                m_minLength = m_sourcePropertyRuleIntrospector.getMinLength();

            // Assign the upper of the values from the FieldMetaData and the Rules
            Integer x = getMinLengthFromFieldMetaData();
            m_minLength = x == null ? m_minLength : (m_minLength == null ? x : (x.intValue() > m_minLength.intValue() ? x : m_minLength));
        }
        return m_minLength;
    }

    /** Returns the maxLength for the property.
     * @return the maxLength for the property.
     */
    public Integer getMaxLength() {
        if(!m_checkedMaxLength) {
            m_checkedMaxLength = true;
            if (m_sourcePropertyRuleIntrospector != null)
                m_maxLength = m_sourcePropertyRuleIntrospector.getMaxLength();

            // Assign the lower of the values from the FieldMetaData and the Rules
            Integer x = getMaxLengthFromFieldMetaData();
            m_maxLength = x == null ? m_maxLength : (m_maxLength == null ? x : (x.intValue() < m_maxLength.intValue() ? x : m_maxLength));
        }
        return m_maxLength;
    }

    /** Returns the maxFracLength for the property.
     * @return the maxFracLength for the property.
     */
    public Integer getMaxFracLength() {
        if(!m_checkedMaxFracLength) {
            m_checkedMaxFracLength = true;
            if (m_sourcePropertyRuleIntrospector != null)
                m_maxFracLength = m_sourcePropertyRuleIntrospector.getMaxFracLength();

            // Assign the lower of the values from the FieldMetaData and the Rules
            Integer x = getMaxFracLengthFromFieldMetaData();
            m_maxFracLength = x == null ? m_maxFracLength : (m_maxFracLength == null ? x : (x.intValue() < m_maxFracLength.intValue() ? x : m_maxFracLength));
        }
        return m_maxFracLength;
    }

    /** Returns the minValue for the property.
     * @return the minValue for the property.
     */
    public Object getMinValue() {
        if(!m_checkedMinValue) {
            m_checkedMinValue = true;
            if (m_sourcePropertyRuleIntrospector != null)
                m_minValue = m_sourcePropertyRuleIntrospector.getMinValue();

            // Assign the maximum of the values from the FieldMetaData and the Rules
            Object x = getMinValueFromFieldMetaData();
            if (x != null) {
                if (m_minValue == null) {
                    m_minValue = x;
                } else if (x instanceof Comparable && ((Comparable) x).compareTo(m_minValue) > 0)
                    m_minValue = x;
            }
        }
        return m_minValue;
    }

    /** Returns the maxValue for the property.
     * @return the maxValue for the property.
     */
    public Object getMaxValue() {
        if(!m_checkedMaxValue) {
            m_checkedMaxValue = true;
            if (m_sourcePropertyRuleIntrospector != null)
                m_maxValue = m_sourcePropertyRuleIntrospector.getMaxValue();

            // Assign the minimum of the values from the FieldMetaData and the Rules
            Object x = getMaxValueFromFieldMetaData();
            if (x != null) {
                if (m_maxValue == null) {
                    m_maxValue = x;
                } else if (x instanceof Comparable && ((Comparable) x).compareTo(m_maxValue) < 0)
                    m_maxValue = x;
            }
        }
        return m_maxValue;
    }

    /** Returns the caseType for the property.
     * If multiple caseType rules are defined for a property, then the caseType from the first rule will be returned.
     * @return the caseType for the property.
     */
    public String getCaseType() {
        if(!m_checkedCaseType) {
            m_checkedCaseType = true;
            if (m_sourcePropertyRuleIntrospector != null)
                m_caseType = m_sourcePropertyRuleIntrospector.getCaseType();
            if (m_caseType == null)
                m_caseType = getCaseTypeFromFieldMetaData();
        }
        return m_caseType;
    }

    /** Returns the label for the property.
     * If multiple label rules are defined for a property, then the label from the last rule will be returned.
     * @return the label for the property.
     */
    public String getLabel() {
        if (!m_checkedLabel) {
            m_checkedLabel = true;
            if (m_sourcePropertyRuleIntrospector != null)
                m_label = m_sourcePropertyRuleIntrospector.getLabel();
            if (m_label == null)
                m_label = getLabelFromFieldMetaData();
        }
        return m_label;
    }

    /** Returns the layout for the property.
     * If multiple layout rules are defined for a property, then the layout from the last rule will be returned.
     * @return the layout for the property.
     */
    public String getLayout() {
        if (!m_checkedLayout) {
            m_checkedLayout = true;
            if (m_sourcePropertyRuleIntrospector != null)
                m_layout = m_sourcePropertyRuleIntrospector.getLayout();
            else
                log.debug("getLayout(): No PropertyRuleIntrospector available");
            if (m_layout == null)
                m_layout = getLayoutFromFieldMetaData();
        }
        return m_layout;
    }

    /** Looks up the layout rules defined for the property and formats the input object using the associated formatterClass.
     * If multiple layout rules are defined for a property, then the layout from the last rule will be used.
     * @param property the property instance to be formatted.
     * @return the formatted property.
     */
    public String format(Object property) {
        return Formatter.format(property, getLayout());
    }

    /** Returns the pattern(s) for the property.
     * @return the pattern(s) for the property.
     */
    public String[] getPattern() {
        if (!m_checkedPattern) {
            m_checkedPattern = true;
            if (m_sourcePropertyRuleIntrospector != null)
                m_pattern = m_sourcePropertyRuleIntrospector.getPattern();

            String pattern = getPatternFromFieldMetaData();
            if (pattern != null) {
                if (m_pattern == null)
                    m_pattern = new String[] {pattern};
                else {
                    // Add the pattern to the list, if it doesn't already exist
                    List list = Arrays.asList(m_pattern);
                    if (!list.contains(pattern)) {
                        list.add(pattern);
                        m_pattern = (String[]) list.toArray(m_pattern);
                    }
                }
            }
        }
        return m_pattern;
    }

    /** Returns a true if the property is marked as mandatory.
     * @return a true if the property is marked as mandatory.
     */
    public boolean isMandatory() {
        if(m_mandatory == null) {
            boolean mandatory = false;
            if (m_sourcePropertyRuleIntrospector != null)
                mandatory = m_sourcePropertyRuleIntrospector.isMandatory();
            if (!mandatory)
                mandatory = getMandatoryFromFieldMetaData();
            m_mandatory = new Boolean(mandatory);
        }
        return m_mandatory.booleanValue();
    }

    /** Returns the client rule for the property.
     * @return the client rule for the property.
     */
    public String getClientRule() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getClientRule() : null;
    }

    public String getCommentStyle() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getCommentStyle() : null;
    }

    /** Returns the in-list values for the property.
     * Each entry in the Map will be a value/label pair.
     * @return the in-list values for the property.
     */
    public Map<String, String> getInListValues() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getInListValues() : null;
    }


    private Class getPropertyTypeFromFieldMetaData() {
        if (m_fieldMetaData != null) {
            String dataType = m_fieldMetaData.getDataType();
            if (dataType != null)
                return Defaults.getClass(dataType);
        }
        return null;
    }

    private Integer getMinLengthFromFieldMetaData() {
        if (m_fieldMetaData != null) {
            try {
                Method m = m_fieldMetaData.getClass().getMethod("getMinLength", (Class[]) null);
                Object size = m.invoke(m_fieldMetaData, (Object[]) null);
                if (size != null) {
                    if (size instanceof Integer)
                        return (Integer) size;
                    else if (size instanceof Number)
                        return new Integer(((Number) size).intValue());
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return null;
    }

    private Integer getMaxLengthFromFieldMetaData() {
        if (m_fieldMetaData != null) {
            try {
                if (m_fieldMetaData instanceof StringFieldMetaData) {
                    return ((StringFieldMetaData) m_fieldMetaData).getLength();
                } else if (m_fieldMetaData instanceof BooleanFieldMetaData
                        || m_fieldMetaData instanceof DateOnlyFieldMetaData
                        || m_fieldMetaData instanceof DateTimeFieldMetaData) {
                    // We do not set the lengths in Date and Boolean FieldMetaData objects. Hence return their width.
                    int width = m_fieldMetaData.getWidth();
                    if (width > 0)
                        return new Integer(width);
                } else {
                    Method m = m_fieldMetaData.getClass().getMethod("getIntSize", (Class[]) null);
                    Object size = m.invoke(m_fieldMetaData, (Object[]) null);
                    if (size != null) {
                        if (size instanceof Integer)
                            return (Integer) size;
                        else if (size instanceof Number)
                            return new Integer(((Number) size).intValue());
                    }
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return null;
    }

    private Integer getMaxFracLengthFromFieldMetaData() {
        if (m_fieldMetaData != null) {
            try {
                Method m = m_fieldMetaData.getClass().getMethod("getFracSize", (Class[]) null);
                Object size = m.invoke(m_fieldMetaData, (Object[]) null);
                if (size != null) {
                    if (size instanceof Integer)
                        return (Integer) size;
                    else if (size instanceof Number)
                        return new Integer(((Number) size).intValue());
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return null;
    }

    private Object getMinValueFromFieldMetaData() {
        if (m_fieldMetaData != null) {
            try {
                Method m = m_fieldMetaData.getClass().getMethod("getMinValue", (Class[]) null);
                return m.invoke(m_fieldMetaData, (Object[]) null);
            } catch (Exception e) {
                // do nothing
            }
        }
        return null;
    }

    private Object getMaxValueFromFieldMetaData() {
        if (m_fieldMetaData != null) {
            try {
                Method m = m_fieldMetaData.getClass().getMethod("getMaxValue", (Class[]) null);
                return m.invoke(m_fieldMetaData, (Object[]) null);
            } catch (Exception e) {
                // do nothing
            }
        }
        return null;
    }

    private String getCaseTypeFromFieldMetaData() {
        return m_fieldMetaData != null && m_fieldMetaData instanceof StringFieldMetaData ? ((StringFieldMetaData) m_fieldMetaData).getCaseType() : null;
    }

    private String getLabelFromFieldMetaData() {
        return m_fieldMetaData != null ? m_fieldMetaData.getLabelToken() : null;
    }

    private String getLayoutFromFieldMetaData() {
        String layout = null;
        if (m_fieldMetaData != null) {
            try {
                Method m = m_fieldMetaData.getClass().getMethod("getLayout", (Class[]) null);
                Object obj = m.invoke(m_fieldMetaData, (Object[]) null);
                layout = obj != null ? obj.toString() : null;
                log.debug("getLayoutFromFieldMetaData(): value in FieldMetaData = " + layout);
            } catch (Exception e) {
                // do nothing
            }
        }
        /* Moved this up to the PropertyRuleIntrospectorUsingWidgetModel
        if (layout == null) {
            if (m_fieldMetaData instanceof DateOnlyFieldMetaData)
                layout = DateOnlyFieldMetaData.getDateOnlyFormat();
            else if (m_fieldMetaData instanceof DateTimeFieldMetaData)
                layout = DateTimeFieldMetaData.getDateTimeFormat();
            else if (m_fieldMetaData instanceof IntegerFieldMetaData)
                layout = IntegerFieldMetaData.getIntegerFormat();
            else if (m_fieldMetaData instanceof DecimalFieldMetaData)
                layout = DecimalFieldMetaData.getDecimalFormat();
            else if (m_fieldMetaData instanceof CurrencyFieldMetaData)
                layout = CurrencyFieldMetaData.getCurrencyFormat();
            log.debug("getLayoutFromFieldMetaData(): Use default layout = " + layout);
        }
         */
        return layout;
    }

    private String getPatternFromFieldMetaData() {
        return m_fieldMetaData != null && m_fieldMetaData instanceof StringFieldMetaData ?
            ((StringFieldMetaData) m_fieldMetaData).getPattern() : null;
    }

    private boolean getMandatoryFromFieldMetaData() {
        return m_fieldMetaData != null && m_fieldMetaData.isMandatory() != null ? m_fieldMetaData.isMandatory().booleanValue() : false;
    }

    public Map<String, Object> getServiceInfo(){
		return null;
	}

    public String toString() {
        StringBuffer s=new StringBuffer("PropertyRuleIntrospectorUsingFieldMetaData@").append(hashCode());
        if(log.isDebugEnabled())
            s.append("\n    getPropertyType() = ").append(getPropertyType())
            .append("\n    isHidden() = ").append(isHidden())
            .append("\n    isReadOnly() = ").append(isReadOnly())
            .append("\n    isMandatory() = ").append(isMandatory())
            .append("\n    getForeignKeyInfo() = ").append(getForeignKeyInfo())
            .append("\n    getMinLength() = ").append(getMinLength())
            .append("\n    getMaxLength() = ").append(getMaxLength())
            .append("\n    getMaxFracLength() = ").append(getMaxFracLength())
            .append("\n    getMinValue() = ").append(getMinValue())
            .append("\n    getMaxValue() = ").append(getMaxValue())
            .append("\n    getCaseType() = ").append(getCaseType())
            .append("\n    getLabel() = ").append(getLabel())
            .append("\n    getLayout() = ").append(getLayout())
            .append("\n    getPattern() = ").append(StringHelper.stringArrayToString(getPattern()))
            .append("\n    getClientRule() = ").append(getClientRule())
            .append("\n    getCommentStyle() = ").append(getCommentStyle())
            .append("\n    getInListValues() = ").append(getInListValues())
            .append("\n    sourcePropertyRuleIntrospector = ").append(StringHelper.linePad(""+m_sourcePropertyRuleIntrospector,8," ",true) );
        return s.toString();
    }

	@Override
	public Properties findInfo(String className, String propertyName,
			Object obj, String ruleName) {
		return null;
	}
    
    
}
