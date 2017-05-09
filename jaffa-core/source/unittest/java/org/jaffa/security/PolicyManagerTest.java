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

package org.jaffa.security;

import junit.framework.TestCase;
import org.jaffa.security.PolicyManager;
import java.util.Arrays;

/**
 *
 * @author PaulE
 */
public class PolicyManagerTest extends TestCase {


    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public PolicyManagerTest(String name) {
        super(name);
    }

    /** Sets up the fixture
     */
    protected void setUp() {
    }

    /** Tears down the fixture
     */
    protected void tearDown() {
    }

    public void testFunctionAccess() {
        try {

            // No-one should have access for this unknown function
            String [] fx = PolicyManager.getRolesForFunction("FunctionX");
            assertNull("No-one has access for FunctionX", fx);

            // Function1 should be accessable by 2 roles (MANAGER,SUPERVISOR)
            String [] f1 = PolicyManager.getRolesForFunction("Function1");
            assertNotNull("Two Roles Have Access To Function1", f1);
            assertEquals("Two Roles Have Access To Function1", 2, f1.length);
            assertTrue("MANAGER has access?", Arrays.asList(f1).contains("MANAGER") );
            assertTrue("SUPERVISOR has access?", Arrays.asList(f1).contains("SUPERVISOR") );
            assertTrue("CLERK has no access?", !Arrays.asList(f1).contains("CLERK") );

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

     // This test makes sure the Security Exception is thrown
     public void testInvalidComponentAccess() {
        try {
            String [] a1 = PolicyManager.getRolesForComponent("Unit.Test.Component21512345231");
            fail("Invalid component didn't throw error");
        } catch (SecurityException e) {
            assertTrue("Invalid component throws error", true);
        }
    }

    // Test a component that has no constraints. Should return a null to imply
    // no security restrictions
    public void testComponentNoConstraints() {
        // Component1 should be accessable by all roles, and should return null
        String [] a1 = PolicyManager.getRolesForComponent("Unit.Test.Component1");
        assertNull("Full Access To Component1", a1);
    }

    
    // Test a component that has constraints, which all roles satify
    // Should return a list with all roles in it
    public void testComponentWithConstraintsAllUsers() {
        try {

            // Component4 should be accessable by 2 roles (MANAGER,SUPERVISOR)
            String [] a4 = PolicyManager.getRolesForComponent("Unit.Test.Component6");
            assertNotNull("All 3 Roles Have Access To Component6", a4);
            assertEquals("All 3 Roles Have Access To Component6", 3, a4.length);
            assertTrue("MANAGER has access?", Arrays.asList(a4).contains("MANAGER") );
            assertTrue("SUPERVISOR has access?", Arrays.asList(a4).contains("SUPERVISOR") );
            assertTrue("CLERK has no access?", Arrays.asList(a4).contains("CLERK") );

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    
    // Test a component that has constraints, which some roles satify
    // Should return a list with 2 roles in it
    public void testComponentWithConstraintsLimitedUsers() {
        try {

            // Component4 should be accessable by 2 roles (MANAGER,SUPERVISOR)
            String [] a4 = PolicyManager.getRolesForComponent("Unit.Test.Component4");
            assertNotNull("Two Roles Have Access To Component4", a4);
            assertEquals("Two Roles Have Access To Component4", 2, a4.length);
            assertTrue("MANAGER has access?", Arrays.asList(a4).contains("MANAGER") );
            assertTrue("SUPERVISOR has access?", Arrays.asList(a4).contains("SUPERVISOR") );
            assertTrue("CLERK has no access?", !Arrays.asList(a4).contains("CLERK") );

            // Component3 should be accessable by 1 role (MANAGER)
            String [] a3 = PolicyManager.getRolesForComponent("Unit.Test.Component3");
            assertNotNull("One Role Has Access To Component3", a3);
            assertEquals("One Role Has Access To Component3", 1, a3.length);
            assertTrue("MANAGER has access?", Arrays.asList(a3).contains("MANAGER") );
            assertTrue("SUPERVISOR has no access?", !Arrays.asList(a3).contains("SUPERVISOR") );
            assertTrue("CLERK has no access?", !Arrays.asList(a3).contains("CLERK") );

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    
    // Test a component that has constraints, which no roles satify
    // Should return an empty list (NOT A NULL!)
    public void testComponentWithConstraintsNoUsers() {
        try {
            // Component5 should be accessable by no roles, and should return an empty array
            String [] a5 = PolicyManager.getRolesForComponent("Unit.Test.Component5");
            assertNotNull("No-One Has Access To Component5", a5);
            assertEquals("No Roles Have Access To Component5", 0, a5.length);
            assertTrue("MANAGER has no access?", !Arrays.asList(a5).contains("MANAGER") );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
