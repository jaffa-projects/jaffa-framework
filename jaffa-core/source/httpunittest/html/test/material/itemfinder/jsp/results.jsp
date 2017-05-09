<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.components.finder.FinderComponent" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.UserGridTag" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<%
    String xml = (String) request.getAttribute(FinderComponent.ATTRIBUTE_EXPORT_TYPE_XML);
    if (xml != null) {
        // Just spit out the XML, ignoring the rest of the JSP
        response.setContentType("text/xml");
        out.println(xml);
    } else {
        // continue with the JSP
%>

<html:html>
    <HEAD>
        <Portlet:Header/>
    </HEAD>
    
    <BODY leftMargin="0" topMargin="0" marginwidth="0" marginheight="0">
        <Portlet:Form action="/material_itemFinderResults">

            <bean:define id="component" name="<%= TagHelper.getFormName(pageContext) %>" property="component" type="org.jaffa.components.finder.FinderComponent"/>
            <bean:define id="exportType" name="component" property="exportType" type="java.lang.String"/>
            <bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_WEB_PAGE %>"/>
            <logic:equal name="exportType" value="<%= FinderComponent.EXPORT_TYPE_WEB_PAGE %>">
                <% response.setContentType("text/html"); %>
            </logic:equal>
            <logic:equal name="exportType" value="<%= FinderComponent.EXPORT_TYPE_EXCEL %>">
                <% outputType= UserGridTag.OUTPUT_TYPE_EXCEL; %>
                <% response.setContentType("application/vnd.ms-excel"); %>
            </logic:equal>
            <logic:equal name="exportType" value="<%= FinderComponent.EXPORT_TYPE_XML %>">
                <% outputType= UserGridTag.OUTPUT_TYPE_XML; %>
                <% response.setContentType("text/xml"); %>
            </logic:equal>

            <Portlet:UserGrid field="rows" userGridId="material_itemFinder" outputType="<%= outputType %>">
                <Portlet:UserGridColumn label="Segregation Code" dataType="CaseInsensitiveString" >
                    <Portlet:Text field="segregationCode" domain="org.jaffa.applications.test.modules.material.domain.Item" domainField="Sc"/>
                </Portlet:UserGridColumn>
                <Portlet:UserGridColumn label="Part No" dataType="CaseInsensitiveString" >
                    <Portlet:Text field="partNo" domain="org.jaffa.applications.test.modules.material.domain.Item" domainField="Part"/>
                </Portlet:UserGridColumn>
                <Portlet:UserGridColumn label="Serial" dataType="CaseInsensitiveString" >
                    <Portlet:Text field="serial" domain="org.jaffa.applications.test.modules.material.domain.Item" domainField="Serial"/>
                </Portlet:UserGridColumn>
                <Portlet:UserGridColumn label="Qty" dataType="Number" >
                    <Portlet:Text field="qty" domain="org.jaffa.applications.test.modules.material.domain.Item" domainField="Qty"/>
                </Portlet:UserGridColumn>

<logic:equal name="exportType" value="<%= FinderComponent.EXPORT_TYPE_WEB_PAGE %>">
</logic:equal>

            </Portlet:UserGrid>

<logic:equal name="exportType" value="<%= FinderComponent.EXPORT_TYPE_WEB_PAGE %>">
            <BR>
            <logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="moreRecordsExist" value="true">
                <B>* More Records Exist</B>
            </logic:equal>

            <BR>
            <BR>
        
            <CENTER>
                <TABLE width="100%">
                    <TR>
                        <TD width="16%"><Portlet:Button field="Refresh" label="[label.Jaffa.Widgets.Button.Refresh]"/></TD>
                        <TD width="16%"><Portlet:Button field="ModifySearch" label="[label.Jaffa.Widgets.Button.ModifySearch]"/></TD>
                        <TD width="16%"><Portlet:Button field="Close" label="[label.Jaffa.Widgets.Button.Close]" preprocess="false"/></TD>
                    </TR>
                </TABLE>
            </CENTER>
</logic:equal>
            
            <Portlet:RaiseErrors/>
        </Portlet:Form>
        <Portlet:Footer/>
    </BODY>
</html:html>

<%
    } // -> if (xml != null)
%>

