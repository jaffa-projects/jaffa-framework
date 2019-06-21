<%-- @TODOs
- Support multiple className{n} request-parameters and generate classmetadata for all of them
- Automatically pull in additional classmetadata for related classes
- Add key, collectionNames, Record information to the classmetadata
- Automatically pull in findermetadata
--%>
<%@ page import='org.jaffa.datatypes.Parser,
org.jaffa.ria.metadata.ClassMetaDataHelper,
org.jaffa.util.StringHelper' %>

<%
  ClassMetaDataHelper classMetaDataHelper = new ClassMetaDataHelper();
  String outputStyle = request.getParameter("outputStyle");
  String outputClassName = request.getParameter("outputClassName");
  String objectString = request.getParameter("object");
  objectString = objectString != null ? objectString.trim() : null;
  Boolean legacy = Parser.parseBoolean(request.getParameter("legacy"));
  String classNameFromRequest = request.getParameter("className");
  String className = classMetaDataHelper.getSafeClassName(classNameFromRequest);

  if (className != null && className.length() > 0) {
    if (ClassMetaDataHelper.JSON.equalsIgnoreCase(outputStyle)) {
      classMetaDataHelper.showRules(className, objectString,
                                    legacy != null && legacy, out,
                                    outputClassName, ClassMetaDataHelper.JSON);
    }
    else {
      classMetaDataHelper.showRules(className, objectString,
                                    legacy != null && legacy, out, outputClassName);
    }
  }
  else { // Either an empty entry or a potentially unsafe class
    out.write("Unrecognized class name: " +
              StringHelper.escapeHtml(classNameFromRequest));
  }
%>
