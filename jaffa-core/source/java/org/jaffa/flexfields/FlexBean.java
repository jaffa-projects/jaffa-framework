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
 * 1.  Redistributions of source code must retain copyright statements and notices.
 *     Redistributions must also contain a copy of this document.
 * 2.  Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 * 3.  The name "JAFFA" must not be used to endorse or promote products derived from
 *     this Software without prior written permission. For written permission,
 *     please contact mail to: jaffagroup@yahoo.com.
 * 4.  Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *     appear in their names without prior written permission.
 * 5.  Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.flexfields;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.log4j.Logger;
import org.jaffa.beans.factory.InitializerFactory;
import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.datatypes.DataTypeMapper;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.flexfields.domain.FlexField;
import org.jaffa.flexfields.domain.FlexFieldMeta;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.rules.fieldvalidators.Validator;
import org.jaffa.rules.fieldvalidators.ValidatorFactory;
import org.jaffa.rules.initializers.Initializer;
import org.jaffa.util.BeanHelper;

import javax.xml.bind.annotation.XmlTransient;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * FlexBean implements the DynaBean interface.
 * <p>
 * It holds the following information when linked to a persistent object:
 * flexClass: the associated DynaClass
 * persistentObject: the persistent object for which this bean will hold FlexField instances.
 * flexFields: the associated FlexField instances.
 * NOTE: For a persistent object, the setter will either set a flex field directly on the
 * associated persistent object, if a domain-mapping is provided, or the flex field will be
 * added to the flexFields property.
 * <p>
 * It holds the following information when linked to a non-persistent object:
 * flexClass: the associated DynaClass
 * flexParams: the associated FlexParam instances.
 * NOTE: For a non-persistent object, the setter will always add the flex field to the
 * flexParams property.
 * <p>
 * It is highly recommended to use the instance() method to instantiate this bean.
 */
public class FlexBean implements DynaBean {

    private static final Logger log = Logger.getLogger(FlexBean.class);
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private final Map<String, FlexField> flexFields = new TreeMap<>();
    private final Map<String, FlexParam> flexParams = new TreeMap<>();
    private final Map<String, Object> changes = new HashMap<>();
    private transient Validator<FlexBean> flexBeanValidator;
    private FlexClass flexClass;
    private IPersistent persistentObject;
    private boolean loaded; //controls the lazy-loading of data

    /**
     * Creates a new instance.
     */
    public FlexBean() {
        addPropertyChangeListener();
    }

