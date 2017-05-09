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
import org.jaffa.datatypes.Formatter;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.DomainObjectChangedException;
import org.jaffa.exceptions.DomainObjectNotFoundException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.rules.util.ScriptHelper;
import org.jaffa.security.SecurityManager;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.transaction.daos.TransactionMessageDAO;
import org.jaffa.transaction.daos.TransactionMessageDAOFactory;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.domain.TransactionDependency;
import org.jaffa.transaction.domain.TransactionMeta;
import org.jaffa.transaction.services.configdomain.Param;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;
import org.jaffa.util.EmailerBean;
import org.jaffa.util.ExceptionHelper;
import org.jaffa.util.MessageHelper;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This is the main class of the Jaffa Transaction module.
 * It provides various services for submitting transactions.
 */
public class TransactionEngine {

    private static final Logger log = Logger.getLogger(TransactionEngine.class);
    private static final String RULE_FAILURE_NOTIFICATION_PREFIX = "jaffa.transaction.failureNotification";
    private static final String EMAIL_SUBJECT_NOTIFICATION = "email.subject.transaction.failureNotification";
    private static final String EMAIL_BODY_NOTIFICATION = "email.body.transaction.failureNotification";
    private static volatile TransactionEngine c_singleton = null;

    /**
     * A private constructor.
     */
    private TransactionEngine() {
    }

    /**
     * Creates an instance of TransactionEngine, if not already instantiated.
     *
     * @return An instance of the TransactionEngine.
     */
    public static TransactionEngine getInstance() {
        if (c_singleton == null) {
            createTransactionEngineInstance();
        }
        return c_singleton;
    }

