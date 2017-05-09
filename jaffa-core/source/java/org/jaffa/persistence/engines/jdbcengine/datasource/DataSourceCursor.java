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

package org.jaffa.persistence.engines.jdbcengine.datasource;

import java.sql.Statement;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.engines.jdbcengine.configservice.ClassMetaData;
import org.jaffa.persistence.engines.jdbcengine.util.MoldingService;
import org.jaffa.persistence.exceptions.PostLoadFailedException;
import org.jaffa.persistence.engines.jdbcengine.datasource.exceptions.DataSourceCursorRuntimeException;
import org.jaffa.persistence.engines.jdbcengine.paging.IPagingPlugin;
import org.jaffa.persistence.engines.jdbcengine.querygenerator.JdbcBridge;

/** This class gives a Collection view of a ResultSet. Each row of the ResultSet is molded into an appropriate Persistent object and added to the Collection.
 * The rows are fetched in groups. The size of each group is determined by the 'hitlistSize' parameter of the init.xml for a particular database definition.
 * On creation, the 1st group of rows are fetched. Use the Iterator (a custom implementation), to fetch all the rows. The Collection interface methods will only on the fetched data.
 * The size() method will return a negative value, unless all the data has been fetched.
 */
public class DataSourceCursor implements Collection {
    
    private static final Logger log = Logger.getLogger(DataSourceCursor.class);
    
    private DataSource m_dataSource = null;
    private Statement m_statement = null;
    private ResultSet m_resultSet = null;
    private ClassMetaData m_classMetaData = null;
    private Criteria m_criteria = null;
    private IPagingPlugin m_pagingPlugin = null;
    private Collection m_collection = null;
    private boolean m_allRead = false;
    
    /** Creates new DataSourceCursor. It loads the initial set of rows.
     * @param dataSource the DataSource object which will be creating this object.
     * @param statement the Statement object used to obtain the ResultSet.
     * @param resultSet the ResultSet from which the data is to be loaded.
     * @param classMetaData the ClassMetaData definition to be used for molding a row into a Persistent object.
     * @param criteria The Criteria used for the query. This will provide the values to set the various flags on the Persistent object.
     * @param pagingPlugin The IPagingPlugin implementation that may be used to return a page of Results.
     * @throws SQLException if any database error occurs.
     * @throws PostLoadFailedException if any error is thrown in the PostLoad trigger of the persistent object.
     * @throws DataSourceCursorRuntimeException if any error occurs while molding the row into the Persistent object.
     * @throws IOException if any error occurs in reading the data from the database.
     */
    DataSourceCursor(DataSource dataSource, Statement statement, ResultSet resultSet, ClassMetaData classMetaData, Criteria criteria, IPagingPlugin pagingPlugin)
    throws SQLException, PostLoadFailedException, DataSourceCursorRuntimeException, IOException {
        m_dataSource = dataSource;
        m_statement = statement;
        m_resultSet = resultSet;
        m_classMetaData = classMetaData;
        m_criteria = criteria;
        m_collection = new ArrayList();
        m_pagingPlugin = pagingPlugin;
        
        try {
            // set the fetch size on the ResultSet
            m_resultSet.setFetchSize(dataSource.getHitlistSize().intValue());
        } catch (Throwable e) {
            // NOTE: The setFetchSize feature may not be implemented by all the drivers. eg.Postgresql.
            // so just ignore the exception
        }
        
        if (log.isDebugEnabled())
            log.debug("Fetching the initial set of data using the hitlist size " + dataSource.getHitlistSize().intValue());
        fetchData();
        if (log.isInfoEnabled())
            log.info("Initial size of the retrieved record set is " + m_collection.size());
    }
    
    /** Returns a custom iterator. Use the iterator for fetching all the rows.
     * @return an iterator over the elements in this collection.
     */
    public Iterator iterator() {
        return new DataSourceCursorIterator();
    }
    
    /** Returns an array containing all of the elements in this collection.
     * Note: This will only return the data that has been fetched so far.
     * @return an array containing all of the elements in this collection.
     */
    public Object[] toArray() {
        return m_collection.toArray();
    }
    
    /** Adds all of the elements in the specified collection to this collection.
     * Note: This will not add any data to the Persistent store.
     * @param collection elements to be inserted into this collection.
     * @return true if this collection changed as a result of the call.
     */
    public boolean addAll(Collection collection) {
        return m_collection.addAll(collection);
    }
    
    /** Returns true if this collection contains all of the elements in the specified collection.
     * Note: This will only check the data that has been fetched so far.
     * @param collection collection to be checked for containment in this collection.
     * @return true if this collection contains all of the elements in the specified collection.
     */
    public boolean containsAll(Collection collection) {
        return m_collection.containsAll(collection);
    }
    
