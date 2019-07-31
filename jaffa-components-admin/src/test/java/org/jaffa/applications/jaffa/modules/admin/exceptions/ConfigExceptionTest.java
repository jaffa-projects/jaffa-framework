package org.jaffa.applications.jaffa.modules.admin.exceptions;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigExceptionTest {

    @Test public void testConstructor1() {
        ConfigException exc = new ConfigException("theProp");
        assertTrue(exc.toString().contains("theProp"));
    }
}
