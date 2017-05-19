/*
 * ====================================================================
 * JAFFA - Java Application Framework For Aerospace
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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *      Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *      this Software without prior written permission. For written permission,
 *      please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *      appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
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

package org.jaffa.dwr.mock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

/**
 * 
 * Mock ServletContext for unit test.
 *
 */
public class MockServletContext implements ServletContext {

	private final String resourceBasePath;
	private String servletContextName = "MockServletContext";
	private final Map<String, String> initParameters = new HashMap<String, String>();
	private final Map<String, Object> attributes = new HashMap<String, Object>();

	/**
	 * 
	 * @param resourceBasePath
	 */
	public MockServletContext(String resourceBasePath) {
		this.resourceBasePath = (resourceBasePath != null ? resourceBasePath : "");

		String tempDir = System.getProperty("java.io.tmpdir");
		if (tempDir != null) {
			attributes.put("javax.servlet.context.tempdir", new File(tempDir));
		}
	}

	@Override
	public Dynamic addFilter(String arg0, String arg1) {
		throw new UnsupportedOperationException("addFilter");
	}

	@Override
	public Dynamic addFilter(String arg0, Filter arg1) {
		throw new UnsupportedOperationException("addFilter");
	}

	@Override
	public Dynamic addFilter(String arg0, Class<? extends Filter> arg1) {
		throw new UnsupportedOperationException("addFilter");
	}

	@Override
	public void addListener(String arg0) {
		throw new UnsupportedOperationException("addListener");

	}

	@Override
	public <T extends EventListener> void addListener(T arg0) {
		throw new UnsupportedOperationException("addListener");

	}

	@Override
	public void addListener(Class<? extends EventListener> arg0) {
		throw new UnsupportedOperationException("addListener");
	}

	@Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, String arg1) {
		throw new UnsupportedOperationException("addServlet");
	}

	@Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Servlet arg1) {
		throw new UnsupportedOperationException("addServlet");
	}

	@Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Class<? extends Servlet> arg1) {
		throw new UnsupportedOperationException("addServlet");
	}

	@Override
	public <T extends Filter> T createFilter(Class<T> arg0) throws ServletException {
		throw new UnsupportedOperationException("createFilter");
	}

	@Override
	public <T extends EventListener> T createListener(Class<T> arg0) throws ServletException {
		throw new UnsupportedOperationException("createListener");
	}

	@Override
	public <T extends Servlet> T createServlet(Class<T> arg0) throws ServletException {
		throw new UnsupportedOperationException("createServlet");
	}

	@Override
	public void declareRoles(String... arg0) {
		throw new UnsupportedOperationException("declareRoles");
	}

	@Override
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return Collections.enumeration(attributes.keySet());
	}

	@Override
	public ClassLoader getClassLoader() {
		throw new UnsupportedOperationException("getClassLoader");
	}

	@Override
	public ServletContext getContext(String arg0) {
		throw new UnsupportedOperationException("getContext");
	}

	@Override
	public String getContextPath() {
		throw new UnsupportedOperationException("getContextPath");
	}

	@Override
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		throw new UnsupportedOperationException("getDefaultSessionTrackingModes");
	}

	@Override
	public int getEffectiveMajorVersion() {
		throw new UnsupportedOperationException("getEffectiveMajorVersion");
	}

	@Override
	public int getEffectiveMinorVersion() {
		throw new UnsupportedOperationException("getEffectiveMinorVersion");
	}

	@Override
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		throw new UnsupportedOperationException("getEffectiveSessionTrackingModes");
	}

	@Override
	public FilterRegistration getFilterRegistration(String arg0) {
		throw new UnsupportedOperationException("getFilterRegistration");
	}

	@Override
	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		throw new UnsupportedOperationException("getFilterRegistrations");
	}

	@Override
	public String getInitParameter(String name) {
		return initParameters.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(initParameters.keySet());
	}

	@Override
	public JspConfigDescriptor getJspConfigDescriptor() {
		throw new UnsupportedOperationException("getJspConfigDescriptor");
	}

	@Override
	public int getMajorVersion() {
		return 1;
	}

	@Override
	public String getMimeType(String arg0) {
		throw new UnsupportedOperationException("getMimeType");
	}

	@Override
	public int getMinorVersion() {
		return 0;
	}

	@Override
	public RequestDispatcher getNamedDispatcher(String arg0) {
		throw new UnsupportedOperationException("getNamedDispatcher");
	}

	@Override
	public String getRealPath(String arg0) {
		throw new UnsupportedOperationException("getRealPath");
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {
		throw new UnsupportedOperationException("getRequestDispatcher");
	}

	@Override
	public URL getResource(String arg0) throws MalformedURLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		try {
			return new FileInputStream(resourceBasePath + path);
		} catch (FileNotFoundException ex) {
			return null;
		}
	}

	@Override
	public Set<String> getResourcePaths(String arg0) {
		return null;
	}

	@Override
	public String getServerInfo() {
		return "MockServletContext";
	}

	@Override
	public Servlet getServlet(String arg0) throws ServletException {
		throw new UnsupportedOperationException("getServlet");
	}

	@Override
	public String getServletContextName() {
		return servletContextName;
	}

	@Override
	public Enumeration<String> getServletNames() {
		throw new UnsupportedOperationException("getServletNames");
	}

	@Override
	public ServletRegistration getServletRegistration(String arg0) {
		throw new UnsupportedOperationException("getServletRegistration");
	}

	@Override
	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		throw new UnsupportedOperationException("getServletRegistrations");
	}

	@Override
	public Enumeration<Servlet> getServlets() {
		throw new UnsupportedOperationException("getServlets");
	}

	@Override
	public SessionCookieConfig getSessionCookieConfig() {
		throw new UnsupportedOperationException("getSessionCookieConfig");
	}

	@Override
	public String getVirtualServerName() {
		throw new UnsupportedOperationException("getVirtualServerName");
	}

	@Override
	public void log(String arg0) {
	}

	@Override
	public void log(Exception arg0, String arg1) {
	}

	@Override
	public void log(String arg0, Throwable arg1) {
	}

	@Override
	public void removeAttribute(String name) {
		this.attributes.remove(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		this.attributes.put(name, value);
	}

	@Override
	public boolean setInitParameter(String name, String value) {
		initParameters.put(name, value);
		return true;
	}

	@Override
	public void setSessionTrackingModes(Set<SessionTrackingMode> arg0) {
		throw new UnsupportedOperationException("setSessionTrackingModes");
	}

}