    /** Removes a single instance of the specified element from this collection, if it is present.
     * Note: This will not remove any data from the Persistent store. This will only check the data that has been fetched so far.
     * @param obj element to be removed from this collection, if present.
     * @return true if this collection changed as a result of the call.
     */
    public boolean remove(Object obj) {
        return m_collection.remove(obj);
    }
    
    /** Ensures that this collection contains the specified element.
     * Note: This will not add any data to the Persistent store.
     * @param obj element whose presence in this collection is to be ensured.
     * @return true if this collection changed as a result of the call.
     */
    public boolean add(Object obj) {
        return m_collection.add(obj);
    }
    
    /** Removes all of the elements from this collection.
     * Note: This will not have any effect on the Persistent store.
     * This will close the underlying ResultSet.
     * @throws DataSourceCursorRuntimeException if any error occurs in closing the underlying ResultSet.
     */
    public void clear() throws DataSourceCursorRuntimeException {
        try {
            m_collection.clear();
            if (!m_allRead) {
                if (log.isDebugEnabled())
                    log.debug("Cleared the internal Collection. Invoking the closeStatement() method of the DataSource.");
                m_dataSource.closeStatement(m_statement);
                m_allRead = true;
            }
        } catch (SQLException e) {
            String str = "Error in closing the ResultSet";
            log.error(str, e);
            throw new DataSourceCursorRuntimeException(str, e);
        }
    }
    
    /** Returns true if this collection contains the specified element.
     * Note: This will only check the data that has been fetched so far.
     * @param obj element whose presence in this collection is to be tested.
     * @return true if this collection contains the specified element.
     */
    public boolean contains(Object obj) {
        return m_collection.contains(obj);
    }
    
    /** Returns an array containing all of the elements in this collection; the runtime type of the returned array is that of the specified array.
     * Note: This will only return the data that has been fetched so far.
     * @param obj the array into which the elements of this collection are to be stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose.
     * @return an array containing the elements of this collection.
     */
    public Object[] toArray(Object[] obj) {
        return m_collection.toArray(obj);
    }
    
    /** Returns true if this collection contains no elements. However, a 'true' does not mean that
     * there is no more data to be fetched; since it is possible that the fetched elements could have been removed by the remove() methods.
     * @return true if this collection contains no elements.
     */
    public boolean isEmpty() {
        return m_collection.isEmpty();
    }
    
    /** Removes all this collection's elements that are also contained in the specified collection.
     * Note: This will not remove any data from the Persistent store. This will only check the data that has been fetched so far.
     * @param collection elements to be removed from this collection.
     * @return true if this collection changed as a result of the call.
     */
    public boolean removeAll(Collection collection) {
        return m_collection.removeAll(collection);
    }
    
    /** Retains only the elements in this collection that are contained in the specified collection.
     * Note: This will not remove any data from the Persistent store. This will only check the data that has been fetched so far.
     * @param collection elements to be retained in this collection.
     * @return true if this collection changed as a result of the call.
     */
    public boolean retainAll(Collection collection) {
        return m_collection.retainAll(collection);
    }
    
    /** Returns the number of elements in this collection.
     * Note: This will return a negative number, if all the data has not been fetched.
     * @return the number of elements in this collection;
     */
    public int size() {
        return (m_allRead ? 1 : -1) * m_collection.size();
    }
    
