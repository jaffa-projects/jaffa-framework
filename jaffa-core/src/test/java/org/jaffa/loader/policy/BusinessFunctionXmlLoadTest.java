package org.jaffa.loader.policy;

import org.jaffa.loader.ContextKey;
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
        assertNull(businessFunctionManager.getBusinessFunction("Jaffa.WebServices.WebServiceMaintenance.TEST"));
        assertNotNull(businessFunctionManager.getBusinessFunction("Jaffa.WebServices.WebServiceMaintenance"));
        assertNotNull(businessFunctionManager.getBusinessFunction("FunctionAll"));
        assertNull(businessFunctionManager.getBusinessFunction(new String()));
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
        assertNull(businessFunctionManager.getBusinessFunction("Jaffa.WebServices.Test"));
        BusinessFunction businessFunction = new BusinessFunction();
        ContextKey key = new ContextKey("Jaffa.WebServices.Test", "business-functions.xml", "DEF", "100-Highest");
        businessFunctionManager.registerBusinessFunction(key, businessFunction);
        assertNotNull(businessFunctionManager.getBusinessFunction("Jaffa.WebServices.Test"));
        businessFunctionManager.unregisterBusinessFunction(key);
        assertNull(businessFunctionManager.getBusinessFunction("Jaffa.WebServices.Test"));
    }

}
