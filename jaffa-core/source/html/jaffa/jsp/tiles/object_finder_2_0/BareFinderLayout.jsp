<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>

<!-- Start of '/jaffa/jsp/tiles/object_finder_2_0/BareFinderLayout.jsp' -->
<tiles:useAttribute name='action' classname='String'/>
<table cellspacing="0" cellpadding="0" border="0" valign='top' width="100%">
  <tr>
    <td valign='top' align="left" width="100%">
      <Portlet:Form action='<%= action %>' >
      <table cellspacing="0" cellpadding="0" border="0">
        <tr>
          <td>
            <table cellspacing="0"  cellpadding="0" border="0">
              <tr>
                <td>
                  <%-- This will determine if the Criteria screen is to be displayed --%>
                  <tiles:useAttribute name='body1'/>
                  <logic:notEmpty name='body1'>
                  <table class="trim">
                    <tr><td><tiles:insert attribute='body1'/></td></tr>
                  </table>
                  </logic:notEmpty>
                </td>
              </tr>
              <tr>
                <td>
                  <%-- This will determine if the Results screen is to be displayed --%>
                  <tiles:useAttribute name='body2'/>
                  <logic:notEmpty name='body2'>
                    <tiles:insert attribute='body2'/>
                  </logic:notEmpty>
                </td>
              </tr>
            </table>  
          </td>
        </tr>
        <tr>
          <td>
            <tiles:useAttribute name='createComponent' scope='request' ignore='true'/>
            <tiles:useAttribute name='defaultNavLinks' scope='request' ignore='true'/>
            <tiles:insert attribute='defaultNav' ignore='true'/>
          </td>
        </tr>
      </table>
      <Portlet:RaiseErrors/>
      </Portlet:Form>
    </td>
  </tr>
</table>
<!-- End of '/jaffa/jsp/tiles/object_finder_2_0/BareFinderLayout.jsp' -->
