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
/** The input for the AttachmentMaintenance.
 */
public class AttachmentMaintenanceRetrieveInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property attachmentId. */
    private java.lang.String attachmentId;



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





    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<AttachmentMaintenanceRetrieveInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<attachmentId>"); if (attachmentId != null) buf.append(attachmentId); buf.append("</attachmentId>");
        buf.append("</AttachmentMaintenanceRetrieveInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
