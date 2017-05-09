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
    <Portlet:Form action="/editBox">

    <table>
        <tr><td>fieldLinkedToCCAndCached:</td><td><Portlet:EditBox field="fieldLinkedToCCAndCached"/></td></tr>
        <tr><td>fieldLinkedToCC:</td><td><Portlet:EditBox field="fieldLinkedToCC"/></td></tr>
        <tr><td>fieldWithCachedModel:</td><td><Portlet:EditBox field="fieldWithCachedModel" onValidate="if(field.value!='AAA') error='Must be AAA';"/> - onValidate attribute used to insert some custom validation code.</td></tr>
        <tr><td>fieldWithKeyBoard:</td><td><Portlet:EditBox field="fieldWithKeyBoard" lookup="true"/></td></tr>
        <tr><td>fieldWithValidation:</td><td><Portlet:EditBox field="fieldWithValidation" validate="true"/></td></tr>
        <tr><td>textArea:</td><td><Portlet:EditBox field="textArea" rows="5"/></td></tr>
        <tr><td>password field:</td><td><Portlet:EditBox field="fieldWithKeyBoard" password="true"/></td></tr>

        <tr><td>String DataType</td><td><Portlet:EditBox field="dataTypeString"/>Maxsize="8" MinSize="4" Expression="[0-9]+"</td></tr>
        <tr><td>Integer DataType</td><td><Portlet:EditBox field="dataTypeInteger"/>MaxSize="8" MinValue="100" MaxValue="200"</td></tr>
        <tr><td>Decimal DataType</td><td><Portlet:EditBox field="dataTypeDecimal"/>MaxSize="4" maxDigits="2" minValue="0.0" maxValue="20.5"</td></tr>
        <tr><td>DateOnly DataType</td><td><Portlet:EditBox field="dataTypeDateOnly"/><Portlet:Calendar field="dataTypeDateOnly"/></td></tr>
        <tr><td>DateTime DataType</td><td><Portlet:EditBox field="dataTypeDateTime"/><Portlet:Calendar field="dataTypeDateTime"/></td></tr>       
    </table>

    <BR/>
    <BR/>
    (Note: also test that the auto-submit works on the password field. Bug#710928)
    <BR/>
    <BR/>
    <Portlet:Button field="quit" type="link" label="Quit" submit="true" preprocess="false"/>

    <Portlet:RaiseErrors/>
</Portlet:Form>
<Portlet:Footer/>
</body>
</html:html>
