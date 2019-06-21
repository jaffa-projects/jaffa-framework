<%-- ---------------------------------------------------------------- 
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.components.finder.FinderComponent2" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table>
    <Portlet:Property field='eventName'>
    <tr> 
        <td class='label'>
            <Portlet:Label key="[label.Jaffa.Printing.FormGroup.EventName]"/>:
        </td>
        <td>
            <Portlet:DropDown field="eventNameDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <%--
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
    --%>
    <tr>
        <td class='label'><Portlet:Label key="label.Jaffa.Inquiry.export"/>:</td>
        <td colspan="2">
            <table>
                <tr>
                    <td class='radiolabel'><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent2.EXPORT_TYPE_WEB_PAGE %>"/><span class="radiolabel"><Portlet:Label key="label.Jaffa.Inquiry.exporttype.webPage"/></span></td>
                    <td class='radiolabel'><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent2.EXPORT_TYPE_EXCEL %>"/><span class="radiolabel"><Portlet:Label key="label.Jaffa.Inquiry.exporttype.excel"/></span></td>
                    <td class='radiolabel'><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent2.EXPORT_TYPE_XML %>"/><span class="radiolabel"><Portlet:Label key="label.Jaffa.Inquiry.exporttype.xml"/></span></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="label"><Portlet:Label key="label.Jaffa.Inquiry.maxRecords"/>:</td>
        <td colspan="2"><Portlet:DropDown field="maxRecords"/></td>
    </tr>
</table>
