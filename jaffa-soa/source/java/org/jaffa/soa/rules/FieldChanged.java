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
package org.jaffa.soa.rules;

/**
 *
 * @author paule
 */
public class FieldChanged {

    private String object = "";
    private String field = "";
    private Object oldValue;
    private Object key1;
    private Object key2;
    private Object key3;

    /**
     * Getter for property object.
     * @return Value of property object.
     */
    public String getObject() {
        return this.object;
    }

    /**
     * Setter for property object.
     * @param object New value of property object.
     */
    public void setObject(String object) {
        this.object = object;
    }

    /**
     * Getter for property field.
     * @return Value of property field.
     */
    public String getField() {
        return this.field;
    }

    /**
     * Setter for property field.
     * @param field New value of property field.
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Getter for property oldValue.
     * @return Value of property oldValue.
     */
    public Object getOldValue() {
        return this.oldValue;
    }

    /**
     * Setter for property oldValue.
     * @param oldValue New value of property oldValue.
     */
    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * Getter for property key1.
     * @return Value of property key1.
     */
    public Object getKey1() {
        return this.key1;
    }

    /**
     * Setter for property key1.
     * @param key1 New value of property key1.
     */
    public void setKey1(Object key1) {
        this.key1 = key1;
    }

    /**
     * Getter for property key2.
     * @return Value of property key2.
     */
    public Object getKey2() {
        return key2;
    }

    /**
     * Setter for property key2.
     * @param key2 New value of property key2.
     */
    public void setKey2(Object key2) {
        this.key2 = key2;
    }

    /**
     * Getter for property key3.
     * @return Value of property key3.
     */
    public Object getKey3() {
        return key3;
    }

    /**
     * Setter for property key3.
     * @param key3 New value of property key3.
     */
    public void setKey3(Object key3) {
        this.key3 = key3;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof FieldChanged))
            return false;
        FieldChanged fc = (FieldChanged) obj;
        return (object != null ? object.equals(fc.object) : fc.object == null) &&
                (field != null ? field.equals(fc.field) : fc.field == null) &&
                (key1 != null ? key1.equals(fc.key1) : fc.key1 == null) &&
                (key2 != null ? key2.equals(fc.key2) : fc.key2 == null) &&
                (key3 != null ? key3.equals(fc.key3) : fc.key3 == null);
    }

    public String toString() {
        StringBuilder buf = new StringBuilder(object);
        buf.append('[').append(key1);
        if (key2 != null)
            buf.append(", ").append(key2);
        if (key3 != null)
            buf.append(", ").append(key3);
        buf.append("].").append(field).append('=').append(oldValue);
        return buf.toString();
    }
}
