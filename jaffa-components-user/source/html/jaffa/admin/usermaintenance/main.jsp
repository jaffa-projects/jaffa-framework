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



<table>
        <tr> 
            <td class="label">
                <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='UserName'/>:
            </td>
            <td>
                <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
                    <Portlet:Text field='userName' domain='org.jaffa.applications.jaffa.modules.admin.domain.User' domainField='UserName'/>
                </logic:equal>
                <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
                 <% if (((MaintForm) TagHelper.getFormBase(pageContext)).isDisplayOnlyField("userName")) { %>
                        <Portlet:Text field='userName' domain='org.jaffa.applications.jaffa.modules.admin.domain.User' domainField='UserName'/>
                 <% } else { %>
                        <Portlet:EditBox field='userName' />
                 <% } %>
                </logic:equal>
            </td>
        </tr>

        <tr> 
            <td class="label">
                <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='FirstName'/>:
            </td>
            <td>
                        <Portlet:EditBox field='firstName' />
            </td>
        </tr>

        <tr> 
            <td class="label">
                <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='LastName'/>:
            </td>
            <td>
                        <Portlet:EditBox field='lastName' />
            </td>
        </tr>
 <tr> 
            <td colspan="2" width="100%">
 <Portlet:FoldingSection id="passwords" key="[label.Jaffa.Admin.User.PasswordSection]">
 <table width="100%">

        <tr> 
            <td class="label">
                &nbsp;<Portlet:Label key='[label.Jaffa.Admin.User.Password]'/>:
            </td>
            <td>
                        <Portlet:EditBox field='password1' password='true' columns='19'/>
            </td>
        </tr>

        <tr> 
            <td class="label">
                &nbsp;<Portlet:Label key='[label.Jaffa.Admin.User.VerifyPassword]'/>:
            </td>
            <td>
                        <Portlet:EditBox field='password2' password='true' columns='19'/> <Portlet:CheckBox field='autoPassword' /> <a class=checkboxlabel nowrap><Portlet:Label key='[label.Jaffa.Admin.User.AutoPassword]'/></a>
            </td>
        </tr>
        </table>
</Portlet:FoldingSection>
</td>
</tr>
        <tr> 
            <td class="label">
                <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='Status'/>:
            </td>
            <td>
                        <Portlet:DropDown field='status'/> 
            </td>
        </tr>
       <tr> 
            <td class="label">
                
            </td>
            <td>
                     <Portlet:CheckBox field='notifyUser' /><a class=checkboxlabel nowrap> <Portlet:Label key='[label.Jaffa.Admin.User.NotifyUser]'/> </a>
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
       <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='SecurityQuestion'/>:
     </td>
     <td>
           <Portlet:DropDown field='securityQuestion1'/>
     </td>
   </tr>
 
   <tr> 
     <td class="label">
       <Portlet:Label domain='org.jaffa.applications.jaffa.modules.user.domain.UserRequest' field='SecurityAnswer'/>:
     </td>
     <td>
           <Portlet:EditBox field='securityAnswer' />
     </td>
  </tr>

        <tr> 
            <td class="label">
            </td>
            <td>
                <Portlet:Grid field='rolesGrid' displayOnly='true'>
                    <Portlet:GridColumn label='Column 1'>
                       <Portlet:CheckBox field='checkbox0'/> <A class='checkboxlabel' title='<Portlet:Text field="description0"/>'><Portlet:Text field='rolename0'/></A>
                    </Portlet:GridColumn>
                    <Portlet:GridColumn label='Column 2'>
                       <Portlet:CheckBox field='checkbox1'/> <A class='checkboxlabel' title='<Portlet:Text field="description1"/>'><Portlet:Text field='rolename1'/></A>
                    </Portlet:GridColumn>
                    <Portlet:GridColumn label='Column 3'>
                       <Portlet:CheckBox field='checkbox2'/> <A class='checkboxlabel' title='<Portlet:Text field="description2"/>'><Portlet:Text field='rolename2'/></A>
                    </Portlet:GridColumn>
                </Portlet:Grid>
            </td>
        </tr>

    <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <tr> 
            <td class="label">
                <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='CreatedOn'/>:
            </td>
            <td>
                <Portlet:Text field='createdOn' domain='org.jaffa.applications.jaffa.modules.admin.domain.User' domainField='CreatedOn'/>
            </td>
        </tr>
    </logic:equal>

    <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <tr> 
            <td class="label">
                <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='CreatedBy'/>:
            </td>
            <td>
                <Portlet:Text field='createdBy' domain='org.jaffa.applications.jaffa.modules.admin.domain.User' domainField='CreatedBy'/>
            </td>
        </tr>
    </logic:equal>

    <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <tr> 
            <td class="label">
                <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='LastUpdatedOn'/>:
            </td>
            <td>
                <Portlet:Text field='lastUpdatedOn' domain='org.jaffa.applications.jaffa.modules.admin.domain.User' domainField='LastUpdatedOn'/>
            </td>
        </tr>
    </logic:equal>

    <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <tr> 
            <td class="label">
                <Portlet:Label domain='org.jaffa.applications.jaffa.modules.admin.domain.User' field='LastUpdatedBy'/>:
            </td>
            <td>
                <Portlet:Text field='lastUpdatedBy' domain='org.jaffa.applications.jaffa.modules.admin.domain.User' domainField='LastUpdatedBy'/>
            </td>
        </tr>
    </logic:equal>

</table>

<%-- A maintenance component may consist of more than one screen. This hidden field is used to identify the current screen being displayed --%>
<tiles:useAttribute name='currentScreenCounter' classname='String'/>
<input type='hidden' name='<%= MaintForm.PARAMETER_CURRENT_SCREEN_COUNTER%>' value='<%= currentScreenCounter %>'/>

