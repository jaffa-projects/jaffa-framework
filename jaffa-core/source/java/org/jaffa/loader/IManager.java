package org.jaffa.loader;

import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Interface for xml managers to registerXml and getXmlFileName.
 */
public interface IManager {

    /**
     * registers the XML config file to repository.
     * @param resource the object that contains the xml config file.
     * @param context key with which config file to be registered.
     * @throws JAXBException if xml file is not valid.
     * @throws SAXException if xml file is not valid.
     * @throws IOException if resource does not found.
     */
    void registerXML(Resource resource, String context) throws JAXBException, SAXException, IOException;

    /**
     * gets the name of xml config file.
     * @return xml config file name
     */
    String getXmlFileName();
}
