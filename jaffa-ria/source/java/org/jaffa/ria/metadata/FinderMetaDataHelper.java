/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3. The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4. Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5. Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package org.jaffa.ria.metadata;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.directwebremoting.Container;
import org.directwebremoting.extend.CreatorManager;
import org.directwebremoting.extend.Creator;
import org.directwebremoting.impl.ContainerUtil;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.IDateBase;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.metadata.PropertyRuleIntrospectorUsingFieldMetaData;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.rules.rulemeta.RuleMetaHelper;
import org.jaffa.rules.rulemeta.data.RuleMetaDataCriteria;
import org.jaffa.soa.dataaccess.GraphMapping;
import org.jaffa.soa.dataaccess.MappingFactory;
import org.jaffa.soa.graph.GraphDataObject;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.StringHelper;

public class FinderMetaDataHelper {

   private static final Logger log = Logger.getLogger(FinderMetaDataHelper.class);

    /** The quotation character used for JSON. */
    static final String JSON_QUOTE = "\"";

    /** The single quote used for some JavaScript quoting. */
    static final String SINGLE_QUOTE = "'";

    /** JSON output desired. */
    public static final String JSON = "JSON";

    /** The names of fields whose corresponding values are not strings. */
    private static final List<String> NON_STRING_FIELDS =
            Arrays.asList("alwaysHidden", "hidden", "maxLength", "sortable");

    /** The quote to use to surround property names (possibly none). */
    String propertyNameQuote = "";

    /** The quote to use to surround property values (possibly none). */
    String propertyValueQuote = SINGLE_QUOTE;


    /** This Pattern is used to obtain the domain-classname from a Tx class. */
      private static final Pattern PATTERN_TO_OBTAIN_DOMAIN_CLASSNAME = Pattern.compile("^(.+)\\.components\\..+\\.(.+)(Lookup|Finder)(Tx)$");

      /**
       * A flag to control caching.
       * Caching is disabled for now, since translated labels are being returned in the metaData.
       * Caching may only be enabled, if the cached metaData contains label-tokens and if a routine is written
       * for translating the cached label-tokens on the way out.
       */
      private static boolean c_enableCaching = false;

      /** A cache of generated metaData for a unique set of input parameters. */
      private static final ConcurrentHashMap<Map<String, String>, String> c_metaDataCache = c_enableCaching ? new ConcurrentHashMap<Map<String, String>, String>() : null;


      /** Looks up the request stream for input parameters and then generates appropriate metaData. */
      public static void perform(HttpServletRequest request, JspWriter out, ServletContext servletContext) throws Exception {
          // Create a parameter Map from the input request
          Map<String, String> parameters = getParameters(request);
          if (log.isDebugEnabled())
              log.debug("Input: " + parameters);

          // Error out if parameters are not passed or if unsafe parameter is passed
          if (parameters == null || !parameters.get("component").matches("^[a-zA-Z0-9._$]*$")) {
              StringBuilder strBuilder = new StringBuilder("MetaData cannot be generated since ");
              strBuilder.append(parameters == null ? "parameters have not been passed"
                  : "unsafe input detected in component field: " + parameters.get("component"));
              log.error(strBuilder.toString());
              throw new IllegalArgumentException(strBuilder.toString());
          }

          // Obtain metaData
          String metaData = c_enableCaching ? obtainMetaData(parameters, servletContext)
                                            : generateMetaData(parameters, servletContext);
          out.print(metaData);
      }

      /** Returns a Map containing the parameters needed for generating the metaData. */
      private static Map<String, String> getParameters(HttpServletRequest request) throws Exception {
          // Create a Map and load it with non-null request parameters
          Map<String, String> m = new HashMap<String, String>();
          String component = StringHelper.escapeJavascript(request.getParameter("component"));
          if (component != null) {
              m.put("component", component);
          }
          String bypassCriteriaScreen = StringHelper.escapeJavascript(request.getParameter("bypassCriteriaScreen"));
          if (bypassCriteriaScreen != null) {
              m.put("bypassCriteriaScreen", bypassCriteriaScreen);
          }
          String dynamicParameters = StringHelper.escapeJavascript(request.getParameter("dynamicParameters"));
          if (dynamicParameters != null) {
              m.put("dynamicParameters", dynamicParameters);
          }
          String staticParameters = StringHelper.escapeJavascript(request.getParameter("staticParameters"));
          if (staticParameters != null) {
              m.put("staticParameters", staticParameters);
          }
          String targetFields = StringHelper.escapeJavascript(request.getParameter("targetFields"));
          if (targetFields != null) {
              m.put("targetFields", targetFields);
          }

          String outputStyle = StringHelper.escapeJavascript(request.getParameter("outputStyle"));
          if (outputStyle != null) {
              m.put("outputStyle", outputStyle);
          }

          // Use the PropertyRuleIntrospector to determine the parameters which were not passed in the request
          String className = StringHelper.escapeJavascript(request.getParameter("className"));
          String propertyName = StringHelper.escapeJavascript(request.getParameter("propertyName"));
          if (className != null && propertyName != null) {
              IPropertyRuleIntrospector i = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(className, propertyName, null);
              Properties p = i.getForeignKeyInfo();
              if (p != null && !p.isEmpty()) {
                  // Add the parameter if it doesn't already exist in the Map, but exists in the foreign-key rule
                  if (!m.containsKey("component") && p.containsKey("component"))
                      m.put("component", p.getProperty("component"));
                  if (!m.containsKey("bypassCriteriaScreen") && p.containsKey("bypassCriteriaScreen"))
                      m.put("bypassCriteriaScreen", p.getProperty("bypassCriteriaScreen"));
                  if (!m.containsKey("dynamicParameters") && p.containsKey("dynamicParameters"))
                      m.put("dynamicParameters", p.getProperty("dynamicParameters"));
                  if (!m.containsKey("staticParameters") && p.containsKey("staticParameters"))
                      m.put("staticParameters", p.getProperty("staticParameters"));
                  if (!m.containsKey("targetFields") && p.containsKey("targetFields"))
                      m.put("targetFields", p.getProperty("targetFields"));
            }
          }
          return !m.isEmpty() && m.containsKey("component") ? m : null;
      }

