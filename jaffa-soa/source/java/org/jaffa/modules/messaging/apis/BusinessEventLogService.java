package org.jaffa.modules.messaging.apis;

import org.apache.log4j.Logger;
import org.jaffa.modules.messaging.apis.data.BusinessEventLogCriteria;
import org.jaffa.modules.messaging.apis.data.BusinessEventLogGraph;
import org.jaffa.modules.messaging.apis.data.BusinessEventLogQueryResponse;
import org.jaffa.modules.messaging.apis.data.BusinessEventLogUpdateResponse;
import org.jaffa.soa.dataaccess.GraphService;
import org.jaffa.soa.dataaccess.TransformationHandler;

/**
 * Provides functions to manage ValidFieldValue records.
 */
public class BusinessEventLogService extends GraphService<BusinessEventLogCriteria, BusinessEventLogGraph, BusinessEventLogQueryResponse, BusinessEventLogUpdateResponse, TransformationHandler> {

    private static Logger log = Logger.getLogger(BusinessEventLogService.class);

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
    public BusinessEventLogQueryResponse query(BusinessEventLogCriteria criteria) {
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
    public BusinessEventLogUpdateResponse[] update(BusinessEventLogGraph[] graphs) {
        return super._update(graphs);
    }
}
