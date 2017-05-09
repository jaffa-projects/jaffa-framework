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
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.Criteria;
import java.util.*;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 * @author GautamJ
 */
public class PerformanceTest extends TestCase {
    
    private UOW m_uow = null;
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(PerformanceTest.class));
    }
    
    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public PerformanceTest(String name) {
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
    
    /** This will first create 1000 item records.
     * It will then read them 5 times and display the average read time.
     */
    public void testRead1000() {
        try {
            read(1000, 5);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** It will create 1000 Item records 5 times and display the average write time.
     */
    public void testWrite1000() {
        try {
            write(1000, 5);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** This will first create 10000 item records.
     * It will then read them 5 times and display the average read time.
     */
    public void testRead10000() {
        try {
            read(10000, 5);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** It will create 10000 Item records 5 times and display the average write time.
     */
    public void testWrite10000() {
        try {
            write(10000, 5);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    
    
    
    private void read(int recordSet, int cycles) throws Exception {
        long time = 0;
        
        // ensure no records exist
        deleteItems("Z-TEST-PERF");
        
        // create records
        createItems("Z-TEST-PERF", recordSet);
        
        // read the Record Set 'n' times to calculate the average read-time
        for (int i = 0; i < cycles; i++)
            time += readItems("Z-TEST-PERF", recordSet);
        
        // delete the record set
        deleteItems("Z-TEST-PERF");
        
        // display the average read time
        System.out.println("Average Time for reading " + recordSet + " records = " + time/cycles + " ms");
    }
    
    private void write(int recordSet, int cycles) throws Exception {
        long time = 0;
        
        // create the Record Set 'n' times to calculate the average write-time
        for (int i = 0; i < cycles; i++) {
            // ensure no records exist
            deleteItems("Z-TEST-PERF");
            
            // create records
            time += createItems("Z-TEST-PERF", recordSet);
        }
        
        // delete the record set
        deleteItems("Z-TEST-PERF");
        
        // display the average write time
        System.out.println("Average Time for writing " + recordSet + " records = " + time/cycles + " ms");
    }
    
    /** Deletes Item records having item_id like 'itemIdBeginsWith%'.
     * This will commit the UOW and then acquire a new UOW.
     */
    private void deleteItems(String itemIdBeginsWith) throws Exception {
        Criteria c = new Criteria();
        c.setTable(ItemMeta.getName());
        c.addCriteria(ItemMeta.ITEM_ID, Criteria.RELATIONAL_BEGINS_WITH, itemIdBeginsWith);
        for (Iterator itr = m_uow.query(c).iterator(); itr.hasNext(); ) {
            Item item = (Item) itr.next();
            m_uow.delete(item);
        }
        
        // commit the exiting UOW and create a new one
        m_uow.commit();
        m_uow = new UOW();
    }
    
    /** Creates Item records (no. of records = recordSet) having item_id like 'itemIdBeginsWith-N'.
     * This will commit the UOW and then acquire a new UOW.
     * It will return the time required to create the record set.
     */
    private long createItems(String itemIdBeginsWith, int recordSet) throws Exception {
        long time = System.currentTimeMillis();
        for (int i = 0; i < recordSet; i++) {
            Item item = (Item) m_uow.newPersistentInstance(Item.class);
            item.updateItemId(itemIdBeginsWith + i);
            m_uow.add(item);
        }
        
        // commit the exiting UOW and create a new one
        m_uow.commit();
        time = System.currentTimeMillis() - time;
        
        m_uow = new UOW();
        return time;
    }
    
    /** Reads Item records having item_id like 'itemIdBeginsWith%'.
     * An assertion error will be thrown if the no. of records retrieved does not match the input recordSet.
     * It will return the time required to read the record set.
     */
    private long readItems(String itemIdBeginsWith, int recordSet) throws Exception {
        int count = 0;
        long time = System.currentTimeMillis();
        Criteria c = new Criteria();
        c.setTable(ItemMeta.getName());
        c.addCriteria(ItemMeta.ITEM_ID, Criteria.RELATIONAL_BEGINS_WITH, itemIdBeginsWith);
        for (Iterator itr = m_uow.query(c).iterator(); itr.hasNext(); ) {
            Item item = (Item) itr.next();
            ++count;
        }
        time = System.currentTimeMillis() - time;
        assertEquals(count, recordSet);
        return time;
    }
    
    
    
}
