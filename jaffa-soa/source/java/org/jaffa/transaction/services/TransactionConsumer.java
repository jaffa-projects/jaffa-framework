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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;
import org.jaffa.persistence.UOW;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.transaction.daos.TransactionMessageDAOFactory;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.domain.TransactionField;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.util.ExceptionHelper;
import org.jaffa.util.MessageHelper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *
 */
public class TransactionConsumer {

    private static final Logger log = Logger.getLogger(TransactionConsumer.class);
    protected static final String KEEP = "_keep";
    protected static final String RULE_RETRY_LIMIT = "jaffa.transaction.consumer.retry.limit";
    protected static final String RULE_RETRY_SLEEP_TIME = "jaffa.transaction.consumer.retry.sleepTimeInMillisecs";
    protected static final int DEFAULT_RETRY_LIMIT = 5;
    protected static final int DEFAULT_RETRY_SLEEP_TIME = 5000;
    protected static final String RETRY_EXCEPTION_RULE = "jaffa.transaction.retry.exceptions";
    protected static final String DEFAULT_RETRY_EXCEPTIONS = "org.jaffa.persistence.engines.jdbcengine.LockedApplicationException";

    /**
     * This method is invoked by JaffaMessaging for processing a Transaction.
     * It'll invoke the handler associated with the input transaction's payload, as obtained from the transaction configuration file.
     * In case of an error, the status of the transaction will be set to 'E'.
     *
     * @param transactionMessage the TransactionMessage.
     */
    public void process(TransactionMessage transactionMessage) throws Exception {

        UOW uow = null;
        boolean createdLoggingContext = false;
        try {
            uow = new UOW();
            uow = process(uow, transactionMessage);
        } catch (Exception e) {
            log.error("Exception occurred while processing message : " + transactionMessage, e);
        } finally {
            if (uow != null) {
                uow.rollback();
            }
            // Unset the Logging context
            if (createdLoggingContext) {
                LoggingService.unsetLoggingContext();
            }
        }
    }


