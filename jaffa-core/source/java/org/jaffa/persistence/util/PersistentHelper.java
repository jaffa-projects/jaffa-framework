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

package org.jaffa.persistence.util;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.datatypes.Defaults;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.Criteria.CriteriaEntry;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.DomainObjectValidationException;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.StringHelper;

/** Helper methods for persistent objects.
 */
public class PersistentHelper {
    
    private static final Logger log = Logger.getLogger(PersistentHelper.class);
    private static final String META_CLASS_SUFFIX = "Meta";
    
    /** This returns the corresponding meta class for the input persistent class.
     * It assumes the name of the meta class by appending 'Meta' to the input persistent class.
     * @param persistentClassName The persistent class.
     * @return the meta class.
     * @throws ClassNotFoundException if the Meta class for the input persistent class is not found.
     */
    public static Class getMetaClass(String persistentClassName) throws ClassNotFoundException {
        return Class.forName(persistentClassName + META_CLASS_SUFFIX);
    }
    
    /** This returns an array of meta information for all the key fields of the persistent class.
     * @param persistentClassName The name of the persistent class.
     * @throws ClassNotFoundException if the Meta class for the input persistent class is not found.
     * @throws NoSuchMethodException if the Meta class does not have the 'public static FieldMetaData[] getKeyFields()' method.
     * @throws IllegalAccessException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class throws an exception.
     */
    public static FieldMetaData[] getKeyFields(String persistentClassName)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // Get the meta class
        Class metaClass = getMetaClass(persistentClassName);
        
        // Get a handle on the method for retrieving the FieldMetaData from the meta class
        Method m = metaClass.getMethod("getKeyFields", (Class[]) null);
        
