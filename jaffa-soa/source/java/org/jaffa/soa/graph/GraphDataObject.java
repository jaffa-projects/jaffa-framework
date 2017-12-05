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

/*
 * GraphDataObject.java
 *
 */
package org.jaffa.soa.graph;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.flexfields.IFlexFields;
import org.jaffa.rules.fieldvalidators.Validator;
import org.jaffa.soa.dataaccess.GraphMapping;
import org.jaffa.soa.dataaccess.MappingFactory;
import org.jaffa.soa.dataaccess.TransformerUtils;
import org.jaffa.soa.events.ProcessEventGraph;
import org.jaffa.util.BeanHelper;

import java.util.*;

/**
 * This is the base class for all Graph Data Objects (GDOs).
 * <p>
 * The primary feature of a GDO as compared to a simple POJO or JavaBean is
 * that we must distinguish between an a un-initialized property, and an
 * property that has been set with a null value.
 * <p>
 * This base class provides a default implementation of the PropertyChange function
 * from the Java Bean specification, which keeps track of which properties in
 * the Bean have been modified since construction, or since the last <code>
 * clearChanges()</code>
 * <p>
 * Tools such as web service implementations may choose to ignore null values.
 * In such a scenario, the nullify property can be used to specify the properties
 * to be nullified.
 * <p>
 * The DataTransformer that uses these objects will not transform any field that
 * has not been directly set. This allows this objects to be partially populated
 * with data both on the query and update services.
 * <p>
 * It is critical to this classes behavior that any classes that wrapper this one
 * (ie like the @WebMethod) also honor this behavior and don't implicitly "set" all
 * fields, it must only set the fields that data has been passed in.
 * <p>
 * The performDirtyReadCheck property can be used to enforce dirty-read checks during
 * updates/deletes.
 * NOTE: That will only work if a Graph has been configured for dirty-read check, and if
 * the graph contains the field used for that check.
 *
 * @author PaulE
 * @version 1.0
 */
public abstract class GraphDataObject implements IFlexFields {

    private static final Logger log = Logger.getLogger(GraphDataObject.class);
    protected transient java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    private Boolean performDirtyReadCheck;
    private Boolean deleteObject;
    private String[] nullify;
    private ProcessEventGraph[] processEventGraphs;
    private final Map<String, Object> changes = new HashMap<>();
    private transient Validator<GraphDataObject> validator; //do not create a getter for validators, will cause serialization problems
    private transient FlexBean flexBean;
    private transient Boolean shouldLookupFlexbean = true;

    // TODO-SWAT add script event here

