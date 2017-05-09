// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.userfinder.dto;

import java.util.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the UserFinder.
 */
public class UserFinderInDto extends FinderInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property userName. */
    private StringCriteriaField userName;

    /** Holds value of property firstName. */
    private StringCriteriaField firstName;

    /** Holds value of property lastName. */
    private StringCriteriaField lastName;

    /** Holds value of property status. */
    private StringCriteriaField status;

    /** Holds value of property eMailAddress. */
    private StringCriteriaField eMailAddress;



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
    public StringCriteriaField getUserName() {
        return userName;
    }
    
    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(StringCriteriaField userName) {
        this.userName = userName;
    }

    /** Getter for property firstName.
     * @return Value of property firstName.
     */
    public StringCriteriaField getFirstName() {
        return firstName;
    }
    
    /** Setter for property firstName.
     * @param firstName New value of property firstName.
     */
    public void setFirstName(StringCriteriaField firstName) {
        this.firstName = firstName;
    }

    /** Getter for property lastName.
     * @return Value of property lastName.
     */
    public StringCriteriaField getLastName() {
        return lastName;
    }
    
    /** Setter for property lastName.
     * @param lastName New value of property lastName.
     */
    public void setLastName(StringCriteriaField lastName) {
        this.lastName = lastName;
    }

    /** Getter for property status.
     * @return Value of property status.
     */
    public StringCriteriaField getStatus() {
        return status;
    }
    
    /** Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(StringCriteriaField status) {
        this.status = status;
    }

    /** Getter for property eMailAddress.
     * @return Value of property eMailAddress.
     */
    public StringCriteriaField getEMailAddress() {
        return eMailAddress;
    }
    
    /** Setter for property eMailAddress.
     * @param eMailAddress New value of property eMailAddress.
     */
    public void setEMailAddress(StringCriteriaField eMailAddress) {
        this.eMailAddress = eMailAddress;
    }




    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserFinderInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<userName>"); if (userName != null) buf.append(userName); buf.append("</userName>");
        buf.append("<firstName>"); if (firstName != null) buf.append(firstName); buf.append("</firstName>");
        buf.append("<lastName>"); if (lastName != null) buf.append(lastName); buf.append("</lastName>");
        buf.append("<status>"); if (status != null) buf.append(status); buf.append("</status>");
        buf.append("<eMailAddress>"); if (eMailAddress != null) buf.append(eMailAddress); buf.append("</eMailAddress>");

        buf.append("<orderByFields>");
        OrderByField[] orderByFields = getOrderByFields();
        if (orderByFields != null) {
            for (int i = 0; i < orderByFields.length; i++)
                buf.append(orderByFields[i].toString());
        }
        buf.append("</orderByFields>");
        
        buf.append("<maxRecords>"); if (getMaxRecords() != null) buf.append(getMaxRecords()); buf.append("</maxRecords>");
        
        buf.append("</UserFinderInDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
