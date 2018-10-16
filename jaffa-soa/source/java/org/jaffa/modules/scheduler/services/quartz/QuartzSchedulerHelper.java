/*
 *
 *  **************************************************TAPESTRY SOLUTIONS PROPRIETARY VER 2.0
 *  *
 *  *  Copyright 2016 Tapestry Solutions, Inc.
 *  *  THIS PROGRAM IS PROPRIETARY TO TAPESTRY SOLUTIONS, INC.
 *  *  REPRODUCTION, DISCLOSURE, OR USE, IN WHOLE OR IN PART,
 *  *  UNDERTAKEN EXCEPT WITH PRIOR WRITTEN AUTHORIZATION OF
 *  *  TAPESTRY SOLUTIONS IS PROHIBITED.
 *  *
 *  **************************************************TAPESTRY SOLUTIONS PROPRIETARY VER 2.0
 *
 */

package org.jaffa.modules.scheduler.services.quartz;

import org.jaffa.modules.scheduler.services.*;
import org.jaffa.modules.scheduler.services.configdomain.Task;
import org.apache.log4j.Logger;
import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorFactory;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.ExceptionHelper;
import org.jaffa.util.JAXBHelper;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import javax.xml.bind.JAXBException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * A SchedulerHelper implementation based on the Quartz Scheduler.
 */
public class QuartzSchedulerHelper implements SchedulerHelper {

  private static final Logger LOGGER = Logger
          .getLogger(QuartzSchedulerHelper.class);

  private static final String INITIALIZE_ERROR = "Failed to initialize the StdSchedulerFactory: ";
  private static final String SHUTDOWN_ERROR = "Failed to shutdown the StdSchedulerFactory: ";

  /**
   * The GroupName used when submitting Jobs to the Quartz service.
   */
  protected final static String JOB_GROUP = "JAFFA";

  /**
   * An enumeration of elements added to the JobDataMap of a Job.
   */
  protected enum JobData {
    SCHEDULED_TASK_OBJECT, SCHEDULED_TASK_OBJECT_CLASS_NAME, BUSINESS_OBJECT, BUSINESS_OBJECT_CLASS_NAME, RECURRENCE, RECURRENCE_CLASS_NAME
  }

  /**
   * The Quartz Scheduler.
   */
  private Scheduler scheduler;

  /**
   * Creates a new instance of QuartzSchedulerHelper. Locates the Quartz service
   * from JNDI.
   *
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  public QuartzSchedulerHelper() throws FrameworkException,
          ApplicationExceptions {
  }

  /**
   * <p>
   * Initialize the <code>{@link org.quartz.SchedulerFactory}</code> with the
   * contents of the given <code>Properties</code> object.
   * </p>
   */
  public void instantiateSchedulerFactory() {
    final StdSchedulerFactory factory = new StdSchedulerFactory();
    try {
      scheduler = factory.getScheduler();
    } catch (SchedulerException e) {
      LOGGER.error(INITIALIZE_ERROR, e);
    }
  }

  /**
   * Starts the Scheduler.
   */
  public void startScheduler() {
    try {
      if (scheduler == null || scheduler.isShutdown()) {
        instantiateSchedulerFactory();
        scheduler.startDelayed(600 /*seconds to delay the start = 10 min*/);
        LOGGER.info("GOLDesp Scheduler has been started.");
      } else if (scheduler.isInStandbyMode()) {
        scheduler.start();
        LOGGER.info("GOLDesp Scheduler has been started.");
      }
    } catch (SchedulerException e) {
      LOGGER.error("Error starting the scheduler", e);
    }
  }

  /**
   * Pauses the Scheduler.
   */
  public void pauseScheduler() {
    try {
      if (!scheduler.isShutdown()) {
        scheduler.standby();
        LOGGER.info("GOLDesp Scheduler has been paused.");
      }
    } catch (SchedulerException e) {
      LOGGER.error("Error calling pause for the scheduler", e);
    }
  }

  /**
   * Halts the <code>Scheduler</code>'s firing of <code>{@link Trigger}s</code>,
   * and cleans up all resources associated with the Scheduler. Equivalent to
   * <code>shutdown(false)</code>.
   */
  public void shutdownScheduler() {
    shutdownScheduler(false);
  }

