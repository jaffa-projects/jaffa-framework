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
package org.jaffa.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.WeakHashMap;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

/** This has convenience methods for dealing with JAXB.
 */
public class JAXBHelper {

    private static Logger log = Logger.getLogger(JAXBHelper.class);
    /** A cache of JAXBContext for a class. */
    private static Map<Class, JAXBContext> c_jaxbContextCache = new WeakHashMap<Class, JAXBContext>();

    /** A convenience method to obtain the JAXBContext for the input class.
     * The JAXBContexts are cached for faster processing.
     * @param clazz the class.
     * @throws JAXBException if any error occurs in creating the JAXBContext.
     * @return the JAXBContext for the input class.
     */
    public static JAXBContext obtainJAXBContext(Class clazz) throws JAXBException {
        JAXBContext jaxbContext = c_jaxbContextCache.get(clazz);
        if (jaxbContext == null) {
            // Create a new JAXBContext and cache it
            synchronized (c_jaxbContextCache) {
                jaxbContext = c_jaxbContextCache.get(clazz);
                if (jaxbContext == null) {
                    jaxbContext = JAXBContext.newInstance(new Class[]{clazz});
                    c_jaxbContextCache.put(clazz, jaxbContext);
                    if (log.isDebugEnabled()) {
                        log.debug("Created JAXBContext for " + clazz);
                    }
                }
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Reusing the cached JAXBContext for " + clazz);
            }
        }
        return jaxbContext;
    }

    /** Marshals the payload into XML.
     * JAXB is used for marshalling, if the payload carries the 'XmlRootElement' JAXB annotation.
     * Else the XMLEncoder will be used for marshalling.
     * @param payload Any serializable object.
     * @return the XML representation of the payload.
     * @throws JAXBException if any JAXB error occurs.
     */
    public static String marshalPayload(Object payload) throws JAXBException {
        String output = null;
        if (payload.getClass().isAnnotationPresent(XmlRootElement.class)) {
            if (log.isDebugEnabled()) {
                log.debug(payload.getClass().getName() + " has the 'XmlRootElement' JAXB annotation, and hence will be marshalled using JAXB");
            }
            StringWriter tempWriter = new StringWriter();
            JAXBContext jc = obtainJAXBContext(payload.getClass());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(payload, new BufferedWriter(tempWriter));
            output = tempWriter.toString();
        } else if (payload.getClass().isAnnotationPresent(XmlType.class)) {
            if (log.isDebugEnabled())
                log.debug(payload.getClass().getName() + " has the 'XmlType' JAXB annotation, and hence will be marshalled using JAXB");
            StringWriter tempWriter = new StringWriter();
            JAXBContext jc = obtainJAXBContext(payload.getClass());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            XmlType xmlType = payload.getClass().getAnnotation(XmlType.class);
            QName qName = new QName(xmlType.namespace() != null ? xmlType.namespace() : XMLConstants.NULL_NS_URI, xmlType.name() != null ? xmlType.name() : StringHelper.getJavaBeanStyle(payload.getClass().getSimpleName()));
            JAXBElement rootElement = new JAXBElement(qName, payload.getClass(), payload);
            marshaller.marshal(rootElement, new BufferedWriter(tempWriter));
            output = tempWriter.toString();
        } else {
            if (log.isDebugEnabled()) {
                log.debug(payload.getClass().getName() + " does not have the 'XmlRootElement' JAXB annotation, and hence will be marshalled using XMLEncoder");
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            XMLEncoder e = new XMLEncoder(new BufferedOutputStream(os));
            e.writeObject(payload);
            e.close();
            output = os.toString();
        }
        if (log.isDebugEnabled()) {
            log.debug("Marshalled Payload is\n" + output);
        }
        return output;
    }

    /** Obtains the contents of the input message, and unmarshals it into the original POJO.
     * JAXB is used for unmarshalling, if the dataBeanClass carries the 'XmlRootElement' JAXB annotation.
     * Else the XMLDecoder will be used for unmarshalling.
     * @param xml the XML to be unmarshalled.
     * @param dataBeanClassName the className of the source dataBean that was used to generate the Message.
     * @return the contents of the input message, unmarshalled into the original POJO.
     * @throws ClassNotFoundException if the dataBean class is not found.
     * @throws JAXBException if any error occurs during the unmarshal process.
     */
    public static Object unmarshalPayload(String xml, String dataBeanClassName)
            throws JAXBException, ClassNotFoundException {
        if (log.isDebugEnabled()) {
            log.debug("Unmarshalling into an instance of " + dataBeanClassName + " the XML\n" + xml);
        }
        Object payload = null;
        Class dataBeanClass = Class.forName(dataBeanClassName);
        if (dataBeanClass.isAnnotationPresent(XmlRootElement.class)) {
            if (log.isDebugEnabled()) {
                log.debug(dataBeanClassName + " has the 'XmlRootElement' JAXB annotation, and hence will be unmarshalled using JAXB");
            }
            JAXBContext jc = obtainJAXBContext(dataBeanClass);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            payload = unmarshaller.unmarshal(new BufferedReader(new StringReader(xml)));
        } else if (dataBeanClass.isAnnotationPresent(XmlType.class)) {
            if (log.isDebugEnabled())
                log.debug(dataBeanClassName + " has the 'XmlType' JAXB annotation, and hence will be unmarshalled using JAXB");
            JAXBContext jc = JAXBHelper.obtainJAXBContext(dataBeanClass);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement rootElement = unmarshaller.unmarshal(new StreamSource(new BufferedReader(new StringReader(xml))), dataBeanClass);
            payload = rootElement.getValue();
        } else {
            if (log.isDebugEnabled()) {
                log.debug(dataBeanClassName + " does not have the 'XmlRootElement' JAXB annotation, and hence will be unmarshalled using XMLDecoder");
            }
            XMLDecoder d = new XMLDecoder(new BufferedInputStream(new ByteArrayInputStream(xml.getBytes())));
            payload = d.readObject();
            d.close();
        }
        if (log.isDebugEnabled()) {
            log.debug("Unmarshalled Payload: " + payload);
        }
        return payload;
    }

    /** Creates a Schema instance to represent the input URL.
     * This Schema instance can be used for performing validations during (un)marshalling XML via JAXB.
     * @param url A URL that point to an XML Schema Definition (XSD) file.
     * @return the Schema instance.
     * @throws IOException if any I/O error occurs.
     * @throws SAXException if the XSD is invalid
     */
    public static Schema createSchema(String url) throws IOException, SAXException {
        return SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(URLHelper.newExtendedURL(url));
    }

    /**
     * Unmarshalls the Config file which is as part of resource.
     * @param clazz Name of class used for unmarshalling.
     * @param resource contains the input file.
     * @param configurationSchemaFile which contains the schema of xml file to be processed.
     * @param <T>
     * @return Unmarshalled Object of T
     * @throws IOException
     * @throws JAXBException
     * @throws SAXException
     */
    public static <T> T unmarshalConfigFile(Class clazz, Resource resource, String configurationSchemaFile)
            throws IOException, JAXBException, SAXException {

        // Convert the file names to URLs since we don't know where they would be in the deployed application.
        JAXBContext jc = JAXBContext.newInstance(new Class[] {clazz});
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(URLHelper.newExtendedURL(configurationSchemaFile));
        unmarshaller.setSchema(schema);
        return (T) unmarshaller.unmarshal(XmlHelper.stripDoctypeDeclaration(resource.getInputStream()));
    }
}
