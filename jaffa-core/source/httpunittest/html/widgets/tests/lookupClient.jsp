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
    <Portlet:Form action="/lookupClient">

<table>
<tr>
<td>Field1:</td>
<td><Portlet:EditBox field="field1"/></TD>
<td>Field2:</td>
<td><Portlet:EditBox field="field2"/></TD>
<td><Portlet:Lookup component="Portlet.Widget.Test.Lookup" bypassCriteriaScreen="true" staticParameters="status=O;condition=New" targetFields="field1=partNo;field2=segCode">Lookup Without Dynamic Parameters</Portlet:Lookup></td>
<td><Portlet:Lookup component="Portlet.Widget.Test.Lookup" dynamicParameters="partNo=field1;segCode=field2" staticParameters="status=O;condition=New" targetFields="field1=partNo;field2=segCode">Lookup With Dynamic Parameters</Portlet:Lookup></td>
</tr>
</table>
<BR/>
<BR/>
<Portlet:Button field="quit" label="Quit"/>

        <Portlet:RaiseErrors/>
    </Portlet:Form>
    <Portlet:Footer/>
</body>
</html:html>
