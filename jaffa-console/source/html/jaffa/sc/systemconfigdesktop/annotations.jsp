<%-- @TODOs
- Support multiple className{n} request-parameters and generate classmetadata for all of them
- Automatically pull in additional classmetadata for related classes
- Add key, collectionNames, Record information to the classmetadata
- Automatically pull in findermetadata
--%>
<%@ page import='java.beans.BeanInfo,
java.beans.Introspector,
java.beans.PropertyDescriptor,
java.util.Arrays,
java.util.Collection,
java.util.Enumeration,
java.util.Iterator,
java.util.LinkedHashSet,
java.util.List,
java.util.ArrayList,
java.util.Map,
java.util.HashMap,
java.util.Properties,
java.util.Set,
javax.servlet.jsp.JspWriter,
net.sf.json.JSONObject,
org.apache.log4j.Logger,
org.apache.commons.beanutils.PropertyUtils,
org.jaffa.datatypes.Currency,
org.jaffa.datatypes.DateOnly,
org.jaffa.datatypes.DateTime,
org.jaffa.datatypes.Parser,
org.jaffa.flexfields.FlexClass,
org.jaffa.flexfields.FlexProperty,
org.jaffa.metadata.FieldMetaData,
org.jaffa.persistence.Criteria,
org.jaffa.persistence.Persistent,
org.jaffa.persistence.UOW,
org.jaffa.rules.IPropertyRuleIntrospector,
org.jaffa.rules.RulesEngineFactory,
org.jaffa.rules.meta.MetaDataRepository,
org.jaffa.rules.meta.RuleMetaData,
org.jaffa.rules.rulemeta.RuleMetaHelper,
org.jaffa.rules.rulemeta.RuleRepository,
org.jaffa.rules.rulemeta.data.RuleMetaDataCriteria,
org.jaffa.session.ContextManagerFactory,
org.jaffa.util.BeanHelper,
org.jaffa.util.MessageHelper,
org.jaffa.util.StringHelper' %>

<%!
private static final Logger log = Logger.getLogger("jaffa.sc.systemconfigdesktop.annotations");

// to hold domain Object and its content
private Map<String, Object> domainRulesMap = new HashMap<String, Object>();

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
private void showRules(String className, JspWriter out, ServletContext servletContext) throws Exception {

  String[] splitClass = className.split("\\.");
  out.write("ClassMetaData." + splitClass[splitClass.length-1] + " = {\n  fields: {\n  ");

  // Show all the Property attributes
  showProperties(className, out, servletContext);
  out.write("  }\n");
  out.write("};");
}

/** Show Properties. */
private void showProperties(String className, JspWriter out, ServletContext servletContext) throws Exception {
  String[] propertyNames = determinePropertyNames(className);
  if (propertyNames != null && propertyNames.length > 0) {
    StringBuilder buf = new StringBuilder();

    for (String propertyName : propertyNames) {
      addPropertyMetaData(className, propertyName, buf, propertyName, servletContext);
    }
    if (buf.length() > 0)
      buf.append('\n');
    out.write(buf.toString());
  }
}

