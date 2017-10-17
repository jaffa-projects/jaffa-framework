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

package org.jaffa.presentation.portlet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.Globals;
import javax.servlet.http.HttpServletRequest;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.session.WidgetCache;
import org.jaffa.presentation.portlet.session.UserSession;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import org.jaffa.presentation.portlet.widgets.taglib.FormTag;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import java.util.Iterator;

/** This is the base class for all the 'Form' classes
 */
public class FormBase extends ActionForm {

    /** Component to which this form belongs */
    private Component m_component = null;

    /** Widget-cache for the form. Maintained here for convenience */
    private WidgetCache m_widgetCache = null;

    /** Returns the Component to which this Form belongs
     * @return The Component for this Form
     */
    public Component getComponent() {
        return m_component;
    }
    /** Sets the Component for the Form
     * Pass all derived classes to the static context to be initialized
     *
     * @param component The Component object
     */
    public void setComponent(Component component) {
        m_component = component;
        setWidgetCache(null);
        StaticContext.initialize(this);
    }

    /** Returns the WidgetCache of all the models for the Component
     * @return The WidgetCache object
     */
    public WidgetCache getWidgetCache() {
        if (m_component != null && m_widgetCache == null)
            m_widgetCache = getComponent().getUserSession().getWidgetCache( getComponent().getComponentId() );
        return m_widgetCache;
    }
    /** Sets the WidgetCache object, which will have the models for the Component
     * @param widgetCache The WidgetCache object
     */
    private void setWidgetCache(WidgetCache widgetCache) {
        m_widgetCache = widgetCache;
    }

    /** The form should override this method for defaulting the data in the form-bean
     */
    public void initForm() {
    }

    /** The form should override this method for destroying any references that it holds
     */
    public void cleanup() {
    }

