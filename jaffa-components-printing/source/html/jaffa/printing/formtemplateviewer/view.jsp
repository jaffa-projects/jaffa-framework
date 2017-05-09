<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- //JAFFA-OVERWRITE: As long as this line exists, this file will be overwritten in all future runs of the pattern generator --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.modules.printing.components.formtemplateviewer.ui.FormTemplateViewerForm" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table>
  <Portlet:Property field='formId'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.FormId]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='templateData'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormTemplate.TemplateData]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='layoutData'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormTemplate.LayoutData]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
</table>


