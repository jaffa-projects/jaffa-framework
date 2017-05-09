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
package org.jaffa.rules.realm;

import org.jaffa.rules.commons.MetaData;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Defines a Realm, instances of which are declared in Rule and ClassMetaData instances.
 * <p/>
 * It has the following properties:
 * <ul>classRegexes: Optional. A List of regular expression per realm corresponding to a classname, or any of its parent classes or its interfaces.
 * <ul>Check {@link MetaData} for more properties
 */
public class Realm extends MetaData {

    private List<String> m_classRegexes;
    private List<String> m_inheritanceRulesToInclude;
    private List<String> m_inheritanceRulesToExclude;

    /**
     * Default constructor.
     */
    public Realm() {
    }

    /**
     * Constructs a realm with a name.
     *
     * @param name the name of the realm.
     */
    public Realm(String name) {
        setName(name);
    }

    /**
     * Getter for property classRegexes.
     *
     * @return Value of property classRegexes.
     */
    public List<String> getClassRegexes() {
        return m_classRegexes;
    }

    /**
     * Adds the classRegexes.
     *
     * @param classRegexes the classRegexes.
     */
    public void addClassRegexes(List<String> classRegexes) {
        if (m_classRegexes == null)
            m_classRegexes = classRegexes;
        else if (classRegexes != null)
            m_classRegexes.addAll(classRegexes);
    }

    /**
     * Adds a classRegex to this property.
     *
     * @param classRegex a classRegex.
     */
    public void addClassRegex(String classRegex) {
        if (m_classRegexes == null)
            m_classRegexes = new LinkedList<String>();
        m_classRegexes.add(classRegex);
    }

    /**
     * Getter for property inheritanceRulesToInclude.
     *
     * @return Value of property inheritanceRulesToInclude.
     */
    public List<String> getInheritanceRulesToInclude() {
        return m_inheritanceRulesToInclude;
    }

    /**
     * Adds the inheritanceRulesToInclude.
     *
     * @param inheritanceRulesToInclude the inheritanceRulesToInclude.
     */
    public void addInheritanceRulesToInclude(List<String> inheritanceRulesToInclude) {
        if (m_inheritanceRulesToInclude == null)
            m_inheritanceRulesToInclude = inheritanceRulesToInclude;
        else if (inheritanceRulesToInclude != null)
            m_inheritanceRulesToInclude.addAll(inheritanceRulesToInclude);
    }

    /**
     * Adds an inheritanceRuleToInclude to this property.
     *
     * @param inheritanceRuleToInclude an inheritanceRuleToInclude.
     */
    public void addInheritanceRuleToInclude(String inheritanceRuleToInclude) {
        if (m_inheritanceRulesToInclude == null)
            m_inheritanceRulesToInclude = new LinkedList<String>();
        m_inheritanceRulesToInclude.add(inheritanceRuleToInclude);
    }

    /**
     * Getter for property inheritanceRulesToExclude.
     *
     * @return Value of property inheritanceRulesToExclude.
     */
    public List<String> getInheritanceRulesToExclude() {
        return m_inheritanceRulesToExclude;
    }

    /**
     * Adds the inheritanceRulesToExclude.
     *
     * @param inheritanceRulesToExclude the inheritanceRulesToExclude.
     */
    public void addInheritanceRulesToExclude(List<String> inheritanceRulesToExclude) {
        if (m_inheritanceRulesToExclude == null)
            m_inheritanceRulesToExclude = inheritanceRulesToExclude;
        else if (inheritanceRulesToExclude != null)
            m_inheritanceRulesToExclude.addAll(inheritanceRulesToExclude);
    }

    /**
     * Adds a inheritanceRuleToExclude to this property.
     *
     * @param inheritanceRuleToExclude a inheritanceRuleToExclude.
     */
    public void addInheritanceRuleToExclude(String inheritanceRuleToExclude) {
        if (m_inheritanceRulesToExclude == null)
            m_inheritanceRulesToExclude = new LinkedList<String>();
        m_inheritanceRulesToExclude.add(inheritanceRuleToExclude);
    }

    /**
     * Returns debug info.
     *
     * @return debug info.
     */
    public String toString() {
        StringBuilder buf = new StringBuilder("<realm");
        if (getName() != null)
            buf.append(" name='").append(getName()).append('\'');
        buf.append(super.toString());
        buf.append('>');
        if (getClassRegexes() != null && getClassRegexes().size() > 0) {
            for (String classRegex : getClassRegexes())
                buf.append("<class regex='").append(classRegex).append("'/>");
        }
        if (getInheritanceRulesToInclude() != null && getInheritanceRulesToInclude().size() > 0) {
            StringBuilder rules = new StringBuilder();
            for (String rule : getInheritanceRulesToInclude()) {
                if (rules.length() > 0)
                    rules.append(',');
                rules.append(rule);
            }
            buf.append("<inheritance-rule-filter includes='").append(rules.toString()).append("'/>");
        }
        if (getInheritanceRulesToExclude() != null && getInheritanceRulesToExclude().size() > 0) {
            StringBuilder rules = new StringBuilder();
            for (String rule : getInheritanceRulesToExclude()) {
                if (rules.length() > 0)
                    rules.append(',');
                rules.append(rule);
            }
            buf.append("<inheritance-rule-filter excludes='").append(rules.toString()).append("'/>");
        }
        buf.append("</realm>");
        return buf.toString();
    }

    /**
     * Adds a classRegex to this property.
     *
     * @param classRegex a classRegex.
     */
    public Realm regex(String classRegex) {
        addClassRegex(classRegex);
        return this;
    }

    /**
     * Adds the inheritanceRulesToInclude.
     *
     * @param includes the inheritanceRulesToInclude.
     */
    public Realm inheritanceRulesToInclude(String includes) {
        addInheritanceRulesToInclude(Arrays.asList(includes.split(",")));
        return this;
    }

    /**
     * Adds the inheritanceRulesToExclude.
     *
     * @param excludes the inheritanceRulesToExclude.
     */
    public Realm inheritanceRulesToExclude(String excludes) {
        addInheritanceRulesToExclude(Arrays.asList(excludes.split(",")));
        return this;
    }

    /**
     * Registers this realm with the repository.
     *
     * @return this realm.
     */
    public Realm register() {
        RealmRepository.instance().addRealm(this);
        return this;
    }
}
