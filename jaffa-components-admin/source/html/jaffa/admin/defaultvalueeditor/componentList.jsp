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

<table width='100%' class='sectionheader'>
<tr><td class='sectiontitle'><Portlet:Label key='label.Jaffa.Admin.DefaultValueEditor.ComponentList'/></td></tr>
</table>

<table><tr><td>
    <Portlet:UserGrid field='components' userGridId="admin_defaultValueEditor" detail="true">
        <Portlet:UserGridColumn label="[label.Jaffa.Admin.LabelEditor.ComponentName]" dataType="CaseInsensitiveString" >
            <Portlet:Text field="ComponentName"/>
        </Portlet:UserGridColumn>
        <Portlet:UserGridColumn label="[label.Jaffa.Admin.LabelEditor.ComponentClass]" dataType="CaseInsensitiveString" >
            <Portlet:Text field="ComponentClass"/>
        </Portlet:UserGridColumn>
        <Portlet:UserGridColumn label="[label.Jaffa.Admin.LabelEditor.ComponentType]" dataType="CaseInsensitiveString" >
            <Portlet:Text field="ComponentType"/>
        </Portlet:UserGridColumn>
        <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
            <Portlet:Button field="Update" label="[label.Jaffa.Widgets.Button.Update]" image="jaffa/imgs/icons/update.gif"/>
        </Portlet:UserGridColumn>
    </Portlet:UserGrid>
</td></tr></table>
