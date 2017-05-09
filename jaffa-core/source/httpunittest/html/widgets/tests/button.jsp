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
    <Portlet:Form action="/button">

<table>
<tr>
	<td>Text Button:</td>
	<td><Portlet:Button field="butt1" label="label for text button" text="true" confirm="Confirm for Text Button"/></td>
</tr>
<tr>
	<td>Regular Button:</td>
	<td><Portlet:Button field="butt2" label="label for regular button" confirm="Confirm for Regular Button"/></td>
</tr>
<tr>
	<td>Image Button:</td>
	<td><Portlet:Button field="butt3" label="label for image button" image="widgets/tests/testimage1_up.gif" confirm="Confirm for Image Button"/></td>
</tr>
<%-- can't have two buttons as submit = true, add this in if you need to test it
<tr>
	<td>Rollover Image Button(this is also defined as a 'Submit'):</td>
	<td><Portlet:Button field="butt4" label="label for rollover image button" image="widgets/tests/testimage1_up.gif" rolloverImage="widgets/tests/testimage1_down.gif" submit="true" confirm="Confirm for Submit Button"/></td>
</tr>
--%>
<%-- remove this section if you comment in the above one --%>
<tr>
	<td>Rollover Image Button:</td>
	<td><Portlet:Button field="butt4" label="label for rollover image button" image="widgets/tests/testimage1_up.gif" rolloverImage="widgets/tests/testimage1_down.gif" confirm="Confirm This Button"/></td>
</tr>
<%-- end --%>
<tr>
	<td>Link Button:</td>
	<td><Portlet:Button field="butt5" label="Google" type="link"/></td>
</tr>
<%-- remove this section if you comment in the submit button above above one --%>
<tr>
	<td>CSS-style Button:</td>
	<td><Portlet:Button field="butt6" label="Go Gray" type="sized" submit="true"/></td>
</tr>
<%-- end --%>
<tr>
	<td>CSS-style Button (width Factor):</td>
	<td><Portlet:Button field="butt7" label="Switch To Color" type="sized" widthFactor="10"/></td>
</tr>
<tr>
	<td>Regular Button (Class override):</td>
	<td><Portlet:Button field="butt8" label="label for regular button" classOverride="CSSClass" confirm="Confirm for Regular Button"/></td>
</tr>
<tr>
	<td>Image Button (Class Extn):</td>
	<td><Portlet:Button field="butt9" label="label for image button" classExtn="Extn" image="widgets/tests/testimage1_up.gif" confirm="Confirm for Image Button"/></td>
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
