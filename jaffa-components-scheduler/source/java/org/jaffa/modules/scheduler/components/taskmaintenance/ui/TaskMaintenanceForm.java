/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package org.jaffa.modules.scheduler.components.taskmaintenance.ui;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.jaffa.components.maint.MaintForm;
import org.jaffa.datatypes.DateTime;
import org.jaffa.metadata.IntegerFieldMetaData;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutDto;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto;
import org.jaffa.modules.scheduler.components.taskmaintenance.dto.TaskMaintenanceDto;
import org.jaffa.modules.scheduler.services.Recurrence;
import org.jaffa.modules.scheduler.services.ScheduledTask;
import org.jaffa.presentation.portlet.widgets.controller.GridController;
import org.jaffa.presentation.portlet.widgets.controller.SimpleWidgetController;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;

/** The FormBean class to support the maintenance jsp of the TaskMaintenance.
 */
public class TaskMaintenanceForm extends MaintForm {
    
    public enum RecurrenceEnum {ONCE_ONLY, OFTEN, DAILY, WEEKLY, MONTHLY, YEARLY, CUSTOM}
    public enum MonthlyType {DAY, WEEK}
    
    private static final int MONTHS_GRID_COLUMNS = 4; //Number of columns in the layout for the months grid
    
    // *******************************************************
    // ******************* Task properties *******************
    // *******************************************************
    
    /** Getter for property scheduledTaskId.
     * @return Value of property scheduledTaskId.
     */
    public String getScheduledTaskId() {
        return ((TaskMaintenanceComponent) getComponent()).getScheduledTaskId();
    }
    
    /** Getter for property scheduledTaskId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property scheduledTaskId.
     */
    public SimpleWidgetModel getScheduledTaskIdWM() {
        SimpleWidgetModel w_scheduledTaskId = (SimpleWidgetModel) getWidgetCache().getModel("scheduledTaskId");
        if (w_scheduledTaskId == null) {
            w_scheduledTaskId = new SimpleWidgetModel(getScheduledTaskId());
            getWidgetCache().addModel("scheduledTaskId", w_scheduledTaskId);
        }
        return w_scheduledTaskId;
    }
    
    /** Setter for property scheduledTaskId. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property scheduledTaskId.
     */
    public void setScheduledTaskIdWV(String value) {
        SimpleWidgetController.updateModel(value, getScheduledTaskIdWM());
    }
    
    /** Getter for property taskType.
     * @return Value of property taskType.
     */
    public String getTaskType() {
        return ((TaskMaintenanceComponent) getComponent()).getTaskType();
    }
    
    /** Getter for property taskType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property taskType.
     */
    public SimpleWidgetModel getTaskTypeWM() {
        SimpleWidgetModel w_taskType = (SimpleWidgetModel) getWidgetCache().getModel("taskType");
        if (w_taskType == null) {
            w_taskType = new SimpleWidgetModel(getTaskType());
            Collection<String> taskTypeCodes = ((TaskMaintenanceComponent) getComponent()).getTaskTypeCodes();
            if (taskTypeCodes != null) {
                for (String taskTypeCode : taskTypeCodes)
                    w_taskType.addOption(taskTypeCode, taskTypeCode);
            }
            getWidgetCache().addModel("taskType", w_taskType);
        }
        return w_taskType;
    }
    
    /** Setter for property taskType. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property taskType.
     */
    public void setTaskTypeWV(String value) {
        SimpleWidgetController.updateModel(value, getTaskTypeWM());
    }
    
    /** Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return ((TaskMaintenanceComponent) getComponent()).getDescription();
    }
    
    /** Getter for property description. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property description.
     */
    public SimpleWidgetModel getDescriptionWM() {
        SimpleWidgetModel w_description = (SimpleWidgetModel) getWidgetCache().getModel("description");
        if (w_description == null) {
            w_description = new SimpleWidgetModel(getDescription());
            getWidgetCache().addModel("description", w_description);
        }
        return w_description;
    }
    
    /** Setter for property description. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property description.
     */
    public void setDescriptionWV(String value) {
        SimpleWidgetController.updateModel(value, getDescriptionWM());
    }
    
    /** Getter for property runAs.
     * @return Value of property runAs.
     */
    public String getRunAs() {
        return ((TaskMaintenanceComponent) getComponent()).getRunAs();
    }
    
    /** Getter for property runAs. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property runAs.
     */
    public SimpleWidgetModel getRunAsWM() {
        SimpleWidgetModel w_runAs = (SimpleWidgetModel) getWidgetCache().getModel("runAs");
        if (w_runAs == null) {
            w_runAs = new SimpleWidgetModel(getRunAs());
            getWidgetCache().addModel("runAs", w_runAs);
        }
        return w_runAs;
    }
    
    /** Setter for property runAs. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property runAs.
     */
    public void setRunAsWV(String value) {
        SimpleWidgetController.updateModel(value, getRunAsWM());
    }
    
    /** Getter for property startOn.
     * @return Value of property startOn.
     */
    public DateTime getStartOn() {
        return ((TaskMaintenanceComponent) getComponent()).getStartOn();
    }
    
