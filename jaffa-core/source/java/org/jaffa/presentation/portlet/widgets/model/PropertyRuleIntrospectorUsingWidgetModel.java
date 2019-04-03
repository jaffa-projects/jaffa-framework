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

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Currency;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Defaults;
import org.jaffa.datatypes.Formatter;
import org.jaffa.metadata.CurrencyFieldMetaData;
import org.jaffa.metadata.DateOnlyFieldMetaData;
import org.jaffa.metadata.DateTimeFieldMetaData;
import org.jaffa.metadata.DecimalFieldMetaData;
import org.jaffa.metadata.IntegerFieldMetaData;
import org.jaffa.metadata.PropertyRuleIntrospectorUsingFieldMetaData;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.util.StringHelper;


/**
 * This is a helper class to lookup property-level rules.
 * It should be used as a wrapper around another IPropertyRuleIntrospector instance.
 * This class will delegate calls to the passed IPropertyRuleIntrospector instance.
 * Additionally it'll use the supplied SimpleWidgetModel for additional info where required.
 */
public class PropertyRuleIntrospectorUsingWidgetModel implements IPropertyRuleIntrospector {

    private static Logger log = Logger.getLogger(PropertyRuleIntrospectorUsingWidgetModel.class);

    private IPropertyRuleIntrospector m_sourcePropertyRuleIntrospector = null;
    private SimpleWidgetModel m_widgetModel = null;

    private Class m_propertyType = null;
    private String m_caseType = null;
    private String m_layout = null;
    private Boolean m_mandatory = null;

    private boolean m_checkedPropertyType = false;
    private boolean m_checkedCaseType = false;
    private boolean m_checkedLayout = false;

    /**
     * Creates an instance.
     * @param sourcePropertyRuleIntrospector The source introspector.
     * @param widgetModel The widget model.
     */
    public PropertyRuleIntrospectorUsingWidgetModel(IPropertyRuleIntrospector sourcePropertyRuleIntrospector, SimpleWidgetModel widgetModel) {
        if (widgetModel != null && widgetModel.getFieldMetaData() != null)
            m_sourcePropertyRuleIntrospector = new PropertyRuleIntrospectorUsingFieldMetaData(sourcePropertyRuleIntrospector, widgetModel.getFieldMetaData());
        else
            m_sourcePropertyRuleIntrospector = sourcePropertyRuleIntrospector;

        m_widgetModel = widgetModel;
    }

    /** Returns a Class object that identifies the declared type for the property.
     * @return a Class object that identifies the declared type for the property.
     */
    public Class getPropertyType() {
        if (!m_checkedPropertyType) {
            m_checkedPropertyType = true;
            if (m_sourcePropertyRuleIntrospector != null) {
                m_propertyType = m_sourcePropertyRuleIntrospector.getPropertyType();
                log.debug("getPropertyType()::PropertyRuleIntrospector -> " + m_propertyType);
            }
            if (m_propertyType == null) {
                m_propertyType = getPropertyTypeFromWidgetModel();
                log.debug("getPropertyType()::WidgetModel -> " + m_propertyType);
            }

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
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getMinLength() : null;
    }

    /** Returns the maxLength for the property.
     * @return the maxLength for the property.
     */
    public Integer getMaxLength() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getMaxLength() : null;
    }

