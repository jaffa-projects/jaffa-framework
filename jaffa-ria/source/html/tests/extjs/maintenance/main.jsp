<!DOCTYPE html>
<%@ page import = "org.jaffa.security.SecurityManager" %>
<%@ page import = "org.jaffa.util.MessageHelper" %>
<%@ page import = "org.jaffa.util.StringHelper" %>
<%@ page import = "org.jaffa.util.URLHelper" %>

<%!
  private static final String COMPONENT = "WorkRecording.Defaults.DefaultWorkOrderMaintenance";
%>
<%
  // Ensure that the user has access to this component  if (!org.jaffa.security.SecurityManager.checkComponentAccess(COMPONENT))    throw new java.security.AccessControlException("No Access To Component " + COMPONENT);  
  // Determine the context path and pageRef
  String rcp = request.getContextPath();
  String pageRef = request.getRequestURI().substring(rcp.length() + 1);
%>
<html>
  <head>
    <title><%= MessageHelper.findMessage("title." + COMPONENT, null) %></title>
    <base href='<%= URLHelper.getBase((HttpServletRequest) pageContext.getRequest()) %>'/>

    <jsp:include page="/js/extjs/loadExtJS.jsp" flush="true"/>
    <jsp:include page="/js/extjs/loadJaffaRIA.jsp" flush="true">
      <jsp:param name="_pageRef" value="<%=pageRef%>"/>
      <jsp:param name="source" value="true"/>
    </jsp:include>
    
    <!-- Component Specific Files -->
    <link rel="stylesheet" type="text/css" href="js/extjs/resources/css/ext-all.css"/>
    <link rel="stylesheet" type="text/css" href="js/extjs/resources/css/jaffa-ext.css"/>
    <link rel="stylesheet" type="text/css" href="css/product-ext.css"/>
    <link rel="stylesheet" type="text/css" href="css/product-ext-override.css"/>
    <%-- Use the module, submodule and component-specific CSSes as needed
    <link rel="stylesheet" type="text/css" href="tests/css/module-ext.css"/>
    <link rel="stylesheet" type="text/css" href="tests/css/module-ext-override.css"/>
    <link rel="stylesheet" type="text/css" href="tests/extjs/css/submodule-ext.css"/>
    <link rel="stylesheet" type="text/css" href="tests/extjs/css/submodule-ext-override.css"/>
    <link rel="stylesheet" type="text/css" href="tests/extjs/maintenance/css/main-core.css"/>
    <link rel="stylesheet" type="text/css" href="tests/extjs/maintenance/css/main-override.css"/>
    --%>

    <!-- URL Input Parameters -->
    <script type="text/javascript">
      var params = {
        appCtx: '<%=rcp%>'
        ,defaultName: '<%= StringHelper.escapeJavascript(request.getParameter("defaultName")) %>'
      };

      var security = {
        hasHelpDataDicDetails: <%= SecurityManager.checkFunctionAccess("Help.DataDic.Details") %>
      };
    </script>

    <!-- Top level panel and tab management -->
    <script type="text/javascript" src="tests/extjs/maintenance/main.js"></script>

  </head>
</html>
