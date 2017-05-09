// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.dto;

import java.util.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the UserRequestViewer.
 */
public class UserRequestViewerInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property requestId. */
    private java.lang.Long requestId;



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
    

    /** Getter for property requestId.
     * @return Value of property requestId.
     */
    public java.lang.Long getRequestId() {
        return requestId;
    }
    
    /** Setter for property requestId.
     * @param requestId New value of property requestId.
     */
    public void setRequestId(java.lang.Long requestId) {
        this.requestId = requestId;
    }





    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserRequestViewerInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<requestId>"); if (requestId != null) buf.append(requestId); buf.append("</requestId>");
        buf.append("</UserRequestViewerInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
