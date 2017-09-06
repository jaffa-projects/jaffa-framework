package org.jaffa.config;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test fot ApplicationRulesLoader.
 */
public class ApplicationRulesLoaderTest {


    /**
     * tests whether applications global and variation rules are loaded correctly
     */
    @Test
    public void testApplicationRulesLoading() {

        //Intiatize
        System.setProperty("server.instanceId", "MyTestServer");
        ApplicationRulesLoader applicationRulesLoader = ApplicationRulesLoader.getInstance();
        
        //Test Global Rules
        assertEquals("MyTestServer", applicationRulesLoader.getApplicationRulesGlobal().getProperty("server.instanceId"));
        assertEquals("global", applicationRulesLoader.getApplicationRulesGlobal().getProperty("org.jaffa.config.global"));
        assertEquals("false", applicationRulesLoader.getApplicationRulesGlobal().getProperty("org.jaffa.config.hidepanel"));
        assertEquals("true", applicationRulesLoader.getApplicationRulesGlobal().getProperty("jaffa.component.checkClassExistenceOnStartup"));

        //Test Variation Rules
        assertEquals("variation", applicationRulesLoader.getApplicationRulesVariation("TEST").getProperty("org.jaffa.config.variation"));
        assertEquals("true", applicationRulesLoader.getApplicationRulesVariation("TEST").getProperty("org.jaffa.config.hidemaintenancepanel"));
        assertEquals("true", applicationRulesLoader.getApplicationRulesVariation("TEST").getProperty("org.jaffa.config.hidepanel"));

    }
}
