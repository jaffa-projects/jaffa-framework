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

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import org.jaffa.persistence.Criteria;

/** This class has helper routines for passing values in DML strings and PreparedStatements.
 * There are routines to extract appropriate objects from ResultSets and CallableStatements.
 */
public class DataTranslator {

    // no need to create an instance
    private DataTranslator() {
    }

    /** Returns the string to be used in SQL statements, appropriate for the datatype.
     * @param object an object for which the SQL statement is to be generated.
     * @param typeName The sqlType defined in the mapping file.
     * @param engineType The engine type as defined in init.xml
     * @return the string to be used in SQL statements, appropriate for the datatype.
     * @throws IOException if any error occurs while extracting the String from the input object.
     */
    public static String getDml(Object object, String typeName, String engineType) throws IOException {
        return TypeDefs.getTypeDefinitionBySqlTypeName(typeName).getDml(object, engineType);
    }

    /** Returns the string to be used in SQL statements, appropriate for the datatype.
     * @param object an object for which the SQL statement is to be generated.
     * @param typeName The sqlType defined in the mapping file.
     * @param engineType The engine type as defined in init.xml
     * @return the string to be used in SQL statements, appropriate for the datatype.
     * @throws IOException if any error occurs while extracting the String from the input object.
     */
    public static String getLikeDml(Object object, String typeName, String engineType) throws IOException {
        return TypeDefs.getTypeDefinitionBySqlTypeName(typeName).getLikeDml(object, Criteria.RELATIONAL_LIKE, engineType);
    }

    /** Returns the string to be used in SQL statements, appropriate for the datatype.
     * @param object an object for which the SQL statement is to be generated.
     * @param typeName The sqlType defined in the mapping file.
     * @param engineType The engine type as defined in init.xml
     * @return the string to be used in SQL statements, appropriate for the datatype.
     * @throws IOException if any error occurs while extracting the String from the input object.
     */
    public static String getBeginsWithDml(Object object, String typeName, String engineType) throws IOException {
        return TypeDefs.getTypeDefinitionBySqlTypeName(typeName).getLikeDml(object, Criteria.RELATIONAL_BEGINS_WITH, engineType);
    }

    /** Returns the string to be used in SQL statements, appropriate for the datatype.
     * @param object an object for which the SQL statement is to be generated.
     * @param typeName The sqlType defined in the mapping file.
     * @param engineType The engine type as defined in init.xml
     * @return the string to be used in SQL statements, appropriate for the datatype.
     * @throws IOException if any error occurs while extracting the String from the input object.
     */
    public static String getEndsWithDml(Object object, String typeName, String engineType) throws IOException {
        return TypeDefs.getTypeDefinitionBySqlTypeName(typeName).getLikeDml(object, Criteria.RELATIONAL_ENDS_WITH, engineType);
    }

    /** Sets a parameter in the PreparedStatement.
     * @param pstmt The PreparedStatement.
     * @param parameterIndex The index of the parameter that is to be set.
     * @param value The object to be assigned to the parameter.
     * @param typeName The sqlType defined in the mapping file.
     * @param engineType The engine type as defined in init.xml
     * @throws SQLException if a database access error occurs.
     */
    public static void setAppObject(PreparedStatement pstmt, int parameterIndex
    , Object value, String typeName, String engineType) throws SQLException {
        TypeDefs.getTypeDefinitionBySqlTypeName(typeName).setAppObject(pstmt, parameterIndex, value, engineType);
    }

    /** Gets a parameter value for use in a store procedure call.
     * @param value The object to be assigned to the parameter.
     * @param typeName The sqlType defined in the mapping file.
     * @param engineType The engine type as defined in init.xml
     * @return teh value for use as an in parameter for stored procedures
     */
    public static Object getAppValue(Object value, String typeName, String engineType) {
        return TypeDefs.getTypeDefinitionBySqlTypeName(typeName).getAppValue(value, typeName, engineType);
    }

    /**
     * Gets the object value for the Jaffa typed value
     * @param value The object to be assigned to the parameter.
     * @param typeName The sqlType defined in the mapping file.
     * @param engineType The engine type as defined in init.xml
     * @return the object value for the Jaffa typed value
     */
    public static Object getAppObject(Object value, String typeName, String engineType) throws IOException {
        return TypeDefs.getTypeDefinitionBySqlTypeName(typeName).getHibernateDml(value, engineType);
    }

    /** Gets a parameter from the ResultSet.
     * @param rs The ResultSet.
     * @param columnName The name of the parameter.
     * @param engineType The engine type as defined in init.xml
     * @return the parameter.
     * @param typeName The sqlType defined in the mapping file.
     * @throws SQLException if a database access error occurs.
     * @throws IOException if any error occurs in reading the data from the database.
     */
    public static Object getAppObject(ResultSet rs, String columnName
    , String typeName, String engineType) throws SQLException, IOException {
        return TypeDefs.getTypeDefinitionBySqlTypeName(typeName).getAppObject(rs, columnName, engineType);
    }

    /** Gets a parameter from the CallableStatement.
     * @param cstmt The CallableStatement.
     * @param parameterIndex The index of the parameter.
     * @param engineType The engine type as defined in init.xml
     * @return the parameter.
     * @param typeName The sqlType defined in the mapping file.
     * @throws SQLException if a database access error occurs.
     * @throws IOException if any error occurs in reading the data from the database.
     */
    public static Object getAppObject(CallableStatement cstmt, int parameterIndex
    , String typeName, String engineType) throws SQLException, IOException {
        return TypeDefs.getTypeDefinitionBySqlTypeName(typeName).getAppObject(cstmt, parameterIndex, engineType);
    }

    /** This method returns a Type constant defined in java.sql.Types, based on the sqlType defined in the mapping file.
     * This throws a runtime exception IllegalArgumentException, in case the typename is not supported.
     * @param typeName The sqlType defined in the mapping file.
     * @param engineType The engine type as defined in init.xml
     * @return a Type constant defined in java.sql.Types.
     */
    public static int getSqlType(String typeName, String engineType) {
        return TypeDefs.getSqlType(typeName, engineType);
    }
}
