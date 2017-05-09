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

package org.jaffa.session;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;


/** This servlet filter is used to set thread context. <p>
 * This should be invoked after the the UserSession has been created (by the PortletFilter)
 * if you are using UserSessions.
 * <p>
 * Example use in web.xml
 * <code><pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;Portlet Filter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;org.jaffa.presentation.portlet.PortletFilter&lt;/filter-class&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-name&gt;Session Filter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;*.jsp&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * &lt;filter-name&gt;Session Filter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;*.do&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * </pre></code>
 *
 * @author  PaulE
 * @version 1.0
 */
public class SessionFilter implements Filter {
    
    private static final Logger log = Logger.getLogger(SessionFilter.class);
    
    /** Called by the web container to indicate to a filter that it is being placed into service.
     * This does nothing.
     * @param filterConfig Config object from web.xml
     * @throws ServletException Should never be thrown by this filter
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("Filter Initailized");
    }
    
    
    /** Called by the web container to indicate to a filter that it is being taken out of service.
     * This does nothing.
     */
    public void destroy() {
    }
    
    
    /** The doFilter method of the Filter is called by the container each time a request/response pair is passed through the chain due to a client request for a resource at the end of the chain.
     * It will invoke the SecurityManager to execute the doFilterUnderSecurityContext() method under a SecurityContext.
     * @param request Request to process
     * @param response Response to return from filter
     * @param chain Chain of other filters to call
     * @throws IOException Standard Exception For Filter
     * @throws ServletException Standard Exception For Filter
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
        try {
            // Set the thread context
            if(request instanceof HttpServletRequest) {
                ContextManagerFactory.instance().setThreadContext((HttpServletRequest)request);
                log.debug("Set Thread Context for Request : " +  request);
            }
            
            // Continue normal processing of the request
            chain.doFilter(request, response);
        } finally {
            // unset the thread context
            ContextManagerFactory.instance().unsetThreadContext();
            log.debug("Unset Thread Context for Request : " +  request);
        }
    }
    
}