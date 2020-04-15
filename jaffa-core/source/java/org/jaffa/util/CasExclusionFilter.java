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
package org.jaffa.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Top-level Central Authentication Service (CAS) web.xml filter that directs the HTTP filter chain to skip
 * all other CAS filters if a certain endpoint is reached. This allows portions of a CAS-protected webapp to
 * bypass CAS authentication but retain security through Tomcat Basic Authentication
 * @author Matthew Wayles
 * @version 1.0
 */
public class CasExclusionFilter implements Filter {

    /**
     * Initialize the filter
     * @param request The HTTP request for endpoint access
     * @throws ServletException General exception thrown by a servlet when it encounters difficulty
     */
    public void init(FilterConfig request) throws ServletException {
    }

    /**
     * Implementation of filter operations when it is triggered. This filter skips all subsequent filters unless they
     * are configured with the FORWARD dispatcher
     * @param request The HTTP request for endpoint access
     * @param response    The HTTP response supplied by the server
     * @param chain   The linear chain of filters
     * @throws IOException    When a filter, endpoint, or server component cannot be accesses
     * @throws ServletException   General exception thrown by a servlet when it encounters difficulty
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String path = httpServletRequest.getServletPath();
        String info = StringUtils.defaultString(httpServletRequest.getPathInfo());

        //security check by URL encoder if the string from the request is harmful
        //then use the decoded path info
        String encoded = URLEncoder.encode(path + info, StandardCharsets.UTF_8.toString());
        RequestDispatcher dispatcher = request.getRequestDispatcher(URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString()));
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    /**
     * Remove the filter from the filter chain
     */
    public void destroy() {
    }

}
