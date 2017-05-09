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
import java.lang.reflect.*;
import java.util.*;
import org.jaffa.util.StringHelper;

/** Helper class for introspecting Domain objects. */
public class Introspection {
    
    private static final Logger log = Logger.getLogger(Introspection.class);
    
    /** This will introspect the input Class for an AccessibleObject, for each of the input fieldName passed in the 'fields' Map.
     * For each fieldName, this method will introspect the input class for an AccessibleObject in the following order.
     *      1- If an entry exists for the fieldName in the members Map, search for a Field object having the same name as that memberName
     *      2- Else search for a Method object - getFieldName() or isFieldName()
     *      3- If no AccessibleObject is found, then recursively call itself for each parent class.
     * It will return a Map of key=fieldName/value=Field/Method object pairs.
     * The returnType of the Field/Method will have to match the type passed in the input 'fields' Map.
     * NOTE: An inaccessible Field/Method will be forced to be accessible !!
     * No exception is raised, in case an appropriate method is not found for a fieldName.
     * The calling class can always compare the size of the input Map with the Map returned by this method,
     * and raise suitable exceptions.
     * @param clazz The class to be introspected.
     * @param fields The key of this HashMap is the name of the field (String) and the value is the data type (String).
     * @param members This map will contain the fields which are to be introspected by using member variables as opposed to getters/setters. The key of this HashMap is the name of the field (String) and the value is the memberName (String).
     * @return a map of key=fieldName/value=Field/Method object pairs.
     */
    public static Map getAccessors(Class clazz, Map fields, Map members) {
        return getAccessibleObjects(clazz, fields, members, true);
    }
    
    /** This will introspect the input Class for an AccessibleObject, for each of the input fieldName passed in the 'fields' Map.
     * For each fieldName, this method will introspect the input class for an AccessibleObject in the following order.
     *      1- If an entry exists for the fieldName in the members Map, search for a Field object having the same name as that memberName
     *      2- Else search for a Method object - setFieldName(Object obj)
     *      3- If no AccessibleObject is found, then recursively call itself for each parent class.
     * It will return a Map of key=fieldName/value=Field/Method object pairs.
     * The returnType/paramter of the Field/Method will have to match the type passed in the input 'fields' Map.
     * NOTE: An inaccessible Field/Method will be forced to be accessible !!
     * No exception is raised, in case an appropriate method is not found for a fieldName.
     * The calling class can always compare the size of the input Map with the Map returned by this method,
     * and raise suitable exceptions.
     * @param clazz The class to be introspected.
     * @param fields The key of this HashMap is the name of the field (String) and the value is the data type (String).
     * @param members This map will contain the fields which are to be introspected by using member variables as opposed to getters/setters. The key of this HashMap is the name of the field (String) and the value is the memberName (String).
     * @return a map of key=fieldName/value=Field/Method object pairs.
     */
    public static Map getMutators(Class clazz, Map fields, Map members) {
        return getAccessibleObjects(clazz, fields, members, false);
    }
    
    /** This method will introspect the input class for for a Field object having the same name as the input fieldName.
     * If no Field is found, then it'll recursively call itself for each parent class.
     * NOTE: An inaccessible Field will be forced to be accessible !!
     * @param clazz The class to be introspected.
     * @param fieldName The fieldName for which the Field object is to be found.
     * @param datatype This is an optional input. If passed then it'll ensure that the Field object has the correct signature.
     * @return the for a Field object having the same name as the input fieldName. A null is returned, in case the field doesn't exist.
     */
    public static Field getAccessibleField(Class clazz, String fieldName, Class datatype) {
        return (Field) getAccessibleObject(clazz, fieldName, true, true, false, datatype);
    }
    
    /** This method will introspect the input class for an accessor Method object - getFieldName() or isFieldName()
     * If no Method is found, then it'll recursively call itself for each parent class.
     * NOTE: An inaccessible Method will be forced to be accessible !!
     * @param clazz The class to be introspected.
     * @param fieldName The fieldName for which the accessor is to be found.
     * @param datatype This is an optional input. If passed then it'll ensure that the accessor has the correct signature.
     * @return the accessor Method for the property. A null is returned, in case the property doesn't exist.
     */
    public static Method getAccessorMethod(Class clazz, String fieldName, Class datatype) {
        return (Method) getAccessibleObject(clazz, fieldName, true, false, true, datatype);
    }
    
    /** This method will introspect the input class for a mutator Method object - setFieldName().
     * If no Method is found, then it'll recursively call itself for each parent class.
     * NOTE: An inaccessible Method will be forced to be accessible !!
     * @param clazz The class to be introspected.
     * @param fieldName The fieldName for which the mutator is to be found.
     * @param datatype This is an optional input. If passed then it'll ensure that the mutator has the correct signature.
     * @return the mutator Method for the property. A null is returned, in case the property doesn't exist.
     */
    public static Method getMutatorMethod(Class clazz, String fieldName, Class datatype) {
        return (Method) getAccessibleObject(clazz, fieldName, false, false, true, datatype);
    }
    
    
    
    
    
