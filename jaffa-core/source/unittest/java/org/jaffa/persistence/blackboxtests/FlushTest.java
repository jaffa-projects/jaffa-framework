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

package org.jaffa.persistence.blackboxtests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jaffa.persistence.*;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.persistence.domainobjects.*;

/**
 *
 * @author  GautamJ
 * @version
 */
public class FlushTest extends TestCase {
    
    private UOW m_uow = null;
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(FlushTest.class));
    }

    /** Creates new FlushTest */
    public FlushTest(String name) {
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
    
    
    /** This test will create a CategoryOfInstrument. It'll then try to create a duplicate instance of the CategoryOfInstrument.
     * No exception will be raised if the underlying persistence engine queues up the Adds.
     * However, an exception will be raised if a flush() is done prior to adding the duplicate instance.
     */
    public void testFlush() {
        // *** Check if the underlying persistence engine queues the Adds ***
        // Add an object
        try {
            ICategoryOfInstrument obj = (ICategoryOfInstrument) m_uow.newPersistentInstance(ICategoryOfInstrument.class);
            obj.setCategoryInstrument("Z-TESTCI-02");
            obj.setDescription("Z-TESTCIDESC-02");
            obj.setSupportEquip(Boolean.FALSE);
            m_uow.add(obj);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        // Add a duplicate instance. No exception should be raised if the underlying persistence engine queues the Adds.
        try {
            ICategoryOfInstrument obj = (ICategoryOfInstrument) m_uow.newPersistentInstance(ICategoryOfInstrument.class);
            obj.setCategoryInstrument("Z-TESTCI-02");
            obj.setDescription("Z-TESTCIDESC-02");
            obj.setSupportEquip(Boolean.FALSE);
            m_uow.add(obj);
        } catch (Exception e) {
            // flush() cannot be tested.. Just bail out
            System.out.println("NOTE: The underlying persistence engine is performing the database i/o immediately, without waiting for commit() or flush() invocations. The flush() operation cannot be tested !!!");
            e.printStackTrace();
            return;
        }
        
        
        
        // *** The underlying persistence engine can be tested for the flush() operation ***
        // Start with a new UOW
        try {
            m_uow.rollback();
            m_uow = new UOW();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        
        
        // *** Now test the flush() operation ***
        // Add an object
        try {
            ICategoryOfInstrument obj = (ICategoryOfInstrument) m_uow.newPersistentInstance(ICategoryOfInstrument.class);
            obj.setCategoryInstrument("Z-TESTCI-02");
            obj.setDescription("Z-TESTCIDESC-02");
            obj.setSupportEquip(Boolean.FALSE);
            m_uow.add(obj);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        // Now flush the queue, which should add the object to the database
        try {
            m_uow.flush();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        // Add a duplicate instance
        try {
            ICategoryOfInstrument obj = (ICategoryOfInstrument) m_uow.newPersistentInstance(ICategoryOfInstrument.class);
            obj.setCategoryInstrument("Z-TESTCI-02");
            obj.setDescription("Z-TESTCIDESC-02");
            obj.setSupportEquip(Boolean.FALSE);
            m_uow.add(obj);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        // Flush the queue, which will try to add the duplicate instance to the database. This should fail.
        try {
            m_uow.flush();
            fail("No exception was raised while trying to add a duplicate instance, even after a flush() invocation");
        } catch (Exception e) {
            // An exception is expected.
        }
    }
    
}