    /**
     * Returns an array of Transaction Type names, as defined in the configuration file.
     * The array will contain the accessible Types only.
     *
     * @return an array of Transaction Type names, as defined in the configuration file.
     */
    public String[] getAccessibleTypeNames() {
        String[] typeNames = ConfigurationService.getInstance().getTypeNames();
        if (typeNames != null && typeNames.length > 0) {
            List<String> accessibleTypeNames = new LinkedList<String>();
            for (String typeName : typeNames) {
                TypeInfo typeInfo = ConfigurationService.getInstance().getTypeInfo(typeName);
                if (hasBrowseAccess(typeInfo)) {
                    accessibleTypeNames.add(typeName);
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("No browse access to " + typeName);
                    }
                }
            }
            typeNames = accessibleTypeNames.toArray(new String[accessibleTypeNames.size()]);
        }
        return typeNames;
    }


    /**
     * Returns an array of Transaction Types with Labels, as defined in the configuration file.
     * The array will contain the accessible Types only.
     *
     * @return an array of Transaction Type names, as defined in the configuration file.
     */
    public String[] getAccessibleTypeNamesWithLabels() {
        String[] typeNames = ConfigurationService.getInstance().getTypeNames();
        if (typeNames != null && typeNames.length > 0) {
            List<String> accessibleTypeNames = new LinkedList<String>();
            for (String typeName : typeNames) {
                TypeInfo typeInfo = ConfigurationService.getInstance().getTypeInfo(typeName);
                if (hasBrowseAccess(typeInfo)) {
                    accessibleTypeNames.add(typeInfo.getLabel() + "=" + typeName);
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("No browse access to " + typeName);
                    }
                }
            }
            typeNames = accessibleTypeNames.toArray(new String[accessibleTypeNames.size()]);
        }
        return typeNames;
    }

    /**
     * Returns an array of Transaction SubType names, as defined in the configuration file.
     * The array will contain the accessible SubTypes only.
     *
     * @return an array of Transaction SubType names, as defined in the configuration file.
     */
    public String[] getAccessibleSubTypeNames() {
        Collection<String> subTypes = new LinkedHashSet<String>();
        TransactionInfo[] allTransactionInfo = ConfigurationService.getInstance().getAllTransactionInfo();
        for (TransactionInfo transactionInfo : allTransactionInfo) {
            if (transactionInfo != null && transactionInfo.getSubType() != null && transactionInfo.getSubType().length() > 0 && hasBrowseAccess(transactionInfo.getType())) {
                subTypes.add(transactionInfo.getSubType());
            }
        }
        return subTypes.toArray(new String[subTypes.size()]);
    }

    /**
     * Returns true if the current authenticated user is in a role, which has access to browse the input Transaction Type.
     *
     * @param typeName the Transaction Type name.
     * @return true if the current authenticated user is in a role, which has access to browse the input Transaction Type.
     */
    public boolean hasBrowseAccess(String typeName) {
        TypeInfo typeInfo = ConfigurationService.getInstance().getTypeInfo(typeName);
        return typeInfo != null && hasBrowseAccess(typeInfo);
    }

    /**
     * Returns true if the current authenticated user is in a role, which has access to Delete/Resubmit Transactions of the input Type.
     *
     * @param typeName the Transaction Type name.
     * @return true if the current authenticated user is in a role, which has access to Delete/Resubmit Transactions of the input Type.
     */
    public boolean hasAdminAccess(String typeName) {
        TypeInfo typeInfo = ConfigurationService.getInstance().getTypeInfo(typeName);
        return typeInfo != null && hasAdminAccess(typeInfo);
    }

    /**
     * Retrieves the Transaction and update it's status to I.
     * NOTE: The processing happens within the scope of a local new UOW.
     *
     * @param id the Transaction Id.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void updateTransactionStatusToInProcess(String id) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            uow = new UOW();
            updateTransactionStatusToInProcess(uow, id);
            uow.commit();
        } catch (Exception e) {
            throw ExceptionHelper.throwAFR(e);
        } finally {
            if (uow != null) {
                uow.rollback();
            }
        }
    }

    /**
     * Updates the input transaction's status to I.
     * NOTE: The processing happens within the scope of a local new UOW.
     *
     * @param transaction the Transaction to update.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void updateTransactionStatusToInProcess(Transaction transaction) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            uow = new UOW();
            transaction.setUOW(uow);
            updateTransactionStatusToInProcess(uow, transaction);
            uow.commit();
        } catch (Exception e) {
            throw ExceptionHelper.throwAFR(e);
        } finally {
            if (uow != null) {
                uow.rollback();
            }
        }
    }


    /**
     * Retrieves the Transaction and update it's status to I.
     * This method should only be used during a synchronous transaction as the the entire transaction must be scoped within the same UOW
     * NOTE: The processing happens within the scope of the input UOW.
     *
     * @param uow the input UOW.
     * @param id  the Transaction Id.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void updateTransactionStatusToInProcess(UOW uow, String id) throws FrameworkException, ApplicationExceptions {
        Transaction transaction = getTransaction(uow, id);
        updateTransactionStatusToInProcess(uow, transaction);
    }


    /**
     * Updates the transaction's status to I.
     * This method should only be used during a synchronous transaction as the the entire transaction must be scoped within the same UOW
     * NOTE: The processing happens within the scope of the input UOW.
     *
     * @param uow         the input UOW.
     * @param transaction the Transaction to update.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void updateTransactionStatusToInProcess(UOW uow, Transaction transaction)
            throws FrameworkException, ApplicationExceptions {
        try {
            if (transaction == null) {
                log.error("Transaction not defined; cannot update it's status to I");
                throw new DomainObjectNotFoundException(TransactionMeta.getLabelToken());
            } else if (!Transaction.Status.O.toString().equals(transaction.getStatus())) {
                log.error("Only an Open transaction can be updated to In-Process status. Another thread maybe "
                                  + "processing or has already processed " + transaction);
                throw new DomainObjectChangedException(transaction.getLastChangedBy(),
                                                       Formatter.format(transaction.getLastChangedOn()));
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Updating Transaction '" + transaction.getId() + "' to status I");
                }

                // Add diagnostic information to the error message field
                // This is a temporary solution and should be removed
                String computerName = InetAddress.getLocalHost().getHostName();
                String processId = ManagementFactory.getRuntimeMXBean().getName();
                long threadId = Thread.currentThread().getId();
                transaction.setErrorMessage("Transaction started execution on Computer: " + computerName + " Process: "
                                                    + processId + " Thread: " + threadId);

                transaction.setStatus(Transaction.Status.I.toString());
                getTransactionDAO().save(uow, transaction);
            }
        } catch (Exception e) {
            throw ExceptionHelper.throwAFR(e);
        }
    }


    /**
     * Retrieves the Transaction and update it's status to S.
     * NOTE: The processing happens within the scope of the input UOW.
     *
     * @param uow The UOW.
     * @param id  the Transaction Id.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void updateTransactionStatusToSatisfied(UOW uow, String id) throws FrameworkException, ApplicationExceptions {
        try {
            Transaction transaction = getTransaction(uow, id);
            if (transaction == null) {
                log.error("Transaction '" + id + "' not found; cannot update it's status to S");
                throw new DomainObjectNotFoundException(TransactionMeta.getLabelToken());
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Updating Transaction '" + transaction.getId() + "' to status S");
                }
                transaction.setStatus(Transaction.Status.S.toString());
                getTransactionDAO().save(uow, transaction);
            }
        } catch (Exception e) {
            throw ExceptionHelper.throwAFR(e);
        }
    }

    /**
     * Retrieves the Transaction and deletes it.
     * NOTE: The processing happens within the scope of the input UOW.
     *
     * @param uow The UOW.
     * @param id  the Transaction Id.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void deleteTransaction(UOW uow, String id) throws FrameworkException, ApplicationExceptions {
        try {
            Transaction transaction = getTransaction(uow, id);
            if (transaction == null) {
                log.error("Transaction '" + id + "' not found; unable to delete it");
                throw new DomainObjectNotFoundException(TransactionMeta.getLabelToken());
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Deleting Transaction '" + transaction.getId() + "'");
                }
                getTransactionDAO().delete(uow, transaction);
            }
        } catch (Exception e) {
            throw ExceptionHelper.throwAFR(e);
        }
    }


    /**
     * Retrieves the Transaction and update it's status to E.
     * NOTE: The processing happens within the scope of a local new UOW.
     *
     * @param id        the Transaction Id.
     * @param exception the cause for the error.
     * @throws FrameworkException    Indicates some system error.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public void updateTransactionStatusToError(String id, Exception exception) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            uow = new UOW();
            Transaction transaction = getTransaction(uow, id);
            if (transaction == null) {
                log.error("Transaction '" + id + "' not found; cannot update it's status to E");
                throw new DomainObjectNotFoundException(TransactionMeta.getLabelToken());
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Updating Transaction '" + transaction.getId() + "' to status E");
                }
                transaction.stampError(exception);
                getTransactionDAO().save(uow, transaction);
                uow.commit();
                try {
                    sendFailureNotification(transaction, exception);
                } catch (Exception e) {
                    log.error("Error in sending transaction failure notification", e);
                }
            }
        } catch (Exception e) {
            log.error("Failed to set failed transaction id " + id + " to E", e);
            throw ExceptionHelper.throwAFR(e);
        } finally {
            if (uow != null) {
                uow.rollback();
            }
        }
    }

    /**
     * Constructs an email with error information, provided the application
     * rule 'jaffa.transaction.failureNotification.{type}.{subType}' is defined.
     */
    public static void sendFailureNotification(Transaction transaction, Exception exception) throws MessagingException, FrameworkException {
        //Determine the recipients from an appropriate application-rule
        //The recipients string is expected to be a semicolon-separated list of email-addresses
        String type = transaction.getType();
        String subType = transaction.getSubType();
        String recipients = null;
        if (type != null && subType != null) {
            recipients = (String) ContextManagerFactory.instance().getProperty(RULE_FAILURE_NOTIFICATION_PREFIX + '.' + type + '.' + subType);
        }
        if ((recipients == null || recipients.length() == 0) && type != null) {
            recipients = (String) ContextManagerFactory.instance().getProperty(RULE_FAILURE_NOTIFICATION_PREFIX + '.' + type);
        }
        if (recipients == null || recipients.length() == 0) {
            recipients = (String) ContextManagerFactory.instance().getProperty(RULE_FAILURE_NOTIFICATION_PREFIX);
        }
        if (recipients != null && recipients.length() > 0) {
            String[] to = recipients.split(";");
            Object dataBean = transaction.getTransactionPayloadObject() != null ? transaction.getTransactionPayloadObject().moldInternalPayload() : null;

            //Obtain the template for the subject
            String subject = null;
            if (dataBean != null) {
                subject = MessageHelper.findMessage(EMAIL_SUBJECT_NOTIFICATION + '.' + dataBean.getClass().getName(), null);
            }
            if (subject == null || subject.length() == 0 || subject.startsWith("???")) {
                subject = MessageHelper.findMessage(EMAIL_SUBJECT_NOTIFICATION, null);
            }

            //Obtain the template for the body
            String body = null;
            if (dataBean != null) {
                body = MessageHelper.findMessage(EMAIL_BODY_NOTIFICATION + '.' + dataBean.getClass().getName(), null);
            }
            if (body == null || body.length() == 0 || body.startsWith("???")) {
                body = MessageHelper.findMessage(EMAIL_BODY_NOTIFICATION, null);
            }

            //Create the context for the template
            Map context = new HashMap();
            context.put("transaction", transaction);
            context.put("bean", dataBean);
            context.put("exception", exception);
            context.put("type", type);
            context.put("subType", subType);
            context.put("errorMessage", ExceptionHelper.extractErrorMessage(exception));
            context.put("context", ContextManagerFactory.instance().getThreadContext());
            context.put("appUrl", ContextManagerFactory.instance().getProperty("app.url"));

            // transform the stacktrace into an attachment
            BodyPart attachment = new MimeBodyPart();
            attachment.setContent(ExceptionHelper.getStackTrace(exception), "text/plain");
            attachment.setFileName("stacktrace.txt");

            // send the failure notification
            if (log.isDebugEnabled()) {
                log.debug("Sending failure notification to " + recipients);
            }
            EmailerBean eb = new EmailerBean();
            eb.sendMailTemplate(null, to, subject, body, context, new BodyPart[]{attachment});
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Failure Notification will not be sent since rule '" + RULE_FAILURE_NOTIFICATION_PREFIX + "' is undefined");
            }
        }
    }

    /**
     * Returns the value from the input Param object.
     * Uses Java Scripting to evaluate the value, in case its an expression.
     *
     * @param param    the Param object.
     * @param dataBean the bean to be used in the context of BSF.
     * @return the value from the input Param object.
     * @throws FrameworkException    in case any internal error occurs.
     * @throws ApplicationExceptions Indicates application error(s).
     */
    public static String obtainParamValue(Param param, Object dataBean) throws FrameworkException, ApplicationExceptions {
        Object value = param.getValue();
        if (value != null && param.isExpression()) {
            try {
                value = ScriptHelper.instance(param.getLanguage()).evaluate(null, param.getValue(), dataBean, null, 0, 0);
                if (log.isDebugEnabled()) {
                    log.debug("Evaluation of the expression '" + param.getValue() + "' is: " + value);
                }
            } catch (Exception e) {
                throw ExceptionHelper.throwAFR(e);
            }
        }
        return value != null ? Formatter.format(value) : null;
    }

    /**
     * Creates an instance of TransactionEngine, if not already instantiated.
     */
    private static synchronized void createTransactionEngineInstance() {
        if (c_singleton == null) {
            c_singleton = new TransactionEngine();
            if (log.isDebugEnabled()) {
                log.debug("An instance of the TransactionEngine has been created");
            }
        }
    }

    /**
     * Gets the {@link Transaction} with the specified ID.  Returns null if the {@link Transaction} cannot be found.
     *
     * @param uow the UOW to use for the query to get the {@link Transaction}
     * @param id  the ID of the desired {@link Transaction}
     * @return the {@link Transaction} with the specified ID, or null if it cannot be found
     * @throws FrameworkException
     * @throws ApplicationExceptions
     */
    private Transaction getTransaction(UOW uow, String id) throws FrameworkException, ApplicationExceptions {
        return Transaction.findByPK(uow, id);
    }

    /**
     * Returns true if the current authenticated user is in a role, which has access to browse the input Transaction Type.
     *
     * @param typeInfo the TypeInfo for the Transaction Type.
     * @return true if the current authenticated user is in a role, which has access to browse the input Transaction Type.
     */
    private boolean hasBrowseAccess(TypeInfo typeInfo) {
        return typeInfo != null && (typeInfo.getSecurity() == null || hasFunctionAccess(typeInfo.getSecurity().getBrowse()));
    }

    /**
     * Returns true if the current authenticated user is in a role, which has access to Delete/Resubmit Transactions of the input Type.
     *
     * @param typeInfo the TypeInfo for the Transaction Type.
     * @return true if the current authenticated user is in a role, which has access to Delete/Resubmit Transactions of the input Type.
     */
    private boolean hasAdminAccess(TypeInfo typeInfo) {
        return typeInfo != null && (typeInfo.getSecurity() == null || hasFunctionAccess(typeInfo.getSecurity().getAdmin()));
    }

    /**
     * Returns true if the current authenticated user is in a role, which has access to the input business function.
     * A true will be returned if the input is null.
     * A false will be returned if the input is empty.
     *
     * @param functionName the business function.
     * @return true if the current authenticated user is in a role, which has access to the input business function.
     */
    private boolean hasFunctionAccess(String functionName) {
        boolean access;
        if (functionName == null) {
            access = true;
        } else if (functionName.trim().length() == 0) {
            access = false;
        } else {
            access = SecurityManager.checkFunctionAccess(functionName);
        }
        return access;
    }

    public void createTransactionDependency(UOW uow, Transaction transaction, String dependsOnId) throws ApplicationException, ApplicationExceptions, FrameworkException {
        TransactionDependency transactionDependency = null;

        if (transaction != null && dependsOnId != null) {
            transactionDependency = new TransactionDependency();

            transactionDependency.setTransactionId(transaction.getId());

            transactionDependency.setDependsOnId(dependsOnId);

            transactionDependency.setStatus(Transaction.Status.O.toString());

            TransactionMessageDAO transactionDAO = getTransactionDAO();
            transactionDAO.save(uow, transactionDependency);

            //Set the transaction to hold status. This will get fulfilled once the transaction dependency is satisfied
            transaction.setStatus(Transaction.Status.H.toString());
            transactionDAO.save(uow, transaction);
        }

        if (log.isDebugEnabled()) {
            log.debug("Created TransactionDependency " + transactionDependency);
        }

    }

    /**
     * Gets a DAO for transactions.
     * @return a DAO.
     */
    private static TransactionMessageDAO getTransactionDAO() {
        return TransactionMessageDAOFactory.getTransactionMessageDAO();
    }
}