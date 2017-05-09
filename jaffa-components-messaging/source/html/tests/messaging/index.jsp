<%@ page language="java" %>
<html>
    <head>
        <title>JaffaComponentsMessaging Test Screens</title>
        <link href='/jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
    </head>
    
    <body>
        <h1>JaffaComponentsMessaging Test Screens</h1>
        <b><u>Prerequisite</u>: Copy the contents of tests/messaging/jaffa-messaging-config.xml to resources/jaffa-messaging-config.xml file in the deployed application</b>
        <ul>
            <li><a href="browse.jsp">Browse messages using JaffaComponentsMessaging</a>
            <li><a href="delete.jsp">Delete messages using JaffaComponentsMessaging</a>
            <li><a href="sendMessageUsingJms.jsp">Send Message using JMS</a>
            <li><a href="sendMessageUsingJaffa.jsp">Send Message using JaffaComponentsMessaging</a>
            <li><a href="sendMessageUsingJaffaTransaction.jsp">Send Messages using JaffaComponentsMessaging in a Transaction</a>
            <li><a href="changeMessagePriority.jsp">Change Message Priority using JaffaComponentsMessaging</a>
        </ul>  
    </body>
</html>