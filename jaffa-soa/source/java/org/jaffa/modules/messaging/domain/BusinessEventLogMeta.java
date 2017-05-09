// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.messaging.domain;

import org.jaffa.metadata.*;
import java.util.*;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/** This class has the meta information for the BusinessEventLog persistent class.
 */
public class BusinessEventLogMeta {
    
    // domain-object class name
    private static String m_name = "org.jaffa.modules.messaging.domain.BusinessEventLog";

    // token to be used for getting the label for the domain-object
    private static String m_labelToken = "[label.Jaffa.Messaging.BusinessEventLog]";



    // Field constants
    /** A constant to identity the LogId field.*/
    public static final String LOG_ID = "LogId";
    /** A constant to identity the CorrelationType field.*/
    public static final String CORRELATION_TYPE = "CorrelationType";
    /** A constant to identity the CorrelationKey1 field.*/
    public static final String CORRELATION_KEY1 = "CorrelationKey1";
    /** A constant to identity the CorrelationKey2 field.*/
    public static final String CORRELATION_KEY2 = "CorrelationKey2";
    /** A constant to identity the CorrelationKey3 field.*/
    public static final String CORRELATION_KEY3 = "CorrelationKey3";
    /** A constant to identity the ScheduledTaskId field.*/
    public static final String SCHEDULED_TASK_ID = "ScheduledTaskId";
    /** A constant to identity the MessageId field.*/
    public static final String MESSAGE_ID = "MessageId";
    /** A constant to identity the LoggedOn field.*/
    public static final String LOGGED_ON = "LoggedOn";
    /** A constant to identity the LoggedBy field.*/
    public static final String LOGGED_BY = "LoggedBy";
    /** A constant to identity the ProcessName field.*/
    public static final String PROCESS_NAME = "ProcessName";
    /** A constant to identity the SubProcessName field.*/
    public static final String SUB_PROCESS_NAME = "SubProcessName";
    /** A constant to identity the MessageType field.*/
    public static final String MESSAGE_TYPE = "MessageType";
    /** A constant to identity the MessageText field.*/
    public static final String MESSAGE_TEXT = "MessageText";
    /** A constant to identity the SourceClass field.*/
    public static final String SOURCE_CLASS = "SourceClass";
    /** A constant to identity the SourceMethod field.*/
    public static final String SOURCE_METHOD = "SourceMethod";
    /** A constant to identity the SourceLine field.*/
    public static final String SOURCE_LINE = "SourceLine";
    /** A constant to identity the StackTrace field.*/
    public static final String STACK_TRACE = "StackTrace";


    // Meta Data Definitions

