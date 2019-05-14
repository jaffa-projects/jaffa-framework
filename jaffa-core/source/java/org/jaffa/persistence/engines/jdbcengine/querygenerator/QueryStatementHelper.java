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
package org.jaffa.persistence.engines.jdbcengine.querygenerator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import java.util.*;
import java.io.IOException;

import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Defaults;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.AtomicCriteria;
import org.jaffa.persistence.engines.jdbcengine.configservice.ConfigurationService;
import org.jaffa.persistence.engines.jdbcengine.configservice.ClassMetaData;
import org.jaffa.persistence.engines.jdbcengine.datasource.DataSource;
import org.jaffa.persistence.engines.jdbcengine.paging.IPagingPlugin;
import org.jaffa.persistence.engines.jdbcengine.variants.Variant;
import org.jaffa.util.StringHelper;

/** Use the helper method to generate SQL statements for querying the database.
 */
public class QueryStatementHelper {

    private static final Logger log = Logger.getLogger(QueryStatementHelper.class);
    // some constants that are used internally
    private static final String SELECT = "SELECT";
    private static final String DISTINCT = "DISTINCT";
    private static final String FROM = "FROM";
    private static final String WHERE = "WHERE";
    private static final String AND = "AND";
    private static final String OR = "OR";
    private static final String IS_NULL = "IS NULL";
    private static final String IS_NOT_NULL = "IS NOT NULL";
    private static final String LIKE = "LIKE";
    private static final String NOT_LIKE = "NOT LIKE";
    private static final String ORDER_BY = "ORDER BY";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String TABLE_NAME_PREFIX = "T";
    private static final String MAX = "MAX";
    private static final String MIN = "MIN";
    private static final String SUM = "SUM";
    private static final String AVG = "AVG";
    private static final String COUNT = "COUNT";
    private static final String COUNT_STAR = "COUNT(*)";
    private static final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";
    private static final String GROUP_BY = "GROUP BY";

    /** This parses a <code>Criteria</code> object for constructing relevant SQL.
     * @param criteria the object containing query data.
     * @param dataSource The DataSource against which the query is to be executed.
     * @param pagingPlugin The IPagingPlugin implementation that may be used to return a page of Results.
     * @throws IOException if any error occurs while extracting the String from the criteria.
     * @throws SQLException if any database error occurs.
     * @return a String representing SQL statement.
     */
    public static PreparedStatement getPreparedStatement(Criteria criteria, DataSource dataSource, IPagingPlugin pagingPlugin)
            throws IOException, SQLException {
        List<PreparedStatementArgument> psArguments = new LinkedList<PreparedStatementArgument>();
        String sql = getSQLInternal(criteria, dataSource.getEngineType(), pagingPlugin, psArguments, false);
        PreparedStatement pstmt = dataSource.getPreparedStatement(sql);
        int i = 0;
        for (PreparedStatementArgument psArgument : psArguments)
            DataTranslator.setAppObject(pstmt, ++i, psArgument.value, psArgument.typeName, dataSource.getEngineType());
        return pstmt;
    }

    /** This parses a <code>Criteria</code> object for constructing relevant SQL.
     * @param criteria the object containing query data.
     * @param dataSource The DataSource against which the query is to be executed.
     * @param pagingPlugin The IPagingPlugin implementation that may be used to return a page of Results.
     * @throws IOException if any error occurs while extracting the String from the criteria.
     * @return a String representing SQL statement.
     */
    public static String getSQL(Criteria criteria, DataSource dataSource, IPagingPlugin pagingPlugin)
            throws IOException {
        return getSQLInternal(criteria, dataSource.getEngineType(), pagingPlugin, null, false);
    }

    /** This parses a <code>Criteria</code> object for constructing relevant SQL.
     *
     * @param criteria the object containing query data.
     * @param engineType The engine type as defined in init.xml
     * @param pagingPlugin The IPagingPlugin implementation that may be used to return a page of Results.
     * @throws IOException if any error occurs while extracting the String from the criteria.
     * @return a String representing SQL statement.
     */
    public static QueryInfo getSQL(Criteria criteria, String engineType, IPagingPlugin pagingPlugin) throws IOException {
        List<PreparedStatementArgument> psArguments = new ArrayList<>();
        String SQL = getSQLInternal(criteria, engineType, pagingPlugin, psArguments, true);
        return new QueryInfo(SQL, psArguments, engineType);
    }