      /** Obtains metaData from the cache. If not found, generates metaData based on the input parameters. */
      private static String obtainMetaData(Map<String, String> parameters, ServletContext servletContext) throws Exception {
          String metaData = c_metaDataCache.get(parameters);
          if (metaData == null) {
              String generatedMetaData = generateMetaData(parameters, servletContext);
              metaData = c_metaDataCache.putIfAbsent(parameters, generatedMetaData);
              if (metaData == null) {
                  metaData = generatedMetaData;
              } else {
                  if (log.isDebugEnabled())
                      log.debug("Obtained metaData from cache");
              }
          } else {
              if (log.isDebugEnabled())
                  log.debug("Obtained metaData from cache");
          }
          return metaData;
      }

      /**
       * Generates metaData based on the input parameters. In many cases, this metaData will be
       * used to create two executable JavaScript statements:
       * (1) Ext.apply(ClassMetaData { serviceName : new Jaffa.data.FinderOutDto({some object}) };
       * (2) ClassMetaData.serviceName;
       *
       * Prior to October 2018, the JavaScript would eval() the output from this method to execute
       * the two statements above, i.e., this method would generate a String including "Ext.apply".
       * After October 2018, The two JavaScript statements will exist in the JavaScript code, and
       * this method will provide the "some object" argument to the new Jaffa.data.FinderOutDto call.
       * In other words, the .js file will contain the "Ext.apply" statement, and this method will
       * supply the JSON argument on which it operates.
       */
      private static String generateMetaData(Map<String, String> parameters, ServletContext servletContext)
              throws Exception {
          String serviceName = getServiceName(parameters);
          Class serviceClass = getServiceClass(serviceName, servletContext);
          boolean graphBased = isGraphBased(serviceClass);
          String serviceMethodName = getServiceMethodName(serviceClass, graphBased);
          Class outputClass = getOutputClass(serviceClass, serviceMethodName, graphBased);
          Class domainClass = getDomainClass(serviceClass, outputClass, graphBased);
          String[] keys = (domainClass == null) ? null
                                                : getKeys(serviceClass, outputClass, domainClass, graphBased);
          Map<String, Map<String, String>> fieldMap =
                  getFieldMap(serviceClass, outputClass, domainClass, graphBased, keys);
          String valueField = getValueField(parameters, keys);
          Map<String, Map<String, String>> codeDescField = getCodeDescField(serviceClass, valueField, fieldMap);
          String codeDescFieldName = codeDescField != null ? codeDescField.keySet().iterator().next() : null;
          String staticParameters = parseStaticParameters(parameters);

          String metaData;
          if (JSON.equals(parameters.get("outputStyle"))) {
              FinderMetaDataHelper helper = new FinderMetaDataHelper();
              helper.propertyNameQuote = JSON_QUOTE;
              helper.propertyValueQuote = JSON_QUOTE;
//              metaData = helper.getJSONMetaData(serviceName, graphBased, serviceMethodName, keys, fieldMap, valueField,
//                                                codeDescField, codeDescFieldName, staticParameters);
              metaData =  helper.buildFinderOutDtoArgument(serviceName, graphBased, serviceMethodName, keys, fieldMap,
                                                            valueField, codeDescField, codeDescFieldName,
                                                            staticParameters);

          }
          else {
              // JavaScript code written prior to October 2018 expected evaluable JavaScript to be returned by
              // this method.
              metaData = getJavaScriptMetaData(serviceName, graphBased, serviceMethodName, keys, fieldMap, valueField,
                                               codeDescField, codeDescFieldName, staticParameters);
          }

          if (log.isDebugEnabled()) {
              log.debug("Generated metaData:\n" + metaData);
          }
          return metaData;
      }