  /**
   * Halts the <code>Scheduler</code>'s firing of <code>{@link Trigger}s</code>,
   * and cleans up all resources associated with the Scheduler. Equivalent to
   * <code>shutdown(false)</code>.
   *
   * @param waitForJobsToComplete if <code>true</code> the scheduler will not allow this method to
   *                              return until all currently executing jobs have completed.
   */
  public void shutdownScheduler(final boolean waitForJobsToComplete) {
    try {
      if (!scheduler.isShutdown()) {
        scheduler.shutdown(waitForJobsToComplete);
        LOGGER.info("GOLDesp Scheduler has been shutdown.");
      }
    } catch (SchedulerException e) {
      LOGGER.error(SHUTDOWN_ERROR, e);
    }
  }

  /**
   * Returns the list of Tasks from the Scheduler.
   *
   * @return the list of Tasks from the Scheduler.
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  public ScheduledTask[] getAllTasks() throws FrameworkException,
          ApplicationExceptions {
    // Ensure that the Scheduler is available
    checkScheduler();
    List<ScheduledTask> taskList = getTaskList();
    return taskList != null && taskList.size() > 0 ? taskList
            .toArray(new ScheduledTask[taskList.size()]) : null;
  }

  /**
   * Returns the list of Tasks from the Scheduler created by a given user id with a given task type.
   *
   * @param userId   the identifier for a user.
   * @param taskType the type of tasks to be returned.
   * @return the list of Tasks from the Scheduler.
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  public ScheduledTask[] getTasks(String userId, String taskType) throws FrameworkException, ApplicationExceptions {
    // Ensure that the Scheduler is available
    checkScheduler();
    List<ScheduledTask> returnTasks = null;
    List<ScheduledTask> taskList = getTaskList();
    if (taskList != null && taskList.size() > 0) {
      returnTasks = new LinkedList<ScheduledTask>();
      for (ScheduledTask scheduledTask : taskList) {
        if ((userId == null || scheduledTask.getRunAs().equals(userId)) && (taskType == null || scheduledTask.getTaskType().equals(taskType)))
          returnTasks.add(scheduledTask);
      }
    }
    return returnTasks != null && returnTasks.size() > 0 ? returnTasks.toArray(new ScheduledTask[returnTasks.size()]) : null;
  }

  /**
   * Returns the list of Tasks from the Scheduler for the given userId.
   *
   * @param userId the identifier for a user.
   * @return the list of Tasks from the Scheduler for the given userId.
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  public ScheduledTask[] getUserTasks(String userId) throws FrameworkException,
          ApplicationExceptions {
    // Ensure that the Scheduler is available
    checkScheduler();
    List<ScheduledTask> userTasks = null;
    List<ScheduledTask> taskList = getTaskList();
    if (taskList != null && taskList.size() > 0) {
      userTasks = new LinkedList<ScheduledTask>();
      for (ScheduledTask scheduledTask : taskList) {
        if (scheduledTask.getRunAs().equals(userId))
          userTasks.add(scheduledTask);
      }
    }
    return userTasks != null && userTasks.size() > 0 ? userTasks
            .toArray(new ScheduledTask[userTasks.size()]) : null;
  }

  /**
   * Returns the Task from the Scheduler based on the given taskId.
   *
   * @param taskId the identifier for a Task.
   * @return the Task from the Scheduler based on the given taskId.
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  public ScheduledTask getTask(final String taskId) throws FrameworkException,
          ApplicationExceptions {
    // Ensure that the Scheduler is available
    checkScheduler();
    try {
      final JobKey jobKey = getJobKey(taskId);
      final JobDetail jobDetail = scheduler.getJobDetail(jobKey);
      final List<? extends Trigger> triggers = scheduler
              .getTriggersOfJob(jobKey);
      return jobAndTriggerToTask(jobDetail, triggers);
    } catch (SchedulerException e) {
      LOGGER.error("Error getting task from scheduler " + taskId, e);
      throw new ApplicationExceptions(new JaffaSchedulerApplicationException(
              JaffaSchedulerApplicationException.TASK_NOT_FOUND,
              new Object[]{taskId}));
    }
  }

  public JobKey getJobKey(final String taskId) {
    JobKey jobKey = null;
    try {
      for (final JobKey key : scheduler.getJobKeys(GroupMatcher
              .jobGroupEquals(JOB_GROUP))) {
        if (key.getName().equalsIgnoreCase(taskId)) {
          jobKey = key;
          break;
        }
      }
    } catch (SchedulerException e) {
      LOGGER.error(e);
    }
    return jobKey;
  }

  /**
   * Updates the Task in the Scheduler.
   *
   * @param task the Task.
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  public void updateTask(ScheduledTask task) throws FrameworkException,
          ApplicationExceptions {
    // Ensure that the Scheduler is available
    checkScheduler();

    // Perform as many validations as possible, before performing the delete
    // Most of the validations happen during Trigger creation.
    if (LOGGER.isDebugEnabled())
      LOGGER.debug("Validating " + task + " before updating it");
    prevalidate(task);

    // Stamp LastChangedBy and LastChangedOn
    if (SecurityManager.getPrincipal() != null)
      task.setLastChangedBy(SecurityManager.getPrincipal().getName());
    task.setLastChangedOn(new DateTime());

    TriggerHelper.taskToTrigger(task);

    // Get the existing Task, in case we need to revert after the delete
    if (LOGGER.isDebugEnabled())
      LOGGER.debug("Get existing values for " + task.getScheduledTaskId()
              + " before updating it");
    ScheduledTask existingTask = getTask(task.getScheduledTaskId());

    if (LOGGER.isDebugEnabled())
      LOGGER.debug("Updating " + task.getScheduledTaskId()
              + " by deleting and then adding it");
    deleteTask(task.getScheduledTaskId());

    try {
      addTask(task);
    } catch (Exception e) {
      // revert to original values
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Updating " + task.getScheduledTaskId()
                + " has failed. Will now add the original values for the Task");
      addTask(existingTask);
      ExceptionHelper.throwAFR(e);
    }
  }

  /**
   * Adds the Task to the Scheduler.
   *
   * @param task the Task.
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  public void addTask(ScheduledTask task) throws FrameworkException,
          ApplicationExceptions {
    // Ensure that the Scheduler is available
    checkScheduler();

    if (LOGGER.isDebugEnabled())
      LOGGER.debug("Adding " + task);

    // Validate the Task
    prevalidate(task);

    // Voucher the technical key
    if (task.getScheduledTaskId() == null
            || task.getScheduledTaskId().length() == 0) {
      IVoucherGenerator vg = VoucherGeneratorFactory.instance();
      vg.setDomainClassName(ScheduledTask.class.getName());
      vg.setFieldName(BusinessEventLogMeta.SCHEDULED_TASK_ID);
      vg.setLabelToken(BusinessEventLogMeta.META_SCHEDULED_TASK_ID
              .getLabelToken());
      task.setScheduledTaskId(vg.generate());
    }

    // Stamp CreatedBy and CreatedOn
    if (task.getCreatedBy() == null && SecurityManager.getPrincipal() != null)
      task.setCreatedBy(SecurityManager.getPrincipal().getName());
    if (task.getCreatedOn() == null)
      task.setCreatedOn(new DateTime());

    // Create the Job and Trigger
    final JobDetail job = taskToJob(task);
    final Trigger trigger = TriggerHelper.taskToTrigger(task);

    // Schedule the Task
    try {
      final Date ft = scheduler.scheduleJob(job, trigger);
      if (LOGGER.isInfoEnabled())
        LOGGER
                .info(task.getScheduledTaskId()
                        + " has been scheduled to run at: "
                        + ft
                        + (trigger instanceof CronTrigger ? " and repeat based on expression: "
                        + ((CronTrigger) trigger).getCronExpression()
                        : ""));
    } catch (SchedulerException e) {
      LOGGER.error(
              "Error adding task in scheduler " + task.getScheduledTaskId(), e);
      // TODO: if (se.isClientError())
      // throw new ApplicationExceptions(new
      // JaffaSchedulerApplicationException(JaffaSchedulerApplicationException.CLIENT_ERROR,
      // new Object[] {se.getLocalizedMessage()}));
      // else
      throw new JaffaSchedulerFrameworkException(
              JaffaSchedulerFrameworkException.ADD_TASK_ERROR,
              new Object[]{task.getScheduledTaskId()}, e);
    }
  }

  /**
   * Deletes the Task from the Scheduler.
   *
   * @param taskId the identifier for a Task.
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  public void deleteTask(final String taskId) throws FrameworkException,
          ApplicationExceptions {
    // Ensure that the Scheduler is available
    checkScheduler();
    try {
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Deleting " + taskId);
      scheduler.deleteJob(getJobKey(taskId));
    } catch (SchedulerException se) {
      LOGGER.error("Error deleting task from scheduler " + taskId, se);
      throw new JaffaSchedulerFrameworkException(
              JaffaSchedulerFrameworkException.DELETE_TASK_ERROR,
              new Object[]{taskId}, se);
    }
  }

  /**
   * Activates the Task in the Scheduler.
   *
   * @param taskId the identifier for a Task.
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  public void activateTask(final String taskId) throws FrameworkException,
          ApplicationExceptions {
    // Ensure that the Scheduler is available
    checkScheduler();
    try {
      scheduler.resumeJob(getJobKey(taskId));
    } catch (SchedulerException se) {
      LOGGER.error("Error calling resume task in scheduler " + taskId, se);
      throw new JaffaSchedulerFrameworkException(
              JaffaSchedulerFrameworkException.ACTIVATE_TASK_ERROR,
              new Object[]{taskId}, se);
    }
  }

  /**
   * Inactivates the Task in the Scheduler.
   *
   * @param taskId the identifier for a Task.
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  public void inactivateTask(final String taskId) throws FrameworkException,
          ApplicationExceptions {
    // Ensure that the Scheduler is available
    checkScheduler();
    try {
      scheduler.pauseJob(getJobKey(taskId));
    } catch (SchedulerException se) {
      LOGGER.error("Error calling pause task in scheduler " + taskId, se);
      throw new JaffaSchedulerFrameworkException(
              JaffaSchedulerFrameworkException.INACTIVATE_TASK_ERROR,
              new Object[]{taskId}, se);
    }
  }

  /**
   * Returns the status of the Scheduler.
   *
   * @return the status of the Scheduler.
   */
  public SchedulerStatusEnumeration getSchedulerStatus() {
    try {
      if (scheduler.isShutdown())
        return SchedulerStatusEnumeration.STOPPED;
      else if (scheduler.isInStandbyMode())
        return SchedulerStatusEnumeration.PAUSED;
      else
        return SchedulerStatusEnumeration.RUNNING;
    } catch (SchedulerException e) {
      LOGGER
              .error(
                      "Error in determing the Scheduler status. Will return UNKNOWN status",
                      e);
      return SchedulerStatusEnumeration.UNKNOWN;
    }
  }

