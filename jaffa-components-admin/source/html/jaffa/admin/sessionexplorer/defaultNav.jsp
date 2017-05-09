<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'defaultNav' of ViewerLayout.jsp --%>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<!-- start of '/jaffa/admin/sessionexplorer/defaultnav.jsp' -->
<table width="100%" class="defaultNav">
  <tr>
    <%-- Default BACK action --%>
    <td align="left" width="1%"><Portlet:Button field='Close' image='jaffa/imgs/icons/prevArrows.gif' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/></td>
    <td align="left" valign="middle"><Portlet:Button field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/></td>
    
    <%-- Default FORWARD action --%>
  </tr>
</table>
<!-- End of '/jaffa/admin/sessionexplorer/defaultNav.jsp' -->
