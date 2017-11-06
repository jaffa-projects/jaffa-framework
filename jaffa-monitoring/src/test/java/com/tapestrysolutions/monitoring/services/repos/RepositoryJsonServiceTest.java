package com.tapestrysolutions.monitoring.services.repos;

import org.jaffa.loader.ContextKey;
import org.jaffa.loader.ManagerRepositoryService;
import org.jaffa.loader.components.ComponentManager;
import org.jaffa.loader.config.ApplicationRulesManager;
import org.jaffa.loader.messaging.JndiJmsManager;
import org.jaffa.loader.messaging.MessagingManager;
import org.jaffa.loader.navigation.NavigationManager;
import org.jaffa.loader.policy.BusinessFunctionManager;
import org.jaffa.loader.policy.RoleManager;
import org.jaffa.loader.scheduler.SchedulerManager;
import org.jaffa.loader.soa.SoaEventManager;
import org.jaffa.loader.transaction.TransactionManager;
import org.jaffa.modules.scheduler.services.configdomain.Task;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * RepositoryJsonServiceTest - Validates the functionality of com.tapestrysolutions.monitoring.services.repos.RepositoryJsonService
 */
public class RepositoryJsonServiceTest {

    private ContextKey testKey;
    private ManagerRepositoryService managerRepositoryService;
    private RepositoryJsonService repositoryJsonService;
    private SchedulerManager testManager;
    private Task testTask;

    /**
     * This setup function registers resources to each managed repository to produce correct retrieval results
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        managerRepositoryService = ManagerRepositoryService.getInstance();
        repositoryJsonService = new RepositoryJsonService();
        testKey = new ContextKey("testKey", "file1.xml", "DEF", "1-PRODUCT");
        testManager = new SchedulerManager();
        testTask = new Task();

        //Must populate a repository element in order to retrieve data
        testTask.setAutoCreateDataBean(true);
        testTask.setDataBean("DataBeanTest");
        testTask.setType("TestType");
        testManager.registerSchedulerTask(testKey, testTask);

        //Add managers to ManagerRepositoryService
        managerRepositoryService.add("Task", testManager);
        managerRepositoryService.add("ComponentManager", new ComponentManager());
        managerRepositoryService.add("ApplicationRulesManager", new ApplicationRulesManager());
        managerRepositoryService.add("NavigationManager", new NavigationManager());
        managerRepositoryService.add("BusinessFunctionManager", new BusinessFunctionManager());
        managerRepositoryService.add("RoleManager", new RoleManager());
        managerRepositoryService.add("JndiJmsManager", new JndiJmsManager());
        managerRepositoryService.add("MessagingManager", new MessagingManager());
        managerRepositoryService.add("SoaEventManager", new SoaEventManager());
        managerRepositoryService.add("TransactionManager", new TransactionManager());
    }

    /**
     * getRepositoryNames - Validates that a returned list of repository names can be converted to a JSON array
     */
    @Test
    public void getRepositoryNames() throws Exception {
        String actual = repositoryJsonService.getRepositoryNames();
        assertEquals(
                "[\"TypeInfo\",\"TransactionInfo\",\"Properties\",\"Role\",\"ComponentDefinition\"," +
                        "\"Task\",\"MessageFilter\",\"MessageInfo\",\"QueueInfo\",\"TopicInfo\",\"SoaEventInfo\"," +
                        "\"BusinessFunction\",\"JmsConfig\",\"GlobalMenu\"]", actual);

    }

    /**
     * getRepository - Returns a named repository
     */
    @Test
    public void getRepository() throws Exception {
        assertEquals("{\"testKey\":{\"autoCreateDataBean\":true,\"dataBean\":\"DataBeanTest\",\"type\":\"TestType\"}}",
                repositoryJsonService.getRepository("Task"));
    }

    /**
     * getRepository - Returns a named repository
     */
    @Test
    public void getRepositoryValue() throws Exception {
        assertEquals("{\"autoCreateDataBean\":true,\"dataBean\":\"DataBeanTest\"," +
                "\"type\":\"TestType\"}", repositoryJsonService.getRepositoryValue("Task", "testKey"));
    }
}