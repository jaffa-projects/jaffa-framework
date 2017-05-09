<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.datatypes.Formatter" %>
<%@ page import="org.jaffa.components.finder.FinderComponent2" %>
<%@ page import="org.jaffa.components.finder.FinderForm" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.UserGridTag" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_WEB_PAGE %>"/>
<logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_EXCEL %>">
  <bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_EXCEL %>"/>
</logic:equal>
<logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_XML %>">
  <bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_XML %>"/>
</logic:equal>

<Portlet:UserGrid field="rows" userGridId="jaffa_attachment_attachmentFinder" outputType="<%= outputType %>" detail="true" target="_blank">
  <%
    java.util.Map map = TagHelper.getModelMap(pageContext);
    String originalFileName = map != null ? (String) map.get("originalFileName") : null;
    String attachmentType = map != null ? (String) map.get("attachmentType") : null;
    byte[] data = map != null ? (byte[]) map.get("data") : null;
  %>
  <Portlet:Property field='originalFileName' propertyClass='org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.OriginalFileName]'>
      <% if (originalFileName != null && "W".equals(attachmentType)) { %>
        <a href='<%= originalFileName %>' target='_blank'><%= originalFileName %></a>
      <% } else if (originalFileName != null && "L".equals(attachmentType)) { %>
        <%-- By default, the browsers do not allow a webpage to access the local filesystem. Hence will display the filename only --%>
        <%-- <a href='<%= new java.io.File(originalFileName).toURI().toURL().toString() %>' target='_blank'><%= originalFileName %></a> --%>
        <Portlet:Text />
      <% } else if (originalFileName != null && "E".equals(attachmentType) && data != null && data.length > 0) { %>
        <Portlet:Button field='ViewAttachmentData' target='_blank' type='link' label='<%= originalFileName %>'/>
      <% } else { %>
        <Portlet:Text />
      <% } %>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='attachmentType' propertyClass='org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.AttachmentType]'>
      <% if (attachmentType != null) { %>
        <Portlet:Label key="<%= "label.Jaffa.Attachment.Attachment.AttachmentType." + attachmentType %>"/>
      <% } %>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='contentType' propertyClass='org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.ContentType]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='description' propertyClass='org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.Description]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='remarks' propertyClass='org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.Remarks]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='supercededBy' propertyClass='org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Attachment.Attachment.SupercededBy]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='createdOn' propertyClass='org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="Date" label='[label.Common.CreatedOn]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='createdBy' propertyClass='org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Common.CreatedBy]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='lastChangedOn' propertyClass='org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="Date" label='[label.Common.LastChangedOn]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <Portlet:Property field='lastChangedBy' propertyClass='org.jaffa.components.attachment.components.attachmentfinder.dto.AttachmentFinderOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Common.LastChangedBy]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>

  <logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_WEB_PAGE %>">
    <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
      <Portlet:ComponentGuard name="Jaffa.Attachment.AttachmentViewer">
        <Portlet:Button field="View" label="[label.Jaffa.Widgets.Button.View]" image="jaffa/imgs/icons/detail.gif" target="_blank"/>
      </Portlet:ComponentGuard>
      <Portlet:ComponentGuard name="Jaffa.Attachment.AttachmentMaintenance">
        <Portlet:Button field="Update" label="[label.Jaffa.Widgets.Button.Update]" image="jaffa/imgs/icons/update.gif"/>
      </Portlet:ComponentGuard>
      <Portlet:ComponentGuard name="Jaffa.Attachment.AttachmentMaintenance">
        <Portlet:Button field="Delete" label="[label.Jaffa.Widgets.Button.Delete]" image="jaffa/imgs/icons/delete.gif"  confirm="[label.Jaffa.Inquiry.delete.confirm]"/>
      </Portlet:ComponentGuard>
    </Portlet:UserGridColumn>
  </logic:equal>
</Portlet:UserGrid>

<logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_WEB_PAGE %>">
  <table>
    <tr>
      <td>
        <logic:greaterThan name="<%= TagHelper.getFormName(pageContext) %>" property="numberOfRecords" value="0">
          <Portlet:Label key='label.Jaffa.Inquiry.numberOfRecords' arg0='<%= Formatter.format(((FinderForm) TagHelper.getFormBase(pageContext)).getNumberOfRecords()) %>'/>
          <logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="moreRecordsExist" value="true">
            <Portlet:Label key='label.Jaffa.Inquiry.moreRecordsPrefix'/> <Portlet:Button field="MoreRecords" type="link" label="[label.Jaffa.Inquiry.moreRecordsExist]"/> <Portlet:Label key='label.Jaffa.Inquiry.moreRecordsSuffix'/> 
          </logic:equal>
        </logic:greaterThan>
      </td>
    </tr>
  </table>
</logic:equal>
