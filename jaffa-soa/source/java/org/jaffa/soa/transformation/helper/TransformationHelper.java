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
 * TransformationHelper.java
 *
 */
package org.jaffa.soa.transformation.helper;

import org.jaffa.soa.transformation.meta.Mapping;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Array;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.Logger;
import org.jaffa.util.JAXBHelper;
import org.jaffa.util.URLHelper;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.jaffa.soa.transformation.meta.MetaDataRepository;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.ws.handler.MessageContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * This TransformationHelper class has various helper
 * methods for marshal, unmarshall and transformation 
 * using xslt.
 *
 * @author SaravananN
 */
public class TransformationHelper {

    private static Logger log = Logger.getLogger(TransformationHelper.class);
    public static final String DIRECTION_IN = "IN";
    public static final String DIRECTION_OUT = "OUT";

    /**
     * This Method mashall the input object with given qName
     * (if input is array it will marshall with rootElement name passed) 
     * @param argument
     * @param mapping
     * @return
     * @throws Exception
     * @throws RuntimeException
     */
    public static String marshall(Object argument, Mapping mapping) throws Exception, RuntimeException {

        if (log.isDebugEnabled()) {
            log.debug("Marshalling Object :" + argument.getClass().getName());
            log.debug("Marshalling qName :" + mapping.getQName());
            log.debug("Marshalling roolElemt :" + mapping.getRootElement());
        }

        StringWriter xmlString = new StringWriter();


        // Use JAXB to add nodes to operationElement by marshalling the input arguments to that element
        // An arg{i} node will be created for each argument
        if (argument != null) {
            Class argumentClass = argument.getClass();
            if (argumentClass.isArray()) {


                String rootElement = "<?xml version='1.0' encoding='UTF-8'?><" + mapping.getRootElement() + "></" + mapping.getRootElement() + ">";
                DocumentBuilder document = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Element node = document.parse(new ByteArrayInputStream(rootElement.getBytes())).getDocumentElement();

                Result domResult = new DOMResult(node);

                argumentClass = argumentClass.getComponentType();
                JAXBContext jc = JAXBHelper.obtainJAXBContext(argumentClass);
                Marshaller marshaller = jc.createMarshaller();
                //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                for (int j = 0, len = Array.getLength(argument); j < len; j++) {
                    Object arrayElement = Array.get(argument, j);
                    marshaller.marshal(new JAXBElement(new QName(mapping.getQName()), argumentClass, arrayElement), domResult);
                }

                nodeToString(node, xmlString);

            } else {
                JAXBContext jc = JAXBHelper.obtainJAXBContext(argumentClass);
                Marshaller marshaller = jc.createMarshaller();
                //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(new JAXBElement(new QName(mapping.getQName()), argumentClass, argument), xmlString);
            }
        }

        // validating input data against xsd
        if (mapping.getInboundSchema() != null) {
            URL configSchemaFileUrl = URLHelper.newExtendedURL(mapping.getInboundSchema());
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(configSchemaFileUrl);
            schema.newValidator().validate(new StreamSource(new StringReader(xmlString.toString())));
        }

