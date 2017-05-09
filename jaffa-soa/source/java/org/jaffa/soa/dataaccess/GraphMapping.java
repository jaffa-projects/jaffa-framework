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
package org.jaffa.soa.dataaccess;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;
import org.jaffa.util.DefaultEntityResolver;
import org.jaffa.util.DefaultErrorHandler;
import org.jaffa.util.StringHelper;
import org.jaffa.util.URLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 */
public class GraphMapping {

    private static Logger log = Logger.getLogger(GraphMapping.class);
    private static final String NODE_DAO_CLASS = "graph-data-object";
    private static final String NODE_DO_CLASS = "domain-object";
    private static final String NODE_QUERY_DO_CLASS = "query-domain-object";
    private static final String NODE_MAP_KEYS = "map-key-fields";
    private static final String NODE_MAP_FIELDS = "map-fields";
    private static final String NODE_MAP_FOREIGN = "map-foreign-objects";
    private static final String NODE_MAP_RELATED = "map-aggregate-objects";
    private static final String NODE_FIELD = "field";
    private static final String NODE_FOREIGN_OBJECT = "foreign-object";
    private static final String NODE_FOREIGN_KEY = "foreign-key-field";
    private static final String NODE_DIRTY_READ = "dirty-read";
    private static final String ATTR_CLASS = "class";
    private static final String ATTR_DAO_FIELD = "gdo";
    private static final String ATTR_DO_FIELD = "do";
    private static final String ATTR_ERROR_LABEL_TOKEN = "error-label-token";
    private static final String ATTR_ERROR_PARAMS = "error-params";
    private static final String ATTR_READ_ONLY = "read-only";
    private static final String ATTR_NO_CLONING = "no-cloning";
    private static final String ATTR_SCOPE = "scope";
    private static final String ATTR_TARGET_SCOPE = "target-scope";
    private Class domainClass;
    private Class queryDomainClass;
    private Class dataClass;
    private DirtyReadInfo dirtyReadInfo;
    private Map<String, BaseElement> elementMap;
    private Set<String> keys;
    private Map<String, Set<String>> fields; //fields per scope
    private Map<String, Set<String>> foreignObjects; //foreignObjects per scope
    private Map<String, Set<String>> relatedObjects; //relatedObjects per scope
    private Map<String, PropertyDescriptor> domainPropertyDescriptors; //propertyName+PropertyDescriptor pairs for the Domain class
    private Map<String, PropertyDescriptor> queryDomainPropertyDescriptors; //propertyName+PropertyDescriptor pairs for the Domain class

    public static PropertyDescriptor[] getPropertyDescriptors(Class clazz) {
        try {
            return Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        } catch (java.beans.IntrospectionException e) {
            log.error("Can't Introspect Mold Methods", e);
            return new PropertyDescriptor[]{};
        }
    }

    public static Map<String, PropertyDescriptor> getPropertyDescriptorMap(Class clazz) {
        Map<String, PropertyDescriptor> m = new HashMap<String, PropertyDescriptor>();
        PropertyDescriptor[] props = getPropertyDescriptors(clazz);
        if (props != null && props.length != 0)
            for (int i = 0; i < props.length; i++) {
                PropertyDescriptor prop = props[i];
                m.put(prop.getName(), prop);
            }
        return m;
    }

    /** Returns a Field object corresponding to the input propertyName for the given Class.
     * If a Field object is not found, then the search if performed recursively up the class heirarchy.
     * @param clazz the Class.
     * @param propertyName the property name.
     * @param propertyType the property type.
     * @return a Field object corresponding to the input propertyName for the given Class.
     */
    public static Field getField(Class clazz, String propertyName, Class propertyType) {
        while (clazz != null) {
            try {
                // Check the class
                Field field = clazz.getDeclaredField(propertyName);
                if (field.getType().isAssignableFrom(propertyType)) {
                    // Force an inaccessible Field to be accessible
                    if (!field.isAccessible())
                        field.setAccessible(true);

                    if (log.isDebugEnabled())
                        log.debug("Found Field object for " + clazz + '/' + propertyName + '/' + propertyType);
                    return field;
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Type mismatch. Field object " + field + " ignored for " + clazz + '/' + propertyName + '/' + propertyType);
                }
            } catch (NoSuchFieldException e) {
                // Do nothing
            }
            clazz = clazz.getSuperclass();
        }
        if (log.isDebugEnabled())
            log.debug("Field object not found for " + clazz + '/' + propertyName + '/' + propertyType);
        return null;
    }

