/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2015 JAFFA Development Group
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

package org.jaffa.rules.initializers;

import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.TestModel;
import org.jaffa.rules.TestSupport;
import org.jaffa.rules.jbossaop.interceptors.AbstractRuleInterceptor;
import org.jaffa.rules.jbossaop.interceptors.InitializeInterceptor;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.rulemeta.IRuleEvaluator;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Initializer tests
 */
public class FieldInitializerTest extends TestSupport {
    public static final String FIELD1_INIT_VALUE = "Init Value";
    public static final Integer FIELD2_INIT_VALUE = 42;
    public static final int FIELD3_INIT_VALUE = 123;
    private FieldInitializer<TestModel> target = new FieldInitializer<>();

    /**
     * Setup all tests.
     */
    @Before
    public void setup() throws Exception {
        target.initializeRuleMapFromMetaData(TestModel.class.getName());
    }

    /**
     * Tests the interceptor based initializer
     */
    @Test
    public void testInterceptor() throws Throwable {
        TestModel testModel = new TestModel();

        AbstractRuleInterceptor interceptor = new InitializeInterceptor();

        interceptor.setTargetClassName(testModel.getClass().getName());
        Method method = TestModel.class.getMethod("validate");//this has to be a valid method, in production code it would be the constructor
        MethodInvocation invocation = new MethodInvocation(new Interceptor[0], 0, method, method, null);
        invocation.setTargetObject(testModel);

        assertNull(testModel.getField1());

        interceptor.invoke(invocation);

        assertEquals(FIELD1_INIT_VALUE, testModel.getField1());
    }

    /**
     * Tests the initializer rule sets the property value
     */
    @Test
    public void testInitializer() throws FrameworkException {
        TestModel testModel = new TestModel();
        assertNull(testModel.getField1());

        target.initialize(testModel);

        assertEquals(FIELD1_INIT_VALUE, testModel.getField1());//test when member parameter is not provided
        assertEquals(FIELD2_INIT_VALUE, testModel.getField2());//test when member parameter is provided
    }

    /**
     * If a condition exists for the rule, it should be evaluated.
     */
    @Test
    public void testConditionalInitialization() throws FrameworkException, ApplicationExceptions {
        TestModel testModel = new TestModel();
        IRuleEvaluator evaluator = mock(IRuleEvaluator.class);
        when(evaluator.checkCondition(anyObject(), any(RuleMetaData.class))).thenReturn(false);
        target.setRuleEvaluator(evaluator);

        target.initialize(testModel);

        // Neither field should have been initialized.
        assertNull(testModel.getField1());
        assertNull(testModel.getField2());
    }

    /**
     * If the meta data repository returns a null rule map, there are simply no rules to initialize.
     */
    @Test
    public void testNullPropertyRuleMap() throws FrameworkException, ApplicationExceptions {
        target.initializeRuleMapFromMetaData(null);
    }
}