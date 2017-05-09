<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.session.UserSession" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<html>
<body>
<script type='text/javascript'>
window.close();
</script>

<%
  // remove the session and logout the user
  UserSession us = UserSession.getUserSession(request);
  us.kill();
  HttpSession s = request.getSession();
  if (s != null) {
    s.invalidate();
  }
%>

</body>
</html>