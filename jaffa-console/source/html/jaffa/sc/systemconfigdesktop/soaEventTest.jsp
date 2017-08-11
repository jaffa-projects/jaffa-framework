<%@ page import='java.lang.reflect.Method,
         org.apache.log4j.Logger,
         org.jaffa.util.StringHelper,
         net.sf.json.JSONObject,
         org.jaffa.persistence.UOW,
         net.sf.json.JSON,
         net.sf.json.JSONSerializer,
         org.jaffa.soa.domain.SOAEvent,
         org.jaffa.soa.domain.SOAEventParam,
         net.sf.json.util.JSONUtils,
         net.sf.json.JSONArray,
         net.sf.json.JsonConfig,
         java.io.IOException,
         net.sf.json.JsonConfig,
         java.util.*,
         java.util.Map.Entry,
         net.sf.json.util.CycleDetectionStrategy,
         org.jaffa.soa.services.SOAEventQueueMessage,
         org.jaffa.modules.messaging.services.HeaderParam,
         org.jaffa.persistence.util.UOWHelper,
         java.util.Date,java.net.URLDecoder,
         org.jaffa.soa.services.RaiseEventService,
         org.jaffa.soa.rules.ServiceRulesInterceptor'
         %>


<%!
private static final Logger log = Logger.getLogger("jaffa.sc.systemconfigdesktop.soaEventTest");

/** Convert the input to HTML compatible String. */
private String toHtml(Object obj) {
  return obj == null ? "" : StringHelper.convertToHTML(obj.toString());
}

private void createSoaEvent(String eventName,String description,String soaEventParamJson,int postMessageTimes, JspWriter out){

    UOW uow = null;
    try{

        uow = new UOW();


        uow.addPersistenceLoggingPlugin(0, new ServiceRulesInterceptor("soaeventservice"));
        for(int i=0;i<postMessageTimes;i++){
            
            // Create a SOAEvent
            SOAEvent soaEvent = new SOAEvent();
            soaEvent.setEventName(eventName);
            soaEvent.setDescription(description);
            uow.add(soaEvent);

            if (log.isDebugEnabled()){
                log.debug("Created " + soaEvent);
            }

            // Add Parameters for the SOAEvent
            createSOAEventParameters(uow, soaEvent.getEventId(), URLDecoder.decode(soaEventParamJson, "UTF-8")/* soaEventParamJson */,out);
        
        }
        String jsonStr = "{\"success\":true,\"data\":{\"result\": \""+postMessageTimes+" SOA Event(s) successfully created\"}}";
        out.write(jsonStr);
        uow.commit();

     }catch(Throwable e){
        String jsonStr = "{\"success\":false,\"data\":{\"result\":\"" + e.getMessage().replaceAll("\"", "\'").replaceAll("\\n", "") + "\"}}";
        try{
          out.write(jsonStr);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        e.printStackTrace();
     }finally{
         if(uow!=null){
             try{
               uow.rollback();
             }catch(Throwable e){
                 e.printStackTrace();
             }
         }
     }
}


private void createSOAEventParameters(UOW uow, String eventId, String soaEventParamJson,JspWriter out) throws Throwable {
        
        JSONObject soaEventParam = (JSONObject) JSONSerializer.toJSON(soaEventParamJson);

        Set<Entry> set = soaEventParam.entrySet();
        Iterator<Entry> itr = set.iterator();
        while (itr.hasNext()) {
            Entry entry = itr.next();

            SOAEventParam soaEventParamObj = new SOAEventParam();
            soaEventParamObj.setEventId(eventId);
            soaEventParamObj.setName((String)entry.getKey());
            soaEventParamObj.setValue((String)entry.getValue());
            uow.add(soaEventParamObj);
            if (log.isDebugEnabled()){
                log.debug("Created " + soaEventParamObj);
            }
        }
        
}

private void createSOAEventQueueMessage(String eventName,String description,String category,String soaEventParamJson,int postMessageTimes, JspWriter out){

    try{

        for(int i=0;i<postMessageTimes;i++){
          
            JSONObject soaEventParam = (JSONObject) JSONSerializer.toJSON(soaEventParamJson);

            Set<Entry> set = soaEventParam.entrySet();
            Iterator<Entry> itr = set.iterator();
            List<HeaderParam> paramsList = new ArrayList<HeaderParam>();
            while (itr.hasNext()) {
                Entry entry = itr.next();

                paramsList.add(new HeaderParam((String)entry.getKey(),(String)entry.getValue()));
                
            }
            
            new RaiseEventService().raiseSoaEvent(null, eventName, description, category ,paramsList);

        }
        
        String jsonStr = "{\"success\":true,\"data\":{\"result\": \""+postMessageTimes+" SOA Event(s) successfully created\"}}";
        out.write(jsonStr);

     }catch(Throwable e){
        String jsonStr = "{\"success\":false,\"data\":{\"result\":\"" + e.getMessage().replaceAll("\"", "\'").replaceAll("\\n", "") + "\"}}";
        try{
          out.write(jsonStr);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        e.printStackTrace();
     }
}

%>
<%

    String eventName = (String)request.getParameter("eventName");
    String description = (String)request.getParameter("description");
    String category = ("".equals((String)request.getParameter("category")))? null: (String)request.getParameter("category");
    String soaEventParamJson = (String)request.getParameter("soaEventParamJson");
    int postMessageTimes = Integer.parseInt((String)request.getParameter("postMessageTimes"));
    String submitType = (String)request.getParameter("submitType");
    
    if(submitType.equals("db")){
       createSoaEvent(eventName,description,soaEventParamJson,postMessageTimes,out);
    }else if(submitType.equals("q")){
        createSOAEventQueueMessage(eventName,description,category,soaEventParamJson,postMessageTimes,out);
    }

%>