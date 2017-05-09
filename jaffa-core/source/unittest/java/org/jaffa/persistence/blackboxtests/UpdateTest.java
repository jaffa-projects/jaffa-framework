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
 * UpdateTest.java
 *
 * Created on April 1, 2002, 5:47 PM
 */

package org.jaffa.persistence.blackboxtests;

import junit.framework.TestCase;
import org.jaffa.persistence.domainobjects.*;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.Criteria;
import java.util.Iterator;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jaffa.datatypes.DateTime;
import org.jaffa.persistence.AtomicCriteria;
import org.jaffa.util.StringHelper;

/** Tests for performing updates through the Jaffa Persistence Engine.
 *
 * @author  GautamJ
 */
public class UpdateTest extends TestCase {

    private static StringBuffer buf = new StringBuffer();
    static {
        for (int i = 0; i < 100000; i++)
            buf.append('Z');
    }
    private static final String LONG_FIELD = buf.toString();

    private UOW m_uow = null;

    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(UpdateTest.class));
    }

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public UpdateTest(String name) {
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

    /** Executes the following query:
     * update syci set description='UPDATED DESC', SUPPORT_EQUIP_B='F', CALCULATE_MTBF_B='T' WHERE CATEGORY_INSTRUMENT='Z-TESTCI-01'
     * It then ensures that the record was properly updated.
     */
    public void testUpdateCategoryOfInstrument() {
        try {
            Criteria c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, "Z-TESTCI-01");
            Iterator i = m_uow.query(c).iterator();
            CategoryOfInstrument syci = (CategoryOfInstrument) i.next();
            syci.updateDescription("UPDATED DESC");
            syci.updateSupportEquip(Boolean.FALSE);
            syci.updateCalculateMtbf(Boolean.TRUE);
            m_uow.update(syci);
            m_uow.commit();

            // Now ensure that the record got updated
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, "Z-TESTCI-01");
            i = m_uow.query(c).iterator();
            syci = (CategoryOfInstrument) i.next();
            assertEquals("UPDATED DESC", syci.getDescription());
            assertEquals(Boolean.FALSE, syci.getSupportEquip());
            assertEquals(Boolean.TRUE, syci.getCalculateMtbf());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Updates a CATZ using the query:
     * update catz set REMARKS = some 10,000 character string WHERE PART='Z-TESTPART-01'
     * It then ensures that the record was properly updated.
     */
    public void testUpdateLong() {
        try {
            String longString = LONG_FIELD + '\n' + LONG_FIELD;
            Criteria c = new Criteria();
            c.setTable(PartRemarksMeta.getName());
            c.addCriteria(PartRemarksMeta.PART, "Z-TESTPART-01");
            Iterator i = m_uow.query(c).iterator();
            PartRemarks catz = (PartRemarks) i.next();
            catz.updateRemarks(longString);
            m_uow.update(catz);
            m_uow.commit();


            // Now ensure that the record got updated
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(PartRemarksMeta.getName());
            c.addCriteria(PartRemarksMeta.PART, "Z-TESTPART-01");
            i = m_uow.query(c).iterator();
            catz = (PartRemarks) i.next();
            assertEquals(longString, catz.getRemarks());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Executes the following query:
     * update syci set description='AFTER ROLLBACK' WHERE CATEGORY_INSTRUMENT='Z-TESTCI-01'
     * It then does a rollback. This is followed by a commit.
     * The test fails if the record is updated.
     */
    public void testCheckRollbackAfterUpdate() {
        try {
            Criteria c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, "Z-TESTCI-01");
            Iterator i = m_uow.query(c).iterator();
            CategoryOfInstrument syci = (CategoryOfInstrument) i.next();
            String originalDescription = syci.getDescription();
            syci.updateDescription("AFTER ROLLBACK");
            m_uow.update(syci);
            m_uow.rollback();

            // Now ensure the record isn't updated
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, "Z-TESTCI-01");
            i = m_uow.query(c).iterator();
            syci = (CategoryOfInstrument) i.next();
            assertEquals("Record has been updated even after a rollback", originalDescription, syci.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Executes the following query:
     * update item set created_datetime=to_date('2003-10-10', 'yyyy-MM-dd') where item_id='Z-TESTITEM-01'
     * It then ensures that the record was properly updated and then resets it back to the original value
     */
    public void testUpdateDateTime() {
        try {
            Criteria c = new Criteria();
            c.setTable(ItemMeta.getName());
            c.addCriteria(ItemMeta.ITEM_ID, "Z-TESTITEM-01");
            Iterator i = m_uow.query(c).iterator();
            Item item = (Item) i.next();
            DateTime originalCreatedDatetime = item.getCreatedDatetime();
            DateTime newCreatedDatetime = new DateTime(2003, DateTime.OCTOBER, 10);
            item.updateCreatedDatetime(newCreatedDatetime);
            m_uow.update(item);
            m_uow.commit();

            // Now ensure that the record got updated
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(ItemMeta.getName());
            c.addCriteria(ItemMeta.ITEM_ID, "Z-TESTITEM-01");
            i = m_uow.query(c).iterator();
            item = (Item) i.next();
            assertEquals(newCreatedDatetime, item.getCreatedDatetime());
            
            // Now reset to the original value
            item.updateCreatedDatetime(originalCreatedDatetime);
            m_uow.update(item);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Executes the following query:
     * update item set price=1234567890.12345 where item_id='Z-TESTITEM-01'
     * It then ensures that the record was properly updated and then resets it back to the original value
     */
    public void testUpdateDecimal() {
        try {
            String itemId = "Z-TESTITEM-01";
            Double price = new Double(1234567890.12345);
            
            Item item = Item.findByPK(m_uow, itemId);
            Double originalPrice = item.getPrice();
            assertNull("The Price for Z-TESTITEM-01 should have been null", originalPrice);
            item.setPrice(price);
            m_uow.update(item);
            m_uow.commit();

            // Now ensure that the record got updated
            m_uow = new UOW();
            item = Item.findByPK(m_uow, itemId);
            assertEquals(price, item.getPrice());
            
            // Now reset to the original value
            item.setPrice(originalPrice);
            m_uow.update(item);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Executes the following query:
     * update syci set description='JOHN'S DESC' WHERE CATEGORY_INSTRUMENT='Z-TESTCI-01'
     * It then ensures that the record was properly updated and resets the description to the old value.
     */
    public void testUpdateUsingQuote() {
        try {
            String category = "Z-TESTCI-01";
            String newDesc = "DE'SC WI'TH Q'UOTES'";
            String oldDesc= null;
            
            Criteria c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, category);
            Iterator i = m_uow.query(c).iterator();
            CategoryOfInstrument syci = (CategoryOfInstrument) i.next();
            oldDesc = syci.getDescription();
            syci.updateDescription(newDesc);
            m_uow.update(syci);
            m_uow.commit();

            // Now ensure that the record got updated
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.DESCRIPTION, newDesc);
            i = m_uow.query(c).iterator();
            syci = (CategoryOfInstrument) i.next();
            assertEquals(category, syci.getCategoryInstrument());
            assertEquals(newDesc, syci.getDescription());
            
            // Reset to the original value
            syci.updateDescription(oldDesc);
            m_uow.update(syci);
            m_uow.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }





    /** Executes the following query:
     * update syci set description='UPDATED DESC', SUPPORT_EQUIP_B='F', CALCULATE_MTBF_B='T' WHERE CATEGORY_INSTRUMENT='Z-TESTCI-01'
     * It then ensures that the record was properly updated.
     */
    public void testUpdateCategoryOfInstrumentUsingProxy() {
        try {
            Criteria c = new Criteria();
            c.setTable(ICategoryOfInstrument.class.getName());
            c.addCriteria(ICategoryOfInstrument.CATEGORY_INSTRUMENT, "Z-TESTCI-01");
            Iterator i = m_uow.query(c).iterator();
            ICategoryOfInstrument syci = (ICategoryOfInstrument) i.next();
            syci.setDescription("UPDATED DESC");
            syci.setSupportEquip(Boolean.FALSE);
            syci.setCalculateMtbf(Boolean.TRUE);
            m_uow.update(syci);
            m_uow.commit();

            // Now ensure that the record got updated
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(ICategoryOfInstrument.class.getName());
            c.addCriteria(ICategoryOfInstrument.CATEGORY_INSTRUMENT, "Z-TESTCI-01");
            i = m_uow.query(c).iterator();
            syci = (ICategoryOfInstrument) i.next();
            assertEquals("UPDATED DESC", syci.getDescription());
            assertEquals(Boolean.FALSE, syci.getSupportEquip());
            assertEquals(Boolean.TRUE, syci.getCalculateMtbf());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Updates a CATZ using the query:
     * update catz set REMARKS = some 10,000 character string WHERE PART='Z-TESTPART-01'
     * It then ensures that the record was properly updated.
     */
    public void testUpdateLongUsingProxy() {
        try {
            String longString = LONG_FIELD + '\n' + LONG_FIELD;
            Criteria c = new Criteria();
            c.setTable(IPartRemarks.class.getName());
            c.addCriteria(IPartRemarks.PART, "Z-TESTPART-01");
            Iterator i = m_uow.query(c).iterator();
            IPartRemarks catz = (IPartRemarks) i.next();
            catz.setRemarks(longString);
            m_uow.update(catz);
            m_uow.commit();


            // Now ensure that the record got updated
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(IPartRemarks.class.getName());
            c.addCriteria(IPartRemarks.PART, "Z-TESTPART-01");
            i = m_uow.query(c).iterator();
            catz = (IPartRemarks) i.next();
            assertEquals(longString, catz.getRemarks());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Executes the following query:
     * update syci set description='AFTER ROLLBACK' WHERE CATEGORY_INSTRUMENT='Z-TESTCI-01'
     * It then does a rollback. This is followed by a commit.
     * The test fails if the record is updated.
     */
    public void testCheckRollbackAfterUpdateUsingProxy() {
        try {
            Criteria c = new Criteria();
            c.setTable(ICategoryOfInstrument.class.getName());
            c.addCriteria(ICategoryOfInstrument.CATEGORY_INSTRUMENT, "Z-TESTCI-01");
            Iterator i = m_uow.query(c).iterator();
            ICategoryOfInstrument syci = (ICategoryOfInstrument) i.next();
            String originalDescription = syci.getDescription();
            syci.setDescription("AFTER ROLLBACK");
            m_uow.update(syci);
            m_uow.rollback();

            // Now ensure the record isn't updated
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(ICategoryOfInstrument.class.getName());
            c.addCriteria(ICategoryOfInstrument.CATEGORY_INSTRUMENT, "Z-TESTCI-01");
            i = m_uow.query(c).iterator();
            syci = (ICategoryOfInstrument) i.next();
            assertEquals("Record has been updated even after a rollback", originalDescription, syci.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Executes the following query:
     * update item set created_datetime=to_date('2003-10-10', 'yyyy-MM-dd') where item_id='Z-TESTITEM-01'
     * It then ensures that the record was properly updated and then resets it back to the original value
     */
    public void testUpdateDateTimeUsingProxy() {
        try {
            Criteria c = new Criteria();
            c.setTable(IItem.class.getName());
            c.addCriteria(IItem.ITEM_ID, "Z-TESTITEM-01");
            Iterator i = m_uow.query(c).iterator();
            IItem item = (IItem) i.next();
            DateTime originalCreatedDatetime = item.getCreatedDatetime();
            DateTime newCreatedDatetime = new DateTime(2003, DateTime.OCTOBER, 10);
            item.setCreatedDatetime(newCreatedDatetime);
            m_uow.update(item);
            m_uow.commit();

            // Now ensure that the record got updated
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(IItem.class.getName());
            c.addCriteria(IItem.ITEM_ID, "Z-TESTITEM-01");
            i = m_uow.query(c).iterator();
            item = (IItem) i.next();
            assertEquals(newCreatedDatetime, item.getCreatedDatetime());
            
            // Now reset to the original value
            item.setCreatedDatetime(originalCreatedDatetime);
            m_uow.update(item);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Executes the following query:
     * update item set price=1234567890.12345 where item_id='Z-TESTITEM-01'
     * It then ensures that the record was properly updated and then resets it back to the original value
     */
    public void testUpdateDecimalUsingProxy() {
        try {
            String itemId = "Z-TESTITEM-01";
            Double price = new Double(1234567890.12345);
            
            Criteria c = new Criteria();
            c.setTable(IItem.class.getName());
            c.addCriteria(IItem.ITEM_ID, itemId);
            Iterator i = m_uow.query(c).iterator();
            IItem item = (IItem) i.next();
            Double originalPrice = item.getPrice();
            assertNull("The Price for Z-TESTITEM-01 should have been null", originalPrice);
            item.setPrice(price);
            m_uow.update(item);
            m_uow.commit();

            // Now ensure that the record got updated
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(IItem.class.getName());
            c.addCriteria(IItem.ITEM_ID, itemId);
            i = m_uow.query(c).iterator();
            item = (IItem) i.next();
            assertEquals(price, item.getPrice());
            
            // Now reset to the original value
            item.setPrice(originalPrice);
            m_uow.update(item);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Executes the following query:
     * update syci set description='JOHN'S DESC' WHERE CATEGORY_INSTRUMENT='Z-TESTCI-01'
     * It then ensures that the record was properly updated and resets the description to the old value.
     */
    public void testUpdateUsingQuoteUsingProxy() {
        try {
            String category = "Z-TESTCI-01";
            String newDesc = "DE'SC WI'TH Q'UOTES'";
            String oldDesc= null;
            
            Criteria c = new Criteria();
            c.setTable(ICategoryOfInstrument.class.getName());
            c.addCriteria(ICategoryOfInstrument.CATEGORY_INSTRUMENT, category);
            Iterator i = m_uow.query(c).iterator();
            ICategoryOfInstrument syci = (ICategoryOfInstrument) i.next();
            oldDesc = syci.getDescription();
            syci.setDescription(newDesc);
            m_uow.update(syci);
            m_uow.commit();

            // Now ensure that the record got updated
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(ICategoryOfInstrument.class.getName());
            c.addCriteria(ICategoryOfInstrument.DESCRIPTION, newDesc);
            i = m_uow.query(c).iterator();
            syci = (ICategoryOfInstrument) i.next();
            assertEquals(category, syci.getCategoryInstrument());
            assertEquals(newDesc, syci.getDescription());
            
            // Reset to the original value
            syci.setDescription(oldDesc);
            m_uow.update(syci);
            m_uow.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


}
