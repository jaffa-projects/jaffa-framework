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
    <Portlet:Form action="/grid">


<Portlet:Grid field="model1">
    <Portlet:GridColumn label="EditBox-es" >
        <Portlet:EditBox field="field1"/>
    </Portlet:GridColumn>
    <Portlet:GridColumn label="Another instance of an EditBox">
        <Portlet:EditBox field="field2"/>
    </Portlet:GridColumn>
    <Portlet:GridColumn label="HTML Text">
        Plain Text
    </Portlet:GridColumn>
    <Portlet:GridColumn label="DateTime">
        <Portlet:DateTime field="field3"/>
    </Portlet:GridColumn>
    <Portlet:GridColumn label="CheckBox">
        <Portlet:CheckBox field="field4"/>
    </Portlet:GridColumn>
    <Portlet:GridColumn label="DropDown">
        <Portlet:DropDown field="field6">
            <Portlet:DropDownOption value="Value1" label="Value1"/>
            <Portlet:DropDownOption value="Value2" label="Value2"/>
            <Portlet:DropDownOption value="Value3" label="Value3"/>
            <Portlet:DropDownOption value="Value4" label="Value4"/>
        </Portlet:DropDown>
    </Portlet:GridColumn>
</Portlet:Grid>



<br>
<br>
<br>


<Portlet:Grid field="model2" displayOnly="true">
    <Portlet:GridColumn label="tree" treeField="level">
        <img src="jaffa/imgs/tree/childNode.gif" border="0" valign="middle">
        <Portlet:Text field="field1"/>
        <Portlet:CheckBox field="field4"/>
    </Portlet:GridColumn>
</Portlet:Grid>
<br>
<br>
<br>

<Portlet:Grid field="model3" detail="true">
    <Portlet:GridColumn label="tree" treeField="level" >
    <img src="jaffa/imgs/tree/childNode.gif" border="0" valign="middle">
    </Portlet:GridColumn>
    <Portlet:GridColumn label="EditBox-es" >
        <Portlet:EditBox field="field1"/>
    </Portlet:GridColumn>
    <Portlet:GridColumn label="CheckBox">
        <Portlet:CheckBox field="field4"/>
    </Portlet:GridColumn>
</Portlet:Grid>



<BR/>
<BR/>
<BR/>
<BR/>
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
