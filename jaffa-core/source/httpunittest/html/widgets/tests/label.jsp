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
    <Portlet:Form action="/label">

<table>
<tr><td>Label with Meta Field:</td><td><Portlet:Label domain="org.jaffa.presentation.portlet.widgets.tests.Label" field="code"/></td></tr>
<tr><td>Label with Meta Field and Token:</td><td><Portlet:Label domain="org.jaffa.presentation.portlet.widgets.tests.Label" field="description"/></td></tr>
<tr><td>Label with Meta Field and Key:</td><td><Portlet:Label domain="org.jaffa.presentation.portlet.widgets.tests.Label" field="status" key="label.Common.Status"/></td></tr>
<tr><td>Label with Key and Linked Tokens:</td><td><Portlet:Label key="label.Common.LinkToLinkToQuit"/></td></tr>
<tr><td>Label with Key and Args:</td><td><Portlet:Label key="label.Common.MessageWithArgs" arg0="static-argument" arg1="[label.Common.LinkToLinkToQuit]"/></td></tr>
</table>
<BR/>
<BR/>
<Portlet:Button field="quit" label="Quit"/>

        <Portlet:RaiseErrors/>
    </Portlet:Form>
    <Portlet:Footer/>
</body>
</html:html>
