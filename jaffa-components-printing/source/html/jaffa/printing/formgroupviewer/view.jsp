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
<%@ page import="org.jaffa.modules.printing.components.formgroupviewer.ui.FormGroupViewerForm" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table>
  <Portlet:Property field='formName'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormGroup.FormName]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='description'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormGroup.Description]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='formType'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormDefinition.FormType]"/>:
      </td>
      <td>
        <Portlet:Label key='<%="label.Jaffa.Printing.FormGroup.FormType."+((FormGroupViewerForm)TagHelper.getFormBase(pageContext)).getFormType()%>'/>
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
</table>

  <BR>
  <BR>

  <Portlet:FoldingSection id="printing_formGroupViewer_formUsage" label="<%=org.jaffa.util.MessageHelper.replaceTokens(pageContext,org.jaffa.modules.printing.domain.FormUsageMeta.getLabelToken())%>">
    <Portlet:UserGrid field="relatedFormUsage" userGridId="jaffa_printing_formGroupViewer_formUsage">
        <Portlet:Property field='eventName'>
          <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormEvent.EventName]' key='true'>
            <Portlet:Text />
          </Portlet:UserGridColumn>
        </Portlet:Property>
        <Portlet:Property field='formAlternate'>
          <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormDefinition.FormAlternate]'>
            <Portlet:Text />
          </Portlet:UserGridColumn>
        </Portlet:Property>
        <Portlet:Property field='copies'>
          <Portlet:UserGridColumn dataType="Number" label='[label.Jaffa.Printing.FormUsage.Copies]'>
            <Portlet:Text />
          </Portlet:UserGridColumn>
        </Portlet:Property>
        <Portlet:Property field='description'>
          <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormEvent.Description]'>
            <Portlet:Text />
          </Portlet:UserGridColumn>
        </Portlet:Property>
    </Portlet:UserGrid>
  </Portlet:FoldingSection>

