package org.jaffa.config.loader.messaging;

import org.apache.log4j.Logger;
import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.modules.messaging.services.configdomain.*;
import org.jaffa.util.JAXBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
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
    private IRepository<String, MessageInfo> messageInfoRepository =
            new MapRepository<>();

    /** The QueueInfo repository.  The key is the name;
     *  the value in the QueueInfo object. */
    private IRepository<String, QueueInfo> queueInfoRepository =
            new MapRepository<>();

    /** The TopicInfo repository.  The key is the name;
     *  the value in the TopicInfo object. */
    private IRepository<String, TopicInfo> topicInfoRepository =
            new MapRepository<>();

    /** The MessageFilter repository.  The key is the filter name;
     *  the value in the MessageFilter object. */
    private IRepository<String, MessageFilter> messageFilterRepository =
            new MapRepository<>();

    @Autowired
    @Qualifier("contextOrder")
    public ArrayList<String> contextOrder;

    /**
     * Unmarshall the contents of the configuration to create and register
     * MessageInfo, QueueInfo, TopicInfo, and/or MessageFilter objects.
     * @param resource the object that contains the xml config file.
     * @param context key with which config file to be registered.
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public void registerXML(Resource resource, String context)
            throws JAXBException, SAXException, IOException {

        Config config = JAXBHelper.unmarshalConfigFile(Config.class, resource,
                CONFIGURATION_SCHEMA_FILE);

        List<Object> messageObjects = config.getMessageOrQueueOrTopic();

        if (messageObjects != null) {
            for (final Object o : messageObjects) {

                if (o instanceof MessageInfo) {
                    final MessageInfo info = (MessageInfo) o;
                    validateMessageInfo(info);
                    registerMessageInfo(info.getDataBean(), info, context);
                } else if (o instanceof QueueInfo) {
                    final QueueInfo info = (QueueInfo) o;
                    registerQueueInfo(info.getName(), info, context);
                } else if (o instanceof TopicInfo) {
                    final TopicInfo info = (TopicInfo) o;
                    registerTopicInfo(info.getName(), info, context);
                } else if (o instanceof MessageFilter) {
                    final MessageFilter filter = (MessageFilter) o;
                    registerMessageFilter(filter.getFilterName(), filter, context);
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
        Set<String> queueNames = new HashSet<>(queueInfoRepository.getAllKeys());
        Set<String> topicInfoKeys = topicInfoRepository.getAllKeys();

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
     * @param dataBeanClass the class for a dataBean.
     * @return the MessageInfo object for the input dataBeanClass
     */
    public MessageInfo getMessageInfo(Class dataBeanClass, List<String>
            contextOrder) {
        final String dataBeanClassName = dataBeanClass.getName();
        MessageInfo messageInfo =
                getMessageInfo(dataBeanClassName, contextOrder);
        // Lookup the class hierarchy. Add a NULL for the dataBeanClassName,
        // even if a MessageInfo is not found
        while (messageInfo == null
                && dataBeanClass.getSuperclass() != null) {
            dataBeanClass = dataBeanClass.getSuperclass();
            messageInfo = getMessageInfo(dataBeanClass.getName(),
                    contextOrder);
            registerMessageInfo(dataBeanClassName, messageInfo, null);
        }
        return messageInfo;
    }

    /**
     * retrieves the MessageInfo from the repository
     * @param dataBeanClassName key used for the repository
     * @param contextOrderParam Order of the contexts used for retrieval
     * @return MessageInfo
     */
    public MessageInfo getMessageInfo(String dataBeanClassName,
                                      List<String> contextOrderParam) {
        if (contextOrderParam == null) {
            contextOrderParam = contextOrder;
        }
        return messageInfoRepository.query(dataBeanClassName, contextOrderParam);
    }

    /**
     * retrieves the QueueInfo from the repository
     * @param dataBeanClassName key used for the repository
     * @param contextOrderParam Order of the contexts used for retrieval
     * @return QueueInfo
     */
    public QueueInfo getQueueInfo(String dataBeanClassName,
                                      List<String> contextOrderParam) {
        if (contextOrderParam == null) {
            contextOrderParam = contextOrder;
        }
        return queueInfoRepository.query(dataBeanClassName, contextOrderParam);
    }

    /**
     * retrieves the TopicInfo from the repository
     * @param dataBeanClassName key used for the repository
     * @param contextOrderParam Order of the contexts used for retrieval
     * @return TopicInfo
     */
    public TopicInfo getTopicInfo(String dataBeanClassName,
                                  List<String> contextOrderParam) {
        if (contextOrderParam == null) {
            contextOrderParam = contextOrder;
        }
        return topicInfoRepository.query(dataBeanClassName, contextOrderParam);
    }

    /**
     * retrieves all the Queue Names in the repository
     * @return all Queue Names
     */
    public String[] getQueueNames() {
        return queueInfoRepository.getAllKeys().toArray(new String[0]);
    }

    /**
     * retrieves all the Topic Names in the repository
     * @return all Topic Names
     */
    public String[] getTopicNames() {
        return topicInfoRepository.getAllKeys().toArray(new String[0]);
    }

    /**
     * retrieves all the message filters in the repository
     * @return list of all message filters
     */
    public List<MessageFilter> getMessageFilters() {
        return messageFilterRepository.getAllValues(null);
    }

    ////////////////////////////////////////////////////////////////////////
    //////////////////////////// Registration //////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    /**
     * Register TypeInfo in the repository
     * @param key the key associated with the value in the repository
     * @param value the object to store
     * @param context with which repository to be associated with
     */
    public void registerMessageFilter(String key, MessageFilter value, String context) {
        messageFilterRepository.register(key, value, context);
    }

    /**
     * Register TopicInfo in the repository
     * @param key the key associated with the value in the repository
     * @param value the object to store
     * @param context with which repository to be associated with
     */
    public void registerTopicInfo(String key, TopicInfo value, String context) {
        topicInfoRepository.register(key, value, context);
    }

    /**
     * Register QueueInfo in the repository
     * @param key the key associated with the value in the repository
     * @param value the object to store
     * @param context with which repository to be associated with
     */
    public void registerQueueInfo(String key, QueueInfo value, String context) {
        queueInfoRepository.register(key, value, context);
    }

    /**
     * Register MessageInfo in the repository
     * @param key the key associated with the value in the repository
     * @param context with which repository to be associated with
     * @param value the object to store
     */
    public void registerMessageInfo(String key, MessageInfo value, String context) {
        messageInfoRepository.register(key, value, context);
    }

    /**
     * Unregister a MessageFilter object from the repository
     * @param key the key for the value being removed from the repository
     * @param context with which repository to be associated with
     */
    public void unregisterMessageFilter(String key, String context) {
        messageFilterRepository.unregister(key, context);
    }

    /**
     * Unregister a TopicInfo object from the repository
     * @param key the key for the value being removed from the repository
     * @param context with which repository to be associated with
     */
    public void unregisterTopicInfo(String key, String context) {
        topicInfoRepository.unregister(key, context);
    }

    /**
     * Unregister a QueueInfo object from the repository
     * @param key the key for the value being removed from the repository
     * @param context with which repository to be associated with
     */
    public void unregisterQueueInfo(String key, String context) {
        queueInfoRepository.unregister(key, context);
    }

    /**
     * Unregister a MessageInfo object from the repository
     * @param key the key for the value being removed from the repository
     * @param context with which repository to be associated with
     */
    public void unregisterMessageInfo(String key, String context) {
        messageInfoRepository.unregister(key, context);
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////// Simple Accessors ////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    @Override
    public String getXmlFileName() {
        return DEFAULT_CONFIGURATION_FILE;
    }

    public IRepository<String, MessageInfo> getMessageInfoRepository() {
        return messageInfoRepository;
    }

    public void setMessageInfoRepository(IRepository<String, MessageInfo>
                                                 messageInfoRepository) {
        this.messageInfoRepository = messageInfoRepository;
    }

    public IRepository<String, QueueInfo> getQueueInfoRepository() {
        return queueInfoRepository;
    }

    public void setQueueInfoRepository(IRepository<String, QueueInfo> repo) {
        this.queueInfoRepository = repo;
    }

    public IRepository<String, TopicInfo> getTopicInfoRepository() {
        return topicInfoRepository;
    }

    public void setTopicInfoRepository(IRepository<String, TopicInfo> topicInfoRepository) {
        this.topicInfoRepository = topicInfoRepository;
    }

    public IRepository<String, MessageFilter> getMessageFilterRepository() {
        return messageFilterRepository;
    }

    public void setMessageFilterRepository(IRepository<String, MessageFilter> messageFilterRepository) {
        this.messageFilterRepository = messageFilterRepository;
    }


}
