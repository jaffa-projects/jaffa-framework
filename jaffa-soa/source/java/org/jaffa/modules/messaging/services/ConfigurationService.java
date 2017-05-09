/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2015 JAFFA Development Group
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
package org.jaffa.modules.messaging.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.jaffa.modules.messaging.services.configdomain.*;
import org.jaffa.util.JAXBHelper;
import org.jaffa.util.URLHelper;
import org.xml.sax.SAXException;

/**
 * This class implements the Singleton pattern. Use the getInstance() method to get an instance of this class.
 * The Configuration Service reads the 'resources/jaffa-messaging-config.xml' file. It then performs the initializations.
 * It provides methods to extract information from the configuration file.
 */
public class ConfigurationService {

  private static final Logger log = Logger.getLogger(ConfigurationService.class);
  /**
   * The location of the configuration file.
   */
  private static final String DEFAULT_CONFIGURATION_FILE = "resources/jaffa-messaging-config.xml";
  private static final String CONFIGURATION_FILE = System.getProperty(ConfigurationService.class.getName(), DEFAULT_CONFIGURATION_FILE);
  /**
   * The location of the configuration file.
   */
  private static final String DEFAULT_JMS_JNDI_CONFIGURATION_FILE = "resources/jms-jndi-config.xml";
  private static final String JMS_JNDI_CONFIGURATION_FILE = System.getProperty(ConfigurationService.class.getName(), DEFAULT_JMS_JNDI_CONFIGURATION_FILE);
  /**
   * The location of the schema for the configuration file.
   */
  private static final String CONFIGURATION_SCHEMA_FILE = "org/jaffa/modules/messaging/services/configdomain/jaffa-messaging-config_1_0.xsd";
  /**
   * The location of the schema for the configuration file.
   */
  private static final String JMS_JNDI_CONFIGURATION_SCHEMA_FILE = "org/jaffa/modules/messaging/services/configdomain/jms-jndi-config_1_0.xsd";
  /**
   * The singleton instance of this class.
   */
  private static ConfigurationService c_singleton = null;
  /**
   * Holds the Config.
   */
  private Config m_config;
  /**
   * Holds the Config.
   */
  private JndiConfig jmsConfig;
  /**
   * Map of dataBeanClassName and corresponding MessageInfo instance.
   */
  private Map<String, MessageInfo> m_messageInfoMap = new LinkedHashMap<String, MessageInfo>();
  /**
   * Map of queueName and corresponding QueueInfo instance.
   */
  private Map<String, QueueInfo> m_queueInfoMap = new LinkedHashMap<String, QueueInfo>();
  /**
   * Map of topicName and corresponding TopicInfo instance.
   */
  private Map<String, TopicInfo> m_topicInfoMap = new LinkedHashMap<String, TopicInfo>();
  /**
   * List of MessageFilter instances.
   */
  private List<MessageFilter> m_messageFilters = new LinkedList<MessageFilter>();

  /**
   * Creates an instance of ConfigurationService, if not already instantiated.
   *
   * @return An instance of the ConfigurationService.
   */
  public static ConfigurationService getInstance() {
    if (c_singleton == null)
      createConfigurationServiceInstance(true);
    return c_singleton;
  }

  /**
   * Creates an instance of ConfigurationService, if not already instantiated.
   *
   * @return An instance of the ConfigurationService.
   */
  public static ConfigurationService getInstance(final boolean setJmsConfig) {
    if (c_singleton == null)
      createConfigurationServiceInstance(setJmsConfig);
    return c_singleton;
  }

  private static synchronized void createConfigurationServiceInstance(final boolean setJmsConfig) {
    if (c_singleton == null) {
      c_singleton = new ConfigurationService(setJmsConfig);
      if (log.isDebugEnabled())
        log.debug("An instance of the ConfigurationService has been created");
    }
  }

  /**
   * Parses the configuration file.
   *
   * @param setJmsConfig flag.
   *                     <p/>
   *                     A RuntimeException is thrown
   *                     - If the configuration file is not found
   *                     - If the configuration file has invalid XML
   */
  private ConfigurationService(final boolean setJmsConfig) {
    setConfig();
    if (setJmsConfig)
      setJmsConfig();
  }

