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
package org.jaffa.rules.testinterceptors;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.interceptors.CxfFunctionGuardInterceptor;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.rulemeta.IRuleHelper;
import org.jaffa.rules.rulemeta.RuleHelperFactory;
import org.jaffa.security.SecurityManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityManager.class, MetaDataRepository.class, RuleHelperFactory.class})
public class CxfFunctionGuardInterceptorTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    //@Test
    public void testInterceptor() throws NoSuchMethodException, FrameworkException, ApplicationExceptions {
        // Mock the security manager
        PowerMockito.mockStatic(SecurityManager.class);
        PowerMockito.when(SecurityManager.checkFunctionAccess("Test")).thenReturn(true);

        // Mock the Rule helper factory and the rule it returns
        mockStatic(RuleHelperFactory.class);
        IRuleHelper ruleHelperFactory = mock(IRuleHelper.class);
        when(RuleHelperFactory.instance(Mockito.anyString())).thenReturn(ruleHelperFactory);

        // Mock the metadata repository
        mockStatic(MetaDataRepository.class);
        MetaDataRepository repository = mock(MetaDataRepository.class);
        when(MetaDataRepository.instance()).thenReturn(repository);

        // Create a real instance of an interceptor
        CxfFunctionGuardInterceptor interceptor = new CxfFunctionGuardInterceptor();

        // Successful access - matching method and name
        updateRules("Test", "Test", repository, ruleHelperFactory);
        interceptor.handleMessage(getMockMessage());

        // Failed access - matching method not valid name
        Fault fault = null;
        try {
            updateRules("Fail", "Test", repository, ruleHelperFactory);
            interceptor.handleMessage(getMockMessage());
        } catch (Fault f) {
            fault = f;
        }
        Assert.assertNotNull("Fault should have been thrown du to failed access", fault);

        // Successful access - not matching method, so rules not enforced
        updateRules("xxxxx", "NoMatch", repository, ruleHelperFactory);
        interceptor.handleMessage(getMockMessage());
    }

    /**
     * Update the rules list and set the name of the rule in the list - Test is the expected name
     *
     * @param name       Name of the rule for the test
     * @param method     Name of the method the rule applies to
     * @param repository Rule repository
     * @param ruleHelper Rule helper
     */
    private void updateRules(String name, String method, MetaDataRepository repository, IRuleHelper ruleHelper) throws FrameworkException, ApplicationExceptions {
        Map<String, List<RuleMetaData>> rules = new HashMap<>();
        List<RuleMetaData> ruleMetaDatas = new ArrayList<>();
        RuleMetaData rule = new RuleMetaData();
        rule.addParameter("method", method);
        rule.addParameter("name", name);
        ruleMetaDatas.add(rule);
        rules.put(null, ruleMetaDatas);
        when(repository.getPropertyRuleMap(Mockito.anyString(), Mockito.anyString())).thenReturn(rules);
        when(ruleHelper.getApplicableRules(Mockito.anyString(), Mockito.anyObject(), Mockito.anyListOf(RuleMetaData.class), Mockito.anyBoolean())).thenReturn(ruleMetaDatas);
    }

    /**
     * Creates a mock message that is destined for a method called "test"on
     */
    private Message getMockMessage() throws NoSuchMethodException {
        Message message = mock(Message.class);
        Exchange exchange = mock(Exchange.class);
        BindingOperationInfo bindingOperationInfo = mock(BindingOperationInfo.class);
        Service service = mock(Service.class);
        MethodDispatcher methodDispatcher = mock(MethodDispatcher.class);
        Method method = TestClass.class.getMethod("test");

        when(message.getExchange()).thenReturn(exchange);
        when(exchange.get(BindingOperationInfo.class)).thenReturn(bindingOperationInfo);
        when(exchange.get(Service.class)).thenReturn(service);
        when(service.get(MethodDispatcher.class.getName())).thenReturn(methodDispatcher);
        when(methodDispatcher.getMethod(bindingOperationInfo)).thenReturn(method);

        return message;
    }

    /**
     * Class used for the "test" method mock
     */
    public class TestClass {
        public void test() {
        }
    }
}
