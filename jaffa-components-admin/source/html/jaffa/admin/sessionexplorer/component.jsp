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
<!-- start : component.jsp -->
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <Portlet:FoldingSection id="general" key="label.Jaffa.Admin.SessionExplorer.Session" closed="false">
      <table align="left">
        <tr>
          <td align="right" class="label"><Portlet:Label key="label.Jaffa.Admin.SessionExplorer.Session.JaffaId"/>:</td>
          <td><Portlet:Text field="id"/></td>
        </tr>
        <tr>
          <td align="right" class="label"><Portlet:Label key="label.Jaffa.Admin.SessionExplorer.Session.SessionId"/>:</td>
          <td><Portlet:Text field="userId"/></td>
        </tr>
        <tr>
          <td align="right" class="label"><Portlet:Label key="label.Jaffa.Admin.SessionExplorer.Session.Host"/>:</td>
          <td><Portlet:Text field="host"/></td>
        </tr>
      </table>
      </Portlet:FoldingSection>
    <td>
  </tr>
  <tr>
    <td>
      <Portlet:FoldingSection id="components" key="label.Jaffa.Admin.SessionExplorer.Components" closed="false">
      <table width="100%" align="left">
        <tr>
          <td>
            <!-- Grid With Session Info -->
            <Portlet:UserGrid field="componentList" userGridId="componentExplorer">
              <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Component.Id]" dataType="CaseInsensitiveString" >
                 <Portlet:Text field="ID"/>
              </Portlet:UserGridColumn>
              <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Component.Name]" dataType="CaseInsensitiveString" >
                 <Portlet:Text field="NAME"/>
              </Portlet:UserGridColumn>
              <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Component.ParentId]" dataType="CaseInsensitiveString" >
                 <Portlet:Text field="PARENT_ID"/>
              </Portlet:UserGridColumn>
              <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Component.Idle]" dataType="CaseInsensitiveString" >
                 <Portlet:Text field="IDLE"/>
              </Portlet:UserGridColumn>
              <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Component.LastAccess]" dataType="CaseInsensitiveString" >
                 <Portlet:Text field="LAST_ACCESS"/>
              </Portlet:UserGridColumn>
              <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Component.TimeOut]" dataType="CaseInsensitiveString" >
                 <Portlet:Text field="TIMEOUT"/>
              </Portlet:UserGridColumn>
              <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]" dataType="CaseInsensitiveString" >
                <Portlet:Button field="Introspect" label="Introspect" image="jaffa/imgs/icons/detail.gif"/>
              </Portlet:UserGridColumn>
            </Portlet:UserGrid>
          </td>
        </tr>
      </table>
      </Portlet:FoldingSection>
    </td>
  </tr>
  <tr>
    <td>
      <Portlet:FoldingSection id="userData" key="label.Jaffa.Admin.SessionExplorer.UserData" closed="false">
      <table width="100%" align="left">
        <tr>
          <td>
            <table align="left">
              <tr>
                <td align="right" class="label"><Portlet:Label key="label.Jaffa.Admin.SessionExplorer.UserData.Class"/>:</td>
                <td><Portlet:Text field="userDataClass"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%" align="left">
	      <tr>
	        <td align="center">
	          <!-- Grid With Session Info -->
	          <Portlet:UserGrid field="userData" userGridId="userData" noRecordsKey="No Data Available">
	            <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Bean.Property]" dataType="CaseInsensitiveString" >
	               <Portlet:Text field="NAME"/>
	            </Portlet:UserGridColumn>
	            <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Bean.Class]" dataType="CaseInsensitiveString" >
	               <Portlet:Text field="CLASS"/>
	            </Portlet:UserGridColumn>
	            <Portlet:UserGridColumn label="[label.Jaffa.Admin.SessionExplorer.Bean.Value]" dataType="CaseInsensitiveString" >
	               <Portlet:Text field="VALUE"/>
	            </Portlet:UserGridColumn>
	          </Portlet:UserGrid>
	        </td>
	      </tr>
	    </table>
          </td>
        </tr>
      </table>
      </Portlet:FoldingSection>
    <td>
  </tr>
</table>
<!-- end : component.jsp -->
