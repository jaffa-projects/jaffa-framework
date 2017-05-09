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
import junit.framework.*;

public class ListMapTest extends TestCase {
    // constants
    private static final String KEY1="Key1", KEY2="Key2", KEY3="Key3", KEY4="Key4", KEY5="Key5";
    private static final String KEY1_VALUE="Key1Value", KEY2_VALUE="Key2Value",
    KEY3_VALUE="Key3Value", KEY4_VALUE="Key4Value", KEY5_VALUE="Key5Value";

    // fixtures
    private ListMap f_listMap = null;
    private Set f_keySet = null;
    private Collection f_values = null;


    public ListMapTest(java.lang.String testName) {
        super(testName);
    }

    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ListMapTest.class);

        return suite;
    }

    public void setUp() {
        f_listMap = new ListMap();
        f_listMap.put(KEY1, KEY1_VALUE);
        f_listMap.put(KEY2, KEY2_VALUE);
        f_listMap.put(KEY3, KEY3_VALUE);
        f_listMap.put(KEY4, KEY4_VALUE);
        f_listMap.put(KEY5, KEY5_VALUE);

        f_keySet = new HashSet();
        f_keySet.add(KEY1);
        f_keySet.add(KEY2);
        f_keySet.add(KEY3);
        f_keySet.add(KEY4);
        f_keySet.add(KEY5);

        f_values = new ArrayList();
        f_values.add(KEY1_VALUE);
        f_values.add(KEY2_VALUE);
        f_values.add(KEY3_VALUE);
        f_values.add(KEY4_VALUE);
        f_values.add(KEY5_VALUE);
    }

    public void tearDown() {
        f_listMap = null;
    }


    /** Test of put method, of class org.jaffa.util.ListMap. */
    public void testPut() {
        f_listMap.put("Key6", "Key6Value");
        if ( !"Key6Value".equals( f_listMap.get("Key6") ) )
            fail("Failed to add a key 'Key6' to the ListMap");
    }

    /** Test of put method, of class org.jaffa.util.ListMap for substitution */
    public void testPutAndSubstitute() {
        // Add an existing key & see if it returns its old value
        Object obj = f_listMap.put(KEY1, "Key1NewValue");
        if ( !KEY1_VALUE.equals(obj) )
            fail("Failed to substitute value for the key '" + KEY1 + "'");
    }

    /** Test of put method, of class org.jaffa.util.ListMap for substitution */
    public void testPutAndSubstitute1() {
        // Add an existing key
        f_listMap.put(KEY1, "Key1NewValue");

        // Now make sure that the new value was indeed stored
        if ( !"Key1NewValue".equals( f_listMap.get(KEY1) ) )
            fail("Failed to add a new value for the key '" + KEY1 + "' to the ListMap");
    }

    /** Test of remove method, of class org.jaffa.util.ListMap. */
    public void testRemove() {
        f_listMap.remove(KEY1);
        if (f_listMap.get(KEY1) != null)
            fail("Failed to remove the key '" + KEY1 + "' from the ListMap");
    }

    /** Test of remove method, of class org.jaffa.util.ListMap,
     making sure that the removed object is returned */
    public void testRemove1() {
        Object value = f_listMap.remove(KEY1);
        if ( !KEY1_VALUE.equals(value) )
            fail("Failed to get the value object for key '" + KEY1 + "' when removing it from the ListMap");
    }

    /** Test of remove method, of class org.jaffa.util.ListMap from the keySet() */
    public void testRemove2() {
        Set keySet = f_listMap.keySet();
        keySet.remove(KEY1);
        if (f_listMap.get(KEY1) != null)
            fail("Failed to remove the key '" + KEY1 + "' from the ListMap");
    }

    /** Test of remove method, of class org.jaffa.util.ListMap from the keySet().Iterator() */
    public void testRemove3() {
        Iterator itr = f_listMap.keySet().iterator();
        itr.next(); itr.remove();
        if (f_listMap.get(KEY1) != null)
            fail("Failed to remove the key '" + KEY1 + "' from the ListMap");

        // make sure no exception is raised
        itr.next();
    }

    /** Test of remove method, of class org.jaffa.util.ListMap from the entrySet() */
    public void testRemove4() {
        Set entrySet = f_listMap.entrySet();
        Map.Entry entry = (Map.Entry) entrySet.iterator().next();
        entrySet.remove(entry);
        if (f_listMap.get(KEY1) != null)
            fail("Failed to remove the key '" + KEY1 + "' from the ListMap");
    }

    /** Test of remove method, of class org.jaffa.util.ListMap from the entrySet().Iterator() */
    public void testRemove5() {
        Iterator itr = f_listMap.entrySet().iterator();
        itr.next(); itr.remove();
        if (f_listMap.get(KEY1) != null)
            fail("Failed to remove the key '" + KEY1 + "' from the ListMap");

        // make sure no exception is raised
        itr.next();
    }

    /** Test of remove method, of class org.jaffa.util.ListMap from the values() */
    public void testRemove6() {
        Collection values = f_listMap.values();
        values.remove(KEY2_VALUE);
        if (f_listMap.get(KEY2) != null)
            fail("Failed to remove the key '" + KEY1 + "' from the ListMap");
    }

    /** Test of remove method, of class org.jaffa.util.ListMap from the values().Iterator() */
    public void testRemove7() {
        Iterator itr = f_listMap.values().iterator();
        itr.next(); itr.remove();
        if (f_listMap.get(KEY1) != null)
            fail("Failed to remove the key '" + KEY1 + "' from the ListMap");

        // make sure no exception is raised
        itr.next();
    }

    /** Test of keySet method, of class org.jaffa.util.ListMap. */
    public void testKeySet() {
        Set keySet = f_listMap.keySet();
        if ( keySet == null || f_keySet.size() != keySet.size() )
            fail("Failed to get the correct keySet from the ListMap");
    }

    /** Test of keySet method, of class org.jaffa.util.ListMap. */
    public void testKeySet1() {
        Set keySet = f_listMap.keySet();
        if ( !f_keySet.containsAll(keySet) )
            fail("Failed to get the keySet from the ListMap");
    }

    /** Test of keySet method, of class org.jaffa.util.ListMap.
     Check if changes to the keySet affect the underlying ListMap */
    public void testKeySet2() {
        Set keySet = f_listMap.keySet();
        keySet.remove(KEY1);
        if ( f_listMap.get(KEY1) != null )
            fail("Failed to remove '" + KEY1 + "' from the listMap by removing it from the keySet");
    }

    /** Test of keySet method, of class org.jaffa.util.ListMap.
     Check if changes to the keySet affect the underlying ListMap */
    public void testKeySet3() {
        Set keySet = f_listMap.keySet();
        keySet.clear();
        if ( !f_listMap.isEmpty() )
            fail("Failed to clear the listMap by clearing its keySet");
    }

    /** Test of keySet method, of class org.jaffa.util.ListMap.
        Create a ListMap & add elemenst in a particular order
        Make sure the collection returns values in the same order */
    public void testKeySet4() {
        // Create a ListMap & add elemenst in a particular order
        ListMap listMap = new ListMap();
        listMap.put(KEY1, KEY1_VALUE);
        listMap.put(KEY5, KEY5_VALUE);
        listMap.put(KEY2, KEY2_VALUE);
        listMap.put(KEY4, KEY4_VALUE);
        listMap.put(KEY3, KEY3_VALUE);

        // make sure the collection returns values in the same order
        Iterator itr = listMap.keySet().iterator();
        if ( !KEY1.equals( itr.next() )
        ||  !KEY5.equals( itr.next() )
        ||  !KEY2.equals( itr.next() )
        ||  !KEY4.equals( itr.next() )
        ||  !KEY3.equals( itr.next() ) )
            fail("Failed to read the Values in the Order of entry");
    }

    /** Test of clear method, of class org.jaffa.util.ListMap. */
    public void testClear() {
        f_listMap.clear();
        if ( !f_listMap.isEmpty() )
            fail("Failed to clear the listMap");
    }

    public void testValues() {
        Collection values = f_listMap.values();
        if ( values == null || f_values.size() != values.size() )
            fail("Failed to get the correct Values from the ListMap");
    }

    public void testValues1() {
        Collection values = f_listMap.values();
        if ( !f_values.containsAll(values) )
            fail("Failed to get the Values from the ListMap");
    }

    public void testValues2() {
        // check if changes to the Values affect the underlying ListMap
        Collection values = f_listMap.values();
        values.remove(KEY1_VALUE);
        if ( f_listMap.get(KEY1) != null )
            fail("Failed to un-touch the ListMap by updating the Values");
    }

    public void testValues3() {
        // check if changes to the Values affect the underlying ListMap
        Collection values = f_listMap.values();
        values.clear();
        if ( !f_listMap.isEmpty() )
            fail("Failed to un-touch the ListMap by updating the Values");
    }

    public void testValues4() {
        // Create a ListMap & add elemenst in a particular order
        ListMap listMap = new ListMap();
        listMap.put(KEY1, KEY1_VALUE);
        listMap.put(KEY5, KEY5_VALUE);
        listMap.put(KEY2, KEY2_VALUE);
        listMap.put(KEY4, KEY4_VALUE);
        listMap.put(KEY3, KEY3_VALUE);

        // make sure the collection returns values in the same order
        Iterator itr = listMap.values().iterator();
        if ( !KEY1_VALUE.equals( itr.next() )
        ||  !KEY5_VALUE.equals( itr.next() )
        ||  !KEY2_VALUE.equals( itr.next() )
        ||  !KEY4_VALUE.equals( itr.next() )
        ||  !KEY3_VALUE.equals( itr.next() ) )
            fail("Failed to read the Values in the Order of entry");
    }

    public void testValues5() {
        // Check if the values() method works when the listMap() is modified externally
        Iterator itr = f_listMap.keySet().iterator();
        Object key = itr.next();
        Object value = f_listMap.get(key);

        // now modify the listMap externally
        itr.remove();

        // check if the collection still has the 'value' in it.. it shouldnt
        Collection values = f_listMap.values();
        if ( values.contains(value) )
            fail("The Values method still returns a removed key");
    }

    /** Test of hashCode method, of class org.jaffa.util.ListMap. */
    public void testHashCode() {
        // get the hashcode
        int hashCode1 = f_listMap.hashCode();

        // create a similar listMap & check its hashCode
        ListMap listMap = new ListMap(f_listMap);
        int hashCode2 = listMap.hashCode();

        // the 2 hashcodes should be the same
        if (hashCode1 != hashCode2)
            fail("Failed to get a consistent hashCode");
    }


    public void testHashCode1() {
        // get the hashcode
        int hashCode1 = f_listMap.hashCode();

        // manipulate the listMap such that the net effect is unchanged & check its hashCode
        f_listMap.remove(KEY1);
        f_listMap.remove(KEY2);
        f_listMap.put(KEY2, KEY2_VALUE);
        f_listMap.put(KEY3, KEY3_VALUE); // just a substitution
        f_listMap.put(KEY1, KEY1_VALUE);
        int hashCode2 = f_listMap.hashCode();

        // the 2 hashcodes should be the same
        if (hashCode1 != hashCode2)
            fail("Failed to get a consistent hashCode");
    }


    /** Test of containsKey method, of class org.jaffa.util.ListMap. */
    public void testContainsKey() {
        if ( !f_listMap.containsKey(KEY1) )
            fail("Failed to find '" + KEY1 + "' in the listMap");
    }

    public void testContainsKey1() {
        if ( f_listMap.containsKey("blahblahblah") )
            fail("Method - containsKey - Failed");
    }

    /** Test of size method, of class org.jaffa.util.ListMap. */
    public void testSize() {
        if ( f_listMap.size() != f_keySet.size() ||
        f_listMap.size() != f_values.size() )
            fail("Method - size - Failed");
    }

    /** Test of entrySet method, of class org.jaffa.util.ListMap. */
    public void testEntrySet() {
        Set entrySet = f_listMap.entrySet();
        if ( entrySet == null || f_keySet.size() != entrySet.size() )
            fail("Failed to get the correct entrySet from the ListMap");
    }

    public void testEntrySet1() {
        Set entrySet = f_listMap.entrySet();

        // create a keySet & a valuesList
        Set keySet = new HashSet();
        List valuesList = new ArrayList();
        for (Iterator itr = entrySet.iterator(); itr.hasNext();) {
            Map.Entry me = (Map.Entry) itr.next();
            keySet.add( me.getKey() );
            valuesList.add( me.getValue() );
        }

        if ( !f_keySet.containsAll(keySet)
        || !f_values.containsAll(valuesList) )
            fail("Failed to get the entrySet from the ListMap");
    }

    public void testEntrySet2() {
        // check if changes to the entrySet affect the underlying ListMap
        Set entrySet = f_listMap.entrySet();

        // get a Map.Entry & store its key
        Map.Entry me = (Map.Entry) entrySet.iterator().next();
        Object key = me.getKey();

        // remove the Map.Entry from the entrySet & check if 'key' still exists in the listMap
        entrySet.remove(me);
        if ( f_listMap.get(key) != null )
            fail("Failed to remove '" + key + "' from the listMap by removing it from the entrySet");
    }

    public void testEntrySet3() {
        // check if changes to the entrySet affect the underlying ListMap
        Set entrySet = f_listMap.entrySet();
        entrySet.clear();
        if ( !f_listMap.isEmpty() )
            fail("Failed to clear the listMap by clearing its entrySet");
    }


    /** Test of containsValue method, of class org.jaffa.util.ListMap. */
    public void testContainsValue() {
        if ( !f_listMap.containsValue(KEY1_VALUE) )
            fail("Method - containsValue - Failed");
    }

    /** Test of putAll method, of class org.jaffa.util.ListMap. */
    public void testPutAll() {
        Map map = new HashMap();
        map.put("KeyA", "KeyAValue");
        map.put(KEY1, "Key1NewValue"); // should substitute the old value
        map.put("KeyZ", "KeyZValue" );
        f_listMap.putAll(map);

        if ( !"Key1NewValue".equals( f_listMap.get(KEY1) )
        || !"KeyAValue".equals( f_listMap.get("KeyA") )
        || !"KeyZValue".equals( f_listMap.get("KeyZ") ) )
            fail("Method - putAll - Failed");
    }

    /** Test of equals method, of class org.jaffa.util.ListMap. */
    public void testEquals() {
        ListMap listMap = new ListMap();
        listMap.put(KEY1, KEY1_VALUE);
        listMap.put(KEY3, KEY3_VALUE);
        listMap.put(KEY2, KEY2_VALUE);
        listMap.put(KEY5, KEY5_VALUE);
        listMap.put(KEY4, KEY4_VALUE);

        if ( !f_listMap.equals(listMap) )
            fail("Method - equals - failed");
    }

    public void testEquals1() {
        ListMap listMap = new ListMap();
        listMap.put(KEY1, KEY1_VALUE);
        listMap.put(KEY3, KEY3_VALUE);
        listMap.put(KEY2, KEY2_VALUE);
        listMap.put(KEY5, KEY5_VALUE);
        listMap.put("KeyZ", KEY4_VALUE);

        if ( f_listMap.equals(listMap) )
            fail("Method - equals - failed");
    }

    /** Test of isEmpty method, of class org.jaffa.util.ListMap. */
    public void testIsEmpty() {
        if ( f_listMap.isEmpty() )
            fail("Method - isEmpty - failed");
    }

    public void testIsEmpty1() {
        f_listMap.clear();
        if ( !f_listMap.isEmpty() )
            fail("Method - isEmpty - failed");
    }

    public void testIsEmpty2() {
        // remove() all the elements as opposed to using a clear()
        for (Iterator itr = f_listMap.keySet().iterator(); itr.hasNext(); ) {
            itr.next();
            itr.remove();
        }
        if ( !f_listMap.isEmpty() )
            fail("Method - isEmpty - failed");
    }
    /** Test of get method, of class org.jaffa.util.ListMap. */
    public void testGet() {
        if ( !KEY1_VALUE.equals( f_listMap.get(KEY1) ) )
            fail("Method - get - failed");
    }

    public void testGet1() {
        if (f_listMap.get("blah") != null)
            fail("Method - get - failed");
    }
}