  /**
   * Returns an array of execution dates for the given task, after the given
   * date.
   *
   * @param task  the Task.
   * @param after the date after which the execution dates are to be computed.
   * @param count the number of execution dates to compute.
   * @return an array of execution dates for the given task, after the given
   * date.
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  public DateTime[] getExecutionDates(ScheduledTask task, DateTime after,
                                      Integer count) throws FrameworkException, ApplicationExceptions {
    if (LOGGER.isDebugEnabled())
      LOGGER.debug("Computing the next valid times for " + task + " after "
              + after + ' ' + count + " times");

    final Collection<DateTime> output = new LinkedList<DateTime>();
    Trigger trigger = TriggerHelper.taskToTrigger(task);
    Date d = after.getUtilDate();
    for (int i = 0; i < count; i++) {
      d = trigger.getFireTimeAfter(d);
      if (d == null)
        break;
      output.add(new DateTime(d));
    }

    if (LOGGER.isDebugEnabled()) {
      StringBuilder buf = new StringBuilder("Valid times are:");
      if (0 < output.size()) {
        for (DateTime dt : output)
          buf.append(' ').append(dt);
      }
      LOGGER.debug(buf.toString());
    }
    return 0 < output.size() ? output.toArray(new DateTime[output.size()]) : null;
  }

  /**
   * Returns all Jobs from the Scheduler.
   *
   * @return all Jobs from the Scheduler.
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  private List<ScheduledTask> getTaskList() throws FrameworkException,
          ApplicationExceptions {
    List<ScheduledTask> tasks = null;
    try {
      final List<String> names = scheduler.getJobGroupNames();
      if (names != null && 0 < names.size()) {
        tasks = new LinkedList<ScheduledTask>();
        for (final String groupName : names) {
          for (final JobKey jobKey : scheduler.getJobKeys(GroupMatcher
                  .jobGroupEquals(groupName))) {
            final JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            final List<? extends Trigger> triggers = scheduler
                    .getTriggersOfJob(jobKey);
            final ScheduledTask task = jobAndTriggerToTask(jobDetail, triggers);
            tasks.add(task);
          }
        }
      }
      return tasks;
    } catch (SchedulerException e) {
      LOGGER.error("Error in retriving Jobs from the Scheduler", e);
      throw new JaffaSchedulerFrameworkException(
              JaffaSchedulerFrameworkException.TASK_RETRIEVE_ERROR, null, e);
    }
  }

  /**
   * Molds the input Job and Trigger into a Task.
   */
  private ScheduledTask jobAndTriggerToTask(final JobDetail jobDetail,
                                            final List<? extends Trigger> triggers) throws FrameworkException {
    // Obtain the Task from the JobDataMap of the Job
    ScheduledTask task = jobDataMapToTask(jobDetail.getJobDataMap());

    // Obtain the Trigger Details
    if (triggers != null && 0 < triggers.size()) {
      final Trigger trigger = triggers.get(0);
      task.setNextDue(trigger.getNextFireTime() != null ? new DateTime(trigger
              .getNextFireTime()) : null);
      task.setLastRunOn(trigger.getPreviousFireTime() != null ? new DateTime(
              trigger.getPreviousFireTime()) : null);
    }

    // Determine the Status
    ScheduledTask.TaskStatusEnumeration status;
    try {
      final TriggerState triggerState = scheduler.getTriggerState(TriggerKey
              .triggerKey(task.getScheduledTaskId(), JOB_GROUP));
      switch (triggerState) {
        case BLOCKED:
          status = ScheduledTask.TaskStatusEnumeration.BLOCKED;
          break;
        case COMPLETE:
          status = ScheduledTask.TaskStatusEnumeration.COMPLETE;
          break;
        case ERROR:
          status = ScheduledTask.TaskStatusEnumeration.ERROR;
          break;
        case NORMAL:
          status = ScheduledTask.TaskStatusEnumeration.NORMAL;
          break;
        case PAUSED:
          status = ScheduledTask.TaskStatusEnumeration.PAUSED;
          break;
        default:
          status = ScheduledTask.TaskStatusEnumeration.UNKNOWN;
          break;
      }
    } catch (SchedulerException e) {
      LOGGER.error(
              "Error in determing the trigger state. Will assume UNKNOWN status for "
                      + task.getScheduledTaskId(), e);
      status = ScheduledTask.TaskStatusEnumeration.UNKNOWN;
    }
    task.setStatus(status);

    return task;
  }

