/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2014 JAFFA Development Group
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

package org.jaffa.config;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.*;
import java.util.*;
import java.net.URL;
import java.net.MalformedURLException;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.BasicConfigurator;
import org.jaffa.util.URLHelper;
import org.jaffa.util.PropertyMessageResourcesFactory;
import org.jaffa.util.PropertyMessageResources;
import org.jaffa.presentation.portlet.session.SessionManager;

/** This servlet is called by web.xml as part of the startup process.
 * It is used to initialize the Logging API and to also get a handle on
 * the base diretory that the web application is deployed in. This can be used as
 * a reference point for locating resources relative to the deployment of the web app.
 *
 * The following optional parameters can be passed to this servlet:
 *
 * framework.ApplicationResourcesLocation - The physical path of where the ApplicationResources.properties is.
 * Example Absolute location : "C:\tomcat\webapps\Tools\WEB-INF\classes\resources\ApplicationResources.properties"
 * Example Relative to classpath : "resources/ApplicationResources.properties"
 * NOTE: This value will have to be provided to the Struts ActionServlet as well in web.xml or struts-config.xml
 *
 * framework.ApplicationResourcesDefaultLocation - The physical path of where the ApplicationResources.default is.
 * Example Absolute location : "C:\tomcat\webapps\Tools\WEB-INF\classes\resources\ApplicationResources.default"
 * Example Relative to classpath : "resources/ApplicationResources.default"
 *
 * framework.ApplicationResourcesOverrideLocation - The physical path of where the ApplicationResources.override is.
 * Example "C:/ApplicationResources.override"
 * Example Relative to classpath : "resources/ApplicationResources.override"
 * NOTE: It is recommended that this file will outside the webapp, so that it is not overwritten during product upgrades.
 *
 * If the values are provided for the above parameters, this servlet will combine the ApplicationResources.default and ApplicationResources.override files to generate the ApplicationResources.properties file.
 * Additionally, the servlet will set the appropriate dynamic properties in the org.jaffa.config.Config class.
 * These parameters control the working of the 'Jaffa.Admin.LabelEditor' component.
 *
 */
public class InitApp extends HttpServlet {
    
    private static Logger log = null;
    private final static String UTF_8 = "UTF-8";
    