    /** Returns a Map containing propertyName and correponding Field object for the input Class.
     * @param clazz the Class.
     * @return a Map containing propertyName and correponding Field object for the input Class.
     */
    public static Map<String, Field> getFieldMap(Class clazz) {
        Map<String, Field> m = new HashMap<String, Field>();
        PropertyDescriptor[] props = getPropertyDescriptors(clazz);
        if (props != null)
            for (PropertyDescriptor prop : props) {
                Field field = getField(clazz, prop.getName(), prop.getPropertyType());
                if (field != null)
                    m.put(prop.getName(), field);
            }
        return m;
    }

    /** Creates a new instance of Graph Mapper from an XMl definition */
    GraphMapping(Class dataClass) {

        String filename = findMappingFile(dataClass);
        InputStream stream = null;
        try {
            // Create a factory object for creating DOM parsers
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Specifies that the parser produced by this factory will validate documents as they are parsed.
            factory.setValidating(false);

            // Now use the factory to create a DOM parser
            DocumentBuilder parser = factory.newDocumentBuilder();

            // Specifies the EntityResolver to resolve DTD used in XML documents
            parser.setEntityResolver(new DefaultEntityResolver());

            // Specifies the ErrorHandler to handle warning/error/fatalError conditions
            parser.setErrorHandler(new DefaultErrorHandler());

            // Parse the file and build a Document tree to represent its content
            stream = URLHelper.getInputStream(filename);
            if (stream == null)
                throw new IOException("File not found: " + filename);
            Document document = parser.parse(stream);


            // Get the DAO class, and make sure the loaded one matches the supplied one!
            NodeList nl = document.getElementsByTagName(NODE_DAO_CLASS);
            if (nl.getLength() != 1)
                throw new RuntimeException("Can't find node '" + NODE_DAO_CLASS + "' in file " + filename);
            Node n = nl.item(0).getAttributes().getNamedItem(ATTR_CLASS);
            if (n == null)
                throw new RuntimeException("Node '" + NODE_DAO_CLASS + "' has no attribute '" + ATTR_CLASS + "' in file " + filename);

//            String clazz = n.getNodeValue();
//            if (!dataClass.getName().equals(clazz))
//                throw new RuntimeException("Expected '" + NODE_DAO_CLASS + "\\" + ATTR_CLASS + "' to have value '" + dataClass.getName() + "' not '" + clazz + "' in file " + filename);

            this.dataClass = dataClass;

            // Get the DO class, and make sure the loaded one matches the supplied one!
            nl = document.getElementsByTagName(NODE_DO_CLASS);
            if (nl.getLength() != 1)
                throw new RuntimeException("Can't find node '" + NODE_DO_CLASS + "' in file " + filename);
            n = nl.item(0).getAttributes().getNamedItem(ATTR_CLASS);
            if (n == null)
                throw new RuntimeException("Node '" + NODE_DO_CLASS + "' has no attribute '" + ATTR_CLASS + "' in file " + filename);

            String clazz = n.getNodeValue();
            try {
                domainClass = Class.forName(clazz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Domain class '" + clazz
                        + "' not found in file " + filename);
            }

            // Get the Query DO class, and make sure the loaded one matches the supplied one!
            nl = document.getElementsByTagName(NODE_QUERY_DO_CLASS);
            n = nl.getLength() == 1 ? nl.item(0).getAttributes().getNamedItem(ATTR_CLASS) : null;
            if (nl.getLength() ==1 && n == null)
                throw new RuntimeException("Node '" + NODE_QUERY_DO_CLASS + "' has no attribute '" + ATTR_CLASS + "' in file " + filename);

            if(n!=null) {
                String queryClazz = n.getNodeValue();
                try {
                    queryDomainClass = Class.forName(queryClazz);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Domain class '" + clazz
                            + "' not found in file " + filename);
                }
            }

            // Set up descriptor details
            Map<String, Field> graphFields = getFieldMap(dataClass);
            Map<String, PropertyDescriptor> graphPropertyDescriptors = getPropertyDescriptorMap(dataClass);
            domainPropertyDescriptors = getPropertyDescriptorMap(domainClass);

            // Set up descriptor details
            if(queryDomainClass!=null)
                queryDomainPropertyDescriptors = getPropertyDescriptorMap(queryDomainClass);

            // Initalize Fields
            elementMap = new LinkedHashMap<String, BaseElement>();
            keys = new LinkedHashSet<String>();
            fields = new LinkedHashMap<String, Set<String>>();
            foreignObjects = new LinkedHashMap<String, Set<String>>();
            relatedObjects = new LinkedHashMap<String, Set<String>>();
            
            //To avoid NPE in existing code, initialize the collections for default scope
            fields.put(null, new LinkedHashSet<String>()) ;
            foreignObjects.put(null, new LinkedHashSet<String>()) ;
            relatedObjects.put(null, new LinkedHashSet<String>()) ;

            // Get The Key Fields
            nl = document.getElementsByTagName(NODE_MAP_KEYS);
            if (nl.getLength() == 1) {
                Element el = (Element) nl.item(0);
                NodeList nl2 = el.getElementsByTagName(NODE_FIELD);
                for (int i = 0; i < nl2.getLength(); i++) {
                    Element el2 = (Element) nl2.item(i);
                    BaseElement baseElement = new KeyField(readAttribute(el2, ATTR_DAO_FIELD), readAttribute(el2, ATTR_DO_FIELD), readAttribute(el2, ATTR_READ_ONLY), readAttribute(el2, ATTR_NO_CLONING));
                    if (baseElement.getGraphFieldName() == null)
                        throw new RuntimeException("Key field " + (i + 1) + " has missing attribute '" + ATTR_DAO_FIELD + "' in file " + filename);
                    baseElement.graphPropertyDescriptor = graphPropertyDescriptors.get(baseElement.getGraphFieldName());
                    baseElement.graphField = graphFields.get(baseElement.getGraphFieldName());
                    elementMap.put(baseElement.getGraphFieldName(), baseElement);
                    keys.add(baseElement.getGraphFieldName());
                }
            }

            // Get The Normal Fields
            nl = document.getElementsByTagName(NODE_MAP_FIELDS);
            if (nl.getLength() == 1) {
                Element el = (Element) nl.item(0);
                NodeList nl2 = el.getElementsByTagName(NODE_FIELD);
                for (int i = 0; i < nl2.getLength(); i++) {
                    Element el2 = (Element) nl2.item(i);
                    BaseElement baseElement = new PlainField(readAttribute(el2, ATTR_DAO_FIELD), readAttribute(el2, ATTR_DO_FIELD), readAttribute(el2, ATTR_READ_ONLY), readAttribute(el2, ATTR_NO_CLONING), readAttribute(el2, ATTR_SCOPE));
                    if (baseElement.getGraphFieldName() == null)
                        throw new RuntimeException("Field " + (i + 1) + " has missing attribute '" + ATTR_DAO_FIELD + "' in file " + filename);
                    baseElement.graphPropertyDescriptor = graphPropertyDescriptors.get(baseElement.getGraphFieldName());
                    baseElement.graphField = graphFields.get(baseElement.getGraphFieldName());
                    elementMap.put(baseElement.getGraphFieldName(), baseElement);
                    addNamePerScope(fields, baseElement);
                }
            }

            // Get The Foreign Objects
            nl = document.getElementsByTagName(NODE_MAP_FOREIGN);
            if (nl.getLength() == 1) {
                Element el = (Element) nl.item(0);
                NodeList nl2 = el.getElementsByTagName(NODE_FOREIGN_OBJECT);
                for (int i = 0; i < nl2.getLength(); i++) {
                    Element el2 = (Element) nl2.item(i);
                    BaseElement baseElement = new ForeignObject(readAttribute(el2, ATTR_DAO_FIELD), readAttribute(el2, ATTR_DO_FIELD), readAttribute(el2, ATTR_READ_ONLY), readAttribute(el2, ATTR_NO_CLONING), readAttribute(el2, ATTR_SCOPE), readAttribute(el2, ATTR_TARGET_SCOPE));
                    if (baseElement.getGraphFieldName() == null)
                        throw new RuntimeException("Foreign Object " + (i + 1) + " has missing attribute '" + ATTR_DAO_FIELD + "' in file " + filename);
                    baseElement.graphPropertyDescriptor = graphPropertyDescriptors.get(baseElement.getGraphFieldName());
                    baseElement.graphField = graphFields.get(baseElement.getGraphFieldName());
                    elementMap.put(baseElement.getGraphFieldName(), baseElement);
                    addNamePerScope(foreignObjects, baseElement);

                    //Determine the foreign-keys
                    List<String> foreignKeys = new LinkedList<String>();
                    NodeList nl3 = el2.getElementsByTagName(NODE_FOREIGN_KEY);
                    if (nl3.getLength() == 0)
                        throw new RuntimeException("Foreign object '" + baseElement.getGraphFieldName() + "' does not have nodes '" + NODE_FOREIGN_KEY + "' in file " + filename);
                    for (int i2 = 0; i2 < nl3.getLength(); i2++) {
                        Element el3 = (Element) nl3.item(i2);
                        String foreignKey = readAttribute(el3, ATTR_DO_FIELD);
                        if (foreignKey == null)
                            throw new RuntimeException("Foreign object '" + baseElement.getGraphFieldName() + "\\" + (i + 1) + " has missing attribute '" + ATTR_DO_FIELD + "' in file " + filename);
                        foreignKeys.add(foreignKey);
                    }
                    ((ForeignObject) baseElement).foreignKeys = foreignKeys;
                }
            }

            // Get The Related Fields
            nl = document.getElementsByTagName(NODE_MAP_RELATED);
            if (nl.getLength() == 1) {
                Element el = (Element) nl.item(0);
                NodeList nl2 = el.getElementsByTagName(NODE_FIELD);
                for (int i = 0; i < nl2.getLength(); i++) {
                    Element el2 = (Element) nl2.item(i);
                    BaseElement baseElement = new RelatedObject(readAttribute(el2, ATTR_DAO_FIELD), readAttribute(el2, ATTR_DO_FIELD), readAttribute(el2, ATTR_READ_ONLY), readAttribute(el2, ATTR_NO_CLONING), readAttribute(el2, ATTR_SCOPE), readAttribute(el2, ATTR_TARGET_SCOPE));
                    if (baseElement.getGraphFieldName() == null)
                        throw new RuntimeException("Related Object " + (i + 1) + " has missing attribute '" + ATTR_DAO_FIELD + "' in file " + filename);
                    baseElement.graphPropertyDescriptor = graphPropertyDescriptors.get(baseElement.getGraphFieldName());
                    baseElement.graphField = graphFields.get(baseElement.getGraphFieldName());
                    elementMap.put(baseElement.getGraphFieldName(), baseElement);
                    addNamePerScope(relatedObjects, baseElement);
                }
            }

            // Get the dirty-read information
            nl = document.getElementsByTagName(NODE_DIRTY_READ);
            if (nl.getLength() == 1) {
                Element el = (Element) nl.item(0);
                dirtyReadInfo = new DirtyReadInfo(readAttribute(el, ATTR_DAO_FIELD), readAttribute(el, ATTR_ERROR_LABEL_TOKEN), readAttribute(el, ATTR_ERROR_PARAMS));
                if (dirtyReadInfo.dirtyReadDataFieldName == null)
                    throw new RuntimeException("Element '" + NODE_DIRTY_READ + "' has missing attribute '" + ATTR_DAO_FIELD + "' in file " + filename);
                if (dirtyReadInfo.dirtyReadErrorParams == null)
                    throw new RuntimeException("Element '" + NODE_DIRTY_READ + "' has missing attribute '" + ATTR_ERROR_PARAMS + "' in file " + filename);
            }

        } catch (ParserConfigurationException e) {
            log.error("Can't Parse File " + filename, e);
            throw new InstantiationError("Can't Parse File " + filename);
        } catch (SAXException e) {
            log.error("Can't Parse File " + filename, e);
            throw new InstantiationError("Can't Parse File " + filename);
        } catch (IOException e) {
            log.error("Can't Parse File " + filename, e);
            throw new InstantiationError("Can't Parse File " + filename);
        } finally {
            if (stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                    // We don't care about this error!
                }
        }

