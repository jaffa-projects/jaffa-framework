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

package org.jaffa.applications.test.modules.security.components.test1.ui;

import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.security.SecurityManager;
import org.jaffa.presentation.portlet.FormKey;
import java.security.PrivilegedAction;
import org.jaffa.presentation.portlet.session.UserSession;
import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedActionException;
import java.security.AccessControlException;
import javax.servlet.http.HttpSession;
import org.jaffa.presentation.portlet.component.ComponentManager;
import org.jaffa.presentation.portlet.component.IComponent;
import org.jaffa.presentation.portlet.component.ComponentCreationRuntimeException;

/**
 * @author PaulE
 * @version 1.0
 */
public class Page1Action extends ActionBase {

    /** This button is protected by 'Function1' */
    public FormKey do_Button1_Clicked() {

        System.out.println("Trying to do something protected by Function1");

        SecurityManager.runFunction("Function1", new PrivilegedAction()
        {
            public Object run() {
                String msg = "Doing Something Protected By Function1";
                ((Page1Form)form).setMessage(msg);
                System.out.println(msg);
                return null;
            }
        });

        return new FormKey(Page1Form.NAME, component != null ? component.getComponentId() : null);
    }

    /** This button is protected by 'Function1', it throws an exception from the secured method */
    public FormKey do_Button1b_Clicked() {

        System.out.println("Trying to do something protected by Function1");
        try {
            SecurityManager.runFunction("Function1", new PrivilegedExceptionAction()
            {
                public Object run() {
                    throw new UnsupportedOperationException("Test Exception");
                }
            });
        } catch (AccessControlException e) {
               ((Page1Form)form).setMessage("You were not allowed access to Function1");
        } catch (PrivilegedActionException e) {
            ((Page1Form)form).setMessage("Caught Exception From Secured Function : " + e.getException().getClass().getName() + " - " + e.getException().getMessage());
        }
        return new FormKey(Page1Form.NAME, component != null ? component.getComponentId() : null);
    }

    /** This handles the fact that security access is denied. */
    public FormKey do_Button2_Clicked() {
        System.out.println("Trying to do something protected by Function2");

        try {

            SecurityManager.runFunction("Function2", new PrivilegedAction()
            {
                public Object run() {
                    String msg = "Doing Something Protected By Function2";
                    ((Page1Form)form).setMessage(msg);
                    System.out.println(msg);
                    return null;
                }
            });
        } catch (AccessControlException e) {
            String msg = "You Were Not Allowed Access To Function 2";
            ((Page1Form)form).setMessage(msg);
            System.out.println(msg);
        }
        return new FormKey(Page1Form.NAME, component != null ? component.getComponentId() : null);
    }


    /** This tries to run a component that the user may, or may not have access to  */
    public FormKey do_Button3_Clicked() {

        System.out.println("Trying to run component Test.Security.Test2");

        try {

            IComponent c = ComponentManager.run("Test.Security.Test2", UserSession.getUserSession(request) );
            String msg = "Running Component : Create Succeeded";
            ((Page1Form)form).setMessage(msg);
            System.out.println(msg);
        } catch (AccessControlException e) {
            String msg = "No Access To Component";
            ((Page1Form)form).setMessage(msg);
            System.out.println(msg);
        } catch (ComponentCreationRuntimeException e) {
            String msg = "Running Component : Create Failed";
            ((Page1Form)form).setMessage(msg);
            System.out.println(msg);
        }
        return new FormKey(Page1Form.NAME, component != null ? component.getComponentId() : null);
    }



    /** Log this user out. */
    public FormKey do_Logout_Clicked() {
        return null;
    }

}
