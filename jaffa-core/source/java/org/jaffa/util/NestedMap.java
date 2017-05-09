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

/*
 * NestedHashMap.java
 *
 * Created on October 10, 2004, 11:29 PM
 */

package org.jaffa.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** This decorator pattern is based on a normal HashMap (or any other Map supplied
 * in the constructor). It is also backed by a parent map, which is used if
 * a mapping is not found in the base map. The parent map is not directly
 * accessible, and is therefore 'read-only'. Any function that can modify the map,
 * works directly on the base map. The only functions that use the parent map
 * are <ul>
 * <li>get(Objetc key)
 * <li>keySet()
 * <li>containsKey(Object key)
 * <li>size()
 * <li>isEmpty()
 * </ul>
 *
 * @author PaulE
 */
public class NestedMap implements Map {
    
    Map m_base = null, m_parent = null;
    
    /** Creates a new instance.
     * This will use a new HashMap instance as the base map.
     * @param parent The parent map
     */
    public NestedMap(Map parent) {
        this(parent, null);
    }
    
    /** Creates a new instance.
     * @param parent The parent map
     * @param base The base map. If null, a new HashMap will be created.
     */
    public NestedMap(Map parent, Map base) {
        m_parent = parent;
        m_base = base != null ? base : new HashMap();
    }
    
    /** Returns the value for the specified key from the base map, and if not found, then the parent map.
     * A null will be returned, if no mapping is found.
     * @param key The key.
     * @return the value for the specified key.
     */
    public Object get(Object key) {
        return m_base.containsKey(key) ? m_base.get(key) : (m_parent != null ? m_parent.get(key) : null);
    }
    
    /** Returns a set containing the keys from both the parent and base maps.
     * @return a set containing the keys from both the parent and base maps.
     */
    public Set keySet() {
        Set s = new HashSet();
        s.addAll(m_base.keySet());
        if (m_parent != null)
            s.addAll(m_parent.keySet());
        return s;
    }
    
    /** Returns a true if the base or the parent Maps contain the input key.
     * @param key The key.
     * @return a true if the base or the parent Maps contain the input key.
     */
    public boolean containsKey(Object key) {
        boolean b = m_base.containsKey(key);
        if(!b && m_parent != null)
            b = m_parent.containsKey(key);
        return b;
    }
    
    /** Returns the number of unique keys in both parent and base maps.
     * @return the number of unique keys in both parent and base maps.
     */
    public int size() {
        return keySet().size();
    }
    
    /** Returns a true if both the parent and base maps are empty.
     * @return a true if both the parent and base maps are empty.
     */
    public boolean isEmpty() {
        boolean b = m_base.isEmpty();
        if (b && m_parent != null)
            b = m_parent.isEmpty();
        return b;
    }
    
    
    
    /** Associates the specified value with the specified key in the base map.
     * @param key the key.
     * @param value the value.
     * @return previous value associated with specified key, or null  if there was no mapping for key.
     */
    public Object put(Object key, Object value) {
        return m_base.put(key, value);
    }
    
    /** Copies all of the mappings from the specified map to the base map.
     * @param t the input map.
     */
    public void putAll(Map t) {
        m_base.putAll(t);
    }
    
    /** Removes the mapping for this key from the base map if it is present.
     * @param key the key.
     * @return previous value associated with specified key, or null if there was no mapping for key.
     */
    public Object remove(Object key) {
        return m_base.remove(key);
    }
    
    /** Clears the base map only.*/
    public void clear() {
        m_base.clear();
    }
    
    /** Returns a true if the base map contains the input value.
     * @param value The value.
     * @return a true if the base map contains the input value.
     */
    public boolean containsValue(Object value) {
        return m_base.containsValue(value);
    }
    
    /** Returns the entry set for the base map only.
     * @return the entry set for the base map only.
     */
    public Set entrySet() {
        return m_base.entrySet();
    }
    
    /** Returns a collection view of the values contained in the base map.
     * @return a collection view of the values contained in the base map.
     */
    public Collection values() {
        return m_base.values();
    }
    
}
