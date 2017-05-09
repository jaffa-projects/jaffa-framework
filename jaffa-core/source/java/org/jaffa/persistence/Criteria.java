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

package org.jaffa.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.log4j.Logger;


/** This object is used by the persistence layer to query the underlying store.
 */
public class Criteria implements Serializable {
    private static Logger log = Logger.getLogger(Criteria.class);

    /** The static to be used in OrderBy declarations to indicate an ascending sort*/
    public final static int ORDER_BY_ASC                      = 0x01;
    /** The static to be used in OrderBy declarations to indicate an descending sort*/
    public final static int ORDER_BY_DESC                     = 0x02;


    /** The static to be used in criteria declarations to indicate an = operator */
    public final static int RELATIONAL_EQUALS                 = 0x03;
    /** The static to be used in criteria declarations to indicate a != operator */
    public final static int RELATIONAL_NOT_EQUALS             = 0x04;
    /** The static to be used in criteria declarations to indicate an > operator */
    public final static int RELATIONAL_GREATER_THAN           = 0x05;
    /** The static to be used in criteria declarations to indicate an < operator */
    public final static int RELATIONAL_SMALLER_THAN           = 0x06;
    /** The static to be used in criteria declarations to indicate an >= operator */
    public final static int RELATIONAL_GREATER_THAN_EQUAL_TO  = 0x07;
    /** The static to be used in criteria declarations to indicate an <= operator */
    public final static int RELATIONAL_SMALLER_THAN_EQUAL_TO  = 0x08;
    /** The static to be used in criteria declarations to indicate an 'is not null' operator */
    public final static int RELATIONAL_IS_NOT_NULL            = 0x09;
    /** The static to be used in criteria declarations to indicate an 'is null' operator */
    public final static int RELATIONAL_IS_NULL                = 0x0A;
    /** The static to be used in criteria declarations to create a construct of the type "like  'Abc%'"*/
    public final static int RELATIONAL_BEGINS_WITH            = 0x0B;
    /** The static to be used in criteria declarations to create a construct of the type "not like  'Abc%'"*/
    public final static int RELATIONAL_NOT_BEGINS_WITH        = 0x18;
    /** The static to be used in criteria declarations to create a construct of the type "like  '%Abc'"*/
    public final static int RELATIONAL_ENDS_WITH              = 0x0C;
    /** The static to be used in criteria declarations to create a construct of the type "not like  '%Abc'"*/
    public final static int RELATIONAL_NOT_ENDS_WITH          = 0x19;
    /** The static to be used in criteria declarations to create a construct of the type "like  '%Abc%'"*/
    public final static int RELATIONAL_LIKE                   = 0x0D;
    /** The static to be used in criteria declarations to create a construct of the type "not like  '%Abc%'"*/
    public final static int RELATIONAL_NOT_LIKE               = 0x1A;


    /** Use this static to indicate optimistic locking on a query.
     * The record in the database will be locked, only when the persistent object is added to the database.
     * This is the default locking strategy.
     */
    public static final int LOCKING_OPTIMISTIC                = 0x0E;
    /** Use this static to indicate pessimistic locking on a query.
     * The record in the database will be locked, whenever a field is updated on the object.
     */
    public static final int LOCKING_PESSIMISTIC               = 0x0F;
    /** Use this static to indicate paranoid locking on a query.
     * The record in the database will be locked, at the instant it is read.
     */
    public static final int LOCKING_PARANOID                  = 0x10;
    /** Use this static to indicate that the persistent object cannot be modified.
     */
    public static final int LOCKING_READ_ONLY                 = 0x11;


    /** Use this static to indicate the max() function in a query */
    public static final int FUNCTION_MAX                      = 0x12;
    /** Use this static to indicate the min() function in a query */
    public static final int FUNCTION_MIN                      = 0x13;
    /** Use this static to indicate the sum() function in a query */
    public static final int FUNCTION_SUM                      = 0x14;
    /** Use this static to indicate the avg() function in a query */
    public static final int FUNCTION_AVG                      = 0x15;
    /** Use this static to indicate the count() function in a query */
    public static final int FUNCTION_COUNT                    = 0x16;
    /** Use this static to indicate the current_timestamp function in a query */
    public static final int FUNCTION_CURRENT_DATE_TIME        = 0x17;


