// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.components.attachment.components.attachmentviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.components.attachment.components.attachmentviewer.dto.AttachmentViewerOutDto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the AttachmentViewer.
 */
public class AttachmentViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "jaffa_attachment_attachmentViewer";
    private static Logger log = Logger.getLogger(AttachmentViewerForm.class);


    /** Getter for property attachmentId.
     * @return Value of property attachmentId.
     */
    public java.lang.String getAttachmentId() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getAttachmentId() : null;
    }

    /** Getter for property serializedKey.
     * @return Value of property serializedKey.
     */
    public java.lang.String getSerializedKey() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getSerializedKey() : null;
    }

    /** Getter for property originalFileName.
     * @return Value of property originalFileName.
     */
    public java.lang.String getOriginalFileName() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getOriginalFileName() : null;
    }

    /** Getter for property attachmentType.
     * @return Value of property attachmentType.
     */
    public java.lang.String getAttachmentType() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getAttachmentType() : null;
    }

    /** Getter for property contentType.
     * @return Value of property contentType.
     */
    public java.lang.String getContentType() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getContentType() : null;
    }

    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getDescription() : null;
    }

    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getRemarks() : null;
    }

    /** Getter for property supercededBy.
     * @return Value of property supercededBy.
     */
    public java.lang.String getSupercededBy() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getSupercededBy() : null;
    }

    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getCreatedOn() : null;
    }

    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getCreatedBy() : null;
    }

    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getLastChangedOn() : null;
    }

    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getLastChangedBy() : null;
    }

    /** Getter for property data.
     * @return Value of property data.
     */
    public byte[] getData() {
        AttachmentViewerOutDto outputDto = ((AttachmentViewerComponent) getComponent()).getAttachmentViewerOutDto();
        return outputDto != null ? outputDto.getData() : null;
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
