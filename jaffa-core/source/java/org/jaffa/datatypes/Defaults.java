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

package org.jaffa.datatypes;

import java.util.*;

/**
 * This class has the various supported datatypes.
 */
public class Defaults {

    /** A constant for the boolean datatype */
    public static final String BOOLEAN = "BOOLEAN";

    /** A constant for the string datatype */
    public static final String STRING = "STRING";

    /** A constant for the integer datatype */
    public static final String INTEGER = "INTEGER";

    /** A constant for the decimal datatype */
    public static final String DECIMAL = "DECIMAL";

    /** A constant for the date datatype */
    public static final String DATEONLY = "DATE_ONLY";

    /** A constant for the datetime datatype */
    public static final String DATETIME = "DATE_TIME";

    /** A constant for the currency datatype */
    public static final String CURRENCY = "CURRENCY";

    /** A constant for the raw datatype */
    public static final String RAW = "RAW";

    /** A constant for the long-string datatype */
    public static final String LONG_STRING = "LONGSTRING";

    /** A constant for the long-raw datatype */
    public static final String LONG_RAW = "LONGRAW";

    /** A constant for the clob datatype */
    public static final String CLOB = "CLOB";

    /** A constant for the blob datatype */
    public static final String BLOB = "BLOB";


    private static final Map MAP = new LinkedHashMap();
    static {
        MAP.put(BOOLEAN, java.lang.Boolean.class);
        MAP.put(STRING, java.lang.String.class);
        MAP.put(INTEGER, java.lang.Long.class);
        MAP.put(DECIMAL, java.lang.Double.class);
        MAP.put(DATEONLY, org.jaffa.datatypes.DateOnly.class);
        MAP.put(DATETIME, org.jaffa.datatypes.DateTime.class);
        MAP.put(CURRENCY, org.jaffa.datatypes.Currency.class);
        MAP.put(RAW, byte[].class);
        MAP.put(LONG_STRING, java.lang.String.class);
        MAP.put(LONG_RAW, byte[].class);
        MAP.put(CLOB, java.lang.String.class);
        MAP.put(BLOB, byte[].class);
    }

    // no need for an instance of this class
    private Defaults() {
    }

    /**Returns the className for a logicalDataType.
     * @param key the logicalDataType.
     * @return the className for a logicalDataType.
     */
    public static String getClassString(String key) {
        Class clazz = (Class) MAP.get(key);
        if (clazz != null)
            return clazz.getName().equals("[B") ? "byte[]" : clazz.getName();
        else
            return null;
    }

    /**Returns the Class object for a logicalDataType.
     * @param key the logicalDataType.
     * @return the Class object for a logicalDataType.
     */
    public static Class getClass(String key) {
        return (Class) MAP.get(key);
    }

    /** Returns logicalDataType for an object.
     * NOTE: If more than one datatype utilises the same JAVA class, then any one of them will be returned.
     * @param obj the object whose datatype is to be determined.
     * @return logicalDataType for an object.
     */
    public static String getDataType(Object obj) {
        String output = null;
        Set entrySet = MAP.entrySet();
        for (Iterator itr = entrySet.iterator(); itr.hasNext(); ) {
            Map.Entry me = (Map.Entry) itr.next();
            if ( ((Class) me.getValue()).isInstance(obj)) {
                output = (String) me.getKey();
                break;
            }
        }
        return output;
    }

    /** Returns logicalDataType for an specified java class.
     * NOTE: If more than one datatype utilises the same JAVA class, then any one of them will be returned.
     * @param clazz the object whose datatype is to be determined.
     * @return logicalDataType for an object.
     */
    public static String getDataType(String clazz) {
        String output = null;
        if("byte[]".equals(clazz))
            clazz = "[B";
        Set entrySet = MAP.entrySet();
        for (Iterator itr = entrySet.iterator(); itr.hasNext(); ) {
            Map.Entry me = (Map.Entry) itr.next();
            if ( ((Class) me.getValue()).getName().equals(clazz)) {
                output = (String) me.getKey();
                break;
            }
        }
        return output;
    }
}
