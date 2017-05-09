// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.jaffa.modules.admin.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the User persistent class.
 */
public class UserMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.applications.jaffa.modules.admin.domain.User";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Admin.User]";



    // Field constants
    /** A constant to identity the UserName field.*/
    public static final String USER_NAME = "UserName";
    /** A constant to identity the FirstName field.*/
    public static final String FIRST_NAME = "FirstName";
    /** A constant to identity the LastName field.*/
    public static final String LAST_NAME = "LastName";
    /** A constant to identity the Password field.*/
    public static final String PASSWORD = "Password";
    /** A constant to identity the Status field.*/
    public static final String STATUS = "Status";
    /** A constant to identity the EMailAddress field.*/
    public static final String E_MAIL_ADDRESS = "EMailAddress";
    /** A constant to identity the SecurityQuestion field.*/
    public static final String SECURITY_QUESTION = "SecurityQuestion";
    /** A constant to identity the SecurityAnswer field.*/
    public static final String SECURITY_ANSWER = "SecurityAnswer";
    /** A constant to identity the CreatedOn field.*/
    public static final String CREATED_ON = "CreatedOn";
    /** A constant to identity the CreatedBy field.*/
    public static final String CREATED_BY = "CreatedBy";
    /** A constant to identity the LastUpdatedOn field.*/
    public static final String LAST_UPDATED_ON = "LastUpdatedOn";
    /** A constant to identity the LastUpdatedBy field.*/
    public static final String LAST_UPDATED_BY = "LastUpdatedBy";


    // Meta Data Definitions

    /** A constant which holds the meta information for the UserName field.*/
    public static final FieldMetaData META_USER_NAME = new StringFieldMetaData(USER_NAME, "[label.Jaffa.Admin.User.UserName]", Boolean.TRUE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the FirstName field.*/
    public static final FieldMetaData META_FIRST_NAME = new StringFieldMetaData(FIRST_NAME, "[label.Jaffa.Admin.User.FirstName]", Boolean.TRUE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the LastName field.*/
    public static final FieldMetaData META_LAST_NAME = new StringFieldMetaData(LAST_NAME, "[label.Jaffa.Admin.User.LastName]", Boolean.TRUE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the Password field.*/
    public static final FieldMetaData META_PASSWORD = new StringFieldMetaData(PASSWORD, "[label.Jaffa.Admin.User.Password]", Boolean.TRUE, null, new Integer(80), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the Status field.*/
    public static final FieldMetaData META_STATUS = new StringFieldMetaData(STATUS, "[label.Jaffa.Admin.User.Status]", Boolean.TRUE, null, new Integer(1), FieldMetaData.UPPER_CASE);

    /** A constant which holds the meta information for the EMailAddress field.*/
    public static final FieldMetaData META_E_MAIL_ADDRESS = new StringFieldMetaData(E_MAIL_ADDRESS, "[label.Jaffa.Admin.User.EMailAddress]", Boolean.FALSE, null, new Integer(75), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the SecurityQuestion field.*/
    public static final FieldMetaData META_SECURITY_QUESTION = new IntegerFieldMetaData(SECURITY_QUESTION, "[label.Jaffa.Admin.UserRequest.SecurityQuestion]", Boolean.FALSE, null, null, null, new Integer(20));

    /** A constant which holds the meta information for the SecurityAnswer field.*/
    public static final FieldMetaData META_SECURITY_ANSWER = new StringFieldMetaData(SECURITY_ANSWER, "[label.Jaffa.Admin.UserRequest.SecurityAnswer]", Boolean.FALSE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the CreatedOn field.*/
    public static final FieldMetaData META_CREATED_ON = new DateTimeFieldMetaData(CREATED_ON, "[label.Jaffa.Admin.User.CreatedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the CreatedBy field.*/
    public static final FieldMetaData META_CREATED_BY = new StringFieldMetaData(CREATED_BY, "[label.Jaffa.Admin.User.CreatedBy]", Boolean.FALSE, null, new Integer(50), FieldMetaData.MIXED_CASE);

    /** A constant which holds the meta information for the LastUpdatedOn field.*/
    public static final FieldMetaData META_LAST_UPDATED_ON = new DateTimeFieldMetaData(LAST_UPDATED_ON, "[label.Jaffa.Admin.User.LastUpdatedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the LastUpdatedBy field.*/
    public static final FieldMetaData META_LAST_UPDATED_BY = new StringFieldMetaData(LAST_UPDATED_BY, "[label.Jaffa.Admin.User.LastUpdatedBy]", Boolean.FALSE, null, new Integer(50), FieldMetaData.MIXED_CASE);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(USER_NAME, META_USER_NAME);
        m_fieldMap.put(FIRST_NAME, META_FIRST_NAME);
        m_fieldMap.put(LAST_NAME, META_LAST_NAME);
        m_fieldMap.put(PASSWORD, META_PASSWORD);
        m_fieldMap.put(STATUS, META_STATUS);
        m_fieldMap.put(E_MAIL_ADDRESS, META_E_MAIL_ADDRESS);
        m_fieldMap.put(SECURITY_QUESTION, META_SECURITY_QUESTION);
        m_fieldMap.put(SECURITY_ANSWER, META_SECURITY_ANSWER);
        m_fieldMap.put(CREATED_ON, META_CREATED_ON);
        m_fieldMap.put(CREATED_BY, META_CREATED_BY);
        m_fieldMap.put(LAST_UPDATED_ON, META_LAST_UPDATED_ON);
        m_fieldMap.put(LAST_UPDATED_BY, META_LAST_UPDATED_BY);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_USER_NAME);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
        m_mandatoryFields.add(META_USER_NAME);
        m_mandatoryFields.add(META_FIRST_NAME);
        m_mandatoryFields.add(META_LAST_NAME);
        m_mandatoryFields.add(META_PASSWORD);
        m_mandatoryFields.add(META_STATUS);
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

