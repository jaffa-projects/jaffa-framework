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

package org.jaffa.components.finder;

/**
 * This class is used to encapsulate an element used in the OrderBy clause of a query.
 */
public class OrderByField {

    /** Holds value of property fieldName. */
    private String fieldName;

    /** Holds value of property sortAscending. */
    private Boolean sortAscending;


    /** Creates an instance of this class.
     */
    public OrderByField() {
        this(null);
    }

    /** Creates an instance of this class.
     * @param fieldName the value of property fieldName.
     */
    public OrderByField(String fieldName) {
        this(fieldName, Boolean.TRUE);
    }

    /** Creates an instance of this class.
     * @param fieldName the value of property fieldName.
     * @param sortAscending the value of property sortAscending.
     */
    public OrderByField(String fieldName, Boolean sortAscending) {
        this.fieldName = fieldName;
        this.sortAscending = sortAscending;
    }

    /** Getter for property fieldName.
     * @return Value of property fieldName.
     */
    public String getFieldName() {
        return fieldName;
    }

    /** Setter for property fieldName.
     * @param fieldName New value of property fieldName.
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /** Getter for property sortAscending.
     * @return Value of property sortAscending.
     */
    public Boolean getSortAscending() {
        return sortAscending;
    }

    /** Setter for property sortAscending.
     * @param sortAscending New value of property sortAscending.
     */
    public void setSortAscending(Boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    /** Returns diagnostic information.
     * @return diagnostic information.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer("<OrderByField>");
        buf.append("<fieldName>"); if (fieldName != null) buf.append(fieldName); buf.append("</fieldName>");
        buf.append("<sortAscending>"); if (sortAscending != null) buf.append(sortAscending); buf.append("</sortAscending>");
        buf.append("</OrderByField>");
        return buf.toString();
    }
}