    /** Use this convenience static when passing the ID for the count() function in a query. This may internally be used by the optimization for checking the existence by primary-key */
    public static final String ID_FUNCTION_COUNT              = "c";


    private String m_table = null;
    private int m_locking = LOCKING_OPTIMISTIC;
    private Collection m_criteriaEntries = null;
    private Collection m_aggregates = null;
    private Collection m_innerCriteriaEntries = null;
    private Collection m_orderByElements = null;
    private Collection m_functionEntries = null;
    private Collection m_groupByElements = null;
    private Integer m_firstResult;
    private Integer m_maxResults;
    private UOW m_uow = null;


    /** Returns the UOW against which the query is to be performed.
     * @return the UOW against which the query is to be performed.
     */
    public UOW getUow() {
        return m_uow;
    }

    /** Sets the UOW on the criteria. This is mainly used by the DataSource to assign the UOW on the persistent objects.
     * @param uow  The UOW.
     */
    public void setUow(UOW uow) {
        m_uow = uow;
    }

    /** Returns the name of the persistent class on which the query is to be performed.
     * @return the name of the persistent class.
     */
    public String getTable() {
        return m_table;
    }

    /** Set the persistent class name. This will determine the database-table on which the query is to be performed.
     * @param table  The name of the persistent class.
     */
    public void setTable(String table) {
        m_table = table;
    }

    /** Returns the locking strategy for a query.
     * @return the locking strategy for a query.
     */
    public int getLocking() {
        return m_locking;
    }

    /** Set the locking strategy for a query.
     * @param locking the locking strategy.
     */
    public void setLocking(int locking) {
        m_locking = locking;
    }

    /** Add a criteria entry. The operator '=' is used.
     * @param name The name of the field.
     * @param value The value to be assigned to the field.
     */
    public void addCriteria(String name, Object value) {
        addCriteria(new CriteriaEntry(name, value));
    }

    /** Add a criteria entry.
     * @param name The name of the field.
     * @param operator The operator to be used in the assignment.
     * @param value The value to be assigned to the field.
     */
    public void addCriteria(String name, int operator, Object value) {
        addCriteria(new CriteriaEntry(name, operator, value));
    }

    /** Add a criteria entry. This method is to be used for unary operator like 'null' and 'not null'.
     * @param name The name of the field.
     * @param operator The operator to be used in the assignment.
     */
    public void addCriteria(String name, int operator) {
        addCriteria(new CriteriaEntry(name, operator));
    }

    /** Add an OR criteria entry. The operator '=' is used.
     * @param name The name of the field.
     * @param value The value to be assigned to the field.
     */
    public void addOrCriteria(String name, Object value) {
        CriteriaEntry criteriaEntry = new CriteriaEntry(name, value);
        criteriaEntry.setLogic(CriteriaEntry.LOGICAL_OR);
        addCriteria(criteriaEntry);
    }

    /** Add an OR criteria entry.
     * @param name The name of the field.
     * @param operator The operator to be used in the assignment.
     * @param value The value to be assigned to the field.
     */
    public void addOrCriteria(String name, int operator, Object value) {
        CriteriaEntry criteriaEntry = new CriteriaEntry(name, operator, value);
        criteriaEntry.setLogic(CriteriaEntry.LOGICAL_OR);
        addCriteria(criteriaEntry);
    }

    /** Add an OR criteria entry. This method is to be used for unary operator like 'null' and 'not null'.
     * @param name The name of the field.
     * @param operator The operator to be used in the assignment.
     */
    public void addOrCriteria(String name, int operator) {
        CriteriaEntry criteriaEntry = new CriteriaEntry(name, operator);
        criteriaEntry.setLogic( CriteriaEntry.LOGICAL_OR );
        addCriteria(criteriaEntry);
    }

    /** Add a dual criteria entry. The operator '=' is used.
     * @param name The name of the first field.
     * @param name2 The name of the second field.
     */
    public void addDualCriteria(String name, String name2) {
        addCriteria(new CriteriaEntry(name, name2, true));
    }

