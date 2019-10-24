/* Generated test code. */

package org.jaffa.components.attachment.components.attachmentmaintenance.dto;

import static org.junit.Assert.*;

import org.jaffa.components.dto.HeaderDto;
import org.jaffa.datatypes.DateTime;
import org.junit.Test;

/**
 * Generated test code for AttachmentMaintenanceDeleteInDto.
 * @author kcassell
 */
public class AttachmentMaintenanceDeleteInDtoTapGenTest {

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceDeleteInDto#getHeaderDto()}.
	 */
	@Test
	public void testGetHeaderDto() {
	    AttachmentMaintenanceDeleteInDto testObject = new AttachmentMaintenanceDeleteInDto();
	    HeaderDto setValue = new HeaderDto();
	    testObject.setHeaderDto(setValue);
	    HeaderDto getValue = testObject.getHeaderDto();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceDeleteInDto#getPerformDirtyReadCheck()}.
	 */
	@Test
	public void testGetPerformDirtyReadCheck() {
	    AttachmentMaintenanceDeleteInDto testObject = new AttachmentMaintenanceDeleteInDto();
	    Boolean setValue = false;
	    testObject.setPerformDirtyReadCheck(setValue);
	    Boolean getValue = testObject.getPerformDirtyReadCheck();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceDeleteInDto#getAttachmentId()}.
	 */
	@Test
	public void testGetAttachmentId() {
	    AttachmentMaintenanceDeleteInDto testObject = new AttachmentMaintenanceDeleteInDto();
	    java.lang.String setValue = "testString";
	    testObject.setAttachmentId(setValue);
	    java.lang.String getValue = testObject.getAttachmentId();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceDeleteInDto#getLastChangedOn()}.
	 */
	@Test
	public void testGetLastChangedOn() {
	    AttachmentMaintenanceDeleteInDto testObject = new AttachmentMaintenanceDeleteInDto();
	    org.jaffa.datatypes.DateTime setValue = new org.jaffa.datatypes.DateTime();
	    testObject.setLastChangedOn(setValue);
	    org.jaffa.datatypes.DateTime getValue = testObject.getLastChangedOn();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceDeleteInDto#toString()}.
	 */
	@Test
	public void testToString() {
	    AttachmentMaintenanceDeleteInDto testObject = new AttachmentMaintenanceDeleteInDto();
	    String result = testObject.toString();
	    assertNotNull(result);
	    assertTrue(result.contains("AttachmentMaintenanceDeleteInDto"));
	}

}
