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

/** Tests for performing inserts through the Jaffa Persistence Engine.
 *
 * @author GautamJ
 */
public class AddTest extends TestCase {

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
        return new Wrapper(new TestSuite(AddTest.class));
    }

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public AddTest(String name) {
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


    /** Inserts a record into SYCI using the query:
     * insert into SYCI(CATEGORY_INSTRUMENT, DESCRIPTION) values('Z-TESTCI-02', 'Z-TESTCIDESC-02').
     * It then checks if the record was added. Finally the record is deleted
     */
    public void testCreateCategoryOfInstrument() {
        try {
            CategoryOfInstrument obj = (CategoryOfInstrument) m_uow.newPersistentInstance(CategoryOfInstrument.class);
            obj.updateCategoryInstrument("Z-TESTCI-02");
            obj.updateDescription("Z-TESTCIDESC-02");
            obj.updateSupportEquip(Boolean.FALSE);
            m_uow.add(obj);
            m_uow.commit();

            // Now retrieve the added record & check if it was correctly added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, "Z-TESTCI-02");
            Iterator i = m_uow.query(c).iterator();
            CategoryOfInstrument syci = (CategoryOfInstrument) i.next();
            assertEquals("Z-TESTCI-02", syci.getCategoryInstrument());
            assertEquals("Z-TESTCIDESC-02", syci.getDescription());
            assertEquals(Boolean.FALSE, syci.getSupportEquip());
            assertNull(syci.getCalculateMtbf());


            // Now delete the bugger
            m_uow.delete(syci);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Inserts a record into CATZ using the query:
     * insert into CATZ(PART, REMARKS) values('Z-TESTPART-02', some 10,000 character string).
     * It then checks if the record was added. Finally the record is deleted
     */
    public void testAddLong() {
        try {
            PartRemarks obj = (PartRemarks) m_uow.newPersistentInstance(PartRemarks.class);
            obj.updatePart("Z-TESTPART-02");
            obj.updateRemarks(LONG_FIELD);
            m_uow.add(obj);
            m_uow.commit();

            // Now retrieve the added record & check if it was correctly added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(PartRemarksMeta.getName());
            c.addCriteria(PartRemarksMeta.PART, "Z-TESTPART-02");
            Iterator i = m_uow.query(c).iterator();
            PartRemarks catz = (PartRemarks) i.next();
            assertEquals("Z-TESTPART-02", catz.getPart());
            assertEquals(LONG_FIELD, catz.getRemarks());


            // Now delete the bugger
            m_uow.delete(catz);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Inserts a record into SYCI using the query:
     * insert into SYCI(CATEGORY_INSTRUMENT, DESCRIPTION) values('Z-TESTCI-03', 'Z-TESTCIDESC-03').
     * It then tries to add the same record. The test is successful, if an exception is raised.
     * Finally it rollbacks the changes
     */
    public void testAddDuplicates() {
        try {
            CategoryOfInstrument obj1 = (CategoryOfInstrument) m_uow.newPersistentInstance(CategoryOfInstrument.class);
            obj1.updateCategoryInstrument("Z-TESTCI-03");
            obj1.updateDescription("Z-TESTCIDESC-03");
            m_uow.add(obj1);

            CategoryOfInstrument obj2 = (CategoryOfInstrument) m_uow.newPersistentInstance(CategoryOfInstrument.class);
            obj2.updateCategoryInstrument("Z-TESTCI-03");
            obj2.updateDescription("Z-TESTCIDESC-03");
            m_uow.add(obj2);

            m_uow.commit();

            // The test has failed, if this point is reached
            fail("No exception was raised while adding duplicates");
        } catch (Exception e) {
            // do nothing.. reaching this point is a success !!!
        } finally {
            try {
                m_uow.rollback();
            } catch(Exception e) {
                e.printStackTrace();
                fail();
            }
        }
    }

    /** Inserts a record into SYCI using the query:
     * insert into SYCI(CATEGORY_INSTRUMENT, DESCRIPTION) values('Z-TESTCI-03', 'Z-TESTCIDESC-03').
     * It then does a rollback. This is followed by a commit. The test fails if the record is added.
     */
    public void testCheckRollbackAfterAdd() {
        try {
            CategoryOfInstrument obj = (CategoryOfInstrument) m_uow.newPersistentInstance(CategoryOfInstrument.class);
            obj.updateCategoryInstrument("Z-TESTCI-03");
            obj.updateDescription("Z-TESTCIDESC-03");
            m_uow.add(obj);
            m_uow.rollback();

            // Now ensure the record isn't added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, "Z-TESTCI-03");
            Iterator i = m_uow.query(c).iterator();
            if (i.hasNext()) {
                // delete the record & fail the test
                CategoryOfInstrument syci = (CategoryOfInstrument) i.next();
                m_uow.delete(syci);
                m_uow.commit();
                fail("Record has been added even after a rollback");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Inserts a record into ITEM using the query:
     * insert into ITEM(ITEM_ID, CREATED_DATETIME) values('Z-TESTITEM-04', to_date('2003-09-10 20:30:40', 'yyyy-MM-dd hh24:mi:ss')).
     * It then checks if the record was added. Finally the record is deleted
     */
    public void testAddDateTime() {
        try {
            String itemId = "Z-TESTITEM-04";
            DateTime datetime = new DateTime(2003, DateTime.SEPTEMBER, 10, 20, 30, 40, 0);

            Item obj = (Item) m_uow.newPersistentInstance(Item.class);
            obj.updateItemId(itemId);
            obj.updateCreatedDatetime(datetime);
            m_uow.add(obj);
            m_uow.commit();

            // Now retrieve the added record & check if it was correctly added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(ItemMeta.getName());
            c.addCriteria(ItemMeta.ITEM_ID, itemId);
            Iterator i = m_uow.query(c).iterator();
            Item item = (Item) i.next();
            assertEquals(itemId, item.getItemId());
            assertEquals(datetime, item.getCreatedDatetime());


            // Now delete the bugger
            m_uow.delete(item);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Inserts a record into ITEM using the query:
     * insert into ITEM(ITEM_ID, PRICE) values('Z-TESTITEM-04', 1234567890.12345).
     * It then checks if the record was added. Finally the record is deleted
     */
    public void testAddDecimal() {
        try {
            String itemId = "Z-TESTITEM-04";
            Double price = new Double(1234567890.12345);

            Item obj = (Item) m_uow.newPersistentInstance(Item.class);
            obj.setItemId(itemId);
            obj.setPrice(price);
            m_uow.add(obj);
            m_uow.commit();

            // Now retrieve the added record & check if it was correctly added
            m_uow = new UOW();
            obj = Item.findByPK(m_uow, itemId);
            assertEquals(price, obj.getPrice());

            // Now delete the bugger
            m_uow.delete(obj);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Inserts a record into ASSET, which has a database generated key
     * It then checks if the record was added. Finally the record is deleted
     */
    public void testAddWithAutoKey() {
        try {
            String assetId = "Z-TESTASSET-01";
            DateTime datetime = new DateTime(2003, DateTime.SEPTEMBER, 10, 20, 30, 40, 0);


            Asset obj = (Asset) m_uow.newPersistentInstance(Asset.class);
            obj.updateAssetId(assetId);
            obj.updateCreatedDatetime(datetime);
            m_uow.add(obj);
            m_uow.commit();

            // Now retrieve the added record & check if it was correctly added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(AssetMeta.getName());
            c.addCriteria(AssetMeta.ASSET_ID, assetId);
            Iterator i = m_uow.query(c).iterator();
            Asset asset = (Asset) i.next();
            assertNotNull(asset.getAssetTk());
            assertEquals(assetId, asset.getAssetId());
            assertEquals(datetime, asset.getCreatedDatetime());


            // Now delete the bugger
            m_uow.delete(asset);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Inserts a record into SYCI using the query:
     * insert into SYCI(CATEGORY_INSTRUMENT, DESCRIPTION) values('Z-TESTCI-02', 'Z-TESTCIDESC-02').
     * It then checks if the record was added. Finally the record is deleted
     */
    public void testCreateCategoryOfInstrumentUsingProxy() {
        try {
            ICategoryOfInstrument obj = (ICategoryOfInstrument) m_uow.newPersistentInstance(ICategoryOfInstrument.class);
            obj.setCategoryInstrument("Z-TESTCI-02");
            obj.setDescription("Z-TESTCIDESC-02");
            obj.setSupportEquip(Boolean.FALSE);
            m_uow.add(obj);
            m_uow.commit();

            // Now retrieve the added record & check if it was correctly added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(ICategoryOfInstrument.class.getName());
            c.addCriteria(ICategoryOfInstrument.CATEGORY_INSTRUMENT, "Z-TESTCI-02");
            Iterator i = m_uow.query(c).iterator();
            ICategoryOfInstrument syci = (ICategoryOfInstrument) i.next();
            assertEquals("Z-TESTCI-02", syci.getCategoryInstrument());
            assertEquals("Z-TESTCIDESC-02", syci.getDescription());
            assertEquals(Boolean.FALSE, syci.getSupportEquip());
            assertNull(syci.getCalculateMtbf());


            // Now delete the bugger
            m_uow.delete(syci);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Inserts a record into CATZ using the query:
     * insert into CATZ(PART, REMARKS) values('Z-TESTPART-02', some 10,000 character string).
     * It then checks if the record was added. Finally the record is deleted
     */
    public void testAddLongUsingProxy() {
        try {
            IPartRemarks obj = (IPartRemarks) m_uow.newPersistentInstance(IPartRemarks.class);
            obj.setPart("Z-TESTPART-02");
            obj.setRemarks(LONG_FIELD);
            m_uow.add(obj);
            m_uow.commit();

            // Now retrieve the added record & check if it was correctly added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(IPartRemarks.class.getName());
            c.addCriteria(IPartRemarks.PART, "Z-TESTPART-02");
            Iterator i = m_uow.query(c).iterator();
            IPartRemarks catz = (IPartRemarks) i.next();
            assertEquals("Z-TESTPART-02", catz.getPart());
            assertEquals(LONG_FIELD, catz.getRemarks());


            // Now delete the bugger
            m_uow.delete(catz);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Inserts a record into SYCI using the query:
     * insert into SYCI(CATEGORY_INSTRUMENT, DESCRIPTION) values('Z-TESTCI-03', 'Z-TESTCIDESC-03').
     * It then tries to add the same record. The test is successful, if an exception is raised.
     * Finally it rollbacks the changes
     */
    public void testAddDuplicatesUsingProxy() {
        try {
            ICategoryOfInstrument obj1 = (ICategoryOfInstrument) m_uow.newPersistentInstance(ICategoryOfInstrument.class);
            obj1.setCategoryInstrument("Z-TESTCI-03");
            obj1.setDescription("Z-TESTCIDESC-03");
            m_uow.add(obj1);

            ICategoryOfInstrument obj2 = (ICategoryOfInstrument) m_uow.newPersistentInstance(ICategoryOfInstrument.class);
            obj2.setCategoryInstrument("Z-TESTCI-03");
            obj2.setDescription("Z-TESTCIDESC-03");
            m_uow.add(obj2);

            m_uow.commit();

            // The test has failed, if this point is reached
            fail("No exception was raised while adding duplicates");
        } catch (Exception e) {
            // do nothing.. reaching this point is a success !!!
        } finally {
            try {
                m_uow.rollback();
            } catch(Exception e) {
                e.printStackTrace();
                fail();
            }
        }
    }

    /** Inserts a record into SYCI using the query:
     * insert into SYCI(CATEGORY_INSTRUMENT, DESCRIPTION) values('Z-TESTCI-03', 'Z-TESTCIDESC-03').
     * It then does a rollback. This is followed by a commit. The test fails if the record is added.
     */
    public void testCheckRollbackAfterAddUsingProxy() {
        try {
            ICategoryOfInstrument obj = (ICategoryOfInstrument) m_uow.newPersistentInstance(ICategoryOfInstrument.class);
            obj.setCategoryInstrument("Z-TESTCI-03");
            obj.setDescription("Z-TESTCIDESC-03");
            m_uow.add(obj);
            m_uow.rollback();

            // Now ensure the record isn't added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(ICategoryOfInstrument.class.getName());
            c.addCriteria(ICategoryOfInstrument.CATEGORY_INSTRUMENT, "Z-TESTCI-03");
            Iterator i = m_uow.query(c).iterator();
            if (i.hasNext()) {
                // delete the record & fail the test
                ICategoryOfInstrument syci = (ICategoryOfInstrument) i.next();
                m_uow.delete(syci);
                m_uow.commit();
                fail("Record has been added even after a rollback");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Inserts a record into ITEM using the query:
     * insert into ITEM(ITEM_ID, CREATED_DATETIME) values('Z-TESTITEM-04', to_date('2003-09-10 20:30:40', 'yyyy-MM-dd hh24:mi:ss')).
     * It then checks if the record was added. Finally the record is deleted
     */
    public void testAddDateTimeUsingProxy() {
        try {
            String itemId = "Z-TESTITEM-04";
            DateTime datetime = new DateTime(2003, DateTime.SEPTEMBER, 10, 20, 30, 40, 0);

            IItem obj = (IItem) m_uow.newPersistentInstance(IItem.class);
            obj.setItemId(itemId);
            obj.setCreatedDatetime(datetime);
            m_uow.add(obj);
            m_uow.commit();

            // Now retrieve the added record & check if it was correctly added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(IItem.class.getName());
            c.addCriteria(IItem.ITEM_ID, itemId);
            Iterator i = m_uow.query(c).iterator();
            IItem item = (IItem) i.next();
            assertEquals(itemId, item.getItemId());
            assertEquals(datetime, item.getCreatedDatetime());


            // Now delete the bugger
            m_uow.delete(item);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** Inserts a record into ITEM using the query:
     * insert into ITEM(ITEM_ID, PRICE) values('Z-TESTITEM-04', 1234567890.12345).
     * It then checks if the record was added. Finally the record is deleted
     */
    public void testAddDecimalUsingProxy() {
        try {
            String itemId = "Z-TESTITEM-04";
            Double price = new Double(1234567890.12345);

            IItem obj = (IItem) m_uow.newPersistentInstance(IItem.class);
            obj.setItemId(itemId);
            obj.setPrice(price);
            m_uow.add(obj);
            m_uow.commit();

            // Now retrieve the added record & check if it was correctly added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(IItem.class.getName());
            c.addCriteria(IItem.ITEM_ID, itemId);
            Iterator i = m_uow.query(c).iterator();
            obj = (IItem) i.next();
            assertEquals(price, obj.getPrice());

            // Now delete the bugger
            m_uow.delete(obj);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Inserts a record into ASSET, which has a database generated key
     * It then checks if the record was added. Finally the record is deleted
     */
    public void testAddWithAutoKeyUsingProxy() {
        try {
            String assetId = "Z-TESTASSET-01";
            DateTime datetime = new DateTime(2003, DateTime.SEPTEMBER, 10, 20, 30, 40, 0);


            IAsset obj = (IAsset) m_uow.newPersistentInstance(IAsset.class);
            obj.setAssetId(assetId);
            obj.setCreatedDatetime(datetime);
            m_uow.add(obj);
            m_uow.commit();

            // Now retrieve the added record & check if it was correctly added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(IAsset.class.getName());
            c.addCriteria(IAsset.ASSET_ID, assetId);
            Iterator i = m_uow.query(c).iterator();
            IAsset asset = (IAsset) i.next();
            assertNotNull(asset.getAssetTk());
            assertEquals(assetId, asset.getAssetId());
            assertEquals(datetime, asset.getCreatedDatetime());


            // Now delete the bugger
            m_uow.delete(asset);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


}
