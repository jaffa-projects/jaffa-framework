// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printerdefinitionmaintenance.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.components.codehelper.dto.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.maint.MaintForm;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.*;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.metadata.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.util.StringHelper;

import org.jaffa.modules.printing.components.printerdefinitionmaintenance.dto.*;
import org.jaffa.modules.printing.domain.PrinterDefinitionMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports



// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the PrinterDefinitionMaintenance.
 */
public class PrinterDefinitionMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(PrinterDefinitionMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:printerId_1_be
    /** Getter for property printerId.
     * @return Value of property printerId.
     */
    public java.lang.String getPrinterId() {
        return ( (PrinterDefinitionMaintenanceComponent) getComponent() ).getPrinterId();
    }

    /** Setter for property printerId.
     * @param printerId New value of property printerId.
     */
    public void setPrinterId(java.lang.String printerId) {
        ( (PrinterDefinitionMaintenanceComponent) getComponent() ).setPrinterId(printerId);
    }

    /** Getter for property printerId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property printerId.
     */
    public EditBoxModel getPrinterIdWM() {
        EditBoxModel w_printerId = (EditBoxModel) getWidgetCache().getModel("printerId");
        if (w_printerId == null) {
            if (getPrinterId() != null)
                w_printerId = new EditBoxModel(getPrinterId(), (StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_ID);
            else
                w_printerId = new EditBoxModel((StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_ID);
            // .//GEN-END:printerId_1_be
            // Add custom code//GEN-FIRST:printerId_1


            // .//GEN-LAST:printerId_1
            // .//GEN-BEGIN:printerId_2_be
            getWidgetCache().addModel("printerId", w_printerId);
        }
        return w_printerId;
    }

    /** Setter for property printerId. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property printerId.
     */
    public void setPrinterIdWV(String value) {
        EditBoxController.updateModel(value, getPrinterIdWM());
    }
    // .//GEN-END:printerId_2_be
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return ( (PrinterDefinitionMaintenanceComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        ( (PrinterDefinitionMaintenanceComponent) getComponent() ).setDescription(description);
    }

    /** Getter for property description. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property description.
     */
    public EditBoxModel getDescriptionWM() {
        EditBoxModel w_description = (EditBoxModel) getWidgetCache().getModel("description");
        if (w_description == null) {
            if (getDescription() != null)
                w_description = new EditBoxModel(getDescription(), (StringFieldMetaData) PrinterDefinitionMeta.META_DESCRIPTION);
            else
                w_description = new EditBoxModel((StringFieldMetaData) PrinterDefinitionMeta.META_DESCRIPTION);
            // .//GEN-END:description_1_be
            // Add custom code//GEN-FIRST:description_1


            // .//GEN-LAST:description_1
            // .//GEN-BEGIN:description_2_be
            getWidgetCache().addModel("description", w_description);
        }
        return w_description;
    }

    /** Setter for property description. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property description.
     */
    public void setDescriptionWV(String value) {
        EditBoxController.updateModel(value, getDescriptionWM());
    }
    // .//GEN-END:description_2_be
    // .//GEN-BEGIN:siteCode_1_be
    /** Getter for property siteCode.
     * @return Value of property siteCode.
     */
    public java.lang.String getSiteCode() {
        return ( (PrinterDefinitionMaintenanceComponent) getComponent() ).getSiteCode();
    }

    /** Setter for property siteCode.
     * @param siteCode New value of property siteCode.
     */
    public void setSiteCode(java.lang.String siteCode) {
        ( (PrinterDefinitionMaintenanceComponent) getComponent() ).setSiteCode(siteCode);
    }

    /** Getter for property siteCode. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property siteCode.
     */
    public EditBoxModel getSiteCodeWM() {
        EditBoxModel w_siteCode = (EditBoxModel) getWidgetCache().getModel("siteCode");
        if (w_siteCode == null) {
            if (getSiteCode() != null)
                w_siteCode = new EditBoxModel(getSiteCode(), (StringFieldMetaData) PrinterDefinitionMeta.META_SITE_CODE);
            else
                w_siteCode = new EditBoxModel((StringFieldMetaData) PrinterDefinitionMeta.META_SITE_CODE);
            // .//GEN-END:siteCode_1_be
            // Add custom code//GEN-FIRST:siteCode_1


            // .//GEN-LAST:siteCode_1
            // .//GEN-BEGIN:siteCode_2_be
            getWidgetCache().addModel("siteCode", w_siteCode);
        }
        return w_siteCode;
    }

    /** Setter for property siteCode. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property siteCode.
     */
    public void setSiteCodeWV(String value) {
        EditBoxController.updateModel(value, getSiteCodeWM());
    }
    // .//GEN-END:siteCode_2_be
    // .//GEN-BEGIN:locationCode_1_be
    /** Getter for property locationCode.
     * @return Value of property locationCode.
     */
    public java.lang.String getLocationCode() {
        return ( (PrinterDefinitionMaintenanceComponent) getComponent() ).getLocationCode();
    }

    /** Setter for property locationCode.
     * @param locationCode New value of property locationCode.
     */
    public void setLocationCode(java.lang.String locationCode) {
        ( (PrinterDefinitionMaintenanceComponent) getComponent() ).setLocationCode(locationCode);
    }

    /** Getter for property locationCode. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property locationCode.
     */
    public EditBoxModel getLocationCodeWM() {
        EditBoxModel w_locationCode = (EditBoxModel) getWidgetCache().getModel("locationCode");
        if (w_locationCode == null) {
            if (getLocationCode() != null)
                w_locationCode = new EditBoxModel(getLocationCode(), (StringFieldMetaData) PrinterDefinitionMeta.META_LOCATION_CODE);
            else
                w_locationCode = new EditBoxModel((StringFieldMetaData) PrinterDefinitionMeta.META_LOCATION_CODE);
            // .//GEN-END:locationCode_1_be
            // Add custom code//GEN-FIRST:locationCode_1


            // .//GEN-LAST:locationCode_1
            // .//GEN-BEGIN:locationCode_2_be
            getWidgetCache().addModel("locationCode", w_locationCode);
        }
        return w_locationCode;
    }

    /** Setter for property locationCode. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property locationCode.
     */
    public void setLocationCodeWV(String value) {
        EditBoxController.updateModel(value, getLocationCodeWM());
    }
    // .//GEN-END:locationCode_2_be
    // .//GEN-BEGIN:remote_1_be
    /** Getter for property remote.
     * @return Value of property remote.
     */
    public java.lang.Boolean getRemote() {
        return ( (PrinterDefinitionMaintenanceComponent) getComponent() ).getRemote();
    }

    /** Setter for property remote.
     * @param remote New value of property remote.
     */
    public void setRemote(java.lang.Boolean remote) {
        ( (PrinterDefinitionMaintenanceComponent) getComponent() ).setRemote(remote);
    }

    /** Getter for property remote. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remote.
     */
    public CheckBoxModel getRemoteWM() {
        CheckBoxModel w_remote = (CheckBoxModel) getWidgetCache().getModel("remote");
        if (w_remote == null) {
            if (getRemote() != null)
                w_remote = new CheckBoxModel( getRemote() );
            else
                w_remote = new CheckBoxModel(false);
            // .//GEN-END:remote_1_be
            // Add custom code//GEN-FIRST:remote_1


            // .//GEN-LAST:remote_1
            // .//GEN-BEGIN:remote_2_be
            getWidgetCache().addModel("remote", w_remote);
        }
        return w_remote;
    }

    /** Setter for property remote. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property remote.
     */
    public void setRemoteWV(String value) {
        CheckBoxController.updateModel(value, getRemoteWM());
    }
    // .//GEN-END:remote_2_be
    // .//GEN-BEGIN:realPrinterName_1_be
    /** Getter for property realPrinterName.
     * @return Value of property realPrinterName.
     */
    public java.lang.String getRealPrinterName() {
        return ( (PrinterDefinitionMaintenanceComponent) getComponent() ).getRealPrinterName();
    }

    /** Setter for property realPrinterName.
     * @param realPrinterName New value of property realPrinterName.
     */
    public void setRealPrinterName(java.lang.String realPrinterName) {
        ( (PrinterDefinitionMaintenanceComponent) getComponent() ).setRealPrinterName(realPrinterName);
    }

    /** Getter for property realPrinterName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property realPrinterName.
     */
    public EditBoxModel getRealPrinterNameWM() {
        EditBoxModel w_realPrinterName = (EditBoxModel) getWidgetCache().getModel("realPrinterName");
        if (w_realPrinterName == null) {
            if (getRealPrinterName() != null)
                w_realPrinterName = new EditBoxModel(getRealPrinterName(), (StringFieldMetaData) PrinterDefinitionMeta.META_REAL_PRINTER_NAME);
            else
                w_realPrinterName = new EditBoxModel((StringFieldMetaData) PrinterDefinitionMeta.META_REAL_PRINTER_NAME);
            // .//GEN-END:realPrinterName_1_be
            // Add custom code//GEN-FIRST:realPrinterName_1


            // .//GEN-LAST:realPrinterName_1
            // .//GEN-BEGIN:realPrinterName_2_be
            getWidgetCache().addModel("realPrinterName", w_realPrinterName);
        }
        return w_realPrinterName;
    }

    /** Setter for property realPrinterName. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property realPrinterName.
     */
    public void setRealPrinterNameWV(String value) {
        EditBoxController.updateModel(value, getRealPrinterNameWM());
    }
    // .//GEN-END:realPrinterName_2_be
    // .//GEN-BEGIN:printerOption1_1_be
    /** Getter for property printerOption1.
     * @return Value of property printerOption1.
     */
    public java.lang.String getPrinterOption1() {
        return ( (PrinterDefinitionMaintenanceComponent) getComponent() ).getPrinterOption1();
    }

    /** Setter for property printerOption1.
     * @param printerOption1 New value of property printerOption1.
     */
    public void setPrinterOption1(java.lang.String printerOption1) {
        ( (PrinterDefinitionMaintenanceComponent) getComponent() ).setPrinterOption1(printerOption1);
    }

    /** Getter for property printerOption1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property printerOption1.
     */
    public EditBoxModel getPrinterOption1WM() {
        EditBoxModel w_printerOption1 = (EditBoxModel) getWidgetCache().getModel("printerOption1");
        if (w_printerOption1 == null) {
            if (getPrinterOption1() != null)
                w_printerOption1 = new EditBoxModel(getPrinterOption1(), (StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_OPTION1);
            else
                w_printerOption1 = new EditBoxModel((StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_OPTION1);
            // .//GEN-END:printerOption1_1_be
            // Add custom code//GEN-FIRST:printerOption1_1


            // .//GEN-LAST:printerOption1_1
            // .//GEN-BEGIN:printerOption1_2_be
            getWidgetCache().addModel("printerOption1", w_printerOption1);
        }
        return w_printerOption1;
    }

    /** Setter for property printerOption1. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property printerOption1.
     */
    public void setPrinterOption1WV(String value) {
        EditBoxController.updateModel(value, getPrinterOption1WM());
    }
    // .//GEN-END:printerOption1_2_be
    // .//GEN-BEGIN:printerOption2_1_be
    /** Getter for property printerOption2.
     * @return Value of property printerOption2.
     */
    public java.lang.String getPrinterOption2() {
        return ( (PrinterDefinitionMaintenanceComponent) getComponent() ).getPrinterOption2();
    }

    /** Setter for property printerOption2.
     * @param printerOption2 New value of property printerOption2.
     */
    public void setPrinterOption2(java.lang.String printerOption2) {
        ( (PrinterDefinitionMaintenanceComponent) getComponent() ).setPrinterOption2(printerOption2);
    }

    /** Getter for property printerOption2. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property printerOption2.
     */
    public EditBoxModel getPrinterOption2WM() {
        EditBoxModel w_printerOption2 = (EditBoxModel) getWidgetCache().getModel("printerOption2");
        if (w_printerOption2 == null) {
            if (getPrinterOption2() != null)
                w_printerOption2 = new EditBoxModel(getPrinterOption2(), (StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_OPTION2);
            else
                w_printerOption2 = new EditBoxModel((StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_OPTION2);
            // .//GEN-END:printerOption2_1_be
            // Add custom code//GEN-FIRST:printerOption2_1


            // .//GEN-LAST:printerOption2_1
            // .//GEN-BEGIN:printerOption2_2_be
            getWidgetCache().addModel("printerOption2", w_printerOption2);
        }
        return w_printerOption2;
    }

    /** Setter for property printerOption2. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property printerOption2.
     */
    public void setPrinterOption2WV(String value) {
        EditBoxController.updateModel(value, getPrinterOption2WM());
    }
    // .//GEN-END:printerOption2_2_be
    // .//GEN-BEGIN:outputType_1_be
    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public java.lang.String getOutputType() {
        return ( (PrinterDefinitionMaintenanceComponent) getComponent() ).getOutputType();
    }

    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(java.lang.String outputType) {
        ( (PrinterDefinitionMaintenanceComponent) getComponent() ).setOutputType(outputType);
    }

    /** Getter for property outputType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property outputType.
     */
    public DropDownModel getOutputTypeWM() {
        DropDownModel w_outputType = (DropDownModel) getWidgetCache().getModel("outputType");
        if (w_outputType == null) {
            w_outputType = new DropDownModel((getOutputType() != null ? getOutputType() : ""), (StringFieldMetaData) PrinterDefinitionMeta.META_OUTPUT_TYPE);
            CodeHelperOutElementDto dto = ( (PrinterDefinitionMaintenanceComponent) getComponent() ).getOutputTypeCodes();
            if (dto != null && dto.getCodeHelperOutCodeDtoCount() > 0) {
                CodeHelperOutCodeDto[] codes = dto.getCodeHelperOutCodeDtos();
                for (int i = 0; i < codes.length; i++) {
                    CodeHelperOutCodeDto code = codes[i];
                    w_outputType.addOption(Formatter.format(code.getDescription()), Formatter.format(code.getCode()));
                }
            }

            // .//GEN-END:outputType_1_be
            // Add custom code//GEN-FIRST:outputType_1
            

            // .//GEN-LAST:outputType_1
            // .//GEN-BEGIN:outputType_2_be
            getWidgetCache().addModel("outputType", w_outputType);
        }
        return w_outputType;
    }

    /** Setter for property outputType. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property outputType.
     */
    public void setOutputTypeWV(String value) {
        DropDownController.updateModel(value, getOutputTypeWM());
    }
    // .//GEN-END:outputType_2_be
    // .//GEN-BEGIN:scaleToPageSize_1_be
    /** Getter for property scaleToPageSize.
     * @return Value of property scaleToPageSize.
     */
    public java.lang.String getScaleToPageSize() {
        return ( (PrinterDefinitionMaintenanceComponent) getComponent() ).getScaleToPageSize();
    }

    /** Setter for property scaleToPageSize.
     * @param scaleToPageSize New value of property scaleToPageSize.
     */
    public void setScaleToPageSize(java.lang.String scaleToPageSize) {
        ( (PrinterDefinitionMaintenanceComponent) getComponent() ).setScaleToPageSize(scaleToPageSize);
    }

    /** Getter for property scaleToPageSize. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property scaleToPageSize.
     */
    public DropDownModel getScaleToPageSizeWM() {
        DropDownModel w_scaleToPageSize = (DropDownModel) getWidgetCache().getModel("scaleToPageSize");
        if (w_scaleToPageSize == null) {
            w_scaleToPageSize = new DropDownModel((getScaleToPageSize() != null ? getScaleToPageSize() : ""), (StringFieldMetaData) PrinterDefinitionMeta.META_SCALE_TO_PAGE_SIZE);
            CodeHelperOutElementDto dto = ((PrinterDefinitionMaintenanceComponent) getComponent()).getScaleToPageSizeCodes();
            if (dto != null && dto.getCodeHelperOutCodeDtoCount() > 0) {
                CodeHelperOutCodeDto[] codes = dto.getCodeHelperOutCodeDtos();
                //Add a blank default option which retains the normal page size.
                w_scaleToPageSize.addOption("",null);
                for (int i = 0; i < codes.length; i++) {
                    CodeHelperOutCodeDto code = codes[i];
                    w_scaleToPageSize.addOption(Formatter.format(code.getDescription()), Formatter.format(code.getCode()));
                }
            }
            // .//GEN-END:scaleToPageSize_1_be
            // Add custom code //GEN-FIRST:scaleToPageSize_1


            // .//GEN-LAST:scaleToPageSize_1
            // .//GEN-BEGIN:scaleToPageSize_2_be
            getWidgetCache().addModel("scaleToPageSize", w_scaleToPageSize);
        }
        return w_scaleToPageSize;
    }

    /** Setter for property scaleToPageSize. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property scaleToPageSize.
     */
    public void setScaleToPageSizeWV(String value) {
        DropDownController.updateModel(value, getScaleToPageSizeWM());
    }
    // .//GEN-END:scaleToPageSize_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        if (!doValidate0(request))
            valid = false;

        // .//GEN-END:_doValidate_1_be
        // Add custom validation code//GEN-FIRST:_doValidate_1


        // .//GEN-LAST:_doValidate_1
        // .//GEN-BEGIN:_doValidate_2_be
        return valid;
    }
    // .//GEN-END:_doValidate_2_be
    // .//GEN-BEGIN:_doValidateMain_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate0(HttpServletRequest request) {
        boolean valid = true;
        try {
            String htmlString = getPrinterIdWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_ID, true);

            setPrinterId(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_ID).getLabelToken(), e);
        }


        try {
            String htmlString = getDescriptionWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) PrinterDefinitionMeta.META_DESCRIPTION, true);

            setDescription(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) PrinterDefinitionMeta.META_DESCRIPTION).getLabelToken(), e);
        }


        try {
            String htmlString = getSiteCodeWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) PrinterDefinitionMeta.META_SITE_CODE, true);

            setSiteCode(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) PrinterDefinitionMeta.META_SITE_CODE).getLabelToken(), e);
        }


        try {
            String htmlString = getLocationCodeWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) PrinterDefinitionMeta.META_LOCATION_CODE, true);

            setLocationCode(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) PrinterDefinitionMeta.META_LOCATION_CODE).getLabelToken(), e);
        }


        try {
            java.lang.Boolean value = new Boolean(getRemoteWM().getState());
            value = FieldValidator.validate(value, (BooleanFieldMetaData) PrinterDefinitionMeta.META_REMOTE, true);

            setRemote(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((BooleanFieldMetaData) PrinterDefinitionMeta.META_REMOTE).getLabelToken(), e);
        }


        try {
            String htmlString = getRealPrinterNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) PrinterDefinitionMeta.META_REAL_PRINTER_NAME, true);

            setRealPrinterName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) PrinterDefinitionMeta.META_REAL_PRINTER_NAME).getLabelToken(), e);
        }


        try {
            String htmlString = getPrinterOption1WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_OPTION1, true);

            setPrinterOption1(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_OPTION1).getLabelToken(), e);
        }


        try {
            String htmlString = getPrinterOption2WM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_OPTION2, true);

            setPrinterOption2(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_OPTION2).getLabelToken(), e);
        }


        try {
            java.lang.String value = getOutputTypeWM().getValue();
            value = FieldValidator.validate(value, (StringFieldMetaData) PrinterDefinitionMeta.META_OUTPUT_TYPE, true);
            setOutputType(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) PrinterDefinitionMeta.META_OUTPUT_TYPE).getLabelToken(), e);
        }


        try {
            String htmlString = getScaleToPageSizeWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) PrinterDefinitionMeta.META_SCALE_TO_PAGE_SIZE, true);

            setScaleToPageSize(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) PrinterDefinitionMeta.META_SCALE_TO_PAGE_SIZE).getLabelToken(), e);
        }


        // .//GEN-END:_doValidateMain_1_be
        // Add custom validation code//GEN-FIRST:_doValidateMain_1


        // .//GEN-LAST:_doValidateMain_1
        // .//GEN-BEGIN:_doValidateMain_2_be
        return valid;
    }
    // .//GEN-END:_doValidateMain_2_be
    // All the custom code goes here//GEN-FIRST:_custom



    // .//GEN-LAST:_custom
}
