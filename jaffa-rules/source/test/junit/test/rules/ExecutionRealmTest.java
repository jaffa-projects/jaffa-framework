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
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.util.ExceptionHelper;

/**
 */
public class ExecutionRealmTest extends TestCase {

    /** Creates new Test
     * @param name The name of the test case.
     */
    public ExecutionRealmTest(String name) {
        super(name);
    }
    
    public void testBusinessRealmRule() {
        try {
            Child2 c2 = new Child2();
            
            // Pass an invalid foreign-key
            try {
                c2.setField4("ZZZ");
                c2.validate();
                fail("The invocation Child2.setField4(ZZZ) should have failed since we passed an invalid foreign-key");
            } catch (Exception e) {
                InvalidForeignKeyException appExp = (InvalidForeignKeyException) ExceptionHelper.extractException(e, InvalidForeignKeyException.class);
                if (appExp != null) {
                    Object[] arguments = appExp.getArguments();
                    assertNotNull("The InvalidForeignKeyException should have arguments", arguments);
                    assertTrue("The InvalidForeignKeyException should have arguments", arguments.length > 0);
                    assertEquals("The InvalidForeignKeyException should have been created for field4", "Child2Field4", arguments[0]);
                } else {
                    throw e;
                }
            }
            
            // Now pass a valid foreign-key
            c2.setField4("KEY2");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    
    public void testBusinessRealmRuleInheritance() {
        try {
            // Child3 inherits the rules from Child2
            Child3 c3 = new Child3();
            
            // Pass an invalid foreign-key.
            // It should work, since Child3 has no execution-realm, and the foreign-key rule requires a 'business' realm
            c3.setField4("ZZZ");
            
            // Child3 will however inherit the non realm related rules from Child2
            // For example, it should inherit the label
            assertEquals("Child3 should have inherited the non-realm rules from Child2", "Child2Field4", RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(c3.getClass().getName(), "field4", c3).getLabel());
            
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    
}