        // Ensure that the method is static and that the method returns FieldMetaData[]
        if (Modifier.isStatic(m.getModifiers()) && m.getReturnType() == FieldMetaData[].class) {
            // Now invoke the method to get the FieldMetaData objects
            return (FieldMetaData[]) m.invoke(null, (Object[]) null);
        } else {
            String str = "The getKeyFields() method in the meta class for the persistent class " + persistentClassName + " should be static and return an array of the FieldMetaData instances";
            if (log.isDebugEnabled())
                log.debug(str);
            throw new NoSuchMethodException(str);
        }
    }
    
    /** This returns an array of meta information for all the mandatory fields of the persistent class.
     * @param persistentClassName The name of the persistent class.
     * @throws ClassNotFoundException if the Meta class for the input persistent class is not found.
     * @throws NoSuchMethodException if the Meta class does not have the 'public static FieldMetaData[] getMandatoryFields()' method.
     * @throws IllegalAccessException if the 'public static FieldMetaData[] getMandatoryFields()' method of the Meta class enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the 'public static FieldMetaData[] getMandatoryFields()' method of the Meta class throws an exception.
     */
    public static FieldMetaData[] getMandatoryFields(String persistentClassName)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // Get the meta class
        Class metaClass = getMetaClass(persistentClassName);
        
        // Get a handle on the method for retrieving the FieldMetaData from the meta class
        Method m = metaClass.getMethod("getMandatoryFields", (Class[]) null);
        
        // Ensure that the method is static and that the method returns FieldMetaData[]
        if (Modifier.isStatic(m.getModifiers()) && m.getReturnType() == FieldMetaData[].class) {
            // Now invoke the method to get the FieldMetaData objects
            return (FieldMetaData[]) m.invoke(null, (Object[]) null);
        } else {
            String str = "The getMandatoryFields() method in the meta class for the persistent class " + persistentClassName + " should be static and return an array of the FieldMetaData instances";
            if (log.isDebugEnabled())
                log.debug(str);
            throw new NoSuchMethodException(str);
        }
    }
    
    /** This returns an array of meta information for all the fields of the persistent class.
     * @param persistentClassName The name of the persistent class.
     * @throws ClassNotFoundException if the Meta class for the input persistent class is not found.
     * @throws NoSuchMethodException if the Meta class does not have the 'public static FieldMetaData[] getFieldMetaData()' method.
     * @throws IllegalAccessException if the 'public static FieldMetaData[] getFieldMetaData()' method of the Meta class enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the 'public static FieldMetaData[] getFieldMetaData()' method of the Meta class throws an exception.
     */
    public static FieldMetaData[] getFieldMetaData(String persistentClassName)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // Get the meta class
        Class metaClass = getMetaClass(persistentClassName);
        
        // Get a handle on the method for retrieving the FieldMetaData from the meta class
        Method m = metaClass.getMethod("getFieldMetaData", (Class[]) null);
        
        // Ensure that the method is static and that the method returns FieldMetaData[]
        if (Modifier.isStatic(m.getModifiers()) && m.getReturnType() == FieldMetaData[].class) {
            // Now invoke the method to get the FieldMetaData objects
            return (FieldMetaData[]) m.invoke(null, (Object[]) null);
        } else {
            String str = "The getFieldMetaData() method in the meta class for the persistent class " + persistentClassName + " should be static and return an array of the FieldMetaData instances";
            if (log.isDebugEnabled())
                log.debug(str);
            throw new NoSuchMethodException(str);
        }
    }
    
    /** Returns the FieldMetaData object from the meta class for the input persistent class for the input field.
     * @param persistentClassName The name of the persistent class.
     * @param fieldName the field name.
     * @throws ClassNotFoundException if the Meta class for the input persistent class is not found.
     * @throws NoSuchMethodException if the Meta class does not have the 'public static FieldMetaData[] getFieldMetaData(String fieldName)' method.
     * @throws IllegalAccessException if the 'public static FieldMetaData[] getFieldMetaData(String fieldName)' method of the Meta class enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the 'public static FieldMetaData[] getFieldMetaData(String fieldName)' method of the Meta class throws an exception.
     */
    public static FieldMetaData getFieldMetaData(String persistentClassName, String fieldName)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // Get the meta class
        Class metaClass = getMetaClass(persistentClassName);
        
        // Get a handle on the method for retrieving the FieldMetaData from the meta class
        Method m = metaClass.getMethod("getFieldMetaData", new Class[] {String.class});
        
        // Ensure that the method is static and that the method returns FieldMetaData
        if (Modifier.isStatic(m.getModifiers()) && m.getReturnType() == FieldMetaData.class) {
            // Now invoke the method to get the FieldMetaData object
            return (FieldMetaData) m.invoke(null, new Object[] {StringHelper.getUpper1(fieldName)});
        } else {
            String str = "The getFieldMetaData() method in the meta class for the persistent class " + persistentClassName + " should be static and return an instance of the FieldMetaData class";
            if (log.isDebugEnabled())
                log.debug(str);
            throw new NoSuchMethodException(str);
        }
    }
    
    /** This returns the labelToken for a persistent class. It will look for the persistent meta class and invoke its getLabelToken method to determine the value to return.
     * @param persistentClassName The persistent class.
     * @throws ClassNotFoundException If the persistent meta class is not found.
     * @throws NoSuchMethodException If there is no 'public static String getLabelToken()' method in the persistent meta class.
     * @throws IllegalAccessException if the method is inaccessible.
     * @throws InvocationTargetException if the method throws an exception.
     * @return the labelToken for a persistent class.
     */
    public static String getLabelToken(String persistentClassName)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        // Get the meta class
        Class metaClass = getMetaClass(persistentClassName);
        
        // Get a handle on the method for retrieving the labelToken from the meta class
        Method m = metaClass.getMethod("getLabelToken", (Class[]) null);
        
        // Ensure that the method is static and that the method returns String
        if (Modifier.isStatic(m.getModifiers()) && m.getReturnType() == String.class) {
            // Now invoke the method to return the labelToken
            return (String) m.invoke(null, (Object[]) null);
        } else {
            String str = "The getLabelToken() method in the meta class for the persistent class " + persistentClassName + " should be static and return a String object";
            if (log.isDebugEnabled())
                log.debug(str);
            throw new NoSuchMethodException(str);
        }
    }
    
    /** This returns the labelToken for a field in a persistent class. It will look for the persistent meta class and invoke its getFieldMetaData method to determine the appropriate FieldMetaData object. It will finally return the labelToken as specified in the FieldMetaData object.
     * @param persistentClassName The persistent class.
     * @param fieldName The field name.
     * @throws ClassNotFoundException If the persistent meta class is not found.
     * @throws NoSuchMethodException If there is no 'public static FieldMetaData getFieldMetaData(String fieldName)' method in the persistent meta class.
     * @throws IllegalAccessException if the method is inaccessible.
     * @throws InvocationTargetException if the method throws an exception.
     * @return the labelToken for a field in a domain class.
     */
    public static String getLabelToken(String persistentClassName, String fieldName)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,InvocationTargetException {
        FieldMetaData fieldMetaData = getFieldMetaData(persistentClassName, fieldName);
        return fieldMetaData == null ? null : fieldMetaData.getLabelToken();
    }
    
    /** This will check all the mandatory fields of the persistent object.
     * It utilises the corresponding Meta class for determining the mandatory fields.
     * @param object The persistent object.
     * @throws ApplicationExceptions Will contain a collection of MandatoryFieldExceptions for the all the mandatory fields which do not have values.
     * @throws FrameworkException If any framework error occurs.
     */
    public static void checkMandatoryFields(IPersistent object)
    throws ApplicationExceptions, FrameworkException {
        try {
            FieldMetaData[] fields = getMandatoryFields(object.getClass().getName());
            if (fields != null && fields.length > 0) {
                ApplicationExceptions appExps = new ApplicationExceptions();
                for (int i = 0; i < fields.length; i++) {
                    FieldMetaData fieldMetaData = fields[i];
                    Object fieldValue = BeanHelper.getField(object, fieldMetaData.getName());
                    if (fieldValue == null || (fieldValue instanceof String && ((String) fieldValue).length() == 0)) {
                        if (log.isDebugEnabled())
                            log.debug("Mandatory validation failed for the field " + fieldMetaData.getName());
                        appExps.add(new MandatoryFieldException(fieldMetaData.getLabelToken()));
                    }
                }
                if (appExps.size() > 0)
                    throw appExps;
            }
        } catch (ClassNotFoundException e) {
            String str = "Exception thrown while validating the domain object " + object;
            log.error(str, e);
            throw new DomainObjectValidationException(null, e);
        } catch (NoSuchMethodException e) {
            String str = "Exception thrown while validating the domain object " + object;
            log.error(str, e);
            throw new DomainObjectValidationException(null, e);
        } catch (IllegalAccessException e) {
            String str = "Exception thrown while validating the domain object " + object;
            log.error(str, e);
            throw new DomainObjectValidationException(null, e);
        } catch (InvocationTargetException e) {
            String str = "Exception thrown while validating the domain object " + object;
            log.error(str, e);
            throw new DomainObjectValidationException(null, e);
        }
    }
    
    /** This will query the database to see if the primary-key of the input persistent object is already in use.
     * It'll invoke the exists() method of the persistent class perform the check.
     * Note: This method will determine the key fields by looking up the getKeyFields method in the corresponding meta class for the input persistent object.
     * @param uow The UOW object. If null, then a UOW will be created implicitly by the exists() method to load the persistent object.
     * @param object The persistent object.
     * @throws ClassNotFoundException if the Meta class for the input persistent class is not found.
     * @throws NoSuchMethodException if the Meta class does not have the 'public static FieldMetaData[] getKeyFields()' method.
     * @throws IllegalAccessException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class throws an exception.
     * @throws IllegalArgumentException if the input persistent class does not have any key-fields
     * @throws FrameworkException if the exists() method of the persistent class fails.
     * @return true if the primary-key is already in use. A false will be returned if the key is not in use or if any of the key fields is null.
     */
    public static boolean exists(UOW uow, IPersistent object)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IllegalArgumentException,
            FrameworkException {
        Class persistentClass = object.getClass();
        
        // Create an argument List to be passed to the exists() method of the persistent class
        List arguments = new LinkedList();
        arguments.add(uow);
        
        FieldMetaData[] keyFields = getKeyFields(persistentClass.getName());
        if (keyFields != null && keyFields.length > 0) {
            for (int i = 0; i < keyFields.length; i++) {
                String keyFieldName = keyFields[i].getName();
                Object keyFieldValue = BeanHelper.getField(object, keyFieldName);
                if (keyFieldValue != null) {
                    arguments.add(keyFieldValue);
                } else {
                    // Return a false if any of the key-fields is null
                    return false;
                }
            }
            
            // Get a handle on the exists method of the persistent class
            Class[] argumentsClassArray = new Class[arguments.size()];
            argumentsClassArray[0] = UOW.class;
            for (int i = 1; i < arguments.size(); i++)
                argumentsClassArray[i] = arguments.get(i).getClass();
            Method m = persistentClass.getMethod("exists", argumentsClassArray);
            try {
                Boolean result = (Boolean) m.invoke(null, arguments.toArray());
                return result != null ? result.booleanValue() : false;
            } catch (InvocationTargetException e) {
                if (e.getCause() != null && e.getCause() instanceof FrameworkException)
                    throw (FrameworkException) e.getCause();
                else
                    throw e;
            }
        } else {
            String str = "Exists check cannot be performed. The input persistent object does not have any key fields defined in its meta class: " + object.getClass().getName();
            if (log.isDebugEnabled())
                log.debug(str);
            throw new IllegalArgumentException(str);
        }
    }
    
    /** This will generate a Criteria for reloading the input persistent object, based on the persistent class name and its key values.
     * Note: This method will determine the key fields by looking up the getKeyFields method in the corresponding meta class for the input persistent object.
     *
     * @param object The persistent object.
     * @throws ClassNotFoundException if the Meta class for the input persistent class is not found.
     * @throws NoSuchMethodException if the Meta class does not have the 'public static FieldMetaData[] getKeyFields()' method.
     * @throws IllegalAccessException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class throws an exception.
     * @throws IllegalArgumentException if the input persistent class does not have any key-fields or if any of the key-fields is null.
     * @return a Criteria for reloading the persistent object.
     */
    public static Criteria generateKeyCriteria(IPersistent object)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IllegalArgumentException {
        Criteria criteria = new Criteria();
        criteria.setTable(object.getClass().getName());
        FieldMetaData[] keyFields = getKeyFields(object.getClass().getName());
        if (keyFields != null && keyFields.length > 0) {
            for (FieldMetaData keyField : keyFields) {
                String keyFieldName = keyField.getName();
                Object keyFieldValue = BeanHelper.getField(object, keyFieldName);
                if (keyFieldValue != null) {
                    criteria.addCriteria(keyFieldName, keyFieldValue);
                } else {
                    String str = "KeyCriteria cannot be generated. The input persistent object has a null value for its key-field " + object + ':' + keyFieldName;
                    if (log.isDebugEnabled())
                        log.debug(str);
                    throw new IllegalArgumentException(str);
                }
            }
        } else {
            String str = "KeyCriteria cannot be generated. The input persistent object does not have any key fields defined in its meta class: " + object.getClass().getName();
            if (log.isDebugEnabled())
                log.debug(str);
            throw new IllegalArgumentException(str);
        }
        return criteria;
    }

    /** This will generate a unique string for the input persistent object, based on the persistent class name and its key values.
     * The format of the generated key will be: package.classname;key1value;key2value;key3value
     * For eg:
     *     For a Person persistent object having a primary key PersonId, the serialized key could be "org.example.Person;P0021"
     *     For an EventEntry persistent object having a composite primary key of EventId and PersonId primary, the serialized key could be "org.example.EventEntry;E01;P0021"
     * The back-slash '\' will be the escape character.
     * Hence, if the key-value contains a '\', then it'll be replaced by '\\'
     * If the key value contains a semi-colon, then it'll be replaced by '\;'
     *
     * Note: This method will determine the key fields by looking up the getKeyFields method in the corresponding meta class for the input persistent object.
     *
     * @param object The persistent object.
     * @throws ClassNotFoundException if the Meta class for the input persistent class is not found.
     * @throws NoSuchMethodException if the Meta class does not have the 'public static FieldMetaData[] getKeyFields()' method.
     * @throws IllegalAccessException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class throws an exception.
     * @throws IllegalArgumentException if the input persistent class does not have any key-fields or if any of the key-fields is null.
     * @return a unique String for identifying the persistent object.
     */
    public static String generateSerializedKey(IPersistent object)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IllegalArgumentException {
        StringBuffer buf = new StringBuffer(object.getClass().getName());
        FieldMetaData[] keyFields = getKeyFields(object.getClass().getName());
        if (keyFields != null && keyFields.length > 0) {
            for (int i = 0; i < keyFields.length; i++) {
                String keyFieldName = keyFields[i].getName();
                Object keyFieldValue = BeanHelper.getField(object, keyFieldName);
                if (keyFieldValue != null) {
                    buf.append(';').append(quoteSerializedKeyValue(keyFieldValue.toString()));
                } else {
                    String str = "SerializedKey cannot be generated. The input persistent object has a null value for its key-field " + object + ':' + keyFieldName;
                    if (log.isDebugEnabled())
                        log.debug(str);
                    throw new IllegalArgumentException(str);
                }
            }
        } else {
            String str = "SerializedKey cannot be generated. The input persistent object does not have any key fields defined in its meta class: " + object.getClass().getName();
            if (log.isDebugEnabled())
                log.debug(str);
            throw new IllegalArgumentException(str);
        }
        return buf.toString();
    }
    
    /** This will generate a unique string for a persistent object, based on the persistent class name and its key values specified in the input Criteria.
     * The format of the generated key will be: package.classname;key1value;key2value;key3value
     * For eg:
     *     For a Person persistent object having a primary key PersonId, the serialized key could be "org.example.Person;P0021"
     *     For an EventEntry persistent object having a composite primary key of EventId and PersonId primary, the serialized key could be "org.example.EventEntry;E01;P0021"
     * The back-slash '\' will be the escape character.
     * Hence, if the key-value contains a '\', then it'll be replaced by '\\'
     * If the key value contains a semi-colon, then it'll be replaced by '\;'
     *
     * Note: This method will determine the key fields by looking up the getKeyFields method in the corresponding meta class for the input persistent object.
     *
     * @param criteria The Criteria containing the key-field values.
     * @throws ClassNotFoundException if the Meta class for the persistent class is not found.
     * @throws NoSuchMethodException if the Meta class does not have the 'public static FieldMetaData[] getKeyFields()' method.
     * @throws IllegalAccessException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class throws an exception.
     * @throws IllegalArgumentException if the input Criteria does not have any key-fields or if any of the key-fields is null.
     * @return a unique String for identifying a persistent object.
     */
    public static String generateSerializedKey(Criteria criteria)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IllegalArgumentException {
        StringBuffer buf = new StringBuffer(criteria.getTable());
        FieldMetaData[] keyFields = getKeyFields(criteria.getTable());
        if (keyFields != null && keyFields.length > 0) {
            for (int i = 0; i < keyFields.length; i++) {
                String keyFieldName = keyFields[i].getName();
                Object keyFieldValue = getValueFromCriteria(criteria, keyFieldName);
                if (keyFieldValue != null) {
                    buf.append(';').append(quoteSerializedKeyValue(keyFieldValue.toString()));
                } else {
                    String str = "SerializedKey cannot be generated. The input Criteria has a null value for its key-field " + keyFieldName;
                    if (log.isDebugEnabled())
                        log.debug(str);
                    throw new IllegalArgumentException(str);
                }
            }
        } else {
            String str = "SerializedKey cannot be generated. The input Criteria does not have any key fields defined in its meta class: " + criteria.getTable();
            if (log.isDebugEnabled())
                log.debug(str);
            throw new IllegalArgumentException(str);
        }
        return buf.toString();
    }
    
    /** This will load the persistent object from the input serialized key, by invoking the findByPK() method of the persistent class encoded in the input String.
     * @param uow The UOW object. If null, then a UOW will be created implicitly by the findByPK method to load the persistent object.
     * @param serializedKey The serialized key which will have the right information to load the persistent object.
     * @throws ClassNotFoundException if the Persistent class or its Meta class are not found.
     * @throws NoSuchMethodException if the Persistent class does not have the 'public static IPersistent findByPK(UOW uow, KeyField1...)'  method or the Meta class does not have the 'public static FieldMetaData[] getKeyFields()' method.
     * @throws IllegalAccessException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class throws an exception.
     * @throws IllegalArgumentException if the input persistent class does not have any key-fields or if any of the key-fields is null.
     * @throws IntrospectionException if an exception occurs during introspection.
     * @throws FrameworkException if the findByPK() method of the persistent class fails.
     * @return a Persistent object.
     */
    public static IPersistent loadFromSerializedKey(UOW uow, String serializedKey)
    throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            IllegalArgumentException, IntrospectionException, FrameworkException {
        // Determine the persistent class name
        int beginIndex = 0;
        int endIndex = serializedKey.indexOf(';');
        if (endIndex <= 0) {
            String str = "Persistent object cannot be loaded. The input serialized key does not have the Persistent class name " + serializedKey;
            log.error(str);
            throw new IllegalArgumentException(str);
        }
        String persistentClassName = serializedKey.substring(beginIndex, endIndex);
        if (log.isDebugEnabled())
            log.debug("Found persistent class " + persistentClassName);
        Class persistentClass = Class.forName(persistentClassName);
        
        // Create an argument List to be passed to the findByPK method of the persistent class
        List arguments = new LinkedList();
        arguments.add(uow);
        
        // Get the key fields for the persistent class from its meta class
        FieldMetaData[] keyFields = getKeyFields(persistentClassName);
        if (keyFields != null && keyFields.length > 0) {
            for (int i = 0; i < keyFields.length; i++) {
                // Determine the value for a keyfield
                String keyFieldName = keyFields[i].getName();
                String keyFieldValue = null;
                beginIndex = endIndex + 1;
                endIndex = beginIndex + 1;
                if (beginIndex < serializedKey.length()) {
                    if (endIndex >= serializedKey.length()) {
                        // We are currently on the last character of the input serializedkey
                        keyFieldValue = serializedKey.substring(beginIndex);
                    } else {
                        // Search for the next ';', making sure that its not quoted
                        while(true) {
                            endIndex = serializedKey.indexOf(';', endIndex);
                            if (endIndex < 0) {
                                // No more ';'
                                keyFieldValue = serializedKey.substring(beginIndex);
                                break;
                            } else if (serializedKey.charAt(endIndex - 1) != '\\') {
                                // The ';' is not quoted
                                keyFieldValue = serializedKey.substring(beginIndex, endIndex);
                                break;
                            } else {
                                // We've reached a quoted ';'. Go past it
                                ++endIndex;
                                if (endIndex >= serializedKey.length()) {
                                    // No more search possible
                                    keyFieldValue = serializedKey.substring(beginIndex);
                                    break;
                                }
                            }
                        }
                    }
                }
                // Unquote the keyvalue
                keyFieldValue = unquoteSerializedKeyValue(keyFieldValue);
                
                // Throw an exception if the input serializedkey does not have a value for the keyfield
                if (keyFieldValue == null || keyFieldValue.length() == 0) {
                    String str = "Persistent object cannot be loaded. The input serialized key does not have a value for the key field " + keyFieldName;
                    log.error(str);
                    throw new IllegalArgumentException(str);
                }
                
                if (log.isDebugEnabled())
                    log.debug("Found " + keyFieldName + '=' + keyFieldValue);
                
                // Convert the keyFieldValue to the appropriate datatype
                Object convertedKeyFieldValue = DataTypeMapper.instance().map(keyFieldValue, Defaults.getClass(keyFields[i].getDataType()));
                if (convertedKeyFieldValue == null) {
                    String str = "Persistent object cannot be loaded. The keyField " + keyFieldName + " having the value " + keyFieldValue + " could not be converted to the appropriate datatype";
                    log.error(str);
                    throw new IllegalArgumentException(str);
                }
                
                arguments.add(convertedKeyFieldValue);
            }
            
            // Get a handle on the findByPK method of the persistent class
            Class[] argumentsClassArray = new Class[arguments.size()];
            argumentsClassArray[0] = UOW.class;
            for (int i = 1; i < arguments.size(); i++)
                argumentsClassArray[i] = arguments.get(i).getClass();
            Method m = persistentClass.getMethod("findByPK", argumentsClassArray);
            try {
                return (IPersistent) m.invoke(null, arguments.toArray());
            } catch (InvocationTargetException e) {
                if (e.getCause() != null && e.getCause() instanceof FrameworkException)
                    throw (FrameworkException) e.getCause();
                else
                    throw e;
            }
        } else {
            String str = "Persistent object cannot be loaded. The persistent class specified in the input serialzedKey does not have any key fields defined in its meta class: " + persistentClassName;
            log.error(str);
            throw new IllegalArgumentException(str);
        }
    }
    
    /** This will quote the semi-colon characters in the input string.
     * The back-slash '\' will be the escape character.
     * Hence, if the key-value contains a '\', then it'll be replaced by '\\'
     * If the key value contains a semi-colon, then it'll be replaced by '\;'
     */
    private static String quoteSerializedKeyValue(String input) {
        // replace '\' with '\\' and then replace ';' with '\;'
        return StringHelper.replace(StringHelper.replace(input, "\\", "\\\\"), ";", "\\;");
    }
    
    /** This will unquote the semi-colon characters in the input string.
     * The back-slash '\' will be the escape character.
     * Hence, if the key-value contains a '\;', then it'll be replaced by ';'
     * If the key value contains a '\\', then it'll be replaced by '\'
     */
    private static String unquoteSerializedKeyValue(String input) {
        // replace '\;' with ';' and then replace '\\' with '\'
        return StringHelper.replace(StringHelper.replace(input, "\\;", ";"), "\\\\", "\\");
    }
    
    /** This gets the value of the attributeName from the Criteria object.
     * @param criteria The Criteria object.
     * @param attributeName The attribute whose value will be returned.
     * @return the value of the attribute.
     */
    private static Object getValueFromCriteria(Criteria criteria, String attributeName) {
        Object value = null;
        if (criteria.getCriteriaEntries() != null) {
            for (Iterator i = criteria.getCriteriaEntries().iterator(); i.hasNext();) {
                CriteriaEntry ce = (CriteriaEntry) i.next();
                if (attributeName.equals(ce.getName())) {
                    value = ce.getValue();
                    break;
                }
            }
        }
        
        //Need a ClassMetaData to be able to pad the value. For now just return the value as-is.
        //return rpad(classMetaData, attributeName, value);
        return value;
    }
    
}
