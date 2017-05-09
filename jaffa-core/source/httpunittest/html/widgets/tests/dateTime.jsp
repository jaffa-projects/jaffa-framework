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
    <Portlet:Form action="/dateTime">

<table>
<tr><td>fieldLinkedToCCAndCached (display as DateOnly):</td><td><Portlet:DateTime field="fieldLinkedToCCAndCached" dateOnly="true"/></td></tr>
<tr><td>fieldLinkedToCC:</td><td><Portlet:DateTime field="fieldLinkedToCC"/></td></tr>
<tr><td>fieldWithCachedModel (display as DateOnly):</td><td><Portlet:DateTime field="fieldWithCachedModel" dateOnly="true"/></td></tr>
<tr><td>fieldWithValidation (doesn't work yet):</td><td><Portlet:DateTime field="fieldWithValidation" validate="true"/></td></tr>
</table>
<BR><BR>
<Portlet:Button field="butt1" label="Post" submit="true" detail="true"/>

<BR/>
<BR/>
<Portlet:Button field="quit" label="Quit"/>

        <Portlet:RaiseErrors/>
    </Portlet:Form>
    <Portlet:Footer/>
</body>
</html:html>
