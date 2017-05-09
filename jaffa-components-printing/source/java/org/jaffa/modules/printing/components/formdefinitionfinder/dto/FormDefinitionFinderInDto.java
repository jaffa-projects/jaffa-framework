// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formdefinitionfinder.dto;

import java.util.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The input for the FormDefinitionFinder.
 */
public class FormDefinitionFinderInDto extends FinderInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property formId. */
    private IntegerCriteriaField formId;

    /** Holds value of property formName. */
    private StringCriteriaField formName;

    /** Holds value of property formAlternate. */
    private StringCriteriaField formAlternate;

    /** Holds value of property formVariation. */
    private StringCriteriaField formVariation;

    /** Holds value of property outputType. */
    private StringCriteriaField outputType;

    /** Holds value of property formTemplate. */
    private StringCriteriaField formTemplate;



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
    

    /** Getter for property formId.
     * @return Value of property formId.
     */
    public IntegerCriteriaField getFormId() {
        return formId;
    }
    
    /** Setter for property formId.
     * @param formId New value of property formId.
     */
    public void setFormId(IntegerCriteriaField formId) {
        this.formId = formId;
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

    /** Getter for property formVariation.
     * @return Value of property formVariation.
     */
    public StringCriteriaField getFormVariation() {
        return formVariation;
    }
    
    /** Setter for property formVariation.
     * @param formVariation New value of property formVariation.
     */
    public void setFormVariation(StringCriteriaField formVariation) {
        this.formVariation = formVariation;
    }

    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public StringCriteriaField getOutputType() {
        return outputType;
    }
    
    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(StringCriteriaField outputType) {
        this.outputType = outputType;
    }

    /** Getter for property formTemplate.
     * @return Value of property formTemplate.
     */
    public StringCriteriaField getFormTemplate() {
        return formTemplate;
    }
    
    /** Setter for property formTemplate.
     * @param formTemplate New value of property formTemplate.
     */
    public void setFormTemplate(StringCriteriaField formTemplate) {
        this.formTemplate = formTemplate;
    }




    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormDefinitionFinderInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");

        buf.append("<formId>"); if (formId != null) buf.append(formId); buf.append("</formId>");
        buf.append("<formName>"); if (formName != null) buf.append(formName); buf.append("</formName>");
        buf.append("<formAlternate>"); if (formAlternate != null) buf.append(formAlternate); buf.append("</formAlternate>");
        buf.append("<formVariation>"); if (formVariation != null) buf.append(formVariation); buf.append("</formVariation>");
        buf.append("<outputType>"); if (outputType != null) buf.append(outputType); buf.append("</outputType>");
        buf.append("<formTemplate>"); if (formTemplate != null) buf.append(formTemplate); buf.append("</formTemplate>");

        buf.append("<orderByFields>");
        OrderByField[] orderByFields = getOrderByFields();
        if (orderByFields != null) {
            for (int i = 0; i < orderByFields.length; i++)
                buf.append(orderByFields[i].toString());
        }
        buf.append("</orderByFields>");
        
        buf.append("<maxRecords>"); if (getMaxRecords() != null) buf.append(getMaxRecords()); buf.append("</maxRecords>");
        
        buf.append("</FormDefinitionFinderInDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
