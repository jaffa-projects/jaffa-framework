<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.net.*,javax.xml.parsers.*,org.w3c.dom.*"%>
<%@page import="org.jaffa.dwr.services.ConfigurationService,org.jaffa.dwr.services.configdomain.Create,java.util.List"%>


<%
try{
	List<Create> list = ConfigurationService.getInstance().getCreateList();
	
	for(Create c:list) {
	   out.println(c.getJavascript());
	   out.println("</br>");
	}
	
} catch (Exception e) {
    e.printStackTrace();
}
%>

