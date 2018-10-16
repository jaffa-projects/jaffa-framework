/**  querySaver.jsp
  Creates savedQuery javascript object to hold the saved query criteria at the inital loading.
  Performs save, delete of queries upon subsequent json requests.
*/
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.io.*,java.net.*,java.util.*" %>
<%@page import = "org.jaffa.util.URLHelper,org.jaffa.util.MessageHelper,org.jaffa.security.SecurityTag, org.jaffa.session.ContextManagerFactory,org.jaffa.persistence.*,org.jaffa.ria.finder.domain.*,org.jaffa.presentation.portlet.component.ComponentDefinition,org.jaffa.presentation.portlet.component.componentdomain.Loader,net.sf.json.*,org.jaffa.util.JAXBHelper,javax.xml.bind.JAXBException,java.nio.charset.Charset" %>
<%@page import = "org.apache.log4j.Logger" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%!
    private static final Logger log = Logger.getLogger("js.extjs.jaffa.querySaver");
    private static final String PROPERTY_USER_ID = "user.id";
    private static final Charset CHARSET = Charset.forName("UTF-8");
%>
<%
    String componentRef = request.getParameter("componentRef");
    String contextRef = request.getParameter("contextRef")!=null?request.getParameter("contextRef"):"";
    String eventId = request.getParameter("eventId");
    Boolean isDefault = Boolean.valueOf(request.getParameter("isDefault"));
    String queryName = request.getParameter("queryName");
    String data = request.getParameter("criteria")!=null?request.getParameter("criteria"):"";
    boolean hasQueryName = queryName != null && queryName.length()>0;
    String queries;
    if ("saveQuery".equals(eventId) && hasQueryName) {
        SavedQuery.saveQuery(componentRef, contextRef, queryName, data, isDefault);
    } else if ("deleteSavedQuery".equals(eventId) && hasQueryName) {
        SavedQuery.deleteQuery(componentRef, contextRef, queryName);
    } else if (componentRef != null && componentRef.length()>0) {
      // this must be the load at the start of the page
        queries = SavedQuery.getSavedQueries(componentRef, contextRef);
%>
  SavedQueries = {
      componentRef: '<%=componentRef%>',
      contextRef: '<%=contextRef%>',
      url: '<%= request.getRequestURI() %>',
      nameQueryPairs: <%=queries.toString()%>,
      version:'2.0'
  };
<%
    }
%>
