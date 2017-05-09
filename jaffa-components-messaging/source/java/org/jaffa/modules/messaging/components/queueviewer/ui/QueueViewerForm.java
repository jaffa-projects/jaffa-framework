package org.jaffa.modules.messaging.components.queueviewer.ui;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.modules.messaging.components.queueviewer.dto.*;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.controller.GridController;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;



/** The FormBean class to support the View jsp of the QueueViewer.
 */
public class QueueViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */
    public static final String NAME = "jaffa_messaging_queueViewer";
    private static Logger log = Logger.getLogger(QueueViewerForm.class);


    /** Getter for property queue.
     * @return Value of property queue.
     */
    public java.lang.String getQueue() {
        return ( (QueueViewerComponent) getComponent() ).getQueue();
    }

    /** Setter for property queue.
     * @param queue New value of property queue.
     */
    public void setQueue(java.lang.String queue) {
        ( (QueueViewerComponent) getComponent() ).setQueue(queue);
    }

    /** Getter for property filter.
     * @return Value of property filter.
     */
    public java.lang.String getFilter() {
        return ( (QueueViewerComponent) getComponent() ).getFilter();
    }

    /** Setter for property filter.
     * @param filter New value of property filter.
     */
    public void setFilter(java.lang.String filter) {
        ( (QueueViewerComponent) getComponent() ).setFilter(filter);
    }

    /** Getter for property filter. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property filter.
     */
    public EditBoxModel getFilterWM() {
        EditBoxModel filterModel = (EditBoxModel) getWidgetCache().getModel("filter");
        if (filterModel == null) {
            if (getFilter() != null)
                filterModel = new EditBoxModel( getFilter() );
            else
                filterModel = new EditBoxModel();

            getWidgetCache().addModel("filter", filterModel);
        }
        return filterModel;
    }

    /** Setter for property filter. This is invoked by the servlet, when a post is done on the Viewer screen.
     * @param value New value of property filter.
     */
    public void setFilterWV(String value) {
        EditBoxController.updateModel(value, getFilterWM());
    }

    /** Getter for property QueueHeader. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property queueHeader.
     */
    public GridModel getRelatedQueueHeaderWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("relatedQueueHeader");
        if (rows == null) {
            rows = new GridModel();
            populateRelatedQueueHeader(rows);
            getWidgetCache().addModel("relatedQueueHeader", rows);
        }
        return rows;
    }

    /** Setter for property queueHeader. This is invoked by the servlet, when a post is done on the View screen.
     * It sets the selected rows on the model.
     * @param value New value of property queueHeader.
     */
    public void setRelatedQueueHeaderWV(String value) {
        GridController.updateModel(value, getRelatedQueueHeaderWM());
    }

    private void populateRelatedQueueHeader(GridModel rows) {
        rows.clearRows();
        QueueViewerOutDto outputDto = ((QueueViewerComponent) getComponent()).getQueueViewerOutDto();
        if (outputDto != null) {
            GridModelRow row = null;
            MessageDto[] messages = outputDto.getMessages();
            if(messages != null) {
                for (int i = 0; i < messages.length; i++) {
                    MessageDto message = messages[i];
                    HeaderElementDto[] headerElements =  message.getHeaderElements();
                    row = rows.newRow();
                    row.addElement("messageId", message.getMessageId());
                    row.addElement("manageable", message.getManageable());
                    if (headerElements != null) {
                        for (int j = 0; j < headerElements.length; j++)
                            row.addElement("header" + j, headerElements[j].getValue());
                    }
                }
            }
        }
    }

    public boolean doValidate(HttpServletRequest request) {
        boolean valid = true;
        String value = null;
        StringBuffer buf = null;

        value = getFilterWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFilter(value);

        return valid;
    }
}
