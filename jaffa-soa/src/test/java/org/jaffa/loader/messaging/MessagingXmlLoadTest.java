package org.jaffa.loader.messaging;

import org.jaffa.loader.SoaLoaderConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * MessagingXmlLoadTest - Verifies the Messaging beans can be loaded from the MessageManager implementation.
 */
public class MessagingXmlLoadTest {


    private static AnnotationConfigApplicationContext xmlLoaderConfig =
            new AnnotationConfigApplicationContext(SoaLoaderConfig.class);

    /**
     * Test load the XML config for the scheduler task repository.
     */
    @Test
    public void testXmlLoad(){
        MessagingManager messagingManager = xmlLoaderConfig.getBean(MessagingManager.class);

        assertNull(messagingManager.getMessageInfo("org.jaffa.scheduler.tester.TestMessageSingleton"));
        assertNotNull(messagingManager.getMessageInfo("org.jaffa.modules.messaging.services.BaseMessage"));
        assertNotNull(messagingManager.getMessageInfo("org.jaffa.modules.messaging.services.Example1Message"));
        assertNull(messagingManager.getMessageInfo(""));

        assertNull(messagingManager.getQueueInfo("jaffa/junkQueue"));
        assertNotNull(messagingManager.getQueueInfo("jaffa/queue"));
        assertNotNull(messagingManager.getQueueInfo("jaffa/queue1"));
        assertNotNull(messagingManager.getQueueInfo("jaffa/queue2"));
        assertNotNull(messagingManager.getQueueInfo("jaffa/errorQueue"));
        assertNull(messagingManager.getQueueInfo(""));

        assertNull(messagingManager.getTopicInfo("topic info"));
        assertNotNull(messagingManager.getTopicInfo("topic1"));
        assertNotNull(messagingManager.getTopicInfo("topic2"));
        assertNull(messagingManager.getTopicInfo(""));
    }

    /**
     * Verify that the MessagingManager can get all queue names registered in the repository.
     */
    @Test
    public void testGetQueueNames() {
        MessagingManager messagingManager = xmlLoaderConfig.getBean(MessagingManager.class);

        assertEquals(4, messagingManager.getQueueNames().length);
        List<String> queueNames = Arrays.asList("jaffa/queue","jaffa/queue1","jaffa/queue2","jaffa/errorQueue");
        String[] allQueueNames = messagingManager.getQueueNames();
        assertTrue(queueNames.contains(allQueueNames[0]));
        assertTrue(queueNames.contains(allQueueNames[1]));
        assertTrue(queueNames.contains(allQueueNames[2]));
        assertTrue(queueNames.contains(allQueueNames[3]));
    }

    /**
     * Verify that the MessagingManager can get all topic names registered in the repository.
     */
    @Test
    public void testGetTopicNames() {
        MessagingManager messagingManager = xmlLoaderConfig.getBean(MessagingManager.class);

        assertEquals(2, messagingManager.getTopicNames().length);
        List<String> schedulerTasks = Arrays.asList("topic1","topic2");
        String[] topicNames = messagingManager.getTopicNames();
        assertTrue(schedulerTasks.contains(topicNames[0]));
        assertTrue(schedulerTasks.contains(topicNames[1]));
    }
}
