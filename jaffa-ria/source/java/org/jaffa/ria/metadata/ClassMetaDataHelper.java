package org.jaffa.ria.metadata;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
//import javax.servlet.jsp.JspWriter;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.jaffa.api.services.repository.ApplicationRulesUtilities;
import org.jaffa.datatypes.Parser;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.flexfields.FlexClass;
import org.jaffa.flexfields.FlexProperty;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.persistence.Persistent;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.rules.IObjectRuleIntrospector;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.IRulesEngine;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.rulemeta.RuleRepository;
import org.jaffa.soa.dataaccess.DataTransformer;
import org.jaffa.soa.dataaccess.GraphMapping;
import org.jaffa.soa.dataaccess.MappingFactory;
import org.jaffa.soa.dataaccess.MappingFilter;
import org.jaffa.soa.graph.GraphDataObject;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.StringHelper;

public class ClassMetaDataHelper {
    private static final Logger log = Logger.getLogger("js.extjs.jaffa.metadata.classMetaData");

    /** The quotation character used for JSON. */
    private static final String JSON_QUOTE = "\"";

    /** The single quote used for some JavaScript quoting. */
    private static final String SINGLE_QUOTE = "'";

    /** JSON output desired. */
    public static final String JSON = "JSON";

    /** The quote to use to surround property names (possibly none). */
    String propertyNameQuote = "";

    /** The quote to use to surround property values (possibly none). */
    String propertyValueQuote = SINGLE_QUOTE;

    /** Convert the input to HTML compatible String. */
    String toHtml(Object obj) {
        return obj == null ? "" :  StringHelper.escapeJavascript(StringHelper.convertToHTML(obj.toString()));
    }

