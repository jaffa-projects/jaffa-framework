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

/* Tests the addSpecial(), updateSpecial() and deleteSpecial() methods of the UOW class.
 */

package org.jaffa.persistence.blackboxtests;

import junit.framework.TestCase;
import org.jaffa.persistence.domainobjects.*;
import org.jaffa.persistence.UOW;
import junit.framework.Test;
import junit.framework.TestSuite;

/** Tests for performing inserts through the Jaffa Persistence Engine.
 *
 * @author GautamJ
 */
public class SpecialAddUpdateDeleteTest extends TestCase {
    
    private UOW m_uow = null;
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(SpecialAddUpdateDeleteTest.class));
    }
    
    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public SpecialAddUpdateDeleteTest(String name) {
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
    
    
    /** Inserts a record into Item using add() and addSpecial() methods.
     * The receivedItemId field, which is set in the preAdd() method of Item, should get stamped during the add() invocation only.
     */
    public void testAdd() {
        createItem(false);
        createItem(true);
    }
    
    /** Creates an Item record and updates it using the update() and updateSpecial() methods.
     * The receivedItemId field, which is set in the preUpdate() method of Item, should get stamped during the update() invocation only.
     */
    public void testUpdate() {
        updateItem(false);
        updateItem(true);
    }
    
    /** Creates an Item record and deletes it using the delete() and deleteSpecial() methods.
     * The receivedItemId field, which is set in the preDelete() method of Item, should get stamped during the delete() invocation only.
     */
    public void testDelete() {
        deleteItem(false);
        deleteItem(true);
    }
    
    
    
    /** Inserts a record into Item using add() and addSpecial() methods.
     * The receivedItemId field, which is set in the preAdd() method of Item, should get stamped during the add() invocation only.
     */
    private void createItem(boolean useSpecial) {
        try {
            String itemId = "Z-TESTITEM-04";
            if (m_uow == null || !m_uow.isActive())
                m_uow = new UOW();
            
            // Create an item using the add() or addSpecial() methods
            Item obj = (Item) m_uow.newPersistentInstance(Item.class);
            obj.setItemId(itemId);
            if (!useSpecial)
                m_uow.add(obj);
            else
                m_uow.addSpecial(obj);
            m_uow.commit();
            
            // Now retrieve the added record & check if the receivedItemId got stamped for the add() case only
            m_uow = new UOW();
            obj = Item.findByPK(m_uow, itemId);
            if (!useSpecial)
                assertEquals("ADD" + itemId, obj.getReceivedItemId());
            else
                assertNull("The preAdd trigger should not have been fired in the special case", obj.getReceivedItemId());
            
            // Now delete the object
            m_uow.delete(obj);
            m_uow.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** Creates an Item record and updates it using the update() and updateSpecial() methods.
     * The receivedItemId field, which is set in the preUpdate() method of Item, should get stamped during the update() invocation only.
     */
    private void updateItem(boolean useSpecial) {
        try {
            String itemId = "Z-TESTITEM-04";
            if (m_uow == null || !m_uow.isActive())
                m_uow = new UOW();
            
            // Create an item
            Item obj = (Item) m_uow.newPersistentInstance(Item.class);
            obj.setItemId(itemId);
            m_uow.add(obj);
            m_uow.commit();
            
            // update the item using update() or updateSpecial() methods
            m_uow = new UOW();
            if (!useSpecial) {
                // retrieve the item, and update it
                obj = Item.findByPK(m_uow, itemId);
                obj.setReceivedItemId(null);
                m_uow.update(obj);
            } else {
                // the updateSpecial should work well on both retrieved or 'new' objects
                obj = (Item) m_uow.newPersistentInstance(Item.class);
                obj.setItemId(itemId);
                obj.setReceivedItemId(null);
                m_uow.updateSpecial(obj);
            }
            m_uow.commit();
            
            // Now retrieve the updated record & check if the receivedItemId got stamped for the update() case only
            m_uow = new UOW();
            obj = Item.findByPK(m_uow, itemId);
            if (!useSpecial)
                assertEquals("UPD" + itemId, obj.getReceivedItemId());
            else
                assertNull("The preUpdate trigger should not have been fired in the special case", obj.getReceivedItemId());
            
            // Now delete the object
            m_uow.delete(obj);
            m_uow.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** Creates an Item record and deletes it using the delete() and deleteSpecial() methods.
     * The receivedItemId field, which is set in the preDelete() method of Item, should get stamped during the delete() invocation only.
     */
    private void deleteItem(boolean useSpecial) {
        try {
            String itemId = "Z-TESTITEM-04";
            if (m_uow == null || !m_uow.isActive())
                m_uow = new UOW();
            
            // Create an item
            Item obj = (Item) m_uow.newPersistentInstance(Item.class);
            obj.setItemId(itemId);
            m_uow.add(obj);
            m_uow.commit();
            
            // delete the item using delete() or deleteSpecial() methods
            m_uow = new UOW();
            if (!useSpecial) {
                // retrieve the item, and delete it
                obj = Item.findByPK(m_uow, itemId);
                obj.setReceivedItemId(null);
                m_uow.delete(obj);
            } else {
                // the deleteSpecial should work well on both retrieved or 'new' objects
                obj = (Item) m_uow.newPersistentInstance(Item.class);
                obj.setItemId(itemId);
                obj.setReceivedItemId(null);
                m_uow.deleteSpecial(obj);
            }
            m_uow.commit();
            
            // For the purpose of this test, lets check the attributes of the object, even though it has been deleted from the database
            // Check if the receivedItemId got stamped for the update() case only
            if (!useSpecial)
                assertEquals("DEL" + itemId, obj.getReceivedItemId());
            else
                assertNull("The preDelete trigger should not have been fired in the special case", obj.getReceivedItemId());
            
            
            // Ensure that the object has been deleted
            m_uow = new UOW();
            obj = Item.findByPK(m_uow, itemId);
            assertNull("The item record should have been deleted", obj);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
}
