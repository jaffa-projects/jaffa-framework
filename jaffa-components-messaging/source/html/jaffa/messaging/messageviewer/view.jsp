<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jaffa.modules.messaging.components.messageviewer.MessageModeEnum" %>
<%@ page import="org.jaffa.modules.messaging.components.messageviewer.ui.*" %>
<%@ page import="org.jaffa.presentation.portlet.FormBase" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.model.*" %>
<%@ page import="org.jaffa.datatypes.Formatter" %>
<%@ page import="org.jaffa.modules.messaging.components.messageviewer.dto.*" %>
<%@ page import="org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutDto" %>
<%@ page import="javax.servlet.jsp.tagext.BodyContent" %>

<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>

<%
 MessageViewerForm myForm = (MessageViewerForm) TagHelper.getFormBase(pageContext);
 MessageViewerComponent myComp = (MessageViewerComponent) myForm.getComponent();
 MessageViewerOutDto outputDto = myComp.getMessageViewerOutDto();
 BusinessEventLogFinderOutDto businessEventLogOutDto = outputDto.getBusinessEventLog();
%>

<% if((outputDto != null) && (outputDto.getError() != null)) { %>
  <Portlet:Property field='error'>
    <Portlet:FoldingSection id='Error' key='[label.Jaffa.Messaging.MessageViewer.Error]'>
      <table class='comments'>
        <tr bgcolor="#FFB1AF">
          <td colspan='2'><Portlet:Text/></td>
        </tr>
      </table>
    </Portlet:FoldingSection>
  </Portlet:Property>
<% } %>

<Portlet:FoldingSection id='Header' key='[label.Jaffa.Messaging.MessageViewer.Header]'>
  <table>
    <Portlet:Property field='JMSMessageID'>
      <tr>
        <td class="label"><Portlet:Label key="[label.Jaffa.Messaging.Message.JMSMessageID]"/>:</td>
        <td><Portlet:Text /></td>
      </tr>
    </Portlet:Property>
    <Portlet:Property field='priority'>
      <tr>
        <td class="label"><Portlet:Label key="[label.Jaffa.Messaging.MessageViewer.Priority]"/>:</td>
        <td>
          <% if (myComp.getMessageMode() != MessageModeEnum.IN_PROCESS) { %>
            <Portlet:DropDown/>
          <% } else { %>
            <Portlet:Text/>
          <% } %>
        </td>
      </tr>
    </Portlet:Property>
    <tr>
      <td colspan='2'>
        <Portlet:Grid field='headers' displayOnly='true'>
          <Portlet:GridColumn label='label' columnCssClass='label'><Portlet:Label key='<%= (String) TagHelper.getModelMap(pageContext).get("label") %>'/>:&nbsp;</Portlet:GridColumn>
          <Portlet:GridColumn label='value'><Portlet:Text field='value'/></Portlet:GridColumn>
        </Portlet:Grid>
      </td>
    </tr>
  </table>
</Portlet:FoldingSection>

<Portlet:FoldingSection id='TechnicalDetails' key='[label.Jaffa.Messaging.MessageViewer.TechnicalDetails]'>
  <table>
    <Portlet:Property field='JMSDestination'>
      <tr>
        <td class="label"><Portlet:Label key="[label.Jaffa.Messaging.Message.JMSDestination]"/>:</td>
        <td><Portlet:Text /></td>
      </tr>
    </Portlet:Property>
    <Portlet:Property field='JMSDeliveryMode'>
      <tr>
        <td class="label"><Portlet:Label key="[label.Jaffa.Messaging.Message.JMSDeliveryMode]"/>:</td>
        <td><Portlet:Text /></td>
      </tr>
    </Portlet:Property>
    <Portlet:Property field='JMSTimestamp'>
      <tr>
        <td class="label"><Portlet:Label key="[label.Jaffa.Messaging.Message.JMSTimestamp]"/>:</td>
        <td><Portlet:Text /></td>
      </tr>
    </Portlet:Property>
    <Portlet:Property field='JMSCorrelationID'>
      <tr>
        <td class="label"><Portlet:Label key="[label.Jaffa.Messaging.Message.JMSCorrelationID]"/>:</td>
        <td><Portlet:Text /></td>
      </tr>
    </Portlet:Property>
    <Portlet:Property field='JMSReplyTo'>
      <tr>
        <td class="label"><Portlet:Label key="[label.Jaffa.Messaging.Message.JMSReplyTo]"/>:</td>
        <td><Portlet:Text /></td>
      </tr>
    </Portlet:Property>
    <Portlet:Property field='JMSRedelivered'>
      <tr>
        <td class="label"><Portlet:Label key="[label.Jaffa.Messaging.Message.JMSRedelivered]"/>:</td>
        <td><Portlet:Text /></td>
      </tr>
    </Portlet:Property>
    <Portlet:Property field='JMSType'>
      <tr>
        <td class="label"><Portlet:Label key="[label.Jaffa.Messaging.Message.JMSType]"/>:</td>
        <td><Portlet:Text /></td>
      </tr>
    </Portlet:Property>
    <Portlet:Property field='JMSExpiration'>
      <tr>
        <td class="label"><Portlet:Label key="[label.Jaffa.Messaging.Message.JMSExpiration]"/>:</td>
        <td><Portlet:Text /></td>
      </tr>
    </Portlet:Property>
    <tr>
      <td colspan='2'>
        <Portlet:Grid field='technicalDetails' displayOnly='true'>
          <Portlet:GridColumn label='label' columnCssClass='label'><Portlet:Text field='label'/>:&nbsp;</Portlet:GridColumn>
          <Portlet:GridColumn label='value'><Portlet:Text field='value'/></Portlet:GridColumn>
        </Portlet:Grid>
      </td>
    </tr>
  </table>
