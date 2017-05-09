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
    <Portlet:Form action="/dropDown">

<table>
<tr><td>fieldLinkedToCCAndCached:</td><td>
    <Portlet:DropDown field="fieldLinkedToCCAndCached">
        <Portlet:DropDownOption value="FLTCCACValue1" label="Value1"/>
        <Portlet:DropDownOption value="FLTCCACValue2" label="Value2"/>
        <Portlet:DropDownOption value="FLTCCACValue3" label="Value3"/>
    </Portlet:DropDown>
</td></tr>
<tr><td>fieldLinkedToCC:</td><td>
    <Portlet:DropDown field="fieldLinkedToCC">
        <Portlet:DropDownOption value="FLTCCValue1" label="Value1"/>
        <Portlet:DropDownOption value="FLTCCValue2" label="Value2"/>
        <Portlet:DropDownOption value="FLTCCValue3" label="Value3"/>
    </Portlet:DropDown>
</td></tr>
<tr><td>fieldWithCachedModel:</td><td>
    <Portlet:DropDown field="fieldWithCachedModel">
        <Portlet:DropDownOption value="FWCMValue1" label="Value1"/>
        <Portlet:DropDownOption value="FWCMValue2" label="Value2"/>
        <Portlet:DropDownOption value="FWCMValue3" label="Value3"/>
    </Portlet:DropDown>
</td></tr>
<tr><td>fieldWithValidation (doesn't work yet):</td><td>
    <Portlet:DropDown field="fieldWithValidation">
        <Portlet:DropDownOption value="FWVValue1" label="Value1"/>
        <Portlet:DropDownOption value="FWVValue2" label="Value2"/>
        <Portlet:DropDownOption value="FWVValue3" label="Value3"/>
    </Portlet:DropDown>
</td></tr>
<tr><td>User Status (Static Dropdown target)</td><td>
<Portlet:DropDown field='status'/> 
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
