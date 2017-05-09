<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c' %>
<%@ page import='org.jaffa.components.finder.QuerySaver' %>
<%@ page import='org.jaffa.presentation.portlet.component.ComponentManager' %>
<%@ page import='org.jaffa.components.finder.SavedQueryBean' %>
<%@ page import='java.util.List' %>

<script src="js/searchHelp.js"></script>
<script type="text/javascript">
function removeSavedQueryShortcut(cName, qId) {
  var el = document.getElementById(cName+"_"+qId);
  var imgE = document.getElementById(cName+"_"+qId+"_img");
  imgE.src = "jaffa/jsp/iframe/removeSavedQueryShortcut.jsp?componentName="+cName+"&savedQueryId="+qId;  
  el.parentNode.removeChild(el);
}
</script>
<table height="100%" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
    <td  valign="top"> 
      <table border="0" cellspacing="5" cellpadding="0" valign="top" class="mainsection" width="95%">
        <tr> 
          <td class="top"><Portlet:Label key="label.Jaffa.DesktopError.Error"/></td>
        </tr>
        <tr> 
          <td class="trim"><Portlet:Label key="label.Jaffa.DesktopError.Error.Body"/></td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
        </tr>

        <tr> 
          <td>&nbsp;</td>
        </tr>
      </table>
    </td>
</table>
