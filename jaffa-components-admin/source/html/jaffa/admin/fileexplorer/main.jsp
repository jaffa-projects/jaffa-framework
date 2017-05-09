<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'body' of ViewerLayout.jsp --%>
<%@ page import='org.jaffa.presentation.portlet.widgets.taglib.TagHelper' %>
<%@ page import='org.jaffa.presentation.portlet.widgets.model.GridModelRow' %>
<%@ taglib prefix='j' uri='/WEB-INF/jaffa-portlet.tld'  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Start of '/jaffa/admin/fileexplorer/main.jsp' -->
<c:if test="${!(empty form.returnMessage)}">
<p class='ReturnMessage'><c:out value="${form.returnMessage}"/></p>
</c:if>

<j:Grid field='fileTree' style="width:100%;">
  <j:GridColumn treeField="level" treeSpacer="<img src='jaffa/imgs/tree/spacer.gif' border='0'>"
                label="[label.Jaffa.Admin.FileExplorer.Name]">
    <%GridModelRow r = (GridModelRow) pageContext.findAttribute("row");
      String icon = "";
      if(r.getRowId()==0)
          icon="root";
      else {
        Boolean p = (Boolean)r.get("isParent");
        if(p!=null && p.booleanValue())
          icon="child";
        else
          icon="leaf";
      }%>
          

    <img src='jaffa/imgs/tree/<%=icon%>Node.gif' border='0'> <j:CheckBox field="selected"/> <j:Text field="name"/>
    <c:if test="${!(empty row.error)}">
      &nbsp;<img src='jaffa/imgs/icons/warningIcon.gif' border='0' title='<c:out value="${row.error}"/>'>
      <%r.remove("error");%>
    </c:if>
    
  </j:GridColumn>
  <j:GridColumn  label="[label.Jaffa.Admin.FileExplorer.Size]" style="text-align:right">
    <j:Text field="size"/>
  </j:GridColumn>
  <j:GridColumn  label="[label.Jaffa.Admin.FileExplorer.LastModified]">
    <j:Text field="lastModified"/>
  </j:GridColumn>
  <j:GridColumn label="[label.Jaffa.Widgets.Button.Action]">
    <c:if test="${row.canDownload}">
    <j:Button field="download" label="Download" image="jaffa/imgs/icons/downloadIcon.gif"/>
    </c:if>
  </j:GridColumn>
</j:Grid>

<!-- End of '/jaffa/admin/fileexplorer/main.jsp' -->
