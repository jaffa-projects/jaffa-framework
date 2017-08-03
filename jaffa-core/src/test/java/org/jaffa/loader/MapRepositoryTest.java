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

    MapRepository<String, String> mapRepository = new MapRepository();
    List<String> jwlContextOrder = new ArrayList<String>();
    List<String> safContextOrder = new ArrayList<String>();

    /**
     * initialize mapRepsitory with keys/values
     * initialize jwlContextOrder and safContextOrder
     */
    @Before
    public void setup() {

        //initialize map repository
        mapRepository.register("key1", "value1", "1-PRODUCT");
        mapRepository.register("key1", "value2", "2-SAF");
        mapRepository.register("key1", "value3", "0-PLATFORM");
        mapRepository.register("key2", "value6", "1-PRODUCT");
        mapRepository.register("key3", "valueX", "1-PRODUCT");
        mapRepository.register("key3", "valueY", "0-PLATFORM");

        //initialize jwl context order
        jwlContextOrder.add("2-JWL");
        jwlContextOrder.add("1-PRODUCT");
        jwlContextOrder.add("0-PLATFORM");

        //initialize saf context order
        safContextOrder.add("2-SAF");
        safContextOrder.add("1-PRODUCT");
        safContextOrder.add("0-PLATFORM");
    }

    /**
     * tests the Query() method by passing various combinations
     */
    @Test
    public void testQuery() {
        assertEquals("value1", mapRepository.query("key1", jwlContextOrder));
        assertEquals("value6", mapRepository.query("key2", jwlContextOrder));
        assertEquals("valueX", mapRepository.query("key3", jwlContextOrder));

        assertEquals("value2", mapRepository.query("key1", null));
        assertEquals("value6", mapRepository.query("key2", null));
        assertEquals("valueX", mapRepository.query("key3", null));
    }

    /**
     * tests the register() method by passing various combinations
     * and also tests whether overriding of certain keys function correctly
     */
    @Test
    public void testRegister() {
        mapRepository.register("key1", "valueA", "2-JWL");
        mapRepository.register("key2", "valueB", "2-JWL");
        assertEquals("valueA", mapRepository.query("key1", jwlContextOrder));
        assertEquals("valueB", mapRepository.query("key2", jwlContextOrder));
        assertEquals("valueX", mapRepository.query("key3", jwlContextOrder));

        assertEquals("value2", mapRepository.query("key1", safContextOrder));
        assertEquals("value6", mapRepository.query("key2", safContextOrder));
        assertEquals("valueX", mapRepository.query("key3", safContextOrder));

    }

    /**
     * tests the unregister() method by passing various combinations
     */
    @Test
    public void testUnregister() {
        mapRepository.unregister("key1", "2-JWL");
        mapRepository.unregister("key1", "2-JWL");
        assertEquals("value1", mapRepository.query("key1", jwlContextOrder));
        assertEquals("value2", mapRepository.query("key1", safContextOrder));
        assertEquals("value6", mapRepository.query("key2", safContextOrder));
    }

    /**
     * tests getAllKeys() method and does different checks
     */
    @Test
    public void testGetAllKeys() {
        assertNotNull(mapRepository.getAllKeys());
        assertEquals(3, mapRepository.getAllKeys().size());
        assertTrue(mapRepository.getAllKeys().contains("key3"));
    }

    /**
     * tests getAllValues() method and does different checks
     */
    @Test
    public void testGetAllValues() {
        assertNotNull(mapRepository.getAllValues(jwlContextOrder));
        assertEquals(3, mapRepository.getAllValues(jwlContextOrder).size());
        assertTrue(mapRepository.getAllValues(jwlContextOrder).contains("value6"));
    }
}
