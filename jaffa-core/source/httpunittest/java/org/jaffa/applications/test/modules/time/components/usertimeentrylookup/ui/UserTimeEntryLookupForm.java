// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.test.modules.time.components.usertimeentrylookup.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.lookup.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.util.StringHelper;
import org.jaffa.applications.test.modules.time.components.usertimeentrylookup.dto.UserTimeEntryLookupOutDto;
import org.jaffa.applications.test.modules.time.components.usertimeentrylookup.dto.UserTimeEntryLookupOutRowDto;
import org.jaffa.applications.test.modules.time.domain.UserTimeEntryMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support UserTimeEntryLookup.
 */
public class UserTimeEntryLookupForm extends LookupForm {

    private static Logger log = Logger.getLogger(UserTimeEntryLookupForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:userName_1_be
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public String getUserName() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getUserName();
    }

    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(String userName) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setUserName(userName);
    }

    /** Getter for property userName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property userName.
     */
    public EditBoxModel getUserNameWM() {
        EditBoxModel userNameModel = (EditBoxModel) getWidgetCache().getModel("userName");
        if (userNameModel == null) {
            if (getUserName() != null)
                userNameModel = new EditBoxModel( getUserName() );
            else
                userNameModel = new EditBoxModel();
            userNameModel.setStringCase( ((StringFieldMetaData) UserTimeEntryMeta.META_USER_NAME).getCaseType() );

            // .//GEN-END:userName_1_be
            // Add custom code //GEN-FIRST:userName_1


            // .//GEN-LAST:userName_1
            // .//GEN-BEGIN:userName_2_be
            getWidgetCache().addModel("userName", userNameModel);
        }
        return userNameModel;
    }

    /** Setter for property userName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property userName.
     */
    public void setUserNameWV(String value) {
        EditBoxController.updateModel(value, getUserNameWM());
    }

    /** Getter for DropDown property userName.
     * @return Value of property userNameDd.
     */
    public String getUserNameDd() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getUserNameDd();
    }

    /** Setter for DropDown property userName.
     * @param userNameDd New value of property userNameDd.
     */
    public void setUserNameDd(String userNameDd) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setUserNameDd(userNameDd);
    }

    /** Getter for DropDown property userName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property userNameDd.
     */
    public DropDownModel getUserNameDdWM() {
        DropDownModel userNameDdModel = (DropDownModel) getWidgetCache().getModel("userNameDd");
        if (userNameDdModel == null) {
            if (getUserNameDd() != null)
                userNameDdModel = new DropDownModel( getUserNameDd() );
            else
                userNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                userNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("userNameDd", userNameDdModel);
        }
        return userNameDdModel;
    }

    /** Setter for DropDown property userName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property userNameDd.
     */
    public void setUserNameDdWV(String value) {
        DropDownController.updateModel(value, getUserNameDdWM());
    }

    // .//GEN-END:userName_2_be
    // .//GEN-BEGIN:projectCode_1_be
    /** Getter for property projectCode.
     * @return Value of property projectCode.
     */
    public String getProjectCode() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getProjectCode();
    }

    /** Setter for property projectCode.
     * @param projectCode New value of property projectCode.
     */
    public void setProjectCode(String projectCode) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setProjectCode(projectCode);
    }

    /** Getter for property projectCode. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property projectCode.
     */
    public EditBoxModel getProjectCodeWM() {
        EditBoxModel projectCodeModel = (EditBoxModel) getWidgetCache().getModel("projectCode");
        if (projectCodeModel == null) {
            if (getProjectCode() != null)
                projectCodeModel = new EditBoxModel( getProjectCode() );
            else
                projectCodeModel = new EditBoxModel();
            projectCodeModel.setStringCase( ((StringFieldMetaData) UserTimeEntryMeta.META_PROJECT_CODE).getCaseType() );

            // .//GEN-END:projectCode_1_be
            // Add custom code //GEN-FIRST:projectCode_1


            // .//GEN-LAST:projectCode_1
            // .//GEN-BEGIN:projectCode_2_be
            getWidgetCache().addModel("projectCode", projectCodeModel);
        }
        return projectCodeModel;
    }

    /** Setter for property projectCode. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property projectCode.
     */
    public void setProjectCodeWV(String value) {
        EditBoxController.updateModel(value, getProjectCodeWM());
    }

    /** Getter for DropDown property projectCode.
     * @return Value of property projectCodeDd.
     */
    public String getProjectCodeDd() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getProjectCodeDd();
    }

    /** Setter for DropDown property projectCode.
     * @param projectCodeDd New value of property projectCodeDd.
     */
    public void setProjectCodeDd(String projectCodeDd) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setProjectCodeDd(projectCodeDd);
    }

    /** Getter for DropDown property projectCode. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property projectCodeDd.
     */
    public DropDownModel getProjectCodeDdWM() {
        DropDownModel projectCodeDdModel = (DropDownModel) getWidgetCache().getModel("projectCodeDd");
        if (projectCodeDdModel == null) {
            if (getProjectCodeDd() != null)
                projectCodeDdModel = new DropDownModel( getProjectCodeDd() );
            else
                projectCodeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                projectCodeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("projectCodeDd", projectCodeDdModel);
        }
        return projectCodeDdModel;
    }

    /** Setter for DropDown property projectCode. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property projectCodeDd.
     */
    public void setProjectCodeDdWV(String value) {
        DropDownController.updateModel(value, getProjectCodeDdWM());
    }

    // .//GEN-END:projectCode_2_be
    // .//GEN-BEGIN:activity_1_be
    /** Getter for property activity.
     * @return Value of property activity.
     */
    public String getActivity() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getActivity();
    }

    /** Setter for property activity.
     * @param activity New value of property activity.
     */
    public void setActivity(String activity) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setActivity(activity);
    }

    /** Getter for property activity. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property activity.
     */
    public EditBoxModel getActivityWM() {
        EditBoxModel activityModel = (EditBoxModel) getWidgetCache().getModel("activity");
        if (activityModel == null) {
            if (getActivity() != null)
                activityModel = new EditBoxModel( getActivity() );
            else
                activityModel = new EditBoxModel();
            activityModel.setStringCase( ((StringFieldMetaData) UserTimeEntryMeta.META_ACTIVITY).getCaseType() );

            // .//GEN-END:activity_1_be
            // Add custom code //GEN-FIRST:activity_1


            // .//GEN-LAST:activity_1
            // .//GEN-BEGIN:activity_2_be
            getWidgetCache().addModel("activity", activityModel);
        }
        return activityModel;
    }

    /** Setter for property activity. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property activity.
     */
    public void setActivityWV(String value) {
        EditBoxController.updateModel(value, getActivityWM());
    }

    /** Getter for DropDown property activity.
     * @return Value of property activityDd.
     */
    public String getActivityDd() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getActivityDd();
    }

    /** Setter for DropDown property activity.
     * @param activityDd New value of property activityDd.
     */
    public void setActivityDd(String activityDd) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setActivityDd(activityDd);
    }

    /** Getter for DropDown property activity. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property activityDd.
     */
    public DropDownModel getActivityDdWM() {
        DropDownModel activityDdModel = (DropDownModel) getWidgetCache().getModel("activityDd");
        if (activityDdModel == null) {
            if (getActivityDd() != null)
                activityDdModel = new DropDownModel( getActivityDd() );
            else
                activityDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                activityDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("activityDd", activityDdModel);
        }
        return activityDdModel;
    }

    /** Setter for DropDown property activity. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property activityDd.
     */
    public void setActivityDdWV(String value) {
        DropDownController.updateModel(value, getActivityDdWM());
    }

    // .//GEN-END:activity_2_be
    // .//GEN-BEGIN:task_1_be
    /** Getter for property task.
     * @return Value of property task.
     */
    public String getTask() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getTask();
    }

    /** Setter for property task.
     * @param task New value of property task.
     */
    public void setTask(String task) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setTask(task);
    }

    /** Getter for property task. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property task.
     */
    public EditBoxModel getTaskWM() {
        EditBoxModel taskModel = (EditBoxModel) getWidgetCache().getModel("task");
        if (taskModel == null) {
            if (getTask() != null)
                taskModel = new EditBoxModel( getTask() );
            else
                taskModel = new EditBoxModel();
            taskModel.setStringCase( ((StringFieldMetaData) UserTimeEntryMeta.META_TASK).getCaseType() );

            // .//GEN-END:task_1_be
            // Add custom code //GEN-FIRST:task_1


            // .//GEN-LAST:task_1
            // .//GEN-BEGIN:task_2_be
            getWidgetCache().addModel("task", taskModel);
        }
        return taskModel;
    }

    /** Setter for property task. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property task.
     */
    public void setTaskWV(String value) {
        EditBoxController.updateModel(value, getTaskWM());
    }

    /** Getter for DropDown property task.
     * @return Value of property taskDd.
     */
    public String getTaskDd() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getTaskDd();
    }

    /** Setter for DropDown property task.
     * @param taskDd New value of property taskDd.
     */
    public void setTaskDd(String taskDd) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setTaskDd(taskDd);
    }

    /** Getter for DropDown property task. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property taskDd.
     */
    public DropDownModel getTaskDdWM() {
        DropDownModel taskDdModel = (DropDownModel) getWidgetCache().getModel("taskDd");
        if (taskDdModel == null) {
            if (getTaskDd() != null)
                taskDdModel = new DropDownModel( getTaskDd() );
            else
                taskDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                taskDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("taskDd", taskDdModel);
        }
        return taskDdModel;
    }

    /** Setter for DropDown property task. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property taskDd.
     */
    public void setTaskDdWV(String value) {
        DropDownController.updateModel(value, getTaskDdWM());
    }

    // .//GEN-END:task_2_be
    // .//GEN-BEGIN:periodStart_1_be
    /** Getter for property periodStart.
     * @return Value of property periodStart.
     */
    public String getPeriodStart() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getPeriodStart();
    }

    /** Setter for property periodStart.
     * @param periodStart New value of property periodStart.
     */
    public void setPeriodStart(String periodStart) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setPeriodStart(periodStart);
    }

    /** Getter for property periodStart. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property periodStart.
     */
    public EditBoxModel getPeriodStartWM() {
        EditBoxModel periodStartModel = (EditBoxModel) getWidgetCache().getModel("periodStart");
        if (periodStartModel == null) {
            if (getPeriodStart() != null)
                periodStartModel = new EditBoxModel( getPeriodStart() );
            else
                periodStartModel = new EditBoxModel();

            // .//GEN-END:periodStart_1_be
            // Add custom code //GEN-FIRST:periodStart_1


            // .//GEN-LAST:periodStart_1
            // .//GEN-BEGIN:periodStart_2_be
            getWidgetCache().addModel("periodStart", periodStartModel);
        }
        return periodStartModel;
    }

    /** Setter for property periodStart. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property periodStart.
     */
    public void setPeriodStartWV(String value) {
        EditBoxController.updateModel(value, getPeriodStartWM());
    }

    /** Getter for DropDown property periodStart.
     * @return Value of property periodStartDd.
     */
    public String getPeriodStartDd() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getPeriodStartDd();
    }

    /** Setter for DropDown property periodStart.
     * @param periodStartDd New value of property periodStartDd.
     */
    public void setPeriodStartDd(String periodStartDd) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setPeriodStartDd(periodStartDd);
    }

    /** Getter for DropDown property periodStart. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property periodStartDd.
     */
    public DropDownModel getPeriodStartDdWM() {
        DropDownModel periodStartDdModel = (DropDownModel) getWidgetCache().getModel("periodStartDd");
        if (periodStartDdModel == null) {
            if (getPeriodStartDd() != null)
                periodStartDdModel = new DropDownModel( getPeriodStartDd() );
            else
                periodStartDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getDateCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                periodStartDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("periodStartDd", periodStartDdModel);
        }
        return periodStartDdModel;
    }

    /** Setter for DropDown property periodStart. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property periodStartDd.
     */
    public void setPeriodStartDdWV(String value) {
        DropDownController.updateModel(value, getPeriodStartDdWM());
    }

    // .//GEN-END:periodStart_2_be
    // .//GEN-BEGIN:periodEnd_1_be
    /** Getter for property periodEnd.
     * @return Value of property periodEnd.
     */
    public String getPeriodEnd() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getPeriodEnd();
    }

    /** Setter for property periodEnd.
     * @param periodEnd New value of property periodEnd.
     */
    public void setPeriodEnd(String periodEnd) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setPeriodEnd(periodEnd);
    }

    /** Getter for property periodEnd. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property periodEnd.
     */
    public EditBoxModel getPeriodEndWM() {
        EditBoxModel periodEndModel = (EditBoxModel) getWidgetCache().getModel("periodEnd");
        if (periodEndModel == null) {
            if (getPeriodEnd() != null)
                periodEndModel = new EditBoxModel( getPeriodEnd() );
            else
                periodEndModel = new EditBoxModel();

            // .//GEN-END:periodEnd_1_be
            // Add custom code //GEN-FIRST:periodEnd_1


            // .//GEN-LAST:periodEnd_1
            // .//GEN-BEGIN:periodEnd_2_be
            getWidgetCache().addModel("periodEnd", periodEndModel);
        }
        return periodEndModel;
    }

    /** Setter for property periodEnd. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property periodEnd.
     */
    public void setPeriodEndWV(String value) {
        EditBoxController.updateModel(value, getPeriodEndWM());
    }

    /** Getter for DropDown property periodEnd.
     * @return Value of property periodEndDd.
     */
    public String getPeriodEndDd() {
        return ( (UserTimeEntryLookupComponent) getComponent() ).getPeriodEndDd();
    }

    /** Setter for DropDown property periodEnd.
     * @param periodEndDd New value of property periodEndDd.
     */
    public void setPeriodEndDd(String periodEndDd) {
        ( (UserTimeEntryLookupComponent) getComponent() ).setPeriodEndDd(periodEndDd);
    }

    /** Getter for DropDown property periodEnd. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property periodEndDd.
     */
    public DropDownModel getPeriodEndDdWM() {
        DropDownModel periodEndDdModel = (DropDownModel) getWidgetCache().getModel("periodEndDd");
        if (periodEndDdModel == null) {
            if (getPeriodEndDd() != null)
                periodEndDdModel = new DropDownModel( getPeriodEndDd() );
            else
                periodEndDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getDateCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                periodEndDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("periodEndDd", periodEndDdModel);
        }
        return periodEndDdModel;
    }

    /** Setter for DropDown property periodEnd. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property periodEndDd.
     */
    public void setPeriodEndDdWV(String value) {
        DropDownController.updateModel(value, getPeriodEndDdWM());
    }

    // .//GEN-END:periodEnd_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        String value = null;
        StringBuffer buf = null;

        value = getUserNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setUserName(value);
        setUserNameDd(getUserNameDdWM().getValue());

        value = getProjectCodeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setProjectCode(value);
        setProjectCodeDd(getProjectCodeDdWM().getValue());

        value = getActivityWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setActivity(value);
        setActivityDd(getActivityDdWM().getValue());

        value = getTaskWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setTask(value);
        setTaskDd(getTaskDdWM().getValue());

        value = getPeriodStartWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setPeriodStart(value);
        setPeriodStartDd(getPeriodStartDdWM().getValue());

        value = getPeriodEndWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setPeriodEnd(value);
        setPeriodEndDd(getPeriodEndDdWM().getValue());

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
        UserTimeEntryLookupOutDto outputDto = (UserTimeEntryLookupOutDto) ((UserTimeEntryLookupComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                UserTimeEntryLookupOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement(LookupComponent2.MULTI_SELECT_CHECKBOX, new CheckBoxModel(false));
                row.addElement("userName", rowDto.getUserName());
                row.addElement("projectCode", rowDto.getProjectCode());
                row.addElement("activity", rowDto.getActivity());
                row.addElement("task", rowDto.getTask());
                row.addElement("periodStart", rowDto.getPeriodStart());
                row.addElement("periodEnd", rowDto.getPeriodEnd());
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
