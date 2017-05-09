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
package org.jaffa.persistence.engines.jdbcengine.configservice;

import org.apache.log4j.Logger;
import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import org.jaffa.config.Config;
import org.jaffa.persistence.engines.jdbcengine.configservice.exceptions.InitFileNotFoundRuntimeException;
import org.jaffa.persistence.engines.jdbcengine.configservice.exceptions.ConfigurationServiceRuntimeException;
import org.jaffa.util.URLHelper;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Init;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.Database;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.PreloadClass;
import org.jaffa.persistence.engines.jdbcengine.configservice.initdomain.DirLoc;
import org.jaffa.util.DefaultEntityResolver;
import org.jaffa.util.DefaultErrorHandler;
import org.jaffa.util.JAXBHelper;
import org.jaffa.util.XmlHelper;

/** This class implements the Singleton pattern. Use the getInstance() method to get an instance of this class.
 * The Configuration Service reads the init.xml file. It then performs the initializations.
 * This class caches the ClassMetaData objects.
 * It also maintains all the Database objects.
 */
public class ConfigurationService {

    private static final Logger log = Logger.getLogger(ConfigurationService.class);
    private static final String PARSER_NAME = "org.apache.xerces.parsers.SAXParser";
    private static final String MAPPING_FILE_PREFIX = ".xml";
    private static final String SCHEMA = "org/jaffa/persistence/engines/jdbcengine/configservice/initdomain/jdbc-engine-init_1_0.xsd";
    private static ConfigurationService m_singleton = null;
    private static Map m_metaCache = new WeakHashMap();
    private static Map m_databases = null;
    private List m_locations = null;

    /** Creates an instance of ConfigurationService, if not already instantiated.
     * This gets the location of the init.xml (relative to the classpath) from the framework.properties file.
     * @return An instance of the ConfigurationService.
     */
    public static ConfigurationService getInstance() {
        if (m_singleton == null) {
            createConfigurationServiceInstance();
        }
        return m_singleton;
    }

    private static synchronized void createConfigurationServiceInstance() {
        if (m_singleton == null) {
            String initfile = (String) Config.getProperty(Config.PROP_JDBC_ENGINE_INIT);

            if (log.isDebugEnabled()) {
                log.debug("Creating an instance of the ConfigurationService using the initialization file: " + initfile);
            }
            m_singleton = new ConfigurationService(initfile);
        }
    }

