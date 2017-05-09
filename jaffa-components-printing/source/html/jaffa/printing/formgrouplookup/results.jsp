<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.datatypes.Formatter" %>
<%@ page import="org.jaffa.components.lookup.LookupComponent2" %>
<%@ page import="org.jaffa.components.lookup.LookupForm" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<bean:define id='component' name='<%= TagHelper.getFormName(pageContext) %>' property='component' type='org.jaffa.components.lookup.LookupComponent2'/>
<bean:define id='inMultiSelectLookupMode' name='component' property='inMultiSelectLookupMode' type='java.lang.Boolean'/>

<Portlet:UserGrid field="rows" userGridId="jaffa_printing_formGroupLookup" detail="<%= !inMultiSelectLookupMode.booleanValue() %>">
    <logic:equal name='inMultiSelectLookupMode' value='true'>
        <Portlet:UserGridColumn label='[label.Jaffa.Widgets.Button.Select]' dataType='CaseInsensitiveString'>
            <Portlet:CheckBox field='<%= LookupComponent2.MULTI_SELECT_CHECKBOX %>'/>
        </Portlet:UserGridColumn>
    </logic:equal>
  <Portlet:Property field='formName' propertyClass='org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormGroup.FormName]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='description' propertyClass='org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormGroup.Description]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='formType' propertyClass='org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormDefinition.FormType]'>
      <Portlet:Label key='<%="label.Jaffa.Printing.FormGroup.FormType."+TagHelper.getModelMap(pageContext).get("formType")%>'/>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='createdOn' propertyClass='org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutRowDto'>
    <Portlet:UserGridColumn dataType="Date" label='[label.Jaffa.Common.CreatedOn]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='createdBy' propertyClass='org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Common.CreatedBy]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='lastChangedOn' propertyClass='org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutRowDto'>
    <Portlet:UserGridColumn dataType="Date" label='[label.Jaffa.Common.LastChangedOn]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='lastChangedBy' propertyClass='org.jaffa.modules.printing.components.formgrouplookup.dto.FormGroupLookupOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Common.LastChangedBy]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>

  <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
    <logic:equal name='inMultiSelectLookupMode' value='false'>
      <Portlet:Button field="Select" label="[label.Jaffa.Widgets.Button.Select]" image="jaffa/imgs/icons/select.gif"/>
    </logic:equal>
    <Portlet:ComponentGuard name="Jaffa.Printing.FormGroupViewer">
      <Portlet:Button field="View" label="[label.Jaffa.Widgets.Button.View]" image="jaffa/imgs/icons/detail.gif" target="_blank"/>
    </Portlet:ComponentGuard>
    <Portlet:ComponentGuard name="Jaffa.Printing.FormGroupMaintenance">
      <Portlet:Button field="Update" label="[label.Jaffa.Widgets.Button.Update]" image="jaffa/imgs/icons/update.gif"/>
    </Portlet:ComponentGuard>
    <Portlet:ComponentGuard name="Jaffa.Printing.FormGroupMaintenance">
      <Portlet:Button field="Delete" label="[label.Jaffa.Widgets.Button.Delete]" image="jaffa/imgs/icons/delete.gif"  confirm="[label.Jaffa.Inquiry.delete.confirm]"/>
    </Portlet:ComponentGuard>
  </Portlet:UserGridColumn>
</Portlet:UserGrid>

<table>
  <tr>
    <td>
      <logic:greaterThan name="<%= TagHelper.getFormName(pageContext) %>" property="numberOfRecords" value="0">
        <Portlet:Label key='label.Jaffa.Inquiry.numberOfRecords' arg0='<%= Formatter.format(((LookupForm) TagHelper.getFormBase(pageContext)).getNumberOfRecords()) %>'/>
        <logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="moreRecordsExist" value="true">
          <Portlet:Label key='label.Jaffa.Inquiry.moreRecordsPrefix'/> <Portlet:Button field="MoreRecords" type="link" label="[label.Jaffa.Inquiry.moreRecordsExist]"/> <Portlet:Label key='label.Jaffa.Inquiry.moreRecordsSuffix'/> 
        </logic:equal>
      </logic:greaterThan>
    </td>
  </tr>
</table>
