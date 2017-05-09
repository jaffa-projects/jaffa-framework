<%-- ---------------------------------------------------------------- 
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- //JAFFA-OVERWRITE: As long as this line exists, this file will be overwritten in all future runs of the pattern generator --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.components.finder.FinderComponent2" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table>
    <Portlet:Property field='originalFileName'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="originalFileNameDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='attachmentType'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="attachmentTypeDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='contentType'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="contentTypeDd"/>
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
    <Portlet:Property field='remarks'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="remarksDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='supercededBy'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="supercededByDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='createdOn'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="createdOnDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
            <Portlet:Calendar/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='createdBy'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="createdByDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='lastChangedOn'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="lastChangedOnDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
            <Portlet:Calendar/>
        </td>
    </tr>
    </Portlet:Property>
    <Portlet:Property field='lastChangedBy'>
    <tr> 
        <td class='label'>
            <Portlet:Label/>:
        </td>
        <td>
            <Portlet:DropDown field="lastChangedByDd"/>
        </td>
        <td>
            <Portlet:EditBox/>
        </td>
    </tr>
    </Portlet:Property>
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
