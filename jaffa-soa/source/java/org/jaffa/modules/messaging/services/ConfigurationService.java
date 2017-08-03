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

import org.apache.log4j.Logger;
import org.jaffa.loader.messaging.JndiJmsManager;
import org.jaffa.loader.messaging.MessagingManager;
import org.jaffa.modules.messaging.services.configdomain.*;

import java.util.List;

/**
 * This class implements the Singleton pattern. Use the getInstance() method
 * to get an instance of this class.
 * The Configuration Service reads the 'resources/jaffa-messaging-config.xml'
 * file. It then performs the initializations.
 * It provides methods to extract information from the configuration file.
 */
public class ConfigurationService {

  private static final Logger log = Logger.getLogger(ConfigurationService.class);


  /**
   * The singleton instance of this class.
   */
  private static ConfigurationService c_singleton = null;

  /** The manager of messaging configurations.  */
  private static MessagingManager messagingManager;

  /** The manager of JNDI JMS configurations.  */
  private static JndiJmsManager jndiJmsManager;

  /**
   * The private constructor forces use of the singleton via getInstance().
   */
  private ConfigurationService() {
  }

  /**
   * Creates an instance of ConfigurationService, if not already instantiated.
   * @return An instance of the ConfigurationService.
   */
  public static ConfigurationService getInstance() {
    if (c_singleton == null)
      createConfigurationServiceInstance(true);
    return c_singleton;
  }

  /**
   * Creates an instance of ConfigurationService, if not already instantiated.
   * @return An instance of the ConfigurationService.
   */
  public static ConfigurationService getInstance(final boolean setJmsConfig) {
    if (c_singleton == null)
      createConfigurationServiceInstance(setJmsConfig);
    return c_singleton;
  }

  private static synchronized void createConfigurationServiceInstance(final boolean setJmsConfig) {
    if (c_singleton == null) {
      c_singleton = new ConfigurationService();
      if (log.isDebugEnabled())
        log.debug("An instance of the ConfigurationService has been created");
    }
  }


  /**
   * Returns the JmsConfig object, as defined in the configuration file.
   * @return the JmsConfig object, as defined in the configuration file.
   */
  public JmsConfig getJmsConfig() {
     return jndiJmsManager.getJmsConfig();
  }

  /**
   * Returns the MessageInfo object for the input dataBeanClassName, as defined in the configuration file.
   *
   * @param dataBeanClassName the class name for a dataBean.
   * @return the MessageInfo object for the input dataBeanClassName, as defined in the configuration file.
   * @throws ClassNotFoundException if dataBeanClassName is not found on the classpath
   */
  public MessageInfo getMessageInfo(String dataBeanClassName)
          throws ClassNotFoundException {
    return messagingManager.getMessageInfo(dataBeanClassName, null);
  }

  /**
   * Returns an array of queue names, as defined in the configuration file.
   *
   * @return an array of queue names, as defined in the configuration file.
   */
  public String[] getQueueNames() {
    return messagingManager.getQueueNames();
  }

  /**
   * Returns the QueueInfo object for the input queueName,
   * as defined in the configuration file.
   * @param queueName the queue name.
   * @return the QueueInfo object for the input queueName,
   * as defined in the configuration file.
   */
  public QueueInfo getQueueInfo(String queueName) {
    return messagingManager.getQueueInfo(queueName, null);
  }

  /**
   * Returns an array of topic names, as defined in the configuration file.
   *
   * @return an array of topic names, as defined in the configuration file.
   */
  public String[] getTopicNames() {
    return messagingManager.getTopicNames();
  }

  /**
   * Returns the TopicInfo object for the input topicName, as defined in the configuration file.
   * @param topicName the topic name.
   * @return the TopicInfo object for the input topicName, as defined in the configuration file.
   */
  public TopicInfo getTopicInfo(String topicName) {
    return messagingManager.getTopicInfo(topicName, null);
  }

  /**
   * Returns a list of MessageFilter instances, as defined in the configuration file.
   * @return a list of MessageFilter instances, as defined in the configuration file.
   */
  public List<MessageFilter> getMessageFilters() {
     return messagingManager.getMessageFilters();
  }

  public static MessagingManager getMessagingManager() {
    return messagingManager;
  }

  public static void setMessagingManager(MessagingManager manager) {
    messagingManager = manager;
  }

  public static JndiJmsManager getJndiJmsManager() {
    return jndiJmsManager;
  }

  public static void setJndiJmsManager(JndiJmsManager manager) {
    jndiJmsManager = manager;
  }

}