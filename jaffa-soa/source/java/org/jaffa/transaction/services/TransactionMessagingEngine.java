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

package org.jaffa.transaction.services;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.services.JaffaMessagingFrameworkException;
import org.jaffa.modules.messaging.services.JmsSender;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.engines.IMessagingEngine;
import org.jaffa.persistence.engines.ISender;
import org.jaffa.persistence.engines.MessagingEngineFactory;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.domain.Transaction.Direction;
import org.jaffa.transaction.domain.Transaction.Status;
import org.jaffa.transaction.domain.TransactionField;
import org.jaffa.transaction.domain.TransactionFieldMeta;
import org.jaffa.util.ExceptionHelper;

import java.util.Collection;
import java.util.LinkedHashMap;
import org.jaffa.util.MessageHelper;

/**
 * Creates new {@link Transaction} when given payload objects and adds them to an internal queue by type.
 * There is a queue for {@link Transaction} with the "Event" type, and a queue for all other {@link Transaction}.
 * This is done because the "Event" transactions can be processed asynchronously and do not need to follow
 * the same lifecycle as all other {@link Transaction}.
 */
public class TransactionMessagingEngine implements IMessagingEngine {

    private static final Logger log = Logger.getLogger(TransactionMessagingEngine.class);
    private static ITransactionMessagingEngineFactory factory;
    private UOW m_uow;
    private LinkedHashMap<String, Transaction> m_transactions = new LinkedHashMap<String, Transaction>();
    private ISender sender;

    /**
     * Sets the unit of work for this Transaction Messaging Engine
     *
     * @param uow the Unit of Work to set
     */
    public TransactionMessagingEngine(UOW uow) {
        m_uow = uow;
        if (log.isDebugEnabled()) {
            log.debug("New->TransactionMessagingEngine [" + this + "] for UOW [" + m_uow + "]");
        }
    }

    /**
     * Creates an instance of this class.
     * It obtains a connection with the JmsProvider and starts a Session.
     *
     * @return an instance of this class.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static IMessagingEngine getInstance(UOW uow) throws FrameworkException, ApplicationExceptions {
        return factory != null ? factory.newInstance(uow) : new TransactionMessagingEngine(uow);
    }

    /**
     * Sets the factory to create new instances of {@link IMessagingEngine}
     *
     * @param factory the factory that creates instances of {@link IMessagingEngine}
     */
    public static void setFactory(ITransactionMessagingEngineFactory factory) {
        TransactionMessagingEngine.factory = factory;
    }

    /**
     * Adds a {@link Transaction} to the internal transaction queue or event queue.
     * It is possible that the input payload is already a transaction.
     *
     * @param payload      Any serializable object, this could already be a transaction
     * @param dependsOnIds IDs of {@link Transaction} that the new {@link Transaction} depends on.
     * @param externalMessage the external message of the transaction payload
     * @return the ID of the new {@link Transaction}
     * @throws FrameworkException
     * @throws ApplicationExceptions
     */
    @Deprecated
    @Override
    public String add(Object payload, String[] dependsOnIds, byte[] externalMessage) throws FrameworkException, ApplicationExceptions {
        log.warn("Transaction submission failed; this method has been deprecated, the Transaction Engine has not yet been initialized.");
        log.warn("Message - " + payload.toString() + " - must be submitted after the system finishes its startup process.");
        return null;
    }

    /**
     * Creates a JMS Message, as defined in the configuration file for the payload.
     * An implementation may choose to send the message only when the JMS transaction is committed.
     *
     * @param payload      Any serializable object.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    @Deprecated
    @Override
    public String addOutbound(Object payload) throws FrameworkException, ApplicationExceptions {
        log.warn("Transaction submission failed; this method has been deprecated, the Transaction Engine has not yet been initialized.");
        log.warn("Message - " + payload.toString() + " - must be submitted after the system finishes its startup process.");
        return null;
    }

    /**
     * Creates a JMS Message, as defined in the configuration file for the payload.
     * An implementation may choose to send the message only when the JMS transaction is committed.
     *
     * @param payload      Any serializable object.
     * @param externalMessage the external message of the transaction payload
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    @Deprecated
    @Override
    public Transaction addTransaction(Object payload, byte[] externalMessage) throws FrameworkException, ApplicationExceptions {
        log.warn("Transaction submission failed; this method has been deprecated, the Transaction Engine has not yet been initialized.");
        log.warn("Message - " + payload.toString() + " - must be submitted after the system finishes its startup process.");
        return null;
    }

    /**
     * On commit of the Transaction Messaging Engine, any internally queued {@link Transaction} needs a corresponding
     * JMS message submitted.  Any queued events will be processed asynchronously and do not need to be sent
     * to the JMS queue.
     * Prior versions did this in the postAdd() of the Transaction domain object.
     * <p/>
     * This location is now one of a few places that should be using the JmsClientHelper.send() method.
     * The other place(s) are:
     * - In the drl files for sending messages to topic queues (resources/rules/jaffa/soa/SOAEventService.drl)
     */
    @Override
    public void commit() throws FrameworkException, ApplicationExceptions {
        log.debug("Commit->TransactionMessagingEngine [" + this + "] for UOW [" + m_uow + "]");
        processTransactions();
    }

