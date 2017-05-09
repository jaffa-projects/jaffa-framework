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

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

/**
 * A helper class to invoke WebServices using the SOAP API of JSE.
 * This utility is mainly useful for invoking self-authored WebServices that are deployed in an external installation.
 * There is no need for generating any client stubs, since the application classes can be used for
 * marshalling/unmarshalling a WebService's input/output.
 * <p>
 * The following is an example of using this class:
 *     WebServiceInvoker wsi = new WebServiceInvoker();
 *     wsi.setWebServiceClass(SomeWebServiceClass.class);
 *     wsi.setOperationName("methodNameToInvoke");
 *     wsi.setTargetHostPlusApplication("http://hostName:port/applicationName");
 *     wsi.setUserId("SomeUser");
 *     wsi.setPassword("SomePassword");
 *     Object output = wsi.invoke(arg1, arg2, arg3, ...);
 * <p>
 * NOTES:
 *   1) The WebService class is expected to contain the WebService annotation, while the method is expected to have the WebMethod annotation.
 *   2) This utility will work even in the absence of JAXB annotations on the input and output arguments.
 * @author GautamJ
 */
public class WebServiceInvoker {

    private static final Logger log = Logger.getLogger(WebServiceInvoker.class);
    private static final String CUSTOM_PREFIX = "j"; //prefix to use for the targetNamespace
    private Class webServiceClass;
    private String operationName;
    private String targetHostPlusApplication;
    private String targetNamespace;
    private String userId;
    private String password;

    /**
     * @return the webServiceClass
     */
    public Class getWebServiceClass() {
        return webServiceClass;
    }

    /**
     * @param webServiceClass the webServiceClass to set
     */
    public void setWebServiceClass(Class webServiceClass) {
        this.webServiceClass = webServiceClass;
    }

    /**
     * @return the operationName
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * @param operationName the operationName to set
     */
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    /**
     * @return the targetHostPlusApplication
     */
    public String getTargetHostPlusApplication() {
        return targetHostPlusApplication;
    }

    /**
     * @param targetHostPlusApplication the targetHostPlusApplication to set
     */
    public void setTargetHostPlusApplication(String targetHostPlusApplication) {
        this.targetHostPlusApplication = targetHostPlusApplication;
    }

    /**
     * @return the targetNamespace
     */
    public String getTargetNamespace() {
        return targetNamespace;
    }

