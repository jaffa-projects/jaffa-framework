package org.jaffa.components.attachment.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jaffa.components.attachment.domain.Attachment;
import org.jaffa.components.attachment.domain.AttachmentMeta;
import org.jaffa.test.junit.AbstractDataWrapper;

/** UnitTests for the JDBCAppender
 * @author GautamJ
 */
public class JDBCAppenderWithAttachmentTest extends TestCase {
    
    private static final Logger log = Logger.getLogger(JDBCAppenderWithAttachmentTest.class);
    private static final String BUSINESS_EVENT_LOGS = JDBCAppenderWithAttachmentWrapper.TABLE_PREFIX + "J_BUSINESS_EVENT_LOGS";
    private static final String ATTACHMENTS = JDBCAppenderWithAttachmentWrapper.TABLE_PREFIX + "J_ATTACHMENTS";
    
    private Connection m_connection = null;
    private Statement m_stmt = null;
    private ResultSet m_rs = null;
    
    public static Test suite() {
        return new JDBCAppenderWithAttachmentWrapper(new TestSuite(JDBCAppenderWithAttachmentTest.class));
    }
    
    public JDBCAppenderWithAttachmentTest(String testName) {
        super(testName);
    }
    
    protected void tearDown() throws Exception {
        clearMDC();
        if (m_connection != null && !m_connection.isClosed())
            m_connection.close();
        super.tearDown();
    }
    
    private void createMDC(String correlationType) throws Exception {
        MDC.put("CorrelationType", correlationType);
        MDC.put("CorrelationKey1", "CK1");
        MDC.put("CorrelationKey2", "CK2");
        MDC.put("CorrelationKey3", "CK3");
        MDC.put("ScheduledTaskId", "STI");
        MDC.put("MessageId", "MI");
        MDC.put("LoggedBy", "LB");
        MDC.put("ProcessName", "PN");
        MDC.put("SubProcessName", "SPN");
    }
    
    private void clearMDC() {
        for (Object key : new HashMap(MDC.getContext()).keySet())
            MDC.remove((String) key);
    }
    
