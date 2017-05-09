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

import java.io.Serializable;
import java.util.*;

/**
 * This class is used to hold currency data. Instances of this class are immutable.
 */
public class Currency implements Cloneable, Comparable, Serializable {

    // check http://www.bsi-global.com/iso4217currency for the ISO 4217 currency codes

    /** The constant to denote US Dollar.*/
    public static String USD = "USD";

    // *** PRIVATE FIELDS ***
    private Double m_value = null;
    private String m_currencyType = null;


    /** Default constructor. This creates a Currency object with 0 value.
     * This will default to USD as the currency type.
     */
    public Currency() {
        this( new Double(0) );
    }

    /** Constructs a Currency object with the specified value.
     * This will default to USD as the currency type.
     * @param value the value of the object.
     */
    public Currency(double value) {
        this( new Double(value) );
    }

    /** Constructs a Currency object with the specified value.
     * This will default to USD as the currency type.
     * @param s the value of the object.
     */
    public Currency(String s) {
        this( new Double(s) );
    }

    /** Constructs a Currency object with the specified value.
     * This will default to USD as the currency type.
     * @param value the value of the object.
     */
    public Currency(Double value) {
        this( value, USD );
    }

    /** Constructs a Currency object with the specified value and currency type
     * A RuntimeException will be thrown if the input currency type is not supported.
     * @param value the value of the object.
     * @param currencyType the currency type.
     */
    public Currency(double value, String currencyType) {
        this( new Double(value), currencyType );
    }

    /** Constructs a Currency object with the specified value and currency type
     * A RuntimeException will be thrown if the input currency type is not supported.
     * @param s the value of the object.
     * @param currencyType the currency type.
     */
    public Currency(String s, String currencyType) {
        this( new Double(s), currencyType );
    }

    /** Constructs a Currency object with the specified value and currency type
     * A RuntimeException will be thrown if the input currency type is not supported.
     * @param value the value of the object.
     * @param currencyType the currency type.
     */
    public Currency(Double value, String currencyType) {
        // ensure that the currencyType is a valid currency code
        // the following will throw an exception, in case the currencyType is null or invalid
        java.util.Currency.getInstance(currencyType);
        
        m_value = value;
        m_currencyType = currencyType;
    }



    // *** PUBLIC METHODS ***
    // - Some standard Methods

    /** Returns a clone of the object.
     * @return a clone of the object.
     */
    public Object clone() {
        try {
            return  super.clone();
            // no more processign required since the fields are immutable
        } catch(CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            return null;
        }
    }

    /** Compares this object with another Currency object.
     * Note: this class has a natural ordering that is inconsistent with equals
     * The target object is converted to the same currencyType before the comparison is performed.
     * Hence, it is quite possible that this method might indicate a equality, while the 'equals' method may return a false.
     * @param obj the other Currency object.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Object obj) {
        // convert the obj to a Currency of the same type as the current
        Currency target = ( (Currency) obj ).convert(m_currencyType);
        return m_value.compareTo(target.m_value);
    }

    /** Compares this object with another Currency object.
     * Returns a true if both the objects have the same currency type and value.
     * @param obj the other Currency object.
     * @return a true if both the objects have the same currency type and value.
     */
    public boolean equals(Object obj) {
        if ( obj instanceof Currency ) {
            Currency target = (Currency) obj;
            return m_currencyType.equals(target.m_currencyType) && m_value.equals(target.m_value);
        } else {
            return false;
        }
    }

    /** Returns an int which will be the sum of the of the hashcodes of the value and the currency type.
     * @return  an int which will be the sum of the of the hashcodes of the value and the currency type.
     */
    public int hashCode() {
        return m_value.hashCode() + m_currencyType.hashCode();
    }

    /** Returns the diagnostic information.
     * @return the diagnostic information.
     */
    public String toString() {
        return Formatter.format(this);
    }



    // - Getters

    /** Getter for property value.
     * @return Value of property value.
     */
    public Double getValue() {
        return m_value;
    }

    /** Getter for property currencyType.
     * @return Value of property currencyType.
     */
    public String getCurrencyType() {
        return m_currencyType;
    }


    // - Currency Manipulation methods

    /** Returns a new Currency object, which has the same currency type as this object,
     * and a value which is the sum of the 2 Currency objects. The input Currency is first converted
     * to the same currency type, before adding the values.
     * @param c the other Currency object.
     * @return a new Currency object which has the same currency type as this object, and a value which is the sum of the 2 Currency objects
     */
    public Currency add(Currency c) {
        double value = m_value.doubleValue() +
        c.convert(m_currencyType).m_value.doubleValue();
        return new Currency( value, m_currencyType );
    }

    /** Returns a new Currency object, which will have the value of the current object converted to the input currency type.
     * A RuntimeException will be thrown if the input currency type is not supported.
     * @param currencyType the type to convert to.
     * @return  a new Currency object, which will have the value of the current object converted to the input currency type.
     */
    public Currency convert(String currencyType) {
        if( currencyType != null && currencyType.equals(m_currencyType) )
            return (Currency) this.clone();
        else
            // todo: just throw a RuntimeException for the time being
            throw new RuntimeException("No conversion Available");
    }

}
