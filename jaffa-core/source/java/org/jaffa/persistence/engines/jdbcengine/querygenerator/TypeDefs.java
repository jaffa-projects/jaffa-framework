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

import org.apache.log4j.Logger;
import java.util.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.Connection;

import org.jaffa.datatypes.*;
import org.jaffa.datatypes.Currency;
import org.jaffa.datatypes.Formatter;
import org.jaffa.persistence.engines.jdbcengine.IStoredProcedure;
import org.jaffa.util.StringHelper;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.engines.jdbcengine.datasource.DataSourceFactory;
import org.jaffa.persistence.engines.jdbcengine.variants.Variant;
import org.jaffa.session.ContextManagerFactory;


/** This class encapsulates the TypeDefinition classes for the various datatypes.
 * Use the getTypeDefintionBySqlTypeName() method to get a TypeDefintion object appropriate for a datatype.
 * An appropriate TypeDefinition object can be used to
 * 1) Generate DML strings
 * 2) Generate DML strings for LIKE clauses
 * 3) Set the appropriate value on a PreparedStatement
 * 4) Get the appropriate object from a ResultSet
 * 5) Get the appropriate object from a CallableStatement
 */
public class TypeDefs {
    private static final Logger log = Logger.getLogger(TypeDefs.class);
    private static final String DO_NOT_TRIM_RULE_NAME = "jaffa.persistence.jdbcengine.doNotTrimStrings";
    
    private static final String BOOLEAN_BIT = "BOOLEAN_BIT";
    private static final String BOOLEAN_TF = "BOOLEAN_TF";
    private static final String BOOLEAN_YN = "BOOLEAN_YN";
    private static final String BOOLEAN_10 = "BOOLEAN_10";
    
    private static final String VARCHAR = "VARCHAR";
    private static final String BIGINT = "BIGINT";
    private static final String DOUBLE = "DOUBLE";
    private static final String LONGVARCHAR = "LONGVARCHAR";
    
    
    // all the possible TypeDefinitions pre-instantiated
    private static TypeDefinition c_nullTypeDefinition = new NullTypeDefinition();
    private static TypeDefinition c_nullStringTypeDefinition = new NullStringTypeDefinition();
    private static TypeDefinition c_stringTypeDefinition = new StringTypeDefinition();
    private static TypeDefinition c_integerTypeDefinition = new IntegerTypeDefinition();
    private static TypeDefinition c_decimalTypeDefinition = new DecimalTypeDefinition();
    private static TypeDefinition c_booleanTypeDefinition = new BooleanTypeDefinition();
    private static TypeDefinition c_booleanAsBitTypeDefinition = new BooleanAsBitTypeDefinition();
    private static TypeDefinition c_booleanAsStringTfTypeDefinition = new BooleanAsStringTypeDefinition("'T'", "'F'", "T", "F");
    private static TypeDefinition c_booleanAsStringYnTypeDefinition = new BooleanAsStringTypeDefinition("'Y'", "'N'", "Y", "N");
    private static TypeDefinition c_booleanAsString10TypeDefinition = new BooleanAsStringTypeDefinition("'1'", "'0'", "1", "0");
    private static TypeDefinition c_dateOnlyTypeDefinition = new DateOnlyTypeDefinition();
    private static TypeDefinition c_dateTimeTypeDefinition = new DateTimeTypeDefinition();
    private static TypeDefinition c_currencyTypeDefinition = new CurrencyTypeDefinition();
    private static TypeDefinition c_rawTypeDefinition = new RawTypeDefinition();
    private static TypeDefinition c_longStringTypeDefinition = new LongStringTypeDefinition();
    private static TypeDefinition c_longRawTypeDefinition = new LongRawTypeDefinition();
    private static TypeDefinition c_clobTypeDefinition = new ClobTypeDefinition();
    private static TypeDefinition c_blobTypeDefinition = new BlobTypeDefinition();
    
    // supported sql types that can be defined in the mapping files
    private static Map SQL_MAPPINGS = new HashMap();
    static {
        SQL_MAPPINGS.put(VARCHAR, c_stringTypeDefinition);
        SQL_MAPPINGS.put(Defaults.STRING, c_stringTypeDefinition);
        SQL_MAPPINGS.put(BIGINT, c_integerTypeDefinition);
        SQL_MAPPINGS.put(Defaults.INTEGER, c_integerTypeDefinition);
        SQL_MAPPINGS.put(DOUBLE, c_decimalTypeDefinition);
        SQL_MAPPINGS.put(Defaults.DECIMAL, c_decimalTypeDefinition);
        SQL_MAPPINGS.put(Defaults.BOOLEAN, c_booleanTypeDefinition);
        SQL_MAPPINGS.put(BOOLEAN_BIT, c_booleanAsBitTypeDefinition);
        SQL_MAPPINGS.put(BOOLEAN_TF, c_booleanAsStringTfTypeDefinition);
        SQL_MAPPINGS.put(BOOLEAN_YN, c_booleanAsStringYnTypeDefinition);
        SQL_MAPPINGS.put(BOOLEAN_10, c_booleanAsString10TypeDefinition);
        SQL_MAPPINGS.put(Defaults.DATEONLY, c_dateOnlyTypeDefinition);
        SQL_MAPPINGS.put(Defaults.DATETIME, c_dateTimeTypeDefinition);
        SQL_MAPPINGS.put(Defaults.CURRENCY, c_currencyTypeDefinition);
        SQL_MAPPINGS.put(Defaults.RAW, c_rawTypeDefinition);
        SQL_MAPPINGS.put(LONGVARCHAR, c_longStringTypeDefinition);
        SQL_MAPPINGS.put(Defaults.LONG_STRING, c_longStringTypeDefinition);
        SQL_MAPPINGS.put(Defaults.LONG_RAW, c_longRawTypeDefinition);
        SQL_MAPPINGS.put(Defaults.CLOB, c_clobTypeDefinition);
        SQL_MAPPINGS.put(Defaults.BLOB, c_blobTypeDefinition);
    }
    
