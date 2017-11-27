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

package org.jaffa.tools;

import org.jaffa.datatypes.Defaults;
import org.jaffa.datatypes.Parser;
import org.jaffa.util.DefaultEntityResolver;
import org.jaffa.util.DefaultErrorHandler;
import org.jaffa.util.StringHelper;
import org.jaffa.util.XmlHelper;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/** This parses an input XML file into a Map of Maps.
 */
public class DomParser {
    
    /** Parses the XML without validating it. It returns a Map.
     * @param uri The location of the content to be parsed.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies the configuration requested.
     * @throws SAXException If any parse errors occur.
     * @throws IOException If any IO errors occur.
     * @return The Map.
     */
    public static Map parse(String uri)
    throws ParserConfigurationException, SAXException, IOException {
        return parse(uri, false);
    }
    
    /** Parses the XML. It returns a Map.
     * @param uri The location of the content to be parsed.
     * @param validate if the XML is to be validated while parsing.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies the configuration requested.
     * @throws SAXException If any parse errors occur.
     * @throws IOException If any IO errors occur.
     * @return The Map.
     */
    public static Map parse(String uri, boolean validate)
    throws ParserConfigurationException, SAXException, IOException {
        // Create a factory object for creating DOM parsers
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        // Specifies that the parser produced by this factory will validate documents as they are parsed.
        factory.setValidating(validate);
        
        // Now use the factory to create a DOM parser
        DocumentBuilder parser = factory.newDocumentBuilder();
        
        // Specifies the EntityResolver to resolve DTD used in XML documents
        parser.setEntityResolver(new DefaultEntityResolver());
        
        // Specifies the ErrorHandler to handle warning/error/fatalError conditions
        parser.setErrorHandler(new DefaultErrorHandler());
        
        Document document = parser.parse(uri);
        
        return parseXML(document);
    }
    
    
    private static Map parseXML(Document document) {
        // this is the Map to be returned
        Map xmlMap = new LinkedHashMap();
        
        // get the root Element
        Element root = document.getDocumentElement();
        if (root != null) {
            Map m = new LinkedHashMap();
            xmlMap.put(root.getNodeName(), m);
            if (root.hasAttributes()) {
                NamedNodeMap attributes = root.getAttributes();
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attribute = attributes.item(i);
                    m.put(attribute.getNodeName(), new ValueObject(StringHelper
                                                                           .replace(attribute.getNodeValue(), "\"", "\\\"") ) );
                }
            }
            if (XmlHelper.hasChildElements(root))
                loopJDOM(root, m);
        }
        
