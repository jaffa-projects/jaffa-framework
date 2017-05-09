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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.*;
import java.io.*;
import java.net.URL;

/** Utility Class for Common XML Manipulation routines.
 */
public class XmlHelper {
    
    /** Test whether the input element has a child element.
     * @param node The Element to be tested.
     * @return true if this element has at least one child element.
     */
    public static boolean hasChildElements(Node node) {
        boolean result = false;
        if (node != null) {
            NodeList children = node.getChildNodes();
            if (children != null) {
                for (int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }
    
    /** This returns the textual content directly held under this element. This will include all text within this single element, including whitespace and CDATA sections if they exist. It's essentially the concatenation of all Text and CDATA nodes. The call does not recurse into child elements. If no textual value exists for the element, an empty String ("") is returned.
     * @param node The Element for which the Text is to be returned
     * @return text content for the element, or empty string if none.
     */
    public static String getTextTrim(Node node) {
        StringBuffer buf = new StringBuffer();
        if (node != null) {
            NodeList children = node.getChildNodes();
            if (children != null) {
                for (int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    if (child.getNodeType() == Node.CDATA_SECTION_NODE || child.getNodeType() == Node.TEXT_NODE)
                        buf.append(child.getNodeValue().trim());
                }
            }
        }
        return buf.toString();
    }
    
    
    /**
     * This routine will return an InputStream which will strip off the DOCTYPE declaration from the source URL.
     * @param in The source URL from which the DOCTYPE will be stripped off.
     * @return The InputStream with no DOCTYPE.
     * @throws IOException if an I/O error occurs.
     * @see #stripDoctypeDeclaration(InputStream)
     * @deprecated This method should not be used since it opens the stream associated with the input URL and does not close it. Instead use the version of this method which takes an InputStream as the input
     */
    public static InputStream stripDoctypeDeclaration(URL in) throws IOException {
        return stripDoctypeDeclaration(in.openStream());
    }
    
    
    /** This routine will return an InputStream which will strip off the DOCTYPE declaration from the source InputStream.
     * Example declaration: '<!DOCTYPE blah blah blah>'
     * <br><br>
     * This is mostly used on incomming JAXB unmarshalling where there is no way specify a default
     * entity resolver. Here is an example...
     * <pre>
     *        URL xmlFile = "testfile.xml";
     *
     *        try {
     *            xmlFile = URLHelper.newExtendedURL(name);
     *        } catch (MalformedURLException e) {
     *            log.fatal("Can't Find Components Definition File. Bad URL - " + name, e);
     *            return null;
     *        }
     *        InputStream xmlStream = null;
     *        try {
     *            // create a JAXBContext capable of handling classes generated into the package
     *            JAXBContext jc = JAXBContext.newInstance("org.jaffa.presentation.portlet.component.componentdomain");
     *
     *            // create an Unmarshaller
     *            Unmarshaller u = jc.createUnmarshaller();
     *
     *            // enable validation
     *            u.setValidating(true);
     *
     *            // unmarshal a document into a tree of Java content objects composed of classes from the package.
     *            xmlStream = xmlFile.openStream();
     *            compList = (Components) u.unmarshal(<b>XmlHelper.stripDoctypeDeclaration</b>(xmlStream));
     *        } catch (JAXBException e) {
     *            log.fatal("XML Formatting Error Reading Components Definition File", e);
     *            return null;
     *        } catch (IOException e) {
     *            log.fatal("Error in Reading Components Definition File", e);
     *            return null;
     *        } finally {
     *            try {
     *                if (xmlStream != null)
     *                    xmlStream.close();
     *            } catch (IOException e) { / * do nothing * / }
     *        }
     * </pre>
     * @param in The source InputStream from which the DOCTYPE will be stripped off.
     * @return The InputStream with no DOCTYPE.
     * @throws IOException if an I/O error occurs.
     */
    public static InputStream stripDoctypeDeclaration(InputStream in) throws IOException {
        // Create a BufferedInputStream for efficiency
        in = new BufferedInputStream(in);
        
        // This array will hold the bytes read off the input, until the DoctypeDeclaration is encountered
        byte[] byteArray = new byte[in.available()];
        
        // This will maintain a count of the bytes added to the array
        int byteArrayCount = 0;
        
        // This flag will indicate if the XML comment block is being parsed
        boolean insideCommentBlock = false;
        
        int aByte;
        while (true) {
            // If insideCommentBlock, then simply add each byte to the array until we encounter '-->', at which point we'll reset the insideCommentBlock flag
            if (insideCommentBlock) {
                aByte = in.read();
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                if (aByte != '-')
                    continue;
                
                aByte = in.read();
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                if (aByte != '-')
                    continue;
                
                aByte = in.read();
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                if (aByte != '>')
                    continue;
                
                insideCommentBlock = false;
            }
            
            
            // Now check for '<!DOCTYPE'
            // Also check for '<!--', to determine the comment block
            if ((aByte = in.read()) != '<') {
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                continue;
            }
            
            if ((aByte = in.read()) != '!') {
                byteArray[byteArrayCount++] = '<';
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                continue;
            }
            
            if ((aByte = in.read()) != 'D') {
                byteArray[byteArrayCount++] = '<';
                byteArray[byteArrayCount++] = '!';
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                
                // Check for the comment marker '<!--'
                if (aByte == '-') {
                    aByte = in.read();
                    if (aByte == -1)
                        break;
                    byteArray[byteArrayCount++] = (byte) aByte;
                    if (aByte == '-')
                        insideCommentBlock = true;
                }
                continue;
            }
            
            if ((aByte = in.read()) != 'O') {
                byteArray[byteArrayCount++] = '<';
                byteArray[byteArrayCount++] = '!';
                byteArray[byteArrayCount++] = 'D';
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                continue;
            }
            
            if ((aByte = in.read()) != 'C') {
                byteArray[byteArrayCount++] = '<';
                byteArray[byteArrayCount++] = '!';
                byteArray[byteArrayCount++] = 'D';
                byteArray[byteArrayCount++] = 'O';
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                continue;
            }
            
            if ((aByte = in.read()) != 'T') {
                byteArray[byteArrayCount++] = '<';
                byteArray[byteArrayCount++] = '!';
                byteArray[byteArrayCount++] = 'D';
                byteArray[byteArrayCount++] = 'O';
                byteArray[byteArrayCount++] = 'C';
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                continue;
            }
            
            if ((aByte = in.read()) != 'Y') {
                byteArray[byteArrayCount++] = '<';
                byteArray[byteArrayCount++] = '!';
                byteArray[byteArrayCount++] = 'D';
                byteArray[byteArrayCount++] = 'O';
                byteArray[byteArrayCount++] = 'C';
                byteArray[byteArrayCount++] = 'T';
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                continue;
            }
            
            if ((aByte = in.read()) != 'P') {
                byteArray[byteArrayCount++] = '<';
                byteArray[byteArrayCount++] = '!';
                byteArray[byteArrayCount++] = 'D';
                byteArray[byteArrayCount++] = 'O';
                byteArray[byteArrayCount++] = 'C';
                byteArray[byteArrayCount++] = 'T';
                byteArray[byteArrayCount++] = 'Y';
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                continue;
            }
            
            if ((aByte = in.read()) != 'E') {
                byteArray[byteArrayCount++] = '<';
                byteArray[byteArrayCount++] = '!';
                byteArray[byteArrayCount++] = 'D';
                byteArray[byteArrayCount++] = 'O';
                byteArray[byteArrayCount++] = 'C';
                byteArray[byteArrayCount++] = 'T';
                byteArray[byteArrayCount++] = 'Y';
                byteArray[byteArrayCount++] = 'P';
                if (aByte == -1)
                    break;
                byteArray[byteArrayCount++] = (byte) aByte;
                continue;
            }
            
            
            // We've encountered '<!DOCTYPE'
            // Consume all the bytes till we get '>'
            // *** NOTE*** This logic will fail, if any of the text inside the DOCTYPE declaration contains a '>'. will fix it, if we run into the problem.
            while ((aByte = in.read()) != -1) {
                if (aByte == '>')
                    break;
            }
            
            // In a valid XML document, there can be only one instance of the <!DOCTYPE...> tag.
            // So simply break out of the loop and create a SequenceInputStream, by concatenating the byteArray and whatever is left in the original InputStream
            break;
        }
        
        if (in.available() > 0) {
            if (byteArrayCount > 0)
                return new SequenceInputStream(new ByteArrayInputStream(byteArray, 0, byteArrayCount), in);
            else
                return in;
        } else
            return new ByteArrayInputStream(byteArray, 0, byteArrayCount);
    }
    
}
