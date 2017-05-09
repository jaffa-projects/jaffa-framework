// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.components.codehelper.dto.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.maint.MaintForm;
import org.jaffa.datatypes.*;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.metadata.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.util.StringHelper;

import org.jaffa.applications.test.modules.time.components.usertimeentrymaintenance.dto.*;
import org.jaffa.applications.test.modules.time.domain.UserTimeEntryMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support the maintenance jsp of the UserTimeEntryMaintenance.
 */
public class UserTimeEntryMaintenanceForm extends MaintForm {

    private static Logger log = Logger.getLogger(UserTimeEntryMaintenanceForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:userName_1_be
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        return ( (UserTimeEntryMaintenanceComponent) getComponent() ).getUserName();
    }

    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(java.lang.String userName) {
        ( (UserTimeEntryMaintenanceComponent) getComponent() ).setUserName(userName);
    }

    /** Getter for property userName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property userName.
     */
    public EditBoxModel getUserNameWM() {
        EditBoxModel w_userName = (EditBoxModel) getWidgetCache().getModel("userName");
        if (w_userName == null) {
            if (getUserName() != null)
                w_userName = new EditBoxModel(getUserName(), (StringFieldMetaData) UserTimeEntryMeta.META_USER_NAME);
            else
                w_userName = new EditBoxModel((StringFieldMetaData) UserTimeEntryMeta.META_USER_NAME);
            // .//GEN-END:userName_1_be
            // Add custom code //GEN-FIRST:userName_1


            // .//GEN-LAST:userName_1
            // .//GEN-BEGIN:userName_2_be
            getWidgetCache().addModel("userName", w_userName);
        }
        return w_userName;
    }

    /** Setter for property userName. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property userName.
     */
    public void setUserNameWV(String value) {
        EditBoxController.updateModel(value, getUserNameWM());
    }
    // .//GEN-END:userName_2_be
    // .//GEN-BEGIN:projectCode_1_be
    /** Getter for property projectCode.
     * @return Value of property projectCode.
     */
    public java.lang.String getProjectCode() {
        return ( (UserTimeEntryMaintenanceComponent) getComponent() ).getProjectCode();
    }

    /** Setter for property projectCode.
     * @param projectCode New value of property projectCode.
     */
    public void setProjectCode(java.lang.String projectCode) {
        ( (UserTimeEntryMaintenanceComponent) getComponent() ).setProjectCode(projectCode);
    }

    /** Getter for property projectCode. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property projectCode.
     */
    public DropDownModel getProjectCodeWM() {
        DropDownModel w_projectCode = (DropDownModel) getWidgetCache().getModel("projectCode");
        if (w_projectCode == null) {
            w_projectCode = new DropDownModel((getProjectCode() != null ? getProjectCode() : ""), (StringFieldMetaData) UserTimeEntryMeta.META_PROJECT_CODE);
            CodeHelperOutElementDto dto = ( (UserTimeEntryMaintenanceComponent) getComponent() ).getProjectCodeCodes();
            if (dto != null && dto.getCodeHelperOutCodeDtoCount() > 0) {
                CodeHelperOutCodeDto[] codes = dto.getCodeHelperOutCodeDtos();
                for (int i = 0; i < codes.length; i++) {
                    CodeHelperOutCodeDto code = codes[i];
                    w_projectCode.addOption(Formatter.format(code.getDescription()), Formatter.format(code.getCode()));
                }
            }

            // .//GEN-END:projectCode_1_be
            // Add custom code //GEN-FIRST:projectCode_1
 

            // .//GEN-LAST:projectCode_1
            // .//GEN-BEGIN:projectCode_2_be
            getWidgetCache().addModel("projectCode", w_projectCode);
        }
        return w_projectCode;
    }

    /** Setter for property projectCode. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property projectCode.
     */
    public void setProjectCodeWV(String value) {
        DropDownController.updateModel(value, getProjectCodeWM());
    }
    // .//GEN-END:projectCode_2_be
    // .//GEN-BEGIN:activity_1_be
    /** Getter for property activity.
     * @return Value of property activity.
     */
    public java.lang.String getActivity() {
        return ( (UserTimeEntryMaintenanceComponent) getComponent() ).getActivity();
    }

