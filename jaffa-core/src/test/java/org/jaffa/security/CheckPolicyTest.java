package org.jaffa.security;

import org.jaffa.loader.XmlLoaderConfig;
import org.jaffa.loader.policy.BusinessFunctionManager;
import org.jaffa.loader.policy.RoleManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests the CheckPolicy class for loading business-functions from resources or META-INF
 */
public class CheckPolicyTest {

    CheckPolicy checkPolicy;
    PolicyCache policyCache;

    private static AnnotationConfigApplicationContext xmlLoaderConfig = new AnnotationConfigApplicationContext(XmlLoaderConfig.class);

    /**
     * setting the up objects/properties before a Test is run
     * @throws Exception
     */
    @Before
    public void setup(){
        //initialize
        BusinessFunctionManager businessFunctionManager = xmlLoaderConfig.getBean(BusinessFunctionManager.class);
        checkPolicy = new CheckPolicy();
        checkPolicy.setBusinessFunctionManager(businessFunctionManager);
        RoleManager roleManager = xmlLoaderConfig.getBean(RoleManager.class);
        policyCache.setRoleManager(roleManager);
    }


    /**
     * Tests whether the business functions are loaded correctly from
     *  "/META-INF/business-functions.xml", when there is
     *  no business-functions.xml in resources/business-functions.xml.
     * @throws Exception
     */
    @Test
    public void testCheckPolicy() throws ServletException {

        //test
        checkPolicy.init(null);

        //verify
        Map compErrors = checkPolicy.getCompErrors();
        assertNotNull(compErrors.size());
        assertNotNull(compErrors.get("Jaffa.UnitTest.TestFunctionFinder"));
        assertNotNull(compErrors.get("Jaffa.UnitTest.TestFunctionViewer"));

        Map roleErrors = checkPolicy.getRoleErrors();
        assertNotNull(roleErrors.size());
        assertNull(roleErrors.get("CLERK"));
        assertNotNull(roleErrors.get("MANAGER"));
        assertNotNull(roleErrors.get("SUPERVISOR"));
    }
}
