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

import org.jaffa.persistence.engines.jdbcengine.configservice.ClassMetaData;
import org.jaffa.persistence.engines.jdbcengine.configservice.ConfigurationService;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.engines.jdbcengine.util.MoldingService;
import org.jaffa.persistence.engines.jdbcengine.proxy.PersistentInstanceFactory;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.util.Iterator;
import org.jaffa.persistence.engines.jdbcengine.variants.Variant;

/** This class has functions to return SQL Strings used in Statements.
 */
public class StatementHelper {
    
    // some constants used in the methods.
    private static final String INSERT_INTO = "INSERT INTO";
    private static final String VALUES = "VALUES";
    private static final String UPDATE = "UPDATE";
    private static final String SET = "SET";
    private static final String WHERE = "WHERE";
    private static final String AND = "AND";
    private static final String DELETE_FROM = "DELETE FROM";
    private static final String SELECT_1_FROM = "SELECT 1 FROM";
    private static final String IS_NULL = "IS NULL";
    
    
    /** Returns a SQL String for use in Statements for inserting records into the table corresponding to the input Persistent object.
     * @param object The object to be inserted.
     * @param engineType The engine type as defined in init.xml
     * @throws IllegalAccessException if the accessor Method for an attribute enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the accessor method for an attribute throws an exception.
     * @throws IOException if any error occurs while extracting the value for an attribute.
     * @return a SQL String for use in Statements for inserting records.
     */
    public static String getInsertStatementString(IPersistent object, String engineType)
    throws IllegalAccessException, InvocationTargetException, IOException {
        ClassMetaData classMetaData = getClassMetaData(object);
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
            String attributeName = (String) i.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, attributeName);
            buf1.append(formatSqlName(classMetaData.getSqlName(attributeName), engineType));
            buf2.append(DataTranslator.getDml(value, classMetaData.getSqlType(attributeName), engineType));
        }
        for (Iterator i = classMetaData.getAttributes().iterator(); i.hasNext();) {
            if (first) {
                first = false;
            } else {
                buf1.append(',');
                buf2.append(',');
            }
            String attributeName = (String) i.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, attributeName);
            buf1.append(formatSqlName(classMetaData.getSqlName(attributeName), engineType));
            buf2.append(DataTranslator.getDml(value, classMetaData.getSqlType(attributeName), engineType));
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
        return buf.toString();
    }
    
    /** Returns a SQL String for use in Statements for updating records in the table corresponding to the input Persistent object.
     * @param object The object to be updated.
     * @param engineType The engine type as defined in init.xml
     * @throws IllegalAccessException if the accessor Method for an attribute enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the accessor method for an attribute throws an exception.
     * @throws IOException if any error occurs while extracting the value for an attribute.
     * @return a SQL String for use in Statements for updating records.
     */
    public static String getUpdateStatementString(IPersistent object, String engineType)
    throws IllegalAccessException, InvocationTargetException, IOException {
        ClassMetaData classMetaData = getClassMetaData(object);
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
            String attributeName = (String) i.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, attributeName);
            buf.append(formatSqlName(classMetaData.getSqlName(attributeName), engineType));
            buf.append('=');
            buf.append(DataTranslator.getDml(value, classMetaData.getSqlType(attributeName), engineType));
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
            String attributeName = (String) i.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, attributeName);
            buf.append(formatSqlName(classMetaData.getSqlName(attributeName), engineType));
            if (value == null)
                buf.append(' ').append(IS_NULL);
            else
                buf.append('=').append(DataTranslator.getDml(value, classMetaData.getSqlType(attributeName), engineType));
        }
        return buf.toString();
    }
    
    /** Returns a SQL String for use in Statements for deleting records from the table corresponding to the input Persistent object.
     * @param object The object to be deleted.
     * @param engineType The engine type as defined in init.xml
     * @throws IllegalAccessException if the accessor Method for an attribute enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the accessor method for an attribute throws an exception.
     * @throws IOException if any error occurs while extracting the value for an attribute.
     * @return a SQL String for use in Statements for deleting records.
     */
    public static String getDeleteStatementString(IPersistent object, String engineType)
    throws IllegalAccessException, InvocationTargetException, IOException {
        ClassMetaData classMetaData = getClassMetaData(object);
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
            String attributeName = (String) i.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, attributeName);
            buf.append(formatSqlName(classMetaData.getSqlName(attributeName), engineType));
            if (value == null)
                buf.append(' ').append(IS_NULL);
            else
                buf.append('=').append(DataTranslator.getDml(value, classMetaData.getSqlType(attributeName), engineType));
        }
        return buf.toString();
    }
    
    /** Returns a SQL String for use in Statements for locking records in the table corresponding to the input Persistent object.
     * @param object The object to be locked.
     * @param engineType The engine type as defined in init.xml
     * @throws IllegalAccessException if the accessor Method for an attribute enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the accessor method for an attribute throws an exception.
     * @throws IOException if any error occurs while extracting the value for an attribute.
     * @return a SQL String for use in Statements for locking records.
     */
    public static String getLockStatementString(IPersistent object, String engineType)
    throws IllegalAccessException, InvocationTargetException, IOException {
        ClassMetaData classMetaData = getClassMetaData(object);
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
            String attributeName = (String) i.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, attributeName);
            buf.append(formatSqlName(classMetaData.getSqlName(attributeName), engineType));
            if (value == null)
                buf.append(' ').append(IS_NULL);
            else
                buf.append('=').append(DataTranslator.getDml(value, classMetaData.getSqlType(attributeName), engineType));
            buf.append(' ');
        }
        buf.append(Variant.getProperty(engineType, Variant.PROP_LOCK_CONSTRUCT_IN_SELECT_STATEMENT));
        return buf.toString();
    }
    
    
    private static ClassMetaData getClassMetaData(IPersistent object) {
        return ConfigurationService.getInstance().getMetaData(PersistentInstanceFactory.getActualPersistentClass(object).getName());
    }
    
    private static String formatSqlName(String sqlName, String engineType) {
        if (sqlName != null && "mssqlserver".equals(engineType)) {
            sqlName = "[" + sqlName + "]";
        }
        return sqlName;
    }
}

