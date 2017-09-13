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
 * DataTransformer.java
 *
 */
package org.jaffa.soa.dataaccess;

import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;
import org.jaffa.exceptions.*;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.flexfields.FlexProperty;
import org.jaffa.flexfields.IFlexFields;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.soa.graph.GraphCriteria;
import org.jaffa.soa.graph.GraphDataObject;
import org.jaffa.util.ExceptionHelper;

/**
 * Bean Moudler is used to mapp data between two Java Beans via a mapping file.
 * It has been specifcially coded to map between benas that extend/implement
 * GraphDataObject and IPersistent for marshalling data to and from the database.
 *
 * @author PaulE
 * @version 1.0
 * @TODO - Use nested query clauses for limiting the results of sub queries
 * @TODO - Stop using the getXxxArray() methods for subQueries, do the query in here
 * @TODO - Implement the objectStart on the subQueries
 */
public class DataTransformer {

    private static Logger log = Logger.getLogger(DataTransformer.class);
    private static final Pattern RELATED_OBJECT_GETTER = Pattern.compile("get(.+)Array");

    enum Mode {VALIDATE_ONLY, CLONE, MASS_UPDATE}

    /**
     * Mould data from domain object and its related objects into a new JavaBean based
     * domain object graph, based on the defined mapping rules.
     * <p/>
     * Same as {@link #buildGraphFromDomain(Object source, Object target, GraphMapping graph, MappingFilter filter, String objectPath, boolean includeKeys)}
     * except the graph is derived from the target object, a default MappingFilter (that
     * just returns fields from the root object) and includeKeys is false.
     * The objectPath is also null, assuming this is the first object in the domain
     * object graph.
     * <p/>
     *
     * @param source Source object to mould data from, typically extends Persistent
     * @param target Target object to mould data to, typically extends GraphDataObject
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException    Thrown if any runtime moulding error has occured.
     */
    public static void buildGraphFromDomain(Object source, Object target)
            throws ApplicationExceptions, FrameworkException {
        buildGraphFromDomain(source, target, null, null);
    }

    /**
     * Mould data from domain object and its related objects into a new JavaBean based
     * domain object graph, based on the defined mapping rules.
     * <p/>
     * Same as {@link #buildGraphFromDomain(Object source, Object target, GraphMapping graph, MappingFilter filter, String objectPath, boolean includeKeys)}
     * except the graph is derived from the target object, and includeKeys is false.
     * <p/>
     *
     * @param source     Source object to mould data from, typically extends Persistent
     * @param target     Target object to mould data to, typically extends GraphDataObject
     * @param filter     Filter object that it is used to control what fields are populated or the target objects
     * @param objectPath The path of this object being processed. This identifies possible parent
     *                   and/or indexed entries where this object is contained.
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException    Thrown if any runtime moulding error has occured.
     */
    public static void buildGraphFromDomain(Object source, Object target,
                                            MappingFilter filter, String objectPath)
            throws ApplicationExceptions, FrameworkException {
        buildGraphFromDomain(source, target, null, filter, objectPath, false);
    }

    /**
     * Mould data from domain object and its related objects into a new JavaBean based
     * domain object graph, based on the defined mapping rules.
     *
     * @param source      Source object to mould data from, typically extends Persistent
     * @param target      Target object to mould data to, typically extends GraphDataObject
     * @param graph       The mapping class with the rules of how to map this source object
     * @param filter      Filter object that it is used to control what fields are populated or the target objects
     * @param objectPath  The path of this object being processed. This identifies possible parent
     *                    and/or indexed entries where this object is contained.
     * @param includeKeys true if key fields should be included in results regardless of the filters
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException    Thrown if any runtime moulding error has occured.
     */
    public static void buildGraphFromDomain(Object source, Object target, GraphMapping graph,
                                            MappingFilter filter, String objectPath, boolean includeKeys)
            throws ApplicationExceptions, FrameworkException {
        buildGraphFromDomain(source, target, graph, filter, objectPath, includeKeys, null, null);
    }

