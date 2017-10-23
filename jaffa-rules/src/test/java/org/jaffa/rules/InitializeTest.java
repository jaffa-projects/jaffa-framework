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

package org.jaffa.rules;

import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;

/**
 * @author PaulE
 */
public class InitializeTest extends TestCase {

    private ApplicationContext ctx;

    /**
     * Creates new AccessTest
     *
     * @param name The name of the test case.
     */
    public InitializeTest(String name) {
        super(name);
    }

    /**
     * Runs the test suite.
     *
     * @param args The input args are not used.
     */
    /*public static void main(String[] args) {
        junit.textui.TestRunner.run(InitializeTest.class);
    }
*/
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.setupRepos();
    }

    public void testAOPInjection() {
        //assertTrue(org.jboss.aop.Advised.class.isAssignableFrom(Initialize1.class));
        //assertTrue(org.jboss.aop.Advised.class.isAssignableFrom(Initialize2.class));
    }
/*
    Commenting out until AOP interceptors are finished

    public void testInitialProperties() {
        try {
            Initialize1 obj = new Initialize1();
            assertEquals(obj.getFieldboolean(), true);
            assertEquals(123, obj.getFieldbyte());
            assertEquals("12345.567", "" + obj.getFielddouble());
            assertEquals("12345.56", "" + obj.getFieldfloat());
            assertEquals(1234567, obj.getFieldint());
            assertEquals(12345678L, obj.getFieldlong());
            assertEquals(12345, obj.getFieldshort());
            assertEquals(Boolean.TRUE, obj.getFieldBoolean());
            assertEquals(new Byte((byte) 123), obj.getFieldByte());
            assertEquals(12345.567, obj.getFieldDouble());
            assertEquals(12345.567f, obj.getFieldFloat());
            assertEquals(new Integer(1234567), obj.getFieldInteger());
            assertEquals(new Long(12345678), obj.getFieldLong());
            assertEquals(new Short((short) 12345), obj.getFieldShort());
            assertEquals("Hello World", obj.getFieldString());
            assertEquals(DateOnly.addDay(new DateOnly(), -30).dayOfYear(), obj.getFieldDateOnly().dayOfYear());
            assertEquals(new DateTime(2004, DateTime.JANUARY, 31, 12, 47, 44, 0), obj.getFieldDateTime());
            assertEquals(new Currency(100000.21), obj.getFieldCurrency());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    */

    /*
    Commenting out until AOP interceptors are finished

    public void testInitialMembers() {
        try {
            Initialize2 obj = new Initialize2();
            assertEquals(obj.getFieldboolean(), true);
            assertEquals(123, obj.getFieldbyte());
            assertEquals("12345.567", "" + obj.getFielddouble());
            assertEquals("12345.56", "" + obj.getFieldfloat());
            assertEquals(1234567, obj.getFieldint());
            assertEquals(12345678L, obj.getFieldlong());
            assertEquals(12345, obj.getFieldshort());
            assertEquals(Boolean.TRUE, obj.getFieldBoolean());
            assertEquals(new Byte((byte) 123), obj.getFieldByte());
            assertEquals(12345.567, obj.getFieldDouble());
            assertEquals(12345.567f, obj.getFieldFloat());
            assertEquals(new Integer(1234567), obj.getFieldInteger());
            assertEquals(new Long(12345678), obj.getFieldLong());
            assertEquals(new Short((short) 12345), obj.getFieldShort());
            assertEquals("Hello World", obj.getFieldString());
            assertEquals(DateOnly.addDay(new DateOnly(), -30).dayOfYear(), obj.getFieldDateOnly().dayOfYear());
            assertEquals(new DateTime(2004, DateTime.JANUARY, 31, 12, 47, 44, 0), obj.getFieldDateTime());
            assertEquals(new Currency(100000.21), obj.getFieldCurrency());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
*/
}
