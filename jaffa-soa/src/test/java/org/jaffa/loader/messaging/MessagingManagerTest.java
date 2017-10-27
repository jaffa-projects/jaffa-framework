package org.jaffa.loader.messaging;

import org.jaffa.loader.ContextKey;
import org.jaffa.modules.messaging.services.configdomain.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests of the MessagingManager.
 * Created by kcassell on 7/30/2017.
 */
public class MessagingManagerTest {

    /** The manager being tested. */
    private MessagingManager messagingManager;

    @Before
    public void setUp() throws Exception {
        messagingManager = new MessagingManager();
    }

    @After
    public void tearDown() throws Exception {
        messagingManager = null;
    }


    @Test
    public void registerXML() throws Exception {
// TODO
    }



    /**
     * Test that an empty MessageInfo object causes validateMessageInfo
     * to throw a runtime exception.
     * @throws Exception
     */
    @Test
    public void testValidateMessageInfoEmpty() throws Exception {
        MessageInfo info = new MessageInfo();
        try {
            messagingManager.validateMessageInfo(info);
            fail("MessageInfo should have queue name or topic name");
        } catch (RuntimeException e) {
            Assert.assertTrue(e.getMessage().contains("should be specified"));
        }
    }

    /**
     * Test that a MessageInfo object with only a queue name is acceptable to
     * validateMessageInfo
     * @throws Exception
     */
    @Test
    public void testValidateMessageInfoWithQueueName() throws Exception {
        MessageInfo info = new MessageInfo();
        String queueName = "q1";
        info.setQueueName(queueName);
        messagingManager.validateMessageInfo(info);
    }

    /**
     * Test that a MessageInfo object with only a topic name is acceptable to
     * validateMessageInfo
     * @throws Exception
     */
    @Test
    public void testValidateMessageInfoWithTopicName() throws Exception {
        MessageInfo info = new MessageInfo();
        String topicName = "q1";
        info.setTopicName(topicName);
        messagingManager.validateMessageInfo(info);
    }

    /**
     * Test that a MessageInfo object with only a topic name is acceptable to
     * validateMessageInfo
     * @throws Exception
     */
    @Test
    public void testValidateMessageInfoWithTopicNameLockCheck() throws Exception {
        MessageInfo info = new MessageInfo();
        String topicName = "q1";
        info.setTopicName(topicName);
        info.setLockCheck(new LockCheck());
        try {
            messagingManager.validateMessageInfo(info);
            fail("MessageInfo should not allow a lock check and topic name");
        } catch (RuntimeException e) {
            Assert.assertTrue(e.getMessage().contains("lock-check"));
        }
    }

