<!DOCTYPE html>
<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>

<!-- Start of '/jaffa/jsp/tiles/MainLayout.jsp' -->
<html:html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <tiles:useAttribute name='title' classname='String' ignore='true'/>
    <tiles:insert attribute='header' ignore='true'>
    <tiles:put name='title' value='<%= title %>'/>
  </tiles:insert>
</head>

<body topmargin="0" marginheight="0" leftmargin="0" marginwidth="0">
<script type="text/javascript" src="jaffa/js/tiles/styleswitcher.js"></script>
<table width="100%" cellspacing="0" cellpadding="0" border="0">
  <tr>
    <td>
      <tiles:insert attribute='globalNav' ignore='true'/>
    </td>
  </tr>
  <tr>
    <td>
      <tiles:insert attribute='body' ignore='true'>
        <tiles:put name='title' value='<%= title %>'/>
      </tiles:insert>
    </td>
  </tr>
  <tr>
    <td>
      <tiles:insert attribute='footer' ignore='true'/>
    </td>
  </tr>
</table>
</body>
</html:html>
<!-- End of '/jaffa/jsp/tiles/MainLayout.jsp' -->
