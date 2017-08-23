package org.jaffa.loader;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the MapRepository class.
 */
public class MapRepositoryTest {

    MapRepository<String> mapRepository = new MapRepository();

    //initialize map repository
    ContextKey contextKey1 = new ContextKey("key1", "file1.xml", "cust-code1", "1-PRODUCT");
    ContextKey contextKey2 = new ContextKey("key2", "file2.xml", "cust-code2", "2-CUST");
    ContextKey contextKey3 = new ContextKey("key3", "file3.xml", "cust-code3", "0-PLATFORM");
    ContextKey contextKey4 = new ContextKey("key4", "file4.xml", "cust-code4", "3-CUST");
    ContextKey contextKey5 = new ContextKey("key5", "file5.xml", "cust-code5", "1-PRODUCT");
    ContextKey contextKey6 = new ContextKey("key6", "file6.xml", "cust-code6", "1-PRODUCT");
    /**
     * initialize mapRepsitory with keys/values
     * initialize jwlContextOrder and safContextOrder
     */
    @Before
    public void setup() {

        mapRepository.register(contextKey1, "value1");
        mapRepository.register(contextKey2, "value2");
        mapRepository.register(contextKey3, "value3");
        mapRepository.register(contextKey4, "value6");
        mapRepository.register(contextKey5, "valueX");
        mapRepository.register(contextKey6, "valueY");

    }

    /**
     * tests the Query() method by passing various combinations
     */
    @Test
    public void testQuery() {
        assertEquals("value1", mapRepository.query(contextKey1));
        assertEquals("value6", mapRepository.query(contextKey4));
        assertEquals("valueX", mapRepository.query(contextKey5));

        assertEquals("value2", mapRepository.query(contextKey2));
        assertEquals("value6", mapRepository.query(contextKey4));
        assertEquals("valueX", mapRepository.query(contextKey5));
    }

    /**
     * tests the register() method by passing various combinations
     * and also tests whether overriding of certain keys function correctly
     */
    @Test
    public void testRegister() {
        mapRepository.register(contextKey1, "value1");
        mapRepository.register(contextKey2, "value2");

        assertEquals("value1", mapRepository.query(contextKey1));
        assertEquals("value2", mapRepository.query(contextKey2));

    }

    /**
     * tests the unregister() method by passing various combinations
     */
    @Test
    public void testUnregister() {
        mapRepository.unregister(contextKey1);
        mapRepository.unregister(contextKey2);
        assertNull(mapRepository.query(contextKey1));
        assertNull(mapRepository.query(contextKey2));
    }

    /**
     * tests getAllKeys() method and does different checks
     */
    @Test
    public void testGetAllKeys() {
        assertNotNull(mapRepository.getAllKeys());
        assertEquals(6, mapRepository.getAllKeys().size());
        ContextKey key = new ContextKey("key3", null, "cust-code3", "0-PLATFORM");
        assertTrue(mapRepository.getAllKeys().contains(key));
    }

    /**
     * tests getAllValues() method and does different checks
     */
    @Test
    public void testGetAllValues() {
        assertNotNull(mapRepository.getAllValues());
        assertEquals(6, mapRepository.getAllValues().size());
        assertTrue(mapRepository.getAllValues().contains("value6"));
    }

    /**
     * tests order in which keys are stored
     */
    @Test
    public void testKeyOrder(){
        ContextKey platformLevelRule = new ContextKey("apprule", "file1.xml", "DEF", "0-PLATFORM");
        ContextKey productLevelRule = new ContextKey("apprule", "file2.xml", "DEF", "1-PRODUCT");
        ContextKey customerLevelRule = new ContextKey("apprule", "file3.xml", "DEF", "2-CUSTOMER");
        ContextKey addOncustomerLevelRule = new ContextKey("apprule", "file4.xml", "cust-code", "2-CUSTOMER");


        MapRepository<String> ruleRepository = new MapRepository();
        ruleRepository.register(platformLevelRule, "platform");
        ruleRepository.register(productLevelRule, "product");
        ruleRepository.register(customerLevelRule, "customer");
        ruleRepository.register(addOncustomerLevelRule, "addon-customer");

        assertEquals("customer", ruleRepository.query("apprule"));
        assertEquals(customerLevelRule, ruleRepository.findKey("apprule"));
    }
}