        // Validate mappings
        validateMapping();
    }

    private void validateMapping()
            throws InstantiationError {
        // Check all mapped fields have valid descriptors
        String[] names = getDataFieldNames();
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                String name = names[i];
                PropertyDescriptor d = getDataFieldDescriptor(name);
                if (d == null)
                    throw new InstantiationError("Missing GraphDataObject Property : " + name + " on " + getDataClassShortName());
                if (d.getReadMethod() == null)
                    throw new InstantiationError("Missing GraphDataObject getter for : " + name + " on " + getDataClassShortName());
                if (d.getWriteMethod() == null)
                    throw new InstantiationError("Missing GraphDataObject setter for : " + name + " on " + getDataClassShortName());
                d = getDomainFieldDescriptor(name);
                if (d == null)
                    throw new InstantiationError("Missing DomainObject Property : " + getDomainFieldName(name) + " on " + getDomainClassShortName());
                if (d.getReadMethod() == null)
                    throw new InstantiationError("Missing DomainObject getter for : " + getDomainFieldName(name) + " on " + getDomainClassShortName());
                //if(d.getWriteMethod() == null)
                //    throw new InstantiationError("Missing DO setter for : " + getDomainFieldName(name) + " on " + getDomainClassShortName());
            }
        }
        // Check all foreign key fields
        Set<String> foreignFields = getForeignFields();
        if (foreignFields != null) {
            for (String field : foreignFields) {
                List foreignKeys = getForeignKeys(field);
                if(foreignKeys!=null){
                    for (Iterator k = foreignKeys.iterator(); k.hasNext();) {
                        String name = (String) k.next();
                        PropertyDescriptor d = getRealDomainFieldDescriptor(name);

                        if (d == null)
                            throw new InstantiationError("Missing DO Property : " + name + " on " + getDomainClassShortName() + " for foreign object " + field);
                        if (d.getReadMethod() == null)
                            throw new InstantiationError("Missing DO getter for : " + name + " on " + getDomainClassShortName() + " for foreign object " + field);
                        if (d.getWriteMethod() == null)
                            throw new InstantiationError("Missing DO setter for : " + name + " on " + getDomainClassShortName() + " for foreign object " + field);
                    }
                }
            }
        }
    }

    public Class getDataClass() {
        return dataClass;
    }

    public String getDataClassName() {
        return dataClass.getName();
    }

    public String getDataClassShortName() {
        return dataClass.getSimpleName();
    }

    public Class getDomainClass() {
        return domainClass;
    }

    public String getDomainClassName() {
        return domainClass.getName();
    }

    public String getDomainClassShortName() {
        return domainClass.getSimpleName();
    }

    public Class getQueryDomainClass() {
        return queryDomainClass;
    }

    public String getQueryDomainClassName() {
        return queryDomainClass.getName();
    }

    public String getQueryDomainClassShortName() {
        return queryDomainClass.getSimpleName();
    }


    public String[] getDataFieldNames() {
        return getDataFieldNames(null);
    }

    public String[] getDataFieldNames(String targetScope) {
        Collection<String> dataFieldNames = null;
        if (targetScope == null) {
            dataFieldNames = elementMap != null ? elementMap.keySet() : null;
        } else {
            dataFieldNames = new LinkedList<String>();
            Set set = getKeyFields();
            if (set != null)
                dataFieldNames.addAll(set);
            set = getFields(targetScope);
            if (set != null)
                dataFieldNames.addAll(set);
            set = getForeignFields(targetScope);
            if (set != null)
                dataFieldNames.addAll(set);
            set = getRelatedFields(targetScope);
            if (set != null)
                dataFieldNames.addAll(set);
        }
        return dataFieldNames != null ? dataFieldNames.toArray(new String[dataFieldNames.size()]) : null;
    }

    public boolean containsDataField(String dataFieldName) {
        return elementMap != null && elementMap.containsKey(dataFieldName);
    }

    public boolean isKeyField(String dataFieldName) {
        return keys != null && keys.contains(dataFieldName);
    }

    public boolean hasKeyFields() {
        return keys != null && !keys.isEmpty();
    }

    public Set getKeyFields() {
        return keys;
    }

    public boolean isField(String dataFieldName) {
        return containsDataField(dataFieldName) && elementMap.get(dataFieldName) instanceof PlainField;
    }

    public boolean hasFields() {
        return hasFields(null);
    }

    public boolean hasFields(String targetScope) {
        return fields != null && fields.get(targetScope) != null && !fields.get(targetScope).isEmpty();
    }

    public Set getFields() {
        return getFields(null);
    }

    public Set getFields(String targetScope) {
        return fields != null ? fields.get(targetScope) : null;
    }

    public boolean isForeignField(String dataFieldName) {
        return containsDataField(dataFieldName) && elementMap.get(dataFieldName) instanceof ForeignObject;
    }

    public List getForeignKeys(String dataFieldName) {
        return isForeignField(dataFieldName) ? ((ForeignObject) elementMap.get(dataFieldName)).getForeignKeys() : null;
    }

    public Set getForeignFields() {
        return getForeignFields(null);
    }

    public Set getForeignFields(String targetScope) {
        return foreignObjects != null ? foreignObjects.get(targetScope) : null;
    }

    public boolean hasForeignFields() {
        return hasForeignFields(null);
    }

    public boolean hasForeignFields(String targetScope) {
        return foreignObjects != null && foreignObjects.get(targetScope) != null && !foreignObjects.get(targetScope).isEmpty();
    }

    public boolean isRelatedField(String dataFieldName) {
        return containsDataField(dataFieldName) && elementMap.get(dataFieldName) instanceof RelatedObject;
    }

    public boolean hasRelatedFields() {
        return hasRelatedFields(null);
    }

    public boolean hasRelatedFields(String targetScope) {
        return relatedObjects != null && relatedObjects.get(targetScope) != null && !relatedObjects.get(targetScope).isEmpty();
    }

    public Set getRelatedFields() {
        return getRelatedFields(null);
    }

    public Set getRelatedFields(String targetScope) {
        return relatedObjects != null ? relatedObjects.get(targetScope) : null;
    }

    public String getDirtyReadDataFieldName() {
        return dirtyReadInfo != null ? dirtyReadInfo.dirtyReadDataFieldName : null;
    }

    public String getDirtyReadErrorLabelToken() {
        return dirtyReadInfo != null ? dirtyReadInfo.dirtyReadErrorLabelToken : null;
    }

    public String[] getDirtyReadErrorParams() {
        return dirtyReadInfo != null ? dirtyReadInfo.dirtyReadErrorParams : null;
    }

    public boolean isReadOnly(String dataFieldName) {
        return containsDataField(dataFieldName) && elementMap.get(dataFieldName).isReadOnly();
    }

    public boolean isNoCloning(String dataFieldName) {
        return containsDataField(dataFieldName) && elementMap.get(dataFieldName).isNoCloning();
    }

    public String getDomainFieldName(String dataFieldName) {
        return containsDataField(dataFieldName) ? elementMap.get(dataFieldName).getDomainFieldName() : null;
    }



    /** Returns the Field object, if available, or the setter Method, corresponding to the input dataFieldName.
     * @param dataFieldName the name of the data field.
     * @return the Field object, if available, or the setter Method, corresponding to the input dataFieldName.
     */
    public AccessibleObject getDataMutator(String dataFieldName) {
        AccessibleObject accessibleObject = containsDataField(dataFieldName) ? elementMap.get(dataFieldName).getGraphField() : null;
        if (accessibleObject == null) {
            PropertyDescriptor pd = getDataFieldDescriptor(dataFieldName);
            if (pd != null) {
                accessibleObject = pd.getWriteMethod();
                if (!accessibleObject.isAccessible())
                    accessibleObject.setAccessible(true);
            }
        }
        return accessibleObject;
    }

    public PropertyDescriptor getDataFieldDescriptor(String dataFieldName) {
        PropertyDescriptor pd = containsDataField(dataFieldName) ? elementMap.get(dataFieldName).getGraphPropertyDescriptor() : null;
        if (pd == null)
            log.error("Tried to access descriptor for invalid field " + getDataClassShortName() + "." + dataFieldName);
        return pd;
    }

    public PropertyDescriptor getDomainFieldDescriptor(String dataFieldName) {
        return domainPropertyDescriptors != null ? domainPropertyDescriptors.get(getDomainFieldName(dataFieldName)) : null;
    }

    public PropertyDescriptor getQueryDomainFieldDescriptor(String dataFieldName) {
        return queryDomainPropertyDescriptors != null ? queryDomainPropertyDescriptors.get(getDomainFieldName(dataFieldName)) : null;
    }

    public PropertyDescriptor getRealDomainFieldDescriptor(String domainFieldName) {
        return domainPropertyDescriptors != null ? domainPropertyDescriptors.get(domainFieldName) : null;
    }

    public String getTargetScope(String dataFieldName) {
        return containsDataField(dataFieldName) ? elementMap.get(dataFieldName).getTargetScope() : null;
    }
    
    public String[] getScope(String dataFieldName) {
        return containsDataField(dataFieldName) ? elementMap.get(dataFieldName).getScope() : null;
    }

    /** Returns the mapping file for the input class.
     * @param clazz the input class.
     * @return the name of the corresponding mapping file.
     */
    private static String findMappingFile(Class clazz) {
        Class originalClass = clazz;
        String filename = null;
        while (clazz != null) {
            filename = StringHelper.replace(clazz.getName(), ".", "/") + ".mapping";
            if (log.isDebugEnabled())
                log.debug("Checking the existence of " + filename);
            try {
                URLHelper.newExtendedURL(filename);
                // No exception thrown. So the filename must be valid. Exit the loop
                break;
            } catch (MalformedURLException e) {
                if (log.isDebugEnabled())
                    log.debug(filename + " not found. Will check the mapping file for the super-class");
                filename = null;
                clazz = clazz.getSuperclass();
            }
        }
        if (filename == null) {
            String s = "Can't find mapping file for class " + originalClass.getName();
            if (log.isDebugEnabled())
                log.debug(s);
            throw new InstantiationError(s);
        }
        return filename;
    }

    /** Returns an attribute from the element. A null is returned in case the attribute is not defined. */
    private static String readAttribute(Element element, String attributeName) {
        String attribute = element.getAttribute(attributeName);
        return attribute != null && attribute.length() > 0 ? attribute : null;
    }

    /** Adds the name to the map for each scope. */
    private static void addNamePerScope(Map<String, Set<String>> map, BaseElement baseElement) {
        // Always add the name to the default scope
        Set<String> set = map.get(null);
        if (set == null) {
            set = new LinkedHashSet<String>();
            map.put(null, set);
        }
        set.add(baseElement.getGraphFieldName());

        // Now add the name for each of the input scopes
        if (baseElement.getScope() != null) {
            for (String aScope : baseElement.getScope()) {
                set = map.get(aScope);
                if (set == null) {
                    set = new LinkedHashSet<String>();
                    map.put(aScope, set);
                }
                set.add(baseElement.getGraphFieldName());
            }
        }
    }

    private static class DirtyReadInfo {

        private String dirtyReadDataFieldName; //dataFieldName to be used for dirty-read check
        private String dirtyReadErrorLabelToken; //label-token to be used in the error-message for dirty-read check
        private String[] dirtyReadErrorParams; //do[] to be used in the error-message for dirty-read check

        public DirtyReadInfo(String dirtyReadDataFieldName, String dirtyReadErrorLabelToken, String dirtyReadErrorParams) {
            this.dirtyReadDataFieldName = dirtyReadDataFieldName;
            this.dirtyReadErrorLabelToken = dirtyReadErrorLabelToken;
            this.dirtyReadErrorParams = StringHelper.split(dirtyReadErrorParams, ',');
        }

        public String getDirtyReadDataFieldName() {
            return dirtyReadDataFieldName;
        }

        public String getDirtyReadErrorLabelToken() {
            return dirtyReadErrorLabelToken;
        }

        public String[] getDirtyReadErrorParams() {
            return dirtyReadErrorParams;
        }
    }

    private static abstract class BaseElement {

        private String graphFieldName;
        private String domainFieldName;
        private boolean readOnly;
        private boolean noCloning;
        private String[] scope;
        private String targetScope;
        private PropertyDescriptor graphPropertyDescriptor;
        private Field graphField;

        public BaseElement(String graphFieldName, String domainFieldName, String readOnly, String noCloning, String scope, String targetScope) {
            this.graphFieldName = graphFieldName;
            this.domainFieldName = domainFieldName != null && domainFieldName.length() > 0 ? domainFieldName : graphFieldName;
            Boolean b = Parser.parseBoolean(readOnly);
            this.readOnly = b != null && b;
            b = Parser.parseBoolean(noCloning);
            this.noCloning = b != null && b;
            if (scope != null && scope.length() > 0) {
                String[] a = StringHelper.split(scope, ',');
                Arrays.sort(a);
                this.scope = a;
            }
            this.targetScope = targetScope;
        }

        public String getGraphFieldName() {
            return graphFieldName;
        }

        public String getDomainFieldName() {
            return domainFieldName;
        }

        public boolean isReadOnly() {
            return readOnly;
        }

        public boolean isNoCloning() {
            return noCloning;
        }

        public String[] getScope() {
            return scope;
        }

        public String getTargetScope() {
            return targetScope;
        }

        public PropertyDescriptor getGraphPropertyDescriptor() {
            return graphPropertyDescriptor;
        }

        public Field getGraphField() {
            return graphField;
        }
    }

    private static class KeyField extends BaseElement {

        public KeyField(String graphFieldName, String domainFieldName, String readOnly, String noCloning) {
            super(graphFieldName, domainFieldName, readOnly, noCloning, null, null);
        }
    }

    private static class PlainField extends BaseElement {

        public PlainField(String graphFieldName, String domainFieldName, String readOnly, String noCloning, String scope) {
            super(graphFieldName, domainFieldName, readOnly, noCloning, scope, null);
        }
    }

    private static class ForeignObject extends BaseElement {

        private List<String> foreignKeys;

        public ForeignObject(String graphFieldName, String domainFieldName, String readOnly, String noCloning, String scope, String targetScope) {
            super(graphFieldName, domainFieldName, readOnly, noCloning, scope, targetScope);
        }

        public List<String> getForeignKeys() {
            return foreignKeys;
        }
    }

    private static class RelatedObject extends BaseElement {

        public RelatedObject(String graphFieldName, String domainFieldName, String readOnly, String noCloning, String scope, String targetScope) {
            super(graphFieldName, domainFieldName, readOnly, noCloning, scope, targetScope);
        }
    }
}
