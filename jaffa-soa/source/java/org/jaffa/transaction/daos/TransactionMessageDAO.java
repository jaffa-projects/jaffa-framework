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

package org.jaffa.transaction.daos;

import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.domain.TransactionDependency;
import org.jaffa.transaction.domain.TransactionDependencySweeperView;
import org.jaffa.transaction.domain.TransactionField;
import org.jaffa.transaction.domain.TransactionPayload;
import org.jaffa.transaction.domain.TransactionStatusCount;
import org.jaffa.transaction.domain.TransactionSweeperView;

import java.util.*;

/**
 * Used to access Transaction object data.  This includes the following models: Transaction, TransactionPayload,
 * TransactionField and TransactionDependency.  This allows us to abstract the need for data from the specific details
 * of how that data is persisted.  This interface is used internally by the Transaction processing of Jaffa.
 */
public interface TransactionMessageDAO extends TransactionDAOBase {

    /**
     * Saves the input Transaction
     *
     * @param uow         the Unit of Work this save should occur in
     * @param transaction the Transaction to save
     * @return the new version of the saved Transaction
     * @throws FrameworkException
     */
    Transaction save(UOW uow, Transaction transaction) throws FrameworkException;

    /**
     * Saves the input TransactionPayload
     *
     * @param uow                the Unit of Work this save should occur in
     * @param transactionPayload the TransactionPayload to save
     * @return the new version of the saved TransactionPayload
     * @throws FrameworkException
     */
    TransactionPayload save(UOW uow, TransactionPayload transactionPayload) throws FrameworkException;

    /**
     * Saves the input TransactionField
     *
     * @param uow              the Unit of Work this save should occur in
     * @param transactionField the TransactionField to save
     * @return the new version of the saved TransactionField
     * @throws FrameworkException
     */
    TransactionField save(UOW uow, TransactionField transactionField) throws FrameworkException;

    /**
     * Saves the input TransactionDependency
     *
     * @param uow                   the Unit of Work this save should occur in
     * @param transactionDependency the TransactionDependency to save
     * @return the new version of the saved TransactionDependency
     * @throws FrameworkException
     */
    TransactionDependency save(UOW uow, TransactionDependency transactionDependency) throws FrameworkException;

    /**
     * Deletes the input Transaction
     *
     * @param uow         the Unit of Work this delete should occur in
     * @param transaction the Transaction to delete
     * @throws FrameworkException
     */
    void delete(UOW uow, Transaction transaction) throws FrameworkException;

    /**
     * Deletes the input Transaction Dependency
     *
     * @param uow                   the Unit of Work this delete should occur in
     * @param transactionDependency the TransactionDependency to delete
     * @throws FrameworkException
     */
    void delete(UOW uow, TransactionDependency transactionDependency) throws FrameworkException;

    /**
     * Gets an ordered list of Transaction that match the input type, subType and fields.
     * If more than one value is defined for a field, that field will be checked against all values to see if it
     * matched any of them (they will be OR-ed).
     *
     * @param type    the type of Transactions to return, if type is null all types will be returned
     * @param subType the subType of Transactions to return, if subType is null all types will be returned
     * @param fields  the fields to check the Transaction for, if multiple values are defined for a field, the
     *                field will be checked to see if it matches any of the values (they will be OR-ed)
     * @param orderBy the fields to sort the return list by, each field has a boolean value that determines the
     *                direction.  True for ascending and false for descending.
     * @return an ordered list of Transactions that match the input type, subType and fields
     * @throws FrameworkException
     */
    List<Transaction> getTransactionsByTypeSubTypeFieldsOrderBy(String type, String subType,
                                                                HashMap<String, List<String>> fields,
                                                                LinkedHashMap<String, Boolean> orderBy) throws FrameworkException;

    /**
     * Gets all Transactions that have the specified field with the input value.
     *
     * @param fieldName the field to check for the input value
     * @param value     the value to check against the field
     * @return a collection of Transactions that contain the specified field with the input value
     * @throws FrameworkException
     */
    Collection<Transaction> getTransactionsByField(String fieldName, String value) throws FrameworkException;

    /**
     * Gets the count per status of all Transactions with the input type
     *
     * @param type the type to filter on
     * @return the count of all open Transaction with the input type
     * @throws FrameworkException
     */
    TransactionStatusCount getTransactionCountPerStatusByType(String type) throws FrameworkException;

    /**
     * Returns a Map of TransactionStatusCount(s) for all Transaction in the types array
     *
     * @param types The list of types to get the transaction status counts for
     * @return a Map of TransactionStatusCount(s) for all Transaction in the types array
     * @throws FrameworkException
     */
    Map<String, TransactionStatusCount> getTransactionCountPerStatusForTypes(String[] types) throws FrameworkException;

    /**
     * Gets the timestamp that a Transaction of the specified type was most recently put into the error state
     *
     * @param type the type of Transaction
     * @return the timestamp that a Transaction of the specified type was most recently put into the error state
     * @throws FrameworkException
     */
    DateTime getLastErrorTimeByType(String type) throws FrameworkException;

