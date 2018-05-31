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

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.session.SessionManager;
import org.jaffa.util.URLHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/** This servlet listener is invoked when servlet context is initialized.
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
@WebListener
public class InitApp implements ServletContextListener {
    
    private static Logger log = null;
    private final static String UTF_8 = "UTF-8";



    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // custom inits
        initializeLogField();
        initBaseDir(servletContextEvent);
        initGarbageCollectionOfIdleComponents();
        checkUserGridSettingsUrls();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        destroy();
    }

    /** This is invoked by the servlet container when the servlet is destroyed.
     * It clears up the settings related to the logging API.
     * It then invokes the destroy method of the super class.
     */
    public void destroy() {
        // destroy custom stuff
        destroyGarbageCollectionOfIdleComponents();
    }




    /** Initialize the base directory. This is the physical root of the web application.
     * This will be used inside the server to get a real path to relative files...
     */
    private void initBaseDir(ServletContextEvent servletContextEvent) {
        try {
            String baseFile = (String) Config.getProperty(Config.PROP_WEB_SERVER_ROOT_FILE, "/index.html");
            URL u = servletContextEvent.getServletContext().getResource( baseFile );
            String base = "";
            if(u == null) {
                log.warn("Base Web File Not Found '" + baseFile + "', Can't Set Web Root");
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
                log.warn("Bad URL for the " + (isDefault ? "default" : "") + " User Grid Settings folder- " + propValue);
            }
        }
    }

}