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

package org.jaffa.modules.user.services;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The mock request that is used by the {@link UserContextWrapper} to set user security on a thread.
 */
public class MockHttpServletRequest implements HttpServletRequest {

    Principal p = null;
    List<String> roles = null;
    Hashtable attr = new Hashtable();
    HttpSession sess = null;

    MockHttpServletRequest(String user, String[] roles) {
        p = new MockPrincipal(user);
        this.roles = Arrays.asList(roles);
    }

    public List getRoles() {
        return roles;
    }

    public HttpSession getSession(boolean b) {
        if (b == false && sess == null) {
            return null;
        }
        return getSession();
    }

    public boolean isUserInRole(String string) {
        return roles.contains(string);
    }

    public Locale getLocale() {
        return Locale.getDefault();
    }

    public HttpSession getSession() {
        if (sess == null) {
            sess = new MockHttpSession();
        }
        return sess;
    }

    public Principal getUserPrincipal() {
        return p;
    }

    public Enumeration getAttributeNames() {
        return attr.keys();
    }

    public void removeAttribute(String string) {
        attr.remove(string);
    }

    public Object getAttribute(String string) {
        return attr.get(string);
    }

    public void setAttribute(String string, Object object) {
        attr.put(string, object);
    }

    public String[] getParameterValues(String string) {
        return null;
    }

    public String getParameter(String string) {
        return null;
    }

    public int getIntHeader(String string) {
        return 0;
    }

    public long getDateHeader(String string) {
        return 0;
    }

    public String getHeader(String string) {
        return null;
    }

    public Enumeration getHeaders(String string) {
        return null;
    }

    /**
     * @deprecated
     */
    public String getRealPath(String string) {
        return null;
    }

    public RequestDispatcher getRequestDispatcher(String string) {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public String getPathInfo() {
        return null;
    }

    public Enumeration getParameterNames() {
        return null;
    }

    public Map getParameterMap() {
        return null;
    }

    public String getMethod() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }

    public String getLocalName() {
        return null;
    }

    public String getLocalAddr() {
        return null;
    }

    public String getAuthType() {
        return null;
    }

    public String getCharacterEncoding() {
        return null;
    }

    /**
     * ----------- NO IMPLEMENTATION -----------------------*
     */
    public void setCharacterEncoding(String string) throws UnsupportedEncodingException {
    }

    public int getContentLength() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public String getContextPath() {
        return null;
    }

    public Cookie[] getCookies() {
        return null;
    }

    public Enumeration getHeaderNames() {
        return null;
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    public String getPathTranslated() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public String getQueryString() {
        return null;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        String remoteHost = null;
        try {
            remoteHost = InetAddress.getLocalHost()!=null ? InetAddress.getLocalHost().getHostAddress() : null;
        } catch (UnknownHostException e) {
            //do nothing
        }
        return remoteHost;
    }

    public int getRemotePort() {
        return 0;
    }

    public String getRemoteUser() {
        return null;
    }

    public String getRequestURI() {
        return null;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public String getRequestedSessionId() {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public String getServletPath() {
        return null;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    /**
     * @deprecated
     */
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }
}

class MockHttpSession implements HttpSession {

    Hashtable attr = new Hashtable();

    public Enumeration getAttributeNames() {
        return attr.keys();
    }

    public void removeAttribute(String string) {
        Object originalValue = attr.remove(string);
        if (originalValue != null && originalValue instanceof HttpSessionBindingListener) {
            ((HttpSessionBindingListener) originalValue).valueUnbound(new HttpSessionBindingEvent(this, string));
        }
    }

    public Object getAttribute(String string) {
        return attr.get(string);
    }

    public void setAttribute(String string, Object object) {
        Object originalValue = attr.put(string, object);
        if (originalValue != null && originalValue instanceof HttpSessionBindingListener) {
            ((HttpSessionBindingListener) originalValue).valueUnbound(new HttpSessionBindingEvent(this, string));
        }
        if (object != null && object instanceof HttpSessionBindingListener) {
            ((HttpSessionBindingListener) object).valueBound(new HttpSessionBindingEvent(this, string));
        }
    }

    public void invalidate() {
        Enumeration attributeNames = getAttributeNames();
        if (attributeNames != null) {
            while (attributeNames.hasMoreElements()) {
                removeAttribute((String) attributeNames.nextElement());
            }
        }
    }

    /**----------- NO IMPLEMENTATION -----------------------**/

    /**
     * @deprecated
     */
    public void removeValue(String string) {
    }

    /**
     * @deprecated
     */
    public Object getValue(String string) {
        return null;
    }

    /**
     * @deprecated
     */
    public void putValue(String string, Object object) {
    }

    public long getCreationTime() {
        return new java.util.Date().getTime();
    }

    public String getId() {
        return null;
    }

    public long getLastAccessedTime() {
        return new java.util.Date().getTime();
    }

    public int getMaxInactiveInterval() {
        return 0;
    }

    public void setMaxInactiveInterval(int i) {
    }

    public ServletContext getServletContext() {
        return null;
    }

    /**
     * @deprecated
     */
    public HttpSessionContext getSessionContext() {
        return null;
    }

    /**
     * @deprecated
     */
    public String[] getValueNames() {
        return null;
    }

    public boolean isNew() {
        return false;
    }
}

class MockPrincipal implements Principal {

    private String name;

    MockPrincipal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
