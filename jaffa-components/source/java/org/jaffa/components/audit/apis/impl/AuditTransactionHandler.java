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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.components.audit.apis.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jaffa.components.audit.apis.data.AuditTransactionFieldGraph;
import org.jaffa.components.audit.apis.data.AuditTransactionObjectGraph;
import org.jaffa.components.audit.apis.data.AuditTransactionViewGraph;
import org.jaffa.components.audit.apis.data.AuditTransactionViewQueryResponse;
import org.jaffa.components.audit.domain.AuditTransactionField;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.rules.IObjectRuleIntrospector;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.soa.dataaccess.MappingFilter;
import org.jaffa.soa.dataaccess.TransformationHandler;
import org.jaffa.soa.graph.GraphCriteria;
import org.jaffa.util.MessageHelper;

/**
 * A handler for the AuditTransactionService.
 *
 * @author GautamJ
 */
public class AuditTransactionHandler extends TransformationHandler {

    private Map<String, String> classNameByObjectName;
    private Map<String, String> classLabelByObjectName;
    private Map<String, Map<String, String[]>> propertyNameByFieldName;
    private Map<String, Map<String, String>> propertyLabelByFieldName;
    private Map<String, Map<String, Boolean>> propertyHiddenByFieldName;
    private Pattern eolPattern;

    /**
     * Creates an instance.
     */
    public AuditTransactionHandler(UOW uow) {
    }

    /**
     * Called after a bean has been loaded.
     * @param path This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the domain object that was just queried.
     * @param target the graph object being molded.
     * @param filter Filter object that it is used to control what fields are populated or the target objects.
     * @param originalCriteria the original graph criteria.
     * @throws ApplicationException if any Application error occurs.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException if an internal error occurs.
     */
    @Override
    public void endBeanLoad(String path, Object source, Object target, MappingFilter filter, GraphCriteria originalCriteria) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (target instanceof AuditTransactionObjectGraph) {
            AuditTransactionObjectGraph t = (AuditTransactionObjectGraph) target;
            t.setObjectLabel(findLabel(t.getObjectName()));
            removeHiddenFields(t);
            t.clearChanges();
        } else if (source instanceof AuditTransactionField && target instanceof AuditTransactionFieldGraph) {
            AuditTransactionField s = (AuditTransactionField) source;
            AuditTransactionFieldGraph t = (AuditTransactionFieldGraph) target;
            t.setFieldLabel(findLabel(s.getAuditTransactionObjectObject().getObjectName(), s.getFieldName()));
            t.clearChanges();
        } else if (target instanceof AuditTransactionViewGraph) {
            AuditTransactionViewGraph t = (AuditTransactionViewGraph) target;
            t.setFieldLabel(findLabel(t.getObjectName(), t.getFieldName()));
            t.setObjectLabel(findLabel(t.getObjectName()));
            t.clearChanges();
            if ("M".equals(t.getMultilineHtmlFlag())) {
                if (t.getFromValue() != null) {
                    t.setFromValue(trimValue(t.getFromValue()));
                }
                if (t.getToValue() != null) {
                    t.setToValue(trimValue(t.getToValue()));
                }
            }
        }
    }

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
    private void removeHiddenFields(AuditTransactionObjectGraph ato) throws FrameworkException {
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
    private String findLabel(String objectName) throws FrameworkException {
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
    private String findLabel(String objectName, String fieldName) throws FrameworkException {
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

    private String trimValue(String input) {
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
