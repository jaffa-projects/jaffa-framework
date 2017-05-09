<%-- ---------------------------------------------------------------- 
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'contextNav' of Layout.jsp --%>
<%@ page import='org.jaffa.util.MessageHelper' %>
<%@ page import='org.jaffa.presentation.portlet.widgets.taglib.TagHelper' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/admin/defaultvalueeditor/contextNav.jsp' -->
<TABLE cellpadding='0' cellspacing='0' align='center' class='contextNav'>
  <TR>
    <TD valign='bottom' width='100%'>
      <table class='contextNavHeader' width='100%'>
        <tr>
          <td class='contextNavTitle'><Portlet:Label key='title.Jaffa.ContextNav'/></td>
          <td align='right'><a href="javascript:expand('context','contextExpand')" ><img src='jaffa/imgs/foldingsection/arrowminimize.gif' border='0' name='contextExpand'></a></td>
        </tr>
      </table>
    </TD> 
  </TR>
  <TR align='middle'>
    <TD>
      <span id="context" syle="display:block">
      <table class='contextNavBody' cellspacing='0' cellpadding='0' width='100%'>
        <%-- ********* COMMONLY USED BUTTONS *********
        <TR><TD><img src='jaffa/imgs/icons/save.gif'><Portlet:Button field='Save' type='link' label='[label.Jaffa.Widgets.Button.Save]' guarded='true'/></TD></TR>
        <TR><TD><img src='jaffa/imgs/icons/finish.gif'><Portlet:Button field='Finish' type='link' label='[label.Jaffa.Widgets.Button.Finish]' guarded='true'/></TD></TR>
        <TR><TD><img src='jaffa/imgs/icons/refresh.gif'><Portlet:Button field='Refresh' type='link' label='[label.Jaffa.Widgets.Button.Refresh]' guarded='true' preprocess='false'/></TD></TR>
        <TR><TD><img src='jaffa/imgs/icons/delete.gif'><Portlet:Button field='Delete' type='link' label='[label.Jaffa.Widgets.Button.Delete]' preprocess='false' guarded='true' confirm='[label.Jaffa.Inquiry.delete.confirm]'/></TD></TR>
        <TR><TD><img src='jaffa/imgs/icons/clear.gif'><a href='javascript:resetForm()'><%= MessageHelper.replaceTokens(pageContext, "[label.Jaffa.Widgets.Button.Clear]") %></a></TD></TR>
        <TR><TD><img src='jaffa/imgs/icons/close.gif'><Portlet:Button field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/></TD></TR>
        --%>


        <logic:equal property='currentScreenCounter' name='<%= TagHelper.getFormName(pageContext) %>' value='1'>
          <logic:equal property='fileUpdateable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
          <TR><TD><img src='jaffa/imgs/icons/save.gif'><Portlet:Button field='save' type='link' label='[label.Jaffa.Widgets.Button.Save]' guarded='true'/></TD></TR>
          <TR><TD><img src='jaffa/imgs/icons/finish.gif'><Portlet:Button field='finish' type='link' label='[label.Jaffa.Widgets.Button.Finish]' guarded='true'/></TD></TR>
          <TR><TD><img src='jaffa/imgs/icons/refresh.gif'><Portlet:Button field='refresh' type='link' label='[label.Jaffa.Widgets.Button.Refresh]' guarded='true'/></TD></TR>
          </logic:equal>
        </logic:equal>


        <logic:equal property='previousActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
          <TR><TD><img src='jaffa/imgs/icons/prev.gif'><Portlet:Button field='Previous' type='link' label='[label.Jaffa.Widgets.Button.Previous]' preprocess='false'/></TD></TR>
        </logic:equal>
        <logic:equal property='nextActionAvailable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
          <TR><TD><img src='jaffa/imgs/icons/next.gif'><Portlet:Button field='Next' type='link' label='[label.Jaffa.Widgets.Button.Next]' guarded='true'/></TD></TR>
        </logic:equal>
        <TR><TD><img src='jaffa/imgs/icons/cancel.gif'><Portlet:Button field='Cancel' type='link' label='[label.Jaffa.Widgets.Button.Cancel]' preprocess='false'/></TD></TR>
      </table>
      </span>
    </TD>
  </TR>
</TABLE>
<!-- End of '/jaffa/admin/defaultvalueeditor/contextNav.jsp' -->
