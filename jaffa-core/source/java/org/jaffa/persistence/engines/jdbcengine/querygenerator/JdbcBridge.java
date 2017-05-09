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

import org.apache.log4j.Logger;
import java.util.*;
import java.lang.reflect.*;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.io.IOException;
import java.sql.SQLException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Statement;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.engines.jdbcengine.datasource.DataSource;
import org.jaffa.persistence.engines.jdbcengine.configservice.ConfigurationService;
import org.jaffa.persistence.engines.jdbcengine.configservice.ClassMetaData;
import org.jaffa.persistence.engines.jdbcengine.datasource.exceptions.DataSourceCursorRuntimeException;
import org.jaffa.persistence.engines.jdbcengine.IStoredProcedure;
import org.jaffa.persistence.engines.jdbcengine.paging.DefaultPagingPlugin;
import org.jaffa.persistence.engines.jdbcengine.paging.IPagingPlugin;
import org.jaffa.persistence.engines.jdbcengine.util.Introspection;
import org.jaffa.persistence.engines.jdbcengine.util.MoldingService;
import org.jaffa.persistence.engines.jdbcengine.proxy.PersistentInstanceFactory;
import org.jaffa.persistence.engines.jdbcengine.variants.Variant;

import org.jaffa.persistence.exceptions.PostLoadFailedException;

/** Use the methods of this class to execute various operations on the database.
 */
public class JdbcBridge {
    private static final Logger log = Logger.getLogger(JdbcBridge.class);
    private static final int QUERY_TIMEOUT_FOR_LOCKING = 5;
    
    /** No need to instantiate this class.*/
    private JdbcBridge() {
    }
    
    /** Adds the Persistent object to the database.
     * @param object The object to be added.
     * @param dataSource The DataSource to which the object will be added.
     * @throws SQLException if any database error occurs.
     * @throws IllegalAccessException if the Persistent class is not accessible for introspection.
     * @throws InvocationTargetException if the accessor method for the Persistent class throws an exception.
     * @throws IOException if any error occurs while extracting the value for an attribute.
     */
    public static void executeAdd(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException, IOException {
        if (usePreparedStatement(dataSource))
            executeAddWithPreparedStatement(object, dataSource);
        else
            executeAddWithStatement(object, dataSource);
        updatePersistentFlagsOnAdd(object);
    }
    
    private static void executeAddWithPreparedStatement(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException {
        ClassMetaData classMetaData = ConfigurationService.getInstance().getMetaData(PersistentInstanceFactory.getActualPersistentClass(object).getName());
        String sql = PreparedStatementHelper.getInsertPreparedStatementString(classMetaData, dataSource.getEngineType());
        PreparedStatement pstmt = dataSource.getPreparedStatement(sql);
        
        int i = 0;
        for (Iterator itr = classMetaData.getNonAutoKeyFieldNames().iterator(); itr.hasNext();) {
            ++i;
            String fieldName = (String) itr.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, fieldName);
            DataTranslator.setAppObject(pstmt, i, value, classMetaData.getSqlType(fieldName), dataSource.getEngineType());
        }
        for (Iterator itr = classMetaData.getAttributes().iterator(); itr.hasNext();) {
            ++i;
            String fieldName = (String) itr.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, fieldName);
            DataTranslator.setAppObject(pstmt, i, value, classMetaData.getSqlType(fieldName), dataSource.getEngineType());
        }
        
        dataSource.executeUpdate(pstmt);
    }
    
