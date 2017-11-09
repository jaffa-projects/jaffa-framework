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
import org.jaffa.loader.soa.SoaEventManager;
import org.jaffa.loader.transaction.TransactionManager;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.NotFoundException;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * RepositoryJsonServiceTest - Validates the functionality of com.tapestrysolutions.monitoring.services.repos.RepositoryJsonService
 */
public class RepositoryJsonServiceTest {

    private ContextKey testKey;
    private ManagerRepositoryService managerRepositoryService;
    private RepositoryJsonService repositoryJsonService;
    private ApplicationRulesManager testManager;
    private Properties testProperties;

    /**
     * This setup function registers resources to each managed repository to produce correct retrieval results
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        managerRepositoryService = ManagerRepositoryService.getInstance();
        repositoryJsonService = new RepositoryJsonService();
        testKey = new ContextKey("testKey", "file1.xml", "DEF", "1-PRODUCT");
        testManager = new ApplicationRulesManager();
        testProperties= new Properties();

        //Must populate a repository element in order to retrieve data
        testProperties.setProperty("Property1","PropertyValue1");
        testProperties.setProperty("Property2","PropertyValue2");
        testManager.registerProperties(testKey, testProperties);

        //Add managers to ManagerRepositoryService
        managerRepositoryService.add("Properties", testManager);
        managerRepositoryService.add("ComponentManager", new ComponentManager());
        managerRepositoryService.add("NavigationManager", new NavigationManager());
        managerRepositoryService.add("BusinessFunctionManager", new BusinessFunctionManager());
        managerRepositoryService.add("RoleManager", new RoleManager());
        managerRepositoryService.add("JndiJmsManager", new JndiJmsManager());
        managerRepositoryService.add("MessagingManager", new MessagingManager());
        managerRepositoryService.add("SoaEventManager", new SoaEventManager());
        managerRepositoryService.add("TransactionManager", new TransactionManager());
    }

    /**
     * testGetRepositoryNames - Validates that a returned list of repository names can be converted to a JSON array
     */
    @Test
    public void testGetRepositoryNames() throws Exception {
        String actual = repositoryJsonService.getRepositoryNames();
        assertEquals(
                "[\"TypeInfo\",\"TransactionInfo\",\"Role\",\"ComponentDefinition\"," +
                        "\"MessageFilter\",\"MessageInfo\",\"QueueInfo\",\"TopicInfo\",\"SoaEventInfo\"," +
                        "\"BusinessFunction\",\"JmsConfig\",\"GlobalMenu\",\"Properties\"]", actual);

    }
    /**
     * testGetRepository - Returns a named repository
     */
    @Test
    public void testGetRepository() throws Exception {
        assertEquals("{\"Property2\":\"PropertyValue2\",\"Property1\":\"PropertyValue1\"}",
                repositoryJsonService.getRepository("Properties"));
    }

    /**
     * testRepositoryNameNotFound - Verifies that the proper exception is thrown when a non-existent repository
     * it queried
     * @throws Exception 404 Not Found
     */
    @Test(expected = NotFoundException.class)
    public void testRepositoryNameNotFoundException() throws Exception {
        repositoryJsonService.getRepository("Non-Existent");
    }

    /**
     * testGetRepository - Returns a named repository
     */
    @Test
    public void testGetRepositoryValue() throws Exception {
        assertEquals("\"PropertyValue1\"",
                repositoryJsonService.getRepositoryValue("Properties", "Property1"));
    }

    /**
     * testRepositoryValueNotFound - Verifies that the proper exception is thrown when a non-existent ID
     * it queried
     * @throws Exception 404 Not Found
     */
    @Test(expected = NotFoundException.class)
    public void testRepositoryValueNotFoundException() throws Exception {
        repositoryJsonService.getRepositoryValue("Properties", "Non-Existent_ID");
    }
}