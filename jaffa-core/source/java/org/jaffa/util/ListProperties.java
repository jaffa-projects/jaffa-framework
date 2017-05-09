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

import java.lang.reflect.Method;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

/** This class combines the utility of the Properties class with a List. Features are:
 * 1) Maintains the order of properties when loading them from an InputStream and when the properties are added manually.
 * 2) Provides a sort capability for the properties
 * 3) Provides a 'comments' attribute for each property
 * 4) Stores the properties in an OutputStream, maintaining the order of the properties and the comments
 * NOTE: Even though this class extends HashTable (since Properties extends HashTable), this implementation is not synchronized !!
 */
public class ListProperties extends Properties {

    private static final Logger log = Logger.getLogger(ListProperties.class);
    
    // Contains a reference to the 'private String saveConvert(String theString, boolean escapeSpace, boolean escapeUnicode)' method of the Properties class.
    private static Method c_saveConvertMethod = null;
    static {
        try {
            c_saveConvertMethod = Properties.class.getDeclaredMethod("saveConvert", new Class[] {String.class, Boolean.TYPE, Boolean.TYPE});
            c_saveConvertMethod.setAccessible(true);
        } catch (Exception e) {
            log.warn("Could not obtain a handle to the 'private String saveConvert(String theString, boolean escapeSpace, boolean escapeUnicode)' method of java.util.Properties class. Consequently this class will not escape keys and values when storing a property to a file.", e);
        }
    }
    
    // ************************************
    // *** FIELDS ***
    // ************************************

    /** This Map will contain the key/value pairs.
     * It will maintain the order in which the entries are made.
     * The iterators will be obtained from this map.
     */
    private Map m_valueMap = new LinkedHashMap();

    /** This Map will contain the key/property pairs.
     * The order is not important in this map.
     * It is very much internal to the ListProperties class.
     * NOTE: The iterators are obtained from the valueMap and hence this map may get out of sync !!
     */
    private Map m_propertyMap = new HashMap();

    /** This will be the contents of an InputStream after the last property entry
     */
    private String m_suffix = null;




    // ************************************
    // *** CONSTRUCTORS ***
    // ************************************

    /** Creates an empty property list with no default values. */
    public ListProperties() {
        super();
    }

    /** Creates an empty property list with the specified defaults.
     * @param defaults The defaults.
     */
    public ListProperties(Properties defaults) {
        super(defaults);
    }




    // ************************************
    // *** CUSTOM METHODS ***
    // ************************************

    /** This sorts the Properties based on the keys.
     */
    public void sort() {
        // Sort the existing keys
        Set keys = new TreeSet(m_valueMap.keySet());

        // Now build a new LinkedHashMap
        Map valueMap = new LinkedHashMap();
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            valueMap.put(key, m_valueMap.get(key));
        }

