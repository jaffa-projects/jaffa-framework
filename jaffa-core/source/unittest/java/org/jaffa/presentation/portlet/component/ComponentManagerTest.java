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

package org.jaffa.presentation.portlet.component;

import junit.framework.TestCase;
import java.util.HashMap;
import org.jaffa.presentation.portlet.component.ComponentDefinition;
import org.jaffa.presentation.portlet.component.componentdomain.Loader;
import org.jaffa.config.Config;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author PaulE
 */
public class ComponentManagerTest extends TestCase {


    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public ComponentManagerTest(String name) {
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

    public void testReadDefinitions() {
        try {

            Map m = Loader.getComponentPool();
            assertNotNull("Loading Component Definitions", m);
            assertEquals("Got All Components", 6, m.size() );

            // Simple Component
            ComponentDefinition cd1 = (ComponentDefinition) m.get("Unit.Test.Component1");
            assertNotNull("Reading Component1", cd1);

            // Component will type and description
            ComponentDefinition cd2 = (ComponentDefinition) m.get("Unit.Test.Component2");
            assertNotNull("Reading Component2", cd2);
            assertNotNull("Component2 has type", cd2.getComponentType());

            // Component with optional and mandatory functions
            ComponentDefinition cd3 = (ComponentDefinition) m.get("Unit.Test.Component3");
            assertNotNull("Reading Component3", cd3);
            assertEquals("Component3 Has 3 Mandatory Functions", 3, cd3.getMandatoryFunctions().length );
            assertEquals("Component3 Has 3 Optional Functions", 3, cd3.getMandatoryFunctions().length );

            String[] mand = new String[] {"Function1","Function2","Function3"};
            assertTrue("Check Component3 Mandatory Functions", Arrays.equals(mand, cd3.getMandatoryFunctions() ) );

            String[] opt = new String[] {"FunctionA","FunctionB","FunctionC"};
            assertTrue("Check Component3 Optional Functions", Arrays.equals(opt, cd3.getOptionalFunctions() ) );


        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    public void testGetRequirements() {
        try {

            Map m = ComponentManager.getComponentRequirements();
            assertNotNull("Get Requirements", m);

            assertTrue("Only Component Unit.Test.Component3 Has Requirements", m.containsKey("Unit.Test.Component3") );

            String[] mandList = (String[]) m.get("Unit.Test.Component3");

            assertEquals("Component has 3 Required Functions", 3, mandList.length);
            assertEquals("Has Function1", "Function1", mandList[0]);
            assertEquals("Has Function2", "Function2", mandList[1]);
            assertEquals("Has Function3", "Function3", mandList[2]);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
