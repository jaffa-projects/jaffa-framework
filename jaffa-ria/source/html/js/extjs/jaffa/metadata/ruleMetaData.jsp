<%-- @TODOs
- Support multiple className{n} request-parameters and generate classmetadata for all of them
- Automatically pull in additional classmetadata for related classes
- Add key, collectionNames, Record information to the classmetadata
- Automatically pull in findermetadata
--%>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="org.jaffa.ria.metadata.ClassMetaDataHelper" %>
<%@ page import="org.jaffa.ria.metadata.RuleMetaDataHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>

<%!
private static final Logger log = Logger.getLogger("js.extjs.jaffa.metadata.classMetaData");
%>

<%
  RuleMetaDataHelper ruleMetaDataHelper = new RuleMetaDataHelper();
  ClassMetaDataHelper classMetaDataHelper = new ClassMetaDataHelper();
  String classNameFromRequest = request.getParameter("className");
  String className = classMetaDataHelper.getSafeClassName(classNameFromRequest);

  if (log.isDebugEnabled()) {
    log.debug("Generating Meta Data for Rule " + className);
  }

  if (className != null && className.length() > 0) {
    ruleMetaDataHelper.showRules(className, out);
  }
  else { // Either an empty entry or a potentially unsafe class
    out.write("Unrecognized class name: " +
              StringHelper.escapeHtml(classNameFromRequest));
  }
%>