    /** Setter for property activity.
     * @param activity New value of property activity.
     */
    public void setActivity(java.lang.String activity) {
        ( (UserTimeEntryMaintenanceComponent) getComponent() ).setActivity(activity);
    }

    /** Getter for property activity. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property activity.
     */
    public DropDownModel getActivityWM() {
        DropDownModel w_activity = (DropDownModel) getWidgetCache().getModel("activity");
        if (w_activity == null) {
            w_activity = new DropDownModel((getActivity() != null ? getActivity() : ""), (StringFieldMetaData) UserTimeEntryMeta.META_ACTIVITY);

            // .//GEN-END:activity_1_be
            // Add custom code //GEN-FIRST:activity_1


            // .//GEN-LAST:activity_1
            // .//GEN-BEGIN:activity_2_be
            getWidgetCache().addModel("activity", w_activity);
        }
        return w_activity;
    }

    /** Setter for property activity. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property activity.
     */
    public void setActivityWV(String value) {
        DropDownController.updateModel(value, getActivityWM());
    }
    // .//GEN-END:activity_2_be
    // .//GEN-BEGIN:task_1_be
    /** Getter for property task.
     * @return Value of property task.
     */
    public java.lang.String getTask() {
        return ( (UserTimeEntryMaintenanceComponent) getComponent() ).getTask();
    }

    /** Setter for property task.
     * @param task New value of property task.
     */
    public void setTask(java.lang.String task) {
        ( (UserTimeEntryMaintenanceComponent) getComponent() ).setTask(task);
    }

    /** Getter for property task. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property task.
     */
    public DropDownModel getTaskWM() {
        DropDownModel w_task = (DropDownModel) getWidgetCache().getModel("task");
        if (w_task == null) {
            w_task = new DropDownModel((getTask() != null ? getTask() : ""), (StringFieldMetaData) UserTimeEntryMeta.META_TASK);

            // .//GEN-END:task_1_be
            // Add custom code //GEN-FIRST:task_1


            // .//GEN-LAST:task_1
            // .//GEN-BEGIN:task_2_be
            getWidgetCache().addModel("task", w_task);
        }
        return w_task;
    }

    /** Setter for property task. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property task.
     */
    public void setTaskWV(String value) {
        DropDownController.updateModel(value, getTaskWM());
    }
    // .//GEN-END:task_2_be
    // .//GEN-BEGIN:periodStart_1_be
    /** Getter for property periodStart.
     * @return Value of property periodStart.
     */
    public org.jaffa.datatypes.DateTime getPeriodStart() {
        return ( (UserTimeEntryMaintenanceComponent) getComponent() ).getPeriodStart();
    }

