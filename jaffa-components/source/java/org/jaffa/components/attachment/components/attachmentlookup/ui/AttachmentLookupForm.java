// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.components.attachment.components.attachmentlookup.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.lookup.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.util.StringHelper;
import org.jaffa.components.attachment.components.attachmentlookup.dto.AttachmentLookupOutDto;
import org.jaffa.components.attachment.components.attachmentlookup.dto.AttachmentLookupOutRowDto;
import org.jaffa.components.attachment.domain.AttachmentMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support AttachmentLookup.
 */
public class AttachmentLookupForm extends LookupForm {

    private static Logger log = Logger.getLogger(AttachmentLookupForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:attachmentId_1_be
    /** Getter for property attachmentId.
     * @return Value of property attachmentId.
     */
    public String getAttachmentId() {
        return ( (AttachmentLookupComponent) getComponent() ).getAttachmentId();
    }

    /** Setter for property attachmentId.
     * @param attachmentId New value of property attachmentId.
     */
    public void setAttachmentId(String attachmentId) {
        ( (AttachmentLookupComponent) getComponent() ).setAttachmentId(attachmentId);
    }

    /** Getter for property attachmentId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property attachmentId.
     */
    public EditBoxModel getAttachmentIdWM() {
        EditBoxModel attachmentIdModel = (EditBoxModel) getWidgetCache().getModel("attachmentId");
        if (attachmentIdModel == null) {
            if (getAttachmentId() != null)
                attachmentIdModel = new EditBoxModel( getAttachmentId() );
            else
                attachmentIdModel = new EditBoxModel();
            attachmentIdModel.setStringCase( ((StringFieldMetaData) AttachmentMeta.META_ATTACHMENT_ID).getCaseType() );

            // .//GEN-END:attachmentId_1_be
            // Add custom code//GEN-FIRST:attachmentId_1


            // .//GEN-LAST:attachmentId_1
            // .//GEN-BEGIN:attachmentId_2_be
            getWidgetCache().addModel("attachmentId", attachmentIdModel);
        }
        return attachmentIdModel;
    }

    /** Setter for property attachmentId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property attachmentId.
     */
    public void setAttachmentIdWV(String value) {
        EditBoxController.updateModel(value, getAttachmentIdWM());
    }

    /** Getter for DropDown property attachmentId.
     * @return Value of property attachmentIdDd.
     */
    public String getAttachmentIdDd() {
        return ( (AttachmentLookupComponent) getComponent() ).getAttachmentIdDd();
    }

    /** Setter for DropDown property attachmentId.
     * @param attachmentIdDd New value of property attachmentIdDd.
     */
    public void setAttachmentIdDd(String attachmentIdDd) {
        ( (AttachmentLookupComponent) getComponent() ).setAttachmentIdDd(attachmentIdDd);
    }

    /** Getter for DropDown property attachmentId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property attachmentIdDd.
     */
    public DropDownModel getAttachmentIdDdWM() {
        DropDownModel attachmentIdDdModel = (DropDownModel) getWidgetCache().getModel("attachmentIdDd");
        if (attachmentIdDdModel == null) {
            if (getAttachmentIdDd() != null)
                attachmentIdDdModel = new DropDownModel( getAttachmentIdDd() );
            else
                attachmentIdDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                attachmentIdDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("attachmentIdDd", attachmentIdDdModel);
        }
        return attachmentIdDdModel;
    }

    /** Setter for DropDown property attachmentId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property attachmentIdDd.
     */
    public void setAttachmentIdDdWV(String value) {
        DropDownController.updateModel(value, getAttachmentIdDdWM());
    }

    // .//GEN-END:attachmentId_2_be
    // .//GEN-BEGIN:serializedKey_1_be
    /** Getter for property serializedKey.
     * @return Value of property serializedKey.
     */
    public String getSerializedKey() {
        return ( (AttachmentLookupComponent) getComponent() ).getSerializedKey();
    }

    /** Setter for property serializedKey.
     * @param serializedKey New value of property serializedKey.
     */
    public void setSerializedKey(String serializedKey) {
        ( (AttachmentLookupComponent) getComponent() ).setSerializedKey(serializedKey);
    }

    /** Getter for property serializedKey. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property serializedKey.
     */
    public EditBoxModel getSerializedKeyWM() {
        EditBoxModel serializedKeyModel = (EditBoxModel) getWidgetCache().getModel("serializedKey");
        if (serializedKeyModel == null) {
            if (getSerializedKey() != null)
                serializedKeyModel = new EditBoxModel( getSerializedKey() );
            else
                serializedKeyModel = new EditBoxModel();
            serializedKeyModel.setStringCase( ((StringFieldMetaData) AttachmentMeta.META_SERIALIZED_KEY).getCaseType() );

            // .//GEN-END:serializedKey_1_be
            // Add custom code//GEN-FIRST:serializedKey_1


            // .//GEN-LAST:serializedKey_1
            // .//GEN-BEGIN:serializedKey_2_be
            getWidgetCache().addModel("serializedKey", serializedKeyModel);
        }
        return serializedKeyModel;
    }

    /** Setter for property serializedKey. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property serializedKey.
     */
    public void setSerializedKeyWV(String value) {
        EditBoxController.updateModel(value, getSerializedKeyWM());
    }

    /** Getter for DropDown property serializedKey.
     * @return Value of property serializedKeyDd.
     */
    public String getSerializedKeyDd() {
        return ( (AttachmentLookupComponent) getComponent() ).getSerializedKeyDd();
    }

    /** Setter for DropDown property serializedKey.
     * @param serializedKeyDd New value of property serializedKeyDd.
     */
    public void setSerializedKeyDd(String serializedKeyDd) {
        ( (AttachmentLookupComponent) getComponent() ).setSerializedKeyDd(serializedKeyDd);
    }

    /** Getter for DropDown property serializedKey. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property serializedKeyDd.
     */
    public DropDownModel getSerializedKeyDdWM() {
        DropDownModel serializedKeyDdModel = (DropDownModel) getWidgetCache().getModel("serializedKeyDd");
        if (serializedKeyDdModel == null) {
            if (getSerializedKeyDd() != null)
                serializedKeyDdModel = new DropDownModel( getSerializedKeyDd() );
            else
                serializedKeyDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                serializedKeyDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("serializedKeyDd", serializedKeyDdModel);
        }
        return serializedKeyDdModel;
    }

    /** Setter for DropDown property serializedKey. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property serializedKeyDd.
     */
    public void setSerializedKeyDdWV(String value) {
        DropDownController.updateModel(value, getSerializedKeyDdWM());
    }

    // .//GEN-END:serializedKey_2_be
    // .//GEN-BEGIN:originalFileName_1_be
    /** Getter for property originalFileName.
     * @return Value of property originalFileName.
     */
    public String getOriginalFileName() {
        return ( (AttachmentLookupComponent) getComponent() ).getOriginalFileName();
    }

    /** Setter for property originalFileName.
     * @param originalFileName New value of property originalFileName.
     */
    public void setOriginalFileName(String originalFileName) {
        ( (AttachmentLookupComponent) getComponent() ).setOriginalFileName(originalFileName);
    }

    /** Getter for property originalFileName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property originalFileName.
     */
    public EditBoxModel getOriginalFileNameWM() {
        EditBoxModel originalFileNameModel = (EditBoxModel) getWidgetCache().getModel("originalFileName");
        if (originalFileNameModel == null) {
            if (getOriginalFileName() != null)
                originalFileNameModel = new EditBoxModel( getOriginalFileName() );
            else
                originalFileNameModel = new EditBoxModel();
            originalFileNameModel.setStringCase( ((StringFieldMetaData) AttachmentMeta.META_ORIGINAL_FILE_NAME).getCaseType() );

            // .//GEN-END:originalFileName_1_be
            // Add custom code//GEN-FIRST:originalFileName_1


            // .//GEN-LAST:originalFileName_1
            // .//GEN-BEGIN:originalFileName_2_be
            getWidgetCache().addModel("originalFileName", originalFileNameModel);
        }
        return originalFileNameModel;
    }

    /** Setter for property originalFileName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property originalFileName.
     */
    public void setOriginalFileNameWV(String value) {
        EditBoxController.updateModel(value, getOriginalFileNameWM());
    }

    /** Getter for DropDown property originalFileName.
     * @return Value of property originalFileNameDd.
     */
    public String getOriginalFileNameDd() {
        return ( (AttachmentLookupComponent) getComponent() ).getOriginalFileNameDd();
    }

    /** Setter for DropDown property originalFileName.
     * @param originalFileNameDd New value of property originalFileNameDd.
     */
    public void setOriginalFileNameDd(String originalFileNameDd) {
        ( (AttachmentLookupComponent) getComponent() ).setOriginalFileNameDd(originalFileNameDd);
    }

    /** Getter for DropDown property originalFileName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property originalFileNameDd.
     */
    public DropDownModel getOriginalFileNameDdWM() {
        DropDownModel originalFileNameDdModel = (DropDownModel) getWidgetCache().getModel("originalFileNameDd");
        if (originalFileNameDdModel == null) {
            if (getOriginalFileNameDd() != null)
                originalFileNameDdModel = new DropDownModel( getOriginalFileNameDd() );
            else
                originalFileNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                originalFileNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("originalFileNameDd", originalFileNameDdModel);
        }
        return originalFileNameDdModel;
    }

    /** Setter for DropDown property originalFileName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property originalFileNameDd.
     */
    public void setOriginalFileNameDdWV(String value) {
        DropDownController.updateModel(value, getOriginalFileNameDdWM());
    }

    // .//GEN-END:originalFileName_2_be
    // .//GEN-BEGIN:attachmentType_1_be
    /** Getter for property attachmentType.
     * @return Value of property attachmentType.
     */
    public String getAttachmentType() {
        return ( (AttachmentLookupComponent) getComponent() ).getAttachmentType();
    }

    /** Setter for property attachmentType.
     * @param attachmentType New value of property attachmentType.
     */
    public void setAttachmentType(String attachmentType) {
        ( (AttachmentLookupComponent) getComponent() ).setAttachmentType(attachmentType);
    }

    /** Getter for property attachmentType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property attachmentType.
     */
    public EditBoxModel getAttachmentTypeWM() {
        EditBoxModel attachmentTypeModel = (EditBoxModel) getWidgetCache().getModel("attachmentType");
        if (attachmentTypeModel == null) {
            if (getAttachmentType() != null)
                attachmentTypeModel = new EditBoxModel( getAttachmentType() );
            else
                attachmentTypeModel = new EditBoxModel();
            attachmentTypeModel.setStringCase( ((StringFieldMetaData) AttachmentMeta.META_ATTACHMENT_TYPE).getCaseType() );

            // .//GEN-END:attachmentType_1_be
            // Add custom code//GEN-FIRST:attachmentType_1


            // .//GEN-LAST:attachmentType_1
            // .//GEN-BEGIN:attachmentType_2_be
            getWidgetCache().addModel("attachmentType", attachmentTypeModel);
        }
        return attachmentTypeModel;
    }

    /** Setter for property attachmentType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property attachmentType.
     */
    public void setAttachmentTypeWV(String value) {
        EditBoxController.updateModel(value, getAttachmentTypeWM());
    }

    /** Getter for DropDown property attachmentType.
     * @return Value of property attachmentTypeDd.
     */
    public String getAttachmentTypeDd() {
        return ( (AttachmentLookupComponent) getComponent() ).getAttachmentTypeDd();
    }

    /** Setter for DropDown property attachmentType.
     * @param attachmentTypeDd New value of property attachmentTypeDd.
     */
    public void setAttachmentTypeDd(String attachmentTypeDd) {
        ( (AttachmentLookupComponent) getComponent() ).setAttachmentTypeDd(attachmentTypeDd);
    }

    /** Getter for DropDown property attachmentType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property attachmentTypeDd.
     */
    public DropDownModel getAttachmentTypeDdWM() {
        DropDownModel attachmentTypeDdModel = (DropDownModel) getWidgetCache().getModel("attachmentTypeDd");
        if (attachmentTypeDdModel == null) {
            if (getAttachmentTypeDd() != null)
                attachmentTypeDdModel = new DropDownModel( getAttachmentTypeDd() );
            else
                attachmentTypeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                attachmentTypeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("attachmentTypeDd", attachmentTypeDdModel);
        }
        return attachmentTypeDdModel;
    }

    /** Setter for DropDown property attachmentType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property attachmentTypeDd.
     */
    public void setAttachmentTypeDdWV(String value) {
        DropDownController.updateModel(value, getAttachmentTypeDdWM());
    }

    // .//GEN-END:attachmentType_2_be
    // .//GEN-BEGIN:contentType_1_be
    /** Getter for property contentType.
     * @return Value of property contentType.
     */
    public String getContentType() {
        return ( (AttachmentLookupComponent) getComponent() ).getContentType();
    }

    /** Setter for property contentType.
     * @param contentType New value of property contentType.
     */
    public void setContentType(String contentType) {
        ( (AttachmentLookupComponent) getComponent() ).setContentType(contentType);
    }

    /** Getter for property contentType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property contentType.
     */
    public EditBoxModel getContentTypeWM() {
        EditBoxModel contentTypeModel = (EditBoxModel) getWidgetCache().getModel("contentType");
        if (contentTypeModel == null) {
            if (getContentType() != null)
                contentTypeModel = new EditBoxModel( getContentType() );
            else
                contentTypeModel = new EditBoxModel();
            contentTypeModel.setStringCase( ((StringFieldMetaData) AttachmentMeta.META_CONTENT_TYPE).getCaseType() );

            // .//GEN-END:contentType_1_be
            // Add custom code//GEN-FIRST:contentType_1


            // .//GEN-LAST:contentType_1
            // .//GEN-BEGIN:contentType_2_be
            getWidgetCache().addModel("contentType", contentTypeModel);
        }
        return contentTypeModel;
    }

    /** Setter for property contentType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property contentType.
     */
    public void setContentTypeWV(String value) {
        EditBoxController.updateModel(value, getContentTypeWM());
    }

    /** Getter for DropDown property contentType.
     * @return Value of property contentTypeDd.
     */
    public String getContentTypeDd() {
        return ( (AttachmentLookupComponent) getComponent() ).getContentTypeDd();
    }

    /** Setter for DropDown property contentType.
     * @param contentTypeDd New value of property contentTypeDd.
     */
    public void setContentTypeDd(String contentTypeDd) {
        ( (AttachmentLookupComponent) getComponent() ).setContentTypeDd(contentTypeDd);
    }

    /** Getter for DropDown property contentType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property contentTypeDd.
     */
    public DropDownModel getContentTypeDdWM() {
        DropDownModel contentTypeDdModel = (DropDownModel) getWidgetCache().getModel("contentTypeDd");
        if (contentTypeDdModel == null) {
            if (getContentTypeDd() != null)
                contentTypeDdModel = new DropDownModel( getContentTypeDd() );
            else
                contentTypeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                contentTypeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("contentTypeDd", contentTypeDdModel);
        }
        return contentTypeDdModel;
    }

    /** Setter for DropDown property contentType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property contentTypeDd.
     */
    public void setContentTypeDdWV(String value) {
        DropDownController.updateModel(value, getContentTypeDdWM());
    }

    // .//GEN-END:contentType_2_be
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return ( (AttachmentLookupComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        ( (AttachmentLookupComponent) getComponent() ).setDescription(description);
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
            descriptionModel.setStringCase( ((StringFieldMetaData) AttachmentMeta.META_DESCRIPTION).getCaseType() );

            // .//GEN-END:description_1_be
            // Add custom code//GEN-FIRST:description_1


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
        return ( (AttachmentLookupComponent) getComponent() ).getDescriptionDd();
    }

    /** Setter for DropDown property description.
     * @param descriptionDd New value of property descriptionDd.
     */
    public void setDescriptionDd(String descriptionDd) {
        ( (AttachmentLookupComponent) getComponent() ).setDescriptionDd(descriptionDd);
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
    // .//GEN-BEGIN:remarks_1_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public String getRemarks() {
        return ( (AttachmentLookupComponent) getComponent() ).getRemarks();
    }

    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(String remarks) {
        ( (AttachmentLookupComponent) getComponent() ).setRemarks(remarks);
    }

    /** Getter for property remarks. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remarks.
     */
    public EditBoxModel getRemarksWM() {
        EditBoxModel remarksModel = (EditBoxModel) getWidgetCache().getModel("remarks");
        if (remarksModel == null) {
            if (getRemarks() != null)
                remarksModel = new EditBoxModel( getRemarks() );
            else
                remarksModel = new EditBoxModel();
            remarksModel.setStringCase( ((StringFieldMetaData) AttachmentMeta.META_REMARKS).getCaseType() );

            // .//GEN-END:remarks_1_be
            // Add custom code//GEN-FIRST:remarks_1


            // .//GEN-LAST:remarks_1
            // .//GEN-BEGIN:remarks_2_be
            getWidgetCache().addModel("remarks", remarksModel);
        }
        return remarksModel;
    }

    /** Setter for property remarks. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property remarks.
     */
    public void setRemarksWV(String value) {
        EditBoxController.updateModel(value, getRemarksWM());
    }

    /** Getter for DropDown property remarks.
     * @return Value of property remarksDd.
     */
    public String getRemarksDd() {
        return ( (AttachmentLookupComponent) getComponent() ).getRemarksDd();
    }

    /** Setter for DropDown property remarks.
     * @param remarksDd New value of property remarksDd.
     */
    public void setRemarksDd(String remarksDd) {
        ( (AttachmentLookupComponent) getComponent() ).setRemarksDd(remarksDd);
    }

    /** Getter for DropDown property remarks. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remarksDd.
     */
    public DropDownModel getRemarksDdWM() {
        DropDownModel remarksDdModel = (DropDownModel) getWidgetCache().getModel("remarksDd");
        if (remarksDdModel == null) {
            if (getRemarksDd() != null)
                remarksDdModel = new DropDownModel( getRemarksDd() );
            else
                remarksDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                remarksDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("remarksDd", remarksDdModel);
        }
        return remarksDdModel;
    }

    /** Setter for DropDown property remarks. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property remarksDd.
     */
    public void setRemarksDdWV(String value) {
        DropDownController.updateModel(value, getRemarksDdWM());
    }

    // .//GEN-END:remarks_2_be
    // .//GEN-BEGIN:supercededBy_1_be
    /** Getter for property supercededBy.
     * @return Value of property supercededBy.
     */
    public String getSupercededBy() {
        return ( (AttachmentLookupComponent) getComponent() ).getSupercededBy();
    }

    /** Setter for property supercededBy.
     * @param supercededBy New value of property supercededBy.
     */
    public void setSupercededBy(String supercededBy) {
        ( (AttachmentLookupComponent) getComponent() ).setSupercededBy(supercededBy);
    }

    /** Getter for property supercededBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property supercededBy.
     */
    public EditBoxModel getSupercededByWM() {
        EditBoxModel supercededByModel = (EditBoxModel) getWidgetCache().getModel("supercededBy");
        if (supercededByModel == null) {
            if (getSupercededBy() != null)
                supercededByModel = new EditBoxModel( getSupercededBy() );
            else
                supercededByModel = new EditBoxModel();
            supercededByModel.setStringCase( ((StringFieldMetaData) AttachmentMeta.META_SUPERCEDED_BY).getCaseType() );

            // .//GEN-END:supercededBy_1_be
            // Add custom code//GEN-FIRST:supercededBy_1


            // .//GEN-LAST:supercededBy_1
            // .//GEN-BEGIN:supercededBy_2_be
            getWidgetCache().addModel("supercededBy", supercededByModel);
        }
        return supercededByModel;
    }

    /** Setter for property supercededBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property supercededBy.
     */
    public void setSupercededByWV(String value) {
        EditBoxController.updateModel(value, getSupercededByWM());
    }

    /** Getter for DropDown property supercededBy.
     * @return Value of property supercededByDd.
     */
    public String getSupercededByDd() {
        return ( (AttachmentLookupComponent) getComponent() ).getSupercededByDd();
    }

    /** Setter for DropDown property supercededBy.
     * @param supercededByDd New value of property supercededByDd.
     */
    public void setSupercededByDd(String supercededByDd) {
        ( (AttachmentLookupComponent) getComponent() ).setSupercededByDd(supercededByDd);
    }

    /** Getter for DropDown property supercededBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property supercededByDd.
     */
    public DropDownModel getSupercededByDdWM() {
        DropDownModel supercededByDdModel = (DropDownModel) getWidgetCache().getModel("supercededByDd");
        if (supercededByDdModel == null) {
            if (getSupercededByDd() != null)
                supercededByDdModel = new DropDownModel( getSupercededByDd() );
            else
                supercededByDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                supercededByDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("supercededByDd", supercededByDdModel);
        }
        return supercededByDdModel;
    }

    /** Setter for DropDown property supercededBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property supercededByDd.
     */
    public void setSupercededByDdWV(String value) {
        DropDownController.updateModel(value, getSupercededByDdWM());
    }

    // .//GEN-END:supercededBy_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public String getCreatedOn() {
        return ( (AttachmentLookupComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(String createdOn) {
        ( (AttachmentLookupComponent) getComponent() ).setCreatedOn(createdOn);
    }

    /** Getter for property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOn.
     */
    public EditBoxModel getCreatedOnWM() {
        EditBoxModel createdOnModel = (EditBoxModel) getWidgetCache().getModel("createdOn");
        if (createdOnModel == null) {
            if (getCreatedOn() != null)
                createdOnModel = new EditBoxModel( getCreatedOn() );
            else
                createdOnModel = new EditBoxModel();

            // .//GEN-END:createdOn_1_be
            // Add custom code//GEN-FIRST:createdOn_1


            // .//GEN-LAST:createdOn_1
            // .//GEN-BEGIN:createdOn_2_be
            getWidgetCache().addModel("createdOn", createdOnModel);
        }
        return createdOnModel;
    }

    /** Setter for property createdOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property createdOn.
     */
    public void setCreatedOnWV(String value) {
        EditBoxController.updateModel(value, getCreatedOnWM());
    }

    /** Getter for DropDown property createdOn.
     * @return Value of property createdOnDd.
     */
    public String getCreatedOnDd() {
        return ( (AttachmentLookupComponent) getComponent() ).getCreatedOnDd();
    }

    /** Setter for DropDown property createdOn.
     * @param createdOnDd New value of property createdOnDd.
     */
    public void setCreatedOnDd(String createdOnDd) {
        ( (AttachmentLookupComponent) getComponent() ).setCreatedOnDd(createdOnDd);
    }

    /** Getter for DropDown property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOnDd.
     */
    public DropDownModel getCreatedOnDdWM() {
        DropDownModel createdOnDdModel = (DropDownModel) getWidgetCache().getModel("createdOnDd");
        if (createdOnDdModel == null) {
            if (getCreatedOnDd() != null)
                createdOnDdModel = new DropDownModel( getCreatedOnDd() );
            else
                createdOnDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getDateCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                createdOnDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("createdOnDd", createdOnDdModel);
        }
        return createdOnDdModel;
    }

    /** Setter for DropDown property createdOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property createdOnDd.
     */
    public void setCreatedOnDdWV(String value) {
        DropDownController.updateModel(value, getCreatedOnDdWM());
    }

    // .//GEN-END:createdOn_2_be
    // .//GEN-BEGIN:createdBy_1_be
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public String getCreatedBy() {
        return ( (AttachmentLookupComponent) getComponent() ).getCreatedBy();
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(String createdBy) {
        ( (AttachmentLookupComponent) getComponent() ).setCreatedBy(createdBy);
    }

    /** Getter for property createdBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdBy.
     */
    public EditBoxModel getCreatedByWM() {
        EditBoxModel createdByModel = (EditBoxModel) getWidgetCache().getModel("createdBy");
        if (createdByModel == null) {
            if (getCreatedBy() != null)
                createdByModel = new EditBoxModel( getCreatedBy() );
            else
                createdByModel = new EditBoxModel();
            createdByModel.setStringCase( ((StringFieldMetaData) AttachmentMeta.META_CREATED_BY).getCaseType() );

            // .//GEN-END:createdBy_1_be
            // Add custom code//GEN-FIRST:createdBy_1


            // .//GEN-LAST:createdBy_1
            // .//GEN-BEGIN:createdBy_2_be
            getWidgetCache().addModel("createdBy", createdByModel);
        }
        return createdByModel;
    }

    /** Setter for property createdBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property createdBy.
     */
    public void setCreatedByWV(String value) {
        EditBoxController.updateModel(value, getCreatedByWM());
    }

    /** Getter for DropDown property createdBy.
     * @return Value of property createdByDd.
     */
    public String getCreatedByDd() {
        return ( (AttachmentLookupComponent) getComponent() ).getCreatedByDd();
    }

    /** Setter for DropDown property createdBy.
     * @param createdByDd New value of property createdByDd.
     */
    public void setCreatedByDd(String createdByDd) {
        ( (AttachmentLookupComponent) getComponent() ).setCreatedByDd(createdByDd);
    }

    /** Getter for DropDown property createdBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdByDd.
     */
    public DropDownModel getCreatedByDdWM() {
        DropDownModel createdByDdModel = (DropDownModel) getWidgetCache().getModel("createdByDd");
        if (createdByDdModel == null) {
            if (getCreatedByDd() != null)
                createdByDdModel = new DropDownModel( getCreatedByDd() );
            else
                createdByDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                createdByDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("createdByDd", createdByDdModel);
        }
        return createdByDdModel;
    }

    /** Setter for DropDown property createdBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property createdByDd.
     */
    public void setCreatedByDdWV(String value) {
        DropDownController.updateModel(value, getCreatedByDdWM());
    }

    // .//GEN-END:createdBy_2_be
    // .//GEN-BEGIN:lastChangedOn_1_be
    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public String getLastChangedOn() {
        return ( (AttachmentLookupComponent) getComponent() ).getLastChangedOn();
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(String lastChangedOn) {
        ( (AttachmentLookupComponent) getComponent() ).setLastChangedOn(lastChangedOn);
    }

    /** Getter for property lastChangedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedOn.
     */
    public EditBoxModel getLastChangedOnWM() {
        EditBoxModel lastChangedOnModel = (EditBoxModel) getWidgetCache().getModel("lastChangedOn");
        if (lastChangedOnModel == null) {
            if (getLastChangedOn() != null)
                lastChangedOnModel = new EditBoxModel( getLastChangedOn() );
            else
                lastChangedOnModel = new EditBoxModel();

            // .//GEN-END:lastChangedOn_1_be
            // Add custom code//GEN-FIRST:lastChangedOn_1


            // .//GEN-LAST:lastChangedOn_1
            // .//GEN-BEGIN:lastChangedOn_2_be
            getWidgetCache().addModel("lastChangedOn", lastChangedOnModel);
        }
        return lastChangedOnModel;
    }

    /** Setter for property lastChangedOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastChangedOn.
     */
    public void setLastChangedOnWV(String value) {
        EditBoxController.updateModel(value, getLastChangedOnWM());
    }

    /** Getter for DropDown property lastChangedOn.
     * @return Value of property lastChangedOnDd.
     */
    public String getLastChangedOnDd() {
        return ( (AttachmentLookupComponent) getComponent() ).getLastChangedOnDd();
    }

    /** Setter for DropDown property lastChangedOn.
     * @param lastChangedOnDd New value of property lastChangedOnDd.
     */
    public void setLastChangedOnDd(String lastChangedOnDd) {
        ( (AttachmentLookupComponent) getComponent() ).setLastChangedOnDd(lastChangedOnDd);
    }

    /** Getter for DropDown property lastChangedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedOnDd.
     */
    public DropDownModel getLastChangedOnDdWM() {
        DropDownModel lastChangedOnDdModel = (DropDownModel) getWidgetCache().getModel("lastChangedOnDd");
        if (lastChangedOnDdModel == null) {
            if (getLastChangedOnDd() != null)
                lastChangedOnDdModel = new DropDownModel( getLastChangedOnDd() );
            else
                lastChangedOnDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getDateCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                lastChangedOnDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("lastChangedOnDd", lastChangedOnDdModel);
        }
        return lastChangedOnDdModel;
    }

    /** Setter for DropDown property lastChangedOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastChangedOnDd.
     */
    public void setLastChangedOnDdWV(String value) {
        DropDownController.updateModel(value, getLastChangedOnDdWM());
    }

    // .//GEN-END:lastChangedOn_2_be
    // .//GEN-BEGIN:lastChangedBy_1_be
    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public String getLastChangedBy() {
        return ( (AttachmentLookupComponent) getComponent() ).getLastChangedBy();
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(String lastChangedBy) {
        ( (AttachmentLookupComponent) getComponent() ).setLastChangedBy(lastChangedBy);
    }

    /** Getter for property lastChangedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedBy.
     */
    public EditBoxModel getLastChangedByWM() {
        EditBoxModel lastChangedByModel = (EditBoxModel) getWidgetCache().getModel("lastChangedBy");
        if (lastChangedByModel == null) {
            if (getLastChangedBy() != null)
                lastChangedByModel = new EditBoxModel( getLastChangedBy() );
            else
                lastChangedByModel = new EditBoxModel();
            lastChangedByModel.setStringCase( ((StringFieldMetaData) AttachmentMeta.META_LAST_CHANGED_BY).getCaseType() );

            // .//GEN-END:lastChangedBy_1_be
            // Add custom code//GEN-FIRST:lastChangedBy_1


            // .//GEN-LAST:lastChangedBy_1
            // .//GEN-BEGIN:lastChangedBy_2_be
            getWidgetCache().addModel("lastChangedBy", lastChangedByModel);
        }
        return lastChangedByModel;
    }

    /** Setter for property lastChangedBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastChangedBy.
     */
    public void setLastChangedByWV(String value) {
        EditBoxController.updateModel(value, getLastChangedByWM());
    }

    /** Getter for DropDown property lastChangedBy.
     * @return Value of property lastChangedByDd.
     */
    public String getLastChangedByDd() {
        return ( (AttachmentLookupComponent) getComponent() ).getLastChangedByDd();
    }

    /** Setter for DropDown property lastChangedBy.
     * @param lastChangedByDd New value of property lastChangedByDd.
     */
    public void setLastChangedByDd(String lastChangedByDd) {
        ( (AttachmentLookupComponent) getComponent() ).setLastChangedByDd(lastChangedByDd);
    }

    /** Getter for DropDown property lastChangedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedByDd.
     */
    public DropDownModel getLastChangedByDdWM() {
        DropDownModel lastChangedByDdModel = (DropDownModel) getWidgetCache().getModel("lastChangedByDd");
        if (lastChangedByDdModel == null) {
            if (getLastChangedByDd() != null)
                lastChangedByDdModel = new DropDownModel( getLastChangedByDd() );
            else
                lastChangedByDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                lastChangedByDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("lastChangedByDd", lastChangedByDdModel);
        }
        return lastChangedByDdModel;
    }

    /** Setter for DropDown property lastChangedBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastChangedByDd.
     */
    public void setLastChangedByDdWV(String value) {
        DropDownController.updateModel(value, getLastChangedByDdWM());
    }

    // .//GEN-END:lastChangedBy_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        String value = null;
        StringBuffer buf = null;

        value = getAttachmentIdWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setAttachmentId(value);
        setAttachmentIdDd(getAttachmentIdDdWM().getValue());

        value = getSerializedKeyWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSerializedKey(value);
        setSerializedKeyDd(getSerializedKeyDdWM().getValue());

        value = getOriginalFileNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setOriginalFileName(value);
        setOriginalFileNameDd(getOriginalFileNameDdWM().getValue());

        value = getAttachmentTypeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setAttachmentType(value);
        setAttachmentTypeDd(getAttachmentTypeDdWM().getValue());

        value = getContentTypeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setContentType(value);
        setContentTypeDd(getContentTypeDdWM().getValue());

        value = getDescriptionWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setDescription(value);
        setDescriptionDd(getDescriptionDdWM().getValue());

        value = getRemarksWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setRemarks(value);
        setRemarksDd(getRemarksDdWM().getValue());

        value = getSupercededByWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSupercededBy(value);
        setSupercededByDd(getSupercededByDdWM().getValue());

        value = getCreatedOnWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setCreatedOn(value);
        setCreatedOnDd(getCreatedOnDdWM().getValue());

        value = getCreatedByWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setCreatedBy(value);
        setCreatedByDd(getCreatedByDdWM().getValue());

        value = getLastChangedOnWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLastChangedOn(value);
        setLastChangedOnDd(getLastChangedOnDdWM().getValue());

        value = getLastChangedByWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLastChangedBy(value);
        setLastChangedByDd(getLastChangedByDdWM().getValue());

        // .//GEN-END:_doValidate_1_be
        // Add custom code//GEN-FIRST:_doValidate_1



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
        AttachmentLookupOutDto outputDto = (AttachmentLookupOutDto) ((AttachmentLookupComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                AttachmentLookupOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement(LookupComponent2.MULTI_SELECT_CHECKBOX, new CheckBoxModel(false));
                row.addElement("attachmentId", rowDto.getAttachmentId());
                row.addElement("serializedKey", rowDto.getSerializedKey());
                row.addElement("originalFileName", rowDto.getOriginalFileName());
                row.addElement("attachmentType", rowDto.getAttachmentType());
                row.addElement("contentType", rowDto.getContentType());
                row.addElement("description", rowDto.getDescription());
                row.addElement("remarks", rowDto.getRemarks());
                row.addElement("supercededBy", rowDto.getSupercededBy());
                row.addElement("createdOn", rowDto.getCreatedOn());
                row.addElement("createdBy", rowDto.getCreatedBy());
                row.addElement("lastChangedOn", rowDto.getLastChangedOn());
                row.addElement("lastChangedBy", rowDto.getLastChangedBy());
                row.addElement("data", rowDto.getData());
                // .//GEN-END:_populateRows_1_be
                // Add custom code for the row//GEN-FIRST:_populateRows_1


                // .//GEN-LAST:_populateRows_1
                // .//GEN-BEGIN:_populateRows_2_be
            }
        }
    }
    // .//GEN-END:_populateRows_2_be
    // All the custom code goes here//GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
