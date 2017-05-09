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
package org.jaffa.transaction.apis;

import java.util.Iterator;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.soa.dataaccess.GraphService;
import org.jaffa.transaction.apis.data.TransactionCriteria;
import org.jaffa.transaction.apis.data.TransactionGraph;
import org.jaffa.transaction.apis.data.TransactionQueryResponse;
import org.jaffa.transaction.apis.data.TransactionUpdateResponse;
import org.jaffa.transaction.apis.impl.TransactionHandler;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.services.TransactionEngine;

/**
 * Provides functions for Creating, Retrieving, Updating and
 * Deleting data in the set of objects within a specific Graph.
 * @version 1.0
 */
public class TransactionService extends GraphService<TransactionCriteria, TransactionGraph, TransactionQueryResponse, TransactionUpdateResponse, TransactionHandler> {

    /**
     * Query domain objects, returning an array of Graphs in the response.
     * By default no related objects are returned, and any foreign objects will only
     * contain key information. Use the <code>setResultGraphRules()</code> method on
     * the criteria to change this.
     * When an error occurs, one or more instances of ServiceError, indicating
     * some kind of internal system error (like a problem accessing the database),
     * or listing the business logic errors will be returned in the response.
     * @param criteria This specified the criteria for the records to be retrieved.
     * @return An array of Graphs, the content of this graph is based on the result filter 'rules', as provided in the criteria object.
     * while processing. When an error occurs, an array of ServiceError instances will be returned
     * with details about the root cause of the problem.
     */
    public TransactionQueryResponse query(TransactionCriteria criteria) {
        return super._query(criteria);
    }

    /**
     * Create/Update a domain object and/or its related objects. This allows a set of
     * Graphs to be supplied with either existing records to be updated, or new
     * entries that will be created.
     * <p>
     * Each domain object graph in the array is processed in its own ACID Transaction.
     * Any error in processing any part of a single Graph will cause all updates for that
     * graph to be rolled back, and the graph will be returned in a response object
     * along with a list of the errors. In the case of a runtime error (as opposed to
     * and data/validation error) the update is retried before it is treated as an error.
     * @param graphs The set of Object Graphs to Update/Create.
     * @return An array of response objects, each response contains a copy of the Graph
     * that failed and the list of errors as to why it failed. In case the domain object is created,
     * the response will contain the root Graph with the key-fields only.
     */
    public TransactionUpdateResponse[] update(TransactionGraph[] graphs) {
        return super._update(graphs);
    }

    /**
     * Returns an array of Transaction Type names, as defined in the configuration file.
     * The array will contain the accessible Types only.
     * @return an array of Transaction Type names, as defined in the configuration file.
     */
    public String[] getTypeNames() {
        return TransactionEngine.getInstance().getAccessibleTypeNamesWithLabels();
    }

    /**
     * Returns an array of Transaction SubType names, as defined in the configuration file.
     * The array will contain the accessible SubTypes only.
     * @return an array of Transaction SubType names, as defined in the configuration file.
     */
    public String[] getSubTypeNames() {
        return TransactionEngine.getInstance().getAccessibleSubTypeNames();
    }

    /**
     * Returns true if the current authenticated user is in a role, which has access to browse the input Transaction Type.
     * @param typeName the Transaction Type name.
     * @return true if the current authenticated user is in a role, which has access to browse the input Transaction Type.
     */
    public boolean hasBrowseAccess(String typeName) {
        return TransactionEngine.getInstance().hasBrowseAccess(typeName);
    }

    /**
     * Returns true if the current authenticated user is in a role, which has access to Delete/Resubmit Transactions of the input Type.
     * @param typeName the Transaction Type name.
     * @return true if the current authenticated user is in a role, which has access to Delete/Resubmit Transactions of the input Type.
     */
    public boolean hasAdminAccess(String typeName) {
        return TransactionEngine.getInstance().hasAdminAccess(typeName);
    }

    /**
     * This override to the base class performs the following:
     * - Automatically transforms the Delete of an In-Process Transaction, to an Update to INT status
     * - An Interrupted Transaction cannot be deleted
     * - Only allows status change from E to O
     * - Allows creation of a new Transaction in O or H status only
     */
    @Override
    public TransactionGraph localUpdate(String path, TransactionGraph graph, UOW uow) throws FrameworkException, ApplicationExceptions {
        boolean createMode = true;
        if (graph.getId() != null) {
            Transaction domain = Transaction.findByPK(uow, graph.getId());
            if (domain != null) {
                createMode = false;
                if (domain.getType() != null && !TransactionEngine.getInstance().hasAdminAccess(domain.getType())) {
                    //Admin access is needed to update/delete/resubmit this transaction
                    throw new ApplicationExceptions(new ApplicationException("error.Jaffa.Transaction.Transaction.noAdminAccess", new Object[]{domain.getType()}));
                }

                if (graph.getDeleteObject() != null && graph.getDeleteObject()) {
                    if (Transaction.Status.I.toString().equals(domain.getStatus())) {
                        //Automatically transform the Delete of an In-Process Transaction, to an Update to INT status
                        graph.setDeleteObject(null);
                        graph.setStatus(Transaction.Status.INT.toString());
                    } else if (Transaction.Status.INT.toString().equals(domain.getStatus())) {
                        //An Interrupted Transaction cannot be deleted
                        throw new ApplicationExceptions(new ApplicationException("error.Jaffa.Transaction.Transaction.cannnotDeleteINT"));
                    }
                } else if (graph.hasChanged("status") && !((Transaction.Status.E.toString().equals(domain.getStatus()) || Transaction.Status.O.toString().equals(domain.getStatus())) && Transaction.Status.O.toString().equals(graph.getStatus()))) {
                    //Only allow status change from E to O (or O to O)
                    throw new ApplicationExceptions(new ApplicationException("error.Jaffa.Transaction.Transaction.invalidStatusChange"));
                }
            }
        }

        if (createMode) {
            if (graph.getType() != null && !TransactionEngine.getInstance().hasAdminAccess(graph.getType())) {
                //Admin access is needed to create this transaction
                throw new ApplicationExceptions(new ApplicationException("error.Jaffa.Transaction.Transaction.noAdminAccess", new Object[]{graph.getType()}));
            }

            if (graph.getStatus() == null)
                graph.setStatus(Transaction.Status.O.toString());
            else if (!Transaction.Status.O.toString().equals(graph.getStatus()) && !Transaction.Status.H.toString().equals(graph.getStatus())) {
                //Allow creation of a new Transaction in O or H status only
                throw new ApplicationExceptions(new ApplicationException("error.Jaffa.Transaction.Transaction.invalidStatusOnCreate"));
            }
        }

        return super.localUpdate(path, graph, uow);
    }
}