  /**
   * Molds the input Task into a Job.
   */
  private JobDetail taskToJob(final ScheduledTask task)
          throws FrameworkException, ApplicationExceptions {
    // Create a Map containing additional data elements to be added to a Job
    final JobDataMap jobDataMap = taskToJobDataMap(task);
    // This ensures that the Job remains stored after it is orphaned (no
    // Triggers point to it)
    // Create the Job
    return JobBuilder
            .newJob(TransactionInvokerJob.class)
            .withIdentity(task.getScheduledTaskId(), JOB_GROUP)
            .withDescription(task.getDescription()).usingJobData(jobDataMap)
            .storeDurably(true).build();
  }

  /**
   * Checks if the Scheduler has been shutdown, in which case an
   * ApplicationException is thrown..
   *
   * @throws FrameworkException    in case any internal error occurs.
   * @throws ApplicationExceptions Indicates application error(s).
   */
  private void checkScheduler() throws FrameworkException,
          ApplicationExceptions {
    try {
      if (scheduler.isShutdown())
        throw new ApplicationExceptions(new JaffaSchedulerApplicationException(
                JaffaSchedulerApplicationException.SCHEDULER_SHUTDOWN));
    } catch (SchedulerException e) {
      LOGGER.error("Error in checking if the Scheduler has been shutdown", e);
      throw new JaffaSchedulerFrameworkException(
              JaffaSchedulerFrameworkException.TASK_RETRIEVE_ERROR, null, e);
    }
  }

