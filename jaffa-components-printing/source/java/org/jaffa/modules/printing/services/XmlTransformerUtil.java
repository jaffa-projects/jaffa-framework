package org.jaffa.modules.printing.services;

/**
 * Created by dlengyel on 9/16/2016.
 */

import org.apache.log4j.Logger;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class XmlTransformerUtil {
    Logger log = Logger.getLogger(XmlTransformerUtil.class);

    /**
     * Executes the XSLT defined in xslInputStream to transform the source byte array into a
     * file that is written to the descriptor provided by output
     * @param source The input XSLT byte array to transform
     * @param xslInputStream The XSL to do the transformation
     * @param output The output transformed XSLT file
     */
    public static void transform(byte[] source, InputStream xslInputStream, File output) throws IOException, TransformerException {
        try {
            //Create streams
            ByteArrayInputStream input = new ByteArrayInputStream(source);
            StreamSource xslStream = new StreamSource(xslInputStream);
            FileWriter outputXml = new FileWriter(output);
            StreamSource in = new StreamSource(input);
            StreamResult out = new StreamResult(outputXml);

            //Load our xslt into a transformer template
            TransformerFactory factory = TransformerFactory.newInstance();
            Templates template = factory.newTemplates(xslStream);
            //Get a transformer
            Transformer transformer = template.newTransformer();
            //Execute the transform
            transformer.transform(in, out);

        } catch (IOException | TransformerException e) {
            throw e;
        }
    }

}
