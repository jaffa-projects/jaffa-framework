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
 * HitlistTest.java
 *
 * Created on April 18, 2002, 3:52 PM
 */

package org.jaffa.persistence.blackboxtests;

import junit.framework.TestCase;
import java.util.*;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jaffa.datatypes.DateTime;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.domainobjects.*;

/**
 *
 * @author  GautamJ
 * @version
 */
public class HitlistTest extends TestCase {

    private UOW m_uow = null;

    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(HitlistTest.class));
    }

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public HitlistTest(String name) {
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

    /** This creates 123 Item records. It then retrieves them. After reading each record, the size of the Collection will be checked.
     * The size will increment in steps of hitlist size (which is assumed to be 10). The size will be negative, until all the records have been fetched.
     */
    public void testHitlist() {
        try {
            int rows = 123;
            int hitlistSize = 10;
            String itemIdPrefix = "ZZ-HIT-LIST-";
            DateTime createdDateTime = new DateTime();

            // create items
            for (int i = 1; i <= rows; i++) {
                Item item = (Item) m_uow.newPersistentInstance(Item.class);
                item.updateItemId(itemIdPrefix + i);
                item.updateQty(new Long(i));
                item.updateCreatedDatetime(createdDateTime);
                m_uow.add(item);
            }
            m_uow.commit();


            // now retrieve the items
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setLocking(Criteria.LOCKING_PARANOID);
            c.setTable(ItemMeta.getName());
            c.addCriteria(ItemMeta.ITEM_ID, Criteria.RELATIONAL_BEGINS_WITH, itemIdPrefix);
            c.addOrderBy(ItemMeta.QTY, Criteria.ORDER_BY_ASC);
            Collection items = m_uow.query(c);
            assertEquals(-hitlistSize, items.size());
            int i = 0;
            for (Iterator itr = items.iterator(); itr.hasNext();) {
                ++i;
                Item item = (Item) itr.next();
                assertEquals(itemIdPrefix + i, item.getItemId());
                assertEquals(new Long(i), item.getQty());
                assertEquals(createdDateTime.toString(), item.getCreatedDatetime().toString());

                // hitlist checks
                if (i == rows) {
                    assertEquals(rows, items.size());
                } else if (i % hitlistSize == 0) {
                    assertEquals(-i, items.size());
                } else {
                    int expectedSize = i - (i % hitlistSize) + hitlistSize;
                    if (expectedSize >= rows)
                        assertEquals(rows, items.size());
                    else
                        assertEquals(-expectedSize, items.size());
                }
            }

            // one final check.. to ensure that all the records were retrieved
            assertEquals(rows, items.size());

            // now delete the records
            for (Iterator itr = items.iterator(); itr.hasNext();)
                m_uow.delete((IPersistent) itr.next());
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** This creates 123 Item records. It then retrieves them. After reading each record, the size of the Collection will be checked.
     * The size will increment in steps of hitlist size (which is assumed to be 10). The size will be negative, until all the records have been fetched.
     */
    public void testHitlistUsingProxy() {
        try {
            int rows = 123;
            int hitlistSize = 10;
            String itemIdPrefix = "ZZ-HIT-LIST-";
            DateTime createdDateTime = new DateTime();

            // create items
            for (int i = 1; i <= rows; i++) {
                IItem item = (IItem) m_uow.newPersistentInstance(IItem.class);
                item.setItemId(itemIdPrefix + i);
                item.setQty(new Long(i));
                item.setCreatedDatetime(createdDateTime);
                m_uow.add(item);
            }
            m_uow.commit();


            // now retrieve the items
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setLocking(Criteria.LOCKING_PARANOID);
            c.setTable(IItem.class.getName());
            c.addCriteria(IItem.ITEM_ID, Criteria.RELATIONAL_BEGINS_WITH, itemIdPrefix);
            c.addOrderBy(IItem.QTY, Criteria.ORDER_BY_ASC);
            Collection items = m_uow.query(c);
            assertEquals(-hitlistSize, items.size());
            int i = 0;
            for (Iterator itr = items.iterator(); itr.hasNext();) {
                ++i;
                IItem item = (IItem) itr.next();
                assertEquals(itemIdPrefix + i, item.getItemId());
                assertEquals(new Long(i), item.getQty());
                assertEquals(createdDateTime.toString(), item.getCreatedDatetime().toString());

                // hitlist checks
                if (i == rows) {
                    assertEquals(rows, items.size());
                } else if (i % hitlistSize == 0) {
                    assertEquals(-i, items.size());
                } else {
                    int expectedSize = i - (i % hitlistSize) + hitlistSize;
                    if (expectedSize >= rows)
                        assertEquals(rows, items.size());
                    else
                        assertEquals(-expectedSize, items.size());
                }
            }

            // one final check.. to ensure that all the records were retrieved
            assertEquals(rows, items.size());

            // now delete the records
            for (Iterator itr = items.iterator(); itr.hasNext();)
                m_uow.delete((IPersistent) itr.next());
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
