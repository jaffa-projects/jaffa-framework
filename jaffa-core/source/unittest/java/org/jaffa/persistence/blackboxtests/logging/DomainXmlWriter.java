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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.jaffa.persistence.IPersistent;
import org.jaffa.util.StringHelper;

/** This class can be used to serialize a bunch of domain objects into XML.
 *
 * The XML format is
 *
 *     <?xml version='1.0'?>
 *     <rows>
 *
 *       <!-- A <row> element represents a domain object -->
 *       <row>
 *         <!-- The <bean-class> element contains the fully qualified classname of the domain object -->
 *         <bean-class></bean-class>
 *
 *         <!-- The <bean-data> element contains the domain object serialized to XML. The contents will be ESCAPED if the input 'escapeDomainXml' parameter is true -->
 *         <bean-data></bean-data>
 *       </row>
 *
 *       <row>
 *         ...
 *         ...
 *       </row>
 *
 *       ...
 *       ...
 *
 *     </rows>
 *
 */
public class DomainXmlWriter {
    
    private Collection<IPersistent> m_objects = new LinkedList<IPersistent>();
    
    /** Adds the input object to be serialized.
     * @param obj The object to be serialized.
     */
    public void addObject(IPersistent obj) {
        m_objects.add(obj);
    }
    
    /** Generate XML based on the added objects.
     * @param w The writer to which the XML will be written to.
     * @param escapeDomainXml if true, the domain XML will be escaped before being inserted into the main
     * @throws IOException if any IO error occurs.
     */
    public void write(Writer w, boolean escapeDomainXml) throws IOException {
        w.write("<?xml version='1.0'?>\n");
        w.write("<rows>\n");
        for (IPersistent obj : m_objects) {
            w.write("  <row>\n");
            
            w.write("    <bean-class>");
            w.write(obj.getClass().getName());
            w.write("</bean-class>\n");
            
            w.write("    <bean-data>\n");
            try {
                StringWriter tempWriter = new StringWriter();
                JAXBContext jc = JAXBContext.newInstance(new Class[] {obj.getClass()});
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
                marshaller.marshal(obj, tempWriter);
                w.write(escapeDomainXml ? StringHelper.convertToHTML(tempWriter.toString()) : tempWriter.toString());
            } catch (Exception e) {
                throw new RuntimeException("Error in marshalling domain object to XML", e);
            }
            w.write("\n    </bean-data>\n");
            
            w.write("  </row>\n");
        }
        w.write("</rows>\n");
    }
    
}
