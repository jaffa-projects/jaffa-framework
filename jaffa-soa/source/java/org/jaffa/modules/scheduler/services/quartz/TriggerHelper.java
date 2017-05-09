/*
 *
 *  **************************************************TAPESTRY SOLUTIONS PROPRIETARY VER 2.0
 *  *
 *  *  Copyright © 2016 Tapestry Solutions, Inc.
 *  *  THIS PROGRAM IS PROPRIETARY TO TAPESTRY SOLUTIONS, INC.
 *  *  REPRODUCTION, DISCLOSURE, OR USE, IN WHOLE OR IN PART,
 *  *  UNDERTAKEN EXCEPT WITH PRIOR WRITTEN AUTHORIZATION OF
 *  *  TAPESTRY SOLUTIONS IS PROHIBITED.
 *  *
 *  **************************************************TAPESTRY SOLUTIONS PROPRIETARY VER 2.0
 *
 */
package org.jaffa.modules.scheduler.services.quartz;

import java.text.ParseException;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.scheduler.services.*;
import org.quartz.CronTrigger;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.triggers.AbstractTrigger;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;

/** Based on a given Recurrence, builds a Trigger for the Quartz Scheduler.
 */
public class TriggerHelper {
    
    private static final Logger log = Logger.getLogger(TriggerHelper.class);
    
    /** Based on a given Recurrence, builds a Trigger for the Quartz Scheduler.
     * @param task the Task.
     * @return the Trigger.
     * @throws FrameworkException in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static Trigger taskToTrigger(ScheduledTask task) throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Building a Trigger for " + task);
        
        // Validate the recurrence
        if (task.getRecurrence() != null)
            task.getRecurrence().validate();
        
        // Ensure that the startOn is not null
        if (task.getStartOn() == null)
            task.setStartOn(new DateTime());
        
        // Build the Trigger based on the Recurrence
        AbstractTrigger<?> trigger = null;
        if (task.getRecurrence() == null)
            trigger = new SimpleTriggerImpl();
        else if (task.getRecurrence() instanceof Recurrence.Often)
            trigger = createTrigger(task, (Recurrence.Often) task.getRecurrence());
        else if (task.getRecurrence() instanceof Recurrence.Daily)
            trigger = createTrigger(task, (Recurrence.Daily) task.getRecurrence());
        else if (task.getRecurrence() instanceof Recurrence.Weekly)
            trigger = createTrigger(task, (Recurrence.Weekly) task.getRecurrence());
        else if (task.getRecurrence() instanceof Recurrence.Monthly)
            trigger = createTrigger(task, (Recurrence.Monthly) task.getRecurrence());
        else if (task.getRecurrence() instanceof Recurrence.Yearly)
            trigger = createTrigger(task, (Recurrence.Yearly) task.getRecurrence());
        else if (task.getRecurrence() instanceof Recurrence.Custom)
            trigger = createTrigger(task, (Recurrence.Custom) task.getRecurrence());
        else {
            String s = "Unsupported Recurrence passed " + task.getRecurrence().getClass();
            log.fatal(s);
            throw new IllegalArgumentException(s);
        }
        
        // Stamp the fields on the Trigger
        trigger.setName(task.getScheduledTaskId());
        trigger.setGroup(QuartzSchedulerHelper.JOB_GROUP);
        if (task.getMisfireRecovery() != null) {
            if (task.getMisfireRecovery() == ScheduledTask.MisfireRecovery.START_AS_SCHEDULED)
                trigger.setMisfireInstruction(trigger instanceof CronTrigger ? CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING : SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
            else if (task.getMisfireRecovery() == ScheduledTask.MisfireRecovery.RUN_LAST_MISSED)
                trigger.setMisfireInstruction(trigger instanceof CronTrigger ? CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW : SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        }
        trigger.setStartTime(task.getStartOn().getUtilDate());
        if (task.getEndOn() != null)
            trigger.setEndTime(task.getEndOn().getUtilDate());
        
        if (log.isDebugEnabled())
            log.debug("Built trigger: " + trigger);
        return trigger;
    }
    
    
    
    /** Creates a SimpleTrigger with an interval computed from the hours, minutes and seconds.
     */
    private static AbstractTrigger<?> createTrigger(ScheduledTask task, Recurrence.Often recurrence) throws FrameworkException, ApplicationExceptions {
        // Compute the repeatInterval for the SimpleTrigger in milliseconds
        long repeatInterval = 0;
        if (recurrence.getHours() != null)
            repeatInterval += recurrence.getHours() * 60 * 60 * 1000;
        if (recurrence.getMinutes() != null)
            repeatInterval += recurrence.getMinutes() * 60 * 1000;
        if (recurrence.getSeconds() != null)
            repeatInterval += recurrence.getSeconds() * 1000;
        
        SimpleTriggerImpl trigger = new SimpleTriggerImpl();
        if (repeatInterval > 0) {
            trigger.setRepeatInterval(repeatInterval);
            trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        }
        return trigger;
    }
    
    /** The following logic is used to build the cron expression.
     * seconds: Set to startOnSecond.
     * minutes: Set to startOnMinute.
     * hours: Set to startOnHour.
     * day-of-month: Set to No specific value.
     * month: All values
     * day-of-week: Set to MON-FRI, if weekDays is true. Else set to All values.
     */
    private static AbstractTrigger<?> createTrigger(ScheduledTask task, Recurrence.Daily recurrence) throws FrameworkException, ApplicationExceptions {
        StringBuilder buf = new StringBuilder();
        
        // startOn values for seconds, minutes and hours
        buf.append(task.getStartOn().second())
        .append(' ')
        .append(task.getStartOn().minute())
        .append(' ')
        .append(task.getStartOn().hourOfDay());
        
        // No specific value for day-of-month
        buf.append(' ').append('?');
        
        // All values for month
        buf.append(' ').append('*');
        
        // Compute day-of-week
        buf.append(' ');
        if (recurrence.getWeekDaysOnly() != null && recurrence.getWeekDaysOnly())
            buf.append("MON-FRI");
        else
            buf.append('*');
        
        return createCronTrigger(buf.toString());
    }
    
