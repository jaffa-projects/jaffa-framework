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

/*
 * BeanMolder.java
 *
 * Created on February 12, 2004, 4:00 PM
 *
 */

package org.jaffa.beans.moulding.mapping;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.jaffa.beans.moulding.data.domain.DomainDAO;
import org.jaffa.beans.moulding.mapping.GraphMapping;
import org.jaffa.beans.moulding.mapping.MappingFilter;
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.DomainObjectNotFoundException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.MultipleDomainObjectsFoundException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.Persistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.util.StringHelper;

/** Bean Moudler is used to mapp data between two Java Beans via a mapping file.
 * It has been specifcially coded to map between benas that extend/implement
 * DomainDAO and IPersistent for marshalling data to and from the database.
 *
 * @author  PaulE
 * @version 1.0
 *
 * @todo - Switch to use org.jaffa.datatypes.DataTypeMapper instead of org.jaffa.beans.moulding.mapping.DataTypeMapping
 */
public class BeanMoulder {

    private static Logger log = Logger.getLogger(BeanMoulder.class);

    /**
     * Display the properties of this JavaBean. If this bean has properties that implement
     * either DomainDAO or DomainDAO[], then also print this objects too.
     * @param source Javabean who's contents should be printed
     * @return multi-line string of this beans properties and their values
     */
    public static String printBean(Object source) {
        return printBean(source,null);
    }

