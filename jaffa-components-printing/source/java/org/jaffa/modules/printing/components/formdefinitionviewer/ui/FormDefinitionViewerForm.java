// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formdefinitionviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.modules.printing.components.formdefinitionviewer.dto.FormDefinitionViewerOutDto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the FormDefinitionViewer.
 */
public class FormDefinitionViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "jaffa_printing_formDefinitionViewer";
    private static Logger log = Logger.getLogger(FormDefinitionViewerForm.class);


    /** Getter for property formId.
     * @return Value of property formId.
     */
    public java.lang.Long getFormId() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getFormId() : null;
    }

    /** Getter for property formName.
     * @return Value of property formName.
     */
    public java.lang.String getFormName() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getFormName() : null;
    }

    /** Getter for property formAlternate.
     * @return Value of property formAlternate.
     */
    public java.lang.String getFormAlternate() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getFormAlternate() : null;
    }

    /** Getter for property formVariation.
     * @return Value of property formVariation.
     */
    public java.lang.String getFormVariation() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getFormVariation() : null;
    }

    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getDescription() : null;
    }

    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getRemarks() : null;
    }

    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public java.lang.String getOutputType() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getOutputType() : null;
    }

    /** Getter for property followOnFormName.
     * @return Value of property followOnFormName.
     */
    public java.lang.String getFollowOnFormName() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getFollowOnFormName() : null;
    }

    /** Getter for property formTemplate.
     * @return Value of property formTemplate.
     */
    public java.lang.String getFormTemplate() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getFormTemplate() : null;
    }

    /** Getter for property fieldLayout.
     * @return Value of property fieldLayout.
     */
    public java.lang.String getFieldLayout() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getFieldLayout() : null;
    }

    /** Getter for property domClass.
     * @return Value of property domClass.
     */
    public java.lang.String getDomClass() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getDomClass() : null;
    }

    /** Getter for property domKey1.
     * @return Value of property domKey1.
     */
    public java.lang.String getDomKey1() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getDomKey1() : null;
    }

    /** Getter for property domKey2.
     * @return Value of property domKey2.
     */
    public java.lang.String getDomKey2() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getDomKey2() : null;
    }

    /** Getter for property domKey3.
     * @return Value of property domKey3.
     */
    public java.lang.String getDomKey3() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getDomKey3() : null;
    }

    /** Getter for property domKey4.
     * @return Value of property domKey4.
     */
    public java.lang.String getDomKey4() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getDomKey4() : null;
    }

    /** Getter for property domKey5.
     * @return Value of property domKey5.
     */
    public java.lang.String getDomKey5() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getDomKey5() : null;
    }

    /** Getter for property domKey6.
     * @return Value of property domKey6.
     */
    public java.lang.String getDomKey6() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getDomKey6() : null;
    }

    /** Getter for property domFactory.
     * @return Value of property domFactory.
     */
    public java.lang.String getDomFactory() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getDomFactory() : null;
    }

    /** Getter for property additionalDataComponent.
     * @return Value of property additionalDataComponent.
     */
    public java.lang.String getAdditionalDataComponent() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getAdditionalDataComponent() : null;
    }

    /** Getter for property followOnFormAlternate.
     * @return Value of property followOnFormAlternate.
     */
    public java.lang.String getFollowOnFormAlternate() {
        FormDefinitionViewerOutDto outputDto = ((FormDefinitionViewerComponent) getComponent()).getFormDefinitionViewerOutDto();
        return outputDto != null ? outputDto.getFollowOnFormAlternate() : null;
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
