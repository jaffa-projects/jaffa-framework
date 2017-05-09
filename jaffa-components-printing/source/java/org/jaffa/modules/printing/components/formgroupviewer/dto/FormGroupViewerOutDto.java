// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.formgroupviewer.dto;

import java.util.*;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the FormGroupViewer.
 */
public class FormGroupViewerOutDto {

    /** Holds value of property formName. */
    private java.lang.String formName;

    /** Holds value of property description. */
    private java.lang.String description;

    /** Holds value of property formType. */
    private java.lang.String formType;

    /** Holds an array of FormUsage objects to be returned. */
    private List formUsageList;


    /** Default Constructor.*/    
    public FormGroupViewerOutDto() {
        formUsageList = new ArrayList();
    }

    /** Getter for property formName.
     * @return Value of property formName.
     */
    public java.lang.String getFormName() {
        return formName;
    }
    
    /** Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(java.lang.String formName) {
        if (formName == null || formName.length() == 0)
            this.formName = null;
        else
            this.formName = formName;
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

    /** Getter for property formType.
     * @return Value of property formType.
     */
    public java.lang.String getFormType() {
        return formType;
    }
    
    /** Setter for property formType.
     * @param formType New value of property formType.
     */
    public void setFormType(java.lang.String formType) {
        if (formType == null || formType.length() == 0)
            this.formType = null;
        else
            this.formType = formType;
    }

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



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormGroupViewerOutDto>");
        buf.append("<formName>"); if (formName != null) buf.append(formName); buf.append("</formName>");
        buf.append("<description>"); if (description != null) buf.append(description); buf.append("</description>");
        buf.append("<formType>"); if (formType != null) buf.append(formType); buf.append("</formType>");

        buf.append("<formUsages>");
        FormUsageDto[] formUsages = getFormUsage();
        for (int i = 0; i < formUsages.length; i++) {
            buf.append(formUsages[i].toString());
        }
        buf.append("</formUsages>");

        buf.append("</FormGroupViewerOutDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
