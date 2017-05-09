package org.jaffa.soa.dataaccess;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.jaffa.soa.annotation.TrimWSDL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class can be used to trim an auto-generated WSDL from a JSR-181 based WebService.
 * The auto-generated WSDL exposes all the available properties, which can potentially lead to huge WSDLs and slow server-startup.
 * This class utilizes the targetScope feature of the graph-mapping files, to limit the properties being exposed, thereby improving server-startup.
 * 
 * @author GautamJ
 */
public class WSDLTrimmer {

    private static final Logger log = Logger.getLogger(WSDLTrimmer.class);
    private static final String NAMESPACE_URI_XS = "http://www.w3.org/2001/XMLSchema";

    /**
     * Trims the sourceWSDL based on the excludedFields that are declared for the graphClass associated with the input WebService implementation class.
     * The targetWSDL will be generated as a result.
     * @param sourceWSDL a source WSDL.
     * @param targetWSDLToGenerate the WSDL to generate.
     * @param webServiceImplementationClassName the WebService implementation class that may have an annotation declaring the need for WSDL trimming.
     */
    public static void trim(File sourceWSDL, File targetWSDLToGenerate, String webServiceImplementationClassName) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException, ClassNotFoundException {
        Map<Class, Collection<String>> excludedFields = getExcludedFields(webServiceImplementationClassName);
        if (excludedFields != null && excludedFields.size() > 0) {
            if (log.isDebugEnabled())
                log.debug("Trimming '" + sourceWSDL + "' by excluding " + excludedFields);

            //Parse the source WSDL
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder parser = factory.newDocumentBuilder();
            Document document = parser.parse(sourceWSDL);

            //Trim the source WSDL
            trim(document.getDocumentElement(), excludedFields);

            //Generate the target WSDL
            if (log.isDebugEnabled())
                log.debug("Generating: " + targetWSDLToGenerate);
            Source source = new DOMSource(document);
            Result result = new StreamResult(targetWSDLToGenerate);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //transformerFactory.setAttribute("indent-number", 2);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);
        } else if (!sourceWSDL.equals(targetWSDLToGenerate)) {
            if (log.isDebugEnabled())
                log.debug("None of the fields are excluded. '" + targetWSDLToGenerate + "'  will be created as a copy of '" + sourceWSDL + '\'');
            copy(sourceWSDL, targetWSDLToGenerate);
        }
    }

    /**
     * Trims the WSDL element based on the excludedFields that are declared for the webServiceImplementationClass.
     * @param element the <types> element within a WSDL file.
     * @param webServiceImplementationClassName the WebService implementation class that may have an annotation declaring the need for WSDL trimming.
     */
    public static void trim(Element element, String webServiceImplementationClassName) throws ClassNotFoundException {
        Map<Class, Collection<String>> excludedFields = getExcludedFields(webServiceImplementationClassName);
        if (excludedFields != null && !excludedFields.isEmpty()) {
            if (log.isDebugEnabled())
                log.debug("Trimming WSDL for " + webServiceImplementationClassName + " by excluding " + excludedFields);
            trim(element, excludedFields);
        } else {
            if (log.isDebugEnabled())
                log.debug("Excluded fields not found and hence no need to trim WSDL for " + webServiceImplementationClassName);
        }
    }

    /**
     * Trims the WSDL element based on the excludedFields that are declared for the graphClass.
     * @param element the <types> element within a WSDL file.
     * @param excludedFields the fields to remove from the WSDL element.
     */
    private static void trim(Element element, Map<Class, Collection<String>> excludedFields) {
        //Create a new Map of excluded fields which are keyed by the simple classname
        //@todo: Lookup the appropriate JAXB annotation to determine if any overriding class name exists
        Map<String, Collection<String>> excludedFieldsBySimpleClassName = new LinkedHashMap<String, Collection<String>>();
        for (Map.Entry<Class, Collection<String>> me : excludedFields.entrySet())
            excludedFieldsBySimpleClassName.put(me.getKey().getSimpleName().toUpperCase(), me.getValue());

        //Find all the 'complexType' nodes
        NodeList complexTypes = element.getElementsByTagNameNS(NAMESPACE_URI_XS, "complexType");
        if (complexTypes != null) {
            for (int i = 0; i < complexTypes.getLength(); i++) {
                Node complexType = complexTypes.item(i);
                if (complexType != null && complexType.getNodeType() == Node.ELEMENT_NODE) {
                    String complexTypeName = ((Element) complexType).getAttribute("name");
                    if (complexTypeName != null && complexTypeName.endsWith("Graph")) {
                        String simpleClassName = complexTypeName.toUpperCase();
                        if (excludedFieldsBySimpleClassName.containsKey(simpleClassName)) {
                            Collection<String> excludedFieldNames = excludedFieldsBySimpleClassName.get(simpleClassName);
                            if (excludedFieldNames != null && !excludedFieldNames.isEmpty()) {
                                if (log.isDebugEnabled())
                                    log.debug("Trimming complexType '" + complexTypeName + "', removing fields: " + excludedFieldNames);
                                removeChildElements((Element) complexType, excludedFieldNames);
                            } else {
                                if (log.isDebugEnabled())
                                    log.debug("Removing complexType '" + complexTypeName + '\'');
                                complexType.getParentNode().removeChild(complexType);
                                --i;
                            }
                        }
                    }
                }
            }
        }
    }

    /** Determines the excluded fields for the Graph class associated with the input WebService implementation class. */
    private static Map<Class, Collection<String>> getExcludedFields(String webServiceImplementationClassName) throws ClassNotFoundException {
        Map<Class, Collection<String>> excludedFields = null;
        Class<?> webServiceImplementationClass = Class.forName(webServiceImplementationClassName);
        Class graphClass = null;
        if (webServiceImplementationClass.isAnnotationPresent(TrimWSDL.class)) {
            graphClass = Class.forName(webServiceImplementationClass.getAnnotation(TrimWSDL.class).graphClassName());
        } else if (GraphService.class.isAssignableFrom(webServiceImplementationClass)) {
            Type[] typeArguments = ((ParameterizedType) webServiceImplementationClass.getGenericSuperclass()).getActualTypeArguments();
            graphClass = typeArguments[1] instanceof Class ? (Class) typeArguments[1] : null;
        }
        if (graphClass != null)
            excludedFields = MappingFilter.getExcludedFieldMap(graphClass);
        else {
            if (log.isDebugEnabled())
                log.debug("Excluded fields cannot be determined since the TrimWSDL annotation has not been specified on the WebService implementation class " + webServiceImplementationClassName + ", nor does it extend a GraphService");
        }
        return excludedFields;
    }

    /** Removes child elements as specified in the input collection. */
    private static void removeChildElements(Element complexType, Collection<String> excludedFieldNames) {
        NodeList elements = complexType.getElementsByTagNameNS(NAMESPACE_URI_XS, "element");
        if (elements != null) {
            for (int i = 0; i < elements.getLength(); i++) {
                Node element = elements.item(i);
                if (element != null && element.getNodeType() == Node.ELEMENT_NODE) {
                    String elementName = ((Element) element).getAttribute("name");
                    if (excludedFieldNames.contains(elementName)) {
                        element.getParentNode().removeChild(element);
                        --i;
                    }
                }
            }
        }
    }

    /** Creates a copy of the source file. */
    private static void copy(File source, File target) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(source));
            os = new BufferedOutputStream(new FileOutputStream(target));
            int b;
            while ((b = is.read()) > -1)
                os.write(b);
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ignore) {
            }
            try {
                if (os != null)
                    os.close();
            } catch (IOException ignore) {
            }
        }
    }
}
