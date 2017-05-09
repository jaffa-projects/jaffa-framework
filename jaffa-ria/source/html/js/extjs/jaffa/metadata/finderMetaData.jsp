<%--
The following parameters are expected to be passed in:
  className: If passed along with the 'propertyName', will be used to look up the foreign-key rule and determine the rest of the parameters.
  propertyName: If passed along with the 'className', will be used to look up the foreign-key rule and determine the rest of the parameters.

  component: Optional if className+propertyName are passed, in which case this parameter is determined from the foreign-key rule.
  bypassCriteriaScreen: Optional if className+propertyName are passed, in which case this parameter is determined from the foreign-key rule.
  dynamicParameters: Optional if className+propertyName are passed, in which case this parameter is determined from the foreign-key rule.
  staticParameters: Optional if className+propertyName are passed, in which case this parameter is determined from the foreign-key rule.
  targetFields: Optional if className+propertyName are passed, in which case this parameter is determined from the foreign-key rule.
--%>

<%@page import="org.jaffa.ria.metadata.FinderMetaDataHelper"%>


<%
FinderMetaDataHelper.perform(request, out, pageContext.getServletContext());
%>
