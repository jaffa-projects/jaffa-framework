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
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.rules.testmodels.SomeGraphCriteria;
import org.jaffa.rules.testmodels.Strings1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * This tests the selective inheritance of rules across a realm, based on the defined filters
 * The selective inheritance can also be a result of the 'includes' and/or 'excludes' attributes defined in a 'super' rule
 */
public class SelectiveInheritanceTest extends TestCase {

    private ApplicationContext ctx;

    /**
     * Creates new AccessTest
     *
     * @param name The name of the test case.
     */
    public SelectiveInheritanceTest(String name) {
        super(name);
    }

    /**
     * Runs the test suite.
     *
     * @param args The input args are not used.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(SelectiveInheritanceTest.class);
    }

    /*
    Commenting out until AOP interceptors are finished

    public void testRealmFiltering() {
        try {
            IPropertyRuleIntrospector parentPri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(Strings1.class, "field2");
            IPropertyRuleIntrospector childPri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(SomeGraphCriteria.class, "field2");

            // min-length should not be inherited as defined in the realm
            assertEquals("Strings1.field2 should have a min-length of 3", new Integer(3), parentPri.getMinLength());
            assertNull("SomeGraphCriteria.field2 should not have a min-length", childPri.getMinLength());

            // case-type should be inherited as defined in the realm
            assertEquals("Strings1.field2 should have lower case-type", FieldMetaData.LOWER_CASE, parentPri.getCaseType());
            assertEquals("SomeGraphCriteria.field2 should have lower case-type", FieldMetaData.LOWER_CASE, childPri.getCaseType());

        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }*/

    public void testSuperRuleFiltering() {
        try {
            IPropertyRuleIntrospector parentPri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(Strings1.class, "field3");
            IPropertyRuleIntrospector childPri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(SomeGraphCriteria.class, "field3");

            // min-length should not be inherited as defined in the realm
            assertEquals("Strings1.field3 should have a max-length of 10", new Integer(10), parentPri.getMaxLength());
            assertNull("SomeGraphCriteria.field3 should not have a max-length", childPri.getMaxLength());

            // case-type should not be inherited as defined in the super-rule
            assertEquals("Strings1.field3 should have upper case-type", FieldMetaData.UPPER_CASE, parentPri.getCaseType());
            assertNull("SomeGraphCriteria.field3 should not have a case-type", childPri.getCaseType());

        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ctx = new AnnotationConfigApplicationContext(JaffaRulesConfig.class);
    }
}
