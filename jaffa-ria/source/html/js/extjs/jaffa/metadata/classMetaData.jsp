<%-- @TODOs
- Support multiple className{n} request-parameters and generate classmetadata for all of them
- Automatically pull in additional classmetadata for related classes
- Add key, collectionNames, Record information to the classmetadata
- Automatically pull in findermetadata
--%>
<%@ page import='org.jaffa.datatypes.Parser,
org.jaffa.ria.metadata.ClassMetaDataHelper' %>

<%
  String className = request.getParameter("className");
  String outputStyle = request.getParameter("outputStyle");
  String outputClassName = request.getParameter("outputClassName");
  String objectString = request.getParameter("object");
  Boolean legacy = Parser.parseBoolean(request.getParameter("legacy"));
  className = className != null ? className.trim() : null;
  objectString = objectString != null ? objectString.trim() : null;
  if (className != null && className.length() > 0) {
    ClassMetaDataHelper classMetaDataHelper = new ClassMetaDataHelper();

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
%>