    /** Converts the input String to an appropriate value based on the jsType,
     *  and then applies the necessary quote.
     * @param value the value to be converted into JavaScript form
     * @param jsType the JavaScript type for the value
     * @return a value suitable for JavaScript
     */
    String quoteValueByType(String value, String jsType) {
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

    /**
     * Converts the input Properties to a javascript object.
     * NOTE: this method name is a misnomer - the returned string is evaluable JavaScript,
     * not JSON!
     */
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

    /**
     *  Find the rules for the input Class.
     */
    // NOTE: This method branches between old code from classMetaData.jsp,
    // that generates JavaScript, and refactored code that generates JSON
    public void showRules(String className, String objectString, boolean legacy,
                          Writer out, String outputClassName, String outputStyle) throws Exception {
        if (JSON.equalsIgnoreCase(outputStyle)) {
            propertyNameQuote = JSON_QUOTE;
            propertyValueQuote = JSON_QUOTE;
            Class clazz = getObjectClass(className);
            writeObject(clazz, className, objectString, legacy, out);
        }
        // Code prior to October 2018 expects an evaluable JavaScript output format
        else {
            showRules(className, objectString, legacy, out, outputClassName);
        }
    }

    /** Find the rules for the input Class. */
    public void showRules(String className, String objectString, boolean legacy, Writer out, String outputClassName) throws Exception {
        // TODO eventually replace with showRulesRefactored

        // Load the Class
        // TODO eventually replace try block with writeLhsOfAssignment
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
        // TODO eventually replace with writeObject
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
        // TODO eventually replace with writeFlexInfo
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

        // TODO eventually replace with writeLabel
        String label = ori.getLabel();
        if(label != null ) {
            out.write("  ,label: '");
            out.write(toHtml(MessageHelper.replaceTokens(label)));
            out.write("'\n");
        }

        // TODO eventually replace with writeKeys
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

        // TODO eventually replace with writeAggregateObjectNames
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

    /** Show Properties.
     * @see #showPropertiesRefactored
     */
    void showProperties(String className, Class clazz, Object bean, boolean legacy, Writer out) throws Exception {
        String[] propertyNames = determinePropertyNames(className, clazz);
        if (propertyNames != null && propertyNames.length > 0) {
            GraphMapping mapping = null;
            try {
                mapping = clazz != null && GraphDataObject.class.isAssignableFrom(clazz)
                        ? MappingFactory.getInstance(clazz) : null;
            } catch (InstantiationError ignore) {
            }
            StringBuilder buf = new StringBuilder();
            Object domainInstance = null;
            Object graphInstance = null;
            if (clazz != null && mapping != null) {
                try {
                    domainInstance = mapping.getDomainClass().newInstance();
                    graphInstance = clazz.newInstance();
                    String[] filter = { "*" };
                    MappingFilter mappingFilter = MappingFilter.getInstance(mapping, filter);
                    DataTransformer.buildGraphFromDomain(domainInstance, graphInstance,
                                                         mapping, mappingFilter, null, false);
                }
                catch (Exception e) {
                    log.info(e.toString());
                }
            }

            Object beanInstance = null;
            if (graphInstance == null){
                try {
                    FlexClass fc = FlexClass.instance(className);
                    beanInstance = fc.newInstance();
                } catch (Exception e){
                    log.info(e.toString());
                }
            }

//            Map<String, String> foreignKeyMappings =
//                    buildForeignKeyMappings(clazz, propertyNames, mapping);
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
          - The corresponding domain class has the foreign-key fields
               "deliveredPart : String" and "deliveredCage : String"
          - The foreign Graph has the key fields "part : String" and "cage : String"
          - MetaData will be generated for the fields -
               "deliveredPart.part: {...}" and "deliveredPart.cage: {...}"
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
                                addPropertyMetaData(className, foreignKey,
                                        bean, buf, title, mapping.getDomainClass(),
                                        graphInstance, beanInstance, foreignKeyMappings);
                            }
                        }
                    }
                    // For backwards compatibility continue to generate MetaData for the propertyName as well
                    // For new code, do not generate MetaData for the propertyName
                    if (!legacy)
                        continue;
                }
                addPropertyMetaData(className, propertyName, bean, buf, propertyName,
                                    null, graphInstance, beanInstance, foreignKeyMappings);
            }
            if (buf.length() > 0)
                buf.append('\n');
            out.write(buf.toString());
        }
    }

    /**
     * Adds metadata for the given property into the input buffer,
     *  provided rules have been defined for the property.
     * @see #addPropertyMetaDataRefactored(String, String, Object, StringBuilder, String, Class, Object, Object, Map)
     */
    private void addPropertyMetaData(String className, String propertyName, Object bean,
                                     StringBuilder buf, String title, Class domainClass,
                                     Object newGraphInstance, Object beanInstance,
                                     Map<String, String> foreignKeyMappings) throws Exception {
        StringBuilder sb = new StringBuilder();
        IPropertyRuleIntrospector pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(className, propertyName, bean);
        String type = ApplicationRulesUtilities.toJsType(pri.getPropertyType());
        if (domainClass != null && type.equals("object")) {
            // A foreign-key on a Graph will typically be modelled as another Graph, in which case obtain the datatype for that field from the domainClass
            type = ApplicationRulesUtilities.toJsType(RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(domainClass, propertyName).getPropertyType());
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
            Collection<String> checkedPropertyNames = new LinkedHashSet<>();
            if (clazz == null) {
                // A flex class may have been passed. Load the corresponding FlexProperties to find the properties
                try {
                    FlexClass flexClass = FlexClass.instance(className);
                    for (FlexProperty flexProperty : flexClass.getDynaProperties()) {
                        checkedPropertyNames.add(flexProperty.getName());
                    }
                }
                catch (Exception ignore) {
                }

                if (checkedPropertyNames.size() == 0) {
                    // A non-flex virtual class is passed. Find properties for which rules have been declared
                    Collection ruleNames = RuleRepository.instance().getRuleNames();
                    for (Iterator ruleItr = ruleNames.iterator(); ruleItr.hasNext(); ) {
                        String ruleName = (String) ruleItr.next();
                        Map propertyRuleMap = MetaDataRepository.instance().getPropertyRuleMap(className, ruleName);
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
            else {
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


    ///////////////////////////////////////////////////////////////////////////////////////////
    // CODE REFACTORED TO PRODUCE JSON BEGINS HERE
    ///////////////////////////////////////////////////////////////////////////////////////////


    /** Find the rules for the input Class. */
    public void showRulesRefactored(String className, String objectString, boolean legacy,
                          Writer out, String outputClassName) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Generating Meta Data for Class " + className +
                      (objectString != null && !objectString.isEmpty() ?
                       " with object state " + objectString : ""));
        }
        // Load the Class and write the left hand side (LHS) of the assignment statement
        Class clazz = writeLhsOfAssignment(className, out, outputClassName);
        // write the object to be assigned (the RHS of the assignment)
        writeObject(clazz, className, objectString, legacy, out);
        out.write(";"); // statement terminator
    }

    /**
     * Get the class corresponding to the name
     * @param name the name of the class
     * @return the class, possibly null for a "virtual class"
     */
    private Class getObjectClass(String name) {
        Class clazz;
        try {
            clazz = Class.forName(name);
        } catch (Exception e) {
            clazz = null;
        }
        return clazz;
    }

    /**
     * Write out the left hand side of an executable JavaScript assignment statement, e.g.,
     * "ClassMetaData['YourClassName'] = "
     * @param className the name of the class
     * @param out where to write
     * @param suggestedClassName a suggested class name
     * @return the class corresponding to the className.  If it's a "virtual class",
     * this will be null.
     * @throws IOException when the write fails
     */
    Class writeLhsOfAssignment(String className, Writer out,
                              String suggestedClassName) throws IOException {
        Class clazz = null;
        String outputClassName;
        try {
            clazz = Class.forName(className);

            if (log.isDebugEnabled()) {
                log.debug("Print Out Properties From " + className);
            }
            outputClassName = (suggestedClassName != null && !suggestedClassName.isEmpty())
                              ? suggestedClassName : clazz.getSimpleName();
        } catch (Exception e) {
            // Print output for "virtual" (non-existent) class
            if (log.isDebugEnabled()) {
                log.debug("Print Out Properties From Virtual Class " + className);
            }
            String[] splitClass = className.split("\\.");
            outputClassName = splitClass[splitClass.length-1];
        }
        out.write("ClassMetaData['" + outputClassName + "'] = ");
        return clazz;
    }

    /**
     * Write text that can be used to create a JavaScript object
     * @param className the name of the object's class
     * @param out where the text representing the object will be accumulated
     */
    private void writeObject(Class clazz, String className, String objectString, boolean legacy, Writer out)
            throws Exception {
        out.write("{\n  ");
        out.write(quoteName("fields"));
        out.write(": {\n  "); // beginning of object, beginning of fields
        // Unmarshall the objectString into an object
        Object bean = createBeanFromObjectStream(objectString, clazz);

        // Show all the Property attributes
        showPropertiesRefactored(className, clazz, bean, legacy, out);
        out.write("  }\n"); // end of fields

        // Show Object attributes
        IRulesEngine rulesEngine = RulesEngineFactory.getRulesEngine();
        IObjectRuleIntrospector ori = rulesEngine.getObjectRuleIntrospector(className, bean);
        writeFlexInfo(out, bean, ori);
        String label = ori.getLabel();
        writeLabel(out, label);
        String[] keys = findKeys(clazz, ori);
        writeKeys(out, keys);

        String[] aggregateObjectNames = findAggregateObjectNames(clazz);
        if (aggregateObjectNames != null && aggregateObjectNames.length > 0) {
            writeAggregateObjectNames(out, aggregateObjectNames);
        }
        out.write("}"); // end of object
    }

    /**
     * Unmarshall the objectString into an object
     */
    private Object createBeanFromObjectStream(String objectString, Class clazz) {
        Object bean = null;
        if (clazz != null && objectString != null && !objectString.isEmpty()) {
            try {
                // Assume the objectString to be in JSON format
                JSONObject jsonObject = JSONObject.fromObject(objectString);
                bean = JSONObject.toBean( jsonObject,  clazz);
            } catch (Exception e) {
                log.warn("Exception thrown while unmarhsalling the passed objectString '"
                         + objectString + "' into an object", e);
            }
        }
        return bean;
    }

    /** Show Properties.
     * @see #showProperties
     */
    // Implementation NOTE: the primary difference between this and showProperties is
    // that this calls addPropertyMetaDataRefactored instead of addPropertyMetaData.
    // Also, some methods have been extracted.
    void showPropertiesRefactored(String className, Class clazz, Object bean, boolean legacy, Writer out) throws Exception {
        String[] propertyNames = determinePropertyNames(className, clazz);
        if (propertyNames != null && propertyNames.length > 0) {
            GraphMapping mapping = null;
            try {
                mapping = clazz != null && GraphDataObject.class.isAssignableFrom(clazz)
                          ? MappingFactory.getInstance(clazz) : null;
            } catch (InstantiationError ignore) {
            }
            StringBuilder buf = new StringBuilder();
            Object domainInstance = null;
            Object graphInstance = null;
            if (clazz != null && mapping != null) {
                try {
                    domainInstance = mapping.getDomainClass().newInstance();
                    graphInstance = clazz.newInstance();
                    String[] filter = { "*" };
                    MappingFilter mappingFilter = MappingFilter.getInstance(mapping, filter);
                    DataTransformer.buildGraphFromDomain(domainInstance, graphInstance,
                                                         mapping, mappingFilter, null, false);
                }
                catch (Exception e) {
                    log.info(e.toString());
                }
            }

            Object beanInstance = null;
            if (graphInstance == null){
                try {
                    FlexClass fc = FlexClass.instance(className);
                    beanInstance = fc.newInstance();
                } catch (Exception e){
                    log.info(e.toString());
                }
            }

            Map<String, String> foreignKeyMappings =
                    buildForeignKeyMappings(clazz, propertyNames, mapping);

            for (String propertyName : propertyNames) {
                if (mapping != null && mapping.isForeignField(propertyName)) {
        /*
          For a foreign object, generate MetaData for all foreign-key fields.
          Consider the following scenario:
          - Graph has a foreign-object "deliveredPart : PartGraph"
          - The corresponding domain class has the foreign-key fields
               "deliveredPart : String" and "deliveredCage : String"
          - The foreign Graph has the key fields "part : String" and "cage : String"
          - MetaData will be generated for the fields -
               "deliveredPart.part: {...}" and "deliveredPart.cage: {...}"
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
                                addPropertyMetaDataRefactored(className, foreignKey,
                                                    bean, buf, title, mapping.getDomainClass(),
                                                    graphInstance, beanInstance, foreignKeyMappings);
                            }
                        }
                    }
                    // For backwards compatibility continue to generate MetaData for the propertyName as well
                    // For new code, do not generate MetaData for the propertyName
                    if (!legacy) {
                        continue;
                    }
                }
                addPropertyMetaDataRefactored(className, propertyName, bean, buf, propertyName,
                                    null, graphInstance, beanInstance, foreignKeyMappings);
            }
            if (buf.length() > 0) {
                buf.append('\n');
            }
            out.write(buf.toString());
        }
    }

    /**
     * If flex info exists, writes the flex info, following the "flexInfo" field name.
     * @param out where to write
     */
    private void writeFlexInfo(Writer out, Object bean, IObjectRuleIntrospector ori) throws IOException {
        Properties[] flexInfoArray = null;
        if (bean != null) {
            Properties p = ori.getFlexInfo();
            if (p != null) {
                flexInfoArray = new Properties[] { p };
            }
        }
        else {
            flexInfoArray = ori.getDeclaredFlexInfo();
        }

        if (flexInfoArray != null && flexInfoArray.length > 0) {
            writeFlexInfoArrayProperties(out, flexInfoArray);
        }
    }

    void writeAggregateObjectNames(Writer out, String[] aggregateObjectNames) throws IOException {
        StringBuilder buf = new StringBuilder();
        for (String aggregateObjectName : aggregateObjectNames) {
            buf.append(buf.length() == 0 ? "[" : ", ");
            buf.append('\'').append(aggregateObjectName).append('\'');
        }
        buf.append(']');
        out.write("  ,");
        out.write(quoteName("collectionNames"));
        out.write(": ");
        String aggregateObjectNamesString = buf.toString();
        out.write(aggregateObjectNamesString);
        out.write('\n');
    }

    Map<String, String> buildForeignKeyMappings(Class clazz, String[] propertyNames, GraphMapping mapping)
            throws IntrospectionException {
        Map<String, String> foreignKeyMappings = new HashMap<>();
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
        return foreignKeyMappings;
    }

    /**
     * Writes the flex info, following the "flexInfo" field name.
     * @param out where to write
     */
    void writeFlexInfoArrayProperties(Writer out, Properties[] flexInfoArray) throws IOException {
        StringBuilder buf = new StringBuilder();
        for (Properties p : flexInfoArray) {
            if (buf.length() > 0) {
                buf.append(", ");
            }
            buf.append(toJavaScript(p));
        }
        out.write("  ,");
        out.write(quoteName("flexInfo"));
        out.write(": [");
        String flexInfoPropertyString = buf.toString();
        out.write(flexInfoPropertyString);
        out.write("]\n");
    }

    /**
     * Writes the label, following the "label" field name.
     * @param out where to write
     * @param label the label to write
     */
    void writeLabel(Writer out, String label) throws IOException {
        if (label != null ) {
            out.write("  ,");
            out.write(quoteName("label"));
            out.write(": ");
            out.write(quoteValue(toHtml(MessageHelper.replaceTokens(label))));
            out.write("\n");
        }
    }

    /**
     * Writes one or more keys, following the "key" field name.
     * @param out where to write
     * @param keys the keys to write
     */
    void writeKeys(Writer out, String[] keys) throws Exception {
        if (keys != null && keys.length > 0) {
            StringBuilder buf = new StringBuilder();

            // gather the keys (which are the values following the "keys" field name)
            if (keys.length == 1) {
                buf.append(quoteValue(keys[0]));
            }
            else {
                for (String key : keys) {
                    buf.append(buf.length() == 0 ? "[" : ", ");
                    buf.append(quoteValue(key));
                }
                buf.append(']');
            }
            out.write("  ,");
            out.write(quoteName("key"));
            out.write(": ");
            out.write(buf.toString());
            out.write('\n');
        }
    }

    /**
     * Adds metadata for the given property into the input buffer,
     *  provided rules have been defined for the property.
     * @see #addPropertyMetaData(String, String, Object, StringBuilder, String, Class, Object, Object, Map)
     */
    void addPropertyMetaDataRefactored(String className, String propertyName, Object bean,
                                     StringBuilder buf, String title, Class domainClass,
                                     Object newGraphInstance, Object beanInstance,
                                     Map<String, String> foreignKeyMappings) throws Exception {
        StringBuilder sb = new StringBuilder();
        IRulesEngine rulesEngine = RulesEngineFactory.getRulesEngine();
        IPropertyRuleIntrospector pri =
                rulesEngine.getPropertyRuleIntrospector(className, propertyName, bean);
        String type = ApplicationRulesUtilities.toJsType(pri.getPropertyType());
        if (domainClass != null && type.equals("object")) {
            // A foreign-key on a Graph will typically be modelled as another Graph,
            // in which case obtain the datatype for that field from the domainClass
            type = ApplicationRulesUtilities.toJsType(
                    rulesEngine.getPropertyRuleIntrospector(domainClass, propertyName).getPropertyType());
        }
        Object rule;
        if (pri.isHidden()) {
            appendProperty(sb, "hidden", true);
        }
        if (pri.isReadOnly()){
            appendProperty(sb, "readOnly", true);
        }
        if (pri.isMandatory()) {
            appendProperty(sb, "mandatory", true);
        }

        rule = pri.getLabel();
        if (rule != null ) {
            appendProperty(sb, "label", toHtml(MessageHelper.replaceTokens((String) rule)));
            if (((String) rule).startsWith("[") && ((String) rule).endsWith("]")){
                String tmp = ((String) rule).substring(1);
                tmp = tmp.substring(0, tmp.length()-1);
                appendProperty(sb, "labelToken", tmp);
            }
        }

        rule = pri.getMinLength();
        if (rule != null ) {
            appendProperty(sb, "minLength", rule);
        }

        rule = pri.getMaxLength();
        if (rule != null ) {
            if (!type.equals("number")) {
                appendProperty(sb, "maxLength", rule);
            } else {
                int maxLength = (Integer) rule;
                appendProperty(sb, "maxIntegralLength", maxLength);

                rule = pri.getMaxFracLength();
                if (rule != null && ((Integer) rule) > 0) {
                    appendProperty(sb, "decimalPrecision", rule);
                    maxLength = maxLength + 1 + ((Integer) rule); //account for the decimal separator
                } else {
                    appendProperty(sb, "allowDecimals", false);
                }

                //account for negative sign
                rule = pri.getMinValue();
                if (rule == null || ((Number) rule).doubleValue() < 0) {
                    maxLength = 1 + maxLength;
                }
                appendProperty(sb, "maxLength", maxLength);
            }
        }
        rule = pri.getMinValue();
        if (rule != null ) {
            appendProperty(sb, "minValue", rule);
        }

        rule = pri.getMaxValue();
        if (rule != null ) {
            appendProperty(sb, "maxValue", rule);
        }

        rule = pri.getCaseType();
        if (rule != null ) {
            appendProperty(sb, "caseType", toHtml(rule));
        }

        rule = pri.getLayout();
        if (rule != null ) {
            appendProperty(sb, "layout", toHtml(rule));
        }

        rule = pri.getPattern();
        if (rule != null ) {
            appendProperty(sb, "pattern", toHtml(rule));
        }

        rule = pri.getCommentStyle();
        if (rule != null) {
            appendProperty(sb, "commentStyle", toHtml(rule));
        }

        appendForeignKeyInfo(sb, pri.getForeignKeyInfo());
        appendPropertyInfo(sb, pri.getPropertyInfo());
        appendHyperlinkInfo(foreignKeyMappings, sb, pri.getHyperlinkInfo());

        try {
            if (newGraphInstance != null && !type.equals("object")){
                Object initializeValue =  PropertyUtils.getProperty(newGraphInstance, title);
                if (initializeValue != null) {
                    appendProperty(sb, "initialize",
                                   initializeValue.toString().replaceAll("'", "\\\\'"));
                }
            } else if (beanInstance != null){
                Object initializeValue = ((FlexBean)beanInstance).get(title);
                if (initializeValue != null) {
                    appendProperty(sb, "initialize",
                                   initializeValue.toString().replaceAll("'", "\\\\'"));
                }
            }
        } catch (Exception e){
            log.info(e.toString());
        }

        Map<String, String> inListValues = pri.getInListValues();
        appendInListValues(sb, type, inListValues);

        if (sb.length() > 0) {
            buf.append(buf.length() > 0 ? "," : "  ");
            buf.append(quoteName(toHtml(title)));
            buf.append(": {\n");
            buf.append("      ").append(quoteName("type")).append(": ").append(quoteValue(type)).append("\n");
            buf.append(sb);
            buf.append("    }");
        }
    }

    private void appendInListValues(StringBuilder sb, String type, Map<String, String> inListValues) {
        if (inListValues != null && inListValues.size() > 0) {
            StringBuilder inList = new StringBuilder();
            for (Map.Entry<String, String> entry : inListValues.entrySet()) {
                String value = toHtml(entry.getKey());
                String label = entry.getValue();
                label = label != null ? toHtml(MessageHelper.replaceTokens(label)) : value;
                if (inList.length() > 0) {
                    inList.append(", ");
                }
                String quotedValue = quoteValueByTypeJSON(value, type);
                inList.append("[");
                inList.append(quotedValue);
                inList.append(", ");
                inList.append(quoteValue(label));
                inList.append("]");
            }
            sb.append("      ,inList: ").append('[').append(inList).append(']').append('\n');
        }
    }

    /**
     * Append the foreign key info to the string builder.  The output has the form:
     *   foreignKeyInfo: {a, b, c}
     * @param builder the string builder to which we add the foreign key info
     * @param foreignKeyInfo the information to add
     */
    void appendForeignKeyInfo(StringBuilder builder, Properties foreignKeyInfo) {
        if (foreignKeyInfo != null) {
            builder.append("      ,");
            builder.append(quoteName("foreignKeyInfo"));
            builder.append(": ");
            builder.append(propertiesToString(foreignKeyInfo));
            builder.append('\n');
        }
    }

    /**
     * Append the property info for display sequence, display group,
     * and display group label to the string builder.
     * @param builder the string builder to which we add the foreign key info
     * @param propertyInfo the information to add
     */
    void appendPropertyInfo(StringBuilder builder, Properties propertyInfo) {
        if (propertyInfo != null) {
            String s;
            s = propertyInfo.getProperty("display-sequence");
            if (s != null) {
                appendProperty(builder, "displaySequence", toHtml(s));
            }
            s = propertyInfo.getProperty("display-group");
            if (s != null) {
                appendProperty(builder, "displayGroup", toHtml(s));
            }
            s = propertyInfo.getProperty("display-group-label");
            if (s != null) {
                appendProperty(builder, "displayGroupLabel", toHtml(s));
            }
        }
    }

    void appendHyperlinkInfo(Map<String, String> foreignKeyMappings,
                             StringBuilder sb, Properties hyperlinkInfo) {
        if (hyperlinkInfo != null) {
            String s = hyperlinkInfo.getProperty("component");
            if (s != null && org.jaffa.security.SecurityManager.checkComponentAccess(s)) {
                sb.append("      ,");
                sb.append(quoteName("hyperlink"));
                sb.append(": {\n"); // begin hyperlink properties

                appendProperty(sb, "component", toHtml(s));

                s = hyperlinkInfo.getProperty("inputs");
                if (s != null) {
                    appendProperty(sb, "inputs", toHtml(s));
                }

                s = hyperlinkInfo.getProperty("values");

                /* check if fields are mapped to foreign objects and replace with
                 *  the foreign object mapping */
                if (s != null) {
                    StringBuilder values = new StringBuilder();
                    String[] arr = s.split(";");
                    for (int i = 0; i < arr.length; i++) {
                        if (i > 0) {
                            values.append(";");
                        }
                        String valueField = arr[i];
                        String foreignValueField = foreignKeyMappings.get(valueField);
                        if (foreignValueField != null) {
                            valueField = foreignValueField;
                        }
                        values.append(valueField);
                    }
                    appendProperty(sb, "values", quoteValue(toHtml(values.toString())));
                }
                sb.append("      }\n"); // end hyperlink properties
            }
        }
    }


    /**
     * Append the property keys and values to the accumulating string
     * @param properties the properties to add
     * @param buf the accumulator
     */
    private void appendHtmlProperties(Properties properties, StringBuilder buf) {
        for (Enumeration e = properties.propertyNames(); e.hasMoreElements(); ) {
            if (buf.length() > 0) {
                buf.append(", ");
            }
            String key = (String) e.nextElement();
            String value = properties.getProperty(key);
            buf.append(quoteName(toHtml(key)));
            buf.append(": ");
            buf.append(quoteValue(toHtml(value)));
        }
    }

    /**
     * Appends information about the indicated property to the string builder
     * @param builder the builder for the string being built
     * @param propertyName the property to add
     * @param value the property's value
     */
    void appendProperty(StringBuilder builder, String propertyName, Object value) {
//        sb.append("      ,hidden: true\n");
        builder.append("      ,");
        builder.append(quoteName(propertyName));
        builder.append(": ");
        appendValue(builder, value);
        builder.append("\n");
    }

    /**
     * Appends information about the indicated property value to the string builder
     * @param builder the builder for the string being built
     * @param value the property's value
     */
    private void appendValue(StringBuilder builder, Object value) {
        // Handle cases that don't require quotes
        if (value instanceof Boolean || value instanceof Number) {
            builder.append(value);
        }
        // Quotes required
        else {
            builder.append(quoteValue(value));
        }
    }

    /** Converts the input Properties to a javascript object. */
    String propertiesToString(Properties properties) {
        String result = "";

        if (properties != null) {
            StringBuilder builder = new StringBuilder();
            appendHtmlProperties(properties, builder);
            builder.insert(0, '{');
            builder.append('}');
            result = builder.toString();
        }
        return result;
    }

    /**
     * Surround the indicated name with quotes, if any are required.
     * @param name the name
     * @return the name, possibly surrounded by quotes
     */
    private String quoteName(Object name) {
        return quoteObject(name, propertyNameQuote);
    }

    /**
     * Surround the indicated value with quotes, if any are required.
     * @param value the value
     * @return the value, possibly surrounded by quotes
     */
    private String quoteValue(Object value) {
        return quoteObject(value, propertyValueQuote);
    }

    /**
     * Surround the indicated value with quotes, if any are required.
     * @param value the value
     * @return the value, possibly surrounded by quotes
     */
    private String quoteObject(Object value, String quote) {
        return quote + value + quote;
    }

    /** Converts the input String to an appropriate value based on the jsType,
     *  and then applies the necessary quote.
     * @param value the value to be converted into JSON form
     * @param jsType the JavaScript type for the value
     * @return a value suitable for JSON
     */
    private String quoteValueByTypeJSON(String value, String jsType) {
        String result = value;
        if (value != null) {
            if ("boolean".equals(jsType)) {
                Boolean booleanValue = Parser.parseBoolean(value);
                result = booleanValue != null ? booleanValue.toString() : "false";
            } else if (!"number".equals(jsType)) { // (do nothing for numbers)
                result = JSON_QUOTE + value + JSON_QUOTE;
            }
        }
        return result;
    }

    /** Converts the input Properties to a javascript object. */
    String toJavaScript(Properties p) {
        StringBuilder buf = new StringBuilder();
        appendHtmlProperties(p, buf);
        buf.insert(0, '{');
        buf.append('}');
        return buf.toString();
    }


}
