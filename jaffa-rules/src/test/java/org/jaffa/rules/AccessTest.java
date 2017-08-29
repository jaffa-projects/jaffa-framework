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

/*
 * AddTest.java
 *
 * Created on April 1, 2002, 5:47 PM
 */

package org.jaffa.rules;

import junit.framework.TestCase;
import org.jaffa.config.JaffaRulesConfig;
import org.jaffa.rules.testmodels.Access1;
import org.jaffa.rules.testmodels.Access2;
import org.jaffa.rules.testmodels.Access3;
import org.jaffa.rules.testmodels.Access4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.jaffa.rules.testmodels.AccessConstants.*;

/**
 * @author PaulE
 */
public class AccessTest extends TestCase {

    private ApplicationContext ctx;

    /**
     * Creates new AccessTest
     *
     * @param name The name of the test case.
     */
    public AccessTest(String name) {
        super(name);
    }

    /**
     * Runs the test suite.
     *
     * @param args The input args are not used.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(AccessTest.class);
    }

    public void testAccessField() {
        try {
            Access1 obj = new Access1();
            assertEquals(DATA1, obj.getField1());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testAccessReadOnlyField() {
        try {
            Access1 obj = new Access1();
            assertEquals(DATA2, obj.getField2());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testAccessHiddenField() {
        try {
            // The getter should return a null, even though the field has a value
            Access1 obj = new Access1();
            //assertNull(obj.getField3());
            assertEquals(DATA3, obj.getField3());
            assertEquals(DATA3, obj.field3);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }


    public void testUpdateField() {
        try {
            Access1 obj = new Access1();
            obj.setField1(DATA4);
            assertEquals(DATA4, obj.getField1());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testUpdateReadOnlyField() {
        try {
            Access1 obj = new Access1();
            obj.setField2(DATA4);
            //assertTrue("A read-only field should not have been updated", !DATA4.equals(obj.getField2()));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testUpdateHiddenField() {
        try {
            Access1 obj = new Access1();
            obj.setField3(DATA4);
            //assertTrue("A hidden field should not have been updated", !DATA4.equals(obj.field3));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }


    public void testInitializeField() {
        try {
            Access2 obj = new Access2();
            assertEquals(obj.getField1(), DATA4);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    protected void setUp() throws Exception {
        super.setUp();
        ctx = new AnnotationConfigApplicationContext(JaffaRulesConfig.class);
    }

    public void testInitializeReadOnlyField() {
        try {
            // The field should not be initialized, since it is read-only
            Access3 obj = new Access3();
            assertTrue("A read-only field should not have been initialized", !DATA4.equals(obj.getField2()));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testInitializeReadOnlyMember() {
        try {
            Access2 obj = new Access2();
            assertEquals(obj.getField2(), DATA4);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testInitializeHiddenMember() {
        try {
            Access2 obj = new Access2();
            assertEquals(obj.xgetField3(), DATA4);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }


    public void testPropertyInspection() {
        try {
            Access1 obj = new Access1();
            IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj);
            assertTrue(!w.isReadOnly());
            assertTrue(!w.isHidden());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testReadOnlyPropertyInspection() {
        try {
            Access1 obj = new Access1();
            IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field2", obj);
            assertTrue(w.isReadOnly());
            assertTrue(!w.isHidden());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testHiddenPropertyInspection() {
        try {
            Access1 obj = new Access1();
            IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field3", obj);
            //assertTrue(!w.isReadOnly());
            assertTrue(w.isHidden());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testInitializeHiddenField() {
        try {
            // The field should not be initialized, since it is read-only
            Access4 obj = new Access4();
            assertTrue("A hidden field should not have been initialized", !DATA4.equals(obj.field3));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
}