    /** The following logic is used to build the cron expression.
     * seconds: Set to startOnSecond.
     * minutes: Set to startOnMinute.
     * hours: Set to startOnHour.
     * day-of-month: Set to No specific value.
     * month: All values
     * day-of-week: If frequency equals LAST, set to {day}#L. Else set to {day}#{frequency}.
     */
    private static AbstractTrigger<?> createTrigger(ScheduledTask task, Recurrence.Weekly recurrence) throws FrameworkException, ApplicationExceptions {
        StringBuilder buf = new StringBuilder();
        
        // startOn values for seconds, minutes and hours
        buf.append(task.getStartOn().second())
        .append(' ')
        .append(task.getStartOn().minute())
        .append(' ')
        .append(task.getStartOn().hourOfDay());
        
        // No specific value for day-of-month
        buf.append(' ').append('?');
        
        // All values for month
        buf.append(' ').append('*');
        
        // Compute day-of-week
        buf.append(' ').append(recurrence.getDay().ordinal() + 1);
        if (recurrence.getFrequency() == Recurrence.WeekFrequency.LAST)
            buf.append('L');
        else if (recurrence.getFrequency() != Recurrence.WeekFrequency.EVERY)
            buf.append('#').append(recurrence.getFrequency().ordinal() + 1);
        return createCronTrigger(buf.toString());
    }
    
    /** The following logic is used to build the cron expression.
     * seconds: Set to startOnSecond.
     * minutes: Set to startOnMinute.
     * hours: Set to startOnHour.
     * day-of-month: Set to {day} if passed, else set to No specific value.
     * month: Set to a comma-separated list of months.
     * day-of-week: Set to No specific value if {day} is passed. Else If weekFrequency equals LAST, set to {weekDay}#L. Else set to {weekDay}#{weekFrequency}.
     */
    private static AbstractTrigger<?> createTrigger(ScheduledTask task, Recurrence.Monthly recurrence) throws FrameworkException, ApplicationExceptions {
        StringBuilder buf = new StringBuilder();
        
        // startOn values for seconds, minutes and hours
        buf.append(task.getStartOn().second())
        .append(' ')
        .append(task.getStartOn().minute())
        .append(' ')
        .append(task.getStartOn().hourOfDay());
        
        // Compute day-of-month
        buf.append(' ');
        if (recurrence.getDay() != null && recurrence.getDay() > 0)
            buf.append(recurrence.getDay());
        else
            buf.append('?');
        
        // Compute month
        buf.append(' ');
        for (int i = 0; i < recurrence.getMonths().length; i++) {
            if (i > 0)
                buf.append(',');
            buf.append(recurrence.getMonths()[i].ordinal() + 1);
        }
        
        // Compute day-of-week
        buf.append(' ');
        if (recurrence.getDay() != null && recurrence.getDay() > 0)
            buf.append('?');
        else {
            buf.append(recurrence.getWeekDay().ordinal() + 1);
            if (recurrence.getWeekFrequency() == Recurrence.WeekFrequency.LAST)
                buf.append('L');
            else if (recurrence.getWeekFrequency() != Recurrence.WeekFrequency.EVERY)
                buf.append('#').append(recurrence.getWeekFrequency().ordinal() + 1);
        }
        
        return createCronTrigger(buf.toString());
    }
    
    /** The following logic is used to build the cron expression.
     * seconds: Set to startOnSecond.
     * minutes: Set to startOnMinute.
     * hours: Set to startOnHour.
     * day-of-month: Set to 1.
     * month: Set to {on}.
     * day-of-week: Set to No specific value.
     * year: Incremental by given frequency, starting at startOnYear.
     */
    private static AbstractTrigger<?> createTrigger(ScheduledTask task, Recurrence.Yearly recurrence) throws FrameworkException, ApplicationExceptions {
        StringBuilder buf = new StringBuilder();
        
        // startOn values for seconds, minutes and hours
        buf.append(task.getStartOn().second())
        .append(' ')
        .append(task.getStartOn().minute())
        .append(' ')
        .append(task.getStartOn().hourOfDay());
        
        // Set day-of-month to 1
        buf.append(' ').append('1');
        
        // Compute month
        buf.append(' ').append(recurrence.getOn().ordinal() + 1);
        
        // No specific value for day-of-week
        buf.append(' ').append('?');
        
        // Compute year
        buf.append(' ').append(task.getStartOn().year()).append('/').append(recurrence.getFrequency());
        
        return createCronTrigger(buf.toString());
    }
    
    /** Uses the cron expression of the Custom Recurrence.
     */
    private static AbstractTrigger<?> createTrigger(ScheduledTask task, Recurrence.Custom recurrence) throws FrameworkException, ApplicationExceptions {
        return createCronTrigger(recurrence.getCustomPattern());
    }
    
    /** Creates a CronTrigger with the given expression.
     */
    private static CronTriggerImpl createCronTrigger(String cronExpression) throws FrameworkException, ApplicationExceptions {
        try {
            final CronTriggerImpl trigger = new CronTriggerImpl();
            trigger.setCronExpression(cronExpression);
            return trigger;
        } catch (ParseException e) {
            log.error("Error parsing cron expression " + cronExpression, e);
            throw new ApplicationExceptions(new JaffaSchedulerApplicationException(JaffaSchedulerApplicationException.INVALID_TRIGGER, new Object[] {cronExpression}));
        }
    }
    
}