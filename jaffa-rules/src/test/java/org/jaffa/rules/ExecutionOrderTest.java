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
import org.jaffa.rules.testmodels.ExecutionOrder1;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;


public class ExecutionOrderTest extends TestCase {

    public static ArrayList<String> eventLog = new ArrayList<>();
    private ApplicationContext ctx;

    /**
     * Runs the test suite.
     *
     * @param args The input args are not used.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(ExecutionOrderTest.class);
    }

    public ExecutionOrderTest(String name) {
        super(name);
    }


    public void testAOPInjection() {
        //assertTrue(Advised.class.isAssignableFrom(ExecutionOrder1.class));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.setupRepos();
    }

    public void test2DifferentClasses() {
        eventLog = new ArrayList<>();
        try {
            // TODO: Port this test to use new interceptors
//            System.out.println("--- Start test2DifferentClasses() ---");
//            // Add the first interceptor
//            System.out.println("--- Add the first interceptor : Log1 ---");
//            AdviceBinding binding1 = new AdviceBinding("execution(void test.rules.ExecutionOrder1->setField1(java.lang.String))", null);
//            binding1.addInterceptor(Log1Interceptor.class);
//            AspectManager.instance().addBinding(binding1);
//
//            // Add the second interceptor
//            System.out.println("--- Add the second interceptor : Log2 ---");
//            AdviceBinding binding2 = new AdviceBinding("execution(void test.rules.ExecutionOrder1->setField1(java.lang.String))", null);
//            binding2.addInterceptor(Log2Interceptor.class);
//            AspectManager.instance().addBinding(binding2);

            System.out.println("--- Create ExecutionOrder1 ---");
            ExecutionOrder1 obj = new ExecutionOrder1();

            System.out.println("--- setField1 ---");
            obj.setField1("hello");

            System.out.println("--- Event log ---");
            for (int i = 0; i < eventLog.size(); i++)
                System.out.println("    " + i + ") " + eventLog.get(i));

            System.out.println("--- End test2DifferentClasses() ---");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail(e.getLocalizedMessage());
        }
    }


//    public void test2CustomInstances() {
//        eventLog = new ArrayList();
//        try {
//            System.out.println("--- Start test2CustomInstances() ---");
//            // Add the first interceptor
//            System.out.println("--- Add the first interceptor ---");
//            Interceptor log1a = new Log1Interceptor();
//            System.out.println("-- 1st Logger = " + log1a.hashCode());
//            CustomAdviceBinding binding1 = new CustomAdviceBinding("execution(void test.rules.ExecutionOrder1->setField3(java.lang.String))", null);
//            binding1.addInterceptor(log1a);
//            AspectManager.instance().addBinding(binding1);
//            
//            // Add the second interceptor
//            System.out.println("--- Add the second interceptor ---");
//            Interceptor log1b = new Log1Interceptor();
//            System.out.println("-- 2st Logger = " + log1b.hashCode());
//            CustomAdviceBinding binding2 = new CustomAdviceBinding("execution(void test.rules.ExecutionOrder1->setField3(java.lang.String))", null);
//            binding2.addInterceptor(log1b);
//            AspectManager.instance().addBinding(binding2);
//
//            System.out.println("--- Create ExecutionOrder1 ---");
//            ExecutionOrder1 obj = new ExecutionOrder1();
//
//            System.out.println("--- setField3 ---");
//            obj.setField3("hello");
//
//            System.out.println("--- Event log ---");
//            for(int i =0; i<eventLog.size(); i++)
//                System.out.println("    " + i + ") " + eventLog.get(i));
//            
//            System.out.println("--- End test2CustomInstances() ---");
//        } catch (Exception e) {
//            e.printStackTrace(System.err);
//            fail(e.getLocalizedMessage());
//        }
//    }

    public void test2DifferentInstances() {
        eventLog = new ArrayList();
        try {
            System.out.println("--- Start test2DifferentInstances() ---");
            // Add the first interceptor
            // Add the first interceptor
            System.out.println("--- Add the first interceptor : Log1 ---");
            // TODO: Port this test to use the new interceptors
//            AdviceBinding binding1 = new AdviceBinding("execution(void test.rules.ExecutionOrder1->setField2(java.lang.String))", null);
//            binding1.addInterceptor(Log1Interceptor.class);
//            AspectManager.instance().addBinding(binding1);
//
//            // Add the second interceptor
//            System.out.println("--- Add the second interceptor : Log1 ---");
//            AdviceBinding binding2 = new AdviceBinding("execution(void test.rules.ExecutionOrder1->setField2(java.lang.String))", null);
//            binding2.addInterceptor(Log1Interceptor.class);
//            AspectManager.instance().addBinding(binding2);

            System.out.println("--- Create ExecutionOrder1 ---");
            ExecutionOrder1 obj = new ExecutionOrder1();

            System.out.println("--- setField2 ---");
            obj.setField2("hello");

            System.out.println("--- Event log ---");
            for (int i = 0; i < eventLog.size(); i++)
                System.out.println("    " + i + ") " + eventLog.get(i));

            System.out.println("--- End test2DifferentInstances() ---");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail(e.getLocalizedMessage());
        }
    }

    public void testInterceptors() {
        eventLog = new ArrayList();
        try {
            System.out.println("--- Start testInterceptors() ---");
            System.out.println("--- Create ExecutionOrder1 ---");
            ExecutionOrder1 obj = new ExecutionOrder1();

            System.out.println("--- setField1 ---");
            obj.setField1("hello");

            System.out.println("--- setField2 ---");
            obj.setField2("hello");

            System.out.println("--- setField3 ---");
            obj.setField3("hello");

            System.out.println("--- Event log ---");
            for (int i = 0; i < eventLog.size(); i++)
                System.out.println("    " + i + ") " + eventLog.get(i));

            System.out.println("--- End testInterceptors() ---");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail(e.getLocalizedMessage());
        }
    }
}
