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
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.transaction.daos.TransactionMessageDAO;
import org.jaffa.transaction.daos.TransactionMessageDAOFactory;
import org.jaffa.transaction.daos.TransactionMessageService;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.domain.TransactionFieldMeta;
import org.jaffa.transaction.domain.TransactionMeta;
import org.jaffa.transaction.services.configdomain.Filter;
import org.jaffa.transaction.services.configdomain.Param;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.util.ExceptionHelper;

import java.util.Collection;

/**
 * A helper class to check for locks in existing Transactions.
 */
public class LockingService {

    private static final Logger log = Logger.getLogger(LockingService.class);

    /**
     * Browses all Transactions looking for the locks, as specified in the transaction-configuration for the input dataBean.
     * Creates a new UOW to perform the lock check.
     * Throws an ApplicationException, if any matching Transaction is found.
     *
     * @param dataBean Any serializable object.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void checkLock(Object dataBean) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            uow = new UOW();
            checkLock(uow, dataBean);
        } finally {
            if (uow != null) {
                try {
                    uow.rollback();
                } catch (Exception e) {
                    //UOW unable to be rolledback
                    log.error("UOW unable to be rolled back");
                }
            }
        }
    }

    /**
     * Browses all Transactions looking for the locks, as specified in the transaction-configuration for the input dataBean.
     * Throws an ApplicationException, if any matching Transaction is found.
     *
     * @param uow      The UOW.
     * @param dataBean Any serializable object.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void checkLock(UOW uow, Object dataBean) throws FrameworkException, ApplicationExceptions {
        //Load transaction configuration
        TransactionInfo transactionInfo = ConfigurationService.getInstance().getTransactionInfo(dataBean);

        // Perform the check
        checkLock(uow, dataBean, transactionInfo);
    }

    /**
     * Browses all Transactions looking for the locks, as specified in the transaction-configuration for the input dataBean.
     * Throws an ApplicationException, if any matching Transaction is found.
     *
     * @param uow             The UOW.
     * @param dataBean        Any serializable object.
     * @param transactionInfo the corresponding TransactionInfo object, as specified in the configuration file.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void checkLock(UOW uow, Object dataBean, TransactionInfo transactionInfo) throws FrameworkException, ApplicationExceptions {
        checkOrDeleteLockingTransactions(uow, dataBean, transactionInfo, false, null);
    }


    /**
     * Browses all Transactions looking for the locks, as specified in the transaction-configuration for the input dataBean.
     * Throws an ApplicationException, if any matching Transaction is found.
     *
     * @param uow             The UOW.
     * @param dataBean        Any serializable object.
     * @param transactionInfo the corresponding TransactionInfo object, as specified in the configuration file.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void checkLock(UOW uow, Object dataBean, TransactionInfo transactionInfo, String transactionId) throws FrameworkException, ApplicationExceptions {
        checkOrDeleteLockingTransactions(uow, dataBean, transactionInfo, false, transactionId);
    }

    /**
     * Browses all Transactions looking for the locks, as specified in the transaction-configuration for the input dataBean.
     * Deletes the matching Transactions. Creates a UOW.
     *
     * @param dataBean Any serializable object.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void deleteLockingTransactions(Object dataBean) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            uow = new UOW();
            deleteLockingTransactions(uow, dataBean);
        } finally {
            if (uow != null) {
                try {
                    uow.rollback();
                } catch (Exception e) {
                    //UOW unable to be rolledback
                    log.error("UOW unable to be rolled back");
                }
            }
        }


    }

    /**
     * Browses all Transactions looking for the locks, as specified in the transaction-configuration for the input dataBean.
     * Deletes the matching Transactions.
     *
     * @param uow      The UOW.
     * @param dataBean Any serializable object.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void deleteLockingTransactions(UOW uow, Object dataBean) throws FrameworkException, ApplicationExceptions {
        //Load transaction configuration
        TransactionInfo transactionInfo = ConfigurationService.getInstance().getTransactionInfo(dataBean);

        // Delete the matching Transactions
        deleteLockingTransactions(uow, dataBean, transactionInfo);
    }

    /**
     * Browses all Transactions looking for the locks, as specified in the transaction-configuration for the input dataBean.
     * Deletes the matching Transactions.
     *
     * @param uow             The UOW.
     * @param dataBean        Any serializable object.
     * @param transactionInfo the corresponding TransactionInfo object, as specified in the configuration file.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static void deleteLockingTransactions(UOW uow, Object dataBean, TransactionInfo transactionInfo) throws FrameworkException, ApplicationExceptions {
        checkOrDeleteLockingTransactions(uow, dataBean, transactionInfo, true, null);
    }

    /**
     * Browses all Transactions looking for the locks, as specified in the transaction-configuration for the input dataBean.
     * Calls any specified lock-check filter class if one has been defined
     * Throws an ApplicationException, if any matching Transaction is found, and if the input argument 'deleteLockingTransaction' is false.
     * Deletes all matching Transactions, if the input argument 'deleteLockingTransaction' is true.
     *
     * @param uow                      The UOW.
     * @param dataBean                 Any serializable object.
     * @param transactionInfo          the corresponding TransactionInfo object, as specified in the configuration file.
     * @param deleteLockingTransaction determines if the matching Transactions are to be deleted.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    private static void checkOrDeleteLockingTransactions(UOW uow,
                                                         Object dataBean, TransactionInfo transactionInfo,
                                                         boolean deleteLockingTransaction, String transactionId) throws FrameworkException,
                                                                                                                        ApplicationExceptions {

        // Check if lock checks exists for the message
        if (transactionInfo.getLockCheck() != null) {

            Filter filterConfig = transactionInfo.getLockCheck().getFilter();
            // Load the appropriate class
            if (filterConfig != null) {
                try {
                    Class<ILockCheckFilter> filterClass = (Class<ILockCheckFilter>) Class.forName(filterConfig.getClassName());
                    TransactionMessageService transactionService = TransactionMessageDAOFactory.getTransactionMessageService();
                    Collection<Transaction> transactions = filterClass.newInstance().getFilteredResults(dataBean, transactionService);
                    deleteLockCheck(uow, transactions, deleteLockingTransaction, transactionId);
                } catch (Exception e) {
                    throw ExceptionHelper.throwAFR(e);
                }
                // load the load check parameters directly from the message config
            } else if (transactionInfo.getLockCheck().getParam() != null) {
                TransactionMessageDAO transactionDAO = TransactionMessageDAOFactory.getTransactionMessageDAO();
                // Check if any Transaction exists having a TransactionField that matches the Param
                // Run this logic for each Param
                for (Param param : transactionInfo.getLockCheck().getParam()) {
                    String value = TransactionEngine.obtainParamValue(param, dataBean);
                    Collection<Transaction> transactions = transactionDAO.getTransactionsByField(param.getName(), value);
                    deleteLockCheck(uow, transactions, deleteLockingTransaction, transactionId);
                }
            }
        }
    }

    /**
     * @param uow                      The UOW.
     * @param transactions             the transactions the check
     * @param deleteLockingTransaction determines if the matching Transactions are to be deleted.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    private static void deleteLockCheck(UOW uow, Collection<Transaction> transactions, boolean deleteLockingTransaction, String transactionId) throws FrameworkException, ApplicationExceptions {

        // Now perform the checking based on the flag
        if (deleteLockingTransaction) {
            TransactionMessageDAO transactionDAO = TransactionMessageDAOFactory.getTransactionMessageDAO();
            for (Transaction transaction : transactions) {
                if (Transaction.Status.I.toString().equals(
                        transaction.getStatus())) {
                    // Interrupt an In-Process Transaction
                    try {
                        transaction.setStatus(Transaction.Status.INT.toString());
                        transactionDAO.save(uow, transaction);
                    } catch (ApplicationException e) {
                        if (log.isDebugEnabled()) {
                            log.debug("Exception thrown while Interrupting an In-Process Transaction", e);
                        }
                        throw new ApplicationExceptions(e);
                    }
                } else if (!Transaction.Status.INT.toString().equals(transaction.getStatus())) {
                    // Ignore an Interrupted Transaction
                    // Delete the Transaction
                    transactionDAO.delete(uow, transaction);
                }
            }
        } else {
            for (Transaction transaction : transactions) {
                if (!Transaction.Status.S.toString().equals(transaction.getStatus())) {

                    // Should throw an exception. This also prevents a message from locking itself out
                    if ((transactionId == null) || !transaction.getId().equals(transactionId)) {
                        if (log.isDebugEnabled()) {
                            log.debug("Found Transaction record(s) that match the lock criteria");
                        }
                        throw new ApplicationExceptions(new ApplicationException("error.Jaffa.Transaction.Transaction.lockError"));
                    }
                }
            }
        }
    }
}
