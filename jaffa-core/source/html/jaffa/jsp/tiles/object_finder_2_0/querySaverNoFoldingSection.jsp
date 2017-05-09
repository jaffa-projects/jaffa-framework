<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'QuerySaver' of FinderLayout.jsp --%>
<%@ page import='java.util.Map' %>
<%@ page import='java.util.Iterator' %>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ page import='org.jaffa.components.finder.FinderComponent2' %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.presentation.portlet.FormBase" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/jsp/tiles/object_finder_2_0/QuerySaver.jsp' -->

<script>queryTypes={
<%
FormBase fb = TagHelper.getFormBase(pageContext);
Map queries = ((FinderComponent2) fb.getComponent()).retrieveQueryTypes();
if (queries!=null && queries.size() > 0) {
    Iterator it = queries.entrySet().iterator();
    Boolean first = true;
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
%>
<%=first?"":","%>"<%= (String) pairs.getKey() %>":"<%= (String) pairs.getValue() %>"
<%
        first=false;
    }
}
%>
}</script>

<table>
  <tr> 
    <td class="label">
      <Portlet:Label  key='label.Jaffa.Finder.SavedQueries'/>:
    </td>
    <td>
      <Portlet:DropDown field='savedQueryId'/>
      <logic:equal name='<%= TagHelper.getFormName(pageContext) %>' property="hasSavedQueries" value="true">
      <Portlet:Button field='LoadQuery' label='[label.Jaffa.Finder.LoadQuery]' type='link'/>
      <Portlet:Button field='RunQuery' label='[label.Jaffa.Finder.RunQuery]' type='link'/>
      <Portlet:Button field='DeleteQuery' label='[label.Jaffa.Finder.DeleteQuery]' type='link'/>
      </logic:equal>
      <%-- If this is a desktop component this option may be required
      <a href='javascript:resetForm()'><Portlet:label key='label.Jaffa.Finder.ClearQuery'/></a>
      --%>
    </td>
  </tr>
  <tr>
    <td class='label'>
      <Portlet:Label key="label.Jaffa.Finder.DefaultQuery"/>:
    </td>
    <td>
      <table>
        <tr>
          <td class='radiolabel'><Portlet:RadioButton field="defaultQueryYn" selectValue="<%= FinderComponent2.DEFAULT_QUERY_NO  %>"/><span class="radiolabel"><Portlet:Label key="label.Jaffa.Finder.DefaultQueryNo"/></span></td>
          <td class='radiolabel'><Portlet:RadioButton field="defaultQueryYn" selectValue="<%= FinderComponent2.DEFAULT_QUERY_YES %>"/><span class="radiolabel"><Portlet:Label key="label.Jaffa.Finder.DefaultQueryYes"/></span></td>
        </tr>
      </table>
    </td>
  </tr>
  <% 
       //FormBase fb = TagHelper.getFormBase(pageContext);
     if ("Inquiry".equals(fb.getComponent().getComponentDefinition().getComponentType())) {
  %>
  <tr>
    <td class='label'>
      <Portlet:Label key="label.Jaffa.Finder.QueryHasShortCut"/>:
    </td>
    <td>
      <table>
        <tr>
          <td class='radiolabel'><Portlet:RadioButton field="queryHasShortcutYn" selectValue="<%= FinderComponent2.QUERY_HAS_SHORTCUT_NO  %>"/><span class="radiolabel"><Portlet:Label key="label.Jaffa.Finder.QueryHasShortCutNo"/></span></td>
          <td class='radiolabel'><Portlet:RadioButton field="queryHasShortcutYn" selectValue="<%= FinderComponent2.QUERY_HAS_SHORTCUT_YES %>"/><span class="radiolabel"><Portlet:Label key="label.Jaffa.Finder.QueryHasShortCutYes"/></span></td>
        </tr>
      </table>
    </td>
  </tr>
  <% } %>
  <tr> 
    <td class="label">
      <Portlet:Label  key='label.Jaffa.Finder.NewQueryName'/>:
    </td>
    <td>
      <Portlet:EditBox field='newQueryName' />
      <Portlet:Button field='SaveQuery' label='[label.Jaffa.Finder.SaveQuery]' type='link'/>
    </td>
  </tr>
</table>

<!-- End of '/jaffa/jsp/tiles/object_finder_2_0/QuerySaver.jsp' -->


