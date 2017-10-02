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

package org.jaffa.presentation.portlet.component;

import java.util.*;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.*;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.FormKeyChangeEvent;
import org.jaffa.presentation.portlet.FormKeyChangeListener;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.util.BeanHelper;

/** The base class for all Components
 */
public abstract class Component implements IComponent {
    private static Logger log = Logger.getLogger(Component.class);
    
    private String m_componentId = null;
    private ComponentDefinition m_componentDefinition = null;
    private UserSession m_userSession = null;
    private FormKey m_returnToFormKey = null;
    private FormKey m_containerFormKey = null;
    private Collection m_formKeyChangeListeners = null;
    private DateTime m_lastActivityDate = new DateTime();
    private Collection m_childComponents = null;
    private String m_token = null;

    /**
     * Pass this to the static context to be initialized.
     */
    protected void initializeThis() {
        StaticContext.initialize(this);
    }
    
    // *****************************************
    //  METHODS INVOKED BY THE ComponentManager
    // *****************************************
    void setComponentId(String componentId) {
        m_componentId = componentId;
    }
    
    void setComponentDefinition(ComponentDefinition componentDefinition) {
        m_componentDefinition = componentDefinition;
    }
    
    void setUserSession(UserSession userSession) {
        m_userSession = userSession;
    }
    
    
    
    
    // ******************************
    //  IMPLEMENTATION METHODS
    // ******************************
    
    /** Returns a FormKey, which has the componentId & the formName to which control should be passed
     * @throws FrameworkException if any framework error occurs.
     * @throws ApplicationExceptions if any application error occurs.
     * @return the FormKey object
     */
    public abstract FormKey display() throws FrameworkException, ApplicationExceptions;
    
    /** This will kill all the Forms
     * Finally de-register itself from the UserSession
     */
    public void quit(){
        if (m_componentId != null) {
            if(log.isInfoEnabled())
                log.info("Quitting Component " + getComponentDefinition().getComponentName() + " having id " + getComponentId());
            if (log.isDebugEnabled())
                log.debug(null, new Exception("This exception is created merely to record the current stacktrace on a component.quit()"));
            
            m_userSession.removeComponent(this);
            
            // Nullify all the references
            m_componentId = null;
            m_componentDefinition = null;
            m_userSession = null;
            
            // Pass the ReturnToFormKey to all the registered FormKeyChangeListeners
            if (m_formKeyChangeListeners != null && m_formKeyChangeListeners.size() > 0) {
                FormKeyChangeEvent e = new FormKeyChangeEvent(this, getReturnToFormKey());
                for (Iterator i = m_formKeyChangeListeners.iterator(); i.hasNext(); )
                    ((FormKeyChangeListener) i.next()).formKeyChanged(e);
                m_formKeyChangeListeners.clear();
            }
            m_containerFormKey = null;
            m_formKeyChangeListeners = null;
        } else {
            if(log.isDebugEnabled())
                log.debug("Component's quit() method has already been invoked");
        }
    }
    
    /** This should invoke the quit() method, and then return the FormKey for the calling screen.
     * The FormKey should have been set by a call to setReturnToFormKey().
     * @return The FormKey for the caling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey quitAndReturnToCallingScreen() {
        quit();
        return getReturnToFormKey();
    }
    
    /** Returns the Id for the component
     * @return The componentId
     */
    public String getComponentId() {
        return m_componentId;
    }
    
    /** Returns the ComponentDefinition based on which this Component was created
     * @return The ComponentDefintion object for the Component
     */
    public ComponentDefinition getComponentDefinition() {
        return m_componentDefinition;
    }
    
    /** Returns the UserSession, under which the Component was created
     * @return The UserSession object
     */
    public UserSession getUserSession() {
        return m_userSession;
    }
    
    /** Getter for property returnToFormKey.
     * This FormKey determines the screen to display when quitting from a component.
     * @return Value of property returnToFormKey.
     */
    public FormKey getReturnToFormKey() {
        return m_returnToFormKey;
    }
    
    /** Setter for property returnToFormKey.
     * This FormKey determines the screen to display when quitting from a component.
     * @param returnToFormKey New value of property returnToFormKey.
     */
    public void setReturnToFormKey(FormKey returnToFormKey) {
        m_returnToFormKey = returnToFormKey;
    }
    
    /** Getter for property containerFormKey.
     * This property is useful when this component is being rendered as a tile inside another component.
     * The outer component is expected to set this property on this component.
     * The outer component is also expected to register the FormKeyChangeListener on this component.
     * The ActionBase will intercept all FormKeys for this component. It will then fire the FormKeyChangeEvents on the listeners. The ActionBase will then return the ContainerFormKey.
     * The Component will fire the FormKeyChangeEvents on the listeners during the quit(), passing the ReturnToFormKey.
     * All this helps render the tiles correctly.
     * @return Value of property containerFormKey.
     *
     */
    public FormKey getContainerFormKey() {
        return m_containerFormKey;
    }
    
