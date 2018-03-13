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
java.util.HashMap,
java.util.Iterator,
java.util.LinkedHashSet,
java.util.List,
java.util.Map,
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
org.jaffa.flexfields.FlexBean,
org.jaffa.flexfields.FlexClass,
org.jaffa.flexfields.FlexProperty,
org.jaffa.metadata.FieldMetaData,
org.jaffa.persistence.Persistent,
org.jaffa.persistence.util.PersistentHelper,
org.jaffa.rules.IObjectRuleIntrospector,
org.jaffa.rules.IPropertyRuleIntrospector,
org.jaffa.rules.RulesEngineFactory,
org.jaffa.rules.meta.MetaDataRepository,
org.jaffa.rules.rulemeta.RuleRepository,
org.jaffa.security.SecurityManager,
org.jaffa.soa.dataaccess.DataTransformer,
org.jaffa.soa.dataaccess.GraphMapping,
org.jaffa.soa.dataaccess.MappingFactory,
org.jaffa.soa.dataaccess.MappingFilter,
org.jaffa.soa.graph.GraphDataObject,
org.jaffa.util.BeanHelper,
org.jaffa.util.MessageHelper,
org.jaffa.util.StringHelper' %>

<%!
private static final Logger log = Logger.getLogger("js.extjs.jaffa.metadata.classMetaData");

