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

package org.jaffa.rules.validators;

import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.TestModel;
import org.jaffa.rules.TestSupport;
import org.jaffa.rules.jbossaop.interceptors.CommentInterceptor;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertNotEquals;

/**
 * A comment validator should emulate the original interceptor's behavior.
 */
public class CommentValidatorTest extends TestSupport {

    private CommentValidator target = new CommentValidator();
    private TestModel testModel = new TestModel();
    private static String NEW_COMMENT = "some comment";

    /**
     * Setup all tests.
     */
    @Before
    public void setup() throws Exception {
        // This is what the validator factory would build up.
        target.setRuleMap(MetaDataRepository.instance().getPropertyRuleMap(TestModel.class.getName(), "comment"));
    }

    /**
     * Demonstrate that there's a proper situation for the CommentInterceptor to change the value of the field.
     */
    @Test
    public void testInterceptor() throws Throwable {
        testModel.setField1(NEW_COMMENT);
        testModel.setOriginalValue("");

        CommentInterceptor interceptor = new CommentInterceptor();
        interceptor.setTargetClassName(TestModel.class.getName());
        Method method = TestModel.class.getMethod("validate");
        MethodInvocation invocation = new MethodInvocation(new Interceptor[0], 0, method, method, null);

        // "field3" is configured for the comment
        invocation.setTargetObject(testModel);

        interceptor.invoke(invocation);

        //if field1 has changed, them the comment was changed by the interceptor.
        assertNotEquals(NEW_COMMENT, testModel.getField1());
    }

    /**
     * Demonstrate that there's a proper situation for the CommentValidator to change the value of the field.
     */
    @Test
    public void testValidator() throws ApplicationException, FrameworkException {
        testModel.setField1(NEW_COMMENT);

        target.validate(testModel);

        //if field1 has changed, them the comment was changed by the interceptor.
        assertNotEquals(NEW_COMMENT, testModel.getField1());
    }
}
