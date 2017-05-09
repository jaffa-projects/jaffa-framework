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

<!-- start of '/jaffa/admin/log4jconfig/defaultnav.jsp' -->
<table width="100%" class="defaultNav">
  <tr>
    <%-- Default BACK action --%>
    <td align="left" width="1%"><Portlet:Button field='Close' image='jaffa/imgs/icons/prevArrows.gif' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/></td>
    <td align="left" valign="middle"><Portlet:Button field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/></td>
    
    <%-- Default FORWARD action --%>
    <td width="100%" align="right" valign="middle"><Portlet:Button  field='Save' type='link' label='[label.Jaffa.Widgets.Button.Save]' submit="true"/></td>
    <td align="right" width="1%"><Portlet:Button field='Save' image='jaffa/imgs/icons/nextArrows.gif' label='[label.Jaffa.Widgets.Button.Save]'/></td>
  </tr>
</table>
<!-- End of '/jaffa/admin/log4jconfig/defaultNav.jsp' -->
