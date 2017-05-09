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
 * SecurityTag.java
 *
 * Created on April 10, 2002, 5:48 PM
 */

package org.jaffa.security;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.security.SecurityManager;

/** This class is used by the Tag Libraries that implement security
 * It is design to give access to the security manager such that a
 * context can be pushed onto the thread context stack and then popped off.
 *
 * This methods are only given package level access, and hence this class can
 * be used by the Custom Tags to get access to this functionality.
 *
 * If there is ever a case where a context gets pushed on the stack, and not
 * popped off, then there is a possibility that the Security Manager could allow
 * a re-used thread to have the wrong access privilidges.
 *
 * Always make sure that the 'pop' invocation is put inside a 'finally' block in which
 * the main 'try' section has the 'push' invocation.
 *
 * @author  paule
 * @version 1.0
 */
public class SecurityTag {

    /** Set up Logging for Log4J */
    private static Logger log = Logger.getLogger(SecurityTag.class);

    /** Set the context of the current thread with the current request,
     * if this request stream already is the current context it has no
     * effect on the context.
     *
     * This initally through and error if the same contexted is pushed on the stack
     * twice, this is no longer the case, as the PortletServlet may puch it on once,
     * and then call a JSP whose FORM tag will push it on again!. This behaviour should
     * not cause any problems, as long as the component that pushes, guarantees a single
     * pop at then end.
     * @param request The web server request to use for context
     * @throws SecurityException Thrown if there is any problem puching this context onto the threads security stack
     */
    public static void setThreadContext(HttpServletRequest request)
    throws SecurityException {
        if(log.isDebugEnabled())
            log.debug("Set Tag Security Context. Suppied Context=" + request);
        SecurityContext ctx = new SecurityContext(request);
        SecurityManager.bindToThread(ctx);
    }

    /** Remove the context from the current thread. As a check the current
     * context is passed back in to the routine, and this makes sure
     * we are popping of the expected context.
     *
     * In the event of a mismatch, all thread contexts are
     * removed and a SecurityException is thrown
     * @param request The web server request to use for context
     * @throws SecurityException Thrown if there is any problem puching this context onto the threads security stack
     */
    public static void unsetThreadContext(HttpServletRequest request)
    throws SecurityException {

        SecurityContext ctx = new SecurityContext(request);
        SecurityContext current = SecurityManager.getCurrentContext();
        if(log.isDebugEnabled())
            log.debug("Unset Thread Context: current-" + current + ", supplied=" + request + ", are they equal?:" + ctx.equals(current));

        if(!ctx.equals(current)) {
            log.error("Attempt To Pop Of Wrong Context On Security Stack. Attempting Prinipal = " + (ctx.getPrincipal()!=null?ctx.getPrincipal().getName():"unknown"));
            // Drop all security contexts
            for(;SecurityManager.getCurrentContext() != null; SecurityManager.unbindFromThread()) {
                // unbind context
                if(log.isDebugEnabled())
                    try {
                        log.debug("Paraniod Security: Popping Context for Principal - " + SecurityManager.getCurrentContext().getPrincipal().getName());
                    } catch (NullPointerException e) {}
            }
            throw new SecurityException();
        }

        // Everything is good, unbind the context.
        SecurityManager.unbindFromThread();
    }

    /** See if the user associted to the supplied request context, has acces to the component
     * @param request The web server request to use for context
     * @param componentName Name of component to check access for
     * @return true, if access to component is allowed, otherwise false
     */
    public static boolean hasComponentAccess(HttpServletRequest request, String componentName) {
        return SecurityManager.checkComponentAccess(componentName, new SecurityContext(request));
    }

    /** See if the user associted to the supplied request context, has acces to the business function
     * @param request The web server request to use for context
     * @param functionName Name of business function to check access for
     * @return true, if access to business function is allowed, otherwise false
     */
    public static boolean hasFunctionAccess(HttpServletRequest request, String functionName) {
        return SecurityManager.checkFunctionAccess(functionName, new SecurityContext(request));
    }
}
