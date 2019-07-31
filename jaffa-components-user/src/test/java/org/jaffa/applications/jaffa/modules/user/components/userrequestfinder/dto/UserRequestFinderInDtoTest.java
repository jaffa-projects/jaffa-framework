package org.jaffa.applications.jaffa.modules.user.components.userrequestfinder.dto;

import static org.junit.Assert.*;

public class UserRequestFinderInDtoTest {

    @org.junit.Test public void getHeaderDtoTest() {
        UserRequestFinderInDto testObject = new UserRequestFinderInDto();
        testObject.setHeaderDto(null);
        assertNull(testObject.getHeaderDto());
    }
}
