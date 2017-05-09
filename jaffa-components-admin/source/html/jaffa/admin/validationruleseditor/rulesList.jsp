<%-- ---------------------------------------------------------------- 
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.applications.jaffa.modules.admin.components.validationruleseditor.ui.ValidationRulesEditorForm" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<table>
  <tr>
    <td class="label"><Portlet:Label key="label.Jaffa.Admin.ValidationRulesEditor.CoreRulesUrl"/>:</td>
    <td><Portlet:Button field='coreRulesUrl' type='link' label='<%= ((ValidationRulesEditorForm) TagHelper.getFormBase(pageContext)).getCoreRulesUrl()%>'/></td>
  </tr>
  <tr>
    <td class="label"><Portlet:Label key="label.Jaffa.Admin.ValidationRulesEditor.VariationRulesDir"/>:</td>
    <td><Portlet:Text field="variationRulesDir"/></td>
  </tr>
  <tr>
    <td class="label" valign="top"><Portlet:Label key="label.Jaffa.Admin.ValidationRulesEditor.ValidatorsUrls"/>:</td>
    <td>
      <Portlet:Grid field='validatorsUrls' displayOnly='true' noRecordsKey='[label.Jaffa.Admin.ValidationRulesEditor.NoRecords]'>
        <Portlet:GridColumn label="none">
          <Portlet:Button field='update' type='link' label='<%= (String) TagHelper.getModelMap(pageContext).get("validatorsUrl") %>'/>
        </Portlet:GridColumn>
      </Portlet:Grid>
    </td>
  </tr>

  <tr>
    <td colspan="2">
      <Portlet:FoldingSection key='[title.Jaffa.Admin.ValidationRulesEditor.Rules]' id="Jaffa.Admin.ValidationRulesEditor">
        <Portlet:Grid field='rules' detail='true' noRecordsKey='[label.Jaffa.Admin.ValidationRulesEditor.NoRecords]'>
          <Portlet:GridColumn label="[label.Jaffa.Admin.ValidationRulesEditor.Variation]">
            <Portlet:Text field='variation'/>
          </Portlet:GridColumn>
          <Portlet:GridColumn label="[label.Jaffa.Admin.ValidationRulesEditor.Name]">
            <Portlet:Text field='name'/>
          </Portlet:GridColumn>
          <Portlet:GridColumn label="[label.Jaffa.Widgets.Button.Action]">
            <Portlet:Button field="update" image="jaffa/imgs/icons/update.gif" label="[label.Jaffa.Widgets.Button.Update]"/>
          </Portlet:GridColumn>
        </Portlet:Grid>
      </Portlet:FoldingSection>
    </td>
  </tr>
</table>
