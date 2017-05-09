// .//GEN-BEGIN:_1_be
package org.jaffa.applications.test.modules.material.components.itemfinder.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.presentation.portlet.widgets.controller.UserGridController;
import org.jaffa.components.finder.IFinderListener;
import java.util.EventObject;
import org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderOutDto;
import org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderOutRowDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the Results jsp of the ItemFinder.
 */
public class ItemFinderResultsForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */
    public static final String NAME = "material_itemFinderResults";

    private static final Logger log = Logger.getLogger(ItemFinderResultsForm.class);
    private GridModel w_rows = null;
    private boolean m_moreRecordsExist = false;

    /** Getter for property rows. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property qty.
     */
    public GridModel getRowsWM() {
        if (w_rows == null) {
            w_rows = (GridModel) getWidgetCache().getModel("rows");
            if (w_rows == null) {
                w_rows = new GridModel();
                getWidgetCache().addModel("rows", w_rows);
                populateRows();
            }
        }
        return w_rows;
    }

    /** Setter for property rows. This is invoked by the servlet, when a post is done on the Results screen.
     * It sets the selected rows on the model.
     * @param value New value of property qty.
     */
    public void setRowsWV(String value) {
        UserGridController.updateModel(value, getRowsWM(), this);
    }

    /** Getter for property moreRecordsExist.
     * @return Value of property moreRecordsExist.
     */
    public boolean getMoreRecordsExist() {
        return m_moreRecordsExist;
    }

    /** Setter for property moreRecordsExist.
     * @param moreRecordsExist New value of property moreRecordsExist.
     */
    public void setMoreRecordsExist(boolean moreRecordsExist) {
        m_moreRecordsExist = moreRecordsExist;
    }
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_populateRows_1_be
    private void populateRows() {
        GridModel rows = getRowsWM();
        rows.clearRows();
        ItemFinderOutDto outputDto = ((ItemFinderComponent) getComponent()).getItemFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                ItemFinderOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement("segregationCode", rowDto.getSegregationCode());
                row.addElement("itemId", rowDto.getItemId());
                row.addElement("partNo", rowDto.getPartNo());
                row.addElement("serial", rowDto.getSerial());
                row.addElement("qty", rowDto.getQty());
                // .//GEN-END:_populateRows_1_be
                // Add custom code for the row //GEN-FIRST:_populateRows_1


                // .//GEN-LAST:_populateRows_1
                // .//GEN-BEGIN:_populateRows_2_be
            }
        }
    }
    // .//GEN-END:_populateRows_2_be
    // .//GEN-BEGIN:_determineMoreRecordsExist_1_be
    private void determineMoreRecordsExist() {
        ItemFinderOutDto outputDto = ((ItemFinderComponent) getComponent()).getItemFinderOutDto();
        if (outputDto != null && outputDto.getMoreRecordsExist() != null && outputDto.getMoreRecordsExist().booleanValue())
            m_moreRecordsExist = true;
        else
            m_moreRecordsExist = false;
    }
    // .//GEN-END:_determineMoreRecordsExist_1_be
    // .//GEN-BEGIN:_initForm_1_be
    /** This registers a listener with the Component. */
    public void initForm() {
        super.initForm();
        determineMoreRecordsExist();

        ((ItemFinderComponent) getComponent()).setFinderListener( new IFinderListener() {
            public void inquiryDone(EventObject eventObject) {
                // .//GEN-END:_initForm_1_be
                // Add custom code //GEN-FIRST:_initForm_1


                // .//GEN-LAST:_initForm_1
                // .//GEN-BEGIN:_initForm_2_be
                populateRows();
                determineMoreRecordsExist();
            }
        });
        // .//GEN-END:_initForm_2_be
        // Add custom code //GEN-FIRST:_initForm_2


        // .//GEN-LAST:_initForm_2
        // .//GEN-BEGIN:_initForm_3_be
    }
    // .//GEN-END:_initForm_3_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
