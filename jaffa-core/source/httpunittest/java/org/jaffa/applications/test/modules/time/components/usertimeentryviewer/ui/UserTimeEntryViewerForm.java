// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.test.modules.time.components.usertimeentryviewer.ui;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.applications.test.modules.time.components.usertimeentryviewer.dto.UserTimeEntryViewerOutDto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the View jsp of the UserTimeEntryViewer.
 */
public class UserTimeEntryViewerForm extends FormBase {
    /** The name constant used for determining the corresponding jsp through the struts-config file.
     */    
    public static final String NAME = "usertimeentry_userTimeEntryViewer";
    private static Logger log = Logger.getLogger(UserTimeEntryViewerForm.class);


    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        UserTimeEntryViewerOutDto outputDto = ((UserTimeEntryViewerComponent) getComponent()).getUserTimeEntryViewerOutDto();
        return outputDto != null ? outputDto.getUserName() : null;
    }

    /** Getter for property projectCode.
     * @return Value of property projectCode.
     */
    public java.lang.String getProjectCode() {
        UserTimeEntryViewerOutDto outputDto = ((UserTimeEntryViewerComponent) getComponent()).getUserTimeEntryViewerOutDto();
        return outputDto != null ? outputDto.getProjectCode() : null;
    }

    /** Getter for property activity.
     * @return Value of property activity.
     */
    public java.lang.String getActivity() {
        UserTimeEntryViewerOutDto outputDto = ((UserTimeEntryViewerComponent) getComponent()).getUserTimeEntryViewerOutDto();
        return outputDto != null ? outputDto.getActivity() : null;
    }

    /** Getter for property task.
     * @return Value of property task.
     */
    public java.lang.String getTask() {
        UserTimeEntryViewerOutDto outputDto = ((UserTimeEntryViewerComponent) getComponent()).getUserTimeEntryViewerOutDto();
        return outputDto != null ? outputDto.getTask() : null;
    }

    /** Getter for property periodStart.
     * @return Value of property periodStart.
     */
    public org.jaffa.datatypes.DateTime getPeriodStart() {
        UserTimeEntryViewerOutDto outputDto = ((UserTimeEntryViewerComponent) getComponent()).getUserTimeEntryViewerOutDto();
        return outputDto != null ? outputDto.getPeriodStart() : null;
    }

    /** Getter for property periodEnd.
     * @return Value of property periodEnd.
     */
    public org.jaffa.datatypes.DateTime getPeriodEnd() {
        UserTimeEntryViewerOutDto outputDto = ((UserTimeEntryViewerComponent) getComponent()).getUserTimeEntryViewerOutDto();
        return outputDto != null ? outputDto.getPeriodEnd() : null;
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
