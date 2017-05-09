<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- //JAFFA-OVERWRITE: As long as this line exists, this file will be overwritten in all future runs of the pattern generator --
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


<html:html>
    <HEAD>
  <SCRIPT type="text/javascript" src="jaffa/js/panels/reset.js"></SCRIPT>
        <Portlet:Header/>
    </HEAD>
    
    <BODY leftMargin="0" topMargin="0" marginwidth="0" marginheight="0">
    <div id="iframe" style="display:none"></div>
<Portlet:Form action="/usertimeentry_userTimeEntryMaintenance">
<table width='100%' border='0' cellspacing='0' align='center'>
  <tr> 
    <td class="label">
     UserName:
    </td>
    <td>
     <Portlet:EditBox field='userName' />
    </td>
  </tr>

  <tr> 
    <td class="label">
      ProjectCode:
    </td>
    <td>
      <Portlet:DropDown field='projectCode'/>
    </td>
  </tr>

  <tr> 
    <td class="label">
      Activity:
    </td>
    <td>
      <Portlet:DropDown dependant1="projectCode" key1="ProjectCode" domain="org.jaffa.applications.test.modules.time.domain.ActivityCode" dropdownValueField="Activity" dropdownDescField="Description" field='activity' />
    </td>
  </tr>

  <tr> 
    <td class="label">
      Task:
    </td>
    <td>
      <Portlet:DropDown dependant1="activity" key1="Activity" domain="org.jaffa.applications.test.modules.time.domain.TaskCode" dropdownValueField="Task" dropdownDescField="Description" field='task' />        
    </td>
  </tr>
  <tr> 
    <td class="label">
      PeriodStart:
    </td>
    <td>
      <Portlet:DateTime field='periodStart' />
    </td>
  </tr>
  <tr> 
    <td class="label">
      PeriodEnd:
    </td>
    <td>
     <Portlet:DateTime field='periodEnd' />
    </td>
  </tr>
  <tr>    
    <td></td><Portlet:Button field='Finish' type='link' label='[label.Jaffa.Widgets.Button.Finish]' guarded='true' submit='true'/></td>        
  </tr>
</table>


        <Portlet:RaiseErrors/>
        </Portlet:Form>
        <Portlet:Footer/>
    </BODY>
</html:html>