  /**
   * Marshals the input Task into XML and adds it to a JobDataMap.
   *
   * @param task the Task.
   * @return a JobDataMap containing the XML representation of the Task.
   * @throws FrameworkException in case any internal error occurs.
   */
  static JobDataMap taskToJobDataMap(ScheduledTask task)
          throws FrameworkException {
    /*
     * If using native Java serialization, we need to store only the
     * SCHEDULED_TASK_OBJECT in the JobDataMap. However due to the probable
     * versioning issues, it is recommended to avoid native Java serialization.
     * See http://www.opensymphony.com/quartz/wikidocs/TutorialLesson9.html
     * 
     * Hence we'll use JAXB for serializing the ScheduledTask object. In
     * addition to SCHEDULED_TASK_OBJECT, the following elements will be added
     * to the JobDataMap as well SCHEDULED_TASK_OBJECT_CLASS_NAME: Required for
     * unmarshalling the Task, since it is quite possible that a subclass of
     * ScheduledTask may be passed in here. For example TaskMaintenanceDto.
     * BUSINESS_OBJECT: JAXB cannot marshal an Object property within the Task.
     * Hence we need to marshal the BusinessObject separately.
     * BUSINESS_OBJECT_CLASS_NAME: Required for unmarshalling the
     * BusinessObject. RECURRENCE: JAXB cannot marshal an abstract property
     * within the Task. Problems are encountered even if the Recurrence is made
     * non-abstract, since we actually deal with subclasses of Recurrence. Hence
     * we need to marshal the Recurrence separately. RECURRENCE_CLASS_NAME:
     * Required for unmarshalling the Recurrence.
     */
    try {
      JobDataMap jobDataMap = new JobDataMap();
      jobDataMap.put(JobData.SCHEDULED_TASK_OBJECT.toString(),
              JAXBHelper.marshalPayload(task));
      jobDataMap.put(JobData.SCHEDULED_TASK_OBJECT_CLASS_NAME.toString(), task
              .getClass().getName());
      if (task.getBusinessObject() != null) {
        jobDataMap.put(JobData.BUSINESS_OBJECT.toString(),
                JAXBHelper.marshalPayload(task.getBusinessObject()));
        jobDataMap.put(JobData.BUSINESS_OBJECT_CLASS_NAME.toString(), task
                .getBusinessObject().getClass().getName());
      }
      if (task.getRecurrence() != null) {
        jobDataMap.put(JobData.RECURRENCE.toString(),
                JAXBHelper.marshalPayload(task.getRecurrence()));
        jobDataMap.put(JobData.RECURRENCE_CLASS_NAME.toString(), task
                .getRecurrence().getClass().getName());
      }
      return jobDataMap;
    } catch (JAXBException e) {
      LOGGER.error("Error in marshalling the ScheduledTask into XML", e);
      throw new JaffaSchedulerFrameworkException(
              JaffaSchedulerFrameworkException.MARSHALLING_ERROR, null, e);
    }
  }

