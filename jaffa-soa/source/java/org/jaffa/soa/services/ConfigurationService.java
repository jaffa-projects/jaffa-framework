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
package org.jaffa.soa.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.log4j.Logger;
import org.jaffa.soa.services.configdomain.SoaEventInfo;
import org.jaffa.soa.services.configdomain.SoaEvents;
import org.jaffa.util.JAXBHelper;
import org.jaffa.util.URLHelper;
import org.xml.sax.SAXException;

/**
 * This class implements the Singleton pattern. Use the getInstance() method to get an instance of this class.
 * The Configuration Service reads the 'resources/soa-events.xml' file. It then performs the initializations.
 * It provides methods to extract information from the configuration file.
 * <p>
 * An example configuration file
 *     <?xml version="1.0" encoding="UTF-8"?>
 *     <soa-events>
 *       <soa-event name='NAME1' label='Label1' description='Some description'>
 *         <param name='param1' data-type='STRING' description='Some description'/>
 *         <param name='param2' data-type='STRING' description='Some description'/>
 *         <inject-domain-fact domain-class='com.miro.requitioning.part.domain.PartRequest'>
 *           <domain-key>params.requestId</domain-key>
 *         </inject-domain-fact>
 *       </soa-event>
 *
 *       <soa-event ...>
 *         ...
 *         ...
 *       </soa-event>
 *     </soa-events>
 *
 */
public class ConfigurationService {

    private static final Logger log = Logger.getLogger(ConfigurationService.class);
    private static final String DEFAULT_CONFIGURATION_FILE = "resources/soa-events.xml";
    private static final String CONFIGURATION_FILE = System.getProperty(ConfigurationService.class.getName(), DEFAULT_CONFIGURATION_FILE);
    private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/soa/services/configdomain/soa-events_1_0.xsd";
    private static volatile ConfigurationService c_singleton = null;
    private SoaEvents m_config = null;
    private final Map<String, SoaEventInfo> m_soaEventInfoMap = new LinkedHashMap<String, SoaEventInfo>();

    /**
     * Creates an instance of ConfigurationService, if not already instantiated.
     * @return An instance of the ConfigurationService.
     */
    public static ConfigurationService getInstance() {
        if (c_singleton == null)
            createConfigurationServiceInstance();
        return c_singleton;
    }

    private static synchronized void createConfigurationServiceInstance() {
        if (c_singleton == null) {
            c_singleton = new ConfigurationService();
            if (log.isDebugEnabled())
                log.debug("An instance of the ConfigurationService has been created");
        }
    }

    /**
     * Parses the configuration file.
     * <p>
     * A RuntimeException is thrown
     *   - If the configuration file is not found
     *   - If the configuration file has invalid XML
     */
    private ConfigurationService() {
        try {
            // unmarshal the configuration file
            m_config = parseConfigurationFile();

            // build up the maps/list
            if (m_config.getSoaEvent() != null) {
                for (SoaEventInfo soaEventInfo : m_config.getSoaEvent())
                    m_soaEventInfoMap.put(soaEventInfo.getName(), soaEventInfo);
            }
        } catch (JAXBException e) {
            String s = "Error in parsing the configuration file " + CONFIGURATION_FILE;
            log.fatal(s, e);
            throw new RuntimeException(s, e);
        } catch (MalformedURLException e) {
            String s = "Error in locating the configuration file " + CONFIGURATION_FILE;
            log.fatal(s, e);
            throw new RuntimeException(s, e);
        } catch (SAXException e) {
            String s = "Error in loading the schema for the configuration file " + CONFIGURATION_SCHEMA_FILE;
            log.fatal(s, e);
            throw new RuntimeException(s, e);
        }
    }

    /**
     * Returns the SoaEventInfo object for the input soaEventName, as defined in the configuration file.
     * @param soaEventName the name of a SOA Event.
     * @return the SoaEventInfo object for the input soaEventName, as defined in the configuration file.
     */
    public SoaEventInfo getSoaEventInfo(String soaEventName) {
        return m_soaEventInfoMap.get(soaEventName);
    }

    /**
     * Returns all SoaEventInfo objects, as defined in the configuration file.
     * @return all SoaEventInfo objects, as defined in the configuration file.
     */
    public SoaEventInfo[] getAllSoaEventInfo() {
        return m_soaEventInfoMap.values().toArray(new SoaEventInfo[m_soaEventInfoMap.size()]);
    }

    /**
     * Returns an array of SOA Event names, as defined in the configuration file.
     * @return an array of SOA Event names, as defined in the configuration file.
     */
    public String[] getSoaEventNames() {
        return m_soaEventInfoMap.keySet().toArray(new String[m_soaEventInfoMap.size()]);
    }

    /**
     * Loads the configurationFile using JAXB.
     * The XML is validated as per the schema 'org/jaffa/soa/services/configdomain/soa-events_1_0.xsd'.
     * The XML is then parsed to return a corresponding Java object.
     * @return the Java representation of the XML inside the configuration file.
     * @throws MalformedURLException if the configuration file is not found.
     * @throws JAXBException if any error occurs during the unmarshalling of XML.
     * @throws SAXException if the schema file cannot be loaded.
     */
    private SoaEvents parseConfigurationFile()
            throws MalformedURLException, JAXBException, SAXException {
        if (log.isDebugEnabled())
            log.debug("Unmarshalling the configuration file " + CONFIGURATION_FILE);
        URL configFileUrl = URLHelper.newExtendedURL(CONFIGURATION_FILE);
        URL configSchemaFileUrl = URLHelper.newExtendedURL(CONFIGURATION_SCHEMA_FILE);
        JAXBContext jc = JAXBHelper.obtainJAXBContext(SoaEvents.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(configSchemaFileUrl);
        unmarshaller.setSchema(schema);
        return (SoaEvents) unmarshaller.unmarshal(configFileUrl);
    }
}
