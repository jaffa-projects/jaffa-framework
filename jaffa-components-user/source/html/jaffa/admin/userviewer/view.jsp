<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- //JAFFA-OVERWRITE: As long as this line exists, this file will be overwritten in all future runs of the pattern generator --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.applications.jaffa.modules.admin.components.userviewer.ui.UserViewerForm" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<table>
  <Portlet:Property field='userName'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Admin.User.UserName]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='firstName'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Admin.User.FirstName]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='lastName'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Admin.User.LastName]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='statusDescription'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Admin.User.Status]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='EMailAddress'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Admin.User.EMailAddress]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='createdOn'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Admin.User.CreatedOn]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='createdBy'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Admin.User.CreatedBy]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='lastUpdatedOn'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Admin.User.LastUpdatedOn]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='lastUpdatedBy'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Admin.User.LastUpdatedBy]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
</table>

  <BR>
  <BR>

  <Portlet:FoldingSection id="admin_userViewer_userRole" label="<%=org.jaffa.util.MessageHelper.replaceTokens(pageContext,org.jaffa.applications.jaffa.modules.admin.domain.UserRoleMeta.getLabelToken())%>">
    <Portlet:UserGrid field="relatedUserRole" userGridId="jaffa_admin_userViewer_userRole">
        <Portlet:Property field='roleName'>
          <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Admin.UserRole.RoleName]' key='true'>
            <Portlet:Text />
          </Portlet:UserGridColumn>
        </Portlet:Property>
    </Portlet:UserGrid>
  </Portlet:FoldingSection>

