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
<h1>This is a test of the tree widget</h1>
<Portlet:Form action="/tree">

    <Portlet:Tree field='tree'>
        <Portlet:TreeNodeType name='Session' nodeType='SessionType' nodeText='Session Text' image='jaffa/imgs/checkbox/false.gif' />
        <Portlet:TreeNodeType name='Component' nodeType='ComponentType' nodeText='Component Text' image='jaffa/imgs/checkbox/check.gif' />
    </Portlet:Tree>

    <BR/>
    <BR/>
    <Portlet:Button field="quit" label="Quit"/>

    <Portlet:RaiseErrors/>
</Portlet:Form>
<Portlet:Footer/>
</body>
</html:html>
