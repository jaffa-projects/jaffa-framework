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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.rules.fieldvalidators;

import org.jaffa.rules.KeySet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for ValidatorKey
 */
public class ValidatorKeyTest {
    private static final String KEY_0 = "key";
    private static final String KEY_1 = "classname";
    private static final String KEY_2 = "realmname";
    private static final String KEY_3 = "variationname";
    private static final String KEY_STRING = KEY_0 + "." + KEY_1 + "." + KEY_2 + "." + KEY_3;
    private KeySet target;

    /**
     * Common setup
     *
     * @throws Exception
     */
    @Before
    public void setup() throws Exception {
        target = new KeySet();
        target.getKeySet().add(KEY_1);
        target.getKeySet().add(KEY_2);
        target.getKeySet().add(KEY_3);
    }

    /**
     * Test the toString operator.
     *
     * @throws Exception
     */
    @Test
    public void testToString() throws Exception {
        assertEquals(KEY_STRING, target.toString());
    }

    /**
     * Test the hashcode operator
     *
     * @throws Exception
     */
    @Test
    public void testHashCode() throws Exception {
        assertEquals(KEY_STRING.hashCode(), target.hashCode());
    }

    /**
     * Test the equals operator.
     *
     * @throws Exception
     */
    @Test
    public void testEquals() throws Exception {
        KeySet other = new KeySet();
        other.getKeySet().add(KEY_1);
        other.getKeySet().add(KEY_2);
        other.getKeySet().add(KEY_3);
        assertTrue(target.equals(other));
        assertEquals(target, other);
        assertNotSame(target, other);
        assertEquals(target.toString(), other.toString());
    }

    /**
     * Test the equals operator after adding key strings in different order.  This makes sure that
     * order does not dictate equality.
     *
     * @throws Exception
     */
    @Test
    public void testOrderIndependence() throws Exception {
        KeySet other = new KeySet();
        other.getKeySet().add(KEY_3);
        other.getKeySet().add(KEY_1);
        other.getKeySet().add(KEY_2);
        assertTrue(target.equals(other));
        assertEquals(target, other);
        assertNotSame(target, other);
        assertEquals(target.toString(), other.toString());
    }
}
