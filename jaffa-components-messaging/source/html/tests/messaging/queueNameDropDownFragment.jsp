<%@page import="org.jaffa.modules.messaging.services.ConfigurationService"%>

<tr>
    <td>QueueName: </td>
    <td>
        <select name="queueName">
            <%
            String queueName = request.getParameter("queueName");
            out.println("<option value=' '" + (queueName == null || queueName.equals(" ") ? " selected" : "") + '>' + "All Queues" + "</option>");
            String[] queueNames = ConfigurationService.getInstance().getQueueNames();
            if (queueNames != null) {
                for (int i = 0; i < queueNames.length; i++) {
                    String option = queueNames[i];
                    out.println("<option value='" + option + '\'' + (option.equals(queueName) ? " selected" : "") + '>' + option + "</option>");
                }
            }
            %>
        </select>
    </td>
</tr>
