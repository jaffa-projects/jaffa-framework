<!DOCTYPE html>
<%-- Valid request parameters

 @param workType <String> - Defaulted into Criteria Parameters
 @param part <String> - Defaulted into Criteria Parameters
 @param serial <String> - Defaulted into Criteria Parameters

 @param {String} _mode [*esp|nt]
 @param {String} _layout [card|*border|slide]

 @TODO @param {String} _autoSearch [true|*false]
 @TODO @param {String} _namedSearch


--%><%@ page import = "org.jaffa.security.SecurityManager" %>
<%@ page import = "org.jaffa.util.MessageHelper" %>
<%@ page import = "org.jaffa.util.StringHelper" %>
<%@ page import = "org.jaffa.util.URLHelper" %><%@
  taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="j"
%><%@
  taglib uri="/WEB-INF/jawr.tld" prefix="jwr"
%>

<%! 
private static final String COMPONENT = "Jaffa.Transaction.TransactionMaintenance";
%>
<%
//See if user has access to this component
if (!org.jaffa.security.SecurityManager.checkComponentAccess(COMPONENT)){
    throw new java.security.AccessControlException("No Access To Component " + COMPONENT);
}

String rcp = request.getContextPath();
String pageRef = request.getRequestURI().substring(rcp.length()+1);

%>
 
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= MessageHelper.findMessage("title.Jaffa.SC.SystemConfigDesktop", null) %></title>
  <base href='<%=URLHelper.getBase((HttpServletRequest) pageContext.getRequest())%>'/>
  <jwr:style src="/bundles/task/setup.css"/>
  <jsp:include page="/js/extjs/loadExtJS.jsp" flush="true"/>
  <jsp:include page="/js/extjs/loadJaffaRIA.jsp" flush="true">
    <jsp:param name="_pageRef" value="<%=pageRef%>"/>
  </jsp:include>
  <jsp:include page="/js/extjs/loadProduct1.jsp" flush="true"/>
  <script type="text/javascript">
    Ext.namespace('Jaffa', 'Jaffa.Transaction', 'Jaffa.Messaging');
  </script>
  <script type="text/javascript">


    var params = {
      appCtx: '<%=rcp%>',
      currentUser: '<%=StringHelper.escapeJavascript(SecurityManager.getPrincipal().getName())%>',
      id: '<%=StringHelper.escapeJavascript(request.getParameter("id")) %>',
      _update: <%= (request.getParameter("_update") != null) ? true : false %>
    };

    var security = {
      hasHelpDataDicDetails: <%= SecurityManager.checkFunctionAccess("Help.DataDic.Details") %>
    };

    <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
      <jsp:param name="className" value="org.jaffa.transaction.apis.data.TransactionGraph"/>
    </jsp:include>
    
    <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
      <jsp:param name="className" value="org.jaffa.transaction.apis.data.TransactionFieldGraph"/>
    </jsp:include>
    
    <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
      <jsp:param name="className" value="org.jaffa.transaction.apis.data.TransactionDependencyGraph"/>
    </jsp:include>
    
    <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
      <jsp:param name="className" value="org.jaffa.transaction.apis.data.TransactionPayloadGraph"/>
    </jsp:include>
    
    <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
      <jsp:param name="className" value="org.jaffa.transaction.apis.data.BusinessEventLogGraph"/>
    </jsp:include>
 
    <!-- lookup components -->
 
  </script>

  <script type="text/javascript" src="js/extjs/jaffa/finder/querySaver.jsp?pageRef=<%=pageRef%>"></script>

  
  <jwr:script src='/bundles/jaffa/messaging/businesseventfinder/findercontainer.js'/>
  <jwr:script src='/bundles/jaffa/transaction/transactionmaintenance.js' useRandomParam="false"/>

</head>

<body>

</body>
</html>
