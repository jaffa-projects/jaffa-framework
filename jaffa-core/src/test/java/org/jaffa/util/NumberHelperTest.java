package org.jaffa.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class NumberHelperTest {

    @Test
    public void round() {
        Double rounded1 = NumberHelper.round(3.55, 1);
        assertEquals("equals", new Double(3.6), rounded1);
        Double rounded2 = NumberHelper.round(3.555, 2);
        assertEquals("equals", new Double(3.56), rounded2);
        Double rounded3 = NumberHelper.round(null, 2);
        assertNull(rounded3);
        Double rounded4 = NumberHelper.round(3d, 2);
        assertEquals("equals", new Double(3), rounded4);
    }
}