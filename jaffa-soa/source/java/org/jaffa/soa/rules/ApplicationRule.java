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
package org.jaffa.soa.rules;

import java.util.Collection;
import java.util.LinkedList;
import org.drools.StatefulSession;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.session.IContextManager;

/**
 * A convenience class to insert ApplicationRules into the Drools session.
 * Each instance encapsulates the name and value of an ApplicationRule.
 * For rules having a comma-separated list of values, it is recommended to create
 * a unique ApplicationRule instance per value.
 */
public class ApplicationRule {

    private String name;
    private Object value;

    /**
     * Default constructor.
     */
    public ApplicationRule() {
    }

    /**
     * Constructor with name and value initialized to the inputs.
     * @param name the name.
     * @param value the value.
     */
    public ApplicationRule(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 17 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ApplicationRule other = (ApplicationRule) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return new StringBuilder("<ApplicationRule>").append("<name>").append(this.name != null ? this.name : "").append("</name>").append("<value>").append(this.value != null ? this.value : "").append("</value>").append("</ApplicationRule>").toString();
    }

    /**
     * Creates an ApplicationRule instance by looking up the ApplicationRules for the input name.
     * A null is returned if an entry is not found for the input.
     * @param name the application rule name.
     * @return an ApplicationRule instance.
     */
    public static ApplicationRule createFact(String name) {
        IContextManager contextManager = ContextManagerFactory.instance();
        return contextManager.getThreadContext().containsKey(name) ? new ApplicationRule(name, contextManager.getProperty(name)) : null;
    }

    /**
     * Creates a Collection of ApplicationRule instances by looking up the ApplicationRules for the input name.
     * This should typically be used if the rule points to a comma-separated list of values, or if
     * the value is Iterable.
     * A null is returned if an entry is not found for the input.
     * @param name the application rule name.
     * @return a Collection of ApplicationRule instances
     */
    public static Collection<ApplicationRule> createFacts(String name) {
        IContextManager contextManager = ContextManagerFactory.instance();
        if (contextManager.getThreadContext().containsKey(name)) {
            Collection<ApplicationRule> applicationRules = new LinkedList<ApplicationRule>();
            Object values = contextManager.getProperty(name);
            if (values != null) {
                // split a comma-separated string into an array
                if (values instanceof String)
                    values = ((String) values).split(",");

                // create an instance for each element in an array/collection
                if (values.getClass().isArray()) {
                    for (Object value : (Object[]) values)
                        applicationRules.add(new ApplicationRule(name, value));
                } else if (values instanceof Iterable) {
                    for (Object value : (Iterable) values)
                        applicationRules.add(new ApplicationRule(name, value));
                } else
                    applicationRules.add(new ApplicationRule(name, values));
            } else
                applicationRules.add(new ApplicationRule(name, values));
            return applicationRules;
        }
        return null;
    }

    /**
     * Creates an ApplicationRule instance by looking up the ApplicationRules for the input name.
     * The instance is then added to the session, if one doesn't already exist.
     * @param name the application rule name.
     * @param session the drools session.
     */
    public static void insertFact(StatefulSession session, String name) {
        Object fact = createFact(name);
        if (fact != null && session.getFactHandle(fact) == null)
            session.insert(fact);
    }

    /**
     * Creates a Collection of ApplicationRule instances by looking up the ApplicationRules for the input name.
     * This should typically be used if the rule points to a comma-separated list of values, or if
     * the value is Iterable.
     * The instances are then added to the session, if one doesn't already exist.
     * @param name the application rule name.
     * @param session the drools session.
     */
    public static void insertFacts(StatefulSession session, String name) {
        Collection<?> facts = createFacts(name);
        if (facts != null) {
            for (Object fact : facts) {
                if (fact != null && session.getFactHandle(fact) == null)
                    session.insert(fact);
            }
        }
    }
}
