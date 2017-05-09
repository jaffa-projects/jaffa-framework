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
 * RawTest.java
 *
 * Created on April 19, 2002, 9:44 AM
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
public class RawTest extends TestCase {

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
        return new Wrapper(new TestSuite(RawTest.class));
    }

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public RawTest(String name) {
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

    /** Creates ZZ_TEST_PART_PICTURE records having raw and longraw elements. It then checks if the data has been added.
     */
    public void testAddRawAndLongRaw() {
        try {
            // create 3 records
            PartPicture partPicture = null;
            partPicture = (PartPicture) m_uow.newPersistentInstance(PartPicture.class);
            partPicture.updatePart("Z-TESTPART-01");
            partPicture.updateSmallPicture("Z-TESTSMALLPICTURE-01".getBytes());
            partPicture.updatePicture((LONG_FIELD + "01").getBytes());
            m_uow.add(partPicture);

            partPicture = (PartPicture) m_uow.newPersistentInstance(PartPicture.class);
            partPicture.updatePart("Z-TESTPART-02");
            partPicture.updateSmallPicture("Z-TESTSMALLPICTURE-02".getBytes());
            partPicture.updatePicture((LONG_FIELD + "02").getBytes());
            m_uow.add(partPicture);

            partPicture = (PartPicture) m_uow.newPersistentInstance(PartPicture.class);
            partPicture.updatePart("Z-TESTPART-03");
            partPicture.updateSmallPicture("Z-TESTSMALLPICTURE-03".getBytes());
            partPicture.updatePicture((LONG_FIELD + "03").getBytes());
            m_uow.add(partPicture);
            m_uow.commit();


            // now check if they have been added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(PartPictureMeta.getName());
            c.addCriteria(PartPictureMeta.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z");
            c.addOrderBy(PartPictureMeta.PART, Criteria.ORDER_BY_ASC);
            Collection col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            PartPicture[] partPictures = (PartPicture[]) col.toArray(new PartPicture[0]);
            assertEquals("Z-TESTPART-01", partPictures[0].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-01".getBytes(), partPictures[0].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "01").getBytes(), partPictures[0].getPicture()));

            assertEquals("Z-TESTPART-02", partPictures[1].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-02".getBytes(), partPictures[1].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "02").getBytes(), partPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partPictures[2].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-03".getBytes(), partPictures[2].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "03").getBytes(), partPictures[2].getPicture()));

            // now delete the records
            m_uow.delete(partPictures[0]);
            m_uow.delete(partPictures[1]);
            m_uow.delete(partPictures[2]);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Creates ZZ_TEST_PART_PICTURE records having raw and longraw elements.
     * It then retrieves and updates the rows. Finally it checks if the data has been updated.
     */
    public void testUpdateRawAndLongRaw() {
        try {
            // create 3 records
            PartPicture partPicture = null;
            partPicture = (PartPicture) m_uow.newPersistentInstance(PartPicture.class);
            partPicture.updatePart("Z-TESTPART-01");
            partPicture.updateSmallPicture("Z-TESTSMALLPICTURE-01".getBytes());
            partPicture.updatePicture((LONG_FIELD + "01").getBytes());
            m_uow.add(partPicture);

            partPicture = (PartPicture) m_uow.newPersistentInstance(PartPicture.class);
            partPicture.updatePart("Z-TESTPART-02");
            partPicture.updateSmallPicture("Z-TESTSMALLPICTURE-02".getBytes());
            partPicture.updatePicture((LONG_FIELD + "02").getBytes());
            m_uow.add(partPicture);

            partPicture = (PartPicture) m_uow.newPersistentInstance(PartPicture.class);
            partPicture.updatePart("Z-TESTPART-03");
            partPicture.updateSmallPicture("Z-TESTSMALLPICTURE-03".getBytes());
            partPicture.updatePicture((LONG_FIELD + "03").getBytes());
            m_uow.add(partPicture);
            m_uow.commit();


            // now check if they have been added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(PartPictureMeta.getName());
            c.addCriteria(PartPictureMeta.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z");
            c.addOrderBy(PartPictureMeta.PART, Criteria.ORDER_BY_ASC);
            Collection col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            PartPicture[] partPictures = (PartPicture[]) col.toArray(new PartPicture[0]);
            assertEquals("Z-TESTPART-01", partPictures[0].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-01".getBytes(), partPictures[0].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "01").getBytes(), partPictures[0].getPicture()));

            assertEquals("Z-TESTPART-02", partPictures[1].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-02".getBytes(), partPictures[1].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "02").getBytes(), partPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partPictures[2].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-03".getBytes(), partPictures[2].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "03").getBytes(), partPictures[2].getPicture()));



            // now update the records
            partPictures[0].updateSmallPicture(null);
            partPictures[0].updatePicture(null);
            partPictures[1].updateSmallPicture("Z-TESTSMALLPICTURE-022".getBytes());
            partPictures[1].updatePicture("Z-TESTPICTURE-022".getBytes());
            m_uow.update(partPictures[0]);
            m_uow.update(partPictures[1]);
            m_uow.commit();


            // now check if the updates were successful
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(PartPictureMeta.getName());
            c.addCriteria(PartPictureMeta.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z");
            c.addOrderBy(PartPictureMeta.PART, Criteria.ORDER_BY_ASC);
            col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            partPictures = (PartPicture[]) col.toArray(new PartPicture[0]);
            assertEquals("Z-TESTPART-01", partPictures[0].getPart());
            assertNull(partPictures[0].getSmallPicture());
            assertNull(partPictures[0].getPicture());

            assertEquals("Z-TESTPART-02", partPictures[1].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-022".getBytes(), partPictures[1].getSmallPicture()));
            assertTrue(Arrays.equals("Z-TESTPICTURE-022".getBytes(), partPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partPictures[2].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-03".getBytes(), partPictures[2].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "03").getBytes(), partPictures[2].getPicture()));


            // now delete the records
            m_uow.delete(partPictures[0]);
            m_uow.delete(partPictures[1]);
            m_uow.delete(partPictures[2]);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }



    /** Creates ZZ_TEST_PART_PICTURE records having raw and longraw elements. It then checks if the data has been added.
     */
    public void testAddRawAndLongRawUsingProxy() {
        try {
            // create 3 records
            IPartPicture partPicture = null;
            partPicture = (IPartPicture) m_uow.newPersistentInstance(IPartPicture.class);
            partPicture.setPart("Z-TESTPART-01");
            partPicture.setSmallPicture("Z-TESTSMALLPICTURE-01".getBytes());
            partPicture.setPicture((LONG_FIELD + "01").getBytes());
            m_uow.add(partPicture);

            partPicture = (IPartPicture) m_uow.newPersistentInstance(IPartPicture.class);
            partPicture.setPart("Z-TESTPART-02");
            partPicture.setSmallPicture("Z-TESTSMALLPICTURE-02".getBytes());
            partPicture.setPicture((LONG_FIELD + "02").getBytes());
            m_uow.add(partPicture);

            partPicture = (IPartPicture) m_uow.newPersistentInstance(IPartPicture.class);
            partPicture.setPart("Z-TESTPART-03");
            partPicture.setSmallPicture("Z-TESTSMALLPICTURE-03".getBytes());
            partPicture.setPicture((LONG_FIELD + "03").getBytes());
            m_uow.add(partPicture);
            m_uow.commit();


            // now check if they have been added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(IPartPicture.class.getName());
            c.addCriteria(IPartPicture.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z");
            c.addOrderBy(IPartPicture.PART, Criteria.ORDER_BY_ASC);
            Collection col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            IPartPicture[] partPictures = (IPartPicture[]) col.toArray(new IPartPicture[0]);
            assertEquals("Z-TESTPART-01", partPictures[0].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-01".getBytes(), partPictures[0].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "01").getBytes(), partPictures[0].getPicture()));

            assertEquals("Z-TESTPART-02", partPictures[1].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-02".getBytes(), partPictures[1].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "02").getBytes(), partPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partPictures[2].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-03".getBytes(), partPictures[2].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "03").getBytes(), partPictures[2].getPicture()));

            // now delete the records
            m_uow.delete(partPictures[0]);
            m_uow.delete(partPictures[1]);
            m_uow.delete(partPictures[2]);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** Creates ZZ_TEST_PART_PICTURE records having raw and longraw elements.
     * It then retrieves and updates the rows. Finally it checks if the data has been updated.
     */
    public void testUpdateRawAndLongRawUsingProxy() {
        try {
            // create 3 records
            IPartPicture partPicture = null;
            partPicture = (IPartPicture) m_uow.newPersistentInstance(IPartPicture.class);
            partPicture.setPart("Z-TESTPART-01");
            partPicture.setSmallPicture("Z-TESTSMALLPICTURE-01".getBytes());
            partPicture.setPicture((LONG_FIELD + "01").getBytes());
            m_uow.add(partPicture);

            partPicture = (IPartPicture) m_uow.newPersistentInstance(IPartPicture.class);
            partPicture.setPart("Z-TESTPART-02");
            partPicture.setSmallPicture("Z-TESTSMALLPICTURE-02".getBytes());
            partPicture.setPicture((LONG_FIELD + "02").getBytes());
            m_uow.add(partPicture);

            partPicture = (IPartPicture) m_uow.newPersistentInstance(IPartPicture.class);
            partPicture.setPart("Z-TESTPART-03");
            partPicture.setSmallPicture("Z-TESTSMALLPICTURE-03".getBytes());
            partPicture.setPicture((LONG_FIELD + "03").getBytes());
            m_uow.add(partPicture);
            m_uow.commit();


            // now check if they have been added
            m_uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(IPartPicture.class.getName());
            c.addCriteria(IPartPicture.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z");
            c.addOrderBy(IPartPicture.PART, Criteria.ORDER_BY_ASC);
            Collection col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            IPartPicture[] partPictures = (IPartPicture[]) col.toArray(new IPartPicture[0]);
            assertEquals("Z-TESTPART-01", partPictures[0].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-01".getBytes(), partPictures[0].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "01").getBytes(), partPictures[0].getPicture()));

            assertEquals("Z-TESTPART-02", partPictures[1].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-02".getBytes(), partPictures[1].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "02").getBytes(), partPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partPictures[2].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-03".getBytes(), partPictures[2].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "03").getBytes(), partPictures[2].getPicture()));



            // now update the records
            partPictures[0].setSmallPicture(null);
            partPictures[0].setPicture(null);
            partPictures[1].setSmallPicture("Z-TESTSMALLPICTURE-022".getBytes());
            partPictures[1].setPicture("Z-TESTPICTURE-022".getBytes());
            m_uow.update(partPictures[0]);
            m_uow.update(partPictures[1]);
            m_uow.commit();


            // now check if the updates were successful
            m_uow = new UOW();
            c = new Criteria();
            c.setTable(IPartPicture.class.getName());
            c.addCriteria(IPartPicture.PART, Criteria.RELATIONAL_BEGINS_WITH, "Z");
            c.addOrderBy(IPartPicture.PART, Criteria.ORDER_BY_ASC);
            col = m_uow.query(c);
            // fetch in all the records
            for (Iterator i = col.iterator(); i.hasNext();)
                i.next();
            assertEquals(3, col.size());
            partPictures = (IPartPicture[]) col.toArray(new IPartPicture[0]);
            assertEquals("Z-TESTPART-01", partPictures[0].getPart());
            assertNull(partPictures[0].getSmallPicture());
            assertNull(partPictures[0].getPicture());

            assertEquals("Z-TESTPART-02", partPictures[1].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-022".getBytes(), partPictures[1].getSmallPicture()));
            assertTrue(Arrays.equals("Z-TESTPICTURE-022".getBytes(), partPictures[1].getPicture()));

            assertEquals("Z-TESTPART-03", partPictures[2].getPart());
            assertTrue(Arrays.equals("Z-TESTSMALLPICTURE-03".getBytes(), partPictures[2].getSmallPicture()));
            assertTrue(Arrays.equals((LONG_FIELD + "03").getBytes(), partPictures[2].getPicture()));


            // now delete the records
            m_uow.delete(partPictures[0]);
            m_uow.delete(partPictures[1]);
            m_uow.delete(partPictures[2]);
            m_uow.commit();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
