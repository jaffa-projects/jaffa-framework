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

package org.jaffa.cache;

import java.util.*;

/** This implementation of the ICache interface uses the WeakHashMap for caching. <B>Note that this implementation is not synchronized.</B> If multiple threads access this cache concurrently, and at least one of the threads modifies the cache structurally, it must be synchronized externally.
 *
 * @author  GautamJ
 */
public class WeakCache implements ICache {
    
    private Map m_map = null;
    
    /** Creates a new instance of SimpleCache */
    public WeakCache() {
        m_map = new WeakHashMap();
    }
    
    /** Removes all mappings from this cache.
     */
    public void clear() {
        m_map.clear();
    }
    
    /** Returns true if this cache contains a mapping for the specified key.
     * @param key key whose presence in this cache is to be tested.
     * @return true if this cache contains a mapping for the specified key.
     */
    public boolean containsKey(Object key) {
        return m_map.containsKey(key);
    }
    
    /** Returns the value to which cache maps the specified key. Returns null if the cache contains no mapping for this key. A return value of null does not necessarily indicate that the cache contains no mapping for the key; it's also possible that the cache explicitly maps the key to null. The containsKey operation may be used to distinguish these two cases.
     * @param key key whose associated value is to be returned.
     * @return the value to which this cache maps the specified key, or null if the cache contains no mapping for this key.
     */
    public Object get(Object key) {
        return m_map.get(key);
    }
    
    /** Returns true if this cache contains no key-value mappings.
     * @return true if this cache contains no key-value mappings.
     */
    public boolean isEmpty() {
        return m_map.isEmpty();
    }
    
    /** Returns a set view of the keys contained in this cache. The set is backed by the cache, so changes to the cache are reflected in the set, and vice-versa. If the cache is modified while an iteration over the set is in progress, the results of the iteration are undefined.
     * @return a set view of the keys contained in this cache.
     */
    public Set keySet() {
        return m_map.keySet();
    }
    
    /** Associates the specified value with the specified key in this cache. If the cache previously contained a mapping for this key, the old value is replaced by the specified value.
     * @param key key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     */
    public void put(Object key, Object value) {
        m_map.put(key, value);
    }
    
    /** Removes the mapping for this key from this cache if it is present.
     * @param key key whose mapping is to be removed from the cache.
     */
    public void remove(Object key) {
        m_map.remove(key);
    }
    
    /** Returns the number of key-value mappings in this cache.
     * @return the number of key-value mappings in this cache.
     */
    public int size() {
        return m_map.size();
    }
    
}
