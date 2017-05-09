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
    <Portlet:Form action="/userGrid">

<Portlet:UserGrid field="model1"  userGridId="fred" >
    <Portlet:UserGridColumn label="EditBox-es" dataType="CaseInsensitiveString">
        <Portlet:EditBox field="field1"/>
        <Portlet:EditBox field="field2"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="Another instance of an EditBox">
        <Portlet:EditBox field="field2" />
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="HTML Text">
        Plain Text
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="Button">
        <Portlet:Button field="butt1" label="label for text button" text="true" detail="true"/>
        <Portlet:Button field="butt2" label="label for regular button" detail="true"/>
        <Portlet:Button field="butt3" label="label for image button" detail="true" image="widgets/tests/testimage1_up.gif"/>
        <Portlet:Button field="butt4" label="label for rollover image button" detail="true" image="widgets/tests/testimage1_up.gif" rolloverImage="widgets/tests/testimage1_down.gif"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="DateTime">
        <Portlet:DateTime field="field3"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="CheckBox">
        <Portlet:CheckBox field="field4"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="Image">
        <Portlet:Image field="field5"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="DropDown">
        <Portlet:DropDown field="field6">
            <Portlet:DropDownOption value="Value1" label="Value1"/>
            <Portlet:DropDownOption value="Value2" label="Value2"/>
            <Portlet:DropDownOption value="Value3" label="Value3"/>
            <Portlet:DropDownOption value="Value4" label="Value4"/>
        </Portlet:DropDown>
    </Portlet:UserGridColumn>
</Portlet:UserGrid>

<BR/>

<BR/>
<Portlet:Button field="post" label="Post" submit="true"/>
<BR/>
<BR/>
<Portlet:Button field="quit" label="Quit"/>

        <Portlet:RaiseErrors/>
    </Portlet:Form>
    <Portlet:Footer/>
</body>
</html:html>
