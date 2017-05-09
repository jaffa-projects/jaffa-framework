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
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>

<table>
<tr><td width='100%' style='text-align: left;'><Portlet:Text field='validationRulesFile'/>:</td></tr>
</table>

<table>
  <tr>
    <logic:equal property='fileUpdateable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
    <td colspan='2'><Portlet:EditBox field='fileContents' rows='2' horizontalSizePercentage='60' verticalSizePercentage='60'/></td>
    </logic:equal>
    <logic:equal property='fileUpdateable' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
    <td colspan='2'><BR><Portlet:Text field='fileContents'/></td>
    </logic:equal>
  </tr>
</table>
