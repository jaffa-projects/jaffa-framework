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

package org.jaffa.rules.metadata;

import java.util.*;
import java.net.URL;
import org.jaffa.cache.CacheManager;
import org.jaffa.cache.ICache;
import org.jaffa.util.URLHelper;
import org.jaffa.security.VariationContext;
import org.jaffa.config.Config;
import org.jaffa.datatypes.Parser;
import org.jaffa.util.DefaultEntityResolver;
import org.jaffa.util.DefaultErrorHandler;

import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.FileNotFoundException;

/** This parses the Rules config file and creates instances of the ClassMetaData class. These instances are cached.
 */
public class RulesMetaDataService {

    public static final String VARIATION_FILE_SUFFIX = "-rules.xml";
    private static final String PARSER_NAME = "org.apache.xerces.parsers.SAXParser";
    private static final String DEFAULT_XML_FILE = "classpath:///resources/core-rules.xml";
    private static final String SUCCESS_MESSAGE = RulesMetaDataService.class.getName();
    private static Map c_caches = new HashMap();

    /** Returns an instance of ClassMetaData for the given name. A null will be returned in case no definition is found in the Rules Config file.
     * @param className The class for which the XML will be parsed.
     * @param variation This will determine the Rules config file to be parsed. If a null is passed, then the core file will be parsed.
     * @return an instance of ClassMetaData.
     * @throws SAXException If any exception is thrown while parsing the XML file.
     * @throws IOException If any I/O error occurs in reading the XML file. Note: A FileNotFoundException will be thrown, in case the file is not found.
     */
    public static ClassMetaData getClassMetaData(String className, String variation)
    throws SAXException, IOException {
        if (variation == null)
            variation = VariationContext.DEFAULT_VARIATION;

        // Get the appropriate cache
        ICache cache = (ICache) c_caches.get(variation);
        if (cache == null) {
            synchronized(c_caches) {
                // try again
                cache = (ICache) c_caches.get(variation);
                if (cache == null) {
                    cache = CacheManager.getCache(RulesMetaDataService.class.getName() + '-' + variation);
                    c_caches.put(variation, cache);
                }
            }
        }

        // Now check the cache
        if (!cache.containsKey(className))
            find(className, cache, variation);
        return (ClassMetaData) cache.get(className);
    }

    /** Parse the rules-xml file for the given named className.
     */
    private static void find(String className, ICache cache, String variation)
    throws SAXException, IOException {
        synchronized(cache) {
            if (cache.containsKey(className))
                return;

            CustomHandler handler = new CustomHandler(className);
            try {
                String xmlFile;
                if (VariationContext.DEFAULT_VARIATION.equals(variation))
                    xmlFile = (String) Config.getProperty(Config.PROP_RULES_ENGINE_CORE_RULES_URL, DEFAULT_XML_FILE);
                else {
                    String variationRulesDirectory = (String) Config.getProperty(Config.PROP_RULES_ENGINE_VARIATIONS_DIR, null);
                    if (variationRulesDirectory == null || variationRulesDirectory.length() == 0) {
                        // No directory specified. Hence no variations exist. Just return.
                        return;
                    } else {
                        xmlFile = variationRulesDirectory + '/' + variation + VARIATION_FILE_SUFFIX;
                    }
                }
                URL url = URLHelper.newExtendedURL(xmlFile);
                if (url == null)
                    throw new FileNotFoundException("File not found - " + xmlFile);

                XMLReader reader = XMLReaderFactory.createXMLReader(PARSER_NAME);
                reader.setContentHandler(handler);
                reader.setEntityResolver(new DefaultEntityResolver());
                reader.setErrorHandler(new DefaultErrorHandler());
                reader.parse(new InputSource(url.openStream()));
                cache.put(className, handler.getClassMetaData());
            } catch (SAXException e) {
                // Its alrite if the SUCCESS_MESSAGE is returned
                if (SUCCESS_MESSAGE.equals(e.getMessage()))
                    cache.put(className, handler.getClassMetaData());
                else
                    throw e;
            } catch (IOException e) {
                throw e;
            }
        }
    }


