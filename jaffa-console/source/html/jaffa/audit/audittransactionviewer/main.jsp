<!DOCTYPE html>
<%@ page import = "org.jaffa.security.SecurityManager" %>
<%@ page import = "org.jaffa.util.MessageHelper" %>
<%@ page import = "org.jaffa.util.StringHelper" %>
<%@ page import = "org.jaffa.util.URLHelper" %><%@
  taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="j"
%><%@
  taglib uri="/WEB-INF/jawr.tld" prefix="jwr"
%>

<%!
  private static final String COMPONENT = "Jaffa.Audit.AuditTransactionViewer";
%>

<%
  // Ensure that the user has access to this component
  if (!org.jaffa.security.SecurityManager.checkComponentAccess(COMPONENT))
    throw new java.security.AccessControlException("No Access To Component " + COMPONENT);

  // Determine the context path and pageRef
  String rcp = request.getContextPath();
  String pageRef = request.getRequestURI().substring(rcp.length() + 1);
%>

<html>
  <head>
    <title><%= MessageHelper.findMessage("title.Jaffa.Audit.AuditTransactionViewer.titleHeader", null)  %></title>
    <base href='<%= URLHelper.getBase((HttpServletRequest) pageContext.getRequest()) %>'/>

    <jwr:style src="/bundles/jaffa/audit.css"/>

    <jsp:include page="/js/extjs/loadExtJS.jsp" flush="true"/>
    <jsp:include page="/js/extjs/loadJaffaRIA.jsp" flush="true">
      <jsp:param name="_pageRef" value="<%=pageRef%>"/>
    </jsp:include>
    <jsp:include page="/js/extjs/loadProduct1.jsp" flush="true"/>

    <%-- Component Specific Files --%>
    <script type="text/javascript">
      <%-- URL Input Parameters --%>
      var params = {
        appCtx: '<%=rcp%>',
        transactionId: '<%= StringHelper.escapeJavascript(request.getParameter("transactionId")) %>',
        pageRef: '<%= StringHelper.escapeJavascript(request.getRequestURI().substring(rcp.length() + 1)) %>'
      };

      var security = {
        hasHelpDataDicDetails: <%= SecurityManager.checkFunctionAccess("Help.DataDic.Details") %>
      };

      <%-- Load Specific MetaData --%>
      <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
        <jsp:param name='className' value='org.jaffa.components.audit.apis.data.AuditTransactionGraph'/>
      </jsp:include>
    </script>

  <!-- Edit this bundle definition to include more files into this component -->
    <jwr:script src="/bundles/jaffa/audit/audittransactionviewer/auditTransactionViewer.js" useRandomParam="false"/>
  </head>
</html>