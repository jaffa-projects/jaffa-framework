// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.userviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.applications.jaffa.modules.admin.components.userviewer.dto.UserViewerOutDto;

import java.util.*;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.controller.GridController;
import org.jaffa.applications.jaffa.modules.admin.components.userviewer.dto.UserRoleDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the UserViewer.
 */
public class UserViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "jaffa_admin_userViewer";
    private static Logger log = Logger.getLogger(UserViewerForm.class);


    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        UserViewerOutDto outputDto = ((UserViewerComponent) getComponent()).getUserViewerOutDto();
        return outputDto != null ? outputDto.getUserName() : null;
    }

    /** Getter for property firstName.
     * @return Value of property firstName.
     */
    public java.lang.String getFirstName() {
        UserViewerOutDto outputDto = ((UserViewerComponent) getComponent()).getUserViewerOutDto();
        return outputDto != null ? outputDto.getFirstName() : null;
    }

    /** Getter for property lastName.
     * @return Value of property lastName.
     */
    public java.lang.String getLastName() {
        UserViewerOutDto outputDto = ((UserViewerComponent) getComponent()).getUserViewerOutDto();
        return outputDto != null ? outputDto.getLastName() : null;
    }

    /** Getter for property status.
     * @return Value of property status.
     */
    public java.lang.String getStatus() {
        UserViewerOutDto outputDto = ((UserViewerComponent) getComponent()).getUserViewerOutDto();
        return outputDto != null ? outputDto.getStatus() : null;
    }

    /** Getter for property statusDescription.
     * @return Value of property statusDescription.
     */
    public java.lang.String getStatusDescription() {
        UserViewerOutDto outputDto = ((UserViewerComponent) getComponent()).getUserViewerOutDto();
        return outputDto != null ? outputDto.getStatusDescription() : null;
    }

    /** Getter for property EMailAddress.
     * @return Value of property EMailAddress.
     */
    public java.lang.String getEMailAddress() {
        UserViewerOutDto outputDto = ((UserViewerComponent) getComponent()).getUserViewerOutDto();
        return outputDto != null ? outputDto.getEMailAddress() : null;
    }

    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        UserViewerOutDto outputDto = ((UserViewerComponent) getComponent()).getUserViewerOutDto();
        return outputDto != null ? outputDto.getCreatedOn() : null;
    }

    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        UserViewerOutDto outputDto = ((UserViewerComponent) getComponent()).getUserViewerOutDto();
        return outputDto != null ? outputDto.getCreatedBy() : null;
    }

    /** Getter for property lastUpdatedOn.
     * @return Value of property lastUpdatedOn.
     */
    public org.jaffa.datatypes.DateTime getLastUpdatedOn() {
        UserViewerOutDto outputDto = ((UserViewerComponent) getComponent()).getUserViewerOutDto();
        return outputDto != null ? outputDto.getLastUpdatedOn() : null;
    }

    /** Getter for property lastUpdatedBy.
     * @return Value of property lastUpdatedBy.
     */
    public java.lang.String getLastUpdatedBy() {
        UserViewerOutDto outputDto = ((UserViewerComponent) getComponent()).getUserViewerOutDto();
        return outputDto != null ? outputDto.getLastUpdatedBy() : null;
    }

    // .//GEN-END:_2_be
    // .//GEN-BEGIN:RelatedUserRole_1_be
    /** Getter for property UserRole. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property userRole.
     */
    public GridModel getRelatedUserRoleWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("relatedUserRole");
        if (rows == null) {
            rows = new GridModel();
            populateRelatedUserRole(rows);
            getWidgetCache().addModel("relatedUserRole", rows);
        }
        return rows;
    }
    
    /** Setter for property userRole. This is invoked by the servlet, when a post is done on the View screen.
     * It sets the selected rows on the model.
     * @param value New value of property userRole.
     */
    public void setRelatedUserRoleWV(String value) {
        GridController.updateModel(value, getRelatedUserRoleWM());
    }
    
    private void populateRelatedUserRole(GridModel rows) {
        rows.clearRows();
        UserViewerOutDto outputDto = ((UserViewerComponent) getComponent()).getUserViewerOutDto();
        if (outputDto != null) {
            GridModelRow row = null;
            UserRoleDto[] userRole = outputDto.getUserRole();
            for (int i = 0; i < userRole.length; i++) {
                UserRoleDto rowDto = userRole[i];
                row = rows.newRow();
                row.addElement("userName", rowDto.getUserName());
                row.addElement("roleName", rowDto.getRoleName());
                // .//GEN-END:RelatedUserRole_1_be
                // Add custom code for the row //GEN-FIRST:RelatedUserRole_1


                // .//GEN-LAST:RelatedUserRole_1
                // .//GEN-BEGIN:RelatedUserRole_2_be
            }
        }
    }
    // .//GEN-END:RelatedUserRole_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
