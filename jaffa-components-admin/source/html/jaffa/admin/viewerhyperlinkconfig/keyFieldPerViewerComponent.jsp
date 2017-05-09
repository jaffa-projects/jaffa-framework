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

<table width='100%' class='sectionheader'>
<tr><td class='sectiontitle'><Portlet:Label key='label.Jaffa.Admin.ViewerHyperlinkConfig.KeyFieldPerViewerComponent.FileName'/></td></tr>
</table>

<table>
<tr><td width='100%' style='text-align: left;'><Portlet:Text field='keyFieldPerViewerComponentFileName'/>:</td></tr>
</table>

<table width='100%'>
<tr><td>
    <logic:equal property='file1Updateable' name='<%= TagHelper.getFormName(pageContext) %>' value='true'>
        <Portlet:EditBox field='keyFieldPerViewerComponentContents' rows='2' horizontalSizePercentage='60' verticalSizePercentage='60'/>
    </logic:equal>
    <logic:equal property='file1Updateable' name='<%= TagHelper.getFormName(pageContext) %>' value='false'>
        <BR><Portlet:Text field='keyFieldPerViewerComponentContents'/>
    </logic:equal>
</td></tr>
</table>
