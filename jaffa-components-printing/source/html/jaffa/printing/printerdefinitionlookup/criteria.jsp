<%-- ---------------------------------------------------------------- 
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>

<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table>
    <Portlet:Property field='printerId'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="printerIdDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='description'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="descriptionDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='siteCode'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="siteCodeDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='locationCode'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="locationCodeDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='realPrinterName'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="realPrinterNameDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='outputType'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
        </td>
        <td>
            <Portlet:DropDown/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='scaleToPageSize'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
        </td>
        <td>
            <Portlet:DropDown/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='remote'>
    <tr> 
        <td class='label'>
            <Portlet:Label key="[label.Jaffa.Printing.PrinterDefinition.Type]"/>:
        </td>
        <td>
            <Portlet:RadioButton selectValue="false"/> 
            <Portlet:Label key='label.Jaffa.Printing.PrinterDefinition.Type.local'/>	            
	
        </td>
        <td>
            <Portlet:RadioButton  selectValue="true"/>
	    <Portlet:Label key='label.Jaffa.Printing.PrinterDefinition.Type.remote'/>        
        </td>
    </tr>
    </Portlet:Property>
    <tr>
	<td class='label'><Portlet:Label key="label.Jaffa.Inquiry.sort"/>:</td>
        <td colspan="2">
            <Portlet:DropDown field="sortDropDown">
                <Portlet:DropDownOption label='<%= org.jaffa.modules.printing.domain.PrinterDefinitionMeta.META_PRINTER_ID.getLabelToken() + "[label.Jaffa.Inquiry.OrderByField.SortAscending]" %>' value='PrinterId'/>
                <Portlet:DropDownOption label='<%= org.jaffa.modules.printing.domain.PrinterDefinitionMeta.META_SITE_CODE.getLabelToken() + "[label.Jaffa.Inquiry.OrderByField.SortAscending]" %>' value='SiteCode'/>
                <Portlet:DropDownOption label='<%= org.jaffa.modules.printing.domain.PrinterDefinitionMeta.META_LOCATION_CODE.getLabelToken() + "[label.Jaffa.Inquiry.OrderByField.SortAscending]" %>' value='LocationCode'/>
                <Portlet:DropDownOption label='<%= org.jaffa.modules.printing.domain.PrinterDefinitionMeta.META_OUTPUT_TYPE.getLabelToken() + "[label.Jaffa.Inquiry.OrderByField.SortAscending]" %>' value='OutputType'/>
            </Portlet:DropDown>
        </td>
    </tr>    
    <tr>
        <td class="label"><Portlet:Label key="label.Jaffa.Inquiry.maxRecords"/>:</td>
        <td colspan="2"><Portlet:DropDown field="maxRecords"/></td>
    </tr>
</table>
