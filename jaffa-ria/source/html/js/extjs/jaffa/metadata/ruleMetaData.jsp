<%-- @TODOs
- Support multiple className{n} request-parameters and generate classmetadata for all of them
- Automatically pull in additional classmetadata for related classes
- Add key, collectionNames, Record information to the classmetadata
- Automatically pull in findermetadata
--%>
<%@ page import='
java.util.Collection,
java.util.Enumeration,
java.util.Iterator,
java.util.LinkedHashSet,
java.util.Map,
java.util.Properties,
javax.servlet.jsp.JspWriter,
org.apache.log4j.Logger,
org.jaffa.datatypes.Currency,
org.jaffa.datatypes.DateOnly,
org.jaffa.datatypes.DateTime,
org.jaffa.datatypes.Parser,
org.jaffa.rules.IObjectRuleIntrospector,
org.jaffa.rules.IPropertyRuleIntrospector,
org.jaffa.rules.RulesEngineFactory,
org.jaffa.rules.meta.MetaDataRepository,
org.jaffa.rules.rulemeta.RuleRepository,
org.jaffa.util.MessageHelper,
org.jaffa.rules.meta.RuleMetaData,
org.jaffa.rules.rulemeta.RuleMetaHelper,
org.jaffa.rules.rulemeta.data.RuleMetaDataCriteria,
org.jaffa.util.StringHelper' %>

<%!
private static final Logger log = Logger.getLogger("js.extjs.jaffa.metadata.classMetaData");

/** Convert the input to HTML compatible String. */
private String toHtml(Object obj) {
  return obj == null ? "" : StringHelper.convertToHTML(obj.toString());
}

/* Convert a Java Class to a JavaScript Equivilent */
private String toJsType(Class cls) {
  if(cls == null)  return "object";
  if(String.class.isAssignableFrom(cls)) return "string";
  if(Number.class.isAssignableFrom(cls)) return "number";
  if(Boolean.class.isAssignableFrom(cls)) return "boolean";
  if(DateTime.class.isAssignableFrom(cls)) return "datetime";
  if(DateOnly.class.isAssignableFrom(cls)) return "dateonly";
  if(Currency.class.isAssignableFrom(cls)) return "currency";
  return "object";
}

/** Converts the input String to an appropriate value based on the jsType, and then applies the necessary quote. */
private String quoteValueByType(String value, String jsType) {
  if (value != null) {
    if ("boolean".equals(jsType)) {
      Boolean b = Parser.parseBoolean(value);
      value = b != null ? b.toString() : "false";
    } else if ("number".equals(jsType)) {
      // do nothing
    } else {
      value = '\'' + value + '\'';
    }
  }
  return value;
}

/** Converts the input Properties to a javascript object. */
private String toJSON(Properties p) {
  StringBuilder buf = new StringBuilder();
  for (Enumeration e = p.propertyNames(); e.hasMoreElements(); ) {
    if (buf.length() > 0)
      buf.append(", ");
    String k = (String) e.nextElement();
    buf.append(toHtml(k)).append(": '").append(toHtml(p.getProperty(k))).append('\'');
  }
  buf.insert(0, '{');
  buf.append('}');
  return buf.toString();
}