    /**
     * Mould data from domain object and its related objects into a new JavaBean based
     * domain object graph, based on the defined mapping rules.
     *
     * @param source           Source object to mould data from, typically extends Persistent
     * @param target           Target object to mould data to, typically extends GraphDataObject
     * @param graph            The mapping class with the rules of how to map this source object
     * @param filter           Filter object that it is used to control what fields are populated or the target objects
     * @param objectPath       The path of this object being processed. This identifies possible parent
     *                         and/or indexed entries where this object is contained.
     * @param includeKeys      true if key fields should be included in results regardless of the filters
     * @param originalCriteria the original graph criteria.
     * @param handler          Possible bean handler to be used when processing this source object graph
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException    Thrown if any runtime moulding error has occured.
     */
    public static void buildGraphFromDomain(Object source, Object target, GraphMapping graph,
                                            MappingFilter filter, String objectPath, boolean includeKeys,
                                            GraphCriteria originalCriteria, ITransformationHandler handler)
            throws ApplicationExceptions, FrameworkException {

        if (graph == null)
            graph = MappingFactory.getInstance(target);

        boolean useQueryDomain = graph.getQueryDomainClass() != null && source.getClass().isAssignableFrom(graph.getQueryDomainClass());

        //throw new InstantiationException("A GraphMapping must be supplied");
        if (filter == null)
            filter = MappingFilter.getInstance(graph);

        try {
            // get list of target fileds to populate
            String[] tFields = graph.getDataFieldNames();
            if (tFields != null && tFields.length != 0)
                for (int i = 0; i < tFields.length; i++) {
                    // Try to map a source to a target
                    String tName = tFields[i];
                    String fullName = tName;
                    if (objectPath != null)
                        fullName = objectPath + "." + fullName;

                    if (filter == null || filter.isFieldIncluded(fullName) || (includeKeys && graph.isKeyField(tName))) {
                        String sName = graph.getDomainFieldName(tName);
                        AccessibleObject tAccessibleObject = graph.getDataMutator(tName);
                        PropertyDescriptor tDesc = graph.getDataFieldDescriptor(tName);
                        PropertyDescriptor sDesc = useQueryDomain ? graph.getQueryDomainFieldDescriptor(tName) : graph.getDomainFieldDescriptor(tName);

                        if (useQueryDomain && sDesc == null)
                            continue;

                        // Based on validation in GraphMapping, that there is a
                        // GraphObject descriptor with a setter, and a DO descriptor with a getter
                        if (sDesc == null)
                            log.error("No Getter for " + tName + " in path " + fullName);

                        // incase getter is not public, make it available
                        Method sm = sDesc.getReadMethod();
                        if (!sm.isAccessible())
                            sm.setAccessible(true);

                        // Set the value if the source and target are the same datatype
                        Class tClass = tDesc.getPropertyType();
                        Class sClass = sDesc.getPropertyType();
                        if (tClass.isAssignableFrom(sClass)) {
                            // Get the value being copied
                            Object sValue = sm.invoke(source, (Object[]) null);
                            setValue(tAccessibleObject, target, sValue);
                            if (log.isDebugEnabled())
                                log.debug("Set " + tName + " = " + sValue);

                            // See if there is a datatype mapper for these classes
                        } else if (DataTypeMapper.instance().isMappable(sClass, tClass)) {
                            // Get the value being copied
                            Object sValue = sm.invoke(source, (Object[]) null);
                            if (sValue != null) {
                                sValue = DataTypeMapper.instance().map(sValue, tClass);
                                if (log.isDebugEnabled())
                                    log.debug("Set " + tName + " = " + sValue);
                            }
                            setValue(tAccessibleObject, target, sValue);

                            // See if target is a GraphObject, this could be a foreign object or one-to-one relationship...
                        } else if (GraphDataObject.class.isAssignableFrom(tClass) && IPersistent.class.isAssignableFrom(sClass)) {
                            // Get the mapper for the related GraphObject, if it has keys, it must be a foriegn object
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
                                        value = m.invoke(source, new Object[]{});
                                        if (value == null)
                                            nullKey = true;
                                        foreignKeyValues.add(value);
                                    } else {
                                        throw new TransformException(TransformException.INVALID_FK_MAPPING, objectPath, doProp, graph.getDomainClassShortName());
                                    }
                                }
                                if (nullKey) {
                                    if (log.isDebugEnabled())
                                        log.debug("Did not create skeleton object '" + tClass.getName() + "': one or more foreign key values missing.");
                                } else {
                                    // Create the foreign object
                                    if (log.isDebugEnabled())
                                        log.debug("Creating foreign object - " + tClass.getName());
                                    Object newGDO = newGraphDataObject(tClass);
                                    boolean createSkeleton = true;
                                    // Only retrieve related domain object and introspect if need
                                    if (filter.areSubFieldsIncluded(fullName)) {
                                        // read object and introspect all
                                        if (log.isDebugEnabled())
                                            log.debug("Read foreign object '" + fullName + "' and mold");
                                        try {
                                            Object sValue = sm.invoke(source, (Object[]) null);
                                            if (sValue != null) {
                                                DataTransformer.buildGraphFromDomain(sValue, newGDO, null, filter, fullName, true, originalCriteria, handler);
                                                createSkeleton = false;
                                            }
                                        } catch (InvocationTargetException e) {
                                            // If the foreign object is not found, create the skeleton
                                            if (e.getCause() != null && e.getCause() instanceof InvalidForeignKeyException) {
                                                if (log.isDebugEnabled())
                                                    log.debug("All foreign keys present, but foreign object does not exist", e);
                                            } else
                                                throw e;
                                        }
                                    }
                                    if (createSkeleton) {
                                        // just set foreign keys from current object
                                        if (log.isDebugEnabled())
                                            log.debug("Set keys on skeleton foreign object only");
                                        GraphMapping graph2 = MappingFactory.getInstance(newGDO);
                                        Set keys = graph2.getKeyFields();
                                        if (keys == null || keys.size() != foreignKeyValues.size()) {
                                            throw new TransformException(TransformException.MISMATCH_FK_MAPPING, objectPath, target.getClass().getName(), newGDO.getClass().getName());
                                        }
                                        int k2 = 0;
                                        // Look through all the foreign keys on the skeleton object
                                        for (Iterator k = keys.iterator(); k.hasNext(); k2++) {
                                            String keyField = (String) k.next();
                                            Object keyValue = foreignKeyValues.get(k2);
                                            AccessibleObject accessibleObject = graph2.getDataMutator(keyField);
                                            if (accessibleObject != null) {
                                                setValue(accessibleObject, newGDO, keyValue);
                                            } else {
                                                throw new TransformException(TransformException.CANT_SET_KEY_FIELD, objectPath, keyField, newGDO.getClass().getName());
                                            }
                                        }
                                    }
                                    setValue(tAccessibleObject, target, newGDO);
                                    if (log.isDebugEnabled())
                                        log.debug("Set " + tName + " = " + newGDO);
                                }
                            } else {
                                // This is not a foreign object, must be a related object
                                if (filter.areSubFieldsIncluded(fullName)) {
                                    // Create the related object
                                    if (log.isDebugEnabled())
                                        log.debug("Creating One-To-One object - " + tClass.getName());
                                    Object newGDO = newGraphDataObject(tClass);
                                    // read object and introspect all
                                    if (log.isDebugEnabled())
                                        log.debug("Read related object '" + fullName + "' and mold");
                                    Object sValue = sm.invoke(source, (Object[]) null);
                                    if (sValue != null) {
                                        DataTransformer.buildGraphFromDomain(sValue, newGDO, null, filter, fullName, false, originalCriteria, handler);
                                        setValue(tAccessibleObject, target, newGDO);
                                        if (log.isDebugEnabled())
                                            log.debug("Set " + tName + " = " + newGDO);
                                    } else {
                                        if (log.isDebugEnabled())
                                            log.debug("Related object '" + fullName + "' not found. Ignore it!");
                                    }
                                } else {
                                    if (log.isDebugEnabled())
                                        log.debug("No subfields for object " + fullName + " included. Object not retrieved");
                                }
                            }//END-related object

                            // See if Target may be an array of GraphObject's
                        } else if (tClass.isArray() && GraphDataObject.class.isAssignableFrom(tClass.getComponentType()) && filter.areSubFieldsIncluded(fullName)) {
                            if (log.isDebugEnabled())
                                log.debug("Target is an array of GraphObject's");
                            if (sClass.isArray() && IPersistent.class.isAssignableFrom(sClass.getComponentType())) {
                                if (log.isDebugEnabled()) {
                                    log.debug("Source is an array of Persistent Objects");
                                    log.debug("Read related objects '" + fullName + "' and mold");
                                }
                                Object[] sArray = findRelatedObjects(source, sClass, sm, handler, originalCriteria, fullName);
                                if (sArray != null && sArray.length > 0) {
                                    Object[] tArray = (Object[]) Array.newInstance(tClass.getComponentType(), sArray.length);
                                    if (log.isDebugEnabled())
                                        log.debug("Translate Array of Size " + sArray.length);
                                    for (int j = 0; j < sArray.length; j++) {
                                        Object newGDO = newGraphDataObject(tClass.getComponentType());
                                        DataTransformer.buildGraphFromDomain(sArray[j], newGDO, null, filter, fullName, false, originalCriteria, handler);
                                        tArray[j] = newGDO;
                                        if (log.isDebugEnabled())
                                            log.debug("Add to array [" + j + "] : " + newGDO);
                                    }
                                    setValue(tAccessibleObject, target, tArray);
                                    if (log.isDebugEnabled())
                                        log.debug("Set Array " + tName);
                                } else {
                                    if (log.isDebugEnabled())
                                        log.debug("Source Array is empty! Do Nothing");
                                }
                            } // source is DO array

                            // Error... No way to map property
                        } else {
                            String err = "Can't Mold Property " + fullName + " from " + sClass.getName() + " to " + tClass.getName();
                            log.error(err);
                            throw new RuntimeException(err);
                        }
                    } // is included in filtered fields
                }

            // Load flex fields
            // By default all the domain-mapped flex fields will be loaded; unless excluded by a rule
            if (source instanceof IFlexFields && target instanceof IFlexFields) {
                String fullName = (objectPath != null ? objectPath + '.' : "") + "flexBean";
                if (filter == null || filter.isFieldIncluded(fullName)) {
                    if (log.isDebugEnabled())
                        log.debug("Loading FlexBean " + fullName);
                    FlexBean sFlexBean = ((IFlexFields) source).getFlexBean();
                    FlexBean tFlexBean = ((IFlexFields) target).getFlexBean();
                    if (sFlexBean != null && tFlexBean != null) {
                        for (DynaProperty flexProperty : sFlexBean.getDynaClass().getDynaProperties()) {
                            String name = flexProperty.getName();
                            Boolean include = filter.includeField(fullName + '.' + name);
                            if (include != null ? include : ((FlexProperty) flexProperty).getFlexInfo().getProperty("domain-mapping") != null) {
                                Object value = sFlexBean.get(name);
                                if (value != null) {
                                    if (log.isDebugEnabled())
                                        log.debug("Loaded flex field '" + name + '=' + value + '\'');
                                    tFlexBean.set(name, value);
                                }
                            }
                        }
                    }
                }
            }

            // Clear changed fields on updated GraphObject
            if (target != null && target instanceof GraphDataObject)
                ((GraphDataObject) target).clearChanges();

            // Invoke the handler
            if (handler != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Invoking the endBeanLoad on the handler");
                }
                for (ITransformationHandler transformationHandler : handler.getTransformationHandlers()) {
                    transformationHandler.endBeanLoad(objectPath, source, target, filter, originalCriteria);
                }
            }

        } catch (ApplicationException e) {
            throw new ApplicationExceptions(e);
        } catch (IllegalAccessException e) {
            TransformException me = new TransformException(TransformException.ACCESS_ERROR, objectPath, e.getMessage());
            log.error(me.getLocalizedMessage(), e);
            throw me;
        } catch (InvocationTargetException e) {
            ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
            if (appExps != null)
                throw appExps;
            FrameworkException fe = ExceptionHelper.extractFrameworkException(e);
            if (fe != null)
                throw fe;
            TransformException me = new TransformException(TransformException.INVOCATION_ERROR, objectPath, e);
            log.error(me.getLocalizedMessage(), me.getCause());
            throw me;
        } catch (InstantiationException e) {
            TransformException me = new TransformException(TransformException.INSTANTICATION_ERROR, objectPath, e.getMessage());
            log.error(me.getLocalizedMessage(), e);
            throw me;
        }
    }

    // Create a new Graph Data Object based on a specified class that implement this abstract class
    private static Object newGraphDataObject(Class clazz) throws InstantiationException {
        try {
            Constructor c = clazz.getConstructor(new Class[]{});
            if (c == null)
                throw new InstantiationException("No zero argument construtor found");
            Object dao = c.newInstance((Object[]) null);
            if (log.isDebugEnabled())
                log.debug("Created Object : " + dao);
            return dao;
        } catch (InstantiationException e) {
            throw e;
        } catch (Exception e) {
            log.error("Can't create Graph Data Object - " + e.getMessage(), e);
            throw new InstantiationException(e.getMessage());
        }
    }

    /**
     * Take a source object and try and mold it back it its domain object
     * This method is typically used to validate a few user-entered fields
     * and to default related data back on to the source object.
     *
     * @param path    The path of this object being processed. This identifies possible parent and/or indexed entries where this object is contained.
     * @param source  Source object to mould from, typically a GraphDataObject
     * @param uow     Transaction handle all creates/update will be performed within. Throws an exception if null.
     * @param handler Possible bean handler to be used when processing this source object graph
     * @return the source object will be returned with default data.
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException    Thrown if any runtime moulding error has occured.
     */
    public static GraphDataObject prevalidateGraph(String path, GraphDataObject source, UOW uow, ITransformationHandler handler)
            throws ApplicationExceptions, FrameworkException {
        return updateGraph(path, source, uow, handler, Mode.VALIDATE_ONLY, null);
    }

    /**
     * Clones the source object and adds it to the persistent store.
     * The newGraph is used to supply values for the key fields and others.
     *
     * @param path     The path of this object being processed. This identifies possible parent and/or indexed entries where this object is contained.
     * @param source   Source object to mould from, typically a GraphDataObject
     * @param uow      Transaction handle all creates/update will be performed within. Throws an exception if null.
     * @param handler  Possible bean handler to be used when processing this source object graph
     * @param newGraph supplies values for the key fields and others.
     * @return a GraphDataObject with just the key-fields of the root object will be returned.
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException    Thrown if any runtime moulding error has occured.
     */
    public static GraphDataObject cloneGraph(String path, GraphDataObject source, UOW uow, ITransformationHandler handler,
                                             GraphDataObject newGraph) throws ApplicationExceptions, FrameworkException {
        return updateGraph(path, source, uow, handler, Mode.CLONE, newGraph);
    }

    /**
     * MassUpdates the source object in the persistent store.
     * The newGraph is used to supply non-key values.
     *
     * @param path     The path of this object being processed. This identifies possible parent and/or indexed entries where this object is contained.
     * @param source   Source object to mould from, typically a GraphDataObject
     * @param uow      Transaction handle all creates/update will be performed within. Throws an exception if null.
     * @param handler  Possible bean handler to be used when processing this source object graph
     * @param newGraph supplies non-key values.
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException    Thrown if any runtime moulding error has occured.
     */
    public static void massUpdateGraph(String path, GraphDataObject source, UOW uow, ITransformationHandler handler,
                                       GraphDataObject newGraph) throws ApplicationExceptions, FrameworkException {
        updateGraph(path, source, uow, handler, Mode.MASS_UPDATE, newGraph);
    }

    /**
     * Take a source object and try and mold it back it its domain object
     *
     * @param path    The path of this object being processed. This identifies possible parent and/or indexed entries where this object is contained.
     * @param source  Source object to mould from, typically a GraphDataObject
     * @param uow     Transaction handle all creates/update will be performed within. Throws an exception if null.
     * @param handler Possible bean handler to be used when processing this source object graph
     * @return a GraphDataObject with just the key-fields of the root object will be returned if that object was newly created. Else a null will be returned.
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException    Thrown if any runtime moulding error has occured.
     */
    public static GraphDataObject updateGraph(String path, GraphDataObject source, UOW uow, ITransformationHandler handler)
            throws ApplicationExceptions, FrameworkException {
        return updateGraph(path, source, uow, handler, null, null);
    }

    /**
     * Take a source object and try and mold it back it its domain object
     *
     * @param path    The path of this object being processed. This identifies possible parent and/or indexed entries where this object is contained.
     * @param source  Source object to mould from, typically a GraphDataObject
     * @param uow     Transaction handle all creates/update will be performed within. Throws an exception if null.
     * @param handler Possible bean handler to be used when processing this source object graph
     * @return In VALIDATE_ONLY mode the source object will be returned with default data. Else a GraphDataObject with just the key-fields of the root object will be returned if that object was newly created. Else a null will be returned.
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException    Thrown if any runtime moulding error has occured.
     */
    private static GraphDataObject updateGraph(String path, GraphDataObject source, UOW uow, ITransformationHandler handler,
                                               Mode mode, GraphDataObject newGraph) throws ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled())
            log.debug("Update Bean " + path);
        if (source.getDeleteObject() != null && source.getDeleteObject()) {
            if (mode == Mode.VALIDATE_ONLY) {
                if (log.isDebugEnabled())
                    log.debug("The 'deleteObject' property is true. No prevalidations will be performed. The input object will be returned as is.");
                return source;
            } else {
                if (log.isDebugEnabled())
                    log.debug("The 'deleteObject' property is true. Invoking deleteGraph()");
                deleteGraph(path, source, uow, handler);
                return null;
            }
        } else {
            try {
                IPersistent domainObject = null;
                GraphMapping mapping = MappingFactory.getInstance(source);
                Map keys = new LinkedHashMap();
                Class doClass = mapping.getDomainClass();

                // Get the key fields used in the domain object
                // In CLONE mode, get the keys from the new graph, and force the creation of the domain object
                boolean gotKeys = false;
                if (mode == Mode.CLONE) {
                    if (newGraph != null)
                        gotKeys = TransformerUtils.fillInKeys(path, newGraph, mapping, keys);
                } else
                    gotKeys = TransformerUtils.fillInKeys(path, source, mapping, keys);

                // read DO based on key
                if (gotKeys) {
                    // get the method on the DO to read via PK
                    Method[] ma = doClass.getMethods();
                    Method findByPK = null;
                    for (int i = 0; i < ma.length; i++) {
                        if (ma[i].getName().equals("findByPK")) {
                            if (ma[i].getParameterTypes().length == (keys.size() + 1) && (ma[i].getParameterTypes())[0] == UOW.class) {
                                // Found with name and correct no. of input params
                                findByPK = ma[i];
                                break;
                            }
                        }
                    }
                    if (findByPK == null)
                        throw new ApplicationExceptions(new DomainObjectNotFoundException(TransformerUtils.findDomainLabel(doClass)));

                    // Build input array
                    Object[] inputs = new Object[keys.size() + 1];
                    {
                        inputs[0] = uow;
                        int i = 1;
                        for (Iterator it = keys.values().iterator(); it.hasNext(); i++) {
                            inputs[i] = it.next();
                        }
                    }

                    // Find Object based on key
                    domainObject = (IPersistent) findByPK.invoke(null, inputs);

                    if (domainObject != null && mode == Mode.CLONE)
                        throw new ApplicationExceptions(new DuplicateKeyException(TransformerUtils.findDomainLabel(doClass)));

                } else {
                    if (log.isDebugEnabled())
                        log.debug("Object " + path + " has either missing or null key values - Assume Create is needed");
                }

                // Create object if not found
                boolean createMode = false;
                if (domainObject == null) {
                    // In MASS_UPDATE mode, error if DO not found
                    if (mode == Mode.MASS_UPDATE)
                        throw new ApplicationExceptions(new DomainObjectNotFoundException(TransformerUtils.findDomainLabel(doClass)));

                    // NEW OBJECT, create and reflect keys
                    if (log.isDebugEnabled())
                        log.debug("DO '" + mapping.getDomainClassShortName() + "' not found with key, create a new one...");
                    domainObject = (IPersistent) doClass.newInstance();
                    // set the key fields
                    for (Iterator it = keys.keySet().iterator(); it.hasNext(); ) {
                        String keyField = (String) it.next();
                        if (mapping.isReadOnly(keyField))
                            continue;
                        Object value = keys.get(keyField);
                        TransformerUtils.updateProperty(mapping.getDomainFieldDescriptor(keyField), value, domainObject);
                    }
                    createMode = true;
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Found DO '" + mapping.getDomainClassShortName() + "' with key,");
                }

                // Now update all domain fields
                TransformerUtils.updateBeanData(path, source, uow, handler, mapping, domainObject, mode, newGraph);

                // Invoke the changeDone trigger
                if (handler != null && handler.isChangeDone()) {
                    for (ITransformationHandler transformationHandler : handler.getTransformationHandlers()) {
                        transformationHandler.changeDone(path, source, domainObject, uow);
                    }
                }

                // Return an appropriate output
                if (mode == Mode.VALIDATE_ONLY) {
                    // In VALIDATE_ONLY mode, return the input graph (with defaulted data)
                    return source;
                } else if (createMode) {
                    // In create-mode, Create a new graph and stamp just the keys
                    GraphDataObject outputGraph = source.getClass().newInstance();
                    for (Iterator i = keys.keySet().iterator(); i.hasNext(); ) {
                        String keyField = (String) i.next();
                        PropertyDescriptor pd = mapping.getDomainFieldDescriptor(keyField);
                        if (pd != null && pd.getReadMethod() != null) {
                            Method m = pd.getReadMethod();
                            if (!m.isAccessible())
                                m.setAccessible(true);
                            Object value = m.invoke(domainObject, (Object[]) null);
                            AccessibleObject accessibleObject = mapping.getDataMutator(keyField);
                            setValue(accessibleObject, outputGraph, value);
                        } else {
                            TransformException me = new TransformException(TransformException.NO_KEY_ON_OBJECT, path, keyField, source.getClass().getName());
                            log.error(me.getLocalizedMessage());
                            throw me;
                        }
                    }
                    return outputGraph;
                } else {
                    // In update-mode, return a null
                    return null;
                }
            } catch (IllegalAccessException e) {
                TransformException me = new TransformException(TransformException.ACCESS_ERROR, path, e.getMessage());
                log.error(me.getLocalizedMessage(), e);
                throw me;
            } catch (InvocationTargetException e) {
                ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                if (appExps != null)
                    throw appExps;
                FrameworkException fe = ExceptionHelper.extractFrameworkException(e);
                if (fe != null)
                    throw fe;
                TransformException me = new TransformException(TransformException.INVOCATION_ERROR, path, e);
                log.error(me.getLocalizedMessage(), me.getCause());
                throw me;
            } catch (InstantiationException e) {
                TransformException me = new TransformException(TransformException.INSTANTICATION_ERROR, path, e.getMessage());
                log.error(me.getLocalizedMessage(), e);
                throw me;
            }
        }
    }

    /**
     * Take a source object and delete it or delete is children if it has any
     *
     * @param path    The path of this object being processed. This identifies possible parent
     *                and/or indexed entries where this object is contained.
     * @param source  Source object to mould from, typically a GraphDataObject
     * @param uow     Transaction handle all creates/update will be performed within.
     *                Throws an exception if null.
     * @param handler Possible bean handler to be used when processing this source object graph
     * @throws ApplicationExceptions Thrown if one or more application logic errors are generated during moulding
     * @throws FrameworkException    Thrown if any runtime moulding error has occured.
     */
    public static void deleteGraph(String path, GraphDataObject source, UOW uow, ITransformationHandler handler)
            throws ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled())
            log.debug("Delete Bean " + path);
        try {
            IPersistent domainObject = null;
            GraphMapping mapping = MappingFactory.getInstance(source);
            Map keys = new LinkedHashMap();
            Class doClass = mapping.getDomainClass();

            // Get the key fields used in the domain object
            boolean gotKeys = TransformerUtils.fillInKeys(path, source, mapping, keys);

            //----------------------------------------------------------------
            // read DO based on key
            if (gotKeys) {
                // get the method on the DO to read via PK
                Method[] ma = doClass.getMethods();
                Method findByPK = null;
                for (int i = 0; i < ma.length; i++) {
                    if (ma[i].getName().equals("findByPK")) {
                        if (ma[i].getParameterTypes().length == (keys.size() + 1) && (ma[i].getParameterTypes())[0] == UOW.class) {
                            // Found with name and correct no. of input params
                            findByPK = ma[i];
                            break;
                        }
                    }
                }
                if (findByPK == null)
                    throw new ApplicationExceptions(new DomainObjectNotFoundException(doClass.getName()));

                // Build input array
                Object[] inputs = new Object[keys.size() + 1];
                {
                    inputs[0] = uow;
                    int i = 1;
                    for (Iterator it = keys.values().iterator(); it.hasNext(); i++) {
                        inputs[i] = it.next();
                    }
                }

                // Find Object based on key
                domainObject = (IPersistent) findByPK.invoke(null, inputs);

            } else {
                if (log.isDebugEnabled())
                    log.debug("Object " + path + " has either missing or null key values - Assume Create is needed");
            }
            // Error if DO not found
            if (domainObject == null)
                throw new ApplicationExceptions(new DomainObjectNotFoundException(TransformerUtils.findDomainLabel(doClass)));

            // Process the delete, either on this DO, or a related DO if there is one
            TransformerUtils.deleteBeanData(path, source, uow, handler, mapping, domainObject);

            // Invoke the changeDone trigger
            if (handler != null && handler.isChangeDone()) {
                for (ITransformationHandler transformationHandler : handler.getTransformationHandlers()) {
                    transformationHandler.changeDone(path, source, domainObject, uow);
                }
            }
        } catch (IllegalAccessException e) {
            TransformException me = new TransformException(TransformException.ACCESS_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(), e);
            throw me;
        } catch (InvocationTargetException e) {
            ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
            if (appExps != null)
                throw appExps;
            FrameworkException fe = ExceptionHelper.extractFrameworkException(e);
            if (fe != null)
                throw fe;
            TransformException me = new TransformException(TransformException.INVOCATION_ERROR, path, e);
            log.error(me.getLocalizedMessage(), me.getCause());
            throw me;
        } catch (InstantiationException e) {
            TransformException me = new TransformException(TransformException.INSTANTICATION_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(), e);
            throw me;
        }
