<%-- The contents of this JSP will appear in the 'contextNav' of ViewerLayout.jsp --%>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum" %>
<%@ page import="org.jaffa.modules.messaging.components.messageviewer.ui.*" %>
<%@ page import="org.jaffa.presentation.portlet.FormBase" %>
<%@ page import="org.jaffa.modules.messaging.components.messageviewer.dto.*" %>

<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/jsp/tiles/object_viewer_2_0/contextNav.jsp' -->
<table cellpadding='0' cellspacing='0' align='center' class='contextNav'>
  <tr>
    <td valign='bottom' width='100%'>
      <table class='contextNavHeader' width='100%'>
        <tr>
          <td class='contextNavTitle'><Portlet:Label key='title.Jaffa.ContextNav'/></td>
	      <td align='right'>
	        <a href="javascript:expand('context','contextExpand')" >
	        <img src='jaffa/imgs/foldingsection/arrowminimize.gif' border='0' name='contextExpand'></a>
          </td>
        </tr>
      </table>
    </td> 
  </tr>
  <tr align='middle'>
    <td>
    <span id="context" syle="display:block">
      <table class='contextNavBody' cellspacing='0' cellpadding='0' width='100%'>
        <tr>
          <td>
            <img src="jaffa/imgs/icons/close.gif">
            <Portlet:Button  field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/>
          </td>
        <tr>          
        <%
            MessageViewerForm myForm = (MessageViewerForm) TagHelper.getFormBase(pageContext);        
            MessageViewerComponent myComp = (MessageViewerComponent) myForm.getComponent();
            MessageViewerOutDto outputDto = myComp.getMessageViewerOutDto();
            if (outputDto != null && Boolean.TRUE.equals(outputDto.getHasPriorityAccess())
            && myComp.getMessageMode() != MessageModeEnum.IN_PROCESS) {	         
        %>
        <tr>
          <td>
            <img src="jaffa/imgs/icons/update.gif">
            <Portlet:Button  field='ChangePriority' type='link' label='[label.Jaffa.Widgets.Button.ChangePriority]' preprocess='true'/>
          </td>
        </tr>    
        <%
	   }
	%>        
      </table>
      </span>
    </td>
  </tr>
</table>
<!-- End of '/jaffa/jsp/tiles/object_viewer_2_0/contextNav.jsp' -->