    /** This parses a <code>Criteria</code> object for constructing relevant SQL.
     * @param criteria the object containing query data.
     * @param engineType The engine type as defined in init.xml
     * @param pagingPlugin The IPagingPlugin implementation that may be used to return a page of Results.
     * @param psArguments If a PreparedStatement is to be generated, pass an empty List. This will be filled with instances of PreparedStatementArgument.
     * @throws IOException if any error occurs while extracting the String from the criteria.
     * @return a String representing SQL statement.
     */
    private static String getSQLInternal(Criteria criteria, String engineType, IPagingPlugin pagingPlugin, List<PreparedStatementArgument> psArguments, boolean indexArguments)
            throws IOException {
        ClassMetaData meta = criteria.getTable() != null ? ConfigurationService.getInstance().getMetaData(criteria.getTable()) : null;

        // This buffer will hold the SQL
        StringBuffer buf = new StringBuffer(SELECT);

        // This buffer will hold a wrapper SQL when using functions against a JOIN criteria.
        // In such a scenario, buf will contain the SQL with the JOIN criteria,
        // while the functionWrapperBuf will contain a wrapping SQL of the type:
        // SELECT f1, count(f2), max(f3) from (SELECT distinct f1, f2, f3, f4 from T1, T2 WHERE ....) GROUP BY f1
        StringBuffer functionWrapperBuf = null;

        if (criteria.getGroupBys() != null || criteria.getFunctionEntries() != null) {
            // Initialize the functionWrapperBuf only against a JOIN criteria
            if ((criteria.getAggregates() != null && criteria.getAggregates().size() > 0) || hasAtomicAggregates(criteria))
                functionWrapperBuf = new StringBuffer(SELECT);

            // Add the fields specified in the group-by clause and the function-list
            StringBuffer fieldBuf = new StringBuffer();
            if (criteria.getGroupBys() != null) {
                String groupBySelectFieldList = getGroupBySelectFieldList(criteria, meta, engineType);
                if (groupBySelectFieldList != null && groupBySelectFieldList.length() > 0)
                    fieldBuf.append(groupBySelectFieldList);
            }
            if (criteria.getFunctionEntries() != null) {
                String functionList = getFunctionList(criteria, meta, engineType);
                if (functionList != null && functionList.length() > 0) {
                    if (fieldBuf.length() > 0)
                        fieldBuf.append(',');
                    fieldBuf.append(functionList);
                }
            }
            if (fieldBuf.length() > 0) {
                StringBuffer targetBuf = functionWrapperBuf != null ? functionWrapperBuf : buf;
                targetBuf.append(' ').append(fieldBuf);
            }

            // The 'table' can be null when using scalar functions like CURRENT_TIMESTAMP.
            // Just add the optional FROM clause and return
            if (criteria.getTable() == null) {
                StringBuffer targetBuf = functionWrapperBuf != null ? functionWrapperBuf : buf;
                String deftableName = Variant.getProperty(engineType, Variant.PROP_DEFAULT_FUNCTION_TABLE_NAME);
                if (deftableName != null && deftableName.length() > 0)
                    targetBuf.append(' ').append(FROM).append(' ').append(deftableName);
                return targetBuf.toString();
            }
        }

        if (functionWrapperBuf != null || (criteria.getGroupBys() == null && criteria.getFunctionEntries() == null)) {
            // This will ensure distinct records are returned during JOINs
            if ((criteria.getAggregates() != null && criteria.getAggregates().size() > 0) || hasAtomicAggregates(criteria))
                buf.append(' ').append(DISTINCT);

            // Add the field-list
            buf.append(' ').append(getFieldList(meta));
        }

        // Create 2 buffers for holding the From and Where clauses.
        // And then invoke the recursive 'parse' routine to fill up the 2 buffers
        StringBuffer fromBuf = new StringBuffer();
        StringBuffer whereBuf = new StringBuffer();
        doFromAndWhere(criteria, meta,
                null, null, null,
                new Counter(), fromBuf, whereBuf, engineType, psArguments, indexArguments);

        // Add the From clause
        if (fromBuf.length() > 0)
            buf.append(' ').append(FROM).append(' ').append(fromBuf);

        // Added for supplying Locking 'Hints' used by MS-Sql-Server
        if (criteria.getLocking() == Criteria.LOCKING_PARANOID && meta.isLockable())
            buf.append(' ').append(Variant.getProperty(engineType, Variant.PROP_LOCK_CONSTRUCT_IN_FROM_SELECT_STATEMENT));

        // Add the Where clause
        if (whereBuf.length() > 0)
            buf.append(' ').append(WHERE).append(' ').append(whereBuf);

        // Add the GroupBy clause, but only if functionWrapperBuf has not been initialized
        if (criteria.getGroupBys() != null && functionWrapperBuf == null) {
            String groupByFieldList = getGroupByFieldList(criteria, meta, engineType);
            if (groupByFieldList != null && groupByFieldList.length() > 0)
                buf.append(' ').append(GROUP_BY).append(' ').append(groupByFieldList);
        }

        // Append the ordering information, if any
        Collection orderBys = criteria.getOrderBys();
        if (orderBys != null && orderBys.size() > 0) {
            StringBuffer orderByBuf = new StringBuffer();
            for (Iterator itr = orderBys.iterator(); itr.hasNext();) {
                Criteria.OrderBy orderBy = (Criteria.OrderBy) itr.next();
                String sqlName = formatSqlName(meta.getSqlName(orderBy.getOrderByElement()), engineType);
                if (sqlName != null) {
                    if (orderByBuf.length() > 0)
                        orderByBuf.append(',');
                    orderByBuf.append(meta.getTable()).append('.').append(sqlName);
                    if (orderBy.getOrdering() == Criteria.ORDER_BY_ASC)
                        orderByBuf.append(' ').append(ASC);
                    else
                        orderByBuf.append(' ').append(DESC);
                } else if (criteria.getFunctionEntries() != null) {
                    for (Iterator i = criteria.getFunctionEntries().iterator(); i.hasNext();) {
                        Criteria.FunctionEntry fe = (Criteria.FunctionEntry) i.next();
                        if (orderBy.getOrderByElement().equals(fe.getId())) {
                            if (orderByBuf.length() > 0)
                                orderByBuf.append(',');
                            orderByBuf.append(orderBy.getOrderByElement());
                            if (orderBy.getOrdering() == Criteria.ORDER_BY_ASC)
                                orderByBuf.append(' ').append(ASC);
                            else
                                orderByBuf.append(' ').append(DESC);
                            break;
                        }
                    }
                } else if (log.isDebugEnabled())
                    log.debug("Will ignore the unmapped OrderByElement " + meta.getTable() + '.' + orderBy.getOrderByElement());
            }
            if (orderByBuf.length() > 0)
                buf.append(' ').append(ORDER_BY).append(' ').append(orderByBuf);
        }

        // check the locking strategy
        if (criteria.getLocking() == Criteria.LOCKING_PARANOID && meta.isLockable()) {
            buf.append(' ');
            buf.append(Variant.getProperty(engineType, Variant.PROP_LOCK_CONSTRUCT_IN_SELECT_STATEMENT));
        }

        // build the functionWrapperBuf
        if (functionWrapperBuf != null) {
            functionWrapperBuf.append(' ').append(FROM).append(' ').append('(').append(buf).append(')').append(' ').append(meta.getTable());

            // Add the GroupBy clause
            if (criteria.getGroupBys() != null) {
                String groupByFieldList = getGroupByFieldList(criteria, meta, engineType);
                if (groupByFieldList != null && groupByFieldList.length() > 0)
                    functionWrapperBuf.append(' ').append(GROUP_BY).append(' ').append(groupByFieldList);
            }
        }

        String sql = functionWrapperBuf != null ? functionWrapperBuf.toString() : buf.toString();
        if (pagingPlugin != null)
            sql = pagingPlugin.preQuery(sql);
        return sql;
    }

