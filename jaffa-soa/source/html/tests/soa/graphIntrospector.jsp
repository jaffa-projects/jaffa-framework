<%@page import="java.util.Collection, java.util.List, java.util.Map, org.jaffa.datatypes.Parser, org.jaffa.soa.dataaccess.MappingFilter"%><%

String graphClassName = request.getParameter("graphClassName") != null ? request.getParameter("graphClassName") : "";
Boolean includeUnmappedFields = Parser.parseBoolean(request.getParameter("includeUnmappedFields"));
String option = request.getParameter("option") != null ? request.getParameter("option") : "FIELD_LIST";
String event = request.getParameter("event");
if ("Introspect".equals(event) && graphClassName != null) {
  Class graphClass = Class.forName(graphClassName);
  if ("FIELD_LIST".equals(option)) {
    List<String> fieldList = MappingFilter.getFieldList(graphClass, includeUnmappedFields != null && includeUnmappedFields);
    response.setContentType("text/plain");
    response.setHeader("Content-Disposition", "attachment; filename=\"fieldList.txt\"");
    for (String field : fieldList)
      out.println(field);
    return;
  } else if ("FIELD_MAP".equals(option)) {
    Map<Class, Collection<String>> fieldMap = MappingFilter.getFieldMap(graphClass, includeUnmappedFields != null && includeUnmappedFields);
    response.setContentType("text/plain");
    response.setHeader("Content-Disposition", "attachment; filename=\"fieldMap.txt\"");
    for (Map.Entry<Class, Collection<String>> me : fieldMap.entrySet()) {
      out.println(me.getKey().getSimpleName());
      if (me.getValue() != null) {
        for (String field : me.getValue()) {
          out.print('\t');
          out.println(field);
        }
      }
    }
    return;
  } else if ("EXCLUDED_FIELD_MAP".equals(option)) {
    Map<Class, Collection<String>> excludedFieldMap = MappingFilter.getExcludedFieldMap(graphClass);
    response.setContentType("text/plain");
    response.setHeader("Content-Disposition", "attachment; filename=\"excludedFieldMap.txt\"");
    for (Map.Entry<Class, Collection<String>> me : excludedFieldMap.entrySet()) {
      out.println(me.getKey().getSimpleName());
      if (me.getValue() != null) {
        for (String field : me.getValue()) {
          out.print('\t');
          out.println(field);
        }
      }
    }
    return;
  }
}

%>
<html>
  <head>
    <title>Graph Introspector</title>
    <link href='/jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
  </head>
  <body>
    <form action="graphIntrospector.jsp" method="post">
      <h2>Graph Introspector</h2>
      <table>
        <tr>
          <td>Graph Class Name (fully qualified): </td>
          <td><input type="text" name="graphClassName" value="<%= graphClassName %>" size="150"></td>
        </tr>
        <tr>
          <td>Include Unmapped Fields: </td>
          <td><input type="checkbox" name="includeUnmappedFields" value="true" <%= includeUnmappedFields != null && includeUnmappedFields ? "checked" : "" %>></td>
        </tr>
        <tr>
          <td colspan="2"><input name="option" type="radio" value="FIELD_LIST" <%= option.equals("FIELD_LIST") ? "checked" : "" %>> Field List</td>
        </tr>
        <tr>
          <td colspan="2"><input name="option" type="radio" value="FIELD_MAP" <%= option.equals("FIELD_MAP") ? "checked" : "" %>> Field Map</td>
        </tr>
        <tr>
          <td colspan="2"><input name="option" type="radio" value="EXCLUDED_FIELD_MAP" <%= option.equals("EXCLUDED_FIELD_MAP") ? "checked" : "" %>> Excluded Field Map</td>
        </tr>
        <tr>
          <td colspan="2"><input type="submit" name="event" value="Introspect"></td>
        </tr>
      </table>
    </form>
  </body>
</html>
