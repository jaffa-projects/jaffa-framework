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
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * This servlet filter is used to invalidate HttpSession, thus ensuring that HttpSessions
 * are not kept open un-necessarily after WebService invocations.
 * <p>
 * This filter should only be used for WebService invocations.
 * If used, this filter should be the first element in a filter-chain.
 * <p>
 * A servlet container, for example JBoss, shows the number of 'activeSessions' in
 * the mbean identified by 'path=/{WebServiceApp},type=Manager'.
 * <p>
 * Example use in web.xml
 * <code><pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;WebService Filter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;org.jaffa.session.WebServiceFilter&lt;/filter-class&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-name&gt;WebService Filter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * </pre></code>
 *
 * @author  GautamJ
 * @version 1.0
 */
public class WebServiceSessionFilter implements Filter {

    private static final Logger log = Logger.getLogger(WebServiceSessionFilter.class);

    /**
     * Called by the web container to indicate to a filter that it is being placed into service.
     * This does nothing.
     * @param filterConfig Config object from web.xml
     * @throws ServletException Should never be thrown by this filter.
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        if (log.isDebugEnabled())
            log.debug("Filter Initailized");
    }

    /**
     * Called by the web container to indicate to a filter that it is being taken out of service.
     * This does nothing.
     */
    public void destroy() {
        if (log.isDebugEnabled())
            log.debug("Filter Destroyed");
    }

    /**
     * The doFilter method of the Filter is called by the container each time a request/response pair is passed through the chain due to a client request for a resource at the end of the chain.
     * On the way in, this method will simply delegate to the next element in the filter-chain.
     * However on the way out, this method will invalidate the HttpSession; thus ensuring that HttpSessions
     * are not kept open un-necessarily after WebService invocations.
     * <p>
     * It is important that this filter should be the first filter in a filter-chain.
     * @param request Request to process
     * @param response Response to return from filter
     * @param chain Chain of other filters to call
     * @throws IOException Standard Exception For Filter
     * @throws ServletException Standard Exception For Filter
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // Continue normal processing of the request
            chain.doFilter(request, response);
        } finally {
            // Invalidate the associated HttpSession, if any
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            if (session != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Invalidating Session");
                }
                session.invalidate();
            }
        }
    }
}
