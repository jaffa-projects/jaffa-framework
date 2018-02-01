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

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.ComponentManager;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.session.UserSession;

import java.security.AccessControlException;

import org.jaffa.exceptions.ApplicationExceptions;

/**
 * This Action invokes the component passed in the request-stream
 * <p/>
 * The internal parameters that can be passed are
 * component -> The name of the component to execute
 * finalUrl -> The place to go after executing the component. This should be a valid forward mapping as defined in the struts-config.xml file.
 * All other paramters will be introspected on the component being executed, prior to
 * calling its 'display()' method.
 */
public class StartComponentAction extends ActionBase {

    private static Logger log = Logger.getLogger(StartComponentAction.class);

    /**
     * Constant to denote the 'component' parameter passed to this Action.
     */
    public static final String COMPONENT_PARAMETER = "component";

    /**
     * Constant to denote the 'finalUrl' parameter passed to this Action.
     */
    public static final String FINALURL_PARAMETER = "finalUrl";

    /**
     * Where to go (via struts forwards) if component access is denied
     */
    private static final String NO_ACCESS_FORWARD = "jaffa_noComponentAccess";

    /**
     * Invokes the component passed in the request stream.
     *
     * @return A FormKey instance which describes the current Component & Form
     * @throws Exception if the application business logic throws an exception
     */
    public FormKey defaultAction() throws Exception {

        // Get the component & finalUrl parameter-values from the request-stream
        String initialComponent = request.getParameter(COMPONENT_PARAMETER);
        String finalUrl = request.getParameter(FINALURL_PARAMETER);
        if (log.isDebugEnabled())
            log.debug("Received the Initial Parameters - component=" + initialComponent + ", finalUrl=" + finalUrl);

        // set the default value for initialComponent
        if (initialComponent == null) {
            if (finalUrl != null) {
                // if there is just a finalUrl and no component specified, just redirect to this URL
                // the form key escapes the input for XSS prevention
                return new FormKey(finalUrl, null);
            } else {
                String str = "The parameter " + COMPONENT_PARAMETER + " should be passed";
                log.error(str);
                throw new IllegalArgumentException(str);
            }
        }

        /**********************************************************
         * Create Component To Run
         * **********************************************************/
        if (log.isDebugEnabled()) log.debug("Calling the ComponentManager to run " + initialComponent);

        Component comp = null;
        try {
            comp = ComponentManager.run(initialComponent, UserSession.getUserSession(request));
        } catch (AccessControlException e) {
            // No access to component, direct to an error page
            request.setAttribute("componentName", initialComponent);
            return new FormKey(NO_ACCESS_FORWARD, null);
        }

        // Initialize the HistoryNav by setting an appropriate attribute in the request stream
        // Need to do this before setting "returnToFormKey" otherwise it will assume "close_browser"
        HistoryNav.initializeHistoryNav(request, finalUrl);

        // Set the ReturnToFromKey property of the component
        if (finalUrl != null) {
            comp.setReturnToFormKey(new FormKey(finalUrl, null));
        }

        // Set the parameters on the component using reflection
        comp.reflectAndSetParms(request);

        FormKey fk;
        try {
            // get the formKey for the component
            fk = comp.display();
        } catch (ApplicationExceptions e) {
            String str = "Error in invoking the display method of the Component " + comp.getClass().getName();
            if (log.isDebugEnabled()) log.debug(str);
            throw e;
        } catch (Exception e) {
            String str = "Error in invoking the display method of the Component " + comp.getClass().getName();
            log.error(str, e);
            throw e;
        }

        return fk;
    }

}
