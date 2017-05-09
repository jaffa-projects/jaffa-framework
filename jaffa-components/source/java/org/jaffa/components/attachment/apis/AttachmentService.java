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
package org.jaffa.components.attachment.apis;

import org.jaffa.components.attachment.apis.data.AttachmentCriteria;
import org.jaffa.components.attachment.apis.data.AttachmentGraph;
import org.jaffa.components.attachment.apis.data.AttachmentQueryResponse;
import org.jaffa.components.attachment.apis.data.AttachmentUpdateResponse;
import org.jaffa.components.attachment.apis.impl.AttachmentHandler;
import org.jaffa.components.finder.CriteriaField;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.soa.dataaccess.GraphService;

/**
 * Provides functions for Creating, Retrieving, Updating and
 * Deleting data in the set of objects within a specific Graph.
 * @author GautamJ
 */
public class AttachmentService extends GraphService<AttachmentCriteria, AttachmentGraph, AttachmentQueryResponse, AttachmentUpdateResponse, AttachmentHandler> {

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
    public AttachmentQueryResponse query(AttachmentCriteria criteria) {
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
    public AttachmentUpdateResponse[] update(AttachmentGraph[] graphs) {
        return super._update(graphs);
    }


    public static void deleteAttachment(UOW uow, String serializedKey) throws FrameworkException, ApplicationExceptions{
        AttachmentService service = new AttachmentService();
        AttachmentCriteria attachmentCriteria = new AttachmentCriteria();
        attachmentCriteria.setSerializedKey(StringCriteriaField.getStringCriteriaField(CriteriaField.RELATIONAL_EQUALS, serializedKey, null));

        AttachmentGraph[] graphs = service.localQuery(attachmentCriteria, uow);
        if(graphs!=null && graphs.length > 0){
            for (AttachmentGraph graph : graphs) {
                graph.setDeleteObject(Boolean.TRUE);
                service.localUpdate(null, graph, uow);
            }
        }
    }

    public static AttachmentUpdateResponse cloneAttachment(UOW uow, String oldSerializedKey, String newSerializedKey) throws FrameworkException, ApplicationExceptions{
        AttachmentService service = new AttachmentService();
        AttachmentCriteria attachmentCriteria = new AttachmentCriteria();
        attachmentCriteria.setSerializedKey(StringCriteriaField.getStringCriteriaField(CriteriaField.RELATIONAL_EQUALS, oldSerializedKey, null));
        AttachmentGraph attachmentGraph = new AttachmentGraph();
        attachmentGraph.setSerializedKey(newSerializedKey);
        AttachmentUpdateResponse[] attachmentUpdateResponses = service.localCloneOrMassUpdate(attachmentCriteria, attachmentGraph, true, uow);

        return attachmentUpdateResponses!=null ? attachmentUpdateResponses[0] : null;
    }

}
