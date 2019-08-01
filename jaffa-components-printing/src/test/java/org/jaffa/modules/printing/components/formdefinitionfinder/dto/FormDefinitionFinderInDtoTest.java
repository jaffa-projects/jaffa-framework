package org.jaffa.modules.printing.components.formdefinitionfinder.dto;

import org.junit.Test;

import static org.junit.Assert.*;

public class FormDefinitionFinderInDtoTest {

    @Test public void getHeaderDtoTest() {
        FormDefinitionFinderInDto testObject = new FormDefinitionFinderInDto();
        testObject.setHeaderDto(null);
        assertNull(testObject.getHeaderDto());
    }
}
