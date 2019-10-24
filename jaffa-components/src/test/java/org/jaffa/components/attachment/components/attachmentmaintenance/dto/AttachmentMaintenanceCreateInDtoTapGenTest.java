/* Generated test code. */

package org.jaffa.components.attachment.components.attachmentmaintenance.dto;

import static org.junit.Assert.*;

import org.jaffa.components.dto.HeaderDto;
import org.junit.Test;

/**
 * Generated test code for AttachmentMaintenanceCreateInDto.
 * @author kcassell
 */
public class AttachmentMaintenanceCreateInDtoTapGenTest {

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto#getHeaderDto()}.
	 */
	@Test
	public void testGetHeaderDto() {
	    AttachmentMaintenanceCreateInDto testObject = new AttachmentMaintenanceCreateInDto();
	    HeaderDto setValue = new HeaderDto();
	    testObject.setHeaderDto(setValue);
	    HeaderDto getValue = testObject.getHeaderDto();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto#getAttachmentId()}.
	 */
	@Test
	public void testGetAttachmentId() {
	    AttachmentMaintenanceCreateInDto testObject = new AttachmentMaintenanceCreateInDto();
	    java.lang.String setValue = "testString";
	    testObject.setAttachmentId(setValue);
	    java.lang.String getValue = testObject.getAttachmentId();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto#getSerializedKey()}.
	 */
	@Test
	public void testGetSerializedKey() {
	    AttachmentMaintenanceCreateInDto testObject = new AttachmentMaintenanceCreateInDto();
	    java.lang.String setValue = "testString";
	    testObject.setSerializedKey(setValue);
	    java.lang.String getValue = testObject.getSerializedKey();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto#getOriginalFileName()}.
	 */
	@Test
	public void testGetOriginalFileName() {
	    AttachmentMaintenanceCreateInDto testObject = new AttachmentMaintenanceCreateInDto();
	    java.lang.String setValue = "testString";
	    testObject.setOriginalFileName(setValue);
	    java.lang.String getValue = testObject.getOriginalFileName();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto#getAttachmentType()}.
	 */
	@Test
	public void testGetAttachmentType() {
	    AttachmentMaintenanceCreateInDto testObject = new AttachmentMaintenanceCreateInDto();
	    java.lang.String setValue = "testString";
	    testObject.setAttachmentType(setValue);
	    java.lang.String getValue = testObject.getAttachmentType();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto#getContentType()}.
	 */
	@Test
	public void testGetContentType() {
	    AttachmentMaintenanceCreateInDto testObject = new AttachmentMaintenanceCreateInDto();
	    java.lang.String setValue = "testString";
	    testObject.setContentType(setValue);
	    java.lang.String getValue = testObject.getContentType();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto#getDescription()}.
	 */
	@Test
	public void testGetDescription() {
	    AttachmentMaintenanceCreateInDto testObject = new AttachmentMaintenanceCreateInDto();
	    java.lang.String setValue = "testString";
	    testObject.setDescription(setValue);
	    java.lang.String getValue = testObject.getDescription();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto#getRemarks()}.
	 */
	@Test
	public void testGetRemarks() {
	    AttachmentMaintenanceCreateInDto testObject = new AttachmentMaintenanceCreateInDto();
	    java.lang.String setValue = "testString";
	    testObject.setRemarks(setValue);
	    java.lang.String getValue = testObject.getRemarks();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto#getSupercededBy()}.
	 */
	@Test
	public void testGetSupercededBy() {
	    AttachmentMaintenanceCreateInDto testObject = new AttachmentMaintenanceCreateInDto();
	    java.lang.String setValue = "testString";
	    testObject.setSupercededBy(setValue);
	    java.lang.String getValue = testObject.getSupercededBy();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto#getData()}.
	 */
	@Test
	public void testGetData() {
	    AttachmentMaintenanceCreateInDto testObject = new AttachmentMaintenanceCreateInDto();
	    byte[] setValue = null;
	    testObject.setData(setValue);
	    byte[] getValue = testObject.getData();
	    assertNull(getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentmaintenance.dto.AttachmentMaintenanceCreateInDto#toString()}.
	 */
	@Test
	public void testToString() {
	    AttachmentMaintenanceCreateInDto testObject = new AttachmentMaintenanceCreateInDto();
	    String result = testObject.toString();
	    assertNotNull(result);
	    assertTrue(result.contains("AttachmentMaintenanceCreateInDto"));
	}

}
