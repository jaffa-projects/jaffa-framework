<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- 
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.modules.printing.components.printeroutputtypeviewer.ui.PrinterOutputTypeViewerForm" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table>
  <Portlet:Property field='outputType'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.PrinterOutputType.OutputType]"/>:
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
        <Portlet:Label key="[label.Jaffa.Printing.PrinterOutputType.Description]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='directPrinting'>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.PrinterOutputType.DirectPrinting]"/>:
      </td>
      <td>
        <table>
          <%-- Direct Printing is not implemented
          <tr>
            <td>
              <Portlet:RadioButton displayOnly='true' selectValue='true'/><Portlet:Label key='[label.Jaffa.Printing.PrinterOutputType.DirectPrinting.True]'/>
            </td>
          </tr>
          --%>
          <tr>
            <td>
              <Portlet:RadioButton displayOnly='true' selectValue='false'/><Portlet:Label key='[label.Jaffa.Printing.PrinterOutputType.DirectPrinting.False]'/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </Portlet:Property>
</table>

  <BR>
  <BR>

  <Portlet:FoldingSection id="printing_printerOutputTypeViewer_outputCommand" label="<%=org.jaffa.util.MessageHelper.replaceTokens(pageContext,org.jaffa.modules.printing.domain.OutputCommandMeta.getLabelToken())%>">
    <Portlet:UserGrid field="relatedOutputCommand" userGridId="jaffa_printing_printerOutputTypeViewer_outputCommand">
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
  </Portlet:FoldingSection>

