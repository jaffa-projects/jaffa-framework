<!DOCTYPE html>
<%-- Valid request parameters

 @param workType <String> - Defaulted into Criteria Parameters
 @param part <String> - Defaulted into Criteria Parameters
 @param serial <String> - Defaulted into Criteria Parameters

 @param {String} _mode [*esp|nt]
 @param {String} _layout [card|*border|slide]

 @TODO @param {String} _autoSearch [true|*false]
 @TODO @param {String} _namedSearch


--%><%@
  taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="j"
%><%@
  taglib uri="/WEB-INF/jawr.tld" prefix="jwr"
%><%@
  page import = "org.jaffa.util.JSONHelper,org.jaffa.util.URLHelper,org.jaffa.util.MessageHelper,org.jaffa.security.SecurityManager,org.jaffa.util.StringHelper"
%><%!

  private static final String COMPONENT = "Jaffa.Setup.ValidFieldValueMaintenance";

%><%

  // See if user has access to this component


  String rcp=request.getContextPath();
  String pageRef = StringHelper.escapeHtml(request.getRequestURI().substring(rcp.length()+1));
  String widgetStateRef = pageRef; // + '-XYZ';
   String reqParams = JSONHelper.requestParams2json(request);

%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title><%=MessageHelper.findMessage("title."+COMPONENT,null)%></title>
  <base href='<%=URLHelper.getBase((HttpServletRequest) pageContext.getRequest())%>'/>
  
  <jwr:style src="/bundles/usersecurity/user.css"/>
   
  <jsp:include page="/js/extjs/loadExtJS.jsp" flush="true"/>
  <jsp:include page="/js/extjs/loadJaffaRIA.jsp" flush="true">
    <jsp:param name="_pageRef" value="<%=pageRef%>"/>
  </jsp:include>
  <jsp:include page="/js/extjs/loadProduct1.jsp" flush="true"/>
  
  <script type="text/javascript">

    var params = <%= reqParams %> || {};
    params.appCtx = '<%= rcp %>';

    var security = {
  	  hasMaintenance: <%= SecurityManager.checkFunctionAccess("Jaffa.Setup.ValidFieldValue.Maintenance") %>,	
  	  hasHelpDataDicDetails : <%= SecurityManager.checkFunctionAccess("Help.DataDic.Details")%>
    };

    <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
      <jsp:param name="className" value="org.jaffa.modules.setup.apis.data.ValidFieldValueGraph"/>
    </jsp:include>

    Ext.namespace('Jaffa.Setup');
  </script>

  <script type="text/javascript" src="js/extjs/jaffa/finder/querySaver.jsp?pageRef=<%=pageRef%>"></script>
  <jwr:script src='/bundles/jaffa/setup/validfieldvaluefinder.js' />

</head>
<body>
</body>
</html>

