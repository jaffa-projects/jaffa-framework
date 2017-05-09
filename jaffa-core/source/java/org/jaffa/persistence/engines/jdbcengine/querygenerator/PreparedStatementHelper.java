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

package org.jaffa.persistence.engines.jdbcengine.querygenerator;

import java.util.*;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.engines.jdbcengine.configservice.ClassMetaData;
import org.jaffa.persistence.engines.jdbcengine.variants.Variant;

/** This class has functions to return SQL Strings used in PreparedStatements.
 * These Strings are cached for efficiency purposes.
 */
public class PreparedStatementHelper {
    
    // the caches
    private static Map c_insertStatementCache = new WeakHashMap();
    private static Map c_updateStatementCache = new WeakHashMap();
    private static Map c_deleteStatementCache = new WeakHashMap();
    private static Map c_lockStatementCache = new WeakHashMap();
    private static Map c_findByPKStatementCache = new WeakHashMap();
    private static Map c_findByPKForUpdateStatementCache = new WeakHashMap();
    private static Map c_existsStatementCache = new WeakHashMap();
    
    // some constants used in the methods.
    private static final String INSERT_INTO = "INSERT INTO";
    private static final String VALUES = "VALUES";
    private static final String UPDATE = "UPDATE";
    private static final String SET = "SET";
    private static final String WHERE = "WHERE";
    private static final String AND = "AND";
    private static final String DELETE_FROM = "DELETE FROM";
    private static final String SELECT_1_FROM = "SELECT 1 FROM";
    private static final String SELECT = "SELECT";
    private static final String DOT_STAR = ".*";
    private static final String FROM = "FROM";
    private static final String SELECT_COUNT_STAR_FROM = "SELECT COUNT(*) \"" + Criteria.ID_FUNCTION_COUNT + "\" FROM";
    
    
    /** Returns a SQL String for use in PreparedStatements for inserting records into the table corresponding to the input ClassMetaData object.
     * This String is cached.
     * @param classMetaData the meta object, for which the PreparedStatement String is to be generated.
     * @return a SQL String for use in PreparedStatements for inserting records.
     */
    public static String getInsertPreparedStatementString(ClassMetaData classMetaData, String engineType) {
        String sql = (String) c_insertStatementCache.get(classMetaData.getClassName());
        if (sql == null) {
            StringBuffer buf1 = new StringBuffer(); buf1.append('('); // the fieldlist buffer
            StringBuffer buf2 = new StringBuffer(); buf2.append('('); // the Values buffer
            boolean first = true;
            for (Iterator i = classMetaData.getNonAutoKeyFieldNames().iterator(); i.hasNext();) {
                if (first) {
                    first = false;
                } else {
                    buf1.append(',');
                    buf2.append(',');
                }
                buf1.append(formatSqlName(classMetaData.getSqlName((String) i.next()), engineType));
                buf2.append('?');
            }
            for (Iterator i = classMetaData.getAttributes().iterator(); i.hasNext();) {
                if (first) {
                    first = false;
                } else {
                    buf1.append(',');
                    buf2.append(',');
                }
                buf1.append(formatSqlName(classMetaData.getSqlName((String) i.next()), engineType));
                buf2.append('?');
            }
            buf1.append(')');
            buf2.append(')');
            
            StringBuffer buf = new StringBuffer(INSERT_INTO);
            buf.append(' ');
            buf.append(classMetaData.getTable());
            buf.append(buf1);
            buf.append(' ');
            buf.append(VALUES);
            buf.append(buf2);
            synchronized(c_insertStatementCache) {
                //check again before inserting into cache
                sql = (String) c_insertStatementCache.get(classMetaData.getClassName());
                if (sql == null) {
                    sql = buf.toString();
                    c_insertStatementCache.put(classMetaData.getClassName(), sql);
                }
            }
        }
        return sql;
    }
    
