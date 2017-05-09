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
 * StoredProcedureTest.java
 *
 * Created on April 1, 2002, 5:47 PM
 */

package org.jaffa.persistence.blackboxtests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jaffa.persistence.domainobjects.*;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.Criteria;

/**
 *
 * @author GautamJ
 */
public class StoredProcedureTest extends TestCase {
    private UOW m_uow = null;
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(StoredProcedureTest.class));
    }
    
    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public StoredProcedureTest(String name) {
        super(name);
    }
    
    /** Sets up the fixture, by creating the UOW. This method is called before a test is executed.
     */
    protected void setUp() {
        try {
            m_uow = new UOW();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to create a UOW: " + e.toString());
        }
    }
    
    /** Tears down the fixture, by closing the UOW. This method is called after a test is executed.
     */
    protected void tearDown() {
        try {
            if (m_uow != null)
                m_uow.rollback();
            m_uow = null;
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to rollback a UOW: " + e.toString());
        }
    }
    
    /** This just executes the Voucher Stored Procedure. If a voucher gets generated, then everything went thru well.
     * It then performs a rollback.
     */
    public void testVoucherStoredProcedure() {
        try {
            // create a StoredProcedure Object
            VoucherStoredProcedure obj = new VoucherStoredProcedure();
            obj.updatePrefix("V");
            obj.updateLength(new Long(15));
            
            // create the criteria
            Criteria c = new Criteria();
            c.setTable(obj.getClass().getName());
            c.addCriteria(null, obj);
            m_uow.query(c);
            
            // check if a voucher was generated
            assertNotNull(obj.getVoucher());
            m_uow.rollback();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** This just executes the Items Stored Procedure. Will obtain the Item records for records having itemId that begin with the passed value.
     * It then performs a rollback.
     */
    public void testStoredProcedureReturningObjects() {
        try {
            // create a StoredProcedure Object and set the input parameter
            ItemStoredProcedure obj = new ItemStoredProcedure();
            obj.setItemId("Z-TESTITEM-0");
            
            // create the criteria
            Criteria c = new Criteria();
            c.setTable(obj.getClass().getName());
            c.addCriteria(null, obj);
            m_uow.query(c);
            
            // check if items were returned
            assertNotNull("Items should have been returned by the Stored Procedure", obj.getItems());
            assertEquals("3 items should have been returned by the Stored Procedure", 4, obj.getItems().length);
            assertEquals("Z-TESTITEM-01", obj.getItems()[0].getItemId());
            assertEquals("Z-TESTITEM-02", obj.getItems()[1].getItemId());
            assertEquals("Z-TESTITEM-03", obj.getItems()[2].getItemId());
            m_uow.rollback();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    
}
