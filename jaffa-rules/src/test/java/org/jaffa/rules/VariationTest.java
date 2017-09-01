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

package org.jaffa.rules;

import junit.framework.TestCase;
import org.jaffa.config.JaffaRulesConfig;
import org.jaffa.rules.testmodels.VariationBean;
import org.jaffa.security.VariationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * It is recommened that the variant rules be put after the non-variant rules.
 * If the rules are spread across multiple JAR files, then ensure that the non-variant rules appear before the variant rules.
 */
public class VariationTest extends TestCase {

    private ApplicationContext ctx;

    /**
     * Creates a new Test
     *
     * @param name The name of the test case.
     */
    public VariationTest(String name) {
        super(name);
    }

    /**
     * Runs the test suite.
     *
     * @param args The input args are not used.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(VariationTest.class);
    }

    protected void setUp() throws Exception {
        ctx = new AnnotationConfigApplicationContext(JaffaRulesConfig.class);
    }

    public void testCoreRules() {
        try {
            VariationBean obj = new VariationBean();

            // Test the rules to be applied, irrespective of the current variation
            VariationContext.setVariation(null);
            assertEquals("NoVariationField1", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj).getLabel());
            assertEquals("NoVariationField2", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field2", obj).getLabel());
            assertEquals("NoVariationField3", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field3", obj).getLabel());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testNoVariationRules() {
        try {
            VariationBean obj = new VariationBean();

            // We should still pick up the core rules if no rules are defined for the current variation
            VariationContext.setVariation("BLAH-BLAH-BLAH");
            assertEquals("NoVariationField1", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj).getLabel());
            assertEquals("NoVariationField2", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field2", obj).getLabel());
            assertEquals("NoVariationField3", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field3", obj).getLabel());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testClassLevelVariation() {
        try {
            VariationBean obj = new VariationBean();

            // This will pickup the VAR1 rules, which have been defined at the class-level
            VariationContext.setVariation("VAR1");
            assertEquals("VAR1Field1", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj).getLabel());
            assertEquals("NoVariationField2", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field2", obj).getLabel());
            assertEquals("NoVariationField3", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field3", obj).getLabel());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testPropertyLevelVariation() {
        try {
            VariationBean obj = new VariationBean();

            // This will pickup the VAR2 rules, which have been defined at the property-level
            VariationContext.setVariation("VAR2");
            assertEquals("NoVariationField1", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj).getLabel());
            assertEquals("VAR2Field2", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field2", obj).getLabel());
            assertEquals("NoVariationField3", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field3", obj).getLabel());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testRuleLevelVariation() {
        try {
            VariationBean obj = new VariationBean();

            // This will pickup the VAR3 rules, which have been defined at the rule-level
            VariationContext.setVariation("VAR3");
            assertEquals("NoVariationField1", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj).getLabel());
            assertEquals("NoVariationField2", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field2", obj).getLabel());
            assertEquals("VAR3Field3", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field3", obj).getLabel());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    /*
        public void testUriMatchingVariation() {
            try {
                VariationBean obj = new VariationBean();

                // This will pickup the rules defined in a MetaData file having a mapping for it's URI with a comma-separated list of variations
                VariationContext.setVariation("VAR1");
                assertTrue("Field1 should have been hidden for variation VAR1", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj).isHidden());

                VariationContext.setVariation("VAR2");
                assertTrue("Field1 should have been hidden for variation VAR2", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj).isHidden());

                VariationContext.setVariation("VAR3");
                assertTrue("Field1 should not have been hidden for variation VAR3", !RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj).isHidden());
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
    */
    public void testOverridingUriMatchingVariation() {
        try {
            VariationBean obj = new VariationBean();

            VariationContext.setVariation("VAR1");
            assertTrue("Field2 should not have been hidden for variation VAR1", !RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field2", obj).isHidden());

            VariationContext.setVariation("VAR2");
            assertTrue("Field2 should not have been hidden for variation VAR2", !RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field2", obj).isHidden());

            VariationContext.setVariation("VAR3");
            assertTrue("Field2 should have been hidden for variation VAR3", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field2", obj).isHidden());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
}
