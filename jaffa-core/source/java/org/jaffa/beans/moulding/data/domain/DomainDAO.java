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
 * DomainDAO.java
 *
 * Created on February 12, 2004, 11:00 AM
 */

package org.jaffa.beans.moulding.data.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jaffa.beans.moulding.mapping.BeanMoulder;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

/** This is the base class for all Domain Data Access Objects.
 * <p>
 * It provides a default implementation of the PropertyChange function from
 * the Java Bean specification, which keeps track of which properties in
 * the Bean have been modified since construction, or since the last <code>
 * clearChanges()</code>
 * <p>
 * Tools such as web service implementations may choose to ignore null values. 
 * In such a scenario, the nullify property can be used to specify the properties
 * to be nullified.
 * <p>
 * It therefore provides (for the bean moulding framework) a way to infer if
 * a value has been passed, which can be used to see if a field value should
 * be moulded from the source to target bean.
 *
 * @author  PaulE
 * @version 1.0
 */
public abstract class DomainDAO {

    private static Logger log = Logger.getLogger(DomainDAO.class);

    private String[] nullify;
    private Map changes = new HashMap();

    /** Utility field used by bound properties. */
    protected java.beans.PropertyChangeSupport propertyChangeSupport =  new java.beans.PropertyChangeSupport(this);

    /** Creates a new instance of DAO */
    public DomainDAO() {
        //log.debug("Create Change Listener");
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                //log.debug("Listener Invoked on "+evt.getPropertyName());
                //if(!(evt.getOldValue()==null && evt.getNewValue()==null)) {
                    valueChanged(evt.getPropertyName(),evt.getOldValue());
                    log.debug("Field '"+evt.getPropertyName()+"' updated from '"+evt.getOldValue()+"' to '"+evt.getNewValue()+"'");
              //}
             /* else
                   log.debug("IGNORE Field '"+evt.getPropertyName()+"' updated from '"+evt.getOldValue()+"' to '"+evt.getNewValue()+"'");
                  */
            }
        });
    }

    /** Adds a PropertyChangeListener to the listener list.
     * @param l The listener to add.
     */
    public final void addPropertyChangeListener(java.beans.PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    /** Removes a PropertyChangeListener from the listener list.
     * @param l The listener to remove.
     *
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
    }
    /**
     * Has the specified bean property been changed since the bean was
     * created or last cleared
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
     * @param property Name of bean property to check
     * @throws NoSuchFieldException Throw if the property has not been changed, or does not exist.
     * @return The object representing the original values. Primitives are return as their
     * Object counterparts.
     */
    public Object getOriginalValue(String property) throws NoSuchFieldException {
        if(changes.containsKey(property))
            return changes.get(property);
        else
            throw new NoSuchFieldException(property);
    }

    /**
     * This is called prior to a domain DAO being used in a service. This is
     *  only called on the top level DAO of a DAO Graph, so this method should roll
     *  up lower level validations. The DAO is assumed to be valid if no exception
     *  is thrown.
     *
     * @throws ApplicationExceptions Contains an list of possible business logic
     * exceptions that caused the validation to fail
     * @throws FrameworkException Thrown if there is an environment/runtime problem
     * that prevented the validation from being performed.
     */
    public abstract void validate() throws ApplicationExceptions, FrameworkException;

    /**
     * Converts the current contents of the bean to a multi-line
     * nested output string, listing all the bean's properties.
     * <p>
     * Property names that are suffixed with an asterisk (*) indicate
     * that the value <CODE>hasChanged()</CODE>.
     * @return test string listing the beans contents
     */
    public String toString() {
        return BeanMoulder.printBean(this);
    }

    /**
     * Converts the current contents of the bean to a multi-line
     * nested output string, listing all the bean's properties.
     * <p>
     * Property names that are suffixed with an asterisk (*) indicate
     * that the value <CODE>hasChanged()</CODE>.
     * @return test string listing the beans contents
     */
    public String toString(List objectStack) {
        return BeanMoulder.printBean(this, objectStack);
    }

    private void valueChanged(String property, Object value) {
        if(!changes.containsKey(property))
            changes.put(property, value);
    }

    /** Getter for property nullify.
     * @return Value of property nullify.
     */
    public String[] getNullify() {
        return this.nullify;
    }

    /** Setter for property nullify.
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

}
