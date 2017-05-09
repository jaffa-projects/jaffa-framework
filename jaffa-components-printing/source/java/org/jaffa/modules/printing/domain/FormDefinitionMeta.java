// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the FormDefinition persistent class.
 */
public class FormDefinitionMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.modules.printing.domain.FormDefinition";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Printing.FormDefinition]";



    // Field constants
    /** A constant to identity the FormId field.*/
    public static final String FORM_ID = "FormId";
    /** A constant to identity the FormName field.*/
    public static final String FORM_NAME = "FormName";
    /** A constant to identity the FormAlternate field.*/
    public static final String FORM_ALTERNATE = "FormAlternate";
    /** A constant to identity the FormVariation field.*/
    public static final String FORM_VARIATION = "FormVariation";
    /** A constant to identity the OutputType field.*/
    public static final String OUTPUT_TYPE = "OutputType";
    /** A constant to identity the FormTemplate field.*/
    public static final String FORM_TEMPLATE = "FormTemplate";
    /** A constant to identity the FieldLayout field.*/
    public static final String FIELD_LAYOUT = "FieldLayout";
    /** A constant to identity the Description field.*/
    public static final String DESCRIPTION = "Description";
    /** A constant to identity the Remarks field.*/
    public static final String REMARKS = "Remarks";
    /** A constant to identity the DomFactory field.*/
    public static final String DOM_FACTORY = "DomFactory";
    /** A constant to identity the DomClass field.*/
    public static final String DOM_CLASS = "DomClass";
    /** A constant to identity the DomKey1 field.*/
    public static final String DOM_KEY1 = "DomKey1";
    /** A constant to identity the DomKey2 field.*/
    public static final String DOM_KEY2 = "DomKey2";
    /** A constant to identity the DomKey3 field.*/
    public static final String DOM_KEY3 = "DomKey3";
    /** A constant to identity the DomKey4 field.*/
    public static final String DOM_KEY4 = "DomKey4";
    /** A constant to identity the DomKey5 field.*/
    public static final String DOM_KEY5 = "DomKey5";
    /** A constant to identity the DomKey6 field.*/
    public static final String DOM_KEY6 = "DomKey6";
    /** A constant to identity the AdditionalDataComponent field.*/
    public static final String ADDITIONAL_DATA_COMPONENT = "AdditionalDataComponent";
    /** A constant to identity the FollowOnFormName field.*/
    public static final String FOLLOW_ON_FORM_NAME = "FollowOnFormName";
    /** A constant to identity the FollowOnFormAlternate field.*/
    public static final String FOLLOW_ON_FORM_ALTERNATE = "FollowOnFormAlternate";
    /** A constant to identity the CreatedOn field.*/
    public static final String CREATED_ON = "CreatedOn";
    /** A constant to identity the CreatedBy field.*/
    public static final String CREATED_BY = "CreatedBy";
    /** A constant to identity the LastChangedOn field.*/
    public static final String LAST_CHANGED_ON = "LastChangedOn";
    /** A constant to identity the LastChangedBy field.*/
    public static final String LAST_CHANGED_BY = "LastChangedBy";


    // Meta Data Definitions

    /** A constant which holds the meta information for the FormId field.*/
    public static final FieldMetaData META_FORM_ID = new IntegerFieldMetaData(FORM_ID, "[label.Jaffa.Printing.FormDefinition.FormId]", Boolean.FALSE, null, null, null, new Integer(20));

    /** A constant which holds the meta information for the FormName field.*/
    public static final FieldMetaData META_FORM_NAME = new StringFieldMetaData(FORM_NAME, "[label.Jaffa.Printing.FormGroup.FormName]", Boolean.TRUE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the FormAlternate field.*/
    public static final FieldMetaData META_FORM_ALTERNATE = new StringFieldMetaData(FORM_ALTERNATE, "[label.Jaffa.Printing.FormDefinition.FormAlternate]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the FormVariation field.*/
    public static final FieldMetaData META_FORM_VARIATION = new StringFieldMetaData(FORM_VARIATION, "[label.Jaffa.Printing.FormDefinition.FormVariation]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the OutputType field.*/
    public static final FieldMetaData META_OUTPUT_TYPE = new StringFieldMetaData(OUTPUT_TYPE, "[label.Jaffa.Printing.FormDefinition.OutputType]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the FormTemplate field.*/
    public static final FieldMetaData META_FORM_TEMPLATE = new StringFieldMetaData(FORM_TEMPLATE, "[label.Jaffa.Printing.FormDefinition.FormTemplate]", Boolean.FALSE, null, new Integer(255), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the FieldLayout field.*/
    public static final FieldMetaData META_FIELD_LAYOUT = new StringFieldMetaData(FIELD_LAYOUT, "[label.Jaffa.Printing.FormDefinition.FieldLayout]", Boolean.FALSE, null, new Integer(255), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the Description field.*/
    public static final FieldMetaData META_DESCRIPTION = new StringFieldMetaData(DESCRIPTION, "[label.Jaffa.Printing.FormDefinition.Description]", Boolean.FALSE, null, new Integer(100), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the Remarks field.*/
    public static final FieldMetaData META_REMARKS = new StringFieldMetaData(REMARKS, "[label.Jaffa.Printing.FormDefinition.Remarks]", Boolean.FALSE, null, new Integer(255), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the DomFactory field.*/
    public static final FieldMetaData META_DOM_FACTORY = new StringFieldMetaData(DOM_FACTORY, "[label.Jaffa.Printing.FormDefinition.DomFactory]", Boolean.FALSE, null, new Integer(255), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the DomClass field.*/
    public static final FieldMetaData META_DOM_CLASS = new StringFieldMetaData(DOM_CLASS, "[label.Jaffa.Printing.FormDefinition.DomClass]", Boolean.FALSE, null, new Integer(255), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the DomKey1 field.*/
    public static final FieldMetaData META_DOM_KEY1 = new StringFieldMetaData(DOM_KEY1, "[label.Jaffa.Printing.FormDefinition.DomKey1]", Boolean.FALSE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the DomKey2 field.*/
    public static final FieldMetaData META_DOM_KEY2 = new StringFieldMetaData(DOM_KEY2, "[label.Jaffa.Printing.FormDefinition.DomKey2]", Boolean.FALSE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the DomKey3 field.*/
    public static final FieldMetaData META_DOM_KEY3 = new StringFieldMetaData(DOM_KEY3, "[label.Jaffa.Printing.FormDefinition.DomKey3]", Boolean.FALSE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the DomKey4 field.*/
    public static final FieldMetaData META_DOM_KEY4 = new StringFieldMetaData(DOM_KEY4, "[label.Jaffa.Printing.FormDefinition.DomKey4]", Boolean.FALSE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the DomKey5 field.*/
    public static final FieldMetaData META_DOM_KEY5 = new StringFieldMetaData(DOM_KEY5, "[label.Jaffa.Printing.FormDefinition.DomKey5]", Boolean.FALSE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the DomKey6 field.*/
    public static final FieldMetaData META_DOM_KEY6 = new StringFieldMetaData(DOM_KEY6, "[label.Jaffa.Printing.FormDefinition.DomKey6]", Boolean.FALSE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the AdditionalDataComponent field.*/
    public static final FieldMetaData META_ADDITIONAL_DATA_COMPONENT = new StringFieldMetaData(ADDITIONAL_DATA_COMPONENT, "[label.Jaffa.Printing.FormDefinition.AdditionalDataComponent]", Boolean.FALSE, null, new Integer(255), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the FollowOnFormName field.*/
    public static final FieldMetaData META_FOLLOW_ON_FORM_NAME = new StringFieldMetaData(FOLLOW_ON_FORM_NAME, "[label.Jaffa.Printing.FormDefinition.FollowOnFormName]", Boolean.FALSE, null, new Integer(20), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the FollowOnFormAlternate field.*/
    public static final FieldMetaData META_FOLLOW_ON_FORM_ALTERNATE = new StringFieldMetaData(FOLLOW_ON_FORM_ALTERNATE, "[label.Jaffa.Printing.FormDefinition.FollowOnFormAlternate]", Boolean.FALSE, null, new Integer(20), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the CreatedOn field.*/
    public static final FieldMetaData META_CREATED_ON = new DateTimeFieldMetaData(CREATED_ON, "[label.Jaffa.Common.CreatedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the CreatedBy field.*/
    public static final FieldMetaData META_CREATED_BY = new StringFieldMetaData(CREATED_BY, "[label.Jaffa.Common.CreatedBy]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the LastChangedOn field.*/
    public static final FieldMetaData META_LAST_CHANGED_ON = new DateTimeFieldMetaData(LAST_CHANGED_ON, "[label.Jaffa.Common.LastChangedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the LastChangedBy field.*/
    public static final FieldMetaData META_LAST_CHANGED_BY = new StringFieldMetaData(LAST_CHANGED_BY, "[label.Jaffa.Common.LastChangedBy]", Boolean.FALSE, null, new Integer(20), FieldMetaData.UPPER_CASE);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(FORM_ID, META_FORM_ID);
        m_fieldMap.put(FORM_NAME, META_FORM_NAME);
        m_fieldMap.put(FORM_ALTERNATE, META_FORM_ALTERNATE);
        m_fieldMap.put(FORM_VARIATION, META_FORM_VARIATION);
        m_fieldMap.put(OUTPUT_TYPE, META_OUTPUT_TYPE);
        m_fieldMap.put(FORM_TEMPLATE, META_FORM_TEMPLATE);
        m_fieldMap.put(FIELD_LAYOUT, META_FIELD_LAYOUT);
        m_fieldMap.put(DESCRIPTION, META_DESCRIPTION);
        m_fieldMap.put(REMARKS, META_REMARKS);
        m_fieldMap.put(DOM_FACTORY, META_DOM_FACTORY);
        m_fieldMap.put(DOM_CLASS, META_DOM_CLASS);
        m_fieldMap.put(DOM_KEY1, META_DOM_KEY1);
        m_fieldMap.put(DOM_KEY2, META_DOM_KEY2);
        m_fieldMap.put(DOM_KEY3, META_DOM_KEY3);
        m_fieldMap.put(DOM_KEY4, META_DOM_KEY4);
        m_fieldMap.put(DOM_KEY5, META_DOM_KEY5);
        m_fieldMap.put(DOM_KEY6, META_DOM_KEY6);
        m_fieldMap.put(ADDITIONAL_DATA_COMPONENT, META_ADDITIONAL_DATA_COMPONENT);
        m_fieldMap.put(FOLLOW_ON_FORM_NAME, META_FOLLOW_ON_FORM_NAME);
        m_fieldMap.put(FOLLOW_ON_FORM_ALTERNATE, META_FOLLOW_ON_FORM_ALTERNATE);
        m_fieldMap.put(CREATED_ON, META_CREATED_ON);
        m_fieldMap.put(CREATED_BY, META_CREATED_BY);
        m_fieldMap.put(LAST_CHANGED_ON, META_LAST_CHANGED_ON);
        m_fieldMap.put(LAST_CHANGED_BY, META_LAST_CHANGED_BY);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_FORM_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_FORM_NAME);
    }
    
    
    
    
    
    /** Returns the name of the persistent class.
     * @return the name of the persistent class.
     */
    public static String getName() {
        return m_name;
    }
    
    /** Getter for property labelToken.
     * @return Value of property labelToken.
     */
    public static String getLabelToken() {
        return m_labelToken;
    }
    
    /** This returns an array of all the fields of the persistent class.
     * @return an array of all the fields of the persistent class.
     */
    public static String[] getAttributes() {
        return DomainMetaDataHelper.getAttributes(m_fieldMap);
    }
    
    /** This returns an array of meta information for all the fields of the persistent class.
     * @return an array of meta information for all the fields of the persistent class.
     */
    public static FieldMetaData[] getFieldMetaData() {
        return DomainMetaDataHelper.getFieldMetaData(m_fieldMap);
    }
    
    /** This returns meta information for the input field.
     * @param fieldName the field name.
     * @return meta information for the input field.
     */
    public static FieldMetaData getFieldMetaData(String fieldName) {
        return DomainMetaDataHelper.getFieldMetaData(m_fieldMap, fieldName);
    }

    /** This returns an array of meta information for all the key fields of the persistent class.
     * @return an array of meta information for all the key fields of the persistent class.
     */
    public static FieldMetaData[] getKeyFields() {
        return (FieldMetaData[]) m_keyFields.toArray(new FieldMetaData[0]);
    }
    
    /** This returns an array of meta information for all the mandatory fields of the persistent class.
     * @return an array of meta information for all the mandatory fields of the persistent class.
     */
    public static FieldMetaData[] getMandatoryFields() {
        return (FieldMetaData[]) m_mandatoryFields.toArray(new FieldMetaData[0]);
    }
    
// .//GEN-END:2_be
// All the custom code goes here//GEN-FIRST:custom






// .//GEN-LAST:custom
}

