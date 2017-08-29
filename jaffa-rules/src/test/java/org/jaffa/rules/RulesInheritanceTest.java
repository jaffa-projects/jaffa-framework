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
import org.jaffa.datatypes.exceptions.PatternMismatchException;
import org.jaffa.datatypes.exceptions.TooMuchDataException;
import org.jaffa.rules.testmodels.Child2;
import org.jaffa.rules.testmodels.Child3;
import org.jaffa.rules.testmodels.Parent;
import org.jaffa.util.ExceptionHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//import org.jaffa.rules.aop.metadata.ClassAdvisorHelper;

/**
 * @author PaulE
 */
public class RulesInheritanceTest extends TestCase {

    private ApplicationContext ctx;

    /**
     * Creates new Test
     *
     * @param name The name of the test case.
     */
    public RulesInheritanceTest(String name) {
        super(name);
    }

    /**
     * Runs the test suite.
     *
     * @param args The input args are not used.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(RulesInheritanceTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ctx = new AnnotationConfigApplicationContext(JaffaRulesConfig.class);
    }

    public void testChainedInheritanceObjectCreation() {
        // Create a child instance, which extends Child2, which in turn extends Parent
        Child3 c3 = new Child3();
        assertEquals("value12", c3.getField1());
        assertEquals("value21", c3.getField2());
        assertEquals("value21", c3.getField3());
    }

    public void testObjectCreation() {
        // Print debug info
        //ClassAdvisorHelper.debugListInterceptors(Parent.class);
        //ClassAdvisorHelper.debugListInterceptors(Child2.class);
        //ClassAdvisorHelper.debugListInterceptors(Child3.class);

        // Create a parent instance and check the initialized values
        Parent p = new Parent();
        assertEquals("value11", p.getField1());
        assertEquals("value21", p.getField2());
        assertEquals("value31", p.getField3());

        // Create a child instance
        Child2 c2 = new Child2();
        assertEquals("value12", c2.getField1());
        assertEquals("value21", c2.getField2());
        assertEquals("value21", c2.getField3());
    }

    public void testParentOnlyMethodInvocation() {
        try {
            // Create a parent instance and check the firing of a rule on invocation of a setter
            Parent p = new Parent();
            p.setField1("value13");
            try {
                p.setField1("value14");
                p.validate();
                fail("Parent.setField1(value14) should have failed since allowed values are 'value11,value12,value13'");
            } catch (Exception e) {
                PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                if (appExp == null)
                    throw e;
            }

            // Create a child instance and invoke a method that is not coded in the child class
            Child2 c2 = new Child2();
            c2.setField1("value13"); //common to both the Parent and Child2 rule-sets
            try {
                c2.setField1("value14"); //not a valid value for Parent
                c2.validate();
                fail("Child2.setField1(value14) should have failed since allowed values are 'value11,value12,value13' for the Parent and since the method is not overridden in the child class");
            } catch (Exception e) {
                PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                if (appExp == null)
                    throw e;
            }
            try {
                c2.setField1("value11"); //not a valid value for Child2
                c2.validate();
                fail("Child2.setField1(value11) should have failed since allowed values are 'value12,value13,value14,value15', even though the method is defined in the base class only");
            } catch (Exception e) {
                PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                if (appExp == null)
                    throw e;
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testChainedInheritanceMethodInvocation() {
        try {
            // Create a child instance and invoke a method that is not coded in the child class
            Child3 c3 = new Child3();
            c3.setField1("value13");
            try {
                c3.setField1("value14");
                c3.validate();
                fail("Child3.setField1(value14) should have failed since allowed values are 'value11,value12,value13'");
            } catch (Exception e) {
                PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                if (appExp == null)
                    throw e;
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testChildOnlyMethodInvocation() {
        try {
            // Create a parent instance and check the firing of a rule on invocation of a setter
            Parent p = new Parent();
            p.setField2("value23");
            try {
                p.setField2("value24");
                p.validate();
                fail("Parent.setField2(value24) should have failed since allowed values are 'value21,value22,value23'");
            } catch (Exception e) {
                PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                if (appExp == null)
                    throw e;
            }

            // Create a child instance and invoke a method that is overridden in the child class, and which does not invoke the corresponding 'super' method
            Child2 c2 = new Child2();
            c2.setField2("value23");
            try {
                c2.setField2("value24");
                c2.validate();
                fail("Child2.setField2(value24) should have failed since allowed values are 'value21,value22,value23'");
            } catch (Exception e) {
                PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                if (appExp == null)
                    throw e;
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testMultipleInheritance() {
        try {
            // Create a parent class and check the firing of a rule on invocation of a setter
            Parent p = new Parent();
            p.setField3("lOwEr");
            p.validate();
            assertEquals("lower", p.getField3());

            // Create a child instance and invoke a method that is overridden in the child class, and which also invokes the corresponding 'super' method
            // Note this is multiple inheritance at play:
            // Child2.field3 extends Parent.field3 via he natural java inheritance
            // Child2.field3 extends Child2.field2 via he jaffa rules inheritance
            Child2 c2 = new Child2();
            c2.setField3("value23");
            c2.validate();
            assertEquals("value23", c2.getField3());
            try {
                // "bigvalue" is a valid value for Child2.field2, but is too big for Parent.field3
                c2.setField3("bigvalue");
                c2.validate();
            } catch (Exception e) {
                TooMuchDataException appExp = (TooMuchDataException) ExceptionHelper.extractException(e, TooMuchDataException.class);
                if (appExp == null)
                    throw e;
            }

            // Create a child instance and invoke a method that is overridden in the child class, and which also invokes the corresponding 'super' method
            Child3 c3 = new Child3();
            c3.setField3("value23");
            c3.validate();
            assertEquals("value23", c3.getField3());
            try {
                // "bigvalue" is a valid value for Child2.field2, but is too big for Parent.field3
                c3.setField3("bigvalue");
                c3.validate();
            } catch (Exception e) {
                TooMuchDataException appExp = (TooMuchDataException) ExceptionHelper.extractException(e, TooMuchDataException.class);
                if (appExp == null)
                    throw e;
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testLabelUsingIntrospector() {
        try {
            IPropertyRuleIntrospector pri;

            Parent p = new Parent();
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(p.getClass().getName(), "field1", p);
            assertEquals("ParentField1", pri.getLabel());
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(p.getClass().getName(), "field2", p);
            assertEquals("ParentField2", pri.getLabel());
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(p.getClass().getName(), "field3", p);
            assertEquals("ParentField3", pri.getLabel());

            Child2 c2 = new Child2();
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(c2.getClass().getName(), "field1", c2);
            assertEquals("ParentField1", pri.getLabel());
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(c2.getClass().getName(), "field2", c2);
            assertEquals("Child2Field2", pri.getLabel());
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(c2.getClass().getName(), "field3", c2);
            assertEquals("Child2Field2", pri.getLabel());

            Child3 c3 = new Child3();
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(c3.getClass().getName(), "field1", c3);
            assertEquals("ParentField1", pri.getLabel());
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(c3.getClass().getName(), "field2", c3);
            assertEquals("Child2Field2", pri.getLabel());
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(c3.getClass().getName(), "field3", c3);
            assertEquals("Child2Field2", pri.getLabel());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
}