    /** This sets the Component & the WidgetCache fields
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        /* NOTE: Prior to Struts1.1, only the ActionServlet was invoking the reset() method.
         * But in Struts1.1, the reset() method is called from 2 places -
         * 1- RequestProcessor (which in turn is invoked from ActionServlet)
         * 2- FormTag
         * While processing an action, we need to set the component on the formbean, before the other properties are set. Hence the need for setting the component here.
         * However, it is quite possible, that an action on one screen, will result in returning a FormKey for a different component. In that case, when the other component's screen is being rendered, the FormTag will invoke this method, but pass the componentId of the original component in the request stream. This would mean an erroneous component being set on the formbean. However, the Jaffa-FormTag will ultimately set the correct component. We just shudn't be setting the widget-cache in this method.
         */
        // this will set the Component & WidgetCache
        UserSession us = UserSession.getUserSession(request);
        String componentId = request.getParameter(FormTag.PARAMETER_COMPONENT_ID);
        if (componentId != null)
            setComponent( us.getComponent(componentId) );
        else
            setComponent(null);
        setWidgetCache(null);
    }

    /** This clears all the errors generated while processing this Form
     * @param request The servlet request we are processing
     */
    public void clearErrors(HttpServletRequest request) {
        request.removeAttribute(Globals.ERROR_KEY);
    }

    /** Returns the errors generated while processing this Form
     * @param request The servlet request we are processing
     * @return If no errors are found, return null or an ActionMessages object with recorded error messages
     */
    public static ActionMessages getErrors(HttpServletRequest request) {
        return (ActionMessages) request.getAttribute(Globals.ERROR_KEY);
    }

    /** Checks if any errors have been generated
     * @param request The servlet request we are processing
     * @return true if errors exist
     */
    public static boolean hasErrors(HttpServletRequest request) {
        ActionMessages errors = getErrors(request);
        if (errors != null && !errors.isEmpty())
            return true;
        else
            return false;
    }

    /** Record an error
     * @param request The servlet request we are processing
     * @param property Property name (or ActionMessages.GLOBAL_ERROR)
     * @param error The error message to be added
     */
    public static void raiseError(HttpServletRequest request, String property, ActionMessage error) {
        ActionMessages errors = getErrors(request);
        if (errors == null) {
            errors = new ActionMessages();
            request.setAttribute(Globals.ERROR_KEY, errors);
        }
        errors.add(property, error);
    }

    /** Record an error. This implicitly creates an ActionMessage for the input key
     * @param request The servlet request we are processing
     * @param property Property name (or ActionMessages.GLOBAL_ERROR)
     * @param key Message key for this error message
     */
    public static void raiseError(HttpServletRequest request, String property, String key) {
        raiseError(request, property, new ActionMessage(key));
    }

    /** Record an error. This implicitly creates an ActionMessage for the input key & replacement values
     * @param request The servlet request we are processing
     * @param property Property name (or ActionMessages.GLOBAL_ERROR)
     * @param key Message key for this error message
     * @param value0 First replacement value
     */
    public static void raiseError(HttpServletRequest request, String property, String key
    , Object value0) {
        raiseError(request, property, new ActionMessage(key, value0));
    }

    /** Record an error. This implicitly creates an ActionMessage for the input key & replacement values
     * @param request The servlet request we are processing
     * @param property Property name (or ActionMessages.GLOBAL_ERROR)
     * @param key Message key for this error message
     * @param value0 First replacement value
     * @param value1 Second replacement value
     */
    public static void raiseError(HttpServletRequest request, String property, String key
    , Object value0, Object value1) {
        raiseError(request, property, new ActionMessage(key, value0, value1));
    }

    /** Record an error. This implicitly creates an ActionMessage for the input key & replacement values
     * @param request The servlet request we are processing
     * @param property Property name (or ActionMessages.GLOBAL_ERROR)
     * @param key Message key for this error message
     * @param value0 First replacement value
     * @param value1 Second replacement value
     * @param value2 Third replacement value
     */
    public static void raiseError(HttpServletRequest request, String property, String key
    , Object value0, Object value1, Object value2) {
        raiseError(request, property, new ActionMessage(key, value0, value1, value2));
    }

    /** Record an error. This implicitly creates an ActionMessage for the input key & replacement values
     * @param request The servlet request we are processing
     * @param property Property name (or ActionMessages.GLOBAL_ERROR)
     * @param key Message key for this error message
     * @param value0 First replacement value
     * @param value1 Second replacement value
     * @param value2 Third replacement value
     * @param value3 Fourth replacement value
     */
    public static void raiseError(HttpServletRequest request, String property, String key
    , Object value0, Object value1, Object value2, Object value3) {
        raiseError(request, property, new ActionMessage(key, value0, value1, value2, value3));
    }

    /** Record an error. This implicitly creates an ActionMessage for the input key & replacement values
     * @param request The servlet request we are processing
     * @param property Property name (or ActionMessages.GLOBAL_ERROR)
     * @param key Message key for this error message
     * @param values Array of replacement values
     */
    public static void raiseError(HttpServletRequest request, String property, String key, Object[] values) {
        raiseError(request, property, new ActionMessage(key, values));
    }

    /** Record an error. This implicitly creates an ActionMessage for the input key & replacement values.
     * @param request The servlet request we are processing
     * @param property Property name (or ActionMessages.GLOBAL_ERROR)
     * @param appExp the exception against which the error is being raised. This exception will have the errorToken and arguments, if any.
     */
    public static void raiseError(HttpServletRequest request, String property, ApplicationException appExp) {
        String key = appExp.getMessage();
        Object[] args = appExp.getArguments();
        if (args == null || args.length == 0)
            raiseError(request, property, key);
        else
            raiseError(request, property, key, args);
    }

    /** Record an error. This implicitly creates an ActionMessage for the input key & replacement values.
     * @param request The servlet request we are processing
     * @param property Property name (or ActionMessages.GLOBAL_ERROR)
     * @param appExps Error will raised against each ApplicationException passed in this parameter.
     */
    public static void raiseError(HttpServletRequest request, String property, ApplicationExceptions appExps) {
        for (Iterator i = appExps.iterator(); i.hasNext();)
            raiseError(request, property, (ApplicationException) i.next());
    }
}