    /** Recursive method which parses a <code>Criteria</code> object for constructing relevant SQL.
     * It fills the 2 input buffers with relevant SQL
     */
    private static void doFromAndWhere(Criteria criteria, ClassMetaData meta,
            Criteria parentCriteria, ClassMetaData parentMeta, String parentTableName,
            Counter tableCounter, StringBuffer fromBuf, StringBuffer whereBuf, String engineType,
            List<PreparedStatementArgument> psArguments, boolean indexArguments)
            throws IOException {
        String tableName = null;
        if (tableCounter.getCount() == 0) {
            tableName = meta.getTable();
        } else {
            tableName = TABLE_NAME_PREFIX + tableCounter.getCount();
        }

        // Append table name
        if (fromBuf.length() > 0)
            fromBuf.append(',');
        fromBuf.append(meta.getTable());
        if (!tableName.equals(meta.getTable())) {
            fromBuf.append(' ');
            fromBuf.append(tableName);
        }

        if (criteria != null) {
            // Now do the where clause
            doWhere(criteria, meta, tableName, parentMeta, parentTableName, tableCounter, fromBuf, whereBuf, engineType, psArguments, indexArguments);

            // Check for aggregates
            Collection aggregates = criteria.getAggregates();
            aggregate(criteria, meta, tableCounter, fromBuf, whereBuf, engineType, psArguments, indexArguments,
                      tableName, aggregates);
        }
    }

    private static void aggregate(Criteria criteria, ClassMetaData meta, Counter tableCounter, StringBuffer fromBuf,
                                  StringBuffer whereBuf, String engineType, List<PreparedStatementArgument> psArguments,
                                  boolean indexArguments, String tableName, Collection aggregates) throws IOException {
        if (aggregates != null) {
            for (Iterator itr = aggregates.iterator(); itr.hasNext();) {
                tableCounter.increment();
                Criteria aggregate = (Criteria) itr.next();
                doFromAndWhere(aggregate, ConfigurationService.getInstance().getMetaData(aggregate.getTable()),
                               criteria, meta, tableName,
                               tableCounter, fromBuf, whereBuf, engineType, psArguments, indexArguments);
            }
        }
    }

