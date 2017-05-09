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



<table>
    <tr> 
        <td class="label">
            <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='UserName'/>:
        </td>
        <td>
            <Portlet:Text field='userName' domain='org.jaffa.applications.jaffa.modules.admin.domain.User' domainField='UserName'/>
        </td>
    </tr>

    <tr> 
        <td class="label">
            <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='FirstName'/>:
        </td>
        <td>
            <Portlet:Text field='firstName' />
        </td>
    </tr>

    <tr> 
        <td class="label">
            <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='LastName'/>:
        </td>
        <td>
            <Portlet:Text field='lastName' />
        </td>
    </tr>

    <tr> 
        <td class="label">
            <Portlet:Label key='[label.Jaffa.Admin.User.Password]'/>:
        </td>
        <td>
            <Portlet:EditBox field='password1' password='true'/>
        </td>
    </tr>

    <tr> 
        <td class="label">
            <Portlet:Label key='[label.Jaffa.Admin.User.VerifyPassword]'/>:
        </td>
        <td>
            <Portlet:EditBox field='password2' password='true'/>
        </td>
    </tr>

    <tr> 
        <td class="label">
            <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='Status'/>:
        </td>
        <td>
            <Portlet:Text field='status'/>
        </td>
    </tr>

    <tr> 
        <td class="label">
            <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='EMailAddress'/>:
        </td>
        <td>
            <Portlet:EditBox field='EMailAddress' />
        </td>
    </tr>

    <tr> 
        <td class="label">
        </td>
        <td>
            <Portlet:Grid field='rolesGrid' displayOnly='true'>
                <Portlet:GridColumn label='Column 1'>
                   <Portlet:CheckBox field='checkbox0' displayOnly='true'/> <A class='checkboxlabel' title='<Portlet:Text field="description0"/>'><Portlet:Text field='rolename0'/></A>
                </Portlet:GridColumn>
                <Portlet:GridColumn label='Column 2'>
                   <Portlet:CheckBox field='checkbox1' displayOnly='true'/> <A class='checkboxlabel' title='<Portlet:Text field="description1"/>'><Portlet:Text field='rolename1'/></A>
                </Portlet:GridColumn>
                <Portlet:GridColumn label='Column 3'>
                   <Portlet:CheckBox field='checkbox2' displayOnly='true'/> <A class='checkboxlabel' title='<Portlet:Text field="description2"/>'><Portlet:Text field='rolename2'/></A>
                </Portlet:GridColumn>
            </Portlet:Grid>
        </td>
    </tr>

    <tr> 
        <td class="label">
            <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='CreatedOn'/>:
        </td>
        <td>
            <Portlet:Text field='createdOn' domain='org.jaffa.applications.jaffa.modules.admin.domain.User' domainField='CreatedOn'/>
        </td>
    </tr>

    <tr> 
        <td class="label">
            <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='CreatedBy'/>:
        </td>
        <td>
            <Portlet:Text field='createdBy' domain='org.jaffa.applications.jaffa.modules.admin.domain.User' domainField='CreatedBy'/>
        </td>
    </tr>

    <tr> 
        <td class="label">
            <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='LastUpdatedOn'/>:
        </td>
        <td>
            <Portlet:Text field='lastUpdatedOn' domain='org.jaffa.applications.jaffa.modules.admin.domain.User' domainField='LastUpdatedOn'/>
        </td>
    </tr>

    <tr> 
        <td class="label">
            <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='LastUpdatedBy'/>:
        </td>
        <td>
            <Portlet:Text field='lastUpdatedBy' domain='org.jaffa.applications.jaffa.modules.admin.domain.User' domainField='LastUpdatedBy'/>
        </td>
    </tr>

</table>

<%-- A maintenance component may consist of more than one screen. This hidden field is used to identify the current screen being displayed --%>
<tiles:useAttribute name='currentScreenCounter' classname='String'/>
<input type='hidden' name='<%= MaintForm.PARAMETER_CURRENT_SCREEN_COUNTER%>' value='<%= currentScreenCounter %>'/>

