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

import org.jaffa.exceptions.FrameworkException;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.domain.TransactionField;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Used to access Transaction model information from outside of the internal processing
 * of Transactions done by Jaffa.  This allows us to abstract the need for data from the specific details
 * of how that data is persisted.
 */
public interface TransactionMessageService extends TransactionDAOBase {

    /**
     * Takes the input list of Transaction IDs and filters it down to the Transactions that are either in
     * the Open or InProgress state.  The filtered list is returned.
     *
     * @param transactionIds the list of all Transaction IDs to check the status of
     * @return Transaction IDs of the input list that are in the Open or InProgress state
     * @throws FrameworkException
     */
    List<String> filterSetOfTxIdsToOpenOrInProgress(List<String> transactionIds) throws FrameworkException;

    /**
     * Gets the count of all Transactions in the input list of IDs that are in the error state.
     *
     * @param transactionIds the IDs of the Transactions to check for the error state
     * @return the count of all Transactions in the error state
     * @throws FrameworkException
     */
    long getCountTxInError(List<String> transactionIds) throws FrameworkException;

    /**
     * Gets the count of Transactions of a specified type.
     *
     * @param type the type of Transactions to return the count of
     * @return the count of Transactions of the input type
     * @throws FrameworkException
     */
    long getCountByType(String type) throws FrameworkException;

    /**
     * Gets the count of inbound Transactions with the specified field-value, type and subType that
     * are in the Open or InProgress state.
     *
     * @param field   the field to check the value of
     * @param value   the input value of the field we are looking for
     * @param type    the type of the Transaction
     * @param subType the subType of the Transaction
     * @return the count of Transactions with the specified field-value, type and subType that are in the
     *         Open or inProgress state.
     * @throws FrameworkException
     */
    long getCountInboundOpenInProgress(String field, String value, String type, String subType) throws FrameworkException;

    /**
     * Gets the count of all Transactions with the specified field-value that are in the Open or InProgress state.
     *
     * @param field the field to check the value of
     * @param value the input value of the field we are looking for
     * @return the count of all Transactions with the specified field-value that are in the Open or InProgress state
     * @throws FrameworkException
     */
    long getCountOpenInProgress(String field, String value) throws FrameworkException;

    /**
     * Gets the count of all Transactions with the specified field-value and type that are in the Satisfied or
     * Error state.
     *
     * @param field the field to check the value of
     * @param value the input value of the field we are looking for
     * @param type  the type of the Transaction
     * @return count of all Transactions with the specified field-value that are in the Open or InProgress state
     * @throws FrameworkException
     */
    long getCountSatisfiedOrInError(String field, String value, String type) throws FrameworkException;

    /**
     * Gets the count of all Transactions with the specified field-value and type that are not in the Satisfied or
     * Error state.
     *
     * @param field the field to check the value of
     * @param value the input value of the field we are looking for
     * @param type  the type of the Transaction
     * @return count of all Transactions with the specified field-value that are not in the Open or InProgress state
     * @throws FrameworkException
     */
    long getCountNotSatisfiedOrInError(String field, String value, String type) throws FrameworkException;

    /**
     * Gets a list of all open inbound Transactions of the input type, ordered descnding by the createdOn parameter.
     *
     * @param type the type of Transactions to return
     * @return all open inbound Transactions of the input type, ordered by the createdOn parameter
     * @throws FrameworkException
     */
    List<Transaction> getOpenOutboundByTypeOrderByCreatedOnDesc(String type) throws FrameworkException;

    /**
     * Gets all Transactions with the input field-value and returns them ordered descending by the createdOn parameter.
     *
     * @param field the field to check the value of
     * @param value the input value of the field we are looking for
     * @return all Transactions with the input field-value and returns them ordered by the createdOn parameter
     * @throws FrameworkException
     */
    List<Transaction> findByFieldValueOrderByCreatedOnDesc(String field, String value) throws FrameworkException;

    /**
     * Gets the Transaction that has the specified fields with the input values.
     *
     * @param fields the field name/value pairs
     * @return the Transaction that contains the specified fields with the input values
     * @throws FrameworkException
     */
    Transaction getTransactionByFields(HashMap<String, String> fields) throws FrameworkException;

    /**
     * Gets the Transaction that has the specified fields-values and also fields that begin with
     * the specified values.
     *
     * @param fields     the field name/value pairs
     * @param beginsWith the field name/begins-with values
     * @return the Transaction that contains the specified fields with the input values
     * @throws FrameworkException
     */
    Transaction getTransactionByFieldsAndBeginsWithFields(HashMap<String, String> fields, HashMap<String, String> beginsWith) throws FrameworkException;

    /**
     * Gets all Transactions that have the specified fields with the input values.
     * If there are more than one value for a key, this will return fields that have any of the specified values.
     *
     * @param fields the field name/value pairs
     * @return a collection of Transactions that contain the specified fields with the input values
     * @throws FrameworkException
     */
    Collection<Transaction> getTransactionsByFields(HashMap<String, List<Serializable>> fields) throws FrameworkException;

    /**
     * Gets all Transactions that have any of the specified fields with the input values.
     * If there are more than one value for a key, this will return fields that have any of the specified values.
     *
     * @param fields the field name/value pairs that a Transaction must have at least one of to ne returned
     * @return a collection of Transactions that contain the specified fields with the input values
     * @throws FrameworkException
     */
    Collection<Transaction> getTransactionsByFieldsOred(HashMap<String, Serializable> fields) throws FrameworkException;

    /**
     * Gets all inbound Transactions that have the specified type, subType and fields with the input values.
     * If there are more than one value for a key, this will return fields that have any of the specified values.
     *
     * @param type    the type of Transactions to return
     * @param subType the subType of Transactions to return
     * @param fields  the field name/value pairs
     * @return a collection of inbound Transactions of the input type and subType that contain the specified
     *         fields with the input values
     * @throws FrameworkException
     */
    Collection<Transaction> getInboundByTypeSubTypeAndFields(String type, String subType, HashMap<String, String> fields) throws FrameworkException;

    /**
     * Get a TansactionField given its name and value.
     *
     * @param fieldName  the name of the field to return
     * @param fieldValue the value of the field to return
     * @return the TransactionField with the input fieldName and value
     * @throws FrameworkException
     */
    TransactionField getFieldByNameValue(String fieldName, String fieldValue) throws FrameworkException;
}
