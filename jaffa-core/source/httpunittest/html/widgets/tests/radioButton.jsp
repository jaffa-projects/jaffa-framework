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
    <Portlet:Form action="/radioButton">

<table>
<tr><td>fieldLinkedToCCAndCached:</td><td>
    <table><tr>
    <td><Portlet:RadioButton field="fieldLinkedToCCAndCached" selectValue="FLTCCACValue1"/>Value1</td>
    <td><Portlet:RadioButton field="fieldLinkedToCCAndCached" selectValue="FLTCCACValue2"/>Value2</td>
    <td><Portlet:RadioButton field="fieldLinkedToCCAndCached" selectValue="FLTCCACValue3"/>Value3</td>
    </tr></table>
</td></tr>
<tr><td>fieldLinkedToCC:</td><td>
    <table><tr>
    <td><Portlet:RadioButton field="fieldLinkedToCC" selectValue="FLTCCValue1"/>Value1</td>
    <td><Portlet:RadioButton field="fieldLinkedToCC" selectValue="FLTCCValue2"/>Value2</td>
    <td><Portlet:RadioButton field="fieldLinkedToCC" selectValue="FLTCCValue3"/>Value3</td>
    </tr></table>
</td></tr>
<tr><td>fieldWithCachedModel:</td><td>
    <table><tr>
    <td><Portlet:RadioButton field="fieldWithCachedModel" selectValue="FWCMValue1"/>Value1</td>
    <td><Portlet:RadioButton field="fieldWithCachedModel" selectValue="FWCMValue2"/>Value2</td>
    <td><Portlet:RadioButton field="fieldWithCachedModel" selectValue="FWCMValue3"/>Value3</td>
    </tr></table>
</td></tr>
<tr><td>fieldWithValidation (doesn't work yet):</td><td>
    <table><tr>
    <td><Portlet:RadioButton field="fieldWithValidation" selectValue="FWVValue1"/>Value1</td>
    <td><Portlet:RadioButton field="fieldWithValidation" selectValue="FWVValue2"/>Value2</td>
    <td><Portlet:RadioButton field="fieldWithValidation" selectValue="FWVValue3"/>Value3</td>
    </tr></table>
</td></tr>
</table>
<BR><BR>
<Portlet:Button field="butt1" label="Post" detail="true"/>

<BR/>
<BR/>
<Portlet:Button field="quit" label="Quit"/>

        <Portlet:RaiseErrors/>
    </Portlet:Form>
    <Portlet:Footer/>
</body>
</html:html>
