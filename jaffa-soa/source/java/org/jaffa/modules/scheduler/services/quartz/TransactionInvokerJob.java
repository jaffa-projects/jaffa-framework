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

import org.jaffa.modules.scheduler.services.ScheduledTask;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;
import org.jaffa.modules.user.services.UserContextWrapper;
import org.jaffa.modules.user.services.UserContextWrapperFactory;
import org.jaffa.persistence.util.UOWHelper;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.MessageHelper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * The GOLDespTransactionInvokerJob will be called by the Quartz scheduler
 * It will then determine use the JobDataMap to retrieve the job information
 * and send an appropriate JMS message.
 */
public class TransactionInvokerJob implements Job {

  /**
   * This constant contains the id of the context variable that will be inserted in the threadcontext,
   * so the messaging architecture can add this as a field to all transactions it submits,
   * so they can get logged back to this scheduled process
   */
  public static final String CONTEXT_SCHEDULED_TASK_ID = "Jaffa.Scheduler.JaffaTransactionInvokerJob.ScheduledTaskId";

  private static final Logger log = Logger.getLogger(TransactionInvokerJob.class);

  /**
   * Called by the <code>{@link org.quartz.Scheduler}</code> when a
   * <code>{@link org.quartz.Trigger}</code> fires that is associated with
   * the <code>Job</code>.
   *
   * @param context The Quartz JobExecutionContext structure used to retrieve job specific information
   * @throws JobExecutionException if there is an exception while executing the job.
   */
  public void execute(JobExecutionContext context) throws JobExecutionException {
    String scheduledTaskId = null;
    String userId = null;
    Object businessObject = null;
    UserContextWrapper ucw = null;
    try {
      List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
      if (log.isDebugEnabled())
        log.debug("Job Size:- " + jobs.size());
      if (log.isDebugEnabled())
        log.debug("Starting Checking Scheduled Tasks");
      for (JobExecutionContext job : jobs) {
        if (log.isDebugEnabled()) {
          log.debug("Job Trigger: " + job.getTrigger());
          log.debug("Context Trigger: " + context.getTrigger());
        }
        if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
          if (log.isDebugEnabled())
            log.debug("There's another instance running, so leaving" + this);
          return;
        }
      }

      // Obtain the Task from the Job details
      ScheduledTask task = QuartzSchedulerHelper.jobDataMapToTask(context.getJobDetail().getJobDataMap());
      if (log.isDebugEnabled())
        log.debug("Executing " + task);

      scheduledTaskId = task.getScheduledTaskId();
      userId = task.getRunAs();
      businessObject = task.getBusinessObject();

      // Switch the thread to use the context the "RunAs" is set to, so this propergates when creating the JaffaTransaction/JMS records to process
      if (log.isDebugEnabled())
        log.debug("Set up use context. RunAs = " + userId);
      ucw = UserContextWrapperFactory.instance(userId);

      // Sets Log4J's MDC to enable BusinessEventLogging
      MDC.put(BusinessEventLogMeta.SCHEDULED_TASK_ID, scheduledTaskId);
      MDC.put(BusinessEventLogMeta.LOGGED_BY, userId);

      //Set up some Thread Level variables for the Jaffa Transaction. This will help distinguish between a Schedule invoke transaction and any others
      ContextManagerFactory.instance().setProperty(CONTEXT_SCHEDULED_TASK_ID, scheduledTaskId);

      // Send a Jaffa Transaction message
      if (log.isInfoEnabled())
        log.info(MessageHelper.findMessage("label.Jaffa.Scheduler.JaffaTransactionInvokerJob.start", new Object[]{businessObject, userId, scheduledTaskId}));

      UOWHelper.addMessage(businessObject);

      if (log.isInfoEnabled())
        log.info(MessageHelper.findMessage("label.Jaffa.Scheduler.JaffaTransactionInvokerJob.success", new Object[]{businessObject, userId, scheduledTaskId}));
    } catch (Exception e) {
      log.error(MessageHelper.findMessage("error.Jaffa.Scheduler.JaffaTransactionInvokerJob.error", new Object[]{businessObject, userId, scheduledTaskId}), e);
      throw new JobExecutionException(e);
    } finally {
      // Unset the Logging context
      MDC.remove(BusinessEventLogMeta.SCHEDULED_TASK_ID);
      MDC.remove(BusinessEventLogMeta.LOGGED_BY);

      // Clear context for this user
      if (ucw != null) {
        if (log.isDebugEnabled())
          log.debug("Unset user context");
        ucw.unsetContext();
      }
    }
  }
}