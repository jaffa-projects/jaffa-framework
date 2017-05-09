<%@page import="org.jaffa.modules.messaging.services.JmsBrowser"%>
<%@page import="org.jaffa.modules.messaging.services.JmsClientHelper"%>
<%@page import="org.jaffa.modules.messaging.services.ConfigurationService"%>
<%@page import="javax.jms.Message"%>

<html>
    <head>
        <title>Change Message Priority</title>
        <link href='/jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
    </head>
    
    <body>
        <h1>Change Message Priority</h1>
        <br>
        
        <form action="changeMessagePriority.jsp" method="post">
            <table>
                <jsp:include page="queueNameDropDownFragment.jsp"/>
                <jsp:include page="filterEditBoxFragment.jsp"/>
                <tr>
                    <td>New Priority: </td>
                    <td>
                        <select name="newPriority">
                            <%
                            String newPriority = request.getParameter("newPriority");
                            for (int i = 0; i < 10; i++) {
                                boolean selected = Integer.toString(i).equals(newPriority);
                                out.println("<option value='" + i + "'" + (selected ? " selected" : "") + ">" + i + "</option>");
                            }
                            %>
                        </select>
                    </td>
                </tr>
                <tr><td colspan="2"><input type="submit" value="Change Priority"></td></tr>
            </table>
        </form>
        <br>
        
        <%
        String queueName = request.getParameter("queueName");
        String filter = request.getParameter("filter");
        if (filter != null && filter.length() == 0)
            filter = null;
        if (queueName != null) {
            if (newPriority == null) {
                out.println("ERROR: Enter the new priority");
            } else {
                String[] targetQueueNames = null;
                if (queueName.equals(" "))
                    targetQueueNames = ConfigurationService.getInstance().getQueueNames();
                else
                    targetQueueNames = new String[] {queueName};
                for (int i = 0; i < targetQueueNames.length; i++) {
                    String targetQueueName = targetQueueNames[i];
                    out.println("<h2>" + targetQueueName + "</h2>");
                    out.println("<ul>");
                    Message[] messages = JmsBrowser.getPendingMessages(targetQueueName, filter);
                    if (messages != null) {
                        for (int j = 0; j < messages.length; j++) {
                            Message message = messages[j];
                            out.println("<li>Changing priority of " + message.getJMSMessageID() + " to " + newPriority);
                            JmsBrowser.changeMessagePriority(targetQueueName, message.getJMSMessageID(), Integer.parseInt(newPriority));
                        }
                    }
                    out.println("</ul>");
                    out.println("<br>");
                }
            }
        }
        %>
    </body>
</html>