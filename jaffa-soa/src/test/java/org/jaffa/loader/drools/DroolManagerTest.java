package org.jaffa.loader.drools;

import org.jaffa.security.VariationContext;
import org.jaffa.soa.rules.RuleAgentKey;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Test for Drools manager.
 */
public class DroolManagerTest {

    DroolManager droolManager = new DroolManager();
    PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    private static final String SERVICE_NAME = "testservice";
    private static final String RESOURCE_PATH = "classpath:META-INF/rules/testservice/testservice.drl";

    /**
     * tests the registering of drool method
     * @throws Exception
     */
    @Test
    public void testRegisterDrool() throws Exception{
        Resource resource = pathMatchingResourcePatternResolver.getResource(RESOURCE_PATH);
        //Test
        droolManager.registerDrool(resource, VariationContext.DEFAULT_VARIATION);

        //verify
        assertNotNull(droolManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
    }

    /**
     * tests the unregistered method of DroolManager
     * @throws Exception
     */
    @Test
    public void testUnRegisterDrool() throws Exception{
        Resource resource = pathMatchingResourcePatternResolver.getResource(RESOURCE_PATH);
        //test
        droolManager.registerDrool(resource, VariationContext.DEFAULT_VARIATION);

        //verify
        assertNotNull(droolManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));

        //Test
        droolManager.unRegisterDrool(resource, "SAF");

        //verify
        assertNotNull(droolManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));

        droolManager.unRegisterDrool(resource, VariationContext.DEFAULT_VARIATION);

        //verify
        assertEquals(" ", droolManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)).toString());

    }

    /**
     * tests the refreshAgent method of DroolManager
     * @throws Exception
     */
    @Test
    public void refreshAgentTest()throws  Exception{
        droolManager.refreshAgent(SERVICE_NAME);

        //verify
        assertNull(droolManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));

        Resource resource = pathMatchingResourcePatternResolver.getResource(RESOURCE_PATH);
        //test
        droolManager.registerDrool(resource, VariationContext.DEFAULT_VARIATION);

        //verify
        assertNotNull(droolManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));

        //test
        droolManager.createAgents();

        //verify
        assertNotNull(droolManager.getRuleAgents().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));

        droolManager.refreshAgent(SERVICE_NAME);

        //verify
        assertNull(droolManager.getRuleAgents().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
    }

    /**
     * tests the create Agents Method of DroolManager
     * @throws Exception
     */
    @Test
    public void createAgentsTest() throws  Exception{
        Resource resource = pathMatchingResourcePatternResolver.getResource(RESOURCE_PATH);
        //test
        droolManager.registerDrool(resource, VariationContext.DEFAULT_VARIATION);

        droolManager.createAgents();

        //verify
        assertNotNull(droolManager.getRuleAgents().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
    }

    /**
     * tests the service Name retrieval
     */
    @Test
    public void getServiceNameTest(){
        //verify
        assertEquals("Service Name Method Fail", SERVICE_NAME, droolManager.getServiceName(RESOURCE_PATH));

        assertNull(droolManager.getServiceName(null));
    }
}