    private static void doWhere(Criteria criteria, ClassMetaData meta, String tableName,
            ClassMetaData parentMeta, String parentTableName, Counter tableCounter, StringBuffer fromBuf, StringBuffer whereBuf, String engineType, List<PreparedStatementArgument> psArguments, boolean indexArguments)
            throws IOException {
        // Here we handle the join fields
        Collection inners = criteria.getInners();
        if (inners != null && parentMeta != null) {
            for (Iterator i = inners.iterator(); i.hasNext();) {
                Criteria.CriteriaEntry innerCriteriaEntry = (Criteria.CriteriaEntry) i.next();
                String tfield = formatSqlName(meta.getSqlName(innerCriteriaEntry.getName()), engineType);
                int operator = innerCriteriaEntry.getOperator();
                String ofield = formatSqlName(parentMeta.getSqlName((String) innerCriteriaEntry.getValue()), engineType);
                if (tfield != null && ofield != null) {
                    // Now we need to set the two fields up with their proper prefixes
                    tfield = tableName + '.' + tfield;
                    ofield = parentTableName + '.' + ofield;

                    // Trim CHAR field when joining between VARCHAR and CHAR fields
                    // We could've used the rpad (or lpad) function on the VARCHAR field.
                    Integer trpad = meta.getRpad(innerCriteriaEntry.getName());
                    Integer orpad = parentMeta.getRpad((String) innerCriteriaEntry.getValue());
                    if (trpad != null && trpad.intValue() > 0 && (orpad == null || orpad.intValue() == 0))
                        tfield = Variant.getProperty(engineType, Variant.PROP_TRIM_FUNCTION_PREFIX) + tfield + Variant.getProperty(engineType, Variant.PROP_TRIM_FUNCTION_SUFFIX);
                    else if ((trpad == null || trpad.intValue() == 0) && orpad != null && orpad.intValue() > 0)
                        ofield = Variant.getProperty(engineType, Variant.PROP_TRIM_FUNCTION_PREFIX) + ofield + Variant.getProperty(engineType, Variant.PROP_TRIM_FUNCTION_SUFFIX);

                    if (whereBuf.length() > 0)
                        whereBuf.append(' ').append(AND).append(' ');
                    whereBuf.append(parseDualOperator(tfield, operator, ofield));
                } else if (log.isDebugEnabled()) {
                    if (tfield == null && ofield == null)
                        log.debug("Will ignore the unmapped join fields " + meta.getTable() + '.' + innerCriteriaEntry.getName() + ", " + parentMeta.getTable() + '.' + innerCriteriaEntry.getValue());
                    else if (tfield == null)
                        log.debug("Will ignore the unmapped join field " + meta.getTable() + '.' + innerCriteriaEntry.getName());
                    else
                        log.debug("Will ignore the unmapped join field " + parentMeta.getTable() + '.' + innerCriteriaEntry.getValue());
                }
            }
        }

        // This handles the normal fields and recursively looks for atomic aggregates
        Collection criteriaEntries = criteria.getCriteriaEntries();
        if (criteriaEntries != null) {
            for (Iterator i = criteriaEntries.iterator(); i.hasNext();) {
                Criteria.CriteriaEntry criteriaEntry = (Criteria.CriteriaEntry) i.next();
                String clause = getClause(criteriaEntry, criteria, meta, tableName, tableCounter, fromBuf, engineType, psArguments, indexArguments);
                if (clause != null && clause.length() > 0) {
                    if (whereBuf.length() > 0) {
                        whereBuf.append(' ');
                        if (criteriaEntry.isLogicAND())
                            whereBuf.append(AND);
                        else
                            whereBuf.append(OR);
                        whereBuf.append(' ');
                    }
                    whereBuf.append('(').append(clause).append(')');
                }
            }
        }
    }

    // This handles any atomic criteria recursively looks for atomic criteria entries within them to populate clause with aggregates within atomic criteria entries
    private static void populateClauseWithAtomicAggregates(Criteria.CriteriaEntry criteriaEntry, Criteria criteria, ClassMetaData meta, String tableName,
                                      StringBuffer clause, Counter tableCounter, StringBuffer fromBuf, String engineType, List<PreparedStatementArgument> psArguments, boolean indexArguments) throws IOException{
        if (criteriaEntry instanceof Criteria.AtomicCriteriaEntry) {
            // Check for aggregates
            Collection atomicAggregates = ((Criteria.AtomicCriteriaEntry) criteriaEntry).getEntry().getAggregates();
            aggregate(criteria, meta, tableCounter, fromBuf, clause, engineType, psArguments, indexArguments, tableName,
                      atomicAggregates);
        }
    }