    /** Setter for property containerFormKey.
     * This property is useful when this component is being rendered as a tile inside another component.
     * The outer component is expected to set this property on this component.
     * The outer component is also expected to register the FormKeyChangeListener on this component.
     * The ActionBase will intercept all FormKeys for this component. It will then fire the FormKeyChangeEvents on the listeners. The ActionBase will then return the ContainerFormKey.
     * The Component will fire the FormKeyChangeEvents on the listeners during the quit(), passing the ReturnToFormKey.
     * All this helps render the tiles correctly.
     * @param containerFormKey New value of property containerFormKey.
     *
     */
    public void setContainerFormKey(FormKey containerFormKey) {
        m_containerFormKey = containerFormKey;
    }
    
    /** This method is useful when this component is being rendered as a tile inside another component.
     * It helps the ActionBase decide if a FormKey is to be replaced by the containerFormKey.
     * By default, this method will return false for all FormKeys having formName ending with
     * either "ExcelResults" or "XmlResults"; which would result in those pages appearing in
     * stand-alone screens without the trimmings of the container component.
     * For all other FormKeys, a true will be returned; resulting in the ActionBase replacing them
     * with the containerFormKey and invoking the registered FormKeyChangeListener instance.
     * <p>
     * A component implementation may override the default behavior as per it's needs.
     * @param fk The input FormKey.
     *
     */
    public boolean replaceWithContainerFormKey(FormKey fk) {
        String formName = fk.getFormName();
        boolean b = formName == null || !formName.matches(".+(Excel|Xml)Results");
        if (log.isDebugEnabled())
            log.debug("FormKey '" + fk + (b ? "' will be" : " will not be") + " replaced by the containerFormKey '" + m_containerFormKey + '\'');
        return b;
    }

    /** Registers listener so that it will receive FormKeyChangeEvents.
     * The ActionBase typically creates the FormKeyChangeEvent object, when processing an event for a component, that has a ContainerFormKey.
     * It will then fire the FormKeyChangeListener registered with the component, passing the FormKeyChangeEvent object.
     * The Component will fire the FormKeyChangeEvents on the listeners during the quit(), passing the ReturnToFormKey.
     * @param listener the FormKeyChangeListener to register.
     */
    public void addFormKeyChangeListener(FormKeyChangeListener listener) {
        if (m_formKeyChangeListeners == null)
            m_formKeyChangeListeners = new HashSet();
        m_formKeyChangeListeners.add(listener);
    }
    
    /** Unregisters listener so that it will no longer receive FormKeyChangeEvents.
     * @param listener the FormKeyChangeListener to be removed.
     */
    public void removeFormKeyChangeListener(FormKeyChangeListener listener) {
        if (m_formKeyChangeListeners != null)
            m_formKeyChangeListeners.remove(listener);
    }
    
    /** Returns an array of all the FormKeyChangeListeners registered on this component.
     * @return all of the component's FormKeyChangeListeners or a null if no ancestor listeners are currently registered.
     */
    public FormKeyChangeListener[] getFormKeyChangeListeners() {
        if (m_formKeyChangeListeners != null)
            return (FormKeyChangeListener[]) m_formKeyChangeListeners.toArray(new FormKeyChangeListener[0]);
        else
            return null;
    }
    
    /** Returns a true if the component is still active. The component will be set to inactive status after a quit().
     * @return a true if the component is still active.
     */
    public boolean isActive() {
        // m_componentId is set to null after a quit()
        return m_componentId != null;
    }
    
    /** Getter for property token.
     * @return Value of property token.
     *
     */
    public String getToken() {
        return m_token;
    }
    
    /** Setter for property token.
     * @param token New value of property token.
     *
     */
    public void setToken(String token) {
        m_token = token;
    }
    
    // ******************************
    //  ADDITIONAL METHODS
    // ******************************
    /** Convenience Method for 'ComponentManager.run()'.
     * This may throw the runtime ComponentCreationRuntimeException
     * @param component The name of the component to create. There should be a valid definition for this name in the 'components.xml' file
     * @return An instance of the Component
     */
    public Component run(String component) {
        Component comp = ComponentManager.run(component, m_userSession);
        addChildComponent(comp);
        return comp;
    }
    
    /** Invoke the setters on the component passing the parameters in the request-stream
     * This is a convenience method which can be invoked after creation of a new Component
     * @param request The HTTP request we are processing
     */
    public void reflectAndSetParms(HttpServletRequest request) {
        Class clazz = this.getClass();
        for (Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
            String parameterName = (String) enumeration.nextElement();
            try {
                BeanHelper.setField(this, parameterName, request.getParameter(parameterName));
            } catch (Exception e) {
                // do nothing
            }
        }
    }
    
    
    /** This updates the activity timestamp on the component. This will ideally be called by the central servlet that services an event on a component.
     */
    public void updateLastActivityDate() {
        m_lastActivityDate = new DateTime();
    }
    
    /** This returns the timestamp for the last known activity on the component.
     * @return The timestamp for the last known activity on the component.
     */
    public DateTime returnLastActivityDate() {
        return m_lastActivityDate;
    }
    
    /** This adds a child component to an internal list. A child component instantiated via the run() method, will be implicitly added to the internal list.
     * @param component The child component.
     */
    public void addChildComponent(Component component) {
        if (m_childComponents == null)
            m_childComponents = new LinkedList();
        m_childComponents.add(component);
    }
    
    /** This returns a collection of components which were created by this component.
     * @return a collection of components which were created by this component.
     */
    public Collection returnChildComponents() {
        return m_childComponents;
    }

}
