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

package org.jaffa.dwr;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.jaffa.dwr.servlet.DwrServlet;
import org.jaffa.util.OrderedPathMatchingResourcePatternResolver;
import org.junit.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import junit.framework.TestCase;

/**
 * 
 * Test for DwrServlet extension.
 *
 */
public class DwrServletTest extends TestCase {

	@Test
	public void testDwrLoad() throws Exception {

		ServletContext servletContext = mock(ServletContext.class);
		ServletConfig servletConfig = mock(ServletConfig.class);

		Map<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("meta-config", "classpath*:dummy*-dwr.xml");
		//initParameters.put("skipDefaultConfig", "true");

		when(servletContext.getResourceAsStream("/WEB-INF/dwr.xml")).thenReturn(getResourceAsStream("/dwr.xml"));
		when(servletContext.getAttribute("javax.servlet.context.tempdir")).thenReturn(new File("abc"));

		when(servletConfig.getServletContext()).thenReturn(servletContext);
		when(servletConfig.getServletContext().getServerInfo()).thenReturn("dwr");
		when(servletConfig.getServletName()).thenReturn("dwr-invoker");
		when(servletConfig.getInitParameterNames()).thenReturn(Collections.enumeration(initParameters.keySet()));
		when(servletConfig.getInitParameter("meta-config")).thenReturn("classpath*:dummy*-dwr.xml");
		//when(servletConfig.getInitParameter("skipDefaultConfig")).thenReturn("true");

		
		DwrServlet dwrServlet = new DwrServlet();
		dwrServlet.init(servletConfig);
		
		//There are three DWR files in the classpath. Checking all three loaded successfully.
		verify(servletContext).setAttribute(eq("org.directwebremoting.ContainerList"), any());

		//String result = ContainerUtil.getAllPublishedContainers(servletContext).size() > 0 ? "Dwr Xml Loaded" : "Dwr Xml Not Loaded";

		//assertEquals("Dwr Xml Loaded", result);
	}
	
	/** 
	 * Overriden to reset the default path from "/WEB-INF/dwr.xml" to
	 * "/dwr.xml".
	 */
	public InputStream getResourceAsStream(String path) {
		InputStream inputStream = null;
		try {
			PathMatchingResourcePatternResolver resolver = OrderedPathMatchingResourcePatternResolver.getInstance();
			inputStream = resolver.getResource(path).getInputStream();
			System.out.println("path:"+path);
			System.out.println("inputStream:"+inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return inputStream;
	}
}