  /**
   * Unmarshals the XML from the JobDataMap into a ScheduledTask instance.
   *
   * @param jobDataMap the JobDataMap containing the XML representation of a Task.
   * @return the Task.
   * @throws FrameworkException in case any internal error occurs.
   */
  static ScheduledTask jobDataMapToTask(JobDataMap jobDataMap)
          throws FrameworkException {
    try {
      String scheduledTaskXml = jobDataMap
              .getString(JobData.SCHEDULED_TASK_OBJECT.toString());
      String scheduledTaskClassName = jobDataMap
              .getString(JobData.SCHEDULED_TASK_OBJECT_CLASS_NAME.toString());
      String businessObjectXml = jobDataMap.getString(JobData.BUSINESS_OBJECT
              .toString());
      String businessObjectClassName = jobDataMap
              .getString(JobData.BUSINESS_OBJECT_CLASS_NAME.toString());
      String recurrenceXml = jobDataMap
              .getString(JobData.RECURRENCE.toString());
      String recurrenceClassName = jobDataMap
              .getString(JobData.RECURRENCE_CLASS_NAME.toString());

      ScheduledTask task = (ScheduledTask) JAXBHelper.unmarshalPayload(
              scheduledTaskXml, scheduledTaskClassName);
      if (businessObjectXml != null && businessObjectClassName != null) {
        Object businessObject = JAXBHelper.unmarshalPayload(businessObjectXml,
                businessObjectClassName);
        task.setBusinessObject(businessObject);
      }
      if (recurrenceXml != null && recurrenceClassName != null) {
        Recurrence recurrence = (Recurrence) JAXBHelper.unmarshalPayload(
                recurrenceXml, recurrenceClassName);
        task.setRecurrence(recurrence);
      }
      return task;
    } catch (Exception e) {
      LOGGER
              .error(
                      "Error in unmarshalling the XML from JobDataMap into a ScheduledTask instance",
                      e);
      throw new JaffaSchedulerFrameworkException(
              JaffaSchedulerFrameworkException.UNMARSHALLING_ERROR, null, e);
    }
  }

