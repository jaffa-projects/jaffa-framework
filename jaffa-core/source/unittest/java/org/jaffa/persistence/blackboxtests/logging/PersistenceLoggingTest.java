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

package org.jaffa.persistence.blackboxtests.logging;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jaffa.datatypes.DateTime;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.blackboxtests.Wrapper;
import org.jaffa.persistence.domainobjects.Item;
import org.jaffa.session.ContextManagerFactory;

public class PersistenceLoggingTest extends TestCase {
    
    private static final int ITEM_COUNT = 5;
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(PersistenceLoggingTest.class));
    }
    
    /** Creates new PersistenceLoggingTest
     * @param name The name of the test case.
     */
    public PersistenceLoggingTest(String name) {
        super(name);
    }
    
    public void testLogging() throws Exception {
        UOW uow = null;
        try {
            // Specify the IPersistenceLoggingPlugin. This should ideally be mentioned in ApplicationRules.global
            ContextManagerFactory.instance().setThreadContext(null);
            ContextManagerFactory.instance().setProperty("jaffa.persistence.IPersistenceLoggingPlugin", "org.jaffa.persistence.blackboxtests.logging.DemoLogger");
            
            // Create some Items
            uow = new UOW();
            for (int i = 0; i < ITEM_COUNT; i++) {
                Item obj = new Item();
                obj.setItemId("Z-TESTLOG-" + i);
                obj.setKeyRef("KR" + i);
                obj.setCreatedDatetime(new DateTime(2000, DateTime.JANUARY, 15, (i + 1), 30, 40, 0));
                obj.setPrice(new Double(i * 10.5));
                obj.setQty(new Long(i * 10));
                uow.add(obj);
            }
            uow.commit();
            
            // Delete the Items
            uow = new UOW();
            for (int i = 0; i < ITEM_COUNT; i++) {
                Item obj = Item.findByPK(uow, "Z-TESTLOG-" + i);
                assertNotNull("Item having id " + "Z-TESTLOG-" + i + " should have been retrieved", obj);
                uow.delete(obj);
            }
            uow.commit();
            
            // Unmarshal the serialized objects to java
            uow = new UOW();
            for (String s : DemoLogger.c_addedSerializedObjects) {
                JAXBContext jc = JAXBContext.newInstance(new Class[] {Item.class});
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                Object obj = unmarshaller.unmarshal(new StringReader(s));
                uow.addSpecial(obj);
            }
            DemoLogger.c_addedSerializedObjects.clear();
            uow.commit();
            
            // Delete the Items
            uow = new UOW();
            for (int i = 0; i < ITEM_COUNT; i++) {
                Item obj = Item.findByPK(uow, "Z-TESTLOG-" + i);
                assertNotNull("Item having id " + "Z-TESTLOG-" + i + " should have been retrieved", obj);
                assertEquals("KR" + i, obj.getKeyRef());
                assertEquals(new DateTime(2000, DateTime.JANUARY, 15, (i + 1), 30, 40, 0), obj.getCreatedDatetime());
                assertEquals(new Double(i * 10.5), obj.getPrice());
                assertEquals(new Long(i * 10), obj.getQty());
                uow.delete(obj);
            }
            uow.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }
    
}