/** Find the rules for the input Class. */
private void showRules(String className, JspWriter out) throws Exception {

  // Load the Class
  Class clazz = null;
  try {
    clazz = Class.forName(className);
    if (log.isDebugEnabled())
        log.debug("Print Out Properties From " + className);
    String outputClassName = clazz.getSimpleName();
    out.write("RuleMetaData['" + outputClassName + "'] = {\n  properties: {\n  ");
  } catch (Exception e) {
    if (log.isDebugEnabled())
      log.debug("Print Out Properties From Virtual Class " + className);
    String[] splitClass = className.split("\\.");
    out.write("RuleMetaData['" + splitClass[splitClass.length-1] + "'] = {\n  properties: {\n  ");
  }

  // Show all the Property attributes
  showProperties(className, clazz, out);
  out.write("  }\n");

  // Show Object attributes
  IObjectRuleIntrospector ori = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(className, null);
  Properties[] flexInfoArray = null;
  flexInfoArray = ori.getDeclaredFlexInfo();
  if (flexInfoArray != null && flexInfoArray.length > 0) {
    StringBuilder buf = new StringBuilder();
    for (Properties p : flexInfoArray) {
      if (buf.length() > 0)
        buf.append(", ");
      buf.append(toJSON(p));
    }
    out.write("  ,flexInfo: [");
    out.write(buf.toString());
    out.write("]\n");
  }

  String label = ori.getLabel();
  if(label != null ) {
    out.write("  ,label: '");
    out.write(toHtml(MessageHelper.replaceTokens(label)));
    out.write("'\n");
  }

 	RuleMetaDataCriteria criteria = new RuleMetaDataCriteria();
  criteria.setClassName(className);
  criteria.setRuleName("template");
  RuleMetaData rule =  RuleMetaHelper.findRule(criteria);
  if (rule != null) {
    out.write("      ,ruleTemplate: '" + rule.getParameter("value"));
    out.write("'\n");
  }
  out.write("};");
}


/** Show Properties. */
private void showProperties(String className, Class clazz, JspWriter out) throws Exception {
  String[] propertyNames = determinePropertyNames(className, clazz);
  if (propertyNames != null && propertyNames.length > 0) {
    StringBuilder buf = new StringBuilder();
    for (String propertyName : propertyNames) {
      addPropertyMetaData(className, propertyName, buf, propertyName);
    }
    if (buf.length() > 0)
      buf.append('\n');
    out.write(buf.toString());
  }
}

