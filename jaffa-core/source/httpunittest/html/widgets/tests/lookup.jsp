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
    <Portlet:Form action="/lookup">

<Portlet:UserGrid field="model1" detail="true" userGridId="someId">
    <Portlet:UserGridColumn label="Part Number" dataType="CaseInsensitiveString">
        <Portlet:Text field="partNo"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="Seg Code" dataType="CaseInsensitiveString">
        <Portlet:Text field="segCode"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="Status" dataType="CaseInsensitiveString">
        <Portlet:Text field="status"/>
    </Portlet:UserGridColumn>
    <Portlet:UserGridColumn label="Condition" dataType="CaseInsensitiveString">
        <Portlet:Text field="condition"/>
    </Portlet:UserGridColumn>
</Portlet:UserGrid>


        <Portlet:RaiseErrors/>
    </Portlet:Form>
    <Portlet:Footer/>
</body>
</html:html>
