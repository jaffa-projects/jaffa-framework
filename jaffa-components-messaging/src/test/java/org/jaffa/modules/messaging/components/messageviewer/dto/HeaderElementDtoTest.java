package org.jaffa.modules.messaging.components.messageviewer.dto;

import org.junit.Test;

import static org.junit.Assert.*;

public class HeaderElementDtoTest {

    @Test public void getNameTest() {
        HeaderElementDto testObject = new HeaderElementDto();
        testObject.setName("bob");
        assertEquals("bob", testObject.getName());
    }
}