    /** This will handle the SAX events raised while parsing the rules xml file. */
    private static class CustomHandler extends DefaultHandler {

        private static final String DOMAIN = "domain";
        private static final String DTO = "dto";
        private static final String CLASS = "class";
        private static final String FIELD = "field";
        private static final String NAME = "name";
        private static final String OVERRIDES_DEFAULT = "overridesDefault";
        private static final String EXTENDS_CLASS = "extendsClass";
        private static final String EXTENDS_FIELD = "extendsField";


        private String m_className = null;
        private ClassMetaData m_classMetaData = null;
        private FieldMetaData m_fieldMetaData = null;

        /** Create an instance.
         */
        private CustomHandler(String className) {
            m_className = className;
        }

        /** Returns the ClassMetaData for the given name. A null may be returned, if no matching definition was found.
         */
        private ClassMetaData getClassMetaData() {
            return m_classMetaData;
        }


        /** Receive notification of the start of an element.
         * @param uri The uri.
         * @param sName The local name (without prefix), or the empty string if Namespace processing is not being performed.
         * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
         * @param atts The specified or defaulted attributes
         */
        public void startElement(String uri, String sName, String qName, Attributes atts) {
            if (m_classMetaData == null) {
                if(sName.equals(DOMAIN) || sName.equals(DTO)) {
                    if (m_className.equals(atts.getValue(CLASS))) {
                        m_classMetaData = new ClassMetaData();
                        m_classMetaData.setClassName(m_className);
                    }
                }
            } else {
                if (sName.equals(FIELD)) {
                    m_fieldMetaData = new FieldMetaData();
                    m_fieldMetaData.setName(atts.getValue(NAME));
                    m_fieldMetaData.setOverridesDefault(Parser.parseBoolean(atts.getValue(OVERRIDES_DEFAULT)).booleanValue());
                    m_fieldMetaData.setExtendsClass(atts.getValue(EXTENDS_CLASS));
                    m_fieldMetaData.setExtendsField(atts.getValue(EXTENDS_FIELD));
                    m_classMetaData.addField(m_fieldMetaData);
                } else if (m_fieldMetaData != null) {
                    RuleMetaData ruleMetaData = new RuleMetaData();
                    ruleMetaData.setName(sName);
                    for (int i = 0; i < atts.getLength(); i++) {
                        String name = atts.getQName(i);
                        ruleMetaData.addParameter(name, atts.getValue(name));
                    }
                    m_fieldMetaData.addRule(ruleMetaData);
                }
            }
        }

        /** Receive notification of the end of an element.
         * @param uri The uri.
         * @param sName The local name (without prefix), or the empty string if Namespace processing is not being performed.
         * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
         * @throws SAXException Any SAX exception, possibly wrapping another exception. NOTE: This exception will also be thrown after the named validator is found, so as to terminate any further parsing.
         */
        public void endElement(String uri, String sName, String qName) throws SAXException {
            if (m_classMetaData != null) {
                if (sName.equals(DOMAIN) || sName.equals(DTO)) {
                    // the classMetaData has been found. End the parsing.
                    throw new SAXException(SUCCESS_MESSAGE);
                }
            }
        }
    }


    public static void clearCache() {
        synchronized(c_caches) {
            c_caches.clear();
        }
    }

    public static void clearCache(String variation) {
        if(variation==null)
            clearCache();
        else
            if(c_caches.containsKey(variation)) {
                synchronized(c_caches) {
                    c_caches.remove(variation);
                }
            }
    }

    
    public static void main(String[] args) {
        try {
            System.out.println(getClassMetaData("org.jaffa.persistence.domainobjects.CategoryOfInstrument", null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
