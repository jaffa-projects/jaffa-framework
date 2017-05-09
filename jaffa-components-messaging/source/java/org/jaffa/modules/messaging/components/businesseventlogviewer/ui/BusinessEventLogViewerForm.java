// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.messaging.components.businesseventlogviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.modules.messaging.components.businesseventlogviewer.dto.BusinessEventLogViewerOutDto;

import java.util.*;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.controller.GridController;
import org.jaffa.modules.messaging.components.businesseventlogviewer.dto.AttachmentDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the BusinessEventLogViewer.
 */
public class BusinessEventLogViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "jaffa_messaging_businessEventLogViewer";
    private static Logger log = Logger.getLogger(BusinessEventLogViewerForm.class);


    /** Getter for property logId.
     * @return Value of property logId.
     */
    public java.lang.String getLogId() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getLogId() : null;
    }

    /** Getter for property correlationType.
     * @return Value of property correlationType.
     */
    public java.lang.String getCorrelationType() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getCorrelationType() : null;
    }

    /** Getter for property correlationKey1.
     * @return Value of property correlationKey1.
     */
    public java.lang.String getCorrelationKey1() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getCorrelationKey1() : null;
    }

    /** Getter for property correlationKey2.
     * @return Value of property correlationKey2.
     */
    public java.lang.String getCorrelationKey2() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getCorrelationKey2() : null;
    }

    /** Getter for property correlationKey3.
     * @return Value of property correlationKey3.
     */
    public java.lang.String getCorrelationKey3() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getCorrelationKey3() : null;
    }

    /** Getter for property scheduledTaskId.
     * @return Value of property scheduledTaskId.
     */
    public java.lang.String getScheduledTaskId() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getScheduledTaskId() : null;
    }

    /** Getter for property messageId.
     * @return Value of property messageId.
     */
    public java.lang.String getMessageId() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getMessageId() : null;
    }

    /** Getter for property loggedOn.
     * @return Value of property loggedOn.
     */
    public org.jaffa.datatypes.DateTime getLoggedOn() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getLoggedOn() : null;
    }

    /** Getter for property loggedBy.
     * @return Value of property loggedBy.
     */
    public java.lang.String getLoggedBy() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getLoggedBy() : null;
    }

    /** Getter for property processName.
     * @return Value of property processName.
     */
    public java.lang.String getProcessName() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getProcessName() : null;
    }

    /** Getter for property subProcessName.
     * @return Value of property subProcessName.
     */
    public java.lang.String getSubProcessName() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getSubProcessName() : null;
    }

    /** Getter for property messageType.
     * @return Value of property messageType.
     */
    public java.lang.String getMessageType() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getMessageType() : null;
    }

    /** Getter for property messageText.
     * @return Value of property messageText.
     */
    public java.lang.String getMessageText() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getMessageText() : null;
    }

    /** Getter for property sourceClass.
     * @return Value of property sourceClass.
     */
    public java.lang.String getSourceClass() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getSourceClass() : null;
    }

    /** Getter for property sourceMethod.
     * @return Value of property sourceMethod.
     */
    public java.lang.String getSourceMethod() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getSourceMethod() : null;
    }

    /** Getter for property sourceLine.
     * @return Value of property sourceLine.
     */
    public java.lang.Long getSourceLine() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getSourceLine() : null;
    }

    /** Getter for property stackTrace.
     * @return Value of property stackTrace.
     */
    public java.lang.String getStackTrace() {
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        return outputDto != null ? outputDto.getStackTrace() : null;
    }

    // .//GEN-END:_2_be
    // .//GEN-BEGIN:RelatedAttachment_1_be
    /** Getter for property Attachment. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property attachment.
     */
    public GridModel getRelatedAttachmentWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("relatedAttachment");
        if (rows == null) {
            rows = new GridModel();
            populateRelatedAttachment(rows);
            getWidgetCache().addModel("relatedAttachment", rows);
        }
        return rows;
    }
    
    /** Setter for property attachment. This is invoked by the servlet, when a post is done on the View screen.
     * It sets the selected rows on the model.
     * @param value New value of property attachment.
     */
    public void setRelatedAttachmentWV(String value) {
        GridController.updateModel(value, getRelatedAttachmentWM());
    }
    
    private void populateRelatedAttachment(GridModel rows) {
        rows.clearRows();
        BusinessEventLogViewerOutDto outputDto = ((BusinessEventLogViewerComponent) getComponent()).getBusinessEventLogViewerOutDto();
        if (outputDto != null) {
            GridModelRow row = null;
            AttachmentDto[] attachment = outputDto.getAttachment();
            for (int i = 0; i < attachment.length; i++) {
                AttachmentDto rowDto = attachment[i];
                row = rows.newRow();
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
                // .//GEN-END:RelatedAttachment_1_be
                // Add custom code for the row //GEN-FIRST:RelatedAttachment_1


                // .//GEN-LAST:RelatedAttachment_1
                // .//GEN-BEGIN:RelatedAttachment_2_be
            }
        }
    }
    // .//GEN-END:RelatedAttachment_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
