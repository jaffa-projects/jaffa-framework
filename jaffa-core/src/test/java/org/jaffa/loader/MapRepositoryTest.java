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
    public void setup(){

        //initialize map repository
        mapRepository.register("key1", "value1", "saf");
        mapRepository.register("key1", "value2", "jwl");
        mapRepository.register("key1", "value3", "default");
        mapRepository.register("key2", "value6", "saf");
        mapRepository.register("key3", "valueX", "saf");
        mapRepository.register("key3", "valueY", "default");

        //initialize jwl context order
        jwlContextOrder.add("jwl");
        jwlContextOrder.add("default");

        //initialize saf context order
        safContextOrder.add("saf");
        safContextOrder.add("default");
    }

    /**
     * tests the Query() method by passing various combinations
     */
    @Test
    public void testQuery(){
        assertEquals("value2", mapRepository.query("key1", jwlContextOrder)) ;
        assertEquals(null, mapRepository.query("key2", jwlContextOrder)) ;
        assertEquals("valueY", mapRepository.query("key3", jwlContextOrder)) ;

        assertEquals("value1", mapRepository.query("key1", safContextOrder)) ;
        assertEquals("value6", mapRepository.query("key2", safContextOrder)) ;
        assertEquals("valueX", mapRepository.query("key3", safContextOrder)) ;
    }

    /**
     * tests the register() method by passing various combinations
     * and also tests whether overriding of certain keys function correctly
     */
    @Test
    public void testRegister(){
        mapRepository.register("key1", "valueA", "jwl");
        mapRepository.register("key2", "valueB", "jwl");
        assertEquals("valueA", mapRepository.query("key1", jwlContextOrder)) ;
        assertEquals("valueB", mapRepository.query("key2", jwlContextOrder)) ;
        assertEquals("valueY", mapRepository.query("key3", jwlContextOrder)) ;

        assertEquals("value1", mapRepository.query("key1", safContextOrder)) ;
        assertEquals("value6", mapRepository.query("key2", safContextOrder)) ;
        assertEquals("valueX", mapRepository.query("key3", safContextOrder)) ;

    }

    /**
     * tests the unregister() method by passing various combinations
     */
    @Test
    public void testUnregister(){
        mapRepository.unregister("key1", "jwl");
        mapRepository.unregister("key1", "saf");
        assertEquals("value3", mapRepository.query("key1", jwlContextOrder)) ;
        assertEquals("value3", mapRepository.query("key1", safContextOrder)) ;
        assertEquals("value6", mapRepository.query("key2", safContextOrder)) ;
    }

    /**
     * tests getAllKeys() method and does different checks
     */
    @Test
    public void testGetAllKeys(){
        assertNotNull(mapRepository.getAllKeys());
        assertEquals(3, mapRepository.getAllKeys().size());
        assertTrue(mapRepository.getAllKeys().contains("key3"));
    }

    /**
     * tests getAllValues() method and does different checks
     */
    @Test
    public void testGetAllValues(){
        assertNotNull(mapRepository.getAllValues(jwlContextOrder));
        assertEquals(2, mapRepository.getAllValues(jwlContextOrder).size());
        assertFalse(mapRepository.getAllValues(jwlContextOrder).contains("value6"));
    }
}
