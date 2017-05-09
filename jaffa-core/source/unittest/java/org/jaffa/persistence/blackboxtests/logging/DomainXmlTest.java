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
import java.io.StringWriter;
import java.util.Iterator;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.blackboxtests.Wrapper;
import org.jaffa.persistence.domainobjects.CategoryOfInstrument;
import org.jaffa.persistence.domainobjects.Item;
import org.jaffa.persistence.domainobjects.Part;

/** This class will test the DomainXmlReader and DomainXmlWriter utilities.
 */
public class DomainXmlTest extends TestCase {
    
    private static final int ITEM_COUNT = 5;
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(DomainXmlTest.class));
    }
    
    /** Creates new DomainXmlTest
     * @param name The name of the test case.
     */
    public DomainXmlTest(String name) {
        super(name);
    }
    
    /** This method serializes a bunch of domain objects to XML.
     * The XML is then deserialized back to domain objects.
     */
    public void testEscapedDomainXml() throws Exception {
        writeAndReadDomainXml(true);
    }
    
    /** This method serializes a bunch of domain objects to XML.
     * The XML is then deserialized back to domain objects.
     */
    public void testUnEscapedDomainXml() throws Exception {
        writeAndReadDomainXml(false);
    }
    
    /** This method serializes a bunch of domain objects to XML.
     * The XML is then deserialized back to domain objects.
     */
    private void writeAndReadDomainXml(boolean escapeDomainXml) throws Exception {
        UOW uow = null;
        try {
            
            // Retrieve a few objects and add them to the DomainXmlWriter
            uow = new UOW();
            DomainXmlWriter dxw = new DomainXmlWriter();
            dxw.addObject(CategoryOfInstrument.findByPK(uow, "Z-TESTCI-01"));
            dxw.addObject(Part.findByPK(uow, "Z-TESTPART-01"));
            dxw.addObject(Part.findByPK(uow, "Z-TESTPART-02"));
            dxw.addObject(Item.findByPK(uow, "Z-TESTITEM-01"));
            dxw.addObject(Item.findByPK(uow, "Z-TESTITEM-03"));
            
            // write XML
            StringWriter w = new StringWriter();
            dxw.write(w, escapeDomainXml);
            
            // Now unmarhsal the XML into domain objects
            Object obj = null;
            String xml = w.toString();
            System.out.println("*** Marshalled XML ***\n" + xml);
            DomainXmlReader dxr = new DomainXmlReader(new StringReader(xml));
            Iterator itr = dxr.iterator();
            
            // Verify the deserialzed objects
            obj = itr.next();
            assertTrue("CategoryOfInstrument should have been generated", obj instanceof CategoryOfInstrument);
            assertEquals("Z-TESTCI-01", ((CategoryOfInstrument) obj).getCategoryInstrument());
            assertEquals("Z-TESTCIDESC-01", ((CategoryOfInstrument) obj).getDescription());
            
            obj = itr.next();
            assertTrue("Part should have been generated", obj instanceof Part);
            assertEquals("Z-TESTPART-01", ((Part) obj).getPart());
            assertEquals("Z-TESTNOUN-01", ((Part) obj).getNoun());
            
            obj = itr.next();
            assertTrue("Part should have been generated", obj instanceof Part);
            assertEquals("Z-TESTPART-02", ((Part) obj).getPart());
            assertEquals("Z-TESTNOUN-02", ((Part) obj).getNoun());
            
            obj = itr.next();
            assertTrue("Item should have been generated", obj instanceof Item);
            assertEquals("Z-TESTITEM-01", ((Item) obj).getItemId());
            
            obj = itr.next();
            assertTrue("Item should have been generated", obj instanceof Item);
            assertEquals("Z-TESTITEM-03", ((Item) obj).getItemId());
            
            assertTrue("No more records should exist", !itr.hasNext());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }
    
}
