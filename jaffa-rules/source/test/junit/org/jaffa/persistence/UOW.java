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

package org.jaffa.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.modules.setup.domain.ValidFieldValue;
import test.rules.Strings1;

/**
 * This is a mock UOW, used to mimic the actual UOW functionality without any database I/O.
 * It is to be used for unit-tests only.
 * NOTE: Do not use this class for production-level code.
 */
public class UOW {
    
    /** Creates new UOW. A connection is established with the underlying persistence store.
     * @throws UOWException if any error occurs during the process.
     */
    public UOW() throws UOWException {
    }
    
    /** Generates an appropriate instance for the input persistentClass.
     * If the persistentClass is a 'Class', then it should implement the 'IPersistent' interface. The persistence engine will simply instantiate the class.
     * If the persistentClass is an 'Interface', then the persistence engine will generate a dynamic proxy, to implement the IPersistent and the 'persistentClass' interfaces.
     * @param persistentClass The actual persistentClass which can represent a 'Class' or an 'Interface'
     * @return an instance implementing the IPersistent interface.
     */
    public IPersistent newPersistentInstance(Class persistentClass) {
        try {
            return (IPersistent) persistentClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * This is a helper method to determine the actual class which was used to create an IPersistent instance.
     * It is quite possible that the input object is a dynamic proxy.
     * @return The class which was used for instantiating the instance.
     * @param persistentObject An IPersistence instance.
     */
    public Class getActualPersistentClass(Object persistentObject) {
        return persistentObject.getClass();
    }
    
    /**
     * Adds an object to the UOW for addition to the persistent store.
     * The persistence engine may choose to add the object(s) only on a <code>commit</code> or on a <code>flush</code>.
     * @param object The object to persist. It should implement the IPersistent interface.
     * @throws AddFailedException if any error occurs during the process.
     */
    public void add(Object object) throws AddFailedException {
    }
    
    /**
     * Adds an object to the UOW for updation to the persistent store.
     * The persistence engine may choose to update the object(s) only on a <code>commit</code> or on a <code>flush</code>.
     * @param object The object to update. It should implement the IPersistent interface.
     * @throws UpdateFailedException if any error occurs during the process.
     */
    public void update(Object object) throws UpdateFailedException {
    }
    
    /**
     * Adds an object to the UOW for deletion from the persistent store.
     * The persistence engine may choose to delete the object(s) only on a <code>commit</code> or on a <code>flush</code>.
     * @param object The object to delete from persistent storage. It should implement the IPersistent interface.
     * @throws DeleteFailedException if any error occurs during the process.
     */
    public void delete(Object object) throws DeleteFailedException {
    }
    
    /**
     * Queries the underlying persistent store based on the search profile passed in the {@link Criteria} object.
     * @param criteria search profile for the query.
     * @return a Collection of persistent objects.
     * @throws QueryFailedException if any error occurs during the process.
     * @throws PostLoadFailedException if any error occurs during the invocation of the PostLoad trigger on the persistent object.
     */
    public Collection query(Criteria criteria) throws QueryFailedException, PostLoadFailedException {
        Collection col = new ArrayList();
        if (criteria.getTable().equals("org.jaffa.modules.setup.domain.ValidFieldValue"))
            queryValidFieldValue(criteria, col);
        else if (criteria.getTable().equals("test.rules.Strings1"))
            queryStrings1(criteria, col);
        return col;
    }
    
    
    /**
     * Flushes the add/update/delete queues of the persistence engine.
     * This will have no effect if the persistence engine does not queue the add, update or the delete operations.
     * @throws UOWException if any error occurs.
     */
    public void flush() throws UOWException {
    }
    
    
    /**
     * Objects that have been added, objects that have been deleted,
     * and objects that have been updated, will all be persisted via
     * an invocation of this method.
     * Note: After a successful commit, this object will free up its connection to the database,
     * and will no longer be available.
     * @throws AddFailedException if any error occurs during the addition of objects to the persistent store.
     * @throws UpdateFailedException if any error occurs while updating the objects of the persistent store.
     * @throws DeleteFailedException if any error occurs while deleting the objects of the persistent store.
     * @throws CommitFailedException if any error occurs during the commit.
     */
    public void commit() throws AddFailedException, UpdateFailedException
            , DeleteFailedException, CommitFailedException {
    }
    
    
    /**
     * Rollbacks all the additions, deletions, updations.
     * Note: This object will free up its connection to the database, and will no longer be available.
     * @throws RollbackFailedException if any error occurs during the process.
     */
    public void rollback() throws RollbackFailedException {
    }
    
    /**
     * This will acquire a lock on the database row corrsponding to the input persistent object.
     * @param object The persistent object to be locked. It should implement the IPersistent interface.
     * @throws AlreadyLockedObjectException if the database row has been locked by another process.
     */
    public void acquireLock(Object object) throws AlreadyLockedObjectException {
    }
    
    /** Returns true if the UOW is active. The UOW becomes inactive after a commit or a rollback.
     * @return true if the UOW is active.
     */
    public boolean isActive() {
        return true;
    }
    
    
    /** This retrieves mock data for queries on the 'ValidFieldValue' table.
     */
    private void queryValidFieldValue(Criteria criteria, Collection col) throws QueryFailedException {
        try {
            Collection criteriaEntries = criteria.getCriteriaEntries();
            String tableName = null, fieldName = null, value = null;
            String[][] validValues = new String[][] {
                {"TABLEA", "FIELD1", "VALUEA11"},
                {"TABLEA", "FIELD1", "VALUEA12"},
                {"TABLEA", "FIELD2", "1"},
                {"TABLEA", "FIELD2", "2"},
                {"TABLEB", "FIELD1", "VALUEB11"},
                {"TABLEB", "FIELD1", "VALUEB12"}
            };
            if (criteriaEntries != null) {
                for (Iterator i = criteriaEntries.iterator(); i.hasNext(); ) {
                    Criteria.CriteriaEntry criteriaEntry = (Criteria.CriteriaEntry) i.next();
                    if (criteriaEntry.getName().equals("TableName"))
                        tableName = (String) criteriaEntry.getValue();
                    else if (criteriaEntry.getName().equals("FieldName"))
                        fieldName = (String) criteriaEntry.getValue();
                    else if (criteriaEntry.getName().equals("LegalValue"))
                        value = (String) criteriaEntry.getValue();
                }
                for (int i = 0; i < validValues.length; i++) {
                    if ((tableName == null || tableName.equals(validValues[i][0]))
                    && (fieldName == null || fieldName.equals(validValues[i][1]))
                    && (value == null || value.equals(validValues[i][2]))) {
                        ValidFieldValue obj = new ValidFieldValue();
                        obj.setUOW(this);
                        
                        // Now set the fields on the object using reflection.
                        // Since the setters throw exceptions as the default domain-object code invokes the old rules engine.
                        Field f = ValidFieldValue.class.getDeclaredField("m_tableName");
                        f.setAccessible(true);
                        f.set(obj, tableName);
                        f = ValidFieldValue.class.getDeclaredField("m_fieldName");
                        f.setAccessible(true);
                        f.set(obj, fieldName);
                        f = ValidFieldValue.class.getDeclaredField("m_legalValue");
                        f.setAccessible(true);
                        f.set(obj, value);
                        col.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            throw new QueryFailedException(null, e);
        }
    }
    
    /** This retrieves mock data for queries on the 'Strings1' table.
     */
    private void queryStrings1(Criteria criteria, Collection col) throws QueryFailedException {
        try {
            Collection criteriaEntries = criteria.getCriteriaEntries();
            String field1 = null, field2 = null, field3 = null, field4 = null;
            String field1i = null, field2i = null, field3i = null, field4i = null;
            String[][] validValues = new String[][] {
                {"Field11", "field21", "FIELD31", "Field41"},
                {"Field12", "field22", "FIELD32", "Field42"},
                {"Field13", "field23", "FIELD33", "Field43"}
            };
            if (criteriaEntries != null) {
                for (Iterator i = criteriaEntries.iterator(); i.hasNext(); ) {
                    Criteria.CriteriaEntry criteriaEntry = (Criteria.CriteriaEntry) i.next();
                    if (criteriaEntry instanceof Criteria.AtomicCriteriaEntry) {
                        AtomicCriteria ac = ((Criteria.AtomicCriteriaEntry) criteriaEntry).getEntry();
                        if (ac.getCriteriaEntries() != null) {
                            for (Iterator iac = ac.getCriteriaEntries().iterator(); iac.hasNext(); ) {
                                criteriaEntry = (Criteria.CriteriaEntry) iac.next();
                                if (criteriaEntry.getName().equals("Field1"))
                                    field1i = (String) criteriaEntry.getValue();
                                else if (criteriaEntry.getName().equals("Field2"))
                                    field2i = (String) criteriaEntry.getValue();
                                else if (criteriaEntry.getName().equals("Field3"))
                                    field3i = (String) criteriaEntry.getValue();
                                else if (criteriaEntry.getName().equals("Field4"))
                                    field4i = (String) criteriaEntry.getValue();
                            }
                        }
                    } else {
                        if (criteriaEntry.getName().equals("Field1"))
                            field1 = (String) criteriaEntry.getValue();
                        else if (criteriaEntry.getName().equals("Field2"))
                            field2 = (String) criteriaEntry.getValue();
                        else if (criteriaEntry.getName().equals("Field3"))
                            field3 = (String) criteriaEntry.getValue();
                        else if (criteriaEntry.getName().equals("Field4"))
                            field4 = (String) criteriaEntry.getValue();
                    }
                }
                for (int i = 0; i < validValues.length; i++) {
                    if ((field1 == null || field1.equals(validValues[i][0]))
                    && (field2 == null || field2.equals(validValues[i][1]))
                    && (field3 == null || field3.equals(validValues[i][2]))
                    && (field4 == null || field4.equals(validValues[i][3]))
                    && (field1i == null || !field1i.equals(validValues[i][0]))
                    && (field2i == null || !field2i.equals(validValues[i][1]))
                    && (field3i == null || !field3i.equals(validValues[i][2]))
                    && (field4i == null || !field4i.equals(validValues[i][3]))) {
                        Strings1 obj = new Strings1();
                        // Now set the fields on the object using reflection.
                        // Since the setters throw exceptions as the default domain-object code invokes the old rules engine.
                        Field f = Strings1.class.getDeclaredField("field1");
                        f.setAccessible(true);
                        f.set(obj, field1);
                        f = Strings1.class.getDeclaredField("field2");
                        f.setAccessible(true);
                        f.set(obj, field2);
                        f = Strings1.class.getDeclaredField("field3");
                        f.setAccessible(true);
                        f.set(obj, field3);
                        f = Strings1.class.getDeclaredField("field4");
                        f.setAccessible(true);
                        f.set(obj, field4);
                        col.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            throw new QueryFailedException(null, e);
        }
    }
    
}
