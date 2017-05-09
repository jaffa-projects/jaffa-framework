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
    <Portlet:Form action="/checkBox">

<table>
<tr>
   <td>Type</td>
   <td>&nbsp;</td>
   <td>Editable</td>
   <td>Non-Editable</td>
</tr>
<tr>
   <td>html</td>
   <td>fieldLinkedToCCAndCached:</td>
   <td><Portlet:CheckBox field="fieldLinkedToCCAndCached"/></td>
   <td>N/A
	<%-- // This will give an error: The checkbox does not support display only HTML
	     <Portlet:CheckBox field="fieldWithCachedModel" type="html" displayOnly="true"/>
	--%>
   </td>
</tr>
<tr>
   <td>image</td>
   <td>fieldLinkedToCC:</td>
   <td><Portlet:CheckBox field="fieldLinkedToCC" type="image"/></td>
   <td><Portlet:CheckBox field="fieldLinkedToCCAndCached" type="image" displayOnly="true"/></td>
</tr>
<tr>
   <td>html</td>
   <td>fieldWithCachedModel:</td>
   <td><Portlet:CheckBox field="fieldWithCachedModel" type="html"/></td>
   <td>N/A</td>
</tr>
<tr>
   <td>custom</td>
   <td>fieldWithValidation (doesn't work yet):</td>
   <td><Portlet:CheckBox field="fieldWithValidation" type="custom" imageOn="widgets/tests/testimage2_up.gif" imageOff="widgets/tests/testimage2_down.gif"/></td>
   <td><Portlet:CheckBox field="fieldWithCachedModel" type="custom" displayOnly="true" imageOn="widgets/tests/testimage2_up.gif" imageOff="widgets/tests/testimage2_down.gif"/></td>
</tr>

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
