<%@page import="org.jaffa.modules.scheduler.services.*"%>

<html>
<head>
    <title>Browse Scheduled Tasks</title>
    <link href='/jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
</head>

<body>
  <h1>Browse Scheduled Tasks</h1>
  <br>
        
        <%
        SchedulerHelper sh = null;
        try {
            sh = SchedulerHelperFactory.instance();
        } catch(Exception e) {%>
  <h1>Error - Can't lookup the Scheduler</h1>
  <pre><xml>
  <%e.printStackTrace(new java.io.PrintWriter(out));%>
  </xml></pre>
        <%}
        if(sh!=null) {
            ScheduledTask[] tasks = sh.getAllTasks();
            if(tasks==null) {
        %>
  No Task List Returned!
        <%
            } else {
        %>
  Found <%=tasks.length%> task(s)
        <%
                for (int i = 0; i < tasks.length; i++) {
        %>
  <hr>
  <pre><xml>
  <%=org.jaffa.beans.moulding.mapping.BeanMoulder.printBean(tasks[i])%>
  </xml></pre>
        <%
                }
            }
        }
        %>
</body>
</html>