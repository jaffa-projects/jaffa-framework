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
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>



<table width='100%' border='0' cellspacing='0' align='center'>
  <Portlet:Property field='outputType'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <Portlet:Text />
      </logic:equal>
      <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <% if (((MaintForm) TagHelper.getFormBase(pageContext)).isDisplayOnlyField("outputType")) { %>
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
  
  <Portlet:Property field='directPrinting'>
    <tr> 
      <td class="label">
        <Portlet:Label key='[label.Jaffa.Printing.PrintOutputType.PrintMethod]'/>:
      </td>
      <td>
        <table>
          <%-- Direct Printing is not implemented
          <tr>
            <td>
              <Portlet:RadioButton selectValue='true'/><Portlet:Label key='[label.Jaffa.Printing.PrinterOutputType.DirectPrinting.True]'/>
            </td>
          </tr>
          --%>
          <tr>
            <td>
              <Portlet:RadioButton selectValue='false'/><Portlet:Label key='[label.Jaffa.Printing.PrinterOutputType.DirectPrinting.False]'/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </Portlet:Property>
  


  <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
  <tr>
    <td colspan='2'>
      <Portlet:FoldingSection id='OutputCommand' key='<%= org.jaffa.modules.printing.domain.OutputCommandMeta.getLabelToken() %>' closed='false'>
        <table>
          <tr>
            <td class="label">
              <Portlet:Label key='[label.Jaffa.Printing.CurrentOsName]'/>:
            </td>
            <td>
               <%=StringHelper.convertToHTML( System.getProperty("os.name") )%>
            </td>
          </tr>
          <tr>
            <td colspan='2'>
              <Portlet:UserGrid field="relatedOutputCommand" userGridId="jaffa_printing_printerOutputTypeMaintenance_outputCommand" detail="true" target="_blank">
                <Portlet:Property field='sequenceNo'>
                <Portlet:UserGridColumn dataType="Number" label='[label.Jaffa.Printing.OutputCommand.SequenceNo]'>
                  <Portlet:Text />
                 </Portlet:UserGridColumn>
                 </Portlet:Property>
                <Portlet:Property field='osPattern'>
                <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.OutputCommand.OsPattern]'>
                  <Portlet:Text />
                 </Portlet:UserGridColumn>
                 </Portlet:Property>
                <Portlet:Property field='commandLine'>
                <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.OutputCommand.CommandLine]'>
                  <Portlet:Text />
                 </Portlet:UserGridColumn>
                 </Portlet:Property>
                 <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
                  <Portlet:ComponentGuard name="Jaffa.Printing.OutputCommandMaintenance">
                    <Portlet:Button field="Update" label="[label.Jaffa.Widgets.Button.Update]" image="jaffa/imgs/icons/update.gif"/>
                  </Portlet:ComponentGuard>
                  <Portlet:ComponentGuard name="Jaffa.Printing.OutputCommandMaintenance">
                    <Portlet:Button field="Delete" label="[label.Jaffa.Widgets.Button.Delete]" image="jaffa/imgs/icons/delete.gif"  confirm="[label.Jaffa.Inquiry.delete.confirm]"/>
                  </Portlet:ComponentGuard>
                </Portlet:UserGridColumn>
              </Portlet:UserGrid>
            </td>
          </tr>
          <tr>
            <td class='createNew'>
              <Portlet:Button field='RelatedOutputCommand_Create' type='sized' label='[label.Jaffa.Widgets.Button.CreateNew]'/>
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