    /** Returns the maxFracLength for the property.
     * @return the maxFracLength for the property.
     */
    public Integer getMaxFracLength() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getMaxFracLength() : null;
    }

    /** Returns the minValue for the property.
     * @return the minValue for the property.
     */
    public Object getMinValue() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getMinValue() : null;
    }

    /** Returns the maxValue for the property.
     * @return the maxValue for the property.
     */
    public Object getMaxValue() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getMaxValue() : null;
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
                m_caseType = getCaseTypeFromWidgetModel();
        }
        return m_caseType;
    }

    /** Returns the label for the property.
     * If multiple label rules are defined for a property, then the label from the last rule will be returned.
     * @return the label for the property.
     */
    public String getLabel() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getLabel() : null;
    }

    /** Returns the layout for the property.
     * If multiple layout rules are defined for a property, then the layout from the last rule will be returned.
     * @return the layout for the property.
     */
    public String getLayout() {
        if (!m_checkedLayout) {
            m_checkedLayout = true;
            if (m_sourcePropertyRuleIntrospector != null) {
                m_layout = m_sourcePropertyRuleIntrospector.getLayout();
                log.debug("getLayout(): PropertyRuleIntrospector's Layout -> " + m_layout);
            }
            if (m_layout == null) {
                m_layout = getLayoutFromWidgetModel();
                log.debug("getLayout(): WidgetModel's Layout -> " + m_layout);
            }

            // Get the default layout based on the datatype
            if(m_layout==null && getPropertyType()!=null) {
                if (getPropertyType() == Float.class ||
                        getPropertyType() == Float.TYPE ||
                        getPropertyType() == Double.class ||
                        getPropertyType() == Double.TYPE ||
                        getPropertyType() == BigDecimal.class) {
                    m_layout = DecimalFieldMetaData.getDecimalFormat();
                } else if (Number.class.isAssignableFrom(getPropertyType())) {
                    m_layout = IntegerFieldMetaData.getIntegerFormat();
                } else if (getPropertyType() == Currency.class) {
                    m_layout = CurrencyFieldMetaData.getCurrencyFormat();
                } else if (getPropertyType() == DateOnly.class) {
                    m_layout = DateOnlyFieldMetaData.getDateOnlyFormat();
                } else if (getPropertyType() == DateTime.class || Date.class.isAssignableFrom(getPropertyType())) {
                    m_layout = DateTimeFieldMetaData.getDateTimeFormat();
                }
                log.debug("getLayout(): Use default layout based on PropertyType = " + m_layout);
            }
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
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getPattern() : null;
    }

    /** Returns a true if the property is marked as mandatory.
     * @return a true if the property is marked as mandatory.
     */
    public boolean isMandatory() {
        if(m_mandatory == null) {
            boolean mandatory = false;
            // Has mandatory been overriden in the model
            if(m_widgetModel!=null && m_widgetModel.getMandatoryOverride()!=null)
                mandatory = m_widgetModel.getMandatoryOverride().booleanValue();
            else {
                if (m_sourcePropertyRuleIntrospector != null)
                    mandatory = m_sourcePropertyRuleIntrospector.isMandatory();
                if (!mandatory)
                    mandatory = getMandatoryFromWidgetModel();
            }
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

    /** Returns the comment type for the property.
     * @return the comment type for the property.
     */
    public String getCommentStyle() {
        //log.info("&&& PropertyRuleIntrospectorUsingWidgetModel.getCommentStyle(): m_sourcePropertyRuleIntrospector="+m_sourcePropertyRuleIntrospector);
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getCommentStyle() : null;
    }

    /** Returns the in-list values for the property.
     * Each entry in the Map will be a value/label pair.
     * @return the in-list values for the property.
     */
    public Map<String, String> getInListValues() {
        return m_sourcePropertyRuleIntrospector != null ? m_sourcePropertyRuleIntrospector.getInListValues() : null;
    }


    private Class getPropertyTypeFromWidgetModel() {
        if (m_widgetModel != null) {
            String dataType = m_widgetModel.getDataType();
            if (dataType != null)
                return Defaults.getClass(dataType);
        }
        return null;
    }

    private String getCaseTypeFromWidgetModel() {
        return m_widgetModel != null ? m_widgetModel.getStringCase() : null;
    }

    private String getLayoutFromWidgetModel() {
        return m_widgetModel != null ? m_widgetModel.getLayout() : null;
    }

    private boolean getMandatoryFromWidgetModel() {
        return m_widgetModel != null ? m_widgetModel.isMandatory() : false;
    }

    public Map<String, Object> getServiceInfo(){
		return null;
	}

    public String toString() {
        StringBuffer s=new StringBuffer("PropertyRuleIntrospectorUsingWidgetModel@").append(hashCode());
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
