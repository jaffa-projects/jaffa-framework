// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.components.attachment.components.attachmentmaintenance.dto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the AttachmentMaintenance prevalidations.
 */
public class AttachmentMaintenancePrevalidateOutDto extends AttachmentMaintenanceRetrieveOutDto {



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<AttachmentMaintenancePrevalidateOutDto>");
        buf.append("<attachmentId>"); if (getAttachmentId() != null) buf.append(getAttachmentId()); buf.append("</attachmentId>");
        buf.append("<serializedKey>"); if (getSerializedKey() != null) buf.append(getSerializedKey()); buf.append("</serializedKey>");
        buf.append("<originalFileName>"); if (getOriginalFileName() != null) buf.append(getOriginalFileName()); buf.append("</originalFileName>");
        buf.append("<attachmentType>"); if (getAttachmentType() != null) buf.append(getAttachmentType()); buf.append("</attachmentType>");
        buf.append("<contentType>"); if (getContentType() != null) buf.append(getContentType()); buf.append("</contentType>");
        buf.append("<description>"); if (getDescription() != null) buf.append(getDescription()); buf.append("</description>");
        buf.append("<remarks>"); if (getRemarks() != null) buf.append(getRemarks()); buf.append("</remarks>");
        buf.append("<supercededBy>"); if (getSupercededBy() != null) buf.append(getSupercededBy()); buf.append("</supercededBy>");
        buf.append("<createdOn>"); if (getCreatedOn() != null) buf.append(getCreatedOn()); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (getCreatedBy() != null) buf.append(getCreatedBy()); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (getLastChangedOn() != null) buf.append(getLastChangedOn()); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (getLastChangedBy() != null) buf.append(getLastChangedBy()); buf.append("</lastChangedBy>");
        buf.append("<data>"); if (getData() != null) buf.append(getData()); buf.append("</data>");

        buf.append("</AttachmentMaintenancePrevalidateOutDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
