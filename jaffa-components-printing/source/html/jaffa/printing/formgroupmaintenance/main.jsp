<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --  
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.components.maint.MaintForm" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.modules.printing.services.FormPrintFactory" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>



<table width='100%' border='0' cellspacing='0' align='center'>
  <Portlet:Property field='formName'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <Portlet:Text />
      </logic:equal>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <% if (((MaintForm) TagHelper.getFormBase(pageContext)).isDisplayOnlyField("formName")) { %>
          <Portlet:Text />
        <% } else { %>
          <Portlet:EditBox />
        <% } %>
      </logic:equal>
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='description'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='formType'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <table>
            <tr>
              <td>
                <Portlet:RadioButton selectValue='<%=FormPrintFactory.ENGINE_TYPE_ITEXT%>'/><Portlet:Label key='<%="[label.Jaffa.Printing.FormGroup.FormType."+FormPrintFactory.ENGINE_TYPE_ITEXT+"]"%>'/>
              </td>
              <td>
                <Portlet:RadioButton selectValue='<%=FormPrintFactory.ENGINE_TYPE_FOP%>'/><Portlet:Label key='<%="[label.Jaffa.Printing.FormGroup.FormType."+FormPrintFactory.ENGINE_TYPE_FOP+"]"%>'/>
              </td>
              <td>
                <Portlet:RadioButton selectValue='<%=FormPrintFactory.ENGINE_TYPE_VELOCITY%>'/><Portlet:Label key='<%="[label.Jaffa.Printing.FormGroup.FormType."+FormPrintFactory.ENGINE_TYPE_VELOCITY+"]"%>'/>
              </td>
            </tr>
          </table>
    </td>
  </tr>
  </Portlet:Property>

  <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
  <tr>
    <td colspan='2'>
      <Portlet:FoldingSection id='FormUsage' key='<%= org.jaffa.modules.printing.domain.FormUsageMeta.getLabelToken() %>' closed='false'>
        <table>
          <tr>
            <td>
              <Portlet:UserGrid field="relatedFormUsage" userGridId="jaffa_printing_formGroupMaintenance_formUsage">
                <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Delete]" dataType="CaseInsensitiveString">
                  <Portlet:CheckBox field="select"/>
                </Portlet:UserGridColumn>                                      
                <Portlet:Property field='eventName'>
                <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormGroup.EventName]'>
                  <Portlet:Text />
                </Portlet:UserGridColumn>
                </Portlet:Property>
                <Portlet:Property field='formAlternate'>
                <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormDefinition.FormAlternate]'>
                  <Portlet:EditBox columns='20'/>
                </Portlet:UserGridColumn>
                </Portlet:Property>
                <Portlet:Property field='copies'>
                <Portlet:UserGridColumn dataType="Number" label='[label.Jaffa.Printing.FormUsage.Copies]'>
                  <Portlet:EditBox columns='2'/>
                </Portlet:UserGridColumn>
                </Portlet:Property>
                <Portlet:Property field='description'>
                <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormEvent.Description]'>
                  <Portlet:Text />
                </Portlet:UserGridColumn>
                </Portlet:Property>
              </Portlet:UserGrid>
            </td>
          </tr>
          <tr>
            <td align='left'>
              <Portlet:Button field='AddRelatedFormUsage' type='link' label='[label.Jaffa.Printing.FormGroup.Add]'/>
              <Portlet:Button field='RemoveRelatedFormUsage' type='link' label='[label.Jaffa.Widgets.Button.Delete]' confirm="[label.Jaffa.Inquiry.delete.confirm]"/>
            </td>
          </tr>
        </table>
      </Portlet:FoldingSection>
    </td>
  </tr>
</logic:equal>
</table>

<%-- A maintenance component may consist of more than one screen. This hidden field is used to identify the current screen being displayed --%>
<tiles:useAttribute name='currentScreenCounter' classname='String'/>
<input type='hidden' name='<%= MaintForm.PARAMETER_CURRENT_SCREEN_COUNTER%>' value='<%= currentScreenCounter %>'/>

