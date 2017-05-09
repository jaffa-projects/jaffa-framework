// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.formdefinitionmaintenance.dto;

import java.util.*;
import org.jaffa.components.dto.HeaderDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the FormDefinitionMaintenance.
 */
public class FormDefinitionMaintenanceUpdateInDto extends FormDefinitionMaintenanceCreateInDto {

    /** Holds value of property performDirtyReadCheck. */
    private Boolean performDirtyReadCheck;

    /** Holds value of property lastChangedOn. */
    private org.jaffa.datatypes.DateTime lastChangedOn;


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

    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        return lastChangedOn;
    }
    
    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) {
        this.lastChangedOn = lastChangedOn;
    }


    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<FormDefinitionMaintenanceUpdateInDto>");
        buf.append("<headerDto>"); if (getHeaderDto() != null) buf.append( getHeaderDto().toString() ); buf.append("</headerDto>");
        buf.append("<performDirtyReadCheck>"); if (performDirtyReadCheck != null) buf.append( performDirtyReadCheck.toString() ); buf.append("</performDirtyReadCheck>");

        buf.append("<formId>"); if (getFormId() != null) buf.append(getFormId()); buf.append("</formId>");
        buf.append("<formName>"); if (getFormName() != null) buf.append(getFormName()); buf.append("</formName>");
        buf.append("<formAlternate>"); if (getFormAlternate() != null) buf.append(getFormAlternate()); buf.append("</formAlternate>");
        buf.append("<formVariation>"); if (getFormVariation() != null) buf.append(getFormVariation()); buf.append("</formVariation>");
        buf.append("<outputType>"); if (getOutputType() != null) buf.append(getOutputType()); buf.append("</outputType>");
        buf.append("<formTemplate>"); if (getFormTemplate() != null) buf.append(getFormTemplate()); buf.append("</formTemplate>");
        buf.append("<fieldLayout>"); if (getFieldLayout() != null) buf.append(getFieldLayout()); buf.append("</fieldLayout>");
        buf.append("<description>"); if (getDescription() != null) buf.append(getDescription()); buf.append("</description>");
        buf.append("<remarks>"); if (getRemarks() != null) buf.append(getRemarks()); buf.append("</remarks>");
        buf.append("<domFactory>"); if (getDomFactory() != null) buf.append(getDomFactory()); buf.append("</domFactory>");
        buf.append("<domClass>"); if (getDomClass() != null) buf.append(getDomClass()); buf.append("</domClass>");
        buf.append("<domKey1>"); if (getDomKey1() != null) buf.append(getDomKey1()); buf.append("</domKey1>");
        buf.append("<domKey2>"); if (getDomKey2() != null) buf.append(getDomKey2()); buf.append("</domKey2>");
        buf.append("<domKey3>"); if (getDomKey3() != null) buf.append(getDomKey3()); buf.append("</domKey3>");
        buf.append("<domKey4>"); if (getDomKey4() != null) buf.append(getDomKey4()); buf.append("</domKey4>");
        buf.append("<domKey5>"); if (getDomKey5() != null) buf.append(getDomKey5()); buf.append("</domKey5>");
        buf.append("<domKey6>"); if (getDomKey6() != null) buf.append(getDomKey6()); buf.append("</domKey6>");
        buf.append("<additionalDataComponent>"); if (getAdditionalDataComponent() != null) buf.append(getAdditionalDataComponent()); buf.append("</additionalDataComponent>");
        buf.append("<followOnFormName>"); if (getFollowOnFormName() != null) buf.append(getFollowOnFormName()); buf.append("</followOnFormName>");
        buf.append("<followOnFormAlternate>"); if (getFollowOnFormAlternate() != null) buf.append(getFollowOnFormAlternate()); buf.append("</followOnFormAlternate>");
        buf.append("<lastChangedOn>"); if (lastChangedOn != null) buf.append(lastChangedOn); buf.append("</lastChangedOn>");

        buf.append("</FormDefinitionMaintenanceUpdateInDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here//GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