//        } catch (Exception e) {
//            throw handleException(e,aes);
//        }
    }

    /**
     * Retrieves the related object, as specified by the relatedObjectClass, from the input domain object.
     * It'll utilize the findXyzCriteria() method, if available, to build the query, as well as to fire the handler.
     * Else it'll utilize the getXyzArray() method, which may cache a huge number of objects.
     *
     * @param domain              The domain object against which the query is to be invoked.
     * @param relatedObjectClass  The Class of the related object.
     * @param relatedObjectGetter The getter for obtaining the array of related objects.
     * @param handler             The handler, if passed, will be invoked prior to retrieving the related object.
     * @param originalCriteria    The original Criteria used for the query. This is passed to the handler.
     * @param path                This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @return an array of related objects.
     * @throws IllegalAccessException    if the relatedObjectGetter is not accessible.
     * @throws InvocationTargetException if any error occurs during invocation of the relatedObjectGetter.
     */
    private static Object[] findRelatedObjects(Object domain, Class relatedObjectClass, Method relatedObjectGetter,
                                               ITransformationHandler handler, GraphCriteria originalCriteria, String path)
            throws IllegalAccessException, InvocationTargetException {
        // The Getter typically caches the related objects. Hence try to locate the corresponding findXyzCriteria() method
        try {
            Matcher m = RELATED_OBJECT_GETTER.matcher(relatedObjectGetter.getName());
            if (m.matches()) {
                Method criteriaMethod = domain.getClass().getMethod("find" + m.group(1) + "Criteria");
                if (Criteria.class.isAssignableFrom(criteriaMethod.getReturnType())) {
                    Criteria criteria = (Criteria) criteriaMethod.invoke(domain);

                    // Invoke the handler
                    if (handler != null) {
                        if (log.isDebugEnabled()) {
                            log.debug("Invoking the preQuery on the handler");
                        }
                        for (ITransformationHandler transformationHandler : handler.getTransformationHandlers()) {
                            Criteria handlerCriteria = transformationHandler.preQuery(path, criteria, originalCriteria, relatedObjectClass);
                            if (handlerCriteria != null) {
                                criteria = handlerCriteria;
                            }
                        }
                    }

                    if (criteria == null) {
                        // The handler will return a null if this query is not to be performed
                        return null;
                    } else {
                        UOW uow = domain instanceof IPersistent ? ((IPersistent) domain).getUOW() : null;
                        boolean localUow = uow == null;
                        try {
                            if (localUow)
                                uow = new UOW();
                            Collection col = uow.query(criteria);
                            // Iterate through the Collection to retrieve all the objects
                            for (Object o : col) {
                            }
                            return col.toArray();
                        } finally {
                            if (localUow && uow != null)
                                uow.rollback();
                        }
                    }
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Method '" + criteriaMethod.getName() + "' does not does not return a Criteria. Will invoke the method '" + relatedObjectGetter.getName() + "' directly to obtain the related object");
                }
            } else {
                if (log.isDebugEnabled())
                    log.debug("Method '" + relatedObjectGetter.getName() + "' does not match the pattern '" + RELATED_OBJECT_GETTER + "'. Will invoke the method directly to obtain the related object");
            }
        } catch (Exception e) {
            // do nothing
            if (log.isDebugEnabled())
                log.debug("Exception thrown during creation of the criteria for the related object or while retrieving it. Will invoke the method '" + relatedObjectGetter.getName() + "' directly to obtain the related object", e);
        }

        // There must have been an error in invoking the findXyzCriteria() method. Simply invoke the Getter
        return (Object[]) relatedObjectGetter.invoke(domain);
    }

    private static void setValue(AccessibleObject accessibleObject, Object target, Object value) throws IllegalAccessException, InvocationTargetException {
        if (accessibleObject instanceof Field)
            ((Field) accessibleObject).set(target, value);
        else
            ((Method) accessibleObject).invoke(target, new Object[]{value});
    }
}
