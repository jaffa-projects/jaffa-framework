package org.jaffa.modules.messaging.services;

import junit.framework.*;
import org.jaffa.modules.messaging.services.configdomain.JmsConfig;
import org.jaffa.modules.messaging.services.configdomain.JndiContext;
import org.jaffa.modules.messaging.services.configdomain.MessageInfo;
import org.jaffa.modules.messaging.services.configdomain.QueueInfo;
import org.jaffa.test.junit.ContextWrapper;

/**
 *
 * @author GautamJ
 */
public class ConfigurationServiceTest extends TestCase {
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new ContextWrapper(new TestSuite(ConfigurationServiceTest.class));
    }
    
    /** Creates new instance.
     * @param name The name of the test case.
     */
    public ConfigurationServiceTest(String testName) {
        super(testName);
    }
    
    /**
     * Test of getInstance method, of class org.jaffa.modules.messaging.services.ConfigurationService.
     */
    public void testGetInstance() {
        ConfigurationService configurationService = ConfigurationService.getInstance();
        assertNotNull("Should have received an instance of the ConfigurationService", configurationService);
    }
    
    /**
     * Test of getJmsConfig method, of class org.jaffa.modules.messaging.services.ConfigurationService.
     */
    public void testGetJmsConfig() {
        ConfigurationService configurationService = ConfigurationService.getInstance();
        assertNotNull("Should have received an instance of the ConfigurationService", configurationService);
        
        JmsConfig jmsConfig = ConfigurationService.getInstance().getJmsConfig();
        assertNotNull("Should have received an instance of the JmsConfig", jmsConfig);
        assertEquals("ConnectionFactory", jmsConfig.getConnectionFactory());
        assertEquals("someUser", jmsConfig.getUser());
        assertEquals("somePassword", jmsConfig.getPassword());
        
        JndiContext jndiContext = jmsConfig.getJndiContext();
        assertNotNull("Should have received an instance of the JndiContext", jndiContext);
        assertNotNull("The JndiContext should have a List of param elements", jndiContext.getParam());
        assertEquals("The JndiContext should have a List of 3 param elements", 3, jndiContext.getParam().size());
        assertEquals("java.naming.factory.initial", jndiContext.getParam().get(0).getName());
        assertEquals("org.jnp.interfaces.NamingContextFactory", jndiContext.getParam().get(0).getValue());
        assertEquals("java.naming.factory.url.pkgs", jndiContext.getParam().get(1).getName());
        assertEquals("org.jnp.interfaces", jndiContext.getParam().get(1).getValue());
        assertEquals("java.naming.provider.url", jndiContext.getParam().get(2).getName());
        assertEquals("localhost", jndiContext.getParam().get(2).getValue());
        
    }
    
    /**
     * Test of getMessageInfo method, of class org.jaffa.modules.messaging.services.ConfigurationService.
     */
    public void testGetMessageInfo() throws ClassNotFoundException {
        ConfigurationService configurationService = ConfigurationService.getInstance();
        assertNotNull("Should have received an instance of the ConfigurationService", configurationService);
        
        MessageInfo messageInfo = null;
        messageInfo = configurationService.getMessageInfo("org.jaffa.modules.messaging.services.Example1Message");
        assertNotNull("Should have received a MessageInfo object for org.jaffa.modules.messaging.services.Example1Message", messageInfo);
        assertEquals("org.jaffa.modules.messaging.services.Example1Message", messageInfo.getDataBean());
        assertEquals("jaffa/queue1", messageInfo.getQueueName());
        assertEquals("org.jaffa.modules.messaging.services.Example1Service", messageInfo.getToClass());
        assertEquals("process", messageInfo.getToMethod());
        assertNotNull("Should have received a LockCheck object", messageInfo.getLockCheck());
        assertNotNull("Should have received a LockCheck object with a List of param elements", messageInfo.getLockCheck().getParam());
        assertEquals("Should have received a LockCheck object with a List of 2 param elements", 2, messageInfo.getLockCheck().getParam().size());
        assertEquals("jaffa.locks.global", messageInfo.getLockCheck().getParam().get(0).getName());
        assertEquals("true", messageInfo.getLockCheck().getParam().get(0).getValue());
        assertFalse(messageInfo.getLockCheck().getParam().get(0).isExpression());
        assertEquals("jaffa.locks.asset", messageInfo.getLockCheck().getParam().get(1).getName());
        assertEquals("bean.assetId", messageInfo.getLockCheck().getParam().get(1).getValue());
        assertTrue(messageInfo.getLockCheck().getParam().get(1).isExpression());
        assertNotNull("Should have received a Header object", messageInfo.getHeader());
        assertNotNull("Should have received a Header object with a List of param elements", messageInfo.getHeader().getParam());
        assertEquals("Should have received a Header object with a List of 1 param elements", 1, messageInfo.getHeader().getParam().size());
        assertEquals("jaffa.locks.asset", messageInfo.getHeader().getParam().get(0).getName());
        assertEquals("bean.assetId", messageInfo.getHeader().getParam().get(0).getValue());
        assertTrue(messageInfo.getHeader().getParam().get(0).isExpression());
    }
    
    /**
     * Test of getMessageInfo method, of class org.jaffa.modules.messaging.services.ConfigurationService.
     * This tries to read the MessageInfo for Example2Message, which is not defined.
     * Instead it expects to get the definition for Example2Message's base class BaseMessage.
     */
    public void testGetMessageInfoForChildClassWithNoDefinition() throws ClassNotFoundException {
        ConfigurationService configurationService = ConfigurationService.getInstance();
        assertNotNull("Should have received an instance of the ConfigurationService", configurationService);
        
        MessageInfo messageInfo = null;
        messageInfo = configurationService.getMessageInfo("org.jaffa.modules.messaging.services.Example2Message");
        assertNotNull("Should have received a MessageInfo object for org.jaffa.modules.messaging.services.Example2Message", messageInfo);
        assertEquals("org.jaffa.modules.messaging.services.BaseMessage", messageInfo.getDataBean());
        assertEquals("jaffa/queue", messageInfo.getQueueName());
        assertEquals("org.jaffa.modules.messaging.services.BaseService", messageInfo.getToClass());
        assertEquals("process", messageInfo.getToMethod());
        assertNotNull("Should have received a LockCheck object", messageInfo.getLockCheck());
        assertNotNull("Should have received a LockCheck object with a List of param elements", messageInfo.getLockCheck().getParam());
        assertEquals("Should have received a LockCheck object with a List of 2 param elements", 2, messageInfo.getLockCheck().getParam().size());
        assertEquals("jaffa.locks.global", messageInfo.getLockCheck().getParam().get(0).getName());
        assertEquals("true", messageInfo.getLockCheck().getParam().get(0).getValue());
        assertFalse(messageInfo.getLockCheck().getParam().get(0).isExpression());
        assertEquals("jaffa.locks.asset", messageInfo.getLockCheck().getParam().get(1).getName());
        assertEquals("bean.assetId", messageInfo.getLockCheck().getParam().get(1).getValue());
        assertTrue(messageInfo.getLockCheck().getParam().get(1).isExpression());
        assertNotNull("Should have received a Header object", messageInfo.getHeader());
        assertNotNull("Should have received a Header object with a List of param elements", messageInfo.getHeader().getParam());
        assertEquals("Should have received a Header object with a List of 1 param elements", 1, messageInfo.getHeader().getParam().size());
        assertEquals("jaffa.locks.asset", messageInfo.getHeader().getParam().get(0).getName());
        assertEquals("bean.assetId", messageInfo.getHeader().getParam().get(0).getValue());
        assertTrue(messageInfo.getHeader().getParam().get(0).isExpression());
    }
    
    /**
     * Test of getQueueNames method, of class org.jaffa.modules.messaging.services.ConfigurationService.
     */
    public void testGetQueueNames() {
        ConfigurationService configurationService = ConfigurationService.getInstance();
        assertNotNull("Should have received an instance of the ConfigurationService", configurationService);
        assertNotNull("Should have received an array of queue names", configurationService.getQueueNames());
        assertEquals("Should have received an array having 4 queue names", 4, configurationService.getQueueNames().length);
        assertEquals("jaffa/queue", configurationService.getQueueNames()[0]);
        assertEquals("jaffa/queue1", configurationService.getQueueNames()[1]);
        assertEquals("jaffa/queue2", configurationService.getQueueNames()[2]);
        assertEquals("jaffa/errorQueue", configurationService.getQueueNames()[3]);
    }
    
    /**
     * Test of getQueueInfo method, of class org.jaffa.modules.messaging.services.ConfigurationService.
     */
    public void testGetQueueInfo() {
        ConfigurationService configurationService = ConfigurationService.getInstance();
        assertNotNull("Should have received an instance of the ConfigurationService", configurationService);
        
        QueueInfo queueInfo = null;
        queueInfo = configurationService.getQueueInfo("jaffa/queue1");
        assertNotNull("Should have received a QueueInfo object for jaffa/queue1", queueInfo);
        assertEquals("jaffa/queue1", queueInfo.getName());
        assertEquals("single", queueInfo.getConsumerPolicy().value());
        assertFalse(queueInfo.isErrorQueue());
        assertNotNull("The queueInfo should have a List of display-param elements", queueInfo.getDisplayParam());
        assertEquals("The queueInfo should have a List of 2 display-param elements", 2, queueInfo.getDisplayParam().size());
        assertEquals("JMSMessageID", queueInfo.getDisplayParam().get(0).getName());
        assertEquals("Message ID", queueInfo.getDisplayParam().get(0).getLabel());
        assertEquals("JMSMessagePriority", queueInfo.getDisplayParam().get(1).getName());
        assertEquals("Message Priority", queueInfo.getDisplayParam().get(1).getLabel());
        
        queueInfo = configurationService.getQueueInfo("jaffa/queue2");
        assertNotNull("Should have received a QueueInfo object for jaffa/queue2", queueInfo);
        assertEquals("jaffa/queue2", queueInfo.getName());
        assertEquals("multi", queueInfo.getConsumerPolicy().value());
        assertFalse(queueInfo.isErrorQueue());
        assertNotNull("The queueInfo should have a List of display-param elements", queueInfo.getDisplayParam());
        assertEquals("The queueInfo should have a List of 2 display-param elements", 2, queueInfo.getDisplayParam().size());
        assertEquals("JMSMessageID", queueInfo.getDisplayParam().get(0).getName());
        assertEquals("Message ID", queueInfo.getDisplayParam().get(0).getLabel());
        assertEquals("JMSMessagePriority", queueInfo.getDisplayParam().get(1).getName());
        assertEquals("Message Priority", queueInfo.getDisplayParam().get(1).getLabel());
        
        queueInfo = configurationService.getQueueInfo("jaffa/errorQueue");
        assertNotNull("Should have received a QueueInfo object for jaffa/errorQueue", queueInfo);
        assertEquals("jaffa/errorQueue", queueInfo.getName());
        assertEquals("none", queueInfo.getConsumerPolicy().value());
        assertTrue(queueInfo.isErrorQueue());
        assertNotNull("The queueInfo should have a List of display-param elements", queueInfo.getDisplayParam());
        assertEquals("The queueInfo should have a List of 2 display-param elements", 2, queueInfo.getDisplayParam().size());
        assertEquals("JMSMessageID", queueInfo.getDisplayParam().get(0).getName());
        assertEquals("Message ID", queueInfo.getDisplayParam().get(0).getLabel());
        assertEquals("JMSMessagePriority", queueInfo.getDisplayParam().get(1).getName());
        assertEquals("Message Priority", queueInfo.getDisplayParam().get(1).getLabel());
    }
    
    /**
     * Test of class org.jaffa.modules.messaging.services.ConfigurationService.
     */
    public void testConfigurationService() {
        ConfigurationService configurationService = ConfigurationService.getInstance();
        assertNotNull("Should have received an instance of the ConfigurationService", configurationService);
    }
    
}