    /** Getter for property startOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property startOn.
     */
    public SimpleWidgetModel getStartOnWM() {
        SimpleWidgetModel w_startOn = (SimpleWidgetModel) getWidgetCache().getModel("startOn");
        if (w_startOn == null) {
            w_startOn = new SimpleWidgetModel(getStartOn());
            getWidgetCache().addModel("startOn", w_startOn);
        }
        return w_startOn;
    }
    
    /** Setter for property startOn. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property startOn.
     */
    public void setStartOnWV(String value) {
        SimpleWidgetController.updateModel(value, getStartOnWM());
    }
    
    /** Getter for property endOn.
     * @return Value of property endOn.
     */
    public DateTime getEndOn() {
        return ((TaskMaintenanceComponent) getComponent()).getEndOn();
    }
    
    /** Getter for property endOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property endOn.
     */
    public SimpleWidgetModel getEndOnWM() {
        SimpleWidgetModel w_endOn = (SimpleWidgetModel) getWidgetCache().getModel("endOn");
        if (w_endOn == null) {
            w_endOn = new SimpleWidgetModel(getEndOn());
            getWidgetCache().addModel("endOn", w_endOn);
        }
        return w_endOn;
    }
    
    /** Setter for property endOn. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property endOn.
     */
    public void setEndOnWV(String value) {
        SimpleWidgetController.updateModel(value, getEndOnWM());
    }
    
    /** Getter for property misfireRecovery.
     * @return Value of property misfireRecovery.
     */
    public String getMisfireRecovery() {
        ScheduledTask.MisfireRecovery misfireRecovery = ((TaskMaintenanceComponent) getComponent()).getMisfireRecovery();
        return misfireRecovery != null ? misfireRecovery.toString() : null;
    }
    
    /** Getter for property misfireRecovery. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property misfireRecovery.
     */
    public SimpleWidgetModel getMisfireRecoveryWM() {
        SimpleWidgetModel w_misfireRecovery = (SimpleWidgetModel) getWidgetCache().getModel("misfireRecovery");
        if (w_misfireRecovery == null) {
            w_misfireRecovery = new SimpleWidgetModel(getMisfireRecovery() != null ? getMisfireRecovery().toString() : null);
            getWidgetCache().addModel("misfireRecovery", w_misfireRecovery);
        }
        return w_misfireRecovery;
    }
    
    /** Setter for property misfireRecovery. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property misfireRecovery.
     */
    public void setMisfireRecoveryWV(String value) {
        SimpleWidgetController.updateModel(value, getMisfireRecoveryWM());
    }
    
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public DateTime getCreatedOn() {
        return ((TaskMaintenanceComponent) getComponent()).getCreatedOn();
    }
    
    /** Getter for property createdOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdOn.
     */
    public SimpleWidgetModel getCreatedOnWM() {
        SimpleWidgetModel w_createdOn = (SimpleWidgetModel) getWidgetCache().getModel("createdOn");
        if (w_createdOn == null) {
            w_createdOn = new SimpleWidgetModel(getCreatedOn());
            getWidgetCache().addModel("createdOn", w_createdOn);
        }
        return w_createdOn;
    }
    
    /** Setter for property createdOn. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property createdOn.
     */
    public void setCreatedOnWV(String value) {
        SimpleWidgetController.updateModel(value, getCreatedOnWM());
    }
    
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public String getCreatedBy() {
        return ((TaskMaintenanceComponent) getComponent()).getCreatedBy();
    }
    
    /** Getter for property createdBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property createdBy.
     */
    public SimpleWidgetModel getCreatedByWM() {
        SimpleWidgetModel w_createdBy = (SimpleWidgetModel) getWidgetCache().getModel("createdBy");
        if (w_createdBy == null) {
            w_createdBy = new SimpleWidgetModel(getCreatedBy());
            getWidgetCache().addModel("createdBy", w_createdBy);
        }
        return w_createdBy;
    }
    
    /** Setter for property createdBy. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property createdBy.
     */
    public void setCreatedByWV(String value) {
        SimpleWidgetController.updateModel(value, getCreatedByWM());
    }
    
    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public DateTime getLastChangedOn() {
        return ((TaskMaintenanceComponent) getComponent()).getLastChangedOn();
    }
    
    /** Getter for property lastChangedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedOn.
     */
    public SimpleWidgetModel getLastChangedOnWM() {
        SimpleWidgetModel w_lastChangedOn = (SimpleWidgetModel) getWidgetCache().getModel("lastChangedOn");
        if (w_lastChangedOn == null) {
            w_lastChangedOn = new SimpleWidgetModel(getLastChangedOn());
            getWidgetCache().addModel("lastChangedOn", w_lastChangedOn);
        }
        return w_lastChangedOn;
    }
    
    /** Setter for property lastChangedOn. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property lastChangedOn.
     */
    public void setLastChangedOnWV(String value) {
        SimpleWidgetController.updateModel(value, getLastChangedOnWM());
    }
    
    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public String getLastChangedBy() {
        return ((TaskMaintenanceComponent) getComponent()).getLastChangedBy();
    }
    
    /** Getter for property lastChangedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastChangedBy.
     */
    public SimpleWidgetModel getLastChangedByWM() {
        SimpleWidgetModel w_lastChangedBy = (SimpleWidgetModel) getWidgetCache().getModel("lastChangedBy");
        if (w_lastChangedBy == null) {
            w_lastChangedBy = new SimpleWidgetModel(getLastChangedBy());
            getWidgetCache().addModel("lastChangedBy", w_lastChangedBy);
        }
        return w_lastChangedBy;
    }
    
