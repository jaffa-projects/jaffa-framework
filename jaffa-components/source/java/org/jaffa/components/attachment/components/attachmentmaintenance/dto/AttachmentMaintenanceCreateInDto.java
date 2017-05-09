// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.components.attachment.components.attachmentmaintenance.dto;

import java.util.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the AttachmentMaintenance.
 */
public class AttachmentMaintenanceCreateInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property attachmentId. */
    private java.lang.String attachmentId;

    /** Holds value of property serializedKey. */
    private java.lang.String serializedKey;

    /** Holds value of property originalFileName. */
    private java.lang.String originalFileName;

    /** Holds value of property attachmentType. */
    private java.lang.String attachmentType;

    /** Holds value of property contentType. */
    private java.lang.String contentType;

    /** Holds value of property description. */
    private java.lang.String description;

    /** Holds value of property remarks. */
    private java.lang.String remarks;

    /** Holds value of property supercededBy. */
    private java.lang.String supercededBy;

    /** Holds value of property data. */
    private byte[] data;



    /** Getter for property headerDto.
     * @return Value of property headerDto.
     */
    public HeaderDto getHeaderDto() {
        return headerDto;
    }
    
    /** Setter for property headerDto.
     * @param headerDto New value of property headerDto.
     */
    public void setHeaderDto(HeaderDto headerDto) {
        this.headerDto = headerDto;
    }

    /** Getter for property attachmentId.
     * @return Value of property attachmentId.
     */
    public java.lang.String getAttachmentId() {
        return attachmentId;
    }
    
    /** Setter for property attachmentId.
     * @param attachmentId New value of property attachmentId.
     */
    public void setAttachmentId(java.lang.String attachmentId) {
        if (attachmentId == null || attachmentId.length() == 0)
            this.attachmentId = null;
        else
            this.attachmentId = attachmentId;
    }

    /** Getter for property serializedKey.
     * @return Value of property serializedKey.
     */
    public java.lang.String getSerializedKey() {
        return serializedKey;
    }
    
    /** Setter for property serializedKey.
     * @param serializedKey New value of property serializedKey.
     */
    public void setSerializedKey(java.lang.String serializedKey) {
        if (serializedKey == null || serializedKey.length() == 0)
            this.serializedKey = null;
        else
            this.serializedKey = serializedKey;
    }

    /** Getter for property originalFileName.
     * @return Value of property originalFileName.
     */
    public java.lang.String getOriginalFileName() {
        return originalFileName;
    }
    
    /** Setter for property originalFileName.
     * @param originalFileName New value of property originalFileName.
     */
    public void setOriginalFileName(java.lang.String originalFileName) {
        if (originalFileName == null || originalFileName.length() == 0)
            this.originalFileName = null;
        else
            this.originalFileName = originalFileName;
    }

    /** Getter for property attachmentType.
     * @return Value of property attachmentType.
     */
    public java.lang.String getAttachmentType() {
        return attachmentType;
    }
    
    /** Setter for property attachmentType.
     * @param attachmentType New value of property attachmentType.
     */
    public void setAttachmentType(java.lang.String attachmentType) {
        if (attachmentType == null || attachmentType.length() == 0)
            this.attachmentType = null;
        else
            this.attachmentType = attachmentType;
    }

    /** Getter for property contentType.
     * @return Value of property contentType.
     */
    public java.lang.String getContentType() {
        return contentType;
    }
    
    /** Setter for property contentType.
     * @param contentType New value of property contentType.
     */
    public void setContentType(java.lang.String contentType) {
        if (contentType == null || contentType.length() == 0)
            this.contentType = null;
        else
            this.contentType = contentType;
    }

    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return description;
    }
    
    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        if (description == null || description.length() == 0)
            this.description = null;
        else
            this.description = description;
    }

    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        return remarks;
    }
    
    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(java.lang.String remarks) {
        if (remarks == null || remarks.length() == 0)
            this.remarks = null;
        else
            this.remarks = remarks;
    }

    /** Getter for property supercededBy.
     * @return Value of property supercededBy.
     */
    public java.lang.String getSupercededBy() {
        return supercededBy;
    }
    
    /** Setter for property supercededBy.
     * @param supercededBy New value of property supercededBy.
     */
    public void setSupercededBy(java.lang.String supercededBy) {
        if (supercededBy == null || supercededBy.length() == 0)
            this.supercededBy = null;
        else
            this.supercededBy = supercededBy;
    }

    /** Getter for property data.
     * @return Value of property data.
     */
    public byte[] getData() {
        return data;
    }
    
    /** Setter for property data.
     * @param data New value of property data.
     */
    public void setData(byte[] data) {
        this.data = data;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<AttachmentMaintenanceCreateInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<attachmentId>"); if (attachmentId != null) buf.append(attachmentId); buf.append("</attachmentId>");
        buf.append("<serializedKey>"); if (serializedKey != null) buf.append(serializedKey); buf.append("</serializedKey>");
        buf.append("<originalFileName>"); if (originalFileName != null) buf.append(originalFileName); buf.append("</originalFileName>");
        buf.append("<attachmentType>"); if (attachmentType != null) buf.append(attachmentType); buf.append("</attachmentType>");
        buf.append("<contentType>"); if (contentType != null) buf.append(contentType); buf.append("</contentType>");
        buf.append("<description>"); if (description != null) buf.append(description); buf.append("</description>");
        buf.append("<remarks>"); if (remarks != null) buf.append(remarks); buf.append("</remarks>");
        buf.append("<supercededBy>"); if (supercededBy != null) buf.append(supercededBy); buf.append("</supercededBy>");
        buf.append("<data>"); if (data != null) buf.append(data); buf.append("</data>");

        buf.append("</AttachmentMaintenanceCreateInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
