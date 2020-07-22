<!DOCTYPE html>
<%@
  taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="j"
%><%@
  taglib uri="/WEB-INF/jawr.tld" prefix="jwr"
%>
<%@ page import = "org.jaffa.util.StringHelper" %>
<%@
  page import = "org.jaffa.rules.RulesEngineFactory,org.jaffa.rules.IObjectRuleIntrospector,java.util.Properties,java.util.Map,org.jaffa.components.audit.apis.AuditTransactionViewService,org.jaffa.util.JSONHelper,org.jaffa.util.URLHelper,org.jaffa.util.MessageHelper,org.jaffa.security.SecurityManager"
%>
<%@ page import="org.jaffa.rules.IRulesEngine" %>
<%!
  private static final String COMPONENT = "Jaffa.Audit.AuditTransactionFinder";
  private String n(String s) {return s==null?"":s;}
%><%
  // See if user has access to this component
  if(!org.jaffa.security.SecurityManager.checkComponentAccess(COMPONENT))
    throw new java.security.AccessControlException("No Access To Component " + COMPONENT);

  String rcp=request.getContextPath();
  String pageRef = StringHelper.escapeHtml(request.getRequestURI().substring(rcp.length()+1));
  String reqParams = JSONHelper.requestParams2json(request);


  // Input Paramaters
  // domainObject=com.mirotechnologies.task.core.domain.Task
  // auditFields=[{mapping:'field1', value:'VALUE'},{mapping:'field2', value:'VALUE'},{mapping:'field3', value:'VALUE'}]
  // Since we know object, preload the drop downs in the domain field section.
  String domainObjectLabel = "";
  String domainObjectName = "";
  String auditFieldsList = "{}";
  String domainObjectParam = request.getParameter("domainObject");
  if (domainObjectParam != null){
    IRulesEngine rulesEngine = RulesEngineFactory.getRulesEngine();
    IObjectRuleIntrospector introspector = rulesEngine.getObjectRuleIntrospector(StringHelper.escapeJavascript(domainObjectParam), null);
    if (introspector!=null){
      domainObjectLabel =  MessageHelper.replaceTokens(introspector.getLabel());
      if (introspector.getAuditInfo()!=null)
        domainObjectName = introspector.getAuditInfo().getProperty("name","");
      else{
        String[] nameSegments = domainObjectParam.split("\\.");
        if (nameSegments.length>0)
          domainObjectName = nameSegments[nameSegments.length-1];
        else
          domainObjectName = domainObjectParam;
      }
    }

    Map<String, Properties> mapFields = new AuditTransactionViewService().getAuditableProperties(StringHelper.escapeJavascript(domainObjectParam));
    if (mapFields!=null) 
      auditFieldsList = JSONHelper.map2json(mapFields);
  }
  
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%= MessageHelper.findMessage("title.Jaffa.Audit.AuditTransactionFinder.titleHeader", null)  %></title>
  
    <base href='<%=URLHelper.getBase((HttpServletRequest) pageContext.getRequest())%>'/>
  
    <jwr:style src="/bundles/jaffa/audit.css"/>

    <jsp:include page="/js/extjs/loadExtJS.jsp" flush="true"/>
    <jsp:include page="/js/extjs/loadJaffaRIA.jsp" flush="true">
      <jsp:param name="_pageRef" value="<%=pageRef%>"/>
    </jsp:include>
    <jsp:include page="/js/extjs/loadProduct1.jsp" flush="true"/>
  
    <script type="text/javascript">
      var params = <%= reqParams %>;
      params = params || {};
      params.appCtx = '<%= rcp %>';

      params.auditFieldsList = <%= auditFieldsList %>;
      params.objectLabel = '<%= domainObjectLabel %>';
      params.objectName = '<%= StringHelper.escapeJavascript(domainObjectName) %>';
  
      var security = {
        hasHelpDataDicDetails:   <%= SecurityManager.checkFunctionAccess("Help.DataDic.Details") %>
     };
  
      <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
        <jsp:param name='className' value='org.jaffa.components.audit.apis.data.AuditTransactionGraph'/>
      </jsp:include>
      <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
        <jsp:param name='className' value='org.jaffa.components.audit.apis.data.AuditTransactionObjectGraph'/>
      </jsp:include>
      <jsp:include page="/js/extjs/jaffa/metadata/classMetaData.jsp" flush="true">
        <jsp:param name='className' value='org.jaffa.components.audit.apis.data.AuditTransactionFieldGraph'/>
      </jsp:include>
    </script>

    <script type="text/javascript" src="js/extjs/jaffa/finder/querySaver.jsp?pageRef=<%=pageRef%>"></script>
    <!-- Edit this bundle definition to include more files into this component -->
    <jwr:script src="/bundles/jaffa/audit/audittransactionfinder/auditTransactionFinder.js" useRandomParam="false"/>

  </head>
<body>
</body>
</html>
