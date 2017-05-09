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
    <Portlet:Form action="/table">

<Portlet:Table field="model1" align="center" detail="true" multiSelect="true">
    <Portlet:TableColumn column="Column1" title="String"/>
    <Portlet:TableColumn column="Column2" title="Integer"/>
    <Portlet:TableColumn column="Column3" title="DateOnly"/>
</Portlet:Table>

<BR/>
<BR/>
<Portlet:Button field="quit" label="Quit"/>

        <Portlet:RaiseErrors/>
    </Portlet:Form>
    <Portlet:Footer/>
</body>
</html:html>
