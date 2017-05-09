// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.components.attachment.components.attachmentmaintenance.ui;

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

import org.jaffa.components.attachment.components.attachmentmaintenance.dto.*;
import org.jaffa.components.attachment.domain.AttachmentMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import java.io.IOException;
import org.apache.struts.upload.FormFile;


// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the AttachmentMaintenance.
 */
public class AttachmentMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(AttachmentMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:attachmentId_1_be
    /** Getter for property attachmentId.
     * @return Value of property attachmentId.
     */
    public java.lang.String getAttachmentId() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getAttachmentId();
    }

    /** Setter for property attachmentId.
     * @param attachmentId New value of property attachmentId.
     */
    public void setAttachmentId(java.lang.String attachmentId) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setAttachmentId(attachmentId);
    }

    /** Getter for property attachmentId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property attachmentId.
     */
    public EditBoxModel getAttachmentIdWM() {
        EditBoxModel w_attachmentId = (EditBoxModel) getWidgetCache().getModel("attachmentId");
        if (w_attachmentId == null) {
            if (getAttachmentId() != null)
                w_attachmentId = new EditBoxModel(getAttachmentId(), (StringFieldMetaData) AttachmentMeta.META_ATTACHMENT_ID);
            else
                w_attachmentId = new EditBoxModel((StringFieldMetaData) AttachmentMeta.META_ATTACHMENT_ID);
            // .//GEN-END:attachmentId_1_be
            // Add custom code//GEN-FIRST:attachmentId_1


            // .//GEN-LAST:attachmentId_1
            // .//GEN-BEGIN:attachmentId_2_be
            getWidgetCache().addModel("attachmentId", w_attachmentId);
        }
        return w_attachmentId;
    }

    /** Setter for property attachmentId. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property attachmentId.
     */
    public void setAttachmentIdWV(String value) {
        EditBoxController.updateModel(value, getAttachmentIdWM());
    }
    // .//GEN-END:attachmentId_2_be
    // .//GEN-BEGIN:serializedKey_1_be
    /** Getter for property serializedKey.
     * @return Value of property serializedKey.
     */
    public java.lang.String getSerializedKey() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getSerializedKey();
    }

    /** Setter for property serializedKey.
     * @param serializedKey New value of property serializedKey.
     */
    public void setSerializedKey(java.lang.String serializedKey) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setSerializedKey(serializedKey);
    }

    /** Getter for property serializedKey. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property serializedKey.
     */
    public EditBoxModel getSerializedKeyWM() {
        EditBoxModel w_serializedKey = (EditBoxModel) getWidgetCache().getModel("serializedKey");
        if (w_serializedKey == null) {
            if (getSerializedKey() != null)
                w_serializedKey = new EditBoxModel(getSerializedKey(), (StringFieldMetaData) AttachmentMeta.META_SERIALIZED_KEY);
            else
                w_serializedKey = new EditBoxModel((StringFieldMetaData) AttachmentMeta.META_SERIALIZED_KEY);
            // .//GEN-END:serializedKey_1_be
            // Add custom code//GEN-FIRST:serializedKey_1


            // .//GEN-LAST:serializedKey_1
            // .//GEN-BEGIN:serializedKey_2_be
            getWidgetCache().addModel("serializedKey", w_serializedKey);
        }
        return w_serializedKey;
    }

    /** Setter for property serializedKey. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property serializedKey.
     */
    public void setSerializedKeyWV(String value) {
        EditBoxController.updateModel(value, getSerializedKeyWM());
    }
    // .//GEN-END:serializedKey_2_be
    // .//GEN-BEGIN:originalFileName_1_be
    /** Getter for property originalFileName.
     * @return Value of property originalFileName.
     */
    public java.lang.String getOriginalFileName() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getOriginalFileName();
    }

    /** Setter for property originalFileName.
     * @param originalFileName New value of property originalFileName.
     */
    public void setOriginalFileName(java.lang.String originalFileName) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setOriginalFileName(originalFileName);
    }

    /** Getter for property originalFileName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property originalFileName.
     */
    public EditBoxModel getOriginalFileNameWM() {
        EditBoxModel w_originalFileName = (EditBoxModel) getWidgetCache().getModel("originalFileName");
        if (w_originalFileName == null) {
            if (getOriginalFileName() != null)
                w_originalFileName = new EditBoxModel(getOriginalFileName(), (StringFieldMetaData) AttachmentMeta.META_ORIGINAL_FILE_NAME);
            else
                w_originalFileName = new EditBoxModel((StringFieldMetaData) AttachmentMeta.META_ORIGINAL_FILE_NAME);
            // .//GEN-END:originalFileName_1_be
            // Add custom code//GEN-FIRST:originalFileName_1


            // .//GEN-LAST:originalFileName_1
            // .//GEN-BEGIN:originalFileName_2_be
            getWidgetCache().addModel("originalFileName", w_originalFileName);
        }
        return w_originalFileName;
    }

    /** Setter for property originalFileName. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property originalFileName.
     */
    public void setOriginalFileNameWV(String value) {
        EditBoxController.updateModel(value, getOriginalFileNameWM());
    }
    // .//GEN-END:originalFileName_2_be
    // .//GEN-BEGIN:attachmentType_1_be
    /** Getter for property attachmentType.
     * @return Value of property attachmentType.
     */
    public java.lang.String getAttachmentType() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getAttachmentType();
    }

    /** Setter for property attachmentType.
     * @param attachmentType New value of property attachmentType.
     */
    public void setAttachmentType(java.lang.String attachmentType) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setAttachmentType(attachmentType);
    }

    /** Getter for property attachmentType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property attachmentType.
     */
    public RadioButtonModel getAttachmentTypeWM() {
        RadioButtonModel w_attachmentType = (RadioButtonModel) getWidgetCache().getModel("attachmentType");
        if (w_attachmentType == null) {
            w_attachmentType = new RadioButtonModel((getAttachmentType() != null ? getAttachmentType() : ""));

            // .//GEN-END:attachmentType_1_be
            // Add custom code//GEN-FIRST:attachmentType_1


            // .//GEN-LAST:attachmentType_1
            // .//GEN-BEGIN:attachmentType_2_be
            getWidgetCache().addModel("attachmentType", w_attachmentType);
        }
        return w_attachmentType;
    }

    /** Setter for property attachmentType. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property attachmentType.
     */
    public void setAttachmentTypeWV(String value) {
        RadioButtonController.updateModel(value, getAttachmentTypeWM());
    }
    // .//GEN-END:attachmentType_2_be
    // .//GEN-BEGIN:contentType_1_be
    /** Getter for property contentType.
     * @return Value of property contentType.
     */
    public java.lang.String getContentType() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getContentType();
    }

    /** Setter for property contentType.
     * @param contentType New value of property contentType.
     */
    public void setContentType(java.lang.String contentType) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setContentType(contentType);
    }

    /** Getter for property contentType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property contentType.
     */
    public EditBoxModel getContentTypeWM() {
        EditBoxModel w_contentType = (EditBoxModel) getWidgetCache().getModel("contentType");
        if (w_contentType == null) {
            if (getContentType() != null)
                w_contentType = new EditBoxModel(getContentType(), (StringFieldMetaData) AttachmentMeta.META_CONTENT_TYPE);
            else
                w_contentType = new EditBoxModel((StringFieldMetaData) AttachmentMeta.META_CONTENT_TYPE);
            // .//GEN-END:contentType_1_be
            // Add custom code//GEN-FIRST:contentType_1


            // .//GEN-LAST:contentType_1
            // .//GEN-BEGIN:contentType_2_be
            getWidgetCache().addModel("contentType", w_contentType);
        }
        return w_contentType;
    }

    /** Setter for property contentType. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property contentType.
     */
    public void setContentTypeWV(String value) {
        EditBoxController.updateModel(value, getContentTypeWM());
    }
    // .//GEN-END:contentType_2_be
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getDescription();
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setDescription(description);
    }

    /** Getter for property description. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property description.
     */
    public EditBoxModel getDescriptionWM() {
        EditBoxModel w_description = (EditBoxModel) getWidgetCache().getModel("description");
        if (w_description == null) {
            if (getDescription() != null)
                w_description = new EditBoxModel(getDescription(), (StringFieldMetaData) AttachmentMeta.META_DESCRIPTION);
            else
                w_description = new EditBoxModel((StringFieldMetaData) AttachmentMeta.META_DESCRIPTION);
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
    // .//GEN-BEGIN:remarks_1_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getRemarks();
    }

    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(java.lang.String remarks) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setRemarks(remarks);
    }

    /** Getter for property remarks. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property remarks.
     */
    public EditBoxModel getRemarksWM() {
        EditBoxModel w_remarks = (EditBoxModel) getWidgetCache().getModel("remarks");
        if (w_remarks == null) {
            if (getRemarks() != null)
                w_remarks = new EditBoxModel(getRemarks(), (StringFieldMetaData) AttachmentMeta.META_REMARKS);
            else
                w_remarks = new EditBoxModel((StringFieldMetaData) AttachmentMeta.META_REMARKS);
            // .//GEN-END:remarks_1_be
            // Add custom code//GEN-FIRST:remarks_1


            // .//GEN-LAST:remarks_1
            // .//GEN-BEGIN:remarks_2_be
            getWidgetCache().addModel("remarks", w_remarks);
        }
        return w_remarks;
    }

    /** Setter for property remarks. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property remarks.
     */
    public void setRemarksWV(String value) {
        EditBoxController.updateModel(value, getRemarksWM());
    }
    // .//GEN-END:remarks_2_be
    // .//GEN-BEGIN:supercededBy_1_be
    /** Getter for property supercededBy.
     * @return Value of property supercededBy.
     */
    public java.lang.String getSupercededBy() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getSupercededBy();
    }

    /** Setter for property supercededBy.
     * @param supercededBy New value of property supercededBy.
     */
    public void setSupercededBy(java.lang.String supercededBy) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setSupercededBy(supercededBy);
    }

    /** Getter for property supercededBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property supercededBy.
     */
    public EditBoxModel getSupercededByWM() {
        EditBoxModel w_supercededBy = (EditBoxModel) getWidgetCache().getModel("supercededBy");
        if (w_supercededBy == null) {
            if (getSupercededBy() != null)
                w_supercededBy = new EditBoxModel(getSupercededBy(), (StringFieldMetaData) AttachmentMeta.META_SUPERCEDED_BY);
            else
                w_supercededBy = new EditBoxModel((StringFieldMetaData) AttachmentMeta.META_SUPERCEDED_BY);
            // .//GEN-END:supercededBy_1_be
            // Add custom code//GEN-FIRST:supercededBy_1


            // .//GEN-LAST:supercededBy_1
            // .//GEN-BEGIN:supercededBy_2_be
            getWidgetCache().addModel("supercededBy", w_supercededBy);
        }
        return w_supercededBy;
    }

    /** Setter for property supercededBy. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property supercededBy.
     */
    public void setSupercededByWV(String value) {
        EditBoxController.updateModel(value, getSupercededByWM());
    }
    // .//GEN-END:supercededBy_2_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getCreatedOn();
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setCreatedOn(createdOn);
    }

    /** Getter for property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOn.
     */
    public DateTimeModel getCreatedOnWM() {
        DateTimeModel w_createdOn = (DateTimeModel) getWidgetCache().getModel("createdOn");
        if (w_createdOn == null) {
            w_createdOn = new DateTimeModel(getCreatedOn(), (DateTimeFieldMetaData) AttachmentMeta.META_CREATED_ON);
            // .//GEN-END:createdOn_1_be
            // Add custom code//GEN-FIRST:createdOn_1


            // .//GEN-LAST:createdOn_1
            // .//GEN-BEGIN:createdOn_2_be
            getWidgetCache().addModel("createdOn", w_createdOn);
        }
        return w_createdOn;
    }

    /** Setter for property createdOn. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property createdOn.
     */
    public void setCreatedOnWV(String value) {
        DateTimeController.updateModel(value, getCreatedOnWM());
    }
    // .//GEN-END:createdOn_2_be
    // .//GEN-BEGIN:createdBy_1_be
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getCreatedBy();
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(java.lang.String createdBy) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setCreatedBy(createdBy);
    }

    /** Getter for property createdBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdBy.
     */
    public EditBoxModel getCreatedByWM() {
        EditBoxModel w_createdBy = (EditBoxModel) getWidgetCache().getModel("createdBy");
        if (w_createdBy == null) {
            if (getCreatedBy() != null)
                w_createdBy = new EditBoxModel(getCreatedBy(), (StringFieldMetaData) AttachmentMeta.META_CREATED_BY);
            else
                w_createdBy = new EditBoxModel((StringFieldMetaData) AttachmentMeta.META_CREATED_BY);
            // .//GEN-END:createdBy_1_be
            // Add custom code//GEN-FIRST:createdBy_1


            // .//GEN-LAST:createdBy_1
            // .//GEN-BEGIN:createdBy_2_be
            getWidgetCache().addModel("createdBy", w_createdBy);
        }
        return w_createdBy;
    }

    /** Setter for property createdBy. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property createdBy.
     */
    public void setCreatedByWV(String value) {
        EditBoxController.updateModel(value, getCreatedByWM());
    }
    // .//GEN-END:createdBy_2_be
    // .//GEN-BEGIN:lastChangedOn_1_be
    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getLastChangedOn();
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setLastChangedOn(lastChangedOn);
    }

    /** Getter for property lastChangedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedOn.
     */
    public DateTimeModel getLastChangedOnWM() {
        DateTimeModel w_lastChangedOn = (DateTimeModel) getWidgetCache().getModel("lastChangedOn");
        if (w_lastChangedOn == null) {
            w_lastChangedOn = new DateTimeModel(getLastChangedOn(), (DateTimeFieldMetaData) AttachmentMeta.META_LAST_CHANGED_ON);
            // .//GEN-END:lastChangedOn_1_be
            // Add custom code//GEN-FIRST:lastChangedOn_1


            // .//GEN-LAST:lastChangedOn_1
            // .//GEN-BEGIN:lastChangedOn_2_be
            getWidgetCache().addModel("lastChangedOn", w_lastChangedOn);
        }
        return w_lastChangedOn;
    }

    /** Setter for property lastChangedOn. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property lastChangedOn.
     */
    public void setLastChangedOnWV(String value) {
        DateTimeController.updateModel(value, getLastChangedOnWM());
    }
    // .//GEN-END:lastChangedOn_2_be
    // .//GEN-BEGIN:lastChangedBy_1_be
    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getLastChangedBy();
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(java.lang.String lastChangedBy) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setLastChangedBy(lastChangedBy);
    }

    /** Getter for property lastChangedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedBy.
     */
    public EditBoxModel getLastChangedByWM() {
        EditBoxModel w_lastChangedBy = (EditBoxModel) getWidgetCache().getModel("lastChangedBy");
        if (w_lastChangedBy == null) {
            if (getLastChangedBy() != null)
                w_lastChangedBy = new EditBoxModel(getLastChangedBy(), (StringFieldMetaData) AttachmentMeta.META_LAST_CHANGED_BY);
            else
                w_lastChangedBy = new EditBoxModel((StringFieldMetaData) AttachmentMeta.META_LAST_CHANGED_BY);
            // .//GEN-END:lastChangedBy_1_be
            // Add custom code//GEN-FIRST:lastChangedBy_1


            // .//GEN-LAST:lastChangedBy_1
            // .//GEN-BEGIN:lastChangedBy_2_be
            getWidgetCache().addModel("lastChangedBy", w_lastChangedBy);
        }
        return w_lastChangedBy;
    }

    /** Setter for property lastChangedBy. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property lastChangedBy.
     */
    public void setLastChangedByWV(String value) {
        EditBoxController.updateModel(value, getLastChangedByWM());
    }
    // .//GEN-END:lastChangedBy_2_be
    // .//GEN-BEGIN:data_1_be
    /** Getter for property data.
     * @return Value of property data.
     */
    public byte[] getData() {
        return ( (AttachmentMaintenanceComponent) getComponent() ).getData();
    }

    /** Setter for property data.
     * @param data New value of property data.
     */
    public void setData(byte[] data) {
        ( (AttachmentMaintenanceComponent) getComponent() ).setData(data);
    }

    /** Getter for property data. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property data.
     */
    public EditBoxModel getDataWM() {
        EditBoxModel w_data = (EditBoxModel) getWidgetCache().getModel("data");
        if (w_data == null) {
            if (getData() != null)
                w_data = new EditBoxModel(getData(), (RawFieldMetaData) AttachmentMeta.META_DATA);
            else
                w_data = new EditBoxModel((RawFieldMetaData) AttachmentMeta.META_DATA);
            // .//GEN-END:data_1_be
            // Add custom code//GEN-FIRST:data_1


            // .//GEN-LAST:data_1
            // .//GEN-BEGIN:data_2_be
            getWidgetCache().addModel("data", w_data);
        }
        return w_data;
    }

    /** Setter for property data. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property data.
     */
    public void setDataWV(String value) {
        EditBoxController.updateModel(value, getDataWM());
    }
    // .//GEN-END:data_2_be
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
            String htmlString = getAttachmentIdWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) AttachmentMeta.META_ATTACHMENT_ID, true);

            setAttachmentId(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_ATTACHMENT_ID).getLabelToken(), e);
        }


        try {
            String htmlString = getSerializedKeyWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) AttachmentMeta.META_SERIALIZED_KEY, true);

            setSerializedKey(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_SERIALIZED_KEY).getLabelToken(), e);
        }


        try {
            String htmlString = getOriginalFileNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) AttachmentMeta.META_ORIGINAL_FILE_NAME, true);

            setOriginalFileName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_ORIGINAL_FILE_NAME).getLabelToken(), e);
        }


        try {
            java.lang.String value = getAttachmentTypeWM().getValue();
            value = FieldValidator.validate(value, (StringFieldMetaData) AttachmentMeta.META_ATTACHMENT_TYPE, true);
            setAttachmentType(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_ATTACHMENT_TYPE).getLabelToken(), e);
        }


        try {
            String htmlString = getContentTypeWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) AttachmentMeta.META_CONTENT_TYPE, true);

            setContentType(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_CONTENT_TYPE).getLabelToken(), e);
        }


        try {
            String htmlString = getDescriptionWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) AttachmentMeta.META_DESCRIPTION, true);

            setDescription(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_DESCRIPTION).getLabelToken(), e);
        }


        try {
            String htmlString = getRemarksWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) AttachmentMeta.META_REMARKS, true);

            setRemarks(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_REMARKS).getLabelToken(), e);
        }


        try {
            String htmlString = getSupercededByWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) AttachmentMeta.META_SUPERCEDED_BY, true);

            setSupercededBy(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_SUPERCEDED_BY).getLabelToken(), e);
        }


        try {
            org.jaffa.datatypes.DateTime value = getCreatedOnWM().getValue();
            value = FieldValidator.validate(value, (DateTimeFieldMetaData) AttachmentMeta.META_CREATED_ON, true);
            setCreatedOn(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((DateTimeFieldMetaData) AttachmentMeta.META_CREATED_ON).getLabelToken(), e);
        }


        try {
            String htmlString = getCreatedByWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) AttachmentMeta.META_CREATED_BY, true);

            setCreatedBy(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_CREATED_BY).getLabelToken(), e);
        }


        try {
            org.jaffa.datatypes.DateTime value = getLastChangedOnWM().getValue();
            value = FieldValidator.validate(value, (DateTimeFieldMetaData) AttachmentMeta.META_LAST_CHANGED_ON, true);
            setLastChangedOn(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((DateTimeFieldMetaData) AttachmentMeta.META_LAST_CHANGED_ON).getLabelToken(), e);
        }


        try {
            String htmlString = getLastChangedByWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) AttachmentMeta.META_LAST_CHANGED_BY, true);

            setLastChangedBy(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_LAST_CHANGED_BY).getLabelToken(), e);
        }


        // .//GEN-END:_doValidateMain_1_be
        // Add custom validation code//GEN-FIRST:_doValidateMain_1
        // Set the custom fields
        {
            String htmlString = getLocalLinkWM().getStringValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            setLocalLink(htmlString);
        }
        {
            String htmlString = getWebLinkWM().getStringValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            setWebLink(htmlString);
        }
        
        // Determine the originalFileName and data, based on the attachmentType
        if ("L".equals(getAttachmentType())) {
            setOriginalFileName(getLocalLink());
            setData(null);
        } else if ("W".equals(getAttachmentType())) {
            setOriginalFileName(getWebLink());
            setData(null);
        } else if ("E".equals(getAttachmentType())) {
            // Retain the existing data, if a new file is not specified
            if (getEmbeddedFile() != null && getEmbeddedFile().getFileName() != null && getEmbeddedFile().getFileName().length() > 0) {
                try {
                    setEmbeddedFileName(getEmbeddedFile().getFileName());
                    setOriginalFileName(getEmbeddedFileName());
                    setData(getEmbeddedFile().getFileData());
                    if (getData() == null || getData().length == 0) {
                        valid = false;
                        raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_ORIGINAL_FILE_NAME).getLabelToken(), "exception.org.jaffa.components.attachment.attachmentNotFound", getEmbeddedFile().getFileName());
                    }
                } catch (IOException e) {
                    valid = false;
                    raiseError(request, ((StringFieldMetaData) AttachmentMeta.META_ORIGINAL_FILE_NAME).getLabelToken(), "exception.org.jaffa.components.attachment.attachmentNotFound", getEmbeddedFile().getFileName());
                }
            }
        }
        
        // .//GEN-LAST:_doValidateMain_1
        // .//GEN-BEGIN:_doValidateMain_2_be
        return valid;
    }
    // .//GEN-END:_doValidateMain_2_be
    // All the custom code goes here//GEN-FIRST:_custom
    
    /** Disables the 'save' option in the update mode.
     * @return a false in the update mode.
     */
    public boolean isSaveActionAvailable() {
        return ((AttachmentMaintenanceComponent) getComponent()).isCreateMode() ? false : super.isSaveActionAvailable();
    }
    
    /** Getter for property localLink.
     * @return Value of property localLink.
     */
    public String getLocalLink() {
        return ((AttachmentMaintenanceComponent) getComponent()).getLocalLink();
    }
    
    /** Setter for property localLink.
     * @param localLink New value of property localLink.
     */
    public void setLocalLink(String localLink) {
        ((AttachmentMaintenanceComponent) getComponent()).setLocalLink(localLink);
    }
    
    /** Getter for property localLink. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property localLink.
     */
    public SimpleWidgetModel getLocalLinkWM() {
        SimpleWidgetModel w_localLink = (SimpleWidgetModel) getWidgetCache().getModel("localLink");
        if (w_localLink == null) {
            w_localLink = new SimpleWidgetModel(getLocalLink());
            getWidgetCache().addModel("localLink", w_localLink);
        }
        return w_localLink;
    }
    
    /** Setter for property localLink. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property localLink.
     */
    public void setLocalLinkWV(String value) {
        SimpleWidgetController.updateModel(value, getLocalLinkWM());
    }
    
    /** Getter for property webLink.
     * @return Value of property webLink.
     */
    public String getWebLink() {
        return ((AttachmentMaintenanceComponent) getComponent()).getWebLink();
    }
    
    /** Setter for property webLink.
     * @param webLink New value of property webLink.
     */
    public void setWebLink(String webLink) {
        ((AttachmentMaintenanceComponent) getComponent()).setWebLink(webLink);
    }
    
    /** Getter for property webLink. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property webLink.
     */
    public SimpleWidgetModel getWebLinkWM() {
        SimpleWidgetModel w_webLink = (SimpleWidgetModel) getWidgetCache().getModel("webLink");
        if (w_webLink == null) {
            w_webLink = new SimpleWidgetModel(getWebLink());
            getWidgetCache().addModel("webLink", w_webLink);
        }
        return w_webLink;
    }
    
    /** Setter for property webLink. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property webLink.
     */
    public void setWebLinkWV(String value) {
        SimpleWidgetController.updateModel(value, getWebLinkWM());
    }
    
    /** Getter for property embeddedFileName.
     * @return Value of property embeddedFileName.
     */
    public String getEmbeddedFileName() {
        return ((AttachmentMaintenanceComponent) getComponent()).getEmbeddedFileName();
    }
    
    /** Setter for property embeddedFileName.
     * @param embeddedFileName New value of property embeddedFileName.
     */
    public void setEmbeddedFileName(String embeddedFileName) {
        ((AttachmentMaintenanceComponent) getComponent()).setEmbeddedFileName(embeddedFileName);
    }
    
    /** Getter for property embeddedFile.
     * @return Value of property embeddedFile.
     */
    public FormFile getEmbeddedFile() {
        return ((AttachmentMaintenanceComponent) getComponent()).getEmbeddedFile();
    }
    
    /** Setter for property embeddedFile.
     * @param embeddedFile New value of property embeddedFile.
     */
    public void setEmbeddedFile(FormFile embeddedFile) {
        ((AttachmentMaintenanceComponent) getComponent()).setEmbeddedFile(embeddedFile);
    }
    
    
    // .//GEN-LAST:_custom
}
