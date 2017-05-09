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

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/** This class can be used to unmarshal XML of the following format into domain objects.
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
 *         <!-- The <bean-data> element contains the domain object serialized to XML. The contents may be ESCAPED -->
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
public class DomainXmlReader {
    
    private Reader m_xml = null;
    private XMLEventReader m_xer = null;
    
    
    /** Creates an instance
     * @param xml The XML to be unmarshalled.
     */
    public DomainXmlReader(Reader xml) {
        m_xml = xml;
    }
    
    /** Returns an Iterator for iterating through the domain objects unmarshalled from the source XML.
     * @return an Iterator for iterating through the domain objects unmarshalled from the source XML.
     * @throws XMLStreamException if any error occurs during XML parsing.
     */
    public Iterator iterator() throws XMLStreamException {
        if (m_xer != null)
            throw new UnsupportedOperationException("Only one iterator can be obtained from this instance");
        XMLInputFactory factory = XMLInputFactory.newInstance();
        m_xer = factory.createXMLEventReader(m_xml);
        return new XmlIterator();
    }
    
    
    
    /** This is a Custom implementation of the Iterator. It unmarshals the XML on demand.
     */
    private class XmlIterator implements Iterator {
        
        private String m_beanClass = null;
        private Object m_beanData = null;
        
        
        /** Returns true if any more domain objects can be unmarshalled from the XML.
         * @return true if any more domain objects can be unmarshalled from the XML.
         */
        public boolean hasNext() {
            if (m_beanData == null)
                parseXml();
            return m_beanData != null;
        }
        
        /** Returns the next domain object unmarshalled from the XML.
         * A NoSuchElementException is thrown if the end of the XML document is reached.
         * @return the next domain object unmarshalled from the XML.
         */
        public Object next() {
            if (hasNext()) {
                Object obj = m_beanData;
                m_beanData = null;
                return obj;
            } else {
                throw new NoSuchElementException("No more elements to iterate through");
            }
        }
        
        /** This method is not supported.
         * An UnsupportedOperationException will always be thrown.
         */
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported on this iterator");
        }
        
        /** Parses the XML until a domain object is unmarshalled.
         */
        private void parseXml() {
            StringBuilder buf = null;
            while (m_beanData == null && m_xer.hasNext()) {
                try {
                    XMLEvent event = (XMLEvent) m_xer.next();
                    if (buf != null) {
                        if (event.isStartElement()) {
                            String localName = ((StartElement) event).getName().getLocalPart();
                            buf.append('<').append(localName).append('>');
                        } else if (event.isEndElement()) {
                            String localName = ((EndElement) event).getName().getLocalPart();
                            if ("bean-data".equals(localName)) {
                                JAXBContext jc = JAXBContext.newInstance(new Class[] {Class.forName(m_beanClass)});
                                Unmarshaller unmarshaller = jc.createUnmarshaller();
                                m_beanData = unmarshaller.unmarshal(new StringReader(buf.toString()));
                            } else {
                                buf.append('<').append('/').append(localName).append('>');
                            }
                        } else if (event.isCharacters()) {
                            buf.append(((Characters) event).getData());
                        }
                    } else if (event.isStartElement()) {
                        String localName = ((StartElement) event).getName().getLocalPart();
                        if ("bean-class".equals(localName)) {
                            m_beanClass = m_xer.getElementText();
                        } else if ("bean-data".equals(localName)) {
                            buf = new StringBuilder();
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error in parsing XML", e);
                }
            }
        }
    }
    
}
