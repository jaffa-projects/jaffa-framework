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

package org.jaffa.rules.commons;

import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.util.BeanHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * Provides some common functionality shared between different repositories to commonly
 * load xml elements.
 * <p>
 * <p>
 * A concrete class will provide implementation for the abstract load() and clear() methods to
 * appropriately handle the contents of each metadata element.
 */
public abstract class AbstractLoader {

    /**
     * Imports XML.
     *
     * @param metadataElement the metadata element we are processing within a source.
     * @param source          the name of the source file.
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public abstract void load(Element metadataElement, String source) throws JaffaRulesFrameworkException;

    /**
     * Unloads the XML that was imported from the input.
     *
     * @param uri the URI of the source file.
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public abstract void unload(String uri) throws JaffaRulesFrameworkException;

    /**
     * Clears the Repository.
     *
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public abstract void clear() throws JaffaRulesFrameworkException;


    //---------------------------------------------------------------------------------
    //
    //   CONVENIENCE METHODS
    //
    //----------------------------------------------------------------------------------


    /**
     * Returns the line number for the input node.
     *
     * @param node A Node.
     * @return the line number for the input node.
     */
    protected Integer getLine(Node node) {
        try {
            //@todo: The following doesn't work!
            return (Integer) BeanHelper.getField(node, "lineNumber");
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Returns the child elements within the input element.
     *
     * @param element An Element.
     * @return the child elements within the input element.
     */
    protected Element[] getChildren(Element element) {
        Collection<Element> elements = new LinkedList<>();
        NodeList nodes = element.getChildNodes();
        if (nodes != null) {
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                    elements.add((Element) node);

            }
        }
        return elements.toArray(new Element[elements.size()]);
    }

    /**
     * Returns the text within the input element.
     *
     * @param element An Element.
     * @return the text within the input element.
     */
    protected String getText(Element element) {
        StringBuilder buf = null;
        NodeList nodes = element.getChildNodes();
        if (nodes != null) {
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if ((node.getNodeType() == Node.TEXT_NODE) || (node.getNodeType() == Node.CDATA_SECTION_NODE)) {
                    if (buf == null)
                        buf = new StringBuilder();
                    buf.append(node.getNodeValue());
                }
            }
        }
        return buf != null ? buf.toString().trim() : null;
    }

    /**
     * Reads the attributes within the input element and adds them to the input Map.
     *
     * @param element An Element.
     * @param map     A Map.
     */
    protected void attributesToMap(Element element, Map<String, String> map) {
        NamedNodeMap attributes = element.getAttributes();
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attribute = attributes.item(i);
                if (attribute.getNodeType() == Node.ATTRIBUTE_NODE)
                    map.put(attribute.getNodeName(), attribute.getNodeValue());
            }
        }
    }

}
