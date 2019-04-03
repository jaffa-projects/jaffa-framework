package org.jaffa.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringHelperTest {

    @Test public void testStringArrayToString() {
        assertNull(StringHelper.stringArrayToString(null));
        String[] strings = new String[] {};
        assertEquals("[]", StringHelper.stringArrayToString(strings));
        strings = new String[] {"one"};
        String result = StringHelper.stringArrayToString(strings);
        assertTrue(result.contains("[one]"));
        assertFalse(result.contains(","));
        strings = new String[] {"one, two", "three"};
        result = StringHelper.stringArrayToString(strings);
        assertTrue(result.contains("[one,"));
        assertTrue(result.contains("two,"));
        assertTrue(result.contains("three]"));
    }
}