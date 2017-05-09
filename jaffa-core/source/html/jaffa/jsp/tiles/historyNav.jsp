<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page import="java.util.*" %>
<%@ page import="org.jaffa.presentation.portlet.HistoryNav" %>
<%@ page import="org.jaffa.presentation.portlet.FormKey" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import = "org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>

<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<!-- Start of '/jaffa/jsp/tiles/historyNav.jsp' -->
<tiles:useAttribute name='title' classname='String' ignore='true'/>
<TABLE>
    <TR>
<%
List historyNavList = (List) request.getAttribute(HistoryNav.HISTORY_NAV_PARAMETER);
if (historyNavList != null && historyNavList.size() > 0) {
    for (int i = 0; i < historyNavList.size(); i++) {
        FormKey fk = (FormKey) historyNavList.get(i);
        String fkTitle = fk.getTitle();
        
        // Set the title for the last element in the List with the value passed from tiles
        if ((i + 1) == historyNavList.size() && title != null) {
            fkTitle = title;
            fk.setTitle(fkTitle);
        }
        
        if (fkTitle != null) {
            String labelEditorComponentLink = "";
            
            // Replace the tokens
            if (MessageHelper.hasTokens(fkTitle)) {
                labelEditorComponentLink = TagHelper.getLabelEditorLink(pageContext, fkTitle);
                fkTitle = MessageHelper.replaceTokens(pageContext, fkTitle);
            }
            
            // Display plain text for the last element in the list or if the fk has a null formName
            if ((i + 1) == historyNavList.size() || fk.getFormName() == null) {
%>

            <TD class="historyNav"><%= fkTitle %><%= labelEditorComponentLink %> | </TD>

<%          } else { %>

            <TD class="historyNav"><Portlet:Button classOverride="historyNav" field='<%= "HistoryNav=" + i %>' type='link' label='<%= fkTitle %>' preprocess='false'/><%= labelEditorComponentLink %> | </TD>

<%          }
        }
    }
    
    // Marshal the historyNavList into XML and add it as a hidden field
    String historyNavXml = HistoryNav.encode(historyNavList);
%>

    <INPUT type='hidden' name='<%= HistoryNav.HISTORY_NAV_PARAMETER %>' value='<%= historyNavXml %>'/>

<%
}
%>
    </TR>
</TABLE>
<!-- End of '/jaffa/jsp/tiles/historyNav.jsp' -->

