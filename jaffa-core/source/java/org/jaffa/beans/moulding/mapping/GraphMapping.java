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

package org.jaffa.beans.moulding.mapping;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
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
 * @version 2.0
 * @author  PaulE
 */
public  class GraphMapping {

    private static Logger log = Logger.getLogger(GraphMapping.class);

    private Class domainClass;
    private Class dataClass;
    private Map fieldMap;
    private Map keyMap;
    private Map foreignMap;
    private Map relatedMap;
    //private List keyFields;
    private PropertyDescriptor[] dataDescriptors;
    private Map dataDescriptorMap;
    private PropertyDescriptor[] domainDescriptors;
    private Map domainDescriptorMap;

    public static PropertyDescriptor[] getPropertyDescriptors(Class clazz) {
        try {
            return Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        } catch (java.beans.IntrospectionException e) {
            log.error("Can't Introspect Mold Methods",e);
            return new PropertyDescriptor[] {};
        }
    }

    public static Map getPropertyDescriptorMap(PropertyDescriptor[] props) {
        Map m = new HashMap();
        if(props != null && props.length != 0)
            for(int i=0; i<props.length;i++) {
                PropertyDescriptor prop = props[i];
                m.put(prop.getName(), prop);
            }
        return m;
    }

    private static final String NODE_DAO_CLASS      = "data-access-object";
    private static final String NODE_DO_CLASS       = "domain-object";
    private static final String NODE_MAP_KEYS       = "map-key-fields";
    private static final String NODE_MAP_FIELDS     = "map-fields";
    private static final String NODE_MAP_FOREIGN    = "map-foreign-objects";
    private static final String NODE_MAP_RELATED    = "map-aggregate-objects";
    private static final String NODE_FIELD          = "field";
    private static final String NODE_FOREIGN_OBJECT = "foreign-object";
    private static final String NODE_FOREIGN_KEY    = "foreign-key-field";
    private static final String ATTR_CLASS          = "class";
    private static final String ATTR_DAO_FIELD      = "dao";
    private static final String ATTR_DO_FIELD       = "do";


