<%@page import="org.jaffa.modules.messaging.services.ConfigurationService"%>
<%@page import="javax.jms.Connection"%>
<%@page import="javax.jms.ConnectionFactory"%>
<%@page import="javax.jms.Destination"%>
<%@page import="javax.jms.Message"%>
<%@page import="javax.jms.MessageConsumer"%>
<%@page import="javax.jms.MessageProducer"%>
<%@page import="javax.jms.Session"%>
<%@page import="javax.jms.TextMessage"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>

<html>
    <head>
        <title>Send Messages using JMS</title>
        <link href='/jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
    </head>
    
    <body>
        <h1>Send Messages using JMS</h1>
        <br>
        
        <form action="sendMessageUsingJms.jsp" method="post">
            <table>
                <jsp:include page="queueNameDropDownFragment.jsp"/>
                <tr>
                    <td>Message Prefix: </td>
                    <td><input type="text" name='messagePrefix' value='<%= request.getParameter("messagePrefix") != null ? request.getParameter("messagePrefix") : "hello" %>'></td>
                </tr>
                <tr><td colspan="2"><input type="submit" value="Send"></td></tr>
            </table>
        </form>
        <br>
        
        <%
        String queueName = request.getParameter("queueName");
        String messagePrefix = request.getParameter("messagePrefix");
        if (messagePrefix != null && messagePrefix.length() == 0)
            messagePrefix = null;
        if (queueName != null) {
            String[] targetQueueNames = null;
            if (queueName.equals(" "))
                targetQueueNames = ConfigurationService.getInstance().getQueueNames();
            else
                targetQueueNames = new String[] {queueName};
            
            
            for (int i = 0; i < targetQueueNames.length; i++) {
                String targetQueueName = targetQueueNames[i];
                String messageContents = (messagePrefix != null ? messagePrefix : "") + targetQueueName;
                
                // The following code will send the message using JMS
                InitialContext context = new InitialContext();
                Destination destination = (Destination) context.lookup("queue/" + targetQueueName);
                ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
                Connection connection = connectionFactory.createConnection("guest", "guest");
                try {
                    Session jmsSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageProducer producer = jmsSession.createProducer(destination);
                    TextMessage message = jmsSession.createTextMessage(messageContents);
                    producer.send(message);
                    out.println("<pre>" + "Sent to " + targetQueueName + ":\n" + message + "</pre>");
                } finally {
                    connection.close();
                }
            }
        }
        %>
    </body>
</html>