</Portlet:FoldingSection>

<Portlet:Property field='payLoad'>
  <Portlet:FoldingSection id='Message' key='[label.Jaffa.Messaging.MessageViewer.Message]' closed='true' hideIfNoWidgets='false'>

    <%
      String clazzName = null, jspName = null;
      if (myComp.getMessageViewerOutDto().getHeaderElements() != null) {
        for (int i = 0; i < myComp.getMessageViewerOutDto().getHeaderElements().length; i++) {
          HeaderElementDto h = myComp.getMessageViewerOutDto().getHeaderElements()[i];
          if("jaffa_dataBeanClassName".equals(h.getName())) {
            clazzName = h.getValue();
            break;
          }
        }
      }

      if(clazzName != null) {
        // Trim off package name
        int pos = clazzName==null?-1:clazzName.lastIndexOf(".");
        clazzName = pos<0 ? clazzName : clazzName.substring(pos+1);
        jspName = "/jaffa/messaging/messageviewer/"+clazzName+".jsp";
      }

      /************************* NOTE ******************************************
      * The getResource() method works only if a JSP is deployed on the server.*
      * It does not work if the JSP is precompiled, and hence not deployed.    *
      * We'll simply include the JSP. Some containers do not throw any error   *
      * if the JSP is not found. Hence the use of the BodyContent to determine *
      * existence of the JSP.                                                  *
      *************************************************************************/
      // if(jspName!=null && request.getSession().getServletContext().getResource(jspName) != null) {
      //   request.setAttribute(MessageViewerComponent.class.getName(), myComp);
      String customContents = null;
      if (jspName != null) {
        BodyContent bodyContentForCustomJSP = pageContext.pushBody();
        request.setAttribute(MessageViewerComponent.class.getName(), myComp);
        try {
            pageContext.include(jspName);
        } catch (Exception e) {
            // A container MAY throw an exception in case it doesn't find the JSP
        }
        request.removeAttribute(MessageViewerComponent.class.getName());
        pageContext.popBody();
        customContents = bodyContentForCustomJSP.getString();
      }
      
      if (customContents != null && customContents.length() > 0) {
    %>
        <%-- <jsp:include page="<%=jspName%>"/> --%>
        <%= customContents %>
    <% } else { %>
      <table class='comments'>
        <tr>
          <td colspan='2'><pre><Portlet:Text/></pre></td>
        </tr>
      </table>
    <% } %>

  </Portlet:FoldingSection>
</Portlet:Property>

<Portlet:FoldingSection id='BusinessEventLog' key='[label.Jaffa.Messaging.MessageViewer.BusinessEventLog]' closed='true'>
  <table>
    <tr>
      <td>
        <Portlet:UserGrid field="relatedBusinessEventLog" userGridId="jaffa_messaging_messageViewer_businessEventLog">
          <Portlet:Property field='loggedOn' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
            <Portlet:UserGridColumn dataType="Date" label='[label.Jaffa.Messaging.BusinessEventLog.LoggedOn]'>
              <Portlet:Text />
            </Portlet:UserGridColumn>
          </Portlet:Property>
          <Portlet:Property field='messageType' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
            <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.MessageType]'>
              <Portlet:Text />
            </Portlet:UserGridColumn>
          </Portlet:Property>
          <Portlet:Property field='messageText' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
            <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.MessageText]'>
              <Portlet:Text />
            </Portlet:UserGridColumn>
          </Portlet:Property>
          <Portlet:Property field='processName' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
            <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.ProcessName]'>
              <Portlet:Text />
            </Portlet:UserGridColumn>
          </Portlet:Property>
          <Portlet:Property field='subProcessName' propertyClass='org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto'>
            <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Messaging.BusinessEventLog.SubProcessName]'>
              <Portlet:Text />
            </Portlet:UserGridColumn>
          </Portlet:Property>
          <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
            <Portlet:ComponentGuard name="Jaffa.Messaging.BusinessEventLogViewer">
              <Portlet:Button field="View" label="[label.Jaffa.Widgets.Button.View]" image="jaffa/imgs/icons/detail.gif" target="_blank"/>
            </Portlet:ComponentGuard>
          </Portlet:UserGridColumn>
        </Portlet:UserGrid>
      </td>
    </tr>
    <tr>
      <td>
        <logic:greaterThan name="<%= TagHelper.getFormName(pageContext) %>" property="numberOfRecords" value="0">
          <Portlet:Label key='label.Jaffa.Inquiry.numberOfRecords' arg0='<%= Formatter.format(((MessageViewerForm) TagHelper.getFormBase(pageContext)).getNumberOfRecords()) %>'/>
          <logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="moreRecordsExist" value="true">
            <Portlet:Label key='label.Jaffa.Inquiry.moreRecordsPrefix'/> <Portlet:Button field="MoreRecords" type="link" label="[label.Jaffa.Inquiry.moreRecordsExist]" target="_blank"/> <Portlet:Label key='label.Jaffa.Inquiry.moreRecordsSuffix'/>
          </logic:equal>
        </logic:greaterThan>
      </td>
    </tr>
  </table>
</Portlet:FoldingSection>
