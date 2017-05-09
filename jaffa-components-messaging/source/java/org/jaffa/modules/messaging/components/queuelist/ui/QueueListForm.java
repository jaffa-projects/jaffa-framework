package org.jaffa.modules.messaging.components.queuelist.ui;

import org.apache.log4j.Logger;
import org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutDto;
import org.jaffa.modules.messaging.components.queuelist.dto.QueueListOutRowDto;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.controller.GridController;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;


/** The FormBean class to support QueueList.
 */
public class QueueListForm extends FormBase {
    
    private static Logger log = Logger.getLogger(QueueListForm.class);
    
    /** Getter for property rows. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property rows.
     */
    public GridModel getRowsWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("rows");
        if (rows == null) {
            rows = new GridModel();
            populateRows(rows);
            getWidgetCache().addModel("rows", rows);
        }
        return rows;
    }
    
    /** Setter for property rows. This is invoked by the servlet, when a post is done on the screen.
     * It sets the selected rows on the model.
     * @param value New value of property rows.
     */
    public void setRowsWV(String value) {
        GridController.updateModel(value, getRowsWM());
    }
    
    /** This will populate the input GridModel with the data in the finderOutDto of the Component.
     * @param rows The GridModel object to populate.
     */
    public void populateRows(GridModel rows) {
        rows.clearRows();
        QueueListOutDto outputDto = (QueueListOutDto) ((QueueListComponent) getComponent()).getFinderOutDto();
        if (outputDto != null && outputDto.getRows() != null) {
            GridModelRow row;
            for (QueueListOutRowDto rowDto : outputDto.getRows()) {
                row = rows.newRow();
                row.addElement("queue", rowDto.getQueue());
                row.addElement("pending", rowDto.getPending());
                row.addElement("error", rowDto.getError());
                row.addElement("inProcess", rowDto.getInProcess());
                row.addElement("activeConsumer", rowDto.getActiveConsumer());
                row.addElement("started", rowDto.getStarted());
                row.addElement("stopped", rowDto.getStopped());
            }
        }
    }
    
}