/** Adds metadata for the given property into the input buffer, provided rules have been defined for the property. */
private void addPropertyMetaData(String className, String propertyName, StringBuilder buf, String title) throws Exception {

  StringBuilder sb = new StringBuilder();
  IPropertyRuleIntrospector pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(className, propertyName, null);
  String type = toJsType(pri.getPropertyType());
  Object rule;
  Properties p;
  if(pri.isHidden())
    sb.append("      ,hidden: true\n");
  if(pri.isReadOnly())
    sb.append("      ,readOnly: true\n");
  if(pri.isMandatory())
    sb.append("      ,mandatory: true\n");
  rule = pri.getLabel();
  if(rule != null ) {
    sb.append("      ,label: '").append(toHtml(MessageHelper.replaceTokens((String) rule))).append("'\n");
    String tmp = ((String) rule).substring(1);
    tmp = tmp.substring(0, tmp.length()-1);
    sb.append("      ,labelToken: '").append(tmp).append("'\n");
  }
  rule = pri.getMinLength();
  if(rule != null )
    sb.append("      ,minLength: ").append(toHtml(rule)).append('\n');
  rule = pri.getMaxLength();
  if(rule != null ) {
    if (!type.equals("number")) {
      sb.append("      ,maxLength: ").append(toHtml(rule)).append('\n');
    } else {
      int maxLength = (Integer) rule;
      sb.append("      ,maxIntegralLength: ").append(maxLength).append('\n');
      rule = pri.getMaxFracLength();
      if (rule != null && ((Integer) rule) > 0) {
        sb.append("      ,decimalPrecision: ").append(rule).append('\n');
        maxLength = maxLength + 1 + ((Integer) rule); //account for the decimal separator
      } else {
        sb.append("      ,allowDecimals: false\n");
      }

      //account for negative sign
      rule = pri.getMinValue();
      if (rule == null || ((Number) rule).doubleValue() < 0)
        maxLength = 1 + maxLength;

      sb.append("      ,maxLength: ").append(maxLength).append('\n');
    }
  }
  rule = pri.getMinValue();
  if(rule != null )
    sb.append("      ,minValue: ").append(toHtml(rule)).append('\n');
  rule = pri.getMaxValue();
  if(rule != null )
    sb.append("      ,maxValue: ").append(toHtml(rule)).append('\n');
  rule = pri.getCaseType();
  if(rule != null )
    sb.append("      ,caseType: '").append(toHtml(rule)).append("'\n");
  rule = pri.getLayout();
  if(rule != null )
    sb.append("      ,layout: '").append(toHtml(rule)).append("'\n");
  rule = pri.getPattern();
  if(rule != null )
    sb.append("      ,pattern: '").append(toHtml(rule)).append("'\n");
  rule = pri.getCommentStyle();
  if (rule != null)
    sb.append("      ,commentStyle: '").append(toHtml(rule)).append("'\n");
  p = pri.getForeignKeyInfo();
  if (p != null)
    sb.append("      ,foreignKeyInfo: ").append(toJSON(p)).append('\n');
  p = pri.getPropertyInfo();
  if (p != null) {
    String s;
    s = p.getProperty("display-sequence");
    if (s != null)
      sb.append("      ,displaySequence: '").append(toHtml(s)).append("'\n");
    s = p.getProperty("display-group");
    if (s != null)
      sb.append("      ,displayGroup: '").append(toHtml(s)).append("'\n");
    s = p.getProperty("display-group-label");
    if (s != null)
      sb.append("      ,displayGroupLabel: '").append(toHtml(s)).append("'\n");
  }

  Map<String, String> inListValues = pri.getInListValues();
  if (inListValues != null && inListValues.size() > 0) {
    StringBuilder inList = new StringBuilder();
    for (Map.Entry<String, String> me : inListValues.entrySet()) {
      String value = toHtml(me.getKey());
      String label = me.getValue();
      label = label != null ? toHtml(MessageHelper.replaceTokens(label)) : value;
      if (inList.length() > 0)
        inList.append(", ");
      inList.append("[").append(quoteValueByType(value, type)).append(", '").append(label).append("']");
    }
    sb.append("      ,inList: ").append('[').append(inList).append(']').append('\n');
  }

  Properties info = pri.findInfo(className, propertyName, null, "rule-info");
  if (info != null) {
    sb.append("      ,ruleInfo: {");
    String isClass = info.getProperty("is-class");
    if (isClass != null && isClass.equals("true")) {
      sb.append("      isClass: true\n");
    }
    String isProperty = info.getProperty("is-property");
    if (isProperty != null && isProperty.equals("true")) {
      if (isClass != null && isClass.equals("true")) {
        sb.append(",");
      }
        sb.append("      isProperty: true\n");
    }
    sb.append("}\n");
  }

  if (sb.length() > 0) {
    buf.append(buf.length() > 0 ? "," : "  ");
    buf.append('\'').append(toHtml(title)).append("': {\n");
    buf.append("      type: '").append(type).append("'\n");
    buf.append(sb);
    buf.append("    }");
  }
}

/** Determine the properties for the input Class. */
private String[] determinePropertyNames(String className, Class clazz) throws Exception {
  Collection<String> checkedPropertyNames = new LinkedHashSet<String>();
  if (clazz == null) {
    if (checkedPropertyNames.size() == 0) {
      Collection ruleNames = RuleRepository.instance().getRuleNames();
      for (Iterator ruleItr = ruleNames.iterator(); ruleItr.hasNext(); ) {
        String ruleName = (String) ruleItr.next();
        Map propertyRuleMap = MetaDataRepository.instance()
          .getPropertyRuleMap(className, ruleName);
        if (propertyRuleMap != null) {
          for (Iterator propertyItr = propertyRuleMap.keySet().iterator(); propertyItr.hasNext(); ) {
            String propertyName = (String) propertyItr.next();
            if (propertyName != null)
              checkedPropertyNames.add(propertyName);
          }
        }
      }
    }
  }

  return checkedPropertyNames != null && checkedPropertyNames.size() > 0 ?
        checkedPropertyNames.toArray(new String[checkedPropertyNames.size()]) :
        null;
}
%><%
String className = request.getParameter("className");
className = className != null ? className.trim() : null;
if (log.isDebugEnabled())
  log.debug("Generating Meta Data for Rule " + className);
if (className != null && className.length() > 0)
  showRules(className, out);
%>