    private static void executeAddWithStatement(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException, IOException {
        String sql = StatementHelper.getInsertStatementString(object, dataSource.getEngineType());
        dataSource.executeUpdate(sql);
    }
    
    
    
    /** Updates the Persistent object in the database.
     * @param object The object to be updated.
     * @param dataSource The DataSource in which the object will be updated.
     * @throws SQLException if any database error occurs.
     * @throws IllegalAccessException if the Persistent class is not accessible for introspection.
     * @throws InvocationTargetException if the accessor method for the Persistent class throws an exception.
     * @throws IOException if any error occurs while extracting the value for an attribute.
     */
    public static void executeUpdate(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException, IOException {
        if (usePreparedStatement(dataSource))
            executeUpdateWithPreparedStatement(object, dataSource);
        else
            executeUpdateWithStatement(object, dataSource);
        updatePersistentFlagsOnUpdate(object);
    }
    
    private static void executeUpdateWithPreparedStatement(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException {
        ClassMetaData classMetaData = ConfigurationService.getInstance().getMetaData(PersistentInstanceFactory.getActualPersistentClass(object).getName());
        String sql = PreparedStatementHelper.getUpdatePreparedStatementString(classMetaData, dataSource.getEngineType());
        PreparedStatement pstmt = dataSource.getPreparedStatement(sql);
        
        int i = 0;
        for (Iterator itr = classMetaData.getAttributes().iterator(); itr.hasNext();) {
            ++i;
            String fieldName = (String) itr.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, fieldName);
            DataTranslator.setAppObject(pstmt, i, value, classMetaData.getSqlType(fieldName), dataSource.getEngineType());
        }
        for (Iterator itr = classMetaData.getAllKeyFieldNames().iterator(); itr.hasNext();) {
            ++i;
            String fieldName = (String) itr.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, fieldName);
            DataTranslator.setAppObject(pstmt, i, value, classMetaData.getSqlType(fieldName), dataSource.getEngineType());
        }
        
        dataSource.executeUpdate(pstmt);
    }
    
    private static void executeUpdateWithStatement(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException, IOException {
        String sql = StatementHelper.getUpdateStatementString(object, dataSource.getEngineType());
        dataSource.executeUpdate(sql);
    }
    
    
    
    /** Deletes the Persistent object from the database.
     * @param object The object to be deleted.
     * @param dataSource The DataSource from which the object will be deleted.
     * @throws SQLException if any database error occurs.
     * @throws IllegalAccessException if the Persistent class is not accessible for introspection.
     * @throws InvocationTargetException if the accessor method for the Persistent class throws an exception.
     * @throws IOException if any error occurs while extracting the value for an attribute.
     */
    public static void executeDelete(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException, IOException {
        if (usePreparedStatement(dataSource))
            executeDeleteWithPreparedStatement(object, dataSource);
        else
            executeDeleteWithStatement(object, dataSource);
        updatePersistentFlagsOnDelete(object);
    }
    
    private static void executeDeleteWithPreparedStatement(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException {
        ClassMetaData classMetaData = ConfigurationService.getInstance().getMetaData(PersistentInstanceFactory.getActualPersistentClass(object).getName());
        String sql = PreparedStatementHelper.getDeletePreparedStatementString(classMetaData, dataSource.getEngineType());
        PreparedStatement pstmt = dataSource.getPreparedStatement(sql);
        
        int i = 0;
        for (Iterator itr = classMetaData.getAllKeyFieldNames().iterator(); itr.hasNext();) {
            ++i;
            String fieldName = (String) itr.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, fieldName);
            DataTranslator.setAppObject(pstmt, i, value, classMetaData.getSqlType(fieldName), dataSource.getEngineType());
        }
        
        dataSource.executeUpdate(pstmt);
    }
    
    private static void executeDeleteWithStatement(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException, IOException {
        String sql = StatementHelper.getDeleteStatementString(object, dataSource.getEngineType());
        dataSource.executeUpdate(sql);
    }
    
    
    
    /** Executes the query based on the Criteria object.
     * @param criteria The input Criteria.
     * @param dataSource The DataSource against which the query is to be executed.
     * @throws IOException if any error occurs while extracting the String from the criteria.
     * @throws SQLException if any database error occurs.
     * @throws PostLoadFailedException if any error is thrown in the PostLoad trigger of the persistent object.
     * @throws DataSourceCursorRuntimeException if any error occurs while molding the row into the Persistent object.
     * @return a Collection of Persistent objects as a result of the query.
     */
    public static Collection executeQuery(Criteria criteria, DataSource dataSource)
    throws IOException, SQLException, PostLoadFailedException, DataSourceCursorRuntimeException {
        ClassMetaData classMetaData = criteria.getTable() != null ? ConfigurationService.getInstance().getMetaData(criteria.getTable()) : null;
        
        // The following optimization will use a PreparedStatement, if the criteria contains the primary-key only
        // The optimization will also be used, if the criteria contains a count(*) for performing an existence-check based on the primary-key
        if (usePreparedStatement(dataSource) && hasPKCriteriaOnly(criteria, classMetaData)) {
            if (criteria.getFunctionEntries() == null || criteria.getFunctionEntries().size() == 0) {
                // Check the cache before executing the query
                IPersistent object = dataSource.lookupObjectCache(criteria);
                if (object != null) {
                    if (log.isDebugEnabled())
                        log.debug("Found the cached object: " + object);
                    Collection output = new LinkedList();
                    output.add(object);
                    return output;
                }
                if (log.isDebugEnabled())
                    log.debug("Optimized to use the PreparedStatement for querying by primary-key");
                return executeFindByPKWithPreparedStatement(criteria, dataSource, classMetaData);
            } else if (criteria.getFunctionEntries().size() == 1) {
                Criteria.FunctionEntry fe = (Criteria.FunctionEntry) criteria.getFunctionEntries().iterator().next();
                if (fe.getName() == null && fe.getFunction() == Criteria.FUNCTION_COUNT) {
                    // Check the cache before executing the query
                    IPersistent object = dataSource.lookupObjectCache(criteria);
                    if (object != null) {
                        if (log.isDebugEnabled())
                            log.debug("Found the cached object for existence-check by primary-key: " + object);
                        Collection output = new LinkedList();
                        Map map = new HashMap();
                        map.put(fe.getId(), 1);
                        output.add(map);
                        return output;
                    }
                    if (log.isDebugEnabled())
                        log.debug("Optimized to use the PreparedStatement for existence-check by primary-key");
                    return executeExistsWithPreparedStatement(criteria, dataSource, classMetaData);
                }
            }
        }
        
        // Utilize the pagingPlugin if the Criteria contains values for the firstResult and/or maxResults properties
        IPagingPlugin pagingPlugin = createPagingPlugin(criteria, dataSource.getEngineType());

        if (usePreparedStatement(dataSource)) {
            // Perform a query using a PreparedStatement
            PreparedStatement pstmt = QueryStatementHelper.getPreparedStatement(criteria, dataSource, pagingPlugin);
            return dataSource.executeQuery(pstmt, classMetaData, criteria, (criteria.getLocking() == Criteria.LOCKING_PARANOID ? QUERY_TIMEOUT_FOR_LOCKING : 0), pagingPlugin);
        } else {
            // Perform a query using a regular Statement
            String sql = QueryStatementHelper.getSQL(criteria, dataSource, pagingPlugin);
            return dataSource.executeQuery(sql, classMetaData, criteria, (criteria.getLocking() == Criteria.LOCKING_PARANOID ? QUERY_TIMEOUT_FOR_LOCKING : 0), pagingPlugin);
        }
    }
    
    
    
    /** Executes the Stored Procedure.
     * @param sp The Stored Procedure to execute.
     * @param criteria The input Criteria.
     * @param dataSource The DataSource against which the Stored Procedure is to be executed.
     * @throws SQLException if any database error occurs.
     * @throws PostLoadFailedException if any error is thrown in the PostLoad trigger of the persistent object.
     * @throws IllegalAccessException if the class is not accessible.
     * @throws InvocationTargetException if the accessor/mutator method for the Stored Procedure throws an exception.
     * @throws IOException if any error occurs in reading the data from the database.
     * @throws InstantiationException if any exception occurs in instantiating an object.
     */
    public static void executeStoredProcedure(IStoredProcedure sp, Criteria criteria, DataSource dataSource)
    throws SQLException, PostLoadFailedException, IllegalAccessException, InvocationTargetException, IOException, InstantiationException {
        // get a CallableStatement
        CallableStatement stmt = dataSource.getCallableStatement(sp.prepareCall());
        
        String[] parameters = sp.getParameters();
        String[] paramSqlTypes = sp.getParamSqlTypes();
        int[] paramDirections = sp.getParamDirections();
        Class clazz = PersistentInstanceFactory.getActualPersistentClass(sp);
        
        
        for(int i = 0; i < parameters.length; i++) {
            // set the input parameters
            if(paramDirections[i] == IStoredProcedure.IN || paramDirections[i] == IStoredProcedure.BOTH) {
                Object value = Introspection.getAccessorMethod(clazz, parameters[i], null).invoke(sp, new Object[0]);
                DataTranslator.setAppObject(stmt, i+1, value, paramSqlTypes[i], dataSource.getEngineType());
            }
            
            // register the out parameters
            if(paramDirections[i] == IStoredProcedure.OUT || paramDirections[i] == IStoredProcedure.BOTH) {
                int sqlType = DataTranslator.getSqlType(paramSqlTypes[i], dataSource.getEngineType());
                stmt.registerOutParameter(i+1, sqlType);
            }
        }
        
        // execute the stored procedure
        if (log.isInfoEnabled())
            log.info("Executing the Stored Procedure " + clazz.getName() + " with initial state:\n" + ((Object) sp).toString());
        stmt.execute();
        
        // set the output parameters
        for(int i = 0; i < parameters.length; i++) {
            if(paramDirections[i] == IStoredProcedure.OUT || paramDirections[i] == IStoredProcedure.BOTH) {
                if (IStoredProcedure.CURSOR.equals(paramSqlTypes[i])) {
                    Method mutatorMethod = Introspection.getMutatorMethod(clazz, parameters[i], null);
                    if (mutatorMethod.getParameterTypes() != null && mutatorMethod.getParameterTypes().length == 1
                            && mutatorMethod.getParameterTypes()[0].isArray()) {
                        ClassMetaData classMetaData = ConfigurationService.getInstance().getMetaData(mutatorMethod.getParameterTypes()[0].getComponentType().getName());
                        Collection objects = dataSource.loadResultSet(stmt, (ResultSet) stmt.getObject(i+1), classMetaData, criteria);
                        
                        // Iterate through the collection so that all the objects get loaded
                        for (Iterator itr = objects.iterator(); itr.hasNext(); )
                            itr.next();
                        
                        // Now convert the collection into an array
                        Object arrayObject = null;
                        if (objects.size() > 0) {
                            int index = 0;
                            arrayObject = Array.newInstance(mutatorMethod.getParameterTypes()[0].getComponentType(), objects.size());
                            for (Iterator itr = objects.iterator(); itr.hasNext(); ) {
                                Array.set(arrayObject, index, itr.next());
                                ++index;
                            }
                        }
                        mutatorMethod.invoke(sp, new Object[] {arrayObject});
                    } else {
                        log.warn("Method 'public void set" + parameters[i] + "(Object[] objects)' not found.");
                    }
                } else {
                    Object value = DataTranslator.getAppObject(stmt, i+1, paramSqlTypes[i], dataSource.getEngineType());
                    Introspection.getMutatorMethod(clazz, parameters[i], null).invoke(sp, new Object[] {value});
                }
            }
        }
        
        if (log.isDebugEnabled())
            log.debug("Contents of the Stored Procedure " + clazz.getName() + " after execution:\n" + ((Object) sp).toString());
        dataSource.closeStatement(stmt);
    }
    
    
    
    /** Executes the findByPK query.
     * @param criteria search profile for the query containing the key.
     * @param dataSource The DataSource against which the query is to be executed.
     * @param classMetaData the meta object, for which the PreparedStatement String is to be generated.
     * @throws SQLException if any database error occurs.
     * @throws PostLoadFailedException if any error is thrown in the PostLoad trigger of the persistent object.
     * @throws IOException if any error occurs in reading the data from the database.
     * @return a Collection having at most one Persistent object as a result of the query.
     */
    private static Collection executeFindByPKWithPreparedStatement(Criteria criteria, DataSource dataSource, ClassMetaData classMetaData)
    throws SQLException, PostLoadFailedException, IOException {
        String sql = PreparedStatementHelper.getFindByPKPreparedStatementString(criteria.getLocking(), classMetaData, dataSource.getEngineType());
        PreparedStatement pstmt = dataSource.getPreparedStatement(sql);
        
        int i = 0;
        for (Iterator itr = classMetaData.getAllKeyFieldNames().iterator(); itr.hasNext();) {
            String fieldName = (String) itr.next();
            Object value = MoldingService.getValueFromCriteria(criteria, classMetaData, fieldName);
            DataTranslator.setAppObject(pstmt, ++i, value, classMetaData.getSqlType(fieldName), dataSource.getEngineType());
        }
        
        return dataSource.executeQuery(pstmt, classMetaData, criteria, (criteria.getLocking() == Criteria.LOCKING_PARANOID ? QUERY_TIMEOUT_FOR_LOCKING : 0), null);
    }
    
    /** Executes the exists query.
     * @param criteria search profile for the query containing the key.
     * @param dataSource The DataSource against which the query is to be executed.
     * @param classMetaData the meta object, for which the PreparedStatement String is to be generated.
     * @throws SQLException if any database error occurs.
     * @throws PostLoadFailedException if any error is thrown in the PostLoad trigger of the persistent object.
     * @throws IOException if any error occurs in reading the data from the database.
     * @return a Collection having at most one Map object with the result of the query.
     */
    private static Collection executeExistsWithPreparedStatement(Criteria criteria, DataSource dataSource, ClassMetaData classMetaData)
    throws SQLException, PostLoadFailedException, IOException {
        // Replace the constant used for the count(*) with Criteria.ID_FUNCTION_COUNT
        String originalColumnName = null;
        Iterator feIterator = criteria.getFunctionEntries().iterator();
        Criteria.FunctionEntry fe = (Criteria.FunctionEntry) feIterator.next();
        if (!Criteria.ID_FUNCTION_COUNT.equals(fe.getId())) {
            originalColumnName = fe.getId();
            feIterator.remove();
            criteria.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
        }
        
        String sql = PreparedStatementHelper.getExistsPreparedStatementString(classMetaData, dataSource.getEngineType());
        PreparedStatement pstmt = dataSource.getPreparedStatement(sql);
        
        int i = 0;
        for (Iterator itr = classMetaData.getAllKeyFieldNames().iterator(); itr.hasNext();) {
            String fieldName = (String) itr.next();
            Object value = MoldingService.getValueFromCriteria(criteria, classMetaData, fieldName);
            DataTranslator.setAppObject(pstmt, ++i, value, classMetaData.getSqlType(fieldName), dataSource.getEngineType());
        }
        
        Collection col = dataSource.executeQuery(pstmt, classMetaData, criteria, (criteria.getLocking() == Criteria.LOCKING_PARANOID ? QUERY_TIMEOUT_FOR_LOCKING : 0), null);
        
        // Restore the column name for the count(*) to the original value
        if (originalColumnName != null) {
            for (Iterator itr = col.iterator(); itr.hasNext(); ) {
                Map row = (Map) itr.next();
                row.put(originalColumnName, row.remove(Criteria.ID_FUNCTION_COUNT));
            }
        }
        
        return col;
    }
    
    /** Locks the underlying database row of the Persistent object.
     * @param object The object to be locked.
     * @param dataSource The DataSource in which the object will be locked.
     * @throws SQLException if any database error occurs.
     * @throws IllegalAccessException if the Persistent class is not accessible for introspection.
     * @throws InvocationTargetException if the accessor method for the Persistent class throws an exception.
     * @throws IOException if any error occurs while extracting the value for an attribute.
     */
    public static void executeLock(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException, IOException {
        ClassMetaData classMetaData = ConfigurationService.getInstance().getMetaData(PersistentInstanceFactory.getActualPersistentClass(object).getName());
        if (classMetaData.isLockable()) {
            if (usePreparedStatement(dataSource))
                executeLockWithPreparedStatement(object, dataSource);
            else
                executeLockWithStatement(object, dataSource);
            updatePersistentFlagsOnLock(object);
        } else {
            if (log.isDebugEnabled())
                log.debug("The table in the database " + classMetaData.getTable() + " for the object " + classMetaData.getClassName()
                + " has been defined as not-lockable. Nothing done");
            return;
        }
    }
    
    private static void executeLockWithPreparedStatement(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException {
        ClassMetaData classMetaData = ConfigurationService.getInstance().getMetaData(PersistentInstanceFactory.getActualPersistentClass(object).getName());
        String sql = PreparedStatementHelper.getLockPreparedStatementString(classMetaData, dataSource.getEngineType());
        PreparedStatement pstmt = dataSource.getPreparedStatement(sql);
        
        int i = 0;
        for (Iterator itr = classMetaData.getAllKeyFieldNames().iterator(); itr.hasNext();) {
            ++i;
            String fieldName = (String) itr.next();
            Object value = MoldingService.getInstanceValue(object, classMetaData, fieldName);
            DataTranslator.setAppObject(pstmt, i, value, classMetaData.getSqlType(fieldName), dataSource.getEngineType());
        }
        
        // Added for MS-Sql-Server as 'NO-WAIT" is not implemented like in Oracle
        pstmt.setQueryTimeout(QUERY_TIMEOUT_FOR_LOCKING);
        dataSource.executeUpdate(pstmt);
    }
    
    private static void executeLockWithStatement(IPersistent object, DataSource dataSource)
    throws SQLException, IllegalAccessException, InvocationTargetException, IOException {
        String sql = StatementHelper.getLockStatementString(object, dataSource.getEngineType());
        Statement statement = dataSource.getStatement();
        // Added for MS-Sql-Server as 'NO-WAIT" is not implemented like in Oracle
        statement.setQueryTimeout(QUERY_TIMEOUT_FOR_LOCKING);
        if (log.isInfoEnabled()) {
            log.info("Executing the SQL\n" + sql);
            long currentTimeMillis = System.currentTimeMillis();
            statement.execute(sql);
            log.info("Elapsed:" + (System.currentTimeMillis() - currentTimeMillis));
        } else {
            statement.execute(sql);
        }
        dataSource.closeStatement(statement);
    }
    
    
    
    /** This method sets the appropriate flags on a Persistent object after being added to the database.
     * @param object the Persistent object.
     */
    public static void updatePersistentFlagsOnAdd(IPersistent object) {
        object.setDatabaseOccurence(true);
        object.setLocked(false);
        object.setModified(false);
        object.setQueued(false);
    }
    
    /** This method sets the appropriate flags on a Persistent object after being updated to the database.
     * @param object the Persistent object.
     */
    public static void updatePersistentFlagsOnUpdate(IPersistent object) {
        object.setLocked(false);
        object.setModified(false);
        object.setQueued(false);
    }
    
    /** This method sets the appropriate flags on a Persistent object after being deleted from the database.
     * @param object the Persistent object.
     */
    public static void updatePersistentFlagsOnDelete(IPersistent object) {
        object.setDatabaseOccurence(false);
        object.setLocked(false);
        object.setModified(false);
        object.setQueued(false);
    }
    
    /** This method sets the appropriate flags on a Persistent object after being retrieved from the database.
     * @param object the Persistent object.
     * @param criteria the Criteria used for retrieving the object.
     */
    public static void updatePersistentFlagsOnQuery(IPersistent object, Criteria criteria) {
        object.setUOW(criteria.getUow());
        object.setDatabaseOccurence(true);
        object.setLocking(criteria.getLocking());
        object.setLocked(criteria.getLocking() == Criteria.LOCKING_PARANOID ? true : false);
        object.setModified(false);
    }
    
    /** This method sets the appropriate flags on a Persistent object after the underlying row is locked in the database.
     * @param object the Persistent object.
     */
    public static void updatePersistentFlagsOnLock(IPersistent object) {
        object.setLocked(true);
    }
    
    /** Returns true if the jdbc-engine configuration file has enabled PreparedStatements for the datasource. */
    private static boolean usePreparedStatement(DataSource dataSource) {
        Boolean bool = dataSource.getUsePreparedStatement();
        return bool != null ? bool.booleanValue() : false;
    }
    
    /** Returns true if the input criteria contains EQUALS criteria for the primary-key fields only. */
    private static boolean hasPKCriteriaOnly(Criteria criteria, ClassMetaData classMetaData) {
        if ((criteria.getCriteriaEntries() != null && criteria.getCriteriaEntries().size() > 0)
        && (criteria.getAggregates() == null || criteria.getAggregates().size() == 0)
        && (criteria.getFunctionEntries() == null || criteria.getFunctionEntries().size() <= 1)
        && (criteria.getGroupBys() == null || criteria.getGroupBys().size() == 0)
        && (criteria.getInners() == null || criteria.getInners().size() == 0)
        ) {
            Collection keyFieldNames = classMetaData.getAllKeyFieldNames();
            if (keyFieldNames.size() == criteria.getCriteriaEntries().size()) {
                //Add the fields to a Set to ensure that duplicate entries are not passed for a key-field. Do this only for a composite-key though
                Collection foundKeys = keyFieldNames.size() > 1 ? new HashSet() : null;
                for (Iterator itr = criteria.getCriteriaEntries().iterator(); itr.hasNext(); ) {
                    Criteria.CriteriaEntry ce = (Criteria.CriteriaEntry) itr.next();
                    if (!ce.getDual() && ce.getLogic() == Criteria.CriteriaEntry.LOGICAL_AND && ce.getValue() != null
                            && ce.getOperator() == Criteria.RELATIONAL_EQUALS && ce.getName() != null && keyFieldNames.contains(ce.getName())) {
                        if (foundKeys != null)
                            foundKeys.add(ce.getName());
                    } else {
                        return false;
                    }
                    
                }
                return foundKeys == null || foundKeys.size() == keyFieldNames.size();
            }
        }
        return false;
    }

    /** Returns a PagingPlugin instance if the Criteria contains values for the firstResult and/or maxResults properties. */
    private static IPagingPlugin createPagingPlugin(Criteria criteria, String engineType) {
        IPagingPlugin pagingPlugin = null;
        if ((criteria.getFirstResult() != null && criteria.getFirstResult() > 0) || (criteria.getMaxResults() != null && criteria.getMaxResults() > 0)) {
            try {
                String pagingPluginClass = Variant.getProperty(engineType, Variant.PROP_PAGING_PLUGIN);
                if (pagingPluginClass != null) {
                    if (log.isDebugEnabled())
                        log.debug("Creating an instance of the pagingPlugin: " + pagingPluginClass);
                    pagingPlugin = (IPagingPlugin) Class.forName(pagingPluginClass).newInstance();
                }
            } catch (Exception e) {
                log.warn("Error during creation of the pagingPlugin", e);
            }

            // use the default plugin if unable to create the plugin
            if (pagingPlugin == null) {
                if (log.isDebugEnabled())
                    log.debug("Creating an instance of the DefaultPagingPlugin");
                pagingPlugin = new DefaultPagingPlugin();
            }

            // configure the plugin
            if (criteria.getFirstResult() != null)
                pagingPlugin.setFirstResult(criteria.getFirstResult());
            if (criteria.getMaxResults() != null)
                pagingPlugin.setMaxResults(criteria.getMaxResults());
        }
        return pagingPlugin;
    }
}
