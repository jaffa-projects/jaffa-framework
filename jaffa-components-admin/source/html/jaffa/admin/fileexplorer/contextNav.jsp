<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'contextNav' of ViewerLayout.jsp --%>
<%@ taglib prefix='j' uri='/WEB-INF/jaffa-portlet.tld'  %>

<!-- Start of '/jaffa/admin/fileexplorer/contextNav.jsp' -->
<table cellpadding='0' cellspacing='0' align='center' class='contextNav'>
  <tr>
    <td valign='bottom' width='100%'>
      <table class='contextNavHeader' width='100%'>
        <tr>
          <td class='contextNavTitle'><j:Label key='title.Jaffa.ContextNav'/></td>
          <td align='right'><a href="javascript:expand('context','contextExpand')"><img src='jaffa/imgs/foldingsection/arrowminimize.gif' border='0' name='contextExpand'></a></td>
        </tr>
      </table>
    </td> 
  </tr>
  <tr align='middle'>
    <td>
      <span id="context" syle="display:block">
      <table class='contextNavBody' cellspacing='0' cellpadding='0' width='100%'>
        <tr>
          <td>
            <img src="jaffa/imgs/icons/refresh.gif">
            <j:Button  field='Refresh' type='link' label='[label.Jaffa.Widgets.Button.Refresh]' preprocess='false'/>
          </td>
        </tr>
        <tr>
          <td>
            <img src="jaffa/imgs/icons/delete.gif">
            <j:Button  field='Delete' type='link' label='[label.Jaffa.Widgets.Button.Delete]' confirm="[label.Jaffa.Inquiry.delete.confirm]"/>
          </td>
        </tr>
        <tr>
          <td>
            <img src="jaffa/imgs/icons/cancel.gif">
            <j:Button  field='Close' type='link' label='[label.Jaffa.Widgets.Button.Close]' preprocess='false'/>
          </td>
        </tr>
      </table>
      </span>
    </td>
  </tr>
</table>
<!-- End of '/jaffa/admin/fileexplorer/contextNav.jsp' -->
