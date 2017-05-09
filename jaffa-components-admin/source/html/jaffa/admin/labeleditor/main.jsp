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
<%@ page import='org.jaffa.applications.jaffa.modules.admin.components.labeleditor.ui.LabelEditorComponent' %>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/admin/labeleditor/main.jsp' -->
<table>
  <tr>
    <td><Portlet:Label key='label.Jaffa.Admin.LabelEditor.LabelFilter'/>:</td>
    <td><Portlet:EditBox field='labelFilter' columns='100'/></td>
  </tr>
  <tr>
    <td><Portlet:Label key='label.Jaffa.Admin.LabelEditor.DisplayOverridesOnly'/>:</td>
    <td><Portlet:CheckBox field='displayOverridesOnly'/></td>
  </tr>
</table>

<BR>

<Portlet:Grid field='rows'>
  <Portlet:GridColumn label='[label.Jaffa.Admin.LabelEditor.Label]' dataType='CaseInsensitiveString'>
    <Portlet:Text field='<%= LabelEditorComponent.LABEL %>'/>
  </Portlet:GridColumn>
  <Portlet:GridColumn label='[label.Jaffa.Admin.LabelEditor.Default]' dataType='CaseInsensitiveString'>
    <Portlet:Text field='<%= LabelEditorComponent.DEFAULT %>'/>
  </Portlet:GridColumn>
  <Portlet:GridColumn label='[label.Jaffa.Admin.LabelEditor.Override]' dataType='CaseInsensitiveString'>
    <Portlet:EditBox field='<%= LabelEditorComponent.OVERRIDE %>' rows='3' fontsize='8pt'/>
  </Portlet:GridColumn>
  <Portlet:GridColumn label='[label.Jaffa.Admin.LabelEditor.Value]' dataType='CaseInsensitiveString'>
    <%
    // Add the value of the label to the underlying ModelMap, so that it can be rendered correctly by the TextTag
    String labelKey = (String) TagHelper.getModelMap(pageContext).get(LabelEditorComponent.LABEL);
    TagHelper.getModelMap(pageContext).put("value", MessageHelper.findMessage(labelKey, null));
    %>
    <Portlet:Text field='value'/>
  </Portlet:GridColumn>
</Portlet:Grid>

<Portlet:FunctionGuard name="Jaffa.Admin.Labels.Developer">
  <BR>
  <Portlet:FoldingSection id="syncToSource" key="[label.Jaffa.Admin.LabelEditor.SyncToSource]">
    <table>
      <tr>
        <td><Portlet:Label key='label.Jaffa.Admin.LabelEditor.SearchPathForSourceFragments'/>:</td>
        <td><Portlet:EditBox field='searchPathForSourceFragments' columns='100'/></td>
      </tr>
      <tr>
        <td><Portlet:Label key='label.Jaffa.Admin.LabelEditor.SourceFragmentName'/>:</td>
        <td><Portlet:EditBox field='sourceFragmentName' columns='100'/></td>
      </tr>
      <tr><td colspan='2' align='right'><Portlet:Button  field='SyncToSource' type='link' label='[label.Jaffa.Admin.LabelEditor.SyncToSource]'/></td></tr>
    </table>
  </Portlet:FoldingSection>
</Portlet:FunctionGuard>
<!-- End of '/jaffa/admin/labeleditor/main.jsp' -->
