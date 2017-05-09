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
/** This class has the meta information for the FormTemplate persistent class.
 */
public class FormTemplateMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.modules.printing.domain.FormTemplate";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Printing.FormTemplate]";



    // Field constants
    /** A constant to identity the FormId field.*/
    public static final String FORM_ID = "FormId";
    /** A constant to identity the TemplateData field.*/
    public static final String TEMPLATE_DATA = "TemplateData";
    /** A constant to identity the LayoutData field.*/
    public static final String LAYOUT_DATA = "LayoutData";


    // Meta Data Definitions

    /** A constant which holds the meta information for the FormId field.*/
    public static final FieldMetaData META_FORM_ID = new IntegerFieldMetaData(FORM_ID, "[label.Jaffa.Printing.FormDefinition.FormId]", Boolean.TRUE, null, null, null, new Integer(20));

    /** A constant which holds the meta information for the TemplateData field.*/
    public static final FieldMetaData META_TEMPLATE_DATA = new RawFieldMetaData(TEMPLATE_DATA, "[label.Jaffa.Printing.FormTemplate.TemplateData]", Boolean.FALSE);

    /** A constant which holds the meta information for the LayoutData field.*/
    public static final FieldMetaData META_LAYOUT_DATA = new RawFieldMetaData(LAYOUT_DATA, "[label.Jaffa.Printing.FormTemplate.LayoutData]", Boolean.FALSE);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(FORM_ID, META_FORM_ID);
        m_fieldMap.put(TEMPLATE_DATA, META_TEMPLATE_DATA);
        m_fieldMap.put(LAYOUT_DATA, META_LAYOUT_DATA);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_FORM_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_FORM_ID);
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

