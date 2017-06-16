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
package org.jaffa.config;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jaffa.presentation.portlet.session.SessionManager;
import org.jaffa.util.URLHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


/** This servlet is called during the container startup.
 * It is used to initialize the Logging API and to also get a handle on
 * the base diretory that the web application is deployed in. This can be used as
 * a reference point for locating resources relative to the deployment of the web app.
 * this servlet will combine the ApplicationResources.default and ApplicationResources.override files to generate the ApplicationResources.properties in memory.
 * Additionally, the servlet will set the appropriate dynamic properties in the org.jaffa.config.Config class.
 * These parameters control the working of the 'Jaffa.Admin.LabelEditor' component.
 *
 */
@WebServlet(
		name = "appconfig",
		description = "Application Configuration Servlet",
		displayName = "AppConfigServlet",
		loadOnStartup = 1
	//@todo include location of ApplicationResources.override from data directory (userpref) as a initParam
)
public class AppConfigServlet extends HttpServlet {

    private static Logger log = null;


	public void init(ServletConfig servletConfig) throws ServletException {
		// default init
		super.init(servletConfig);

		// custom inits
		//initLog4j();
		//initBaseDir();
		//initGarbageCollectionOfIdleComponents();
		//checkUserGridSettingsUrls();

		initApplicationResourcesParameters();
		initApplicationRulesParameters();
		initComponents();
	}


	/**
	 * Initialize applicationresources
	 */
	private void initApplicationResourcesParameters() {
		ApplicationResourceLoader.getInstance();
	}
	
	/**
	 * Initialize applicationrules
	 */
	private void initApplicationRulesParameters() {
		ApplicationRulesLoader.getInstance();
	}	
	
	/**
	 * Initialize ComponentDefinition
	 */
	private void initComponents() {
		ComponentLoader.getInstance();
	}

	/** This is invoked by the servlet container when the servlet is destroyed.
	 * It clears up the settings related to the logging API.
	 * It then invokes the destroy method of the super class.
	 */
	public void destroy() {
		// destroy custom stuff
		destroyGarbageCollectionOfIdleComponents();
		destroyLog4j();

		// destroy the default
		super.destroy();
	}

	/** Initialize log4j using the file specified in the 'framework.Log4JConfig' property in the config.properties file.
	 * This will be set to either 'none', 'default' or a classpath-relative file name. If there is no configuration setting
	 * 'default' wil be assumed.
	 *
	 * For more information look at the documentation in 'config.properties'
	 */
	private void initLog4j() {
		//Read setting from configuration file
		String fileName = (String) Config.getProperty(Config.PROP_LOG4J_CONFIG, "default");

		if ( fileName.equalsIgnoreCase("none") ) {
			// do nothing.. Assume that log4j would have been initialized by some other container
			initializeLogField();
			log.info("Skipped log4j configuration. Should be done by Web/J2EE Server first!");
		} else if ( fileName.equalsIgnoreCase("default") ) {
			defaultLog4j();
		} else {
			try {
				URL u = URLHelper.newExtendedURL(fileName);
				DOMConfigurator.configureAndWatch(u.getPath());
				initializeLogField();
				if ( log.isInfoEnabled() )
					log.info("Configured log4j using the configuration file (relative to classpath): " + fileName );
			} catch (Exception e) {
				System.err.println( "Error in initializing Log4j using the configFile (relative to classpath): " + fileName );
				e.printStackTrace();
				defaultLog4j();
			}
		}
	}

	private void defaultLog4j() {
		BasicConfigurator.configure();
		initializeLogField();
		if ( log.isInfoEnabled() )
			log.info("Configured log4j using the Basic Configurator" );
	}

	private void destroyLog4j() {
		LogManager.shutdown();
	}


	/** Initialize the base directory. This is the physical root of the web application.
	 * This will be used inside the server to get a real path to relative files...
	 */
	private void initBaseDir() {
		try {
			String baseFile = (String) Config.getProperty(Config.PROP_WEB_SERVER_ROOT_FILE, "/index.html");
			URL u = getServletContext().getResource( baseFile );
			String base = "";
			if(u == null) {
				log.fatal("Base Web File Not Found '" + baseFile + "', Can't Set Web Root");
				return;
			} else {
				// Try to split the file with the OS native seporator
				int pos = u.toExternalForm().lastIndexOf(File.separator);
				// If this didn't suceed, try the URL sperator (/)
				if(pos == -1 && File.separatorChar != '/')
					pos = u.toExternalForm().lastIndexOf('/');
				if(log.isDebugEnabled())
					log.debug("URL for locating the base is " + u.toExternalForm() + ", drop file after pos " + pos);
				if(pos >= 1)
					base = u.toExternalForm().substring(0,pos);
			}
			log.info("Web App Base Directory : " + base);
			Config.setProperty(Config.PROP_WEB_SERVER_ROOT, base);
		} catch (MalformedURLException e) {
			log.fatal("Failed to get root for web server files", e);
		}
	}

	private void initializeLogField() {
		if (log == null)
			log = Logger.getLogger(InitApp.class);
	}

	private void initGarbageCollectionOfIdleComponents() {
		if (log.isInfoEnabled())
			log.info("Starting a Thread to Garbage Collect idle components");
		SessionManager.startGarbageCollectionOfIdleComponents();
	}

	private void destroyGarbageCollectionOfIdleComponents() {
		if (log.isInfoEnabled())
			log.info("Stopping the Thread to Garbage Collect idle components");
		SessionManager.stopGarbageCollectionOfIdleComponents();
	}

	/** This validate the framework properties - 'framework.widgets.usergrid.user.url' and 'framework.widgets.usergrid.default.url'.
	 * Warnings will be logged, in case invalid values are provided.
	 */
	private void checkUserGridSettingsUrls() {
		// check the URL for the default grid settings
		checkUrlProperty(Config.PROP_DEFAULT_GRID_SETTINGS_URI, true);

		// check the URL for the user grid settings
		checkUrlProperty(Config.PROP_USER_GRID_SETTINGS_URI, false);

	}

	private void checkUrlProperty(String prop, boolean isDefault) {
		String propValue = (String) Config.getProperty(prop);
		if (propValue != null && propValue.length() > 0) {
			try {
				final URL settingsUrl = URLHelper.newExtendedURL(propValue);
				final File f = new File(settingsUrl.getFile());
				if (!f.exists())
					log.warn("The " + (isDefault ? "default" : "") + " User Grid Settings folder doesn't exist- " + f);
				else if (log.isInfoEnabled())
					log.info("The " + (isDefault ? "default" : "") + " User Grid Settings folder- " + f);
			} catch (MalformedURLException e) {
				log.warn("Bad URL for the " + (isDefault ? "default" : "") + " User Grid Settings folder- " + propValue, e);
			}
		}
	}

	/** This will check the input in the classpath. If not found, it'll then assume to be in the File system.
	 * It will then return the physical location of the file in the filesystem.
	 */
	private static String determineLocation(String input) {
		String output = null;
		if (input != null && input.trim().length() > 0) {
			try {
				final URL url = URLHelper.newExtendedURL(input);
				output = url.getPath();
			} catch (MalformedURLException e) {
				final File f = new File(input);
				output = f.getPath();
			}
		}
		return output;
	}
}
