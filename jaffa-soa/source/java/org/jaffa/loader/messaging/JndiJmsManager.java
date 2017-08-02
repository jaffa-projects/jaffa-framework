package org.jaffa.loader.messaging;

import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.modules.messaging.services.ConfigurationService;
import org.jaffa.modules.messaging.services.configdomain.JmsConfig;
import org.jaffa.modules.messaging.services.configdomain.JndiConfig;
import org.jaffa.util.JAXBHelper;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * A class that manages JNDI JMS specifications, as read in from a
 * configuration file.
 * Created by kcassell on 7/29/2017.
 */
public class JndiJmsManager implements IManager {

    /**
     * The location of the configuration file.
     */
    private static final String DEFAULT_JMS_JNDI_CONFIGURATION_FILE =
            "resources/jms-jndi-config.xml";

    private String jmsJndiConfigurationFile =
            System.getProperty(ConfigurationService.class.getName(),
                    DEFAULT_JMS_JNDI_CONFIGURATION_FILE);

    /**
     * The location of the schema for the configuration file.
     */
    private static final String JMS_JNDI_CONFIGURATION_SCHEMA_FILE =
            "org/jaffa/modules/messaging/services/configdomain/jms-jndi" +
                    "-config_1_0.xsd";

    /**
     * The JNDI repository.  The key is the name;
     * the value in the JndiConfig object.
     */
    private IRepository<String, JmsConfig> jmsRepository =
            new MapRepository<>();


    /**
     * Unmarshall the contents of the configuration to create and register
     * JmsConfig objects.
     * @param resource the object that contains the xml config file.
     * @param context key with which config file to be registered.
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public void registerXML(Resource resource, String context)
            throws JAXBException, SAXException, IOException {
        JndiConfig config = JAXBHelper.unmarshalConfigFile(JndiConfig.class, resource,
                JMS_JNDI_CONFIGURATION_SCHEMA_FILE);
        JmsConfig jmsConfig = config.getJmsConfig();
        jmsRepository.register(jmsConfig.getUser(), jmsConfig, null);
    }

    @Override
    public String getXmlFileName() {
        return jmsJndiConfigurationFile;
    }

    public IRepository<String, JmsConfig> getJmsRepository() {
        return jmsRepository;
    }

    public void setJmsRepository(IRepository<String, JmsConfig> repo) {
        this.jmsRepository = repo;
    }

    /**
     * Get the JmsConfig object from the repository
     * @return the single config object
     */
    public JmsConfig getJmsConfig() {
        JmsConfig config = null;
        Set<String> allKeys = jmsRepository.getAllKeys();

        // There should be just one JmsConfig object
        Iterator<String> iterator = allKeys.iterator();
        if (iterator.hasNext()) {
            String key = iterator.next();
            config = jmsRepository.query(key, null);
        }
        return config;
    }
}
