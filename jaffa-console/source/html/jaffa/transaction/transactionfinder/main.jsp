<!DOCTYPE html>
<%-- Valid request parameters
 @param {String} _mode [*esp|nt]
 @param {String} _layout [card|*border|slide]

 @TODO @param {String} _autoSearch [true|*false]
 @TODO @param {String} _namedSearch 
--%><%@
  taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="j"
%><%@
  taglib uri="/WEB-INF/jawr.tld" prefix="jwr"
%>
<%@ page import = "org.jaffa.util.StringHelper" %>
<%@
  page import = "org.jaffa.util.JSONHelper,org.jaffa.util.URLHelper,org.jaffa.util.MessageHelper,org.jaffa.security.SecurityManager"
%>
<%!
  private static final String COMPONENT = "Jaffa.Transaction.TransactionFinder";
  private String n(String s) {return s==null?"":s;}
%><%
  // See if user has access to this component
  if(!org.jaffa.security.SecurityManager.checkComponentAccess(COMPONENT))
    throw new java.security.AccessControlException("No Access To Component " + COMPONENT);

  String rcp=request.getContextPath();
  String pageRef=request.getRequestURI().substring(rcp.length()+1);
  String reqParams = JSONHelper.requestParams2json(request);
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%= MessageHelper.findMessage("title." + COMPONENT, null) %></title>
  
    <base href='<%=URLHelper.getBase((HttpServletRequest) pageContext.getRequest())%>'/>
    <jwr:style src="/bundles/material/material.css"/>
    <jsp:include page="/js/extjs/loadExtJS.jsp" flush="true"/>
    <jsp:include page="/js/extjs/loadJaffaRIA.jsp" flush="true">
      <jsp:param name="_pageRef" value="<%=pageRef%>"/>
    </jsp:include>
    <jsp:include page="/js/extjs/loadProduct1.jsp" flush="true"/>
  
    <script type="text/javascript">
	  params = <%= reqParams %> || {};
	  params.appCtx = '<%= rcp %>';
  
      var security = {
        hasHelpDataDicDetails:   <%= SecurityManager.checkFunctionAccess("Help.DataDic.Details") %>
        ,hasJaffaTransactionMaintenance: <%= SecurityManager.checkFunctionAccess("Jaffa.Transaction.TransactionMaintenance") %>
      };
  
      <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
        <jsp:param name='className' value='org.jaffa.transaction.apis.data.TransactionGraph'/>
      </jsp:include>

      Ext.ns('Jaffa.Transaction');

    </script>

    <script type="text/javascript" src="js/extjs/jaffa/finder/querySaver.jsp?pageRef=<%=pageRef%>"></script>

    <!-- Edit this bundle definition to include more files into this component -->
    <jwr:script src="/bundles/jaffa/transaction/transactionfinder.js"/>

  </head>
<body>
</body>
</html>