    // mapping sql types to the constants defined in java.sql.Types
    private static Map SQL_TYPES = new HashMap();
    static {
        SQL_TYPES.put(VARCHAR, new Integer(Types.VARCHAR));
        SQL_TYPES.put(Defaults.STRING, new Integer(Types.VARCHAR));
        SQL_TYPES.put(BIGINT, new Integer(Types.BIGINT));
        SQL_TYPES.put(Defaults.INTEGER, new Integer(Types.BIGINT));
        SQL_TYPES.put(DOUBLE, new Integer(Types.DOUBLE));
        SQL_TYPES.put(Defaults.DECIMAL, new Integer(Types.DOUBLE));
        SQL_TYPES.put(Defaults.BOOLEAN, null); //this will be either Types.BIT or Types.VARCHAR
        SQL_TYPES.put(BOOLEAN_BIT, new Integer(Types.BIT));
        SQL_TYPES.put(BOOLEAN_TF, new Integer(Types.VARCHAR));
        SQL_TYPES.put(BOOLEAN_YN, new Integer(Types.VARCHAR));
        SQL_TYPES.put(BOOLEAN_10, new Integer(Types.VARCHAR));
        SQL_TYPES.put(Defaults.DATEONLY, new Integer(Types.DATE));
        SQL_TYPES.put(Defaults.DATETIME, new Integer(Types.TIMESTAMP));
        SQL_TYPES.put(Defaults.CURRENCY, new Integer(Types.DOUBLE));
        SQL_TYPES.put(Defaults.RAW, new Integer(Types.VARBINARY));
        SQL_TYPES.put(LONGVARCHAR, new Integer(Types.LONGVARCHAR));
        SQL_TYPES.put(Defaults.LONG_STRING, new Integer(Types.LONGVARCHAR));
        SQL_TYPES.put(Defaults.LONG_RAW, new Integer(Types.LONGVARBINARY));
        SQL_TYPES.put(Defaults.CLOB, new Integer(Types.CLOB));
        SQL_TYPES.put(Defaults.BLOB, new Integer(Types.BLOB));
    }
    
    // some constants used internally
    private static final String LIKE_QUOTE = "|";
    private static final String LIKE_QUOTE_QUOTE = "||";
    private static final String LIKE_CHAR = "%";
    private static final String LIKE_QUOTE_CHAR = "|%";
    private static final String LIKE_CHAR1 = "_";
    private static final String LIKE_QUOTE_CHAR1 = "|_";
    private static final String LIKE_ESCAPE_STRING = " ESCAPE '|'";
    private static final String SINGLE_QUOTE = "'";
    private static final String TWO_SINGLE_QUOTES = "''";
    
    
    /** This method returns a TypeDefinition object based on the sqlType defined in the mapping file.
     * This throws a runtime exception IllegalArgumentException, in case the typename is not supported.
     * @param typeName The sqlType defined in the mapping file.
     * @return a TypeDefinition object based on the sqlType defined in the mapping file.
     */
    public static TypeDefinition getTypeDefinitionBySqlTypeName(String typeName) {
        TypeDefinition output = (TypeDefinition) SQL_MAPPINGS.get(typeName);
        if (output == null) {
            String str = "Unsupported SqlType " + typeName;
            log.error(str);
            throw new IllegalArgumentException(str);
        }
        return output;
    }
    
    /** This method returns a Type constant defined in java.sql.Types, based on the sqlType defined in the mapping file.
     * This throws a runtime exception IllegalArgumentException, in case the typename is not supported.
     * @param typeName The sqlType defined in the mapping file.
     * @param engineType The engine type as defined in init.xml
     * @return a Type constant defined in java.sql.Types.
     */
    public static int getSqlType(String typeName, String engineType) {
        // For "Boolean", get the default packing code, which is database dependent, and then use that to get the actual SqlType
        if (typeName.equals(Defaults.BOOLEAN))
            typeName = getDefaultPackingCodeForBoolean(engineType);
        
        if (IStoredProcedure.CURSOR.equals(typeName)) {
            if ("oracle".equalsIgnoreCase(engineType))
                return getSqlTypeForOracleCursor();
            else
                return Types.OTHER;
        } else {
            Integer output = (Integer) SQL_TYPES.get(typeName);
            if (output == null) {
                String str = "Unsupported SqlType " + typeName;
                log.error(str);
                throw new IllegalArgumentException(str);
            }
            return output.intValue();
        }
    }
    
    
    /** This is the base class for the type definitions of all the possible datatypes.*/
    public abstract static class TypeDefinition {
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public abstract String getDml(Object object, String engineType) throws IOException;

        /** Returns the object to be used in SQL statements, appropriate for hibernate.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the object to be used in SQL statements, appropriate for hibernate.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public abstract Object getHibernateDml(Object object, String engineType) throws IOException;

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public abstract String getLikeDml(Object object, int operator, String engineType) throws IOException;
        
        /** Sets a parameter in the PreparedStatement.
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @param engineType The engine type as defined in init.xml
         * @throws SQLException if a database access error occurs.
         */
        public abstract void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType) throws SQLException;

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @throws SQLException if a database access error occurs.
         */
        public abstract Object getAppValue(Object value, String typeName, String engineType);

        /** Gets a parameter from the ResultSet.
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @param engineType The engine type as defined in init.xml
         * @return the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public abstract Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException;
        
        /** Gets a parameter from the CallableStatement.
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @param engineType The engine type as defined in init.xml
         * @return the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public abstract Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException;
    }
    
    /** This is the TypeDefinition class for Null datatypes. */
    public static class NullTypeDefinition extends TypeDefinition {
        private static final String NULL = "null";
        private static final String BLANK_STRING = "";
        
