// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printerdefinitionfinder.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.*;
import org.jaffa.components.finder.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.util.StringHelper;
import org.jaffa.components.codehelper.dto.*;
import org.jaffa.modules.printing.components.printerdefinitionfinder.dto.PrinterDefinitionFinderOutDto;
import org.jaffa.modules.printing.components.printerdefinitionfinder.dto.PrinterDefinitionFinderOutRowDto;
import org.jaffa.modules.printing.domain.PrinterDefinitionMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support PrinterDefinitionFinder.
 */
public class PrinterDefinitionFinderForm extends FinderForm {

    private static Logger log = Logger.getLogger(PrinterDefinitionFinderForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:printerId_1_be
    /** Getter for property printerId.
     * @return Value of property printerId.
     */
    public String getPrinterId() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getPrinterId();
    }

    /** Setter for property printerId.
     * @param printerId New value of property printerId.
     */
    public void setPrinterId(String printerId) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setPrinterId(printerId);
    }

    /** Getter for property printerId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property printerId.
     */
    public EditBoxModel getPrinterIdWM() {
        EditBoxModel printerIdModel = (EditBoxModel) getWidgetCache().getModel("printerId");
        if (printerIdModel == null) {
            if (getPrinterId() != null)
                printerIdModel = new EditBoxModel( getPrinterId() );
            else
                printerIdModel = new EditBoxModel();
            printerIdModel.setStringCase( ((StringFieldMetaData) PrinterDefinitionMeta.META_PRINTER_ID).getCaseType() );

            // .//GEN-END:printerId_1_be
            // Add custom code //GEN-FIRST:printerId_1


            // .//GEN-LAST:printerId_1
            // .//GEN-BEGIN:printerId_2_be
            getWidgetCache().addModel("printerId", printerIdModel);
        }
        return printerIdModel;
    }

    /** Setter for property printerId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property printerId.
     */
    public void setPrinterIdWV(String value) {
        EditBoxController.updateModel(value, getPrinterIdWM());
    }

    /** Getter for DropDown property printerId.
     * @return Value of property printerIdDd.
     */
    public String getPrinterIdDd() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getPrinterIdDd();
    }

    /** Setter for DropDown property printerId.
     * @param printerIdDd New value of property printerIdDd.
     */
    public void setPrinterIdDd(String printerIdDd) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setPrinterIdDd(printerIdDd);
    }

    /** Getter for DropDown property printerId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property printerIdDd.
     */
    public DropDownModel getPrinterIdDdWM() {
        DropDownModel printerIdDdModel = (DropDownModel) getWidgetCache().getModel("printerIdDd");
        if (printerIdDdModel == null) {
            if (getPrinterIdDd() != null)
                printerIdDdModel = new DropDownModel( getPrinterIdDd() );
            else
                printerIdDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                printerIdDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("printerIdDd", printerIdDdModel);
        }
        return printerIdDdModel;
    }

    /** Setter for DropDown property printerId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property printerIdDd.
     */
    public void setPrinterIdDdWV(String value) {
        DropDownController.updateModel(value, getPrinterIdDdWM());
    }

    // .//GEN-END:printerId_2_be
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setDescription(description);
    }

    /** Getter for property description. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property description.
     */
    public EditBoxModel getDescriptionWM() {
        EditBoxModel descriptionModel = (EditBoxModel) getWidgetCache().getModel("description");
        if (descriptionModel == null) {
            if (getDescription() != null)
                descriptionModel = new EditBoxModel( getDescription() );
            else
                descriptionModel = new EditBoxModel();
            descriptionModel.setStringCase( ((StringFieldMetaData) PrinterDefinitionMeta.META_DESCRIPTION).getCaseType() );

            // .//GEN-END:description_1_be
            // Add custom code //GEN-FIRST:description_1


            // .//GEN-LAST:description_1
            // .//GEN-BEGIN:description_2_be
            getWidgetCache().addModel("description", descriptionModel);
        }
        return descriptionModel;
    }

    /** Setter for property description. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property description.
     */
    public void setDescriptionWV(String value) {
        EditBoxController.updateModel(value, getDescriptionWM());
    }

    /** Getter for DropDown property description.
     * @return Value of property descriptionDd.
     */
    public String getDescriptionDd() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getDescriptionDd();
    }

    /** Setter for DropDown property description.
     * @param descriptionDd New value of property descriptionDd.
     */
    public void setDescriptionDd(String descriptionDd) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setDescriptionDd(descriptionDd);
    }

    /** Getter for DropDown property description. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property descriptionDd.
     */
    public DropDownModel getDescriptionDdWM() {
        DropDownModel descriptionDdModel = (DropDownModel) getWidgetCache().getModel("descriptionDd");
        if (descriptionDdModel == null) {
            if (getDescriptionDd() != null)
                descriptionDdModel = new DropDownModel( getDescriptionDd() );
            else
                descriptionDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                descriptionDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("descriptionDd", descriptionDdModel);
        }
        return descriptionDdModel;
    }

    /** Setter for DropDown property description. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property descriptionDd.
     */
    public void setDescriptionDdWV(String value) {
        DropDownController.updateModel(value, getDescriptionDdWM());
    }

    // .//GEN-END:description_2_be
    // .//GEN-BEGIN:siteCode_1_be
    /** Getter for property siteCode.
     * @return Value of property siteCode.
     */
    public String getSiteCode() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getSiteCode();
    }

    /** Setter for property siteCode.
     * @param siteCode New value of property siteCode.
     */
    public void setSiteCode(String siteCode) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setSiteCode(siteCode);
    }

    /** Getter for property siteCode. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property siteCode.
     */
    public EditBoxModel getSiteCodeWM() {
        EditBoxModel siteCodeModel = (EditBoxModel) getWidgetCache().getModel("siteCode");
        if (siteCodeModel == null) {
            if (getSiteCode() != null)
                siteCodeModel = new EditBoxModel( getSiteCode() );
            else
                siteCodeModel = new EditBoxModel();
            siteCodeModel.setStringCase( ((StringFieldMetaData) PrinterDefinitionMeta.META_SITE_CODE).getCaseType() );

            // .//GEN-END:siteCode_1_be
            // Add custom code //GEN-FIRST:siteCode_1


            // .//GEN-LAST:siteCode_1
            // .//GEN-BEGIN:siteCode_2_be
            getWidgetCache().addModel("siteCode", siteCodeModel);
        }
        return siteCodeModel;
    }

    /** Setter for property siteCode. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property siteCode.
     */
    public void setSiteCodeWV(String value) {
        EditBoxController.updateModel(value, getSiteCodeWM());
    }

    /** Getter for DropDown property siteCode.
     * @return Value of property siteCodeDd.
     */
    public String getSiteCodeDd() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getSiteCodeDd();
    }

    /** Setter for DropDown property siteCode.
     * @param siteCodeDd New value of property siteCodeDd.
     */
    public void setSiteCodeDd(String siteCodeDd) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setSiteCodeDd(siteCodeDd);
    }

    /** Getter for DropDown property siteCode. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property siteCodeDd.
     */
    public DropDownModel getSiteCodeDdWM() {
        DropDownModel siteCodeDdModel = (DropDownModel) getWidgetCache().getModel("siteCodeDd");
        if (siteCodeDdModel == null) {
            if (getSiteCodeDd() != null)
                siteCodeDdModel = new DropDownModel( getSiteCodeDd() );
            else
                siteCodeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                siteCodeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("siteCodeDd", siteCodeDdModel);
        }
        return siteCodeDdModel;
    }

    /** Setter for DropDown property siteCode. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property siteCodeDd.
     */
    public void setSiteCodeDdWV(String value) {
        DropDownController.updateModel(value, getSiteCodeDdWM());
    }

    // .//GEN-END:siteCode_2_be
    // .//GEN-BEGIN:locationCode_1_be
    /** Getter for property locationCode.
     * @return Value of property locationCode.
     */
    public String getLocationCode() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getLocationCode();
    }

    /** Setter for property locationCode.
     * @param locationCode New value of property locationCode.
     */
    public void setLocationCode(String locationCode) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setLocationCode(locationCode);
    }

    /** Getter for property locationCode. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property locationCode.
     */
    public EditBoxModel getLocationCodeWM() {
        EditBoxModel locationCodeModel = (EditBoxModel) getWidgetCache().getModel("locationCode");
        if (locationCodeModel == null) {
            if (getLocationCode() != null)
                locationCodeModel = new EditBoxModel( getLocationCode() );
            else
                locationCodeModel = new EditBoxModel();
            locationCodeModel.setStringCase( ((StringFieldMetaData) PrinterDefinitionMeta.META_LOCATION_CODE).getCaseType() );

            // .//GEN-END:locationCode_1_be
            // Add custom code //GEN-FIRST:locationCode_1


            // .//GEN-LAST:locationCode_1
            // .//GEN-BEGIN:locationCode_2_be
            getWidgetCache().addModel("locationCode", locationCodeModel);
        }
        return locationCodeModel;
    }

    /** Setter for property locationCode. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property locationCode.
     */
    public void setLocationCodeWV(String value) {
        EditBoxController.updateModel(value, getLocationCodeWM());
    }

    /** Getter for DropDown property locationCode.
     * @return Value of property locationCodeDd.
     */
    public String getLocationCodeDd() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getLocationCodeDd();
    }

    /** Setter for DropDown property locationCode.
     * @param locationCodeDd New value of property locationCodeDd.
     */
    public void setLocationCodeDd(String locationCodeDd) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setLocationCodeDd(locationCodeDd);
    }

    /** Getter for DropDown property locationCode. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property locationCodeDd.
     */
    public DropDownModel getLocationCodeDdWM() {
        DropDownModel locationCodeDdModel = (DropDownModel) getWidgetCache().getModel("locationCodeDd");
        if (locationCodeDdModel == null) {
            if (getLocationCodeDd() != null)
                locationCodeDdModel = new DropDownModel( getLocationCodeDd() );
            else
                locationCodeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                locationCodeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("locationCodeDd", locationCodeDdModel);
        }
        return locationCodeDdModel;
    }

    /** Setter for DropDown property locationCode. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property locationCodeDd.
     */
    public void setLocationCodeDdWV(String value) {
        DropDownController.updateModel(value, getLocationCodeDdWM());
    }

    // .//GEN-END:locationCode_2_be
    // .//GEN-BEGIN:realPrinterName_1_be
    /** Getter for property realPrinterName.
     * @return Value of property realPrinterName.
     */
    public String getRealPrinterName() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getRealPrinterName();
    }

    /** Setter for property realPrinterName.
     * @param realPrinterName New value of property realPrinterName.
     */
    public void setRealPrinterName(String realPrinterName) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setRealPrinterName(realPrinterName);
    }

    /** Getter for property realPrinterName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property realPrinterName.
     */
    public EditBoxModel getRealPrinterNameWM() {
        EditBoxModel realPrinterNameModel = (EditBoxModel) getWidgetCache().getModel("realPrinterName");
        if (realPrinterNameModel == null) {
            if (getRealPrinterName() != null)
                realPrinterNameModel = new EditBoxModel( getRealPrinterName() );
            else
                realPrinterNameModel = new EditBoxModel();
            realPrinterNameModel.setStringCase( ((StringFieldMetaData) PrinterDefinitionMeta.META_REAL_PRINTER_NAME).getCaseType() );

            // .//GEN-END:realPrinterName_1_be
            // Add custom code //GEN-FIRST:realPrinterName_1


            // .//GEN-LAST:realPrinterName_1
            // .//GEN-BEGIN:realPrinterName_2_be
            getWidgetCache().addModel("realPrinterName", realPrinterNameModel);
        }
        return realPrinterNameModel;
    }

    /** Setter for property realPrinterName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property realPrinterName.
     */
    public void setRealPrinterNameWV(String value) {
        EditBoxController.updateModel(value, getRealPrinterNameWM());
    }

    /** Getter for DropDown property realPrinterName.
     * @return Value of property realPrinterNameDd.
     */
    public String getRealPrinterNameDd() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getRealPrinterNameDd();
    }

    /** Setter for DropDown property realPrinterName.
     * @param realPrinterNameDd New value of property realPrinterNameDd.
     */
    public void setRealPrinterNameDd(String realPrinterNameDd) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setRealPrinterNameDd(realPrinterNameDd);
    }

    /** Getter for DropDown property realPrinterName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property realPrinterNameDd.
     */
    public DropDownModel getRealPrinterNameDdWM() {
        DropDownModel realPrinterNameDdModel = (DropDownModel) getWidgetCache().getModel("realPrinterNameDd");
        if (realPrinterNameDdModel == null) {
            if (getRealPrinterNameDd() != null)
                realPrinterNameDdModel = new DropDownModel( getRealPrinterNameDd() );
            else
                realPrinterNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                realPrinterNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("realPrinterNameDd", realPrinterNameDdModel);
        }
        return realPrinterNameDdModel;
    }

    /** Setter for DropDown property realPrinterName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property realPrinterNameDd.
     */
    public void setRealPrinterNameDdWV(String value) {
        DropDownController.updateModel(value, getRealPrinterNameDdWM());
    }

    // .//GEN-END:realPrinterName_2_be
    // .//GEN-BEGIN:outputType_1_be
    /** Getter for property outputType.
     * @return Value of property outputType.
     */
    public String getOutputType() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getOutputType();
    }

    /** Setter for property outputType.
     * @param outputType New value of property outputType.
     */
    public void setOutputType(String outputType) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setOutputType(outputType);
    }

    /** Getter for property outputType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property outputType.
     */
    public DropDownModel getOutputTypeWM() {
        DropDownModel outputTypeModel = (DropDownModel) getWidgetCache().getModel("outputType");
        if (outputTypeModel == null) {
            if (getOutputType() != null)
                outputTypeModel = new DropDownModel( getOutputType() );
            else
                outputTypeModel = new DropDownModel("");
            outputTypeModel.addOption("[label.Jaffa.Inquiry.CriteriaOption.Any]", "");
            CodeHelperOutElementDto dto = ( (PrinterDefinitionFinderComponent) getComponent() ).getOutputTypeCodes();
            if (dto != null && dto.getCodeHelperOutCodeDtoCount() > 0) {
                CodeHelperOutCodeDto[] codes = dto.getCodeHelperOutCodeDtos();
                for (int i = 0; i < codes.length; i++) {
                    CodeHelperOutCodeDto code = codes[i];
                    outputTypeModel.addOption(Formatter.format(code.getDescription()), Formatter.format(code.getCode()));
                }
            }

            // .//GEN-END:outputType_1_be
            // Add custom code //GEN-FIRST:outputType_1


            // .//GEN-LAST:outputType_1
            // .//GEN-BEGIN:outputType_2_be
            getWidgetCache().addModel("outputType", outputTypeModel);
        }
        return outputTypeModel;
    }

    /** Setter for property outputType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property outputType.
     */
    public void setOutputTypeWV(String value) {
        DropDownController.updateModel(value, getOutputTypeWM());
    }

    /** Getter for DropDown property outputType.
     * @return Value of property outputTypeDd.
     */
    public String getOutputTypeDd() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getOutputTypeDd();
    }

    /** Setter for DropDown property outputType.
     * @param outputTypeDd New value of property outputTypeDd.
     */
    public void setOutputTypeDd(String outputTypeDd) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setOutputTypeDd(outputTypeDd);
    }

    /** Getter for DropDown property outputType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property outputTypeDd.
     */
    public DropDownModel getOutputTypeDdWM() {
        DropDownModel outputTypeDdModel = (DropDownModel) getWidgetCache().getModel("outputTypeDd");
        if (outputTypeDdModel == null) {
            if (getOutputTypeDd() != null)
                outputTypeDdModel = new DropDownModel( getOutputTypeDd() );
            else
                outputTypeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getDropDownCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                outputTypeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("outputTypeDd", outputTypeDdModel);
        }
        return outputTypeDdModel;
    }

    /** Setter for DropDown property outputType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property outputTypeDd.
     */
    public void setOutputTypeDdWV(String value) {
        DropDownController.updateModel(value, getOutputTypeDdWM());
    }

    // .//GEN-END:outputType_2_be
    // .//GEN-BEGIN:scaleToPageSize_1_be
    /** Getter for property scaleToPageSize.
     * @return Value of property scaleToPageSize.
     */
    public String getScaleToPageSize() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getScaleToPageSize();
    }

    /** Setter for property scaleToPageSize.
     * @param scaleToPageSize New value of property scaleToPageSize.
     */
    public void setScaleToPageSize(String scaleToPageSize) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setScaleToPageSize(scaleToPageSize);
    }

     /** Getter for property scaleToPageSize. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property scaleToPageSize.
     */
    public DropDownModel getScaleToPageSizeWM() {
        DropDownModel scaleToPageSizeModel = (DropDownModel) getWidgetCache().getModel("scaleToPageSize");
        if (scaleToPageSizeModel == null) {
            if (getScaleToPageSize() != null)
                scaleToPageSizeModel = new DropDownModel( getScaleToPageSize() );
            else
                scaleToPageSizeModel = new DropDownModel("");
            scaleToPageSizeModel.addOption("[label.Jaffa.Inquiry.CriteriaOption.Any]", "");
            CodeHelperOutElementDto dto = ( (PrinterDefinitionFinderComponent) getComponent() ).getScaleToPageSizeCodes();
            if (dto != null && dto.getCodeHelperOutCodeDtoCount() > 0) {
                CodeHelperOutCodeDto[] codes = dto.getCodeHelperOutCodeDtos();
                for (int i = 0; i < codes.length; i++) {
                    CodeHelperOutCodeDto code = codes[i];
                    scaleToPageSizeModel.addOption(Formatter.format(code.getDescription()), Formatter.format(code.getCode()));
                }
            }
            // .//GEN-END:scaleToPageSize_1_be
            // Add custom code //GEN-FIRST:scaleToPageSize_1


            // .//GEN-LAST:scaleToPageSize_1
            // .//GEN-BEGIN:scaleToPageSize_2_be
            getWidgetCache().addModel("scaleToPageSize", scaleToPageSizeModel);
        }
        return scaleToPageSizeModel;
    }

    /** Setter for property scaleToPageSize. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property scaleToPageSize.
     */
    public void setScaleToPageSizeWV(String value) {
        DropDownController.updateModel(value, getScaleToPageSizeWM());
    }

    /** Getter for DropDown property scaleToPageSize.
     * @return Value of property scaleToPageSizeDd.
     */
    public String getScaleToPageSizeDd() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getScaleToPageSizeDd();
    }

    /** Setter for DropDown property scaleToPageSize.
     * @param scaleToPageSizeDd New value of property scaleToPageSizeDd.
     */
    public void setScaleToPageSizeDd(String scaleToPageSizeDd) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setScaleToPageSizeDd(scaleToPageSizeDd);
    }

    /** Getter for DropDown property scaleToPageSize. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property scaleToPageSizeDd.
     */
    public DropDownModel getScaleToPageSizeDdWM() {
        DropDownModel scaleToPageSizeDdModel = (DropDownModel) getWidgetCache().getModel("scaleToPageSizeDd");
        if (scaleToPageSizeDdModel == null) {
            if (getScaleToPageSizeDd() != null)
                scaleToPageSizeDdModel = new DropDownModel( getScaleToPageSizeDd() );
            else
                scaleToPageSizeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                scaleToPageSizeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("scaleToPageSizeDd", scaleToPageSizeDdModel);
        }
        return scaleToPageSizeDdModel;
    }

    /** Setter for DropDown property scaleToPageSize. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property scaleToPageSizeDd.
     */
    public void setScaleToPageSizeDdWV(String value) {
        DropDownController.updateModel(value, getScaleToPageSizeDdWM());
    }

    // .//GEN-END:scaleToPageSize_2_be
    // .//GEN-BEGIN:remote_1_be
    /** Getter for property remote.
     * @return Value of property remote.
     */
    public String getRemote() {
        return ( (PrinterDefinitionFinderComponent) getComponent() ).getRemote();
    }

    /** Setter for property remote.
     * @param remote New value of property remote.
     */
    public void setRemote(String remote) {
        ( (PrinterDefinitionFinderComponent) getComponent() ).setRemote(remote);
    }

    /** Getter for property remote. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remote.
     */
    public DropDownModel getRemoteWM() {
        DropDownModel remoteModel = (DropDownModel) getWidgetCache().getModel("remote");
        if (remoteModel == null) {
            if (getRemote() != null)
                remoteModel = new DropDownModel( getRemote() );
            else
                remoteModel = new DropDownModel("");
            Map optionsMap = CriteriaDropDownOptions.getBooleanCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                remoteModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            // .//GEN-END:remote_1_be
            // Add custom code //GEN-FIRST:remote_1


            // .//GEN-LAST:remote_1
            // .//GEN-BEGIN:remote_2_be
            getWidgetCache().addModel("remote", remoteModel);
        }
        return remoteModel;
    }

    /** Setter for property remote. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property remote.
     */
    public void setRemoteWV(String value) {
        DropDownController.updateModel(value, getRemoteWM());
    }
    // .//GEN-END:remote_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        String value = null;
        StringBuffer buf = null;

        value = getPrinterIdWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setPrinterId(value);
        setPrinterIdDd(getPrinterIdDdWM().getValue());

        value = getDescriptionWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDescription(value);
        setDescriptionDd(getDescriptionDdWM().getValue());

        value = getSiteCodeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSiteCode(value);
        setSiteCodeDd(getSiteCodeDdWM().getValue());

        value = getLocationCodeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLocationCode(value);
        setLocationCodeDd(getLocationCodeDdWM().getValue());

        value = getRealPrinterNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setRealPrinterName(value);
        setRealPrinterNameDd(getRealPrinterNameDdWM().getValue());

        value = getOutputTypeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setOutputType(value);
        setOutputTypeDd(getOutputTypeDdWM().getValue());

        value = getScaleToPageSizeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setScaleToPageSize(value);
        setScaleToPageSizeDd(getScaleToPageSizeDdWM().getValue());

        value = getRemoteWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setRemote(value);

        // .//GEN-END:_doValidate_1_be
        // Add custom code //GEN-FIRST:_doValidate_1



        // .//GEN-LAST:_doValidate_1
        // .//GEN-BEGIN:_doValidate_2_be
        return valid;
    }
    // .//GEN-END:_doValidate_2_be
    // .//GEN-BEGIN:_populateRows_1_be
    /** This will populate the input GridModel with the data in the finderOutDto of the Component.
     * @param rows The GridModel object to populate.
     */
    public void populateRows(GridModel rows) {
        rows.clearRows();
        PrinterDefinitionFinderOutDto outputDto = (PrinterDefinitionFinderOutDto) ((PrinterDefinitionFinderComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                PrinterDefinitionFinderOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement("printerId", rowDto.getPrinterId());
                row.addElement("description", rowDto.getDescription());
                row.addElement("siteCode", rowDto.getSiteCode());
                row.addElement("locationCode", rowDto.getLocationCode());
                row.addElement("remote", new CheckBoxModel( (rowDto.getRemote() != null ? rowDto.getRemote() : Boolean.FALSE) ));
                row.addElement("realPrinterName", rowDto.getRealPrinterName());
                row.addElement("outputType", rowDto.getOutputType());
                row.addElement("scaleToPageSize", rowDto.getScaleToPageSize());
                // .//GEN-END:_populateRows_1_be
                // Add custom code for the row //GEN-FIRST:_populateRows_1


                // .//GEN-LAST:_populateRows_1
                // .//GEN-BEGIN:_populateRows_2_be
            }
        }
    }
    // .//GEN-END:_populateRows_2_be
    // All the custom code goes here //GEN-FIRST:_custom


    // .//GEN-LAST:_custom
}