        return xmlMap;
    }
    
    private static void loopJDOM(Node node, Map map) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            // only process Elements
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                Map m = null;
                if (child.hasAttributes()) {
                    // If attributes are present, then add them to a new Map
                    if (m == null) {
                        m = new LinkedHashMap();
                        map.put( uniqueString( map, child.getNodeName() ), m );
                    }
                    NamedNodeMap attributes = child.getAttributes();
                    for (int j = 0; j < attributes.getLength(); j++) {
                        Node attribute = attributes.item(j);
                        m.put(attribute.getNodeName(), new ValueObject(StringHelper
                                                                               .replace(attribute.getNodeValue(), "\"", "\\\"") ) );
                    }
                }
                if (XmlHelper.hasChildElements(child)) {
                    // If child elements are present, then add them recursively to a new Map
                    if (m == null) {
                        m = new LinkedHashMap();
                        map.put( uniqueString( map, child.getNodeName() ), m );
                    }
                    loopJDOM(child, m);
                } else if (m == null) {
                    // If neither attributes nor child elements are present, then add the element contents to the existing Map
                    map.put( uniqueString( map, child.getNodeName() ),
                    new ValueObject(StringHelper
                                            .replace(XmlHelper.getTextTrim(child), "\"", "\\\"") ) );
                }
            }
        }
    }
    
    private static String uniqueString(Map map, String s) {
        int i = 0;
        String str = s;
        while ( map.containsKey(str) )
            str = s + ++i;
        return str;
    }
    
    /** A value in the input XML stream can be represented as a ValueObject.
     * This class supports various helper routines to get different variations of the values.
     */
    public static class ValueObject {
        private String m_value = null;
        private String m_lower1 = null;
        private String m_upper1 = null;
        private String m_lower = null;
        private String m_upper = null;
        private String m_static = null;
        private String m_space = null;
        private String m_javaBeanStyle = null;
        
        private Boolean m_booleanValue = null;
        private String m_javaDataType = null;
        private String m_gridColumnDataType = null;
        private String m_criteriaFieldType = null;
        private Boolean m_criteriaFieldTypeThrowsException = null;
        private String m_metaFieldType = null;
        private String m_widgetType = null;
        private String m_widgetTypeInCriteriaScreen = null;
        private String m_widgetTypeBasedOnBreakup = null;
        private String m_parserMethod = null;
        
        /** Creates an instance.
         * @param value the value of this object.
         */
        public ValueObject(String value) {
            m_value = value;
        }
        
        /** Returns the value with the 1st character in lowercase.
         * @return the value with the 1st character in lowercase.
         */
        public String getLower1() {
            if (m_lower1 == null)
                m_lower1 = StringHelper.getLower1(m_value);
            return m_lower1;
        }
        
        /** Returns the value with the 1st character in uppercase.
         * @return the value with the 1st character in uppercase.
         */
        public String getUpper1() {
            if (m_upper1 == null)
                m_upper1 = StringHelper.getUpper1(m_value);
            return m_upper1;
        }
        
        /** Returns the value with the value in lowercase.
         * @return the value with the value in lowercase.
         */
        public String getLower() {
            if (m_lower == null)
                m_lower = StringHelper.getLower(m_value);
            return m_lower;
        }
        
        /** Returns the value with the value in uppercase.
         * @return the value with the value in uppercase.
         */
        public String getUpper() {
            if (m_upper == null)
                m_upper = StringHelper.getUpper(m_value);
            return m_upper;
        }
        
        /** Translates the value to all UpperCase with underscores separating the words.
         * For eg: "abcDef" would be translated to "ABC_DEF".
         * @return the value as a static.
         */
        public String getStatic() {
            if (m_static == null)
                m_static = StringHelper.getStatic(m_value);
            return m_static;
        }
        
        /** Translates the value to words separated by spaces.
         * For eg: "abcDef" would be translated to "abc Def".
         * @return the value separated into words.
         */
        public String getSpace() {
            if (m_space == null)
                m_space = StringHelper.getSpace(m_value);
            return m_space;
        }
        
        /**
         * Translates the value to a JavaBean styled property name
         * For eg: "AbcDef" would be translated to "abcDef".
         * "abcDef" would be translated to "abcDef".
         * "ABcDef" would be translated to "ABcDef".
         * This uses the java.beans.Introspector.decapitalize() method to perform the conversion
         * @return the modified string
         */
        public String getJavaBeanStyle() {
            if (m_javaBeanStyle == null)
                m_javaBeanStyle = StringHelper.getJavaBeanStyle(m_value);
            return m_javaBeanStyle;
        }
        
        /** Translates the value to an int value.
         * @throws NumberFormatException if the String cannot be converted to an int value.
         * @return the value as an int value.
         */
        public int getIntValue() throws NumberFormatException {
            return Integer.parseInt(m_value);
        }
        
        /** Returns the value as is.
         * @return the value as is.
         */
        public String toString() {
            return m_value;
        }
        
        /** Returns true if the value is any of true/yes/t/y/1.
         * It utilises the Parser.parseBoolean() method to arrive at the result.
         * @return true if the value is any of true/yes/t/y/1.
         */
        public boolean getBooleanValue() {
            if (m_booleanValue == null)
                m_booleanValue = Parser.parseBoolean(m_value);
            return m_booleanValue != null ? m_booleanValue.booleanValue() : false;
        }
        
        /** Returns the Java classname for the datatype represented by this value.
         * It invokes the Defaults.getClassString() method to obtain the Java classname.
         * @return the Java Class Name for the datatype represented by this value.
         */
        public String getJavaDataType() {
            if (m_javaDataType == null)
                determineDataTypeRelatedValues();
            return m_javaDataType;
        }
        
        /** Returns the string to be specified as the GridColumnDataType for the datatype represented by this value.
         * @return the string to be specified as the GridColumnDataType for the datatype represented by this value.
         */
        public String getGridColumnDataType() {
            if (m_gridColumnDataType == null)
                determineDataTypeRelatedValues();
            return m_gridColumnDataType;
        }
        
        /** Returns the CriteriaField implementation classname appropriate for the datatype represented by this value.
         * @return the CriteriaField implementation classname appropriate for the datatype represented by this value.
         */
        public String getCriteriaFieldType() {
            if (m_criteriaFieldType == null)
                determineDataTypeRelatedValues();
            return m_criteriaFieldType;
        }
        
        /** Returns true if the CriteriaField implementation class appropriate for the datatype represented by this value throws an Exception during Instantiation.
         * @return true if the CriteriaField implementation class appropriate for the datatype represented by this value throws an Exception during Instantiation.
         */
        public boolean getCriteriaFieldTypeThrowsException() {
            if (m_criteriaFieldTypeThrowsException == null)
                determineDataTypeRelatedValues();
            return m_criteriaFieldTypeThrowsException != null ? m_criteriaFieldTypeThrowsException.booleanValue() : false;
        }
        
        /** Returns the FieldMetaData implementation classname appropriate for the datatype represented by this value.
         * @return the FieldMetaData implementation classname appropriate for the datatype represented by this value.
         */
        public String getMetaFieldType() {
            if (m_metaFieldType == null)
                determineDataTypeRelatedValues();
            return m_metaFieldType;
        }
        
        /** Returns the WidgetType appropriate for the datatype represented by this value.
         * @return the WidgetType appropriate for the datatype represented by this value.
         */
        public String getWidgetType() {
            if (m_widgetType == null)
                determineDataTypeRelatedValues();
            return m_widgetType;
        }
        
        /** Returns the WidgetType appropriate for the datatype represented by this value for an Inquiry Criteria Screen.
         * @return the WidgetType appropriate for the datatype represented by this value for an Inquiry Criteria Screen
         * @deprecated This method is not in use since the version2.1 patterns.
         */
        public String getWidgetTypeInCriteriaScreen() {
            if (m_widgetTypeInCriteriaScreen == null)
                determineDataTypeRelatedValues();
            return m_widgetTypeInCriteriaScreen;
        }
        
        /** Returns the WidgetType appropriate for the Breakup type represented by this value.
         * @return the WidgetType appropriate for the Breakup type represented by this value.
         */
        public String getWidgetTypeBasedOnBreakup() {
            if (m_widgetTypeBasedOnBreakup == null && m_value != null) {
                if (m_value.equalsIgnoreCase("dropdown"))
                    m_widgetTypeBasedOnBreakup = "DropDown";
                else if (m_value.equalsIgnoreCase("checkbox"))
                    m_widgetTypeBasedOnBreakup = "Grid";
                else if (m_value.equalsIgnoreCase("radiobutton"))
                    m_widgetTypeBasedOnBreakup = "RadioButton";
                else if (m_value.equalsIgnoreCase("propertyeditor"))
                    m_widgetTypeBasedOnBreakup = "Grid";
            }
            return m_widgetTypeBasedOnBreakup;
        }
        
        /** Returns the ParserMethod appropriate for the datatype represented by this value.
         * @return the ParserMethod appropriate for the datatype represented by this value.
         */
        public String getParserMethod() {
            if (m_parserMethod == null)
                determineDataTypeRelatedValues();
            return m_parserMethod;
        }
        
        /** Returns a true if the value represents the DateOnly datatype
         * @return a true if the value represents the DateOnly datatype
         */
        public boolean getDataTypeDateOnly() {
            return m_value != null ? m_value.equalsIgnoreCase(
                    Defaults.DATEONLY) : false;
        }
        
        /** Returns a true if the value represents the DateTime datatype
         * @return a true if the value represents the DateTime datatype
         */
        public boolean getDataTypeDateTime() {
            return m_value != null ? m_value.equalsIgnoreCase(
                    Defaults.DATETIME) : false;
        }
        
        /** Returns a true if the value represents the Boolean datatype or its variants.
         * @return a true if the value represents the Boolean datatype or its variants.
         */
        public boolean getDataTypeBoolean() {
            return m_value != null ? getUpper().startsWith(Defaults.BOOLEAN) : false;
        }
        
        /** Returns a true if the value represents the datatype for which the String class is used.
         * @return a true if the value represents the datatype for which the String class is used.
         */
        public boolean getDataTypeString() {
            return m_value != null ? Defaults.getClass(getUpper()) == String.class : false;
        }
        
        /** Returns a true if the value represents a numeric datatype. Eg. Decimal, Integer, Currency.
         * @return a true if the value represents a numeric datatype.
         */
        public boolean getDataTypeNumeric() {
            return m_value != null ? m_value.equalsIgnoreCase(Defaults.INTEGER) || m_value.equalsIgnoreCase(
                    Defaults.DECIMAL) || m_value.equalsIgnoreCase(
                    Defaults.CURRENCY) : false;
        }
        
        /** This will check the input Map for a RelatedObject having the same Name as this field.
         * This is extensively used by the object-maintenace_2_0 pattern.
         * @param relatedObjectsMap The Map containing a collection of RelatedObjects.
         * @return The matching RelatedObject.
         */
        public Map getRelatedObject(Object relatedObjectsMap) {
            Map relatedObject = null;
            if (m_value != null && relatedObjectsMap != null && relatedObjectsMap instanceof Map) {
                Collection relatedObjectsCollection = ((Map) relatedObjectsMap).values();
                for (Iterator i = relatedObjectsCollection.iterator(); i.hasNext(); ) {
                    relatedObject = (Map) i.next();
                    Object relatedObjectName = relatedObject.get("Name");
                    if (relatedObjectName != null && relatedObjectName.toString().equals(m_value))
                        break;
                    else
                        relatedObject = null;
                }
            }
            return relatedObject;
        }
        
        /** This will check the input Map for a RelatedObject having the same Name as this field.
         * It will then return the value of the property 'RelationshipToDomainObject' for the RelatedObject.
         * This is extensively used by the object-maintenace_2_0 pattern.
         * @param relatedObjectsMap The Map containing a collection of RelatedObjects.
         * @return The RelationshipType for the matching RelatedObject.
         */
        public String getRelationshipType(Object relatedObjectsMap) {
            Object relationshipType = null;
            Map relatedObject = getRelatedObject(relatedObjectsMap);
            if (relatedObject != null)
                relationshipType = relatedObject.get("RelationshipToDomainObject");
            return relationshipType != null ? relationshipType.toString() : null;
        }
        
        /** Returns true if the RelationshipType for the matching RelatedObject equals any of the values:
         * One, OneAndMany, 1, 0..1, 1..1
         * This is extensively used by the object-maintenace_2_0 pattern.
         * @param relatedObjectsMap The Map containing a collection of RelatedObjects.
         * @return true if the RelationshipType for the matching RelatedObject equals any of the values for the One relationship.
         */
        public boolean getRelationshipTypeOne(Object relatedObjectsMap) {
            return getRelationshipTypeOne(getRelationshipType(relatedObjectsMap));
        }
        
        /** Returns true if the RelationshipType for the matching RelatedObject equals any of the values:
         * Many, OneAndMany, *, m..*, m (where m > 1) or m..n (where n >= m && n > 1)
         * This is extensively used by the object-maintenace_2_0 pattern.
         * @param relatedObjectsMap The Map containing a collection of RelatedObjects.
         * @return true if the RelationshipType for the matching RelatedObject equals any of the values for the Many relationship.
         */
        public boolean getRelationshipTypeMany(Object relatedObjectsMap) {
            return getRelationshipTypeMany(getRelationshipType(relatedObjectsMap));
        }
        
        /** Returns true if the value equals any of the values:
         * One, OneAndMany, 1, 0..1, 1..1.
         * @return true if the value equals any of the values for the One relationship.
         */
        public boolean getRelationshipTypeOne() {
            return getRelationshipTypeOne(m_value);
        }
        
        /** Returns true if the value equals any of the values:
         * Many, OneAndMany, *, m..*, m (where m > 1) or m..n (where n >= m && n > 1)
         * @return true if the value equals any of the values for the Many relationship.
         */
        public boolean getRelationshipTypeMany() {
            return getRelationshipTypeMany(m_value);
        }
        
        /** Returns true if the value equals any of the values:
         * 0..*, *, 0 or 0..n
         * @return true if the value equals any of the values for the Optional relationship.
         */
        public boolean getRelationshipTypeOptional() {
            return "*".equals(m_value) || "0..*".equals(m_value)
            || "0".equals(m_value) || m_value.startsWith("0..");
        }
        
        private boolean getRelationshipTypeOne(String relationshipType) {
            return "One".equalsIgnoreCase(relationshipType) || "OneAndMany".equalsIgnoreCase(relationshipType)
            || "1".equals(relationshipType) || "0..1".equals(relationshipType)
            || "1..1".equals(relationshipType);
        }
        
        private boolean getRelationshipTypeMany(String relationshipType) {
            boolean result = "Many".equalsIgnoreCase(relationshipType) || "OneAndMany".equalsIgnoreCase(relationshipType);
            if (!result && relationshipType != null) {
                // check the multiplicity
                if (relationshipType.indexOf('*') >= 0) {
                    result = true;
                } else {
                    // check the pattern 'm..n'
                    // return true if 'n >= m && n > 1'
                    int i = relationshipType.indexOf("..");
                    if (i > 0) {
                        try {
                            int low = Integer.parseInt(relationshipType.substring(0, i));
                            int high = Integer.parseInt(relationshipType.substring(i + "..".length()));
                            result = high >= low && high > 1;
                        } catch (NumberFormatException e) {
                            // do nothing
                        }
                    } else {
                        // check the pattern 'm', return true if m > 1
                        try {
                            int m = Integer.parseInt(relationshipType);
                            result = m > 1;
                        } catch (NumberFormatException e) {
                            // do nothing
                        }
                    }
                }
                
            }
            return result;
        }
        
        private void determineDataTypeRelatedValues() {
            if (m_value != null) {
                String value = getUpper();
                if (value.equals(Defaults.STRING)) {
                    m_javaDataType = Defaults.getClassString(value);
                    m_gridColumnDataType = "CaseInsensitiveString";
                    m_criteriaFieldType = "StringCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.FALSE;
                    m_metaFieldType = "StringFieldMetaData";
                    m_widgetType = "EditBox";
                    m_widgetTypeInCriteriaScreen = "EditBox";
                    m_parserMethod = "parseString";
                } else if (value.equals(Defaults.BLOB)) {
                    m_javaDataType = Defaults.getClassString(value);
                    m_gridColumnDataType = "CaseInsensitiveString";
                    m_criteriaFieldType = "RawCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.FALSE;
                    m_metaFieldType = "RawFieldMetaData";
                    m_widgetType = "EditBox";
                    m_widgetTypeInCriteriaScreen = "EditBox";
                    m_parserMethod = "parseRaw";
                } else if (value.startsWith(Defaults.BOOLEAN)) {
                    m_javaDataType = Defaults.getClassString(Defaults.BOOLEAN);
                    m_gridColumnDataType = "CaseInsensitiveString";
                    m_criteriaFieldType = "BooleanCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.FALSE;
                    m_metaFieldType = "BooleanFieldMetaData";
                    m_widgetType = "CheckBox";
                    m_widgetTypeInCriteriaScreen = "DropDown";
                    m_parserMethod = "parseBoolean";
                } else if (value.equals(Defaults.CLOB)) {
                    m_javaDataType = Defaults.getClassString(value);
                    m_gridColumnDataType = "CaseInsensitiveString";
                    m_criteriaFieldType = "StringCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.FALSE;
                    m_metaFieldType = "StringFieldMetaData";
                    m_widgetType = "EditBox";
                    m_widgetTypeInCriteriaScreen = "EditBox";
                    m_parserMethod = "parseString";
                } else if (value.equals(Defaults.CURRENCY)) {
                    m_javaDataType = Defaults.getClassString(value);
                    m_gridColumnDataType = "Number";
                    m_criteriaFieldType = "CurrencyCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.TRUE;
                    m_metaFieldType = "CurrencyFieldMetaData";
                    m_widgetType = "EditBox";
                    m_widgetTypeInCriteriaScreen = "EditBox";
                    m_parserMethod = "parseCurrency";
                } else if (value.equals(Defaults.DATEONLY)) {
                    m_javaDataType = Defaults.getClassString(value);
                    m_gridColumnDataType = "Date";
                    m_criteriaFieldType = "DateOnlyCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.TRUE;
                    m_metaFieldType = "DateOnlyFieldMetaData";
                    m_widgetType = "DateTime";
                    m_widgetTypeInCriteriaScreen = "EditBox";
                    m_parserMethod = "parseDateOnly";
                } else if (value.equals(Defaults.DATETIME)) {
                    m_javaDataType = Defaults.getClassString(value);
                    m_gridColumnDataType = "Date";
                    m_criteriaFieldType = "DateTimeCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.TRUE;
                    m_metaFieldType = "DateTimeFieldMetaData";
                    m_widgetType = "DateTime";
                    m_widgetTypeInCriteriaScreen = "EditBox";
                    m_parserMethod = "parseDateTime";
                } else if (value.equals(Defaults.DECIMAL)) {
                    m_javaDataType = Defaults.getClassString(value);
                    m_gridColumnDataType = "Number";
                    m_criteriaFieldType = "DecimalCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.TRUE;
                    m_metaFieldType = "DecimalFieldMetaData";
                    m_widgetType = "EditBox";
                    m_widgetTypeInCriteriaScreen = "EditBox";
                    m_parserMethod = "parseDecimal";
                } else if (value.equals(Defaults.INTEGER)) {
                    m_javaDataType = Defaults.getClassString(value);
                    m_gridColumnDataType = "Number";
                    m_criteriaFieldType = "IntegerCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.TRUE;
                    m_metaFieldType = "IntegerFieldMetaData";
                    m_widgetType = "EditBox";
                    m_widgetTypeInCriteriaScreen = "EditBox";
                    m_parserMethod = "parseInteger";
                } else if (value.equals(Defaults.LONG_RAW)) {
                    m_javaDataType = Defaults.getClassString(value);
                    m_gridColumnDataType = "CaseInsensitiveString";
                    m_criteriaFieldType = "RawCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.FALSE;
                    m_metaFieldType = "RawFieldMetaData";
                    m_widgetType = "EditBox";
                    m_widgetTypeInCriteriaScreen = "EditBox";
                    m_parserMethod = "parseRaw";
                } else if (value.equals(Defaults.LONG_STRING)) {
                    m_javaDataType = Defaults.getClassString(value);
                    m_gridColumnDataType = "CaseInsensitiveString";
                    m_criteriaFieldType = "StringCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.FALSE;
                    m_metaFieldType = "StringFieldMetaData";
                    m_widgetType = "EditBox";
                    m_widgetTypeInCriteriaScreen = "EditBox";
                    m_parserMethod = "parseString";
                } else if (value.equals(Defaults.RAW)) {
                    m_javaDataType = Defaults.getClassString(value);
                    m_gridColumnDataType = "CaseInsensitiveString";
                    m_criteriaFieldType = "RawCriteriaField";
                    m_criteriaFieldTypeThrowsException = Boolean.FALSE;
                    m_metaFieldType = "RawFieldMetaData";
                    m_widgetType = "EditBox";
                    m_widgetTypeInCriteriaScreen = "EditBox";
                    m_parserMethod = "parseRaw";
                }
            }
        }
    }
    
}