    /**
     * @param targetNamespace the targetNamespace to set
     */
    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *  Marshals the inout arguments into a SOAPMessage
     * and invokes the WebService.
     * @param arguments the arguments for the WebService.
     * @return the reply from the WebService.
     * @throws SOAPException if any SOAP error occurs.
     * @throws SOAPFaultException if any SOAPFault occurs.
     * @throws JAXBException if any XML (un)marshalling error occurs.
     */
    public Object invoke(Object... arguments) throws SOAPException, SOAPFaultException, JAXBException {
        SOAPConnection connection = null;

        try {
            // Create a connection
            connection = SOAPConnectionFactory.newInstance().createConnection();

            // Create the SOAPMessage
            SOAPMessage message = createSOAPMessage(arguments);

            // Invoke the WebService
            SOAPMessage response = invoke(connection, message);

            // Unmarshal the response
            return unmarshalResponse(response);
        } finally {
            // Always close the connection
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Generate SOAPMessage based on the input arguments.
     * <p>
     * For example:
     *   <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:j="http://product1.mirotechnologies.com/material/core/StockBalanceFinder">
     *     <SOAP-ENV:Header/>
     *     <SOAP-ENV:Body>
     *       <j:performInquiry>
     *         <arg0>
     *           <part><operator>Equals</operator><values>P1000</values></part>
     *         </arg0>
     *       </j:performInquiry>
     *     </SOAP-ENV:Body>
     *   </SOAP-ENV:Envelope>
     * @param arguments the arguments for the WebService.
     * @return a SOAPMessage.
     * @throws SOAPException if any SOAP error occurs.
     * @throws JAXBException if any XML (un)marshalling error occurs.
     */
    protected SOAPMessage createSOAPMessage(Object... arguments) throws SOAPException, JAXBException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPPart messagePart = message.getSOAPPart();
        SOAPEnvelope envelope = messagePart.getEnvelope();
        String tns = this.obtainTargetNamespace();
        if (tns != null) {
            envelope.addNamespaceDeclaration(CUSTOM_PREFIX, tns);
        }
        SOAPBody body = message.getSOAPBody();
        SOAPElement operationElement = tns != null ? body.addBodyElement(envelope.createQName(getOperationName(), CUSTOM_PREFIX)) : body.addBodyElement(envelope.createName(getOperationName()));

        // Add authentication
        addAuthorization(message);

        // Use JAXB to add nodes to operationElement by marshalling the input arguments to that element
        // An arg{i} node will be created for each argument
        if (arguments != null) {
            
            Annotation[][] parameterAnnotations = null;
            for (Method m : getWebServiceClass().getMethods()) {
                if (m.getName().equals(getOperationName()) && m.getAnnotation(WebMethod.class) != null) {
                    parameterAnnotations = m.getParameterAnnotations();
                }
            }
            
            for (int i = 0; i < arguments.length; i++) {
                Object argument = arguments[i];
                Class argumentClass = argument.getClass();
                String webParamName = null;
                if (parameterAnnotations != null && parameterAnnotations.length > 0) {
                    for (Annotation[] annotations : parameterAnnotations) {
                        for (Annotation annotation : annotations) {
                            if (annotation instanceof WebParam) {
                                WebParam webParam = (WebParam) annotation;
                                webParamName = webParam.name();
                                if (log.isDebugEnabled()) {
                                    log.debug("webParamName :" + webParamName);
                                }
                            }
                        }
                    }
                }
                if (Collection.class.isAssignableFrom(argumentClass)) {
                    JAXBContext jc = null;
                    Marshaller marshaller = null;
                    List list = (List) argument;
                    for (Object arrayElement : list) {
                        if (jc == null) {
                            jc = JAXBHelper.obtainJAXBContext(arrayElement.getClass());
                            marshaller = jc.createMarshaller();
                        }
                        if (webParamName != null) {
                            marshaller.marshal(new JAXBElement(new QName(webParamName), arrayElement.getClass(), arrayElement), operationElement);
                        } else {
                            marshaller.marshal(new JAXBElement(new QName("arg0"), arrayElement.getClass(), arrayElement), operationElement);
                        }
                    }
                } else if (argumentClass.isArray()) {
                    argumentClass = argumentClass.getComponentType();
                    JAXBContext jc = JAXBHelper.obtainJAXBContext(argumentClass);
                    Marshaller marshaller = jc.createMarshaller();
                    for (int j = 0, len = Array.getLength(argument); j < len; j++) {
                        Object arrayElement = Array.get(argument, j);
                        if (webParamName != null) {
                            marshaller.marshal(new JAXBElement(new QName(webParamName), argumentClass, arrayElement), operationElement);
                        } else {
                            marshaller.marshal(new JAXBElement(new QName("arg0"), argumentClass, arrayElement), operationElement);
                        }
                    }
                } else {
                    JAXBContext jc = JAXBHelper.obtainJAXBContext(argumentClass);
                    Marshaller marshaller = jc.createMarshaller();
                    if (webParamName != null) {
                        marshaller.marshal(new JAXBElement(new QName(webParamName), argumentClass, argument), operationElement);                        
                    } else {
                        marshaller.marshal(new JAXBElement(new QName("arg0"), argumentClass, argument), operationElement);                        
                    }                                        
                }
            }
        }

        // Save all changes to the Message
        message.saveChanges();

        if (log.isDebugEnabled()) {
            log.debug("Created SOAPMessage: " + message);
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                message.writeTo(os);
                log.debug("Contents of SOAPMessage: " + os.toString());
            } catch (Exception e) {
                // do nothing
            }
        }
        return message;
    }

    /**
     * Returns either the targetNamespace property or the targetNamespace from the WebService annotation; whichever is defined.
     * @return either the targetNamespace property or the targetNamespace from the WebService annotation; whichever is defined.
     */
    protected String obtainTargetNamespace() {
        return getTargetNamespace() != null ? getTargetNamespace() : ((WebService) getWebServiceClass().getAnnotation(WebService.class)).targetNamespace();
    }

    /**
     * Adds 'Authorization' to the input message's header, provided the userId property has been defined.
     * @param message the SOAP message.
     */
    protected void addAuthorization(SOAPMessage message) {
        if (getUserId() != null) {
            StringBuilder userPwd = new StringBuilder(getUserId());
            if (getPassword() != null) {
                userPwd.append(':').append(getPassword());
            }

            // @todo: Do not use SUN's class. Find an alternative or write our own function
            // @todo: Change the password property from String to byte[]
            String authorization = new sun.misc.BASE64Encoder().encode(userPwd.toString().getBytes());
            MimeHeaders headers = message.getMimeHeaders();
            headers.addHeader("Authorization", "Basic " + authorization);
        }
    }

