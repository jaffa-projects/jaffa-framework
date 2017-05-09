<%@page import="org.jaffa.modules.messaging.services.ConfigurationService"%>

<tr>
    <%
    String filter = request.getParameter("filter");
    %>
    <td>Filter: </td>
    <td><input type="text" name="filter" value='<%= filter != null ? filter : "" %>' size="150"/></td>
</tr>
