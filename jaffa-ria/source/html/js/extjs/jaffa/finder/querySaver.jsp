/**  querySaver.jsp
  Creats savedQuery javascript object to hold the saved query criteria at the inital loading.
  Performs save, delete of queries upon subsequent json requests.
*/
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.io.*,java.net.*,java.util.*" %>
<%@page import = "org.jaffa.util.URLHelper,org.jaffa.util.MessageHelper,org.jaffa.security.SecurityTag, org.jaffa.session.ContextManagerFactory" %>
<%@page import = "org.apache.log4j.Logger" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.security.filter.FileFilter" %>
<%!
    private static final Logger log = Logger.getLogger("js.extjs.jaffa.querySaver");
    private static final String PROPERTY_USER_PREFERENCES_FOLDER = "user.preferences.folder";
    private static final String PROPERTY_USER_ID = "user.id";
    
    private String folder = null;

    private String getQueriesFileName(String pageRef) {
        StringBuffer buf = new StringBuffer();
        buf.append( ContextManagerFactory.instance().getProperty(PROPERTY_USER_PREFERENCES_FOLDER) );
        if (buf.length() > 0 && buf.charAt(buf.length() - 1) != File.separatorChar) {
            buf.append(File.separatorChar);
        }
        String encodedUserId = (String) ContextManagerFactory.instance().getProperty(PROPERTY_USER_ID);
        try {
            encodedUserId = URLEncoder.encode(encodedUserId, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.error("UserId encoding error.", ex);
        }
        buf.append(encodedUserId).append(File.separatorChar).append(pageRef).append(File.separatorChar).append("savedQueries.xml");
        return buf.toString();
    } 

    // Read all the tokens in from the file
    Properties readFile(String pageRef) throws IOException {
        FileInputStream fis = null;
        Properties s = new Properties();
        try {
            File f = new File(getQueriesFileName(pageRef));
            if(f.exists()) {
                fis = new FileInputStream(f);
                s.loadFromXML(fis);
            }
        } finally {
            if ( fis!=null ) fis.close();
        }
        return s;
    } 

    // Write a new token to the end of the file
    void writeFile(String pageRef, Properties data, String comments) throws IOException {
        FileOutputStream fos = null;
        try {
            File f = new File(getQueriesFileName(pageRef));
            if(!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            // Write out the script
            fos = new FileOutputStream(f);
            data.storeToXML(fos, comments);
        } finally {
            if (fos != null) fos.close();
        }
    } 
    
    synchronized void saveQuery(String pageRef, String queryName, String queryData, Boolean isDefault) {
        try {
            Properties p = readFile(pageRef);
            if (queryData==null) {
                p.remove(queryName);
            } else {
                if (isDefault != null && isDefault.booleanValue()) {
                    Enumeration en = p.propertyNames();
                    while(en.hasMoreElements()) {
                        String nm = (String) en.nextElement();
                        String qd = p.getProperty(nm);
                        qd = qd.replace("%22isDefault%22:true", "%22isDefault%22:false");
                        p.setProperty(nm, qd);
                    }
                }
                p.setProperty(queryName, queryData);
            }
            writeFile(pageRef, p, "");
        } catch (IOException ex) {
            log.error("Error: "+ex.toString());
        }
    }
%>
<%
    String pageRef = request.getParameter("pageRef");
    pageRef = FileFilter.filterUserInputPath(pageRef);
    String eventId = request.getParameter("eventId");
    Boolean isDefault = Boolean.valueOf(request.getParameter("isDefault"));
    String queryName = request.getParameter("queryName");
    String data = request.getParameter("jsonData");
    boolean hasQueryName = queryName != null && queryName.length()>0;

    if ("saveQuery".equals(eventId) && hasQueryName) {
        saveQuery(pageRef, queryName, data, isDefault);
    } else if ("deleteSavedQuery".equals(eventId) && hasQueryName) {
        saveQuery(pageRef, queryName, null, null);
    } else if (pageRef != null && pageRef.length()>0) {
      // this must be the load at the start of the page
        Properties p = readFile(pageRef);
        // construct the json object
        StringBuffer json = new StringBuffer();
        if (p.size()>0) {
            // get a sorted array of property names
            String[] names = (String[]) p.keySet().toArray(new String[0]);
            Arrays.sort(names);
            json.append("[");
            boolean isFirst = true;
            for (int i=0; i<names.length; i++) {
                String qobj = p.getProperty(names[i]);
                if (isFirst) {
                    isFirst = false;
                } else {
                    json.append(",");
                }
                json.append("[decodeURI('").append(names[i]).append("'), Ext.decode(decodeURI('").append(qobj).append("'))]");
            }
            json.append("]");
        } else {
            json.append("[]");
        }
%>
  SavedQueries = {
      pageRef: '<%=pageRef%>',
      url: '<%= request.getRequestURI() %>',
      nameQueryPairs: <%=json.toString()%>
  };
<%        
    }
%>