    private static String getClause(Criteria.CriteriaEntry criteriaEntry, Criteria criteria, ClassMetaData meta,
                                    String tableName, Counter tableCounter, StringBuffer fromBuf, String engineType,
                                    List<PreparedStatementArgument> psArguments, boolean indexArguments)
            throws IOException {
        StringBuffer buf = new StringBuffer();
        if (criteriaEntry instanceof Criteria.AtomicCriteriaEntry) {
            AtomicCriteria atomicCriteria = ((Criteria.AtomicCriteriaEntry) criteriaEntry).getEntry();
            Collection criteriaEntries = atomicCriteria.getCriteriaEntries();
            if (criteriaEntries != null) {
                for (Iterator i = criteriaEntries.iterator(); i.hasNext();) {
                    Criteria.CriteriaEntry ce = (Criteria.CriteriaEntry) i.next();
                    String clause = getClause(ce, criteria, meta, tableName, tableCounter, fromBuf, engineType, psArguments, indexArguments);
                    if (clause != null && clause.length() > 0) {
                        if (buf.length() > 0)
                            buf.append(' ').append(logicLookAhead(ce)).append(' ');
                        buf.append('(').append(clause).append(')');
                    }
                }
            }
            populateClauseWithAtomicAggregates(criteriaEntry, criteria, meta, tableName, buf, tableCounter, fromBuf, engineType, psArguments, indexArguments);
        } else {
            String name = formatSqlName(meta.getSqlName(criteriaEntry.getName()), engineType);
            if(name==null && criteriaEntry.getTableName()!=null) {
            	meta = ConfigurationService.getInstance().getMetaData(criteriaEntry.getTableName());
            	if(meta!=null)
                    name = formatSqlName(meta.getSqlName(criteriaEntry.getName()), engineType);
            }
            if (name != null && meta != null) {
                int operator = criteriaEntry.getOperator();
                if (criteriaEntry.getDual()) {
                    String name2 = formatSqlName(meta.getSqlName((String) criteriaEntry.getValue()), engineType);
                    if (name2 != null)
                        buf.append(parseDualOperator(tableName + '.' + name, operator, tableName + '.' + name2));
                    else if (log.isDebugEnabled())
                        log.debug("Will ignore the unmapped criteria entry " + meta.getTable() + '.' + criteriaEntry.getValue());
                } else {
                    Object value = criteriaEntry.getValue();
                    String typeName = meta.getSqlType(criteriaEntry.getName());
                    Integer rpad = meta.getRpad(criteriaEntry.getName());
                    operator = checkForOperatorOverride(operator, typeName, value);
                    if (criteriaEntry.getTableName() != null) {
                        buf.append(parseOperator(meta.getTable() + '.' + name, operator, value, typeName, engineType, rpad, psArguments, indexArguments));
                    }
                    else {
                        buf.append(parseOperator(tableName + '.' + name, operator, value, typeName, engineType, rpad, psArguments, indexArguments));
                    }
                }
            } else if (log.isDebugEnabled()  && (meta != null)) {
                log.debug("Will ignore the unmapped criteria entry " + meta.getTable() + '.' + criteriaEntry.getName());
            }
        }
        return buf.toString();
    }

    /**
     * Bug JAFFA-346 - If the type is a date type and the value is null, the operator should to set to IS NULL
     * if the current operator is EQUALS.
     *
     * @param operator the current operator for the predicate
     * @param typeName the type of the value/column
     * @param value the value of the predicate
     * @return the original value of the operator if no override is required or the
     *         RELATIONAL_IS_NULL operator if the above condition is true.
     */
    private static int checkForOperatorOverride(int operator, String typeName, Object value) {
        //do not override operator unless it is EQUALS
        if (Criteria.RELATIONAL_EQUALS == operator) {
            //If value is null and type is a date, the override the operator or the query will always return 0 rows.
            if ((value == null) && (Defaults.DATETIME.equals(typeName) || Defaults.DATEONLY.equals(typeName))) {
                return Criteria.RELATIONAL_IS_NULL;
            }
        }

        return operator;
    }

    private static String logicLookAhead(Criteria.CriteriaEntry criteriaEntry) {
        if (criteriaEntry instanceof Criteria.AtomicCriteriaEntry) {
            AtomicCriteria atomicCriteria = ((Criteria.AtomicCriteriaEntry) criteriaEntry).getEntry();
            if (atomicCriteria.getOrLogic())
                return OR;
            else
                return AND;
        } else {
            if (criteriaEntry.getLogic() == Criteria.CriteriaEntry.LOGICAL_AND)
                return AND;
            else
                return OR;
        }
    }

    //checks if any of the nested atomicCriteria has aggregates
    private static boolean hasAtomicAggregates(Criteria criteria){
        boolean hasAggregates = false;
        Collection criteriaEntries = criteria.getCriteriaEntries();
        if(criteriaEntries!=null && criteriaEntries.size() > 0){
            for (Iterator i = criteriaEntries.iterator(); i.hasNext() && !hasAggregates;) {
                Criteria.CriteriaEntry criteriaEntry = (Criteria.CriteriaEntry) i.next();
                if (criteriaEntry instanceof Criteria.AtomicCriteriaEntry) {
                    AtomicCriteria atomicCriteria = ((Criteria.AtomicCriteriaEntry) criteriaEntry).getEntry();
                    if(atomicCriteria.getAggregates()!=null && atomicCriteria.getAggregates().size() > 0){
                        hasAggregates = true;
                    }else{
                        hasAggregates = hasAtomicAggregates(atomicCriteria);
                    }
                }
            }
        }
        return hasAggregates;
    }

