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
import org.jaffa.persistence.*;
import java.util.*;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jaffa.persistence.exceptions.*;

/** Tests for domain relationships through the Jaffa Persistence Engine.
 *
 * @author GautamJ
 */
public class RelationshipTest extends TestCase {
    
    private UOW m_uow = null;
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(RelationshipTest.class));
    }

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public RelationshipTest(String name) {
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
    
    
    /** This will try to create a PartRemarks object with a non-existent part. It should fail.
     * However, creating a PartRemarks object with an existent part should be successful.
     * Note: PartRemarks has a '0..1 - to - 1 association' relationship with PartPicture.
     */
    public void testValidationOfMandatoryOneObject() {
        try {
            // create a PartRemarks with an invalid Part
            PartRemarks partRemarks = new PartRemarks();
            partRemarks.setPart("Z-TESTPART-02ZZZ");
            partRemarks.setRemarks("Some Remarks");
            try {
                m_uow.add(partRemarks);
                fail("Addition of a PartRemarks object with an invalid Part should have failed.");
            } catch (AddFailedException e) {
                // expected error
            }
            
            // Now create it with a valid Part
            partRemarks.setPart("Z-TESTPART-02");
            m_uow.add(partRemarks);
            
            // Check if the PartRemarks got created
            assertTrue("PartRemarks could not be created", PartRemarks.exists(m_uow, "Z-TESTPART-02"));
            partRemarks = PartRemarks.findByPK(m_uow, "Z-TESTPART-02");
            assertEquals("Z-TESTPART-02", partRemarks.getPart());
            assertEquals("Some Remarks", partRemarks.getRemarks());
            assertNotNull("The PartRemarks should have a reference to the related Part object", partRemarks.getPartObject());
            assertEquals("Z-TESTPART-02", partRemarks.getPartObject().getPart());
            
            // Finally delete the PartRemarks
            m_uow.delete(partRemarks);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** This will create a PartRemarks object and then delete it. The delete should not affect the related Part object.
     * Note: PartRemarks has a '0..1 - to - 1 association' relationship with PartPicture.
     */
    public void testMandatoryOneObjectUntouchedOnDelete() {
        try {
            // create a PartRemarks with a valid Part
            PartRemarks partRemarks = new PartRemarks();
            partRemarks.setPart("Z-TESTPART-02");
            partRemarks.setRemarks("Some Remarks");
            m_uow.add(partRemarks);
            
            
            // Check if the PartRemarks got created
            assertTrue("PartRemarks could not be created", PartRemarks.exists(m_uow, "Z-TESTPART-02"));
            partRemarks = PartRemarks.findByPK(m_uow, "Z-TESTPART-02");
            assertEquals("Z-TESTPART-02", partRemarks.getPart());
            assertEquals("Some Remarks", partRemarks.getRemarks());
            assertNotNull("The PartRemarks should have a reference to the related Part object", partRemarks.getPartObject());
            assertEquals("Z-TESTPART-02", partRemarks.getPartObject().getPart());
            
            // Now delete the PartRemarks
            m_uow.delete(partRemarks);
            
            // Check if the PartRemarks got deleted and that the related Part object is still existing
            assertTrue("PartRemarks should have been deleted", !PartRemarks.exists(m_uow, "Z-TESTPART-02"));
            assertTrue("Part object should still exist even after a related PartRemarks object is deleted", Part.exists(m_uow, "Z-TESTPART-02"));
            
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** This will create a Part object. It should proceed successfully without the need for a PartRemarks object.
     * Note: Part has a '1 - to - 0..1 association' relationship with PartRemarks.
     */
    public void testNonValidationOfOptionalOneObject() {
        try {
            // create a Part object
            Part part = new Part();
            part.setPart("Z-TESTPART-02ZZZ");
            part.setNoun("Z-TESTNOUN-02ZZZ");
            m_uow.add(part);
            
            // check the Part got created and without the need for the PartRemarks
            assertTrue("Part should have been created", Part.exists(m_uow, "Z-TESTPART-02ZZZ"));
            part = Part.findByPK(m_uow, "Z-TESTPART-02ZZZ");
            assertEquals("Z-TESTPART-02ZZZ", part.getPart());
            assertEquals("Z-TESTNOUN-02ZZZ", part.getNoun());
            assertNull("Ensure that the PartRemarks was never present", part.getPartRemarksObject());
            
            // Now delete the part
            m_uow.delete(part);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** This will create a Part object and a related PartRemarks object.
     * The delete of the Part object will fail, unless the PartRemarks is deleted first.
     * Note: Part has a '1 - to - 0..1 association' relationship with PartRemarks.
     */
    public void testRestrictConstraintOfOptionalOneObject() {
        try {
            // create a Part object
            Part part = new Part();
            part.setPart("Z-TESTPART-02ZZZ");
            part.setNoun("Z-TESTNOUN-02ZZZ");
            m_uow.add(part);
            
            // check the Part got created and no PartRemarks exists
            assertTrue("Part should have been created", Part.exists(m_uow, "Z-TESTPART-02ZZZ"));
            part = Part.findByPK(m_uow, "Z-TESTPART-02ZZZ");
            assertEquals("Z-TESTPART-02ZZZ", part.getPart());
            assertEquals("Z-TESTNOUN-02ZZZ", part.getNoun());
            assertNull("Ensure that the PartRemarks was never present", part.getPartRemarksObject());
            
            // Now create a related PartRemarks object
            PartRemarks partRemarks = part.newPartRemarksObject();
            partRemarks.setRemarks("Z-TESTPART-02ZZZ-Remarks");
            m_uow.add(partRemarks);
            
            // Check if the PartRemarks got created
            assertTrue("PartRemarks should have been created", PartRemarks.exists(m_uow, "Z-TESTPART-02ZZZ"));
            partRemarks = PartRemarks.findByPK(m_uow, "Z-TESTPART-02ZZZ");
            assertEquals("Z-TESTPART-02ZZZ", partRemarks.getPart());
            assertEquals("Z-TESTPART-02ZZZ-Remarks", partRemarks.getRemarks());
            
            // Try to delete the part.. it should fail
            try {
                m_uow.delete(part);
                fail("Delete of the part should have failed since the related PartRemarks exists");
            } catch (DeleteFailedException e) {
                // this is expected
            }
            
            // Delete the related PartRemarks
            m_uow.delete(partRemarks);
            
            // Now the delete of Part should be successful
            m_uow.delete(Part.findByPK(m_uow, "Z-TESTPART-02ZZZ"));
            
            // NOTE: We cannot use the 'm_uow.delete(part)', since the part instance has a cached PartRemarks object.
            // As per the current design, this cache is not cleared automtically when the related-object is deleted.
            
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** This will create a Part object and a related PartPicture object.
     * The delete of the Part object will implicitly delete the PartPicture object too.
     * Note: Part has a '1 - to - 0..1 composition' relationship with PartPicture.
     */
    public void testCascadeDeleteOfOptionalOneObject() {
        try {
            // create a Part object
            Part part = new Part();
            part.setPart("Z-TESTPART-02ZZZ");
            part.setNoun("Z-TESTNOUN-02ZZZ");
            m_uow.add(part);
            
            // check the Part got created and no PartPicture exists
            assertTrue("Part should have been created", Part.exists(m_uow, "Z-TESTPART-02ZZZ"));
            part = Part.findByPK(m_uow, "Z-TESTPART-02ZZZ");
            assertEquals("Z-TESTPART-02ZZZ", part.getPart());
            assertEquals("Z-TESTNOUN-02ZZZ", part.getNoun());
            assertNull("Ensure that the PartPicture was never present", part.getPartPictureObject());
            
            // Now create a related PartPicture object
            PartPicture partPicture = part.newPartPictureObject();
            partPicture.setPicture("picture".getBytes());
            m_uow.add(partPicture);
            
            // Check if the PartPicture got created
            assertTrue("PartPicture should have been created", PartPicture.exists(m_uow, "Z-TESTPART-02ZZZ"));
            partPicture = PartPicture.findByPK(m_uow, "Z-TESTPART-02ZZZ");
            assertEquals("Z-TESTPART-02ZZZ", partPicture.getPart());
            assertTrue("The Picture bytes do not match the expected bytes", Arrays.equals("picture".getBytes(), partPicture.getPicture()));
            
            // Delete the part.. it should delete the related PartPicture too
            m_uow.delete(part);
            
            // Check the Part and PartPicture got deleted
            assertTrue("Part should have been deleted", !Part.exists(m_uow, "Z-TESTPART-02ZZZ"));
            assertTrue("PartPicture should have been deleted", !PartPicture.exists(m_uow, "Z-TESTPART-02ZZZ"));
            
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** This will create a Part object. It should be proceed successfully without the need for an Item object.
     * Note: Part has a '1 - to - 0..* composition' relationship with Item.
     */
    public void testNonValidationOfManyObject() {
        try {
            // create a Part object
            Part part = new Part();
            part.setPart("Z-TESTPART-02ZZZ");
            part.setNoun("Z-TESTNOUN-02ZZZ");
            m_uow.add(part);
            
            // check the Part got created and no Item exists
            assertTrue("Part should have been created", Part.exists(m_uow, "Z-TESTPART-02ZZZ"));
            part = Part.findByPK(m_uow, "Z-TESTPART-02ZZZ");
            assertEquals("Z-TESTPART-02ZZZ", part.getPart());
            assertEquals("Z-TESTNOUN-02ZZZ", part.getNoun());
            assertEquals("No related items should have existed", 0, part.getItemArray().length);
            
            // delete of Part
            m_uow.delete(part);
            
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** This will create a CategoryOfInstrument object and a few related Part objects.
     * The delete of the CategoryOfInstrument object will fail, unless the related Part objects are first deleted.
     * Note: CategoryOfInstrument has a '1 - to - 0..* association' relationship with Part.
     */
    public void testRestrictConstraintOfManyObject() {
        try {
            // Create a CategoryOfInstrument
            CategoryOfInstrument categoryOfInstrument = new CategoryOfInstrument();
            categoryOfInstrument.setCategoryInstrument("Z-TESTCI-02");
            categoryOfInstrument.setDescription("Z-TESTCIDESC-02");
            m_uow.add(categoryOfInstrument);
            
            // Create a few related Parts
            for (int i = 0 ; i < 5; i++) {
                Part part = new Part();
                part.setPart("Z-TESTPART-02ZZZ" + i);
                part.setNoun("Z-TESTNOUN-02ZZZ" + i);
                part.setCategoryInstrument("Z-TESTCI-02");
                m_uow.add(part);
            }
            
            // Check if the objects got created
            assertTrue("CategoryOfInstrument should have been created", CategoryOfInstrument.exists(m_uow, "Z-TESTCI-02"));
            categoryOfInstrument = CategoryOfInstrument.findByPK(m_uow, "Z-TESTCI-02");
            assertEquals("Related Parts were not created", 5, categoryOfInstrument.getPartArray().length);
            
            // The delete of the categoryOfInstrument should fail
            try {
                m_uow.delete(categoryOfInstrument);
                fail("The delete of CategoryOfInstrument should have failed since related Part objects exist");
            } catch (DeleteFailedException e) {
                // expected error
            }
            
            // Delete the related Part objects first
            Part[] parts = categoryOfInstrument.getPartArray();
            for (int i = 0; i < parts.length; i++) {
                m_uow.delete(parts[i]);
            }
            
            // Now the delete of CategoryOfInstrument should be successful
            m_uow.delete(CategoryOfInstrument.findByPK(m_uow, "Z-TESTCI-02"));
            
            // NOTE: We cannot use the 'm_uow.delete(categoryOfInstrument)', since the categoryOfInstrument instance has cached Part objects.
            // As per the current design, this cache is not cleared automtically when the related-objects are deleted.
            
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** This will create a Part object and a few related Item objects.
     * The delete of the Part object will implicitly delete the related Item objects too.
     * Note: Part has a '1 - to - 0..* composition' relationship with Item.
     */
    public void testCascadeDeleteOfManyObject() {
        try {
            // create a Part object
            Part part = new Part();
            part.setPart("Z-TESTPART-02ZZZ");
            part.setNoun("Z-TESTNOUN-02ZZZ");
            m_uow.add(part);
            
            // Create a few related Items
            for (int i = 0 ; i < 5; i++) {
                Item item = new Item();
                item.setItemId("Z-TESTITEM-02ZZZ" + i);
                item.setPart("Z-TESTPART-02ZZZ");
                m_uow.add(item);
            }
            
            // check the objects got created
            assertTrue("Part should have been created", Part.exists(m_uow, "Z-TESTPART-02ZZZ"));
            part = Part.findByPK(m_uow, "Z-TESTPART-02ZZZ");
            assertEquals("Related Items were not created", 5, part.getItemArray().length);
            
            // delete the Part
            m_uow.delete(part);
            
            // Part and the related Items should have been deleted
            assertTrue("Part should have been deleted", !Part.exists(m_uow, "Z-TESTPART-02ZZZ"));
            for (int i = 0 ; i < 5; i++) {
                assertTrue("Related Item should have been deleted", !Item.exists(m_uow, "Z-TESTITEM-02ZZZ" + i));
            }
            
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** This will try to create a Part object with a non-existent CategoryOfInstrument. It should fail.
     * However, creating a Part object with an existent CategoryOfInstrument should be successful.
     * Note: Part has a '0..* - to - 1 association' relationship with CategoryOfInstrument.
     */
    public void testValidationOfOneObject() {
        try {
            // create a Part object with an invalid CategoryOfInstrument
            Part part = new Part();
            part.setPart("Z-TESTPART-02ZZZ");
            part.setNoun("Z-TESTNOUN-02ZZZ");
            part.setCategoryInstrument("Z-TESTCI-01ZZZZZ");
            try {
                m_uow.add(part);
                fail("The Part should not have created since an invalid CategoryOfInstrument was used");
            } catch (AddFailedException e) {
                // expected error
            }
            
            // Add it with a valid CategoryOfInstrument
            part.setCategoryInstrument("Z-TESTCI-01");
            m_uow.add(part);
            
            // check the Part got created
            assertTrue("Part should have been created", Part.exists(m_uow, "Z-TESTPART-02ZZZ"));
            part = Part.findByPK(m_uow, "Z-TESTPART-02ZZZ");
            assertEquals("Z-TESTNOUN-02ZZZ", part.getNoun());
            assertEquals("Z-TESTCI-01", part.getCategoryInstrument());
            
            
            // delete the Part
            m_uow.delete(part);
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /** This will create a Part object with a related CategoryOfInstrument and then delete the Part. The delete should not affect the related CategoryOfInstrument object.
     * Note: Part has a '0..* - to - 1 association' relationship with CategoryOfInstrument.
     */
    public void testOneObjectUntouchedOnDelete() {
        try {
            // create a Part object with a valid CategoryOfInstrument
            Part part = new Part();
            part.setPart("Z-TESTPART-02ZZZ");
            part.setNoun("Z-TESTNOUN-02ZZZ");
            part.setCategoryInstrument("Z-TESTCI-01");
            m_uow.add(part);
            
            // check the Part got created
            assertTrue("Part should have been created", Part.exists(m_uow, "Z-TESTPART-02ZZZ"));
            part = Part.findByPK(m_uow, "Z-TESTPART-02ZZZ");
            assertEquals("Z-TESTNOUN-02ZZZ", part.getNoun());
            assertEquals("Z-TESTCI-01", part.getCategoryInstrument());
            
            // delete the Part
            m_uow.delete(part);
            
            // Check the Part got deleted and that the CategoryInstrument still exists
            assertTrue("Part should have been deleted", !Part.exists(m_uow, "Z-TESTPART-02ZZZ"));
            assertTrue("CategoryInstrument should not have been created", CategoryOfInstrument.exists(m_uow, "Z-TESTCI-01"));
            
            m_uow.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
}