    /** Add a dual criteria entry.
     * @param name The name of the first field.
     * @param operator The operator to be used in the assignment.
     * @param name2 The name of the second field.
     */
    public void addDualCriteria(String name, int operator, String name2) {
        addCriteria(new CriteriaEntry(name, operator, name2, true, null));
    }

    /** Add a criteria entry to signify a join condition between 2 persistent classes. The operator '=' is used.
     * @param name The field of the persistent class which corresponds to this Criteria object.
     * @param name2 The field of the persistent class, against which the join is to be made.
     */
    public void addInnerCriteria(String name, String name2) {
        if (m_innerCriteriaEntries == null)
            m_innerCriteriaEntries = new ArrayList();
        m_innerCriteriaEntries.add( new CriteriaEntry(name, name2, true) );
    }

    /** Add a criteria entry to signify a join condition between 2 persistent classes.
     * @param name The field of the persistent class which corresponds to this Criteria object.
     * @param operator The operator to be used in the assignment.
     * @param name2 The field of the persistent class, against which the join is to be made.
     */
    public void addInnerCriteria(String name, int operator, String name2) {
        if (m_innerCriteriaEntries == null)
            m_innerCriteriaEntries = new ArrayList();
        m_innerCriteriaEntries.add( new CriteriaEntry(name, operator, name2, true, null) );
    }

    /** Clears the inner criteria entries.
     */
    public void clearInnerCriteria() {
        if (m_innerCriteriaEntries != null)
            m_innerCriteriaEntries.clear();
    }

    /** Add another criteria object to indicate a join.
     * @param criteria The criteria object.
     */
    public void addAggregate(Criteria criteria) {
        if (m_aggregates == null)
            m_aggregates = new ArrayList();
        m_aggregates.add(criteria);
    }

    /** Add an atomic criteria object. This is used to create combinations of AND/OR clauses.
     * @param atomicCriteria The atomic criteria object
     */
    public void addAtomic(AtomicCriteria atomicCriteria) {
        AtomicCriteriaEntry atomicCriteriaEntry = new AtomicCriteriaEntry(atomicCriteria);
        atomicCriteriaEntry.setLogic(CriteriaEntry.LOGICAL_AND);
        addCriteria(atomicCriteriaEntry);
    }

    /** Add an OR-ed atomic criteria object. This is used to create combinations of AND/OR clauses.
     * @param atomicCriteria The atomic criteria object
     */
    public void addOrAtomic(AtomicCriteria atomicCriteria) {
        atomicCriteria.setOrLogic(true);
        AtomicCriteriaEntry atomicCriteriaEntry = new AtomicCriteriaEntry(atomicCriteria);
        atomicCriteriaEntry.setLogic(CriteriaEntry.LOGICAL_OR);
        addCriteria(atomicCriteriaEntry);
    }

    /** Add an order by element. It'll default to the ascending sort.
     * @param orderByElement The name of the field to be used in the order by clause.
     */
    public void addOrderBy(String orderByElement) {
        addOrderBy(orderByElement, ORDER_BY_ASC);
    }

    /** Add an order by element, specifying the ordering type to be used.
     * @param orderByElement The name of the field to be used in the order by clause.
     * @param ordering indicates an ascending or a descending sort.
     */
    public void addOrderBy(String orderByElement, int ordering) {
        if (m_orderByElements != null && m_orderByElements.size() > 0) {
            for (Object o : m_orderByElements){
                if(((OrderBy)o).getOrderByElement().equalsIgnoreCase(orderByElement)){
                    log.warn("Order by criteria already defined for " + orderByElement + " on " + getTable());
                    return;
                }
            }
        }

        if (m_orderByElements == null)
            m_orderByElements = new ArrayList();

        m_orderByElements.add( new OrderBy(orderByElement, ordering) );
    }

    /** Clear the order by entries.
     */
    public void clearOrderBy() {
        if (m_orderByElements != null)
            m_orderByElements.clear();
    }

    /** Returns the criteria entries.
     * @return A collection of Criteria.CriteriaEntry objects.
     */
    public Collection getCriteriaEntries() {
        return m_criteriaEntries;
    }

