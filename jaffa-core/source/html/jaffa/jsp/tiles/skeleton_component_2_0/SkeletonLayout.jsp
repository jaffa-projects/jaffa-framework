<%-- ---------------------------------------------------------------- 
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'body' of the Main Layout --%>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="org.jaffa.util.BeanHelper" %>
<%@ page import="org.jaffa.components.maint.MaintForm" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>
<%! private static Logger log = Logger.getLogger("jaffa.jsp.tiles.skeleton_component_2_0.SkeletonLayout"); %>

<!-- Start of '/jaffa/jsp/tiles/skeleton_component_2_0/SkeletonLayout.jsp' -->
<tiles:useAttribute name='action' classname='String'/>
<tiles:useAttribute name='enctype' classname='String' ignore='true'/>

<%-- Change the title to the current screen title --%>
<tiles:useAttribute name='title' classname='String' ignore='true'/>
<tiles:useAttribute name='screenTitle' classname='String' ignore='true'/>
<%
if (screenTitle != null)
  title = screenTitle;
%>

<table height="100%" width="100%" cellspacing="0" cellpadding="0" border="0" valign='top' align="left">
  <tr>
    <td>
      <table height="100%" class="leftNav" cellspacing="0" cellpadding="0" border="0" valign='top'>
        <tr>
          <td>
            <Portlet:Form action='<%= action %>' enctype='<%=enctype%>'>
          
            <%-- Set the currentScreenCounter in the FormBean. This helps maintain the correct state when a link is clicked on the historyNav --%>
            <tiles:useAttribute name='currentScreenCounter' classname='String'/>
            <%
            try {
                // Use reflection to invoke setCurrentScreenCounter on the FormBean
                BeanHelper.setField(TagHelper.getFormBase(pageContext), "currentScreenCounter", currentScreenCounter);
            } catch (Exception e) {
                // log a warning
                log.warn("Error thrown while trying to set the currentScreenCounter on the Form", e);
            }
            %>

            <tiles:insert attribute='contextNav' ignore='true'/>
          </td>
        </tr>
        <tr>
          <td>
            <a id="SectionMenu"></a> <%-- The sectionNav.jsp file will insert the code here! --%>
          </td>
        </tr>
        <tr height="100%"><td>&nbsp;</td></tr>
        <tr class="leftNavFooter"><td>&nbsp;</td></tr>
      </table>
    </td>
    <td width="100%" valign='top' align="left">
      <table width="100%" cellspacing="0" cellpadding="0" border="0">
        <%-- This will display the HistoryNav --%>
        <tr>
          <td>
            <tiles:insert attribute='historyNav'>
              <tiles:put name='title' value='<%= title %>'/>
            </tiles:insert>
          </td>
        </tr>
        <tr>
          <td>
            <table cellspacing="0" cellpadding="0" border="0">
              <tr>
                <td>
                  <tiles:insert attribute='body'/>
                </td>
              </tr>
            </table>  
          </td>
        </tr>

        <tr>
          <td>
            <tiles:insert attribute='defaultNav' ignore='true'/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<%-- A component may consist of more than one screen. This hidden field is used to identify the current screen being displayed --%>
<input type='hidden' name='<%= MaintForm.PARAMETER_CURRENT_SCREEN_COUNTER%>' value='<%= currentScreenCounter %>'/>

<Portlet:RaiseErrors/>
</Portlet:Form>
<jsp:include page="jaffa/jsp/tiles/sectionNav.jsp"/>
<!-- End of '/jaffa/jsp/tiles/skeleton_component_2_0/SkeletonLayout.jsp' -->
