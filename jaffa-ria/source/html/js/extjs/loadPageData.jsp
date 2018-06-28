<%-- ----------------------------------------------------------------
  -- Include stuff needed for a typical page using JaffaRIA        --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%><%@

page import = "org.apache.log4j.Logger"

%>
<%@ page import="org.jaffa.util.StringHelper" %>
<%! // Add properties/methods to the class

private static Logger log = Logger.getLogger("extjs");

%><% // Add inline properties
/* Supported Parameters
 * @param {String} _pageRef
 *        This is used to get a unique reference to this web2.0 page, it is used
 *        to load page specific data like Labels, Widget State, Application Rules
 *
 * @param {String} _indetifier
 *        This is optional and appended to the _pageRef so that the same page can
 *        load different copies of WidgetState, in the case where that component may be
 *        used in different "modes" where each "mode" may maintain its own state.
 */
String pageRef = StringHelper.escapeJavascript(request.getParameter("_pageRef"));
String identifier = StringHelper.escapeJavascript(request.getParameter("_identifier"));
String widgetStateSaver;
if (identifier == null || identifier.length() == 0 ) {
    widgetStateSaver = pageRef;
} else {
    widgetStateSaver = pageRef + '-' + identifier;
}
%><%-- ----------------------------------------------------------------
    -- Include All JSPs that load page specific state                --
    -- ------------------------------------------------------------- --%>
<jsp:include page="/js/extjs/jaffa/soa/dwrHandler.jsp" flush="true">
  <jsp:param name="ref" value="<%=pageRef%>"/>
</jsp:include>
<jsp:include page="/js/extjs/jaffa/metadata/rules.jsp" flush="true">
  <jsp:param name="ref" value="<%=pageRef%>"/>
</jsp:include>
<jsp:include page="/js/extjs/jaffa/state/widgetStateSaver.jsp" flush="true">
  <jsp:param name="pageRef" value="<%=widgetStateSaver%>"/>
</jsp:include>
<jsp:include page="/js/extjs/jaffa/state/heartbeat.jsp" flush="true"/>