    /**
     * Creates a new instance.
     *
     * @param flexClass the associated FlexClass.
     */
    public FlexBean(FlexClass flexClass) {
        this();
        this.flexClass = flexClass;

        // Aop Replacement Support:
        // Validation was previous applied via the execution(* org.jaffa.flexfields.FlexBean->validate())
        // pointcut. Removal of AOP support requires that the validators be identified dynamically and
        // (if found) invoked manually in the validated method.
        ValidatorFactory validatorFactory = (ValidatorFactory) StaticContext.getBean("ruleValidatorFactory");
        if (validatorFactory != null) {
            flexBeanValidator = validatorFactory.getValidator(this);
        }
        /**If the FlexBean object is persistent or graph object then the configureFlexBean method already invoked
         * from Persistent or GraphDataObject. Here only doing the initialization of flex bean if its not persistent.
         */
        if (!(this instanceof IFlexFields)) {
            InitializerFactory initializerFactory = (InitializerFactory) StaticContext.getBean(InitializerFactory.class);
            if (initializerFactory != null) {
                Initializer initializer = initializerFactory.getInitializer(this);
                if (initializer != null) {
                    try {
                        initializer.initialize(this);
                    } catch (FrameworkException e) {
                        log.error("Could not initialize object: " + this.getClass().getName() + ". " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Creates a new instance.
     *
     * @param flexClass        the associated FlexClass.
     * @param persistentObject the persistent object.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    public FlexBean(FlexClass flexClass, IPersistent persistentObject) throws ApplicationExceptions, FrameworkException {
        this(flexClass);
        this.persistentObject = persistentObject;
    }

    /**
     * This is the recommended way to instantiate a FlexBean.
     * It obtains the appropriate FlexClass.
     *
     * @param object the associated object.
     * @return a FlexBean instance.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    public static FlexBean instance(Object object) throws ApplicationExceptions, FrameworkException {
        if (object instanceof FlexClass)
            return instance((FlexClass) object);
        else {
            FlexClass flexClass = FlexClass.instance(object);
            return instance(flexClass, object);
        }
    }

    /**
     * Creates an instance based on the input FlexClass and clears all the initial values.
     *
     * @param flexClass the associated FlexClass.
     * @return a FlexBean instance.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    public static FlexBean instance(FlexClass flexClass) throws ApplicationExceptions, FrameworkException {
        return instance(flexClass, null);
    }

    /**
     * This is the recommended way to instantiate a FlexBean.
     * It obtains the appropriate FlexClass.
     *
     * @param flexClass the associated FlexClass.
     * @param object    the associated object.
     * @return a FlexBean instance. A null will be returned if the FlexClass has no dyna-properties.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    private static FlexBean instance(FlexClass flexClass, Object object) throws ApplicationExceptions, FrameworkException {
        FlexBean flexBean = null;
        if (flexClass.getDynaProperties() != null && flexClass.getDynaProperties().length > 0) {
            if (object != null && object instanceof IPersistent) {
                flexBean = new FlexBean(flexClass, (IPersistent) object);
            } else {
                flexBean = new FlexBean(flexClass);
                // Clear all the changes. This can happen if initialze rules are delcared for any of the flex fields.
                // What this means is that initialize rules for a non-persistent object will be ignored.
                // This is necessary, else the initial values will be reapplied even after a flex field was modified to a different value.
                flexBean.flexParams.clear();
                flexBean.clearChanges();
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("FlexBean will not be instantiated for the FlexClass '" + flexClass.getName() + "', since it has no dyna-properties");
        }
        return flexBean;
    }

    /**
     * Attempts to configure an object that implements the IFlexFields interface with optional flex field properties
     * based upon the configuration loaded from the MetaClass Rule repositories. If the instance does not have any
     * flex fields defined it will not be modified.
     *
     * @param flexInstance the instance to configure with the custom flex fields from the repositories
     */
    public static void configureFlexBean(IFlexFields flexInstance) {
        try {
            FlexBean flexBean = instance(flexInstance);

            if (flexBean != null) {
                // simulate the pointcut for construction(org.jaffa.flexfields.FlexBean->new(..)) which referenced
                // the initialize interceptor. That logic has been ported to be set up by the static context instead.
                StaticContext.initialize(flexBean);

                // assign the flexbean to the graphData object
                flexInstance.setFlexBean(flexBean);
            }
        } catch (ApplicationExceptions | FrameworkException ex) {
            log.error("An exception occurred while attempting to initialize flex fields on object of type " + flexInstance.getClass().getName(), ex);
        }
    }

    /**
     * NOTE: This is an unsupported operation for a FlexBean instance.
     */
    public boolean contains(String name, String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Return the value of the input property.
     * If the property has a domain-mapping (as determined from the associated FlexProperty, value will
     * be obtained from the persistentObject. Else the value will be obtained from
     * the appropriate FlexField instance.
     *
     * @param name the property name.
     * @return value for the property.
     */
    public Object get(String name) {
        return getset(true, name, null, null);
    }

    /**
     * NOTE: This is an unsupported operation for a FlexBean instance.
     */
    public Object get(String name, int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * NOTE: This is an unsupported operation for a FlexBean instance.
     */
    public Object get(String name, String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Return the FlexClass instance that describes the set of properties available for this FlexBean.
     *
     * @return the FlexClass instance that describes the set of properties available for this FlexBean.
     */
    @XmlTransient
    public DynaClass getDynaClass() {
        return flexClass;
    }

    /**
     * Sets the FlexClass instance that describes the set of properties available for this FlexBean.
     * <p>
     * NOTE: This method will throw a ClassCastException if a non-FlexClass argument is passed.
     * DynaClass is used in the signature so that this property turns up as a valid read+write property via the JavaBeans Introspector.
     *
     * @param flexClass new value for the property flexClass.
     */
    public void setDynaClass(DynaClass flexClass) {
        this.flexClass = (FlexClass) flexClass;
    }

    /**
     * NOTE: This is an unsupported operation for a FlexBean instance.
     */
    public void remove(String name, String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the value of the input property.
     * If the property has a domain-mapping (as determined from the associated FlexProperty, value will
     * be stamped on the persistentObject. Else the value will be stamped on
     * the appropriate FlexField instance.
     *
     * @param name  the property name.
     * @param value the new value.
     */
    public void set(String name, Object value) {
        getset(false, name, value, null);
    }

    /**
     * NOTE: This is an unsupported operation for a FlexBean instance.
     */
    public void set(String name, int index, Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * NOTE: This is an unsupported operation for a FlexBean instance.
     */
    public void set(String name, String key, Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Getter for the property flexParams.
     *
     * @return the value of the property flexParams.
     */
    public FlexParam[] getFlexParams() {
        return flexParams.values().toArray(new FlexParam[flexParams.size()]);
    }

    /**
     * Setter for the property flexParams.
     *
     * @param flexParams new value for the property flexParams.
     */
    public void setFlexParams(FlexParam[] flexParams) {
        if (flexParams != null) {
            for (FlexParam flexParam : flexParams)
                getset(false, null, null, flexParam);
        }
    }

    /**
     * Getter for the property persistentObject.
     *
     * @return the value of the property persistentObject.
     */
    public IPersistent getPersistentObject() {
        return persistentObject;
    }

    /**
     * Returns debug info.
     *
     * @return debug info.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        try {
            buf.append("<FlexBean");
            if (flexClass != null)
                buf.append(" name='").append(flexClass.getName()).append("' logicalName='").append(flexClass.getLogicalName()).append('\'');
            buf.append('>');
            if (flexClass != null) {
                for (FlexProperty f : flexClass.getDynaProperties()) {
                    buf.append('<').append(f.getName()).append(" logicalName='").append(f.getLogicalName()).append("' value='");
                    Object value = get(f.getName());
                    if (value != null)
                        buf.append(value);
                    buf.append("'/>");
                }
            } else {
                for (FlexParam flexParam : flexParams.values()) {
                    buf.append('<').append(flexParam.getName()).append("' value='");
                    if (flexParam.getValue() != null)
                        buf.append(flexParam.getValue());
                    buf.append("'/>");
                }
            }
            buf.append("</FlexBean>");
        } catch (Exception ignore) {
        }
        return buf.toString();
    }

    /**
     * Loads the FlexField instances related to the persistent object.
     *
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    private void load() throws ApplicationExceptions, FrameworkException {
        if (persistentObject == null || loaded)
            return;
        if (persistentObject.isDatabaseOccurence()) {
            // build the criteria to load related FlexField objects
            Criteria criteria = new Criteria();
            criteria.setTable(FlexFieldMeta.getName());
            criteria.addCriteria(FlexFieldMeta.OBJECT_NAME, flexClass.getLogicalName());
            String[] keyValues = findKeyValues();
            if (keyValues == null || keyValues.length == 0) {
                if (log.isDebugEnabled())
                    log.debug("FlexFields cannot be loaded since key-values not found on " + persistentObject);
                return;
            }
            criteria.addCriteria(FlexFieldMeta.KEY1, keyValues[0]);
            if (keyValues.length > 1)
                criteria.addCriteria(FlexFieldMeta.KEY2, keyValues[1]);
            if (keyValues.length > 2)
                criteria.addCriteria(FlexFieldMeta.KEY3, keyValues[2]);

            // load the FlexFields
            for (Object o : persistentObject.getUOW().query(criteria)) {
                FlexField flexField = (FlexField) o;
                String propertyName = flexClass.getNameByLogicalName(flexField.getFieldName());
                if (propertyName == null)
                    log.warn("LogicalName '" + flexField.getFieldName() + "' has not been defined in the flex-fields definition of '" + flexClass.getName());
                else
                    flexFields.put(propertyName, flexField);
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("FlexFields not loaded since the persistentObject has never been persisted");
        }
        loaded = true;
    }

    /**
     * Adds a PropertyChangeListener to the listener list.
     *
     * @param l The listener to add.
     */
    public final void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    /**
     * Removes a PropertyChangeListener from the listener list.
     *
     * @param l The listener to remove.
     */
    public final void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    /**
     * Clear all the changes on this bean. Will cause all future calls to
     * {@link #hasChanged(String)} to return false
     */
    public void clearChanges() {
        changes.clear();
    }

    /**
     * Has the bean changed since it was created or last cleared.
     *
     * @return true if the bean has been modified
     */
    public boolean hasChanged() {
        return changes != null && changes.size() > 0;
    }

    /**
     * Has the specified bean property been changed since the bean was
     * created or last cleared
     *
     * @param name Name of bean property to check
     * @return true if the property has been modified
     */
    public boolean hasChanged(String name) {
        return changes.containsKey(name);
    }

    /**
     * Get the original value for this field, throw an error if this field has no
     * changed, so you should consider first checking with the  {@link #hasChanged(String)}
     * method
     *
     * @param name Name of bean property to check
     * @return The object representing the original values. Primitives are return as their
     * Object counterparts.
     * @throws NoSuchFieldException Throw if the property has not been changed, or does not exist.
     */
    public Object getOriginalValue(String name) throws NoSuchFieldException {
        if (changes.containsKey(name))
            return changes.get(name);
        else
            throw new NoSuchFieldException(name);
    }

    /**
     * Adds a default PropertyChangeListener to the listener list.
     */
    private void addPropertyChangeListener() {
        addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                valueChanged(evt.getPropertyName(), evt.getOldValue());
                if (log.isDebugEnabled())
                    log.debug("Field '" + evt.getPropertyName() + "' updated from '" + evt.getOldValue() + "' to '" + evt.getNewValue() + "'");
            }
        });
    }

    /**
     * Adds the oldValue to the changes Map.
     */
    private void valueChanged(String name, Object oldValue) {
        if (!changes.containsKey(name))
            changes.put(name, oldValue);
    }

    /**
     * Gets or Sets a property.
     */
    private Object getset(boolean get, String name, Object value, FlexParam flexParam) {
        if (flexParam != null) {
            name = flexParam.getName();
            value = flexParam.getValue();
        }
        try {
            FlexProperty flexProperty = flexClass != null ? flexClass.getDynaProperty(name) : null;
            Class dataType = flexProperty != null ? flexProperty.getType() : String.class;
            Properties flexInfo = flexProperty != null ? flexProperty.getFlexInfo() : null;
            String domainMapping = flexInfo != null ? flexInfo.getProperty("domain-mapping") : null;
            String layout = flexInfo != null ? flexInfo.getProperty("layout") : null;
            if (get) {
                if (persistentObject == null) {
                    if (flexParams.containsKey(name))
                        value = flexParams.get(name).getValue();
                } else if (domainMapping != null) {
                    value = BeanHelper.getField(persistentObject, domainMapping);
                } else {
                    load(); //retrieve data from the database, in case it hasn't be loaded yet
                    if (flexFields.containsKey(name))
                        value = flexFields.get(name).getValue();
                }
                if (value != null) {
                    if (value instanceof String && ((String) value).length() == 0)
                        value = null;
                    else
                        value = convertTo(value, dataType, layout);
                }
                if (log.isDebugEnabled())
                    log.debug("Value of property '" + name + "' is '" + value + '\'');
                return value;
            } else {
                Object oldValue;
                if (persistentObject == null) {
                    oldValue = flexParams.containsKey(name) ? flexParams.get(name).getValue() : null;
                    if (flexParam == null)
                        flexParam = new FlexParam(name, convertToString(value, layout));
                    flexParams.put(name, flexParam);
                } else if (domainMapping != null) {
                    // ignore, if the current value and new value are the same
                    value = convertTo(value, dataType, layout);
                    oldValue = convertTo(BeanHelper.getField(persistentObject, domainMapping), dataType, layout);
                    if (oldValue == null ? value == null : oldValue.equals(value))
                        return null;
                    BeanHelper.setField(persistentObject, domainMapping, convertToString(value, layout));
                } else {
                    load(); //retrieve data from the database, in case it hasn't be loaded yet
                    if (flexFields.containsKey(name)) {
                        // ignore, if the current value and new value are the same
                        value = convertTo(value, dataType, layout);
                        oldValue = convertTo(flexFields.get(name).getValue(), dataType, layout);
                        if (oldValue == null ? value == null : oldValue.equals(value))
                            return null;
                        flexFields.get(name).setValue(convertToString(value, layout));
                    } else {
                        // ignore, if the current value and new value are the same
                        if (value == null)
                            return null;
                        oldValue = null;
                        FlexField flexField = new FlexField();
                        flexField.setObjectName(flexClass.getLogicalName());
                        flexField.setFieldName(flexProperty != null ? flexProperty.getLogicalName() : null);
                        flexField.setValue(convertToString(value, layout));
                        flexFields.put(name, flexField);
                    }
                }
                propertyChangeSupport.firePropertyChange(name, oldValue, value);
                if (log.isDebugEnabled())
                    log.debug("Value of property '" + name + "' has been set to '" + value + '\'');
                return null;
            }
        } catch (Exception e) {
            String s = "Exception thrown while accessing flex field '" + name + "' from " + persistentObject;
            log.error(s, e);
            throw new RuntimeException(s, e);
        }
    }

    /**
     * Returns an array containing key values for the persistent object.
     */
    private String[] findKeyValues() {
        try {
            Class persistentClass = persistentObject.getClass();
            FieldMetaData[] keyFields = PersistentHelper.getKeyFields(persistentClass.getName());
            if (keyFields == null || keyFields.length == 0) {
                String s = "FlexFields cannot be supported on " + persistentClass + " since it has no key-fields";
                log.error(s);
                throw new IllegalArgumentException(s);
            } else if (keyFields.length > 3) {
                String s = "FlexFields cannot be supported on " + persistentClass + " since it has more than 3 key-fields";
                log.error(s);
                throw new IllegalArgumentException(s);
            }
            String[] keyValues = new String[keyFields.length];
            keyValues[0] = (String) convertTo(BeanHelper.getField(persistentObject, keyFields[0].getName()), String.class, null);
            if (keyFields.length > 1)
                keyValues[1] = (String) convertTo(BeanHelper.getField(persistentObject, keyFields[1].getName()), String.class, null);
            if (keyFields.length > 2)
                keyValues[2] = (String) convertTo(BeanHelper.getField(persistentObject, keyFields[2].getName()), String.class, null);
            return keyValues;
        } catch (Exception e) {
            String s = "Exception thrown while determining the key fields of " + persistentObject;
            log.error(s, e);
            throw new RuntimeException(s, e);
        }
    }

    /**
     * Stamps the key values of the persistent object on the input FlexField instance.
     */
    private void stampKeyValues(FlexField flexField) throws ApplicationExceptions, FrameworkException {
        try {
            String[] keyValues = findKeyValues();
            if (keyValues != null) {
                flexField.setKey1(keyValues[0]);
                if (keyValues.length > 1)
                    flexField.setKey2(keyValues[1]);
                if (keyValues.length > 2)
                    flexField.setKey3(keyValues[2]);
            }
        } catch (ApplicationException e) {
            throw new ApplicationExceptions(e);
        }
    }

    private Object convertTo(Object value, Class dataType, String layout) throws ApplicationException {
        return value != null && !dataType.isInstance(value) ? DataTypeMapper.instance().map(value, dataType, layout) : value;
    }

    private String convertToString(Object value, String layout) throws ApplicationException {
        return (String) convertTo(value, String.class, layout);
    }

    /**
     * Validates this bean.
     * This is also useful for binding AOP-based validations.
     *
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    public void validate() throws ApplicationExceptions, FrameworkException {

        // Aop Replacement Support:
        // Validation was previous applied via an aop pointcut that would be applied to this method.
        // this is now performed via dynamic discovery of validators (via spring config), and if
        // found, executed here

        if (flexBeanValidator != null) {
            try {
                flexBeanValidator.validate(this);
            } catch (ApplicationException exception) {
                //re-wrap Application Exception into an Application Exceptions
                throw new ApplicationExceptions(exception);
            }
        }

        // stamps the key-values on new FlexField instances
        for (FlexField flexField : flexFields.values()) {
            if (!flexField.isDatabaseOccurence() && flexField.getValue() != null && flexField.getValue().length() > 0)
                stampKeyValues(flexField);
        }
    }

    /**
     * Adds/Updates FlexField instances to the database.
     * This should ideally be called after the associated persistentObject has been added/updated.
     *
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    public void update() throws ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled())
            log.debug("Validating the FlexBean");
        validate();
        if (log.isDebugEnabled())
            log.debug("Adding/Updating FlexField instances for " + persistentObject);
        for (FlexField flexField : flexFields.values()) {
            if (flexField.isDatabaseOccurence()) {
                if (flexField.getValue() != null && flexField.getValue().length() > 0) {
                    if (log.isDebugEnabled())
                        log.debug("Updating the FlexField instance " + flexField);
                    persistentObject.getUOW().update(flexField);
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Since the value is null, deleting the FlexField instance " + flexField);
                    persistentObject.getUOW().delete(flexField);
                }
            } else {
                if (flexField.getValue() != null && flexField.getValue().length() > 0) {
                    if (log.isDebugEnabled())
                        log.debug("Adding the FlexField instance " + flexField);
                    persistentObject.getUOW().add(flexField);
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Since the value is null, ignoring the FlexField instance " + flexField);
                }
            }
        }
    }

    /**
     * Delete the FlexField instances to the database.
     * This should ideally be called before the associated persistentObject has been deleted.
     *
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    public void delete() throws ApplicationExceptions, FrameworkException {
        load(); //retrieve data from the database, in case it hasn't be loaded yet
        if (log.isDebugEnabled())
            log.debug("Deleting the FlexField instances for " + persistentObject);
        for (FlexField flexField : flexFields.values()) {
            if (flexField.isDatabaseOccurence()) {
                if (log.isDebugEnabled())
                    log.debug("Deleting the FlexField instance " + flexField);
                persistentObject.getUOW().delete(flexField);
            }
        }
    }
}