    /** This will introspect the input Class for an AccessibleObject, for each of the input fieldName passed in the 'fields' Map.
     * For each fieldName, this method will introspect the input class for an AccessibleObject in the following order.
     *      1- If an entry exists for the fieldName in the members Map, search for a Field object having the same name as that memberName
     *      2- Else search for a Method object - getFieldName() or isFieldName() if accessor is true, or setFieldName(Object obj) if accessor is false
     *      3- If no AccessibleObject is found, then recursively call itself for each parent class.
     * It will return a Map of key=fieldName/value=Field/Method object pairs.
     * The returnType/paramter of the Field/Method will have to match the type passed in the input 'fields' Map.
     * NOTE: An inaccessible Field/Method will be forced to be accessible !!
     * No exception is raised, in case an appropriate method is not found for a fieldName.
     * The calling class can always compare the size of the input Map with the Map returned by this method,
     * and raise suitable exceptions.
     * @param clazz The class to be introspected.
     * @param fields The key of this HashMap is the name of the field (String) and the value is the data type (String).
     * @param members This map will contain the fields which are to be introspected by using member variables as opposed to getters/setters. The key of this HashMap is the name of the field (String) and the value is the memberName (String).
     * @param accessor determines whether to return an accessor or a mutator.
     * @return a map of key=fieldName/value=Field/Method object pairs.
     */
    private static Map getAccessibleObjects(Class clazz, Map fields, Map members, boolean accessor) {
        Map output = new HashMap();
        for (Iterator itr = fields.keySet().iterator(); itr.hasNext();) {
            String fieldName = (String) itr.next();
            try {
                Class datatype;
                String type = (String) fields.get(fieldName);
                if (type.equalsIgnoreCase("byte[]"))
                    datatype = byte[].class;
                else
                    datatype = Class.forName(type);
                
                AccessibleObject obj = null;
                if (members.containsKey(fieldName))
                    obj = getAccessibleObject(clazz, (String) members.get(fieldName), accessor, true, false, datatype);
                else
                    obj = getAccessibleObject(clazz, fieldName, accessor, false, true, datatype);
                if (obj != null) {
                    output.put(fieldName, obj);
                } else {
                    if (log.isDebugEnabled())
                        log.debug((accessor ? "Accessor" : "Mutator") + " not found for field '" + fieldName + '\'');
                }
            } catch (ClassNotFoundException e) {
                String str = "Error in loading the Class for the datatype '" + fields.get(fieldName) + "' of the field '" + fieldName + '\'';
                log.warn(str, e);
            }
        }
        
        return output;
    }
    
    /** This method will introspect the input class for an AccessibleObject in the following order.
     *      1- If findField is true, search for a Field object having the same name as the input fieldName
     *      2- If findMethod is true,  search for a Method object - getFieldName() or isFieldName() if accessor is true, or setFieldName(Object obj) if accessor is false
     *      3- If no AccessibleObject is found, then recursively call itself for each parent class.
     * NOTE: An inaccessible Field/Method will be forced to be accessible !!
     * @param clazz The class to be introspected.
     * @param fieldName The fieldName for which the accessor/mutator is to be found.
     * @param accessor determines whether to return an accessor or a mutator.
     * @param findField this will determine if introspection should be performed for a Field object
     * @param findMethod this will determine if introspection should be performed for a Method object
     * @param datatype This is an optional input. If passed then it'll ensure that the accessor/mutator have the correct signature.
     * @return the accessor/mutator Field/Method for the property. A null is returned, in case the property doesn't exist.
     */
    private static AccessibleObject getAccessibleObject(Class clazz, String fieldName, boolean accessor,
    boolean findField, boolean findMethod, Class datatype) {
        AccessibleObject output = null;
        
        // Search for a Field
        if (findField) {
            try {
                output = clazz.getDeclaredField(fieldName);
                if (datatype != null && !datatype.isAssignableFrom(((Field) output).getType()))
                    output = null;
            } catch (NoSuchFieldException e) {
                // do nothing
            }
        }
        
        // If Field is not found, then search for a Method
        if (output == null && findMethod) {
            String methodName;
            if (accessor) {
                methodName = "get" + StringHelper.getUpper1(fieldName);
                try {
                    output = clazz.getDeclaredMethod(methodName, new Class[0]);
                    if (datatype != null && !datatype.isAssignableFrom(((Method) output).getReturnType()))
                        output = null;
                } catch (NoSuchMethodException e) {
                    // do nothing
                }
                if (output == null) {
                    methodName = "is" + StringHelper.getUpper1(fieldName);
                    try {
                        output = clazz.getDeclaredMethod(methodName, new Class[0]);
                        if (datatype != null && !datatype.isAssignableFrom(((Method) output).getReturnType()))
                            output = null;
                    } catch (NoSuchMethodException e) {
                        // do nothing
                    }
                }
            } else {
                methodName = "set" + StringHelper.getUpper1(fieldName);
                if (datatype != null) {
                    try {
                        output = clazz.getDeclaredMethod(methodName, new Class[] {datatype});
                    } catch (NoSuchMethodException e) {
                        // do nothing
                    }
                } else {
                    Method[] setters = clazz.getDeclaredMethods();
                    for (int i = 0; i < setters.length; i++) {
                        if (setters[i].getName().equals(methodName) && setters[i].getParameterTypes().length == 1) {
                            output = setters[i];
                            break;
                        }
                    }
                }
            }
        }
        
        // If no AccessibleObject is found, then recursively call itself for each parent class
        if (output == null) {
            Class superClass = clazz.getSuperclass();
            if (superClass != null && superClass != Object.class)
                output = getAccessibleObject(superClass, fieldName, accessor, findField, findMethod, datatype);
        }
        
        //  Force an inaccessible Field/Method to be accessible.
        if (output != null && !output.isAccessible())
            output.setAccessible(true);
        
        return output;
    }
    
}
