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
 * FakeRequest.java
 *
 * Created on April 8, 2002, 4:31 PM
 */

package org.jaffa.security;

/**
 *
 * @author  paule
 * @version
 */
public class FakeRequest implements javax.servlet.http.HttpServletRequest {

    String m_name = null;

    /** Creates new FakeRequest */
    public FakeRequest(String name) {
        m_name = name;
    }

    public java.lang.String getContextPath() {
        return null;
    }

	/** @deprecated */
    public java.lang.String getRealPath(java.lang.String str) {
        return null;
    }

    public java.lang.String getRequestedSessionId() {
        return null;
    }

    public java.io.BufferedReader getReader() throws java.io.IOException {
        return null;
    }

    public java.lang.String getAuthType() {
        return null;
    }

    public java.util.Enumeration getHeaders(java.lang.String str) {
        return null;
    }

    public long getDateHeader(java.lang.String str) {
        return 0;
    }

    public javax.servlet.ServletInputStream getInputStream() throws java.io.IOException {
        return null;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public java.security.Principal getUserPrincipal() {
        return new FakePrincipal(m_name);
    }

    public void setAttribute(java.lang.String str, java.lang.Object obj) {
    }

    public java.lang.String getPathInfo() {
        return null;
    }

    public java.lang.String getRemoteUser() {
        return null;
    }

    public java.lang.String getHeader(java.lang.String str) {
        return null;
    }

    public java.lang.String getCharacterEncoding() {
        return null;
    }

    public java.lang.String getServerName() {
        return null;
    }

    public java.util.Enumeration getLocales() {
        return null;
    }

    public javax.servlet.http.HttpSession getSession() {
        return null;
    }

    public void removeAttribute(java.lang.String str) {
        return;
    }

    public java.lang.String getContentType() {
        return null;
    }

    public java.lang.String getScheme() {
        return null;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public int getServerPort() {
        return 0;
    }

    public javax.servlet.http.Cookie[] getCookies() {
        return null;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

	/** @deprecated */
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    public java.lang.String getMethod() {
        return null;
    }

    public java.lang.String getParameter(java.lang.String str) {
        return null;
    }

    /** PAUL has Roles CLERK and MANAGER
     *  MAHESH has Role CLERK
     */
    public boolean isUserInRole(java.lang.String str) {
        if(m_name.equals("PAUL")) {
            return (str != null && (str.equals("CLERK") || str.equals("SUPERVISOR") || str.equals("MANAGER")));
        } else if (m_name.equals("MAHESH")) {
            return (str != null && ( str.equals("CLERK") || str.equals("SUPERVISOR") ) );
        } else
            return false;
    }

    public java.util.Enumeration getParameterNames() {
        return null;
    }


    public java.lang.String getServletPath() {
        return null;
    }

    public java.lang.String getRequestURI() {
        return null;
    }

    public java.lang.String getPathTranslated() {
        return null;
    }

    public java.lang.String[] getParameterValues(java.lang.String str) {
        return null;
    }

    public java.util.Locale getLocale() {
        return null;
    }

    public java.lang.String getProtocol() {
        return null;
    }

    public java.lang.String getRemoteAddr() {
        return null;
    }

    public int getContentLength() {
        return 0;
    }

    public javax.servlet.http.HttpSession getSession(boolean param) {
        return null;
    }

    public java.util.Enumeration getHeaderNames() {
        return null;
    }

    public javax.servlet.RequestDispatcher getRequestDispatcher(java.lang.String str) {
        return null;
    }

    public java.lang.String getRemoteHost() {
        return null;
    }

    public java.lang.String getQueryString() {
        return null;
    }

    public int getIntHeader(java.lang.String str) {
        return 0;
    }

    public java.lang.Object getAttribute(java.lang.String str) {
        return null;
    }

    public java.util.Enumeration getAttributeNames() {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public java.util.Map getParameterMap() {
        return null;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public void setCharacterEncoding(String str) throws java.io.UnsupportedEncodingException {
    }

    public int getLocalPort() {
		return 8080;
	}

	public String getLocalAddr() {
		return null;
	}

	public String getLocalName() {
		return null;
	}

    public int getRemotePort() {
		return 8080;
	}


}
