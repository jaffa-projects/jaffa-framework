package org.jaffa.modules.scheduler.components.taskfinder.dto;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskFinderOutDtoTest {

    @Test public void getRowsTest() {
        TaskFinderOutDto testObject = new TaskFinderOutDto();
        testObject.setRows(null);
        assertNull(testObject.getRows());
    }
}