    /** Returns a collection of Criteria objects, indicating the joins to be performed.
     * @return A collection of Criteria objects.
     */
    public Collection getAggregates() {
        return m_aggregates;
    }

    /** Returns a collection of criteria entries to signify the join conditions.
     * @return A collection of Criteria.CriteriaEntry objects.
     */
    public Collection getInners() {
        return m_innerCriteriaEntries;
    }

    /** Returns a collection of order by elements.
     * @return A collection of Criteria.OrderBy objects.
     */
    public Collection getOrderBys() {
        return m_orderByElements;
    }

    /** Add a function entry.
     * @param function The type of function.
     * @param name The name of the field.
     * @param id An unique identifier. This will be used to extract the result of the function from the output Map.
     */
    public void addFunction(int function, String name, String id) {
        addFunction(new FunctionEntry(function, name, id));
    }

    /** Add a function entry.
     * @param function The type of function.
     * @param distinct True if distinct non-null values are to be considered. Else all non-null values will be considered.
     * @param name The name of the field.
     * @param id An unique identifier. This will be used to extract the result of the function from the output Map.
     */
    public void addFunction(int function, boolean distinct, String name, String id) {
        addFunction(new FunctionEntry(function, distinct, name, id));
    }

    /** Returns the function entries.
     * @return A collection of Criteria.FunctionEntry objects.
     */
    public Collection getFunctionEntries() {
        return m_functionEntries;
    }

    /** Add a group by element
     * @param name The name of the field to be used in the group by clause.
     * @param id An unique identifier. This will be used to extract the result of the function from the output Map.
     */
    public void addGroupBy(String name, String id) {
        if (m_groupByElements == null)
            m_groupByElements = new ArrayList();
        m_groupByElements.add(new GroupBy(name, id));
    }

    /** Returns a collection of group by elements.
     * @return A collection of Criteria.GroupBy objects.
     */
    public Collection getGroupBys() {
        return m_groupByElements;
    }


    protected void addCriteria(CriteriaEntry criteriaEntry) {
        if (m_criteriaEntries == null)
            m_criteriaEntries = new ArrayList();
        m_criteriaEntries.add(criteriaEntry);
    }

    private void addFunction(FunctionEntry functionEntry) {
        if (m_functionEntries == null)
            m_functionEntries = new ArrayList();
        m_functionEntries.add(functionEntry);
    }

    /** Getter for property firstResult.
     * @return Value of property firstResult.
     */
    public Integer getFirstResult() {
        return m_firstResult;
    }

    /** Setter for property firstResult.
     * @param firstResult The start position of the first result, numbered from 0.
     */
    public void setFirstResult(Integer firstResult) {
        m_firstResult = firstResult;
    }

    /** Getter for property maxResults.
     * @return Value of property maxResults.
     */
    public Integer getMaxResults() {
        return m_maxResults;
    }

    /** Setter for property maxResults.
     * @param maxResults The maximum number of results to retrieve.
     */
    public void setMaxResults(Integer maxResults) {
        m_maxResults = maxResults;
    }

    /** Returns diagnostic information.
     * @return diagnostic information.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer("<Criteria>");
        if (m_table != null)
            buf.append("<table>").append(m_table).append("</table>");
        buf.append("<locking>").append(m_locking).append("</locking>");
        if (m_criteriaEntries != null && m_criteriaEntries.size() > 0) {
            buf.append("<criteriaEntries>");
            for (Object o : m_criteriaEntries)
                buf.append(o);
            buf.append("</criteriaEntries>");
        }
        if (m_aggregates != null && m_aggregates.size() > 0) {
            buf.append("<aggregates>");
            for (Object o : m_aggregates)
                buf.append(o);
            buf.append("</aggregates>");
        }
        if (m_innerCriteriaEntries != null && m_innerCriteriaEntries.size() > 0) {
            buf.append("<innerCriteriaEntries>");
            for (Object o : m_innerCriteriaEntries)
                buf.append(o);
            buf.append("</innerCriteriaEntries>");
        }
        if (m_orderByElements != null && m_orderByElements.size() > 0) {
            buf.append("<orderByElements>");
            for (Object o : m_orderByElements)
                buf.append(o);
            buf.append("</orderByElements>");
        }
        if (m_functionEntries != null && m_functionEntries.size() > 0) {
            buf.append("<functionEntries>");
            for (Object o : m_functionEntries)
                buf.append(o);
            buf.append("</functionEntries>");
        }
        if (m_groupByElements != null && m_groupByElements.size() > 0) {
            buf.append("<groupByElements>");
            for (Object o : m_groupByElements)
                buf.append(o);
            buf.append("</groupByElements>");
        }
        if (m_firstResult != null)
            buf.append("<firstResult>").append(m_firstResult).append("</firstResult>");
        if (m_maxResults != null)
            buf.append("<maxResults>").append(m_maxResults).append("</maxResults>");
        buf.append("</Criteria>");
        return buf.toString();
    }

    /** This class is used by the Criteria for each order by element.
     */
    public class OrderBy implements Serializable {
        private String m_orderByElement = null;
        private int m_ordering = ORDER_BY_ASC;

