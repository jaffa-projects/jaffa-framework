/* Generated test code. */

package org.jaffa.components.attachment.components.attachmentviewer.dto;

import static org.junit.Assert.*;

import org.jaffa.components.dto.HeaderDto;
import org.junit.Test;

/**
 * Generated test code for AttachmentViewerInDto.
 * @author kcassell
 */
public class AttachmentViewerInDtoTapGenTest {

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentviewer.dto.AttachmentViewerInDto#getHeaderDto()}.
	 */
	@Test
	public void testGetHeaderDto() {
	    AttachmentViewerInDto testObject = new AttachmentViewerInDto();
	    HeaderDto setValue = new HeaderDto();
	    testObject.setHeaderDto(setValue);
	    HeaderDto getValue = testObject.getHeaderDto();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentviewer.dto.AttachmentViewerInDto#getAttachmentId()}.
	 */
	@Test
	public void testGetAttachmentId() {
	    AttachmentViewerInDto testObject = new AttachmentViewerInDto();
	    java.lang.String setValue = "testString";
	    testObject.setAttachmentId(setValue);
	    java.lang.String getValue = testObject.getAttachmentId();
	    assertEquals(setValue, getValue);
	}

	/**
	 * Test method for {@link org.jaffa.components.attachment.components.attachmentviewer.dto.AttachmentViewerInDto#toString()}.
	 */
	@Test
	public void testToString() {
	    AttachmentViewerInDto testObject = new AttachmentViewerInDto();
	    String result = testObject.toString();
	    assertNotNull(result);
	    assertTrue(result.contains("AttachmentViewerInDto"));
	}

}
