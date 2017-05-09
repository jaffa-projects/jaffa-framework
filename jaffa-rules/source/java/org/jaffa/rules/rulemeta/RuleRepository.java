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
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

package org.jaffa.rules.rulemeta;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.commons.AbstractLoader;
import org.w3c.dom.Element;

import java.util.*;

/**
 * This class is used to import rules.
 */
public class RuleRepository extends AbstractLoader {

    private static final String ELEMENT_RULE = "rule";
    private static final String ELEMENT_PARAMETER = "parameter";
    private static Logger log = Logger.getLogger(RuleRepository.class);
    // Singleton instance
    private static RuleRepository c_instance = new RuleRepository();
    // Repository containing the Rule indexed by name
    private Map<String, Rule> m_ruleByName = new HashMap<String, Rule>();
    // Repository containing List of Rule instances per source
    private Map<String, List<Rule>> m_rulesBySource = new HashMap<String, List<Rule>>();

    /**
     * Creates an instance.
     */
    private RuleRepository() {
    }

    /**
     * Returns the Singleton instance.
     *
     * @return the Singleton instance.
     */
    public static RuleRepository instance() {
        return c_instance;
    }

    /**
     * Imports rules.
     *
     * @param metadataElement the metadata element we are processing within a source.
     * @param source          the name of the source file.
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public void load(Element metadataElement, String source) throws JaffaRulesFrameworkException {
        if (log.isDebugEnabled())
            log.debug("Loading rules from " + source);

        Element[] ruleElements = getChildren(metadataElement);
        for (Element ruleElement : ruleElements) {
            if (ELEMENT_RULE.equals(ruleElement.getNodeName())) {
                // create a Rule
                Rule rule = new Rule();
                rule.setName(ruleElement.hasAttribute(Rule.NAME) ? ruleElement.getAttribute(Rule.NAME) : null);
                rule.setSource(source);
                rule.setLine(getLine(ruleElement));
                rule.setExecutionRealms(ruleElement.hasAttribute(Rule.EXECUTION_REALMS) ? ruleElement.getAttribute(Rule.EXECUTION_REALMS).split(",") : null);
                rule.setPrecedence(ruleElement.hasAttribute(Rule.PRECEDENCE) ? ruleElement.getAttribute(Rule.PRECEDENCE) : null);
                rule.setTextParameter(ruleElement.hasAttribute(Rule.TEXT_PARAMETER) ? ruleElement.getAttribute(Rule.TEXT_PARAMETER) : null);
                rule.setHelper(ruleElement.hasAttribute(Rule.HELPER) ? ruleElement.getAttribute(Rule.HELPER) : null);
                rule.setInterceptor(ruleElement.hasAttribute(Rule.INTERCEPTOR) ? ruleElement.getAttribute(Rule.INTERCEPTOR) : null);
                rule.setExtendsRule(ruleElement.hasAttribute(Rule.EXTENDS_RULE) ? ruleElement.getAttribute(Rule.EXTENDS_RULE) : null);
                rule.setInheritable(ruleElement.hasAttribute(Rule.INHERITABLE) ? Parser.parseBoolean(ruleElement.getAttribute(Rule.INHERITABLE)) : true);

                // Add parameters to the rule
                Element[] parameterElements = getChildren(ruleElement);
                for (Element parameterElement : parameterElements) {
                    if (ELEMENT_PARAMETER.equals(parameterElement.getNodeName())) {
                        Parameter p = new Parameter();
                        p.setName(parameterElement.hasAttribute(Rule.NAME) ? parameterElement.getAttribute(Rule.NAME) : null);
                        p.setSource(source);
                        p.setLine(getLine(parameterElement));
                        p.setMandatory(parameterElement.hasAttribute(Rule.MANDATORY) ? Parser.parseBoolean(parameterElement.getAttribute(Rule.MANDATORY)) : null);
                        p.setDefault(parameterElement.hasAttribute(Rule.DEFAULT) ? parameterElement.getAttribute(Rule.DEFAULT) : null);
                        p.setValidValues(parameterElement.hasAttribute(Rule.VALID_VALUES) ? parameterElement.getAttribute(Rule.VALID_VALUES).split(",") : null);
                        p.setCaseInsensitive(parameterElement.hasAttribute(Rule.CASE_INSENSITIVE) ? Parser.parseBoolean(parameterElement.getAttribute(Rule.CASE_INSENSITIVE)) : null);
                        rule.addParameter(p);
                    } else {
                        // unknown element
                        log.warn("Unknown element found while importing parameter inside a rule element: " + parameterElement.getNodeName());
                    }
                }

                if (log.isDebugEnabled())
                    log.debug("Loaded rule: " + rule);

                addRule(rule);
            } else {
                // unknown element
                log.warn("Unknown element found while importing Rule: " + ruleElement.getNodeName());
            }
        }
    }

    /**
     * Adds a rule.
     *
     * @param rule the rule.
     */
    public void addRule(Rule rule) {
        // Add to the repository
        synchronized (this) {
            m_ruleByName.put(rule.getName(), rule);

            List<Rule> rules = m_rulesBySource.get(rule.getSource());
            if (rules == null) {
                rules = new LinkedList<Rule>();
                m_rulesBySource.put(rule.getSource(), rules);
            }
            rules.add(rule);
        }
    }