    /**
     * Creates a new instance of GraphDataObject
     */
    public GraphDataObject() {
        //log.debug("Create Change Listener");
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                valueChanged(evt.getPropertyName(), evt.getOldValue());
                log.debug("Field '" + evt.getPropertyName() + "' updated from '" + evt.getOldValue() + "' to '" + evt.getNewValue() + "'");
            }
        });
    }

    /**
     * Adds a PropertyChangeListener to the listener list.
     *
     * @param l The listener to add.
     */
    public final void addPropertyChangeListener(java.beans.PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    /**
     * Removes a PropertyChangeListener from the listener list.
     *
     * @param l The listener to remove.
     */
    public final void removePropertyChangeListener(java.beans.PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    /**
     * Clear all the changes on this bean. Will cause all future calls to
     * {@link #hasChanged(String)} to return false
     */
    public void clearChanges() {
        changes.clear();

        // If this bean has flexfield support, clear the changes on it as well.
        if (flexBean != null) {
            flexBean.clearChanges();
        }
    }

    /**
     * Has the bean changed since it was created or last cleared.
     *
     * @return true if the bean has been modified
     */
    public boolean hasChanged() {
        boolean returnValue = changes != null && changes.size() > 0;

        // FlexField support:
        // if this class supports flex fields, then the returned value should take that into account.
        if (!returnValue && flexBean != null) {
            if (log.isDebugEnabled()) {
                log.debug("Invoking hasChanged() method on the FlexBean");
            }

            returnValue = flexBean.hasChanged();
        }

        return returnValue;
    }

    /**
     * Has the specified bean property been changed since the bean was
     * created or last cleared
     *
     * @param property Name of bean property to check
     * @return true if the property has been modified
     */
    public boolean hasChanged(String property) {
        return changes.containsKey(property);
    }

    /**
     * Get the original value for this field, throw an error if this field has no
     * changed, so you should consider first checking with the  {@link #hasChanged(String)}
     * method
     *
     * @param property Name of bean property to check
     * @return The object representing the original values. Primitives are return as their
     * Object counterparts.
     * @throws NoSuchFieldException Throw if the property has not been changed, or does not exist.
     */
    public Object getOriginalValue(String property) throws NoSuchFieldException {
        if (changes.containsKey(property))
            return changes.get(property);
        else
            throw new NoSuchFieldException(property);
    }

    /**
     * Returns a map of changed fields.
     * The map contains the name of the field and it's initial value.
     * NOTE: The returned map is a read-only view, and attempts to modify the returned map will result in an UnsupportedOperationException.
     *
     * @return a map of changed fields.
     */
    public Map<String, Object> changedFields() {
        return changes != null ? Collections.unmodifiableMap(changes) : null;
    }

    /**
     * This is called prior to a this GraphDataObject being used in a service.
     * This is only called on the root GraphDataObject of a the graph. This method
     * should roll up lower level validations on child objects in the graph.
     * <p>
     * The GraphDataObject is assumed to be valid if no exception is thrown.
     *
     * @throws ApplicationExceptions Contains an list of possible business logic
     *                               exceptions that caused the validation to fail
     * @throws FrameworkException    Thrown if there is an environment/runtime problem
     *                               that prevented the validation from being performed.
     */
    public void validate() throws ApplicationExceptions, FrameworkException {
        // TODO-SWAT fire script event here
        if (validator != null) {
            try {
                validator.validate(this);
            } catch (ApplicationException exception) {
                // Wrap all validation exceptions into an application exception.
                throw new ApplicationExceptions(exception);
            }
        }

        // If this instance supports flex fields, call validate on the flexbean to validate the flexfield values.
        // Note that the potential exceptions will be raised to the caller automatically and should not need to
        // be wrapped.
        if (flexBean != null) {
            flexBean.validate();
        }
    }

    /**
     * Converts the current contents of the GraphDataObject to a multi-line
     * nested output string, listing all the object's properties.
     * <p>
     * Property names that are suffixed with an asterisk (*) indicate
     * that the value <CODE>hasChanged()</CODE>.
     *
     * @return test string listing the beans contents
     */
    public String toString() {
        return TransformerUtils.printGraph(this);
    }

    /**
     * Converts the current contents of the bean to a multi-line
     * nested output string, listing all the bean's properties.
     * <p>
     * Property names that are suffixed with an asterisk (*) indicate
     * that the value <CODE>hasChanged()</CODE>.
     *
     * @return test string listing the beans contents
     */
    public String toString(List objectStack) {
        return TransformerUtils.printGraph(this, objectStack);
    }

    private void valueChanged(String property, Object value) {
        if (!changes.containsKey(property))
            changes.put(property, value);
    }

    /**
     * Getter for property performDirtyReadCheck.
     *
     * @return Value of property performDirtyReadCheck.
     */
    public Boolean getPerformDirtyReadCheck() {
        return performDirtyReadCheck;
    }

    /**
     * Setter for property performDirtyReadCheck.
     *
     * @param performDirtyReadCheck New value of property performDirtyReadCheck.
     */
    public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck) {
        this.performDirtyReadCheck = performDirtyReadCheck;
    }

    /**
     * Getter for property deleteObject.
     *
     * @return Value of property deleteObject.
     */
    public Boolean getDeleteObject() {
        return deleteObject;
    }

    /**
     * Setter for property deleteObject.
     *
     * @param deleteObject New value of property deleteObject.
     */
    public void setDeleteObject(Boolean deleteObject) {
        this.deleteObject = deleteObject;
    }

    /**
     * Getter for property nullify.
     *
     * @return Value of property nullify.
     */
    public String[] getNullify() {
        return this.nullify;
    }

    /**
     * Setter for property nullify.
     *
     * @param nullify New value of property nullify.
     */
    public void setNullify(String[] nullify) {
        this.nullify = nullify;
        if (nullify != null) {
            if (log.isDebugEnabled())
                log.debug("Fields to be nullified: " + Arrays.toString(nullify));
            for (String s : nullify)
                valueChanged(s, null);
        }
    }

    /**
     * Getter for property processEventGraphs.
     *
     * @return Value of property processEventGraphs.
     */
    public ProcessEventGraph[] getProcessEventGraphs() {
        return this.processEventGraphs;
    }

    /**
     * Setter for property processEventGraphs.
     *
     * @param processEventGraphs New value of property processEventGraphs.
     */
    public void setProcessEventGraphs(ProcessEventGraph[] processEventGraphs) {
        ProcessEventGraph[] oldProcessEventGraphs = this.processEventGraphs;
        this.processEventGraphs = processEventGraphs;
        propertyChangeSupport.firePropertyChange("processEventGraphs", oldProcessEventGraphs, processEventGraphs);
    }

    /**
     * Sets the validator.
     *
     * @param validator the validator.
     */
    public void setValidator(Validator<GraphDataObject> validator) {
        this.validator = validator;
    }

    /**
     * Returns the debug information
     *
     * @return The debug information
     */
    public String toXMLString() {
        return TransformerUtils.printXMLGraph(this);
    }

    /**
     * Compares this object with another Graph object.
     * Returns a true if both the objects have the same primary key.
     *
     * @param obj the other Graph object.
     * @return a true if both the objects have the same primary key.
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null && this.getClass() == obj.getClass()) {
            try {
                GraphMapping mapping = MappingFactory.getInstance(this);
                Collection<String> keyFields = mapping != null ? mapping.getKeyFields() : null;
                if (keyFields != null && keyFields.size() > 0) {
                    for (String keyField : keyFields) {
                        Object thisValue = BeanHelper.getField(this, keyField);
                        Object targetValue = BeanHelper.getField(obj, keyField);
                        result = thisValue == null ? targetValue == null : thisValue.equals(targetValue);
                        if (log.isDebugEnabled())
                            log.debug(keyField + ": thisValue=" + thisValue + ", targetValue=" + targetValue + ", comparison=" + result);
                        if (!result)
                            break;
                    }
                    if (log.isDebugEnabled())
                        log.debug("Output=" + result);
                    return result;
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Will invoke super.equals() since key fields cannot be determined for the class '" + this.getClass() + '\'');
                }
            } catch (Exception e) {
                if (log.isDebugEnabled())
                    log.debug("Will invoke super.equals() since an error occurred while comparing the key fields", e);
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("Will invoke super.equals() since the " + (obj == null ? "argument is null" : "argument's class '" + obj.getClass() + "' does not match the current class '" + this.getClass() + '\''));
        }
        result = super.equals(obj);
        if (log.isDebugEnabled())
            log.debug("Output from super.equals() is " + result);
        return result;
    }

    /**
     * Returns the hashCode of this object based on it's primary key.
     *
     * @return the hashCode of this object based on it's primary key.
     */
    @Override
    public int hashCode() {
        int result = 0;
        try {
            GraphMapping mapping = MappingFactory.getInstance(this);
            Collection<String> keyFields = mapping != null ? mapping.getKeyFields() : null;
            if (keyFields != null && keyFields.size() > 0) {
                for (String keyField : keyFields) {
                    Object thisValue = BeanHelper.getField(this, keyField);
                    if (thisValue != null)
                        result += thisValue.hashCode();
                }
                if (log.isDebugEnabled())
                    log.debug("Output=" + result);
                return result;
            } else {
                if (log.isDebugEnabled())
                    log.debug("Will invoke super.hashCode() since key fields cannot be determined for the class '" + this.getClass() + '\'');
            }
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Will invoke super.hashCode() since an error occurred while evaluating the key fields", e);
        }
        result = super.hashCode();
        if (log.isDebugEnabled())
            log.debug("Output from super.hashCode() is " + result);
        return result;
    }

    /**
     * Returns a FlexBean instance that encapsulates the FlexFields for the persistent object.
     * The FlexBean instance is created during object instantiation when StaticContext.configure is performed.
     * For instances that have not been configured with flexbean support (via aop.xml configuration), this property
     * will always return null.
     *
     * @return a FlexBean instance that encapsulates the FlexFields for the persistent object or null of the object does
     * has not been configured to support flexbeans
     */
    public FlexBean getFlexBean() throws FrameworkException, ApplicationExceptions {
        if (shouldLookupFlexbean) {
            shouldLookupFlexbean = false;
            FlexBean.configureFlexBean(this);
        }
        return flexBean;
    }

    /**
     * Sets a FlexBean instance that encapsulates the FlexFields for an implementation class.
     *
     * @param flexBean a FlexBean instance.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    public void setFlexBean(FlexBean flexBean) throws ApplicationExceptions, FrameworkException {
        if (flexBean == null) {
            log.debug("flexBean is null");
            return;
        }
        //copy the flexParams from the input to the current instance.
        FlexBean currentInstance = getFlexBean();
        if (currentInstance != null) {
            currentInstance.setFlexParams(flexBean.getFlexParams());
        } else {
            this.flexBean = flexBean;
        }
    }
}
