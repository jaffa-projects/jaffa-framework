// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.test.modules.time.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the UserTimeEntry persistent class.
 */
public class UserTimeEntryMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.applications.test.modules.time.domain.UserTimeEntry";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Time.UserTimeEntry]";



    // Field constants
    /** A constant to identity the UserName field.*/
    public static final String USER_NAME = "UserName";
    /** A constant to identity the ProjectCode field.*/
    public static final String PROJECT_CODE = "ProjectCode";
    /** A constant to identity the Activity field.*/
    public static final String ACTIVITY = "Activity";
    /** A constant to identity the Task field.*/
    public static final String TASK = "Task";
    /** A constant to identity the PeriodStart field.*/
    public static final String PERIOD_START = "PeriodStart";
    /** A constant to identity the PeriodEnd field.*/
    public static final String PERIOD_END = "PeriodEnd";


    // Meta Data Definitions

    /** A constant which holds the meta information for the UserName field.*/
    public static final FieldMetaData META_USER_NAME = new StringFieldMetaData(USER_NAME, "[label.Jaffa.Admin.Time.UserName]", Boolean.TRUE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the ProjectCode field.*/
    public static final FieldMetaData META_PROJECT_CODE = new StringFieldMetaData(PROJECT_CODE, "[label.Jaffa.Admin.Time.ProjectCode]", Boolean.TRUE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the Activity field.*/
    public static final FieldMetaData META_ACTIVITY = new StringFieldMetaData(ACTIVITY, "[label.Jaffa.Admin.Time.Activity]", Boolean.TRUE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the Task field.*/
    public static final FieldMetaData META_TASK = new StringFieldMetaData(TASK, "[label.Jaffa.Admin.Time.Task]", Boolean.TRUE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the PeriodStart field.*/
    public static final FieldMetaData META_PERIOD_START = new DateTimeFieldMetaData(PERIOD_START, "[label.Jaffa.Admin.Time.PeriodStart]", Boolean.TRUE, null, null, null);

    /** A constant which holds the meta information for the PeriodEnd field.*/
    public static final FieldMetaData META_PERIOD_END = new DateTimeFieldMetaData(PERIOD_END, "[label.Jaffa.Admin.Time.PeriodEnd]", Boolean.TRUE, null, null, null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(USER_NAME, META_USER_NAME);
        m_fieldMap.put(PROJECT_CODE, META_PROJECT_CODE);
        m_fieldMap.put(ACTIVITY, META_ACTIVITY);
        m_fieldMap.put(TASK, META_TASK);
        m_fieldMap.put(PERIOD_START, META_PERIOD_START);
        m_fieldMap.put(PERIOD_END, META_PERIOD_END);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_USER_NAME);
        m_keyFields.add(META_PROJECT_CODE);
        m_keyFields.add(META_TASK);
        m_keyFields.add(META_PERIOD_START);
        m_keyFields.add(META_PERIOD_END);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_USER_NAME);
        m_mandatoryFields.add(META_PROJECT_CODE);
        m_mandatoryFields.add(META_ACTIVITY);
        m_mandatoryFields.add(META_TASK);
        m_mandatoryFields.add(META_PERIOD_START);
        m_mandatoryFields.add(META_PERIOD_END);
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

