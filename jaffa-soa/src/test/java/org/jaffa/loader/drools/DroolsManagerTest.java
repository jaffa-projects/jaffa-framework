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
public class DroolsManagerTest {

    DroolsManager droolsManager = new DroolsManager();
    PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    private static final String SERVICE_NAME = "testservice";
    private static final String CORE_DRL_PATH = "classpath:META-INF/rules/testservice/TestService.drl";
    private static final String CUSTOMER_DRL_PATH = "classpath:META-INF/rules/testservice/TestService.drl";

    /**
     * tests the registering of drool method
     * @throws Exception
     */
    @Test
    public void testRegisterDrool() throws Exception{
        Resource resource = pathMatchingResourcePatternResolver.getResource(CORE_DRL_PATH);
        //Test
        droolsManager.registerDrool(resource, VariationContext.DEFAULT_VARIATION);
        //verify
        assertNotNull(droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
    }

    /**
     * tests the registering of drool method
     * @throws Exception
     */
    @Test
    public void testRegisterMultipleDrool() throws Exception{
        Resource resource = pathMatchingResourcePatternResolver.getResource(CORE_DRL_PATH);
        droolsManager.registerDrool(resource, VariationContext.DEFAULT_VARIATION);

        Resource customerResource = pathMatchingResourcePatternResolver.getResource(CUSTOMER_DRL_PATH);
        droolsManager.registerDrool(customerResource, "Customer");

        //verify
        assertEquals(" ..\\data\\rules\\default\\testservice\\DEF\\TestService.drl", droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)).toString());
        //verify
        assertEquals(" ..\\data\\rules\\default\\testservice\\Customer\\TestService.drl", droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, "Customer")).toString());
    }

    /**
     * tests the unregistered method of DroolsManager
     * @throws Exception
     */
    @Test
    public void testUnRegisterDrool() throws Exception{
        Resource resource = pathMatchingResourcePatternResolver.getResource(CORE_DRL_PATH);
        droolsManager.registerDrool(resource, VariationContext.DEFAULT_VARIATION);
        //verify
        assertNotNull(droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
        //Test
        droolsManager.unRegisterDrool(resource, "Customer");
        //verify
        assertNotNull(droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
        droolsManager.unRegisterDrool(resource, VariationContext.DEFAULT_VARIATION);
        //verify
        assertEquals("", droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)).toString().trim());
    }

    /**
     * tests the refreshAgent method of DroolsManager
     * @throws Exception
     */
    @Test
    public void refreshAgentTest()throws  Exception{
        droolsManager.refreshAgent(SERVICE_NAME);
        //verify
        assertNull(droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
        Resource resource = pathMatchingResourcePatternResolver.getResource(CORE_DRL_PATH);
        droolsManager.registerDrool(resource, VariationContext.DEFAULT_VARIATION);
       //verify
        assertNotNull(droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
        //test
        droolsManager.createAgents();
        //verify
        assertNotNull(droolsManager.getRuleAgents().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
        droolsManager.refreshAgent(SERVICE_NAME);
        //verify
        assertNull(droolsManager.getRuleAgents().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
    }

    /**
     * tests the create Agents Method of DroolsManager
     * @throws Exception
     */
    @Test
    public void createAgentsTest() throws  Exception{
        Resource resource = pathMatchingResourcePatternResolver.getResource(CORE_DRL_PATH);
        droolsManager.registerDrool(resource, VariationContext.DEFAULT_VARIATION);

        Resource customerResource = pathMatchingResourcePatternResolver.getResource(CUSTOMER_DRL_PATH);
        droolsManager.registerDrool(customerResource, "Customer");
        droolsManager.createAgents();

        //verify
        assertNotNull(droolsManager.getRuleAgents().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
        assertEquals(" ..\\data\\rules\\default\\testservice\\Customer\\TestService.drl ..\\data\\rules\\default\\testservice\\DEF\\TestService.drl", droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, "Customer")).toString());
    }

    /**
     * tests the service Name retrieval
     */
    @Test
    public void getServiceNameTest(){
        //verify
        assertEquals("Service Name Method Fail", SERVICE_NAME, droolsManager.getServiceName(CORE_DRL_PATH));
        assertEquals("Service Name Method Fail", "workorderservice" , droolsManager.getServiceName("..\\Documents\\workorderservice\\Desktop.test.drl"));
        assertNull(droolsManager.getServiceName(null));
    }

    /**
     * tests the Module Name retrieval
     */
    @Test
    public void getModuleNameTest(){
        //verify
        assertEquals("Service Name Method Fail", "goldesp-mils-17.2.0-SNAPSHOT" , droolsManager.getModuleName("file:\\C:\\apache-tomcat-8.5.16\\webapps\\goldesp-platform-1.0.0-SNAPSHOT\\WEB-INF\\lib\\goldesp-mils-17.2.0-SNAPSHOT.jar!\\META-INF\\rules\\jaffa\\soa\\soaeventservice\\InboundTransactionService.drl"));
        assertEquals("default",droolsManager.getModuleName(null));
    }
}