    /**
     * This method is invoked directly when processing a Transaction synchronously.
     * It'll invoke the handler associated with the input transaction's payload, as obtained from the transaction configuration file.
     * In case of an error, the status of the transaction will be set to 'E'.
     *
     * @param uow                the provided uow.
     * @param transactionId      the transaction ID.
     * @param unsavedTransaction the transaction if available, otherwise null.
     */
    public UOW process(UOW uow, String transactionId, Transaction unsavedTransaction) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Processing Transaction " + transactionId);
        }

        // Update Transaction status to I
        Boolean postImmediate = Parser.parseBoolean((String) ContextManagerFactory.instance().getProperty(Transaction.RULE_POST_IMMEDIATE));
        //If the postImmediate is true then we shall make the update to the transaction within same scope of the UOW as this must be synchronous transaction
        if (postImmediate != null && postImmediate.booleanValue()) {
            if (unsavedTransaction == null) {
                // This transaction must already be in the database.
                TransactionEngine.getInstance().updateTransactionStatusToInProcess(uow, transactionId);
            } else {
                uow.flush();
                unsavedTransaction.setStatus(Transaction.Status.I.name());
            }
        } else {
            TransactionEngine.getInstance().updateTransactionStatusToInProcess(transactionId);
        }
        boolean createdLoggingContext = false;
        try {
            Transaction transaction = unsavedTransaction != null ? unsavedTransaction : Transaction.findByPK(uow, transactionId);
            Object dataBean = transaction.getTransactionPayloadObject() != null ? transaction.getTransactionPayloadObject().moldInternalPayload() : null;
            TransactionInfo transactionInfo = null;
            if (dataBean != null) {
                //Load transaction configuration
                transactionInfo = ConfigurationService.getInstance().getTransactionInfo(dataBean);

                // Sets Log4J's MDC to enable BusinessEventLogging
                LoggingService.setLoggingContext(dataBean, transactionInfo, transaction);
                createdLoggingContext = true;

                if (log.isInfoEnabled()) {
                    log.info(MessageHelper.findMessage("label.Jaffa.Transaction.TransactionConsumer.start", null));
                }

                int retryLimit = readRule(RULE_RETRY_LIMIT, DEFAULT_RETRY_LIMIT);
                int retrySleepTimeInMillis = readRule(RULE_RETRY_SLEEP_TIME, DEFAULT_RETRY_SLEEP_TIME);
                int retryCount = 0;
                while (true) {
                    try {
                        // Invokes the handler as specified by the 'toClass and toMethod' combination in the transaction configuration
                        invokeHandler(uow, transactionInfo, dataBean);
                        break;
                    } catch (Exception e) {

                        if (postImmediate == null || !postImmediate.booleanValue()) {
                            //Retry only if the exception is listed as a retryable exception
                            String[] exceptions = readRule(RETRY_EXCEPTION_RULE, DEFAULT_RETRY_EXCEPTIONS);
                            Exception ex = null;
                            Class<Exception> clazz = null;
                            for (String exceptionName : exceptions) {
                                if (log.isDebugEnabled()) {
                                    log.debug("Exception: " + exceptionName + " defined as a retryable exception.");
                                }
                                clazz = (Class<Exception>) Class.forName(exceptionName);
                                ex = clazz.cast(ExceptionHelper.extractException(e, clazz));
                                if (ex != null) {
                                    break;
                                }
                            }
                            if (ex != null && ++retryCount <= retryLimit) {
                                if (log.isDebugEnabled()) {
                                    log.debug(clazz.getSimpleName() + " encountered. Will sleep for " + retrySleepTimeInMillis + " milliseconds and then retry", e);
                                }
                                uow.rollback();
                                Thread.sleep(retrySleepTimeInMillis);
                                uow = new UOW();
                                if (log.isDebugEnabled()) {
                                    log.debug("Retry#" + retryCount);
                                }
                            } else {
                                throw e;
                            }
                        } else {
                            throw e;
                        }
                    }
                }

                if (log.isInfoEnabled()) {
                    log.info(MessageHelper.findMessage("label.Jaffa.Transaction.TransactionConsumer.success", null));
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("There is no payload for the Transaction. Hence nothing to process.");
                }
            }

            TransactionField[] transactionFields = transaction.getTransactionFieldArray();
            boolean keep = false;
            if (transactionFields != null) {
                for (TransactionField tField : transactionFields) {
                    if (KEEP.equals(tField.getFieldName()) && Boolean.parseBoolean(tField.getValue())) {
                        keep = true;
                        break;
                    }
                }
            }

            if (log.isDebugEnabled()) {
                log.info("Finished with transaction: " + transactionId);
            }

            // Commit the UOW if it isn't a post immediate to catch any exceptions that might occur
            if (postImmediate == null || !postImmediate.booleanValue()) {

                if (keep)
                // Update Transaction status to S
                {
                    TransactionEngine.getInstance().updateTransactionStatusToSatisfied(uow, transactionId);
                } else
                //Delete Transaction Record
                {
                    TransactionEngine.getInstance().deleteTransaction(uow, transactionId);
                }
                try {
                    uow.commit();
                } catch (Exception e) {
                    log.error("Error committing UOW in TransactionConsumer.process", e);
                    throw e;
                }
            } else {
                uow.flush();
                TransactionMessageDAOFactory.getTransactionMessageDAO().delete(uow, transaction);
            }

            if (log.isDebugEnabled()) {
                log.debug("Successfully processed Transaction " + transaction);
            }
        } catch (Exception e) {
            // Update Transaction status to E
            if (log.isInfoEnabled()) {
                log.info(MessageHelper.findMessage("label.Jaffa.Transaction.TransactionConsumer.error", null));
            }

            // Rollback the UOW if there is an error
            if (postImmediate == null || !postImmediate.booleanValue()) {
                try {
                    uow.rollback();
                } catch (Exception exception) {
                    log.error("Error rolling back UOW in transaction consumer");
                }
            }

            //Only need to update the transaction if the process is being run asynchronously.
            if (postImmediate == null || !postImmediate.booleanValue()) {

                // release the UOWs connection before creating a new one to move this transaction to the error state
                uow.rollback();

                // Update Transaction status to E
                TransactionEngine.getInstance().updateTransactionStatusToError(transactionId, e);
                log.error(MessageHelper.findMessage("label.Jaffa.Transaction.TransactionConsumer.error", null), e);
            }

            throw e;
        } finally {
            // Unset the Logging context
            if (createdLoggingContext) {
                LoggingService.unsetLoggingContext();
            }
        }
        return uow;
    }

    /**
     * This method is invoked directly when processing a Transaction synchronously.
     * It'll invoke the handler associated with the input transaction's payload, as obtained from the transaction configuration file.
     * In case of an error, the status of the transaction will be set to 'E'.
     *
     * @param uow                the provided uow.
     * @param transactionMessage the TransactionMessage.
     */
    public UOW process(UOW uow, TransactionMessage transactionMessage) throws Exception {
        process(uow, transactionMessage.getId(), null);
        return uow;
    }

    /**
     * Invokes the handler to process the payload.
     */
    protected void invokeHandler(UOW uow, TransactionInfo transactionInfo, Object dataBean) throws Exception {
        // Obtain the handlerClass
        Class handlerClass = Class.forName(transactionInfo.getToClass());

        // Obtain the handler method
        Method handlerMethod = null;
        try {
            handlerMethod = handlerClass.getMethod(transactionInfo.getToMethod(), new Class[]{UOW.class, dataBean.getClass()});
        } catch (NoSuchMethodException e) {
            // Note that the payload could be a subclass of the argument that the handler method expects
            // Hence use the dataBeanClass specified in the transactionInfo to get the appropriate handlerMethod
            if (log.isDebugEnabled()) {
                log.debug("Handler method " + transactionInfo.getToClass() + '.' + transactionInfo.getToMethod() + "(UOW, " + dataBean.getClass().getName() + ')' + " not found. Will look for a method that takes " + transactionInfo.getDataBean() + " as argument");
            }
            Class dataBeanClass = Class.forName(transactionInfo.getDataBean());
            handlerMethod = handlerClass.getMethod(transactionInfo.getToMethod(), new Class[]{UOW.class, dataBeanClass});
        }

        // Create an instance of handlerClass, if the handlerMethod is not static
        Object handlerObject = Modifier.isStatic(handlerMethod.getModifiers()) ? null : handlerClass.newInstance();

        // Invoke the handler
        if (log.isDebugEnabled()) {
            log.debug("Invoking the handler " + handlerMethod);
        }
        handlerMethod.invoke(handlerObject, new Object[]{uow, dataBean});
    }

    protected String[] readRule(String ruleName, String defaultValue) {
        String ruleValues = (String) ContextManagerFactory.instance().getProperty(ruleName);
        String[] ruleArr = null;

        if (ruleValues != null) {
            ruleArr = StringUtils.split(ruleValues, ",");
        }

        return ruleArr != null ? ruleArr : new String[]{defaultValue};
    }

    protected int readRule(String ruleName, int defaultValue) {
        Integer output = null;
        String ruleValue = (String) ContextManagerFactory.instance().getProperty(ruleName);
        if (ruleValue != null) {
            ruleValue = ruleValue.trim();
            if (ruleValue.length() > 0) {
                try {
                    output = Integer.parseInt(ruleValue);
                } catch (NumberFormatException e) {
                    log.warn("Invalid numeric value '" + ruleValue + "' specified for the application-rule '" + ruleName + '\'');
                }
            }
        }
        return output != null ? output : defaultValue;
    }
}
