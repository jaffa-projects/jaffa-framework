package org.jaffa.loader.drools;

import org.jaffa.security.VariationContext;
import org.jaffa.soa.rules.RuleAgentKey;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.jaffa.loader.drools.DroolsManager.DROOLS_FILE_DIRECTORY;
import static org.junit.Assert.*;

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

        String systemProp = System.getProperty("java.io.tmpdir");

        //verify
        assertEquals(" " +Paths.get(DROOLS_FILE_DIRECTORY+"default"+File.separator+"testservice"+File.separator+"DEF"+File.separator+"TestService.drl").toString(), droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)).toString());
        //verify
        assertEquals(" " + Paths.get(DROOLS_FILE_DIRECTORY+"default"+File.separator+"testservice"+File.separator+"Customer"+File.separator+"TestService.drl").toString(), droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, "Customer")).toString());
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
     * tests the unregistered method of DroolsManager throws Exception or not
     * @throws Exception
     */
    @Test
    public void testUnRegisterDroolIndexOutOfBoundException() throws Exception{
        Resource resource = pathMatchingResourcePatternResolver.getResource(CORE_DRL_PATH);
        droolsManager.registerDrool(resource, VariationContext.DEFAULT_VARIATION);

        droolsManager.registerDrool(resource, VariationContext.DEFAULT_VARIATION);
        //verify
        assertNotNull(droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
        //Test with non existant variant
        droolsManager.unRegisterDrool(resource, "Customer");
        //register with customer variant
        droolsManager.registerDrool(resource, "Customer");
        //verify
        assertNotNull(droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
        droolsManager.unRegisterDrool(resource, VariationContext.DEFAULT_VARIATION);
        //call one more time, to see it should not throw ny exception
        droolsManager.unRegisterDrool(resource, VariationContext.DEFAULT_VARIATION);
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
        droolsManager.registerDrool(resource, VariationContext.NULL_VARIATION);

        Resource customerResource = pathMatchingResourcePatternResolver.getResource(CUSTOMER_DRL_PATH);
        droolsManager.registerDrool(customerResource, "Customer");
        droolsManager.createAgents();

        //verify
        assertNotNull(droolsManager.getRuleAgents().get(new RuleAgentKey(SERVICE_NAME, VariationContext.NULL_VARIATION)));
        assertNull(droolsManager.getRuleAgents().get(new RuleAgentKey(SERVICE_NAME, VariationContext.DEFAULT_VARIATION)));
        assertEquals(" " + Paths.get(DROOLS_FILE_DIRECTORY+"default"+File.separator+"testservice"+File.separator+"Customer"+File.separator+"TestService.drl").toString()+ " " + Paths.get(DROOLS_FILE_DIRECTORY+"default"+File.separator+"testservice"+File.separator+"TestService.drl").toString(), droolsManager.getDroolsFiles().get(new RuleAgentKey(SERVICE_NAME, "Customer")).toString());
    }

    /**
     * tests the service Name retrieval
     */
    @Test
    public void getServiceNameTest(){
        //verify
        assertEquals("Service Name Method Fail", SERVICE_NAME, droolsManager.getServiceName(CORE_DRL_PATH));
        assertEquals("Service Name Method Fail", "workorderservice" , droolsManager.getServiceName(".."+File.separator+"Documents"+File.separator+"workorderservice"+File.separator+"Desktop.test.drl"));
        assertNull(droolsManager.getServiceName(null));
    }

    /**
     * tests the Module Name retrieval
     */
    @Test
    public void getModuleNameTest(){
        //verify
        assertEquals("Service Name Method Fail", "goldesp-mils-17.2.0-SNAPSHOT" , droolsManager.getModuleName("file:"+File.separator+"C:"+File.separator+"apache-tomcat-8.5.16"+File.separator+"webapps"+File.separator+"goldesp-platform-1.0.0-SNAPSHOT"+File.separator+"WEB-INF"+File.separator+"lib"+File.separator+"goldesp-mils-17.2.0-SNAPSHOT.jar!"+File.separator+"META-INF"+File.separator+"rules"+File.separator+"jaffa"+File.separator+"soa"+File.separator+"soaeventservice"+File.separator+"InboundTransactionService.drl"));
        assertEquals("default",droolsManager.getModuleName(null));
    }

    /**
     *
     */
    @Test
    public void clearDroolsDirectoryTest(){
        droolsManager.clearDroolsDirectory();
        assertFalse("Directory still exists",
                Files.exists(Paths.get(DROOLS_FILE_DIRECTORY)));
    }

}