  /**
   * Validates the input Task.
   *
   * @param task the Task.
   * @throws JaffaSchedulerFrameworkException in case any internal error occurs.
   * @throws ApplicationExceptions            Indicates application error(s).
   */
  private void prevalidate(ScheduledTask task)
          throws JaffaSchedulerFrameworkException, ApplicationExceptions {

    if (task.getTaskType() == null)
      throw new ApplicationExceptions(new MandatoryFieldException(
              "[label.Jaffa.Scheduler.ScheduledTask.TaskType]"));

    if (task.getEndOn() != null && task.getStartOn() != null
            && task.getEndOn().isBefore(task.getStartOn()))
      throw new ApplicationExceptions(new JaffaSchedulerApplicationException(
              JaffaSchedulerApplicationException.INVALID_END_DATE));

    Task t = SchedulerConfiguration.getInstance().getTask(task.getTaskType());
    if (t == null)
      throw new ApplicationExceptions(new JaffaSchedulerApplicationException(
              JaffaSchedulerApplicationException.INVALID_TASK_TYPE,
              new Object[]{task.getTaskType()}));

    if (task.getBusinessObject() == null) {
      // Create the BusinessObject, if required
      if (!t.isAutoCreateDataBean()) {
        LOGGER
                .error("The business object should be passed, or the task type '"
                        + task.getTaskType()
                        + "' should be configured with an autoCreateDataBean");
        throw new JaffaSchedulerFrameworkException(
                JaffaSchedulerFrameworkException.MISSING_BUSINESS_OBJECT,
                new Object[]{task.getTaskType()});
      } else {
        try {
          if (LOGGER.isDebugEnabled())
            LOGGER.debug("Autocreating the dataBean " + t.getDataBean());
          task.setBusinessObject(Class.forName(t.getDataBean()).newInstance());
        } catch (Exception e) {
          LOGGER.error(
                  "Error in creating the business object for the task type '"
                          + task.getTaskType() + "' when instantiating "
                          + t.getDataBean(), e);
          throw new JaffaSchedulerFrameworkException(
                  JaffaSchedulerFrameworkException.DATA_BEAN_CREATION_ERROR,
                  new Object[]{task.getTaskType(), t.getDataBean()}, e);
        }
      }
    } else {
      // Validate the BusinessObject
      try {
        Class clazz = Class.forName(t.getDataBean());
        if (!clazz.isInstance(task.getBusinessObject()))
          throw new ApplicationExceptions(
                  new JaffaSchedulerApplicationException(
                          JaffaSchedulerApplicationException.INVALID_BUSINESS_OBJECT,
                          new Object[]{task.getTaskType(),
                                  task.getBusinessObject().getClass().getName(),
                                  clazz.getName()}));
      } catch (ClassNotFoundException e) {
        LOGGER.error("The databean class '" + t.getDataBean()
                + "' was not found in the classpath", e);
        throw new JaffaSchedulerFrameworkException(
                JaffaSchedulerFrameworkException.DATA_BEAN_NOT_FOUND,
                new Object[]{t.getDataBean()}, e);
      }
    }

    // Default some values, if required
    if (task.getStartOn() == null)
      task.setStartOn(new DateTime());
    if (task.getRunAs() == null && SecurityManager.getPrincipal() != null)
      task.setRunAs(SecurityManager.getPrincipal().getName());
  }
}