    /** This reads in the init file and parses it using JAXB.
     * It then maintains a Map of Database objects, a List of locations for finding mapping files.
     * It then preloads the mapping files, as defined in the init file.
     * It throws the runtime exception InitFileNotFoundRuntimeException, if the init file is not found.
     * It throws the runtime exception ConfigurationServiceRuntimeException, if the init file or any of the mapping files give parse errors.
     * It throws the runtime exception ClassMetaDataValidationRuntimeException, if the mapping file and the corresponding class have mismatches.
     */
    private ConfigurationService(String initFile) {
        InputStream stream = null;
        try {
            URL initUrl = URLHelper.newExtendedURL(initFile);
            stream = initUrl.openStream();

            // create a JAXBContext capable of handling classes generated into the package
            JAXBContext jc = JAXBContext.newInstance("org.jaffa.persistence.engines.jdbcengine.configservice.initdomain");

            // create an Unmarshaller
            Unmarshaller u = jc.createUnmarshaller();

            // enable validation
            u.setSchema(JAXBHelper.createSchema(SCHEMA));

            // unmarshal a document into a tree of Java content objects composed of classes from the package.
            Init init = (Init) u.unmarshal(XmlHelper.stripDoctypeDeclaration(stream));
            m_databases = new HashMap();
            for (Iterator i = init.getDatabase().iterator(); i.hasNext();) {
                Database database = (Database) i.next();
                m_databases.put(database.getName(), database);
            }

            m_locations = init.getConfLocation().getDirLoc();

            if (init.getPreload() != null) {
                preLoad(init.getPreload().getPreloadClass());
            }
        } catch (MalformedURLException e) {
            String str = "Initialisation file '" + initFile + "' not found in the classpath";
            log.error(str);
            throw new InitFileNotFoundRuntimeException(str);
        } catch (Exception e) {
            String str = "Error while parsing the init file " + initFile;
            log.error(str, e);
            throw new ConfigurationServiceRuntimeException(str, e);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
            // do nothing
            }
        }
    }

    /** Returns a Map of Database objects, keyed by database name, as defined in the init file.
     * @return a Map of Database objects, keyed by database name, as defined in the init file.
     */
    public Map getDatabases() {
        return m_databases;
    }

    /** Returns a Database object for the database name, as defined in the init file.
     * @param name The database name.
     * @return a Database object. A null will be returned, in case the name was not defined in the init file.
     */
    public Database getDatabase(String name) {
        return (Database) m_databases.get(name);
    }

    /** This tries to locate the mapping file for the input classname through the list of locations as defined in the init file.
     * It will create a ClassMetaData object & cache it.
     * It throws the runtime exception ConfigurationServiceRuntimeException, if the the mapping file could not be located.
     * It throws the runtime exception ClassMetaDataValidationRuntimeException, if the mapping file and the corresponding class have mismatches.
     * @param classname The class for which the mapping file is going to be parsed.
     * @return a ClassMetaData object.
     */
    public ClassMetaData getMetaData(String classname) {
        ClassMetaData classMetaData = null;
        if (m_metaCache.containsKey(classname)) {
            classMetaData = (ClassMetaData) m_metaCache.get(classname);
        } else {
            classMetaData = parseNew(classname);
        }
        return classMetaData;
    }

    private void preLoad(List preLoadList) {
        if (preLoadList != null) {
            for (Iterator itr = preLoadList.iterator(); itr.hasNext();) {
                PreloadClass preloadClass = (PreloadClass) itr.next();
                parseNew(preloadClass.getName());
            }
        }
    }

    private ClassMetaData parseNew(String classname) {
        if (log.isDebugEnabled()) {
            log.debug("Locating the mapping file for the Persistent class " + classname);
        }

        String shortname = classname.substring(classname.lastIndexOf('.') + 1);
        for (Iterator itr = m_locations.iterator(); itr.hasNext();) {
            DirLoc dirLoc = (DirLoc) itr.next();
            String packageName = dirLoc.getDir();
            StringBuffer urlNameBuf = new StringBuffer(packageName);
            if (!packageName.endsWith("/") && !packageName.endsWith("\\")) {
                urlNameBuf.append('/');
            }
            urlNameBuf.append(shortname).append(MAPPING_FILE_PREFIX);
            String urlName = urlNameBuf.toString();
            if (log.isDebugEnabled()) {
                log.debug("Looking for the mapping file " + urlName);
            }
            URL url = null;
            try {
                url = URLHelper.newExtendedURL(urlName);
            } catch (MalformedURLException e) {
                if (log.isDebugEnabled()) {
                    log.debug("Could not find the mapping file " + urlName + ". " + e.getMessage());
                }
            }
            if (url != null) {
                DefaultHandler handler = new MappingParser();
                InputStream stream = null;
                try {
                    XMLReader reader = XMLReaderFactory.createXMLReader(PARSER_NAME);
                    reader.setContentHandler(handler);
                    reader.setEntityResolver(new DefaultEntityResolver());
                    reader.setErrorHandler(new DefaultErrorHandler());
                    stream = url.openStream();
                    reader.parse(new InputSource(stream));
                } catch (Exception e) {
                    if (log.isDebugEnabled()) {
                        log.debug("Error in parsing the mapping file " + urlName + ". Will try and look at another location", e);
                    }
                    // try another location !!!
                    continue;
                } finally {
                    try {
                        if (stream != null) {
                            stream.close();
                        }
                    } catch (IOException e) {
                    // do nothing
                    }
                }
                ClassMetaData classMetaData = ((MappingParser) handler).getMetaData();
                if (classMetaData != null && classMetaData.getClassName().equals(classname)) {
                    if (log.isDebugEnabled()) {
                        log.debug("Validating the ClassMetaData object for the mapping file " + urlName);
                    }
                    classMetaData.setXmlFileUrl(url);
                    classMetaData.validate();
                    synchronized (m_metaCache) {
                        // one final check before inserting
                        if (m_metaCache.containsKey(classname)) {
                            classMetaData = (ClassMetaData) m_metaCache.get(classname);
                        } else {
                            m_metaCache.put(classname, classMetaData);
                        }
                    }
                    return classMetaData;
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("The classname in the mapping file " + urlName + ", does not match the required value " + classname + ". Will try and look at another location");
                    }
                }
            }
        }
        String str = "Could not find/parse the mapping file for the class " + classname;
        log.error(str);
        throw new ConfigurationServiceRuntimeException(str);
    }
}
