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

import java.util.*;
import java.io.*;

/**
 * This class combines the utility of the HashMap & the ListSet. Features are:
 * 1) Ensure quick random access to an object using a 'key'
 * 2) Iterate through the Map in the order in which the entries were made
 */
public class ListMap implements Map, Cloneable, Serializable {
    private static final int TYPE_KEY_SET = 0;
    private static final int TYPE_ENTRY_SET = 1;
    private static final int TYPE_VALUES = 2;

    /** This Map will contain the key-value pairs */
    private HashMap m_map = null;

    /** This List will maintain the keys in the order of entry */
    private ListSet m_list = null;


    // fields to hold the return Collections.
    private transient Set m_keySet = null;
    private transient Set m_entrySet = null;
    private transient Collection m_values = null;



    /** Creates new ListMap */
    public ListMap() {
        m_map = new HashMap();
        m_list = new ListSet();
    }

    /** Creates new ListMap specifying the initial capacity.
     * @param initialCapacity The initial capacity.
     */
    public ListMap(int initialCapacity) {
        m_map = new HashMap(initialCapacity);
        m_list = new ListSet(initialCapacity);
    }

    /** Creates new ListMap specifying the initial capacity and load factor.
     * @param initialCapacity The initial capacity.
     * @param loadFactor The loadFactor.
     */
    public ListMap(int initialCapacity, float loadFactor) {
        m_map = new HashMap(initialCapacity, loadFactor);
        m_list = new ListSet(initialCapacity);
    }

    /** Creates new ListMap from an existing Map
     * @param t An existing Map.
     */
    public ListMap(Map t) {
        if (t == null) {
            m_map = new HashMap();
            m_list = new ListSet();
        } else {
            m_map = new HashMap(t);
            m_list = new ListSet( t.keySet() );
        }
    }




    // *** MAP INTERFACE METHODS ***
    /** Adds an object to the Map. If the map previously contained a mapping for this key, the old value is replaced by the specified value.
     * @param key The key used for adding the object.
     * @param value The object to be added.
     * @return previous value associated with specified key, or null if there was no mapping for key. A null return can also indicate that the map previously associated null with the specified key.
     */
    public Object put(Object key, Object value) {
        return put(-1, key, value);
    }

    /** Removes the mapping for this key from this map if it is present.
     * @param key key whose mapping is to be removed from the map.
     * @return previous value associated with specified key, or null if there was no mapping for key.
     */
    public Object remove(Object key) {
        m_list.remove(key);
        return m_map.remove(key);
    }

    /** Returns a set view of the keys contained in this map.
     * @return a set view of the keys contained in this map.
     */
    public Set keySet() {
        if (m_keySet == null)
            m_keySet = new ListMap.KeySet();
        return m_keySet;
    }

    /** Removes all mappings from this map .*/
    public void clear() {
        m_list.clear();
        m_map.clear();
    }

    /** Returns a collection view of the values contained in this map.
     * @return a collection view of the values contained in this map.
     */
    public Collection values() {
        if (m_values == null)
            m_values = new ListMap.Values();
        return m_values;
    }

    /** Returns the hash code value for this map.
     * @return the hash code value for this map.
     */
    public int hashCode() {
        return m_map.hashCode();
    }

    /** Returns true if this map contains a mapping for the specified key.
     * @param key key whose presence in this map is to be tested.
     * @return true if this map contains a mapping for the specified key.
     */
    public boolean containsKey(Object key) {
        return m_map.containsKey(key);
    }

    /** Returns the number of key-value mappings in this map.
     * @return the number of key-value mappings in this map.
     */
    public int size() {
        return m_map.size();
    }

    /** Returns a set view of the mappings contained in this map.
     * @return a set view of the mappings contained in this map.
     */
    public Set entrySet() {
        if (m_entrySet == null)
            m_entrySet = new ListMap.EntrySet();
        return m_entrySet;
    }

    /** Returns true if this map maps one or more keys to the specified value.
     * @param value value whose presence in this map is to be tested.
     * @return true if this map maps one or more keys to the specified value.
     */
    public boolean containsValue(Object value) {
        return m_map.containsValue(value);
    }

    /** Copies all of the mappings from the specified map to this map.
     * @param t Mappings to be stored in this map.
     */
    public void putAll(Map t) {
        if (t != null) {
            m_map.putAll(t);
            m_list.addAll( t.keySet() );
        }
    }

