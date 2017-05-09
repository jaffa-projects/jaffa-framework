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
 * This class is backed by an ArrayList. Features are
 * 1) Ensure the Set functionality of unique elements(including a null) in this data structure
 * 2) Iterate through the list in the order in which the entries were made
 */
public class ListSet implements Set, Cloneable, Serializable {

    List m_set = null;


    /** Creates new ListSet */
    public ListSet() {
        m_set = new ArrayList();
    }

    /** Creates new ListSet specifying the initial capacity.
     * @param initialCapacity The initial capacity.
     */
    public ListSet(int initialCapacity) {
        m_set = new ArrayList(initialCapacity);
    }

    /** Creates new ListSet from an existing Collection.
     * @param c An existing collection.
     */
    public ListSet(Collection c) {
        m_set = new ArrayList();
        if (c != null) {
            for (Iterator itr = c.iterator(); itr.hasNext();) {
                Object obj = itr.next();
                if ( !m_set.contains(obj) )
                    m_set.add(obj);
            }
        }
    }



    // *** Set Interface methods ***
    /** Retains only the elements in this set that are contained in the specified collection.
     * @param c collection that defines which elements this set will retain.
     * @return true if this collection changed as a result of the call.
     */
    public boolean retainAll(Collection c) {
        return m_set.retainAll(c);
    }

    /** Returns true if this set contains the specified element.
     * @param o element whose presence in this set is to be tested.
     * @return true if this set contains the specified element.
     */
    public boolean contains(Object o) {
        return m_set.contains(o);
    }

    /** Returns an array containing all of the elements in this set.
     * @return an array containing all of the elements in this set.
     */
    public Object[] toArray() {
        return m_set.toArray();
    }

    /** Returns an array containing all of the elements in this set; the runtime type of the returned array is that of the specified array.
     * @param a the array into which the elements of this set are to be stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose.
     * @return an array containing all of the elements in this set; the runtime type of the returned array is that of the specified array
     */
    public Object[] toArray(Object[] a) {
        return m_set.toArray(a);
    }

    /** Returns an iterator over the elements in this set.
     * @return an iterator over the elements in this set.
     */
    public Iterator iterator() {
        return m_set.iterator();
    }

    /** Removes from this set all of its elements that are contained in the specified collection.
     * @param c collection that defines which elements will be removed from this set.
     * @return true if this set changed as a result of the call.
     */
    public boolean removeAll(Collection c) {
        return m_set.removeAll(c);
    }

    /** Removes the specified element from this set if it is present.
     * @param o object to be removed from this set, if present.
     * @return true if the set contained the specified element.
     */
    public boolean remove(Object o) {
        return m_set.remove(o);
    }

    /** Removes all of the elements from this set.*/
    public void clear() {
        m_set.clear();
    }

    /** Returns the hash code value for this set.
     * @return the hash code value for this set.
     */
    public int hashCode() {
        return m_set.hashCode();
    }

    /** Adds all of the elements in the specified collection to this set if they're not already present.
     * @param c collection whose elements are to be added to this set.
     * @return true if this set changed as a result of the call.
     */
    public boolean addAll(Collection c) {
        boolean added = false;
        if (c != null) {
            for (Iterator itr = c.iterator(); itr.hasNext();) {
                Object obj = itr.next();
                if ( !m_set.contains(obj) ) {
                    m_set.add(obj);
                    added = true;
                }
            }
        }
        return added;
    }

    /** Returns the number of elements in this set.
     * @return the number of elements in this set.
     */
    public int size() {
        return m_set.size();
    }

    /** Returns true if this set contains all of the elements of the specified collection.
     * @param c collection to be checked for containment in this set.
     * @return true if this set contains all of the elements of the specified collection.
     */
    public boolean containsAll(Collection c) {
        return m_set.containsAll(c);
    }

    /** Adds the specified element to this set if it is not already present.
     * @param o element to be added to this set.
     * @return true if this set did not already contain the specified element.
     */
    public boolean add(Object o) {
        boolean added = false;
        if ( !m_set.contains(o) ) {
            m_set.add(o);
            added = true;
        }
        return added;
    }

    /** Compares the specified object with this set for equality.
     * @param o Object to be compared for equality with this set.
     * @return true if the specified Object is equal to this set.
     */
    public boolean equals(Object o) {
        return m_set.equals(o);
    }

    /** Returns true if this set contains no elements.
     * @return true if this set contains no elements.
     */
    public boolean isEmpty() {
        return m_set.isEmpty();
    }


    // *** Additional Methods ***
    /** Adds the specified element to this set if it is not already present, at the specified index.
     * @param index The position at which the element is to be added.
     * @param o element to be added to this set.
     */
    public void add(int index, Object o) {
        if ( !m_set.contains(o) )
            m_set.add(index, o);
    }

    /** Returns the element from the specified position.
     * @param index The position from which the element is to be retrieved.
     * @return the element from the specified position.
     */
    public Object get(int index) {
        return m_set.get(index);
    }

    /** Remove the element from the specified position.
     * @param index The position from which the element is to be removed.
     * @return the element being removed.
     */
    public Object remove(int index) {
        return m_set.remove(index);
    }

    /** Returns the index of the element in this set.
     * @param o The element whose index is to be found.
     * @return the index of the element in this set.
     */
    public int indexOf(Object o) {
        return m_set.indexOf(o);
    }


    // *** CLONEABLE INTERFACE METHODS ***
    /** Returns a clone of the Set.
     * @throws CloneNotSupportedException if cloning is not supported. Should never happen.
     * @return a clone of the Set.
     */
    public Object clone() throws CloneNotSupportedException {
        Object obj = super.clone();
        if (m_set != null && m_set instanceof Cloneable)
            ( (ListSet) obj ).m_set = (List) ( (ArrayList) m_set ).clone();
        return obj;
    }
}

