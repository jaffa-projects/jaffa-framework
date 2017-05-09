<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page import="org.jaffa.components.finder.FinderComponent2" %>

<%
response.setContentType("text/xml");
out.println(request.getAttribute(FinderComponent2.ATTRIBUTE_EXPORT_TYPE_XML));
%>
