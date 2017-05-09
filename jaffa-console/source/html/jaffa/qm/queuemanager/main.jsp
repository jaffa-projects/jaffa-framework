<!DOCTYPE html>
<%@ page import = "org.jaffa.security.SecurityManager" %>
<%@ page import = "org.jaffa.util.MessageHelper" %>
<%@ page import = "org.jaffa.util.StringHelper" %>
<%@ page import = "org.jaffa.util.URLHelper" %>
<%@ page import = "org.jaffa.util.JSONHelper" %><%@
  taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="j"
%><%@
  taglib uri="/WEB-INF/jawr.tld" prefix="jwr"
%>

<%!
  private static final String COMPONENT = "Jaffa.QM.QueueManager";
%>

<%
  // Ensure that the user has access to this component
  if (!org.jaffa.security.SecurityManager.checkComponentAccess(COMPONENT))
    throw new java.security.AccessControlException("No Access To Component " + COMPONENT);

  // Determine the context path and pageRef
  String rcp = request.getContextPath();
  String pageRef = request.getRequestURI().substring(rcp.length() + 1);
  String reqParams = JSONHelper.requestParams2json(request);
%>

<html>
  <head>
    <title><%=MessageHelper.findMessage("title.Jaffa.QM.QueueManager.titleHeader", null) %></title>
    <base href='<%= URLHelper.getBase((HttpServletRequest) pageContext.getRequest()) %>'/>

    <jwr:style src="/bundles/jaffa/qm.css"/>

    <jsp:include page="/js/extjs/loadExtJS.jsp" flush="true"/>
    <jsp:include page="/js/extjs/loadJaffaRIA.jsp" flush="true">
      <jsp:param name="_pageRef" value="<%=pageRef%>"/>
    </jsp:include>
    <jsp:include page="/js/extjs/loadProduct1.jsp" flush="true"/>

    <%-- Component Specific Files --%>
    <script type="text/javascript">
      <%-- URL Input Parameters --%>
      var params = <%= reqParams %>;
      params = params || {};
      params.appCtx = '<%=rcp%>';
      params.pageRef = '<%= request.getRequestURI().substring(rcp.length() + 1) %>';
      
      var security = {
        hasHelpDataDicDetails: <%= SecurityManager.checkFunctionAccess("Help.DataDic.Details") %>,
        hasQueueAdmin: <%= SecurityManager.checkFunctionAccess("Jaffa.QM.QueueManager.QueueAdmin") %>
      };
      
      Ext.ns("Jaffa", "Jaffa.QM", "Jaffa.Messaging");

      <%-- Load Specific MetaData --%>
      <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
        <jsp:param name='className' value='org.jaffa.qm.apis.data.QueueGraph'/>
      </jsp:include>
      <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
        <jsp:param name='className' value='org.jaffa.qm.apis.data.MessageGraph'/>
      </jsp:include>
      <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
        <jsp:param name="className" value="org.jaffa.transaction.apis.data.BusinessEventLogGraph"/>
      </jsp:include>
    </script>
    <script type="text/javascript" src="js/extjs/jaffa/finder/querySaver.jsp?pageRef=<%=pageRef%>"></script>

  <!-- Edit this bundle definition to include more files into this component -->
    <jwr:script src='/bundles/jaffa/messaging/businesseventfinder/findercontainer.js'/>
    <jwr:script src="/bundles/jaffa/qm/queuemanager/queueManager.js" useRandomParam="false"/>
  </head>
</html>