    private static String parseOperator(String name, int operator, Object value, String typeName, String engineType,
                                        Integer rpad, List<PreparedStatementArgument> psArguments, boolean indexArguments) throws IOException {
        // set some flags based on the input
        boolean isCharField = rpad != null && rpad > 0;
        boolean prepare = psArguments != null;

        // Queries based on PreparedStatements require 'char' fields to be correctly padded, else they fail.
        // For best results, lets rpad 'char' fields for both Prepared and Regular Statements.
        // rpad should be done only for the relevant operators; certainly not for the LIKE operators.
        if (isCharField) {
            switch (operator) {
                case Criteria.RELATIONAL_EQUALS:
                case Criteria.RELATIONAL_NOT_EQUALS:
                case Criteria.RELATIONAL_GREATER_THAN:
                case Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO:
                case Criteria.RELATIONAL_SMALLER_THAN:
                case Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO:
                    value = StringHelper.rpad((String) value, rpad.intValue());
                    break;
                case Criteria.RELATIONAL_IS_NULL:
                case Criteria.RELATIONAL_IS_NOT_NULL:
                    value = StringHelper.rpad(null, rpad.intValue());
                    break;
            }
        }

        // the following object will be used to set a parameter in the PreparedStatement
        PreparedStatementArgument psArgument = prepare ? new PreparedStatementArgument(value, typeName) : null;

        StringBuffer buf = new StringBuffer();
        buf.append(name);
        boolean nullInt = ((value == null) && (Defaults.INTEGER.equals(typeName) || "BIGINT".equals(typeName)));
        switch (operator) {
            case Criteria.RELATIONAL_EQUALS:
                buf.append('=');
                if (prepare && !nullInt) {
                    buf.append('?');
                    buf.append(indexArguments ? psArguments.size() + 1 : "");
                    psArguments.add(psArgument);
                } else
                    buf.append(DataTranslator.getDml(value, typeName, engineType));
                break;
            case Criteria.RELATIONAL_NOT_EQUALS:
                buf.append('!');
                buf.append('=');
                if (prepare && !nullInt) {
                    buf.append('?');
                    buf.append(indexArguments ? psArguments.size() + 1 : "");
                    psArguments.add(psArgument);
                } else
                    buf.append(DataTranslator.getDml(value, typeName, engineType));
                break;
            case Criteria.RELATIONAL_GREATER_THAN:
                buf.append('>');
                if (prepare && !nullInt) {
                    buf.append('?');
                    buf.append(indexArguments ? psArguments.size() + 1 : "");
                    psArguments.add(psArgument);
                } else
                    buf.append(DataTranslator.getDml(value, typeName, engineType));
                break;
            case Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO:
                buf.append('>');
                buf.append('=');
                if (prepare && !nullInt) {
                    buf.append('?');
                    buf.append(indexArguments ? psArguments.size() + 1 : "");
                    psArguments.add(psArgument);
                } else
                    buf.append(DataTranslator.getDml(value, typeName, engineType));
                break;
            case Criteria.RELATIONAL_SMALLER_THAN:
                buf.append('<');
                if (prepare && !nullInt) {
                    buf.append('?');
                    buf.append(indexArguments ? psArguments.size() + 1 : "");
                    psArguments.add(psArgument);
                } else
                    buf.append(DataTranslator.getDml(value, typeName, engineType));
                break;
            case Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO:
                buf.append('<');
                buf.append('=');
                if (prepare && !nullInt) {
                    buf.append('?');
                    buf.append(indexArguments ? psArguments.size() + 1 : "");
                    psArguments.add(psArgument);
                } else
                    buf.append(DataTranslator.getDml(value, typeName, engineType));
                break;
            case Criteria.RELATIONAL_IS_NULL:
                // When applying the RELATIONAL_IS_NULL operator on CHAR fields, generate: (SOME_FIELD IS NULL OR SOME_FIELD=' ')
                if (isCharField) {
                    buf.insert(0, '(');
                    buf.append(' ');
                    buf.append(IS_NULL);
                    buf.append(' ').append(OR).append(' ').append(name).append("='").append(value).append("')");
                } else {
                    buf.append(' ');
                    buf.append(IS_NULL);
                }
                break;
            case Criteria.RELATIONAL_IS_NOT_NULL:
                // When applying the RELATIONAL_IS_NOT_NULL operator on CHAR fields, generate: (SOME_FIELD IS NOT NULL AND SOME_FIELD!=' ')
                if (isCharField) {
                    buf.insert(0, '(');
                    buf.append(' ');
                    buf.append(IS_NOT_NULL);
                    buf.append(' ').append(AND).append(' ').append(name).append("!='").append(value).append("')");
                } else {
                    buf.append(' ');
                    buf.append(IS_NOT_NULL);
                }
                break;
            case Criteria.RELATIONAL_BEGINS_WITH:
                buf.append(' ');
                buf.append(LIKE);
                buf.append(' ');
                buf.append(DataTranslator.getBeginsWithDml(value, typeName, engineType));
                break;
            case Criteria.RELATIONAL_NOT_BEGINS_WITH:
                buf.append(' ');
                buf.append(NOT_LIKE);
                buf.append(' ');
                buf.append(DataTranslator.getBeginsWithDml(value, typeName, engineType));
                break;
            case Criteria.RELATIONAL_ENDS_WITH:
                // When applying the RELATIONAL_ENDS_WITH operator on CHAR fields, generate: trim(SOME_FIELD) like '%blahblah'
                if (isCharField) {
                    buf.insert(0, Variant.getProperty(engineType, Variant.PROP_TRIM_FUNCTION_PREFIX));
                    buf.append(Variant.getProperty(engineType, Variant.PROP_TRIM_FUNCTION_SUFFIX));
                }
                buf.append(' ');
                buf.append(LIKE);
                buf.append(' ');
                buf.append(DataTranslator.getEndsWithDml(value, typeName, engineType));
                break;
            case Criteria.RELATIONAL_NOT_ENDS_WITH:
                // When applying the RELATIONAL_NOT_ENDS_WITH operator on CHAR fields, generate: trim(SOME_FIELD) not like '%blahblah'
                if (isCharField) {
                    buf.insert(0, Variant.getProperty(engineType, Variant.PROP_TRIM_FUNCTION_PREFIX));
                    buf.append(Variant.getProperty(engineType, Variant.PROP_TRIM_FUNCTION_SUFFIX));
                }
                buf.append(' ');
                buf.append(NOT_LIKE);
                buf.append(' ');
                buf.append(DataTranslator.getEndsWithDml(value, typeName, engineType));
                break;
            case Criteria.RELATIONAL_LIKE:
                buf.append(' ');
                buf.append(LIKE);
                buf.append(' ');
                buf.append(DataTranslator.getLikeDml(value, typeName, engineType));
                break;
            case Criteria.RELATIONAL_NOT_LIKE:
                buf.append(' ');
                buf.append(NOT_LIKE);
                buf.append(' ');
                buf.append(DataTranslator.getLikeDml(value, typeName, engineType));
                break;
            default:
                String str = "Illegal operator passed - " + operator;
                log.error(str);
                throw new IllegalArgumentException(str);
        }
        return buf.toString();
    }