/** Adds metadata for the given property into the input buffer, provided rules have been defined for the property. */
private void addPropertyMetaData(String className, String propertyName, StringBuilder buf, String title, ServletContext servletContext) throws Exception {
  StringBuilder sb = new StringBuilder();
  IPropertyRuleIntrospector pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(className, propertyName, null);
//TODO after merge need to directly grab property type as existing implementation returns list as object
  String type = toJsType(pri.getPropertyType());
  Object rule;
  Properties p;
  if(pri.isHidden())
    return;
  if(pri.isReadOnly())
    sb.append("      ,readOnly: true\n");
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
  rule = pri.getAnnotation();
  if(rule != null )
    sb.append("      ,annotation: '").append(rule.toString().replaceAll("\\r\\n|\\r|\\n", "<br>").replaceAll("'","\"").replaceAll("&nbsp;", " ")).append("'\n");
  RuleMetaDataCriteria criteria = new RuleMetaDataCriteria();
  criteria.setClassName(className);
  criteria.setPropertyName(propertyName);
  criteria.setRuleName("default-value");
  if (RuleMetaHelper.findRule(criteria)!=null){
    RuleMetaData ruleMeta = RuleMetaHelper.findRule(criteria);
    if (ruleMeta.getText() != null) sb.append("      ,defaultValue: '").append(ruleMeta.getText().replaceAll("\\r\\n|\\r|\\n", "<br>").replaceAll("'","\"").replaceAll("&nbsp;", " ")).append("'\n");
  }

  Map<String, String> inListValues = pri.getInListValues();
  if (inListValues != null && inListValues.size() > 0) {
    StringBuilder inList = new StringBuilder();
    for (Map.Entry<String, String> me : inListValues.entrySet()) {
      String value = toHtml(me.getKey());
      String label = me.getValue();
      if (value.equals("=")&&label==null){
        value = "\"\"";
        label = "\"\"";
      }
      label = label != null ? toHtml(MessageHelper.replaceTokens(label)) : value;
      if (inList.length() > 0)
        inList.append(", ");
      inList.append("[").append(quoteValueByType(value, type)).append(", '").append(label).append("']");
    }
    sb.append("      ,inList: ").append('[').append(inList).append(']').append('\n');
  }
  Properties foreignKeyInfo = pri.getForeignKeyInfo();
  if(foreignKeyInfo!=null && foreignKeyInfo.size() > 0){
	sb.append("      ,foreignKeyInfo: ").append(toJSON(foreignKeyInfo)).append('\n');
  }
  if (title.indexOf("{rule:")>0){
    int iStart, iEnd;
    iStart = title.indexOf("{rule:");
    iEnd = title.indexOf("}") + 1;
    String preRule, postRule, busRule;
    preRule = title.substring(0,iStart);
    postRule = title.substring(iEnd);
    busRule = title.substring(iStart + 6, iEnd - 1);
    busRule = (String)ContextManagerFactory.instance().getProperty(busRule);
    if (busRule!=null){
        String[] busRules = busRule.split(",");
        for (int i = 0; i<busRules.length; i++){
            buf.append(buf.length() > 0 ? "," : "  ");
            buf.append('\'').append(toHtml(preRule + busRules[i] + postRule)).append("': {\n");
            buf.append("      type: '").append(type).append("'\n");
            buf.append(sb);
            buf.append("    }");
        }
    }
  } else if(title.indexOf("{domain:")>0) {
    int domainStart, domainEnd;
    domainStart = title.indexOf("{domain:");
    domainEnd = title.indexOf("}") + 1;
    String domainPreRule = title.substring(0,domainStart);
    String domainPostRule = title.substring(domainEnd);
    String[] domainInfo = title.substring(domainStart + 6, domainEnd - 1).split(":");
    if (log.isDebugEnabled()) {
       for(String s: domainInfo){
          log.debug("DomainInfo :"+s);
       }
    }

	/**
	*  caching the value in a map to avoid fetch per rule.
	*/
	if(!domainRulesMap.containsKey(domainInfo[1])) {
        List<String> domainRuleList = new ArrayList<String>();
        Criteria myCriteria = new Criteria();
        UOW myUOW = null;
        try {

            myCriteria.setTable(domainInfo[1]);
            /*
            * Added group by to avoid fetching everything. Performance optimization.
            * It will find the unique field value based on the provided field on rule.
            */
            myCriteria.addGroupBy(domainInfo[2],domainInfo[2]);
            myUOW = new UOW();
            Iterator<Persistent> myCollection = myUOW.query(myCriteria).iterator();
            while (myCollection.hasNext()){
                Map row = (Map)myCollection.next();
                String domainFieldValue = (String)row.get(domainInfo[2]);
                buf.append(buf.length() > 0 ? "," : "  ");
                buf.append('\'').append(toHtml(domainPreRule + domainFieldValue + domainPostRule)).append("': {\n");
                buf.append("      type: '").append(type).append("'\n");
                buf.append(sb);
                buf.append("    }");

                // Appending the domainFieldValues to the domainRuleList list
                domainRuleList.add(domainFieldValue);
                if (log.isDebugEnabled()) {
                   log.debug("DomainFieldValue : "+ domainFieldValue);
                }
            }
            // Placing the domainRulename and its content in HashMap
            domainRulesMap.put(domainInfo[1], domainRuleList);

        } catch (Exception e){
            log.error("Unable to split " + title + " :"+e);
        } finally {
            if (myUOW!=null){
                try {
                    myUOW.rollback();
                }
                catch (Exception e){
                    log.error("Failed to rollback UOW:"+e);
                }
            }
        }
	} else if(domainRulesMap.containsKey(domainInfo[1])) {
        List<String> domainContent = (List<String>)domainRulesMap.get(domainInfo[1]);
        for(String domainFieldValue : domainContent){
            buf.append(buf.length() > 0 ? "," : "  ");
            buf.append('\'').append(toHtml(domainPreRule + domainFieldValue + domainPostRule)).append("': {\n");
            buf.append("      type: '").append(type).append("'\n");
            buf.append(sb);
            buf.append("    }");
            if (log.isDebugEnabled()) {
               log.debug("DomainFieldValue : "+ domainFieldValue);
            }
        }
    }
  } else {
    buf.append(buf.length() > 0 ? "," : "  ");
    buf.append('\'').append(toHtml(title)).append("': {\n");
    buf.append("      type: '").append(type).append("'\n");
    buf.append(sb);
    buf.append("    }");
  }
}

/** Determine the properties for the input Class. */
private String[] determinePropertyNames(String className) throws Exception {
  Collection<String> checkedPropertyNames = new LinkedHashSet<String>();
    if (checkedPropertyNames.size() == 0) {
      // A non-flex virtual class is passed. Find properties for which rules have been declared
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
  return checkedPropertyNames != null && checkedPropertyNames.size() > 0 ?
        checkedPropertyNames.toArray(new String[checkedPropertyNames.size()]) :
        null;
}


%><%
String className = "org.jaffa.session.BusinessRules";
className = className != null ? className.trim() : null;
if (className != null && className.length() > 0)
  showRules(className, out, pageContext.getServletContext());
%>