    /** A constant which holds the meta information for the LogId field.*/
    public static final FieldMetaData META_LOG_ID = new StringFieldMetaData(LOG_ID, "[label.Jaffa.Messaging.BusinessEventLog.LogId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the CorrelationType field.*/
    public static final FieldMetaData META_CORRELATION_TYPE = new StringFieldMetaData(CORRELATION_TYPE, "[label.Jaffa.Messaging.BusinessEventLog.CorrelationType]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the CorrelationKey1 field.*/
    public static final FieldMetaData META_CORRELATION_KEY1 = new StringFieldMetaData(CORRELATION_KEY1, "[label.Jaffa.Messaging.BusinessEventLog.CorrelationKey1]", Boolean.FALSE, null, new Integer(100), null);

    /** A constant which holds the meta information for the CorrelationKey2 field.*/
    public static final FieldMetaData META_CORRELATION_KEY2 = new StringFieldMetaData(CORRELATION_KEY2, "[label.Jaffa.Messaging.BusinessEventLog.CorrelationKey2]", Boolean.FALSE, null, new Integer(100), null);

    /** A constant which holds the meta information for the CorrelationKey3 field.*/
    public static final FieldMetaData META_CORRELATION_KEY3 = new StringFieldMetaData(CORRELATION_KEY3, "[label.Jaffa.Messaging.BusinessEventLog.CorrelationKey3]", Boolean.FALSE, null, new Integer(100), null);

    /** A constant which holds the meta information for the ScheduledTaskId field.*/
    public static final FieldMetaData META_SCHEDULED_TASK_ID = new StringFieldMetaData(SCHEDULED_TASK_ID, "[label.Jaffa.Messaging.BusinessEventLog.ScheduledTaskId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the MessageId field.*/
    public static final FieldMetaData META_MESSAGE_ID = new StringFieldMetaData(MESSAGE_ID, "[label.Jaffa.Messaging.BusinessEventLog.MessageId]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the LoggedOn field.*/
    public static final FieldMetaData META_LOGGED_ON = new DateTimeFieldMetaData(LOGGED_ON, "[label.Jaffa.Messaging.BusinessEventLog.LoggedOn]", Boolean.FALSE, null, null, null);

    /** A constant which holds the meta information for the LoggedBy field.*/
    public static final FieldMetaData META_LOGGED_BY = new StringFieldMetaData(LOGGED_BY, "[label.Jaffa.Messaging.BusinessEventLog.LoggedBy]", Boolean.FALSE, null, new Integer(20), null);

    /** A constant which holds the meta information for the ProcessName field.*/
    public static final FieldMetaData META_PROCESS_NAME = new StringFieldMetaData(PROCESS_NAME, "[label.Jaffa.Messaging.BusinessEventLog.ProcessName]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the SubProcessName field.*/
    public static final FieldMetaData META_SUB_PROCESS_NAME = new StringFieldMetaData(SUB_PROCESS_NAME, "[label.Jaffa.Messaging.BusinessEventLog.SubProcessName]", Boolean.FALSE, null, new Integer(80), null);

    /** A constant which holds the meta information for the MessageType field.*/
    public static final FieldMetaData META_MESSAGE_TYPE = new StringFieldMetaData(MESSAGE_TYPE, "[label.Jaffa.Messaging.BusinessEventLog.MessageType]", Boolean.FALSE, null, new Integer(20), null);

    /** A constant which holds the meta information for the MessageText field.*/
    public static final FieldMetaData META_MESSAGE_TEXT = new StringFieldMetaData(MESSAGE_TEXT, "[label.Jaffa.Messaging.BusinessEventLog.MessageText]", Boolean.FALSE, null, new Integer(4000), null);

    /** A constant which holds the meta information for the SourceClass field.*/
    public static final FieldMetaData META_SOURCE_CLASS = new StringFieldMetaData(SOURCE_CLASS, "[label.Jaffa.Messaging.BusinessEventLog.SourceClass]", Boolean.FALSE, null, new Integer(255), null);

    /** A constant which holds the meta information for the SourceMethod field.*/
    public static final FieldMetaData META_SOURCE_METHOD = new StringFieldMetaData(SOURCE_METHOD, "[label.Jaffa.Messaging.BusinessEventLog.SourceMethod]", Boolean.FALSE, null, new Integer(100), null);

    /** A constant which holds the meta information for the SourceLine field.*/
    public static final FieldMetaData META_SOURCE_LINE = new IntegerFieldMetaData(SOURCE_LINE, "[label.Jaffa.Messaging.BusinessEventLog.SourceLine]", Boolean.FALSE, null, null, null, new Integer(8));

    /** A constant which holds the meta information for the StackTrace field.*/
    public static final FieldMetaData META_STACK_TRACE = new StringFieldMetaData(STACK_TRACE, "[label.Jaffa.Messaging.BusinessEventLog.StackTrace]", Boolean.FALSE, null, new Integer(4000), null);



    // Map of FieldConstants + MetaDataDefinitions
    private static Map m_fieldMap = new HashMap();
    static {
        m_fieldMap.put(LOG_ID, META_LOG_ID);
        m_fieldMap.put(CORRELATION_TYPE, META_CORRELATION_TYPE);
        m_fieldMap.put(CORRELATION_KEY1, META_CORRELATION_KEY1);
        m_fieldMap.put(CORRELATION_KEY2, META_CORRELATION_KEY2);
        m_fieldMap.put(CORRELATION_KEY3, META_CORRELATION_KEY3);
        m_fieldMap.put(SCHEDULED_TASK_ID, META_SCHEDULED_TASK_ID);
        m_fieldMap.put(MESSAGE_ID, META_MESSAGE_ID);
        m_fieldMap.put(LOGGED_ON, META_LOGGED_ON);
        m_fieldMap.put(LOGGED_BY, META_LOGGED_BY);
        m_fieldMap.put(PROCESS_NAME, META_PROCESS_NAME);
        m_fieldMap.put(SUB_PROCESS_NAME, META_SUB_PROCESS_NAME);
        m_fieldMap.put(MESSAGE_TYPE, META_MESSAGE_TYPE);
        m_fieldMap.put(MESSAGE_TEXT, META_MESSAGE_TEXT);
        m_fieldMap.put(SOURCE_CLASS, META_SOURCE_CLASS);
        m_fieldMap.put(SOURCE_METHOD, META_SOURCE_METHOD);
        m_fieldMap.put(SOURCE_LINE, META_SOURCE_LINE);
        m_fieldMap.put(STACK_TRACE, META_STACK_TRACE);
    }
    
    // List of MetaDataDefinitions for key fields
    private static List m_keyFields = new LinkedList();
    static {
        m_keyFields.add(META_LOG_ID);
    }
    
    // List of MetaDataDefinitions for mandatory fields
    private static List m_mandatoryFields = new LinkedList();
    static {
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