    /**
     * Test that no conflict is reported when no topics and queues have
     * identical names.
     * @throws Exception
     */
    @Test
    public void testCheckForQueueAndTopicNamingConflictsNoConflict() throws Exception {
        String[] queueNames = messagingManager.getQueueNames();
        assertEquals(0, queueNames.length);

        QueueInfo qInfo = new QueueInfo();
        String queueName = "q1";
        qInfo.setName(queueName);
        ContextKey contextKey = new ContextKey(queueName, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerQueueInfo(contextKey, qInfo);
        queueNames = messagingManager.getQueueNames();
        assertEquals(1, queueNames.length);

        String[] topicNames = messagingManager.getTopicNames();
        assertEquals(0, topicNames.length);

        TopicInfo topicInfo = new TopicInfo();
        String topicName = "t1";
        topicInfo.setName(topicName);
        contextKey = new ContextKey(topicName, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerTopicInfo(contextKey, topicInfo);
        topicNames = messagingManager.getTopicNames();
        assertEquals(1, topicNames.length);

        messagingManager.checkForQueueAndTopicNamingConflicts();
        // an exception would be thrown if there were identical names
    }

    /**
     * Test that no conflict is reported when no topics and queues have
     * identical names.
     * @throws Exception
     */
    @Test
    public void testCheckForQueueAndTopicNamingConflictsConflict() throws Exception {
        String[] queueNames = messagingManager.getQueueNames();
        assertEquals(0, queueNames.length);

        QueueInfo qInfo = new QueueInfo();
        String commonName = "q1";
        qInfo.setName(commonName);
        ContextKey contextKey = new ContextKey(commonName, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerQueueInfo(contextKey, qInfo);
        queueNames = messagingManager.getQueueNames();
        assertEquals(1, queueNames.length);

        String[] topicNames = messagingManager.getTopicNames();
        assertEquals(0, topicNames.length);

        TopicInfo topicInfo = new TopicInfo();
        topicInfo.setName(commonName);
        messagingManager.registerTopicInfo(contextKey, topicInfo);
        topicNames = messagingManager.getTopicNames();
        assertEquals(1, topicNames.length);

        try {
            messagingManager.checkForQueueAndTopicNamingConflicts();
            fail("A topic and queue both had names of 'q1'");
        } catch (RuntimeException e) {
            // good - an exception would be thrown if there were identical names
        }
    }

    @Test
    public void getMessageInfo() throws Exception {
//TODO
    }

    @Test
    public void getQueueInfo() throws Exception {
//TODO
    }

    @Test
    public void getTopicInfo() throws Exception {
//TODO
    }

    /**
     * Test that registering QueueInfo objects has the expected results
     * when requesting all queue names
     * @throws Exception
     */
    @Test
    public void testGetQueueNames() throws Exception {
        String[] queueNames = messagingManager.getQueueNames();
        assertEquals(0, queueNames.length);

        QueueInfo info = new QueueInfo();
        String queueName = "q1";
        info.setName(queueName);
        ContextKey contextKey1 = new ContextKey(queueName, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerQueueInfo(contextKey1, info);
        queueNames = messagingManager.getQueueNames();
        assertEquals(1, queueNames.length);

        QueueInfo info2 = new QueueInfo();
        String queueName2 = "q2";
        info2.setName(queueName2);
        ContextKey contextKey2 = new ContextKey(queueName2, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerQueueInfo(contextKey2, info2);
        queueNames = messagingManager.getQueueNames();
        assertEquals(2, queueNames.length);

        messagingManager.unregisterQueueInfo(contextKey1);
        queueNames = messagingManager.getQueueNames();
        assertEquals(1, queueNames.length);
    }

    /**
     * Test that registering TopicInfo objects has the expected results
     * when requesting all topic names
     * @throws Exception
     */
    @Test
    public void testGetTopicNames() throws Exception {
        String[] topicNames = messagingManager.getTopicNames();
        assertEquals(0, topicNames.length);

        TopicInfo info = new TopicInfo();
        String topicName = "q1";
        info.setName(topicName);
        ContextKey contextKey1 = new ContextKey(topicName, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerTopicInfo(contextKey1, info);
        topicNames = messagingManager.getTopicNames();
        assertEquals(1, topicNames.length);

        TopicInfo info2 = new TopicInfo();
        String topicName2 = "q2";
        info2.setName(topicName2);
        ContextKey contextKey2 = new ContextKey(topicName2, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerTopicInfo(contextKey2, info2);
        topicNames = messagingManager.getTopicNames();
        assertEquals(2, topicNames.length);

        messagingManager.unregisterTopicInfo(contextKey1);
        topicNames = messagingManager.getTopicNames();
        assertEquals(1, topicNames.length);
    }

    /**
     * Tests registering and unregistering of MessageFilters.
     * @throws Exception
     */
    @Test
    public void testGetMessageFilters() throws Exception {
        List<MessageFilter> filterNames = messagingManager.getMessageFilters();
        assertEquals(0, filterNames.size());

        MessageFilter info = new MessageFilter();
        String filterName = "q1";
        info.setFilterName(filterName);
        ContextKey contextKey1 = new ContextKey(filterName, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerMessageFilter(contextKey1, info);
        filterNames = messagingManager.getMessageFilters();
        assertEquals(1, filterNames.size());

        MessageFilter info2 = new MessageFilter();
        String filterName2 = "q2";
        info2.setFilterName(filterName2);
        ContextKey contextKey2 = new ContextKey(filterName2, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerMessageFilter(contextKey2, info2);
        filterNames = messagingManager.getMessageFilters();
        assertEquals(2, filterNames.size());

        messagingManager.unregisterMessageFilter(contextKey1);
        filterNames = messagingManager.getMessageFilters();
        assertEquals(1, filterNames.size());
    }

    /**
     * Tests registration of MessageFilter objects
     * @throws Exception
     */
    @Test
    public void testRegisterMessageFilter() throws Exception {
        MessageFilter info = new MessageFilter();
        String name = "q1";
        info.setFilterName(name);
        ContextKey contextKey = new ContextKey(name, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerMessageFilter(contextKey, info);
        List<MessageFilter> filters = messagingManager.getMessageFilters();
        assertEquals(1, filters.size());
        MessageFilter filter1 = filters.get(0);
        assertEquals(name, filter1.getFilterName());
    }

    /**
     * Tests registration of MessageInfo objects
     * @throws Exception
     */
    @Test
    public void testRegisterMessageInfo() throws Exception {
        MessageInfo info = new MessageInfo();
        String queueName = "q1";
        info.setQueueName(queueName);
        ContextKey contextKey = new ContextKey(queueName, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerMessageInfo(contextKey, info);
        MessageInfo retrievedInfo =
                messagingManager.getMessageInfo(queueName);
        Assert.assertEquals(info, retrievedInfo);
    }

    /**
     * Test that the message repository is retrievable
     * @throws Exception
     */
    @Test
    public void testGetMessageRepository() throws Exception {
        assertNotNull(messagingManager.getMessageInfoRepository());
    }

    /**
     * Test that the queue info repository is retrievable
     * @throws Exception
     */
    @Test
    public void testGetQueueInfoRepository() throws Exception {
        assertNotNull(messagingManager.getQueueInfoRepository());
    }

    /**
     * Test that the topic info repository is retrievable
     * @throws Exception
     */
    @Test
    public void testGetTopicInfoRepository() throws Exception {
        assertNotNull(messagingManager.getTopicInfoRepository());
    }


    /**
     * Test that the message filter repository is retrievable
     * @throws Exception
     */
    @Test
    public void testGetMessageFilterRepository() throws Exception {
        assertNotNull(messagingManager.getMessageFilterRepository());
    }

    /**
     * Test the ability to retrieve a managed repository name
     */
    @Test
    public void testGetName() throws Exception {
        //MessageInfoRepository
        MessageInfo info = new MessageInfo();
        String queueName = "q1";
        info.setQueueName(queueName);
        ContextKey contextKey = new ContextKey(queueName, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerMessageInfo(contextKey, info);
        assertEquals("MessageInfo", messagingManager.getMessageInfoRepository().getName());

        //MessageFilterRepository
        MessageFilter filter = new MessageFilter();
        String name = "q1";
        filter.setFilterName(name);
        contextKey = new ContextKey(name, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerMessageFilter(contextKey, filter);
        assertEquals("MessageFilter", messagingManager.getMessageFilterRepository().getName());

        //TopicInfoRepository
        TopicInfo topicInfo = new TopicInfo();
        String topicName = "t1";
        topicInfo.setName(topicName);
        contextKey = new ContextKey(topicName, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerTopicInfo(contextKey, topicInfo);
        assertEquals("TopicInfo", messagingManager.getTopicInfoRepository().getName());

        //QueueInfoRepository
        QueueInfo qInfo = new QueueInfo();
        String commonName = "q1";
        qInfo.setName(commonName);
        contextKey = new ContextKey(commonName, "jaffa-messaging-config.xml", "DEF", "0-PLATFORM");
        messagingManager.registerQueueInfo(contextKey, qInfo);
        assertEquals("QueueInfo", messagingManager.getQueueInfoRepository().getName());

    }

}