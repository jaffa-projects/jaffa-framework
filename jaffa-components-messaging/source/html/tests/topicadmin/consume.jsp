<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.Properties"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.management.MBeanServer"%>
<%@page import="javax.management.ObjectName"%>
<%@page import="javax.jms.Connection"%>
<%@page import="javax.jms.ConnectionFactory"%>
<%@page import="javax.jms.Destination"%>
<%@page import="javax.jms.Session"%>
<%@page import="javax.jms.MessageConsumer"%>
<%@page import="javax.jms.Message"%>
<%@page import="javax.jms.TextMessage"%>
<%@page import="javax.jms.Topic"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.net.*"%>
<%@page import="java.io.*"%>
<%@ page import="org.jaffa.modules.messaging.services.*" %>
<%@ page import="javax.jms.MessageListener" %>
<%@ page import="javax.jms.TopicSubscriber" %>


<html>
    <head>
        <title>Consume Messages</title>
        <link href="css/main.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="utils.js"></script>
    </head>
    <body>
        <form name="consumeForm" action="consume.jsp" method="post">
            <input type="hidden" name="subDetails"  value=""/>
            <input type="hidden" name="userName"  value=""/>
            <input type="hidden" name="password"  value=""/>
            <input type="hidden" name="topicName"  value=""/>
            <input type="hidden" name="action"  value=""/>
            <table class="outer">
                <tr>
                    <td align='center'><h2>Consume Messages</h2></td>
                </tr>
                <tr>
                    <td align='right'><input type="button" name="refresh" id="refresh" value="Refresh" onclick="doAction(this.name)"/></td>
                </tr>
                <tr>
                    <td>
                        <div class="panel">
                            <h2>Durable Subscriber Messages</h2>
                            <div class="panelcontent">
                                <table class="inner">
                                    <thead class="header"><th>Message ID</th><th>Destination</th><th>Message</th></thead>
                                    <%
                                        String errorMessage = null;
                                        String topicName = (String) request.getParameter("topicName");
                                        String subDetails = (String) request.getParameter("subDetails");
                                        String action = (String) request.getParameter("action");

                                        if (subDetails != null) {
                                            String[] details = subDetails.split(",");
                                            List<TextMessage> list = null;
                                            if("Refresh".equals(action)){
                                               list = consume(topicName, details[0], details[1], details[2], "0");
                                            } else {
                                               list = consume(topicName, details[0], details[1], details[2], details[3]);
                                            }
                                            if (list != null) {
                                                for (int i = 0; i < list.size(); i++) {
                                                    TextMessage message = list.get(i);

                                    %>
                                    <tr class="<%= (i % 2 == 0 ? "odd" : "even")%>">
                                        <td><%= message.getJMSMessageID()%></td>
                                        <td><%= message.getJMSDestination().toString()%></td>
                                        <td><textarea rows="5" cols="80"><%= message.getText()%></textarea></td>
                                    </tr>
                                    <%
                                                }
                                            }
                                        }
                                    %>

                                </table>
                                <% if (errorMessage != null) {%>
                                <b><font color='red'>Error Message: <%= errorMessage%></font></b>
                                    <%}%>                                
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </body>
    <script language="javascript">
        function doAction(action){
            if(action==='refresh'){
                
                document.consumeForm.topicName.value="<%= (String) request.getParameter("topicName")%>";
                document.consumeForm.subDetails.value="<%= (String) request.getParameter("subDetails")%>";
                document.consumeForm.action.value='Refresh';
                document.consumeForm.submit();
               
            }else{
                document.consumeForm.submit();
            }
        }
        
    </script>    
</html>

<%!    private static String createStandardValveTypes[] = {"java.lang.String", "java.lang.String"};

    public List<TextMessage> consume(String topicName, String clientId, String subscriptionName,String selector,String pendingMessageSize) throws Exception {
        System.out.println("pendingMessageSize :"+pendingMessageSize);
        List<TextMessage> consumedMessageList = new ArrayList<TextMessage>();
        Connection connection = null;
        TopicSubscriber consumer = null;
        try {

            ConnectionFactory connectionFactory = JaffaConnectionFactory.obtainConnectionFactory();
            connection = connectionFactory.createConnection();
            connection.setClientID(clientId);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            InitialContext context = InitialContextFactrory.obtainInitialContext();

            // Subscribe to a Topic
            Destination destination = (Destination) context.lookup("topic/OutboundEvents");

            consumer = session.createDurableSubscriber((Topic)destination, subscriptionName);
            connection.start();
            for(int i=0;i<Integer.parseInt(pendingMessageSize);i++){
                TextMessage message = (TextMessage)consumer.receive();
                if(message!=null){
                    consumedMessageList.add(message);
                }
            }
                      
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally{
            if(consumer!=null){
                consumer.close();
                consumer = null;
            }
            if (connection != null) {
                connection.close();
                connection = null;
            }
            pendingMessageSize = "0";
        }
        return consumedMessageList;
    }

%>
