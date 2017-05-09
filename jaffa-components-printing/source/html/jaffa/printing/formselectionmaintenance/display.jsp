<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page language="java" session="false"%>
<%@ page import="org.jaffa.presentation.portlet.session.UserSession" %>
<%@ page import="org.jaffa.modules.printing.components.formselectionmaintenance.ui.FormSelectionMaintenanceComponent" %>
<%@ page import="org.jaffa.presentation.portlet.FormKey" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body  onload="functionLoad()">
<%!
                public String  getFileNames(HttpServletRequest request){
                    FormKey fk = (FormKey) request.getAttribute(FormKey.class.getName());                                                 
                    FormSelectionMaintenanceComponent component = (FormSelectionMaintenanceComponent) UserSession.getUserSession(request).getComponent(fk.getComponentId());
                    return component.getFileNames();                    
    }
%>
<script language="javascript">
function functionLoad(){
  var contextPath = "<%=request.getContextPath()%>";
        var fileNames = "<%= getFileNames(request)%>";
  var path = fileNames.split(",");
  
  for(i=0;i<path.length-1;i++) {
    try {

    <%-- If running on SCO then open PDF as attachment since PDF viewer (XPDF) can not be displayed inline --%>
    if (navigator.userAgent.indexOf("UnixWare")!=-1)
      var w=window.open(contextPath + "/ShowPdf?id="+path[i]+"&attachment=true");
    else
      var w=window.open(contextPath + "/ShowPdf?id="+path[i]);

      if(w==null) {
        alert('<%= MessageHelper.findMessage("label.Jaffa.Printing.FormSelectionMaintenance.FailedToLaunchWindowShowFormDisablePopUpBlockersForSite", null)  %>');
        return;
      }
    } catch(e) {
        alert('<%= MessageHelper.findMessage("label.Jaffa.Printing.FormSelectionMaintenance.FailedToLaunchWindowShowFormDisablePopUpBlockersForSite", null)  %>');
        return;
    }  
  }
  <%-- If running on SCO Unix then go back one page to calling screen, otherwise close display.jsp window --%>
  //alert("navigator is " + navigator.userAgent);
  if (navigator.userAgent.indexOf("UnixWare")!=-1)
    history.go(-1);
  else{   
    // close window if history is less than 2
    //alert("in display jsp : window.history.length=" + window.history.length);    
    if (window.history.length <= 2) {
      // Works for IE6, IE7, IE8 and Firefox 3.6.3
      window.open('', '_self');
      window.close();
    } else {       
      history.go(-2);
    }
  }
}
</script>
<%= MessageHelper.findMessage("label.Jaffa.Printing.FormSelectionMaintenance.LaunchingGeneratedFormsPleaseWaitMsg", null)  %>
</body>
</html>