    private static String getJavaScriptMetaData(String serviceName, boolean graphBased, String serviceMethodName,
                                                String[] keys, Map<String, Map<String, String>> fieldMap,
                                                String valueField, Map<String, Map<String, String>> codeDescField,
                                                String codeDescFieldName, String staticParameters) {
          // TODO investigate replacing with getJSONMetaData
        // Build the metaData
        StringBuilder buf = new StringBuilder();
        boolean firstLoop;
        buf.append("Ext.apply(ClassMetaData, {\n");
        buf.append("  " + serviceName + ": new Jaffa.data.FinderOutDto({\n");

        buf.append("    key: [");
        for (int i = 0; i < keys.length; i++) {
            if (i > 0)
                buf.append(", ");
            buf.append('\'' + keys[i] + '\'');
        }
        buf.append("],\n");

        buf.append("    finder: {\n");
        buf.append("      root: '").append(graphBased ? "graphs" : "rows").append("',\n");
        buf.append("      DWRFunctionName: '").append(serviceName).append('.').append(serviceMethodName).append("',\n");
        if (staticParameters != null)
            buf.append("      DWRFunctionInput: '").append(staticParameters).append("',\n");
        buf.append("      orderByFields: '[");
        for (int i = 0; i < keys.length; i++) {
            if (i > 0)
                buf.append(", ");
            buf.append("{fieldName: \"" + StringHelper.getUpper1(keys[i]) + "\"}");
        }
        buf.append("]',\n");
        buf.append("      combo: {\n");
        buf.append("        columns: [");
        firstLoop = true;
          for (String fieldName : fieldMap.keySet()) {
              if (!firstLoop)
                buf.append(", ");
              firstLoop = false;
              buf.append('\'' + fieldName + '\'');
        }
        if (codeDescFieldName != null)
            buf.append(", '" + codeDescFieldName + '\'');
        buf.append("],\n");
        buf.append("        config: {valueField: '").append(valueField).append("', displayField: '").append(codeDescFieldName != null ? codeDescFieldName : valueField).append("'}\n");
        buf.append("      },\n");
        buf.append("      grid: {\n");
        buf.append("        columns: [");
        firstLoop = true;
        for (String fieldName : fieldMap.keySet()) {
              if (!firstLoop)
                buf.append(", ");
              firstLoop = false;
            buf.append('\'' + fieldName + '\'');
        }
        buf.append("]\n");
        buf.append("      }\n");
        buf.append("    },\n");

        buf.append("    fields: {\n");

        for (Iterator<Map.Entry<String, Map<String, String>>> i = fieldMap.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry<String, Map<String, String>> me = i.next();
            buf.append("      ").append('\'' + me.getKey() + '\'').append(": {");
            firstLoop = true;
            if (me.getValue()!=null){
              for (Map.Entry<String, String> attribute : me.getValue().entrySet()) {
                  if (!firstLoop)
                      buf.append(", ");
                  firstLoop = false;
                  buf.append(attribute.getKey()).append(": ").append(attribute.getValue());
              }
            }
            buf.append('}');
            if (i.hasNext() || codeDescField != null)
                buf.append(',');
            buf.append('\n');
        }
        if (codeDescField != null) {
            buf.append("      ").append('\'' + codeDescFieldName + '\'').append(": {");
            firstLoop = true;
            for (Map.Entry<String, String> attribute : codeDescField.get(codeDescFieldName).entrySet()) {
                if (!firstLoop)
                    buf.append(", ");
                firstLoop = false;
                buf.append(attribute.getKey()).append(": ").append(attribute.getValue());
            }
            buf.append("}\n");
        }
        buf.append("    }\n");

        buf.append("  })\n");
        buf.append("});\n");
        buf.append("ClassMetaData." + serviceName + ";\n");
        return buf.toString();
    }

    /** Returns the component parameter, after replacing dots with underscores. */
      private static String getServiceName(Map<String, String> parameters) {
          return parameters.get("component").replace('.', '_');
      }

      /** Looks up dwr.xml for the input serviceName and returns the corresponding class.
       * The servletContext is needed to get a handle on the DWR context.
       */
      private static Class getServiceClass(String serviceName, ServletContext servletContext) {
          // Obtain the ServletContext for the 'dwr' servlet. Error out if not found
          String dwrPath = servletContext.getContextPath() + "/dwr";
          ServletContext dwrServletContext = servletContext.getContext(dwrPath);
          if (dwrServletContext == null) {
              String m = "Unable to obtain ServletContext for the '" + dwrPath + "' servlet. The ServiceClass cannot be determined for: " + serviceName;
              log.error(m);
              throw new IllegalArgumentException(m);
          }

          // Check the ServletContext for a List of Containers
          List<Container> containers = (List<Container>) dwrServletContext.getAttribute(ContainerUtil.ATTRIBUTE_CONTAINER_LIST);
          if (containers == null) {
              String m = "Unable to load the dwr.xml configuration from the '" + dwrPath + "' servlet. The ServiceClass cannot be determined for: " + serviceName;
              log.error(m);
              throw new IllegalArgumentException(m);
          }

          // Iterate through the Containers, looking for a 'Creator' for the input service
          Class serviceClass = null;
          for (Container container : containers) {
              CreatorManager creatorManager = (CreatorManager) container.getBean(CreatorManager.class.getName());
              Creator creator = creatorManager.getCreator(serviceName);
              if (creator != null) {
                  serviceClass = creator.getType();
                  if (log.isDebugEnabled())
                      log.debug("The ServiceClass for '" + serviceName + "' is " + serviceClass);
                  break;
              }
          }

          // Error out if the service class cannot be determined
          if (serviceClass == null) {
              String m = "ServiceClass not be found for '" + serviceName + "' in dwr.xml";
              log.error(m);
              throw new IllegalArgumentException(m);
          }
          return serviceClass;
      }

      /** Returns true if the serviceClass is based on Graphs. */
      private static boolean isGraphBased(Class serviceClass) {
          boolean graphBased = !serviceClass.getName().endsWith("Tx");
          if (log.isDebugEnabled())
              log.debug("Is " + serviceClass.getSimpleName() + " graph based? " + graphBased);
          return graphBased;
      }

      /** Returns the method name in the input service which provides the finder functionality. */
      private static String getServiceMethodName(Class serviceClass, boolean graphBased) {
          String serviceMethodName = graphBased ? "query" : "find";
          if (log.isDebugEnabled())
              log.debug("ServiceMethodName for " + serviceClass.getSimpleName() + " is " + serviceMethodName);
          return serviceMethodName;
      }

