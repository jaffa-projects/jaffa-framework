<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --  
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.modules.printing.components.printerdefinitionviewer.ui.PrinterDefinitionViewerForm" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table>
  <Portlet:Property field='printerId'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.PrinterId]"/>:
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
        <Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.Description]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='siteCode'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.SiteCode]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='locationCode'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.Location]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <%if( ((PrinterDefinitionViewerForm)TagHelper.getFormBase(pageContext)).getRemote() != null &&  ((PrinterDefinitionViewerForm)TagHelper.getFormBase(pageContext)).getRemote().booleanValue() ) {
  %>
  <Portlet:Property field='remote'>  
    <tr>
      <td class="label">
        <Portlet:Label/>:
      </td>
      <td>
        <Portlet:Label key='label.Jaffa.Printing.PrinterDefinition.Type.remote'/>
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <%}else{%>
  <Portlet:Property field='remote'>  
    <tr>
      <td class="label">
        <Portlet:Label/>:
      </td>
      <td>
        <Portlet:Label key='label.Jaffa.Printing.PrinterDefinition.Type.local'/>
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='realPrinterName'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.PrinterName]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='printerOption1'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.PrinterOption1]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='printerOption2'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.PrinterOption2]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <%}%>
  <Portlet:Property field='outputType'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.PrinterOutputTypes.OutputType]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='scaleToPageSize'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.ScaleToPageSize]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>

</table>


