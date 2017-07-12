<%@page contentType="text/html"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.jaffa.config.*"%>
<%@page import="org.jaffa.presentation.portlet.component.ComponentDefinition"%>
<%@page import="org.jaffa.presentation.portlet.component.componentdomain.*"%>


<html>
    <head>
        <title>Component Unit Tests</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

    <body bgcolor="#FFFFFF" leftmargin="20" topmargin="20" marginwidth="20" marginheight="20">
        <table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr><td><font face="Arial, Helvetica, sans-serif" size="6" color="103563">
            <b>Component Testing</b></font></td></tr>
            <tr bgcolor="103563"><td width="2"></td></tr>
            <tr bgcolor="7197ca"><td height="2"></td></tr>
        </table>
        <font face="Arial, Helvetica, sans-serif"><OL>
<%
try {

	    Map<String, ComponentDefinition> compList = Loader.getComponentPool();
	    for (ComponentDefinition cd : compList.values()) {
			try {
	            String componentName = cd.getComponentName();
	            out.println("<UL><A href='../../startComponent.do?component=" + componentName + "&finalUrl=unittest'>" + componentName + "</A></UL>");
			} catch (Exception e) {
				
			}        	
        }
} catch (Exception e) {
    e.printStackTrace();
} 
%>

        </OL></font>
    </body>
</html>
