<%--
  Assigns Jaffa.state.WidgetStateProvider to StateManager.
  
  @todo: Use ReentrantReadWriteLock to create read/write locks per file, or store state in the database.
  This should improve write-performance by negating the need for a synchonized method.
  The reliability of the read-operation will also improve.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.io.BufferedInputStream" %>
<%@page import = "java.io.BufferedOutputStream" %>
<%@page import = "java.io.File" %>
<%@page import = "java.io.FileInputStream" %>
<%@page import = "java.io.FileOutputStream" %>
<%@page import = "java.io.IOException" %>
<%@page import = "java.io.OutputStream" %>
<%@page import = "java.io.UnsupportedEncodingException" %>
<%@page import = "java.net.MalformedURLException" %>
<%@page import = "java.net.URL" %>
<%@page import = "java.net.URLEncoder" %>
<%@page import = "java.util.Properties" %>
<%@page import = "java.util.Enumeration" %>
<%@page import = "org.apache.log4j.Logger" %>
<%@page import = "org.jaffa.session.ContextManagerFactory" %>
<%@page import = "org.jaffa.util.URLHelper" %>
<%!
    private static final Logger log = Logger.getLogger("js.extjs.jaffa.state.widgetStateSaver");
    private static final String APPLICATION_STATE_URL = "classpath:///resources/presentation";
    private static final String PROPERTY_USER_PREFERENCES_FOLDER = "user.preferences.folder";
    private static final String PROPERTY_DEFAULT_STATE_USER_ID = "jaffa.widgets.defaultState.userId";
    private static final String WIDGET_STATES_FILE_NAME = "widgetStates.xml";
    private static final String CHARACTER_ENCODING = "UTF-8";

    /** Loads the user-specific state for the screen into the input Properties instance. */
    private Properties getState(String currentUserId, String pageRef) throws IOException, UnsupportedEncodingException {
        // A container to hold state
        Properties p = new Properties();
        try {
            // Load the state from the deployed application
            loadApplicationState(pageRef, p);
            
            // Load the default user's state
            String defaultUserId = (String) ContextManagerFactory.instance().getProperty(PROPERTY_DEFAULT_STATE_USER_ID);
            if (defaultUserId != null && defaultUserId.length() > 0 && !defaultUserId.equals(currentUserId))
                loadUserState(defaultUserId, pageRef, p);
            
            // Adds "_inherited=true" for each property. This will be an indicator to the UI that the property is inherited and may not be modified/deleted.
            addInheritedFlag(p);
            
            // Load the state for the logged-in user
            loadUserState(currentUserId, pageRef, p);
        } catch (Exception e) {
            log.warn("Error in loading widget state from '" + pageRef + '\'', e);
        }
        if (log.isDebugEnabled())
            log.debug("Loaded state: " + p);
        return p;
    }
    
    private synchronized void writeProperty(String currentUserId, String pageRef, String name, String value) throws IOException, UnsupportedEncodingException {
        // load the state for the logged-in user and modify/delete the named property
        Properties p = new Properties();
        try {
            loadUserState(currentUserId, pageRef, p);
        } catch (Exception e) {
            // The user's widget state file is probably corrupted. Log a warning and continue.
            log.warn("Error in loading widget state for '" + currentUserId + "' from '" + pageRef + '\'', e);
        }
        if (value == null)
            p.remove(name);
        else
            p.setProperty(name, value);
        
        // write the state back to the file
        OutputStream os = null;
        try {
            File f = new File(getWidgetStatesFileName(currentUserId, pageRef));
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            os = new BufferedOutputStream(new FileOutputStream(f));
            p.storeToXML(os, null);
        } catch (Exception e) {
            log.warn("Error in writing widget state for '" + currentUserId + "' to '" + pageRef + '\'', e);
        } finally {
            if (os != null)
                os.close();
        }
    }

    private synchronized void clearProperties(String currentUserId, String pageRef) throws IOException, UnsupportedEncodingException {
        // load the state for the logged-in user and modify/delete the named property
        Properties p = new Properties();
        try {
            loadUserState(currentUserId, pageRef, p);
        } catch (Exception e) {
            // The user's widget state file is probably corrupted. Log a warning and continue.
            log.warn("Error in loading widget state for '" + currentUserId + "' from '" + pageRef + '\'', e);
        }

        Enumeration props = p.propertyNames();
        while (props.hasMoreElements()) {
          String key = (String) props.nextElement();
          p.remove(key);
        }

        // write the state back to the file
        OutputStream os = null;
        try {
            File f = new File(getWidgetStatesFileName(currentUserId, pageRef));
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            os = new BufferedOutputStream(new FileOutputStream(f));
            p.storeToXML(os, null);
        } catch (Exception e) {
            log.warn("Error in writing widget state for '" + currentUserId + "' to '" + pageRef + '\'', e);
        } finally {
            if (os != null)
                os.close();
        }
    }

    /** Loads the deployed application state for the screen into the input Properties instance. */
    private void loadApplicationState(String pageRef, Properties p) throws IOException {
        String resource = new StringBuilder(APPLICATION_STATE_URL).append('/').append(pageRef).append('/').append(WIDGET_STATES_FILE_NAME).toString();
        try {
            URL url = URLHelper.newExtendedURL(resource);
            if (log.isDebugEnabled())
                log.debug("Loading state from " + resource);
            p.loadFromXML(url.openStream());
        } catch (MalformedURLException e) {
            if (log.isDebugEnabled())
              log.debug("Application state file not found: " + resource);
        }
    }

    /** Loads the user-specific state for the screen into the input Properties instance. */
    private void loadUserState(String userId, String pageRef, Properties p) throws IOException, UnsupportedEncodingException {
        String resource = getWidgetStatesFileName(userId, pageRef);
        File f = new File(resource);
        if (f.exists()) {
            if (log.isDebugEnabled())
                log.debug("Loading state from " + resource);
            p.loadFromXML(new BufferedInputStream(new FileInputStream(f)));
        } else {
            if (log.isDebugEnabled())
              log.debug("User-specific state file not found: " + resource);
        }
    }

    /** Generates the fileName that will hold screen state for the input user. */
    private String getWidgetStatesFileName(String userId, String pageRef) throws UnsupportedEncodingException {
        StringBuilder buf = new StringBuilder((String) ContextManagerFactory.instance().getProperty(PROPERTY_USER_PREFERENCES_FOLDER));
        char endingChar = buf.charAt(buf.length() - 1);
        if (endingChar != '/' && endingChar != '\\')
            buf.append(File.separatorChar);
        buf.append(URLEncoder.encode(userId, CHARACTER_ENCODING)).append(File.separatorChar).append(pageRef).append(File.separatorChar).append(WIDGET_STATES_FILE_NAME);
        return buf.toString();
    }

    /**
     * Adds "_inherited=true" for each property. This will be an indicator to the UI that the property is inherited and may not be deleted.
     * This is mainly to avoid unnecessary delete calls to the server for deleting invalid state.
     * The delete is allowed for the logged-in user's state only. The default user's state can be deleted only when logging-in as the default user.
     */
    private void addInheritedFlag(Properties p) throws IOException {
        for (String propertyName : p.stringPropertyNames()) {
            String propertyValue = p.getProperty(propertyName);
            if (propertyValue != null && propertyValue.length() > 0) {
                // Insert '"_inherited:true"' between "{" and "}" characters
                if (propertyValue.startsWith("%7B") && propertyValue.endsWith("%7D")) {
                    int i = "%7B".length(), j = "%7D".length();
                    String valueToInsert = "%22_inherited%22:true" + (propertyValue.length() > (i + j) ? "," : "");
                    propertyValue = propertyValue.substring(0, i) + valueToInsert + propertyValue.substring(i);
                    p.setProperty(propertyName, propertyValue);
                }
            }
        }
    }
