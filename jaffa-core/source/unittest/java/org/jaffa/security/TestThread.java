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
 * TestThread.java
 *
 * Created on April 8, 2002, 4:51 PM
 */

package org.jaffa.security;

import org.jaffa.security.SecurityManager;
import java.security.PrivilegedAction;
import org.apache.log4j.Logger;
import java.security.AccessControlException;
import org.jaffa.security.PolicyManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author  paule
 * @version
 */
public class TestThread {

    /** Set up Logging for Log4J */
    private static Logger log = Logger.getLogger(TestThread.class);

    private static String FUNC_1 = "payroll.viewSalary";
    private static String FUNC_2 = "payroll.updateSalary";


    public void test1() {
        System.out.println("Start: Test 1...");
        System.out.println("Run some code protected by business function '"+FUNC_1+"'");
        try {
            SecurityManager.runFunction(FUNC_1, new PrivilegedAction() {
                public Object run() {
                    System.out.println("*** Hello, I'm now running "+FUNC_1+"");
                    return null;
                }
            });
        } catch (AccessControlException e) {
            System.out.println("*** Access Denied!");
        }
        System.out.println("End: Test 1...");
    }

    public void test2() {
        System.out.println("Start: Test 2...");
        System.out.println("Run some code protected by business function '"+FUNC_2+"'");
        try {
            SecurityManager.runFunction(FUNC_2, new PrivilegedAction() {
                public Object run() {
                    System.out.println("*** Hello, I'm now running "+FUNC_2+"");
                    return null;
                }
            });
        } catch (AccessControlException e) {
            System.out.println("*** Access Denied!");
        }
        System.out.println("End: Test 2...");
    }

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {

        BasicConfigurator.configure();

        PolicyManager.printPolicy();

        try {
            HttpServletRequest request = new FakeRequest("PAUL");
            System.out.println("Conext: " + request.getUserPrincipal().getName());
            SecurityManager.runWithContext( request, new TestThread(), "test1", null);
            SecurityManager.runWithContext( request, new TestThread(), "test2", null);
        } catch (Exception e) {
            log.error("Test Failed", e);
        }

        try {
            HttpServletRequest request = new FakeRequest("MAHESH");
            System.out.println("Conext: " + request.getUserPrincipal().getName());
            SecurityManager.runWithContext( request, new TestThread(), "test1", null);
            SecurityManager.runWithContext( request, new TestThread(), "test2", null);
        } catch (Exception e) {
            log.error("Test Failed", e);
        }
    }

}
