<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  --
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'body' of ViewerLayout.jsp --%>
<%@ page import='org.jaffa.presentation.portlet.widgets.taglib.TagHelper' %>
<%@ page import='org.jaffa.applications.jaffa.modules.admin.components.log4jconfig.ui.Log4jConfigComponent' %>
<%@ page import="org.jaffa.session.ContextManagerFactory" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.EditBoxTag" %>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>

<%
  // This is a hack to turn off 'hard' wrapping for non-comment TextArea
  // An alternative could be to provide a 'wrap' attribute for the EditBox tag
  // NOTE: This hack should be more fine-grained, if any comment fields are added in the JSP
  Object originalTextareaWrap = ContextManagerFactory.instance().getProperty(EditBoxTag.RULE_TEXTAREA_WRAP);
  ContextManagerFactory.instance().setProperty(EditBoxTag.RULE_TEXTAREA_WRAP, "off");
%>

<!-- Start of '/jaffa/admin/log4jconfig/main.jsp' -->

    <BR>
        <TABLE>
            <TR>
                <TD><Portlet:EditBox field='fileContents' rows='2' horizontalSizePercentage='60' verticalSizePercentage='65'/></TD>
            </TR>
        </TABLE>

<!-- End of '/jaffa/admin/log4jconfig/main.jsp' -->

<% ContextManagerFactory.instance().setProperty(EditBoxTag.RULE_TEXTAREA_WRAP, originalTextareaWrap); %>