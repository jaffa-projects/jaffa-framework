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



<table width='100%' border='0' cellspacing='0' align='center'>

  <Portlet:Property field='originalFileName'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <table>
        <tr>
          <td><Portlet:RadioButton field='attachmentType' selectValue='L'/><Portlet:Label key='[label.Jaffa.Attachment.Attachment.AttachmentType.L]'/></td>
          <td><span id="attachmentTypeL"><Portlet:EditBox field='localLink'/></span></td>
        </tr>
        <tr>
          <td><Portlet:RadioButton field='attachmentType' selectValue='W'/><Portlet:Label key='[label.Jaffa.Attachment.Attachment.AttachmentType.W]'/></td>
          <td><span id="attachmentTypeW"><Portlet:EditBox field='webLink'/></span></td>
        </tr>
        <tr>
          <td><Portlet:RadioButton field='attachmentType' selectValue='E'/><Portlet:Label key='[label.Jaffa.Attachment.Attachment.AttachmentType.E]'/></td>
          <td><span id="attachmentTypeE"><html:file property='embeddedFile'/><Portlet:Text field='embeddedFileName'/></span></td>
        </tr>
      </table>
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='contentType'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='description'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='remarks'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <Portlet:Property field='supercededBy'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
          <Portlet:EditBox />
    </td>
  </tr>
  </Portlet:Property>

  <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
  <Portlet:Property field='createdOn'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <Portlet:Text />
    </td>
  </tr>
  </Portlet:Property>
  </logic:equal>

  <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
  <Portlet:Property field='createdBy'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <Portlet:Text />
    </td>
  </tr>
  </Portlet:Property>
  </logic:equal>

  <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
  <Portlet:Property field='lastChangedOn'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <Portlet:Text />
    </td>
  </tr>
  </Portlet:Property>
  </logic:equal>

  <logic:equal property='updateMode' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
  <Portlet:Property field='lastChangedBy'>
  <tr> 
    <td class="label">
      <Portlet:Label/>:
    </td>
    <td>
      <Portlet:Text />
    </td>
  </tr>
  </Portlet:Property>
  </logic:equal>

</table>

<%-- A maintenance component may consist of more than one screen. This hidden field is used to identify the current screen being displayed --%>
<tiles:useAttribute name='currentScreenCounter' classname='String'/>
<input type='hidden' name='<%= MaintForm.PARAMETER_CURRENT_SCREEN_COUNTER%>' value='<%= currentScreenCounter %>'/>

<% String formId = TagHelper.getFormTag(pageContext).getHtmlIdPrefix(); %>
<SCRIPT type="text/javascript" src="jaffa/attachment/attachmentmaintenance/hideUnhide.js"></SCRIPT>
<SCRIPT>
    // Register a function to the onClick() event of the RadioButtons
    attachmentType = document.forms['<%= formId %>'].elements['<%= formId + "_attachmentType" %>'];
	if(!attachmentType[0].checked && !attachmentType[1].checked && !attachmentType[2].checked){
    	attachmentType[0].checked=true;
    }
    for (var i = 0; i < attachmentType.length; i++) {attachmentType[i].onclick=function() {hideUnhideAttachmentTypeOptions(attachmentType,'<%= formId %>');};}
    
    // Invoke the functions when the screen is first rendered
    hideUnhideAttachmentTypeOptions(attachmentType,'<%= formId %>');
</SCRIPT>

<script>
  // maintain a reference to the original postForm() function
  var origPostForm = postForm;
  
  // create a new postForm() function, which invokes the original function, catching the exception that gets thrown for invalid file names
  postForm = function (frm, validate) {
    try {
      origPostForm(frm, validate);
    } catch (e) {
      // get rid of the progress-bar
      progress_stop() ;
      eval("if (document.getElementById('" + frm.id + "_EntirePage') != null) {document.getElementById('" + frm.id + "_EntirePage').style.display = 'block';}");
      eval("if (document.getElementById('" + frm.id + "_PushPage') != null) {document.getElementById('" + frm.id + "_PushPage').style.display = 'none';}");
      
      
      var message = '<%=MessageHelper.findMessage("exception.org.jaffa.components.attachment.attachmentNotFound", new Object[]{}).replace("\'","\\'")%>';
      
      // display an error message
      alert(message.replace('{0}',frm.embeddedFile.value));
      
    }
  }
</script>