/** Convert the input to HTML compatible String. */
private String toHtml(Object obj) {
  return obj == null ? "" :  StringHelper.escapeJavascript(StringHelper.convertToHTML(obj.toString()));
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
private void showRules(String className, String objectString, boolean legacy, JspWriter out, String outputClassName) throws Exception {

  // Load the Class
  Class clazz = null;
  try {
    clazz = Class.forName(className);
    if (log.isDebugEnabled())
        log.debug("Print Out Properties From " + className);
    outputClassName = (outputClassName != null && !outputClassName.isEmpty())? outputClassName : clazz.getSimpleName();
    out.write("ClassMetaData['" + outputClassName + "'] = {\n  fields: {\n  ");
  } catch (Exception e) {
    if (log.isDebugEnabled())
      log.debug("Print Out Properties From Virtual Class " + className);
    String[] splitClass = className.split("\\.");
    out.write("ClassMetaData['" + splitClass[splitClass.length-1] + "'] = {\n  fields: {\n  ");
  }
  // Unmarshal the objectString into an object
  Object bean = null;
  if (clazz != null && objectString != null && !objectString.isEmpty()) {
    try {
      // Assume the objectString to be in JSON format
      JSONObject jsonObject = JSONObject.fromObject(objectString);
      bean = JSONObject.toBean( jsonObject,  clazz);
    } catch (Exception e) {
      log.warn("Exception thrown while unmarhsalling the passed objectString '" + objectString + "' into an object", e);
    }
  }

  // Show all the Property attributes
  showProperties(className, clazz, bean, legacy, out);
  out.write("  }\n");

  // Show Object attributes
  IObjectRuleIntrospector ori = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(className, bean);
  Properties[] flexInfoArray = null;
  if (bean != null) {
    Properties p = ori.getFlexInfo();
    if (p != null)
      flexInfoArray = new Properties[] {p};
  } else
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

  String[] keys = findKeys(clazz, ori);
  if (keys != null && keys.length > 0) {
    StringBuilder buf = new StringBuilder();
    if (keys.length == 1)
      buf.append('\'').append(keys[0]).append('\'');
    else {
      for (String key : keys) {
        buf.append(buf.length() == 0 ? "[" : ", ");
        buf.append('\'').append(key).append('\'');
      }
      buf.append(']');
    }
    out.write("  ,key: ");
    out.write(buf.toString());
    out.write('\n');
  }

  String[] aggregateObjectNames = findAggregateObjectNames(clazz);
  if (aggregateObjectNames != null && aggregateObjectNames.length > 0) {
    StringBuilder buf = new StringBuilder();
    for (String aggregateObjectName : aggregateObjectNames) {
      buf.append(buf.length() == 0 ? "[" : ", ");
      buf.append('\'').append(aggregateObjectName).append('\'');
    }
    buf.append(']');
    out.write("  ,collectionNames: ");
    out.write(buf.toString());
    out.write('\n');
  }

  out.write("};");
}


/** Show Properties. */
private void showProperties(String className, Class clazz, Object bean, boolean legacy, JspWriter out) throws Exception {
  String[] propertyNames = determinePropertyNames(className, clazz);
  if (propertyNames != null && propertyNames.length > 0) {
    GraphMapping mapping = null;
    try {
        mapping = clazz != null && GraphDataObject.class.isAssignableFrom(clazz) ? MappingFactory.getInstance(clazz) : null;
    } catch (InstantiationError ignore) {
    }
    StringBuilder buf = new StringBuilder();
    Object domainInstance = null;
    Object graphInstance = null;
    if (clazz!=null && mapping!=null){
      try {
        domainInstance = mapping.getDomainClass().newInstance();
        graphInstance = clazz.newInstance();
        String[] filter={"*"};
        MappingFilter mappingFilter = MappingFilter.getInstance(mapping, filter);
        DataTransformer.buildGraphFromDomain(domainInstance, graphInstance, mapping, mappingFilter, null, false);
      } catch (Exception e){
      }
    }

    Object beanInstance = null;
    if (graphInstance==null){
      try {
        FlexClass fc = FlexClass.instance(className);
        beanInstance = fc.newInstance();
      } catch (Exception e){
      }
    }

    Map<String, String> foreignKeyMappings = new HashMap<String, String>();

    for (String propertyName : propertyNames) {
      if (mapping != null && mapping.isForeignField(propertyName)) {
        Class foreignGraph = BeanHelper.getPropertyType(clazz, propertyName);
        if (foreignGraph != null && GraphDataObject.class.isAssignableFrom(foreignGraph)) {
          List<String> foreignKeys = mapping.getForeignKeys(propertyName);
          Set<String> keys = MappingFactory.getInstance(foreignGraph).getKeyFields();
          if (foreignKeys != null && keys != null && foreignKeys.size() == keys.size()) {
            int i = 0;
            for (String key : keys) {
              String title = propertyName + '.' + key;
              String foreignKey = foreignKeys.get(i++);
              foreignKeyMappings.put(foreignKey, title);
            }
          }
        }
      }
    }

    for (String propertyName : propertyNames) {
      if (mapping != null && mapping.isForeignField(propertyName)) {
        /*
          For a foreign object, generate MetaData for all foreign-key fields.
          Consider the following scenario:
          - Graph has a foreign-object "deliveredPart : PartGraph"
          - The corresponding domain class has the foreign-key fields "deliveredPart : String" and "deliveredCage : String"
          - The foreign Graph has the key fields "part : String" and "cage : String"
          - MetaData will be generated for the fields - "deliveredPart.part: {...}" and "deliveredPart.cage: {...}"
          - If legacy==true, MetaData will continue to be generated for the field - "deliveredPart"
        */
        Class foreignGraph = BeanHelper.getPropertyType(clazz, propertyName);
        if (foreignGraph != null && GraphDataObject.class.isAssignableFrom(foreignGraph)) {
          List<String> foreignKeys = mapping.getForeignKeys(propertyName);
          Set<String> keys = MappingFactory.getInstance(foreignGraph).getKeyFields();
          if (foreignKeys != null && keys != null && foreignKeys.size() == keys.size()) {
            int i = 0;
            for (String key : keys) {
              String title = propertyName + '.' + key;
              String foreignKey = foreignKeys.get(i++);
              addPropertyMetaData(className, foreignKey, bean, buf, title, mapping.getDomainClass(), graphInstance, beanInstance, foreignKeyMappings);
            }
          }
        }
        // For backwards compatibility continue to generate MetaData for the propertyName as well
        // For new code, do not generate MetaData for the propertyName
        if (!legacy)
            continue;
      }
      addPropertyMetaData(className, propertyName, bean, buf, propertyName, null, graphInstance, beanInstance, foreignKeyMappings);
    }
    if (buf.length() > 0)
      buf.append('\n');
    out.write(buf.toString());
  }
}

/** Adds metadata for the given property into the input buffer, provided rules have been defined for the property. */
private void addPropertyMetaData(String className, String propertyName, Object bean, StringBuilder buf, String title, Class domainClass, Object newGraphInstance, Object beanInstance, Map<String, String> foreignKeyMappings) throws Exception {

  StringBuilder sb = new StringBuilder();
  IPropertyRuleIntrospector pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(className, propertyName, bean);
  String type = toJsType(pri.getPropertyType());
  if (domainClass != null && type.equals("object")) {
    // A foreign-key on a Graph will typically be modelled as another Graph, in which case obtain the datatype for that field from the domainClass
    type = toJsType(RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(domainClass, propertyName).getPropertyType());
  }
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
    if (((String) rule).startsWith("[") && ((String) rule).endsWith("]")){
      String tmp = ((String) rule).substring(1);
      tmp = tmp.substring(0, tmp.length()-1);
      sb.append("      ,labelToken: '").append(tmp).append("'\n");
    }
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
  p=pri.getHyperlinkInfo();
  if (p != null){
    String s;
    s = p.getProperty("component");
    if (s!=null && org.jaffa.security.SecurityManager.checkComponentAccess(s)){
      sb.append("      ,hyperlink: {").append("\n");
      if (s != null)
        sb.append("        component: '").append(toHtml(s)).append("'\n");
      s = p.getProperty("inputs");
      if (s != null)
        sb.append("        ,inputs: '").append(toHtml(s)).append("'\n");
      s = p.getProperty("values");
      /** check if fields are mapped to foreign objects and replace with the foreign object mapping */
      if (s != null){
        StringBuilder values = new StringBuilder();
        String[] arr = s.split(";");
        for (int i=0; i<arr.length; i++) {
          if (i > 0) values.append(";");
          String valueField = arr[i];
          String foreignValueField = foreignKeyMappings.get(valueField);
          if (foreignValueField!=null) valueField = foreignValueField;
          values.append(valueField);
        }
        sb.append("        ,values: '").append(toHtml(values.toString())).append("'\n");
      }
      sb.append("      }").append("\n");
    }

  }
  try {
    if (newGraphInstance!=null && !type.equals("object")){
      Object initializeValue =  PropertyUtils.getProperty(newGraphInstance, title);
      if (initializeValue!=null)
        sb.append("      ,initialize: '").append(initializeValue.toString().replaceAll("'", "\\\\'")).append("'\n");
    } else if (beanInstance!= null){
      Object initializeValue = ((FlexBean)beanInstance).get(title);
      if (initializeValue!=null)
        sb.append("      ,initialize: '").append(initializeValue.toString().replaceAll("'", "\\\\'")).append("'\n");
    }
  } catch (Exception e){
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
    // A flex class may have been passed. Load the corresponding FlexProperties to find the properties
    try {
      FlexClass flexClass = FlexClass.instance(className);
      for (FlexProperty flexProperty : flexClass.getDynaProperties()){
        checkedPropertyNames.add(flexProperty.getName());
      }
    } catch (Exception ignore) {
    }

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
  } else {
    // Use the Bean introspector to find the properties
    BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    if (propertyDescriptors != null) {
      for (int i = 0; i < propertyDescriptors.length; i++)
        checkedPropertyNames.add(propertyDescriptors[i].getName());
    }
  }
  return checkedPropertyNames != null && checkedPropertyNames.size() > 0 ?
        checkedPropertyNames.toArray(new String[checkedPropertyNames.size()]) :
        null;
}

/** Returns the key fields for the input class. */
private String[] findKeys(Class clazz, IObjectRuleIntrospector ori) throws Exception {
  String[] keys = null;

  // For a Graph class, obtain keys from the mapping file
  // We could have used the Introspector; but the mapping for a child graph usually excludes the key-field
  // used in the join clause
  if (clazz != null && GraphDataObject.class.isAssignableFrom(clazz)) {
    Set<String> keySet = null;
    try {
      keySet = MappingFactory.getInstance(clazz).getKeyFields();
    } catch (InstantiationError ignore) {
    }

    if (keySet != null && keySet.size() > 0)
      keys = keySet.toArray(new String[keySet.size()]);
  }

  // Check the Introspector for the primary-key rule
  if (keys == null && ori != null)
    keys = ori.getPrimaryKey();

  // For a domain class, use the PersistentHelper
  if (keys == null && clazz != null && Persistent.class.isAssignableFrom(clazz)) {
    FieldMetaData[] keyFields = PersistentHelper.getKeyFields(clazz.getName());
    if (keyFields != null && keyFields.length > 0) {
      keys = new String[keyFields.length];
      for (int i = 0; i < keyFields.length; i++)
        keys[i] = keyFields[i].getName();
    }
  }

  return keys;
}

/** Returns the aggregate object names for the input graph class. */
private String[] findAggregateObjectNames(Class clazz) {
  String[] aggregateObjectNames = null;
  if (clazz != null && GraphDataObject.class.isAssignableFrom(clazz)) {
    Set<String> aggregateObjectSet = null;
    try {
      aggregateObjectSet = MappingFactory.getInstance(clazz).getRelatedFields();
    } catch (InstantiationError ignore) {
    }

    if (aggregateObjectSet != null && aggregateObjectSet.size() > 0)
      aggregateObjectNames = aggregateObjectSet.toArray(new String[aggregateObjectSet.size()]);
  }
  return aggregateObjectNames;
}
%><%
String className = StringHelper.escapeJavascript(request.getParameter("className"));
String outputClassName = StringHelper.escapeJavascript(request.getParameter("outputClassName"));
String objectString = StringHelper.escapeJavascript(request.getParameter("object"));
Boolean legacy = Parser.parseBoolean(request.getParameter("legacy"));
className = className != null ? className.trim() : null;
objectString = objectString != null ? objectString.trim() : null;
if (log.isDebugEnabled())
  log.debug("Generating Meta Data for Class " + className + (objectString != null && !objectString.isEmpty() ? " with object state " + objectString : ""));
if (className != null && className.length() > 0)
  showRules(className, objectString, legacy != null && legacy, out,outputClassName);
%>
