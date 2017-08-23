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

package org.jaffa.loader.messaging;

import org.apache.log4j.Logger;
import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.modules.messaging.services.configdomain.*;
import org.jaffa.util.ContextHelper;
import org.jaffa.util.JAXBHelper;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class that manages various kinds of messaging object specifications, as
 * read in from a configuration file.
 * Created by kcassell on 7/26/2017.
 */
public class MessagingManager implements IManager {

    /**
     * The name of the configuration file which this class handles.
     */
    private static final String DEFAULT_CONFIGURATION_FILE =
            "jaffa-messaging-config.xml";
    /**
     * The location of the schema for the configuration file.
     */
    private static final String CONFIGURATION_SCHEMA_FILE =
            "org/jaffa/modules/messaging/services/configdomain/jaffa-messaging-config_1_0.xsd";

    private static final Logger log = Logger.getLogger(MessagingManager.class);

    /** The name of the configuration file which this class handles. */
    private String configurationFile = DEFAULT_CONFIGURATION_FILE;

    /** The MessageInfo repository.  The key is the data bean;
     *  the value in the MessageInfo object. */
    private IRepository<MessageInfo> messageInfoRepository =
            new MapRepository<>();

    /** The QueueInfo repository.  The key is the name;
     *  the value in the QueueInfo object. */
    private IRepository<QueueInfo> queueInfoRepository =
            new MapRepository<>();

    /** The TopicInfo repository.  The key is the name;
     *  the value in the TopicInfo object. */
    private IRepository<TopicInfo> topicInfoRepository =
            new MapRepository<>();

    /** The MessageFilter repository.  The key is the filter name;
     *  the value in the MessageFilter object. */
    private IRepository<MessageFilter> messageFilterRepository =
            new MapRepository<>();

    /**
     * Unmarshall the contents of the configuration to create and register
     * MessageInfo, QueueInfo, TopicInfo, and/or MessageFilter objects.
     * @param resource the object that contains the xml config file.
     * @param context key with which config file to be registered.
     * @param variation key with which config file to be registered.
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public void registerXML(Resource resource, String context, String variation)
            throws JAXBException, SAXException, IOException {

        Config config = JAXBHelper.unmarshalConfigFile(Config.class, resource,
                CONFIGURATION_SCHEMA_FILE);

        List<Object> messageObjects = config.getMessageOrQueueOrTopic();

        if (messageObjects != null) {
            for (final Object o : messageObjects) {

                if (o instanceof MessageInfo) {
                    final MessageInfo info = (MessageInfo) o;
                    validateMessageInfo(info);
                    ContextKey contextKey = new ContextKey(info.getDataBean(), resource.getURI().toString(), variation, context);
                    registerMessageInfo(contextKey, info);
                } else if (o instanceof QueueInfo) {
                    final QueueInfo info = (QueueInfo) o;
                    ContextKey contextKey = new ContextKey(info.getName(), resource.getURI().toString(), variation, context);
                    registerQueueInfo(contextKey, info);
                } else if (o instanceof TopicInfo) {
                    final TopicInfo info = (TopicInfo) o;
                    ContextKey contextKey = new ContextKey(info.getName(), resource.getURI().toString(), variation, context);
                    registerTopicInfo(contextKey, info);
                } else if (o instanceof MessageFilter) {
                    final MessageFilter filter = (MessageFilter) o;
                    ContextKey contextKey = new ContextKey(filter.getFilterName(), resource.getURI().toString(), variation, context);
                    registerMessageFilter(contextKey, filter);
                } else {
                    log.warn("MessagingObject.registerXML, unexpected object: " + o);
                }
            }   // for
            checkForQueueAndTopicNamingConflicts();
        }
    }

    /**
     * Check whether the proposed MessageInfo object is adequately specified.
     * @param messageInfo the object to check
     */
    void validateMessageInfo(MessageInfo messageInfo) {
        if (messageInfo.getQueueName() == null
                && messageInfo.getTopicName() == null) {
            String s = "Either queueName or topicName should be specified for dataBean '"
                    + messageInfo.getDataBean()
                    + "' in the Jaffa Messaging configuration file " + configurationFile;
            log.fatal(s);
            throw new RuntimeException(s);
        } else if (messageInfo.getQueueName() == null
                && messageInfo.getTopicName() != null
                && messageInfo.getLockCheck() != null) {
            String s = "The 'lock-check' feature is not supported for a Topic. See dataBean '"
                    + messageInfo.getDataBean()
                    + "' in the Jaffa Messaging configuration file " + configurationFile;
            log.fatal(s);
            throw new RuntimeException(s);
        }
    }

    /**
     * Ensure that there is no name-clash between Queues and Topics.  If there
     * is, throw a runtime exception
     */
    void checkForQueueAndTopicNamingConflicts() {
        // Ensure that there is no name-clash between Queues and Topics
        Set<ContextKey> queueNames = new HashSet<>(queueInfoRepository.getAllKeys());
        Set<ContextKey> topicInfoKeys = topicInfoRepository.getAllKeys();

        // queueNames will contain the intersection of the two sets
        queueNames.retainAll(topicInfoKeys);

        if (queueNames.size() > 0) {
            String s = "Queues and Topics cannot share the same name. "
                    + "Check definition(s) for '" + queueNames
                    + "' in the Jaffa Messaging configuration file "
                    + configurationFile;
            log.fatal(s);
            throw new RuntimeException(s);
        }
    }

