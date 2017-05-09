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
package org.jaffa.soa.graph;

import java.util.Arrays;
import java.util.Comparator;
import org.apache.log4j.Logger;
import org.jaffa.components.finder.OrderByField;
import org.jaffa.util.BeanHelper;

/**
 * This Comparator can be used for comparing Graphs based on a given array
 * of orderBy fields.
 * <p>
 * It will be much faster to apply orderBy rules in the database. However this
 * class should be used in those special occasions where it is not possible
 * to perform orderBy in the database.
 */
public class GraphComparator implements Comparator {

    private static Logger log = Logger.getLogger(GraphComparator.class);
    private OrderByField[] orderByFields;

    /**
     * Constructs a new instance of the GraphComparator.
     * @param orderByFields an array of orderBy fields, which will used to enforce ordering.
     */
    public GraphComparator(OrderByField[] orderByFields) {
        this.orderByFields = orderByFields;
    }

    public int compare(Object o1, Object o2) {
        int i = 0;
        if (o1 != null && o2 != null) {
            for (OrderByField orderByField : orderByFields) {
                try {
                    Object v1 = BeanHelper.getField(o1, orderByField.getFieldName());
                    Object v2 = BeanHelper.getField(o2, orderByField.getFieldName());
                    i = v1 != null ? (v2 != null ? (v1 instanceof Comparable ? ((Comparable) v1).compareTo(v2) : 0) : 1) : (v2 != null ? -1 : 0);
                    if (i != 0) {
                        //For a descending-sort, multiply the output by -1
                        if (orderByField.getSortAscending() != null && !orderByField.getSortAscending())
                            i *= -1;
                        break;
                    }
                } catch (Exception e) {
                    if (log.isDebugEnabled())
                        log.debug("Exception in enforcing orderBy clause '" + Arrays.toString(orderByFields) + "' on " + o1 + " and " + o2, e);
                }
            }
        } else if (o1 != null) {
            i = 1;
        } else if (o2 != null) {
            i = -1;
        }
        return i;
    }
}
