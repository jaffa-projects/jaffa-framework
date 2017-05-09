<%-- ---------------------------------------------------------------- 
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='tiles' %>
<table>
<tr><td><Portlet:Label key='[label.Jaffa.User.RequestPassword.UserName]'/>:</td><td><Portlet:EditBox field='userName' columns='100'/></td></tr>
<tr><td><Portlet:Label key='[label.Jaffa.User.RequestPassword.Email]'/>:</td><td><Portlet:EditBox field='email' columns='100'/></td></tr>
<tr><td><Portlet:Label key='[label.Jaffa.User.RequestPassword.SecurityQuestion]'/>:</td><td><Portlet:DropDown field='securityQuestion'/></td></tr>
<tr><td><Portlet:Label key='[label.Jaffa.User.RequestPassword.SecurityAnswer]'/>:</td><td><Portlet:EditBox field='securityAnswer' columns='100'/></td></tr>
</table>
