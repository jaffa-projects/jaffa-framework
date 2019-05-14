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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jaffa.config.ApplicationResourceLoader;
import org.jaffa.config.Config;
import org.jaffa.config.InitApp;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.LabelException;
import org.jaffa.util.ListProperties;
import org.jaffa.util.PropertyMessageResources;
import org.jaffa.util.PropertyMessageResourcesFactory;

public class LabelHelper {
    private static Logger log = Logger.getLogger(LabelHelper.class);

    public static String OVERRIDE = "override";

    public static void setLabel(String token, String value) throws FrameworkException{
        Map map = new HashMap();
        map.put(OVERRIDE, value);
        Map labels = new HashMap();
        labels.put(token, map);
        try{
            performSave(labels);
        } catch (FrameworkException e){
            //do nothing
            throw e;
        }
    }

    /** This will perform the following tasks.
     * - Add an entry for each label having an override value, to the ApplicationResources.override file.
     * - Remove all entries from the ApplicationResources.override file, for which the override value is blank
     * - Migrate all changes to the ApplicationResources.properties file by invoking InitApp.generateApplicationResources()
     * - Flush the struts properties cache by invoking the flushCache() method on the MessageResources, provided its an instance of 'org.jaffa.util.PropertyMessageResources'
     * @param labels the labels to process
     * @throws FrameworkException if any error occurs.
     */
    protected static void performSave(Map labels) throws FrameworkException {
        String applicationResourcesOverrideLocation = (String) Config.getProperty(Config.PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION, null);
        try {
            // load the ApplicationResources.override file
            Properties applicationResourcesOverrideProperties = loadPropertiesFromFile(applicationResourcesOverrideLocation, true);
            
            ApplicationResourceLoader appResourceLoader = ApplicationResourceLoader.getInstance();

            // Either update or remove a property
            for (Iterator itr = labels.keySet().iterator(); itr.hasNext(); ) {
                String label = (String) itr.next();
                Map map = (Map) labels.get(label);
                String override = (String) map.get(OVERRIDE);
                if (override != null){
                	//setting the override label into override file
                    applicationResourcesOverrideProperties.setProperty(label, override);
					//Applying the changes into ApplicationResources in memory
					appResourceLoader.getLocaleProperties(ApplicationResourceLoader.DEFAULT_PROP_LOCALE_KEY)
							.setProperty(label, override);
                }else{
                	//removing the override from file if there is any
                    applicationResourcesOverrideProperties.remove(label);
					//reverting/leaving the default value if the override removed.
					appResourceLoader.getLocaleProperties(ApplicationResourceLoader.DEFAULT_PROP_LOCALE_KEY)
							.setProperty(label, appResourceLoader.getApplicationResourcesDefault().getProperty(label));               
                }
            }

            // Sort the  ApplicationResources.override file
            if (applicationResourcesOverrideProperties instanceof ListProperties)
                ((ListProperties) applicationResourcesOverrideProperties).sort();

            // Now save the ApplicationResources.override file
            if (applicationResourcesOverrideLocation != null) {
                storePropertiesToFile(applicationResourcesOverrideProperties, applicationResourcesOverrideLocation);
            }

            // Migrate all changes to the ApplicationResources.properties file by invoking InitApp.generateApplicationResources()
            //InitApp.generateApplicationResources();

            ((PropertyMessageResources) PropertyMessageResourcesFactory.getDefaultMessageResources()).flushCache();
            if (log.isDebugEnabled())
                log.debug("Flushed the struts message cache");

        } catch (IOException e) {
             throw new LabelException(LabelException.WRITE_ERROR);
        }
    }


    /** Reads the input file and loads its properties into a Properties object.
     * @param fileName The file to be loaded.
     * @param maintainFileFormat If true, then the comments and order for each property will be maintained.
     * @throws IOException if any error occurs.
     * @return a Properties object loaded with the properties from the input file.
     */
    protected static Properties loadPropertiesFromFile(String fileName, boolean maintainFileFormat) throws IOException {
        InputStream is = null;
        Properties properties = null;
        if (maintainFileFormat)
            properties = new ListProperties();
        else
            properties = new Properties();

        if (fileName != null) {
            try {
                File file = new File(fileName);
                if (file.exists()) {
                    is = new BufferedInputStream(new FileInputStream(fileName));
                    properties.load(is);
                    if (log.isDebugEnabled())
                        log.debug("Loaded properties from " + fileName);
                } else {
                    if (log.isDebugEnabled())
                        log.debug("No properties loaded, since file does not exist " + fileName);
                }
            } finally {
                if (is != null)
                    is.close();
            }
        }
        else {
            log.info("No file name specified to load properties from.");
        }
        return properties;
    }

    /** Stores the input properties into the given file.
     * The order in which the properties are written to the file is not guaranteed. Comments in an existing file will be lost.
     * @param properties The properties to be stored.
     * @param fileName The file into which the properties will be stored.
     * @throws IOException if any error occurs.
     */
    protected static void storePropertiesToFile(Properties properties, String fileName) throws IOException {
        OutputStream os = null;
        try {
            // Create the directory for the input fileName, if it doesn't already exist.
            File f = new File(fileName);
            if (!f.exists()) {
                if (log.isDebugEnabled())
                    log.debug("File " + fileName + " does not exist. Check the existence of its directory");
                File dir = f.getParentFile();
                if (dir != null && !dir.exists()) {
                    if (log.isDebugEnabled())
                        log.debug("Directory " + dir.getPath() + " does not exist. Creating it..");
                    dir.mkdirs();
                }
            }
            os = new BufferedOutputStream(new FileOutputStream(f, false));
            properties.store(os, null);
            if (log.isDebugEnabled())
                log.debug("Saved properties to " + fileName);
        } finally {
            if (os != null)
                os.close();
        }
    }

}