    /**
     * Gets the TransactionPayload of the Transaction with the input ID.
     *
     * @param transactionId the ID of the Transaction to return the TransactionPayload of
     * @return the TransactionPayload of the Transaction with the input ID
     * @throws FrameworkException
     */
    TransactionPayload getTransactionPayloadByTransactionId(String transactionId) throws FrameworkException;

    /**
     * Gets the TransactionPayload with the input ID.
     *
     * @param transactionPayloadId the ID of the Transaction to return the TransactionPayload of
     * @return the TransactionPayload of the Transaction with the input ID
     * @throws FrameworkException
     */
    TransactionPayload getTransactionPayload(String transactionPayloadId) throws FrameworkException;

    /**
     * Gets the TransactionField with the input Transaction ID and field name.
     *
     * @param transactionId the ID of the Transaction the TransactionField is part of
     * @param fieldName     the field name of the TransactionField to return
     * @return the TransactionField with the input Transaction ID and field name
     * @throws FrameworkException
     */
    TransactionField getTransactionField(String transactionId, String fieldName) throws FrameworkException;

    /**
     * Gets the TransactionField collection of the Transaction with the input ID.
     *
     * @param transactionId the ID of the Transaction to return the TransactionFields of
     * @return the TransactionFields of the Transaction with the input ID
     * @throws FrameworkException
     */
    Collection<TransactionField> getTransactionFields(String transactionId) throws FrameworkException;

    /**
     * Gets a TransactionDependency with the input transaction ID and depends on ID
     *
     * @param transactionId the ID of the Transaction the dependency is for
     * @param dependsOnId   the ID of the Transaction that is being depended on
     * @return the TransactionDependency with the input transaction ID and depends on ID
     * @throws FrameworkException
     */
    TransactionDependency getTransactionDependency(String transactionId, String dependsOnId) throws FrameworkException;

    /**
     * Gets the TransactionDependency collection of the Transaction with the input ID.
     *
     * @param transactionId the ID of the Transaction to return the TransactionDependencies of
     * @return the TransactionDependencies of the Transaction with the input ID
     * @throws FrameworkException
     */
    Collection<TransactionDependency> getTransactionDependencies(String transactionId) throws FrameworkException;

    /**
     * Gets the count of TransactionDependencies of the input Transaction that are in the open state
     *
     * @param transactionId the ID of the Transaction to get the open TransactionDependency count for
     * @return the count of open TransactionDependencies for the input Transaction
     * @throws FrameworkException
     */
    long getOpenTransactionDependencyCount(String transactionId) throws FrameworkException;

    /**
     * Gets all TransactionDependencies in the open state that depend on the Transaction with the input ID.
     *
     * @param transactionId the ID of the Transaction
     * @return all TransactionDependencies in the open state that depend on the Transaction with the input ID
     * @throws FrameworkException
     */
    Collection<TransactionDependency> getOpenDependenciesByDependsOnId(String transactionId) throws FrameworkException;

    /**
     * Gets all TransactionDependencies in any state that depend on the Transaction with the input ID.
     *
     * @param transactionId the ID of the Transaction
     * @return all TransactionDependencies in any state that depend on the Transaction with the input ID
     * @throws FrameworkException
     */
    Collection<TransactionDependency> getDependenciesByDependsOnId(String transactionId) throws FrameworkException;

    /**
     * Gets all Transactions in the open or InProgress state that were last changed before the input time.
     *
     * @param input the time to compare Open and InProgress Transactions lastChangedTime to
     * @return all Transactions in the open or InProgress state that were last changed before the input time
     * @throws FrameworkException
     */
    Collection<Transaction> getOpenInProgressLastChangedBefore(DateTime input) throws FrameworkException;

    /**
     * Takes a Transaction ID and returns the TransactionSweeperView if the Transaction is in the Hold state but has no
     * TransactionDependency in the Open state.  The TransactionSweeperView is a subset of the Transaction model.
     *
     * @param transactionId the ID of the Transaction in question
     * @return a TransactionSweeperView created from the Transaction with the input ID if that Transaction is in
     *         the Hold state but has no TransactionDependency in the Open state.
     * @throws FrameworkException
     */
    TransactionSweeperView getSweeperViewIfOnHoldNoOpenDependency(String transactionId) throws FrameworkException;

    /**
     * Returns all of the TransactionSweeperViews of the Transactions in the Hold state that have no
     * TransactionDependency in the Open state.  The TransactionSweeperView is a subset of the Transaction model.
     *
     * @return all TransactionSweeperViews created from the Transactions in the Hold state that have
     *         no TransactionDependency in the Open state.
     * @throws FrameworkException
     */
    Collection<TransactionSweeperView> getSweeperViewsIfOnHoldNoOpenDependency() throws FrameworkException;

    /**
     * Returns all of the TransactionDependencySweeperViews of the TransactionDependencies in the Open state that have
     * no dependsOn Transaction or have a dependsOn Transaction in the S state.
     * The TransactionDependencySweeperView is a subset of the TransactionDependency model.
     *
     * @return all TransactionSweeperViews created from the Transactions in the Hold state that have
     *         no TransactionDependency in the Open state.
     * @throws FrameworkException
     */
    Collection<TransactionDependencySweeperView> getDependencySweeperViewsIfOpenNoDependsOnTx() throws FrameworkException;
}
