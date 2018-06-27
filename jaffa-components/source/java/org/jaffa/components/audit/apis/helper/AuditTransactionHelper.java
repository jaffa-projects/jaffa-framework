package org.jaffa.components.audit.apis.helper;

import org.jaffa.components.audit.apis.data.AuditTransactionFieldGraph;
import org.jaffa.components.audit.apis.data.AuditTransactionObjectGraph;
import org.jaffa.components.audit.apis.data.AuditTransactionViewGraph;
import org.jaffa.components.audit.apis.data.AuditTransactionViewQueryResponse;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.IObjectRuleIntrospector;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.util.MessageHelper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuditTransactionHelper {

    private Map<String, String> classNameByObjectName;
    private Map<String, String> classLabelByObjectName;
    private Map<String, Map<String, String[]>> propertyNameByFieldName;
    private Map<String, Map<String, String>> propertyLabelByFieldName;
    private Map<String, Map<String, Boolean>> propertyHiddenByFieldName;
    private Pattern eolPattern;


    /**
     * Removes hidden fields from the input.
     * @param response The input from which hidden fields are to be removed.
     * @throws FrameworkException if an internal error occurs.
     */
    public void removeHiddenFields(AuditTransactionViewQueryResponse response) throws FrameworkException {
        if (response.getGraphs() != null && response.getGraphs().length > 0) {
            Collection<AuditTransactionViewGraph> atvs = new LinkedList<AuditTransactionViewGraph>();
            for (AuditTransactionViewGraph atv : response.getGraphs()) {
                if (!isHidden(atv.getObjectName(), atv.getFieldName()))
                    atvs.add(atv);
            }
            if (atvs.size() != response.getGraphs().length)
                response.setGraphs(atvs.size() > 0 ? atvs.toArray(new AuditTransactionViewGraph[atvs.size()]) : null);
        }
    }

    /** Removes hidden fields from the input AuditTransactionObject. */
    public void removeHiddenFields(AuditTransactionObjectGraph ato) throws FrameworkException {
        if (ato.getAuditTransactionFields() != null && ato.getAuditTransactionFields().length > 0) {
            Collection<AuditTransactionFieldGraph> atfs = new LinkedList<AuditTransactionFieldGraph>();
            for (AuditTransactionFieldGraph atf : ato.getAuditTransactionFields()) {
                if (!isHidden(ato.getObjectName(), atf.getFieldName()))
                    atfs.add(atf);
            }
            if (atfs.size() != ato.getAuditTransactionFields().length)
                ato.setAuditTransactionFields(atfs.size() > 0 ? atfs.toArray(new AuditTransactionFieldGraph[atfs.size()]) : null);
        }
    }

    /**
     * Builds up the internal Map of objectName to className, if the Map has not been initialzed.
     * Returns the className for the input objectName.
     * A null is returned if the domain-info rule is not found for the given objectName.
     */
    private String findClassNameByObjectName(String objectName) throws FrameworkException {
        if (classNameByObjectName == null) {
            classNameByObjectName = new HashMap<String, String>();
            String[] classNames = RulesEngineFactory.getRulesEngine().getClassNamesByRuleName("audit");
            if (classNames != null) {
                for (String className : classNames) {
                    //IObjectRuleIntrospector introspector = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(className, null);
                    IObjectRuleIntrospector introspector = RulesEngineFactory.getRulesEngine().getAuditRuleIntrospector(className);
                    Properties domainInfo = introspector.getAuditInfo();
                    if (domainInfo != null)
                        classNameByObjectName.put(domainInfo.getProperty("name"), className);
                }
            }
        }
        return classNameByObjectName.get(objectName);
    }

    /**
     * Builds up the internal Map of objectName+fieldName to className+propertyName, if the Map has not been initialzed.
     * Returns the className+propertyName for the input objectName+fieldName.
     * A null is returned if the property-info rule is not found for the input.
     */
    private String[] findPropertyNameByFieldName(String objectName, String fieldName) throws FrameworkException {
        if (propertyNameByFieldName == null)
            propertyNameByFieldName = new HashMap<String, Map<String, String[]>>();
        if (!propertyNameByFieldName.containsKey(objectName)) {
            Map<String, String[]> m = new HashMap<String, String[]>();
            String className = findClassNameByObjectName(objectName);
            if (className != null) {
                // Find all the property-info rules, and then add name/propertyName pairs to the Map
                IObjectRuleIntrospector introspector = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(className, null);
                Map<String, Properties> infoForProperties = introspector.getInfoForProperties();
                if (infoForProperties != null) {
                    for (Map.Entry<String, Properties> me : infoForProperties.entrySet())
                        m.put(me.getValue().getProperty("name"), new String[]{className, me.getKey()});
                }

                // Add properties from any associated flex classes
                Properties[] flexInfos = introspector.getDeclaredFlexInfo();
                if (flexInfos != null) {
                    for (Properties flexInfo : flexInfos) {
                        String flexClassName = flexInfo.getProperty("source");
                        Map<String, Properties> infoForFlexProperties = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(flexClassName, null).getInfoForProperties();
                        if (infoForFlexProperties != null) {
                            for (Map.Entry<String, Properties> me : infoForFlexProperties.entrySet())
                                m.put(me.getValue().getProperty("name"), new String[]{flexClassName, me.getKey()});
                        }
                    }
                }

            }
            propertyNameByFieldName.put(objectName, m);
        }
        return propertyNameByFieldName.get(objectName).get(fieldName);
    }

    /**
     * Returns the label for the object identified by the input.
     */
    public String findLabel(String objectName) throws FrameworkException {
        if (classLabelByObjectName == null)
            classLabelByObjectName = new HashMap<String, String>();
        if (!classLabelByObjectName.containsKey(objectName)) {
            String label = null;
            String className = findClassNameByObjectName(objectName);
            if (className == null)
                className = objectName;
            label = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(className, null).getLabel();
            label = label != null ? MessageHelper.replaceTokens(label) : objectName;
            classLabelByObjectName.put(objectName, label);
        }
        return classLabelByObjectName.get(objectName);
    }

    /**
     * Returns the label for the field identified by the input.
     */
    public String findLabel(String objectName, String fieldName) throws FrameworkException {
        if (propertyLabelByFieldName == null)
            propertyLabelByFieldName = new HashMap<String, Map<String, String>>();
        if (!propertyLabelByFieldName.containsKey(objectName) || !propertyLabelByFieldName.get(objectName).containsKey(fieldName)) {
            String label = null;
            String[] cp = findPropertyNameByFieldName(objectName, fieldName);
            if (cp != null && cp.length > 1) {
                label = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(cp[0], cp[1], null).getLabel();
            } else {
                String className = findClassNameByObjectName(objectName);
                if (className != null) {
                    label = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(className, fieldName, null).getLabel();
                    if (label == null) {
                        // Check the associated flex classes for labels
                        Properties[] flexInfos = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(className, null).getDeclaredFlexInfo();
                        if (flexInfos != null) {
                            for (Properties flexInfo : flexInfos) {
                                String flexClassName = flexInfo.getProperty("source");
                                label = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(flexClassName, fieldName, null).getLabel();
                                if (label != null)
                                    break;
                            }
                        }
                    }
                }
            }
            label = label != null ? MessageHelper.replaceTokens(label) : fieldName;
            Map<String, String> m = propertyLabelByFieldName.get(objectName);
            if (m == null) {
                m = new HashMap<String, String>();
                propertyLabelByFieldName.put(objectName, m);
            }
            m.put(fieldName, label);
        }
        return propertyLabelByFieldName.get(objectName).get(fieldName);
    }

    /**
     * Returns true if the field identified by the input is declared to be hidden.
     */
    private boolean isHidden(String objectName, String fieldName) throws FrameworkException {
        if (propertyHiddenByFieldName == null)
            propertyHiddenByFieldName = new HashMap<String, Map<String, Boolean>>();
        if (!propertyHiddenByFieldName.containsKey(objectName) || !propertyHiddenByFieldName.get(objectName).containsKey(fieldName)) {
            boolean hidden = false;
            String[] cp = findPropertyNameByFieldName(objectName, fieldName);
            if (cp != null && cp.length > 1) {
                hidden = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(cp[0], cp[1], null).isHidden();
            } else {
                String className = findClassNameByObjectName(objectName);
                if (className != null) {
                    hidden = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(className, fieldName, null).isHidden();
                    if (!hidden) {
                        // Check the associated flex classes
                        Properties[] flexInfos = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(className, null).getDeclaredFlexInfo();
                        if (flexInfos != null) {
                            for (Properties flexInfo : flexInfos) {
                                String flexClassName = flexInfo.getProperty("source");
                                hidden = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(flexClassName, fieldName, null).isHidden();
                                if (hidden)
                                    break;
                            }
                        }
                    }
                }
            }
            Map<String, Boolean> m = propertyHiddenByFieldName.get(objectName);
            if (m == null) {
                m = new HashMap<String, Boolean>();
                propertyHiddenByFieldName.put(objectName, m);
            }
            m.put(fieldName, hidden);
        }
        return propertyHiddenByFieldName.get(objectName).get(fieldName);
    }

    public String trimValue(String input) {
        String trimmed = input.trim();
        if (this.eolPattern == null) {
            this.eolPattern = Pattern.compile("[\r|\n]");
        }
        Matcher m = this.eolPattern.matcher(trimmed);
        if (m.find()) {
            trimmed = trimmed.subSequence(0, m.start()).toString();
        }
        return trimmed.length() > 100 ? trimmed.substring(0, 100) : trimmed;
    }
}
