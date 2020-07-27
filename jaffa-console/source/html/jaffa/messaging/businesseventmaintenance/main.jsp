<!DOCTYPE html>
<%-- VallogId request parameters

 @param workType <String> - Defaulted into Criteria Parameters
 @param part <String> - Defaulted into Criteria Parameters
 @param serial <String> - Defaulted into Criteria Parameters

 @param {String} _mode [*esp|nt]
 @param {String} _layout [card|*border|sllogIde]

 @TODO @param {String} _autoSearch [true|*false]
 @TODO @param {String} _namedSearch


--%><%@ page import = "org.jaffa.security.SecurityManager" %>
<%@ page import = "org.jaffa.util.MessageHelper" %>
<%@ page import = "org.jaffa.util.StringHelper" %>
<%@ page import = "org.jaffa.util.URLHelper" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="j" %>
<%@ taglib uri="/WEB-INF/jawr.tld" prefix="jwr" %>

<%! private static final String COMPONENT = "Jaffa.Messaging.BusinessEventLogViewer"; %>

<%
//See if user has access to this component
if (!org.jaffa.security.SecurityManager.checkComponentAccess(COMPONENT)){
    throw new java.security.AccessControlException("No Access To Component " + COMPONENT);
}

String rcp = request.getContextPath();
String pageRef = StringHelper.escapeHtml(request.getRequestURI().substring(rcp.length()+1));

%>
 
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title><%= MessageHelper.findMessage("title.Jaffa.Messaging.BusinessEventMaintenance.titleHeader", null)  %></title>
  <base href='<%=URLHelper.getBase((HttpServletRequest) pageContext.getRequest())%>'/>
  <jwr:style src="/bundles/jaffa/messaging.css"/>
  <jsp:include page="/js/extjs/loadExtJS.jsp" flush="true"/>
  <jsp:include page="/js/extjs/loadJaffaRIA.jsp" flush="true">
    <jsp:param name="_pageRef" value="<%=pageRef%>"/>
  </jsp:include>
  <jsp:include page="/js/extjs/loadProduct1.jsp" flush="true"/>
  
  <script type="text/javascript">

    var params = {
      appCtx: '<%=rcp%>',
      currentUser:'<%=StringHelper.escapeJavascript(SecurityManager.getPrincipal().getName())%>',
      logId:'<%=StringHelper.escapeJavascript(request.getParameter("logId")) %>'
    };

    var security = {
      hasHelpDataDicDetails: <%= SecurityManager.checkFunctionAccess("Help.DataDic.Details") %>,
      hasAttachmentInquiry : <%= SecurityManager.checkFunctionAccess("Attachment.User.Inquiry")%>,
      hasAttachmentMaintenance : false // these attachments can only be added by system events
    };

    <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
      <jsp:param name="className" value="org.jaffa.transaction.apis.data.BusinessEventLogGraph"/>
    </jsp:include>

    <!-- lookup components -->
 
  </script>

  <script type="text/javascript" src="js/extjs/jaffa/finder/querySaver.jsp?pageRef=<%=pageRef%>"></script>
  
  <script type="text/javascript">
    Ext.namespace('Jaffa.Messaging');
  </script>

  <jwr:script src="/bundles/jaffa/messaging/businesseventmaintenance.js" />

</head>

<body>

</body>
</html>
