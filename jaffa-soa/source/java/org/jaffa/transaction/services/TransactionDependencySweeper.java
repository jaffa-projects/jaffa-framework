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

import java.util.Iterator;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.DomainObjectNotFoundException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.transaction.daos.TransactionMessageDAO;
import org.jaffa.transaction.daos.TransactionMessageDAOFactory;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.domain.TransactionDependency;
import org.jaffa.transaction.domain.TransactionDependencyMeta;
import org.jaffa.transaction.domain.TransactionDependencySweeperView;
import org.jaffa.transaction.domain.TransactionDependencySweeperViewMeta;
import org.jaffa.transaction.domain.TransactionSweeperView;
import org.jaffa.transaction.domain.TransactionSweeperViewMeta;

/**
 * This class polls the Sweeper views for new records.
 * Whenever a new record is picked up, sweeper logic is run to
 * clean up any missed transactions
 * 
 * @author RickK
 * 
 */
@XmlRootElement
public class TransactionDependencySweeper {

	private String scheduleTaskId;
	private String loggedBy;
    private TransactionMessageDAO transactionMessageDAO = TransactionMessageDAOFactory.getTransactionMessageDAO();
	
	private static final Logger log = Logger.getLogger(TransactionDependencySweeper.class);
	
	/**
	 * Polls the transactions view for missed transactions
	 * 
	 * @param uow - the passed in UOW from the Transaction Consumer
	 * @param databean The Polling databean
	 * @throws FrameworkException
	 * @throws ApplicationException
	 * @throws ApplicationExceptions
	 */
    public void poll(UOW uow, TransactionDependencySweeper databean) throws FrameworkException, ApplicationException, ApplicationExceptions {

    	// Poll to check irrelevant Transaction Dependency relationships
        for (TransactionDependencySweeperView sweeperView : transactionMessageDAO.getDependencySweeperViewsIfOpenNoDependsOnTx()) {
            updateTransactionDependency(sweeperView, databean);
        }

    	// Poll to check for transactions where all of their dependencies have been satisfied, put them into the O state
        for (TransactionSweeperView sweeperView : transactionMessageDAO.getSweeperViewsIfOnHoldNoOpenDependency()) {
            updateTransaction(sweeperView, databean);
        }
    }

    /**
     * Updates missed Transaction Dependency records
     * @param tdsv
     * @param databean
     */
    private void updateTransactionDependency(TransactionDependencySweeperView tdsv, TransactionDependencySweeper databean) {
    	UOW innerUow = null;
    
    	try {
    		innerUow = new UOW();
            TransactionDependency transactionDependency = TransactionDependency.findByPK(innerUow, tdsv.getTransactionId(),tdsv.getDependsOnId());
            if (transactionDependency == null) {
                log.info("TransactionDependency '" + tdsv.getTransactionId() +"/"+ tdsv.getDependsOnId() + "' not found; cannot update it's status to S");
                throw new DomainObjectNotFoundException(TransactionDependencyMeta.getLabelToken());
            } else {
            	transactionDependency.setStatus(TransactionDependency.Status.S.toString());
                transactionMessageDAO.save(innerUow, transactionDependency);
            	innerUow.commit();
            }
    	} catch(Exception e) {
    		//Write to business event log
    		// Sets Log4J's MDC to enable BusinessEventLogging
            MDC.put(BusinessEventLogMeta.SCHEDULED_TASK_ID, databean.getScheduleTaskId());
            MDC.put(BusinessEventLogMeta.LOGGED_BY, databean.getLoggedBy());
            log.error("Exception thrown during update of Transaction Dependency record"+e);
    	} finally {
    		if(innerUow!=null) {
    			try {
    				innerUow.rollback();
    			} catch(Exception e) {
    				log.error("Unable to rollback inner UOW in updating Transaction Dependency");
    			}
    		}
    	}
    }
    
    /**
     * Updated missed Transaction records
     * @param tdv
     * @param databean
     */
    
    private void updateTransaction(TransactionSweeperView tdv,TransactionDependencySweeper databean) {
    	UOW innerUow = null;
    	
    	try {
    		innerUow = new UOW();
            Transaction transaction = Transaction.findByPK(innerUow, tdv.getId());
            if (transaction == null) {
                log.info("Transaction '" + tdv.getId() +"' not found; cannot update it's status to O");
                throw new DomainObjectNotFoundException(TransactionDependencyMeta.getLabelToken());
            } else {
            	transaction.setStatus(Transaction.Status.O.toString());
                transactionMessageDAO.save(innerUow, transaction);
            	innerUow.commit();
            }
    	} catch(Exception e) {
    		//Write to business event log
    		MDC.put(BusinessEventLogMeta.SCHEDULED_TASK_ID, databean.getScheduleTaskId());
            MDC.put(BusinessEventLogMeta.LOGGED_BY, databean.getLoggedBy());
    		log.error("Exception thrown during update of Transaction record"+e);
    	} finally {
    		if(innerUow!=null) {
    			try {
    				innerUow.rollback();
    			} catch(Exception e) {
    				log.error("Unable to rollback inner UOW in updating Transaction");
    			}
    		}
    	}
    	
    }
    /**
     * Returns the scheduleTaskId. If one hasn't been set it will return the one from the MDC context
     * @return scheduleTaskId
     */
	public String getScheduleTaskId() {
		return scheduleTaskId !=null ? scheduleTaskId : (String)MDC.get(BusinessEventLogMeta.SCHEDULED_TASK_ID);
	}

	/**
	 * Sets the scheduleTaskId
	 * @param scheduleTaskId
	 */
	public void setScheduleTaskId(String scheduleTaskId) {
		this.scheduleTaskId = scheduleTaskId;
	}

	/**
     * Returns the loggedBy. If one hasn't been set it will return the one from the MDC context
     * @return loggedBy
     */
	public String getLoggedBy() {
		return loggedBy !=null ? loggedBy : (String)MDC.get(BusinessEventLogMeta.LOGGED_BY);
	}

	/**
	 * Sets the scheduleTaskId
	 * @param scheduleTaskId
	 */
	public void setLoggedBy(String loggedBy) {
		this.loggedBy = loggedBy;
	}
    
}