    private void clearLogs(String correlationType) {
        try {
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_stmt.execute("delete from " + BUSINESS_EVENT_LOGS + " where CORRELATION_TYPE='" + correlationType + '\'');
            m_stmt.close();
            m_connection.commit();
            m_connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void clearAttachments(String logId) {
        try {
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_stmt.execute("delete from " + ATTACHMENTS + " where SERIALIZED_KEY='" + generateSerializedKey(logId) + '\'');
            m_stmt.close();
            m_connection.commit();
            m_connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String generateSerializedKey(String logId) {
        return "org.jaffa.modules.messaging.domain.BusinessEventLog;" + logId;
    }
    
    
    /** Tests the logging of a DEBUG message.
     */
    public void testLogDebug() throws Exception {
        String correlationType = "testLogDebug";
        try {
            createMDC(correlationType);
            log.debug("test debug");
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + BUSINESS_EVENT_LOGS + " where CORRELATION_TYPE='" + correlationType + '\'');
            assertFalse("A debug message should not be picked up by the JDBCAppender, since the priority has been set to INFO", m_rs.next());
            m_stmt.close();
            m_connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
    
    /** Tests the logging of an INFO message.
     */
    public void testLogInfo1() throws Exception {
        String correlationType = "testLogInfo1";
        try {
            createMDC(correlationType);
            log.info("test info");
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + BUSINESS_EVENT_LOGS + " where CORRELATION_TYPE='" + correlationType + '\'');
            assertTrue("Query on " + BUSINESS_EVENT_LOGS + " should have returned a ResultSet", m_rs.next());
            assertEquals(correlationType, m_rs.getString("CORRELATION_TYPE"));
            assertEquals("CK1", m_rs.getString("CORRELATION_KEY1"));
            assertEquals("CK2", m_rs.getString("CORRELATION_KEY2"));
            assertEquals("CK3", m_rs.getString("CORRELATION_KEY3"));
            assertEquals("STI", m_rs.getString("SCHEDULED_TASK_ID"));
            assertEquals("MI", m_rs.getString("MESSAGE_ID"));
            assertNotNull("Logged On value should be present", m_rs.getTimestamp("LOGGED_ON"));
            assertEquals("LB", m_rs.getString("LOGGED_BY"));
            assertEquals("PN", m_rs.getString("PROCESS_NAME"));
            assertEquals("SPN", m_rs.getString("SUB_PROCESS_NAME"));
            assertEquals("INFO", m_rs.getString("MESSAGE_TYPE"));
            assertEquals("test info", m_rs.getString("MESSAGE_TEXT"));
            assertEquals(JDBCAppenderWithAttachmentTest.class.getName(), m_rs.getString("SOURCE_CLASS"));
            assertNull(m_rs.getString("SOURCE_METHOD"));
            assertNull(m_rs.getString("SOURCE_LINE"));
            assertTrue(m_rs.getString("STACK_TRACE") == null || m_rs.getString("STACK_TRACE").trim().length() == 0);
            assertFalse("Query on " + BUSINESS_EVENT_LOGS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        } finally {
            clearLogs(correlationType);
        }
    }
    
    /** Tests the logging of an INFO message with a few NULL fields
     */
    public void testLogInfo2() throws Exception {
        String correlationType = "testLogInfo2";
        try {
            MDC.put("CorrelationType", correlationType);
            log.info("test info");
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + BUSINESS_EVENT_LOGS + " where CORRELATION_TYPE='" + correlationType + '\'');
            assertTrue("Query on " + BUSINESS_EVENT_LOGS + " should have returned a ResultSet", m_rs.next());
            assertEquals(correlationType, m_rs.getString("CORRELATION_TYPE"));
            assertNull(m_rs.getString("CORRELATION_KEY1"));
            assertNull(m_rs.getString("CORRELATION_KEY2"));
            assertNull(m_rs.getString("CORRELATION_KEY3"));
            assertNull(m_rs.getString("SCHEDULED_TASK_ID"));
            assertNull(m_rs.getString("MESSAGE_ID"));
            assertNotNull("Logged On value should be present", m_rs.getTimestamp("LOGGED_ON"));
            assertNull(m_rs.getString("LOGGED_BY"));
            assertNull(m_rs.getString("PROCESS_NAME"));
            assertNull(m_rs.getString("SUB_PROCESS_NAME"));
            assertEquals("INFO", m_rs.getString("MESSAGE_TYPE"));
            assertEquals("test info", m_rs.getString("MESSAGE_TEXT"));
            assertEquals(JDBCAppenderWithAttachmentTest.class.getName(), m_rs.getString("SOURCE_CLASS"));
            assertNull(m_rs.getString("SOURCE_METHOD"));
            assertNull(m_rs.getString("SOURCE_LINE"));
            assertTrue(m_rs.getString("STACK_TRACE") == null || m_rs.getString("STACK_TRACE").trim().length() == 0);
            assertFalse("Query on " + BUSINESS_EVENT_LOGS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        } finally {
            clearLogs(correlationType);
        }
    }
    
    /** Tests the logging of an ERROR message.
     */
    public void testLogError() throws Exception {
        String correlationType = "testLogError";
        try {
            createMDC(correlationType);
            log.error("test error", new Exception("Test Exception"));
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + BUSINESS_EVENT_LOGS + " where CORRELATION_TYPE='" + correlationType + '\'');
            assertTrue("Query on " + BUSINESS_EVENT_LOGS + " should have returned a ResultSet", m_rs.next());
            assertEquals(correlationType, m_rs.getString("CORRELATION_TYPE"));
            assertEquals("CK1", m_rs.getString("CORRELATION_KEY1"));
            assertEquals("CK2", m_rs.getString("CORRELATION_KEY2"));
            assertEquals("CK3", m_rs.getString("CORRELATION_KEY3"));
            assertEquals("STI", m_rs.getString("SCHEDULED_TASK_ID"));
            assertEquals("MI", m_rs.getString("MESSAGE_ID"));
            assertNotNull("Logged On value should be present", m_rs.getTimestamp("LOGGED_ON"));
            assertEquals("LB", m_rs.getString("LOGGED_BY"));
            assertEquals("PN", m_rs.getString("PROCESS_NAME"));
            assertEquals("SPN", m_rs.getString("SUB_PROCESS_NAME"));
            assertEquals("ERROR", m_rs.getString("MESSAGE_TYPE"));
            assertEquals("test error", m_rs.getString("MESSAGE_TEXT"));
            assertEquals(JDBCAppenderWithAttachmentTest.class.getName(), m_rs.getString("SOURCE_CLASS"));
            assertNull(m_rs.getString("SOURCE_METHOD"));
            assertNull(m_rs.getString("SOURCE_LINE"));
            assertTrue(m_rs.getString("STACK_TRACE") != null && m_rs.getString("STACK_TRACE").indexOf("Test Exception") > -1);
            assertFalse("Query on " + BUSINESS_EVENT_LOGS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        } finally {
            clearLogs(correlationType);
        }
    }
    
    /** Tests the logging of an INFO message along with a byte[] attachment.
     */
    public void testBytesAttachment() throws Exception {
        String correlationType = "testBytesAttachment";
        byte[] data = "test byte[] attachment".getBytes();
        String logId = null;
        try {
            createMDC(correlationType);
            MDC.put(AttachmentMeta.DATA, data);
            log.info("test attachment");
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + BUSINESS_EVENT_LOGS + " where CORRELATION_TYPE='" + correlationType + '\'');
            assertTrue("Query on " + BUSINESS_EVENT_LOGS + " should have returned a ResultSet", m_rs.next());
            logId = m_rs.getString("LOG_ID");
            assertEquals(correlationType, m_rs.getString("CORRELATION_TYPE"));
            assertEquals("CK1", m_rs.getString("CORRELATION_KEY1"));
            assertEquals("CK2", m_rs.getString("CORRELATION_KEY2"));
            assertEquals("CK3", m_rs.getString("CORRELATION_KEY3"));
            assertEquals("STI", m_rs.getString("SCHEDULED_TASK_ID"));
            assertEquals("MI", m_rs.getString("MESSAGE_ID"));
            assertNotNull("Logged On value should be present", m_rs.getTimestamp("LOGGED_ON"));
            assertEquals("LB", m_rs.getString("LOGGED_BY"));
            assertEquals("PN", m_rs.getString("PROCESS_NAME"));
            assertEquals("SPN", m_rs.getString("SUB_PROCESS_NAME"));
            assertEquals("INFO", m_rs.getString("MESSAGE_TYPE"));
            assertEquals("test attachment", m_rs.getString("MESSAGE_TEXT"));
            assertEquals(JDBCAppenderWithAttachmentTest.class.getName(), m_rs.getString("SOURCE_CLASS"));
            assertNull(m_rs.getString("SOURCE_METHOD"));
            assertNull(m_rs.getString("SOURCE_LINE"));
            assertTrue(m_rs.getString("STACK_TRACE") == null || m_rs.getString("STACK_TRACE").trim().length() == 0);
            assertFalse("Query on " + BUSINESS_EVENT_LOGS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
            
            //Check the attachment
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + ATTACHMENTS + " where SERIALIZED_KEY='" + generateSerializedKey(logId) + '\'');
            assertTrue("Query on " + ATTACHMENTS + " should have returned a ResultSet", m_rs.next());
            assertEquals(generateSerializedKey(logId), m_rs.getString("SERIALIZED_KEY"));
            assertEquals(data.getClass().getName(), m_rs.getString("ORIGINAL_FILE_NAME"));
            assertEquals("E", m_rs.getString("ATTACHMENT_TYPE"));
            assertEquals(AttachmentService.CONTENT_TYPE_BYTE_ARRAY, m_rs.getString("CONTENT_TYPE"));
            assertNull(m_rs.getString("DESCRIPTION"));
            assertNull(m_rs.getString("REMARKS"));
            assertNull(m_rs.getString("SUPERCEDED_BY"));
            assertNotNull(m_rs.getTimestamp("CREATED_ON"));
            assertNull(m_rs.getString("CREATED_BY"));
            assertNull(m_rs.getTimestamp("LAST_CHANGED_ON"));
            assertNull(m_rs.getString("LAST_CHANGED_BY"));
            assertEquals(new String(data), new String(m_rs.getBytes("DATA")));
            assertFalse("Query on " + ATTACHMENTS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        } finally {
            clearAttachments(logId);
            clearLogs(correlationType);
        }
    }
    
    /** Tests the logging of an INFO message along with a String attachment.
     */
    public void testStringAttachment() throws Exception {
        String correlationType = "testStringAttachment";
        String data = "test string attachment";
        String logId = null;
        try {
            createMDC(correlationType);
            MDC.put(AttachmentMeta.DATA, data);
            log.info("test attachment");
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + BUSINESS_EVENT_LOGS + " where CORRELATION_TYPE='" + correlationType + '\'');
            assertTrue("Query on " + BUSINESS_EVENT_LOGS + " should have returned a ResultSet", m_rs.next());
            logId = m_rs.getString("LOG_ID");
            assertEquals(correlationType, m_rs.getString("CORRELATION_TYPE"));
            assertEquals("CK1", m_rs.getString("CORRELATION_KEY1"));
            assertEquals("CK2", m_rs.getString("CORRELATION_KEY2"));
            assertEquals("CK3", m_rs.getString("CORRELATION_KEY3"));
            assertEquals("STI", m_rs.getString("SCHEDULED_TASK_ID"));
            assertEquals("MI", m_rs.getString("MESSAGE_ID"));
            assertNotNull("Logged On value should be present", m_rs.getTimestamp("LOGGED_ON"));
            assertEquals("LB", m_rs.getString("LOGGED_BY"));
            assertEquals("PN", m_rs.getString("PROCESS_NAME"));
            assertEquals("SPN", m_rs.getString("SUB_PROCESS_NAME"));
            assertEquals("INFO", m_rs.getString("MESSAGE_TYPE"));
            assertEquals("test attachment", m_rs.getString("MESSAGE_TEXT"));
            assertEquals(JDBCAppenderWithAttachmentTest.class.getName(), m_rs.getString("SOURCE_CLASS"));
            assertNull(m_rs.getString("SOURCE_METHOD"));
            assertNull(m_rs.getString("SOURCE_LINE"));
            assertTrue(m_rs.getString("STACK_TRACE") == null || m_rs.getString("STACK_TRACE").trim().length() == 0);
            assertFalse("Query on " + BUSINESS_EVENT_LOGS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
            
            //Check the attachment
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + ATTACHMENTS + " where SERIALIZED_KEY='" + generateSerializedKey(logId) + '\'');
            assertTrue("Query on " + ATTACHMENTS + " should have returned a ResultSet", m_rs.next());
            assertEquals(generateSerializedKey(logId), m_rs.getString("SERIALIZED_KEY"));
            assertEquals("java.lang.String", m_rs.getString("ORIGINAL_FILE_NAME"));
            assertEquals("E", m_rs.getString("ATTACHMENT_TYPE"));
            assertEquals(AttachmentService.CONTENT_TYPE_STRING, m_rs.getString("CONTENT_TYPE"));
            assertNull(m_rs.getString("DESCRIPTION"));
            assertNull(m_rs.getString("REMARKS"));
            assertNull(m_rs.getString("SUPERCEDED_BY"));
            assertNotNull(m_rs.getTimestamp("CREATED_ON"));
            assertNull(m_rs.getString("CREATED_BY"));
            assertNull(m_rs.getTimestamp("LAST_CHANGED_ON"));
            assertNull(m_rs.getString("LAST_CHANGED_BY"));
            assertEquals(data, new String(m_rs.getBytes("DATA")));
            assertFalse("Query on " + ATTACHMENTS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        } finally {
            clearAttachments(logId);
            clearLogs(correlationType);
        }
    }
    
    /** Tests the logging of an INFO message along with a domain object attachment (has JAXB annotations).
     */
    public void testJAXBAttachment() throws Exception {
        String correlationType = "testJAXBAttachment";
        Attachment data = new Attachment();
        data.setRemarks("An example domain object");
        String logId = null;
        try {
            createMDC(correlationType);
            MDC.put(AttachmentMeta.DATA, data);
            log.info("test attachment");
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + BUSINESS_EVENT_LOGS + " where CORRELATION_TYPE='" + correlationType + '\'');
            assertTrue("Query on " + BUSINESS_EVENT_LOGS + " should have returned a ResultSet", m_rs.next());
            logId = m_rs.getString("LOG_ID");
            assertEquals(correlationType, m_rs.getString("CORRELATION_TYPE"));
            assertEquals("CK1", m_rs.getString("CORRELATION_KEY1"));
            assertEquals("CK2", m_rs.getString("CORRELATION_KEY2"));
            assertEquals("CK3", m_rs.getString("CORRELATION_KEY3"));
            assertEquals("STI", m_rs.getString("SCHEDULED_TASK_ID"));
            assertEquals("MI", m_rs.getString("MESSAGE_ID"));
            assertNotNull("Logged On value should be present", m_rs.getTimestamp("LOGGED_ON"));
            assertEquals("LB", m_rs.getString("LOGGED_BY"));
            assertEquals("PN", m_rs.getString("PROCESS_NAME"));
            assertEquals("SPN", m_rs.getString("SUB_PROCESS_NAME"));
            assertEquals("INFO", m_rs.getString("MESSAGE_TYPE"));
            assertEquals("test attachment", m_rs.getString("MESSAGE_TEXT"));
            assertEquals(JDBCAppenderWithAttachmentTest.class.getName(), m_rs.getString("SOURCE_CLASS"));
            assertNull(m_rs.getString("SOURCE_METHOD"));
            assertNull(m_rs.getString("SOURCE_LINE"));
            assertTrue(m_rs.getString("STACK_TRACE") == null || m_rs.getString("STACK_TRACE").trim().length() == 0);
            assertFalse("Query on " + BUSINESS_EVENT_LOGS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
            
            //Check the attachment
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + ATTACHMENTS + " where SERIALIZED_KEY='" + generateSerializedKey(logId) + '\'');
            assertTrue("Query on " + ATTACHMENTS + " should have returned a ResultSet", m_rs.next());
            assertEquals(generateSerializedKey(logId), m_rs.getString("SERIALIZED_KEY"));
            assertEquals(data.getClass().getName(), m_rs.getString("ORIGINAL_FILE_NAME"));
            assertEquals("E", m_rs.getString("ATTACHMENT_TYPE"));
            assertEquals(AttachmentService.CONTENT_TYPE_JAXB, m_rs.getString("CONTENT_TYPE"));
            assertNull(m_rs.getString("DESCRIPTION"));
            assertNull(m_rs.getString("REMARKS"));
            assertNull(m_rs.getString("SUPERCEDED_BY"));
            assertNotNull(m_rs.getTimestamp("CREATED_ON"));
            assertNull(m_rs.getString("CREATED_BY"));
            assertNull(m_rs.getTimestamp("LAST_CHANGED_ON"));
            assertNull(m_rs.getString("LAST_CHANGED_BY"));
            assertTrue(new String(m_rs.getBytes("DATA")).indexOf(data.getRemarks()) > -1);
            assertFalse("Query on " + ATTACHMENTS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        } finally {
            clearAttachments(logId);
            clearLogs(correlationType);
        }
    }
    
    /** Tests the logging of an INFO message along with an attachment which is neither of byte[], String, File nor has JAXB annotations.
     */
    public void testXmlEncoderAttachment() throws Exception {
        String correlationType = "testXmlEncoder";
        Map data = new HashMap();
        data.put("someKey", "someValue");
        String logId = null;
        try {
            createMDC(correlationType);
            MDC.put(AttachmentMeta.DATA, data);
            log.info("test attachment");
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + BUSINESS_EVENT_LOGS + " where CORRELATION_TYPE='" + correlationType + '\'');
            assertTrue("Query on " + BUSINESS_EVENT_LOGS + " should have returned a ResultSet", m_rs.next());
            logId = m_rs.getString("LOG_ID");
            assertEquals(correlationType, m_rs.getString("CORRELATION_TYPE"));
            assertEquals("CK1", m_rs.getString("CORRELATION_KEY1"));
            assertEquals("CK2", m_rs.getString("CORRELATION_KEY2"));
            assertEquals("CK3", m_rs.getString("CORRELATION_KEY3"));
            assertEquals("STI", m_rs.getString("SCHEDULED_TASK_ID"));
            assertEquals("MI", m_rs.getString("MESSAGE_ID"));
            assertNotNull("Logged On value should be present", m_rs.getTimestamp("LOGGED_ON"));
            assertEquals("LB", m_rs.getString("LOGGED_BY"));
            assertEquals("PN", m_rs.getString("PROCESS_NAME"));
            assertEquals("SPN", m_rs.getString("SUB_PROCESS_NAME"));
            assertEquals("INFO", m_rs.getString("MESSAGE_TYPE"));
            assertEquals("test attachment", m_rs.getString("MESSAGE_TEXT"));
            assertEquals(JDBCAppenderWithAttachmentTest.class.getName(), m_rs.getString("SOURCE_CLASS"));
            assertNull(m_rs.getString("SOURCE_METHOD"));
            assertNull(m_rs.getString("SOURCE_LINE"));
            assertTrue(m_rs.getString("STACK_TRACE") == null || m_rs.getString("STACK_TRACE").trim().length() == 0);
            assertFalse("Query on " + BUSINESS_EVENT_LOGS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
            
            //Check the attachment
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + ATTACHMENTS + " where SERIALIZED_KEY='" + generateSerializedKey(logId) + '\'');
            assertTrue("Query on " + ATTACHMENTS + " should have returned a ResultSet", m_rs.next());
            assertEquals(generateSerializedKey(logId), m_rs.getString("SERIALIZED_KEY"));
            assertEquals(data.getClass().getName(), m_rs.getString("ORIGINAL_FILE_NAME"));
            assertEquals("E", m_rs.getString("ATTACHMENT_TYPE"));
            assertEquals(AttachmentService.CONTENT_TYPE_XML_ENCODER, m_rs.getString("CONTENT_TYPE"));
            assertNull(m_rs.getString("DESCRIPTION"));
            assertNull(m_rs.getString("REMARKS"));
            assertNull(m_rs.getString("SUPERCEDED_BY"));
            assertNotNull(m_rs.getTimestamp("CREATED_ON"));
            assertNull(m_rs.getString("CREATED_BY"));
            assertNull(m_rs.getTimestamp("LAST_CHANGED_ON"));
            assertNull(m_rs.getString("LAST_CHANGED_BY"));
            assertTrue(new String(m_rs.getBytes("DATA")).indexOf("someKey") > -1 && new String(m_rs.getBytes("DATA")).indexOf("someValue") > -1);
            assertFalse("Query on " + ATTACHMENTS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        } finally {
            clearAttachments(logId);
            clearLogs(correlationType);
        }
    }
    
    /** Tests the logging of an INFO message along with a File attachment.
     */
    public void testFileAttachment() throws Exception {
        // Create a temporary file for use as an attachment
        File data = File.createTempFile("testFileAttachment", ".txt");
        Writer writer = new BufferedWriter(new FileWriter(data));
        writer.write("test file attachment");
        writer.close();
        
        String correlationType = "testFileAttachment";
        String logId = null;
        try {
            createMDC(correlationType);
            MDC.put(AttachmentMeta.DATA, data);
            log.info("test attachment");
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + BUSINESS_EVENT_LOGS + " where CORRELATION_TYPE='" + correlationType + '\'');
            assertTrue("Query on " + BUSINESS_EVENT_LOGS + " should have returned a ResultSet", m_rs.next());
            logId = m_rs.getString("LOG_ID");
            assertEquals(correlationType, m_rs.getString("CORRELATION_TYPE"));
            assertEquals("CK1", m_rs.getString("CORRELATION_KEY1"));
            assertEquals("CK2", m_rs.getString("CORRELATION_KEY2"));
            assertEquals("CK3", m_rs.getString("CORRELATION_KEY3"));
            assertEquals("STI", m_rs.getString("SCHEDULED_TASK_ID"));
            assertEquals("MI", m_rs.getString("MESSAGE_ID"));
            assertNotNull("Logged On value should be present", m_rs.getTimestamp("LOGGED_ON"));
            assertEquals("LB", m_rs.getString("LOGGED_BY"));
            assertEquals("PN", m_rs.getString("PROCESS_NAME"));
            assertEquals("SPN", m_rs.getString("SUB_PROCESS_NAME"));
            assertEquals("INFO", m_rs.getString("MESSAGE_TYPE"));
            assertEquals("test attachment", m_rs.getString("MESSAGE_TEXT"));
            assertEquals(JDBCAppenderWithAttachmentTest.class.getName(), m_rs.getString("SOURCE_CLASS"));
            assertNull(m_rs.getString("SOURCE_METHOD"));
            assertNull(m_rs.getString("SOURCE_LINE"));
            assertTrue(m_rs.getString("STACK_TRACE") == null || m_rs.getString("STACK_TRACE").trim().length() == 0);
            assertFalse("Query on " + BUSINESS_EVENT_LOGS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
            
            //Check the attachment
            m_connection = AbstractDataWrapper.getConnection();
            m_stmt = m_connection.createStatement();
            m_rs = m_stmt.executeQuery("select * from " + ATTACHMENTS + " where SERIALIZED_KEY='" + generateSerializedKey(logId) + '\'');
            assertTrue("Query on " + ATTACHMENTS + " should have returned a ResultSet", m_rs.next());
            assertEquals(generateSerializedKey(logId), m_rs.getString("SERIALIZED_KEY"));
            assertEquals(data.getName(), m_rs.getString("ORIGINAL_FILE_NAME"));
            assertEquals("E", m_rs.getString("ATTACHMENT_TYPE"));
            assertNull(m_rs.getString("CONTENT_TYPE"));
            assertNull(m_rs.getString("DESCRIPTION"));
            assertNull(m_rs.getString("REMARKS"));
            assertNull(m_rs.getString("SUPERCEDED_BY"));
            assertNotNull(m_rs.getTimestamp("CREATED_ON"));
            assertNull(m_rs.getString("CREATED_BY"));
            assertNull(m_rs.getTimestamp("LAST_CHANGED_ON"));
            assertNull(m_rs.getString("LAST_CHANGED_BY"));
            assertEquals("test file attachment", new String(m_rs.getBytes("DATA")));
            assertFalse("Query on " + ATTACHMENTS + " should have returned only one ResultSet", m_rs.next());
            m_stmt.close();
            m_connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        } finally {
            data.deleteOnExit();
            clearAttachments(logId);
            clearLogs(correlationType);
        }
    }
    
}