      /** Returns the class used for generating the output from the input service. */
      private static Class getOutputClass(Class serviceClass, String serviceMethodName, boolean graphBased) throws NoSuchMethodException {
          Class outputClass = null;
          for (Method m : serviceClass.getMethods()) {
              if (m.getName().equals(serviceMethodName)) {
                  String methodNameInResponse = graphBased ? "getGraphs" : "getRows";
                  outputClass = m.getReturnType().getMethod(methodNameInResponse).getReturnType().getComponentType();
                  if (log.isDebugEnabled())
                      log.debug("OutputClass for " + serviceClass.getSimpleName() + " is " + outputClass);
                  break;
              }
          }

          // Error out if the output class cannot be determined
          if (outputClass == null) {
              String m = "Output class cannot be determined for the '" + serviceMethodName + "' method in " + serviceClass;
              log.error(m);
              throw new IllegalArgumentException(m);
          }
          return outputClass;
      }

      /** Returns the domain class that the service is based on. */
      private static Class getDomainClass(Class serviceClass, Class outputClass, boolean graphBased) throws ClassNotFoundException {
          Class domainClass = null;
          if (graphBased) {
              // Use the Graph Mapping file to obtain the domain class
              domainClass = MappingFactory.getInstance(outputClass).getDomainClass();
          } else {
              // Use regular expression to extract the domain class name, based on the service class
              Matcher m = PATTERN_TO_OBTAIN_DOMAIN_CLASSNAME.matcher(serviceClass.getName());
              if (m.matches() && m.groupCount() == 4) {
                  String domainClassName = m.group(1) + ".domain." + m.group(2);
                  domainClass = Class.forName(domainClassName);
              }
          }
          if (log.isDebugEnabled())
              log.debug("DomainClass for " + serviceClass.getSimpleName() + " is " + domainClass);
          return domainClass;
      }

      /** Returns the key fields for the input service class. */
      private static String[] getKeys(Class serviceClass, Class outputClass, Class domainClass, boolean graphBased) throws Exception {
          String[] keys = null;
          if (graphBased) {
              // Use the Graph Mapping file to obtain the key fields
              keys = (String[]) MappingFactory.getInstance(outputClass).getKeyFields().toArray(new String[0]);
          } else {
              // Use the PeristentHelper to obtain the key fields
              FieldMetaData[] keyFields = PersistentHelper.getKeyFields(domainClass.getName());
              if (keyFields != null && keyFields.length > 0) {
                  keys = new String[keyFields.length];
                  for (int i = 0; i < keyFields.length; i++)
                      keys[i] = StringHelper.getJavaBeanStyle(keyFields[i].getName());
              }
          }

          // Error out if the keys cannot be determined
          if (keys == null || keys.length == 0) {
              String m = "Keys cannot be determined for " + serviceClass;
              log.error(m);
              throw new IllegalArgumentException(m);
          } else {
              if (log.isDebugEnabled())
                  log.debug("Keys for " + serviceClass.getSimpleName() + ": " + Arrays.toString(keys));
          }
          return keys;
      }

