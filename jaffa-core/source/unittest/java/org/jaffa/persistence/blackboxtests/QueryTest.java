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
 * QueryTest.java
 *
 * Created on April 1, 2002, 5:47 PM
 */

package org.jaffa.persistence.blackboxtests;

import junit.framework.TestCase;
import org.jaffa.persistence.domainobjects.*;
import org.jaffa.persistence.*;
import java.util.*;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jaffa.datatypes.DateTime;

/** Tests for performing queries through the Jaffa Persistence Engine.
 *
 * @author GautamJ
 */
public class QueryTest extends TestCase {

    private UOW m_uow = null;

    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(QueryTest.class));
    }

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public QueryTest(String name) {
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

    /** Tests the sql:
     * Select * from item where item_id like 'Z-TEST%'
     * and condition like '%TESTSYCD%'
     * and part = 'Z-TESTPART-01�
     * and sc is not null
     * and qty > 1 and qty < 3
     * and key_ref like '%TEST-KEY-REF'
     * order by item_id
     * This should return 3 item records
     */
    public void testBasicQuery() {
        try {
            Criteria c = new Criteria();
            c.setTable(ItemMeta.getName());
            c.addCriteria(ItemMeta.ITEM_ID, Criteria.RELATIONAL_BEGINS_WITH, "Z-TEST");
            c.addCriteria(ItemMeta.CONDITION, Criteria.RELATIONAL_LIKE, "TESTSYCD");
            c.addCriteria(ItemMeta.PART, "Z-TESTPART-01");
            c.addCriteria(ItemMeta.SC, Criteria.RELATIONAL_IS_NOT_NULL);
            c.addCriteria(ItemMeta.QTY, Criteria.RELATIONAL_GREATER_THAN, new Long(1));
            c.addCriteria(ItemMeta.QTY, Criteria.RELATIONAL_SMALLER_THAN, new Long(3));
            c.addCriteria(ItemMeta.KEY_REF, Criteria.RELATIONAL_ENDS_WITH, "TEST-KEY-REF");
            c.addOrderBy(ItemMeta.ITEM_ID, Criteria.ORDER_BY_ASC);

            // The following criteria points to an undefined mapping, and should be ignored and not cause any exception
            c.addCriteria("UnmappedField", "ZZZZ");

            Iterator i = m_uow.query(c).iterator();
            Item item = null;
            item = ((Item) i.next());
            assertEquals( "Z-TESTITEM-01", item.getItemId() );
            assertEquals( "SOME SC", item.getSc() );
            assertEquals( "Z-TESTPART-01", item.getPart() );
            assertEquals( "Z-TESTPRIME-01", item.getPrime() );
            assertEquals( "Z-TESTSYCD-01", item.getCondition() );
            assertEquals( 2, item.getQty().intValue() );
            assertEquals(new DateTime(2003, DateTime.SEPTEMBER, 10, 20, 30, 40, 0), item.getCreatedDatetime());

            item = ((Item) i.next());
            assertEquals( "Z-TESTITEM-02", item.getItemId() );
            assertEquals( "SOME SC", item.getSc() );
            assertEquals( "Z-TESTPART-01", item.getPart() );
            assertEquals( "Z-TESTPRIME-01", item.getPrime() );
            assertEquals( "Z-TESTSYCD-01", item.getCondition() );
            assertEquals( 2, item.getQty().intValue() );
            assertEquals(new DateTime(2003, DateTime.SEPTEMBER, 10), item.getCreatedDatetime());

            item = ((Item) i.next());
            assertEquals( "Z-TESTITEM-03", item.getItemId() );
            assertEquals( "SOME SC", item.getSc() );
            assertEquals( "Z-TESTPART-01", item.getPart() );
            assertEquals( "Z-TESTPRIME-01", item.getPrime() );
            assertEquals( "Z-TESTSYCD-01", item.getCondition() );
            assertEquals( 2, item.getQty().intValue() );
            assertNull(item.getCreatedDatetime());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Tests the sql:
     * Select * from item where item_id = received_item_id
     * and status_2 != status_3
     * and item_id >= 'Z-TESTITEM-01'
     * and item_id <= 'Z-TESTITEM-02'
     * order by item_id desc
     * This should return 2 item records
     */
    public void testDualFieldQuery() {
        try {
            Criteria c = new Criteria();
            c.setTable(ItemMeta.getName());
            c.addDualCriteria(ItemMeta.ITEM_ID, ItemMeta.RECEIVED_ITEM_ID);
            c.addDualCriteria(ItemMeta.STATUS2, Criteria.RELATIONAL_NOT_EQUALS, ItemMeta.STATUS3);
            c.addCriteria(ItemMeta.ITEM_ID, Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO, "Z-TESTITEM-01");
            c.addCriteria(ItemMeta.ITEM_ID, Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO, "Z-TESTITEM-02");
            c.addOrderBy(ItemMeta.ITEM_ID, Criteria.ORDER_BY_DESC);

            // The following criteria points to an undefined mapping, and should be ignored and not cause any exception
            c.addDualCriteria("UnmappedField1", Criteria.RELATIONAL_NOT_EQUALS, "UnmappedField2");

            Iterator i = m_uow.query(c).iterator();
            assertEquals( "Z-TESTITEM-02", ((Item) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-01", ((Item) i.next()).getItemId() );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from item Where
     * ( (status_1 is null or status_2 is null or status_3 = 'A') and condition like 'Z-TESTSYCD%' )
     * or (item_id like 'Z-TESTITEM%')
     * order by item_id
     * This should return 3 item records
     */
    public void testOrAndQuery() {
        try {
            AtomicCriteria ac1 = new AtomicCriteria();
            ac1.addCriteria(ItemMeta.STATUS1, Criteria.RELATIONAL_IS_NULL);
            ac1.addOrCriteria(ItemMeta.STATUS2, Criteria.RELATIONAL_IS_NULL);
            ac1.addOrCriteria(ItemMeta.STATUS3, "A");

            AtomicCriteria ac2 = new AtomicCriteria();
            ac2.addAtomic(ac1);
            ac2.addCriteria(ItemMeta.CONDITION, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTSYCD");

            // The following criteria points to an undefined mapping, and should be ignored and not cause any exception
            ac1.addOrCriteria("UnmappedField2", "ZZZZ");
            ac2.addCriteria("UnmappedField2", "ZZZZ");

            Criteria c = new Criteria();
            c.setTable(ItemMeta.getName());
            c.addAtomic(ac2);
            c.addOrCriteria(ItemMeta.ITEM_ID, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTITEM");
            c.addOrderBy(ItemMeta.ITEM_ID, Criteria.ORDER_BY_ASC);

            Iterator i = m_uow.query(c).iterator();
            assertEquals( "Z-TESTITEM-01", ((Item) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-02", ((Item) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-03", ((Item) i.next()).getItemId() );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from item Where exists
     * (select 1 from cat1 Where cat1.part = item.part and cat1.noun = 'Z-TESTNOUN%')
     * order by item_id
     * This should return 3 item records
     */
    public void testInnerJoinQuery() {
        try {

            Criteria c1 = new Criteria();
            c1.setTable(PartMeta.getName());
            c1.addInnerCriteria(PartMeta.PART, ItemMeta.PART);
            c1.addCriteria(PartMeta.NOUN, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTNOUN");

            // The following criteria points to an undefined mapping, and should be ignored and not cause any exception
            c1.addInnerCriteria("UnmappedField1", "UnmappedField2");
            c1.addCriteria("UnmappedField1", "ZZZZ");

            Criteria c2 = new Criteria();
            c2.setTable(ItemMeta.getName());
            c2.addAggregate(c1);
            c2.addOrderBy(ItemMeta.ITEM_ID, Criteria.ORDER_BY_ASC);

            Iterator i = m_uow.query(c2).iterator();
            assertEquals( "Z-TESTITEM-01", ((Item) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-02", ((Item) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-03", ((Item) i.next()).getItemId() );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Tests the sql:
     * Select * from item Where condition = 'Z-TESTSYCD-01'
     * and exists (select 1 from cat1 Where cat1.part = item.part
     * and exists(select 1 from syci where syci.category_instrument = cat1.category_instrument
     * and category_instrument like 'Z%' and support_equip_b='T') )
     * order by item_id
     * This should return 3 item records
     */
    public void testNestedInnerJoinQuery() {
        try {

            Criteria c0 = new Criteria();
            c0.setTable(CategoryOfInstrumentMeta.getName());
            c0.addInnerCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, PartMeta.CATEGORY_INSTRUMENT);
            c0.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, Criteria.RELATIONAL_BEGINS_WITH, "Z");
            c0.addCriteria(CategoryOfInstrumentMeta.SUPPORT_EQUIP, Boolean.TRUE);

            Criteria c1 = new Criteria();
            c1.setTable(PartMeta.getName());
            c1.addAggregate(c0);
            c1.addInnerCriteria(PartMeta.PART, ItemMeta.PART);

            Criteria c2 = new Criteria();
            c2.setTable(ItemMeta.getName());
            c2.addCriteria(ItemMeta.CONDITION, "Z-TESTSYCD-01");
            c2.addAggregate(c1);
            c2.addOrderBy(ItemMeta.ITEM_ID, Criteria.ORDER_BY_ASC);

            Iterator i = m_uow.query(c2).iterator();
            assertEquals( "Z-TESTITEM-01", ((Item) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-02", ((Item) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-03", ((Item) i.next()).getItemId() );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Tests the sql:
     * Select * from item
     * and exists (select 1 from cat1 Where cat1.part = item.part and cat1.noun = 'Z-TESTNOUN%')
     * and exists (select 1 from sycd where sycd.condition = item.condition and description like 'Z%')
     * order by item_id
     * This should return 3 item records
     */
    public void testTwoInnerJoinsQuery() {
        try {

            Criteria c1 = new Criteria();
            c1.setTable(PartMeta.getName());
            c1.addInnerCriteria(PartMeta.PART, ItemMeta.PART);
            c1.addCriteria(PartMeta.NOUN, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTNOUN");

            Criteria c2 = new Criteria();
            c2.setTable(ConditionMeta.getName());
            c2.addInnerCriteria(ConditionMeta.CONDITION, ItemMeta.CONDITION);
            c2.addCriteria(ConditionMeta.DESCRIPTION, Criteria.RELATIONAL_BEGINS_WITH, "Z");

            Criteria c = new Criteria();
            c.setTable(ItemMeta.getName());
            c.addAggregate(c1);
            c.addAggregate(c2);
            c.addOrderBy(ItemMeta.ITEM_ID, Criteria.ORDER_BY_ASC);

            Iterator i = m_uow.query(c).iterator();
            assertEquals( "Z-TESTITEM-01", ((Item) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-02", ((Item) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-03", ((Item) i.next()).getItemId() );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from item Where exists
     * (select 1 from cat1 Where ((item.SC='Z-TEST1SC%' or item.condition='Z-TESTSYCD%') and cat1.part=item.part)
     * order by item_id
     * This should return 3 item records
     */
	public void testAtomicInnerJoinQuery(){
	    try{
	        Criteria c1 = new Criteria();
	        c1.setTable(PartMeta.getName());
	        c1.addInnerCriteria(PartMeta.PART, ItemMeta.PART);

			AtomicCriteria ac1 = new AtomicCriteria();
			ac1.addCriteria(ItemMeta.SC, Criteria.RELATIONAL_BEGINS_WITH, "Z-TEST1SC");
			ac1.addOrCriteria(ItemMeta.CONDITION, Criteria.RELATIONAL_BEGINS_WITH, "ZX");

            AtomicCriteria ac2 = new AtomicCriteria();
            ac2.addAtomic(ac1);
            ac2.addAggregate(c1);

			Criteria c = new Criteria();
			c.setTable(ItemMeta.getName());
			c.addAtomic(ac2);

            Iterator i = m_uow.query(c).iterator();
            assertEquals( "Z-TESTITEM-09", ((Item) i.next()).getItemId() );

	    }catch (Exception e){
	        e.printStackTrace();
	        fail();
	    }
	}


    /** Tests the sql:
     * Select * from item Where exists
     * (select 1 from cat1 Where ((item.part like Z-TESTPART-04% or items.sc like 'Z-TEST1SC%') and parts.part = items.part and parts.noun like 'Z-TESTNOUN%' and
     * (select 1 from conditions Where (parts.noun like 'ZCC1%' and condition.description=parts.categoryofinstrument and conditions.condition like 'Z%')))
     * order by item_id
     * This should return 1 item records
     */
	public void testNestedAtomicInnerJoinQuery(){
	    try{
            Criteria c2 = new Criteria();
            c2.setTable(ConditionMeta.getName());
            c2.addInnerCriteria(ConditionMeta.DESCRIPTION, PartMeta.CATEGORY_INSTRUMENT);
            c2.addCriteria(ConditionMeta.CONDITION, Criteria.RELATIONAL_BEGINS_WITH, "Z");

            AtomicCriteria ac3 = new AtomicCriteria();
            ac3.addCriteria(PartMeta.NOUN, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTNOUN-03");
            ac3.addAggregate(c2);

            Criteria c1 = new Criteria();
            c1.setTable(PartMeta.getName());
            c1.addInnerCriteria(PartMeta.PART, ItemMeta.PART);
            c1.addCriteria(PartMeta.NOUN, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTNOUN");
            c1.addAtomic(ac3);

            AtomicCriteria ac2 = new AtomicCriteria();
            ac2.addCriteria(ItemMeta.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTPART-04");
            ac2.addOrCriteria(ItemMeta.SC, Criteria.RELATIONAL_BEGINS_WITH, "Z-TEST1SC");
            AtomicCriteria ac4 = new AtomicCriteria();
            ac4.addAtomic(ac2);
            ac4.addAggregate(c1);

            Criteria c = new Criteria();
            c.setTable(ItemMeta.getName());
            c.addAtomic(ac4);
            c.addOrderBy(ItemMeta.ITEM_ID, Criteria.ORDER_BY_ASC);


            Iterator i = m_uow.query(c).iterator();
            assertEquals( "Z-TESTITEM-09", ((Item) i.next()).getItemId() );

	    }catch (Exception e){
	        e.printStackTrace();
	        fail();
	    }
	}

    /** Tests the sql:
     * Select * from items where
     * exists (select 1 from parts Where (items.part like 'Z-TESTPART-04%' or items.sc like 'Z-TEST1SC%') and parts.part = items.part and parts.noun like 'Z-TESTNOUN'
     * or exists (select 1 from conditions where (items.part like 'Z-TESTPART-05%' or items.prime='ZCC1%') and conditions.condition = items.condition))
     * order by item_id
     * This should return 1 item records
     */
    public void testTwoAtomicInnerJoinQuery() {
        try {

            Criteria c2 = new Criteria();
            c2.setTable(ConditionMeta.getName());
            c2.addInnerCriteria(ConditionMeta.CONDITION, ItemMeta.CONDITION);

            AtomicCriteria ac1 = new AtomicCriteria();
            ac1.addCriteria(ItemMeta.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTPART-05");
            ac1.addOrCriteria(ItemMeta.PRIME, Criteria.RELATIONAL_BEGINS_WITH, "ZCC1");
            AtomicCriteria ac3 = new AtomicCriteria();
            ac3.addAtomic(ac1);
            ac3.addAggregate(c2);

            AtomicCriteria mac1 = new AtomicCriteria();
            mac1.addAtomic(ac3);

            Criteria c1 = new Criteria();
            c1.setTable(PartMeta.getName());
            c1.addInnerCriteria(PartMeta.PART, ItemMeta.PART);
            c1.addCriteria(PartMeta.NOUN, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTNOUN");
            c1.addAtomic(ac1);

            AtomicCriteria ac2 = new AtomicCriteria();
            ac2.addCriteria(ItemMeta.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTPART-04");
            ac2.addOrCriteria(ItemMeta.SC, Criteria.RELATIONAL_BEGINS_WITH, "Z-TEST1SC");
            AtomicCriteria ac4 = new AtomicCriteria();
            ac4.addAtomic(ac2);
            ac4.addAggregate(c1);

            AtomicCriteria mac2 = new AtomicCriteria();
            mac2.addAtomic(ac4);

            AtomicCriteria ac = new AtomicCriteria();
            ac.addAtomic(mac2);
            ac.addOrAtomic(mac1);

            Criteria c = new Criteria();
            c.setTable(ItemMeta.getName());
            c.addAtomic(ac);
            c.addOrderBy(ItemMeta.ITEM_ID, Criteria.ORDER_BY_ASC);

            Iterator i = m_uow.query(c).iterator();
            assertEquals( "Z-TESTITEM-10", ((Item) i.next()).getItemId() );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from catz where part='Z-TESTPART-01'
     * This should return 1 catz record
     */
    public void testLongQuery() {
        try {
            Criteria c = new Criteria();
            c.setTable(PartRemarksMeta.getName());
            c.addCriteria(PartRemarksMeta.PART, "Z-TESTPART-01");
            Iterator i = m_uow.query(c).iterator();
            assertEquals( Wrapper.CATZ_REMARKS, ((PartRemarks) i.next()).getRemarks());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from item Where
     * created_datetime = to_date('2003-09-10 20:30:40', 'yyyy-MM-dd hh24:mi:ss')
     * or created_datetime = to_date('2003-09-10', 'yyyy-MM-dd')
     * or created_datetime is null
     * order by item_id
     * This should return 3 item records
     */
    public void testDateTimeQuery() {
        try {
            Criteria c = new Criteria();
            c.addCriteria(ItemMeta.CREATED_DATETIME, new DateTime(2003, DateTime.SEPTEMBER, 10, 20, 30, 40, 0));
            c.addOrCriteria(ItemMeta.CREATED_DATETIME, new DateTime(2003, DateTime.SEPTEMBER, 10));
            c.addOrCriteria(ItemMeta.CREATED_DATETIME, Criteria.RELATIONAL_IS_NULL);
            c.setTable(ItemMeta.getName());
            c.addOrderBy(ItemMeta.ITEM_ID, Criteria.ORDER_BY_ASC);

            Iterator i = m_uow.query(c).iterator();
            Item item = (Item) i.next();
            assertEquals( "Z-TESTITEM-01", item.getItemId() );
            assertEquals(new DateTime(2003, DateTime.SEPTEMBER, 10, 20, 30, 40, 0), item.getCreatedDatetime());

            item = ((Item) i.next());
            assertEquals( "Z-TESTITEM-02", item.getItemId() );
            assertEquals(new DateTime(2003, DateTime.SEPTEMBER, 10), item.getCreatedDatetime());

            item = ((Item) i.next());
            assertNull(item.getCreatedDatetime());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Creates 2 ZZ_JUT_INSTRUMENT records with the prefix 'Z-|%_'
     * It then Tests the sql:
     * Select * from ZZ_JUT_INSTRUMENT where CategoryInstrument like 'Z-|||%|_%' escape '|'
     * This should return 2 ZZ_JUT_INSTRUMENT records.
     * The records are then deleted.
     */
    public void testQueryWithEscapeCharacters() {
        try {
            // create a couple of CategoryOfInstrument with CategoryInstrument having the prefix 'Z-TESTCI-|%_'
            String prefix = "Z-|%_";

            CategoryOfInstrument coi1 = (CategoryOfInstrument) m_uow.newPersistentInstance(CategoryOfInstrument.class);
            coi1.updateCategoryInstrument(prefix + '1');
            CategoryOfInstrument coi2 = (CategoryOfInstrument) m_uow.newPersistentInstance(CategoryOfInstrument.class);
            coi2.updateCategoryInstrument(prefix + '2');
            m_uow.add(coi1);
            m_uow.add(coi2);
            m_uow.commit();

            // now retrieve them
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, Criteria.RELATIONAL_BEGINS_WITH, prefix);
            Collection col = m_uow.query(c);
            assertEquals(2, col.size());

            // now delete the records
            for (Iterator itr = col.iterator(); itr.hasNext();)
                m_uow.delete((IPersistent) itr.next());
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from condition where condition not like 'Z%', which should return 0 records
     * Select * from condition where condition not like 'A%', which should return 3 records
     */
    public void testNotBeginsWithQuery() {
        try {
            Criteria c = new Criteria();
            c.setTable(ConditionMeta.getName());
            c.addCriteria(ConditionMeta.CONDITION, Criteria.RELATIONAL_NOT_BEGINS_WITH, "Z");
            c.addOrderBy(ConditionMeta.CONDITION);
            Condition condition = null;
            Iterator i = m_uow.query(c).iterator();
            assertTrue("No record should have been retrieved", !i.hasNext());

            c = new Criteria();
            c.setTable(ConditionMeta.getName());
            c.addCriteria(ConditionMeta.CONDITION, Criteria.RELATIONAL_NOT_BEGINS_WITH, "A");
            c.addOrderBy(ConditionMeta.CONDITION);
            i = m_uow.query(c).iterator();
            condition = (Condition) i.next();
            assertEquals("Z-TESTSYCD-01", condition.getCondition());
            condition = (Condition) i.next();
            assertEquals("Z-TESTSYCD-02", condition.getCondition());
            condition = (Condition) i.next();
            assertEquals("Z-TESTSYCD-03", condition.getCondition());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from condition where condition not like '%01'
     * This should return 2 records
     */
    public void testNotEndsWithQuery() {
        try {
            Criteria c = new Criteria();
            c.setTable(ConditionMeta.getName());
            c.addCriteria(ConditionMeta.CONDITION, Criteria.RELATIONAL_NOT_ENDS_WITH, "01");
            c.addOrderBy(ConditionMeta.CONDITION);
            Condition condition = null;
            Iterator i = m_uow.query(c).iterator();
            condition = (Condition) i.next();
            assertEquals("Z-TESTSYCD-02", condition.getCondition());
            condition = (Condition) i.next();
            assertEquals("Z-TESTSYCD-03", condition.getCondition());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from condition where condition not like '%TEST%', which should return 0 records
     * Select * from condition where condition not like '%A%', which should return 3 records
     */
    public void testNotLikeWithQuery() {
        try {
            Criteria c = new Criteria();
            c.setTable(ConditionMeta.getName());
            c.addCriteria(ConditionMeta.CONDITION, Criteria.RELATIONAL_NOT_LIKE, "TEST");
            c.addOrderBy(ConditionMeta.CONDITION);
            Condition condition = null;
            Iterator i = m_uow.query(c).iterator();
            assertTrue("No record should have been retrieved", !i.hasNext());

            c = new Criteria();
            c.setTable(ConditionMeta.getName());
            c.addCriteria(ConditionMeta.CONDITION, Criteria.RELATIONAL_NOT_LIKE, "A");
            c.addOrderBy(ConditionMeta.CONDITION);
            i = m_uow.query(c).iterator();
            condition = (Condition) i.next();
            assertEquals("Z-TESTSYCD-01", condition.getCondition());
            condition = (Condition) i.next();
            assertEquals("Z-TESTSYCD-02", condition.getCondition());
            condition = (Condition) i.next();
            assertEquals("Z-TESTSYCD-03", condition.getCondition());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }



    /** Tests the sql:
     * Select * from item where item_id like 'Z-TEST%'
     * and condition like '%TESTSYCD%'
     * and part = 'Z-TESTPART-01�
     * and sc is not null
     * and qty > 1 and qty < 3
     * and key_ref like '%TEST-KEY-REF'
     * order by item_id
     * This should return 3 item records
     */
    public void testBasicQueryUsingProxy() {
        try {
            Criteria c = new Criteria();
            c.setTable(IItem.class.getName());
            c.addCriteria(IItem.ITEM_ID, Criteria.RELATIONAL_BEGINS_WITH, "Z-TEST");
            c.addCriteria(IItem.CONDITION, Criteria.RELATIONAL_LIKE, "TESTSYCD");
            c.addCriteria(IItem.PART, "Z-TESTPART-01");
            c.addCriteria(IItem.SC, Criteria.RELATIONAL_IS_NOT_NULL);
            c.addCriteria(IItem.QTY, Criteria.RELATIONAL_GREATER_THAN, new Long(1));
            c.addCriteria(IItem.QTY, Criteria.RELATIONAL_SMALLER_THAN, new Long(3));
            c.addCriteria(IItem.KEY_REF, Criteria.RELATIONAL_ENDS_WITH, "TEST-KEY-REF");
            c.addOrderBy(IItem.ITEM_ID, Criteria.ORDER_BY_ASC);

            // The following criteria points to an undefined mapping, and should be ignored and not cause any exception
            c.addCriteria("UnmappedField", "ZZZZ");

            Iterator i = m_uow.query(c).iterator();
            IItem item = null;
            item = ((IItem) i.next());
            assertEquals( "Z-TESTITEM-01", item.getItemId() );
            assertEquals( "SOME SC", item.getSc() );
            assertEquals( "Z-TESTPART-01", item.getPart() );
            assertEquals( "Z-TESTPRIME-01", item.getPrime() );
            assertEquals( "Z-TESTSYCD-01", item.getCondition() );
            assertEquals( 2, item.getQty().intValue() );
            assertEquals(new DateTime(2003, DateTime.SEPTEMBER, 10, 20, 30, 40, 0), item.getCreatedDatetime());

            item = ((IItem) i.next());
            assertEquals( "Z-TESTITEM-02", item.getItemId() );
            assertEquals( "SOME SC", item.getSc() );
            assertEquals( "Z-TESTPART-01", item.getPart() );
            assertEquals( "Z-TESTPRIME-01", item.getPrime() );
            assertEquals( "Z-TESTSYCD-01", item.getCondition() );
            assertEquals( 2, item.getQty().intValue() );
            assertEquals(new DateTime(2003, DateTime.SEPTEMBER, 10), item.getCreatedDatetime());

            item = ((IItem) i.next());
            assertEquals( "Z-TESTITEM-03", item.getItemId() );
            assertEquals( "SOME SC", item.getSc() );
            assertEquals( "Z-TESTPART-01", item.getPart() );
            assertEquals( "Z-TESTPRIME-01", item.getPrime() );
            assertEquals( "Z-TESTSYCD-01", item.getCondition() );
            assertEquals( 2, item.getQty().intValue() );
            assertNull(item.getCreatedDatetime());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Tests the sql:
     * Select * from item where item_id = received_item_id
     * and status_2 != status_3
     * and item_id >= 'Z-TESTITEM-01'
     * and item_id <= 'Z-TESTITEM-02'
     * order by item_id desc
     * This should return 2 item records
     */
    public void testDualFieldQueryUsingProxy() {
        try {
            Criteria c = new Criteria();
            c.setTable(IItem.class.getName());
            c.addDualCriteria(IItem.ITEM_ID, IItem.RECEIVED_ITEM_ID);
            c.addDualCriteria(IItem.STATUS2, Criteria.RELATIONAL_NOT_EQUALS, IItem.STATUS3);
            c.addCriteria(IItem.ITEM_ID, Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO, "Z-TESTITEM-01");
            c.addCriteria(IItem.ITEM_ID, Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO, "Z-TESTITEM-02");
            c.addOrderBy(IItem.ITEM_ID, Criteria.ORDER_BY_DESC);

            // The following criteria points to an undefined mapping, and should be ignored and not cause any exception
            c.addDualCriteria("UnmappedField1", Criteria.RELATIONAL_NOT_EQUALS, "UnmappedField2");

            Iterator i = m_uow.query(c).iterator();
            assertEquals( "Z-TESTITEM-02", ((IItem) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-01", ((IItem) i.next()).getItemId() );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from item Where
     * ( (status_1 is null or status_2 is null or status_3 = 'A') and condition like 'Z-TESTSYCD%' )
     * or (item_id like 'Z-TESTITEM%')
     * order by item_id
     * This should return 3 item records
     */
    public void testOrAndQueryUsingProxy() {
        try {
            AtomicCriteria ac1 = new AtomicCriteria();
            ac1.addCriteria(IItem.STATUS1, Criteria.RELATIONAL_IS_NULL);
            ac1.addOrCriteria(IItem.STATUS2, Criteria.RELATIONAL_IS_NULL);
            ac1.addOrCriteria(IItem.STATUS3, "A");

            AtomicCriteria ac2 = new AtomicCriteria();
            ac2.addAtomic(ac1);
            ac2.addCriteria(IItem.CONDITION, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTSYCD");

            // The following criteria points to an undefined mapping, and should be ignored and not cause any exception
            ac1.addOrCriteria("UnmappedField2", "ZZZZ");
            ac2.addCriteria("UnmappedField2", "ZZZZ");

            Criteria c = new Criteria();
            c.setTable(IItem.class.getName());
            c.addAtomic(ac2);
            c.addOrCriteria(IItem.ITEM_ID, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTITEM");
            c.addOrderBy(IItem.ITEM_ID, Criteria.ORDER_BY_ASC);

            Iterator i = m_uow.query(c).iterator();
            assertEquals( "Z-TESTITEM-01", ((IItem) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-02", ((IItem) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-03", ((IItem) i.next()).getItemId() );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from item Where exists
     * (select 1 from cat1 Where cat1.part = item.part and cat1.noun = 'Z-TESTNOUN%')
     * order by item_id
     * This should return 3 item records
     */
    public void testInnerJoinQueryUsingProxy() {
        try {

            Criteria c1 = new Criteria();
            c1.setTable(IPart.class.getName());
            c1.addInnerCriteria(IPart.PART, IItem.PART);
            c1.addCriteria(IPart.NOUN, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTNOUN");

            // The following criteria points to an undefined mapping, and should be ignored and not cause any exception
            c1.addInnerCriteria("UnmappedField1", "UnmappedField2");
            c1.addCriteria("UnmappedField1", "ZZZZ");

            Criteria c2 = new Criteria();
            c2.setTable(IItem.class.getName());
            c2.addAggregate(c1);
            c2.addOrderBy(IItem.ITEM_ID, Criteria.ORDER_BY_ASC);

            Iterator i = m_uow.query(c2).iterator();
            assertEquals( "Z-TESTITEM-01", ((IItem) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-02", ((IItem) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-03", ((IItem) i.next()).getItemId() );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Tests the sql:
     * Select * from item Where condition = 'Z-TESTSYCD-01'
     * and exists (select 1 from cat1 Where cat1.part = item.part
     * and exists(select 1 from syci where syci.category_instrument = cat1.category_instrument
     * and category_instrument like 'Z%' and support_equip_b='T') )
     * order by item_id
     * This should return 3 item records
     */
    public void testNestedInnerJoinQueryUsingProxy() {
        try {

            Criteria c0 = new Criteria();
            c0.setTable(ICategoryOfInstrument.class.getName());
            c0.addInnerCriteria(ICategoryOfInstrument.CATEGORY_INSTRUMENT, IPart.CATEGORY_INSTRUMENT);
            c0.addCriteria(ICategoryOfInstrument.CATEGORY_INSTRUMENT, Criteria.RELATIONAL_BEGINS_WITH, "Z");
            c0.addCriteria(ICategoryOfInstrument.SUPPORT_EQUIP, Boolean.TRUE);

            Criteria c1 = new Criteria();
            c1.setTable(IPart.class.getName());
            c1.addAggregate(c0);
            c1.addInnerCriteria(IPart.PART, IItem.PART);

            Criteria c2 = new Criteria();
            c2.setTable(IItem.class.getName());
            c2.addCriteria(IItem.CONDITION, "Z-TESTSYCD-01");
            c2.addAggregate(c1);
            c2.addOrderBy(IItem.ITEM_ID, Criteria.ORDER_BY_ASC);

            Iterator i = m_uow.query(c2).iterator();
            assertEquals( "Z-TESTITEM-01", ((IItem) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-02", ((IItem) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-03", ((IItem) i.next()).getItemId() );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Tests the sql:
     * Select * from item
     * and exists (select 1 from cat1 Where cat1.part = item.part and cat1.noun = 'Z-TESTNOUN%')
     * and exists (select 1 from sycd where sycd.condition = item.condition and description like 'Z%')
     * order by item_id
     * This should return 3 item records
     */
    public void testTwoInnerJoinsQueryUsingProxy() {
        try {

            Criteria c1 = new Criteria();
            c1.setTable(IPart.class.getName());
            c1.addInnerCriteria(IPart.PART, IItem.PART);
            c1.addCriteria(IPart.NOUN, Criteria.RELATIONAL_BEGINS_WITH, "Z-TESTNOUN");

            Criteria c2 = new Criteria();
            c2.setTable(ICondition.class.getName());
            c2.addInnerCriteria(ICondition.CONDITION, IItem.CONDITION);
            c2.addCriteria(ICondition.DESCRIPTION, Criteria.RELATIONAL_BEGINS_WITH, "Z");

            Criteria c = new Criteria();
            c.setTable(IItem.class.getName());
            c.addAggregate(c1);
            c.addAggregate(c2);
            c.addOrderBy(IItem.ITEM_ID, Criteria.ORDER_BY_ASC);

            Iterator i = m_uow.query(c).iterator();
            assertEquals( "Z-TESTITEM-01", ((IItem) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-02", ((IItem) i.next()).getItemId() );
            assertEquals( "Z-TESTITEM-03", ((IItem) i.next()).getItemId() );
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Tests the sql:
     * Select * from catz where part='Z-TESTPART-01'
     * This should return 1 catz record
     */
    public void testLongQueryUsingProxy() {
        try {
            Criteria c = new Criteria();
            c.setTable(IPartRemarks.class.getName());
            c.addCriteria(IPartRemarks.PART, "Z-TESTPART-01");
            Iterator i = m_uow.query(c).iterator();
            assertEquals( Wrapper.CATZ_REMARKS, ((IPartRemarks) i.next()).getRemarks());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from item Where
     * created_datetime = to_date('2003-09-10 20:30:40', 'yyyy-MM-dd hh24:mi:ss')
     * or created_datetime = to_date('2003-09-10', 'yyyy-MM-dd')
     * or created_datetime is null
     * order by item_id
     * This should return 3 item records
     */
    public void testDateTimeQueryUsingProxy() {
        try {
            Criteria c = new Criteria();
            c.addCriteria(IItem.CREATED_DATETIME, new DateTime(2003, DateTime.SEPTEMBER, 10, 20, 30, 40, 0));
            c.addOrCriteria(IItem.CREATED_DATETIME, new DateTime(2003, DateTime.SEPTEMBER, 10));
            c.addOrCriteria(IItem.CREATED_DATETIME, Criteria.RELATIONAL_IS_NULL);
            c.setTable(IItem.class.getName());
            c.addOrderBy(IItem.ITEM_ID, Criteria.ORDER_BY_ASC);

            Iterator i = m_uow.query(c).iterator();
            IItem item = (IItem) i.next();
            assertEquals( "Z-TESTITEM-01", item.getItemId() );
            assertEquals(new DateTime(2003, DateTime.SEPTEMBER, 10, 20, 30, 40, 0), item.getCreatedDatetime());

            item = ((IItem) i.next());
            assertEquals( "Z-TESTITEM-02", item.getItemId() );
            assertEquals(new DateTime(2003, DateTime.SEPTEMBER, 10), item.getCreatedDatetime());

            item = ((IItem) i.next());
            assertNull(item.getCreatedDatetime());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Creates 2 ZZ_JUT_INSTRUMENT records with the prefix 'Z-|%_'
     * It then Tests the sql:
     * Select * from ZZ_JUT_INSTRUMENT where CategoryInstrument like 'Z-|||%|_%' escape '|'
     * This should return 2 ZZ_JUT_INSTRUMENT records.
     * The records are then deleted.
     */
    public void testQueryWithEscapeCharactersUsingProxy() {
        try {
            // create a couple of CategoryOfInstrument with CategoryInstrument having the prefix 'Z-TESTCI-|%_'
            String prefix = "Z-|%_";

            ICategoryOfInstrument coi1 = (ICategoryOfInstrument) m_uow.newPersistentInstance(ICategoryOfInstrument.class);
            coi1.setCategoryInstrument(prefix + '1');
            ICategoryOfInstrument coi2 = (ICategoryOfInstrument) m_uow.newPersistentInstance(ICategoryOfInstrument.class);
            coi2.setCategoryInstrument(prefix + '2');
            m_uow.add(coi1);
            m_uow.add(coi2);
            m_uow.commit();

            // now retrieve them
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(ICategoryOfInstrument.class.getName());
            c.addCriteria(ICategoryOfInstrument.CATEGORY_INSTRUMENT, Criteria.RELATIONAL_BEGINS_WITH, prefix);
            Collection col = m_uow.query(c);
            assertEquals(2, col.size());

            // now delete the records
            for (Iterator itr = col.iterator(); itr.hasNext();)
                m_uow.delete((IPersistent) itr.next());
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from condition where condition not like 'Z%', which should return 0 records
     * Select * from condition where condition not like 'A%', which should return 3 records
     */
    public void testNotBeginsWithQueryUsingProxy() {
        try {
            Criteria c = new Criteria();
            c.setTable(ICondition.class.getName());
            c.addCriteria(ConditionMeta.CONDITION, Criteria.RELATIONAL_NOT_BEGINS_WITH, "Z");
            c.addOrderBy(ConditionMeta.CONDITION);
            ICondition condition = null;
            Iterator i = m_uow.query(c).iterator();
            assertTrue("No record should have been retrieved", !i.hasNext());

            c = new Criteria();
            c.setTable(ICondition.class.getName());
            c.addCriteria(ConditionMeta.CONDITION, Criteria.RELATIONAL_NOT_BEGINS_WITH, "A");
            c.addOrderBy(ConditionMeta.CONDITION);
            i = m_uow.query(c).iterator();
            condition = (ICondition) i.next();
            assertEquals("Z-TESTSYCD-01", condition.getCondition());
            condition = (ICondition) i.next();
            assertEquals("Z-TESTSYCD-02", condition.getCondition());
            condition = (ICondition) i.next();
            assertEquals("Z-TESTSYCD-03", condition.getCondition());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from condition where condition not like '%01'
     * This should return 2 records
     */
    public void testNotEndsWithQueryUsingProxy() {
        try {
            Criteria c = new Criteria();
            c.setTable(ICondition.class.getName());
            c.addCriteria(ConditionMeta.CONDITION, Criteria.RELATIONAL_NOT_ENDS_WITH, "01");
            c.addOrderBy(ConditionMeta.CONDITION);
            ICondition condition = null;
            Iterator i = m_uow.query(c).iterator();
            condition = (ICondition) i.next();
            assertEquals("Z-TESTSYCD-02", condition.getCondition());
            condition = (ICondition) i.next();
            assertEquals("Z-TESTSYCD-03", condition.getCondition());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Tests the sql:
     * Select * from condition where condition not like '%TEST%', which should return 0 records
     * Select * from condition where condition not like '%A%', which should return 3 records
     */
    public void testNotLikeWithQueryUsingProxy() {
        try {
            Criteria c = new Criteria();
            c.setTable(ICondition.class.getName());
            c.addCriteria(ConditionMeta.CONDITION, Criteria.RELATIONAL_NOT_LIKE, "TEST");
            c.addOrderBy(ConditionMeta.CONDITION);
            ICondition condition = null;
            Iterator i = m_uow.query(c).iterator();
            assertTrue("No record should have been retrieved", !i.hasNext());

            c = new Criteria();
            c.setTable(ICondition.class.getName());
            c.addCriteria(ConditionMeta.CONDITION, Criteria.RELATIONAL_NOT_LIKE, "A");
            c.addOrderBy(ConditionMeta.CONDITION);
            i = m_uow.query(c).iterator();
            condition = (ICondition) i.next();
            assertEquals("Z-TESTSYCD-01", condition.getCondition());
            condition = (ICondition) i.next();
            assertEquals("Z-TESTSYCD-02", condition.getCondition());
            condition = (ICondition) i.next();
            assertEquals("Z-TESTSYCD-03", condition.getCondition());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
