<%@ page import = "org.jaffa.security.SecurityManager" %>
<%@ page import = "org.jaffa.util.MessageHelper" %>
<%@ page import = "org.jaffa.util.StringHelper" %>
<%@ page import = "org.jaffa.util.URLHelper" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="j"%>
<%@  taglib uri="/WEB-INF/jawr.tld" prefix="jwr"%>

<%!
  private static final String COMPONENT = "Jaffa.SC.SystemConfigDesktop";
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
        <title><%= MessageHelper.findMessage("title.Jaffa.SC.SystemConfigDesktop", null) %></title>
        <base href='<%= URLHelper.getBase((HttpServletRequest) pageContext.getRequest()) %>'/>

        <jwr:style src="/bundles/jaffa/sc.css"/>

        <jsp:include page="/js/extjs/loadExtJS.jsp" flush="true"/>
        <jsp:include page="/js/extjs/loadJaffaRIA.jsp" flush="true">
            <jsp:param name="_pageRef" value="<%=pageRef%>"/>
        </jsp:include>
        <jsp:include page="/js/extjs/loadProduct1.jsp" flush="true"/>

        <script type="text/javascript">
            <%-- URL Input Parameters --%>
                var params = {
                    appCtx: '<%=rcp%>',
                    pageRef: '<%= StringHelper.escapeJavascript(request.getRequestURI().substring(rcp.length() + 1)) %>',
                    showRelationShip: true,
                    docType:'DO_DOC',
                    topicName: 'OutboundEvents',
                    env: '<%= StringHelper.escapeJavascript(request.getParameter("env")) %>'
                };

                var security = {
                    hasHelpDataDicDetails: <%= SecurityManager.checkFunctionAccess("Help.DataDic.Details") %>,
                    hasSystemConfigInquiry: <%= SecurityManager.checkFunctionAccess("Jaffa.SC.SystemConfig.Inquiry") %>,
                    hasRuleInquiry: <%= SecurityManager.checkFunctionAccess("Jaffa.SC.SystemConfig.RuleInquiry") %>,
                    hasRuleMaintenance: <%= SecurityManager.checkFunctionAccess("Jaffa.SC.SystemConfig.RuleAdmin") %>,
                    hasFlexFieldInquiry: <%= SecurityManager.checkFunctionAccess("Jaffa.SC.SystemConfig.FlexFieldInquiry") %>,
                    hasSoaEventInquiry: <%= SecurityManager.checkFunctionAccess("Jaffa.SC.SystemConfig.SoaInquiry") %>,
                    hasDomainInquiry: <%= SecurityManager.checkFunctionAccess("Jaffa.SC.SystemConfig.DomainInquiry") %>,
                    hasQueueAdmin: <%= SecurityManager.checkFunctionAccess("Jaffa.QM.QueueManager.QueueAdmin") %>,
                    hasQueueInquiry: <%= SecurityManager.checkFunctionAccess("Jaffa.QM.QueueManager.Inquiry") %>,
                    hasSchedulerAccess: <%= SecurityManager.checkFunctionAccess("Jaffa.Scheduler.Task.Maintenance") %>
                };

                RuleMetaData = {};

                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.label"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.case-type"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.data-type"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.flex-field"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.mandatory"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.hidden"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.min-length"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.max-length"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.min-value"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.max-value"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.pattern"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.in-list"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                   <jsp:param name="className" value="org.jaffa.rules.meta.property-info"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                    <jsp:param name="className" value="org.jaffa.rules.meta.partial-foreign-key"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                    <jsp:param name="className" value="org.jaffa.rules.meta.foreign-key"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.generic-foreign-key"/>
                </jsp:include>
                <jsp:include page="/js/extjs/jaffa/metadata/ruleMetaData.jsp" flush="true">
                  <jsp:param name="className" value="org.jaffa.rules.meta.audit"/>
                </jsp:include>

                Ext.ns("Jaffa", "Jaffa.SC");
                Ext.BLANK_IMAGE_URL = "<%=rcp%>/js/extjs/resources/images/default/s.gif";

        </script>
        <jwr:script src='/bundles/jaffa/sc/systemconfigdesktop/main.js' useRandomParam="false"/>
    </head>
</html>