    /** Creates a new instance of Graph Mapper from an XMl definition */
    GraphMapping(Class dataClass) {

        String filename = StringHelper.replace(dataClass.getName(),".", "/") + ".mapping";
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
            if(nl.getLength()!=1)
                throw new RuntimeException("Can't find node '" + NODE_DAO_CLASS +
                "' in file " + filename);
            Node n = nl.item(0).getAttributes().getNamedItem(ATTR_CLASS);
            if (n==null)
                throw new RuntimeException("Node '" + NODE_DAO_CLASS +
                "' has no attribute '" + ATTR_CLASS + "' in file " + filename);

            String clazz = n.getNodeValue();
            if(!dataClass.getName().equals(clazz))
                throw new RuntimeException("Expected '" + NODE_DAO_CLASS +
                "\\" + ATTR_CLASS + "' to have value '"+dataClass.getName()+"' not '"+clazz+"' in file " + filename);

            this.dataClass = dataClass;

            // Get the DO class, and make sure the loaded one matches the supplied one!
            nl = document.getElementsByTagName(NODE_DO_CLASS);
            if(nl.getLength()!=1)
                throw new RuntimeException("Can't find node '" + NODE_DO_CLASS +
                "' in file " + filename);
            n = nl.item(0).getAttributes().getNamedItem(ATTR_CLASS);
            if (n==null)
                throw new RuntimeException("Node '" + NODE_DO_CLASS +
                "' has no attribute '" + ATTR_CLASS + "' in file " + filename);

            clazz = n.getNodeValue();
            try {
                domainClass = Class.forName(clazz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Domain class '" + clazz +
                "' not found in file " + filename);
            }

            // Set up descriptor details
            dataDescriptors     = getPropertyDescriptors(dataClass);
            domainDescriptors   = getPropertyDescriptors(domainClass);
            dataDescriptorMap   = getPropertyDescriptorMap(dataDescriptors);
            domainDescriptorMap = getPropertyDescriptorMap(domainDescriptors);

            // Initalize Maps
            keyMap = new LinkedHashMap();
            fieldMap = new HashMap();
            foreignMap = new HashMap();
            relatedMap = new HashMap();

            // Get The Key Fields
            nl = document.getElementsByTagName(NODE_MAP_KEYS);
            if(nl.getLength()==1) {
                Element el = (Element) nl.item(0);
                NodeList nl2 = el.getElementsByTagName(NODE_FIELD);
                for (int i = 0; i < nl2.getLength(); i++) {
                    Element el2 = (Element) nl2.item(i);
                    // get do and dao attributes
                    Node nDao = el2.getAttributes().getNamedItem(ATTR_DAO_FIELD);
                    String daoField = null;
                    if(nDao!=null)
                        daoField = nDao.getNodeValue();
                    if(daoField==null)
                        throw new RuntimeException("Key field " + (i+1) + " has missing attribute '" +
                        ATTR_DAO_FIELD + "' in file " + filename);

                    Node nDo = el2.getAttributes().getNamedItem(ATTR_DO_FIELD);
                    String doField = null;
                    if(nDo!=null)
                        doField = nDo.getNodeValue();
                    if(doField==null)
                        doField = daoField;
                    keyMap.put(daoField, doField);
                }
            }

            // Get The Normal Fields
            nl = document.getElementsByTagName(NODE_MAP_FIELDS);
            if(nl.getLength()==1) {
                Element el = (Element) nl.item(0);
                NodeList nl2 = el.getElementsByTagName(NODE_FIELD);
                for (int i = 0; i < nl2.getLength(); i++) {
                    Element el2 = (Element) nl2.item(i);
                    // get do and dao attributes
                    Node nDao = el2.getAttributes().getNamedItem(ATTR_DAO_FIELD);
                    String daoField = null;
                    if(nDao!=null)
                        daoField = nDao.getNodeValue();
                    if(daoField==null)
                        throw new RuntimeException("Field " + (i+1) + " has missing attribute '" +
                        ATTR_DAO_FIELD + "' in file " + filename);

                    Node nDo = el2.getAttributes().getNamedItem(ATTR_DO_FIELD);
                    String doField = null;
                    if(nDo!=null)
                        doField = nDo.getNodeValue();
                    if(doField==null)
                        doField = daoField;
                    fieldMap.put(daoField, doField);
                }
            }

            // Get The Related Fields
            nl = document.getElementsByTagName(NODE_MAP_RELATED);
            if(nl.getLength()==1) {
                Element el = (Element) nl.item(0);
                NodeList nl2 = el.getElementsByTagName(NODE_FIELD);
                for (int i = 0; i < nl2.getLength(); i++) {
                    Element el2 = (Element) nl2.item(i);
                    // get do and dao attributes
                    Node nDao = el2.getAttributes().getNamedItem(ATTR_DAO_FIELD);
                    String daoField = null;
                    if(nDao!=null)
                        daoField = nDao.getNodeValue();
                    if(daoField==null)
                        throw new RuntimeException("Related Object " + (i+1) + " has missing attribute '" +
                        ATTR_DAO_FIELD + "' in file " + filename);

                    Node nDo = el2.getAttributes().getNamedItem(ATTR_DO_FIELD);
                    String doField = null;
                    if(nDo!=null)
                        doField = nDo.getNodeValue();
                    if(doField==null)
                        doField = daoField;
                    relatedMap.put(daoField, doField);
                }
            }

            // Get The Foreign Objects
            nl = document.getElementsByTagName(NODE_MAP_FOREIGN);
            if(nl.getLength()==1) {
                Element el = (Element) nl.item(0);
                NodeList nl2 = el.getElementsByTagName(NODE_FOREIGN_OBJECT);
                for (int i = 0; i < nl2.getLength(); i++) {
                    Element el2 = (Element) nl2.item(i);
                    // get do and dao attributes
                    Node nDao = el2.getAttributes().getNamedItem(ATTR_DAO_FIELD);
                    String daoField = null;
                    if(nDao!=null)
                        daoField = nDao.getNodeValue();
                    if(daoField==null)
                        throw new RuntimeException("Foreign Object " + (i+1) + " has missing attribute '" +
                        ATTR_DAO_FIELD + "' in file " + filename);

                    Node nDo = el2.getAttributes().getNamedItem(ATTR_DO_FIELD);
                    String doField = null;
                    if(nDo!=null)
                        doField = nDo.getNodeValue();
                    if(doField==null)
                        doField = daoField;

                    ArrayList foreign = new ArrayList();
                    foreign.add(doField);

                    // Now read the foreign key fields
                    NodeList nl3 = el2.getElementsByTagName(NODE_FOREIGN_KEY);
                    if(nl3.getLength()==0)
                        throw new RuntimeException("Foreign object '" + daoField + "' has nodes '" +
                        NODE_FOREIGN_KEY + "' in file " + filename);
                    for (int i2 = 0; i2 < nl3.getLength(); i2++) {
                        Element el3 = (Element) nl3.item(i2);

                        Node nf = el3.getAttributes().getNamedItem(ATTR_DO_FIELD);
                        String dof = null;
                        if(nf!=null)
                            dof = nf.getNodeValue();
                        if(dof==null)
                            throw new RuntimeException("Foreign object '" + daoField + "\\" +
                            (i+1) + " has missing attribute '" + ATTR_DO_FIELD + "' in file " + filename);

                        foreign.add(dof);
                    }

                    foreignMap.put(daoField, foreign.toArray(new String[] {}));
                }
            }
        } catch(ParserConfigurationException e) {
            log.error("Can't Parse File " + filename,e);
            throw new InstantiationError("Can't Parse File " + filename);
        } catch(SAXException e) {
            log.error("Can't Parse File " + filename,e);
            throw new InstantiationError("Can't Parse File " + filename);
        } catch(IOException e) {
            log.error("Can't Parse File " + filename,e);
            throw new InstantiationError("Can't Parse File " + filename);
        } finally {
            if (stream != null)
                try {
                    stream.close();
                } catch(IOException e) {
                    // We don't care about this error!
                }
        }