  private void setConfig() {
    try {
      // unmarshal the configuration file
      m_config = parseConfigurationFile(CONFIGURATION_FILE, CONFIGURATION_SCHEMA_FILE);
      // build up the MessageInfo, QueueInfo, TopicInfo and MessageFilter maps/list
      if (m_config.getMessageOrQueueOrTopic() != null) {
        for (Object o : m_config.getMessageOrQueueOrTopic()) {
          if (o.getClass() == MessageInfo.class) {
            MessageInfo messageInfo = (MessageInfo) o;
            m_messageInfoMap.put(messageInfo.getDataBean(), messageInfo);
          } else if (o.getClass() == QueueInfo.class) {
            QueueInfo queueInfo = (QueueInfo) o;
            m_queueInfoMap.put(queueInfo.getName(), queueInfo);
          } else if (o.getClass() == TopicInfo.class) {
            TopicInfo topicInfo = (TopicInfo) o;
            m_topicInfoMap.put(topicInfo.getName(), topicInfo);
          } else if (o.getClass() == MessageFilter.class) {
            m_messageFilters.add((MessageFilter) o);
          }
        }
      }

      // validate the MessageInfo instances
      for (MessageInfo messageInfo : m_messageInfoMap.values()) {
        if (messageInfo.getQueueName() == null && messageInfo.getTopicName() == null) {
          String s = "Either queueName or topicName should be specified for dataBean '" + messageInfo.getDataBean() + "' in the Jaffa Messaging configuration file " + CONFIGURATION_FILE;
          log.fatal(s);
          throw new RuntimeException(s);
        } else if (messageInfo.getQueueName() == null && messageInfo.getTopicName() != null && messageInfo.getLockCheck() != null) {
          String s = "The 'lock-check' feature is not supported for a Topic. See dataBean '" + messageInfo.getDataBean() + "' in the Jaffa Messaging configuration file " + CONFIGURATION_FILE;
          log.fatal(s);
          throw new RuntimeException(s);
        }
      }

      // Ensure that there is no name-clash between Queues and Topics
      Set<String> queueNames = new HashSet(m_queueInfoMap.keySet());
      if (queueNames.size() > 0 && queueNames.retainAll(m_topicInfoMap.keySet()) && queueNames.size() > 0) {
        String s = "Queues and Topics cannot share the same name. Check definition(s) for '" + queueNames + "' in the Jaffa Messaging configuration file " + CONFIGURATION_FILE;
        log.fatal(s);
        throw new RuntimeException(s);
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

  private void setJmsConfig() {
    try {
      jmsConfig = parseJndiConfigurationFile(JMS_JNDI_CONFIGURATION_FILE, JMS_JNDI_CONFIGURATION_SCHEMA_FILE);
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
   * Returns the JmsConfig object, as defined in the configuration file.
   *
   * @return the JmsConfig object, as defined in the configuration file.
   */
  public JmsConfig getJmsConfig() {
    return jmsConfig.getJmsConfig();
  }

  /**
   * Returns the MessageInfo object for the input dataBeanClassName, as defined in the configuration file.
   *
   * @param dataBeanClassName the class name for a dataBean.
   * @return the MessageInfo object for the input dataBeanClassName, as defined in the configuration file.
   * @throws ClassNotFoundException if dataBeanClassName is not found on the classpath
   */
  public MessageInfo getMessageInfo(String dataBeanClassName) throws ClassNotFoundException {
    MessageInfo messageInfo = m_messageInfoMap.get(dataBeanClassName);
    if (messageInfo == null && !m_messageInfoMap.containsKey(dataBeanClassName)) {
      // Lookup the class heirarchy. Add a NULL for the dataBeanClassName, even if a MessageInfo is not found
      synchronized (m_messageInfoMap) {
        messageInfo = m_messageInfoMap.get(dataBeanClassName);
        if (messageInfo == null && !m_messageInfoMap.containsKey(dataBeanClassName)) {
          Class clazz = Class.forName(dataBeanClassName);
          while (clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
            messageInfo = m_messageInfoMap.get(clazz.getName());
            if (messageInfo != null)
              break;
          }
          m_messageInfoMap.put(dataBeanClassName, messageInfo);
        }
      }
    }
    return messageInfo;
  }

  /**
   * Returns an array of queue names, as defined in the configuration file.
   *
   * @return an array of queue names, as defined in the configuration file.
   */
  public String[] getQueueNames() {
    return m_queueInfoMap.keySet().toArray(new String[m_queueInfoMap.size()]);
  }

  /**
   * Returns the QueueInfo object for the input queueName, as defined in the configuration file.
   *
   * @param queueName the queue name.
   * @return the QueueInfo object for the input queueName, as defined in the configuration file.
   */
  public QueueInfo getQueueInfo(String queueName) {
    return m_queueInfoMap.get(queueName);
  }

  /**
   * Returns an array of topic names, as defined in the configuration file.
   *
   * @return an array of topic names, as defined in the configuration file.
   */
  public String[] getTopicNames() {
    return m_topicInfoMap.keySet().toArray(new String[m_topicInfoMap.size()]);
  }

  /**
   * Returns the TopicInfo object for the input topicName, as defined in the configuration file.
   *
   * @param topicName the topic name.
   * @return the TopicInfo object for the input topicName, as defined in the configuration file.
   */
  public TopicInfo getTopicInfo(String topicName) {
    return m_topicInfoMap.get(topicName);
  }

  /**
   * Returns a list of MessageFilter instances, as defined in the configuration file.
   *
   * @return a list of MessageFilter instances, as defined in the configuration file.
   */
  public List<MessageFilter> getMessageFilters() {
    return m_messageFilters;
  }

  /**
   * Loads the configurationFile using JAXB.
   * The XML is validated as per the schema 'org/jaffa/modules/messaging/services/configdomain/jaffa-messaging-config_1_0.xsd'.
   * The XML is then parsed to return a corresponding Java object.
   *
   * @return the Java representation of the XML inside the configuration file.
   * @throws MalformedURLException if the configuration file is not found.
   * @throws JAXBException         if any error occurs during the unmarshalling of XML.
   * @throws SAXException          if the schema file cannot be loaded.
   */
  private Config parseConfigurationFile(final String file, final String configSchema)
      throws MalformedURLException, JAXBException, SAXException {
    if (log.isDebugEnabled())
      log.debug("Unmarshalling the configuration file " + file);
    URL configFileUrl = URLHelper.newExtendedURL(file);
    URL configSchemaFileUrl = URLHelper.newExtendedURL(configSchema);
    JAXBContext jc = JAXBHelper.obtainJAXBContext(Config.class);
    Unmarshaller unmarshaller = jc.createUnmarshaller();
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(configSchemaFileUrl);
    unmarshaller.setSchema(schema);
    return (Config) unmarshaller.unmarshal(configFileUrl);
  }

  /**
   * Loads the configurationFile using JAXB.
   * The XML is validated as per the schema 'org/jaffa/modules/messaging/services/configdomain/jaffa-messaging-config_1_0.xsd'.
   * The XML is then parsed to return a corresponding Java object.
   *
   * @return the Java representation of the XML inside the configuration file.
   * @throws MalformedURLException if the configuration file is not found.
   * @throws JAXBException         if any error occurs during the unmarshalling of XML.
   * @throws SAXException          if the schema file cannot be loaded.
   */
  private JndiConfig parseJndiConfigurationFile(final String file, final String configSchema)
      throws MalformedURLException, JAXBException, SAXException {
    if (log.isDebugEnabled())
      log.debug("Unmarshalling the configuration file " + file);
    URL configFileUrl = URLHelper.newExtendedURL(file);
    URL configSchemaFileUrl = URLHelper.newExtendedURL(configSchema);
    JAXBContext jc = JAXBHelper.obtainJAXBContext(JndiConfig.class);
    Unmarshaller unmarshaller = jc.createUnmarshaller();
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(configSchemaFileUrl);
    unmarshaller.setSchema(schema);
    return (JndiConfig) unmarshaller.unmarshal(configFileUrl);
  }
}