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

import java.lang.reflect.Field;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.jdbcplus.JDBCAppender;

/** The JDBCAppender writes messages into a database via JDBC.
 * Multiple configuration options and parameters are supported.
 * Check the base class' javadoc for more details.
 *
 * <p>
 * This extension to the JDBCAppender provides the following settings
 * <ul>
 * <li>attachmentTable: The name of the attachment table.
 * <li>attachmentMDCKey: The key used to obtain the attachment from the MDC.
 * <li>engine: If 'oracle' is passed, then oracle-specific code will be used to set the attachment BLOB.
 * </ul>
 * An attachment record will be created whenever an attachment is passed in the MDC
 */
public class JDBCAppenderWithAttachment extends JDBCAppender {
    
    // Accessors to private elements of the base class
    private static Field c_sqlHandler = null;
    private static Field c_jlogger = null;
    private static Field c_configured = null;
    static {
        try {
            c_sqlHandler = JDBCAppender.class.getDeclaredField("sqlHandler"); c_sqlHandler.setAccessible(true);
            c_jlogger = JDBCAppender.class.getDeclaredField("jlogger"); c_jlogger.setAccessible(true);
            c_configured = JDBCAppender.class.getDeclaredField("configured"); c_configured.setAccessible(true);
        } catch (Exception e) {
            String errorMsg = "JDBCAppenderWithAttachment::{static-block}, Reflection of private elements of the base class has failed";
            LogLog.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }
    
    // Properties
    private String m_attachmentTable;
    private String m_attachmentMDCKey;
    private String m_engine;
    
    
    /** Getter for property attachmentTable.
     * @return Value of property attachmentTable.
     */
    public String getAttachmentTable() {
        return m_attachmentTable;
    }
    
    /** Setter for property attachmentTable.
     * @param attachmentTable New value of property attachmentTable.
     */
    public void setAttachmentTable(String attachmentTable) {
        if (attachmentTable != null) {
            attachmentTable = attachmentTable.trim();
            if (attachmentTable.length() == 0)
                attachmentTable = null;
        }
        m_attachmentTable = attachmentTable;
    }
    
    /** Getter for property attachmentMDCKey.
     * @return Value of property attachmentMDCKey.
     */
    public String getAttachmentMDCKey() {
        return m_attachmentMDCKey;
    }
    
    /** Setter for property attachmentMDCKey.
     * @param attachmentMDCKey New value of property attachmentMDCKey.
     */
    public void setAttachmentMDCKey(String attachmentMDCKey) {
        if (attachmentMDCKey != null) {
            attachmentMDCKey = attachmentMDCKey.trim();
            if (attachmentMDCKey.length() == 0)
                attachmentMDCKey = null;
        }
        m_attachmentMDCKey = attachmentMDCKey;
    }
    
    /** Getter for property engine.
     * @return Value of property engine.
     */
    public String getEngine() {
        return m_engine;
    }
    
    /** Setter for property engine.
     * @param engine New value of property engine.
     */
    public void setEngine(String engine) {
        if (engine != null) {
            engine = engine.trim();
            if (engine.length() == 0)
                engine = null;
        }
        m_engine = engine;
    }
    
    
    
    /** Overwrites the jlogger field on the base class with a JDBCLoggerWithAttachment instance if:
     * <ul>
     * <li> Attachment settings are provided in the configuration file
     * <li> The usePreparedStatements should be true
     * <li> The stored-procedure, sql and sqlHandler should not be specified.
     * </ul>
     * Finally it invokes the configure() method in the base class.
     * @return true if the configuration was successful.
     */
    protected boolean configure() {
        try {
            if (!((Boolean) c_configured.get(this)).booleanValue()) {
                // Customize the logger if attachmentTable and attachmentMDCKey are provided and if using prepared statements.
                // Ensure that stored-procedure, sql or sqlHandler are not being used.
                if (getAttachmentTable() != null && getAttachmentMDCKey() != null && isUsePreparedStatements()
                && getProcedure() == null && getSql() == null) {
                    // Use reflection to check the 'sqlHandler' field from the base class, since there is no getter
                    if (c_sqlHandler.get(this) == null) {
                        // Use reflection to set the 'jlogger' field on the base class, since there is no setter
                        c_jlogger.set(this, new JDBCLoggerWithAttachment(getAttachmentTable(), getAttachmentMDCKey(), getEngine()));
                        LogLog.debug("JDBCAppenderWithAttachment::configure(), Using JDBCLoggerWithAttachment");
                    }
                    
                }
                return super.configure();
            } else
                return true;
        } catch (Exception e) {
            String errorMsg = "JDBCAppenderWithAttachment::configure()";
            LogLog.error(errorMsg, e);
            errorHandler.error(errorMsg, e, 0);
            return false;
        }
    }
}

