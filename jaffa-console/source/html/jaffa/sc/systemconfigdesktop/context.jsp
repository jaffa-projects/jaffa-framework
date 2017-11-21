<%@ page import='java.io.IOException,
java.io.File,
java.io.InputStream,
java.util.ArrayList,
java.util.Enumeration,
java.util.HashMap,
java.util.Iterator,
java.util.List,
java.util.Map,
java.util.Properties,
javax.servlet.jsp.JspWriter,
net.sf.json.JSONObject,
net.sf.json.JSONArray,
org.apache.log4j.Logger,
org.jaffa.presentation.portlet.session.UserSession,
org.jaffa.security.VariationContext,
org.jaffa.session.ContextManager,
org.jaffa.session.ContextManagerFactory,
org.jaffa.util.StringHelper,
org.jaffa.util.URLHelper' %>
<%@ page import="org.jaffa.loader.config.ApplicationRulesManager" %>

<%!
private static final Logger log = Logger.getLogger("jaffa.sc.systemconfigdesktop");

  /** Find the rules */
  private void showRules(String userId, String variation, JspWriter out, String eventId) throws Exception {
    String prefsFolder;
    Properties globalProps = new Properties();
    Properties varProps = new Properties();
    Properties userProps = new Properties();

    try {
      //Retrieving global rules from the ApplicationRulesManager
      ContextManager contextManager = (ContextManager) ContextManagerFactory.instance();
      ApplicationRulesManager applicationRulesManager = contextManager.getApplicationRulesManager();
      Properties applicationRules = applicationRulesManager.getApplicationRulesGlobal();
      if (applicationRules != null) {
        globalProps = applicationRules;
      }

      //Retrieving variation-specific rules from the ApplicationRulesManager
      applicationRules = applicationRulesManager.getApplicationRulesVariation(VariationContext.getVariation());
      if (applicationRules != null) {
        varProps = applicationRules;
      }

      //Retrieving user-specific rules from the ApplicationRulesManager
      prefsFolder = (String) varProps.get("user.preferences.folder");
      if (prefsFolder == null || prefsFolder.equals("")) {
        prefsFolder = (String) globalProps.get("user.preferences.folder");
      }
      userProps = getRules(prefsFolder + File.separator + userId + File.separator + "user.properties");
    }
    catch (NullPointerException e) {
      log.debug("An exception occurred when trying to retrieve properties. " + e);
    }

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

    showRules(currentUserId, currentVariation, out, eventId);
%>
