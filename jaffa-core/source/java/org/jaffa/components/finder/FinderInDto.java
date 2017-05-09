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

import java.util.*;

/**
 * This is the base class for all the input DTOs used by the Finder components.
 */
public class FinderInDto {

    /** Holds an array of OrderBy Fields to be used in the query. */
    private List orderByFields;
    /** Determines the max records to be returned by the query. */
    private Integer maxRecords;
    /** Determines the starting point from which records are to be returned by the query. */
    private Integer firstRecord;
    /** If true, and if firstRecord and/or maxRecords are passed to limit the output, then a separate query will be issued to find the total number of records for the query. */
    private Boolean findTotalRecords;

    /** Creates an instance of this class.
     */
    public FinderInDto() {
        orderByFields = new ArrayList();
    }

    /** Add orderByFields.
     * @param orderByFields OrderByFields.
     */
    public void addOrderByFields(OrderByField orderByFields) {
        this.orderByFields.add(orderByFields);
    }

    /** Add orderByFields at the specified position.
     * @param orderByFields OrderByFields.
     * @param index The position at which the orderByFields is to be added.
     */
    public void setOrderByFields(OrderByField orderByFields, int index) {
        //-- check bounds for index
        if ((index < 0) || (index > this.orderByFields.size()))
            throw new IndexOutOfBoundsException();

        this.orderByFields.set(index, orderByFields);
    }

    /** Add an array of orderByFields. This will overwrite existing orderByFields.
     * @param orderByFields An array of orderByFields.
     */
    public void setOrderByFields(OrderByField[] orderByFields) {
        this.orderByFields = Arrays.asList(orderByFields);
    }

    /** Clear existing orderByFields.
     */
    public void clearOrderByFields() {
        orderByFields.clear();
    }

    /** Remove orderByFields.
     * @param orderByFields OrderByFields.
     * @return A true indicates a orderByFields was removed. A false indicates, the orderByFields was not found.
     */
    public boolean removeOrderByFields(OrderByField orderByFields) {
        return this.orderByFields.remove(orderByFields);
    }

    /** Returns a orderByFields from the specified position.
     * @param index The position index.
     * @return OrderByFields.
     */
    public OrderByField getOrderByFields(int index) {
        //-- check bounds for index
        if ((index < 0) || (index > orderByFields.size()))
            throw new IndexOutOfBoundsException();

        return (OrderByField) orderByFields.get(index);
    }

    /** Returns an array of orderByFields.
     * @return An array of orderByFields.
     */
    public OrderByField[] getOrderByFields() {
        return (OrderByField[]) orderByFields.toArray(new OrderByField[0]);
    }

    /** Returns a count of orderByFields.
     * @return The count of orderByFields.
     */
    public int getOrderByFieldsCount() {
        return orderByFields.size();
    }

    /** Getter for property maxRecords.
     * @return Value of property maxRecords.
     */
    public Integer getMaxRecords() {
        return maxRecords;
    }

    /** Setter for property maxRecords.
     * @param maxRecords New value of property maxRecords.
     */
    public void setMaxRecords(Integer maxRecords) {
        this.maxRecords = maxRecords;
    }

    /** Getter for property firstRecord.
     * @return Value of property firstRecord.
     */
    public Integer getFirstRecord() {
        return firstRecord;
    }

    /** Setter for property firstRecord.
     * @param firstRecord New value of property firstRecord.
     */
    public void setFirstRecord(Integer firstRecord) {
        this.firstRecord = firstRecord;
    }

    /** Getter for property findTotalRecords.
     * @return Value of property findTotalRecords.
     */
    public Boolean getFindTotalRecords() {
        return findTotalRecords;
    }

    /** Setter for property findTotalRecords.
     * @param findTotalRecords New value of property findTotalRecords.
     */
    public void setFindTotalRecords(Boolean findTotalRecords) {
        this.findTotalRecords = findTotalRecords;
    }

    /** Returns diagnostic information.
     * @return diagnostic information.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        if (firstRecord != null)
            buf.append("<firstRecord>").append(firstRecord).append("</firstRecord>");
        if (maxRecords != null)
            buf.append("<maxRecords>").append(maxRecords).append("</maxRecords>");
        if (findTotalRecords != null)
            buf.append("<findTotalRecords>").append(findTotalRecords).append("</findTotalRecords>");
        if (orderByFields != null) {
            buf.append("<orderByFields>");
            for (Object o : orderByFields)
                buf.append(o);
            buf.append("</orderByFields>");
        }
        return buf.toString();
    }
}