    /**
     * Invokes the WebService, passing it the input message.
     * @param connection the connection to use for invoking the WebService.
     * @param message the input to the WebService.
     * @return the response from the WebService.
     * @throws SOAPException if any SOAP error occurs.
     */
    protected SOAPMessage invoke(SOAPConnection connection, SOAPMessage message) throws SOAPException {
        StringBuilder url = new StringBuilder(getTargetHostPlusApplication());
        if (url.charAt(url.length() - 1) != '/') {
            url.append('/');
        }
        url.append(((WebService) getWebServiceClass().getAnnotation(WebService.class)).serviceName());
        if (log.isDebugEnabled()) {
            log.debug("Invoking WebService at '" + url + "', passing " + message);
        }
        return connection.call(message, url.toString());
    }

    /**
     * Unmarshals the response into a corresponding Java object.
     * @param response the SOAPMessage returned by a WebService.
     * @return the corresponding Java object.
     * @throws SOAPException if any SOAP error occurs.
     * @throws SOAPFaultException if any SOAPFault occurs.
     * @throws JAXBException if any XML (un)marshalling error occurs.
     */
    protected Object unmarshalResponse(SOAPMessage response) throws SOAPException, SOAPFaultException, JAXBException {
        if (log.isDebugEnabled()) {
            log.debug("Unmarshalling response: " + response);
            try {
                StringWriter sw = new StringWriter();
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(response.getSOAPPart()), new StreamResult(sw));
                log.debug("Contents of response: " + sw.toString());
            } catch (Exception e) {
                // do nothing
            }
        }

        // Determine the response type
        Class responseClass = null;
        boolean isCollection = false;
        for (Method m : getWebServiceClass().getMethods()) {
            if (m.getName().equals(getOperationName()) && m.getAnnotation(WebMethod.class) != null) {
                responseClass = m.getReturnType();

                if (Collection.class.isAssignableFrom(responseClass)) {
                    if (ParameterizedType.class.isAssignableFrom(m.getGenericReturnType().getClass())) {
                        isCollection = true;
                        ParameterizedType genericType = (ParameterizedType) m.getGenericReturnType();
                        responseClass = ((Class) (genericType.getActualTypeArguments()[0]));
                    }
                }
                break;
            }
        }

        if (responseClass == null) {
            String s = "Unable to locate the WebMethod '" + getOperationName() + "' on the class " + getWebServiceClass();
            log.error(s);
            throw new IllegalArgumentException(s);
        }

        if (response.getSOAPBody().hasFault()) {
            SOAPFault fault = response.getSOAPBody().getFault();
            log.error("SOAPFault occurred while invoking WebService " + getWebServiceClass() + " with faultcode " + fault.getFaultCode());
            throw new SOAPFaultException(fault);
        }

        // The response body will be of the format
        //   <env:Body>
        //     <j:performInquiryResponse>
        //       <return><summaryStockBalancesOutDto>...</return>
        //       <return><summaryStockBalancesOutDto>...</return>
        //       ...
        //     </j:performInquiryResponse>
        //   </env:Body>
        // Unmarshal the contents of the "return" node to an instance of the responseClass
        Object output = null;
        NodeList returnNodes = response.getSOAPBody().getFirstChild().getChildNodes();
        if (returnNodes != null && returnNodes.getLength() > 0) {
            if (isCollection) {
                output = Array.newInstance(responseClass, returnNodes.getLength());
                JAXBContext jc = JAXBHelper.obtainJAXBContext(responseClass);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                for (int i = 0; i < returnNodes.getLength(); i++) {
                    JAXBElement jaxbElement = unmarshaller.unmarshal(returnNodes.item(i), responseClass);
                    Array.set(output, i, jaxbElement.getValue());
                }
                output = Arrays.asList(output);
            } else if (responseClass.isArray()) {
                responseClass = responseClass.getComponentType();
                output = Array.newInstance(responseClass, returnNodes.getLength());
                JAXBContext jc = JAXBHelper.obtainJAXBContext(responseClass);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                for (int i = 0; i < returnNodes.getLength(); i++) {
                    JAXBElement jaxbElement = unmarshaller.unmarshal(returnNodes.item(i), responseClass);
                    Array.set(output, i, jaxbElement.getValue());
                }
            } else {
                JAXBContext jc = JAXBHelper.obtainJAXBContext(responseClass);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                JAXBElement jaxbElement = unmarshaller.unmarshal(returnNodes.item(0), responseClass);
                output = jaxbElement.getValue();
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Response unmarshalled to: " + output);
        }
        return output;
    }
}