        // do not allow others to instantiate this class
        private NullTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException {
            return NULL;
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            return null;
        }
        
        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            return getLikeDmlHelper(BLANK_STRING, operator);
        }
        
        /** Sets a parameter in the PreparedStatement. This method will never be used.
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @param engineType The engine type as defined in init.xml
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            // nothing to do
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            return value;
        }

        /** Gets a parameter from the ResultSet. This method will never be used.
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @param engineType The engine type as defined in init.xml
         * @return the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            return null;
        }
        
        /** Gets a parameter from the CallableStatement. This method will never be used.
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @param engineType The engine type as defined in init.xml
         * @return the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            return null;
        }
    }
    
    
    /** This is the TypeDefinition class for Null datatypes. */
    public static class NullStringTypeDefinition extends NullTypeDefinition {
        private static final String SPACE_IN_QUOTES = "' '";
        
        // do not allow others to instantiate this class
        private NullStringTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException {
            return SPACE_IN_QUOTES;
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            return null;
        }
    }
    
    
    /** This is the TypeDefinition class for Boolean (java.lang.Boolean) datatypes.
     * It delegates the calls to either BooleanAsBitTypeDefinition or BooleanAsStringXxTypeDefinition, based on the variant setting for "defaultPackingCodeForBoolean" property.
     */
    public static class BooleanTypeDefinition extends TypeDefinition {
        
