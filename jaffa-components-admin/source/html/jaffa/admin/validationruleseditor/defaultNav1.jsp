<%-- ---------------------------------------------------------------- 
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'defaultNav' of Layout.jsp --%>
<%@ page import='org.jaffa.presentation.portlet.widgets.taglib.TagHelper' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- Start of '/jaffa/admin/validationruleseditor/defaultNav1.jsp' -->
<TABLE width='100%' class='defaultNav'>
  <TR>
    <%-- Add a Previous button, if available, or a Cancel button --%>
    <TD align='left' width='1%'><Portlet:Button field='Previous' image='jaffa/imgs/icons/prevArrows.gif' label='[label.Jaffa.Widgets.Button.Previous]' preprocess='false'/></TD>
    <TD align='left' valign='middle'><Portlet:Button field='Previous' type='link' label='[label.Jaffa.Widgets.Button.Previous]' preprocess='false'/></TD>

    <TD width='100%' align='right' valign='middle'><Portlet:Button field='Finish' type='link' label='[label.Jaffa.Widgets.Button.Finish]' submit='true' guarded='true'/></TD>
    <TD align='right' width='1%'><Portlet:Button field='Finish' image='jaffa/imgs/icons/nextArrows.gif' label='[label.Jaffa.Widgets.Button.Finish]' guarded='true'/></TD>
  </TR>
</TABLE>
<!-- End of '/jaffa/admin/validationruleseditor/defaultNav1.jsp' -->
