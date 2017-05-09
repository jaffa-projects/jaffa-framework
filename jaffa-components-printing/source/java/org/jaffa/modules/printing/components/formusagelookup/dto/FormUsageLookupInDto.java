// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formusagelookup.dto;

import java.util.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the FormUsageLookup.
 */
public class FormUsageLookupInDto extends FinderInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property formName. */
    private StringCriteriaField formName;

    /** Holds value of property eventName. */
    private StringCriteriaField eventName;

    /** Holds value of property formAlternate. */
    private StringCriteriaField formAlternate;

    /** Holds value of property copies. */
    private IntegerCriteriaField copies;

    /** Holds value of property createdOn. */
    private DateTimeCriteriaField createdOn;

    /** Holds value of property createdBy. */
    private StringCriteriaField createdBy;

    /** Holds value of property lastChangedOn. */
    private DateTimeCriteriaField lastChangedOn;

    /** Holds value of property lastChangedBy. */
    private StringCriteriaField lastChangedBy;



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
    

    /** Getter for property formName.
     * @return Value of property formName.
     */
    public StringCriteriaField getFormName() {
        return formName;
    }
    
    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(StringCriteriaField formName) {
        this.formName = formName;
    }

    /** Getter for property eventName.
     * @return Value of property eventName.
     */
    public StringCriteriaField getEventName() {
        return eventName;
    }
    
    /** Setter for property eventName.
     * @param eventName New value of property eventName.
     */
    public void setEventName(StringCriteriaField eventName) {
        this.eventName = eventName;
    }

    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public StringCriteriaField getFormAlternate() {
        return formAlternate;
    }
    
    /** Setter for property formAlternate.
     * @param formAlternate New value of property formAlternate.
     */
    public void setFormAlternate(StringCriteriaField formAlternate) {
        this.formAlternate = formAlternate;
    }

    /** Getter for property copies.
     * @return Value of property copies.
     */
    public IntegerCriteriaField getCopies() {
        return copies;
    }
    
    /** Setter for property copies.
     * @param copies New value of property copies.
     */
    public void setCopies(IntegerCriteriaField copies) {
        this.copies = copies;
    }

    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public DateTimeCriteriaField getCreatedOn() {
        return createdOn;
    }
    
    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(DateTimeCriteriaField createdOn) {
        this.createdOn = createdOn;
    }

    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public StringCriteriaField getCreatedBy() {
        return createdBy;
    }
    
    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(StringCriteriaField createdBy) {
        this.createdBy = createdBy;
    }

    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public DateTimeCriteriaField getLastChangedOn() {
        return lastChangedOn;
    }
    
    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(DateTimeCriteriaField lastChangedOn) {
        this.lastChangedOn = lastChangedOn;
    }

    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public StringCriteriaField getLastChangedBy() {
        return lastChangedBy;
    }
    
    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(StringCriteriaField lastChangedBy) {
        this.lastChangedBy = lastChangedBy;
    }




    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormUsageLookupInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<formName>"); if (formName != null) buf.append(formName); buf.append("</formName>");
        buf.append("<eventName>"); if (eventName != null) buf.append(eventName); buf.append("</eventName>");
        buf.append("<formAlternate>"); if (formAlternate != null) buf.append(formAlternate); buf.append("</formAlternate>");
        buf.append("<copies>"); if (copies != null) buf.append(copies); buf.append("</copies>");
        buf.append("<createdOn>"); if (createdOn != null) buf.append(createdOn); buf.append("</createdOn>");
        buf.append("<createdBy>"); if (createdBy != null) buf.append(createdBy); buf.append("</createdBy>");
        buf.append("<lastChangedOn>"); if (lastChangedOn != null) buf.append(lastChangedOn); buf.append("</lastChangedOn>");
        buf.append("<lastChangedBy>"); if (lastChangedBy != null) buf.append(lastChangedBy); buf.append("</lastChangedBy>");

        buf.append("<orderByFields>");
        OrderByField[] orderByFields = getOrderByFields();
        if (orderByFields != null) {
            for (int i = 0; i < orderByFields.length; i++)
                buf.append(orderByFields[i].toString());
        }
        buf.append("</orderByFields>");
        
        buf.append("<maxRecords>"); if (getMaxRecords() != null) buf.append(getMaxRecords()); buf.append("</maxRecords>");
        
        buf.append("</FormUsageLookupInDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