        // do not allow others to instantiate this class
        private BooleanTypeDefinition() {
        }
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException {
            return getTypeDefinitionBySqlTypeName(getDefaultPackingCodeForBoolean(engineType)).getDml(object, engineType);
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            return getDml(object, engineType).replace("'", "");
        }

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            return getTypeDefinitionBySqlTypeName(getDefaultPackingCodeForBoolean(engineType)).getLikeDml(object, operator, engineType);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            getTypeDefinitionBySqlTypeName(getDefaultPackingCodeForBoolean(engineType)).setAppObject(pstmt, parameterIndex, value, engineType);
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            return getTypeDefinitionBySqlTypeName(getDefaultPackingCodeForBoolean(engineType)).getAppValue(value, typeName, engineType);
        }

        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            return getTypeDefinitionBySqlTypeName(getDefaultPackingCodeForBoolean(engineType)).getAppObject(rs, columnName, engineType);
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            return getTypeDefinitionBySqlTypeName(getDefaultPackingCodeForBoolean(engineType)).getAppObject(cstmt, parameterIndex, engineType);
        }
    }
    
    /** This is the TypeDefinition class for Boolean (java.lang.Boolean) datatypes.
     * The underlying field will be assumed to be binary and will deal with 1 or 0.
     */
    public static class BooleanAsBitTypeDefinition extends TypeDefinition {
        private static final String DML_TRUE = "'1'";
        private static final String DML_FALSE = "'0'";
        private static final String NO_QUOTE_TRUE = "1";
        private static final String NO_QUOTE_FALSE = "0";
        
        // do not allow others to instantiate this class
        private BooleanAsBitTypeDefinition() {
        }
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return ((Boolean) object).booleanValue() ? DML_TRUE : DML_FALSE;
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return ((Boolean) object).booleanValue() ? NO_QUOTE_TRUE : NO_QUOTE_FALSE;
        }

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else
                return getLikeDmlHelper(((Boolean) object).booleanValue() ? NO_QUOTE_TRUE : NO_QUOTE_FALSE, operator);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value == null)
                pstmt.setObject(parameterIndex, value, Types.BIT);
            else {
                if (!(value instanceof Boolean))
                    value = DataTypeMapper.instance().map(value, Boolean.class);
                pstmt.setBoolean(parameterIndex, ((Boolean) value).booleanValue());
            }
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            if (value == null)
                return value;
            else {
                if (!(value instanceof Boolean)) {
                    value = DataTypeMapper.instance().map(value, Boolean.class);
                }

                return ((Boolean) value).booleanValue();
            }
        }

        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            boolean value = rs.getBoolean(columnName);
            if (rs.wasNull())
                return null;
            else
                return new Boolean(value);
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            boolean value = cstmt.getBoolean(parameterIndex);
            if (cstmt.wasNull())
                return null;
            else
                return new Boolean(value);
        }
    }
    
    
    /** This is the TypeDefinition class for Boolean (java.lang.Boolean) datatypes.
     * The underlying field will be assumed to be a character field storing T/F, Y/N or 1/0.
     */
    public static class BooleanAsStringTypeDefinition extends TypeDefinition {
        private String m_dml_true;
        private String m_dml_false;
        private String m_no_quote_true;
        private String m_no_quote_false;
        
        // do not allow others to instantiate this class
        private BooleanAsStringTypeDefinition(String dml_true, String dml_false,
                String no_quote_true, String no_quote_false) {
            m_dml_true = dml_true;
            m_dml_false = dml_false;
            m_no_quote_true = no_quote_true;
            m_no_quote_false = no_quote_false;
        }
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullStringTypeDefinition.getDml(null, engineType);
            else {
                if (!(object instanceof Boolean))
                    object = DataTypeMapper.instance().map(object, Boolean.class);
                return ((Boolean) object) ? m_dml_true : m_dml_false;
            }
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullStringTypeDefinition.getDml(null, engineType);
            else {
                if (!(object instanceof Boolean))
                    object = DataTypeMapper.instance().map(object, Boolean.class);
                return ((Boolean) object) ? m_no_quote_true : m_no_quote_false;
            }
        }

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else {
                if (!(object instanceof Boolean))
                    object = DataTypeMapper.instance().map(object, Boolean.class);
                return getLikeDmlHelper(((Boolean) object) ? m_no_quote_true : m_no_quote_false, operator);
            }
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value == null)
                pstmt.setObject(parameterIndex, value, Types.VARCHAR);
            else {
                if (!(value instanceof Boolean))
                    value = DataTypeMapper.instance().map(value, Boolean.class);
                if ( ((Boolean) value).booleanValue() )
                    pstmt.setObject(parameterIndex, m_no_quote_true, Types.VARCHAR);
                else
                    pstmt.setObject(parameterIndex, m_no_quote_false, Types.VARCHAR);
            }
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            if (value == null) {
                return value;
            } else {
                if (!(value instanceof Boolean)) {
                    value = DataTypeMapper.instance().map(value, Boolean.class);
                }
                if ( ((Boolean) value).booleanValue() ) {
                    return m_no_quote_true;
                } else {
                    return m_no_quote_false;
                }
            }
        }
        
        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            return Parser.parseBoolean( rs.getString(columnName) );
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            return Parser.parseBoolean( cstmt.getString(parameterIndex) );
        }
    }
    
    
    /** This is the TypeDefinition class for String (java.lang.String) datatypes.*/
    public static class StringTypeDefinition extends TypeDefinition {
        
        // do not allow others to instantiate this class
        private StringTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullStringTypeDefinition.getDml(null, engineType);
            else
                return '\'' + getDmlHelper(object.toString()) + '\'';
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullStringTypeDefinition.getDml(null, engineType);
            else
                return getDmlHelper(object.toString());
        }

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else
                return getLikeDmlHelper(object.toString(), operator);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value != null && !(value instanceof String))
                value = DataTypeMapper.instance().map(value, String.class);
            pstmt.setString(parameterIndex, (String) value);
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            return value;
        }
        
        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            String str = rs.getString(columnName);
            if (str != null) {
                //Trim the String by default. Do not trim if the rule is set to true!
                Boolean doNotTrim = Parser.parseBoolean((String) ContextManagerFactory.instance().getProperty(DO_NOT_TRIM_RULE_NAME));
                if (doNotTrim == null || !doNotTrim.booleanValue())
                    str = str.trim();
                if (str.length() == 0)
                    str = null;
            }
            return str;
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            String str = cstmt.getString(parameterIndex);
            if (str != null) {
                //Trim the String by default. Do not trim if the rule is set to true!
                Boolean doNotTrim = Parser.parseBoolean((String) ContextManagerFactory.instance().getProperty(DO_NOT_TRIM_RULE_NAME));
                if (doNotTrim == null || !doNotTrim.booleanValue())
                    str = str.trim();
                if (str.length() == 0)
                    str = null;
            }
            return str;
        }
    }
    
    
    /** This is the TypeDefinition class for Integer (java.lang.Long) datatypes. */
    public static class IntegerTypeDefinition extends TypeDefinition {
        
        // do not allow others to instantiate this class
        private IntegerTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException  {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return object.toString();
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return object;
        }
        
        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else
                return getLikeDmlHelper(object.toString(), operator);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value != null && !(value instanceof Long))
                value = DataTypeMapper.instance().map(value, Long.class);
            pstmt.setObject(parameterIndex, value, Types.BIGINT);
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            return value;
        }
        
        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            long value = rs.getLong(columnName);
            if (rs.wasNull())
                return null;
            else
                return new Long(value);
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            long value = cstmt.getLong(parameterIndex);
            if (cstmt.wasNull())
                return null;
            else
                return new Long(value);
        }
    }
    
    
    /** This is the TypeDefinition class for Decimal (java.lang.Double) datatypes. */
    public static class DecimalTypeDefinition extends TypeDefinition {
        
        // do not allow others to instantiate this class
        private DecimalTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException  {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return object.toString();
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return object;
        }

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else
                return getLikeDmlHelper(object.toString(), operator);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value != null && !(value instanceof Double))
                value = DataTypeMapper.instance().map(value, Double.class);
            pstmt.setObject(parameterIndex, value, Types.DOUBLE);
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            return value;
        }

        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            double value = rs.getDouble(columnName);
            if (rs.wasNull())
                return null;
            else
                return new Double(value);
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            double value = cstmt.getDouble(parameterIndex);
            if (cstmt.wasNull())
                return null;
            else
                return new Double(value);
        }
    }
    
    
    /** This is the TypeDefinition class for DateOnly (org.jaffa.datatypes.DateOnly) datatypes. */
    public static class DateOnlyTypeDefinition extends TypeDefinition {
        private static final String FUNC = "to_date";
        private static final String FORMAT = "yyyy-MM-dd";
        
        private static final String PREFIX = FUNC + "('";
        private static final String SUFFIX = "','" + FORMAT + "')";
        
        // do not allow others to instantiate this class
        private DateOnlyTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException  {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else if (Parser.parseBoolean( Variant.getProperty(engineType, Variant.PROP_USE_TO_DATE_SQL_FUNCTION) ).booleanValue())
                return PREFIX + Formatter.format(object, FORMAT) + SUFFIX;
            else
                return '\'' + Formatter.format(object, FORMAT) + '\'';
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null) {
                return c_nullTypeDefinition.getDml(null, engineType);
            } else {
                return new java.sql.Date(((IDateBase) object).getUtilDate().getTime());
            }
        }

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else
                return getLikeDmlHelper(Formatter.format(object), operator);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value == null)
                pstmt.setObject(parameterIndex, value, Types.DATE);
            else {
                if (!(value instanceof DateOnly))
                    value = DataTypeMapper.instance().map(value, DateOnly.class);
                pstmt.setObject(parameterIndex, ((DateOnly) value).sqlDate(), Types.DATE);
            }
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            if (value == null) {
                return value;
            } else {
                if (!(value instanceof DateOnly)) {
                    value = DataTypeMapper.instance().map(value, DateOnly.class);
                }
                return ((DateOnly) value).sqlDate();
            }
        }
        
        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            Date sqlDate = rs.getDate(columnName);
            if (sqlDate != null)
                return new DateOnly(sqlDate);
            else
                return null;
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            Date sqlDate = cstmt.getDate(parameterIndex);
            if (sqlDate != null)
                return new DateOnly(sqlDate);
            else
                return null;
        }
    }
    
    
    /** This is the TypeDefinition class for DateTime (org.jaffa.datatypes.DateTime) datatypes. */
    public static class DateTimeTypeDefinition extends TypeDefinition {
        private static final String FUNC = "to_date";
        private static final String JAVA_FORMAT = "yyyy-MM-dd HH:mm:ss";
        private static final String SQL_FORMAT = "yyyy-MM-dd hh24:mi:ss";
        
        private static final String PREFIX = FUNC + "('";
        private static final String SUFFIX = "','" + SQL_FORMAT + "')";
        
        // do not allow others to instantiate this class
        private DateTimeTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException  {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else if (Parser.parseBoolean( Variant.getProperty(engineType, Variant.PROP_USE_TO_DATE_SQL_FUNCTION) ).booleanValue())
                return PREFIX + Formatter.format(object, JAVA_FORMAT) + SUFFIX;
            else
                return '\'' + Formatter.format(object, JAVA_FORMAT) + '\'';
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null) {
                return c_nullTypeDefinition.getDml(null, engineType);
            } else {
                return new java.sql.Timestamp((((IDateBase) object).getUtilDate().getTime() / 1000) * 1000);
            }
        }

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else
                return getLikeDmlHelper(Formatter.format(object), operator);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value == null)
                pstmt.setObject(parameterIndex, value, Types.TIMESTAMP);
            else {
                if (!(value instanceof DateTime))
                    value = DataTypeMapper.instance().map(value, DateTime.class);
                // Oracle's DATE datatype stores only till second-level accuracy.
                // The following hack will remove the milliseconds from the input value.
                // Remove this hack once we start using Oracle's TIMESTAMP datatype,
                // in which case modify the JAVA_FORMAT and SQL_FORMAT above accordingly
                DateTime dt = (DateTime) value;
                Timestamp timestamp = new Timestamp(dt.milli() > 0 ? dt.timeInMillis() - dt.milli() : dt.timeInMillis());
                pstmt.setObject(parameterIndex, timestamp, Types.TIMESTAMP);
            }
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            if (value == null) {
                return value;
            } else {
                if (!(value instanceof DateTime))
                    value = DataTypeMapper.instance().map(value, DateTime.class);
                // Oracle's DATE datatype stores only till second-level accuracy.
                // The following hack will remove the milliseconds from the input value.
                // Remove this hack once we start using Oracle's TIMESTAMP datatype,
                // in which case modify the JAVA_FORMAT and SQL_FORMAT above accordingly
                DateTime dt = (DateTime) value;
                return new Timestamp(dt.milli() > 0 ? dt.timeInMillis() - dt.milli() : dt.timeInMillis());
            }
        }
        
        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            Timestamp timestamp = rs.getTimestamp(columnName);
            if (timestamp != null)
                return new DateTime(timestamp);
            else
                return null;
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            Timestamp timestamp = cstmt.getTimestamp(parameterIndex);
            if (timestamp != null)
                return new DateTime(timestamp);
            else
                return null;
        }
    }
    
    
    /** This is the TypeDefinition class for Currency (org.jaffa.datatypes.Currency) datatypes. */
    public static class CurrencyTypeDefinition extends TypeDefinition {
        
        // do not allow others to instantiate this class
        private CurrencyTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException  {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return ((Currency) object).getValue().toString();
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return ((Currency) object).getValue();
        }

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else
                return getLikeDmlHelper(((Currency) object).getValue().toString(), operator);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value == null)
                pstmt.setObject(parameterIndex, value, Types.DOUBLE);
            else {
                if (!(value instanceof Currency))
                    value = DataTypeMapper.instance().map(value, Currency.class);
                pstmt.setObject(parameterIndex, ((Currency) value).getValue(), Types.DOUBLE);
            }
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            if (value == null) {
                return null;
            } else {
                if (!(value instanceof Currency)) {
                    value = DataTypeMapper.instance().map(value, Currency.class);
                }

                return  ((Currency) value).getValue();
            }
        }
        
        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            double value = rs.getDouble(columnName);
            if (rs.wasNull())
                return null;
            else
                return new Currency(value);
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            double value = cstmt.getDouble(parameterIndex);
            if (cstmt.wasNull())
                return null;
            else
                return new Currency(value);
        }
    }
    
    
    /** This is the TypeDefinition class for Raw (byte[]) datatypes. */
    public static class RawTypeDefinition extends TypeDefinition {
        
        // do not allow others to instantiate this class
        private RawTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException  {
            if (object == null) {
                return c_nullTypeDefinition.getDml(null, engineType);
            } else {
                return '\'' + new String((byte[]) object) + '\'';
            }
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null) {
                return c_nullTypeDefinition.getDml(null, engineType);
            } else {
                return new String((byte[]) object);
            }
        }
        
        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null) {
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            } else {
                return getLikeDmlHelper(new String((byte[]) object), operator);
            }
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value != null && !(value instanceof byte[]))
                value = DataTypeMapper.instance().map(value, byte[].class);
            pstmt.setBytes(parameterIndex, (byte[]) value);
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            if (value != null && !(value instanceof byte[])) {
                return DataTypeMapper.instance().map(value, byte[].class);
            }

            return value;
        }
        
        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            return rs.getBytes(columnName);
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            return cstmt.getBytes(parameterIndex);
        }
    }
    
    
    /** This is the TypeDefinition class for LongString (java.io.Reader) datatypes. */
    public static class LongStringTypeDefinition extends TypeDefinition {
        
        // do not allow others to instantiate this class
        private LongStringTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * The String should not 4000 characters, else an exception will be thrown. Use PreparedStatements when dealing with long fields.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException  {
            if (object == null)
                return c_nullStringTypeDefinition.getDml(null, engineType);
            else
                return '\'' + getDmlHelper(object.toString()) + '\'';
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullStringTypeDefinition.getDml(null, engineType);
            else
                return getDmlHelper(object.toString());
        }
        
        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else
                return getLikeDmlHelper(object.toString(), operator);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value != null) {
                if (!(value instanceof String))
                    value = DataTypeMapper.instance().map(value, String.class);
                String str = (String) value;
                Reader reader = new BufferedReader(new StringReader(str));
                pstmt.setCharacterStream(parameterIndex, reader, str.length());
            } else
                pstmt.setNull(parameterIndex, getSqlType(Defaults.LONG_STRING, engineType));
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            if (value != null) {
                if (!(value instanceof String)) {
                    return DataTypeMapper.instance().map(value, String.class);
                }

                return value;
            }

            return value;
        }
        
        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            return StringHelper.getString(rs.getCharacterStream(columnName));
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            return cstmt.getString(parameterIndex);
        }
    }
    
    
    /** This is the TypeDefinition class for LongRaw  (java.io.InputStream) datatypes. */
    public static class LongRawTypeDefinition extends TypeDefinition {
        
        // do not allow others to instantiate this class
        private LongRawTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * The String should not exceed 4000 bytes, else an Exception is throws. Use PreparedStatements when dealing with long fields.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException  {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return '\'' + new String((byte[]) object) + '\'';
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return new String((byte[]) object);
        }

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else
                return getLikeDmlHelper(new String((byte[]) object), operator);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value != null) {
                if (!(value instanceof byte[]))
                    value = DataTypeMapper.instance().map(value, byte[].class);
                byte[] bytes = (byte[]) value;
                InputStream stream = new BufferedInputStream(new ByteArrayInputStream(bytes));
                pstmt.setBinaryStream(parameterIndex, stream, bytes.length);
            } else
                pstmt.setNull(parameterIndex, getSqlType(Defaults.LONG_RAW, engineType));
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            return value;
        }

        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            return rs.getBytes(columnName);
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            return cstmt.getBytes(parameterIndex);
        }
    }
    
    /** This is the TypeDefinition class for Clob (java.io.Reader) datatypes. */
    public static class ClobTypeDefinition extends TypeDefinition {
        
        // do not allow others to instantiate this class
        private ClobTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * The String should not 4000 characters, else an exception will be thrown. Use PreparedStatements when dealing with clob fields.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException  {
            if (object == null)
                return c_nullStringTypeDefinition.getDml(null, engineType);
            else
                return '\'' + getDmlHelper(object.toString()) + '\'';
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullStringTypeDefinition.getDml(null, engineType);
            else
                return getDmlHelper(object.toString());
        }

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else
                return getLikeDmlHelper(object.toString(), operator);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value != null) {
                if (!(value instanceof String))
                    value = DataTypeMapper.instance().map(value, String.class);
                if ("oracle".equalsIgnoreCase(engineType) && !supportsStdLob(pstmt)) {
                    Clob clob = createClob(pstmt.getConnection(), (String) value);
                    pstmt.setClob(parameterIndex, clob);
                } else {
                    String str = (String) value;
                    Reader reader = new BufferedReader(new StringReader(str));
                    pstmt.setCharacterStream(parameterIndex, reader, str.length());
                }
            } else
                pstmt.setNull(parameterIndex, getSqlType(Defaults.CLOB, engineType));
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            return value;
        }
        
        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            Clob clob = rs.getClob(columnName);
            if (clob != null)
                return StringHelper.getString(clob.getCharacterStream());
            else
                return null;
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            Clob clob = cstmt.getClob(parameterIndex);
            if (clob != null)
                return StringHelper.getString(clob.getCharacterStream());
            else
                return null;
        }
    }
    
    
    /** This is the TypeDefinition class for Blob (java.io.InputStream) datatypes. */
    public static class BlobTypeDefinition extends TypeDefinition {
        
        // do not allow others to instantiate this class
        private BlobTypeDefinition() {}
        
        /** Returns the string to be used in SQL statements, appropriate for the datatype.
         * The String should not exceed 4000 bytes, else an Exception is throws. Use PreparedStatements when dealing with blob fields.
         * @param object an object for which the SQL statement is to be generated.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getDml(Object object, String engineType) throws IOException  {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return '\'' + new String((byte[]) object) + '\'';
        }

        /**
         * {@inheritDoc}
         */
        public Object getHibernateDml(Object object, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getDml(null, engineType);
            else
                return new String((byte[]) object);
        }

        /** Returns the string to be used in 'like' SQL statements, appropriate for the datatype.
         * @param object an object for which the SQL statement is to be generated.
         * @param operator can be any of the Criteria operators - LIKE, BEGINS_WITH or ENDS_WITH.
         * @param engineType The engine type as defined in init.xml
         * @return the string to be used in SQL statements, appropriate for the datatype.
         * @throws IOException if any error occurs while extracting the String from the input object.
         */
        public String getLikeDml(Object object, int operator, String engineType) throws IOException {
            if (object == null)
                return c_nullTypeDefinition.getLikeDml(null, operator, engineType);
            else
                return getLikeDmlHelper(new String((byte[]) object), operator);
        }
        
        /** Sets a parameter in the PreparedStatement.
         * @param engineType The engine type as defined in init.xml
         * @param pstmt The PreparedStatement.
         * @param parameterIndex The index of the parameter that is to be set.
         * @param value The object to be assigned to the parameter.
         * @throws SQLException if a database access error occurs.
         */
        public void setAppObject(PreparedStatement pstmt, int parameterIndex, Object value, String engineType)
        throws SQLException {
            if (value != null) {
                if (!(value instanceof byte[]))
                    value = DataTypeMapper.instance().map(value, byte[].class);
                if ("oracle".equalsIgnoreCase(engineType) && !supportsStdLob(pstmt)) {
                    Blob blob = createBlob(pstmt.getConnection(), (byte[]) value);
                    pstmt.setBlob(parameterIndex, blob);
                } else {
                    byte[] bytes = (byte[]) value;
                    InputStream stream = new BufferedInputStream(new ByteArrayInputStream(bytes));
                    pstmt.setBinaryStream(parameterIndex, stream, bytes.length);
                }
            } else
                pstmt.setNull(parameterIndex, getSqlType(Defaults.BLOB, engineType));
        }

        /** Gets a parameter value for use in a store procedure call.
         * @param value The object to be assigned to the parameter.
         * @param typeName The sqlType defined in the mapping file.
         * @param engineType The engine type as defined in init.xml
         * @return teh value for use as an in parameter for stored procedures
         */
        public Object getAppValue(Object value, String typeName, String engineType) {
            return value;
        }

        /** Gets a parameter from the ResultSet.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param rs The ResultSet.
         * @param columnName The name of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(ResultSet rs, String columnName, String engineType) throws SQLException, IOException {
            Blob blob = rs.getBlob(columnName);
            if (blob != null)
                return blob.getBytes(1, (int) blob.length());
            else
                return null;
        }
        
        /** Gets a parameter from the CallableStatement.
         * @return the parameter.
         * @param engineType The engine type as defined in init.xml
         * @param cstmt The CallableStatement.
         * @param parameterIndex The index of the parameter.
         * @throws SQLException if a database access error occurs.
         * @throws IOException if any error occurs in reading the data from the database.
         */
        public Object getAppObject(CallableStatement cstmt, int parameterIndex, String engineType) throws SQLException, IOException {
            Blob blob = cstmt.getBlob(parameterIndex);
            if (blob != null)
                return blob.getBytes(1, (int) blob.length());
            else
                return null;
        }
    }
    
    private static String getLikeDmlHelper(String object, int operator) {
        StringBuffer buf = new StringBuffer();
        buf.append('\'');
        switch(operator) {
            case Criteria.RELATIONAL_LIKE :
                buf.append('%');
                break;
            case Criteria.RELATIONAL_BEGINS_WITH :
                break;
            case Criteria.RELATIONAL_ENDS_WITH :
                buf.append('%');
                break;
            default :
                String str = "Illegal operator passed for generating the 'like' DML string - " + operator;
                log.error(str);
                throw new IllegalArgumentException(str);
        }
        
        // replace a single-quote with 2 single-quotes( ' with '' )
        object = getDmlHelper(object);
        
        boolean useEscape = false;
        if (object.indexOf('%') < 0 && object.indexOf('_') < 0) {
            buf.append(object);
        } else {
            // Let '|' be the escape character
            
            // step1 - replace '|' with '||'
            object = StringHelper.replace(object, LIKE_QUOTE, LIKE_QUOTE_QUOTE);
            
            // step2 - replace '%' with '|%'
            object = StringHelper.replace(object, LIKE_CHAR, LIKE_QUOTE_CHAR);
            
            // step3 - replace '_' with '|_'
            object = StringHelper.replace(object, LIKE_CHAR1, LIKE_QUOTE_CHAR1);
            
            
            buf.append(object);
            useEscape = true;
        }
        
        switch(operator) {
            case Criteria.RELATIONAL_LIKE :
                buf.append('%');
                break;
            case Criteria.RELATIONAL_BEGINS_WITH :
                buf.append('%');
                break;
        }
        buf.append('\'');
        
        if (useEscape)
            buf.append(LIKE_ESCAPE_STRING);
        
        return buf.toString();
    }
    
    /** replace a single-quote with 2 single-quotes( ' with '' ) */
    private static String getDmlHelper(String object) {
        if (object != null)
            return StringHelper.replace(object, SINGLE_QUOTE, TWO_SINGLE_QUOTES);
        else
            return null;
    }
    
    /** returns the default packing code for boolean fields */
    private static String getDefaultPackingCodeForBoolean(String engineType) {
        return Variant.getProperty(engineType, Variant.PROP_DEFAULT_PACKING_CODE_FOR_BOOLEAN);
    }
    
    /** This is an oracle-centric method to create a CLOB object containing the value passed in. */
    private static Clob createClob(Connection connection, String value) throws SQLException {
        try {
            connection = DataSourceFactory.getUnderlyingConnection(connection);
            
            // The following code uses reflection, so that we don't need ORACLE jar while compiling !!
            //CLOB tempClob = CLOB.createTemporary(connection, true, CLOB.DURATION_SESSION);
            //tempClob.open(CLOB.MODE_READWRITE);
            //Writer tempClobWriter = tempClob.getCharacterOutputStream();
            //tempClobWriter.write((String) value);
            //tempClobWriter.flush();
            //tempClobWriter.close();
            //tempClob.close();
            
            // Get a handle on the method: public static CLOB CLOB.createTemporary(Connection connection, boolean cache, int CLOB.DURATION_SESSION)
            Class oracleClobClass = Class.forName("oracle.sql.CLOB");
            Class argumentTypes[] = new Class[] {Connection.class, Boolean.TYPE, Integer.TYPE };
            Method createTemporaryMethod = oracleClobClass.getMethod("createTemporary", argumentTypes);
            
            // Get a handle on the constant CLOB.DURATION_SESSION
            Field durationSessionField = oracleClobClass.getField("DURATION_SESSION");
            Object arguments[] = new Object[] {connection, Boolean.TRUE, durationSessionField.get(null)};
            
            // Create a temporary CLOB by invoking the createTemporaryMethod
            Object tempClob = createTemporaryMethod.invoke(null, arguments);
            
            // Get a handle on the method: public void CLOB.open(int CLOB.MODE_READWRITE)
            argumentTypes = new Class[] {Integer.TYPE};
            Method openMethod = oracleClobClass.getMethod("open", argumentTypes);
            
            // Get a handle on the constant CLOB.MODE_READWRITE
            Field modeReadWriteField = oracleClobClass.getField("MODE_READWRITE");
            arguments = new Object[] {modeReadWriteField.get(null)};
            
            // Invoke the openMethod
            openMethod.invoke(tempClob, arguments);
            
            // get the getCharacterOutputStream method
            Method getCharacterOutputStreamMethod = oracleClobClass.getMethod("getCharacterOutputStream", (Class[]) null);
            
            // call the getCharacterOutpitStream method
            Writer tempClobWriter = (Writer) getCharacterOutputStreamMethod.invoke(tempClob, (Object[]) null);
            
            // write the string to the clob
            tempClobWriter.write(value);
            tempClobWriter.flush();
            tempClobWriter.close();
            
            // Close the tempClob
            Method closeMethod = oracleClobClass.getMethod("close", (Class[]) null);
            
            // call the close method
            closeMethod.invoke(tempClob, (Object[]) null);
            
            // It is desirable to invoke the tempClob.freeTemporary() to clear the memory after the Clob has been used
            // Hopefully, it'll be done, when the session is terminated
            
            // return the tempClob
            return (Clob) tempClob;
        } catch(Exception e ) {
            String str = "Error in obtaining the Oracle CLOB object: " + e.toString();
            log.error(str, e);
            throw new SQLException(str);
        }
    }
    
    /** This is an oracle-centric method to create a BLOB object containing the value passed in. */
    private static Blob createBlob(Connection connection, byte[] value) throws SQLException {
        try {
            connection = DataSourceFactory.getUnderlyingConnection(connection);
            
            // The following code uses reflection, so that we don't need ORACLE jar while compiling !!
            //BLOB tempBlob = BLOB.createTemporary(connection, true, BLOB.DURATION_SESSION);
            //tempBlob.open(BLOB.MODE_READWRITE);
            //OutputStream tempBlobOutputStream = tempBlob.getBinaryOutputStream();
            //tempBlobOutputStream.write((String) value);
            //tempBlobOutputStream.flush();
            //tempBlobOutputStream.close();
            //tempBlob.close();
            
            // Get a handle on the method: public static BLOB BLOB.createTemporary(Connection connection, boolean cache, int BLOB.DURATION_SESSION)
            Class oracleBlobClass = Class.forName("oracle.sql.BLOB");
            Class argumentTypes[] = new Class[] {Connection.class, Boolean.TYPE, Integer.TYPE };
            Method createTemporaryMethod = oracleBlobClass.getMethod("createTemporary", argumentTypes);
            
            // Get a handle on the constant BLOB.DURATION_SESSION
            Field durationSessionField = oracleBlobClass.getField("DURATION_SESSION");
            Object arguments[] = new Object[] {connection, Boolean.TRUE, durationSessionField.get(null)};
            
            // Create a temporary BLOB by invoking the createTemporaryMethod
            Object tempBlob = createTemporaryMethod.invoke(null, arguments);
            
            // Get a handle on the method: public void BLOB.open(int BLOB.MODE_READWRITE)
            argumentTypes = new Class[] {Integer.TYPE};
            Method openMethod = oracleBlobClass.getMethod("open", argumentTypes);
            
            // Get a handle on the constant BLOB.MODE_READWRITE
            Field modeReadWriteField = oracleBlobClass.getField("MODE_READWRITE");
            arguments = new Object[] {modeReadWriteField.get(null)};
            
            // Invoke the openMethod
            openMethod.invoke(tempBlob, arguments);
            
            // get the getBinaryOutputStream method
            Method getBinaryOutputStreamMethod = oracleBlobClass.getMethod("getBinaryOutputStream", (Class[]) null);
            
            // call the getCharacterOutpitStream method
            OutputStream tempBlobOutputStream = (OutputStream) getBinaryOutputStreamMethod.invoke(tempBlob, (Object[]) null);
            
            // write the string to the blob
            tempBlobOutputStream.write(value);
            tempBlobOutputStream.flush();
            tempBlobOutputStream.close();
            
            // Close the tempBlob
            Method closeMethod = oracleBlobClass.getMethod("close", (Class[]) null);
            
            // call the close method
            closeMethod.invoke(tempBlob, (Object[]) null);
            
            // It is desirable to invoke the tempBlob.freeTemporary() to clear the memory after the Blob has been used
            // Hopefully, it'll be done, when the session is terminated
            
            // return the tempBlob
            return (Blob) tempBlob;
        } catch(Exception e ) {
            String str = "Error in obtaining the Oracle BLOB object: " + e.toString();
            log.error(str, e);
            throw new SQLException(str);
        }
    }
    
    /** This is an oracle-centric method to return OracleTypes.CURSOR */
    private static int getSqlTypeForOracleCursor() {
        try {
            // The following code uses reflection, so that we don't need ORACLE jar while compiling !!
            //return OracleTypes.CURSOR
            
            // Get a handle on the field: public static int OracleTypes.CURSOR
            Class oracleTypesClass = Class.forName("oracle.jdbc.OracleTypes");
            Field f = oracleTypesClass.getField("CURSOR");
            return ((Integer) f.get(null)).intValue();
        } catch(Exception e ) {
            String str = "Error in obtaining OracleTypes.CURSOR: " + e.toString();
            log.error(str, e);
            throw new IllegalArgumentException(str);
        }
    }

    /** Oracle specific method. Returns true if DriverMajorVersion >= 10,
     * indicating that the driver supports standard JDBC LOB functionality.
     */
    private static boolean supportsStdLob(PreparedStatement pstmt) {
        try {
            boolean bool = pstmt.getConnection().getMetaData().getDriverMajorVersion() >= 10;
            if (log.isDebugEnabled())
                log.debug("Supports standard LOBs when DriverMajorVersion >= 10: " + bool);
            return bool;
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception in trying to extract the DriverMajorVersion from a PreparedStatement", e);
            return false;
        }

    }
}
