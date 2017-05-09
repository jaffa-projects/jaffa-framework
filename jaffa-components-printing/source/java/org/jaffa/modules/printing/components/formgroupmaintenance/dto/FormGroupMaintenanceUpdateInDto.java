// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.formgroupmaintenance.dto;

import java.util.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the FormGroupMaintenance.
 */
public class FormGroupMaintenanceUpdateInDto extends FormGroupMaintenanceCreateInDto {

    /** Holds value of property performDirtyReadCheck. */
    private Boolean performDirtyReadCheck;


    /** Getter for property performDirtyReadCheck.
     * @return Value of property performDirtyReadCheck.
     */
    public Boolean getPerformDirtyReadCheck() {
        return performDirtyReadCheck;
    }
    
    /** Setter for property performDirtyReadCheck.
     * @param performDirtyReadCheck New value of property performDirtyReadCheck.
     */
    public void setPerformDirtyReadCheck(Boolean performDirtyReadCheck) {
        this.performDirtyReadCheck = performDirtyReadCheck;
    }



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormGroupMaintenanceUpdateInDto>");
        buf.append("<headerDto>"); if (getHeaderDto() != null) buf.append( getHeaderDto().toString() ); buf.append("</headerDto>");
        buf.append("<performDirtyReadCheck>"); if (performDirtyReadCheck != null) buf.append( performDirtyReadCheck.toString() ); buf.append("</performDirtyReadCheck>");

        buf.append("<formName>"); if (getFormName() != null) buf.append(getFormName()); buf.append("</formName>");
        buf.append("<description>"); if (getDescription() != null) buf.append(getDescription()); buf.append("</description>");
        buf.append("<formType>"); if (getFormType() != null) buf.append(getFormType()); buf.append("</formType>");

        buf.append("</FormGroupMaintenanceUpdateInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here//GEN-FIRST:_custom

    /** Holds an array of FormUsage objects to be returned. */
    private List formUsageList = new ArrayList();
    
    /** Add FormUsage objects.
     * @param formUsage FormUsage.
     */
    public void addFormUsage(FormUsageDto formUsage) {
        formUsageList.add(formUsage);
    }
    
    /** Add FormUsage at the specified position.
     * @param formUsage FormUsage.
     * @param index The position at which the FormUsage is to be added.
     */
    public void setFormUsage(FormUsageDto formUsage, int index) {
        //-- check bounds for index
        if ((index < 0) || (index > formUsageList.size()))
            throw new IndexOutOfBoundsException();
        
        formUsageList.set(index, formUsage);
    }
    
    /** Add an array of FormUsage objects. This will overwrite existing FormUsage objects.
     * @param objects An array of FormUsage objects.
     */
    public void setFormUsage(FormUsageDto[] objects) {
        formUsageList = Arrays.asList(objects);
    }
    
    /** Clear existing FormUsage objects.
     */
    public void clearFormUsage() {
        formUsageList.clear();
    }
    
    /** Remove FormUsage object.
     * @param formUsage FormUsage.
     * @return A true indicates a FormUsage object was removed. A false indicates, the FormUsage object was not found.
     */
    public boolean removeFormUsage(FormUsageDto formUsage) {
        return formUsageList.remove(formUsage);
    }
    
    /** Returns a FormUsage object from the specified position.
     * @param index The position index.
     * @return FormUsage.
     */
    public FormUsageDto getFormUsage(int index) {
        //-- check bounds for index
        if ((index < 0) || (index > formUsageList.size()))
            throw new IndexOutOfBoundsException();
        
        return (FormUsageDto) formUsageList.get(index);
    }
    
    /** Returns an array of FormUsage objects.
     * @return An array of FormUsage objects.
     */
    public FormUsageDto[] getFormUsage() {
        return (FormUsageDto[]) formUsageList.toArray(new FormUsageDto[0]);
    }
    
    /** Returns a count of FormUsage objects.
     * @return The count of FormUsage objects.
     */
    public int getFormUsageCount() {
        return formUsageList.size();
    }
    
    
    
    
    // .//GEN-LAST:_custom
}