    /** This will fetch additional data. Each row will be molded into a Persistent object
     * and added to the collection. It will invoke the PostLoad trigger of each Persistent object.
     * If all the data has been fetched, then the ResultSet will be closed.
     */
    private void fetchData() throws SQLException, PostLoadFailedException, DataSourceCursorRuntimeException
            , IOException {
        if (!m_allRead) {
            for (int i = 0; i < m_dataSource.getHitlistSize().intValue(); i++) {
                if (m_pagingPlugin != null ? m_pagingPlugin.next(m_resultSet) : m_resultSet.next()) {
                    try {
                        if (m_criteria.getGroupBys() != null || m_criteria.getFunctionEntries() != null) {
                            Map m = MoldingService.getFunctionQueryMap(m_criteria, m_classMetaData, m_resultSet, m_dataSource.getEngineType());
                            if (log.isDebugEnabled()) {
                                StringBuffer buf = new StringBuffer();
                                for (Iterator itr = m.entrySet().iterator(); itr.hasNext(); ) {
                                    if (buf.length() > 0)
                                        buf.append(',');
                                    Map.Entry me = (Map.Entry) itr.next();
                                    buf.append(me.getKey() + "=" + me.getValue());
                                }
                                log.debug("Results from SQL functions:" + buf.toString());
                            }
                            m_collection.add(m);
                        } else {
                            IPersistent object = MoldingService.getObject(m_classMetaData, m_resultSet, m_dataSource.getEngineType());
                            if (log.isDebugEnabled()) {
                                log.debug("Fetched the Persistent object: " + object.toString());
                                log.debug("Invoking the PostLoad trigger of the Persistent object");
                            }
                            JdbcBridge.updatePersistentFlagsOnQuery(object, m_criteria);
                            object.postLoad();
                            m_collection.add(object);
                            m_dataSource.cacheObject(object);
                        }
                        if (log.isDebugEnabled())
                            log.debug("Current size of the retrieved record set is " + m_collection.size());
                        
                    } catch (ClassNotFoundException e) {
                        String str = "Error while molding a ResultSet into a Persistent object";
                        log.error(str, e);
                        throw new DataSourceCursorRuntimeException(str, e);
                    } catch (InstantiationException e) {
                        String str = "Error while molding a ResultSet into a Persistent object";
                        log.error(str, e);
                        throw new DataSourceCursorRuntimeException(str, e);
                    } catch (IllegalAccessException e) {
                        String str = "Error while molding a ResultSet into a Persistent object";
                        log.error(str, e);
                        throw new DataSourceCursorRuntimeException(str, e);
                    } catch (InvocationTargetException e) {
                        String str = "Error while molding a ResultSet into a Persistent object";
                        log.error(str, e);
                        throw new DataSourceCursorRuntimeException(str, e);
                    }
                } else {
                    if (log.isDebugEnabled())
                        log.debug("Fetched in the complete result set. Invoking the closeStatement() method of the DataSource.");
                    m_dataSource.closeStatement(m_statement);
                    m_allRead = true;
                    break;
                }
            }
        }
    }
    
    /** This is a Custom implementation of the Iterator. It fetches all the data from the ResultSet, in hitlistSize chunks.
     */
    private class DataSourceCursorIterator implements Iterator {
        private Iterator m_iterator = null;
        
        /** Creates a DataSourceCursorIterator.*/
        private DataSourceCursorIterator() {
            m_iterator = m_collection.iterator();
        }
        
        /** Returns true if the iteration has more elements.
         * If no more elements exist, it fetches more data from the database in hitlistSize chunks,
         * and returns a true if additional data has been retrieved.
         * @throws DataSourceCursorRuntimeException if any error occurs while fetching additional data.
         * @return Returns true if the iteration has more elements.
         */
        public boolean hasNext() throws DataSourceCursorRuntimeException {
            if (m_allRead) {
                return m_iterator.hasNext();
            } else if (m_iterator.hasNext()) {
                return true;
            } else {
                try {
                    if (log.isDebugEnabled())
                        log.debug("Fetching the next set of data");
                    int oldSize = m_collection.size();
                    fetchData();
                    if (m_collection.size() > oldSize) {
                        // Create a new iterator & move back to the original position.
                        // This is probably inefficient. Could be replaced by a manual implementation like storing index etc. etc.
                        m_iterator = m_collection.iterator();
                        for (int i = 0; i < oldSize; i++)
                            m_iterator.next();
                        return true;
                    } else {
                        return false;
                    }
                } catch (SQLException e) {
                    String str = "Error in fetching data from the database";
                    log.error(str, e);
                    throw new DataSourceCursorRuntimeException(str, e);
                } catch (PostLoadFailedException e) {
                    String str = "Error in execution of the PostLoad trigger";
                    log.error(str, e);
                    throw new DataSourceCursorRuntimeException(str, e);
                } catch (IOException e)  {
                    String str = "Error in fetching data from the database";
                    log.error(str, e);
                    throw new DataSourceCursorRuntimeException(str, e);
                }
            }
        }
        
        /** Returns the next element in the iteration. This will fetch additional data, if available.
         * @throws DataSourceCursorRuntimeException if any error occurs while fetching additional data.
         * @return the next element in the iteration.
         */
        public Object next() throws DataSourceCursorRuntimeException {
            if (hasNext()) {
                return m_iterator.next();
            } else {
                String str = "No more elements to iterate through";
                log.error(str);
                throw new java.util.NoSuchElementException(str);
            }
        }
        
        /** Removes from the underlying collection the last element returned by the iterator.
         * Note: This will not remove any data from the Persistent store.
         */
        public void remove() {
            m_iterator.remove();
        }
    }
}