    /** Returns a SQL String for use in PreparedStatements for updating records in the table corresponding to the input ClassMetaData object.
     * This String is cached.
     * @param classMetaData the meta object, for which the PreparedStatement String is to be generated.
     * @return a SQL String for use in PreparedStatements for updating records.
     */
    public static String getUpdatePreparedStatementString(ClassMetaData classMetaData, String engineType) {
        String sql = (String) c_updateStatementCache.get(classMetaData.getClassName());
        if (sql == null) {
            StringBuffer buf = new StringBuffer(UPDATE);
            buf.append(' ');
            buf.append(classMetaData.getTable());
            buf.append(' ');
            buf.append(SET);
            buf.append(' ');
            
            boolean first = true;
            for (Iterator i = classMetaData.getAttributes().iterator(); i.hasNext();) {
                if (first) {
                    first = false;
                } else {
                    buf.append(',');
                }
                buf.append(formatSqlName(classMetaData.getSqlName((String) i.next()), engineType));
                buf.append('=');
                buf.append('?');
            }
            
            buf.append(' ');
            buf.append(WHERE);
            buf.append(' ');
            first = true;
            for (Iterator i = classMetaData.getAllKeyFieldNames().iterator(); i.hasNext();) {
                if (first) {
                    first = false;
                } else {
                    buf.append(' ');
                    buf.append(AND);
                    buf.append(' ');
                }
                buf.append(formatSqlName(classMetaData.getSqlName((String) i.next()), engineType));
                buf.append('=');
                buf.append('?');
            }
            
            synchronized(c_updateStatementCache) {
                //check again before inserting into cache
                sql = (String) c_updateStatementCache.get(classMetaData.getClassName());
                if (sql == null) {
                    sql = buf.toString();
                    c_updateStatementCache.put(classMetaData.getClassName(), sql);
                }
            }
        }
        return sql;
    }
    
    /** Returns a SQL String for use in PreparedStatements for deleting records from the table corresponding to the input ClassMetaData object.
     * This String is cached.
     * @param classMetaData the meta object, for which the PreparedStatement String is to be generated.
     * @return a SQL String for use in PreparedStatements for deleting records.
     */
    public static String getDeletePreparedStatementString(ClassMetaData classMetaData, String engineType) {
        String sql = (String) c_deleteStatementCache.get(classMetaData.getClassName());
        if (sql == null) {
            StringBuffer buf = new StringBuffer(DELETE_FROM);
            buf.append(' ');
            buf.append(classMetaData.getTable());
            buf.append(' ');
            buf.append(WHERE);
            buf.append(' ');
            boolean first = true;
            for (Iterator i = classMetaData.getAllKeyFieldNames().iterator(); i.hasNext();) {
                if (first) {
                    first = false;
                } else {
                    buf.append(' ');
                    buf.append(AND);
                    buf.append(' ');
                }
                buf.append(formatSqlName(classMetaData.getSqlName((String) i.next()), engineType));
                buf.append('=');
                buf.append('?');
            }
            
            synchronized(c_deleteStatementCache) {
                //check again before inserting into cache
                sql = (String) c_deleteStatementCache.get(classMetaData.getClassName());
                if (sql == null) {
                    sql = buf.toString();
                    c_deleteStatementCache.put(classMetaData.getClassName(), sql);
                }
            }
        }
        return sql;
    }
    
    /** Returns a SQL String for use in PreparedStatements for locking records in the table corresponding to the input ClassMetaData object.
     * This String is cached.
     * @param classMetaData the meta object, for which the PreparedStatement String is to be generated.
     * @param engineType The engine type as defined in init.xml
     * @return a SQL String for use in PreparedStatements for locking records.
     */
    public static String getLockPreparedStatementString(ClassMetaData classMetaData, String engineType) {
        String sql = (String) c_lockStatementCache.get(classMetaData.getClassName());
        if (sql == null) {
            StringBuffer buf = new StringBuffer(SELECT_1_FROM);
            buf.append(' ');
            buf.append(classMetaData.getTable());
            buf.append(' ');
            // Added for supplying Locking 'Hints' used by SqlServer
            buf.append(Variant.getProperty(engineType, Variant.PROP_LOCK_CONSTRUCT_IN_FROM_SELECT_STATEMENT));
            buf.append(' ');
            buf.append(WHERE);
            buf.append(' ');
            
            boolean first = true;
            for (Iterator i = classMetaData.getAllKeyFieldNames().iterator(); i.hasNext();) {
                if (first) {
                    first = false;
                } else {
                    buf.append(AND);
                    buf.append(' ');
                }
                buf.append(formatSqlName(classMetaData.getSqlName((String) i.next()), engineType));
                buf.append('=');
                buf.append('?');
                buf.append(' ');
            }
            buf.append(Variant.getProperty(engineType, Variant.PROP_LOCK_CONSTRUCT_IN_SELECT_STATEMENT));
            
            synchronized(c_lockStatementCache) {
                //check again before inserting into cache
                sql = (String) c_lockStatementCache.get(classMetaData.getClassName());
                if (sql == null) {
                    sql = buf.toString();
                    c_lockStatementCache.put(classMetaData.getClassName(), sql);
                }
            }
        }
        return sql;
    }
    
