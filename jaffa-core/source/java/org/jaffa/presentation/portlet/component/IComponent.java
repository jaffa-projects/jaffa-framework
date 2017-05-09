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

package org.jaffa.presentation.portlet.component;

import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.FormKeyChangeEvent;
import org.jaffa.presentation.portlet.FormKeyChangeListener;

/** All the components implement this interface
 */
public interface IComponent {
    
    /** Returns a FormKey, which has the componentId & the formName to which control should be passed
     * @throws FrameworkException if any framework error occurs.
     * @throws ApplicationExceptions if any application error occurs.
     * @return the FormKey object
     */
    public FormKey display() throws FrameworkException, ApplicationExceptions;
    
    /** This will kill all the Forms
     * Finally de-register itself from the UserSession
     */
    public void quit();
    
    /** This should invoke the quit() method, and then return the FormKey for the calling screen.
     * The FormKey should have been set by a call to setReturnToFormKey().
     * @return The FormKey for the caling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey quitAndReturnToCallingScreen();
    
    /** Returns the Id for the component
     * @return The componentId
     */
    public String getComponentId();
    
    /** Returns the ComponentDefinition based on which this Component was created
     * @return The ComponentDefintion object for the Component
     */
    public ComponentDefinition getComponentDefinition();
    
    /** Returns the UserSession, under which the Component was created
     * @return The UserSession object
     */
    public UserSession getUserSession();
    
    /** Getter for property returnToFormKey.
     * This FormKey determines the screen to display when quitting from a component.
     * @return Value of property returnToFormKey.
     */
    public FormKey getReturnToFormKey();
    
    /** Setter for property returnToFormKey.
     * This FormKey determines the screen to display when quitting from a component.
     * @param returnToFormKey New value of property returnToFormKey.
     */
    public void setReturnToFormKey(FormKey returnToFormKey);
    
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
    public FormKey getContainerFormKey();
    
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
    public void setContainerFormKey(FormKey containerFormKey);
    
    /** Registers listener so that it will receive FormKeyChangeEvents.
     * The ActionBase typically creates the FormKeyChangeEvent object, when processing an event for a component, that has a ContainerFormKey.
     * It will then fire the FormKeyChangeListener registered with the component, passing the FormKeyChangeEvent object.
     * The Component will fire the FormKeyChangeEvents on the listeners during the quit(), passing the ReturnToFormKey.
     * @param listener the FormKeyChangeListener to register.
     */
    public void addFormKeyChangeListener(FormKeyChangeListener listener);
    
    /** Unregisters listener so that it will no longer receive FormKeyChangeEvents.
     * @param listener the FormKeyChangeListener to be removed.
     */
    public void removeFormKeyChangeListener(FormKeyChangeListener listener);
    
    /** Returns an array of all the FormKeyChangeListeners registered on this component.
     * @return all of the component's FormKeyChangeListeners or a null if no ancestor listeners are currently registered.
     */
    public FormKeyChangeListener[] getFormKeyChangeListeners();
    
    /** Returns a true if the component is still active. The component should be set to inactive status after a quit().
     * @return a true if the component is still active.
     */
    public boolean isActive();
    
    /** Getter for property token.
     * @return Value of property token.
     *
     */
    public String getToken();
    
    /** Setter for property token.
     * @param token New value of property token.
     *
     */
    public void setToken(String token);
    
}
