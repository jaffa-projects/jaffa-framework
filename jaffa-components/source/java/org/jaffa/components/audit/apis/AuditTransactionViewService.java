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
package org.jaffa.components.audit.apis;

import org.apache.log4j.Logger;
import org.jaffa.components.audit.apis.data.AuditTransactionCriteria;
import org.jaffa.components.audit.apis.data.AuditTransactionViewGraph;
import org.jaffa.components.audit.apis.data.AuditTransactionViewQueryResponse;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.soa.dataaccess.GraphService;
import org.jaffa.soa.graph.GraphUpdateResponse;
import org.jaffa.rules.IObjectRuleIntrospector;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.util.MessageHelper;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import org.jaffa.components.audit.apis.impl.AuditTransactionHandler;

/**
 * A service for querying AuditTransactionView records.
 *
 * @author GautamJ
 */
public class AuditTransactionViewService extends GraphService<AuditTransactionCriteria, AuditTransactionViewGraph, AuditTransactionViewQueryResponse, GraphUpdateResponse<AuditTransactionViewGraph>, AuditTransactionHandler> {

    private static final Logger log = Logger.getLogger(AuditTransactionViewService.class);

    /**
     * Query domain objects, returning an array of Graphs in the response.
     * By default no related objects are returned, and any foreign objects will only
     * contain key information. Use the <code>setResultGraphRules()</code> method on
     * the criteria to change this.
     * When an error occurs, one or more instances of ServiceError, indicating
     * some kind of internal system error (like a problem accessing the database),
     * or listing the business logic errors will be returned in the response.
     * @param criteria This specified the criteria for the records to be retrieved.
     * @return An array of Graphs, the content of this graph is based on the result filter 'rules', as provided in the criteria object.
     * while processing. When an error occurs, an array of ServiceError instances will be returned
     * with details about the root cause of the problem.
     */
    public AuditTransactionViewQueryResponse query(AuditTransactionCriteria criteria) {
        criteria.queryView(true);
        AuditTransactionViewQueryResponse response = super._query(criteria);
        /*try {
            super.handler.removeHiddenFields(response);
        } catch (FrameworkException e) {
            log.error("Exception thrown while removing hidden fields from the response", e);
            response = super.createGraphQueryResponse(null, e);
        }*/
        return response;
    }


    public Map<String, Properties> getAuditableClasses() throws FrameworkException {
        Map<String, Properties> output = null;
        String[] classNames = RulesEngineFactory.getRulesEngine().getClassNamesByRuleName("audit");
        if (classNames != null) {
            output = new LinkedHashMap<String, Properties>();
            for (String className : classNames) {
                IObjectRuleIntrospector introspector = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(className, null);
                Properties p = introspector.getAuditInfo();
                if (p == null)
                    p = new Properties();
                output.put(className, p);

                // Add the label
                String label = introspector.getLabel();
                if (label != null)
                    p.put("label", MessageHelper.replaceTokens(label));
            }
        }
        return output;
    }

    public Map<String, Properties> getAuditableProperties(String className) throws FrameworkException {
      
        IObjectRuleIntrospector introspector = RulesEngineFactory.getRulesEngine().getAuditRuleIntrospector(className);
        Map<String, Properties> auditInfoForProperties = introspector.getAuditInfoForProperties();
        
        // Add additional information for each property
        if (auditInfoForProperties != null) {
            for (String propertyName : auditInfoForProperties.keySet())
                addExtraInfo(className, propertyName, auditInfoForProperties.get(propertyName));
        }

        // Add properties from any associated flex class
        Properties[] flexInfos = introspector.getDeclaredFlexInfo();
        if (flexInfos != null && flexInfos.length > 0) {
            if (auditInfoForProperties == null)
                auditInfoForProperties = new LinkedHashMap<String, Properties>();
            for (Properties flexInfo : flexInfos) {
                String flexClassName = flexInfo.getProperty("source");
                IObjectRuleIntrospector i = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(flexClassName, null);
                Map<String, Properties> auditInfoForFlexProperties = i.getAuditInfoForProperties();
                if (auditInfoForFlexProperties != null) {
                    for (String propertyName : auditInfoForFlexProperties.keySet()) {
                        addExtraInfo(flexClassName, propertyName, auditInfoForFlexProperties.get(propertyName));
                        auditInfoForFlexProperties.get(propertyName).setProperty("flex", "true");
                    }
                    auditInfoForProperties.putAll(auditInfoForFlexProperties);
                }
            }
        }

        return auditInfoForProperties;

    }

    private void addExtraInfo(String className, String propertyName, Properties p) throws FrameworkException {
        IPropertyRuleIntrospector introspector = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(className, propertyName, null);

        // Add the label
        String label = introspector.getLabel();
        if (label != null)
            p.put("label", MessageHelper.replaceTokens(label));

        // Add the JsType
        Class type = introspector.getPropertyType();
        if (type != null)
            p.put("type", toJsType(type));
    }

    private String toJsType(Class cls) {
        if (cls == null)
            return "string";
        if (String.class.isAssignableFrom(cls))
            return "string";
        if (Number.class.isAssignableFrom(cls))
            return "number";
        if (Boolean.class.isAssignableFrom(cls))
            return "boolean";
        if (DateTime.class.isAssignableFrom(cls))
            return "datetime";
        if (DateOnly.class.isAssignableFrom(cls))
            return "dateonly";
        return "string";
    }
}