        // Assign the new Map to the field m_valueMap
        m_valueMap.clear();
        m_valueMap = valueMap;
    }

    /** Returns the comments for the property.
     * @param key The key used for adding the property.
     * @return the comments for the property.
     */
    public String getComments(String key) {
        String comments = null;
        Property property = (Property) m_propertyMap.get(key);
        if (property != null)
            comments = property.getCommentsInFile();
        return comments;
    }

    /** Sets the comments for the property.
     * Nothing will be done, in case there is no existing property for the input key.
     * @param key The key used for adding the property.
     * @param comments The comments for the property.
     */
    public void setComments(String key, String comments) {
        Property property = (Property) m_propertyMap.get(key);
        if (property != null)
            property.setCommentsInFile(comments);
    }

    /** Adds a new property, or updates if it already exists.
     * @param key The key for the property.
     * @param value The value for the property.
     * @param comments The comment for the property.
     * @return the previous value of the specified key in this property list, or null if it did not have one.
     */
    public Object setProperty(String key, String value, String comments) {
        Property property = (Property) m_propertyMap.get(key);
        if (property == null) {
            property = new Property();
            property.setKey(key);
            property.setSeparatorInFile("=");
        }
        property.setValue(value);
        property.setCommentsInFile(comments);
        m_propertyMap.put(key, property);
        return m_valueMap.put(key, value);
    }

    /** Returns debug info.
     * @return debug info.
     */
    public String toString() {
        return m_valueMap.toString();
    }



    // ************************************
    // *** PROPERTIES METHODS ***
    // ************************************

    /** Reads a property list (key and element pairs) from the input stream.
     * @param inStream the input stream
     * @throws IOException if an error occurred when reading from the input stream.
     */
    public void load(InputStream inStream) throws IOException {
        load(new PushbackReader(new BufferedReader(new InputStreamReader(inStream))));
    }
    
    /** Reads a property list (key and element pairs) from the input stream.
     * @param r the input character stream
     * @throws IOException if an error occurred when reading from the input stream.
     */
    public void load(Reader r) throws IOException {
        PushbackReader reader = r instanceof PushbackReader ? (PushbackReader) r : new PushbackReader(new BufferedReader(r));
        StringHelper.Line line = null;  // holds each line from the file
        StringBuffer commentsBlock = null;  // reference to the buffer holding the comments before the current property
        Property propertyBlock = null;  // reference to the current Property object
        boolean lineAfterContinuationChar = false;  // True if the previous line ended with a '\'

        // Parse each line of the input file
        while ((line = StringHelper.readLine(reader)) != null) {
            boolean foundContinuationChar = false;  // Set to true if a property-line ends with a '\'
            String trimmedLine = line.getContents().trim();
            if ((trimmedLine.length() == 0) ||
            (!lineAfterContinuationChar && ((trimmedLine.startsWith("#") || trimmedLine.startsWith("!"))))) {
                // Add the existing Property object, if any, to the maps
                if (propertyBlock != null) {
                    m_valueMap.put(propertyBlock.getKey(), propertyBlock.getValue());
                    m_propertyMap.put(propertyBlock.getKey(), propertyBlock);
                    propertyBlock = null;
                }

                // Create a buffer for holding the comments
                if (commentsBlock == null)
                    commentsBlock = new StringBuffer();

                // Append the line to the buffer
                commentsBlock.append(line);

            } else {  // This is the complex bit where we deal with the Property object
                if (!lineAfterContinuationChar) {
                    // Add the existing Property object, if any, to the maps
                    if (propertyBlock != null) {
                        m_valueMap.put(propertyBlock.getKey(), propertyBlock.getValue());
                        m_propertyMap.put(propertyBlock.getKey(), propertyBlock);
                        propertyBlock = null;
                    }

                    // Create a new Property object
                    propertyBlock = new Property();

                    // Add the existing commentsBlock, if any, to the property
                    if (commentsBlock != null) {
                        propertyBlock.m_commentsInFile = commentsBlock;
                        commentsBlock = null;
                    }

                }

                for (int i = 0; i < line.getContents().length(); i++) {
                    char ch = line.getContents().charAt(i);
                    if (propertyBlock.m_separatorInFile.length() == 0) {
                        if (ch == ' ' || ch == '\t') {
                            if (propertyBlock.m_key.length() == 0 || lineAfterContinuationChar)
                                propertyBlock.m_keyInFile.append(ch);
                            else
                                propertyBlock.m_separatorInFile.append(ch);
                        } else if (ch == '=' || ch == ':') {
                            propertyBlock.m_separatorInFile.append(ch);
                        } else if (ch == '\\') {
                            if (i == line.getContents().length() - 1) {
                                propertyBlock.m_keyInFile.append(ch);
                                foundContinuationChar = true;
                            } else {
                                char nextCh = line.getContents().charAt(++i);
                                propertyBlock.m_keyInFile.append(ch);
                                propertyBlock.m_keyInFile.append(nextCh);
                                propertyBlock.m_key.append(nextCh);
                            }
                        } else {
                            propertyBlock.m_keyInFile.append(ch);
                            propertyBlock.m_key.append(ch);
                        }
                    } else if (propertyBlock.m_valueInFile.length() == 0) {
                        if (ch == ' ' || ch == '\t') {
                            propertyBlock.m_separatorInFile.append(ch);
                        } else if (ch == '\\') {
                            if (i == line.getContents().length() - 1) {
                                propertyBlock.m_valueInFile.append(ch);
                                foundContinuationChar = true;
                            } else {
                                char nextCh = line.getContents().charAt(++i);
                                propertyBlock.m_valueInFile.append(ch);
                                propertyBlock.m_valueInFile.append(nextCh);
                                propertyBlock.m_value.append(nextCh);
                            }
                        } else {
                            propertyBlock.m_valueInFile.append(ch);
                            propertyBlock.m_value.append(ch);
                        }
                    } else {
                        if (ch == '\\') {
                            if (i == line.getContents().length() - 1) {
                                propertyBlock.m_valueInFile.append(ch);
                                foundContinuationChar = true;
                            } else {
                                char nextCh = line.getContents().charAt(++i);
                                propertyBlock.m_valueInFile.append(ch);
                                propertyBlock.m_valueInFile.append(nextCh);
                                propertyBlock.m_value.append(nextCh);
                            }
                        } else {
                            propertyBlock.m_valueInFile.append(ch);
                            propertyBlock.m_value.append(ch);
                        }
                    }
                }
                // Append newline characters to the appropriate buffer in the Property object
                if (propertyBlock.m_valueInFile.length() > 0)
                    propertyBlock.m_valueInFile.append(line.getEol());
                else if (propertyBlock.m_separatorInFile.length() > 0)
                    propertyBlock.m_separatorInFile.append(line.getEol());
                else
                    propertyBlock.m_keyInFile.append(line.getEol());
            }
            lineAfterContinuationChar = foundContinuationChar;
        }
        // Add the existing Property object, if any, to the maps
        if (propertyBlock != null) {
            m_valueMap.put(propertyBlock.getKey(), propertyBlock.getValue());
            m_propertyMap.put(propertyBlock.getKey(), propertyBlock);
        }
        // The commentsBlock at the end of the file will be treated as the suffix
        if (commentsBlock != null)
            m_suffix = commentsBlock.toString();
    }

    /** Adds a new property, or updates if it already exists.
     * @param key The key for the property.
     * @param value The value for the property.
     * @return the previous value of the specified key in this property list, or null if it did not have one.
     */
    public Object setProperty(String key, String value) {
        Property property = (Property) m_propertyMap.get(key);
        if (property == null) {
            property = new Property();
            property.setKey(key);
            property.setSeparatorInFile("=");
        } 
        property.setValue(value);
        m_propertyMap.put(key, property);
        return m_valueMap.put(key, value);
    }

    /** Searches for the property with the specified key in this property list. If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked. The method returns null if the property is not found.
     * @param key the property key.
     * @return the value in this property list with the specified key value.
     */
    public String getProperty(String key) {
        return getProperty(key, null);
    }

    /** Searches for the property with the specified key in this property list. If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked. The method returns the default value argument if the property is not found.
     * @param key the property key.
     * @param defaultValue a default value.
     * @return the value in this property list with the specified key value.
     */
    public String getProperty(String key, String defaultValue) {
        String value = (String) m_valueMap.get(key);
        if (value == null && super.defaults != null)
            value = super.defaults.getProperty(key);
        if (value == null)
            value = defaultValue;
        return value;
    }

    /** Returns an enumeration of all the keys in this property list, including distinct keys in the default property list if a key of the same name has not already been found from the main properties list.
     * @return an enumeration of all the keys in this property list, including the keys in the default property list.
     */
    public Enumeration propertyNames() {
        Vector propertyNames = new Vector(m_valueMap.keySet());
        if (super.defaults != null) {
            for (Enumeration enumeration = super.defaults.propertyNames(); enumeration.hasMoreElements(); ) {
                String propertyName = (String) enumeration.nextElement();
                if (!propertyNames.contains(propertyName))
                    propertyNames.add(propertyName);
            }
        }
        return propertyNames.elements();
    }

    /** Writes this property list (key and element pairs) in this Properties table to the output stream.
     * Properties from the defaults table of this Properties table (if any) are not written out by this method.
     * After the entries have been written, the output stream is flushed. The output stream remains open after this method returns.
     * @param out an output stream.
     * @param header a description of the property list.
     * @throws IOException if writing this property list to the specified output stream throws an IOException.
     */
    public void store(OutputStream out, String header) throws IOException {
        store(new BufferedWriter(new OutputStreamWriter(out)), header);
    }
    
    /** Writes this property list (key and element pairs) in this Properties table to the output stream.
     * Properties from the defaults table of this Properties table (if any) are not written out by this method.
     * After the entries have been written, the output stream is flushed. The output stream remains open after this method returns.
     * @param w an output character stream.
     * @param header a description of the property list.
     * @throws IOException if writing this property list to the specified output stream throws an IOException.
     */
    public void store(Writer w, String header) throws IOException {
        BufferedWriter writer = w instanceof BufferedWriter ? (BufferedWriter) w : new BufferedWriter(w);

        // First write the header
        if (header != null) {
            writer.write(header);
            writer.newLine();
        }

        // Now write all the properties
        for (Iterator i = m_valueMap.keySet().iterator(); i.hasNext(); ) {
            Property property = (Property) m_propertyMap.get(i.next());
            if (property != null)
                writer.write(property.getContents());
        }

        // Finally write the suffix
        if (m_suffix != null)
            writer.write(m_suffix);

        writer.flush();
    }

    /** Prints this property list out to the specified output stream. This method is useful for debugging.
     * @param out an output stream.
     */
    public void list(PrintStream out) {
        list(new PrintWriter(out));
    }

    /** Prints this property list out to the specified output stream. This method is useful for debugging.
     * @param out an output stream.
     */
    public void list(PrintWriter out) {
        out.println("-- listing properties --");
        for (Enumeration enumeration = propertyNames(); enumeration.hasMoreElements(); ) {
            String propertyName = (String) enumeration.nextElement();
            out.print(propertyName); out.print('='); out.println(getProperty(propertyName));
        }
    }





    // ************************************
    // *** MAP INTERFACE METHODS ***
    // ************************************

    /** Adds an object to the Map. If the map previously contained a mapping for this key, the old value is replaced by the specified value.
     * An IllegalArgumentException is thrown if either of the key and value are not Strings.
     * @param key The key used for adding the object.
     * @param value The object to be added.
     * @return previous value associated with specified key, or null if there was no mapping for key. A null return can also indicate that the map previously associated null with the specified key.
     */
    public Object put(Object key, Object value) {
        if (!(key instanceof String) || !(value instanceof String))
            throw new IllegalArgumentException("The key and value should be String");
        return setProperty((String) key, (String) value);
    }

    /** Removes the mapping for this key from this map if it is present.
     * The default mappings are not touched.
     * @param key key whose mapping is to be removed from the map.
     * @return previous value associated with specified key, or null if there was no mapping for key.
     */
    public Object remove(Object key) {
        m_propertyMap.remove(key);
        return m_valueMap.remove(key);
    }

    /** Returns a set view of the keys contained in this map.
     * The keys from the default mappings are not part of the Set.
     * @return a set view of the keys contained in this map.
     */
    public Set keySet() {
        return m_valueMap.keySet();
    }

    /** Removes all mappings from this map.
     * The default mappings are not touched.
     */
    public void clear() {
        m_propertyMap.clear();
        m_valueMap.clear();
        m_suffix = null;
    }

    /** Returns a collection view of the values contained in this map.
     * The values from the default mappings are not part of the Collection.
     * @return a collection view of the values contained in this map.
     */
    public Collection values() {
        return m_valueMap.values();
    }

    /** Returns the hash code value for this map.
     * The hashCode will not take into account the comments, if any.
     * The default mappings do not affect the return value.
     * @return the hash code value for this map.
     */
    public int hashCode() {
        return m_valueMap.hashCode();
    }

    /** Returns true if this map contains a mapping for the specified key.
     * The default mappings are not searched for the key.
     * @param key key whose presence in this map is to be tested.
     * @return true if this map contains a mapping for the specified key.
     */
    public boolean containsKey(Object key) {
        return m_valueMap.containsKey(key);
    }

    /** Returns the number of key-value mappings in this map.
     * The default mappings are not included in the result.
     * @return the number of key-value mappings in this map.
     */
    public int size() {
        return m_valueMap.size();
    }

    /** Returns a set view of the mappings contained in this map.
     * The entries from the default mappings are not part of the Set.
     * @return a set view of the mappings contained in this map.
     */
    public Set entrySet() {
        return m_valueMap.entrySet();
    }

    /** Returns true if this map maps one or more keys to the specified value.
     * The default mappings are not searched for the value.
     * @param value value whose presence in this map is to be tested.
     * @return true if this map maps one or more keys to the specified value.
     */
    public boolean containsValue(Object value) {
        return m_valueMap.containsValue(value);
    }

    /** Copies all of the mappings from the specified map to this map.
     * This will invoke the put() method for each entry from the specified map.
     * An IllegalArgumentException is thrown if either of the key and value are not Strings.
     * @param t Mappings to be stored in this map.
     */
    public void putAll(Map t) {
        if (t != null && t.size() > 0) {
            for (Iterator i = t.entrySet().iterator(); i.hasNext(); ) {
                Map.Entry me = (Map.Entry) i.next();
                put(me.getKey(), me.getValue());
            }
        }
    }

    /** Compares the specified object with this map for equality.
     * Returns true if the given object is also a ListProperties and the two Properties represent the same mappings.
     * The comments are not compared.
     * The default mappings are not compared.
     * @param o object to be compared for equality with this map.
     * @return true if the specified object is equal to this map.
     */
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof ListProperties) {
            ListProperties listProperties = (ListProperties) o;
            result = m_valueMap.equals(listProperties.m_valueMap);
        }
        return result;
    }

    /** Returns true if this map contains no key-value mappings.
     * The default mappings have no effect on the return value.
     * @return true if this map contains no key-value mappings.
     */
    public boolean isEmpty() {
        return m_valueMap.isEmpty();
    }

    /** Returns the value to which this map maps the specified key.
     * Returns null if the map contains no mapping for this key.
     * A return value of null does not necessarily indicate that the map contains no mapping for the key; it's also possible that the map explicitly maps the key to null.
     * The containsKey operation may be used to distinguish these two cases.
     * The default mappings are not searched for the key.
     * @param key key whose associated value is to be returned.
     * @return the value to which this map maps the specified key, or null if the map contains no mapping for this key.
     */
    public Object get(Object key) {
        return m_valueMap.get(key);
    }




    // ************************************
    // *** HASHTABLE METHODS ***
    // ************************************

    /** Returns an enumeration of the values in this hashtable.
     * Use the Enumeration methods on the returned object to fetch the elements sequentially.
     * The values from the default mappings are not part of the Enumeration.
     * @return an enumeration of the values in this hashtable.
     */
    public Enumeration elements() {
        return new Vector(m_valueMap.values()).elements();
    }

    /** Returns an enumeration of the keys in this hashtable.
     * The keys from the default mappings are not part of the Enumeration.
     * @return an enumeration of the keys in this hashtable.
     */
    public Enumeration keys() {
        return new Vector(m_valueMap.keySet()).elements();
    }



    // ************************************
    // *** CLONEABLE INTERFACE METHODS ***
    // ************************************

    /** Returns a clone of this object.
     * @return a clone of the Map.
     */
    public Object clone() {
        ListProperties obj = (ListProperties) super.clone();

        if (m_valueMap != null)
            obj.m_valueMap = (Map) ((HashMap) m_valueMap).clone();

        if (m_propertyMap != null)
            obj.m_propertyMap = (Map) ((HashMap) m_propertyMap).clone();

        obj.m_suffix = m_suffix;

        return obj;
    }

    /** For the input file, a Property object encapsulates the key, value and comments for an entry.
     */
    private class Property implements Cloneable, Serializable {

        private StringBuffer m_commentsInFile = new StringBuffer();
        private StringBuffer m_key = new StringBuffer();
        private StringBuffer m_keyInFile = new StringBuffer();
        private StringBuffer m_value = new StringBuffer();
        private StringBuffer m_valueInFile = new StringBuffer();
        private StringBuffer m_separatorInFile = new StringBuffer();


        /** Getter for property commentsInFile.
         * @return Value of property commentsInFile.
         */
        public String getCommentsInFile() {
            return m_commentsInFile.toString();
        }

        /** Getter for property key.
         * @return Value of property key.
         */
        public String getKey() {
            return m_key.toString();
        }

        /** Getter for property keyInFile.
         * @return Value of property keyInFile.
         */
        public String getKeyInFile() {
            return m_keyInFile.toString();
        }

        /** Getter for property value.
         * @return Value of property value.
         */
        public String getValue() {
            return m_value.toString();
        }

        /** Getter for property valueInFile.
         * @return Value of property valueInFile.
         */
        public String getValueInFile() {
            return m_valueInFile.toString();
        }

        /** Getter for property separatorInFile.
         * @return Value of property separatorInFile.
         */
        public String getSeparatorInFile() {
            return m_separatorInFile.toString();
        }

        /** Getter for property contents.
         * This concatenates the commentsInFile, keyInFile, separatorInFile and valueInFile.
         * @return Value of property contents.
         */
        public String getContents() {
            StringBuffer buf = new StringBuffer();
            buf.append(m_commentsInFile);
            buf.append(m_keyInFile);
            buf.append(m_separatorInFile);
            buf.append(m_valueInFile);
            return buf.toString();
        }

        /** Sets the key.
         * This will also set the keyInFile, in case the new key is different from the existing key.
         * @param key the key.
         */
        public void setKey(String key) {
            String eol = StringHelper.findEol(m_keyInFile.toString());
            if (key == null) {
                m_key.setLength(0);
                m_keyInFile.setLength(0);
                if (eol != null)
                    m_keyInFile.append(eol);
            } else if (!m_key.toString().equals(key)) {
                m_key.setLength(0);
                m_key.append(key);
                
                // Escape the key before storing it to a file
                if (c_saveConvertMethod != null) {
                    try {
                        key = (String) c_saveConvertMethod.invoke(ListProperties.this, new Object[] {key, true, true});
                    } catch (Exception e) {
                        if (log.isDebugEnabled())
                            log.debug("Error in conversion of special characters in the key '" + key + '\'', e);
                    }
                }
                m_keyInFile.setLength(0);
                m_keyInFile.append(key);
                if (eol != null)
                    m_keyInFile.append(eol);
            }
        }

        /** Sets the separatorInFile.
         * @param separatorInFile the separatorInFile.
         */
        public void setSeparatorInFile(String separatorInFile) {
            String eol = StringHelper.findEol(m_separatorInFile.toString());
            if (separatorInFile == null) {
                m_separatorInFile.setLength(0);
                if (eol != null)
                    m_separatorInFile.append(eol);
            } else if (!m_separatorInFile.toString().equals(separatorInFile)) {
                m_separatorInFile.setLength(0);
                m_separatorInFile.append(separatorInFile);
                if (eol != null)
                    m_separatorInFile.append(eol);
            }
        }

        /** Sets the value.
         * This will also set the valueInFile, in case the new value is different from the existing value.
         * @param value the value.
         */
        public void setValue(String value) {
            String eol = StringHelper.findEol(m_valueInFile.toString());
            if (value == null) {
                m_value.setLength(0);
                m_valueInFile.setLength(0);
                if (eol != null)
                    m_valueInFile.append(eol);
                else
                    m_valueInFile.append(System.getProperty("line.separator"));
            } else if (!m_value.toString().equals(value)) {
                m_value.setLength(0);
                m_value.append(value);
                
                // Escape the value before storing it to a file
                if (c_saveConvertMethod != null) {
                    try {
                        value = (String) c_saveConvertMethod.invoke(ListProperties.this, new Object[] {value, false, true});
                    } catch (Exception e) {
                        if (log.isDebugEnabled())
                            log.debug("Error in conversion of special characters in the value '" + value + '\'', e);
                    }
                }
                m_valueInFile.setLength(0);
                m_valueInFile.append(value);
                if (eol != null)
                    m_valueInFile.append(eol);
                else
                    m_valueInFile.append(System.getProperty("line.separator"));
                //Remove eol if exists on separatorInFile
                String sepEol = StringHelper.findEol(m_separatorInFile.toString());
                if(sepEol!=null) {
                	String rep = StringHelper.replace(m_separatorInFile.toString(), sepEol,"");
                	m_separatorInFile.setLength(0);
                	m_separatorInFile.append(rep);
                }
            }
        }

        /** Sets the commentsInFile.
         * It'll add an initial '#' for each non-blank line, if it doesn't start with a '#' or a '!'
         * @param commentsInFile the commentsInFile.
         */
        public void setCommentsInFile(String commentsInFile) {
            m_commentsInFile.setLength(0);
            if (commentsInFile != null) {
                StringHelper.Line line = null;
                PushbackReader reader = new PushbackReader(new StringReader(commentsInFile));
                try {
                    while ((line = StringHelper.readLine(reader)) != null) {
                        if (line.getContents() != null) {
                            String trimmedLine = line.getContents().trim();
                            // Add a '#' for a non-blank line, if it doesn't start with a '#' or a '!'
                            if (trimmedLine.length() > 0 && !trimmedLine.startsWith("#") && !trimmedLine.startsWith("!"))
                                m_commentsInFile.append("#");
                            m_commentsInFile.append(line.getContents());
                        }
                        // Add an EndOfLine
                        if (line.getEol().length() > 0)
                            m_commentsInFile.append(line.getEol());
                        else
                            m_commentsInFile.append(System.getProperty("line.separator"));
                    }
                } catch (IOException e) {
                    // This should never happen
                    m_commentsInFile.setLength(0);
                }
            }
        }

        /** Returns debug info.
         * @return debug info.
         */
        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append("<Property>");
            buf.append("<commentsInFile>"); buf.append(m_commentsInFile); buf.append("</commentsInFile>");
            buf.append("<keyInFile>"); buf.append(m_keyInFile); buf.append("</keyInFile>");
            buf.append("<separatorInFile>"); buf.append(m_separatorInFile); buf.append("</separatorInFile>");
            buf.append("<valueInFile>"); buf.append(m_valueInFile); buf.append("</valueInFile>");

            buf.append("<key>"); buf.append(m_key); buf.append("</key>");
            buf.append("<value>"); buf.append(m_value); buf.append("</value>");
            buf.append("</Property>");
            return buf.toString();
        }

        /** Returns a clone of this object.
         * @throws CloneNotSupportedException if cloning is not supported. Should never happen.
         * @return a clone of the Map.
         */
        public Object clone() throws CloneNotSupportedException {
            Property obj = (Property) super.clone();
            obj.m_commentsInFile = new StringBuffer(m_commentsInFile.toString());
            obj.m_key = new StringBuffer(m_key.toString());
            obj.m_keyInFile = new StringBuffer(m_keyInFile.toString());
            obj.m_value = new StringBuffer(m_value.toString());
            obj.m_valueInFile = new StringBuffer(m_valueInFile.toString());
            obj.m_separatorInFile = new StringBuffer(m_separatorInFile.toString());
            return obj;
        }

    }

}