        private OrderBy(String orderByElement, int ordering) {
            m_orderByElement = orderByElement;
            m_ordering = ordering;
        }

        /** Returns the name of the field.
         * @return the name of the field.
         */
        public String getOrderByElement() {
            return m_orderByElement;
        }

        /** Returns the type of ordering.
         * @return either Ascending or Descending.
         */
        public int getOrdering() {
            return m_ordering;
        }

        /** Returns diagnostic information.
         * @return diagnostic information.
         */
        public String toString() {
            StringBuffer buf = new StringBuffer("<OrderBy>");
            if (m_orderByElement != null)
                buf.append("<orderByElement>").append(m_orderByElement).append("</orderByElement>");
            buf.append("<ordering>").append(m_ordering).append("</ordering>");
            buf.append("</OrderBy>");
            return buf.toString();
        }
    }

    /** This class is used by the Criteria for each criteria entry.
     */
    public class CriteriaEntry implements Serializable {
        /** A static indicating the criteria entry is to be AND-ed */
        public final static int LOGICAL_AND            = 0x01;

        /** A static indicating the criteria entry is to be OR-ed */
        public final static int LOGICAL_OR             = 0x02;

        private String   m_name = null;
        private Object   m_value = null;
        private int      m_operator;
        private int      m_logic;
        private boolean  m_dual = false;
        private String   m_tableName = null;

        protected CriteriaEntry(String name, Object value) {
            this(name, RELATIONAL_EQUALS, value, false,null);
        }

        protected CriteriaEntry(String name, int operator, Object value) {
            this(name, operator, value, false,null);
        }

        protected CriteriaEntry(String name, int operator) {
            this(name, operator, null, false,null);
        }

        protected CriteriaEntry(String name, Object value, boolean dual) {
            this(name, RELATIONAL_EQUALS, value, dual,null);
        }

        protected CriteriaEntry(String name, int operator, Object value, boolean dual, String tableName) {
            m_name = name;
            m_operator = operator;
            m_value = value;
            m_dual = dual;
            m_logic = LOGICAL_AND;
            m_tableName = tableName;
        }

        CriteriaEntry() {}

        void setLogic(int logic) {
            m_logic = logic;
        }

        /** Returns an int indicating AND or OR condition.
         * @return an int indicating AND or OR condition.
         */
        public int getLogic() {
            return m_logic;
        }

        /** A true is returned, if this criteria entry will be AND-ed.
         * @return a true is returned, if this criteria entry will be AND-ed.
         */
        public boolean isLogicAND() {
            return m_logic == LOGICAL_AND ? true : false;
        }

        /** Returns the name of the field.
         * @return the name of the field.
         */
        public String getName() {
            return m_name;
        }

        /** Returns the value to be assigned to the field. For a dual criteria entry, the name of the second field is returned.
         * @return the value to be assigned to the field. For a dual criteria entry, the name of the second field is returned.
         */
        public Object getValue() {
            return m_value;
        }

        /** Returns the operator to be used in the assignment.
         * @return the operator to be used in the assignment.
         */
        public int getOperator() {
            return m_operator;
        }

