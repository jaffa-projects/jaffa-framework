<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<html:html>
<head>
    <title>Test.Security.Test1.Page1</title>
    <Portlet:Header/>
</head>
<body>
<Portlet:Form action="/test_security_page1">
<h1>You're Logged In As '<%= request.getRemoteUser() %>'</h1>

    <Portlet:RaiseErrors/>

<center>
    <table>
        <tr><td>Field 1:</td><td><Portlet:EditBox field="field1"/></td></tr>
        <tr><td>Field 2:</td><td><Portlet:EditBox field="field2"/></td></tr>
    </table>
    <BR/>
    <BR/>
    <table width="90%"><tr>
        <td>This button should throws an exception if you don't have access to Function1</td><td><Portlet:Button field="button1" label="Button 1"/></td>
    </tr><tr>
        <td>This button displays a messgae if you don't have access to Function1, if you do it should throw an handle an exception</td><td><Portlet:Button field="button1b" label="Button 1b"/></td>
    </tr><tr>
        <td>This button displays a messgae if you<br/>
            don't have access to Function2, and another message if you do</td><td><Portlet:Button field="button2" label="Button 2"/></td>
    </tr><tr>
        <td>This button tries to run Component Test.Security.Test2<br/>
            It requires access to BF 'Function2'</td><td><Portlet:Button field="button3" label="Run Component Test2"/></td>
    </tr></table>
</center>
    <br/>
    <br/>
    <p>The current message text is...</p>
<table id='message'><tr><td>
<PRE><Portlet:Text field="message"/></PRE>
</td></tr></table>

<h2>Tests For Guards</h2><br/>

<table id='tagTable'>
<tr id='Test1'><td>
<Portlet:FunctionGuard name='Function1' hasAccess='true'>
<p>You should see this line if you have access to Function1</p>
</Portlet:FunctionGuard>
</td></tr>
<tr id='Test2'><td>
<Portlet:FunctionGuard name='Function1' hasAccess='false'>
<p>You should see this line if you <b>don't</b> have access to Function1</p>
</Portlet:FunctionGuard>
</td></tr>
<tr id='Test3'><td>
<Portlet:FunctionGuard name='Function2' hasAccess='true'>
<p>You should see this line if you have access to Function2</p>
</Portlet:FunctionGuard>
</td></tr>
<tr id='Test4'><td>
<Portlet:FunctionGuard name='Function2' hasAccess='false'>
<p>You should see this line if you <b>don't</b> have access to Function2</p>
</Portlet:FunctionGuard>
</td></tr>
<tr id='Test5'><td>
<Portlet:FunctionGuard name='FunctionX' hasAccess='true'>
<p>You should see this line if you have access to FunctionX<br/>FunctionX is unknown</p>
</Portlet:FunctionGuard>
</td></tr>
<tr id='Test6'><td>
<Portlet:FunctionGuard name='FunctionX' hasAccess='false'>
<p>You should see this line if you <b>don't</b> have access to FunctionX<br/>FunctionX is unknown</p>
</Portlet:FunctionGuard>
</td></tr>
<tr id='Test7'><td>
<Portlet:ComponentGuard name='Test.Security.Test2' hasAccess='true'>
<p>You should see this line if you have access to Test.Security.Test2<br/>Function2 is mandatory for this component!</p>
</Portlet:ComponentGuard>
</td></tr>
<tr id='Test8'><td>
<Portlet:ComponentGuard name='Test.Security.Test2' hasAccess='false'>
<p>You should see this line if you have  <b>don't</b> access to Test.Security.Test2<br/>Function2 is mandatory for this component!</p>
</Portlet:ComponentGuard>
</td></tr>
<tr id='Test9'><td>
<Portlet:ComponentGuard name='Not.Real.Component' hasAccess='true'>
<p>You should see this line if you have access to Not.Real.Component<br/>This component name is invalid and doesn't exist</p>
</Portlet:ComponentGuard>
</td></tr>
<tr id='Test10'><td>
<Portlet:ComponentGuard name='Not.Real.Component' hasAccess='false'>
<p>You should see this line if you have  <b>don't</b> access to Not.Real.Component<br/>This component name is invalid and doesn't exist</p>
</Portlet:ComponentGuard>
</td></tr>
</table>

<Portlet:Button field="logout" label="Log Out"/>


</Portlet:Form>
<Portlet:Footer/>
</body>
</html:html>
