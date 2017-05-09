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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

package org.jaffa.components.attachment.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.jdbcplus.JDBCColumnHandler;
import org.apache.log4j.jdbcplus.JDBCIDHandler;
import org.apache.log4j.jdbcplus.JDBCLogColumn;
import org.apache.log4j.jdbcplus.JDBCLogType;
import org.apache.log4j.jdbcplus.JDBCLogger;
import org.apache.log4j.jdbcplus.JDBCPoolConnectionHandler;
import org.apache.log4j.spi.LoggingEvent;
import org.jaffa.components.attachment.domain.AttachmentMeta;
import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;
import org.jaffa.persistence.engines.jdbcengine.querygenerator.TypeDefs;
import org.jaffa.security.SecurityManager;

/** This class extends the functionality of the JDBCLogger.
 * If no attachment is provided, then it simply invokes the functionality of the base class.
 * If an attachment is provided in the MDC, then in addition to the regular log, it writes an attachment as well.
 */
public class JDBCLoggerWithAttachment extends JDBCLogger {
    
    // Accessors to private elements of the base class
    private static Field c_poolConnectionHandler = null;
    private static Field c_con = null;
    private static Field c_stmt = null;
    private static Field c_preparedSql = null;
    private static Field c_table = null;
    private static Field c_column_list = null;
    private static Field c_num = null;
    private static Field c_logcols = null;
    private static Field c_inc = null;
    private static Method c_quotedString = null;
    
    private static Field c_ignore = null;
    private static Field c_logtype = null;
    private static Field c_value = null;
    private static Field c_sqlType = null;
    private static Field c_name = null;
    private static Field c_idhandler = null;
    private static Field c_columnHandler = null;
    
