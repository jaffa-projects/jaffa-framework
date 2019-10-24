/* Generated test code. */

package org.jaffa.components.attachment.components.attachmentmaintenance.dto;

import static org.junit.Assert.*;

import org.jaffa.datatypes.DateTime;
import org.junit.Test;

/**
 * Generated test code for AttachmentMaintenanceUpdateInDto.
 * @author kcassell
 */
public class AttachmentMaintenanceUpdateInDtoTapGenTest {

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceUpdateInDto#getPerformDirtyReadCheck()}.
	 */
	@Test
	public void testGetPerformDirtyReadCheck() {
	    AttachmentMaintenanceUpdateInDto testObject = new AttachmentMaintenanceUpdateInDto();
	    Boolean setValue = false;
	    testObject.setPerformDirtyReadCheck(setValue);
	    Boolean getValue = testObject.getPerformDirtyReadCheck();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceUpdateInDto#getLastChangedOn()}.
	 */
	@Test
	public void testGetLastChangedOn() {
	    AttachmentMaintenanceUpdateInDto testObject = new AttachmentMaintenanceUpdateInDto();
	    org.jaffa.datatypes.DateTime setValue = new org.jaffa.datatypes.DateTime();
	    testObject.setLastChangedOn(setValue);
	    org.jaffa.datatypes.DateTime getValue = testObject.getLastChangedOn();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceUpdateInDto#toString()}.
	 */
	@Test
	public void testToString() {
	    AttachmentMaintenanceUpdateInDto testObject = new AttachmentMaintenanceUpdateInDto();
	    String result = testObject.toString();
	    assertNotNull(result);
	    assertTrue(result.contains("AttachmentMaintenanceUpdateInDto"));
	}

}
