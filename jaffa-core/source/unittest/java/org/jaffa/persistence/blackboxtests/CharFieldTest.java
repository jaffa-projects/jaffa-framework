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

import helpers.ConnectionHelper;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.domainobjects.PartAdditional;
import org.jaffa.persistence.domainobjects.PartAdditionalMeta;
import org.jaffa.persistence.domainobjects.PartMeta;

/** Tests for performing various operations on CHAR fields.
 *
 * @author GautamJ
 */
public class CharFieldTest extends TestCase {
    
    private static final String PART = "Z-TESTPART-01";
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(CharFieldTest.class));
    }
    
    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public CharFieldTest(String name) {
        super(name);
    }
    
    /** Inserts a new record.
     */
    public void testAdd() {
        UOW uow = null;
        try {
            uow = new UOW();
            
            // Create a new object
            PartAdditional object = (PartAdditional) uow.newPersistentInstance(PartAdditional.class);
            object.setPart(PART);
            object.setField1(" "); // enter at least a blank string, since the field is mandatory
            uow.add(object);
            uow.commit();
            
            // Now ensure that the record got created
            uow = new UOW();
            object = PartAdditional.findByPK(uow, PART);
            assertNotNull("A PartAdditional object should have been created", object);
            assertEquals(PART, object.getPart());
            assertNull(object.getField1());
            assertNull(object.getField2());
            assertNull(object.getField3());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            try {
                if (uow != null)
                    uow.rollback();
            } catch (Exception e) {
                // do nothing
            }
            cleanupData(PART);
        }
    }
    
    /** Updates a record.
     */
    public void testUpdate() {
        UOW uow = null;
        try {
            uow = new UOW();
            
            // Create a new object
            PartAdditional object = (PartAdditional) uow.newPersistentInstance(PartAdditional.class);
            object.setPart(PART);
            object.setField1(" "); // enter at least a blank string, since the field is mandatory
            uow.add(object);
            uow.commit();
            
            // Now ensure that the record got created
            uow = new UOW();
            object = PartAdditional.findByPK(uow, PART);
            assertNotNull("A PartAdditional object should have been created", object);
            assertEquals(PART, object.getPart());
            
            // Update the object
            object.setField1("NEW VALUE");
            object.setField2("");
            object.setField3("  ");
            uow.update(object);
            uow.commit();
            
            // Now ensure that the record got updated
            uow = new UOW();
            object = PartAdditional.findByPK(uow, PART);
            assertNotNull("A PartAdditional object should have been updated", object);
            assertEquals(PART, object.getPart());
            assertEquals("NEW VALUE", object.getField1());
            assertNull(object.getField2());
            assertNull(object.getField3());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            try {
                if (uow != null)
                    uow.rollback();
            } catch (Exception e) {
                // do nothing
            }
            cleanupData(PART);
        }
    }
    
    /** Deletes a new record.
     */
    public void testDelete() {
        UOW uow = null;
        try {
            uow = new UOW();
            
            // Create a new object
            PartAdditional object = (PartAdditional) uow.newPersistentInstance(PartAdditional.class);
            object.setPart(PART);
            object.setField1(" "); // enter at least a blank string, since the field is mandatory
            uow.add(object);
            uow.commit();
            
            // Now ensure that the record got created
            uow = new UOW();
            object = PartAdditional.findByPK(uow, PART);
            assertNotNull("A PartAdditional object should have been created", object);
            assertEquals(PART, object.getPart());
            
            // Delete the bugger
            uow.delete(object);
            uow.commit();
            
            // Now ensure that the record got deleted
            uow = new UOW();
            object = PartAdditional.findByPK(uow, PART);
            assertNull("A PartAdditional object should have been deleted", object);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            try {
                if (uow != null)
                    uow.rollback();
            } catch (Exception e) {
                // do nothing
            }
            cleanupData(PART);
        }
    }
    
    /** Tests the sql:
     * Select * from zz_jut_part_additional Where exists
     * (select 1 from zz_jut_part Where zz_jut_part.part = trim(zz_jut_part_additional.part))
     * This should return 1 record
     */
    public void testJoinQueryBetweenCharAndVarchar() {
        UOW uow = null;
        try {
            uow = new UOW();
            
            // Create a new object
            PartAdditional object = (PartAdditional) uow.newPersistentInstance(PartAdditional.class);
            object.setPart(PART);
            object.setField1(" "); // enter at least a blank string, since the field is mandatory
            uow.add(object);
            uow.commit();
            
            // Perform  a join between PartAdditional and Part
            uow = new UOW();
            Criteria c1 = new Criteria();
            c1.setTable(PartMeta.getName());
            c1.addInnerCriteria(PartMeta.PART, PartAdditionalMeta.PART);
            
            Criteria c2 = new Criteria();
            c2.setTable(PartAdditionalMeta.getName());
            c2.addAggregate(c1);
            
            Collection col = uow.query(c2);
            assertEquals("One record should have been retrieved", 1, col.size());
            object = (PartAdditional) col.iterator().next();
            assertEquals(PART, object.getPart());
            assertNull(object.getField1());
            assertNull(object.getField2());
            assertNull(object.getField3());
            
            
            // Now lets do a join between PartAdditional and Part, and a non-existent record
            c1 = new Criteria();
            c1.setTable(PartMeta.getName());
            c1.addInnerCriteria(PartMeta.PART, PartAdditionalMeta.PART);
            
            c2 = new Criteria();
            c2.setTable(PartAdditionalMeta.getName());
            c2.addAggregate(c1);
            c2.addCriteria(PartAdditionalMeta.PART, "NON-EXISTENT-PART");
            
            col = uow.query(c2);
            assertEquals("No record should have been retrieved", 0, col.size());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            try {
                if (uow != null)
                    uow.rollback();
            } catch (Exception e) {
                // do nothing
            }
            cleanupData(PART);
        }
    }
    
    /** Tests the sql:
     * Select * from zz_jut_part_additional where trim(part) like '%01'
     * This should return 1 record
     */
    public void testEndsWithQuery() {
        UOW uow = null;
        try {
            uow = new UOW();
            
            // Create a new object
            PartAdditional object = (PartAdditional) uow.newPersistentInstance(PartAdditional.class);
            object.setPart(PART);
            object.setField1(" "); // enter at least a blank string, since the field is mandatory
            uow.add(object);
            uow.commit();
            
            // Perform a query using the EndsWith clause
            uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(PartAdditionalMeta.getName());
            c.addCriteria(PartAdditionalMeta.PART, Criteria.RELATIONAL_ENDS_WITH, "01");
            
            Collection col = uow.query(c);
            assertEquals("One record should have been retrieved", 1, col.size());
            object = (PartAdditional) col.iterator().next();
            assertEquals(PART, object.getPart());
            assertNull(object.getField1());
            assertNull(object.getField2());
            assertNull(object.getField3());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            try {
                if (uow != null)
                    uow.rollback();
            } catch (Exception e) {
                // do nothing
            }
            cleanupData(PART);
        }
    }
    
    /** Tests the sql:
     * Select * from zz_jut_part_additional where field1 is null or field1 = ' '
     * This should return 1 record
     */
    public void testNullQuery() {
        UOW uow = null;
        try {
            uow = new UOW();
            
            // Create a new object
            PartAdditional object = (PartAdditional) uow.newPersistentInstance(PartAdditional.class);
            object.setPart(PART);
            object.setField1(" "); // enter at least a blank string, since the field is mandatory
            object.setField2("A VALUE");
            uow.add(object);
            uow.commit();
            
            // Retrieve records where field1 is null. Should get 1 record
            uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(PartAdditionalMeta.getName());
            c.addCriteria(PartAdditionalMeta.FIELD1, Criteria.RELATIONAL_IS_NULL);
            Collection col = uow.query(c);
            assertEquals("One record should have been retrieved", 1, col.size());
            object = (PartAdditional) col.iterator().next();
            assertEquals(PART, object.getPart());
            assertNull(object.getField1());
            assertEquals("A VALUE", object.getField2());
            assertNull(object.getField3());
            
            // Retrieve records where field2 is null. Nothing should be retrieved
            uow = new UOW();
            c = new Criteria();
            c.setTable(PartAdditionalMeta.getName());
            c.addCriteria(PartAdditionalMeta.FIELD2, Criteria.RELATIONAL_IS_NULL);
            col = uow.query(c);
            assertEquals("No record should have been retrieved", 0, col.size());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            try {
                if (uow != null)
                    uow.rollback();
            } catch (Exception e) {
                // do nothing
            }
            cleanupData(PART);
        }
    }
    
    /** Tests the sql:
     * Select * from zz_jut_part_additional where field1 is not null and field1 != ' '
     * This should return 1 record
     */
    public void testNotNullQuery() {
        UOW uow = null;
        try {
            uow = new UOW();
            
            // Create a new object
            PartAdditional object = (PartAdditional) uow.newPersistentInstance(PartAdditional.class);
            object.setPart(PART);
            object.setField1(" "); // enter at least a blank string, since the field is mandatory
            object.setField2("A VALUE");
            uow.add(object);
            uow.commit();
            
            // Retrieve records where field2 is not null. Should get 1 record
            uow = new UOW();
            Criteria c = new Criteria();
            c.setTable(PartAdditionalMeta.getName());
            c.addCriteria(PartAdditionalMeta.FIELD2, Criteria.RELATIONAL_IS_NOT_NULL);
            Collection col = uow.query(c);
            assertEquals("One record should have been retrieved", 1, col.size());
            object = (PartAdditional) col.iterator().next();
            assertEquals(PART, object.getPart());
            assertNull(object.getField1());
            assertEquals("A VALUE", object.getField2());
            assertNull(object.getField3());
            
            // Retrieve records where field1 is not null. Nothing should be retrieved
            uow = new UOW();
            c = new Criteria();
            c.setTable(PartAdditionalMeta.getName());
            c.addCriteria(PartAdditionalMeta.FIELD1, Criteria.RELATIONAL_IS_NOT_NULL);
            col = uow.query(c);
            assertEquals("No record should have been retrieved", 0, col.size());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            try {
                if (uow != null)
                    uow.rollback();
            } catch (Exception e) {
                // do nothing
            }
            cleanupData(PART);
        }
    }
    
    /** This will delete the records that get created by the test cases
     */
    private void cleanupData(String part) {
        Connection connection = null;
        try {
            connection = ConnectionHelper.getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String sql = null;
            
            sql = "delete from zz_jut_part_additional where part = '" + part + "'";
            statement.execute(sql);
            
            connection.commit();
            connection.close();
            connection = null;
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to cleanup the data: " + e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Failed to cleanup the data: " + e.toString());
                }
            }
        }
    }
    
}