        /** A true is returned for a dual criteria entry.
         * @return true for a dual criteria entry.
         */
        public boolean getDual() {
            return m_dual;
        }

        /** Returns diagnostic information.
         * @return diagnostic information.
         */
        public String toString() {
            StringBuffer buf = new StringBuffer("<CriteriaEntry>");
            if (m_name != null)
                buf.append("<name>").append(m_name).append("</name>");
            if (m_value != null)
                buf.append("<value>").append(m_value).append("</value>");
            buf.append("<operator>").append(m_operator).append("</operator>");
            buf.append("<logic>").append(m_logic).append("</logic>");
            buf.append("<dual>").append(m_dual).append("</dual>");
            buf.append("</CriteriaEntry>");
            return buf.toString();
        }

        public String getTableName() {
            return m_tableName;
        }
    }


    /** The class is used by the Criteria to add an AtomicCriteria object to its collection of criteria entries.
     */
    public class AtomicCriteriaEntry extends CriteriaEntry {
        private AtomicCriteria m_atomic;

        private AtomicCriteriaEntry(AtomicCriteria atomic) {
            m_atomic = atomic;
        }

        /** Returns the AtomicCriteria object encapsulated by this criteria entry.
         * @return the AtomicCriteria object encapsulated by this criteria entry.
         */
        public AtomicCriteria getEntry() {
            return m_atomic;
        }

        /** Returns diagnostic information.
         * @return diagnostic information.
         */
        public String toString() {
            StringBuffer buf = new StringBuffer("<AtomicCriteria>");
            if (m_atomic != null)
                buf.append("<atomic>").append(m_atomic).append("</atomic>");
            buf.append("</AtomicCriteria>");
            return buf.toString();
        }
    }

    /** This class is used by the Criteria for each order by element.
     */
    public class FunctionEntry implements Serializable {
        private int m_function;
        private boolean m_distinct;
        private String m_name;
        private String m_id;

        private FunctionEntry(int function, String name, String id) {
            this(function, false, name, id);
        }

        private FunctionEntry(int function, boolean distinct, String name, String id) {
            m_function = function;
            m_distinct = distinct;
            m_name = name;
            m_id = id;
        }

        /** Returns the type of function.
         * @return the type of function.
         */
        public int getFunction() {
            return m_function;
        }

        /** Returns true if the function is to be applied on distinct non-null values.
         * @return true if the function is to be applied on distinct non-null values.
         */
        public boolean isDistinct() {
            return m_distinct;
        }

        /** Returns the name of the field.
         * @return the name of the field.
         */
        public String getName() {
            return m_name;
        }

        /** Returns the id of the field.
         * @return the id of the field.
         */
        public String getId() {
            return m_id;
        }

        /** Returns diagnostic information.
         * @return diagnostic information.
         */
        public String toString() {
            StringBuffer buf = new StringBuffer("<FunctionEntry>");
            buf.append("<function>").append(m_function).append("</function>");
            buf.append("<distinct>").append(m_distinct).append("</distinct>");
            if (m_name != null)
                buf.append("<name>").append(m_name).append("</name>");
            if (m_id != null)
                buf.append("<id>").append(m_id).append("</id>");
            buf.append("</FunctionEntry>");
            return buf.toString();
        }
    }

    /** This class is used by the Criteria for each group by element.
     */
    public class GroupBy implements Serializable {
        private String m_name = null;
        private String m_id;

        private GroupBy(String name, String id) {
            m_name = name;
            m_id = id;
        }

        /** Returns the name of the field.
         * @return the name of the field.
         */
        public String getName() {
            return m_name;
        }

        /** Returns the id of the field.
         * @return the id of the field.
         */
        public String getId() {
            return m_id;
        }

        /** Returns diagnostic information.
         * @return diagnostic information.
         */
        public String toString() {
            StringBuffer buf = new StringBuffer("<GroupBy>");
            if (m_name != null)
                buf.append("<name>").append(m_name).append("</name>");
            if (m_id != null)
                buf.append("<id>").append(m_id).append("</id>");
            buf.append("</GroupBy>");
            return buf.toString();
        }
    }

}