      /** Returns a Map of fields and attributes for each field. */
      public static Map<String, Map<String, String>> getFieldMap(Class serviceClass, Class outputClass, Class domainClass, boolean graphBased, String[] keys) throws Exception {
          GraphMapping graphMapping = graphBased ? MappingFactory.getInstance(outputClass) : null;

          // determine the field list
          Collection<String> fieldNames = new TreeSet<String>();
          if (graphBased) {
              fieldNames.addAll(graphMapping.getKeyFields());
              fieldNames.addAll(graphMapping.getFields());
              fieldNames.addAll(graphMapping.getForeignFields());
          } else {
              PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(outputClass).getPropertyDescriptors();
              for (PropertyDescriptor propertyDescriptor : propertyDescriptors)
                  fieldNames.add(StringHelper.getJavaBeanStyle(propertyDescriptor.getName()));
          }

          //If any lookups properties have been defined then enable lookup rule filtering for this finder
          //Also build array of fields that should be used in the lookup
          boolean hasLookup = false;
          ArrayList<String> lookupFields = new ArrayList<String>();

          for (String fieldName : fieldNames) {
            if (graphMapping != null && graphMapping.isForeignField(fieldName)) {
                //For a foreign object, generate MetaData for all foreign-key fields.
                Class foreignGraph = BeanHelper.getPropertyType(outputClass, fieldName);
                if (foreignGraph != null && GraphDataObject.class.isAssignableFrom(foreignGraph)) {
                    List<String> foreignKeys = graphMapping.getForeignKeys(fieldName);
                    Set<String> foreignGraphKeys = MappingFactory.getInstance(foreignGraph).getKeyFields();
                    if (foreignKeys != null && foreignGraphKeys != null && foreignKeys.size() == foreignGraphKeys.size()) {
                        int i = 0;

                        for (String foreignGraphKey : foreignGraphKeys) {
                            String title = fieldName + '.' + foreignGraphKey;
                            String foreignKey = foreignKeys.get(i);

                            RuleMetaDataCriteria criteria = new RuleMetaDataCriteria();
                            criteria.setClassName(outputClass.getName());
                            criteria.setPropertyName(foreignKey);
                            criteria.setRuleName("lookup");
                            if (RuleMetaHelper.findRules(criteria)!=null){
                              lookupFields.add(foreignKey);
                              hasLookup = true;
                            }
                            i++;
                        }
                    }
                }
            } else {
              RuleMetaDataCriteria criteria = new RuleMetaDataCriteria();
              criteria.setClassName(outputClass.getName());
              criteria.setPropertyName(fieldName);
              criteria.setRuleName("lookup");
              if (RuleMetaHelper.findRules(criteria)!=null){
                lookupFields.add(fieldName);
                hasLookup = true;
              }
            }
          }

          Map<String, Map<String, String>> output = new LinkedHashMap<String, Map<String, String>>();
          Map<String,ForeignKeyLookup> foreignKeyMap = new HashMap<String,ForeignKeyLookup>();
            // now create the field map
          Map<String, Map<String, String>> fieldMap = new LinkedHashMap<String, Map<String, String>>();
          for (String fieldName : fieldNames) {

             if (graphMapping != null && graphMapping.isForeignField(fieldName)) {
                //For a foreign object, generate MetaData for all foreign-key fields.
                Class foreignGraph = BeanHelper.getPropertyType(outputClass, fieldName);
                if (foreignGraph != null && GraphDataObject.class.isAssignableFrom(foreignGraph)) {
                    List<String> foreignKeys = graphMapping.getForeignKeys(fieldName);
                    Set<String> foreignGraphKeys = MappingFactory.getInstance(foreignGraph).getKeyFields();
                    String[] foreignGraphKeysArr = foreignGraphKeys.toArray(new String[0]);
                    if (foreignKeys != null && foreignGraphKeys != null && foreignKeys.size() == foreignGraphKeys.size()) {
                        int i = 0;
                        for (String foreignKey : foreignKeys) {
                            String foreignGraphKey = foreignGraphKeysArr[i];
                            String title = fieldName + '.' + foreignGraphKey;
                            //If lookup property filtering is being used, only add metadata for fields that have the lookup rule or key fields.
                            if (!(hasLookup==true && lookupFields.indexOf(foreignKey)<0 && java.util.Arrays.asList(keys).indexOf(foreignKey)<0)){

                            if(foreignKeyMap.containsKey(foreignKey)) {
                                ForeignKeyLookup fkl = foreignKeyMap.get(foreignKey);
                                if(fkl.getForeignKeySize()>foreignKeys.size()) {
                                  //Remove from map
                                  foreignKeyMap.remove(foreignKey);
                                  //Add this to the Map
                                        ForeignKeyLookup foreignKeyLookup = new ForeignKeyLookup();
                                        //foreignKeyLookup.setForeignGraphKey(foreignGraphKey);
                                        foreignKeyLookup.setTitle(title);
                                        foreignKeyLookup.setForeignKey(foreignKey);
                                        foreignKeyLookup.setForeignGraphKey(foreignGraphKey);
                                        foreignKeyLookup.setForeignKeySize(foreignKeys.size());
                                        foreignKeyMap.put(foreignKey,foreignKeyLookup);
                                }
                              } else {
                                //Add this to the Map
                                ForeignKeyLookup foreignKeyLookup = new ForeignKeyLookup();
                                    //foreignKeyLookup.setForeignGraphKey(foreignGraphKey);
                                    foreignKeyLookup.setTitle(title);
                                    foreignKeyLookup.setForeignKey(foreignKey);
                                    foreignKeyLookup.setForeignGraphKey(foreignGraphKey);
                                    foreignKeyLookup.setForeignKeySize(foreignKeys.size());
                                    foreignKeyMap.put(foreignKey,foreignKeyLookup);
                              }
                            }
                            i++;
                        }
                    }
                }
             } else {
               //If lookup property filtering is being used, only add metadata for fields that have the lookup rule or key fields.
                   if (!(hasLookup && lookupFields.indexOf(fieldName)<0 && java.util.Arrays.asList(keys).indexOf(fieldName)<0)){
                     Map<String, String> m = determinePropertyMetaData(outputClass, domainClass, graphMapping, fieldName);
                     if (m != null)
                         fieldMap.put(fieldName, m);
                   }
             }
          }

          //Now add foreign keys to the map
          Set<String> foreignKeys = foreignKeyMap.keySet();
          for(String foreignKey :foreignKeys) {
            ForeignKeyLookup fkl = foreignKeyMap.get(foreignKey);

            String title = fkl.getTitle();
            Map<String, String> m = determinePropertyMetaData(outputClass, domainClass, null, fkl.getForeignKey());

            if (m != null) {
                m.put("mapping", '\'' + title + '\'');
                m.put("filterFieldName", '\'' + fkl.getForeignKey() + '\'');
                fieldMap.put(title, m);
            }
          }
          // The output Map should contain the key fields followed by all others
          if (keys != null) {
              for (String key : keys)
                  output.put(key, fieldMap.remove(key));
              output.putAll(fieldMap);
          }
          if (log.isDebugEnabled())
              log.debug("FieldMap for " + serviceClass.getSimpleName() + " is " + output);
          return output;
      }