    /** Setter for property lastChangedBy. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property lastChangedBy.
     */
    public void setLastChangedByWV(String value) {
        SimpleWidgetController.updateModel(value, getLastChangedByWM());
    }
    
    /** Getter for property nextDue.
     * @return Value of property nextDue.
     */
    public DateTime getNextDue() {
        return ((TaskMaintenanceComponent) getComponent()).getNextDue();
    }
    
    /** Getter for property lastRunOn.
     * @return Value of property lastRunOn.
     */
    public DateTime getLastRunOn() {
        return ((TaskMaintenanceComponent) getComponent()).getLastRunOn();
    }
    
    /** Getter for property status.
     * @return Value of property status.
     */
    public String getStatus() {
        ScheduledTask.TaskStatusEnumeration status = ((TaskMaintenanceComponent) getComponent()).getStatus();
        return status != null ? status.toString() : null;
    }
    
    
    // *******************************************************
    // **************** Recurrence properties ****************
    // *******************************************************
    
    /** Getter for property recurrence.
     * @return Value of property recurrence.
     */
    public String getRecurrence() {
        RecurrenceEnum recurrence = null;
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        if (r != null) {
            if (r instanceof Recurrence.Often)
                recurrence = RecurrenceEnum.OFTEN;
            else if (r instanceof Recurrence.Daily)
                recurrence = RecurrenceEnum.DAILY;
            else if (r instanceof Recurrence.Weekly)
                recurrence = RecurrenceEnum.WEEKLY;
            else if (r instanceof Recurrence.Monthly)
                recurrence = RecurrenceEnum.MONTHLY;
            else if (r instanceof Recurrence.Yearly)
                recurrence = RecurrenceEnum.YEARLY;
            else if (r instanceof Recurrence.Custom)
                recurrence = RecurrenceEnum.CUSTOM;
        }
        if (r == null)
            recurrence = RecurrenceEnum.ONCE_ONLY;
        return recurrence.toString();
    }
    
    /** Getter for property recurrence. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property recurrence.
     */
    public SimpleWidgetModel getRecurrenceWM() {
        SimpleWidgetModel w_recurrence = (SimpleWidgetModel) getWidgetCache().getModel("recurrence");
        if (w_recurrence == null) {
            w_recurrence = new SimpleWidgetModel(getRecurrence());
            getWidgetCache().addModel("recurrence", w_recurrence);
        }
        return w_recurrence;
    }
    
    /** Setter for property recurrence. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property recurrence.
     */
    public void setRecurrenceWV(String value) {
        SimpleWidgetController.updateModel(value, getRecurrenceWM());
    }
    