    /** Setter for property periodStart.
     * @param periodStart New value of property periodStart.
     */
    public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart) {
        ( (UserTimeEntryMaintenanceComponent) getComponent() ).setPeriodStart(periodStart);
    }

    /** Getter for property periodStart. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property periodStart.
     */
    public DateTimeModel getPeriodStartWM() {
        DateTimeModel w_periodStart = (DateTimeModel) getWidgetCache().getModel("periodStart");
        if (w_periodStart == null) {
            w_periodStart = new DateTimeModel(getPeriodStart(), (DateTimeFieldMetaData) UserTimeEntryMeta.META_PERIOD_START);
            // .//GEN-END:periodStart_1_be
            // Add custom code //GEN-FIRST:periodStart_1


            // .//GEN-LAST:periodStart_1
            // .//GEN-BEGIN:periodStart_2_be
            getWidgetCache().addModel("periodStart", w_periodStart);
        }
        return w_periodStart;
    }

    /** Setter for property periodStart. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property periodStart.
     */
    public void setPeriodStartWV(String value) {
        DateTimeController.updateModel(value, getPeriodStartWM());
    }
    // .//GEN-END:periodStart_2_be
    // .//GEN-BEGIN:periodEnd_1_be
    /** Getter for property periodEnd.
     * @return Value of property periodEnd.
     */
    public org.jaffa.datatypes.DateTime getPeriodEnd() {
        return ( (UserTimeEntryMaintenanceComponent) getComponent() ).getPeriodEnd();
    }

    /** Setter for property periodEnd.
     * @param periodEnd New value of property periodEnd.
     */
    public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd) {
        ( (UserTimeEntryMaintenanceComponent) getComponent() ).setPeriodEnd(periodEnd);
    }

    /** Getter for property periodEnd. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property periodEnd.
     */
    public DateTimeModel getPeriodEndWM() {
        DateTimeModel w_periodEnd = (DateTimeModel) getWidgetCache().getModel("periodEnd");
        if (w_periodEnd == null) {
            w_periodEnd = new DateTimeModel(getPeriodEnd(), (DateTimeFieldMetaData) UserTimeEntryMeta.META_PERIOD_END);
            // .//GEN-END:periodEnd_1_be
            // Add custom code //GEN-FIRST:periodEnd_1


            // .//GEN-LAST:periodEnd_1
            // .//GEN-BEGIN:periodEnd_2_be
            getWidgetCache().addModel("periodEnd", w_periodEnd);
        }
        return w_periodEnd;
    }

    /** Setter for property periodEnd. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property periodEnd.
     */
    public void setPeriodEndWV(String value) {
        DateTimeController.updateModel(value, getPeriodEndWM());
    }
    // .//GEN-END:periodEnd_2_be
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
        // Add custom validation code //GEN-FIRST:_doValidate_1


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
            String htmlString = getUserNameWM().getValue();
            if (htmlString != null && htmlString.length() == 0)
                htmlString = null;
            java.lang.String value = (java.lang.String) FieldValidator.validateData(htmlString, (StringFieldMetaData) UserTimeEntryMeta.META_USER_NAME, true);

            setUserName(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserTimeEntryMeta.META_USER_NAME).getLabelToken(), e);
        }


        try {
            java.lang.String value = getProjectCodeWM().getValue();
            value = FieldValidator.validate(value, (StringFieldMetaData) UserTimeEntryMeta.META_PROJECT_CODE, true);
            setProjectCode(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserTimeEntryMeta.META_PROJECT_CODE).getLabelToken(), e);
        }


        try {
            java.lang.String value = getActivityWM().getValue();
            value = FieldValidator.validate(value, (StringFieldMetaData) UserTimeEntryMeta.META_ACTIVITY, true);
            setActivity(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserTimeEntryMeta.META_ACTIVITY).getLabelToken(), e);
        }


        try {
            java.lang.String value = getTaskWM().getValue();
            value = FieldValidator.validate(value, (StringFieldMetaData) UserTimeEntryMeta.META_TASK, true);
            setTask(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((StringFieldMetaData) UserTimeEntryMeta.META_TASK).getLabelToken(), e);
        }


        try {
            org.jaffa.datatypes.DateTime value = getPeriodStartWM().getValue();
            value = FieldValidator.validate(value, (DateTimeFieldMetaData) UserTimeEntryMeta.META_PERIOD_START, true);
            setPeriodStart(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((DateTimeFieldMetaData) UserTimeEntryMeta.META_PERIOD_START).getLabelToken(), e);
        }


        try {
            org.jaffa.datatypes.DateTime value = getPeriodEndWM().getValue();
            value = FieldValidator.validate(value, (DateTimeFieldMetaData) UserTimeEntryMeta.META_PERIOD_END, true);
            setPeriodEnd(value);
        } catch (ValidationException e) {
            valid = false;
            raiseError(request, ((DateTimeFieldMetaData) UserTimeEntryMeta.META_PERIOD_END).getLabelToken(), e);
        }


        // .//GEN-END:_doValidateMain_1_be
        // Add custom validation code //GEN-FIRST:_doValidateMain_1


        // .//GEN-LAST:_doValidateMain_1
        // .//GEN-BEGIN:_doValidateMain_2_be
        return valid;
    }
    // .//GEN-END:_doValidateMain_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
