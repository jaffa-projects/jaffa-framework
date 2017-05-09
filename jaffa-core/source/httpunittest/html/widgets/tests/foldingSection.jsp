<%@ page language="java" %>
<%@ page import="org.apache.log4j.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<html:html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <LINK rel=STYLESHEET type='text/css' href='css/mainbody.css'>
  <Portlet:Header/>
</head>
<body bgcolor="8fa5ff" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<Portlet:Form action="/foldingSection">

  This should be open, and it should contain one label widget
  <hr width="50%">
  <Portlet:FoldingSection id="open1" label="Open">
    Label Value = <Portlet:Label key="label.Jaffa.Inquiry.delete.confirm"/>
  </Portlet:FoldingSection>

  <br>
  <br>
  <hr>
  This one will be hidden, it has no widgets, and is not forced open
  <hr width="50%">
  <Portlet:FoldingSection id="open2" label="Open">
    <font color="red">You Shoul Not See This </font>
  </Portlet:FoldingSection>

  <br>
  <br>
  <hr>
  This is an example of a default closed section (force display)
  <hr width="50%">
  <Portlet:FoldingSection id="closed" label="Closed" closed="true" hideIfNoWidgets="false">
    BLAH BLAH BLAH !
  </Portlet:FoldingSection>

  <br>
  <br>
  <hr>
  This is an example of an default open section
  <hr width="50%">
  <Portlet:FoldingSection id="open3" label="Open" hideIfNoWidgets="false">
    BLAH BLAH BLAH !
  </Portlet:FoldingSection>

  <br>
  <br>
  <hr>
  This is an example of a key
  <hr width="50%">
  <Portlet:FoldingSection id="key"  key="label.Jaffa.Widgets.Test.Key" hideIfNoWidgets="false">
    BLAH BLAH BLAH !
  </Portlet:FoldingSection>

  <Portlet:RaiseErrors/>
</Portlet:Form>
<Portlet:Footer/>
</body>
</html:html>
