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

package org.jaffa.components.attachment.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import junit.framework.Test;
import org.apache.log4j.Logger;
import org.jaffa.test.junit.AbstractDataWrapper;
import org.jaffa.util.URLHelper;

/** This class has the methods for one time setup/cleanup of form printint data,
 * before all the tests executed by the suite utilising this class.
 *
 * @author  PaulE
 */
public class JDBCAppenderWithAttachmentWrapper extends AbstractDataWrapper {
    
    private static final Logger log = Logger.getLogger(JDBCAppenderWithAttachmentWrapper.class);
    static final String TABLE_PREFIX = "Z";
    
    /** The constructor.
     * @param test The Test class, for which the Wrapper will be utilised.
     */
    public JDBCAppenderWithAttachmentWrapper(Test test) {
        super(test);
    }
    
    protected void setUpData(Connection connection)
    throws Exception {
        String engine = (String) getDatabaseInfo().get(ENGINE);
        create(connection, engine, "J_BUSINESS_EVENT_LOGS");
        create(connection, engine, "J_ATTACHMENTS");
    }
    
    protected void tearDownData(Connection connection)
    throws Exception {
        try {
            executeSql(connection, "drop table " + TABLE_PREFIX + "J_ATTACHMENTS");
        } catch (Exception e) {
            // do nothing
        }
        try {
            executeSql(connection, "drop table " + TABLE_PREFIX + "J_BUSINESS_EVENT_LOGS");
        } catch (Exception e) {
            // do nothing
        }
        String engine = (String) getDatabaseInfo().get(ENGINE);
        if ("oracle".equalsIgnoreCase(engine)) {
            try {
                executeSql(connection, "drop sequence S_" + TABLE_PREFIX + "J_ATTACHMENTS_1");
            } catch (Exception e) {
                // do nothing
            }
            try {
                executeSql(connection, "drop sequence S_" + TABLE_PREFIX + "J_BUSINESS_EVENT_LOGS_1");
            } catch (Exception e) {
                // do nothing
            }
        }
    }
    
    
    private void create(Connection connection, String engine, String tableName) throws Exception {
        executeSql(connection, filter(loadCreateTableScript(engine, tableName), tableName));
    }
    
    private String filter(String sql, String tableName) {
        // prefix the tableName
        sql = sql.replaceAll(tableName, TABLE_PREFIX + tableName);
        
        // Remove the trailing ';' or '/' character, since they do not work with JDBC
        sql = sql.trim();
        if (sql.length() > 1 && (sql.endsWith(";") || sql.endsWith("/")))
            sql = sql.substring(0, sql.length() - 1);
        
        return sql;
    }
    
    private String loadCreateTableScript(String engine, String tableName) throws IOException, SQLException {
        String sql = null;
        if (tableName.equals("J_BUSINESS_EVENT_LOGS")) {
            // The create table script for J_BUSINESS_EVENT_LOGS is in another module, and which is not in the dependency list
            // So for now, we'll simply hard-code the script
            // @todo: Replace the following with an external script
            StringBuilder buf = new StringBuilder();
            if ("mysql".equalsIgnoreCase(engine)) {
                buf.append("CREATE TABLE J_BUSINESS_EVENT_LOGS (\n")
                .append("        LOG_ID          VARCHAR(80) NOT NULL,\n")
                .append("        CORRELATION_TYPE          VARCHAR(20),\n")
                .append("        CORRELATION_KEY1          VARCHAR(100),\n")
                .append("        CORRELATION_KEY2          VARCHAR(100),\n")
                .append("        CORRELATION_KEY3          VARCHAR(100),\n")
                .append("        SCHEDULED_TASK_ID          VARCHAR(20),\n")
                .append("        MESSAGE_ID          VARCHAR(20),\n")
                .append("        LOGGED_ON          DATETIME,\n")
                .append("        LOGGED_BY          VARCHAR(20),\n")
                .append("        PROCESS_NAME          VARCHAR(20),\n")
                .append("        SUB_PROCESS_NAME          VARCHAR(20),\n")
                .append("        MESSAGE_TYPE          VARCHAR(20),\n")
                .append("        MESSAGE_TEXT          TEXT,\n")
                .append("        SOURCE_CLASS          VARCHAR(255),\n")
                .append("        SOURCE_METHOD          VARCHAR(100),\n")
                .append("        SOURCE_LINE          INT,\n")
                .append("        STACK_TRACE          TEXT,\n")
                .append("    CONSTRAINT J_BUSINESS_EVENT_LOGSP1 PRIMARY KEY(LOG_ID)\n")
                .append(") TYPE=InnoDB\n");
            } else if ("oracle".equalsIgnoreCase(engine)) {
                buf.append("CREATE TABLE \"J_BUSINESS_EVENT_LOGS\"(\n")
                .append("        \"LOG_ID\"          VARCHAR2(80) NOT NULL,\n")
                .append("        \"CORRELATION_TYPE\"          VARCHAR2(20),\n")
                .append("        \"CORRELATION_KEY1\"          VARCHAR2(100),\n")
                .append("        \"CORRELATION_KEY2\"          VARCHAR2(100),\n")
                .append("        \"CORRELATION_KEY3\"          VARCHAR2(100),\n")
                .append("        \"SCHEDULED_TASK_ID\"          VARCHAR2(20),\n")
                .append("        \"MESSAGE_ID\"          VARCHAR2(20),\n")
                .append("        \"LOGGED_ON\"          TIMESTAMP,\n")
                .append("        \"LOGGED_BY\"          VARCHAR2(20),\n")
                .append("        \"PROCESS_NAME\"          VARCHAR2(20),\n")
                .append("        \"SUB_PROCESS_NAME\"          VARCHAR2(20),\n")
                .append("        \"MESSAGE_TYPE\"          VARCHAR2(20),\n")
                .append("        \"MESSAGE_TEXT\"          VARCHAR2(4000),\n")
                .append("        \"SOURCE_CLASS\"          VARCHAR2(255),\n")
                .append("        \"SOURCE_METHOD\"          VARCHAR2(100),\n")
                .append("        \"SOURCE_LINE\"          NUMBER(8),\n")
                .append("        \"STACK_TRACE\"          VARCHAR2(4000),\n")
                .append("    CONSTRAINT \"J_BUSINESS_EVENT_LOGSP1\" PRIMARY KEY(\"LOG_ID\")\n")
                .append(")\n")
                .append("/\n");
            }
            sql = buf.toString();
        } else {
            sql = loadFile("install/" + engine + "/tables/" + tableName + ".sql");
        }
        return sql;
    }
    
    private String loadFile(String resourceName) throws IOException {
        StringBuffer buf = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(URLHelper.getInputStream(resourceName)));
        String line = null;
        while ((line = reader.readLine()) != null) {
            buf.append(line);
            buf.append('\n');
        }
        return buf.toString();
    }
    
}