    /**
     * Same as printBean(Object source), except the objectStack lists all the parent
     * objects its printed, and if this is one of them, it stops. This allows detection
     * of possible infinite recusion.
     * @param source Javabean who's contents should be printed
     * @param objectStack List of objects already traversed
     * @return multi-line string of this beans properties and their values
     */
    public static String printBean(Object source, List objectStack) {
        if(source==null)
            return null;

        // Prevent infinite object recursion
        if(objectStack!=null)
            if(objectStack.contains(source))
                return "Object Already Used. " + source.getClass().getName() + "@" + source.hashCode();
            else
                objectStack.add(source);
        else {
            objectStack = new ArrayList();
            objectStack.add(source);
        }

        StringBuffer out = new StringBuffer();
        out.append(source.getClass().getName());
        out.append("\n");

        try {
            BeanInfo sInfo = Introspector.getBeanInfo(source.getClass());
            PropertyDescriptor[] sDescriptors = sInfo.getPropertyDescriptors();
            if(sDescriptors != null && sDescriptors.length != 0)
                for(int i=0; i<sDescriptors.length;i++) {
                    PropertyDescriptor sDesc = sDescriptors[i];
                    Method sm = sDesc.getReadMethod();
                    if(sm!=null && sDesc.getWriteMethod()!=null) {
                        if(!sm.isAccessible())
                            sm.setAccessible(true);
                        Object sValue = sm.invoke(source, (Object[]) null);

                        out.append("  ");
                        out.append(sDesc.getName());
                        if(source instanceof DomainDAO) {
                            if(((DomainDAO)source).hasChanged(sDesc.getName()))
                                out.append("*");
                        }
                        out.append("=");
                        if(sValue==null)
                            out.append("<--NULL-->\n");
                        else if(sm.getReturnType().isArray()) {
                            StringBuffer out2 = new StringBuffer();
                            out2.append("Array of ");
                            out2.append(sm.getReturnType().getComponentType().getName());
                            out2.append("\n");
                            // Loop through array
                            Object[] a = (Object[])sValue;
                            for(int j=0;j<a.length;j++) {
                                out2.append("[");
                                out2.append(j);
                                out2.append("] ");
                                if(a[j]==null)
                                    out2.append("<--NULL-->");
                                else if(DomainDAO.class.isAssignableFrom(a[j].getClass()))
                                    out2.append( ((DomainDAO) a[j]).toString(objectStack));
                                else
                                    //out2.append(StringHelper.linePad(a[j].toString(), 4, " ",true));
                                    out2.append(a[j].toString());
                            }
                            out.append(StringHelper.linePad(out2.toString(), 4, " ",true));
                        } else {
                             if(DomainDAO.class.isAssignableFrom(sValue.getClass()))
                                out.append(StringHelper.linePad(((DomainDAO) sValue).toString(objectStack), 4, " ",true));
                            else {
                                out.append(StringHelper.linePad(sValue.toString(), 4, " ",true));
                                out.append("\n");
                            }

                        }
                    }
                }
        } catch (IllegalAccessException e) {
            MouldException me = new MouldException(MouldException.ACCESS_ERROR, "???", e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            //throw me;
        } catch (InvocationTargetException e) {
            MouldException me = new MouldException(MouldException.INVOCATION_ERROR, "???", e );
            log.error(me.getLocalizedMessage(),me.getCause());
            //throw me;
        } catch (IntrospectionException e) {
            MouldException me = new MouldException(MouldException.INTROSPECT_ERROR, "???", e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            //throw me;
        }
        return out.toString();
    }


    /**
     * Mould data from domain object and its related objects into a new JavaBean based
     * domain object graph, based on the defined mapping rules.
     *<p>
     * Same as {@link #moldFromDomain(Object source, Object target, GraphMapping graph, MappingFilter filter, String objectPath, boolean includeKeys)}
     * except the graph is derived from the target object, a default MappingFilter (that
     * just returns fields from the root object) and includeKeys is false.
     * The objectPath is also null, assuming this is the first object in the domain
     * object graph.
     *<p>
     * @param source Source object to mould data from, typically extends Persistent
     * @param target Target object to mould data to, typically extends DomainDAO
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException Thrown if any runtime moulding error has occured.
     */
    public static void moldFromDomain(Object source, Object target)
    throws ApplicationExceptions, FrameworkException {
        moldFromDomain(source, target, null, null, null, false );
    }

    /**
     * Mould data from domain object and its related objects into a new JavaBean based
     * domain object graph, based on the defined mapping rules.
     *<p>
     * Same as {@link #moldFromDomain(Object source, Object target, GraphMapping graph, MappingFilter filter, String objectPath, boolean includeKeys)}
     * except the graph is derived from the target object, and includeKeys is false.
     *<p>
     * @param source Source object to mould data from, typically extends Persistent
     * @param target Target object to mould data to, typically extends DomainDAO
     * @param filter Filter object that it is used to control what fields are populated or the target objects
     * @param objectPath  The path of this object being processed. This identifies possible parent
     * and/or indexed entries where this object is contained.
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException Thrown if any runtime moulding error has occured.
     */
    public static void moldFromDomain(Object source, Object target,
                                      MappingFilter filter, String objectPath)
    throws ApplicationExceptions, FrameworkException {
        moldFromDomain(source, target, null, filter, objectPath, false );
    }

    /**
     * Mould data from domain object and its related objects into a new JavaBean based
     * domain object graph, based on the defined mapping rules.
     * @param source Source object to mould data from, typically extends Persistent
     * @param target Target object to mould data to, typically extends DomainDAO
     * @param graph The mapping class with the rules of how to map this source object
     * @param filter Filter object that it is used to control what fields are populated or the target objects
     * @param objectPath  The path of this object being processed. This identifies possible parent
     * and/or indexed entries where this object is contained.
     * @param includeKeys true if key fields should be included in results regardless of the filters
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException Thrown if any runtime moulding error has occured.
     */
    public static void moldFromDomain(Object source, Object target, GraphMapping graph,
                                      MappingFilter filter, String objectPath, boolean includeKeys)
    throws ApplicationExceptions, FrameworkException {
        if(graph==null)
            graph = MappingFactory.getInstance(target);
            //throw new InstantiationException("A GraphMapping must be supplied");
        if(filter==null)
            filter = new MappingFilter(graph);

        try {
            // get list of target fileds to populate
            String[] tFields = graph.getDataFieldNames();
            if(tFields != null && tFields.length != 0)
                for(int i=0; i<tFields.length;i++) {
                    // Try to map a source to a target
                    String tName = tFields[i];
                    String fullName = tName;
                    if(objectPath != null)
                        fullName = objectPath + "." + fullName;

                    if(filter==null || filter.isFieldIncluded(fullName) || (includeKeys && graph.isKeyField(tName)) ) {
                        String sName = graph.getDomainFieldName(tName);
                        PropertyDescriptor tDesc = graph.getDataFieldDescriptor(tName);
                        PropertyDescriptor sDesc = graph.getDomainFieldDescriptor(tName);
                        // Based on validation in GraphMapping, that there is a
                        // DAO descriptor with a setter, and a DO descriptor with a getter
                        if (sDesc == null) {
                            log.error("No Getter for " + tName + " in path " + fullName);
                        }
                        else {
                            // in case getter is not public, make it available
                            Method sm = sDesc.getReadMethod();
                            if (!sm.isAccessible())
                                sm.setAccessible(true);

                            // get the setter, and make is available if needed
                            Method tm = tDesc.getWriteMethod();
                            if (!tm.isAccessible())
                                tm.setAccessible(true);

                            // Set the value if the source and target are the same datatype
                            Class tClass = tDesc.getPropertyType();
                            Class sClass = sDesc.getPropertyType();
                            if (tClass.isAssignableFrom(sClass)) {
                                // Get the value being copied
                                Object sValue = sm.invoke(source, (Object[]) null);
                                if (sValue != null) {
                                    tm.invoke(target, new Object[] { sValue });
                                    log.debug("Set " + tName + " = " + sValue);
                                }
                                else
                                    log.debug(tName + " no set, NULL value");

                                // See if there is a datatype mapper for these classes
                            }
                            else if (DataTypeMapper.instance().isMappable(sClass, tClass)) {
                                // Get the value being copied
                                Object sValue = sm.invoke(source, (Object[]) null);
                                if (sValue != null) {
                                    sValue = DataTypeMapper.instance().map(sValue, tClass);
                                    tm.invoke(target, new Object[] { sValue });
                                    log.debug("Set " + tName + " = " + sValue);
                                }
                                else
                                    log.debug(tName + " no set, NULL value");

                                // See if target is a DAO, this could be a foreign object or one-to-one relationship...
                            }
                            else if (DomainDAO.class.isAssignableFrom(tClass) && IPersistent.class.isAssignableFrom(sClass)) {
                                // Get the mapper for the related DAO, if it has keys, it must be a foriegn object
                                if (graph.isForeignField(tName)) {
                                    // look at foreign key fields, and make sure they are not null
                                    List foreignKeys = graph.getForeignKeys(tName);
                                    List foreignKeyValues = new ArrayList();
                                    boolean nullKey = false;
                                    for (Iterator k = foreignKeys.iterator(); k.hasNext(); ) {
                                        String doProp = (String) k.next();
                                        Object value = null;
                                        PropertyDescriptor doPd = graph.getRealDomainFieldDescriptor(doProp);
                                        if (doPd != null && doPd.getReadMethod() != null) {
                                            Method m = doPd.getReadMethod();
                                            if (!m.isAccessible())
                                                m.setAccessible(true);
                                            value = m.invoke(source, new Object[] {});
                                            if (value == null)
                                                nullKey = true;
                                            foreignKeyValues.add(value);
                                        }
                                        else {
                                            throw new MouldException(MouldException.INVALID_FK_MAPPING, objectPath,
                                                                     doProp, graph.getDomainClassShortName());
                                        }
                                    }
                                    if (nullKey) {
                                        log.debug("Did not create skeleton object '" + tClass.getName()
                                                  + "': one or more foreign key values missing.");
                                    }
                                    else {
                                        // Create the foreign object
                                        log.debug("Creating foreign object - " + tClass.getName());
                                        Object newDAO = newDAO(tClass);
                                        boolean createSkeleton = true;
                                        // Only retrieve related domain object and introspect if need
                                        if (filter.areSubFieldsIncluded(fullName)) {
                                            // read object and introspect all
                                            log.debug("Read foreign object '" + fullName + "' and mold");
                                            try {
                                                Object sValue = sm.invoke(source, (Object[]) null);
                                                if (sValue != null) {
                                                    BeanMoulder.moldFromDomain(sValue, newDAO, null, filter, fullName,
                                                                               true);
                                                    createSkeleton = false;
                                                }
                                            }
                                            catch (InvocationTargetException e) {
                                                // If the foreign object is not found, warn and create the skeleton
                                                if (e.getCause() != null && e.getCause() instanceof InvalidForeignKeyException)
                                                    log.warn(
                                                            "All foreign keys present, but foreign object does not exist");
                                                else
                                                    throw e;
                                            }
                                        }
                                        if (createSkeleton) {
                                            // just set foreign keys from current object
                                            log.debug("Set keys on skeleton foreign object only");
                                            GraphMapping graph2 = MappingFactory.getInstance(newDAO);
                                            Set keys = graph2.getKeyFields();
                                            if (keys == null || keys.size() != foreignKeyValues.size()) {
                                                throw new MouldException(MouldException.MISMATCH_FK_MAPPING, objectPath,
                                                                         target.getClass().getName(), newDAO.getClass().getName());
                                            }
                                            int k2 = 0;
                                            // Look through all the foreign keys on the skeleton object
                                            for (Iterator k = keys.iterator(); k.hasNext(); k2++) {
                                                String keyField = (String) k.next();
                                                Object keyValue = foreignKeyValues.get(k2);
                                                PropertyDescriptor pd = graph2.getDataFieldDescriptor(keyField);
                                                if (pd != null && pd.getWriteMethod() != null) {
                                                    Method m = pd.getWriteMethod();
                                                    if (!m.isAccessible())
                                                        m.setAccessible(true);
                                                    m.invoke(newDAO, new Object[] { keyValue });
                                                }
                                                else {
                                                    throw new MouldException(MouldException.CANT_SET_KEY_FIELD,
                                                                             objectPath, keyField, newDAO.getClass().getName());
                                                }
                                            }
                                        }
                                        tm.invoke(target, new Object[] { newDAO });
                                        log.debug("Set " + tName + " = " + newDAO);
                                    }
                                }
                                else {
                                    // This is not a foreign object, must be a related object
                                    if (filter.areSubFieldsIncluded(fullName)) {
                                        // Create the related object
                                        log.debug("Creating One-To-One object - " + tClass.getName());
                                        Object newDAO = newDAO(tClass);
                                        // read object and introspect all
                                        log.debug("Read related object '" + fullName + "' and mold");
                                        Object sValue = sm.invoke(source, (Object[]) null);
                                        if (sValue != null) {
                                            BeanMoulder.moldFromDomain(sValue, newDAO, null, filter, fullName, true);
                                        }
                                        else {
                                            log.debug("Related object '" + fullName + "' not found. Ignore it!");
                                        }
                                        tm.invoke(target, new Object[] { newDAO });
                                        log.debug("Set " + tName + " = " + newDAO);
                                    }
                                    else
                                        log.debug("No subfields for object " + fullName + " included. Object not retrieved");
                                }//END-related object

                                // See if Target may be an array of DAO's
                            }
                            else if (tClass.isArray() && DomainDAO.class.isAssignableFrom(tClass.getComponentType())
                                     && filter.areSubFieldsIncluded(fullName)) {
                                log.debug("Target is an array of DAO's");
                                log.debug("Read related objects '" + fullName + "' and mold");
                                Object sValue = sm.invoke(source, (Object[]) null);
                                if (sClass.isArray() && IPersistent.class.isAssignableFrom(sClass.getComponentType())) {
                                    log.debug("Source is an array of Persistent Objects");
                                    Object[] sArray = (Object[]) sValue;
                                    if (sArray.length > 0) {
                                        Object[] tArray = (Object[]) Array.newInstance(tClass.getComponentType(), sArray.length);
                                        log.debug("Translate Array of Size " + sArray.length);
                                        for (int j = 0; j < sArray.length; j++) {
                                            Object newDAO = newDAO(tClass.getComponentType());
                                            BeanMoulder.moldFromDomain(sArray[j], newDAO, null, filter, fullName, false);
                                            tArray[j] = newDAO;
                                            log.debug("Add to array [" + j + "] : " + newDAO);
                                        }
                                        tm.invoke(target, new Object[] { (Object) tArray });
                                        log.debug("Set Array " + tName);
                                    }
                                    else
                                        log.debug("Source Array is empty! Do Nothing");
                                } // source is DO array

                                // Error... No way to map property
                            }
                            else {
                                String err = "Can't Mold Property " + fullName + " from " + sClass.getName() + " to " + tClass.getName();
                                log.error(err);
                                throw new RuntimeException(err);
                            }
                        }
                    } // is included in filtered fields
                }

            // Clear changed fields on updated DAO
            if(target != null && target instanceof DomainDAO)
                ( (DomainDAO) target).clearChanges();

        } catch (IllegalAccessException e) {
            MouldException me = new MouldException(MouldException.ACCESS_ERROR, objectPath, e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            throw me;
        } catch (InvocationTargetException e) {
            if(e.getCause()!=null) {
                if(e.getCause() instanceof FrameworkException)
                    throw (FrameworkException)e.getCause();
                if(e.getCause() instanceof ApplicationExceptions)
                    throw (ApplicationExceptions)e.getCause();
                if(e.getCause() instanceof ApplicationException) {
                    ApplicationExceptions aes = new ApplicationExceptions();
                    aes.add( (ApplicationException)e.getCause() );
                    throw aes;
                }
            }
            MouldException me = new MouldException(MouldException.INVOCATION_ERROR, objectPath, e );
            log.error(me.getLocalizedMessage(),me.getCause());
            throw me;
        } catch (InstantiationException e) {
            MouldException me = new MouldException(MouldException.INSTANTICATION_ERROR, objectPath, e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            throw me;
        }
    }



    private static Object newDAO(Class clazz) throws InstantiationException {
        try {
            Constructor c = clazz.getConstructor(new Class[] {});
            if(c==null)
                throw new InstantiationException("No zero argument construtor found");
            Object dao = c.newInstance( (Object[]) null);
            log.debug("Created Object : " + dao);
            return dao;
        } catch (InstantiationException e) {
            throw e;
        } catch (Exception e) {
            log.error("Can't create DAO object - " + e.getMessage(),e);
            throw new InstantiationException(e.getMessage());
        }
    }


    /**
     * Take a source object and try and mold it back it its domain object
     * @param path The path of this object being processed. This identifies possible parent
     * and/or indexed entries where this object is contained.
     * @param source Source object to mould from, typically a DomainDAO
     * @param uow Transaction handle all creates/update will be performed within.
     * Throws an exception if null.
     * @param handler Possible bean handler to be used when processing this source object graph
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException Thrown if any runtime moulding error has occured.
     */
    public static void updateBean(String path, DomainDAO source, UOW uow, MouldHandler handler)
    throws ApplicationExceptions, FrameworkException {
        log.debug("Update Bean " + path);
        // Call custom validation code in the DAO
        source.validate();
        ApplicationExceptions aes = new ApplicationExceptions();
        if(uow==null) {
            String err = "UOW Required";
            log.error(err);
            throw new RuntimeException(err);
        }

        try {
            IPersistent domainObject = null;
            GraphMapping mapping = MappingFactory.getInstance(source);
            Map keys = new LinkedHashMap();
            Class doClass = mapping.getDomainClass();

            // Get the key fields used in the domain object
            boolean gotKeys = fillInKeys(path, source, mapping, keys);

            // read DO based on key
            if(gotKeys) {
                // get the method on the DO to read via PK
                Method[] ma = doClass.getMethods();
                Method findByPK = null;
                for(int i=0;i<ma.length;i++) {
                    if(ma[i].getName().equals("findByPK")) {
                        if(ma[i].getParameterTypes().length == (keys.size() + 1)
                        && (ma[i].getParameterTypes())[0]==UOW.class) {
                            // Found with name and correct no. of input params
                            findByPK = ma[i];
                            break;
                        }
                    }
                }
                if(findByPK == null) {
                    aes.add(new DomainObjectNotFoundException(doClass.getName() + " @ " + path));
                    throw aes;
                }

                // Build input array
                Object[] inputs = new Object[keys.size()+1];
                {
                    inputs[0] = uow;
                    int i=1;
                    for(Iterator it = keys.values().iterator();it.hasNext();i++) {
                        inputs[i] = it.next();
                    }
                }

                // Find Object based on key
                domainObject = (IPersistent) findByPK.invoke(null,inputs);

            } else
                log.debug("Object " + path + " has either missing or null key values - Assume Create is needed");


            // Create object if not found
            if(domainObject == null) {
                // NEW OBJECT, create and reflect keys
                log.debug("DO '" + mapping.getDomainClassShortName() + "' not found with key, create a new one...");
                domainObject = (IPersistent) doClass.newInstance();
                // set the key fields
                for(Iterator it = keys.keySet().iterator();it.hasNext();) {
                    String keyField = (String) it.next();
                    Object value = keys.get(keyField);
                    updateProperty(mapping.getDomainFieldDescriptor(keyField), value, domainObject);
                }
            } else {
                log.debug("Found DO '" + mapping.getDomainClassShortName() + "' with key,");
            }

            // Now update all domain fields
            updateBeanData(path, source, uow, handler, mapping, domainObject);

        } catch (IllegalAccessException e) {
            MouldException me = new MouldException(MouldException.ACCESS_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            throw me;
        } catch (InvocationTargetException e) {
            if(e.getCause()!=null) {
                if(e.getCause() instanceof FrameworkException)
                    throw (FrameworkException)e.getCause();
                if(e.getCause() instanceof ApplicationExceptions)
                    throw (ApplicationExceptions)e.getCause();
                if(e.getCause() instanceof ApplicationException) {
                    aes.add( (ApplicationException)e.getCause() );
                    throw aes;
                }
            }
            MouldException me = new MouldException(MouldException.INVOCATION_ERROR, path, e );
            log.error(me.getLocalizedMessage(),me.getCause());
            throw me;
        } catch (InstantiationException e) {
            MouldException me = new MouldException(MouldException.INSTANTICATION_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            throw me;
        }
    }



    /**
     * Take a source object and delete it or delete is children if it has any
     * @param path The path of this object being processed. This identifies possible parent
     * and/or indexed entries where this object is contained.
     * @param source Source object to mould from, typically a DomainDAO
     * @param uow Transaction handle all creates/update will be performed within.
     * Throws an exception if null.
     * @param handler Possible bean handler to be used when processing this source object graph
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException Thrown if any runtime moulding error has occured.
     */
    public static void deleteBean(String path, DomainDAO source, UOW uow, MouldHandler handler)
    throws ApplicationExceptions, FrameworkException {
        log.debug("Delete Bean " + path);
        // Call custom validation code in the DAO
        source.validate();
        ApplicationExceptions aes = new ApplicationExceptions();
        if(uow==null) {
            String err = "UOW Required";
            log.error(err);
            throw new RuntimeException(err);
        }

        try {
            IPersistent domainObject = null;
            GraphMapping mapping = MappingFactory.getInstance(source);
            Map keys = new LinkedHashMap();
            Class doClass = mapping.getDomainClass();

            // Get the key fields used in the domain object
            boolean gotKeys = fillInKeys(path, source, mapping, keys);

            //----------------------------------------------------------------
            // read DO based on key
            if(gotKeys) {
                // get the method on the DO to read via PK
                Method[] ma = doClass.getMethods();
                Method findByPK = null;
                for(int i=0;i<ma.length;i++) {
                    if(ma[i].getName().equals("findByPK")) {
                        if(ma[i].getParameterTypes().length == (keys.size() + 1)
                        && (ma[i].getParameterTypes())[0]==UOW.class) {
                            // Found with name and correct no. of input params
                            findByPK = ma[i];
                            break;
                        }
                    }
                }
                if(findByPK == null) {
                    aes.add(new DomainObjectNotFoundException(doClass.getName()));
                    throw aes;
                }

                // Build input array
                Object[] inputs = new Object[keys.size()+1];
                {
                    inputs[0] = uow;
                    int i=1;
                    for(Iterator it = keys.values().iterator();it.hasNext();i++) {
                        inputs[i] = it.next();
                    }
                }

                // Find Object based on key
                domainObject = (IPersistent) findByPK.invoke(null,inputs);

            } else
                log.debug("Object " + path + " has either missing or null key values - Assume Create is needed");
            // Error if DO not found
            if(domainObject == null) {
                String label = doClass.getName();
                // Try and use meta data to get domain objects label
                try {
                    label = PersistentHelper.getLabelToken(doClass.getName());
                } catch (Exception e) {
                    // ignore any problem trying to get the label!
                }
                aes.add(new DomainObjectNotFoundException(label + " (path=" + path + ")"));
                throw aes;
            }

            // Process the delete, either on this DO, or a related DO if there is one
            deleteBeanData(path, source, uow, handler, mapping, domainObject);

        } catch (IllegalAccessException e) {
            MouldException me = new MouldException(MouldException.ACCESS_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            throw me;
        } catch (InvocationTargetException e) {
            if(e.getCause()!=null) {
                if(e.getCause() instanceof FrameworkException)
                    throw (FrameworkException)e.getCause();
                if(e.getCause() instanceof ApplicationExceptions)
                    throw (ApplicationExceptions)e.getCause();
                if(e.getCause() instanceof ApplicationException) {
                    aes.add( (ApplicationException)e.getCause() );
                    throw aes;
                }
            }
            MouldException me = new MouldException(MouldException.INVOCATION_ERROR, path, e );
            log.error(me.getLocalizedMessage(),me.getCause());
            throw me;
        } catch (InstantiationException e) {
            MouldException me = new MouldException(MouldException.INSTANTICATION_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            throw me;
        }
//        } catch (Exception e) {
//            throw handleException(e,aes);
//        }
    }

    /** Pass in an emty map and it fills it with Key = Value for the source
     * object. It returns false if one or more key values are null, or if this
     * object has no keys defined
     */
    private static boolean fillInKeys(String path, DomainDAO source, GraphMapping mapping, Map map)
        throws InvocationTargetException, MouldException {
        try{
            Set keys = mapping.getKeyFields();
            boolean nullKey=false;
            if(keys == null || keys.size() == 0) {
                log.debug("Object Has No KEYS! - " + source.getClass().getName());
                return false;
            }
            // Loop through all the keys get het the values
            for(Iterator k = keys.iterator(); k.hasNext();){
                String keyField = (String)k.next();
                PropertyDescriptor pd = mapping.getDataFieldDescriptor(keyField);
                if(pd!=null && pd.getReadMethod()!=null) {
                    Method m = pd.getReadMethod();
                    if(!m.isAccessible()) m.setAccessible(true);
                    Object value = m.invoke(source,new Object[] {});
                    map.put(keyField,value);
                    log.debug("Key "+keyField+"='"+value+"' on object '"+source.getClass().getName()+"'");
                    if(value==null) {
                        nullKey=true;
                    }
                } else {
                    MouldException me = new MouldException(MouldException.NO_KEY_ON_OBJECT, path, keyField, source.getClass().getName());
                    log.error(me.getLocalizedMessage());
                    throw me;
                }
            }
            return !nullKey;
        } catch (IllegalAccessException e) {
            MouldException me = new MouldException(MouldException.ACCESS_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            throw me;
//        } catch (InvocationTargetException e) {
//            MouldException me = new MouldException(MouldException.INVOCATION_ERROR, path, e );
//            log.error(me.getLocalizedMessage(),me.getCause());
//            throw me;
        }
    }



    private static void updateBeanData(String path, DomainDAO source, UOW uow, MouldHandler handler,
                                     GraphMapping mapping, IPersistent domainObject)
    throws InstantiationException, IllegalAccessException, InvocationTargetException,
           ApplicationExceptions, FrameworkException {

        try {
            //----------------------------------------------------------------
            // Fire 'startBean' handler
            if(handler!=null)
                handler.startBean(path, source, domainObject);


            //----------------------------------------------------------------
            // Reflect all normal fields
            for(Iterator it = mapping.getFields().iterator();it.hasNext();) {
                String field = (String) it.next();
                if(source.hasChanged(field)) {
                    Object value = getProperty(mapping.getDataFieldDescriptor(field), source);
                    //if(value!=null)
                        updateProperty(mapping.getDomainFieldDescriptor(field), value, domainObject);
                }
            }


            //----------------------------------------------------------------
            // Reflect any foreign keys
            for(Iterator it = mapping.getForeignFields().iterator();it.hasNext();) {
                String field = (String) it.next();
                if(source.hasChanged(field)) {
                    Object value = getProperty(mapping.getDataFieldDescriptor(field), source);
                    if(value!=null) {
                        // need to map foreign keys back
                        List targetKeys = mapping.getForeignKeys(field);
                        GraphMapping fMapping = MappingFactory.getInstance( mapping.getDataFieldDescriptor(field).getPropertyType() );
                        Set sourceKeys = fMapping.getKeyFields();
                        int i=0;
                        for(Iterator i2 = sourceKeys.iterator(); i2.hasNext();i++) {
                            String sourceFld = (String)i2.next();
                            String targetFld = (String) targetKeys.get(i);
                            log.debug("Copy Foreign Key Field from " + sourceFld + " to " + targetFld);
                            Object value2 = getProperty(fMapping.getDataFieldDescriptor(sourceFld), value);
                            updateProperty(mapping.getRealDomainFieldDescriptor(targetFld), value2, domainObject);
                        }
                    }
                }
            }


            //----------------------------------------------------------------
            // Store Record
            if(domainObject.isDatabaseOccurence()) {
                log.debug("UOW.Update Domain Object");
                //----------------------------------------------------------------
                // Fire 'startBeanUpdate' handler
                if(handler!=null)
                    handler.startBeanUpdate(path, source, domainObject);
                uow.update(domainObject);
                //----------------------------------------------------------------
                // Fire 'endBeanUpdate' handler
                if(handler!=null)
                    handler.endBeanUpdate(path, source, domainObject);
            } else {
                log.debug("UOW.Add Domain Object");
                //----------------------------------------------------------------
                // Fire 'startBeanAdd' handler
                if(handler!=null)
                    handler.startBeanAdd(path, source, domainObject);
                uow.add(domainObject);
                //----------------------------------------------------------------
                // Fire 'endBeanAdd' handler
                if(handler!=null)
                    handler.endBeanAdd(path, source, domainObject);
            }

            //----------------------------------------------------------------
            // Reflect any related objects
            for(Iterator it = mapping.getRelatedFields().iterator();it.hasNext();) {
                String field = (String) it.next();
                if(source.hasChanged(field)) {
                    // Only do the update if the source object was updated!
                    Object value = getProperty(mapping.getDataFieldDescriptor(field), source);
                    if(value!=null) {
                        if(value.getClass().isArray()) {
                            // The related field is an array of objects (one-to-many)
                            Object[] values = (Object[]) value;
                            for(int i=0; i<values.length;i++) {
                                DomainDAO dao = (DomainDAO) values[i];  // Assumes its a DAO....what else could it be?
                                if(dao!=null) {
                                    updateChildBean(path+"."+field+"["+i+"]", dao, uow, handler, domainObject, mapping, field);
                                }
                            }
                        } else {
                            // Or a single Object (one-to-one)
                            DomainDAO dao = (DomainDAO) value; // Assumes its a DAO....what else could it be?
                            updateChildBean(path+"."+field, dao, uow, handler, domainObject, mapping, field);
                        }
                    }
                }
            }

            //----------------------------------------------------------------
            // Fire 'endBean' handler
            if(handler!=null)
                handler.endBean(path, source, domainObject);

        } catch (ApplicationException e) {
            ApplicationExceptions aes = new ApplicationExceptions();
            aes.add(e);
            throw aes;
        }
    }



    /**  Take a source object and try and mold it back it its domain object.
     *  This is the same as updateParent, except from the way it retrieved the
     *  record, and the way it creates a new record.
     */
    private static void updateChildBean(String path, DomainDAO source, UOW uow, MouldHandler handler,
                                   IPersistent parentDomain, GraphMapping parentMapping, String parentField)
    throws ApplicationExceptions, FrameworkException {

        log.debug("Update Child Bean " + path);
        // Call custom validation code in the DAO
        source.validate();
        ApplicationExceptions aes = new ApplicationExceptions();
        if(uow==null) {
            String err = "UOW Required";
            log.error(err);
            throw new RuntimeException(err);
        }

        String relationshipName = parentMapping.getDomainFieldName(parentField);
        if(relationshipName.endsWith("Array"))
            relationshipName = relationshipName.substring(0,relationshipName.length()-5);
        if(relationshipName.endsWith("Object"))
            relationshipName = relationshipName.substring(0,relationshipName.length()-6);

        try {

            IPersistent domainObject = null;
            GraphMapping mapping = MappingFactory.getInstance(source);
            Map keys = new LinkedHashMap();
            Class doClass = mapping.getDomainClass();
            boolean gotKeys=false;

            if(mapping.getKeyFields() == null || mapping.getKeyFields().size() == 0) {
                // No keys, must be one-to-one
                log.debug("Find 'one-to-one' object - " + path);

                // Just use the getXxxObject method to get the related domain object,
                // if there is one...
                domainObject = (IPersistent) getProperty(parentMapping.getDomainFieldDescriptor(parentField), parentDomain);
                if(domainObject==null)
                    log.debug("Not Found - " + path);
            } else {
                // Get the key fields used in the domain object. Use the findXxxxxCriteria() method,
                // then add the extra fields to the criteria object, to get the unique record.
                gotKeys = fillInKeys(path, source, mapping, keys);

                // read DO based on key
                if(gotKeys) {

                    // get the method to get the PK criteria (i.e. public Criteria findVendorSiteCriteria(); )
                    Method findCriteria = null;
                    String methodName = "find" + StringHelper.getUpper1(relationshipName) + "Criteria";
                    try {
                        findCriteria = parentDomain.getClass().getMethod(methodName, new Class[] {});
                    } catch (NoSuchMethodException e) {
                        log.error("Find method '"+methodName+"' not found!");
                    }
                    if(findCriteria == null) {
                        throw new MouldException(MouldException.METHOD_NOT_FOUND, path, methodName);
                    }

                    // Find Criteria For Related Object
                    Criteria criteria = (Criteria) findCriteria.invoke(parentDomain, new Object[] {});
                    // Add extra key info...
                    for(Iterator it = keys.keySet().iterator();it.hasNext();) {
                        String keyField = (String) it.next();
                        Object value = keys.get(keyField);
                        keyField = StringHelper.getUpper1(mapping.getDomainFieldName(keyField));
                        criteria.addCriteria(keyField, value);
                        log.debug(path+"- Add to criteria:"+keyField+"="+value);
                    }
                    // See if we get an object :-)
                    Iterator itr = uow.query(criteria).iterator();
                    if (itr.hasNext())
                        domainObject = (IPersistent) itr.next();
                    if (itr.hasNext()) {
                        // Error, multiple objects found
                        MultipleDomainObjectsFoundException m = new MultipleDomainObjectsFoundException(criteria.getTable() + " @ " + path);
                        aes.add(m);
                        throw aes;
                    }

                } else {
                    log.debug("Object " + path + " has either missing or null key values - Assume Create is needed");
                }
            }


            // Create object if not found
            if(domainObject == null) {
                // NEW OBJECT, create and reflect keys
                log.debug("DO '" + mapping.getDomainClassShortName() + "' not found with key, create a new one...");
                // find method on parent used to create object
                Method newObject = null;
                String methodName = "new" + StringHelper.getUpper1(relationshipName) + "Object";
                try {
                    newObject = parentDomain.getClass().getMethod(methodName, new Class[] {});
                } catch (NoSuchMethodException e) {
                    log.error("Method '"+methodName+"()' not found!");
                }
                if(newObject == null)
                    throw new MouldException(MouldException.METHOD_NOT_FOUND, path, methodName);

                // Call method to create object
                domainObject = (IPersistent) newObject.invoke(parentDomain, new Object[]{});

                // Set the key fields
                for(Iterator it = keys.keySet().iterator();it.hasNext();) {
                    String keyField = (String) it.next();
                    Object value = keys.get(keyField);
                    updateProperty(mapping.getDomainFieldDescriptor(keyField), value, domainObject);
                }
            } else {
                log.debug("Found DO '" + mapping.getDomainClassShortName() + "' with key,");
            }

            // Now update all domain fields
            updateBeanData(path, source, uow, handler, mapping, domainObject);

        } catch (IllegalAccessException e) {
            MouldException me = new MouldException(MouldException.ACCESS_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            throw me;
        } catch (InvocationTargetException e) {
            if(e.getCause()!=null) {
                if(e.getCause() instanceof FrameworkException)
                    throw (FrameworkException)e.getCause();
                if(e.getCause() instanceof ApplicationExceptions)
                    throw (ApplicationExceptions)e.getCause();
                if(e.getCause() instanceof ApplicationException) {
                    aes.add( (ApplicationException)e.getCause() );
                    throw aes;
                }
            }
            MouldException me = new MouldException(MouldException.INVOCATION_ERROR, path, e );
            log.error(me.getLocalizedMessage(),me.getCause());
            throw me;
        } catch (InstantiationException e) {
            MouldException me = new MouldException(MouldException.INSTANTICATION_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            throw me;
        }
    }



    /**  Take a source object and try and mold it back it its domain object.
     *  This is the same as updateParent, except from the way it retrieved the
     *  record, and the way it creates a new record.
     */
    private static void deleteChildBean(String path, DomainDAO source, UOW uow, MouldHandler handler,
                                   IPersistent parentDomain, GraphMapping parentMapping, String parentField)
    throws ApplicationExceptions, FrameworkException {

        log.debug("Delete Child Bean " + path);
        // Call custom validation code in the DAO
        source.validate();
        ApplicationExceptions aes = new ApplicationExceptions();
        if(uow==null) {
            String err = "UOW Required";
            log.error(err);
            throw new RuntimeException(err);
        }

        String relationshipName = parentMapping.getDomainFieldName(parentField);
        if(relationshipName.endsWith("Array"))
            relationshipName = relationshipName.substring(0,relationshipName.length()-5);
        if(relationshipName.endsWith("Object"))
            relationshipName = relationshipName.substring(0,relationshipName.length()-6);

        try {

            IPersistent domainObject = null;
            GraphMapping mapping = MappingFactory.getInstance(source);
            Map keys = new LinkedHashMap();
            Class doClass = mapping.getDomainClass();
            boolean gotKeys=false;

            if(mapping.getKeyFields() == null || mapping.getKeyFields().size() == 0) {
                // No keys, must be one-to-one
                log.debug("Find 'one-to-one' object - " + path);

                // Just use the getXxxObject method to get the related domain object,
                // if there is one...
                domainObject = (IPersistent) getProperty(parentMapping.getDomainFieldDescriptor(parentField), parentDomain);
                if(domainObject==null)
                    log.debug("Not Found - " + path);
            } else {
                // Get the key fields used in the domain object. Use the findXxxxxCriteria() method,
                // then add the extra fields to the criteria object, to get the unique record.
                gotKeys = fillInKeys(path, source, mapping, keys);

                // read DO based on key
                if(gotKeys) {
                    // get the method to get the PK criteria (i.e. public Criteria findVendorSiteCriteria(); )
                    Method findCriteria = null;
                    String methodName = "find" + StringHelper.getUpper1(relationshipName) + "Criteria";
                    try {
                        findCriteria = parentDomain.getClass().getMethod(methodName, new Class[] {});
                    } catch (NoSuchMethodException e) {
                        log.error("Find method '"+methodName+"' not found!");
                    }
                    if(findCriteria == null)
                        throw new MouldException(MouldException.METHOD_NOT_FOUND, path, methodName);

                    // Find Criteria For Related Object
                    Criteria criteria = (Criteria) findCriteria.invoke(parentDomain, new Object[] {});
                    // Add extra key info...
                    for(Iterator it = keys.keySet().iterator();it.hasNext();) {
                        String keyField = (String) it.next();
                        Object value = keys.get(keyField);
                        keyField = StringHelper.getUpper1(mapping.getDomainFieldName(keyField));
                        criteria.addCriteria(keyField, value);
                        log.debug(path+"- Add to criteria:"+keyField+"="+value);
                    }
                    // See if we get an object :-)
                    Iterator itr = uow.query(criteria).iterator();
                    if (itr.hasNext())
                        domainObject = (IPersistent) itr.next();
                    if (itr.hasNext()) {
                        // Error, multiple objects found
                        MultipleDomainObjectsFoundException m = new MultipleDomainObjectsFoundException(criteria.getTable()+" @ "+path);
                        aes.add(m);
                        throw aes;
                    }
                }

            }
            // Error if DO not found
            if(domainObject == null) {
                aes.add(new DomainObjectNotFoundException(doClass.getName() + " @ " + path));
                throw aes;
            }

            // Process the delete, either on this DO, or a related DO if there is one
            deleteBeanData(path, source, uow, handler, mapping, domainObject);

        } catch (IllegalAccessException e) {
            MouldException me = new MouldException(MouldException.ACCESS_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            throw me;
        } catch (InvocationTargetException e) {
            if(e.getCause()!=null) {
                if(e.getCause() instanceof FrameworkException)
                    throw (FrameworkException)e.getCause();
                if(e.getCause() instanceof ApplicationExceptions)
                    throw (ApplicationExceptions)e.getCause();
                if(e.getCause() instanceof ApplicationException) {
                    aes.add( (ApplicationException)e.getCause() );
                    throw aes;
                }
            }
            MouldException me = new MouldException(MouldException.INVOCATION_ERROR, path, e );
            log.error(me.getLocalizedMessage(),me.getCause());
            throw me;
        } catch (InstantiationException e) {
            MouldException me = new MouldException(MouldException.INSTANTICATION_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(),e);
            throw me;
        }
    }

    private static void deleteBeanData(String path, DomainDAO source, UOW uow, MouldHandler handler,
                                       GraphMapping mapping, IPersistent domainObject)
    throws InstantiationException, IllegalAccessException, InvocationTargetException,
           ApplicationExceptions, FrameworkException {
        try {

            //----------------------------------------------------------------
            // Fire 'startBean' handler
            if(handler!=null)
                handler.startBean(path, source, domainObject);

            //----------------------------------------------------------------
            // Now loop through children, if there is one, delete it, and leave parent alone
            boolean deleteChild = false;

            // Reflect any related objects
            for(Iterator it = mapping.getRelatedFields().iterator();it.hasNext();) {
                String field = (String) it.next();
                if(source.hasChanged(field)) {
                    Object value = getProperty(mapping.getDataFieldDescriptor(field), source);
                    if(value!=null) {
                        if(value.getClass().isArray()) {
                            // The related field is an array of objects (one-to-many)
                            Object[] values = (Object[]) value;
                            for(int i=0; i<values.length;i++) {
                                DomainDAO dao = (DomainDAO) values[i];  // Assumes its a DAO....what else could it be?
                                if(dao!=null) {
                                    deleteChild = true;
                                    deleteChildBean(path+"."+field+"["+i+"]", dao, uow, handler, domainObject, mapping, field);
                                }
                            }
                        } else {
                            // The related field is a single object (one-to-many)
                            DomainDAO dao = (DomainDAO) value; // Assumes its a DAO....what else could it be?
                            deleteChild = true;
                            deleteChildBean(path+"."+field, dao, uow, handler, domainObject, mapping, field);
                        }
                    }
                }
            }

            //----------------------------------------------------------------
            // Delete this record, as it had no children
            if(!deleteChild) {
                log.debug("UOW.Delete Domain Object");
                // Fire 'startBeanDelete' handler
                if(handler!=null)
                    handler.startBeanDelete(path, source, domainObject);

                uow.delete(domainObject);

                // Fire 'endBeanDelete' handler
                if(handler!=null)
                    handler.endBeanDelete(path, source, domainObject);
            }



            //----------------------------------------------------------------
            // Fire 'endBean' handler
            if(handler!=null)
                handler.endBean(path, source, domainObject);

        } catch (ApplicationException e) {
            ApplicationExceptions aes = new ApplicationExceptions();
            aes.add(e);
            throw aes;
        }
    }



    private static void setProperty (PropertyDescriptor pd, Object value, Object source)
    throws IllegalAccessException, InvocationTargetException, MouldException {
        if(pd!=null && pd.getWriteMethod()!=null) {
            Method m = pd.getWriteMethod();
            if(!m.isAccessible()) m.setAccessible(true);
            Class tClass = m.getParameterTypes()[0];
            if(value==null || tClass.isAssignableFrom(value.getClass())) {
                m.invoke(source, new Object[] {value});
                log.debug("Set property '" + pd.getName() + "=" + value + "' on object '" + source.getClass().getName() + "'");
            } else if(DataTypeMapper.instance().isMappable(value.getClass(),tClass)) {
                // See if there is a datatype mapper for these classes
                value = DataTypeMapper.instance().map(value, tClass);
                m.invoke(source, new Object[] {value});
                log.debug("Translate+Set property '" + pd.getName() + "=" + value + "' on object '" + source.getClass().getName() + "'");
            } else {
                // Data type mismatch
                throw new MouldException(MouldException.DATATYPE_MISMATCH, source.getClass().getName() + "." + m.getName(), tClass.getName(), value.getClass().getName());
            }
        } else {
            MouldException me = new MouldException(MouldException.NO_SETTER, null,
                                pd==null?"???":pd.getName(), source.getClass().getName());
            log.error(me.getLocalizedMessage());
            throw me;
        }
    }

    private static Object getProperty (PropertyDescriptor pd, Object source)
    throws IllegalAccessException, InvocationTargetException, MouldException {
        if(pd!=null && pd.getReadMethod()!=null) {
            Method m = pd.getReadMethod();
            if(!m.isAccessible()) m.setAccessible(true);
            Object value =  m.invoke(source,new Object[] {});
            log.debug("Get property '" + pd.getName() + "=" + value + "' on object '" + source.getClass().getName() + "'");
            return value;
        } else {
            MouldException me = new MouldException(MouldException.NO_GETTER, null,
                                pd==null?"???":pd.getName(), source.getClass().getName());
            log.error(me.getLocalizedMessage());
            throw me;
        }
    }

    /** Set a value on a Bean, if its a persistent bean, try to use an update method first
     * (for v1.0 domain objects), otherwise use the setter (for v1.1 and above).
     */
    private static void updateProperty (PropertyDescriptor pd, Object value, Object source)
    throws IllegalAccessException, InvocationTargetException, MouldException {
        if(source instanceof Persistent) {
            if(pd!=null && pd.getWriteMethod()!=null) {
                try {
                    Method m = source.getClass().getMethod("update" + StringHelper.getUpper1(pd.getName()), pd.getWriteMethod().getParameterTypes());
                    if(!m.isAccessible()) m.setAccessible(true);
                    Class tClass = m.getParameterTypes()[0];
                    if(value==null || tClass.isAssignableFrom(value.getClass())) {
                        m.invoke(source, new Object[] {value});
                        log.debug("Update property '" + pd.getName() + "=" + value + "' on object '" + source.getClass().getName() + "'");
                    // See if there is a datatype mapper for these classes
                    } else if(DataTypeMapper.instance().isMappable(value.getClass(),tClass)) {
                        value = DataTypeMapper.instance().map(value, tClass);
                        m.invoke(source, new Object[] {value});
                        log.debug("Translate+Update property '" + pd.getName() + "=" + value + "' on object '" + source.getClass().getName() + "'");
                    } else {
                        // Data type mismatch
                        throw new MouldException(MouldException.DATATYPE_MISMATCH, source.getClass().getName() + "." + m.getName(), tClass.getName(), value.getClass().getName());
                    }
                } catch (NoSuchMethodException e) {
                    log.debug("No Updator, try Setter for DO property '" + pd.getName() + "' on object '" + source.getClass().getName() + "'");
                    // try to use the setter if there is no update method
                    setProperty(pd,value,source);
                }
            } else {
                MouldException me = new MouldException(MouldException.NO_SETTER, null,
                                    pd==null?"???":pd.getName(), source.getClass().getName());
                log.error(me.getLocalizedMessage());
                throw me;
            }
        } else
            setProperty(pd,value,source);
    }




}