    private static String parseDualOperator(String name, int operator, String name2) {
        StringBuffer buf = new StringBuffer(name);
        buf.append(' ');
        switch (operator) {
            case Criteria.RELATIONAL_EQUALS:
                buf.append('=');
                break;
            case Criteria.RELATIONAL_NOT_EQUALS:
                buf.append('!');
                buf.append('=');
                break;
            case Criteria.RELATIONAL_GREATER_THAN:
                buf.append('>');
                break;
            case Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO:
                buf.append('>');
                buf.append('=');
                break;
            case Criteria.RELATIONAL_SMALLER_THAN:
                buf.append('<');
                break;
            case Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO:
                buf.append('<');
                buf.append('=');
                break;
            default:
                String str = "Illegal operator passed - " + operator;
                log.error(str);
                throw new IllegalArgumentException(str);
        }
        buf.append(' ');
        buf.append(name2);
        return buf.toString();
    }

    private static String getFieldList(ClassMetaData classMetaData) {
        String fieldList = null;

        if (classMetaData != null) {
            fieldList = classMetaData.getTable() + ".*";
        }
        return fieldList;
    }

    private static String getFunctionList(Criteria criteria, ClassMetaData classMetaData, String engineType) {
        StringBuffer buf = new StringBuffer();
        for (Iterator i = criteria.getFunctionEntries().iterator(); i.hasNext();) {
            Criteria.FunctionEntry fe = (Criteria.FunctionEntry) i.next();
            if (fe.getFunction() == Criteria.FUNCTION_COUNT && fe.getName() == null) {
                if (buf.length() > 0)
                    buf.append(',');
                buf.append(COUNT_STAR);
            } else if (fe.getFunction() == Criteria.FUNCTION_CURRENT_DATE_TIME) {
                if (buf.length() > 0)
                    buf.append(',');
                buf.append(CURRENT_TIMESTAMP);
            } else {
                String sqlName = formatSqlName(classMetaData.getSqlName(fe.getName()), engineType);
                if (sqlName != null) {
                    if (buf.length() > 0)
                        buf.append(',');
                    if (fe.getFunction() == Criteria.FUNCTION_AVG)
                        buf.append(AVG);
                    else if (fe.getFunction() == Criteria.FUNCTION_COUNT)
                        buf.append(COUNT);
                    else if (fe.getFunction() == Criteria.FUNCTION_MAX)
                        buf.append(MAX);
                    else if (fe.getFunction() == Criteria.FUNCTION_MIN)
                        buf.append(MIN);
                    else if (fe.getFunction() == Criteria.FUNCTION_SUM)
                        buf.append(SUM);
                    else
                        throw new IllegalArgumentException("Unknown function passed in Criteria: " + fe.getFunction());

                    buf.append('(');
                    if (fe.isDistinct())
                        buf.append(DISTINCT).append(' ');
                    buf.append(classMetaData.getTable() + '.' + sqlName);
                    buf.append(')');
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Will ignore the unmapped function element " + classMetaData.getTable() + '.' + fe.getName());
                    continue;
                }
            }
            buf.append(' ').append('"').append(fe.getId()).append('"');
        }
        return buf.toString();
    }