    /**
     * Gets the sender
     *
     * @return the sender
     */
    @Override
    public ISender getSender() {
        if (sender == null) {
            sender = new JmsSender();
        }
        return sender;
    }

    /**
     * Sets the sender
     *
     * @param sender the sender
     */
    @Override
    public void setSender(ISender sender) {
        this.sender = sender;
    }

    /**
     * Empties the internal {@link Transaction} queue
     *
     * @throws FrameworkException
     * @throws ApplicationExceptions
     */
    @Override
    public void rollback() throws FrameworkException, ApplicationExceptions {
        m_transactions.clear();
    }

    /**
     * {@inheritDoc}
     */
    public void prepareDeletesForCommit(Collection deletes) {
        // No implementation needed here.
    }

    /**
     * Gets the internal unit of work
     *
     * @return the internal unit of work
     */
    protected UOW getUow() {
        return m_uow;
    }

    /**
     * Gets the internal transaction queue
     *
     * @return the internal transaction queue
     */
    protected LinkedHashMap<String, Transaction> getTransactions() {
        return m_transactions;
    }

    /**
     * Any internally queued {@link Transaction} needs a corresponding JMS message submitted.
     * Prior versions did this in the postAdd() of the Transaction domain object.
     * <p/>
     * This location is now one of a few places that should be using the JmsClientHelper.send() method.
     * The other place(s) are:
     * - In the drl files for sending messages to topic queues (resources/rules/jaffa/soa/SOAEventService.drl)
     */
    private void processTransactions() throws FrameworkException, ApplicationExceptions {
        log.debug("Need to Write JMS for buffered Transactions. BufferSize=" + m_transactions.size());

        // send each transaction to the JmsClientHelper
        for (Transaction transaction : m_transactions.values()) {

            // if the transaction is not defined, skip it
            if (transaction == null) {
                continue;
            }

            // if the direction is not "IN", skip this transaction
            if ((transaction.getDirection() != null) && !Direction.IN.toString().equals(transaction.getDirection())) {
                log.debug("Transaction: " + transaction.getId() + " is not an IN transaction, it will be skipped.");
                continue;
            }

            // if the status is not "O", skip this transaction
            if ((transaction.getStatus() != null) && !Status.O.toString().equals(transaction.getStatus())) {
                log.debug("Transaction: " + transaction.getId() + " is not OPEN, it will be skipped.");
                continue;
            }

            // try to submit the transaction
            try {

                // see if a scheduled ID is defined for this transaction
                String scheduledId = null;
                TransactionField[] transactionFields = transaction.getTransactionFieldArray();
                if (transactionFields != null) {
                    for (TransactionField field : transactionFields) {
                        if (TransactionFieldMeta.JAFFA_TRANSACTION_TASK_ID.equals(field.getFieldName())) {
                            scheduledId = field.getValue();
                            break;
                        }
                    }
                }

                // send the transaction to the JMS queue to be consumed
                getSender().send(new TransactionMessage(transaction), transaction.getCreatedBy(), scheduledId, null);
            } catch (Exception e) {

                // If there is a failure to put the transaction in the JMS queue, set the transaction to error
                Boolean postImmediate = Parser.parseBoolean((String) ContextManagerFactory.instance().getProperty
                        (Transaction.RULE_POST_IMMEDIATE));
                if (postImmediate == null || !postImmediate.booleanValue()) {
                    TransactionEngine.getInstance().updateTransactionStatusToError(transaction.getId(), e);
                }

                // Handle case where an application exception caused the issue
                ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                if (appExps != null) {
					log.error(MessageHelper.findMessage("exception.org.jaffa.modules.messaging.services.JaffaMessagingFrameworkException.jmsTransactionError", new Object[] {transaction}), appExps);					
                    TransactionEngine.getInstance().updateTransactionStatusToError(transaction.getId(), appExps);
                    throw appExps;
                }

                // If the originating exception is not an application exception log it
				log.error(MessageHelper.findMessage("exception.org.jaffa.modules.messaging.services.JaffaMessagingFrameworkException.jmsTransactionError", new Object[] {transaction}), e);					

                // Create a framework exception and rethrow
                FrameworkException frameworkException = new JaffaMessagingFrameworkException(JaffaMessagingFrameworkException.JMS_TRANSACTION_ERROR, new Object[] {transaction}, e);
                if (postImmediate == null || !postImmediate.booleanValue()) {
                    TransactionEngine.getInstance().updateTransactionStatusToError(transaction.getId(),
                                                                                   frameworkException);
                }

                // Throw the framework exception and exit
                throw frameworkException;
            }
        }
    }
}
