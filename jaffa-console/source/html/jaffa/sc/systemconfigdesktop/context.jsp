<%@ page import='java.io.IOException,
java.io.File,
java.io.InputStream,
java.io.OutputStream,
java.net.URL,
java.net.URLConnection,
java.util.ArrayList,
java.util.Enumeration,
java.util.HashMap,
java.util.Iterator,
java.util.LinkedHashSet,
java.util.List,
java.util.Map,
java.util.Properties,
java.util.Set,
javax.servlet.jsp.JspWriter,
javax.servlet.http.HttpServletRequest,
net.sf.json.JSONObject,
net.sf.json.JSONArray,
org.apache.log4j.Logger,
org.jaffa.datatypes.Currency,
org.jaffa.datatypes.DateOnly,
org.jaffa.datatypes.DateTime,
org.jaffa.datatypes.Parser,
org.jaffa.presentation.portlet.session.UserSession,
org.jaffa.session.ContextManagerFactory,
org.jaffa.util.ListProperties,
org.jaffa.util.MessageHelper,
org.jaffa.util.StringHelper,
org.jaffa.util.URLHelper' %>

<%!
private static final Logger log = Logger.getLogger("jaffa.sc.systemconfigdesktop");

/** Find the rules */
private void showRules(String userId, String variation, JspWriter out, String eventId) throws Exception {
  String prefsFolder;
  //get global rules
  Properties globalProps = new Properties();
  globalProps = getRules("resources/ApplicationRules.global");

  //get variation rules
  Properties varProps = new Properties();
  varProps = getRules("resources/ApplicationRules." + variation);

  prefsFolder = (String)varProps.get("user.preferences.folder");
  if (prefsFolder==null || prefsFolder.equals("")){
    prefsFolder = (String)globalProps.get("user.preferences.folder");
  }

  //get user rules
  Properties userProps = new Properties();
  userProps = getRules(prefsFolder + File.separator + userId + File.separator + "user.properties");

  out.write("businessRules={");

  out.write("globalRules:{");
  writeRules (globalProps, out);
  out.write("},");

  out.write("varRules:{");
  writeRules (varProps, out);
  out.write("},");

  out.write("userRules:{");
  writeRules (userProps, out);
  out.write("}");

  out.write("}");
}

private void writeRules (Properties rules, JspWriter out) throws Exception {
  Enumeration e = rules.propertyNames();
  Boolean isFirst = false;
  while (e.hasMoreElements()) {
    String key = (String) e.nextElement();
    if (isFirst){
      out.write(",");
    } else {
      isFirst = true;
    }
    String value = rules.getProperty(key);
    if (value.equals(""))value="\"\"";
    out.write("'"+key + "':'" + StringHelper.escapeJavascript(value) + "'");
  }
}

private Properties getRules(String location) throws Exception {
  InputStream input = null;
  Properties props = new Properties();
  try {
    input = URLHelper.getInputStream(location);
    if (input != null) {
      props.load(input);
      if (log.isDebugEnabled()) {
        if (props.size() < 1) {
          log.debug("No Rules Defined in file " + location);
        } else {
          log.debug("Loaded " + props.size() + " rule(s) from " + location);
        }
      }
    } else {
      if (log.isInfoEnabled()) {
        log.info("No Rules found. Can't find file " + location);
      }
    }
  } catch (Exception e) {
    // No global rules avilable;
    if (log.isInfoEnabled()) {
      log.info("No Rules Found. Error in loading file " + location, e);
    }
  } finally {
    try {
      if (input != null) {
        input.close();
      }
    } catch (IOException e) {
      if (log.isInfoEnabled()) {
        log.info("Exception thrown while closing the properties file", e);
      }
    }
  }
  return props;
}

private ListProperties getListRules(String location) throws Exception {
  InputStream input = null;
  ListProperties props = new ListProperties();
  try {
    input = URLHelper.getInputStream(location);
    if (input != null) {
      props.load(input);
      if (log.isDebugEnabled()) {
        if (props.size() < 1) {
          log.debug("No Rules Defined in file " + location);
        } else {
          log.debug("Loaded " + props.size() + " rule(s) from " + location);
        }
      }
    } else {
      if (log.isInfoEnabled()) {
        log.info("No Rules found. Can't find file " + location);
      }
    }
  } catch (Exception e) {
    // No global rules avilable;
    if (log.isInfoEnabled()) {
      log.info("No Rules Found. Error in loading file " + location, e);
    }
  } finally {
    try {
      if (input != null) {
        input.close();
      }
    } catch (IOException e) {
      if (log.isInfoEnabled()) {
        log.info("Exception thrown while closing the properties file", e);
      }
    }
  }
  return props;
}

private void setListRules(String location, ListProperties props) throws Exception {
  OutputStream output = null;
  try {

    URL url = URLHelper.getUrl(location);

    URLConnection connection = url.openConnection();
    connection.setDoOutput(true);

    output = connection.getOutputStream();

    if (output != null) {
      props.store(output, null);
    } else {
      if (log.isInfoEnabled()) {
        log.info("No Rules found. Can't find file " + location);
      }
    }
  } catch (Exception e) {
    // No global rules avilable;
    if (log.isInfoEnabled()) {
      log.info("No Rules Found. Error in loading file " + location, e);
    }
  } finally {
    try {
      if (output != null) {
        output.close();
      }
    } catch (IOException e) {
      if (log.isInfoEnabled()) {
        log.info("Exception thrown while closing the properties file", e);
      }
    }
  }
}


