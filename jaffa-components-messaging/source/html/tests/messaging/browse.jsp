<%@page import="org.jaffa.modules.messaging.services.JmsBrowser"%>
<%@page import="org.jaffa.modules.messaging.services.JmsClientHelper"%>
<%@page import="org.jaffa.modules.messaging.services.ConfigurationService"%>
<%@page import="org.jaffa.modules.messaging.services.configdomain.QueueInfo"%>
<%@page import="javax.jms.Message"%>

<html>
    <head>
        <title>Browse Queues</title>
        <link href='/jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
    </head>
    
    <body>
        <h1>Browse Queues</h1>
        <br>
        
        <form action="browse.jsp" method="post">
            <table>
                <jsp:include page="queueNameDropDownFragment.jsp"/>
                <jsp:include page="filterEditBoxFragment.jsp"/>
                <tr><td colspan="2"><input type="submit" value="Browse"></td></tr>
            </table>
        </form>
        <br>
        
        <%
        String queueName = request.getParameter("queueName");
        String filter = request.getParameter("filter");
        if (filter != null && filter.length() == 0)
            filter = null;
        if (queueName != null) {
            String[] targetQueueNames = null;
            if (queueName.equals(" "))
                targetQueueNames = ConfigurationService.getInstance().getQueueNames();
            else
                targetQueueNames = new String[] {queueName};
            for (int i = 0; i < targetQueueNames.length; i++) {
                String targetQueueName = targetQueueNames[i];
                QueueInfo queueInfo = ConfigurationService.getInstance().getQueueInfo(targetQueueName);
                Message[] messages = JmsBrowser.getPendingMessages(targetQueueName, filter);
                if (queueInfo != null)
                    out.println("<h2>" + targetQueueName + " (errorQueue=" + queueInfo.isErrorQueue() + ", consumerPolicy=" + queueInfo.getConsumerPolicy() + ", messageCount=" + (messages != null ? messages.length : 0) + ")</h2>");
                else
                    out.println("<h2>" + targetQueueName + " (UNKNOWN, messageCount=" + (messages != null ? messages.length : 0) + ")</h2>");
                out.println("<ul>");
                if (messages != null) {
                    for (int j = 0; j < messages.length; j++) {
                        Message message = messages[j];
                        out.println("<li><pre>" + message + "</pre>");
                    }
                }
                out.println("</ul>");
                out.println("<br>");
            }
        }
        %>
    </body>
</html>