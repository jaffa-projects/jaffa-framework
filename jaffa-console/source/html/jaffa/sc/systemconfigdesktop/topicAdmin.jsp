<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import = "org.jaffa.util.URLHelper" %>
<%@page import="java.util.Properties"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.PropertyResourceBundle"%>
<%@page import="javax.management.MBeanServer"%>
<%@page import="javax.management.ObjectName"%>
<%@page import="javax.jms.Connection"%>
<%@page import="javax.jms.ConnectionFactory"%>
<%@page import="javax.jms.Destination"%>
<%@page import="javax.jms.Session"%>
<%@page import="javax.jms.MessageConsumer"%>
<%@page import="javax.jms.Message"%>
<%@page import="javax.jms.Topic"%>
<%@page import="javax.jms.TextMessage"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.net.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.UUID"%>
<%@page import="org.jaffa.modules.messaging.services.configdomain.JmsConfig"%>
<%@page import="org.jaffa.modules.messaging.services.ConfigurationService"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.jaffa.modules.scheduler.services.ScheduledTask"%>
<%@page import="org.jaffa.modules.scheduler.services.SchedulerBrowser"%>
<%@page import="org.jaffa.modules.scheduler.services.SchedulerHelper"%>
<%@page import="org.jaffa.modules.scheduler.services.SchedulerHelperFactory"%>
<%@page import="org.jaffa.session.ContextManagerFactory"%>
<%@page import="org.jaffa.persistence.UOW"%>
<%@page import="org.jaffa.persistence.Criteria"%>
<%@page import="org.jaffa.soa.domain.SOAEvent"%>
<%@page import="org.jaffa.soa.domain.SOAEventMeta"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="org.jaffa.transaction.daos.TransactionMessageDAOFactory"%>
<%@page import="org.jaffa.transaction.domain.Transaction"%>
<%@page import="org.jaffa.transaction.domain.TransactionMeta"%>
<%@page import="org.jaffa.modules.messaging.services.configdomain.QueueInfo"%>
<%@page import="org.jaffa.modules.messaging.services.ConfigurationService"%>
<%@page import="org.jaffa.modules.messaging.services.JmsBrowser"%>
<%@page import="org.jaffa.qm.apis.data.QueueGraph"%>
<%@page import="org.jaffa.modules.messaging.services.configdomain.ConsumerPolicy"%>
<%@page import="org.apache.activemq.ActiveMQConnection"%>
<%@page import="org.apache.activemq.ActiveMQConnectionFactory"%>
<%@page import="org.jaffa.modules.messaging.services.*"%>
<%@ page import="org.apache.activemq.broker.jmx.BrokerViewMBean" %>
<%@ page import="org.jaffa.sc.RMIContextFactory" %>
<%@ page import="javax.management.*" %>
<%@ page import="java.lang.management.ManagementFactory" %>
<%@ page import="javax.management.MBeanServerInvocationHandler" %>
<%@ page import="javax.management.remote.JMXServiceURL" %>
<%@ page import="javax.management.remote.JMXConnectorFactory" %>
<%@ page import="javax.management.remote.JMXConnector" %>
<%@ page import="javax.management.MalformedObjectNameException" %>
<%@ page import="org.apache.activemq.broker.jmx.DurableSubscriptionViewMBean" %>
<%@ page import="javax.jms.TopicSubscriber" %>
<%@ page import="org.jaffa.util.StringHelper" %>

