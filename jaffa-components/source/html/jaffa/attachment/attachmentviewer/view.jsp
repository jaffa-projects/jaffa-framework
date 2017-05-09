<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.components.attachment.components.attachmentviewer.ui.AttachmentViewerForm" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<% AttachmentViewerForm myForm = (AttachmentViewerForm) TagHelper.getFormBase(pageContext); %>
<table>
  <Portlet:Property field='originalFileName'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Attachment.Attachment.OriginalFileName]"/>:
      </td>
      <td>
        <% if (myForm.getOriginalFileName() != null && "W".equals(myForm.getAttachmentType())) { %>
          <a href='<%= myForm.getOriginalFileName() %>' target='_blank'><%= myForm.getOriginalFileName() %></a>
        <% } else if (myForm.getOriginalFileName() != null && "L".equals(myForm.getAttachmentType())) { %>
          <%-- By default, the browsers do not allow a webpage to access the local filesystem. Hence will display the filename only --%>
          <%-- <a href='<%= new java.io.File(myForm.getOriginalFileName()).toURI().toURL().toString() %>' target='_blank'><%= myForm.getOriginalFileName() %></a> --%>
          <Portlet:Text />
        <% } else if (myForm.getOriginalFileName() != null && "E".equals(myForm.getAttachmentType()) && myForm.getData() != null && myForm.getData().length > 0) { %>
          <Portlet:Button field='ViewAttachmentData' target='_top' type='link' label='<%= myForm.getOriginalFileName() %>' preprocess='false'/>
        <% } else { %>
          <Portlet:Text />
        <% } %>
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='attachmentType'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Attachment.Attachment.AttachmentType]"/>:
      </td>
      <td>
        <% if (myForm.getAttachmentType() != null) { %>
          <Portlet:Label key="<%= "label.Jaffa.Attachment.Attachment.AttachmentType." + myForm.getAttachmentType() %>"/>
        <% } %>
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='contentType'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Attachment.Attachment.ContentType]"/>:
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
        <Portlet:Label key="[label.Jaffa.Attachment.Attachment.Description]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='remarks'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Attachment.Attachment.Remarks]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='supercededBy'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Jaffa.Attachment.Attachment.SupercededBy]"/>:
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
        <Portlet:Label key="[label.Common.CreatedOn]"/>:
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
        <Portlet:Label key="[label.Common.CreatedBy]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='lastChangedOn'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Common.LastChangedOn]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
  <Portlet:Property field='lastChangedBy'>
    <tr>
      <td class="label">
        <Portlet:Label key="[label.Common.LastChangedBy]"/>:
      </td>
      <td>
        <Portlet:Text />
      </td>
      <td></td>
    </tr>
  </Portlet:Property>
</table>
