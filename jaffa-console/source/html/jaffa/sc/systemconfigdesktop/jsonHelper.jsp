<%@ page import='java.lang.reflect.Method,
         org.apache.log4j.Logger,
         org.jaffa.util.StringHelper,
         net.sf.json.JSONObject,
         net.sf.json.JSON,
         net.sf.json.JSONSerializer,
         net.sf.json.util.JSONUtils,
         net.sf.json.JSONArray,
         net.sf.json.JsonConfig,
         java.io.IOException,
         net.sf.json.JsonConfig,
         java.util.*,
         java.util.Map.Entry,
         net.sf.json.util.CycleDetectionStrategy'
         %>



<%!
private static final Logger log = Logger.getLogger("jaffa.sc.systemconfigdesktop.soaEventTest");

private void getJsonString(String jsonStr,String eventName,JspWriter out) throws Throwable {
        
        JSONArray jsonArr = (JSONArray) JSONSerializer.toJSON(jsonStr);
        JSONObject json = new JSONObject();
        StringBuilder fieldJson = new StringBuilder("{success: true,data: [");
        for (int i = 0; i < jsonArr.size(); i++) {

            JSONObject jsonObj = (JSONObject) jsonArr.get(i);

            Set<Entry> set = jsonObj.entrySet();
            Iterator<Entry> itr = set.iterator();
            fieldJson.append("{");
            while (itr.hasNext()) {
                Entry entry = itr.next();
                String key = (String)entry.getKey();
                String value = (String)entry.getValue();
                
                if("dataType".equals(key)){
                    if("Integer".equalsIgnoreCase(value) || "Double".equalsIgnoreCase(value)){
                       fieldJson.append("xtype: 'numberfield',");
                    }else if("String".equalsIgnoreCase(value)){
                       fieldJson.append("xtype: 'textfield',");
                    } else {
                        fieldJson.append("xtype: 'textfield',");
                    }
                    fieldJson.append("width: '250',");
                }
                if("description".equals(key)){
                    fieldJson.append("fieldLabel: '"+value+"',");
                }
                if("name".equals(key)){
                    fieldJson.append("id: '"+eventName+value+"'");
                }
            }
            if(i == jsonArr.size()-1)
                fieldJson.append("},{ xtype: 'checkbox', fieldLabel:'_publish', id: '"+eventName+"_publish', boxLabel: 'send to \"topic/OutboundEvents\"'}");
            else
                fieldJson.append("},");
         }
         fieldJson.append("]}");
        
        out.write(fieldJson.toString());
}

%>
<%

    String json = StringHelper.escapeJavascript(request.getParameter("json"));
    String eventName = StringHelper.escapeJavascript(request.getParameter("eventName"));

    getJsonString(json,eventName,out);

%>