    /** This is invoked by the servlet container when the servlet is first loaded.
     * It first invokes the init method of the super class.
     * It then initialises the logging API and gets a handle on the webapp which invoked this servlet.
     * @param cfg the Servlet configuration passed by the container.
     * @throws ServletException if any error occurs.
     */
    public void init(ServletConfig cfg) throws ServletException {
        // default init
        super.init(cfg);
        
        // custom inits
        initLog4j();
        initBaseDir();
        initGarbageCollectionOfIdleComponents();
        checkUserGridSettingsUrls();
        initApplicationResourcesParameters();
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
    
    /** This method will check the values for the optional sevlet parameters - framework.ApplicationResourcesLocation, framework.ApplicationResourcesDefaultLocation and framework.ApplicationResourcesOverrideLocation.
     * It will set the appropriate dynamic properties in the org.jaffa.config.Config class.
     * It will then invoke the generateApplicationResources() method to combine the ApplicationResources.default and ApplicationResources.override files into the ApplicationResources.properties file.
     */
    private void initApplicationResourcesParameters() {
      String value = (String) Config.getProperty(Config.PROP_MESSAGE_RESOURCES_BUNDLE, null);
        if (value != null && !value.endsWith(".properties"))
            value += ".properties";
        value = determineLocation(value);
        Config.setProperty(Config.PROP_APPLICATION_RESOURCES_LOCATION, value);
        if (log.isInfoEnabled())
            log.info("Config.PROP_APPLICATION_RESOURCES_LOCATION set to: " + value);
        
        value = determineLocation(getServletConfig().getInitParameter(Config.PROP_APPLICATION_RESOURCES_DEFAULT_LOCATION));
        Config.setProperty(Config.PROP_APPLICATION_RESOURCES_DEFAULT_LOCATION, value);
        if (log.isInfoEnabled())
            log.info("Config.PROP_APPLICATION_RESOURCES_DEFAULT_LOCATION set to: " + value);
        
        value = determineLocation(getServletConfig().getInitParameter(Config.PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION));
        Config.setProperty(Config.PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION, value);
        if (log.isInfoEnabled())
            log.info("Config.PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION set to: " + value);
        
        try {
            generateApplicationResources();
        } catch (IOException e) {
            log.fatal("Error in generating ApplicationResources.properties from the Default and Override files", e);
        }
        //Reset PropertyMessageResources to make sure override labels are loaded properly
        if(PropertyMessageResourcesFactory.getDefaultMessageResources()!=null && PropertyMessageResourcesFactory.getDefaultMessageResources() instanceof PropertyMessageResources) {
            ((PropertyMessageResources) PropertyMessageResourcesFactory.getDefaultMessageResources()).flushCache();
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
    
    
    /** Combines the ApplicationResources.default and ApplicationResources.override files into the ApplicationResources.properties file.
     * The locations for the files are obtained from the dynamic properties set in the Config class.
     * If the locations are not set, then nothing will be done.
     * @throws IOException if any I/O error occurs.
     */
    public static void generateApplicationResources() throws IOException {

        String applicationResourcesLocation = (String) Config.getProperty(Config.PROP_APPLICATION_RESOURCES_LOCATION, null);
        String applicationResourcesDefaultLocation = (String) Config.getProperty(Config.PROP_APPLICATION_RESOURCES_DEFAULT_LOCATION, null);
        String applicationResourcesOverrideLocation = (String) Config.getProperty(Config.PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION, null);
        
        if (applicationResourcesLocation == null ||
        (applicationResourcesDefaultLocation == null && applicationResourcesOverrideLocation == null)) {
            if (log.isInfoEnabled())
                log.info("The locations have not been set for ApplicationResources.properties and its default, override file. No new ApplicationResources.properties file generated.");
        } else {
          OutputStreamWriter applicationResourcesOutputStream = null;
          BufferedReader applicationResourcesDefaultReader = null;
          BufferedReader applicationResourcesOverrideReader = null;
            try {
                // Load the 2 properties file, such that the Override file overrides the Default file
                final Properties properties = new Properties();

                if (applicationResourcesDefaultLocation != null) {
                  applicationResourcesDefaultReader = new BufferedReader(new InputStreamReader(new FileInputStream(applicationResourcesDefaultLocation),UTF_8));
                  properties.load(applicationResourcesDefaultReader);
                }
                if (applicationResourcesOverrideLocation != null) {
                    final File applicationResourcesOverrideFile = new File(applicationResourcesOverrideLocation);
                    if (applicationResourcesOverrideFile.exists()) {
                      applicationResourcesOverrideReader = new BufferedReader(new InputStreamReader(new FileInputStream(applicationResourcesOverrideFile),UTF_8));
                      properties.load(applicationResourcesOverrideReader);
                    }
                }
                // Generate the header String
                StringBuffer buf = new StringBuffer("Generated ");
                buf.append(applicationResourcesLocation);
                buf.append(" from ");
                if (applicationResourcesDefaultLocation != null)
                    buf.append(applicationResourcesDefaultLocation);
                if (applicationResourcesDefaultLocation != null && applicationResourcesOverrideLocation != null)
                    buf.append(" and ");
                if (applicationResourcesOverrideLocation != null)
                    buf.append(applicationResourcesOverrideLocation);
                String header = buf.toString();
                
                // Write out the loaded properties to the ApplicationResources.properties
                applicationResourcesOutputStream = new OutputStreamWriter(new FileOutputStream(applicationResourcesLocation, false), UTF_8);
                properties.store(applicationResourcesOutputStream, header);
                if (log!=null && log.isInfoEnabled())
                    log.info(header);
            } finally {
                if (applicationResourcesOutputStream != null)
                    applicationResourcesOutputStream.close();
                if (applicationResourcesDefaultReader != null)
                  applicationResourcesDefaultReader.close();
                if (applicationResourcesOverrideReader != null)
                  applicationResourcesOverrideReader.close();
            }
        }
    }
}