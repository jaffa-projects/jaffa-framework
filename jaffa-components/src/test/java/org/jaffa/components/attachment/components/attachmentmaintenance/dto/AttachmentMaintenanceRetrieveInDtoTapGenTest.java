/* Generated test code. */

package org.jaffa.components.attachment.components.attachmentmaintenance.dto;

import static org.junit.Assert.*;

import org.jaffa.components.dto.HeaderDto;
import org.junit.Test;

/**
 * Generated test code for AttachmentMaintenanceRetrieveInDto.
 * @author kcassell
 */
public class AttachmentMaintenanceRetrieveInDtoTapGenTest {

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceRetrieveInDto#getHeaderDto()}.
	 */
	@Test
	public void testGetHeaderDto() {
	    AttachmentMaintenanceRetrieveInDto testObject = new AttachmentMaintenanceRetrieveInDto();
	    HeaderDto setValue = new HeaderDto();
	    testObject.setHeaderDto(setValue);
	    HeaderDto getValue = testObject.getHeaderDto();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceRetrieveInDto#getAttachmentId()}.
	 */
	@Test
	public void testGetAttachmentId() {
	    AttachmentMaintenanceRetrieveInDto testObject = new AttachmentMaintenanceRetrieveInDto();
	    java.lang.String setValue = "testString";
	    testObject.setAttachmentId(setValue);
	    java.lang.String getValue = testObject.getAttachmentId();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceRetrieveInDto#toString()}.
	 */
	@Test
	public void testToString() {
	    AttachmentMaintenanceRetrieveInDto testObject = new AttachmentMaintenanceRetrieveInDto();
	    String result = testObject.toString();
	    assertNotNull(result);
	    assertTrue(result.contains("AttachmentMaintenanceRetrieveInDto"));
	}

}