        return xmlString.toString();
    }

    /**
     * This method unmashall the input xmlString into specified type.
     * 
     * @param clazz
     * @param xmlString
     * @param isArray
     * @return
     * @throws Exception
     * @throws RuntimeException
     */
    public static Object unmarshall(Class clazz, String xmlString, boolean isArray) throws Exception, RuntimeException {

        if (log.isDebugEnabled()) {
            log.debug("Unmarshalling.....");
            log.debug("clazz :" + clazz.getName());
            log.debug("xmlString :" + xmlString);
            log.debug("isArray :" + isArray);
        }

        Object output = null;
        if (isArray) {

            DocumentBuilder document = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Element node = document.parse(new ByteArrayInputStream(xmlString.getBytes())).getDocumentElement();

            if (log.isDebugEnabled()) {
                log.debug("Parsed XML Document " + nodeToString(node, new StringWriter()));
            }

            NodeList returnNodes = node.getChildNodes();
            if (returnNodes != null && returnNodes.getLength() > 0) {

                output = Array.newInstance(clazz, returnNodes.getLength());
                JAXBContext jc = JAXBContext.newInstance(getSuperClasses(clazz));
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                for (int i = 0; i < returnNodes.getLength(); i++) {
                    if (log.isDebugEnabled()) {
                        log.debug("returnNodes.item(i) :" + nodeToString(returnNodes.item(i), new StringWriter()));
                    }
                    JAXBElement jaxbElement = unmarshaller.unmarshal(new StreamSource(new StringReader(nodeToString(returnNodes.item(i), new StringWriter()))), clazz);
                    Array.set(output, i, jaxbElement.getValue());
                }

            }
        } else {
            JAXBContext jc = JAXBHelper.obtainJAXBContext(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement jaxbElement = unmarshaller.unmarshal(new StreamSource(new StringReader(xmlString)), clazz);
            output = jaxbElement.getValue();
        }

        return output;
    }

    /**
     * Transform the input XML String using the supplied script.
     * 
     * @param xmlString
     * @param xsltScript
     * @return
     * @throws Exception
     * @throws RuntimeException
     */
    public static String transform(String xmlString, String xsltScript) throws Exception, RuntimeException {
        StringWriter sw = new StringWriter();

        StringReader reader = new StringReader(xmlString);

        StreamSource stylesource = new StreamSource(URLHelper.getInputStream(xsltScript));
        Transformer xformer = TransformerFactory.newInstance().newTransformer(stylesource);
        xformer.setOutputProperty(OutputKeys.METHOD, "xml");
        xformer.setOutputProperty(OutputKeys.INDENT, "no");
        xformer.transform(new StreamSource(reader), new StreamResult(sw));

        return sw.toString();
    }

    private static String nodeToString(Node node, StringWriter xmlString) throws Exception, RuntimeException {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "no");
        t.transform(new DOMSource(node), new StreamResult(xmlString));
        return xmlString.toString();
    }

    /**
     * @param source
     * @param target
     * @param endPoint
     * @param function
     * @param direction
     * @return
     * @throws Exception
     * @throws RuntimeException
     */
    public static Object transform(Object source, Class target, String endPoint, String function, String direction) throws Exception, RuntimeException {

        if (log.isDebugEnabled()) {
            log.debug("Transforming .......");
            log.debug("source :" + source.getClass().getName());
            log.debug("target :" + target.getName());
            log.debug("endPoint :" + endPoint);
            log.debug("function :" + function);
            log.debug("direction :" + direction);
        }

        MetaDataRepository metaDataRepository = MetaDataRepository.getInstance();
        Mapping mapping = metaDataRepository.getMapping(endPoint + function + direction);

        String inputXml = marshall(source, mapping);

        if (log.isDebugEnabled()) {
            log.debug("inputXml :" + inputXml);
        }

        String transformedInXml = transform(inputXml, mapping.getXsltscript());

        if (log.isDebugEnabled()) {
            log.debug("transformedInXml :" + transformedInXml);
        }

        return unmarshall(target, transformedInXml, mapping.isArray() != null ? mapping.isArray() : false);

    }

    /**
     * @param context
     * @return
     */
    public static String getEndPoint(MessageContext context) {

        HttpServletRequest req = (HttpServletRequest) context.get(MessageContext.SERVLET_REQUEST);

        return req != null ? req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1) : null;

    }

    private static Class[] getSuperClasses(Class clazz) {
        List<Class> classList = new ArrayList<Class>();
        classList.add(clazz);
        Class superclass = clazz.getSuperclass();
        classList.add(superclass);
        while (superclass != null) {
            clazz = superclass;
            superclass = clazz.getSuperclass();
            if (superclass == null || superclass.getName().equals("java.lang.Object")) {
                break;
            }
            classList.add(superclass);
        }

        Collections.reverse(classList);

        return classList.toArray(new Class[classList.size()]);
    }

    /**
     * @param xmlString
     * @return
     * @throws Exception
     */
    public static String findFunction(String xmlString) throws Exception {

        DocumentBuilder document = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Element node = document.parse(new ByteArrayInputStream(xmlString.getBytes())).getDocumentElement();

        //Xpath
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile("//*[local-name()='Envelope']/*[local-name()='Body']/*");

        Object result = expr.evaluate(node, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        if (log.isDebugEnabled()) {
            log.debug("nodes.item(0).getNodeName():" + nodes.item(0).getNodeName());
        }

        if (nodes.item(0).getNodeName().contains("query")) {
            return "query";
        } else if (nodes.item(0).getNodeName().contains("update")) {
            return "update";
        } else {
            return null;
        }
    }

    /**
     * @param soapMessage
     * @param endPoint
     * @param direction
     * @return
     * @throws Exception
     */
    public static TransformationBean transform(String soapMessage, String endPoint, String direction) throws Exception {

        String function = findFunction(soapMessage);
        
        if (log.isDebugEnabled()) {
            log.debug("function :" + function);
            log.debug("endPoint :" + endPoint);
            log.debug("soapMessage :" + soapMessage);
        }

        MetaDataRepository metaDataRepository = MetaDataRepository.getInstance();
        Mapping mapping = metaDataRepository.getMapping(endPoint + function + direction);

        if (log.isDebugEnabled()) {
            log.debug("mapping :" + mapping);
        }

        String transformedMessage = transform(soapMessage, mapping.getXsltscript());
        
        TransformationBean transformationBean = new TransformationBean();
        transformationBean.setRealEndPoint(mapping.getEndpoint());
        transformationBean.setTransformedMessage(transformedMessage);
        
        return transformationBean;
    }
}
