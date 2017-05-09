<%-- ---------------------------------------------------------------- 
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.applications.jaffa.modules.admin.components.defaultvalueeditor.ui.DefaultValueEditorForm" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>

<%
    // The file name can be very long. The following code will put line breaks at 100 character intervals
    String defaultValueFile = ((DefaultValueEditorForm) TagHelper.getFormBase(pageContext)).getDefaultValueFile();
    defaultValueFile = StringHelper.addHTMLLineBreak(defaultValueFile, 100);
%>

<table>
<tr><td width='100%' style='text-align: left;'><%= defaultValueFile %>:</td></tr>
</table>

<table width='100%'>
<tr><td>
    <logic:equal property='fileUpdateable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <Portlet:EditBox field='defaultValues' rows='2' horizontalSizePercentage='60' verticalSizePercentage='60'/>
    </logic:equal>
    <logic:equal property='fileUpdateable' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <BR><Portlet:Text field='defaultValues'/>
    </logic:equal>
</td></tr>
</table>
