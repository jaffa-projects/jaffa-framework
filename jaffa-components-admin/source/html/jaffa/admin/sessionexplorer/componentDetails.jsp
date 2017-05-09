<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table width="100%" class="sectionheader">
  <tr>
    <td class="sectiontitle"><Portlet:Label key="label.Jaffa.Admin.SessionExplorer.Component"/></td>
  </tr>
</table>  

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <table align="left">
        <tr>
          <td align="right" class="label"><Portlet:Label key="label.Jaffa.Admin.SessionExplorer.Component.Id"/>:</td>
          <td><Portlet:Text field="id"/></td>
        </tr>
        <tr>
          <td align="right" class="label"><Portlet:Label key="label.Jaffa.Admin.SessionExplorer.Component.Name"/>:</td>
          <td><Portlet:Text field="name"/></td>
        </tr>
      </table>
    <td>
  </tr>
  <tr>
    <td>
      <Portlet:FoldingSection id="components" key="label.Jaffa.Admin.SessionExplorer.Component.Properties" closed="false">
      <table width="100%" align="left">
        <tr>
          <td>
            <!-- Grid With Session Info -->
            <Portlet:UserGrid field="componentList" userGridId="componentExplorerProperties">
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
      </Portlet:FoldingSection>
    <td>
  </tr>
</table>
