<%@ page language="java" %>
<%@ page import="org.jaffa.components.finder.FinderComponent" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<html:html>
    <HEAD>
	<SCRIPT type="text/javascript" src="jaffa/js/panels/reset.js"></SCRIPT>
        <Portlet:Header/>
    </HEAD>
    
    <BODY leftMargin="0" topMargin="0" marginwidth="0" marginheight="0">
        <Portlet:Form action="/material_itemFinderCriteria">
        <table>
            <tr> 
                <td align="right" width="200">
                    <Portlet:Label domain="org.jaffa.applications.test.modules.material.domain.Item" field="Sc"/>:
                </td>
                <td align="left">
                    <Portlet:DropDown field="segregationCodeDd"/>
                </td>
                <td align="left">
                    <Portlet:EditBox field="segregationCode"/>
                </td>
            </tr>
            <tr> 
                <td align="right" width="200">
                    <Portlet:Label domain="org.jaffa.applications.test.modules.material.domain.Item" field="Part"/>:
                </td>
                <td align="left">
                    <Portlet:DropDown field="partNoDd"/>
                </td>
                <td align="left">
                    <Portlet:EditBox field="partNo"/>
                </td>
            </tr>
            <tr> 
                <td align="right" width="200">
                    <Portlet:Label domain="org.jaffa.applications.test.modules.material.domain.Item" field="Serial"/>:
                </td>
                <td align="left">
                    <Portlet:DropDown field="serialDd"/>
                </td>
                <td align="left">
                    <Portlet:EditBox field="serial"/>
                </td>
            </tr>
            <tr> 
                <td align="right" width="200">
                    <Portlet:Label domain="org.jaffa.applications.test.modules.material.domain.Item" field="Qty"/>:
                </td>
                <td align="left">
                    <Portlet:DropDown field="qtyDd"/>
                </td>
                <td align="left">
                    <Portlet:EditBox field="qty"/>
                </td>
            </tr>
        </table>

        <BR>
        Export Type:
        <table><tr>
        <td><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent.EXPORT_TYPE_WEB_PAGE %>"/>Web Page</td>
        <td><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent.EXPORT_TYPE_EXCEL %>"/>Excel</td>
        <td><Portlet:RadioButton field="exportType" selectValue="<%= FinderComponent.EXPORT_TYPE_XML %>"/>XML</td>
        </tr></table>

        <BR>
        Max Records:
        <Portlet:DropDown field="maxRecords"/>

        <BR>
        <BR>
        
        <CENTER>
            <TABLE width="100%">
                <TR>
                    <TD width="16%"><INPUT TYPE="button" value="<%= MessageHelper.replaceTokens(pageContext, "[label.Jaffa.Widgets.Button.Clear]") %>" onClick="javascript:resetForm();"></TD>
                    <TD width="16%"><Portlet:Button field="Search" label="[label.Jaffa.Widgets.Button.Search]" submit="true"/></TD>
                    <TD width="16%"><Portlet:Button field="Close" label="[label.Jaffa.Widgets.Button.Close]" preprocess="false"/></TD>
                </TR>
            </TABLE>
        </CENTER>
        
        <Portlet:RaiseErrors/>
        </Portlet:Form>
        <Portlet:Footer/>
    </BODY>
</html:html>
