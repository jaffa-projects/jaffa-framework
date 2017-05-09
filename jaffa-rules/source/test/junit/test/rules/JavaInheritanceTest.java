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

package test.rules;

import junit.framework.TestCase;
import org.jaffa.datatypes.exceptions.PatternMismatchException;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.util.ExceptionHelper;

/**
 * Results of Java Inheritance tests
 * 1 - The rules bound to the constructor of a parent class is not fired on creation of a child instance.
 * 2 - The rules for a child class cannnot be bound to a method, if that method doesn't exist on the child class, and hence will be ignored. The corresponding rules for the parent class will however be invoked.
 * 3 - The rules for a child class will be invoked, if the method is coded in the child class. The rules for the parent class will not be invoked, if the corresponding 'super' method is not invoked by the child.
 * 4 - The interceptor-stack passed while executing the rules for the child class is different from the interceptor-stack passed while executing the rules for the corresponding 'super' method. For example, this would result in the evaluation of the CaseType rule for both a child and a parent, even though the CaseTypePropertyRule is coded to perform case-conversion only once !!
 * 5 - The labels for the parent class are not inherited by a child class.
 *
 * @author PaulE
 */
public class JavaInheritanceTest extends TestCase {
    
    /** Creates new AccessTest
     * @param name The name of the test case.
     */
    public JavaInheritanceTest(String name) {
        super(name);
    }
    
    public void testObjectCreation() {
        // Create a parent instance and check the initialized values
        Parent p = new Parent();
        assertEquals("value11", p.getField1());
        assertEquals("value21", p.getField2());
        assertEquals("value31", p.getField3());
        
        // Create a child instance
        // The rules bound to the constructor of a parent class is not fired on creation of a child instance.
        Child1 c1 = new Child1();
        assertEquals("value12", c1.getField1()); // Override base class value, for inherited field
        assertEquals("value22", c1.getField2()); // Override base class value, for implemented field
        assertEquals("value31", c1.getField3()); // Inherited from base class
    }
    
    public void testParentOnlyMethodInvocation1() {
        // Test the binding of rules to a method coded only in the Parent,
        // and with rules defined only for the Child
        try {
            // Create a parent instance and ensure that no rules are defined for a property
            Parent p = new Parent();
            p.setField0("zzz");
            p.validate();
            assertEquals("No rule has been defined for Parent.field0, and hence its value should not have been transformed", "zzz", p.getField0());
            
            // Create a child instance and invoke a method that is not coded in the child class
            Child1 c1 = new Child1();
            c1.setField0("zzz");
            c1.validate();
            assertEquals("Upper case-type rule has been defined for Child1.field0, and hence its value should have been transformed", "ZZZ", c1.getField0());
            
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    
    public void testParentOnlyMethodInvocation2() {
        // Test the binding of rules to a method coded only in the Parent,
        // and with rules defined for both Parent and Child
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
            Child1 c1 = new Child1();
            c1.setField1("value13"); //common to both the Parent and Child1 rule-sets
            try {
                c1.setField1("value14"); //not a valid value for Parent
                c1.validate();
                fail("Child1.setField1(value14) should have failed since allowed values are 'value11,value12,value13' for the Parent and since the method is not overridden in the child class");
            } catch (Exception e) {
                PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                if (appExp == null)
                    throw e;
            }
            try {
                c1.setField1("value11"); //not a valid value for Child1
                c1.validate();
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
    
    public void testParentOnlyMethodInvocation3() {
        // Test the binding of rules to a method coded only in the Parent,
        // and with rules defined only for the Child
        // and with absolutely no rules defined for the Parent
        try {
            // Create a child instance and invoke a method that is not coded in the child class
            PersistentChild child = new PersistentChild();
            child.setField1("f1");
            child.preAdd(); //This should fire the beanshell script and change the value of field1
            assertEquals("Script rule has been defined for PersistentChild, and it should have changed the value of field1", "NewField1", child.getField1());
            
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
            // The rules for a child class will be invoked, if the method is coded in the child class. The rules for the parent class will not be invoked, if the corresponding 'super' method is not invoked by the child.
            Child1 c1 = new Child1();
            c1.setField2("value24");
            try {
                c1.setField2("value26");
                c1.validate();
                fail("Child1.setField2(value26) should have failed since allowed values are 'value23,value24,value25'");
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
    
    public void testParentAndChildMethodInvocation() {
        try {
            // Create a parent class and check the firing of a rule on invocation of a setter
            Parent p = new Parent();
            p.setField3("lOwEr");
            p.validate();
            assertEquals("lower", p.getField3());
            
            // Create a child instance and invoke a method that is overridden in the child class, and which also invokes the corresponding 'super' method
            // The interceptor-stack passed while executing the rules for the child class is different from the interceptor-stack passed while executing the rules for the corresponding 'super' method. For example, this would result in the evaluation of the CaseType rule for both a child and a parent, even though the CaseTypePropertyRule is coded to perform case-conversion only once !!
            Child1 c1 = new Child1();
            c1.setField3("UpPeR");
            c1.validate();
            assertEquals("UPPER", c1.getField3());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    
    public void testLabelUsingIntrospector() {
        try {
            IPropertyRuleIntrospector pri = null;
            
            Parent p = new Parent();
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(p.getClass().getName(), "field1", p);
            assertEquals("ParentField1", pri.getLabel());
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(p.getClass().getName(), "field2", p);
            assertEquals("ParentField2", pri.getLabel());
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(p.getClass().getName(), "field3", p);
            assertEquals("ParentField3", pri.getLabel());
            
            // The labels for the parent class are not inherited by a child class.
            Child1 c1 = new Child1();
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(c1.getClass().getName(), "field1", c1);
            assertNull(pri.getLabel());
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(c1.getClass().getName(), "field2", c1);
            assertEquals("Child1Field2", pri.getLabel());
            pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(c1.getClass().getName(), "field3", c1);
            assertNull(pri.getLabel());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    
}