    /** Compares the specified object with this map for equality.
     * Returns true if the given object is also a ListMap and the two Maps represent the same mappings.
     * @param o object to be compared for equality with this map.
     * @return true if the specified object is equal to this map.
     */
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof ListMap) {
            ListMap listMap = (ListMap) o;
            result = m_map.equals(listMap.m_map);
        }
        return result;
    }

    /** Returns true if this map contains no key-value mappings.
     * @return true if this map contains no key-value mappings.
     */
    public boolean isEmpty() {
        return m_map.isEmpty();
    }

    /** Returns the value to which this map maps the specified key.
     * Returns null if the map contains no mapping for this key.
     * A return value of null does not necessarily indicate that the map contains no mapping for the key; it's also possible that the map explicitly maps the key to null.
     * The containsKey operation may be used to distinguish these two cases.
     * @param key key whose associated value is to be returned.
     * @return the value to which this map maps the specified key, or null if the map contains no mapping for this key.
     */
    public Object get(Object key) {
        return m_map.get(key);
    }



    // *** Additional Methods ***
    /** Adds an object to the Map. If the map previously contained a mapping for this key, the old value is replaced by the specified value.
     * @param index The position at which the the object will be added.
     * @param key The key used for adding the object.
     * @param value The object to be added.
     * @return previous value associated with specified key, or null if there was no mapping for key. A null return can also indicate that the map previously associated null with the specified key.
     */
    public Object put(int index, Object key, Object value) {
        Object returnObject = m_map.put(key, value);

        // add to the list, only if it doesn't already exist
        if ( !m_list.contains(key) ) {
            if (index == -1)
                m_list.add(key);
            else
                m_list.add(index, key);
        }

        return returnObject;
    }

    /** Returns the mapping at the specified index.
     * @param index The position at from which the mapping is to be retrieved.
     * @return the mapping at the specified index.
     */
    public Object getByIndex(int index) {
        return get( m_list.get(index) );
    }

    /** Removes the mapping for this index from this map if it is present.
     * @param index The position at from which the mapping is to be removed.
     * @return previous value associated with position, or null if there was no mapping for position.
     */
    public Object remove(int index) {
        return remove( m_list.get(index) );
    }

    /** Returns the index in this Map of the specified key.
     * A '-1' is returned in case no such key exists.
     * @param key The key used for adding the object.
     * @return the index in this Map of the specified key.
     */
    public int indexOf(Object key) {
        return m_list.indexOf(key);
    }


    // *** CLONEABLE INTERFACE METHODS ***
    /** Returns a clone of the Map.
     * @throws CloneNotSupportedException if cloning is not supported. Should never happen.
     * @return a clone of the Map.
     */
    public Object clone() throws CloneNotSupportedException {
        ListMap obj = (ListMap) super.clone();

        if (m_map != null && m_map instanceof HashMap)
            obj.m_map = (HashMap) m_map.clone();

        if (m_list != null && m_list instanceof ListSet)
            obj.m_list = (ListSet) m_list.clone();

        // reset the transient fields
        obj.m_keySet = null;
        obj.m_entrySet = null;
        obj.m_values = null;

        return obj;
    }




    // *** PRIVATE METHODS ***
    private Iterator getIterator(int type) {
        return new ListMap.ListMapIterator(type);
    }

    private Map.Entry getEntry(Object key, Object value) {
        return new ListMap.ListMapEntry(key, value);
    }



    // *** INNER CLASSES ***
    private class ListMapIterator implements Iterator {
        private Iterator m_iterator = null;
        private int m_type;
        private Object m_lastReturned = null;

        private ListMapIterator(int type) {
            m_type = type;
            m_iterator = ListMap.this.m_list.iterator();
        }

        /** Returns true if the iteration has more elements.
         * @return true if the iteration has more elements.
         */
        public boolean hasNext() {
            return m_iterator.hasNext();
        }

        /** Returns the next element in the iteration.
         * @return the next element in the iteration.
         */
        public Object next() {
            m_lastReturned = m_iterator.next();
            Object obj = null;
            switch (m_type) {
                case TYPE_KEY_SET :
                    obj = m_lastReturned;
                    break;
                case TYPE_ENTRY_SET :
                    Object value = ListMap.this.get(m_lastReturned);
                    obj = ListMap.this.getEntry(m_lastReturned, value);
                    break;
                case TYPE_VALUES :
                    obj = ListMap.this.get(m_lastReturned);
                    break;
            }
            return obj;
        }

        /** Removes from the underlying collection the last element returned by the iterator.
         */
        public void remove() {
            m_iterator.remove();
            ListMap.this.remove(m_lastReturned);
        }
    }

    private class KeySet extends AbstractSet {
        /** Returns the number of elements in this set.
         * @return the number of elements in this set.
         */
        public int size() {
            return ListMap.this.size();
        }

        /** Returns true if this set contains the specified element.
         * @param o element whose presence in this set is to be tested.
         * @return true if this set contains the specified element.
         */
        public boolean contains(Object o) {
            return ListMap.this.containsKey(o);
        }

        /** Removes the specified element from this set if it is present.
         * @param o object to be removed from this set, if present.
         * @return true if the set contained the specified element.
         */
        public boolean remove(Object o) {
            int i = size();
            ListMap.this.remove(o);
            return ( size() != i );
        }

        /** Removes all of the elements from this set.*/
        public void clear() {
            ListMap.this.clear();
        }

        /** Returns an iterator over the elements in this set.
         * @return an iterator over the elements in this set.
         */
        public Iterator iterator() {
            return ListMap.this.getIterator(ListMap.TYPE_KEY_SET);
        }
    }


    private class EntrySet extends AbstractSet {
        /** Returns the number of elements in this set.
         * @return the number of elements in this set.
         */
        public int size() {
            return ListMap.this.size();
        }

        /** Returns true if this set contains the specified element.
         * @param o element whose presence in this set is to be tested.
         * @return true if this set contains the specified element.
         */
        public boolean contains(Object o) {
            boolean result = false;
            if (o != null && o instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) o;
                Object key = entry.getKey();
                if ( ListMap.this.containsKey(key) ) {
                    Object value1 = ListMap.this.get(key);
                    Object value2 = entry.getValue();
                    result = ( value1 == null ? value2 == null : value1.equals(value2) );
                }
            }
            return result;
        }


        /** Removes the specified element from this set if it is present.
         * @param o object to be removed from this set, if present.
         * @return true if the set contained the specified element.
         */
        public boolean remove(Object o) {
            boolean result = false;
            if (o != null && o instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) o;
                Object key = entry.getKey();
                int i = size();
                ListMap.this.remove(key);
                result = ( i != size() );
            }
            return result;
        }

        /** Removes all of the elements from this set.*/
        public void clear() {
            ListMap.this.clear();
        }

        /** Returns an iterator over the elements in this set.
         * @return an iterator over the elements in this set.
         */
        public Iterator iterator() {
            return ListMap.this.getIterator(ListMap.TYPE_ENTRY_SET);
        }
    }


    private class Values extends AbstractCollection {
        /** Returns the number of elements in this collection.
         * @return the number of elements in this collection.
         */
        public int size() {
            return ListMap.this.size();
        }

        /** Returns true if this collection contains the specified element.
         * @param o element whose presence in this collection is to be tested.
         * @return true if this collection contains the specified element.
         */
        public boolean contains(Object o) {
            return ListMap.this.containsValue(o);
        }

        /** Removes all of the elements from this collection.
         */
        public void clear() {
            ListMap.this.clear();
        }

        /** Returns an iterator over the elements in this collection.
         * @return an iterator over the elements in this collection.
         */
        public Iterator iterator() {
            return ListMap.this.getIterator(ListMap.TYPE_VALUES);
        }
    }



    private class ListMapEntry implements Map.Entry {
        Object m_key = null;
        Object m_value = null;

        private ListMapEntry(Object key, Object value) {
            m_key = key;
            m_value = value;
        }

        /** Returns the key corresponding to this entry.
         * @return the key corresponding to this entry.
         */
        public Object getKey() {
            return m_key;
        }

        /** Returns the hash code value for this map entry.
         * @return the hash code value for this map entry.
         */
        public int hashCode() {
            return (m_key==null ? 0 : m_key.hashCode() )
            + (m_value==null ? 0 : m_value.hashCode() );
        }

        /** Returns the value corresponding to this entry.
         * @return the value corresponding to this entry.
         */
        public Object getValue() {
            return m_value;
        }

        /** This is an Unsupported method. It throws the UnsupportedOperationException.
         * @param value the value to be set.
         * @return old value corresponding to the entry.
         */
        public Object setValue(Object value) {
            throw new UnsupportedOperationException();
        }

        /** Compares the specified object with this entry for equality.
         * Returns true if the given object is also a ListMap.Entry object and the two entries represent the same mapping.
         * @param o object to be compared for equality with this map entry.
         * @return true if the specified object is equal to this map entry.
         */
        public boolean equals(Object o) {
            boolean result = false;
            if (o instanceof ListMap.ListMapEntry) {
                ListMap.ListMapEntry e2 = (ListMap.ListMapEntry) o;
                result =  ( getKey()==null ?
                e2.getKey()==null : getKey().equals( e2.getKey() ) )
                && ( getValue()==null ?
                e2.getValue()==null : getValue().equals(e2.getValue() ) );
            }
            return result;
        }
    }
}