    /**
     * Returns the MessageInfo object for the input dataBeanClass,
     * as defined in the configuration file.
     * @param dataBeanClass key used for the repository
     * @return the MessageInfo object for the input dataBeanClass
     */
    public MessageInfo getMessageInfo(String dataBeanClass){
        MessageInfo messageInfo = messageInfoRepository.query(dataBeanClass);
        if (messageInfo == null) {
            // Lookup the class heirarchy. Add a NULL for the dataBeanClassName, even if a MessageInfo is not found
            synchronized (messageInfoRepository) {
                messageInfo = messageInfoRepository.query(dataBeanClass);
                try {
                    if (messageInfo == null) {
                        Class clazz = Class.forName(dataBeanClass);
                        ContextKey superClassContextKey = null;
                        while (clazz.getSuperclass() != null) {
                            clazz = clazz.getSuperclass();
                            messageInfo = messageInfoRepository.query(clazz.getName());
                            superClassContextKey = messageInfoRepository.findKey(clazz.getName());
                            if (messageInfo != null)
                                break;
                        }
                        if (superClassContextKey != null) {
                            messageInfoRepository.register(new ContextKey(dataBeanClass, superClassContextKey.getFileName(),
                                    superClassContextKey.getVariation(), superClassContextKey.getPrecedence()), messageInfo);
                        }
                    }
                }catch (ClassNotFoundException e){
                    log.error("Unable to find class definition for "+dataBeanClass, e);
                }
            }
        }
        return messageInfo;
    }

    /**
     * retrieves the QueueInfo from the repository
     * @param dataBeanClassName key used for the repository
     * @return QueueInfo
     */
    public QueueInfo getQueueInfo(String dataBeanClassName) {
        return queueInfoRepository.query(dataBeanClassName);
    }

    /**
     * retrieves the TopicInfo from the repository
     * @param dataBeanClassName key used for the repository
     * @return TopicInfo
     */
    public TopicInfo getTopicInfo(String dataBeanClassName) {
        return topicInfoRepository.query(dataBeanClassName);
    }

    /**
     * retrieves all the Queue Names in the repository
     * @return all Queue Names
     */
    public String[] getQueueNames() {
        return queueInfoRepository.getAllKeyIds().toArray(new String[0]);
    }

    /**
     * retrieves all the Topic Names in the repository
     * @return all Topic Names
     */
    public String[] getTopicNames() {
        return topicInfoRepository.getAllKeyIds().toArray(new String[0]);
    }

    /**
     * retrieves all the message filters in the repository
     * @return list of all message filters
     */
    public List<MessageFilter> getMessageFilters() {
        return messageFilterRepository.getAllValues();
    }

    ////////////////////////////////////////////////////////////////////////
    //////////////////////////// Registration //////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    /**
     * Register TypeInfo in the repository
     * @param key the key associated with the value in the repository
     * @param value the object to store
     */
    public void registerMessageFilter(ContextKey key, MessageFilter value) {
        messageFilterRepository.register(key, value);
    }

    /**
     * Register TopicInfo in the repository
     * @param key the key associated with the value in the repository
     * @param value the object to store
     */
    public void registerTopicInfo(ContextKey key, TopicInfo value) {
        topicInfoRepository.register(key, value);
    }

    /**
     * Register QueueInfo in the repository
     * @param key the key associated with the value in the repository
     * @param value the object to store
     */
    public void registerQueueInfo(ContextKey key, QueueInfo value) {
        queueInfoRepository.register(key, value);
    }

    /**
     * Register MessageInfo in the repository
     * @param key the key associated with the value in the repository
     * @param value the object to store
     */
    public void registerMessageInfo(ContextKey key, MessageInfo value) {
        messageInfoRepository.register(key, value);
    }

    /**
     * Unregister a MessageFilter object from the repository
     * @param key the key for the value being removed from the repository
     */
    public void unregisterMessageFilter(ContextKey key) {
        messageFilterRepository.unregister(key);
    }

    /**
     * Unregister a TopicInfo object from the repository
     * @param key the key for the value being removed from the repository
     */
    public void unregisterTopicInfo(ContextKey key) {
        topicInfoRepository.unregister(key);
    }

    /**
     * Unregister a QueueInfo object from the repository
     * @param key the key for the value being removed from the repository
     */
    public void unregisterQueueInfo(ContextKey key) {
        queueInfoRepository.unregister(key);
    }

    /**
     * Unregister a MessageInfo object from the repository
     * @param key the key for the value being removed from the repository
     */
    public void unregisterMessageInfo(ContextKey key) {
        messageInfoRepository.unregister(key);
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////// Simple Accessors ////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    @Override
    public String getXmlFileName() {
        return DEFAULT_CONFIGURATION_FILE;
    }

    public IRepository<MessageInfo> getMessageInfoRepository() {
        return messageInfoRepository;
    }

    public void setMessageInfoRepository(IRepository<MessageInfo>
                                                 messageInfoRepository) {
        this.messageInfoRepository = messageInfoRepository;
    }

    public IRepository<QueueInfo> getQueueInfoRepository() {
        return queueInfoRepository;
    }

    public void setQueueInfoRepository(IRepository<QueueInfo> repo) {
        this.queueInfoRepository = repo;
    }

    public IRepository<TopicInfo> getTopicInfoRepository() {
        return topicInfoRepository;
    }

    public void setTopicInfoRepository(IRepository<TopicInfo> topicInfoRepository) {
        this.topicInfoRepository = topicInfoRepository;
    }

    public IRepository<MessageFilter> getMessageFilterRepository() {
        return messageFilterRepository;
    }

    public void setMessageFilterRepository(IRepository<MessageFilter> messageFilterRepository) {
        this.messageFilterRepository = messageFilterRepository;
    }


}