    private static String getGroupBySelectFieldList(Criteria criteria, ClassMetaData classMetaData, String engineType) {
        StringBuffer buf = new StringBuffer();
        for (Iterator i = criteria.getGroupBys().iterator(); i.hasNext();) {
            Criteria.GroupBy gb = (Criteria.GroupBy) i.next();
            String sqlName = formatSqlName(classMetaData.getSqlName(gb.getName()), engineType);
            if (sqlName != null) {
                if (buf.length() > 0)
                    buf.append(',');
                buf.append(classMetaData.getTable() + '.' + sqlName);
                buf.append(' ').append('"').append(gb.getId()).append('"');
            } else if (log.isDebugEnabled())
                log.debug("Will ignore the unmapped GroupBy element " + classMetaData.getTable() + '.' + gb.getName());
        }
        return buf.toString();
    }

    private static String getGroupByFieldList(Criteria criteria, ClassMetaData classMetaData, String engineType) {
        StringBuffer buf = new StringBuffer();
        for (Iterator i = criteria.getGroupBys().iterator(); i.hasNext();) {
            Criteria.GroupBy gb = (Criteria.GroupBy) i.next();
            String sqlName = formatSqlName(classMetaData.getSqlName(gb.getName()), engineType);
            if (sqlName != null) {
                if (buf.length() > 0)
                    buf.append(',');
                buf.append(classMetaData.getTable() + '.' + sqlName);
            } else if (log.isDebugEnabled())
                log.debug("Will ignore the unmapped GroupBy element " + classMetaData.getTable() + '.' + gb.getName());
        }
        return buf.toString();
    }

    private static String formatSqlName(String sqlName, String engineType) {
        if (sqlName != null && "mssqlserver".equals(engineType)) {
            sqlName = "[" + sqlName + "]";
        }
        return sqlName;
    }

    /** An inner class to name additional tables in a JOIN query. */
    private static class Counter {

        int m_value = 0;

        void increment() {
            ++m_value;
        }

        int getCount() {
            return m_value;
        }
    }

    /**
     * Encapsulates the SQL and bind arguments of a query
     */
    public static class QueryInfo {
        private String SQL;
        private List<Object> arguments = new ArrayList<>();

        /**
         * Constructs a new instance of this class
         *
         * @param SQL           the SQL
         * @param psArguments   list of PS Arguments
         */
        protected QueryInfo(String SQL, List<PreparedStatementArgument> psArguments, String engineType) throws IOException {
            this.SQL = SQL;

            //convert to list of objects
            if (psArguments != null) {
                for (PreparedStatementArgument psArgument : psArguments) {
                    arguments.add(DataTranslator.getAppObject(psArgument.value, psArgument.typeName, engineType));
                }
            }
        }

        /**
         * Gets the SQL
         *
         * @return the SQL
         */
        public String getSQL() {
            return SQL;
        }

        /**
         * Gets the argument list
         *
         * @return the argument list
         */
        public List getArguments() {
            return arguments;
        }

        @Override
        public int hashCode(){
            return SQL.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }

            QueryInfo queryInfo = (QueryInfo) obj;

            if (this.SQL.equals(queryInfo.getSQL())) {
                if (arguments.size() == queryInfo.getArguments().size()) {
                    for (int i = 0; i < arguments.size(); i++) {
                        if ((arguments.get(i) == null && queryInfo.getArguments().get(i) != null) ||
                                (arguments.get(i) != null && queryInfo.getArguments().get(i) == null)) {
                            return false;
                        }
                        if (arguments.get(i) != null && !arguments.get(i).equals(queryInfo.getArguments().get(i))) {
                            return false;
                        }
                    }
                }

                return true;
            }

            return false;
        }
    }

    /** An inner class to stamp values on a PreparedStatement. */
    private static class PreparedStatementArgument {

        Object value;
        String typeName;

        PreparedStatementArgument(Object value, String typeName) {
            this.value = value;
            this.typeName = typeName;
        }
    }
}
