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
 * LobTest.java
 *
 * Created on April 19, 2002, 2:22 PM
 */

package org.jaffa.persistence.blackboxtests;

import junit.framework.TestCase;
import java.util.*;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.domainobjects.*;

/**
 *
 * @author  GautamJ
 * @version
 */
public class LobTest extends TestCase {

    private static StringBuffer buf1 = new StringBuffer();
    static {
        for (int i = 0; i < 10000; i++)
            buf1.append('Z');
    }
    private static final String CLOB_FIELD = buf1.toString();

    private static StringBuffer buf2 = new StringBuffer();
    static {
        for (int i = 0; i < 10000; i++)
            buf2.append('Z');
    }
    private static final String BLOB_FIELD = buf2.toString();

    private UOW m_uow = null;

    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(LobTest.class));
    }

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public LobTest(String name) {
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

    /** Creates ZZ_TEST_PART_REMARKS_PICTURE records having clob and blob elements. It then checks if the data has been added.
     */
    public void testAddClobAndBlob() {
        try {
            // create 3 records
            PartRemarksPicture partRemarksPicture = null;
            partRemarksPicture = (PartRemarksPicture) m_uow.newPersistentInstance(PartRemarksPicture.class);
            partRemarksPicture.updatePart("Z-TESTPART-01");
            partRemarksPicture.updateRemarks(CLOB_FIELD + "01");
            partRemarksPicture.updatePicture((BLOB_FIELD + "01").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (PartRemarksPicture) m_uow.newPersistentInstance(PartRemarksPicture.class);
            partRemarksPicture.updatePart("Z-TESTPART-02");
            partRemarksPicture.updateRemarks(CLOB_FIELD + "02");
            partRemarksPicture.updatePicture((BLOB_FIELD + "02").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (PartRemarksPicture) m_uow.newPersistentInstance(PartRemarksPicture.class);
            partRemarksPicture.updatePart("Z-TESTPART-03");
            partRemarksPicture.updateRemarks(CLOB_FIELD + "03");
            partRemarksPicture.updatePicture((BLOB_FIELD + "03").getBytes());
            m_uow.add(partRemarksPicture);
            m_uow.commit();


            // now check if they have been added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(PartRemarksPictureMeta.getName());
            c.addCriteria(PartRemarksPictureMeta.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-");
            c.addOrderBy(PartRemarksPictureMeta.PART, Criteria.ORDER_BY_ASC);
            Collection col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            PartRemarksPicture[] partRemarksPictures = (PartRemarksPicture[]) col.toArray(new PartRemarksPicture[0]);
            assertEquals("Z-TESTPART-01", partRemarksPictures[0].getPart());
            assertEquals(CLOB_FIELD + "01", partRemarksPictures[0].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "01").getBytes(), partRemarksPictures[0].getPicture()));

            assertEquals("Z-TESTPART-02", partRemarksPictures[1].getPart());
            assertEquals(CLOB_FIELD + "02", partRemarksPictures[1].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "02").getBytes(), partRemarksPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partRemarksPictures[2].getPart());
            assertEquals(CLOB_FIELD + "03", partRemarksPictures[2].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "03").getBytes(), partRemarksPictures[2].getPicture()));

            // now delete the records
            m_uow.delete(partRemarksPictures[0]);
            m_uow.delete(partRemarksPictures[1]);
            m_uow.delete(partRemarksPictures[2]);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }



    /** Creates ZZ_TEST_PART_REMARKS_PICTURE records having clob and blob elements.
     * It then retrieves and updates the rows. Finally it checks if the data has been updated.
     */
    public void testUpdateClob() {
        try {
            // create 3 records
            PartRemarksPicture partRemarksPicture = null;
            partRemarksPicture = (PartRemarksPicture) m_uow.newPersistentInstance(PartRemarksPicture.class);
            partRemarksPicture.updatePart("Z-TESTPART-01");
            partRemarksPicture.updateRemarks(CLOB_FIELD + "01");
            partRemarksPicture.updatePicture((BLOB_FIELD + "01").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (PartRemarksPicture) m_uow.newPersistentInstance(PartRemarksPicture.class);
            partRemarksPicture.updatePart("Z-TESTPART-02");
            partRemarksPicture.updateRemarks(CLOB_FIELD + "02");
            partRemarksPicture.updatePicture((BLOB_FIELD + "02").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (PartRemarksPicture) m_uow.newPersistentInstance(PartRemarksPicture.class);
            partRemarksPicture.updatePart("Z-TESTPART-03");
            partRemarksPicture.updateRemarks(CLOB_FIELD + "03");
            partRemarksPicture.updatePicture((BLOB_FIELD + "03").getBytes());
            m_uow.add(partRemarksPicture);
            m_uow.commit();


            // now check if they have been added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(PartRemarksPictureMeta.getName());
            c.addCriteria(PartRemarksPictureMeta.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-");
            c.addOrderBy(PartRemarksPictureMeta.PART, Criteria.ORDER_BY_ASC);
            Collection col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            PartRemarksPicture[] partRemarksPictures = (PartRemarksPicture[]) col.toArray(new PartRemarksPicture[0]);
            assertEquals("Z-TESTPART-01", partRemarksPictures[0].getPart());
            assertEquals(CLOB_FIELD + "01", partRemarksPictures[0].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "01").getBytes(), partRemarksPictures[0].getPicture()));

            assertEquals("Z-TESTPART-02", partRemarksPictures[1].getPart());
            assertEquals(CLOB_FIELD + "02", partRemarksPictures[1].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "02").getBytes(), partRemarksPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partRemarksPictures[2].getPart());
            assertEquals(CLOB_FIELD + "03", partRemarksPictures[2].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "03").getBytes(), partRemarksPictures[2].getPicture()));


            // now update the records
            partRemarksPictures[0].updateRemarks(null);
            partRemarksPictures[1].updateRemarks("Z-UPDATEDREMARKS-022");
            m_uow.update(partRemarksPictures[0]);
            m_uow.update(partRemarksPictures[1]);
            m_uow.commit();

            // now check if the updates were successful
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(PartRemarksPictureMeta.getName());
            c.addCriteria(PartRemarksPictureMeta.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-");
            c.addOrderBy(PartRemarksPictureMeta.PART, Criteria.ORDER_BY_ASC);
            col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            partRemarksPictures = (PartRemarksPicture[]) col.toArray(new PartRemarksPicture[0]);
            assertNull(partRemarksPictures[0].getRemarks());
            assertEquals("Z-UPDATEDREMARKS-022", partRemarksPictures[1].getRemarks());
            assertEquals(CLOB_FIELD + "03", partRemarksPictures[2].getRemarks());


            // now delete the records
            m_uow.delete(partRemarksPictures[0]);
            m_uow.delete(partRemarksPictures[1]);
            m_uow.delete(partRemarksPictures[2]);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Creates ZZ_TEST_PART_REMARKS_PICTURE records having clob and blob elements.
     * It then retrieves and updates the rows. Finally it checks if the data has been updated.
     */
    public void testUpdateBlob() {
        try {
            // create 3 records
            PartRemarksPicture partRemarksPicture = null;
            partRemarksPicture = (PartRemarksPicture) m_uow.newPersistentInstance(PartRemarksPicture.class);
            partRemarksPicture.updatePart("Z-TESTPART-01");
            partRemarksPicture.updateRemarks(CLOB_FIELD + "01");
            partRemarksPicture.updatePicture((BLOB_FIELD + "01").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (PartRemarksPicture) m_uow.newPersistentInstance(PartRemarksPicture.class);
            partRemarksPicture.updatePart("Z-TESTPART-02");
            partRemarksPicture.updateRemarks(CLOB_FIELD + "02");
            partRemarksPicture.updatePicture((BLOB_FIELD + "02").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (PartRemarksPicture) m_uow.newPersistentInstance(PartRemarksPicture.class);
            partRemarksPicture.updatePart("Z-TESTPART-03");
            partRemarksPicture.updateRemarks(CLOB_FIELD + "03");
            partRemarksPicture.updatePicture((BLOB_FIELD + "03").getBytes());
            m_uow.add(partRemarksPicture);
            m_uow.commit();


            // now check if they have been added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(PartRemarksPictureMeta.getName());
            c.addCriteria(PartRemarksPictureMeta.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-");
            c.addOrderBy(PartRemarksPictureMeta.PART, Criteria.ORDER_BY_ASC);
            Collection col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            PartRemarksPicture[] partRemarksPictures = (PartRemarksPicture[]) col.toArray(new PartRemarksPicture[0]);
            assertEquals("Z-TESTPART-01", partRemarksPictures[0].getPart());
            assertEquals(CLOB_FIELD + "01", partRemarksPictures[0].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "01").getBytes(), partRemarksPictures[0].getPicture()));

            assertEquals("Z-TESTPART-02", partRemarksPictures[1].getPart());
            assertEquals(CLOB_FIELD + "02", partRemarksPictures[1].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "02").getBytes(), partRemarksPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partRemarksPictures[2].getPart());
            assertEquals(CLOB_FIELD + "03", partRemarksPictures[2].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "03").getBytes(), partRemarksPictures[2].getPicture()));


            // now update the records
            partRemarksPictures[0].updatePicture(null);
            partRemarksPictures[1].updatePicture("Z-UPDATEDREMARKS-022".getBytes());
            m_uow.update(partRemarksPictures[0]);
            m_uow.update(partRemarksPictures[1]);
            m_uow.commit();

            // now check if the updates were successful
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(PartRemarksPictureMeta.getName());
            c.addCriteria(PartRemarksPictureMeta.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-");
            c.addOrderBy(PartRemarksPictureMeta.PART, Criteria.ORDER_BY_ASC);
            col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            partRemarksPictures = (PartRemarksPicture[]) col.toArray(new PartRemarksPicture[0]);
            assertNull(partRemarksPictures[0].getPicture());
            assertTrue(Arrays.equals("Z-UPDATEDREMARKS-022".getBytes(), partRemarksPictures[1].getPicture()));
            assertTrue(Arrays.equals((BLOB_FIELD + "03").getBytes(), partRemarksPictures[2].getPicture()));

            // now delete the records
            m_uow.delete(partRemarksPictures[0]);
            m_uow.delete(partRemarksPictures[1]);
            m_uow.delete(partRemarksPictures[2]);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }



    /** Creates ZZ_TEST_PART_REMARKS_PICTURE records having clob and blob elements. It then checks if the data has been added.
     */
    public void testAddClobAndBlobUsingProxy() {
        try {
            // create 3 records
            IPartRemarksPicture partRemarksPicture = null;
            partRemarksPicture = (IPartRemarksPicture) m_uow.newPersistentInstance(IPartRemarksPicture.class);
            partRemarksPicture.setPart("Z-TESTPART-01");
            partRemarksPicture.setRemarks(CLOB_FIELD + "01");
            partRemarksPicture.setPicture((BLOB_FIELD + "01").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (IPartRemarksPicture) m_uow.newPersistentInstance(IPartRemarksPicture.class);
            partRemarksPicture.setPart("Z-TESTPART-02");
            partRemarksPicture.setRemarks(CLOB_FIELD + "02");
            partRemarksPicture.setPicture((BLOB_FIELD + "02").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (IPartRemarksPicture) m_uow.newPersistentInstance(IPartRemarksPicture.class);
            partRemarksPicture.setPart("Z-TESTPART-03");
            partRemarksPicture.setRemarks(CLOB_FIELD + "03");
            partRemarksPicture.setPicture((BLOB_FIELD + "03").getBytes());
            m_uow.add(partRemarksPicture);
            m_uow.commit();


            // now check if they have been added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(IPartRemarksPicture.class.getName());
            c.addCriteria(IPartRemarksPicture.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-");
            c.addOrderBy(IPartRemarksPicture.PART, Criteria.ORDER_BY_ASC);
            Collection col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            IPartRemarksPicture[] partRemarksPictures = (IPartRemarksPicture[]) col.toArray(new IPartRemarksPicture[0]);
            assertEquals("Z-TESTPART-01", partRemarksPictures[0].getPart());
            assertEquals(CLOB_FIELD + "01", partRemarksPictures[0].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "01").getBytes(), partRemarksPictures[0].getPicture()));

            assertEquals("Z-TESTPART-02", partRemarksPictures[1].getPart());
            assertEquals(CLOB_FIELD + "02", partRemarksPictures[1].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "02").getBytes(), partRemarksPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partRemarksPictures[2].getPart());
            assertEquals(CLOB_FIELD + "03", partRemarksPictures[2].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "03").getBytes(), partRemarksPictures[2].getPicture()));

            // now delete the records
            m_uow.delete(partRemarksPictures[0]);
            m_uow.delete(partRemarksPictures[1]);
            m_uow.delete(partRemarksPictures[2]);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }



    /** Creates ZZ_TEST_PART_REMARKS_PICTURE records having clob and blob elements.
     * It then retrieves and updates the rows. Finally it checks if the data has been updated.
     */
    public void testUpdateClobUsingProxy() {
        try {
            // create 3 records
            IPartRemarksPicture partRemarksPicture = null;
            partRemarksPicture = (IPartRemarksPicture) m_uow.newPersistentInstance(IPartRemarksPicture.class);
            partRemarksPicture.setPart("Z-TESTPART-01");
            partRemarksPicture.setRemarks(CLOB_FIELD + "01");
            partRemarksPicture.setPicture((BLOB_FIELD + "01").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (IPartRemarksPicture) m_uow.newPersistentInstance(IPartRemarksPicture.class);
            partRemarksPicture.setPart("Z-TESTPART-02");
            partRemarksPicture.setRemarks(CLOB_FIELD + "02");
            partRemarksPicture.setPicture((BLOB_FIELD + "02").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (IPartRemarksPicture) m_uow.newPersistentInstance(IPartRemarksPicture.class);
            partRemarksPicture.setPart("Z-TESTPART-03");
            partRemarksPicture.setRemarks(CLOB_FIELD + "03");
            partRemarksPicture.setPicture((BLOB_FIELD + "03").getBytes());
            m_uow.add(partRemarksPicture);
            m_uow.commit();


            // now check if they have been added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(IPartRemarksPicture.class.getName());
            c.addCriteria(IPartRemarksPicture.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-");
            c.addOrderBy(IPartRemarksPicture.PART, Criteria.ORDER_BY_ASC);
            Collection col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            IPartRemarksPicture[] partRemarksPictures = (IPartRemarksPicture[]) col.toArray(new IPartRemarksPicture[0]);
            assertEquals("Z-TESTPART-01", partRemarksPictures[0].getPart());
            assertEquals(CLOB_FIELD + "01", partRemarksPictures[0].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "01").getBytes(), partRemarksPictures[0].getPicture()));

            assertEquals("Z-TESTPART-02", partRemarksPictures[1].getPart());
            assertEquals(CLOB_FIELD + "02", partRemarksPictures[1].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "02").getBytes(), partRemarksPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partRemarksPictures[2].getPart());
            assertEquals(CLOB_FIELD + "03", partRemarksPictures[2].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "03").getBytes(), partRemarksPictures[2].getPicture()));


            // now update the records
            partRemarksPictures[0].setRemarks(null);
            partRemarksPictures[1].setRemarks("Z-UPDATEDREMARKS-022");
            m_uow.update(partRemarksPictures[0]);
            m_uow.update(partRemarksPictures[1]);
            m_uow.commit();

            // now check if the updates were successful
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(IPartRemarksPicture.class.getName());
            c.addCriteria(IPartRemarksPicture.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-");
            c.addOrderBy(IPartRemarksPicture.PART, Criteria.ORDER_BY_ASC);
            col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            partRemarksPictures = (IPartRemarksPicture[]) col.toArray(new IPartRemarksPicture[0]);
            assertNull(partRemarksPictures[0].getRemarks());
            assertEquals("Z-UPDATEDREMARKS-022", partRemarksPictures[1].getRemarks());
            assertEquals(CLOB_FIELD + "03", partRemarksPictures[2].getRemarks());


            // now delete the records
            m_uow.delete(partRemarksPictures[0]);
            m_uow.delete(partRemarksPictures[1]);
            m_uow.delete(partRemarksPictures[2]);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Creates ZZ_TEST_PART_REMARKS_PICTURE records having clob and blob elements.
     * It then retrieves and updates the rows. Finally it checks if the data has been updated.
     */
    public void testUpdateBlobUsingProxy() {
        try {
            // create 3 records
            IPartRemarksPicture partRemarksPicture = null;
            partRemarksPicture = (IPartRemarksPicture) m_uow.newPersistentInstance(IPartRemarksPicture.class);
            partRemarksPicture.setPart("Z-TESTPART-01");
            partRemarksPicture.setRemarks(CLOB_FIELD + "01");
            partRemarksPicture.setPicture((BLOB_FIELD + "01").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (IPartRemarksPicture) m_uow.newPersistentInstance(IPartRemarksPicture.class);
            partRemarksPicture.setPart("Z-TESTPART-02");
            partRemarksPicture.setRemarks(CLOB_FIELD + "02");
            partRemarksPicture.setPicture((BLOB_FIELD + "02").getBytes());
            m_uow.add(partRemarksPicture);

            partRemarksPicture = (IPartRemarksPicture) m_uow.newPersistentInstance(IPartRemarksPicture.class);
            partRemarksPicture.setPart("Z-TESTPART-03");
            partRemarksPicture.setRemarks(CLOB_FIELD + "03");
            partRemarksPicture.setPicture((BLOB_FIELD + "03").getBytes());
            m_uow.add(partRemarksPicture);
            m_uow.commit();


            // now check if they have been added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(IPartRemarksPicture.class.getName());
            c.addCriteria(IPartRemarksPicture.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-");
            c.addOrderBy(IPartRemarksPicture.PART, Criteria.ORDER_BY_ASC);
            Collection col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            IPartRemarksPicture[] partRemarksPictures = (IPartRemarksPicture[]) col.toArray(new IPartRemarksPicture[0]);
            assertEquals("Z-TESTPART-01", partRemarksPictures[0].getPart());
            assertEquals(CLOB_FIELD + "01", partRemarksPictures[0].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "01").getBytes(), partRemarksPictures[0].getPicture()));

            assertEquals("Z-TESTPART-02", partRemarksPictures[1].getPart());
            assertEquals(CLOB_FIELD + "02", partRemarksPictures[1].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "02").getBytes(), partRemarksPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partRemarksPictures[2].getPart());
            assertEquals(CLOB_FIELD + "03", partRemarksPictures[2].getRemarks());
            assertTrue(Arrays.equals((BLOB_FIELD + "03").getBytes(), partRemarksPictures[2].getPicture()));


            // now update the records
            partRemarksPictures[0].setPicture(null);
            partRemarksPictures[1].setPicture("Z-UPDATEDREMARKS-022".getBytes());
            m_uow.update(partRemarksPictures[0]);
            m_uow.update(partRemarksPictures[1]);
            m_uow.commit();

            // now check if the updates were successful
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(IPartRemarksPicture.class.getName());
            c.addCriteria(IPartRemarksPicture.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z-");
            c.addOrderBy(IPartRemarksPicture.PART, Criteria.ORDER_BY_ASC);
            col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            partRemarksPictures = (IPartRemarksPicture[]) col.toArray(new IPartRemarksPicture[0]);
            assertNull(partRemarksPictures[0].getPicture());
            assertTrue(Arrays.equals("Z-UPDATEDREMARKS-022".getBytes(), partRemarksPictures[1].getPicture()));
            assertTrue(Arrays.equals((BLOB_FIELD + "03").getBytes(), partRemarksPictures[2].getPicture()));

            // now delete the records
            m_uow.delete(partRemarksPictures[0]);
            m_uow.delete(partRemarksPictures[1]);
            m_uow.delete(partRemarksPictures[2]);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
