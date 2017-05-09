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

package security;

import junit.framework.TestCase;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebTable;
import com.meterware.httpunit.WebForm;



/**
 *
 * @author PaulE
 */
public class JSPGuardsTest extends TestCase {

    private static final String WEB_ROOT = "http://localhost:8080/httpunittest/";

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public JSPGuardsTest(String name) {
        super(name);
    }

    /** Sets up the fixture, by creating the UOW. This method is called before a test is executed.
     */
    protected void setUp() {
    }

    /** Tears down the fixture, by closing the UOW. This method is called after a test is executed.
     */
    protected void tearDown() {
    }

    private WebResponse logOn(WebConversation wc, String url, String user, String password)
    throws Exception {
        WebRequest req = new GetMethodWebRequest(WEB_ROOT + url);
        WebResponse resp = wc.getResponse(req);
        assertEquals("Got To Log On Page", "Log On", resp.getTitle());

        WebForm form = resp.getForms()[0];
        WebRequest request = form.getRequest();
        request.setParameter("j_username", user);
        request.setParameter("j_password", password);

        return wc.getResponse(request);
    }

    public void testBadLogon() {
        try {
            WebConversation wc = new WebConversation();

            WebResponse resp = logOn(wc, "startComponent.do?component=Test.Security.Test1", "nouser","nopassword");
            assertEquals("Got To Error Page", "Error with Log On", resp.getTitle());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testClerkAccess() {
        try {
            WebConversation wc = new WebConversation();
            WebResponse resp = logOn(wc, "startComponent.do?component=Test.Security.Test1", "GAUTAM", "dummy");
            assertEquals("Got To Main Page", "Test.Security.Test1.Page1", resp.getTitle());

            WebTable table = resp.getTableWithID("tagTable");
            assertNotNull("Found Table For Checks", table);
            String text = null;

            // Access Function1 - false
            text = table.getCellAsText(0,0);
            assertTrue("Empty Row 1", text == null || text.length() == 0);

            // No access Function1 - true
            text = table.getCellAsText(1,0);
            assertTrue("Not empty Row 2", text != null && text.length() > 0);

            // Access Function2 - false
            text = table.getCellAsText(2,0);
            assertTrue("Empty Row 3", text == null || text.length() == 0);

            // No access Function2 - true
            text = table.getCellAsText(3,0);
            assertTrue("Not empty Row 4", text != null && text.length() > 0);

            // Access FunctionX - false
            text = table.getCellAsText(4,0);
            assertTrue("Empty Row 5", text == null || text.length() == 0);

            // No access FunctionX - true
            text = table.getCellAsText(5,0);
            assertTrue("Not empty Row 6", text != null && text.length() > 0);

            // Access Component Test.Security.Test2 - false
            text = table.getCellAsText(6,0);
            assertTrue("Empty Row 7", text == null || text.length() == 0);

            // No access Component Test.Security.Test2 - true
            text = table.getCellAsText(7,0);
            assertTrue("Not empty Row 8", text != null && text.length() > 0);

            // Access Component Not.Real.Component - false
            text = table.getCellAsText(8,0);
            assertTrue("Not empty Row 9", text != null && text.length() > 0);

            // No access Component Not.Real.Component - true
            text = table.getCellAsText(9,0);
            assertTrue("Empty Row 10", text == null || text.length() == 0);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testManagerAccess() {
        try {
            WebConversation wc = new WebConversation();
            WebResponse resp = logOn(wc, "startComponent.do?component=Test.Security.Test1", "PAUL", "dummy");
            assertEquals("Got To Main Page", "Test.Security.Test1.Page1", resp.getTitle());

            WebTable table = resp.getTableWithID("tagTable");
            assertNotNull("Found Table For Checks", table);
            String text = null;

            // Access Function1 - true
            text = table.getCellAsText(0,0);
            assertTrue("Empty Row 1", text != null && text.length() > 0);

            // No Access Function1 - false
            text = table.getCellAsText(1,0);
            assertTrue("Not empty Row 2", text == null || text.length() == 0);

            // Access Function2 - true
            text = table.getCellAsText(2,0);
            assertTrue("Not empty Row 3", text != null && text.length() > 0);

            // No Access Function2 - false
            text = table.getCellAsText(3,0);
            assertTrue("Empty Row 4", text == null || text.length() == 0);

            // Access FunctionX - false
            text = table.getCellAsText(4,0);
            assertTrue("Empty Row 5", text == null || text.length() == 0);

            // No access FunctionX - true
            text = table.getCellAsText(5,0);
            assertTrue("Not empty Row 6", text != null && text.length() > 0);

            // Access Component Test.Security.Test2 - true
            text = table.getCellAsText(6,0);
            assertTrue("Empty Row 7", text != null && text.length() > 0);

            // No Access Component Test.Security.Test2 - false
            text = table.getCellAsText(7,0);
            assertTrue("Not Empty Row 8", text == null || text.length() == 0);

            // Access Component Not.Real.Component - false
            text = table.getCellAsText(8,0);
            assertTrue("Not empty Row 9", text != null && text.length() > 0);

            // No access Component Not.Real.Component - true
            text = table.getCellAsText(9,0);
            assertTrue("Empty Row 10", text == null || text.length() == 0);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Make sure the component manager restricts access to a guarded component
     */
    public void testClerkRunComponent() {
        try {
            // See if they can press button 3 and access the Test.Security.Test2 component
            // via the component manager
            WebConversation wc = new WebConversation();
            WebResponse resp = logOn(wc, "startComponent.do?component=Test.Security.Test1", "GAUTAM", "dummy");
            assertEquals("Got To Main Page", "Test.Security.Test1.Page1", resp.getTitle());

/* @todo Bug Fix this

            // press button 3
            WebForm form = resp.getForms()[0];
            form.setParameter("eventId", "Button3;Clicked");
            WebRequest request = form.getRequest();

            resp = wc.getResponse(request);
            WebTable t = resp.getTableWithID("message");
            String text = t.getCellAsText(0,0);
            assertNotNull("Message should be given", text);
            assertEquals("Expect 'No Access' Message", "No Access To Component", text);
*/

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Make sure the component manager gives access to a guarded component
     */
    public void testManagerClerkRunComponent() {
        try {
            // See if they can press button 3 and access the Test.Security.Test2 component
            // via the component manager
            WebConversation wc = new WebConversation();
            WebResponse resp = logOn(wc, "startComponent.do?component=Test.Security.Test1", "PAUL", "dummy");
            assertEquals("Got To Main Page", "Test.Security.Test1.Page1", resp.getTitle());

/* @todo Bug Fix this

            // press button 3
            WebForm form = resp.getForms()[0];
            WebRequest request = form.getRequest();
            request.setParameter("eventId", "Button3;Clicked");

            resp = wc.getResponse(request);
            WebTable t = resp.getTableWithID("message");
            String text = t.getCellAsText(0,0);
            assertNotNull("Message should be given", text);
            assertTrue("Expect 'No Access' Message", text.startsWith("Running Component :") );
*/

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