      /** Returns meta data for the input field.*/
      private static Map<String, String> determinePropertyMetaData(Class outputClass, Class domainClass, GraphMapping graphMapping, String fieldName) throws Exception {
          IPropertyRuleIntrospector i = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(outputClass, fieldName);
          // Apply an optional wrapper that also takes the FieldMetaData into account
          try {
              FieldMetaData fieldMetaData = PersistentHelper.getFieldMetaData(domainClass.getName(), graphMapping != null ? graphMapping.getDomainFieldName(fieldName) : fieldName);
              i = new PropertyRuleIntrospectorUsingFieldMetaData(i, fieldMetaData);
          } catch (Exception ignore) {
          }

          // A Map to hold the various attributes of the field
          Map<String, String> m = new LinkedHashMap<String, String>();


          // determine type
          String type = toJsType(i.getPropertyType());
          if (type == null) {
              // A foreign-key on a Graph will typically be modelled as another Graph, in which case obtain the datatype for that field from the domainClass
              type = toJsType(RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(domainClass, fieldName).getPropertyType());
          }
          //Ignore field having unsupported propertyType
          if (type == null)
              return null;
          m.put("type", '\'' + type + '\'');

          // determine label
          String label = i.getLabel();
          if (label == null) {
              label = StringHelper.getSpace(StringHelper.getUpper1(fieldName));
          } else {
              String tmp = label.substring(1);
              tmp = tmp.substring(0, tmp.length()-1);
              m.put("labelToken", '\'' + tmp + '\'');
              label = StringHelper.escapeJavascript(MessageHelper.replaceTokens(label));
          }
          m.put("label", '\'' + label + '\'');

          // determine maxLength
          Integer maxLength = i.getMaxLength();
          if (maxLength != null)
              m.put("maxLength", maxLength.toString());

          // sortable
          m.put("sortable", "true");

          // determine hidden
          Boolean hidden = i.isHidden();
          if(hidden){
            m.put("hidden", "true");
            m.put("alwaysHidden", "true");
          }

          // determine caseType
          String caseType = i.getCaseType();
          if (caseType != null && caseType.toLowerCase().startsWith("upper"))
              caseType = "UpperCase";
          else if (caseType != null && caseType.toLowerCase().startsWith("lower"))
              caseType = "LowerCase";
          else
              caseType = null;
          if (caseType != null)
              m.put("caseType", '\'' + caseType + '\'');

          return m;
      }

      /* Convert a Java Class to a JavaScript Equivilent */
      private static String toJsType(Class propertyType) {
        if(propertyType == null){
          return null;
        }
        String type;
        if (propertyType == String.class)
            type = "string";
        else if(DateTime.class.isAssignableFrom(propertyType))
            type = "datetime";
        else if(DateOnly.class.isAssignableFrom(propertyType))
            type = "dateonly";
        else if (IDateBase.class.isAssignableFrom(propertyType) || Date.class.isAssignableFrom(propertyType))
            type = "date";
        else if (propertyType == Boolean.class || propertyType == Boolean.TYPE)
            type = "boolean";
        else if (propertyType == Integer.class || propertyType == Integer.TYPE || propertyType == Long.class || propertyType == Long.TYPE)
            type = "int";
        else if (propertyType == Float.class || propertyType == Float.TYPE || propertyType == Double.class || propertyType == Double.TYPE)
            type = "float";
        else
            type = null;
        return type;
      }

      /** Returns the first targetField, if passed. Else the first key is returned. */
      private static String getValueField(Map<String, String> parameters, String[] keys) {
          String valueField = null;

          //targetFields are of the format 'prop1=tprop1;prop2=tprop2;...'. Treat the first target field as the value field.
          String targetFields = parameters.get("targetFields");
          if (targetFields != null && targetFields.length() > 0) {
              String[] t = targetFields.split(";");
              String[] nameValuePair = t[0].split("=");
              if (nameValuePair.length == 2)
                  valueField = nameValuePair[1];
          }

          // If the target fields are not passed, then the first key field will be treated as the value field
          if (valueField == null && keys != null && keys.length > 0)
              valueField = keys[0];

          return valueField;
      }

    /**
     * @Returns a JSON representation of staticParameters.
     */
    static String parseStaticParameters(Map<String, String> parameters) {
          //staticParameters are of the format 'prop1=tprop1;prop2=tprop2;...'
          String staticParameters = parameters.get("staticParameters");
          if (staticParameters != null && staticParameters.length() > 0) {
              StringBuilder buf = new StringBuilder();
              String[] t = staticParameters.split(";");
              for (int i = 0; i < t.length; i++) {
                  String[] nameValuePair = t[i].split("=");
                  if (nameValuePair.length == 2) {
                      if (buf.length() > 0)
                          buf.append(", ");
                      buf.append(nameValuePair[0]).append(": ").append("{values: [\"").append(nameValuePair[1]).append("\"]}");
                  }
              }
              if (buf.length() > 0)
                  return buf.toString();
          }
          return null;
      }

      /** If the input fieldMap contains a 'description' field, then a codeAndDescription field will be synthesized.*/
      private static Map<String, Map<String, String>> getCodeDescField(Class serviceClass, String valueField, Map<String, Map<String, String>> fieldMap) {
          if (fieldMap.containsKey("description")) {
              Map<String, String> keyField = fieldMap.get(valueField);
              Map<String, String> descField = fieldMap.get("description");

              // Map to hold the various attributes of the new field
              Map<String, String> m = new LinkedHashMap<>();
              m.put("type", "'string'");
              m.put("label", keyField.get("label"));
              Integer maxLength = keyField.containsKey("maxLength") ? Integer.parseInt(keyField.get("maxLength")) : 0;
              maxLength += descField.containsKey("maxLength") ? Integer.parseInt(descField.get("maxLength")) : 0;
              maxLength += 3;
              m.put("maxLength", maxLength.toString());
              m.put("tpl", "'{" + valueField + "} - {description}'");

              // Create a Map for the new field
              Map<String, Map<String, String>> codeDescField = new HashMap<>();
              codeDescField.put('_' + valueField + "AndDescription", m);
              if (log.isDebugEnabled())
                  log.debug("CodeDescField for " + serviceClass.getSimpleName() + " is " + codeDescField);
              return codeDescField;
          }
          return null;
      }

