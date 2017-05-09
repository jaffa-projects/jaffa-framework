package org.jaffa.dwr.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.jaffa.dwr.services.configdomain.Allow;
import org.jaffa.dwr.services.configdomain.Create;
import org.jaffa.dwr.services.configdomain.Dwr;
import org.jaffa.util.JAXBHelper;
import org.jaffa.util.URLHelper;
import org.xml.sax.SAXException;




public class ConfigurationService {

    private static final Logger log = Logger.getLogger(ConfigurationService.class);
    private static final String DEFAULT_CONFIGURATION_FILE = "resources/dwr.xml";
    private static final String CONFIGURATION_FILE = System.getProperty(ConfigurationService.class.getName(), DEFAULT_CONFIGURATION_FILE);
    private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/dwr/services/configdomain/dwr_2_0.xsd";
    private static volatile ConfigurationService c_singleton = null;
    private Dwr m_config = null;
    private final List<Create> m_creates = new ArrayList<Create>();

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
            if (m_config.getAllow() != null) {
            	Allow allow = m_config.getAllow();
            	if(allow.getCreateOrConvertOrFilter()!=null) {
            		for (Object obj : allow.getCreateOrConvertOrFilter()) {
            			if(obj instanceof Create) {
            				Create c = (Create)obj;
            				m_creates.add(c);
            			}
            		}
            	}
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
     * Returns the AlertInfo object for the input alertName, as defined in the configuration file.
     * @param alertName the name of a Alert.
     * @return the AlertInfo object for the input alertName, as defined in the configuration file.
     */
    public List<Create> getCreateList() {
        return m_creates;
    }
    
    /**
     * Loads the configurationFile using JAXB.
     * The XML is validated as per the schema ''.
     * The XML is then parsed to return a corresponding Java object.
     * @return the Java representation of the XML inside the configuration file.
     * @throws MalformedURLException if the configuration file is not found.
     * @throws JAXBException if any error occurs during the unmarshalling of XML.
     * @throws SAXException if the schema file cannot be loaded.
     */
    private Dwr parseConfigurationFile()
            throws MalformedURLException, JAXBException, SAXException {
        if (log.isDebugEnabled())
            log.debug("Unmarshalling the configuration file " + CONFIGURATION_FILE);
        URL configFileUrl = URLHelper.newExtendedURL(CONFIGURATION_FILE);
        URL configSchemaFileUrl = URLHelper.newExtendedURL(CONFIGURATION_SCHEMA_FILE);
        JAXBContext jc = JAXBHelper.obtainJAXBContext(Dwr.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(configSchemaFileUrl);
        unmarshaller.setSchema(schema);
        return (Dwr) unmarshaller.unmarshal(configFileUrl);
    }
}
