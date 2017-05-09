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

package org.jaffa.rules;

import org.jaffa.config.JaffaRulesConfig;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.FakeModel;
import org.jaffa.persistence.engines.IPersistenceEngine;
import org.jaffa.persistence.engines.IPersistenceEngineFactory;
import org.jaffa.persistence.engines.PersistenceEngineFactory;
import org.jaffa.rules.jbossaop.interceptors.AbstractRuleInterceptor;
import org.jaffa.rules.jbossaop.interceptors.AbstractValidateInterceptor;
import org.jaffa.rules.rulemeta.Parameter;
import org.jaffa.rules.rulemeta.Rule;
import org.jaffa.rules.rulemeta.RuleRepository;
import org.jaffa.rules.validators.RuleValidator;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.junit.BeforeClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestSupport {
    protected static final String CLASS = FakeModel.class.getName();
    protected static final String REALM = "business";
    protected static final String VARIATION = "DEF";
    private static final Object lockObject = new Object();
    protected static AnnotationConfigApplicationContext appContext;
    protected static FakeModel fakeModel;
    private static IPersistenceEngine engine;

    /**
     * Common initializations
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setupContext() throws Exception {
        synchronized (lockObject) {
            if (appContext != null) {
                return;
            }
            //System.out.println("setupContext()");

            appContext = new AnnotationConfigApplicationContext(JaffaRulesConfig.class,
                    TestConfig.class);
            assertNotNull(appContext);

            Rule label = new Rule();
            label.setName("label");
            Parameter value = new Parameter();
            value.setName("value");
            label.addParameter(value);
            Parameter condition = new Parameter();
            condition.setName("condition");
            label.addParameter(condition);
            RuleRepository.instance().addRule(label);

            // get fake model bean from combined app context
            fakeModel = appContext.getBean("fakeModel", FakeModel.class);
            assertNotNull(fakeModel);

            engine = mock(IPersistenceEngine.class);
            IPersistenceEngineFactory persistenceEngineFactory = mock(IPersistenceEngineFactory.class);
            when(persistenceEngineFactory.newPersistenceEngine()).thenReturn(engine);
            PersistenceEngineFactory.setFactory(persistenceEngineFactory);
        }
    }

    /**
     * Gets the mock persistence engine.
     *
     * @return the mock persistence engine.
     */
    public static IPersistenceEngine getEngine() {
        return engine;
    }

    /**
     * Executes the interceptor and confirms the correct exception is thrown with the field label
     *
     * @param interceptorClass The Interceptor class
     * @param persistent       The target object of the interceptor
     * @param fieldName        The Field Name label
     * @param exceptionClass   The class of the expected exception
     * @throws Throwable
     */
    protected void executeInterceptor(Class interceptorClass, Object persistent, String fieldName,
                                      Class exceptionClass) throws Throwable {

        assertTrue(AbstractValidateInterceptor.class.isAssignableFrom(interceptorClass));

        AbstractRuleInterceptor interceptor = (AbstractRuleInterceptor) interceptorClass.newInstance();

        interceptor.setTargetClassName(persistent.getClass().getName());
        Method method = persistent.getClass().getMethod("validate");
        MethodInvocation invocation = new MethodInvocation(new Interceptor[0], 0, method, method, null);
        invocation.setTargetObject(persistent);

        List<String> actualLabels = new ArrayList<>();
        ApplicationException exception = null;

        try {
            interceptor.invoke(invocation);
        } catch (ApplicationExceptions e) {
            ApplicationException[] exceptions = e.getApplicationExceptionArray();
            exception = exceptions[0];
            for (ApplicationException sub : exceptions) {
                actualLabels.add((String) sub.getArguments()[0]);
            }
        }

        assertEquals(1, actualLabels.size());
        assertEquals(fieldName, actualLabels.get(0));
        assertTrue(exceptionClass.isInstance(exception));
    }

    /**
     * Executes a validator and confirms the correct exception is thrown with the field label
     *
     * @param validator  The validator to execute
     * @param persistent The target object of the validator
     * @param fieldName  The Field Name label
     * @param clazz      The class of the expected exception
     * @throws FrameworkException
     */
    protected void executeValidator(RuleValidator validator, Object persistent, String fieldName,
                                    Class clazz) throws ApplicationException, FrameworkException {
        String actualLabel = null;
        ApplicationException exception = null;

        try {
            validator.validate(persistent);
        } catch (ApplicationException e) {
            actualLabel = (String) e.getArguments()[0];
            exception = e;
        }

        assertEquals(fieldName, actualLabel);
        assertTrue(clazz.isInstance(exception));
    }
}
