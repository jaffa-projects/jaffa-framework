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
 * Created on April 1, 2002, 5:47 PM
 */

package datasecurity;

import junit.framework.TestCase;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebTable;
import com.meterware.httpunit.WebForm;



/**
 *
 * @author Maheshd
 */
public class JDBCPluginTest extends TestCase {

    private static final String WEB_ROOT = "http://localhost:8080/httpunittest/";

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public JDBCPluginTest(String name) {
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


    public void testPolicy1() {
        try {
            WebConversation wc = new WebConversation();
            WebResponse resp = logOn(wc, "startComponent.do?component=Material.ItemFinder&displayResultsScreen=true", "MAHESH", "dummy");


            WebTable table = resp.getTableWithID("material_itemFinderResultsForm_rows");
            assertNotNull("Found Table For Checks", table);
            String text = null;

            text = table.getCellAsText(1,2);
            assertTrue("Serial is not D", text.equals("A"));
            text = table.getCellAsText(2,2);
            assertTrue("Serial is not  E", text.equals("B") );
            text = table.getCellAsText(3,2);
            assertTrue("Serial is not D", text.equals("C"));
            text = table.getCellAsText(4,2);
            assertTrue("Serial is not  E", text.equals("D") );
            text = table.getCellAsText(5,2);
            assertTrue("Serial is not  E", text.equals("E") );

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testPolicy2() {
        try {
            WebConversation wc = new WebConversation();
            WebResponse resp = logOn(wc, "startComponent.do?component=Material.ItemFinder&displayResultsScreen=true", "PAUL", "dummy");

            WebTable table = resp.getTableWithID("material_itemFinderResultsForm_rows");
            assertNotNull("Found Table For Checks", table);
            String text = null;


            text = table.getCellAsText(1,2);
            assertTrue("Serial is not A",text.equals("A") );

            text = table.getCellAsText(2,2);
            assertTrue( "Serial is not B", text.equals("B"));


        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
