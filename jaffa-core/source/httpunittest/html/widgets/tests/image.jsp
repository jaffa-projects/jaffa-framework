<%@ page language="java" %>
<%@ page import="org.apache.log4j.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<html:html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <Portlet:Header/>
    <LINK rel=STYLESHEET type='text/css' href='css/mainbody.css'>
</head>
<body bgcolor="8fa5ff" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <Portlet:Form action="/image">

<table>
<tr><td>fieldLinkedToCCAndCached:</td><td><Portlet:Image field="fieldLinkedToCCAndCached"/></td></tr>
<tr><td>fieldLinkedToCC:</td><td><Portlet:Image field="fieldLinkedToCC"/></td></tr>
<tr><td>fieldWithCachedModel:</td><td><Portlet:Image field="fieldWithCachedModel"/></td></tr>
<tr><td>field1:</td><td><Portlet:Image field="field1"/></td></tr>
</table>

<BR/>
<BR/>
<Portlet:Button field="quit" label="Quit"/>

        <Portlet:RaiseErrors/>
    </Portlet:Form>
    <Portlet:Footer/>
</body>
</html:html>