    private static Method c_createBlob = null;
    static {
        try {
            c_poolConnectionHandler = JDBCLogger.class.getDeclaredField("poolConnectionHandler"); c_poolConnectionHandler.setAccessible(true);
            c_con = JDBCLogger.class.getDeclaredField("con"); c_con.setAccessible(true);
            c_stmt = JDBCLogger.class.getDeclaredField("stmt"); c_stmt.setAccessible(true);
            c_preparedSql = JDBCLogger.class.getDeclaredField("preparedSql"); c_preparedSql.setAccessible(true);
            c_table = JDBCLogger.class.getDeclaredField("table"); c_table.setAccessible(true);
            c_column_list = JDBCLogger.class.getDeclaredField("column_list"); c_column_list.setAccessible(true);
            c_num = JDBCLogger.class.getDeclaredField("num"); c_num.setAccessible(true);
            c_logcols = JDBCLogger.class.getDeclaredField("logcols"); c_logcols.setAccessible(true);
            c_inc = JDBCLogger.class.getDeclaredField("inc"); c_inc.setAccessible(true);
            c_quotedString = JDBCLogger.class.getDeclaredMethod("quotedString", String.class); c_quotedString.setAccessible(true);
            
            c_ignore = JDBCLogColumn.class.getDeclaredField("ignore"); c_ignore.setAccessible(true);
            c_logtype = JDBCLogColumn.class.getDeclaredField("logtype"); c_logtype.setAccessible(true);
            c_value = JDBCLogColumn.class.getDeclaredField("value"); c_value.setAccessible(true);
            c_sqlType = JDBCLogColumn.class.getDeclaredField("sqlType"); c_sqlType.setAccessible(true);
            c_name = JDBCLogColumn.class.getDeclaredField("name"); c_name.setAccessible(true);
            c_idhandler = JDBCLogColumn.class.getDeclaredField("idhandler"); c_idhandler.setAccessible(true);
            c_columnHandler = JDBCLogColumn.class.getDeclaredField("columnHandler"); c_columnHandler.setAccessible(true);
            
            c_createBlob = TypeDefs.class.getDeclaredMethod("createBlob", Connection.class, new byte[0].getClass()); c_createBlob.setAccessible(true);
        } catch (Exception e) {
            String errorMsg = "JDBCLoggerWithAttachment::{static-block}, Reflection of private elements of the base class has failed";
            LogLog.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }
    
    // Properties
    private String m_attachmentTable;
    private String m_attachmentMDCKey;
    private String m_engine;
    
    // Fields introduced in this class
    private String m_preparedSqlForAttachment = null;
    private PreparedStatement m_stmtForAttachment = null;
    
    
    /** Creates an instance.
     * @param attachmentTable The name of the attachment table.
     * @param attachmentMDCKey The key used to obtain the attachment from the MDC.
     * @param engine If 'oracle' is passed, then oracle-specific code will be used to set the attachment BLOB.
     */
    public JDBCLoggerWithAttachment(String attachmentTable, String attachmentMDCKey, String engine) {
        m_attachmentTable = attachmentTable;
        m_attachmentMDCKey = attachmentMDCKey;
        m_engine = engine;
    }
    
    /** This method is similar to the prepareConnection() method of the base class.
     * It invokes the custom createStatementsForAttachment() method to create a PreparedStatement that returns the auto-generated key.
     * @exception Exception Description of Exception
     */
    protected void prepareConnectionForAttachment() throws Exception {
        JDBCPoolConnectionHandler poolConnectionHandler = (JDBCPoolConnectionHandler) c_poolConnectionHandler.get(this);
        if (poolConnectionHandler != null)
            c_con.set(this, poolConnectionHandler.getConnection());
        
        if (!isConnected())
            throw new Exception("JDBCLogger::prepareConnection(), Given connection isn't connected to database!");
        
        try {
            createStatementsForAttachment();
        } catch (SQLException exception) {
            // if this fails, connection may be broken. Try again with new connection.
            if (poolConnectionHandler != null) {
                c_con.set(this, poolConnectionHandler.getConnection());
                createStatementsForAttachment();
            } else {
                throw exception;
            }
        }
    }
    
    /** This method is similar to the createStatement() method of the base class.
     * It creates a PreparedStatement that returns the auto-generated key.
     * It also creates a statement for the attachment.
     * @exception Exception Description of Exception
     */
    protected void createStatementsForAttachment() throws Exception {
        // Create a statement for logging an event
        if (c_preparedSql.get(this) == null) {
            StringBuilder sql = new StringBuilder("insert into ")
            .append(c_table.get(this))
            .append(" (")
            .append(c_column_list.get(this))
            .append(") values (");
            
            boolean addedColumn = false;
            for (int i = 0; i < ((Number) c_num.get(this)).intValue(); i++) {
                JDBCLogColumn logcol = (JDBCLogColumn) ((List) c_logcols.get(this)).get(i);
                // only required columns
                if (!((Boolean) c_ignore.get(logcol)).booleanValue()) {
                    if (addedColumn)
                        sql.append(", ");
                    if (((Number) c_logtype.get(logcol)).intValue() == JDBCLogType.ORACLE_SEQUENCE) {
                        sql.append(c_value.get(logcol).toString()).append(".NEXTVAL");
                    } else {
                        sql.append('?');
                    }
                    addedColumn = true;
                }
            }
            sql.append(')');
            LogLog.debug("JDBCLoggerWithAttachment::createStatementsForAttachment(), prepared statement: " + sql.toString());
            c_preparedSql.set(this, sql.toString());
        }
        Statement stmt = ((Connection) c_con.get(this)).prepareStatement((String) c_preparedSql.get(this));
        c_stmt.set(this, stmt);
        
        // Create a statement for the attachment
        if (m_preparedSqlForAttachment == null) {
            StringBuilder sqlForAttachment = new StringBuilder("insert into ")
            .append(m_attachmentTable)
            .append("(ATTACHMENT_ID, SERIALIZED_KEY, ORIGINAL_FILE_NAME, ATTACHMENT_TYPE, CONTENT_TYPE, CREATED_ON, CREATED_BY, DATA)")
            .append(" values(?, ?, ?, ?, ?, ?, ?, ?)");
            LogLog.debug("JDBCLoggerWithAttachment::createStatementsForAttachment(), prepared statement for attachment: " + sqlForAttachment.toString());
            m_preparedSqlForAttachment = sqlForAttachment.toString();
        }
        m_stmtForAttachment = ((Connection) c_con.get(this)).prepareStatement(m_preparedSqlForAttachment);
    }
    
    /** Invokes the append method of the base class.
     * Additionally it'll create an attachment, if provided in the MDC.
     * @param event the LoggingEvent to log
     * @param layout layout to use for message
     * @exception Exception Description of Exception
     */
    public void append(LoggingEvent event, Layout layout) throws Exception {
        // If attachment is not provided, simply invoke the append() method of the base class and return
        if (event.getMDC(m_attachmentMDCKey) == null) {
            super.append(event, layout);
            return;
        }
        
        // We'll assume that the event is being logged into the BusinessEventLog table, which has one auto-generated key.
        // The attachment record will require the auto-generated key to generate its serializedKey.
        // Hence we'll need to retrieve the auto-generated-key after the insertion of the BusniessEventLog record.
        // The following is a copy of the original method, but with the ability to obtain the auto-generated key.
        if (!ready())
            throw new Exception("JDBCLogger::append(), Not ready to append !");
        
        boolean errorOccurred = false;
        PreparedStatement prepStmt = null;
        try {
            prepareConnectionForAttachment();
            int paramIndex = 1;
            prepStmt = (PreparedStatement) c_stmt.get(this);
            String logId = null;
            for (int i = 0; i < ((Number) c_num.get(this)).intValue(); i++) {
                JDBCLogColumn logcol = (JDBCLogColumn) ((List) c_logcols.get(this)).get(i);
                if (((Boolean) c_ignore.get(logcol)).booleanValue())
                    continue;
                int logtype = ((Number) c_logtype.get(logcol)).intValue();
                int sqlType = ((Number) c_sqlType.get(logcol)).intValue();
                Object value = c_value.get(logcol);
                Object parameter = null;
                if (logtype == JDBCLogType.MSG) {
                    parameter = event.getRenderedMessage();
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setObject(paramIndex, parameter);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.LAYOUT) {
                    parameter = layout.format(event);
                    // default: use first pattern
                    int layoutIndex = ((Integer) value).intValue();
                    
                    // LAYOUT~x. use tokens in layout string
                    List tokenList = getTokenList((String) parameter);
                    String token = getTokenFromList(tokenList, layoutIndex);
                    
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setString(paramIndex, token);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.PRIO) {
                    parameter = event.getLevel().toString();
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setObject(paramIndex, parameter);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.CAT) {
                    parameter = event.getLoggerName();
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setObject(paramIndex, parameter);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.THREAD) {
                    parameter = event.getThreadName();
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setObject(paramIndex, parameter);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.ID) {
                    parameter = ((JDBCIDHandler) c_idhandler.get(logcol)).getID();
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setObject(paramIndex, parameter);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.STATIC) {
                    parameter = value;
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setObject(paramIndex, parameter);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.TIMESTAMP) {
                    parameter = new Timestamp((new java.util.Date()).getTime());
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setObject(paramIndex, parameter);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.INC) {
                    parameter = ((Number) c_inc.get(this));
                    c_inc.set(this, ((Number) parameter).longValue() + 1);
                    prepStmt.setObject(paramIndex, parameter);
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.DYNAMIC) {
                    parameter = ((JDBCColumnHandler) c_columnHandler.get(logcol))
                    .getObject(event, (String) c_table.get(this), (String) c_name.get(logcol));
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setObject(paramIndex, parameter);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.THROWABLE) {
                    // extract throwable information from loggingEvent if available
                    parameter = (String) c_quotedString.invoke(this, getThrowableRepresentationFromLoggingEvent(event));
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setObject(paramIndex, parameter);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.NDC) {
                    parameter = event.getNDC();
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setObject(paramIndex, parameter);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.MDC) {
                    Object mdcObject = event.getMDC(value.toString());
                    parameter = mdcObject == null ? null : mdcObject.toString();
                    if (parameter == null) {
                        prepStmt.setNull(paramIndex, sqlType);
                    } else {
                        prepStmt.setObject(paramIndex, parameter);
                    }
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.IPRIO) {
                    parameter = event.getLevel().toInt();
                    prepStmt.setObject(paramIndex, parameter);
                    paramIndex = paramIndex + 1;
                } else if (logtype == JDBCLogType.ORACLE_SEQUENCE) {
                    // do nothing
                }
                
                // Obtain the logId
                if (parameter != null && "log_id".equalsIgnoreCase((String) c_name.get(logcol)))
                    logId = parameter.toString();
            }
            prepStmt.executeUpdate();
            
            // Obtain the auto-generated key
            if (logId == null)
                throw new Exception("JDBCLogger::append(), LogId was not determined for the Log. Cannot add the attachment");
            prepStmt.close();
            
            // Now create the attachment
            Object attachment = event.getMDC(m_attachmentMDCKey);
            prepStmt = m_stmtForAttachment;
            IVoucherGenerator vg = VoucherGeneratorFactory.instance();
            vg.setConnection((Connection) c_con.get(this));
            vg.setDomainClassName(AttachmentMeta.getName());
            vg.setFieldName(AttachmentMeta.ATTACHMENT_ID);
            vg.setLabelToken(AttachmentMeta.META_ATTACHMENT_ID.getLabelToken());
            prepStmt.setString(1, vg.generate());
            prepStmt.setString(2, "org.jaffa.modules.messaging.domain.BusinessEventLog;" + logId);
            prepStmt.setString(3, AttachmentService.createAttachmentName(attachment));
            prepStmt.setString(4, "E");
            prepStmt.setString(5, AttachmentService.createContentType(attachment));
            prepStmt.setTimestamp(6, new Timestamp((new java.util.Date()).getTime()));
            if (SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
                prepStmt.setString(7, SecurityManager.getPrincipal().getName());
            else
                prepStmt.setNull(7, Types.VARCHAR);
            byte[] bytes = AttachmentService.createAttachmentData(attachment);
            if ("oracle".equalsIgnoreCase(m_engine)) {
                Blob blob = (Blob) c_createBlob.invoke(null, c_con.get(this), bytes);
                prepStmt.setBlob(8, blob);
            } else {
                InputStream stream = new BufferedInputStream(new ByteArrayInputStream(bytes));
                prepStmt.setBinaryStream(8, stream, bytes.length);
            }
            prepStmt.executeUpdate();
        } catch (Exception e) {
            // e.printStackTrace();
            errorOccurred = true;
            throw (e);
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                freeConnection();
            } catch (Exception exception) {
                if (errorOccurred) {
                    // consume exception
                } else {
                    throw (exception);
                }
            }
        }
    }
}

