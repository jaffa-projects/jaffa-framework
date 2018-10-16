<%--
menu_json.jsp

Return the menu structure for this user in a JSON format
--%><%@
  page contentType="text/plain"
%><%@
  page import="
    java.util.HashMap,
    java.util.Iterator,
    java.util.Map,
    java.util.List,
    org.apache.log4j.Logger,
    org.jaffa.components.navigation.NavAccessor,
    org.jaffa.components.navigation.NavOption,
    org.jaffa.util.ListProperties,
    org.jaffa.util.MessageHelper,
    org.jaffa.util.StringHelper,
    org.jaffa.util.URLHelper"
%><%!
    private static final Logger log = Logger.getLogger("json.menu");

    private String jsonEncode(String s) {
        if(s==null)
            return "null";
        else {
            return "'"+s.replace("'","\\'")+"'";
        }
    }
    private String printSubMenu(PageContext pageContext, List menuNodes, int menuLevel)
    throws java.io.UnsupportedEncodingException {
        StringBuffer out = new StringBuffer();
        menuLevel++;
        boolean hasChildren = false;
        boolean comma=false;
        for(Iterator it = menuNodes.iterator(); it.hasNext(); ) {
            NavOption nOption = (NavOption) it.next();
            if(comma)
                out.append(",");
            else
                comma = true;

            if (nOption.isSubMenu() || nOption.isDesktop()) {
                out.append("{ label: ")
                   .append(jsonEncode(nOption.getLabel()))
                   .append(", menu: [")
                   .append(printSubMenu(pageContext, nOption.getChildren(),menuLevel))
                   .append("]\n}");
            } else {
                out.append("{ label: ")
                   .append(jsonEncode(nOption.getLabel()))
                   .append(",\ntarget : ")
                   .append(jsonEncode(nOption.getTarget()))
                   .append(",\nurl : ")
                   .append(jsonEncode(nOption.getURL()))
                   .append(",\ncomponent : ")
                   .append(jsonEncode(nOption.getComponent()))
                   .append(",\nparams : ")
                   .append(jsonEncode(nOption.getParameters()))
                   .append("\n}");
            }
        }
        return out.toString();
    }
%>

<%-- 
Following  Changes done to get GOLDesp button in PUC
--%>
<%
  NavAccessor nA = NavAccessor.getNavAccessor(request);
  
   boolean scriptTag = false;
 String cb = StringHelper.escapeJavascript(request.getParameter("callback"));
 if (cb != null && cb.trim().length() > 0) {
     scriptTag = true;
     response.setContentType("text/javascript");
 } else {
     response.setContentType("text/plain");
 }
 
 if (scriptTag) {
     response.getWriter().write(cb + "(");
 }
 response.getWriter().print("{ menu : ["+printSubMenu(pageContext, nA.getGlobalNavOptions(),0)+"]}");
 if (scriptTag) {
   response.getWriter().write(");");
 }
%>



