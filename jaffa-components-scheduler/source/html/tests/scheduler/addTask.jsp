<%@page import="org.jaffa.datatypes.DateTime"%>
<%@page import="java.util.Date"%>
<%@page import="org.jaffa.modules.scheduler.services.*"%>
<%@page import="org.jaffa.datatypes.*"%>
<%@page import="org.jaffa.security.SecurityManager"%>
<%@page import="org.jaffa.components.voucher.VoucherGeneratorFactory"%>

<%
String scheduledTaskId = request.getParameter("scheduledTaskId") != null ? request.getParameter("scheduledTaskId") : VoucherGeneratorFactory.instance().generate();
String taskType = request.getParameter("taskType") != null ? request.getParameter("taskType") : "TEST";
String description = request.getParameter("description") != null ? request.getParameter("description") : "Blah, Blah, Blah, Blah, Blah, ...";
String runAs = request.getParameter("runAs") != null ? request.getParameter("runAs") : SecurityManager.getPrincipal().getName();
String startOn = request.getParameter("startOn") != null ? request.getParameter("startOn") : DateTime.addMinute(new DateTime(),5).toString();
String endOn = request.getParameter("endOn") != null ? request.getParameter("endOn") : "";
String recurrence = request.getParameter("recurrence") != null ? request.getParameter("recurrence") : "1";
String oftenHours = request.getParameter("oftenHours") != null ? request.getParameter("oftenHours") : "";
String oftenMinutes = request.getParameter("oftenMinutes") != null ? request.getParameter("oftenMinutes") : "";
String oftenSeconds = request.getParameter("oftenSeconds") != null ? request.getParameter("oftenSeconds") : "";
String dailyWeekDaysOnly = "true".equals(request.getParameter("dailyWeekDaysOnly")) ? "true" : "false";
String weeklyFrequency = request.getParameter("weeklyFrequency") != null ? request.getParameter("weeklyFrequency") : "";
String weeklyDay = request.getParameter("weeklyDay") != null ? request.getParameter("weeklyDay") : "";
String monthlyDay = request.getParameter("monthlyDay") != null ? request.getParameter("monthlyDay") : "";
String monthlyWeekFrequency = request.getParameter("monthlyWeekFrequency") != null ? request.getParameter("monthlyWeekFrequency") : "";
String monthlyWeekDay = request.getParameter("monthlyWeekDay") != null ? request.getParameter("monthlyWeekDay") : "";
String yearlyFrequency = request.getParameter("yearlyFrequency") != null ? request.getParameter("yearlyFrequency") : "";
String yearlyOn = request.getParameter("yearlyOn") != null ? request.getParameter("yearlyOn") : "";
String customPattern = request.getParameter("customPattern") != null ? request.getParameter("customPattern") : "";
String misfireRecovery = request.getParameter("misfireRecovery") != null ? request.getParameter("misfireRecovery") : "2";
String executionAfter = request.getParameter("executionAfter") != null ? request.getParameter("executionAfter") : new DateTime().toString();
String executionCount = request.getParameter("executionCount") != null ? request.getParameter("executionCount") : "10";
String event = request.getParameter("event");
%>