    /**
     * Retrieve the metadata in JSON format
     * @return a string represention the metadata in JSON format
     * @see #getJavaScriptMetaData
     */
    private String getJSONMetaData(String serviceName, boolean graphBased, String serviceMethodName, String[] keys,
                                   Map<String, Map<String, String>> fieldMap, String valueField,
                                   Map<String, Map<String, String>> codeDescField, String codeDescFieldName,
                                   String staticParameters) {
        // Build the metaData
        StringBuilder buf = new StringBuilder();
        buf.append("Ext.apply(ClassMetaData, {\n");
        buf.append("  ").append(serviceName).append(": new Jaffa.data.FinderOutDto(");
        String arg = buildFinderOutDtoArgument(serviceName, graphBased, serviceMethodName, keys, fieldMap, valueField,
                                               codeDescField, codeDescFieldName, staticParameters);
        buf.append(arg);
        buf.append(")\n");   // end new Jaffa.data.FinderOutDto(
        buf.append("});\n");
        buf.append("ClassMetaData.").append(serviceName).append(";\n");
        return buf.toString();
    }

    private String buildFinderOutDtoArgument(String serviceName, boolean graphBased, String serviceMethodName,
                                           String[] keys, Map<String, Map<String, String>> fieldMap, String valueField,
                                           Map<String, Map<String, String>> codeDescField, String codeDescFieldName,
                                           String staticParameters) {
        StringBuilder buf = new StringBuilder();
        buf.append("{\n");
        appendKeys(keys, buf);
        appendFinder(serviceName, graphBased, serviceMethodName, keys, fieldMap, valueField, codeDescFieldName,
                     staticParameters, buf);
        appendFields(fieldMap,codeDescFieldName, codeDescField, buf);
        buf.append("  }");   // end argument to new Jaffa.data.FinderOutDto(
        return buf.toString();
    }

    /**
     * Append the JSON that constitutes the finder
     * @param buf the builder that adds the finder JSON
     */
    private void appendFinder(String serviceName, boolean graphBased, String serviceMethodName, String[] keys,
                              Map<String, Map<String, String>> fieldMap, String valueField, String codeDescFieldName,
                              String staticParameters, StringBuilder buf) {
        buf.append("    ").append(quoteName("finder")).append(": {\n");

        // Root
        buf.append("      ").append(quoteName("root")).append(": ");
        String rootValue = graphBased ? "graphs" : "rows";
        appendValue(buf, rootValue);
        buf.append(",\n");

        // DWRFunctionName
        buf.append("      ").append(quoteName("DWRFunctionName")).append(": ");
        buf.append(quoteValue(serviceName + '.' + serviceMethodName)).append(",\n");


        appendDwrFunctionInput(staticParameters, buf);
        appendOrderByFields(keys, buf);
        appendCombo(fieldMap, valueField, codeDescFieldName, buf);
        appendGrid(fieldMap, buf);
        buf.append("    },\n");
    }

    /** Appends DWRFunctionInput information.  In the JavaScript code, the static parameters
     * ultimately serve as the string value corresponding to the
     * "DWRFunctionInput" field name.  This string value may have embedded quotes,
     * which need to be escaped. (The original code used single quotes to delimit
     * the JavaScript string, so there was no need to escape the double quotes.  Here,
     * we want the string to be accepted as JSON, so no single quotes.)
     */
    void appendDwrFunctionInput(String staticParameters, StringBuilder buf) {
        if (staticParameters != null) {
            buf.append("      ").append(quoteName("DWRFunctionInput")).append(": ");
            String escapedStaticParameters = staticParameters.replace("\"", "\\\"");
            buf.append(quoteValue(escapedStaticParameters)).append(",\n");
        }
    }

