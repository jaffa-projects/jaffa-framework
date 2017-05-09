<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'globalNav' of MainLayout.jsp --%>
<%@ page import='java.util.*,org.jaffa.util.MessageHelper,org.jaffa.components.navigation.*' %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%!
    private String getLabelEditorLink(PageContext pageContext, NavOption navOption) {
        return navOption.getToken() != null ? TagHelper.getLabelEditorLink(pageContext, navOption.getToken()) : "";
    }
    
    private void printSubMenu(PageContext pageContext, List menuNodes , String id, StringBuffer out) {
        boolean hasChildren = false;
        StringBuffer out2 = new StringBuffer();
        out2.append("  <div id=\"" + id + "\" class=\"navMenu\">\n");
        for(Iterator it = menuNodes.iterator(); it.hasNext(); ) {
            NavOption nOption = (NavOption) it.next();
            if(nOption.isSubMenu()) {
                hasChildren = true;
                List m = nOption.getChildren();
                if (m != null)
                    printSubMenu(pageContext, m , nOption.getLabel(),out);
            }
            String labelEditorLink = getLabelEditorLink(pageContext, nOption);
            if (hasChildren) {
                // The labelEditorLink would look much better inside a <table> tag, but the submenu is only partially visible due to CSS issues.
                // So for now, we'll just append the labelEditorLink.
                out2.append("    <a class=\"navMenuItem\" href=\"javascript:void(0)\" onMouseOver=\"displaySubMenu(event,'");
                out2.append(nOption.getLabel());
                out2.append("');\"><span class=\"navMenuText\">");
                out2.append(nOption.getLabel());
                out2.append("</span><span class=\"navMenuMore\">&nbsp;</span></a>");
                out2.append(labelEditorLink);
                out2.append('\n');
            } else if (nOption.isDesktop()) {
                if (labelEditorLink.length() > 0)
                    out2.append("<table><tr><td>");
                out2.append("    <a class=\"navMenuItem\" href=\"jaffa/jsp/tiles/desktop.jsp?desktopId=");
                out2.append(nOption.getDesktopId());
                
                /* @todo
                if(nOption.getStrutsTileTemplate()!=null) {
                    out2.append("&strutsTilesTemplate=");
                    out2.append(nOption.getStrutsTileTemplate());
                }
                out2.append("&desktopName=");
                out2.append(nOption.getLabel());
                */
                
                out2.append("\"><span class=\"navMenuText\">");
                out2.append(nOption.getLabel());
                out2.append("</span></a>");
                if (labelEditorLink.length() > 0)
                    out2.append("</td><td>" + labelEditorLink + "</td></tr></table>");
                out2.append('\n');
            } else {
                if (labelEditorLink.length() > 0)
                    out2.append("<table><tr><td>");
                out2.append("    <a class=\"navMenuItem\" href=\"");
                if(nOption.isURL()) {
                    if (nOption.getURL().toLowerCase().startsWith("javascript"))
                        out2.append("javascript:void(0);\" onclick=\"");
                    out2.append(nOption.getURL());
                } else {
                    out2.append("startComponent.do?component=");
                    out2.append(nOption.getComponent());
                    if(nOption.getParameters() != null) {
                        out2.append("&");
                        out2.append(nOption.getParameters());
                    }
                }
                if (nOption.getTarget() != null && nOption.getTarget().length() > 0)
                    out2.append("\" target=\"").append(nOption.getTarget());
                out2.append("\"><span class=\"navMenuText\">");
                out2.append(nOption.getLabel());
                out2.append("</span></a>");
                if (labelEditorLink.length() > 0)
                    out2.append("</td><td>" + labelEditorLink + "</td></tr></table>");
                out2.append('\n');
            }
        }
        out2.append("  </div>\n");
        out.append(out2.toString());
    }
%>
  <!-- Start of '/jaffa/jsp/tiles/globalNav.jsp' -->
  <SCRIPT type='text/javascript' src='jaffa/js/tiles/navigation.js'></SCRIPT>
  <style id="jaffaDropDown">select.WidgetDropDown {visibility : hidden;}</style>
  <script>document.getElementById("jaffaDropDown").disabled = true;</script>
  <div class="navBar" style="width:100%" align="left">
<%  
  NavAccessor nA = NavAccessor.getNavAccessor(request);
  List l = nA.getGlobalNavOptions();
  for(Iterator it = l.iterator(); it.hasNext(); ) {
    NavOption nOption = (NavOption) it.next();
    if(nOption.isSubMenu()) {
%>    <a class="navBarItem" href="javascript:void(0)" onMouseOver="displayMenu(event,'<%=nOption.getLabel()%>')"><%=nOption.getLabel()%></a><%=getLabelEditorLink(pageContext, nOption)%>
<%  } else if (nOption.isDesktop()) {
%>    <a class="navBarItem" href="jaffa/jsp/tiles/desktop.jsp?desktopId=<%=nOption.getDesktopId()%><%--<%=(nOption.getStrutsTileTemplate()==null?"":"&strutsTilesTemplate="+nOption.getStrutsTileTemplate())%>&desktopName=<%=nOption.getLabel()%>--%>" onMouseOver="displayMenu(event,null)"><%=nOption.getLabel()%></a><%=getLabelEditorLink(pageContext, nOption)%>
<%  } else {
      String targetAttribute = nOption.getTarget() != null && nOption.getTarget().length() > 0 ? "target=\"" + nOption.getTarget() + "\"" : "";
      if(nOption.isURL() && (nOption.getURL().toLowerCase().startsWith("javascript")) ) {
%>    <a class="navBarItem" href="javascript:void(0);" <%= targetAttribute %> onclick="<%=nOption.getURL()%>" onMouseOver="displayMenu(event,null)"><%=nOption.getLabel()%></a><%=getLabelEditorLink(pageContext, nOption)%>
<%    } else {
%>    <a class="navBarItem" href="<%=(nOption.isURL()?nOption.getURL():"startComponent.do?component="+nOption.getComponent()+(nOption.getParameters()!=null?"&"+nOption.getParameters():""))%>" <%= targetAttribute %> onMouseOver="displayMenu(event,null)"><%=nOption.getLabel()%></a><%=getLabelEditorLink(pageContext, nOption)%>
<%    }
    }
  }
%>
  </div>
<%
  StringBuffer output = new StringBuffer();
  for(Iterator it = l.iterator(); it.hasNext(); ) {
    NavOption nOption = (NavOption) it.next();
    if (nOption.isSubMenu())
      printSubMenu(pageContext, nOption.getChildren(),nOption.getLabel(),output);
  }
%>
<div id="globalNavMenu">
  <%=output.toString()%>
</div>
  <!-- End of '/jaffa/jsp/tiles/globalNav.jsp' -->