    /**
     * Unloads the XML that was imported from the input.
     *
     * @param uri the URI of the source file.
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public synchronized void unload(String uri) throws JaffaRulesFrameworkException {
        // Remove from the repository
        List<Rule> rules = m_rulesBySource.remove(uri);
        if (rules != null) {
            for (Rule rule : rules) {
                if (log.isDebugEnabled())
                    log.debug("Unloading " + rule);
                m_ruleByName.remove(rule.getName());
            }
        }
    }

    /**
     * Clears the Repository.
     *
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public synchronized void clear() throws JaffaRulesFrameworkException {
        if (log.isDebugEnabled())
            log.debug("Clearing...");
        m_ruleByName.clear();
        m_rulesBySource.clear();
    }

    //---------------------------------------------------------------------------------
    //
    //   THESE METHODS SHOULD BE INVOKED ONLY AFTER ALL THE METADATA HAS BEEN LOADED
    //
    //----------------------------------------------------------------------------------

    /**
     * Returns a Rule instance defined for the input name.
     * The dependencies in the instance are resolved.
     * NOTE: This method should be invoked only after all the metadata has been loaded.
     *
     * @param name the rule name.
     * @return a Rule instance defined for the input name.
     */
    public Rule getRuleByName(String name) {
        return getRuleByName(name, true);
    }

    /**
     * Returns a List of Rule instances defined in the input source file.
     * The dependencies in each instance is resolved.
     * NOTE: This method should be invoked only after all the metadata has been loaded.
     *
     * @param source the name of the source file.
     * @return a List of Rule instances defined in the input source file.
     */
    public List<Rule> getRulesBySource(String source) {
        return prepareRules(m_rulesBySource.get(source));
    }

    /**
     * Returns a Collection of Rule names that have been declared so far.
     *
     * @return a Collection of Rule names that have been declared so far.
     */
    public Collection<String> getRuleNames() {
        return m_ruleByName.keySet();
    }

    /**
     * Returns a Rule instance defined for the input name.
     * NOTE: This method should be invoked only after all the metadata has been loaded.
     *
     * @param name    the rule name.
     * @param prepare the dependencies in each instance is resolved if this parameter is true.
     * @return a Rule instance defined for the input name.
     */
    Rule getRuleByName(String name, boolean prepare) {
        Rule rule = m_ruleByName.get(name);
        if (prepare && rule != null)
            rule.prepare();
        return rule;
    }

    /**
     * Prepares the Rule instances passed in the input List.
     */
    private List<Rule> prepareRules(List<Rule> rules) {
        if (rules != null) {
            for (Rule rule : rules)
                rule.prepare();
        }
        return rules;
    }

}