    /**
     * In the JavaScript code, the orderByFields field has a string value.
     * This string value may have embedded quotes, which need to be escaped.
     * (The original code used single quotes to delimit
     * the JavaScript string, so there was no need to escape the double quotes.  Here,
     * we want the string to be accepted as JSON, so no single quotes.)
     */
    void appendOrderByFields(String[] keys, StringBuilder buf) {
        buf.append("      ").append(quoteName("orderByFields")).append(": \"[");
        for (int i = 0; i < keys.length; i++) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append("{fieldName: \\\"" + StringHelper.getUpper1(keys[i]) + "\\\"}");
        }
        buf.append("]\",\n");
    }

    /**
     * Append the JSON representing the combo field name and object value
     * @param buf the builder that adds the JSON
     */
    void appendCombo(Map<String, Map<String, String>> fieldMap, String valueField, String codeDescFieldName,
                             StringBuilder buf) {
        buf.append("      ").append(quoteName("combo")).append(": {\n");
        appendComboColumns(fieldMap, codeDescFieldName, buf);
        buf.append("        ").append(quoteName("config")).append(": {");
        buf.append(quoteName("valueField")).append(": ");
        appendValue(buf, valueField);
        String displayFieldValue = codeDescFieldName != null ? codeDescFieldName : valueField;
        buf.append(", ").append(quoteName("displayField")).append(": ");
        appendValue(buf, displayFieldValue);
        buf.append("}\n");
        buf.append("      },\n");   // end combo
    }

    /**
     * Append the JSON representing the combo columns
     * @param buf the builder that adds the JSON
     */
    void appendComboColumns(Map<String, Map<String, String>> fieldMap, String codeDescFieldName,
                                    StringBuilder buf) {
        buf.append("        ").append(quoteName("columns")).append(": [");
        appendSetElementsJSON(fieldMap, buf);
        if (codeDescFieldName != null) {
            buf.append(", ");
            appendValue(buf, codeDescFieldName);
        }
        buf.append("],\n");
    }

    /**
     * Append the JSON representing the grid
     * @param buf the builder that adds the JSON
     */
    void appendGrid(Map<String, Map<String, String>> fieldMap, StringBuilder buf) {
        buf.append("      ").append(quoteName("grid")).append(": {\n");
        buf.append("        ").append(quoteValue("columns")).append(": [");
        appendSetElementsJSON(fieldMap, buf);
        buf.append("]\n");  // end columns
        buf.append("      }\n");    // end grid
    }

    /**
     * Append information about each field to the string builder - the field name followed by
     * an object representation
     * @param fieldMap collection of field names and values
     * @param codeDescFieldName the name of the code descriptor field
     * @param codeDescFieldMap code descriptor attribute names and values
     * @param buf the buffer being appended to
     */
    void appendFields(Map<String, Map<String, String>> fieldMap,
                      String codeDescFieldName,
                      Map<String, Map<String, String>> codeDescFieldMap,
                      StringBuilder buf) {
        buf.append("    ").append(quoteName("fields")).append(": {\n");
        boolean firstLoop;
        for (Iterator<Map.Entry<String, Map<String, String>>> i = fieldMap.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry<String, Map<String, String>> me = i.next();
            buf.append("      ").append(quoteName(me.getKey()));
            buf.append(": {");  // start of attribute names and values

            // append each attribute name and value
            firstLoop = true;
            if (me.getValue() != null) {
                for (Map.Entry<String, String> attribute : me.getValue().entrySet()) {
                    if (!firstLoop) {
                        buf.append(", ");
                    }
                    firstLoop = false;
                    appendAttributeKeyValue(buf, attribute);
                }
            }
            buf.append('}');    // end of attribute names and values
            if (i.hasNext() || (codeDescFieldMap != null && codeDescFieldMap.size() > 0)) {
                buf.append(',');
            }
            buf.append('\n');
        }

        appendCodeDescFields(codeDescFieldName, codeDescFieldMap, buf);
        buf.append("    }\n");  // end fields
    }

    void appendCodeDescFields(String codeDescFieldName,
                              Map<String, Map<String, String>> codeDescFieldMap,
                              StringBuilder buf) {
        if (codeDescFieldMap != null && (codeDescFieldMap.get(codeDescFieldName) != null)) {
            Set<Map.Entry<String, String>> entrySet = codeDescFieldMap.get(codeDescFieldName).entrySet();
            if (entrySet != null) {
                buf.append("      ").append(quoteName(codeDescFieldName)).append(": {");
                boolean firstLoop = true;
                // Append the codeDesc attribute/value pairs
                for (Map.Entry<String, String> attribute : entrySet) {
                    if (!firstLoop) {
                        buf.append(", ");
                    }
                    firstLoop = false;
                    appendAttributeKeyValue(buf, attribute);
                }
                buf.append("}\n");
            }   // entrySet != null
        }   // codeDescFieldMap != null
    }

    private void appendAttributeKeyValue(StringBuilder buf, Map.Entry<String, String> attribute) {
        String key = attribute.getKey();
        String value = attribute.getValue();
        appendAttributeKeyValue(buf, key, value);
    }

    private void appendAttributeKeyValue(StringBuilder buf, String key, String value) {
        buf.append(quoteName(key)).append(": ");
        // determinePropertyMetaData causes some numeric or boolean values,
        // e.g. for key "maxLength", to come in as Strings.  Don't quote these values.
        if (NON_STRING_FIELDS.contains(key)) {
            buf.append(value);
        }
        else {
            appendValue(buf, value);
        }
    }

    void appendKeys(String[] keys, StringBuilder buf) {
        buf.append("    ").append(quoteName("key")).append(": [");
        for (int i = 0; i < keys.length; i++) {
            if (i > 0) {
                buf.append(", ");
            }
            appendValue(buf, keys[i]);
        }
        buf.append("],\n");
    }

    void appendSetElementsJSON(Map<String, Map<String, String>> fieldMap, StringBuilder buf) {
        boolean firstLoop = true;
        for (String fieldName : fieldMap.keySet()) {
            if (!firstLoop) {
                buf.append(", ");
            }
            firstLoop = false;
            appendValue(buf, fieldName);
        }
    }

    /**
     * Appends information about the indicated property value to the string builder
     * @param builder the builder for the string being built
     * @param value the property's value
     */
    void appendValue(StringBuilder builder, Object value) {
        // Handle cases that don't require quotes
        if (value instanceof Boolean || value instanceof Number) {
            builder.append(value);
        }
        // Quotes required
        else {
            // In some cases, the String values may already be quoted with single-quotes.
            // To make proper JSON, convert those to double quotes
            if (value instanceof String) {
                String valString = (String)value;
                if (valString.startsWith("'") && valString.endsWith("'")) {
                    valString = valString.substring(1, valString.length() -1);
                    builder.append(quoteValue(valString));
                }
                else {  // The existing value isn't already surrounded by single quotes
                    builder.append(quoteValue(value));
                }
            }
            else { // nonString object that needs quotes
                builder.append(quoteValue(value));
            }
        }   // else quotes required
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



}
