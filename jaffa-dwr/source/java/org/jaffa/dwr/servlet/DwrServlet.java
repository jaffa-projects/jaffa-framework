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
package org.jaffa.dwr.servlet;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.ServletConfig;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.directwebremoting.impl.ContainerUtil;
import org.directwebremoting.util.Messages;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.xml.sax.SAXException;

/**
 * This servet extends the DwrServlet implementation from DWR.
 * 
 * <p>This extended servlet will scan through the jar's to find the dwr.xml in jar
 * under META-INF and pass the input streams of dwr.xml's from jar's to DWR
 * implementation to load it in dwr container.<p>
 * 
 * Note: Set initial parameter skipDefaultConfig to true to skip the default
 * config load
 * Refer: http://directwebremoting.org/dwr/documentation/server/configuration/servlet/multiconfig.html
 * 
 */
@WebServlet(
		name = "dwr", 
		description = "DWR Servlet", 
		displayName = "DWR Servlet", 
		urlPatterns = "/dwr/*", 
		initParams = {
				//@WebInitParam(name = "debug", value = "true"),				
				//@WebInitParam(name = "skipDefaultConfig", value = "true"),
				@WebInitParam(name = "meta-config", value = "classpath*:META-INF/dwr.xml") 
		}
)
public class DwrServlet extends org.directwebremoting.servlet.DwrServlet {

	private static final Logger log = Logger.getLogger(DwrServlet.class);

	private Resource[] resources;

	@Override
	public void init(ServletConfig servletConfig) throws javax.servlet.ServletException {
		super.init(servletConfig);

		if (log.isDebugEnabled()) {
			log.debug("initializing DwrServlet Extension");
		}

		try {
			Enumeration en = servletConfig.getInitParameterNames();
			
			boolean skipDefaultConfig = false;
			
			while (en.hasMoreElements()) {
				
				String paramName = (String) en.nextElement();
				String paramValue = servletConfig.getInitParameter(paramName);

				// meta-config
				if (paramName.startsWith("meta-config") && paramValue != null && !"".equals(paramValue.trim())) {
					PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
					resources = resolver.getResources(paramValue);
				}

				// skipDefaultConfig
				if (paramName.startsWith("skipDefaultConfig") && paramValue != null && !"".equals(paramValue.trim())) {
					skipDefaultConfig = Boolean.parseBoolean(paramValue);
				}
				
			}
			
			/*
			 * Nothing will get loaded into dwr container, since no resources
			 * found in classpath and default config got skipped.
			 */
			if ((resources == null || resources.length == 0) && skipDefaultConfig) {
				throw new IOException(Messages.getString("DwrXmlConfigurator.MissingConfigFile",new String[] { "jar!META-INT/dwr.xml" }));
			}

			/*
			 * When there is no resources found under meta-inf and the
			 * skipDefaultConfig set to false then the validation handled for
			 * default config and the default config will get load into
			 * container by super class.
			 */
			if (resources == null && !skipDefaultConfig) {
				return;
			}
			org.jaffa.dwr.util.ContainerUtil.configureFromResource(getContainer(), servletConfig, resources);
			ContainerUtil.publishContainer(getContainer(), servletConfig);

		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new ServletException(e);
		}
	}
}