<%!

    private static String createStandardValveTypes[] = {"java.lang.String", "java.lang.String"};
    
    private static final String TOPIC_DESTINATION_PREFIX = "topic/";
    private static final String PREFIX_BROKER_NAME = "org.apache.activemq:brokerName=";
    private static final String BROKER_NAME_SYSTEM_PROPERTY = System.getProperty("activemq.broker.name", "goldesp-jms-broker");
    private static final String BROKER_NAME_SUFFIX = ",type=Broker";
    private static final String BROKER_JMX_REMOTE_ACCESS_FILE = System.getProperty("activemq.jmx.remote.access.file", "");
    private static final String BROKER_JMX_REMOTE_PASSWORD_FILE = System.getProperty("activemq.jmx.remote.password.file", "");
    private static final String BROKER_JMX_USER = System.getProperty("activemq.jmxuser", "");
    private static final String BROKER_JMX_PASSWORD = System.getProperty("activemq.jmxpassword", "");
    private static final String BROKER_IP_ADDRESS_SYSTEM_PROPERTY = System.getProperty("activemq.broker.ip.address", "localhost");
    private static final String BROKER_JMX_REMOTE_PORT_SYSTEM_PROPERTY = System.getProperty("activemq.jmxremote.port", "11099");
    private static final String BROKER_JMX_REMOTE_SERVICE_NAME_SYSTEM_PROPERTY = System.getProperty("activemq.jmxremote.service.name", "jmxrmi");
    private static final String REMOTE_JMX_URL = "service:jmx:rmi:///jndi/rmi://"+ BROKER_IP_ADDRESS_SYSTEM_PROPERTY + ":"+ BROKER_JMX_REMOTE_PORT_SYSTEM_PROPERTY + "/" + BROKER_JMX_REMOTE_SERVICE_NAME_SYSTEM_PROPERTY;
    private JMXConnector connector;

    protected ObjectName getObjectName() throws MalformedObjectNameException {
        ObjectName objectName = null;
        try {
            objectName = new ObjectName(PREFIX_BROKER_NAME + BROKER_NAME_SYSTEM_PROPERTY + BROKER_NAME_SUFFIX);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        return objectName;
    }

    private boolean isExternalJMSProvider(){
        return true;
    }

    private MBeanServer getLocalMBeanServer(){
       final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
       return mBeanServer;
    }

    private MBeanServerConnection getRemoteMBeanServer(){
        MBeanServerConnection connection = null;
        try{
            final JMXServiceURL serviceURL = new JMXServiceURL(REMOTE_JMX_URL);

            final Map<String, Object> env = new HashMap<String, Object>();
            env.put(InitialContext.INITIAL_CONTEXT_FACTORY,
                RMIContextFactory.class.getName());

            if (0 < BROKER_JMX_REMOTE_ACCESS_FILE.length()) {
              env.put("com.sun.management.jmxremote.access.file", BROKER_JMX_REMOTE_ACCESS_FILE);
              env.put("com.sun.management.jmxremote.password.file", BROKER_JMX_REMOTE_PASSWORD_FILE);
            } else if (0 < BROKER_JMX_USER.length()) {
              final String[] creds = { BROKER_JMX_USER, BROKER_JMX_PASSWORD };
              env.put(JMXConnector.CREDENTIALS, creds);
            }

            connector = JMXConnectorFactory.connect(serviceURL, env);
            connector.connect();

            connection = connector.getMBeanServerConnection();
        }catch(Exception ioe){
          //ioe.printStackTrace();
	}

        return connection;
    }

    protected List<DurableSubscriptionViewMBean> getDurableSubscriptionViewMBean(final String topicName)  throws Exception {
        List<DurableSubscriptionViewMBean> durableSubViewMBeanList = new ArrayList<DurableSubscriptionViewMBean>();
        try{
            final ObjectName activeMQtopic = getObjectName();
            if(activeMQtopic!=null){
              final BrokerViewMBean brokerMbean = (BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance(isExternalJMSProvider() ? getRemoteMBeanServer() : getLocalMBeanServer(), activeMQtopic, BrokerViewMBean.class, true);
              if(brokerMbean!=null){
                for (ObjectName durableSubscription : brokerMbean.getInactiveDurableTopicSubscribers()) {
                  String destinationName = durableSubscription.getKeyProperty("destinationName");
                  if((TOPIC_DESTINATION_PREFIX+topicName).equals(destinationName)){
                    final DurableSubscriptionViewMBean durableSubscriptionViewMBean = (DurableSubscriptionViewMBean) MBeanServerInvocationHandler.newProxyInstance(isExternalJMSProvider() ? getRemoteMBeanServer() : getLocalMBeanServer(), durableSubscription, DurableSubscriptionViewMBean.class, true);
                    durableSubViewMBeanList.add(durableSubscriptionViewMBean);
                  }
                }
                for (ObjectName durableSubscription : brokerMbean.getDurableTopicSubscribers()) {
                  String destinationName = durableSubscription.getKeyProperty("destinationName");
                  if((TOPIC_DESTINATION_PREFIX+topicName).equals(destinationName)){
                    final DurableSubscriptionViewMBean durableSubscriptionViewMBean = (DurableSubscriptionViewMBean) MBeanServerInvocationHandler.newProxyInstance(isExternalJMSProvider() ? getRemoteMBeanServer() : getLocalMBeanServer(), durableSubscription, DurableSubscriptionViewMBean.class, true);
                    durableSubViewMBeanList.add(durableSubscriptionViewMBean);
                  }
                }
              }
            }
        } catch (Exception e) {
            // Ideally catch the 3 exact exceptions
            e.printStackTrace();
        }
        return durableSubViewMBeanList;
    }


    public void unsubscribe(String clientID,String subscriptionName, String topicName) throws Exception {
        Connection connection = null;
        Session session = null;
        try {

            final ConnectionFactory connectionFactory = JaffaConnectionFactory.obtainConnectionFactory();
            final JmsConfig jmsConfig = ConfigurationService.getInstance().getJmsConfig();
            if (jmsConfig.getUser() == null)
              connection = connectionFactory.createConnection();
            else
              connection = connectionFactory.createConnection(jmsConfig.getUser(),jmsConfig.getPassword());

            connection.setClientID(clientID);
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            session.unsubscribe(subscriptionName);

        } catch (Exception e) {
            e.printStackTrace();
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

            final ConnectionFactory connectionFactory = JaffaConnectionFactory.obtainConnectionFactory();
            final JmsConfig jmsConfig = ConfigurationService.getInstance().getJmsConfig();
            if (jmsConfig.getUser() == null)
              connection = connectionFactory.createConnection();
            else
              connection = connectionFactory.createConnection(jmsConfig.getUser(),jmsConfig.getPassword());

            connection.setClientID(clientId);
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            InitialContext context = InitialContextFactrory.obtainInitialContext();

            // Subscribe to a Topic
            Destination destination = (Destination) context.lookup("topic/OutboundEvents");

            consumer = session.createDurableSubscriber((Topic)destination, subscriptionName, selector, false);
          
        } catch (Exception e) {
            e.printStackTrace();
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


    public List<TextMessage> consume(String topicName, String clientId, String subscriptionName,String selector,int numberOfMessageToConsume,int pendingMessageSize) throws Exception {
        
        List<TextMessage> consumedMessageList = new ArrayList<TextMessage>();
        Connection connection = null;
        TopicSubscriber consumer = null;
        Session session = null;
        try {

            final ConnectionFactory connectionFactory = JaffaConnectionFactory.obtainConnectionFactory();
            final JmsConfig jmsConfig = ConfigurationService.getInstance().getJmsConfig();
            if (jmsConfig.getUser() == null)
              connection = connectionFactory.createConnection();
            else
              connection = connectionFactory.createConnection(jmsConfig.getUser(),jmsConfig.getPassword());

            connection.setClientID(clientId);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();

            InitialContext context = InitialContextFactrory.obtainInitialContext();

            // Subscribe to a Topic
            Destination destination = (Destination) context.lookup("topic/OutboundEvents");

            if(selector == null || "".equals(selector)){
               consumer = session.createDurableSubscriber((Topic)destination,subscriptionName);
            } else {
               consumer = session.createDurableSubscriber((Topic)destination,subscriptionName,selector,false);
            }
            
            int loopthrough = (numberOfMessageToConsume < pendingMessageSize)? numberOfMessageToConsume : pendingMessageSize ;
            for(int i=0;i<loopthrough;i++){
				Message message = consumer.receive();
				// Not every message posted to the topic will be a TextMessage.
				// At this time only TextMessages can be handled:
				if(message instanceof TextMessage){
					TextMessage textMessage = (TextMessage)message;
					if(textMessage!=null){
						consumedMessageList.add(textMessage);
					}
				}
            }
                      
        } catch (Exception e) {
            e.printStackTrace();
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
        return consumedMessageList;
    }
    
    String localId = (String) ContextManagerFactory.instance().getProperty("jaffa.soa.events.localId");
    
    private long getDataBaseEventCount() throws Exception{
    
        UOW uow = null;
        Number count = null;
        try{

            uow = new UOW();
            
            Criteria criteria = new Criteria();
            criteria.setTable(SOAEventMeta.getName());
            criteria.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
            if (localId != null && !"".equals(localId)) {
                criteria.addCriteria(SOAEventMeta.LOCAL_ID, localId);
            } else {
                criteria.addCriteria(SOAEventMeta.LOCAL_ID, Criteria.RELATIONAL_IS_NULL);
            }
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext()) {
                count = (Number) ((Map) itr.next()).get(Criteria.ID_FUNCTION_COUNT);
            }

         }finally{
             if(uow!=null){
                uow.rollback();
             }
         }        
         return count!=null? count.intValue() : 0;
    }
    
    private void getTransactionAndQueueStatus(Map jsonMap) throws Exception{
    
        UOW uow = null;
        try{

            uow = new UOW();
            
            jsonMap.put("transactionQueuePollerStatus","Yes");
            jsonMap.put("transactionQueueEventStatus","Yes");

         }finally{
             if(uow!=null){
                uow.rollback();
             }
         }        
    }
    
    private String getSOAEventHandlerDroolRules() throws Exception{

        String files = (String) ContextManagerFactory.instance().getProperty("jaffa.soa.droolsAgentConfig.SOAEventService.file");
        String userDefDroolsDir = (String) ContextManagerFactory.instance().getProperty("jaffa.soa.droolsAgentConfig.SOAEventService.dir");
        
        Set<String> droolsList = new HashSet<String>();
        if(files!=null){
            droolsList.addAll(Arrays.asList(files.split(" ")));
        }

        if (userDefDroolsDir!=null){
            File droolsDir = new File(userDefDroolsDir);
            if (droolsDir.exists() && droolsDir.isDirectory()) {

                 File[] drools = droolsDir.listFiles();

                 for (File drool : drools) {
                     droolsList.add(drool.getPath());
                 }

            }
        }

        StringBuilder listJson = new StringBuilder("[");
        Iterator itr = droolsList.iterator();        
        while (itr.hasNext()) {
           if(!itr.hasNext()){
             listJson.append("{path :").append("'").append(itr.next()).append("'}");  
           }else{
             listJson.append("{path :").append("'").append(itr.next()).append("'},");
           }
        }                 
        listJson.append("]");
        
        return listJson.toString();
    }    
    
    private long getEventTransactionCount() throws Exception{
        return TransactionMessageDAOFactory.getTransactionMessageService().getCountByType("Event");
    }
       
    private Map<String,String> getSchedulerStatus() throws Exception {

        Map<String,String> statusMap = new HashMap<String,String>();
        try {
            SchedulerHelper sh = SchedulerHelperFactory.instance();
            
            //setting scheduler status
            statusMap.put("schedulderStatus",SchedulerHelper.SchedulerStatusEnumeration.RUNNING.equals(sh.getSchedulerStatus())?"Yes":"No");
            
            ScheduledTask[] tasks = sh.getAllTasks();
            boolean hasConfigured = false;
            if (tasks != null) {
                for (ScheduledTask task : tasks) {
                    if (SchedulerBrowser.hasBrowseTaskAccess(task) && "SOAEventPoller".equals(task.getTaskType())) {
                        
                        hasConfigured = true;
                        
                        //setting poller task status
                        if(ScheduledTask.TaskStatusEnumeration.UNKNOWN.equals(task.getStatus()) || ScheduledTask.TaskStatusEnumeration.PAUSED.equals(task.getStatus())){
                            statusMap.put("pollerConfig","Not Running");
                        } else if(ScheduledTask.TaskStatusEnumeration.COMPLETE.equals(task.getStatus())){
                            statusMap.put("pollerConfig","Finished");
                        } else if(task.getNextDue()!=null && ScheduledTask.TaskStatusEnumeration.NORMAL.equals(task.getStatus())){
                            statusMap.put("pollerConfig","Scheduled to run at "+task.getNextDue());
                        } else  if(task.getLastRunOn()!=null && ScheduledTask.TaskStatusEnumeration.NORMAL.equals(task.getStatus())){
                            statusMap.put("pollerConfig","Running");
                        } 
                        
                    }
                }
            }
            if(!hasConfigured){
                statusMap.put("pollerConfig","Not Configured");
            }           
            
        } catch (Exception e) {
            // Ideally catch the 3 exact exceptions
            e.printStackTrace();
        }
        return statusMap;
    }    

    public String getPollerAndEventConfigJson() throws Exception{
    
        Map jsonMap = getSchedulerStatus();
        
        getTransactionAndQueueStatus(jsonMap);
        //Removing this, since the JaffaHighVelocityQueue no longer available.

        jsonMap.put("dbEventCount",getDataBaseEventCount());
        jsonMap.put("transactionEventCount",getEventTransactionCount());
        jsonMap.put("localId",localId);
        jsonMap.put("drlFilePath",getSOAEventHandlerDroolRules());
        

        JSONObject json = new JSONObject();
        json.putAll(jsonMap);

        return json.toString();    
    }
    
    private String durableSubsciberListJson(List<DurableSubscriptionViewMBean> subscriberList,String topicName){
       StringBuilder listJson = new StringBuilder("[");
       if(subscriberList!=null && 0<subscriberList.size()){
         for(int i=0;i<subscriberList.size();i++){

           DurableSubscriptionViewMBean subscriptionDetails =  subscriberList.get(i);

           listJson.append("{\"clientID\" :");
           listJson.append("\""+subscriptionDetails.getClientId()+"\",");
           listJson.append("\"subscriberName\" :");
           listJson.append("\""+subscriptionDetails.getSubscriptionName()+"\",");
           listJson.append("\"messageSelector\" :");
           listJson.append("\""+(subscriptionDetails.getSelector()!=null?subscriptionDetails.getSelector():"")+"\",");
           listJson.append("\"status\" :");
           listJson.append("\""+(subscriptionDetails.isActive()?"Online":"Offline")+"\",");
           listJson.append("\"pendingMessageSize\" :");
           if(i==subscriberList.size()-1){
             listJson.append("\""+subscriptionDetails.getPendingQueueSize()+"\"}");
           } else {
             listJson.append("\""+subscriptionDetails.getPendingQueueSize()+"\"},");
           }
         }
       }
       listJson.append("]");

       return listJson.toString();
    }
%>

<%

    JmsConfig jmsConfig = ConfigurationService.getInstance().getJmsConfig();

    String action = request.getParameter("action");
    String topicName = StringHelper.escapeJavascript(request.getParameter("topicName"));
    String subscriptionName = StringHelper.escapeJavascript(request.getParameter("subscriptionName"));
    String clientID = StringHelper.escapeJavascript(request.getParameter("clientID"));
    String selector = StringHelper.escapeJavascript(request.getParameter("messageSelector"));
    String pendingMessageSize = request.getParameter("pendingMessageSize");
    String userName = jmsConfig.getUser();    
    String password = jmsConfig.getPassword();   
    String providerURL = null;

    if("subscribe".equals(action)){
       try{
           subscribe(topicName,subscriptionName,clientID,selector);
           
           List<DurableSubscriptionViewMBean>  subscriberList = getDurableSubscriptionViewMBean(topicName);

           out.write("{\"success\":true,\"data\":"+durableSubsciberListJson(subscriberList,topicName)+"}");
       }catch(Exception e){
           out.write("{\"success\":false,\"data\":{\"result\":\"" + e.getMessage().replaceAll("\"", "\'").replaceAll("\\n", "") + "\"}}");
       } finally{
         if(connector != null){
            try{
                connector.close();
            } catch(IOException e){}
         }
       }
       
    }else if("unsubscribe".equals(action)){

       try{
           unsubscribe(clientID,subscriptionName,topicName);
           
           out.write("{\"success\":true,\"data\":{\"result\": \"Successfully unsubscribed Client ID: "+clientID+" and Subscriber Name: "+subscriptionName+"\"}}");
           
       }catch(Exception e){
           out.write("{\"success\":false,\"data\":{\"result\":\"" + e.getMessage().replaceAll("\"", "\'").replaceAll("\\n", "") + "\"}}");
       }
       
    }else if("consume".equals(action)){
      try{

           int numberOfMsgs = Integer.parseInt((String) request.getParameter("numberOfMsgs"));
                   
           List<TextMessage> consumedList = consume(topicName,clientID,subscriptionName,selector,numberOfMsgs,Integer.parseInt(pendingMessageSize));
                   
           List<DurableSubscriptionViewMBean>  subscriberList = getDurableSubscriptionViewMBean(topicName);
           
           session.setAttribute("consumedList", consumedList);

           out.write("{\"success\":true,\"data\":"+durableSubsciberListJson(subscriberList,topicName)+"}");
       }catch(Exception e){
           out.write("{\"success\":false,\"data\":{\"result\":\"" + e.getMessage().replaceAll("\"", "\'").replaceAll("\\n", "") + "\"}}");
       } finally{
         if(connector != null){
            try{
                connector.close();
            } catch(IOException e){}
         }
       }
    }else{
       try{
           List<DurableSubscriptionViewMBean>  subscriberList = getDurableSubscriptionViewMBean(topicName);

           StringBuilder listJson = new StringBuilder("[");
           
           out.write("{\"success\":true,\"data\":"+durableSubsciberListJson(subscriberList,topicName)+",\"schedulerPollerAndEventConfig\":"+getPollerAndEventConfigJson()+"}");
       }catch(Exception e){
		   String err_msg = e.getMessage();
		   e.printStackTrace();
		   if(err_msg!=null && 0<err_msg.length()){
			 String trim_msg = err_msg.replaceAll("\"", "\'");
			 if(trim_msg!=null && 0<trim_msg.length()){
               out.write("{\"success\":false,\"data\":{\"result\":\"" + trim_msg.replaceAll("\\n", "") + "\"}}");	 
			 }
		   }
       } finally{
         if(connector != null){
            try{
                connector.close();
            } catch(IOException e){}
         }
       }
    }
   
%>