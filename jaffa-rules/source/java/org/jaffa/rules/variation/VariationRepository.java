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

package org.jaffa.rules.variation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.commons.AbstractLoader;
import org.w3c.dom.Element;

/** This class is used to import variations.
 */
public class VariationRepository extends AbstractLoader {
    
    private static Logger log = Logger.getLogger(VariationRepository.class);
    
    private static final String ELEMENT_URI = "uri";
    private static final String ATTR_REGEX = "regex";
    private static final String ATTR_VARIATION = "variation";
    
    // Singleton instance
    private static VariationRepository c_instance = new VariationRepository();
    
    // Repository containing List of UriMetaData instances
    private List<UriMetaData> m_uris = new LinkedList<UriMetaData>();
    
    // Repository containing List of UriMetaData instances per source
    private Map<String, List<UriMetaData>> m_urisBySource = new HashMap<String, List<UriMetaData>>();
    
    
    /** Creates an instance. */
    private VariationRepository() {
    }
    
    /** Returns the Singleton instance.
     * @return the Singleton instance.
     */
    public static VariationRepository instance() {
        return c_instance;
    }
    
    /** Imports Variations.
     * @param metadataElement the metadata element we are processing within a source.
     * @param source the name of the source file.
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public void load(Element metadataElement, String source) throws JaffaRulesFrameworkException {
        if (log.isDebugEnabled())
            log.debug("Loading variations from " + source);
        
        Element[] uriElements = getChildren(metadataElement);
        for (Element uriElement : uriElements) {
            if (ELEMENT_URI.equals(uriElement.getNodeName())) {
                // create a UriMetaData
                UriMetaData uriMetaData = new UriMetaData();
                uriMetaData.setSource(source);
                uriMetaData.setLine(getLine(uriElement));
                uriMetaData.setRegex(uriElement.hasAttribute(ATTR_REGEX) ? uriElement.getAttribute(ATTR_REGEX) : null);
                uriMetaData.setVariation(uriElement.hasAttribute(ATTR_VARIATION) ? uriElement.getAttribute(ATTR_VARIATION) : null);
                if (uriMetaData.getRegex() == null) {
                    log.warn("Ignoring variation since regex is unspecified: " + uriMetaData);
                    continue;
                }
                if (log.isDebugEnabled())
                    log.debug("Loaded variation: " + uriMetaData);
                
                // Add to the repository
                synchronized(this) {
                    m_uris.add(uriMetaData);
                    
                    List<UriMetaData> uris = m_urisBySource.get(uriMetaData.getSource());
                    if (uris == null) {
                        uris = new LinkedList<UriMetaData>();
                        m_urisBySource.put(uriMetaData.getSource(), uris);
                    }
                    uris.add(uriMetaData);
                }
            } else {
                // unknown element
                log.warn("Unknown element found while importing Variation: " + uriElement.getNodeName());
            }
        }
    }
    
    /** Unloads the XML that was imported from the input.
     * @param uri the URI of the source file.
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public synchronized void unload(String uri) throws JaffaRulesFrameworkException {
        List<UriMetaData> uris = m_urisBySource.remove(uri);
        if (uris != null) {
            for (UriMetaData uriMetaData : uris) {
                if (log.isDebugEnabled())
                    log.debug("Unloading " + uriMetaData);
                m_uris.remove(uriMetaData);
            }
        }
    }
    
    /** Clears the Repository.
     * @throws JaffaRulesFrameworkException if any internal error occurs.
     */
    public synchronized void clear() throws JaffaRulesFrameworkException {
        if (log.isDebugEnabled())
            log.debug("Clearing...");
        m_uris.clear();
        m_urisBySource.clear();
    }
    
    
    
    //---------------------------------------------------------------------------------
    //
    //   THESE METHODS SHOULD BE INVOKED ONLY AFTER ALL THE METADATA HAS BEEN LOADED
    //
    //----------------------------------------------------------------------------------
    
    /** Determines the variation for the input MetaData URI.
     * A null will be returned if no matching definition is found.
     * NOTE: This method should be invoked only after all the metadata has been loaded.
     * @param uri The URI of a MetaData file.
     * @return The matching variation.
     */
    public String find(String uri) {
        if (uri != null) {
            for (UriMetaData uriMetaData : m_uris) {
                Pattern pattern = uriMetaData.getPattern();
                Matcher matcher  = pattern.matcher(uri);
                if (matcher.matches()) {
                    return uriMetaData.getVariation();
                }
            }
        }
        return null;
    }
    
}
