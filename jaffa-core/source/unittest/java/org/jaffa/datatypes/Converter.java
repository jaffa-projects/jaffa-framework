/*
 * Converter.java
 *
 * Created on October 13, 2004, 1:41 PM
 */

package org.jaffa.datatypes;

/**
 *
 * @author  PaulE
 */
public class Converter {
    
    public static String abcd(StringBuffer b) {
        return b==null?null:b.toString();
    }
    static byte[] efgh(StringBuffer b) {
        return b==null?null:b.toString().getBytes();
    }
}