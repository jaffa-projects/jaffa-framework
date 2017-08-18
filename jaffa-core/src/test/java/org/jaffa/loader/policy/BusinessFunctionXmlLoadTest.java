package org.jaffa.loader.policy;

import org.jaffa.loader.CoreLoaderConfig;
import org.jaffa.security.businessfunctionsdomain.BusinessFunction;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * BusinessFunctionXmlLoadTest - Verifies the BusinessFunctionLoad functionality.
 */
public class BusinessFunctionXmlLoadTest {

    private static AnnotationConfigApplicationContext xmlLoaderConfig = new AnnotationConfigApplicationContext(CoreLoaderConfig.class);

    /**
     * testXmlLoad - Verifies that the loading of xml files occurs correctly.
     */
    @Test
    public void testXmlLoad() {
        BusinessFunctionManager businessFunctionManager = xmlLoaderConfig.getBean(BusinessFunctionManager.class);
        assertNull(businessFunctionManager.getBusinessFunction("Jaffa.WebServices.WebServiceMaintenance.TEST", null));
        assertNotNull(businessFunctionManager.getBusinessFunction("Jaffa.WebServices.WebServiceMaintenance", null));
        assertNotNull(businessFunctionManager.getBusinessFunction("FunctionAll", null));
        assertNull(businessFunctionManager.getBusinessFunction(new String(), null));
    }

    /**
     * Verifies that we can load the xml files and read the businessFunctions from the business-functions.xml file.
     */
    @Test
    public void testGetAllBusinessFunctions() {
        BusinessFunctionManager businessFunctionManager = xmlLoaderConfig.getBean(BusinessFunctionManager.class);
        List<BusinessFunction> businessFunctionList = businessFunctionManager.getAllBusinessFunctions();
        assertEquals(2, businessFunctionList.size());
    }

    /**
     * Verifies that we can register and unregister specific business functions provided by the client.
     */
    @Test
    public void testGetBusinessFunctionRegistration() {
        BusinessFunctionManager businessFunctionManager = xmlLoaderConfig.getBean(BusinessFunctionManager.class);
        assertNull(businessFunctionManager.getBusinessFunction("Jaffa.WebServices.Test", null));
        BusinessFunction businessFunction = new BusinessFunction();
        businessFunctionManager.registerBusinessFunction("Jaffa.WebServices.Test", businessFunction, "100-Test");
        assertNotNull(businessFunctionManager.getBusinessFunction("Jaffa.WebServices.Test", Arrays.asList("100-Test")));
        businessFunctionManager.unregisterBusinessFunction("Jaffa.WebServices.Test", "100-Test");
        assertNull(businessFunctionManager.getBusinessFunction("Jaffa.WebServices.Test", null));
    }

}
