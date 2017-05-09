// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.jaffa.modules.admin.components.userviewer.dto;

import java.util.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the UserViewer.
 */
public class UserViewerInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property userName. */
    private java.lang.String userName;



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
    

    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        return userName;
    }
    
    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(java.lang.String userName) {
        if (userName == null || userName.length() == 0)
            this.userName = null;
        else
            this.userName = userName;
    }





    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserViewerInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<userName>"); if (userName != null) buf.append(userName); buf.append("</userName>");
        buf.append("</UserViewerInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