<html>
  <head>
    <title>Add Scheduled Task</title>
    <link href='/jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
  </head>
  
  <body>
    <form action="addTask.jsp" method="post">
      <h2>Task Details</h2>
      <table>
        <tr>
          <td>Task Id: </td>
          <td><input type="text" name="scheduledTaskId" value="<%= scheduledTaskId %>"></td>
        </tr>
        <tr>
          <td>Task Type: </td>
          <td><input type="text" name="taskType" value="<%= taskType %>"></td>
        </tr>
        <tr>
          <td>Description: </td>
          <td><input type="text" name="description" value="<%= description %>"></td>
        </tr>
        <tr>
          <td>Run As: </td>
          <td><input type="text" name="runAs" value="<%= runAs %>"></td>
        </tr>
      </table>
      
      <h2>Trigger Details</h2>
      <table>
        <tr>
          <td>Start On: </td>
          <td><input type="text" name="startOn" value="<%= startOn %>"></td>
        </tr>
        <tr>
          <td>End On: </td>
          <td><input type="text" name="endOn" value="<%= endOn %>"></td>
        </tr>
        <tr>
          <td><input name="recurrence" type="radio" value="1" <%= recurrence.equals("1") ? "checked" : ""%>> Once only</td>
          <td></td>
        </tr>
        <tr>
          <td><input name="recurrence" type="radio" value="2" <%= recurrence.equals("2") ? "checked" : ""%>> Often</td>
          <td>
            <table>
              <tr>
                <td>Hours: <input type="text" name="oftenHours" value="<%= oftenHours %>"></td>
                <td>Minutes: <input type="text" name="oftenMinutes" value="<%= oftenMinutes %>"></td>
                <td>Seconds: <input type="text" name="oftenSeconds" value="<%= oftenSeconds %>"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td><input name="recurrence" type="radio" value="3" <%= recurrence.equals("3") ? "checked" : ""%>> Daily</td>
          <td>
            <table>
              <tr>
                <td>WeekDays only: <input type="radio" name="dailyWeekDaysOnly" value="true" <%= !dailyWeekDaysOnly.equals("false") ? "checked" : ""%>></td>
                <td>Every Day: <input type="radio" name="dailyWeekDaysOnly" value="false" <%= dailyWeekDaysOnly.equals("false") ? "checked" : ""%>></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td><input name="recurrence" type="radio" value="4" <%= recurrence.equals("4") ? "checked" : ""%>> Weekly</td>
          <td>
            <table>
              <tr>
                <td><input type="text" name="weeklyFrequency" value="<%= weeklyFrequency %>"></td>
                <td><input type="text" name="weeklyDay" value="<%= weeklyDay %>"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td><input name="recurrence" type="radio" value="5" <%= recurrence.equals("5") ? "checked" : ""%>> Montly</td>
          <td>
            <table>
              <tr>
                <td>Day <input type="text" name="monthlyDay" value="<%= monthlyDay %>"></td>
                <td>WeekFrequency <input type="text" name="monthlyWeekFrequency" value="<%= monthlyWeekFrequency %>"></td>
                <td>WeekDay <input type="text" name="monthlyWeekDay" value="<%= monthlyWeekDay %>"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td><input name="recurrence" type="radio" value="6" <%= recurrence.equals("6") ? "checked" : ""%>> Yearly</td>
          <td>
            <table>
              <tr>
                <td>Every <input type="text" name="yearlyFrequency" value="<%= yearlyFrequency %>"> year(s) </td>
                <td>on the first of <input type="text" name="yearlyOn" value="<%= yearlyOn %>"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td><input name="recurrence" type="radio" value="7" <%= recurrence.equals("7") ? "checked" : ""%>> Custom Pattern</td>
          <td><input type="text" name="customPattern" value="<%= customPattern %>"></td>
        </tr>
      </table>
      
      <h2>Recovery Details</h2>
      <table>
        <tr>
          <td colspan='2'><input name="misfireRecovery" type="radio" value="1" <%= misfireRecovery.equals("1") ? "checked" : ""%>> Start as Scheduled (Will not execute task if it was missed)</td>
        </tr>
        <tr>
          <td colspan='2'><input name="misfireRecovery" type="radio" value="2" <%= misfireRecovery.equals("2") ? "checked" : ""%>> Run Last Missed</td>
        </tr>
      </table>
      
      <h2>Test Execution Dates</h2>
      <table>
        <tr>
          <td>Execution After: </td>
          <td><input type="text" name="executionAfter" value="<%= executionAfter %>"></td>
        </tr>
        <tr>
          <td>Execution Count: </td>
          <td><input type="text" name="executionCount" value="<%= executionCount %>"></td>
        </tr>
        <tr>
          <td><input type="submit" name="event" value="ListExecutionDates"></td>
          <td><input type="submit" name="event" value="CreateTask"></td>
        </tr>
      </table>
      
    </form>
    
    <hr>
    <pre>
      <xml>
        <%
        try {
          if (event != null) {
            ScheduledTask task = new ScheduledTask();
            task.setScheduledTaskId(scheduledTaskId);
            task.setTaskType(taskType);
            task.setDescription(description);
            task.setRunAs(runAs);
            task.setStartOn(org.jaffa.datatypes.Parser.parseDateTime(startOn));
            task.setEndOn(org.jaffa.datatypes.Parser.parseDateTime(endOn));
            if (recurrence.equals("2")) {
              task.setRecurrence(new Recurrence.Often(oftenHours.trim().length() > 0 ? new Integer(oftenHours) : null, oftenMinutes.trim().length() > 0 ? new Integer(oftenMinutes) : null, oftenSeconds.trim().length() > 0 ? new Integer(oftenSeconds) : null));
            } else if (recurrence.equals("3")) {
              task.setRecurrence(new Recurrence.Daily(Parser.parseBoolean(dailyWeekDaysOnly)));
            } else if (recurrence.equals("4")) {
              task.setRecurrence(new Recurrence.Weekly(Recurrence.WeekFrequency.valueOf(weeklyFrequency), Recurrence.WeekDay.valueOf(weeklyDay)));
            } else if (recurrence.equals("5")) {
              task.setRecurrence(new Recurrence.Monthly(monthlyDay.trim().length() > 0 ? new Integer(monthlyDay) : null, Recurrence.WeekFrequency.valueOf(monthlyWeekFrequency), Recurrence.WeekDay.valueOf(monthlyWeekDay), new Recurrence.Month[] {Recurrence.Month.JANUARY, Recurrence.Month.FEBRUARY}));
            } else if (recurrence.equals("6")) {
              task.setRecurrence(new Recurrence.Yearly(yearlyFrequency.trim().length() > 0 ? new Integer(yearlyFrequency) : null, Recurrence.Month.valueOf(yearlyOn)));
            } else if (recurrence.equals("7")) {
              task.setRecurrence(new Recurrence.Custom(customPattern));
            }
            if (misfireRecovery.equals("1"))
              task.setMisfireRecovery(ScheduledTask.MisfireRecovery.START_AS_SCHEDULED);
            else if (misfireRecovery.equals("2"))
              task.setMisfireRecovery(ScheduledTask.MisfireRecovery.RUN_LAST_MISSED);
            
            if (event.equals("ListExecutionDates")) {
              DateTime[] dates = SchedulerHelperFactory.instance().getExecutionDates(task, org.jaffa.datatypes.Parser.parseDateTime(executionAfter), new Integer(executionCount));
              if (dates != null) {
                  out.println("\nExecutions Dates are:");
                  for (int i = 0; i < dates.length; i++)
                      out.println(dates[i]);
              }
            } else if (event.equals("CreateTask")) {
              SchedulerHelper sh = SchedulerHelperFactory.instance();
              sh.addTask(task);
              out.println("\nCreated New Task");
            }
          }
        } catch (Exception e) {
          e.printStackTrace(new java.io.PrintWriter(out));
        }
        %>
      </xml>
    </pre>
  </body>
</html>