private void saveRules(String userId, String variation, String globalRules, String varRules, String userRules, HttpServletRequest request) throws Exception {
  Map<String, Object> globalMap = new HashMap<String, Object>();
  if (!globalRules.equals("null")){
    JSONObject gjsonObj = JSONObject.fromObject(globalRules);
    toJavaMap(gjsonObj, globalMap);
    updateRules ("resources/ApplicationRules.global", globalMap);
  }

  Map<String, Object> varMap = new HashMap<String, Object>();
  if (!varRules.equals("null")){
    JSONObject vjsonObj = JSONObject.fromObject(varRules);
    toJavaMap(vjsonObj, varMap);
    updateRules ("resources/ApplicationRules." + variation, varMap);
  }

  Map<String, Object> userMap = new HashMap<String, Object>();
  if (!userRules.equals("null")){
    JSONObject ujsonObj = JSONObject.fromObject(userRules);
    toJavaMap(ujsonObj, userMap);

    String prefsFolder;
    //get global rules
    Properties globalProps = new Properties();
    globalProps = getRules("resources/ApplicationRules.global");

    //get variation rules
    Properties varProps = new Properties();
    varProps = getRules("resources/ApplicationRules." + variation);

    prefsFolder = (String)varProps.get("user.preferences.folder");
    if (prefsFolder==null || prefsFolder.equals("")){
      prefsFolder = (String)globalProps.get("user.preferences.folder");
    }

    String userLocation = prefsFolder + File.separator + userId + File.separator + "user.properties";

    updateRules (userLocation, userMap);
  }

  ContextManagerFactory.newInstance();
}

private void updateRules(String location, Map<String, Object> rulesMap) throws Exception {
  //get user rules
  ListProperties props = new ListProperties();
  props = getListRules(location);

  Set s=rulesMap.entrySet();
  Iterator it=s.iterator();

  while(it.hasNext())
  {
    // key=value separator this by Map.Entry to get key and value
    Map.Entry m =(Map.Entry)it.next();

    // getKey is used to get key of Map
    String key=(String)m.getKey();

    // getValue is used to get value of key in Map
    String value=(String)m.getValue();
    if (value.equals("")){
      props.remove(key);
    }else{
      if (value.equals("\"\"")){
        value = "";
      }
      props.setProperty(key,value);
    }
  }
  setListRules(location, props);
}

/*** getMap provides a Map representation of the JSON Object
* @param jsonResponse The JSON object string
* @return Map of JSONObject.
**/
protected Map<String, Object> getMap(String jsonResponse ) throws Exception {
  Map<String, Object> mapResponse = new HashMap<String, Object>();
  if (jsonResponse.startsWith("{")) {
    JSONObject jsonObj = JSONObject.fromObject(jsonResponse);
    toJavaMap(jsonObj, mapResponse);
  } else {
    throw new Exception("MalFormed JSON Array Response.");
  }
  return mapResponse;
}

/*** toJavaMap converts the JSONObject into a Java Map
* @param o
* JSONObject to be converted to Java Map
* @param b
* Java Map to hold converted JSONObject response.
**/
private static void toJavaMap(JSONObject o, Map<String, Object> b) {
  Iterator ji = o.keys();
  while (ji.hasNext()) {
    String key = (String) ji.next();
    Object val = o.get(key);
    if (val.getClass() == JSONObject.class) {
      Map<String, Object> sub = new HashMap<String, Object>();
      toJavaMap((JSONObject) val, sub);
      b.put(key, sub);
    } else if (val.getClass() == JSONArray.class) {
      List<Object> l = new ArrayList<Object>();
      JSONArray arr = (JSONArray) val;
      for (int a = 0; a < arr.size(); a++) {
        Map<String, Object> sub = new HashMap<String, Object>();
        Object element = arr.get(a);
        if (element instanceof JSONObject) {
          toJavaMap((JSONObject) element, sub);
          l.add(sub);
        } else {
          l.add(element);
        }
      }
      b.put(key, l);
    } else {
      b.put(key, val);
    }
  }
}

/*** toJavaList converts JSON's array response into Java's List
* @param ar
* JSONArray to be converted to Java List
* @param ll
* Java List to hold the converted JSONArray response
**/
private static void toJavaList(JSONArray ar, List<Object> ll) {
  int i = 0;
  while (i < ar.size()) {
    Object val = ar.get(i);
    if (val.getClass() == JSONObject.class) {
      Map<String, Object> sub = new HashMap<String, Object>();
      toJavaMap((JSONObject) val, sub);
      ll.add(sub);
    } else if (val.getClass() == JSONArray.class) {
      List<Object> l = new ArrayList<Object>();
      JSONArray arr = (JSONArray) val;
      for (int a = 0; a < arr.size(); a++) {
        Map<String, Object> sub = new HashMap<String, Object>();
        Object element = arr.get(a);
        if (element instanceof JSONObject) {
          toJavaMap((JSONObject) element, sub);
          ll.add(sub);
        } else {
          ll.add(element);
        }
      }
      l.add(l);
    } else {
      ll.add(val);
    }
    i++;
  }
}




%><%
if (log.isDebugEnabled())
  log.debug("Generating Context data.");

    UserSession us = UserSession.getUserSession(request);

    String currentUserId = request.getUserPrincipal().getName();
    String currentVariation = us.getVariation();

    String eventId = request.getParameter("eventId");
    String globalRules = request.getParameter("globalRules");
    String varRules = request.getParameter("varRules");
    String userRules = request.getParameter("userRules");
    if ("save".equals(eventId)){
      saveRules(currentUserId, currentVariation, globalRules, varRules, userRules, request);
    }else{
      showRules(currentUserId, currentVariation, out, eventId);
    }
%>
