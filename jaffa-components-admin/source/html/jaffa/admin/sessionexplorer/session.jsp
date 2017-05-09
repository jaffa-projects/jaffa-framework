<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table width="100%" class="sectionheader">
  <tr>
    <td class="sectiontitle" ><Portlet:Label key="label.Jaffa.Admin.SessionExplorer.Sessions"/></td>
  </tr>
</table>  

<!-- Grid With Session Info -->
<Portlet:UserGrid field="sessions" userGridId="sessionExplorer">

  <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Id]" dataType="CaseInsensitiveString" >
     <Portlet:Text field="JAFFA_ID"/>
  </Portlet:UserGridColumn>
  
  <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Session.UserId]" dataType="CaseInsensitiveString" >
     <Portlet:Text field="USER_ID"/>
  </Portlet:UserGridColumn>
  
  <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Session.Host]" dataType="CaseInsensitiveString" >
     <Portlet:Text field="HOST"/>
  </Portlet:UserGridColumn>
  
  <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Session.Idle]" dataType="CaseInsensitiveString" >
     <Portlet:Text field="IDLE"/>
  </Portlet:UserGridColumn>
  
  <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Session.ComponentCount]" dataType="CaseInsensitiveString" >
     <Portlet:Text field="COMPONENT_COUNT"/>
  </Portlet:UserGridColumn>
  
  <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Session.SessionId]" dataType="CaseInsensitiveString" >
     <Portlet:Text field="HTTP_ID"/>
  </Portlet:UserGridColumn>
  
  <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Session.Created]" dataType="CaseInsensitiveString" >
     <Portlet:Text field="CREATED"/>
  </Portlet:UserGridColumn>
  
  <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Session.LastAccess]" dataType="CaseInsensitiveString" >
     <Portlet:Text field="LAST_ACCESS"/>
  </Portlet:UserGridColumn>
  
  <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Session.TimeOut]" dataType="CaseInsensitiveString" >
     <Portlet:Text field="TIMEOUT"/>
  </Portlet:UserGridColumn>
  
  <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]" dataType="CaseInsensitiveString" >
    <Portlet:Button field="View" label="[label.Jaffa.Widgets.Button.View]" image="jaffa/imgs/icons/detail.gif"/>
  </Portlet:UserGridColumn>
  
</Portlet:UserGrid>
