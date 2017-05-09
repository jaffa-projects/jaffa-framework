<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="org.apache.activemq.ActiveMQConnection"%>
<%@ page import="org.apache.activemq.ActiveMQConnectionFactory"%>
<%@ page import="org.apache.activemq.broker.jmx.BrokerViewMBean" %>
<%@ page import="org.apache.activemq.broker.jmx.DurableSubscriptionViewMBean" %>
<%@ page import ="org.jaffa.util.URLHelper"%>
<%@ page import="org.jaffa.modules.messaging.services.*"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="javax.management.MBeanServer"%>
<%@ page import="javax.management.ObjectName"%>
<%@ page import="javax.jms.Connection"%>
<%@ page import="javax.jms.ConnectionFactory"%>
<%@ page import="javax.jms.Destination"%>
<%@ page import="javax.jms.Session"%>
<%@ page import="javax.jms.TextMessage"%>
<%@ page import="javax.jms.MessageConsumer"%>
<%@ page import="javax.jms.Topic"%>
<%@ page import="javax.jms.TopicSubscriber" %>
<%@ page import="javax.naming.Context"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="java.net.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.lang.management.ManagementFactory" %>
<%@ page import="javax.management.MBeanServerInvocationHandler" %>
<%@ page import="javax.management.MalformedObjectNameException" %>
<html>
    <head>
        <title>Topic Administrator</title>
        <link href="css/main.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="utils.js"></script>
    </head>
    <body>
        <form name="topicForm"  method="post">
            <input type="hidden" name="option"  value=""/>
            <input type="hidden" name="subDetails"  value=""/>
            <table class="outer">
                <tr>
                    <td align='center'><h2>TOPIC ADMIN</h2></td>
                </tr>
                <tr>
                    <td>
                        <div class="panel">
                            <h2>Durable Subscriber Search/Create</h2>
                            <div class="panelcontent">
                                <table>
                                    <tr>
                                        <td>Client ID : </td>
                                        <td>
                                            <input type="text" name="clientId" id="clientId"/>
                                        </td>
                                    </tr>                            
                                    <tr>
                                        <td>Subscription Name : </td>
                                        <td>
                                            <input type="text" name="subscriptionName" id="subscriptionName"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Message Selector : </td>
                                        <td>
                                            <input type="text" name="messageSelecter" id="messageSelecter"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Topic Name : </td>
                                        <td>
                                            <input type="text" name="topicName" id="topicName" value="<%= request.getParameter("topicName") != null ? request.getParameter("topicName") : ""%>"/>
                                            <input type="button" name="find" id="find" value="Search" onclick="doAction(this.name)"/>
                                            <input type="button" name="create" id="create" value="Create" onclick="doAction(this.name)"/>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <tr>
                    <td>
                        <div class="panel">
                            <h2>Durable Subscriber List</h2>
                            <div class="panelcontent">
                                <table class="inner">
                                    <thead class="header"><th>Client ID</th><th>Subscriber Name</th><th>Message Selector</th><th>Status</th><th>Pending Queue Size</th><th>Action</th></thead>
                                    <%
                                        String errorMessage = null;
                                        String userName = (String) request.getParameter("userName");
                                        String password = (String) request.getParameter("password");
                                        String topicName = (String) request.getParameter("topicName");
                                        String subscriptionName = (String) request.getParameter("subscriptionName");
                                        String clientId = (String) request.getParameter("clientId");
                                        String selector = (String) request.getParameter("messageSelecter");
                                        String action = (String) request.getParameter("option");
                                        String subDetails = (String) request.getParameter("subDetails");


                                        if ("delete".equals(action)) {
                                            try {
                                                unsubscribe(subDetails, topicName);
                                            } catch (Exception e) {
                                                errorMessage = e.getMessage();
                                            }
                                        }

                                        if ("create".equals(action)) {
                                            try {
                                                subscribe(topicName, subscriptionName,clientId,selector);
                                            } catch (Exception e) {
                                                errorMessage = e.getMessage();
                                            }
                                        }

                                        if (topicName != null) {
                                            List<DurableSubscriptionViewMBean> durableSubViewMBeanList = null;
                                            try {
                                                durableSubViewMBeanList = getDurableSubscriptionViewMBean(topicName);
                                            } catch (Exception e) {
                                                errorMessage = e.getMessage();
                                            }
                                            if (durableSubViewMBeanList != null) {
                                                for (int i = 0; i < durableSubViewMBeanList.size(); i++) {
                                                    DurableSubscriptionViewMBean details = (DurableSubscriptionViewMBean) durableSubViewMBeanList.get(i);

                                    %>
                                    <tr class="<%= (i % 2 == 0 ? "odd" : "even")%>">
                                        <td><%= details.getClientId()%></td>
                                        <td><%= details.getSubscriptionName()%></td>
                                        <td><%= details.getSelector()%></td>
                                        <td><%= details.isActive()?"Online":"Offline"%></td>
                                        <td><%= details.getPendingQueueSize()%></td>
                                        <%if(!details.isActive()) {%>
                                        <td><input type="button" name="delete" id="<%= details.getClientId() + "," + details.getSubscriptionName()%>" value="Delete" onclick="doAction(this.name,this.id)"/>
                                            <input type="button" name="consume" id="<%= details.getClientId() + "," + details.getSubscriptionName()+","+details.getSelector()+","+details.getPendingQueueSize()%>" value="Consume" onclick="doAction(this.name,this.id)"/></td>
                                        <% } %>
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
        function doAction(action,id){
            if(action==='delete'){
                var message = '';
                if(document.topicForm.topicName.value === ''){
                    message+="\n"+"Topic name mandatory!";
                }
            
                if(message!==''){
                    alert(message);
                    return;
                }
                if(!confirm('Do you want to delete the subscription?')){
                    return;   
                }                
                document.topicForm.target='';
                document.topicForm.subDetails.value=id;
                document.topicForm.action="topicAdmin.jsp";
            }else if(action==='create'){
                var message = '';
                if(document.topicForm.topicName.value === ''){
                    message+="\n"+"Topic name mandatory!";
                }
                if(document.topicForm.subscriptionName.value === ''){
                    message+="\n"+"Subscription name mandatory!";
                } 
                if(document.topicForm.clientId.value === ''){
                    message+="\n"+"Client ID mandatory!";
                }                 
                if(message!==''){
                    alert(message);
                    return;
                }
                document.topicForm.target='';
                document.topicForm.action="topicAdmin.jsp";
            }else if(action==='consume'){
                var message = '';
                if(document.topicForm.topicName.value === ''){
                    message+="\n"+"Topic name mandatory!";
                }
                if(message!==''){
                    alert(message);
                    return;
                }
                if(!confirm('Do you want to consume the messages?')){
                    return;   
                }                
                document.topicForm.subDetails.value=id;
                document.topicForm.action="consume.jsp";
                document.topicForm.target="_blank";
            }else{
                if(document.topicForm.topicName.value === ''){
                    alert("Topic name mandatory!")
                    return;
                }
                document.topicForm.target='';
                document.topicForm.action="topicAdmin.jsp";
            }
            
            document.topicForm.option.value=action;
            document.topicForm.submit();
        }
        
    </script>    
</html>

<%!

    private static final String TOPIC_DESTINATION_PREFIX = "topic/";
    private static final String PREFIX_BROKER_NAME = "org.apache.activemq:brokerName=";
    private static final String BROKER_NAME_SYSTEM_PROPERTY = System.getProperty("activemq.broker.name", "localhost");
    private static final String BROKER_NAME_SUFFIX = ",type=Broker";

    protected ObjectName getObjectName() throws MalformedObjectNameException {

        ObjectName objectName = null;
        try {
            objectName = new ObjectName(PREFIX_BROKER_NAME + BROKER_NAME_SYSTEM_PROPERTY + BROKER_NAME_SUFFIX);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
            throw e;
        }
        return objectName;
    }

    protected List<DurableSubscriptionViewMBean> getDurableSubscriptionViewMBean(final String topicName)  throws Exception {

        List<DurableSubscriptionViewMBean> durableSubViewMBeanList = new ArrayList<DurableSubscriptionViewMBean>();
        try{
            final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

            final ObjectName activeMQtopic = getObjectName();
            final BrokerViewMBean brokerMbean = (BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance(mBeanServer, activeMQtopic, BrokerViewMBean.class, true);
            for (ObjectName durableSubscription : brokerMbean.getInactiveDurableTopicSubscribers()) {
                String destinationName = durableSubscription.getKeyProperty("destinationName");
                if((TOPIC_DESTINATION_PREFIX+topicName).equals(destinationName)){
                    final DurableSubscriptionViewMBean durableSubscriptionViewMBean = (DurableSubscriptionViewMBean) MBeanServerInvocationHandler.newProxyInstance(mBeanServer, durableSubscription, DurableSubscriptionViewMBean.class, true);
                    durableSubViewMBeanList.add(durableSubscriptionViewMBean);
                }
            }
            for (ObjectName durableSubscription : brokerMbean.getDurableTopicSubscribers()) {
                String destinationName = durableSubscription.getKeyProperty("destinationName");
                if((TOPIC_DESTINATION_PREFIX+topicName).equals(destinationName)){
                    final DurableSubscriptionViewMBean durableSubscriptionViewMBean = (DurableSubscriptionViewMBean) MBeanServerInvocationHandler.newProxyInstance(mBeanServer, durableSubscription, DurableSubscriptionViewMBean.class, true);
                    durableSubViewMBeanList.add(durableSubscriptionViewMBean);
                }
            }
        } catch (Exception e) {
            // Ideally catch the 3 exact exceptions
            e.printStackTrace();
            throw e;
        }
        return durableSubViewMBeanList;
    }
    
    public void unsubscribe(String subDetails,String topicName) throws Exception {
        Connection connection = null;
        Session session = null;
        try {

            String[] details = subDetails.split(",");

            ConnectionFactory connectionFactory = JaffaConnectionFactory.obtainConnectionFactory();
            connection = connectionFactory.createConnection();
            connection.setClientID(details[0]);
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            session.unsubscribe(details[1]);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally{
            if(session!=null){
               session.close();
               session = null;
            }
            if (connection != null) {
               connection.close();
               connection = null;
            }
        }
    }

    public void subscribe(String topicName, String subscriptionName,String clientId, String selector) throws Exception {
        Connection connection = null;
        Session session = null;
        TopicSubscriber consumer = null;
        try {

            ConnectionFactory connectionFactory = JaffaConnectionFactory.obtainConnectionFactory();
            connection = connectionFactory.createConnection();
            connection.setClientID(clientId);
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            InitialContext context = InitialContextFactrory.obtainInitialContext();

            // Subscribe to a Topic
            Destination destination = (Destination) context.lookup("topic/OutboundEvents");

            consumer = session.createDurableSubscriber((Topic)destination, subscriptionName, selector, false);
          
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally{
            if(consumer!=null){
                consumer.close();
                consumer = null;
            }
            if(session!=null){
               session.close();
               session = null;
            }
            if (connection != null) {
               connection.close();
               connection = null;
            }
        }
    }
%>