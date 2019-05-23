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
 * TransformerUtils.java
 */
package org.jaffa.soa.dataaccess;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptionWithContext;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.DomainObjectChangedException;
import org.jaffa.exceptions.DomainObjectNotFoundException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.MultipleDomainObjectsFoundException;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.flexfields.IFlexFields;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.Persistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.soa.graph.GraphDataObject;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.ExceptionHelper;
import org.jaffa.util.StringHelper;

/**
 * Utility Methods used by the Data Transformer
 *
 * @author PaulE
 * @version 1.0
 */
public class TransformerUtils {

    private static Logger log = Logger.getLogger(TransformerUtils.class);

    /**
     * Display the properties of this JavaBean. If this bean has properties that implement
     * either GraphDataObject or GraphDataObject[], then also print this objects too.
     *
     * @param source Javabean who's contents should be printed
     * @return multi-line string of this beans properties and their values
     */
    public static String printGraph(Object source) {
        return printGraph(source, null);
    }

    /**
     * Same as printGraph(Object source), except the objectStack lists all the parent
     * objects its printed, and if this is one of them, it stops. This allows detection
     * of possible infinite recusion.
     *
     * @param source      Javabean who's contents should be printed
     * @param objectStack List of objects already traversed
     * @return multi-line string of this beans properties and their values
     */
    public static String printGraph(Object source, List objectStack) {
        if (source == null)
            return null;

        // Prevent infinite object recursion
        if (objectStack != null)
            if (objectStack.contains(source))
                return "Object Already Used. " + source.getClass().getName() + '@' + source.hashCode();
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
            if (sDescriptors != null && sDescriptors.length != 0)
                for (int i = 0; i < sDescriptors.length; i++) {
                    PropertyDescriptor sDesc = sDescriptors[i];
                    Method sm = sDesc.getReadMethod();
                    if (sm != null && sDesc.getWriteMethod() != null) {
                        if (!sm.isAccessible())
                            sm.setAccessible(true);
                        Object sValue = sm.invoke(source, (Object[]) null);

                        out.append("  ");
                        out.append(sDesc.getName());
                        if (source instanceof GraphDataObject) {
                            if (((GraphDataObject) source).hasChanged(sDesc.getName()))
                                out.append('*');
                        }
                        out.append('=');
                        if (sValue == null)
                            out.append("<--NULL-->\n");
                        else if (sm.getReturnType().isArray() && !sm.getReturnType().getComponentType().isPrimitive()) {
                            StringBuffer out2 = new StringBuffer();
                            out2.append("Array of ");
                            out2.append(sm.getReturnType().getComponentType().getName());
                            out2.append("\n");
                            // Loop through array
                            Object[] a = (Object[]) sValue;
                            for (int j = 0; j < a.length; j++) {
                                out2.append('[');
                                out2.append(j);
                                out2.append("] ");
                                if (a[j] == null)
                                    out2.append("<--NULL-->");
                                else if (GraphDataObject.class.isAssignableFrom(a[j].getClass()))
                                    out2.append(((GraphDataObject) a[j]).toString(objectStack));
                                else
                                    //out2.append(StringHelper.linePad(a[j].toString(), 4, " ",true));
                                    out2.append(a[j].toString());
                            }
                            out.append(StringHelper.linePad(out2.toString(), 4, " ", true));
                        } else {
                            if (GraphDataObject.class.isAssignableFrom(sValue.getClass()))
                                out.append(StringHelper.linePad(((GraphDataObject) sValue).toString(objectStack), 4, " ", true));
                            else {
                                out.append(StringHelper.linePad(sValue.toString(), 4, " ", true));
                                out.append("\n");
                            }

                        }
                    }
                }
        } catch (IllegalAccessException e) {
            TransformException me = new TransformException(TransformException.ACCESS_ERROR, "???", e.getMessage());
            log.error(me.getLocalizedMessage(), e);
            //throw me;
        } catch (InvocationTargetException e) {
            TransformException me = new TransformException(TransformException.INVOCATION_ERROR, "???", e);
            log.error(me.getLocalizedMessage(), me.getCause());
            //throw me;
        } catch (IntrospectionException e) {
            TransformException me = new TransformException(TransformException.INTROSPECT_ERROR, "???", e.getMessage());
            log.error(me.getLocalizedMessage(), e);
            //throw me;
        }
        return out.toString();
    }

    /**
     * Display the properties of this JavaBean in XML format.
     *
     * @param source Javabean who's contents should be printed
     * @return XML formatted string of this beans properties and their values
     */
    public static String printXMLGraph(Object source) {

        StringBuffer out = new StringBuffer();
        out.append("<" + source.getClass().getSimpleName() + ">");

        try {
            BeanInfo sInfo = Introspector.getBeanInfo(source.getClass());
            PropertyDescriptor[] sDescriptors = sInfo.getPropertyDescriptors();
            if (sDescriptors != null && sDescriptors.length != 0) {
                for (int i = 0; i < sDescriptors.length; i++) {
                    PropertyDescriptor sDesc = sDescriptors[i];
                    Method sm = sDesc.getReadMethod();
                    if (sm != null && sDesc.getWriteMethod() != null) {
                        if (!sm.isAccessible())
                            sm.setAccessible(true);
                        Object sValue = sm.invoke(source, (Object[]) null);

                        out.append("<" + sDesc.getName() + ">");

                        if (sValue != null && !sm.getReturnType().isArray()
                                && !GraphDataObject.class.isAssignableFrom(sValue.getClass())) {
                            out.append(sValue.toString().trim());
                        }
                        out.append("</" + sDesc.getName() + ">");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            TransformException me = new TransformException(TransformException.ACCESS_ERROR, "???", e.getMessage());
            log.error(me.getLocalizedMessage(), e);
            //throw me;
        } catch (InvocationTargetException e) {
            TransformException me = new TransformException(TransformException.INVOCATION_ERROR, "???", e);
            log.error(me.getLocalizedMessage(), me.getCause());
            //throw me;
        } catch (IntrospectionException e) {
            TransformException me = new TransformException(TransformException.INTROSPECT_ERROR, "???", e.getMessage());
            log.error(me.getLocalizedMessage(), e);
            //throw me;
        }
        out.append("</" + source.getClass().getSimpleName() + ">");
        return out.toString();
    }

    /**
     * Pass in an empty map and it fills it with Key = Value for the source
     * object. It returns false if one or more key values are null, or if this
     * object has no keys defined
     */
    static boolean fillInKeys(String path, GraphDataObject source, GraphMapping mapping, Map map)
            throws InvocationTargetException, TransformException {
        try {
            Set keys = mapping.getKeyFields();
            boolean nullKey = false;
            if (keys == null || keys.size() == 0) {
                if (log.isDebugEnabled())
                    log.debug("Object Has No KEYS! - " + source.getClass().getName());
                return false;
            }
            // Loop through all the keys get het the values
            for (Iterator k = keys.iterator(); k.hasNext(); ) {
                String keyField = (String) k.next();
                PropertyDescriptor pd = mapping.getDataFieldDescriptor(keyField);
                if (pd != null && pd.getReadMethod() != null) {
                    Method m = pd.getReadMethod();
                    if (!m.isAccessible())
                        m.setAccessible(true);
                    Object value = m.invoke(source, new Object[]{});
                    map.put(keyField, value);
                    if (log.isDebugEnabled())
                        log.debug("Key " + keyField + "='" + value + "' on object '" + source.getClass().getName() + '\'');
                    if (value == null) {
                        nullKey = true;
                    }
                } else {
                    TransformException me = new TransformException(TransformException.NO_KEY_ON_OBJECT, path, keyField, source.getClass().getName());
                    log.error(me.getLocalizedMessage());
                    throw me;
                }
            }
            return !nullKey;
        } catch (IllegalAccessException e) {
            TransformException me = new TransformException(TransformException.ACCESS_ERROR, path, e.getMessage());
            log.error(me.getLocalizedMessage(), e);
            throw me;
//        } catch (InvocationTargetException e) {
//            TransformException me = new TransformException(TransformException.INVOCATION_ERROR, path, e );
//            log.error(me.getLocalizedMessage(),me.getCause());
//            throw me;
        }
    }

    static void updateBeanData(String path, GraphDataObject source, UOW uow, ITransformationHandler handler,
                               GraphMapping mapping, IPersistent domainObject, DataTransformer.Mode mode, GraphDataObject newGraph)
            throws InstantiationException, IllegalAccessException, InvocationTargetException,
            ApplicationExceptions, FrameworkException {

        try {
            // Call custom validation code in the GraphObject
            // The validate() method may have mandatory-rules bound to it via AOP. Hence do not invoke it during prevalidation
            // No need to invoke it during CLONING/MASS_UPDATE as well, since the source object should be unmodified
            if (mode != DataTransformer.Mode.VALIDATE_ONLY && mode != DataTransformer.Mode.CLONE && mode != DataTransformer.Mode.MASS_UPDATE)
                source.validate();

            List<ITransformationHandler> handlers = null;
            if (handler != null) {
                handlers = handler.getTransformationHandlers();
            }

            // Ensure the domain object has not been modified
            domainObjectChangedTest(path, source, mapping, domainObject);

            // Stamp the UOW on the domain object to avoid creation of separate UOWs during foreign-key validations
            if (domainObject.getUOW() == null)
                domainObject.setUOW(uow);

            // Reflect ProcessEventGraphs from newGraph to source - This will handle Pending/Warning Events during a clone/mass update
            if (newGraph != null && newGraph.getProcessEventGraphs() != null)
                source.setProcessEventGraphs(newGraph.getProcessEventGraphs());
            //----------------------------------------------------------------
            // Fire 'startBean' handler
            if (mode != DataTransformer.Mode.VALIDATE_ONLY && handlers != null) {
                for (ITransformationHandler transformationHandler : handlers) {
                    transformationHandler.startBean(path, source, domainObject);
                }
            }

            //----------------------------------------------------------------
            // Reflect all normal fields
            Set fields = mapping.getFields();

            if (fields != null) {
                for (Object o : fields) {
                    String field = (String) o;
                    // ignore read-only fields
                    if (mapping.isReadOnly(field))
                        continue;

                    // values from the newGraph take precedence in CLONE/MASS_UPDATE mode
                    if (mode == DataTransformer.Mode.CLONE) {
                        // ignore dirty-read fields, and no-cloning fields unless a value is passed in the newGraph
                        if (field.equals(mapping.getDirtyReadDataFieldName()) || (mapping.isNoCloning(field) && (
                                newGraph == null || !newGraph.hasChanged(field))))
                            continue;
                        Object value = getProperty(mapping.getDataFieldDescriptor(field),
                                                   newGraph != null && newGraph.hasChanged(field) ? newGraph : source);
                        updateProperty(mapping.getDomainFieldDescriptor(field), value, domainObject);
                    }
                    else if (mode == DataTransformer.Mode.MASS_UPDATE) {
                        if (newGraph != null && newGraph.hasChanged(field)) {
                            Object value = getProperty(mapping.getDataFieldDescriptor(field), newGraph);
                            updateProperty(mapping.getDomainFieldDescriptor(field), value, domainObject);
                        }
                    }
                    else {
                        Object value = getProperty(mapping.getDataFieldDescriptor(field), source);
                        if ((!domainObject.isDatabaseOccurence() && value != null) || source.hasChanged(field))
                            updateProperty(mapping.getDomainFieldDescriptor(field), value, domainObject);
                    }
                }
            }

            //----------------------------------------------------------------
            // Update flex fields
            if (source instanceof IFlexFields && domainObject instanceof IFlexFields) {
                if (log.isDebugEnabled())
                    log.debug("Updating flex fields for " + path);
                FlexBean sFlexBean = ((IFlexFields) source).getFlexBean();
                FlexBean tFlexBean = ((IFlexFields) domainObject).getFlexBean();
                if (sFlexBean != null && tFlexBean != null) {
                    for (DynaProperty flexProperty : tFlexBean.getDynaClass().getDynaProperties()) {
                        String name = flexProperty.getName();
                        // values from the newGraph take precedence in CLONE/MASS_UPDATE mode
                        if (mode == DataTransformer.Mode.CLONE) {
                            FlexBean nFlexBean = newGraph != null && newGraph instanceof IFlexFields ? ((IFlexFields) newGraph).getFlexBean() : null;
                            Object value = nFlexBean != null && nFlexBean.hasChanged(name) ? nFlexBean.get(name) : sFlexBean.get(name);
                            if (value != null)
                                tFlexBean.set(name, value);
                        } else if (mode == DataTransformer.Mode.MASS_UPDATE) {
                            FlexBean nFlexBean = newGraph != null && newGraph instanceof IFlexFields ? ((IFlexFields) newGraph).getFlexBean() : null;
                            if (nFlexBean != null && nFlexBean.hasChanged(name)) {
                                Object value = nFlexBean.get(name);
                                tFlexBean.set(name, value);
                            }
                        } else {
                            if (sFlexBean.hasChanged(name)) {
                                Object value = sFlexBean.get(name);
                                if (log.isDebugEnabled())
                                    log.debug("Update flex field '" + name + '=' + value + "' on object '" + domainObject.getClass().getName() + '\'');
                                tFlexBean.set(name, value);
                            } else {
                                if (log.isDebugEnabled())
                                    log.debug("Flex field '" + name + " has not changed on object " + source.getClass().getName());
                            }
                        }
                    }
                }
            }

            //----------------------------------------------------------------
            // Reflect any foreign keys
            Set foreignFields = mapping.getForeignFields();

            if (foreignFields != null) {
                for (Object foreignField : foreignFields) {
                    String field = (String) foreignField;
                    // ignore read-only fields
                    if (mapping.isReadOnly(field))
                        continue;

                    // It is possible that the foreign object may get resused, and only its fields may have been changed.
                    // Hence also invoke the hasChanged() method on the foreign object itself
                    Object value = null;
                    boolean hasChanged = false;
                    if (mode == DataTransformer.Mode.CLONE) {
                        // ignore dirty-read fields, and no-cloning fields unless a value is passed in the newGraph
                        if (field.equals(mapping.getDirtyReadDataFieldName()) || (mapping.isNoCloning(field) && (
                                newGraph == null || !newGraph.hasChanged(field))))
                            continue;
                        value = getProperty(mapping.getDataFieldDescriptor(field),
                                            newGraph != null && newGraph.hasChanged(field) ? newGraph : source);
                        hasChanged = value != null;
                    }
                    else if (mode == DataTransformer.Mode.MASS_UPDATE) {
                        if (newGraph != null && newGraph.hasChanged(field)) {
                            value = getProperty(mapping.getDataFieldDescriptor(field), newGraph);
                            hasChanged = true;
                        }
                    }
                    else {
                        value = getProperty(mapping.getDataFieldDescriptor(field), source);
                        hasChanged = (!domainObject.isDatabaseOccurence() && value != null) || source.hasChanged(field);
                    }
                    if (!hasChanged && value instanceof GraphDataObject)
                        hasChanged = ((GraphDataObject) value).hasChanged();
                    if (hasChanged) {
                        // need to map foreign keys back
                        List targetKeys = mapping.getForeignKeys(field);
                        GraphMapping fMapping =
                                MappingFactory.getInstance(mapping.getDataFieldDescriptor(field).getPropertyType());
                        Set sourceKeys = fMapping.getKeyFields();
                        int i = 0;

                        if ((targetKeys != null) && (sourceKeys != null)) {
                            for (Iterator i2 = sourceKeys.iterator(); i2.hasNext(); i++) {
                                String sourceFld = (String) i2.next();
                                String targetFld = (String) targetKeys.get(i);
                                if (log.isDebugEnabled())
                                    log.debug("Copy Foreign Key Field from " + sourceFld + " to " + targetFld);
                                if (value == null) {
                                    // ForeignGraph is null. Null out the foreign-key
                                    updateProperty(mapping.getRealDomainFieldDescriptor(targetFld), null, domainObject);
                                }
                                else {
                                    // Obtain the key-field from the ForeignGraph
                                    Object value2 = getProperty(fMapping.getDataFieldDescriptor(sourceFld), value);

                                    // Set the foreign-key, only if the key-field has been flagged as changed in the ForeignGraph.
                                    // This will allow the UI to pass in just the modified portion of a composite foreign-key, and thus
                                    // ensuring that the un-modified portion doesn't get nulled out
                                    // The check is not required while cloning
                                    if (mode == DataTransformer.Mode.CLONE || !(value instanceof GraphDataObject)
                                        || ((GraphDataObject) value).hasChanged(sourceFld))
                                        updateProperty(mapping.getRealDomainFieldDescriptor(targetFld), value2,
                                                       domainObject);
                                }
                            }
                        }

                        // Invoke the getter on the domain. An exception will be raised if the foreign-key is invalid
                        if (log.isDebugEnabled())
                            log.debug("Performing validation on the domain object for the foreign object " + mapping
                                    .getDomainFieldName(field));
                        PropertyDescriptor pd = mapping.getDomainFieldDescriptor(field);
                        if (pd != null && pd.getReadMethod() != null) {
                            Method m = pd.getReadMethod();
                            if (!m.isAccessible())
                                m.setAccessible(true);
                            m.invoke(domainObject, (Object[]) null);
                        }
                    }
                }
            }

            //----------------------------------------------------------------
            // Store Record
            if (mode == DataTransformer.Mode.VALIDATE_ONLY) {
                if (log.isDebugEnabled())
                    log.debug("Domain object will not be persisted during prevalidation. Invoking the prevalidateBean handler");
                if (handlers != null) {
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.prevalidateBean(path, source, domainObject);
                    }
                }
            } else if (domainObject.isDatabaseOccurence()) {
                if (log.isDebugEnabled())
                    log.debug("UOW.Update Domain Object");
                //----------------------------------------------------------------
                // Fire 'startBeanUpdate' handler
                if (handlers != null) {
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.startBeanUpdate(path, source, domainObject);
                    }
                }
                if (domainObject.isModified()) {
                    uow.update(domainObject);
                    if (handlers != null) {
                        for (ITransformationHandler transformationHandler : handlers) {
                            transformationHandler.setChangeDone(true);
                        }
                    }
                }
                //----------------------------------------------------------------
                // Fire 'endBeanUpdate' handler
                if (handlers != null) {
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.endBeanUpdate(path, source, domainObject);
                    }
                }
            } else {
                if (handlers != null && mode == DataTransformer.Mode.CLONE) {
                    if (log.isDebugEnabled()) {
                        log.debug("Invoke startBeanClone");
                    }
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.startBeanClone(path, source, domainObject, newGraph);
                    }
                } else if (handlers != null && mode == DataTransformer.Mode.MASS_UPDATE) {
                    if (log.isDebugEnabled()) {
                        log.debug("Invoke startBeanMassUpdate");
                    }
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.startBeanMassUpdate(path, source, domainObject, newGraph);
                    }
                }
                if (log.isDebugEnabled())
                    log.debug("UOW.Add Domain Object");
                //----------------------------------------------------------------
                // Fire 'startBeanAdd' handler
                if (handlers != null) {
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.startBeanAdd(path, source, domainObject);
                    }
                }
                uow.add(domainObject);
                if (handlers != null) {
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.setChangeDone(true);
                    }
                }
                //----------------------------------------------------------------
                // Fire 'endBeanAdd' handler
                if (handlers != null) {
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.endBeanAdd(path, source, domainObject);
                    }
                }
                if (handlers != null && mode == DataTransformer.Mode.CLONE) {
                    if (log.isDebugEnabled()) {
                        log.debug("Invoke endBeanClone");
                    }
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.endBeanClone(path, source, domainObject, newGraph);
                    }
                } else if (handlers != null && mode == DataTransformer.Mode.MASS_UPDATE) {
                    if (log.isDebugEnabled()) {
                        log.debug("Invoke endBeanMassUpdate");
                    }
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.endBeanMassUpdate(path, source, domainObject, newGraph);
                    }
                }
            }

            //----------------------------------------------------------------
            // Reflect any related objects
            Set relatedFields = mapping.getRelatedFields();

            if (relatedFields != null) {
                for (Object relatedField : relatedFields) {
                    String field = (String) relatedField;
                    if (mapping.isReadOnly(field))
                        continue;
                    Object value = null;
                    if (mode == DataTransformer.Mode.CLONE) {
                        // ignore no-cloning fields unless a value is passed in the newGraph
                        if (mapping.isNoCloning(field) && (newGraph == null || !newGraph.hasChanged(field)))
                            continue;
                        value = getProperty(mapping.getDataFieldDescriptor(field),
                                            newGraph != null && newGraph.hasChanged(field) ? newGraph : source);
                    }
                    else if (mode == DataTransformer.Mode.MASS_UPDATE) {
                        if (newGraph != null && newGraph.hasChanged(field))
                            value = getProperty(mapping.getDataFieldDescriptor(field), newGraph);
                    }
                    else
                        value = getProperty(mapping.getDataFieldDescriptor(field), source);
                    if (value != null) {
                        if (value.getClass().isArray()) {
                            // The related field is an array of objects (one-to-many)
                            Object[] values = (Object[]) value;
                            for (int i = 0; i < values.length; i++) {
                                GraphDataObject dao = (GraphDataObject) values[i];  // Assumes its a DAO....what else could it be?
                                if (dao != null) {
                                    if (dao.getDeleteObject() != null && dao.getDeleteObject()) {
                                        if (mode == DataTransformer.Mode.VALIDATE_ONLY) {
                                            if (log.isDebugEnabled())
                                                log.debug(
                                                        "The 'deleteObject' property is true. No prevalidations will be performed for the childBean.");
                                        }
                                        else {
                                            if (log.isDebugEnabled())
                                                log.debug(
                                                        "The 'deleteObject' property is true. Invoking deleteChildBean()");
                                            deleteChildBean(path + '.' + field + '[' + i + ']', dao, uow, handler,
                                                            domainObject, mapping, field);
                                        }
                                    }
                                    else {
                                        Object newValue = newGraph != null ?
                                                          getProperty(mapping.getDataFieldDescriptor(field), newGraph) :
                                                          null;
                                        GraphDataObject newDao =
                                                newValue != null && ((GraphDataObject[]) newValue).length > i ?
                                                ((GraphDataObject[]) newValue)[i] :
                                                null;
                                        updateChildBean(path + '.' + field + '[' + i + ']', dao, uow, handler,
                                                        domainObject, mapping, field, mode, newDao);
                                    }
                                }
                            }
                        }
                        else {
                            // Or a single Object (one-to-one)
                            GraphDataObject dao = (GraphDataObject) value; // Assumes its a DAO....what else could it be?
                            if (dao.getDeleteObject() != null && dao.getDeleteObject()) {
                                if (mode == DataTransformer.Mode.VALIDATE_ONLY) {
                                    if (log.isDebugEnabled())
                                        log.debug(
                                                "The 'deleteObject' property is true. No prevalidations will be performed for the childBean.");
                                }
                                else {
                                    if (log.isDebugEnabled())
                                        log.debug("The 'deleteObject' property is true. Invoking deleteChildBean()");
                                    deleteChildBean(path + '.' + field, dao, uow, handler, domainObject, mapping, field);
                                }
                            }
                            else {
                                GraphDataObject newDao = newGraph != null ?
                                                         (GraphDataObject) getProperty(mapping.getDataFieldDescriptor(field), newGraph) :
                                                         null;
                                updateChildBean(path + '.' + field, dao, uow, handler, domainObject, mapping, field,
                                                mode, newDao);
                            }
                        }
                    }
                }
            }

            //----------------------------------------------------------------
            // Fire 'endBean' handler
            if (mode != DataTransformer.Mode.VALIDATE_ONLY && handlers != null) {
                for (ITransformationHandler transformationHandler : handlers) {
                    transformationHandler.endBean(path, source, domainObject);
                }
            }

        } catch (SkipTransformException e) {
            if (log.isDebugEnabled()) {
                log.debug("Processing of " + path + " will be skipped", e);
            }
        } catch (ApplicationException e) {
            throw new ApplicationExceptions(e);
        }
    }

    /**
     * Take a source object and try and mold it back it its domain object.
     * This is the same as updateParent, except from the way it retrieved the
     * record, and the way it creates a new record.
     */
    static void updateChildBean(String path, GraphDataObject source, UOW uow, ITransformationHandler handler,
                                IPersistent parentDomain, GraphMapping parentMapping, String parentField, DataTransformer.Mode mode,
                                GraphDataObject newGraph) throws ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled())
            log.debug("Update Child Bean " + path);
        String relationshipName = parentMapping.getDomainFieldName(parentField);
        if (relationshipName.endsWith("Array"))
            relationshipName = relationshipName.substring(0, relationshipName.length() - 5);
        if (relationshipName.endsWith("Object"))
            relationshipName = relationshipName.substring(0, relationshipName.length() - 6);

        try {

            IPersistent domainObject = null;
            GraphMapping mapping = MappingFactory.getInstance(source);
            Map keys = new LinkedHashMap();
            Class doClass = mapping.getDomainClass();
            boolean gotKeys = false;

            // The path for a one-to-many relationship ends with a "[i]". The absence of that suffix indicates a one-to-one relationship
            //// No keys, must be one-to-one
            //if (mapping.getKeyFields() == null || mapping.getKeyFields().size() == 0) {
            if (path == null || path.charAt(path.length() - 1) != ']') {
                if (log.isDebugEnabled())
                    log.debug("Find 'one-to-one' object - " + path);

                // Just use the getXxxObject method to get the related domain object,
                // if there is one...
                domainObject = (IPersistent) getProperty(parentMapping.getDomainFieldDescriptor(parentField), parentDomain);
                if (domainObject == null) {
                    if (log.isDebugEnabled())
                        log.debug("Not Found - " + path);
                }
            } else if (mode == DataTransformer.Mode.CLONE) {
                // In CLONE mode, get the keys from the new graph, and force the creation of the domain object
                if (newGraph != null)
                    fillInKeys(path, newGraph, mapping, keys);
            } else {
                // Get the key fields used in the domain object. Use the findXxxxxCriteria() method,
                // then add the extra fields to the criteria object, to get the unique record.
                gotKeys = fillInKeys(path, source, mapping, keys);

                // read DO based on key
                if (gotKeys) {

                    // get the method to get the PK criteria (i.e. public Criteria findVendorSiteCriteria(); )
                    Method findCriteria = null;
                    String methodName = "find" + StringHelper.getUpper1(relationshipName) + "Criteria";
                    try {
                        findCriteria = parentDomain.getClass().getMethod(methodName, new Class[]{});
                    } catch (NoSuchMethodException e) {
                        log.error("Find method '" + methodName + "' not found!");
                    }
                    if (findCriteria == null)
                        throw new TransformException(TransformException.METHOD_NOT_FOUND, path, methodName);

                    // Find Criteria For Related Object
                    Criteria criteria = (Criteria) findCriteria.invoke(parentDomain, new Object[]{});
                    // Add extra key info...
                    for (Iterator it = keys.keySet().iterator(); it.hasNext(); ) {
                        String keyField = (String) it.next();
                        Object value = keys.get(keyField);
                        keyField = StringHelper.getUpper1(mapping.getDomainFieldName(keyField));
                        criteria.addCriteria(keyField, value);
                        if (log.isDebugEnabled())
                            log.debug(path + "- Add to criteria:" + keyField + '=' + value);
                    }
                    // See if we get an object :-)
                    Iterator itr = uow.query(criteria).iterator();
                    if (itr.hasNext())
                        domainObject = (IPersistent) itr.next();
                    if (itr.hasNext()) {
                        // Error, multiple objects found
                        throw new ApplicationExceptions(new ApplicationExceptionWithContext(path, new MultipleDomainObjectsFoundException(findDomainLabel(criteria.getTable()))));
                    }

                } else {
                    if (log.isDebugEnabled())
                        log.debug("Object " + path + " has either missing or null key values - Assume Create is needed");
                }
            }

            // Create object if not found
            if (domainObject == null) {
                // In MASS_UPDATE mode, error if DO not found
                if (mode == DataTransformer.Mode.MASS_UPDATE)
                    throw new ApplicationExceptions(new ApplicationExceptionWithContext(path, new DomainObjectNotFoundException(TransformerUtils.findDomainLabel(doClass))));

                // NEW OBJECT, create and reflect keys
                if (log.isDebugEnabled())
                    log.debug("DO '" + mapping.getDomainClassShortName() + "' not found with key, create a new one...");
                // find method on parent used to create object
                Method newObject = null;
                String methodName = "new" + StringHelper.getUpper1(relationshipName) + "Object";
                try {
                    newObject = parentDomain.getClass().getMethod(methodName, new Class[]{});
                } catch (NoSuchMethodException e) {
                    log.error("Method '" + methodName + "()' not found!");
                }
                if (newObject == null)
                    throw new TransformException(TransformException.METHOD_NOT_FOUND, path, methodName);

                // Call method to create object
                domainObject = (IPersistent) newObject.invoke(parentDomain, new Object[]{});

                // Set the key fields
                for (Iterator it = keys.keySet().iterator(); it.hasNext(); ) {
                    String keyField = (String) it.next();
                    if (mapping.isReadOnly(keyField))
                        continue;
                    Object value = keys.get(keyField);
                    updateProperty(mapping.getDomainFieldDescriptor(keyField), value, domainObject);
                }
            } else {
                if (log.isDebugEnabled())
                    log.debug("Found DO '" + mapping.getDomainClassShortName() + "' with key,");
            }

            // Now update all domain fields
            updateBeanData(path, source, uow, handler, mapping, domainObject, mode, newGraph);

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

    /**
     * Take a source object and try and mold it back it its domain object.
     * This is the same as updateParent, except from the way it retrieved the
     * record, and the way it creates a new record.
     */
    static void deleteChildBean(String path, GraphDataObject source, UOW uow, ITransformationHandler handler,
                                IPersistent parentDomain, GraphMapping parentMapping, String parentField)
            throws ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled())
            log.debug("Delete Child Bean " + path);
        String relationshipName = parentMapping.getDomainFieldName(parentField);
        if (relationshipName.endsWith("Array"))
            relationshipName = relationshipName.substring(0, relationshipName.length() - 5);
        if (relationshipName.endsWith("Object"))
            relationshipName = relationshipName.substring(0, relationshipName.length() - 6);

        try {

            IPersistent domainObject = null;
            GraphMapping mapping = MappingFactory.getInstance(source);
            Map keys = new LinkedHashMap();
            Class doClass = mapping.getDomainClass();
            boolean gotKeys = false;

            // The path for a one-to-many relationship ends with a "[i]". The absence of that suffix indicates a one-to-one relationship
            //// No keys, must be one-to-one
            //if (mapping.getKeyFields() == null || mapping.getKeyFields().size() == 0) {
            if (path == null || path.charAt(path.length() - 1) != ']') {
                if (log.isDebugEnabled())
                    log.debug("Find 'one-to-one' object - " + path);

                // Just use the getXxxObject method to get the related domain object,
                // if there is one...
                domainObject = (IPersistent) getProperty(parentMapping.getDomainFieldDescriptor(parentField), parentDomain);
                if (domainObject == null) {
                    if (log.isDebugEnabled())
                        log.debug("Not Found - " + path);
                }
            } else {
                // Get the key fields used in the domain object. Use the findXxxxxCriteria() method,
                // then add the extra fields to the criteria object, to get the unique record.
                gotKeys = fillInKeys(path, source, mapping, keys);

                // read DO based on key
                if (gotKeys) {
                    // get the method to get the PK criteria (i.e. public Criteria findVendorSiteCriteria(); )
                    Method findCriteria = null;
                    String methodName = "find" + StringHelper.getUpper1(relationshipName) + "Criteria";
                    try {
                        findCriteria = parentDomain.getClass().getMethod(methodName, new Class[]{});
                    } catch (NoSuchMethodException e) {
                        log.error("Find method '" + methodName + "' not found!");
                    }
                    if (findCriteria == null)
                        throw new TransformException(TransformException.METHOD_NOT_FOUND, path, methodName);

                    // Find Criteria For Related Object
                    Criteria criteria = (Criteria) findCriteria.invoke(parentDomain, new Object[]{});
                    // Add extra key info...
                    for (Iterator it = keys.keySet().iterator(); it.hasNext(); ) {
                        String keyField = (String) it.next();
                        Object value = keys.get(keyField);
                        keyField = StringHelper.getUpper1(mapping.getDomainFieldName(keyField));
                        criteria.addCriteria(keyField, value);
                        if (log.isDebugEnabled())
                            log.debug(path + "- Add to criteria:" + keyField + '=' + value);
                    }
                    // See if we get an object :-)
                    Iterator itr = uow.query(criteria).iterator();
                    if (itr.hasNext())
                        domainObject = (IPersistent) itr.next();
                    if (itr.hasNext()) {
                        // Error, multiple objects found
                        throw new ApplicationExceptions(new ApplicationExceptionWithContext(path, new MultipleDomainObjectsFoundException(findDomainLabel(criteria.getTable()))));
                    }
                }

            }
            // Error if DO not found
            if (domainObject == null)
                throw new ApplicationExceptions(new ApplicationExceptionWithContext(path, new DomainObjectNotFoundException(findDomainLabel(doClass))));

            // Process the delete, either on this DO, or a related DO if there is one
            deleteBeanData(path, source, uow, handler, mapping, domainObject);

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

    static void deleteBeanData(String path, GraphDataObject source, UOW uow, ITransformationHandler handler,
                               GraphMapping mapping, IPersistent domainObject)
            throws InstantiationException, IllegalAccessException, InvocationTargetException,
            ApplicationExceptions, FrameworkException {
        try {
            // Ensure the domain object has not been modified
            domainObjectChangedTest(path, source, mapping, domainObject);

            List<ITransformationHandler> handlers = null;
            if (handler != null) {
                handlers = handler.getTransformationHandlers();
            }

            //----------------------------------------------------------------
            // Fire 'startBean' handler
            if (handlers != null) {
                for (ITransformationHandler transformationHandler : handlers) {
                    transformationHandler.startBean(path, source, domainObject);
                }
            }

            //----------------------------------------------------------------
            // Now loop through children, if there is one, delete it, and leave parent alone
            boolean deleteChild = false;

            // Reflect any related objects
            Set relatedFields = mapping.getRelatedFields();

            if (relatedFields != null) {
                for (Object relatedField : relatedFields) {
                    String field = (String) relatedField;
                    Object value = getProperty(mapping.getDataFieldDescriptor(field), source);
                    if (value != null) {
                        if (value.getClass().isArray()) {
                            // The related field is an array of objects (one-to-many)
                            Object[] values = (Object[]) value;
                            for (int i = 0; i < values.length; i++) {
                                GraphDataObject dao = (GraphDataObject) values[i];  // Assumes its a DAO....what else could it be?
                                if (dao != null) {
                                    deleteChild = true;
                                    deleteChildBean(path + '.' + field + '[' + i + ']', dao, uow, handler, domainObject,
                                                    mapping, field);
                                }
                            }
                        }
                        else {
                            // The related field is a single object (one-to-many)
                            GraphDataObject dao = (GraphDataObject) value; // Assumes its a DAO....what else could it be?
                            deleteChild = true;
                            deleteChildBean(path + '.' + field, dao, uow, handler, domainObject, mapping, field);
                        }
                    }
                }
            }

            //----------------------------------------------------------------
            // Delete this record, as it had no children
            if (!deleteChild) {
                if (log.isDebugEnabled())
                    log.debug("UOW.Delete Domain Object");
                // Fire 'startBeanDelete' handler
                if (handlers != null) {
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.startBeanDelete(path, source, domainObject);
                    }
                }

                uow.delete(domainObject);
                if (handlers != null) {
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.setChangeDone(true);
                    }
                }

                // Fire 'endBeanDelete' handler
                if (handlers != null) {
                    for (ITransformationHandler transformationHandler : handlers) {
                        transformationHandler.endBeanDelete(path, source, domainObject);
                    }
                }
            }

            //----------------------------------------------------------------
            // Fire 'endBean' handler
            if (handlers != null) {
                for (ITransformationHandler transformationHandler : handlers) {
                    transformationHandler.endBean(path, source, domainObject);
                }
            }

        } catch (SkipTransformException e) {
            if (log.isDebugEnabled())
                log.debug("Processing of " + path + " will be skipped", e);
        } catch (ApplicationException e) {
            throw new ApplicationExceptions(e);
        }
    }

    static void setProperty(PropertyDescriptor pd, Object value, Object source)
            throws IllegalAccessException, InvocationTargetException, TransformException {
        if (pd != null && pd.getWriteMethod() != null) {
            Method m = pd.getWriteMethod();
            if (!m.isAccessible())
                m.setAccessible(true);
            Class tClass = m.getParameterTypes()[0];
            if (value == null || tClass.isAssignableFrom(value.getClass())) {
                m.invoke(source, new Object[]{value});
                if (log.isDebugEnabled())
                    log.debug("Set property '" + pd.getName() + '=' + value + "' on object '" + source.getClass().getName() + '\'');
            } else if (DataTypeMapper.instance().isMappable(value.getClass(), tClass)) {
                // See if there is a datatype mapper for these classes
                value = DataTypeMapper.instance().map(value, tClass);
                m.invoke(source, new Object[]{value});
                if (log.isDebugEnabled())
                    log.debug("Translate+Set property '" + pd.getName() + '=' + value + "' on object '" + source.getClass().getName() + '\'');
            } else {
                // Data type mismatch
                throw new TransformException(TransformException.DATATYPE_MISMATCH, source.getClass().getName() + '.' + m.getName(), tClass.getName(), value.getClass().getName());
            }
        } else {
            TransformException me = new TransformException(TransformException.NO_SETTER, null,
                    pd == null ? "???" : pd.getName(), source.getClass().getName());
            log.error(me.getLocalizedMessage());
            throw me;
        }
    }

    static Object getProperty(PropertyDescriptor pd, Object source)
            throws IllegalAccessException, InvocationTargetException, TransformException {
        if (pd != null && pd.getReadMethod() != null) {
            Method m = pd.getReadMethod();
            if (!m.isAccessible())
                m.setAccessible(true);
            Object value = m.invoke(source, new Object[]{});
            if (log.isDebugEnabled())
                log.debug("Get property '" + pd.getName() + '=' + value + "' on object '" + source.getClass().getName() + '\'');
            return value;
        } else {
            TransformException me = new TransformException(TransformException.NO_GETTER, null,
                    pd == null ? "???" : pd.getName(), source.getClass().getName());
            log.error(me.getLocalizedMessage());
            throw me;
        }
    }

    /**
     * Set a value on a Bean, if its a persistent bean, try to use an update method first
     * (for v1.0 domain objects), otherwise use the setter (for v1.1 and above).
     */
    static void updateProperty(PropertyDescriptor pd, Object value, Object source)
            throws IllegalAccessException, InvocationTargetException, TransformException {
        if (source instanceof Persistent) {
            if (pd != null && pd.getWriteMethod() != null) {
                try {
                    Method m = source.getClass().getMethod("update" + StringHelper.getUpper1(pd.getName()), pd.getWriteMethod().getParameterTypes());
                    if (!m.isAccessible())
                        m.setAccessible(true);
                    Class tClass = m.getParameterTypes()[0];
                    if (value == null || tClass.isAssignableFrom(value.getClass())) {
                        m.invoke(source, new Object[]{value});
                        if (log.isDebugEnabled())
                            log.debug("Update property '" + pd.getName() + '=' + value + "' on object '" + source.getClass().getName() + '\'');
                        // See if there is a datatype mapper for these classes
                    } else if (DataTypeMapper.instance().isMappable(value.getClass(), tClass)) {
                        value = DataTypeMapper.instance().map(value, tClass);
                        m.invoke(source, new Object[]{value});
                        if (log.isDebugEnabled())
                            log.debug("Translate+Update property '" + pd.getName() + '=' + value + "' on object '" + source.getClass().getName() + '\'');
                    } else {
                        // Data type mismatch
                        throw new TransformException(TransformException.DATATYPE_MISMATCH, source.getClass().getName() + '.' + m.getName(), tClass.getName(), value.getClass().getName());
                    }
                } catch (NoSuchMethodException e) {
                    if (log.isDebugEnabled())
                        log.debug("No Updator, try Setter for DO property '" + pd.getName() + "' on object '" + source.getClass().getName() + '\'');
                    // try to use the setter if there is no update method
                    setProperty(pd, value, source);
                }
            } else {
                TransformException me = new TransformException(TransformException.NO_SETTER, null,
                        pd == null ? "???" : pd.getName(), source.getClass().getName());
                log.error(me.getLocalizedMessage());
                throw me;
            }
        } else
            setProperty(pd, value, source);
    }

    /**
     * Tries to use meta data to get domain objects label.
     */
    static String findDomainLabel(Class doClass) {
        return findDomainLabel(doClass.getName());
    }

    /**
     * Tries to use meta data to get domain objects label.
     */
    static String findDomainLabel(String doClassName) {
        String label = doClassName;
        try {
            label = PersistentHelper.getLabelToken(doClassName);
        } catch (Exception e) {
            // ignore any problem trying to get the label!
        }
        return label;
    }

    /**
     * Performs the dirty-read check to ensure that the underlying record in the database hasn't
     * been changed by another user, while the current user has been trying to modify it.
     */
    private static void domainObjectChangedTest(String path, GraphDataObject source, GraphMapping mapping, IPersistent domainObject)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, FrameworkException, ApplicationExceptions {
        if (source.getPerformDirtyReadCheck() != null && source.getPerformDirtyReadCheck() && mapping.getDirtyReadDataFieldName() != null) {
            if (log.isDebugEnabled())
                log.debug("Performing dirty-read check for " + domainObject);
            Object lastKnownValue = getProperty(mapping.getDataFieldDescriptor(mapping.getDirtyReadDataFieldName()), source);
            Object currentValue = getProperty(mapping.getDomainFieldDescriptor(mapping.getDirtyReadDataFieldName()), domainObject);
            if (lastKnownValue == null ? currentValue != null : !lastKnownValue.equals(currentValue)) {

                if (log.isDebugEnabled())
                    log.debug("Dirty-read check failed: lastKnownValue='" + lastKnownValue + "', currentValue='" + currentValue + '\'');
                if (!domainObject.isDatabaseOccurence()) {
                    if (log.isDebugEnabled())
                        log.debug("Dirty-read check failed: " + domainObject + " may have been removed.");
                    throw new ApplicationExceptions(new ApplicationExceptionWithContext(path, new ApplicationException(DomainObjectChangedException.OBJECT_REMOVED, null, null)));
                } else {
                    String[] errorParamNames = mapping.getDirtyReadErrorParams();
                    Object[] errorParamValues = new Object[errorParamNames.length];
                    try {
                        for (int i = 0; i < errorParamNames.length; i++)
                            errorParamValues[i] = BeanHelper.getField(domainObject, errorParamNames[i]);
                    } catch (Exception e) {
                        log.warn("Error in creation of the argument array to pass to the DomainObjectChangedException from the list: " + Arrays.toString(mapping.getDirtyReadErrorParams()), e);
                    }
                    throw new ApplicationExceptions(new ApplicationExceptionWithContext(path, new DomainObjectChangedException(mapping.getDirtyReadErrorLabelToken(), errorParamValues, null)));
                }
            } else {
                if (log.isDebugEnabled())
                    log.debug("Dirty-read check succeeded. Both lastKnownValue and currentValue equal '" + currentValue + '\'');
            }
        }
    }

    /**
     * This will generate a unique string for the input persistent object, based on the persistent class name and its key values.
     * The format of the generated key will be: package.classname;key1value;key2value;key3value
     * For eg:
     * For a Person persistent object having a primary key PersonId, the serialized key could be "org.example.Person;P0021"
     * For an EventEntry persistent object having a composite primary key of EventId and PersonId primary, the serialized key could be "org.example.EventEntry;E01;P0021"
     * The back-slash '\' will be the escape character.
     * Hence, if the key-value contains a '\', then it'll be replaced by '\\'
     * If the key value contains a semi-colon, then it'll be replaced by '\;'
     * <p/>
     * Note: This method will determine the key fields by looking up the getKeyFields method in the corresponding meta class for the input persistent object.
     *
     * @param source The persistent object.
     * @return a unique String for identifying the persistent object.
     * @throws ClassNotFoundException    if the Meta class for the input persistent class is not found.
     * @throws NoSuchMethodException     if the Meta class does not have the 'public static FieldMetaData[] getKeyFields()' method.
     * @throws IllegalAccessException    if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class enforces Java language access control and the underlying method is inaccessible.
     * @throws InvocationTargetException if the 'public static FieldMetaData[] getKeyFields()' method of the Meta class throws an exception.
     * @throws IllegalArgumentException  if the input persistent class does not have any key-fields or if any of the key-fields is null.
     */
    public static String generateSerializedKey(GraphDataObject source)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IllegalArgumentException {

        GraphMapping mapping = MappingFactory.getInstance(source);
        Set<String> keyFields = mapping.getKeyFields();
        Class doClass = mapping.getDomainClass();

        StringBuffer buf = new StringBuffer(doClass.getName());

        if (keyFields != null && keyFields.size() > 0) {
            for (String keyFieldName : keyFields) {
                Object keyFieldValue = BeanHelper.getField(source, keyFieldName);
                if (keyFieldValue != null) {
                    buf.append(';').append(quoteSerializedKeyValue(keyFieldValue.toString()));
                } else {
                    String str = "SerializedKey cannot be generated. The input Graph object has a null value for its key-field " + source + ':' + keyFieldName;
                    if (log.isDebugEnabled())
                        log.debug(str);
                    throw new IllegalArgumentException(str);
                }
            }
        } else {
            String str = "SerializedKey cannot be generated. The input persistent object does not have any key fields defined in its meta class: " + doClass.getName();
            if (log.isDebugEnabled())
                log.debug(str);
            throw new IllegalArgumentException(str);
        }
        return buf.toString();
    }

    /**
     * This will quote the semi-colon characters in the input string.
     * The back-slash '\' will be the escape character.
     * Hence, if the key-value contains a '\', then it'll be replaced by '\\'
     * If the key value contains a semi-colon, then it'll be replaced by '\;'
     */
    private static String quoteSerializedKeyValue(String input) {
        // replace '\' with '\\' and then replace ';' with '\;'
        return StringHelper.replace(StringHelper.replace(input, "\\", "\\\\"), ";", "\\;");
    }

    /**
     * This will unquote the semi-colon characters in the input string.
     * The back-slash '\' will be the escape character.
     * Hence, if the key-value contains a '\;', then it'll be replaced by ';'
     * If the key value contains a '\\', then it'll be replaced by '\'
     */
    private static String unquoteSerializedKeyValue(String input) {
        // replace '\;' with ';' and then replace '\\' with '\'
        return StringHelper.replace(StringHelper.replace(input, "\\;", ";"), "\\\\", "\\");
    }
}