    /** Returns a SQL String for use in PreparedStatements for retrieving records by their primary key.
     * @param locking the locking strategy for a query.
     * @param classMetaData the meta object, for which the PreparedStatement String is to be generated.
     * @param engineType The engine type as defined in init.xml
     * @return a SQL String for use in PreparedStatements for retrieving records by their primary key.
     */
    public static String getFindByPKPreparedStatementString(int locking, ClassMetaData classMetaData, String engineType) {
        String sql = null;
        if (locking == Criteria.LOCKING_PARANOID && classMetaData.isLockable())
            sql = (String) c_findByPKForUpdateStatementCache.get(classMetaData.getClassName());
        else
            sql = (String) c_findByPKStatementCache.get(classMetaData.getClassName());
        
        if (sql == null) {
            StringBuffer whereBuf = new StringBuffer();
            for (Iterator i = classMetaData.getAllKeyFieldNames().iterator(); i.hasNext();) {
                if (whereBuf.length() > 0)
                    whereBuf.append(' ').append(AND).append(' ');
                String attributeName = (String) i.next();
                whereBuf.append(formatSqlName(classMetaData.getSqlName(attributeName), engineType)).append('=').append('?');
            }
            
            StringBuffer buf = new StringBuffer(SELECT)
            .append(' ')
            .append(classMetaData.getTable()).append(DOT_STAR)
            .append(' ')
            .append(FROM)
            .append(' ')
            .append(classMetaData.getTable());
            
            // Added for supplying Locking 'Hints' used by MS-Sql-Server
            if (locking == Criteria.LOCKING_PARANOID && classMetaData.isLockable())
                buf.append(' ').append(Variant.getProperty(engineType, Variant.PROP_LOCK_CONSTRUCT_IN_FROM_SELECT_STATEMENT));
            
            buf.append(' ')
            .append(WHERE)
            .append(' ')
            .append(whereBuf);
            if (locking == Criteria.LOCKING_PARANOID && classMetaData.isLockable())
                buf.append(' ').append(Variant.getProperty(engineType, Variant.PROP_LOCK_CONSTRUCT_IN_SELECT_STATEMENT));
            
            //check again before inserting into cache
            if (locking == Criteria.LOCKING_PARANOID && classMetaData.isLockable()) {
                synchronized(c_findByPKForUpdateStatementCache) {
                    sql = (String) c_findByPKForUpdateStatementCache.get(classMetaData.getClassName());
                    if (sql == null) {
                        sql = buf.toString();
                        c_findByPKForUpdateStatementCache.put(classMetaData.getClassName(), sql);
                    }
                }
            } else {
                synchronized(c_findByPKStatementCache) {
                    sql = (String) c_findByPKStatementCache.get(classMetaData.getClassName());
                    if (sql == null) {
                        sql = buf.toString();
                        c_findByPKStatementCache.put(classMetaData.getClassName(), sql);
                    }
                }
            }
        }
        return sql;
    }
    
    /** Returns a SQL String for use in PreparedStatements for checking the existence of records by their primary key.
     * @param classMetaData the meta object, for which the PreparedStatement String is to be generated.
     * @param engineType The engine type as defined in init.xml
     * @return a SQL String for use in PreparedStatements for existence checks.
     */
    public static String getExistsPreparedStatementString(ClassMetaData classMetaData, String engineType) {
        String sql = (String) c_existsStatementCache.get(classMetaData.getClassName());
        if (sql == null) {
            StringBuffer buf = new StringBuffer(SELECT_COUNT_STAR_FROM)
            .append(' ')
            .append(classMetaData.getTable())
            .append(' ')
            .append(WHERE);
            
            boolean first = true;
            for (Iterator i = classMetaData.getAllKeyFieldNames().iterator(); i.hasNext();) {
                if (first)
                    first = false;
                else
                    buf.append(' ').append(AND);
                String attributeName = (String) i.next();
                buf.append(' ').append(formatSqlName(classMetaData.getSqlName(attributeName), engineType)).append('=').append('?');
            }
            synchronized(c_existsStatementCache) {
                //check again before inserting into cache
                sql = (String) c_existsStatementCache.get(classMetaData.getClassName());
                if (sql == null) {
                    sql = buf.toString();
                    c_existsStatementCache.put(classMetaData.getClassName(), sql);
                }
            }
        }
        return sql;
    }
  
    private static String formatSqlName(String sqlName, String engineType) {
        if (sqlName != null && "mssqlserver".equals(engineType)) {
            sqlName = "[" + sqlName + "]";
        }
        return sqlName;
    }
    
}