        // Validate mappings
        validateMapping();
    }

    /** Creates a new instance of DomainObjectGraph */
    protected GraphMapping( Class dataClass, Class domainClass,
                            Map keyMap,
                            Map fieldMap,
                            Map foreignMap,
                            Map relatedMap,
                            PropertyDescriptor[] dataDescriptors,
                            Map dataDescriptorMap,
                            PropertyDescriptor[] domainDescriptors,
                            Map domainDescriptorMap) {
        this.dataClass = dataClass;
        this.domainClass = domainClass;
        this.dataDescriptors = dataDescriptors;
        this.dataDescriptorMap = dataDescriptorMap;
        this.domainDescriptors = domainDescriptors;
        this.domainDescriptorMap = domainDescriptorMap;
        this.keyMap = (keyMap==null?new HashMap():keyMap);
        this.fieldMap = (fieldMap==null?new HashMap():fieldMap);
        this.foreignMap = (foreignMap==null?new HashMap():foreignMap);
        this.relatedMap = (relatedMap==null?new HashMap():relatedMap);

        // Validate mappings
        validateMapping();
    }

    private void validateMapping()
    throws InstantiationError {
        // Check all mapped fields have valid descriptors
        String[] names = getDataFieldNames();
        for(int i = 0; i < names.length; i++) {
            String name = names[i];
            PropertyDescriptor d = getDataFieldDescriptor(name);
            if(d==null)
                throw new InstantiationError("Missing DAO Property : " + name + " on " + getDataClassShortName());
            if(d.getReadMethod() == null)
                throw new InstantiationError("Missing DAO getter for : " + name + " on " + getDataClassShortName());
            if(d.getWriteMethod() == null)
                throw new InstantiationError("Missing DAO setter for : " + name + " on " + getDataClassShortName());
            d = getDomainFieldDescriptor(name);
            if(d==null)
                throw new InstantiationError("Missing DO Property : " + getDomainFieldName(name) + " on " + getDomainClassShortName());
            if(d.getReadMethod() == null)
                throw new InstantiationError("Missing DO getter for : " + getDomainFieldName(name) + " on " + getDomainClassShortName());
            //if(d.getWriteMethod() == null)
            //    throw new InstantiationError("Missing DO setter for : " + getDomainFieldName(name) + " on " + getDomainClassShortName());
        }
        // Check all foreign key fields
        for(Iterator it = getForeignFields().iterator();it.hasNext();) {
            String field = (String) it.next();
            List foreignKeys = getForeignKeys(field);
            for(Iterator k = foreignKeys.iterator();k.hasNext();) {
                String name = (String) k.next();
                PropertyDescriptor d = getRealDomainFieldDescriptor(name);

                if(d==null)
                    throw new InstantiationError("Missing DO Property : " + name + " on " + getDomainClassShortName() + " for foreign object " + field);
                if(d.getReadMethod() == null)
                    throw new InstantiationError("Missing DO getter for : " + name + " on " + getDomainClassShortName() + " for foreign object " + field);
                if(d.getWriteMethod() == null)
                    throw new InstantiationError("Missing DO setter for : " + name + " on " + getDomainClassShortName() + " for foreign object " + field);
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
        return shortName(getDataClassName());
    }

    public Class getDomainClass() {
        return domainClass;
    }

    public String getDomainClassName() {
        return domainClass.getName();
    }

    public String getDomainClassShortName() {
        return shortName(getDomainClassName());
    }

    public String[] getDataFieldNames() {
        ArrayList a = new ArrayList();
        a.addAll(keyMap.keySet());
        a.addAll(fieldMap.keySet());
        a.addAll(foreignMap.keySet());
        a.addAll(relatedMap.keySet());
        return (String[]) a.toArray(new String[] {});
    }

    public boolean containsDataField(String dataFieldName) {
        return isKeyField(dataFieldName) || isField(dataFieldName) ||
               isForeignField(dataFieldName) || isRelatedField(dataFieldName);
    }

    public boolean isKeyField(String dataFieldName) {
        return keyMap.containsKey(dataFieldName);
    }

    public boolean hasKeyFields() {
        return (keyMap!=null && keyMap.size()>0);
    }

    public Set getKeyFields() {
        return keyMap.keySet();
    }

    public boolean isField(String dataFieldName) {
        return fieldMap.containsKey(dataFieldName);
    }

    public boolean hasFields() {
        return (fieldMap!=null && fieldMap.size()>0);
    }

    public Set getFields() {
        return fieldMap.keySet();
    }

    public boolean isForeignField(String dataFieldName) {
        return foreignMap.containsKey(dataFieldName);
    }

    public List getForeignKeys(String dataFieldName) {
        List l = new ArrayList();
        Object o = foreignMap.get(dataFieldName);
        if(o instanceof String[]) {
            String[] x = (String[])o;
            for(int i=1; i<x.length;i++)
                l.add(x[i]);
        }
        return l;
    }

    public Set getForeignFields() {
        return foreignMap.keySet();
    }

    public boolean hasForeignFields() {
        return (foreignMap!=null && foreignMap.size()>0);
    }

    public boolean isRelatedField(String dataFieldName) {
        return relatedMap.containsKey(dataFieldName);
    }
    public boolean hasRelatedFields() {
        return (relatedMap!=null && relatedMap.size()>0);
    }

    public Set getRelatedFields() {
        return relatedMap.keySet();
    }


    public String getDomainFieldName(String dataFieldName) {
        String name=null;
        if(keyMap.containsKey(dataFieldName))
            name=(String)keyMap.get(dataFieldName);
        else if (fieldMap.containsKey(dataFieldName))
            name=(String)fieldMap.get(dataFieldName);
        else if (foreignMap.containsKey(dataFieldName)) {
            Object o = foreignMap.get(dataFieldName);
            if(o!=null) {
                if(o instanceof String)
                    name=(String)o;
                else if(o instanceof String[])
                    name=((String[])o)[0];
            }
        } else if (relatedMap.containsKey(dataFieldName))
            name=(String)relatedMap.get(dataFieldName);
        else
            return null;
        return (name==null?dataFieldName:name);
    }

    public PropertyDescriptor getDataFieldDescriptor(String dataFieldName) {
        PropertyDescriptor pd = (PropertyDescriptor) dataDescriptorMap.get(dataFieldName);
        if(pd==null)
            log.error("Tried to access descriptor for invalid field " + getDataClassShortName() + "." + dataFieldName);
        return pd;
    }
    public PropertyDescriptor getDomainFieldDescriptor(String dataFieldName) {
        if(containsDataField(dataFieldName)) {
            String domain = getDomainFieldName(dataFieldName);
            if(domain!=null)
                return (PropertyDescriptor) domainDescriptorMap.get(domain);
        }
        return null;
    }

    public PropertyDescriptor getRealDomainFieldDescriptor(String domainFieldName) {
        if(domainFieldName!=null)
            return (PropertyDescriptor) domainDescriptorMap.get(domainFieldName);
        return null;
    }

    private String shortName(String s) {
        int p = s.lastIndexOf(".");
        if(p <= 0 )
            return s;
        else
            return s.substring(p+1);
    }
}