    /** Getter for property oftenHours.
     * @return Value of property oftenHours.
     */
    public Long getOftenHours() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Integer oftenHours = r != null && r instanceof Recurrence.Often ? ((Recurrence.Often) r).getHours() : null;
        return oftenHours != null ? new Long(oftenHours) : null;
    }
    
    /** Getter for property oftenHours. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property oftenHours.
     */
    public SimpleWidgetModel getOftenHoursWM() {
        SimpleWidgetModel w_oftenHours = (SimpleWidgetModel) getWidgetCache().getModel("oftenHours");
        if (w_oftenHours == null) {
            // Allow positive integers only
            IntegerFieldMetaData fieldMetaData = new IntegerFieldMetaData("OftenHours", "[label.Jaffa.Scheduler.ScheduledTask.Recurrence.OFTEN.Hours]", Boolean.FALSE, null, new Long(0), null, null);
            w_oftenHours = new SimpleWidgetModel(getOftenHours(), fieldMetaData);
            getWidgetCache().addModel("oftenHours", w_oftenHours);
        }
        return w_oftenHours;
    }
    
    /** Setter for property oftenHours. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property oftenHours.
     */
    public void setOftenHoursWV(String value) {
        SimpleWidgetController.updateModel(value, getOftenHoursWM());
    }
    
    /** Getter for property oftenMinutes.
     * @return Value of property oftenMinutes.
     */
    public Long getOftenMinutes() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Integer oftenMinutes = r != null && r instanceof Recurrence.Often ? ((Recurrence.Often) r).getMinutes() : null;
        return oftenMinutes != null ? new Long(oftenMinutes) : null;
    }
    
    /** Getter for property oftenMinutes. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property oftenMinutes.
     */
    public SimpleWidgetModel getOftenMinutesWM() {
        SimpleWidgetModel w_oftenMinutes = (SimpleWidgetModel) getWidgetCache().getModel("oftenMinutes");
        if (w_oftenMinutes == null) {
            w_oftenMinutes = new SimpleWidgetModel(getOftenMinutes());
            for (int i = 0; i <= 59; i++) {
                String value = Integer.toString(i);
                w_oftenMinutes.addOption(value, value);
            }
            getWidgetCache().addModel("oftenMinutes", w_oftenMinutes);
        }
        return w_oftenMinutes;
    }
    
    /** Setter for property oftenMinutes. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property oftenMinutes.
     */
    public void setOftenMinutesWV(String value) {
        SimpleWidgetController.updateModel(value, getOftenMinutesWM());
    }
    
    /** Getter for property oftenSeconds.
     * @return Value of property oftenSeconds.
     */
    public Long getOftenSeconds() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Integer oftenSeconds = r != null && r instanceof Recurrence.Often ? ((Recurrence.Often) r).getSeconds() : null;
        return oftenSeconds != null ? new Long(oftenSeconds) : null;
    }
    
    /** Getter for property oftenSeconds. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property oftenSeconds.
     */
    public SimpleWidgetModel getOftenSecondsWM() {
        SimpleWidgetModel w_oftenSeconds = (SimpleWidgetModel) getWidgetCache().getModel("oftenSeconds");
        if (w_oftenSeconds == null) {
            w_oftenSeconds = new SimpleWidgetModel(getOftenSeconds());
            for (int i = 0; i <= 59; i++) {
                String value = Integer.toString(i);
                w_oftenSeconds.addOption(value, value);
            }
            getWidgetCache().addModel("oftenSeconds", w_oftenSeconds);
        }
        return w_oftenSeconds;
    }
    
    /** Setter for property oftenSeconds. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property oftenSeconds.
     */
    public void setOftenSecondsWV(String value) {
        SimpleWidgetController.updateModel(value, getOftenSecondsWM());
    }
    
    /** Getter for property dailyWeekDaysOnly.
     * @return Value of property dailyWeekDaysOnly.
     */
    public Boolean getDailyWeekDaysOnly() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Boolean weekDaysOnly = r != null && r instanceof Recurrence.Daily ? ((Recurrence.Daily) r).getWeekDaysOnly() : null;
        return weekDaysOnly != null ? weekDaysOnly : Boolean.FALSE;
    }
    
    /** Getter for property dailyWeekDaysOnly. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property dailyWeekDaysOnly.
     */
    public SimpleWidgetModel getDailyWeekDaysOnlyWM() {
        SimpleWidgetModel w_dailyWeekDaysOnly = (SimpleWidgetModel) getWidgetCache().getModel("dailyWeekDaysOnly");
        if (w_dailyWeekDaysOnly == null) {
            w_dailyWeekDaysOnly = new SimpleWidgetModel(getDailyWeekDaysOnly());
            getWidgetCache().addModel("dailyWeekDaysOnly", w_dailyWeekDaysOnly);
        }
        return w_dailyWeekDaysOnly;
    }
    
    /** Setter for property dailyWeekDaysOnly. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property dailyWeekDaysOnly.
     */
    public void setDailyWeekDaysOnlyWV(String value) {
        SimpleWidgetController.updateModel(value, getDailyWeekDaysOnlyWM());
    }
    
    /** Getter for property weeklyFrequency.
     * @return Value of property weeklyFrequency.
     */
    public String getWeeklyFrequency() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Recurrence.WeekFrequency weeklyFrequency = r != null && r instanceof Recurrence.Weekly ? ((Recurrence.Weekly) r).getFrequency() : null;
        return weeklyFrequency != null ? weeklyFrequency.toString() : null;
    }
    
    /** Getter for property weeklyFrequency. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property weeklyFrequency.
     */
    public SimpleWidgetModel getWeeklyFrequencyWM() {
        SimpleWidgetModel w_weeklyFrequency = (SimpleWidgetModel) getWidgetCache().getModel("weeklyFrequency");
        if (w_weeklyFrequency == null) {
            w_weeklyFrequency = new SimpleWidgetModel(getWeeklyFrequency());
            addWeekFrequencyOptions(w_weeklyFrequency);
            getWidgetCache().addModel("weeklyFrequency", w_weeklyFrequency);
        }
        return w_weeklyFrequency;
    }
    
    /** Setter for property weeklyFrequency. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property weeklyFrequency.
     */
    public void setWeeklyFrequencyWV(String value) {
        SimpleWidgetController.updateModel(value, getWeeklyFrequencyWM());
    }
    
    /** Getter for property weeklyDay.
     * @return Value of property weeklyDay.
     */
    public String getWeeklyDay() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Recurrence.WeekDay weeklyDay = r != null && r instanceof Recurrence.Weekly ? ((Recurrence.Weekly) r).getDay() : null;
        return weeklyDay != null ? weeklyDay.toString() : null;
    }
    
    /** Getter for property weeklyDay. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property weeklyDay.
     */
    public SimpleWidgetModel getWeeklyDayWM() {
        SimpleWidgetModel w_weeklyDay = (SimpleWidgetModel) getWidgetCache().getModel("weeklyDay");
        if (w_weeklyDay == null) {
            w_weeklyDay = new SimpleWidgetModel(getWeeklyDay());
            addWeekDayOptions(w_weeklyDay);
            getWidgetCache().addModel("weeklyDay", w_weeklyDay);
        }
        return w_weeklyDay;
    }
    
    /** Setter for property weeklyDay. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property weeklyDay.
     */
    public void setWeeklyDayWV(String value) {
        SimpleWidgetController.updateModel(value, getWeeklyDayWM());
    }
    
    /** Getter for property monthlyType.
     * @return Value of property monthlyType.
     */
    public String getMonthlyType() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        return r != null && r instanceof Recurrence.Monthly
                && (((Recurrence.Monthly) r).getDay() == null || ((Recurrence.Monthly) r).getDay() == 0)
                ? MonthlyType.WEEK.toString() : MonthlyType.DAY.toString();
    }
    
    /** Getter for property monthlyType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property monthlyType.
     */
    public SimpleWidgetModel getMonthlyTypeWM() {
        SimpleWidgetModel w_monthlyType = (SimpleWidgetModel) getWidgetCache().getModel("monthlyType");
        if (w_monthlyType == null) {
            w_monthlyType = new SimpleWidgetModel(getMonthlyType());
            w_monthlyType.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Type.DAY]", MonthlyType.DAY.toString());
            w_monthlyType.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Type.WEEK]", MonthlyType.WEEK.toString());
            getWidgetCache().addModel("monthlyType", w_monthlyType);
        }
        return w_monthlyType;
    }
    
    /** Setter for property monthlyType. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property monthlyType.
     */
    public void setMonthlyTypeWV(String value) {
        SimpleWidgetController.updateModel(value, getMonthlyTypeWM());
    }
    
    /** Getter for property monthlyDay.
     * @return Value of property monthlyDay.
     */
    public Long getMonthlyDay() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Integer monthlyDay = r != null && r instanceof Recurrence.Monthly ? ((Recurrence.Monthly) r).getDay() : null;
        return monthlyDay  != null ? new Long(monthlyDay ) : null;
    }
    
    /** Getter for property monthlyDay. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property monthlyDay.
     */
    public SimpleWidgetModel getMonthlyDayWM() {
        SimpleWidgetModel w_monthlyDay = (SimpleWidgetModel) getWidgetCache().getModel("monthlyDay");
        if (w_monthlyDay == null) {
            w_monthlyDay = new SimpleWidgetModel(getMonthlyDay());
            for (int i = 1; i <= 31; i++) {
                String value = Integer.toString(i);
                w_monthlyDay.addOption(value, value);
            }
            getWidgetCache().addModel("monthlyDay", w_monthlyDay);
        }
        return w_monthlyDay;
    }
    
    /** Setter for property monthlyDay. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property monthlyDay.
     */
    public void setMonthlyDayWV(String value) {
        SimpleWidgetController.updateModel(value, getMonthlyDayWM());
    }
    
    /** Getter for property monthlyWeekFrequency.
     * @return Value of property monthlyWeekFrequency.
     */
    public String getMonthlyWeekFrequency() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Recurrence.WeekFrequency monthlyWeekFrequency = r != null && r instanceof Recurrence.Monthly ? ((Recurrence.Monthly) r).getWeekFrequency() : null;
        return monthlyWeekFrequency != null ? monthlyWeekFrequency.toString() : null;
    }
    
    /** Getter for property monthlyWeekFrequency. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property monthlyWeekFrequency.
     */
    public SimpleWidgetModel getMonthlyWeekFrequencyWM() {
        SimpleWidgetModel w_monthlyWeekFrequency = (SimpleWidgetModel) getWidgetCache().getModel("monthlyWeekFrequency");
        if (w_monthlyWeekFrequency == null) {
            w_monthlyWeekFrequency = new SimpleWidgetModel(getMonthlyWeekFrequency());
            addWeekFrequencyOptions(w_monthlyWeekFrequency);
            getWidgetCache().addModel("monthlyWeekFrequency", w_monthlyWeekFrequency);
        }
        return w_monthlyWeekFrequency;
    }
    
    /** Setter for property monthlyWeekFrequency. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property monthlyWeekFrequency.
     */
    public void setMonthlyWeekFrequencyWV(String value) {
        SimpleWidgetController.updateModel(value, getMonthlyWeekFrequencyWM());
    }
    
    /** Getter for property monthlyWeekDay.
     * @return Value of property monthlyWeekDay.
     */
    public String getMonthlyWeekDay() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Recurrence.WeekDay monthlyWeekDay = r != null && r instanceof Recurrence.Monthly ? ((Recurrence.Monthly) r).getWeekDay() : null;
        return monthlyWeekDay != null ? monthlyWeekDay.toString() : null;
    }
    
    /** Getter for property monthlyWeekDay. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property monthlyWeekDay.
     */
    public SimpleWidgetModel getMonthlyWeekDayWM() {
        SimpleWidgetModel w_monthlyWeekDay = (SimpleWidgetModel) getWidgetCache().getModel("monthlyWeekDay");
        if (w_monthlyWeekDay == null) {
            w_monthlyWeekDay = new SimpleWidgetModel(getMonthlyWeekDay());
            addWeekDayOptions(w_monthlyWeekDay);
            getWidgetCache().addModel("monthlyWeekDay", w_monthlyWeekDay);
        }
        return w_monthlyWeekDay;
    }
    
    /** Setter for property monthlyWeekDay. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property monthlyWeekDay.
     */
    public void setMonthlyWeekDayWV(String value) {
        SimpleWidgetController.updateModel(value, getMonthlyWeekDayWM());
    }
    
    /** Getter for property monthlyMonths. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property businessEventLog.
     */
    public GridModel getMonthlyMonthsWM() {
        GridModel model = (GridModel) getWidgetCache().getModel("monthlyMonths");
        if (model == null) {
            model = new GridModel();
            populateMonthlyMonths(model);
            getWidgetCache().addModel("monthlyMonths", model);
        }
        return model;
    }
    
    /** Setter for property userId. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property userId.
     */
    public void setMonthlyMonthsWV(String value) {
        GridController.updateModel(value, getMonthlyMonthsWM());
    }
    
    private void populateMonthlyMonths(GridModel model) {
        model.clearRows();
        
        // Get current list of months
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Recurrence.Month[] months = r != null && r instanceof Recurrence.Monthly ? ((Recurrence.Monthly) r).getMonths() : null;
        if (months != null)
            Arrays.sort(months);
        
        // Get all possible months
        Recurrence.Month[] allMonths = Recurrence.Month.values();
        
        // Create rows, such that the labels appear vertically-sorted.. like a newspaper !!
        // To begin with, select all the months
        int noOfRows = allMonths.length % MONTHS_GRID_COLUMNS == 0 ? allMonths.length / MONTHS_GRID_COLUMNS : (allMonths.length / MONTHS_GRID_COLUMNS) + 1;
        for (int i = 0; i < MONTHS_GRID_COLUMNS; i++) {
            for (int j = 0; j < noOfRows; j++) {
                int index = (i * noOfRows) + j;
                if (index >= allMonths.length)
                    break;
                Recurrence.Month month = allMonths[index];
                Boolean selected = months == null ? Boolean.TRUE : Arrays.binarySearch(months, month) >= 0;
                GridModelRow row = (i == 0) ? model.newRow() : model.getRow(j);
                row.addElement("label" + i, "label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month." + month);
                row.addElement("value" + i, month);
                row.addElement("selected" + i, new SimpleWidgetModel(selected));
            }
        }
    }
    
    /** Getter for property yearlyFrequency.
     * @return Value of property yearlyFrequency.
     */
    public Long getYearlyFrequency() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Integer yearlyFrequency = r != null && r instanceof Recurrence.Yearly ? ((Recurrence.Yearly) r).getFrequency() : null;
        return yearlyFrequency != null ? new Long(yearlyFrequency) : null;
    }
    
    /** Getter for property yearlyFrequency. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property yearlyFrequency.
     */
    public SimpleWidgetModel getYearlyFrequencyWM() {
        SimpleWidgetModel w_yearlyFrequency = (SimpleWidgetModel) getWidgetCache().getModel("yearlyFrequency");
        if (w_yearlyFrequency == null) {
            // Allow positive integers only
            IntegerFieldMetaData fieldMetaData = new IntegerFieldMetaData("YearlyFrequency", "[label.Jaffa.Scheduler.ScheduledTask.Recurrence.YEARLY.Frequency]", Boolean.FALSE, null, new Long(0), null, null);
            w_yearlyFrequency = new SimpleWidgetModel(getYearlyFrequency(), fieldMetaData);
            getWidgetCache().addModel("yearlyFrequency", w_yearlyFrequency);
        }
        return w_yearlyFrequency;
    }
    
    /** Setter for property yearlyFrequency. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property yearlyFrequency.
     */
    public void setYearlyFrequencyWV(String value) {
        SimpleWidgetController.updateModel(value, getYearlyFrequencyWM());
    }
    
    /** Getter for property yearlyOn.
     * @return Value of property yearlyOn.
     */
    public String getYearlyOn() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        Recurrence.Month yearlyOn = r != null && r instanceof Recurrence.Yearly ? ((Recurrence.Yearly) r).getOn() : null;
        return yearlyOn != null ? yearlyOn.toString() : null;
    }
    
    /** Getter for property yearlyOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property yearlyOn.
     */
    public SimpleWidgetModel getYearlyOnWM() {
        SimpleWidgetModel w_yearlyOn = (SimpleWidgetModel) getWidgetCache().getModel("yearlyOn");
        if (w_yearlyOn == null) {
            w_yearlyOn = new SimpleWidgetModel(getYearlyOn());
            addMonthOptions(w_yearlyOn);
            getWidgetCache().addModel("yearlyOn", w_yearlyOn);
        }
        return w_yearlyOn;
    }
    
    /** Setter for property yearlyOn. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property yearlyOn.
     */
    public void setYearlyOnWV(String value) {
        SimpleWidgetController.updateModel(value, getYearlyOnWM());
    }
    
    /** Getter for property customPattern.
     * @return Value of property customPattern.
     */
    public String getCustomPattern() {
        Recurrence r = ((TaskMaintenanceComponent) getComponent()).getRecurrence();
        return r != null && r instanceof Recurrence.Custom ? ((Recurrence.Custom) r).getCustomPattern() : null;
    }
    
    /** Getter for property customPattern. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property customPattern.
     */
    public SimpleWidgetModel getCustomPatternWM() {
        SimpleWidgetModel w_customPattern = (SimpleWidgetModel) getWidgetCache().getModel("customPattern");
        if (w_customPattern == null) {
            w_customPattern = new SimpleWidgetModel(getCustomPattern());
            getWidgetCache().addModel("customPattern", w_customPattern);
        }
        return w_customPattern;
    }
    
    /** Setter for property customPattern. This is invoked by the servlet, when a post is done on the JSP.
     * @param value New value of property customPattern.
     */
    public void setCustomPatternWV(String value) {
        SimpleWidgetController.updateModel(value, getCustomPatternWM());
    }
    
    
    // *******************************************************
    // ******************* Validation routines ***************
    // *******************************************************
    
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        if (!doValidate0(request))
            valid = false;
        return valid;
    }
    
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate0(HttpServletRequest request) {
        TaskMaintenanceDto taskMaintenanceDto = ((TaskMaintenanceComponent) getComponent()).getTaskMaintenanceDto();
        String value;
        
        value = getScheduledTaskIdWM().getStringValue();
        if (value != null && value.length() == 0)
            value = null;
        taskMaintenanceDto.setScheduledTaskId(value);
        
        value = getTaskTypeWM().getStringValue();
        if (value != null && value.length() == 0)
            value = null;
        taskMaintenanceDto.setTaskType(value);
        
        value = getDescriptionWM().getStringValue();
        if (value != null && value.length() == 0)
            value = null;
        taskMaintenanceDto.setDescription(value);
        
        value = getRunAsWM().getStringValue();
        if (value != null && value.length() == 0)
            value = null;
        taskMaintenanceDto.setRunAs(value);
        
        taskMaintenanceDto.setStartOn(getStartOnWM().getDateTimeValue());
        
        taskMaintenanceDto.setEndOn(getEndOnWM().getDateTimeValue());
        
        value = getMisfireRecoveryWM().getStringValue();
        if (value != null && value.length() == 0)
            value = null;
        taskMaintenanceDto.setMisfireRecovery(value != null ? ScheduledTask.MisfireRecovery.valueOf(value) : null);
        
        taskMaintenanceDto.setCreatedOn(getCreatedOnWM().getDateTimeValue());
        
        value = getCreatedByWM().getStringValue();
        if (value != null && value.length() == 0)
            value = null;
        taskMaintenanceDto.setCreatedBy(value);
        
        taskMaintenanceDto.setLastChangedOn(getLastChangedOnWM().getDateTimeValue());
        
        value = getLastChangedByWM().getStringValue();
        if (value != null && value.length() == 0)
            value = null;
        taskMaintenanceDto.setLastChangedBy(value);
        
        value = getRecurrenceWM().getStringValue();
        if (value != null && value.length() == 0)
            value = null;
        if (value == null) {
            taskMaintenanceDto.setRecurrence(null);
        } else if (RecurrenceEnum.OFTEN.toString().equals(value)) {
            value = getOftenHoursWM().getStringValue();
            if (value != null && value.length() == 0)
                value = null;
            Integer hours = value != null ? new Integer(value) : null;
            
            value = getOftenMinutesWM().getStringValue();
            if (value != null && value.length() == 0)
                value = null;
            Integer minutes = value != null ? new Integer(value) : null;
            
            value = getOftenSecondsWM().getStringValue();
            if (value != null && value.length() == 0)
                value = null;
            Integer seconds = value != null ? new Integer(value) : null;
            
            taskMaintenanceDto.setRecurrence(new Recurrence.Often(hours, minutes, seconds));
        } else if (RecurrenceEnum.DAILY.toString().equals(value)) {
            taskMaintenanceDto.setRecurrence(new Recurrence.Daily(getDailyWeekDaysOnlyWM().getBooleanValue()));
        } else if (RecurrenceEnum.WEEKLY.toString().equals(value)) {
            value = getWeeklyFrequencyWM().getStringValue();
            if (value != null && value.length() == 0)
                value = null;
            Recurrence.WeekFrequency frequency = value != null ? Recurrence.WeekFrequency.valueOf(value) : null;
            
            value = getWeeklyDayWM().getStringValue();
            if (value != null && value.length() == 0)
                value = null;
            Recurrence.WeekDay day = value != null ? Recurrence.WeekDay.valueOf(value) : null;
            
            taskMaintenanceDto.setRecurrence(new Recurrence.Weekly(frequency, day));
        } else if (RecurrenceEnum.MONTHLY.toString().equals(value)) {
            Integer day = null;
            Recurrence.WeekFrequency weekFrequency = null;
            Recurrence.WeekDay weekDay = null;
            
            value = getMonthlyTypeWM().getStringValue();
            if (value != null && value.length() == 0)
                value = null;
            if (value == null || value.equals(MonthlyType.DAY.toString())) {
                value = getMonthlyDayWM().getStringValue();
                if (value != null && value.length() == 0)
                    value = null;
                day = value != null ? new Integer(value) : null;
            } else {
                value = getMonthlyWeekFrequencyWM().getStringValue();
                if (value != null && value.length() == 0)
                    value = null;
                weekFrequency = value != null ? Recurrence.WeekFrequency.valueOf(value) : null;
                
                value = getMonthlyWeekDayWM().getStringValue();
                if (value != null && value.length() == 0)
                    value = null;
                weekDay = value != null ? Recurrence.WeekDay.valueOf(value) : null;
            }
            
            // Build the list of selected months
            Collection<Recurrence.Month> months = new TreeSet<Recurrence.Month>();
            for (Iterator itr = getMonthlyMonthsWM().getRows().iterator(); itr.hasNext(); ) {
                GridModelRow row = (GridModelRow) itr.next();
                for (int i = 0; i < MONTHS_GRID_COLUMNS; i++) {
                    Boolean selected = ((SimpleWidgetModel) row.get("selected" + i)).getBooleanValue();
                    if (selected != null && selected) {
                        Recurrence.Month month = (Recurrence.Month) row.get("value" + i);
                        months.add(month);
                    }
                }
            }
            
            taskMaintenanceDto.setRecurrence(new Recurrence.Monthly(day, weekFrequency, weekDay, months.toArray(new Recurrence.Month[months.size()])));
        } else if (RecurrenceEnum.YEARLY.toString().equals(value)) {
            value = getYearlyFrequencyWM().getStringValue();
            if (value != null && value.length() == 0)
                value = null;
            Integer frequency = value != null ? new Integer(value) : null;
            
            value = getYearlyOnWM().getStringValue();
            if (value != null && value.length() == 0)
                value = null;
            Recurrence.Month on = value != null ? Recurrence.Month.valueOf(value) : null;
            
            taskMaintenanceDto.setRecurrence(new Recurrence.Yearly(frequency, on));
        } else if (RecurrenceEnum.CUSTOM.toString().equals(value)) {
            value = getCustomPatternWM().getStringValue();
            if (value != null && value.length() == 0)
                value = null;
            taskMaintenanceDto.setRecurrence(new Recurrence.Custom(value));
        } else
            taskMaintenanceDto.setRecurrence(null);
        
        return true;
    }
    
    
    // *******************************************************
    // ******************* Misc properties *******************
    // *******************************************************
    
    /**
     * Getter for property serializedBusinessObject.
     * @return Value of property serializedBusinessObject.
     */
    public String getSerializedBusinessObject() {
        return ((TaskMaintenanceComponent) getComponent()).getSerializedBusinessObject();
    }
    
    /** Getter for property BusinessEventLog. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
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
    
    /** Setter for property businessEventLog. This is invoked by the servlet, when a post is done on the View screen.
     * It sets the selected rows on the model.
     * @param value New value of property businessEventLog.
     */
    public void setRelatedBusinessEventLogWV(String value) {
        GridController.updateModel(value, getRelatedBusinessEventLogWM());
    }
    
    private void populateRelatedBusinessEventLog(GridModel rows) {
        rows.clearRows();
        BusinessEventLogFinderOutDto businessEventLogOutDto = ((TaskMaintenanceComponent) getComponent()).getBusinessEventLog();
        if(businessEventLogOutDto != null){
            for (int i = 0; i < businessEventLogOutDto.getRowsCount(); i++) {
                BusinessEventLogFinderOutRowDto rowDto = businessEventLogOutDto.getRows(i);
                GridModelRow row = rows.newRow();
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
        BusinessEventLogFinderOutDto businessEventLogOutDto = ((TaskMaintenanceComponent) getComponent()).getBusinessEventLog();
        if (businessEventLogOutDto != null && businessEventLogOutDto.getMoreRecordsExist() != null)
            return businessEventLogOutDto.getMoreRecordsExist().booleanValue();
        else
            return false;
    }
    
    
    // *******************************************************
    // ******************* Helper routines *******************
    // *******************************************************
    
    private static void addWeekFrequencyOptions(SimpleWidgetModel model) {
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Frequency.FIRST]", Recurrence.WeekFrequency.FIRST.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Frequency.SECOND]", Recurrence.WeekFrequency.SECOND.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Frequency.THIRD]", Recurrence.WeekFrequency.THIRD.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Frequency.FOURTH]", Recurrence.WeekFrequency.FOURTH.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Frequency.LAST]", Recurrence.WeekFrequency.LAST.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Frequency.EVERY]", Recurrence.WeekFrequency.EVERY.toString());
    }
    
    private static void addWeekDayOptions(SimpleWidgetModel model) {
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Day.SUNDAY]", Recurrence.WeekDay.SUNDAY.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Day.MONDAY]", Recurrence.WeekDay.MONDAY.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Day.TUESDAY]", Recurrence.WeekDay.TUESDAY.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Day.WEDNESDAY]", Recurrence.WeekDay.WEDNESDAY.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Day.THURSDAY]", Recurrence.WeekDay.THURSDAY.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Day.FRIDAY]", Recurrence.WeekDay.FRIDAY.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.WEEKLY.Day.SATURDAY]", Recurrence.WeekDay.SATURDAY.toString());
    }
    
    private static void addMonthOptions(SimpleWidgetModel model) {
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.JANUARY]", Recurrence.Month.JANUARY.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.FEBRUARY]", Recurrence.Month.FEBRUARY.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.MARCH]", Recurrence.Month.MARCH.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.APRIL]", Recurrence.Month.APRIL.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.MAY]", Recurrence.Month.MAY.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.JUNE]", Recurrence.Month.JUNE.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.JULY]", Recurrence.Month.JULY.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.AUGUST]", Recurrence.Month.AUGUST.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.SEPTEMBER]", Recurrence.Month.SEPTEMBER.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.OCTOBER]", Recurrence.Month.OCTOBER.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.NOVEMBER]", Recurrence.Month.NOVEMBER.toString());
        model.addOption("[label.Jaffa.Scheduler.ScheduledTask.Recurrence.MONTHLY.Month.DECEMBER]", Recurrence.Month.DECEMBER.toString());
    }
    
}