%>
<%
    String currentUserId = request.getUserPrincipal().getName();
    String pageRef = request.getParameter("pageRef");
    String eventId = request.getParameter("eventId");
    String name = request.getParameter("name");
    String data = request.getParameter("data");

    if ("save".equals(eventId)) {
        writeProperty(currentUserId, pageRef, name, data);
    } else if ("delete".equals(eventId)) {
        writeProperty(currentUserId, pageRef, name, null);
    } else if ("deleteAll".equals(eventId)){
        clearProperties(currentUserId, pageRef);
    } else if (pageRef != null && pageRef.length()>0) {
        Properties p = getState(currentUserId, pageRef);
        StringBuilder json = new StringBuilder();
        for (String propertyName : p.stringPropertyNames()) {
            String propertyValue = p.getProperty(propertyName);
            if (json.length() > 0)
                json.append(',');
            json.append("['").append(propertyName).append("', Ext.decode(decodeURI('").append(propertyValue).append("'))]");
        }
        json.insert(0, '[');
        json.append("]");
%>
  Ext.state.Manager.setProvider(new Jaffa.state.WidgetStateProvider({
    pageRef: '<%= pageRef %>',
    url: '<%=URLHelper.getBase(request)%>js/extjs/jaffa/state/widgetStateSaver.jsp',
    states: <%=json.toString()%>
  }));
<%
    }
%>
