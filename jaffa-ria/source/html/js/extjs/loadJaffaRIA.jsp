<%-- ----------------------------------------------------------------
  -- Include stuff needed for a typical page using JaffaRIA        --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%><%@
page import = "org.apache.log4j.Logger,org.jaffa.session.ContextManagerFactory,org.jaffa.security.SecurityManager,com.mirotechnologies.localization.UserProfileClientWrapper"

%>

<%@

taglib uri="/WEB-INF/jawr-el.tld" prefix="jwr"

%><%! // Add properties/methods to the class

private static Logger log = Logger.getLogger("extjs");

%><%-- -------------------------------------------------------------

Supported Parameters

@param {String} _pageRef
       This is used to get a unique reference to this web2.0 page, it is used
       to load page specific data like Labels, Widget State, Application Rules

@param {String} _identifier
       This is optional and appended to the _pageRef so that the same page can
       load different copies of WidgetState, in the case where that component may be
       used in different "modes" where each "mode" may maintain its own state.

------------------------------------------------------------- --%>

<!--
	This LocalizationRTL class is used for invoking isRTL() which validates whether input language is Arabic or not
-->
<jsp:useBean id='localizationRTL' class='com.mirotechnologies.localization.LocalizationRTL' type='com.mirotechnologies.localization.LocalizationRTL' />

<%
	String userLocale = UserProfileClientWrapper.getInstance().getUserLanguage(request);
	if(userLocale == null) userLocale = "";
	
	/**
	*	Passing the userLocale to isRTL() which returns true if it is arabic language
	*	else return false
	*	i.e., 	Arabic (true) = Loading jaffaRiaRTL.js bundle - which refers in jawr.properties file
	*	   		English (false) = Loading jaffaRia.js bundle - which refers in jawr.properties file
	*/
	if(localizationRTL.isRtlLanguage(userLocale)){
%>
		<!--
			Loading Arabic JS bundle
		-->
		<jwr:script src="/bundles/jaffaRiaRtl.js" useRandomParam='<%=log.isDebugEnabled()?"false":"true"%>'/>
<%	
	}else{
%>
		<!--
			Loading English JS bundle
		-->
		<jwr:script src="/bundles/jaffaRia.js" useRandomParam='<%=log.isDebugEnabled()?"false":"true"%>'/>
<%	
	}
%>

<jwr:script src="/bundles/dwr.js"/>
<script type="text/javascript">
  <jsp:include page="/js/extjs/jaffa/metadata/labels.jsp" flush="true">
    <jsp:param name="ref" value="${param._pageRef}"/>
  </jsp:include>
  <jsp:include page="/js/extjs/jaffa/soa/dwrHandler.jsp" flush="true">
    <jsp:param name="ref" value="${param._pageRef}"/>
  </jsp:include>
  <jsp:include page="/js/extjs/jaffa/metadata/rules.jsp" flush="true">
    <jsp:param name="ref" value="${param._pageRef}"/>
    <jsp:param name="identifier" value="${param._identifier}"/>
  </jsp:include>
  <jsp:include page="/js/extjs/jaffa/state/widgetStateSaver.jsp" flush="true">
    <jsp:param name="pageRef" value='${param._pageRef}${param._identifier != null ? "-" : ""}${param._identifier != null ? param._identifier : ""}'/>
  </jsp:include>
  <jsp:include page="/js/extjs/jaffa/state/heartbeat.jsp" flush="true"/>
</script>

<!-- See if this user should have label editor capability -->
<%if(SecurityManager.checkComponentAccess("Jaffa.Admin.LabelEditor")) {%>
<jwr:script src="/js/extjs/jaffa/metadata/labelEditor.js"/>
<%}%>

<%-- Include Automated Testing Extentions --%>
<%if("true".equalsIgnoreCase((String)ContextManagerFactory.instance().getProperty("extjs.automatedTesting"))) {
%><script type="text/javascript" src="js/extjs/automated-testing.js"></script><%}%>
