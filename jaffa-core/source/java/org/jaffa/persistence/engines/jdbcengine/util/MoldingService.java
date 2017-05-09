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

package org.jaffa.persistence.engines.jdbcengine.util;

import org.apache.log4j.Logger;
import java.util.*;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.datatypes.Defaults;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.Criteria.CriteriaEntry;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.engines.jdbcengine.configservice.ClassMetaData;
import org.jaffa.persistence.engines.jdbcengine.querygenerator.DataTranslator;
import org.jaffa.persistence.engines.jdbcengine.proxy.PersistentInstanceFactory;
import org.jaffa.util.StringHelper;

/**
 * The MoldingService uses reflection to create/update persistent objects using ClassMetaData defintions.
 * This can also set the initial state of a persistent object from the ResultSet.
 */
public class MoldingService {
    private static final Logger log = Logger.getLogger(MoldingService.class);
    
    /** Prevent instantiation of this class, cos that would be pointless.*/
    private MoldingService() {
    }
    
    /** Creates an instance of the persistent class, as defined in the input ClassMetaData defintion.
     * @param classMetaData The ClassMetaData object, whose corresponding persistent class is to be instantiated.
     * @throws ClassNotFoundException if the Persistent class is not found.
     * @throws InstantiationException if this Class represents an abstract class, an interface, an array class, a primitive type, or void; or if the class has no nullary constructor; or if the instantiation fails for some other reason.
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible.
     * @return an instance of the Persistent class, defined in the ClassMetaData defintion.
     */
    public static IPersistent getObject(ClassMetaData classMetaData)
    throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String className = classMetaData.getClassName();
        Class clazz = Class.forName(className);
        Object object = PersistentInstanceFactory.newPersistentInstance(clazz);
        return (IPersistent) object;
    }
    
    /** This gets the value of the attributeName from the Persistent object, using the accessor method cached in the ClassMetaData defintion.
     * @param object The Persistent object.
     * @param classMetaData The ClassMetaData definition for the Persistent object.
     * @param attributeName The attribute whose value will be returned.
     * @throws IllegalAccessException if the accessor Method object enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the accessor method throws an exception.
     * @return the value of the attribute.
     */
    public static Object getInstanceValue(IPersistent object, ClassMetaData classMetaData, String attributeName)
    throws IllegalAccessException, InvocationTargetException {
        AccessibleObject getter = classMetaData.getAccessor(attributeName);
        Object instanceValue = getter instanceof Field ? ((Field) getter).get(object) : ((Method) getter).invoke(object, new Object[0]);
        return rpad(classMetaData, attributeName, instanceValue);
    }
    
    /** This sets the value of the attribute from the Persistent object, using the mutator method cached in the ClassMetaData defintion.
     * @param object The Persistent object.
     * @param classMetaData The ClassMetaData definition for the Persistent object.
     * @param attributeName The attribute whose value will be set.
     * @param value The value to be set.
     * @throws IllegalAccessException if the mutator Method object enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the mutator method throws an exception.
     */
    public static void setInstanceValue(IPersistent object, ClassMetaData classMetaData, String attributeName, Object value)
    throws IllegalAccessException, InvocationTargetException {
        if (PersistentInstanceFactory.getActualPersistentClass(object) == object.getClass()) {
            // The input object is a regular class
            AccessibleObject setter = classMetaData.getMutator(attributeName);
            if (setter instanceof Field) {
                Field field = (Field) setter;
                if (value != null && value.getClass() != field.getType()) {
                    String layout = null;
                    if (value instanceof Boolean)
                        layout = '[' + classMetaData.getSqlType(attributeName).toLowerCase() + ']';
                    value = DataTypeMapper.instance().map(value, field.getType(), layout);
                }
                field.set(object, value);
            } else {
                Method method = (Method) setter;
                if (value != null && value.getClass() != method.getParameterTypes()[0]) {
                    String layout = null;
                    if (value instanceof Boolean)
                        layout = '[' + classMetaData.getSqlType(attributeName).toLowerCase() + ']';
                    value = DataTypeMapper.instance().map(value, method.getParameterTypes()[0], layout);
                }
                method.invoke(object, new Object[]{value});
            }
        } else {
            // The input object is a Proxy
            PersistentInstanceFactory.setInstanceValue(object, attributeName, value);
        }
    }
    
    /** Creates an instance of the persistent class, as defined in the input ClassMetaData defintion.
     * It will set the attributes using the values from the ResultSet.
     * @param classMetaData The ClassMetaData object, whose corresponding persistent class is to be instantiated.
     * @param rs The ResultSet used for creating the initial state of the persistent object.
     * @param engineType The engine type as defined in init.xml
     * @throws ClassNotFoundException if the Persistent class is not found.
     * @throws InstantiationException if this Class represents an abstract class, an interface, an array class, a primitive type, or void; or if the class has no nullary constructor; or if the instantiation fails for some other reason.
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible.
     * @throws InvocationTargetException if the mutator method throws an exception.
     * @throws SQLException if a database access error occurs.
     * @throws IOException if any error occurs in reading the data from the database.
     * @return an instance of the Persistent class, defined in the ClassMetaData defintion.
     */
    public static IPersistent getObject(ClassMetaData classMetaData, ResultSet rs, String engineType)
    throws ClassNotFoundException, InstantiationException, IllegalAccessException
            , InvocationTargetException, SQLException, IOException {
        IPersistent object = getObject(classMetaData);
        
        // set the keys
        if (classMetaData.getAllKeyFieldNames() != null)
            populateObject(classMetaData, rs, object, classMetaData.getAllKeyFieldNames().iterator(), engineType);
        
        // set all the other fields
        if (classMetaData.getAttributes() != null)
            populateObject(classMetaData, rs, object, classMetaData.getAttributes().iterator(), engineType);
        
        return object;
    }
    
    /**
     * Transfers the contents of the ResultSet into a Map for a query involving functions.
     * @return a Map containing the result of a query involving functions.
     * @param criteria The criteria used for the query.
     * @param classMetaData The ClassMetaData object
     * @param rs The ResultSet
     * @param engineType The engine type as defined in init.xml
     * @throws SQLException if a database access error occurs.
     * @throws IOException if any error occurs in reading the data from the database.
     */
    public static Map getFunctionQueryMap(Criteria criteria, ClassMetaData classMetaData, ResultSet rs, String engineType)
    throws SQLException, IOException {
        Map m = new LinkedHashMap();
        if (criteria.getGroupBys() != null) {
            for (Iterator i = criteria.getGroupBys().iterator(); i.hasNext(); ) {
                Criteria.GroupBy gb = (Criteria.GroupBy) i.next();
                String fieldName = gb.getName();
                String columnName = gb.getId();
                String typeName = classMetaData.getSqlType(fieldName);
                Object value = DataTranslator.getAppObject(rs, columnName, typeName, engineType);
                m.put(columnName, value);
            }
        }
        
        if (criteria.getFunctionEntries() != null) {
            for (Iterator i = criteria.getFunctionEntries().iterator(); i.hasNext(); ) {
                Criteria.FunctionEntry fe = (Criteria.FunctionEntry) i.next();
                String fieldName = fe.getName();
                String columnName = fe.getId();
                String typeName = null;
                if (fe.getFunction() == Criteria.FUNCTION_COUNT)
                    typeName = Defaults.INTEGER;
                else if (fe.getFunction() == Criteria.FUNCTION_AVG)
                    typeName = Defaults.DECIMAL;
                else if (fe.getFunction() == Criteria.FUNCTION_CURRENT_DATE_TIME)
                    typeName = Defaults.DATETIME;
                else
                    typeName = classMetaData.getSqlType(fieldName);
                Object value = DataTranslator.getAppObject(rs, columnName, typeName, engineType);
                m.put(columnName, value);
                
            }
            
        }
        return m;
    }
    
    /** This gets the value of the attributeName from the Criteria object.
     * @param criteria The Criteria object.
     * @param classMetaData The ClassMetaData definition for the Persistent object.
     * @param attributeName The attribute whose value will be returned.
     * @return the value of the attribute.
     */
    public static Object getValueFromCriteria(Criteria criteria, ClassMetaData classMetaData, String attributeName) {
        Object value = null;
        if (criteria.getCriteriaEntries() != null) {
            for (Iterator i = criteria.getCriteriaEntries().iterator(); i.hasNext(); ) {
                CriteriaEntry ce = (CriteriaEntry) i.next();
                if (attributeName.equals(ce.getName())) {
                    value = ce.getValue();
                    break;
                }
            }
        }
        return rpad(classMetaData, attributeName, value);
    }
    
    private static void populateObject(ClassMetaData classMetaData, ResultSet rs , IPersistent object, Iterator fieldItr, String engineType)
    throws IllegalAccessException, InvocationTargetException, SQLException, IOException {
        while (fieldItr.hasNext()) {
            String fieldName = (String) fieldItr.next();
            String columnName = classMetaData.getSqlName(fieldName);
            String typeName = classMetaData.getSqlType(fieldName);
            Object value = DataTranslator.getAppObject(rs, columnName, typeName, engineType);
            setInstanceValue(object, classMetaData, fieldName, value);
        }
    }
    
    /** RPAD CHAR fields. */
    private static Object rpad(ClassMetaData classMetaData, String attributeName, Object value) {
        Integer rpad = classMetaData.getRpad(attributeName);
        if (rpad != null && rpad > 0 && String.class.getName().equals(classMetaData.getType(attributeName)))
            value = StringHelper.rpad((String) value, rpad.intValue());
        return value;
    }
    
}
