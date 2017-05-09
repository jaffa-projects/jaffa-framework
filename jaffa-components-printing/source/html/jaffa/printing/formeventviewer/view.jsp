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
<%@ page import="org.jaffa.modules.printing.components.formeventviewer.ui.FormEventViewerForm" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table>
  <Portlet:Property field='eventName'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.FormEvent.EventName]"/>:
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
        <Portlet:Label key="[label.Jaffa.Printing.FormEvent.Description]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='createdOn'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Common.CreatedOn]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='createdBy'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Common.CreatedBy]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='lastChangedOn'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Common.LastChangedOn]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='lastChangedBy'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Common.LastChangedBy]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
</table>

  <BR>
  <BR>

  <Portlet:FoldingSection id="printing_formEventViewer_formUsage" label="<%=org.jaffa.util.MessageHelper.replaceTokens(pageContext,org.jaffa.modules.printing.domain.FormUsageMeta.getLabelToken())%>">
    <Portlet:UserGrid field="relatedFormUsage" userGridId="jaffa_printing_formEventViewer_formUsage" detail="true" target="_blank">
        <Portlet:Property field='formName'>
          <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormGroup.FormName]' key='true'>
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
        <Portlet:Property field='createdOn'>
          <Portlet:UserGridColumn dataType="Date" label='[label.Jaffa.Common.CreatedOn]'>
            <Portlet:Text />
          </Portlet:UserGridColumn>
        </Portlet:Property>
        <Portlet:Property field='createdBy'>
          <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Common.CreatedBy]'>
            <Portlet:Text />
          </Portlet:UserGridColumn>
        </Portlet:Property>
        <Portlet:Property field='lastChangedOn'>
          <Portlet:UserGridColumn dataType="Date" label='[label.Jaffa.Common.LastChangedOn]'>
            <Portlet:Text />
          </Portlet:UserGridColumn>
        </Portlet:Property>
        <Portlet:Property field='lastChangedBy'>
          <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Common.LastChangedBy]'>
            <Portlet:Text />
          </Portlet:UserGridColumn>
        </Portlet:Property>
      <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
        <Portlet:ComponentGuard name="Jaffa.Printing.FormUsageViewer">
          <Portlet:Button field="View" label="[label.Jaffa.Widgets.Button.View]" image="jaffa/imgs/icons/detail.gif" target="_blank"/>
        </Portlet:ComponentGuard>
        <Portlet:ComponentGuard name="Jaffa.Printing.FormUsageMaintenance">
          <Portlet:Button field="Update" label="[label.Jaffa.Widgets.Button.Update]" image="jaffa/imgs/icons/update.gif"/>
        </Portlet:ComponentGuard>
        <Portlet:ComponentGuard name="Jaffa.Printing.FormUsageMaintenance">
          <Portlet:Button field="Delete" label="[label.Jaffa.Widgets.Button.Delete]" image="jaffa/imgs/icons/delete.gif"  confirm="[label.Jaffa.Inquiry.delete.confirm]"/>
        </Portlet:ComponentGuard>
      </Portlet:UserGridColumn>
    </Portlet:UserGrid>
  </Portlet:FoldingSection>

