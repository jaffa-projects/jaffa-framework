package org.jaffa.modules.messaging.components.messageviewer.ui;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Parser;
import org.jaffa.datatypes.ValidationException;
import org.jaffa.modules.messaging.components.messageviewer.dto.HeaderElementDto;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.model.DropDownModel;
import org.jaffa.presentation.portlet.widgets.controller.DropDownController;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutDto;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto;
import org.jaffa.modules.messaging.components.messageviewer.dto.MessageViewerOutDto;
import org.jaffa.presentation.portlet.widgets.controller.GridController;

/** The FormBean class to support the View jsp of the MessageViewer.
 */
public class MessageViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */
    public static final String NAME = "jaffa_messaging_messageViewer";
    private static Logger log = Logger.getLogger(MessageViewerForm.class);
    
    /** Getter for property error.
     * @return Value of property error.
     */
    public java.lang.String getJMSMessageID() {
        return ( (MessageViewerComponent) getComponent() ).getJMSMessageID();
    }
    
    /** Getter for property error.
     * @return Value of property error.
     */
    public java.lang.String getError() {
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        return outputDto != null ? outputDto.getError() : null;
    }
    
    /** Getter for property jMSDestination.
     * @return Value of property jMSDestination.
     */
    public javax.jms.Destination getJMSDestination() {
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        return outputDto != null ? outputDto.getJMSDestination() : null;
    }
    
    /** Getter for property jMSDeliveryMode.
     * @return Value of property jMSDeliveryMode.
     */
    public java.lang.Integer getJMSDeliveryMode() {
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        return outputDto != null ? outputDto.getJMSDeliveryMode() : null;
    }
    
    /** Getter for property jMSTimestamp.
     * @return Value of property jMSTimestamp.
     */
    public DateTime getJMSTimestamp() {
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        return outputDto != null ? outputDto.getJMSTimestamp() : null;
    }
    
    /** Getter for property jMSCorrelationID.
     * @return Value of property jMSCorrelationID.
     */
    public java.lang.String getJMSCorrelationID() {
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        return outputDto != null ? outputDto.getJMSCorrelationID() : null;
    }
    
    /** Getter for property jMSReplyTo.
     * @return Value of property jMSReplyTo.
     */
    public javax.jms.Destination getJMSReplyTo() {
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        return outputDto != null ? outputDto.getJMSReplyTo() : null;
    }
    
    /** Getter for property jMSRedelivered.
     * @return Value of property jMSRedelivered.
     */
    public java.lang.Boolean getJMSRedelivered() {
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        return outputDto != null ? outputDto.getJMSRedelivered() : null;
    }
    
    /** Getter for property jMSType.
     * @return Value of property jMSType.
     */
    public java.lang.String getJMSType() {
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        return outputDto != null ? outputDto.getJMSType() : null;
    }
    
    /** Getter for property jMSExpiration.
     * @return Value of property jMSExpiration.
     */
    public java.lang.Long getJMSExpiration() {
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        return outputDto != null ? outputDto.getJMSExpiration() : null;
    }
    
    /** Getter for property payLoad.
     * @return Value of property payLoad.
     */
    public java.lang.String getPayLoad() {
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        return outputDto != null ? outputDto.getPayLoad() : null;
    }
    
    /** Getter for property headers. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property businessEventLog.
     */
    public GridModel getHeadersWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("headers");
        if (rows == null) {
            rows = new GridModel();
            populateHeaders(rows);
            getWidgetCache().addModel("headers", rows);
        }
        return rows;
    }
    
    /** Setter for property headers. This is invoked by the servlet, when a post is done on the View screen.
     * It sets the selected rows on the model.
     * @param value New value of property businessEventLog.
     */
    public void setHeadersWV(String value) {
        GridController.updateModel(value, getHeadersWM());
    }
    
    private void populateHeaders(GridModel rows) {
        rows.clearRows();
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        if (outputDto != null) {
            HeaderElementDto[] headerElements = outputDto.getHeaderElements();
            if(headerElements != null) {
                for (HeaderElementDto headerElement : headerElements) {
                    if (headerElement.getLabel() != null) {
                        GridModelRow row = rows.newRow();
                        row.addElement("name", headerElement.getName());
                        row.addElement("value", headerElement.getValue());
                        row.addElement("label", headerElement.getLabel());
                    }
                }
            }
        }
    }
    
    /** Getter for property technicalDetails. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property businessEventLog.
     */
    public GridModel getTechnicalDetailsWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("technicalDetails");
        if (rows == null) {
            rows = new GridModel();
            populateTechnicalDetails(rows);
            getWidgetCache().addModel("technicalDetails", rows);
        }
        return rows;
    }
    
    /** Setter for property technicalDetails. This is invoked by the servlet, when a post is done on the View screen.
     * It sets the selected rows on the model.
     * @param value New value of property businessEventLog.
     */
    public void setTechnicalDetailsWV(String value) {
        GridController.updateModel(value, getTechnicalDetailsWM());
    }
    
    private void populateTechnicalDetails(GridModel rows) {
        rows.clearRows();
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        if (outputDto != null) {
            HeaderElementDto[] headerElements = outputDto.getHeaderElements();
            if(headerElements != null) {
                for (HeaderElementDto headerElement : headerElements) {
                    if (headerElement.getLabel() == null) {
                        GridModelRow row = rows.newRow();
                        row.addElement("name", headerElement.getName());
                        row.addElement("value", headerElement.getValue());
                        row.addElement("label", headerElement.getName());
                    }
                }
            }
        }
    }
    
    /** Getter for property priority.
     * @return Value of property priority.
     */
    public java.lang.Long getPriority() {
        return ( (MessageViewerComponent) getComponent() ).getPriority();
    }
    
    /** Setter for property priority.
     * @param filter New value of property priority.
     */
    public void setPriority(java.lang.Long priority) {
        ( (MessageViewerComponent) getComponent() ).setPriority(priority);
    }
    
    /** Getter for DropDown property priority. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property priority.
     */
    public DropDownModel getPriorityWM() {
        DropDownModel priorityModel = (DropDownModel) getWidgetCache().getModel("priority");
        if (priorityModel == null) {
            if (getPriority() != null)
                priorityModel = new DropDownModel( getPriority() );
            else
                priorityModel = new DropDownModel(new Long(1));
            
            for (int i = 0; i < 10; i++) {
                priorityModel.addOption(String.valueOf(i), String.valueOf(i));
            }
            getWidgetCache().addModel("priority", priorityModel);
        }
        return priorityModel;
    }
    
    /** Setter for DropDown property priority. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property priority.
     */
    public void setPriorityWV(String value) {
        DropDownController.updateModel(value, getPriorityWM());
    }
    
    /** Getter for property relatedBusinessEventLog. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property businessEventLog.
     */
    public GridModel getRelatedBusinessEventLogWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("relatedBusinessEventLog");
        if (rows == null) {
            rows = new GridModel();
            populateRelatedBusinessEventLog(rows);
            getWidgetCache().addModel("relatedBusinessEventLog", rows);
        }
        return rows;
    }
    
    /** Setter for property relatedBusinessEventLog. This is invoked by the servlet, when a post is done on the View screen.
     * It sets the selected rows on the model.
     * @param value New value of property businessEventLog.
     */
    public void setRelatedBusinessEventLogWV(String value) {
        GridController.updateModel(value, getRelatedBusinessEventLogWM());
    }
    
    private void populateRelatedBusinessEventLog(GridModel rows) {
        rows.clearRows();
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        if (outputDto != null) {
            BusinessEventLogFinderOutDto businessEventLogOutDto = outputDto.getBusinessEventLog();
            if(businessEventLogOutDto != null){
                GridModelRow row;
                for (int i = 0; i < businessEventLogOutDto.getRowsCount(); i++) {
                    BusinessEventLogFinderOutRowDto rowDto = businessEventLogOutDto.getRows(i);
                    row = rows.newRow();
                    row.addElement("logId", rowDto.getLogId());
                    row.addElement("messageId", rowDto.getMessageId());
                    row.addElement("loggedOn", rowDto.getLoggedOn());
                    row.addElement("processName", rowDto.getProcessName());
                    row.addElement("subProcessName", rowDto.getSubProcessName());
                    row.addElement("messageType", rowDto.getMessageType());
                    row.addElement("messageText", rowDto.getMessageText());
                }
            }
        }
    }
    
    
    /** Getter for property numberOfRecords.
     * @return Value of property numberOfRecords.
     */
    public Long getNumberOfRecords() {
        GridModel rows = getRelatedBusinessEventLogWM();
        return rows != null && rows.getRows() != null ? new Long(rows.getRows().size()) : new Long(0);
    }
    
    /** Getter for property moreRecordsExist.
     * @return Value of property moreRecordsExist.
     */
    public boolean getMoreRecordsExist() {
        MessageViewerOutDto outputDto = ((MessageViewerComponent) getComponent()).getMessageViewerOutDto();
        BusinessEventLogFinderOutDto businessEventLogOutDto = outputDto.getBusinessEventLog();
        if (outputDto != null && businessEventLogOutDto != null && businessEventLogOutDto.getMoreRecordsExist() != null)
            return businessEventLogOutDto.getMoreRecordsExist().booleanValue();
        else
            return false;
    }
    
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = true;
        try {
            String htmlString = getPriorityWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            setPriority(Parser.parseInteger(htmlString));
        } catch (ValidationException e) {
            valid = false;
            e.setField("[label.Jaffa.Messaging.MessageViewer.Priority]");
            raiseError(request, "[label.Jaffa.Messaging.MessageViewer.Priority]", e);
        }
        return valid;
    }
    
}
