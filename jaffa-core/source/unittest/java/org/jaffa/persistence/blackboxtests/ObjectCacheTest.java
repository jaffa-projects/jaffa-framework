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

/* Tests the object-cache feature of the persistence engine.
 */
package org.jaffa.persistence.blackboxtests;

import java.util.Iterator;
import junit.framework.TestCase;
import org.jaffa.persistence.domainobjects.*;
import org.jaffa.persistence.UOW;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jaffa.persistence.Criteria;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.StringHelper;

/** Tests for performing inserts through the Jaffa Persistence Engine.
 *
 * @author GautamJ
 */
public class ObjectCacheTest extends TestCase {

    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(ObjectCacheTest.class));
    }

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public ObjectCacheTest(String name) {
        super(name);
    }

    /** Tests if the key-based retrieval of data brings up cached objects.
     */
    public void testCaching() {
        UOW uow = null;
        final String prefix = "Z-CACHE-";
        final int count = 5;
        final int pad = ("" + count).length();
        try {
            uow = new UOW();

            // Create CategoryOfInstrument records
            createCategoryOfInstrument(uow, prefix, count);

            // Read the CategoryOfInstrument records by non-key query
            CategoryOfInstrument[] round1 = new CategoryOfInstrument[count];
            Criteria c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, Criteria.RELATIONAL_BEGINS_WITH, prefix);
            c.addOrderBy(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT);
            Iterator itr = uow.query(c).iterator();
            for (int i = 0; i < count; i++) {
                String expected = prefix + StringHelper.pad(i + 1, pad);
                assertTrue("CategoryOfInstrument '" + expected + "' not found", itr.hasNext());
                CategoryOfInstrument obj = (CategoryOfInstrument) itr.next();
                assertEquals(expected, obj.getCategoryInstrument());
                round1[i] = obj;
            }
            assertTrue("No more CategoryOfInstrument records should have been retrieved", !itr.hasNext());

            // Read the CategoryOfInstrument records again by non-key query
            // the 2 sets of objects should not have the same identity
            CategoryOfInstrument[] round2 = new CategoryOfInstrument[count];
            c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, Criteria.RELATIONAL_BEGINS_WITH, prefix);
            c.addOrderBy(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT);
            itr = uow.query(c).iterator();
            for (int i = 0; i < count; i++) {
                String expected = prefix + StringHelper.pad(i + 1, pad);
                assertTrue("CategoryOfInstrument '" + expected + "' not found", itr.hasNext());
                CategoryOfInstrument obj = (CategoryOfInstrument) itr.next();
                assertEquals(expected, obj.getCategoryInstrument());
                round2[i] = obj;
            }
            assertTrue("No more CategoryOfInstrument records should have been retrieved", !itr.hasNext());
            for (int i = 0; i < count; i++) {
                assertTrue(round1[i].equals(round2[i]));
                assertTrue(round1[i] != round2[i]);
            }

            // Now retrieve the next set of objects by their primary-key
            // This set of objects should match the previous set exactly
            CategoryOfInstrument[] round3 = new CategoryOfInstrument[count];
            for (int i = 0; i < count; i++) {
                String key = prefix + StringHelper.pad(i + 1, pad);
                CategoryOfInstrument obj = CategoryOfInstrument.findByPK(uow, key);
                round3[i] = obj;
            }
            for (int i = 0; i < count; i++) {
                assertTrue(round2[i].equals(round3[i]));
                assertTrue(round2[i] == round3[i]);
            }

            // Delete CategoryOfInstrument records
            deleteCategoryOfInstrument(uow, prefix);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            try {
                if (uow != null)
                    uow.rollback();
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }
    }

    /** Tests if caching can be disabled.
     */
    public void testDisableCaching() {
        UOW uow = null;
        final String prefix = "Z-CACHE-";
        final int count = 5;
        final int pad = ("" + count).length();
        try {
            // Disable object caching
            ContextManagerFactory.instance().setThreadContext(null);
            ContextManagerFactory.instance().setProperty("jaffa.persistence.jdbcengine.disableObjectCache", "true");

            uow = new UOW();

            // Create CategoryOfInstrument records
            createCategoryOfInstrument(uow, prefix, count);

            // Read the CategoryOfInstrument records by non-key query
            CategoryOfInstrument[] round1 = new CategoryOfInstrument[count];
            Criteria c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, Criteria.RELATIONAL_BEGINS_WITH, prefix);
            c.addOrderBy(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT);
            Iterator itr = uow.query(c).iterator();
            for (int i = 0; i < count; i++) {
                String expected = prefix + StringHelper.pad(i + 1, pad);
                assertTrue("CategoryOfInstrument '" + expected + "' not found", itr.hasNext());
                CategoryOfInstrument obj = (CategoryOfInstrument) itr.next();
                assertEquals(expected, obj.getCategoryInstrument());
                round1[i] = obj;
            }
            assertTrue("No more CategoryOfInstrument records should have been retrieved", !itr.hasNext());

            // Read the CategoryOfInstrument records again by non-key query
            // the 2 sets of objects should not have the same identity
            CategoryOfInstrument[] round2 = new CategoryOfInstrument[count];
            c = new Criteria();
            c.setTable(CategoryOfInstrumentMeta.getName());
            c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, Criteria.RELATIONAL_BEGINS_WITH, prefix);
            c.addOrderBy(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT);
            itr = uow.query(c).iterator();
            for (int i = 0; i < count; i++) {
                String expected = prefix + StringHelper.pad(i + 1, pad);
                assertTrue("CategoryOfInstrument '" + expected + "' not found", itr.hasNext());
                CategoryOfInstrument obj = (CategoryOfInstrument) itr.next();
                assertEquals(expected, obj.getCategoryInstrument());
                round2[i] = obj;
            }
            assertTrue("No more CategoryOfInstrument records should have been retrieved", !itr.hasNext());
            for (int i = 0; i < count; i++) {
                assertTrue(round1[i].equals(round2[i]));
                assertTrue(round1[i] != round2[i]);
            }

            // Now retrieve the next set of objects by their primary-key
            // the 2 sets of objects should again not have the same identity
            CategoryOfInstrument[] round3 = new CategoryOfInstrument[count];
            for (int i = 0; i < count; i++) {
                String key = prefix + StringHelper.pad(i + 1, pad);
                CategoryOfInstrument obj = CategoryOfInstrument.findByPK(uow, key);
                round3[i] = obj;
            }
            for (int i = 0; i < count; i++) {
                assertTrue(round2[i].equals(round3[i]));
                assertTrue(round2[i] != round3[i]);
            }

            // Delete CategoryOfInstrument records
            deleteCategoryOfInstrument(uow, prefix);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            try {
                if (uow != null)
                    uow.rollback();
            } catch (Exception e) {
                e.printStackTrace();
                fail();
            }
        }
    }

    private void createCategoryOfInstrument(UOW uow, String prefix, int count) throws Exception {
        int pad = ("" + count).length();
        for (int i = 1; i <= count; i++) {
            CategoryOfInstrument obj = (CategoryOfInstrument) uow.newPersistentInstance(CategoryOfInstrument.class);
            String ci = prefix + StringHelper.pad(i, pad);
            obj.setCategoryInstrument(ci);
            obj.setDescription("TEST PAGING");
            uow.add(obj);
        }
    }

    private void deleteCategoryOfInstrument(UOW uow, String prefix) throws Exception {
        Criteria c = new Criteria();
        c.setTable(CategoryOfInstrumentMeta.getName());
        c.addCriteria(CategoryOfInstrumentMeta.CATEGORY_INSTRUMENT, Criteria.RELATIONAL_BEGINS_WITH, prefix);
        for (Iterator i = uow.query(c).iterator(); i.hasNext();)
            uow.delete(i.next());
    }
}
