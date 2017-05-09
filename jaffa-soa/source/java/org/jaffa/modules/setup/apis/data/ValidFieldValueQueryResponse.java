package org.jaffa.modules.setup.apis.data;

import org.jaffa.soa.graph.GraphQueryResponse;

/**
 * This class is used to return the response from the query() method of a graph-based service.
 * <p>
 * Under normal circumstances, an array of Graph objects will be returned.
 * When an error occurs, one or more instances of ServiceError, indicating
 * some kind of internal system error (like a problem accessing the database),
 * or listing the business logic errors will be returned.
 */
public class ValidFieldValueQueryResponse extends GraphQueryResponse<ValidFieldValueGraph> {

    /**
     * Getter for property graphs.
     * @return the graphs
     */
    public ValidFieldValueGraph[] getGraphs() {
        return super.graphs;
    }

    /**
     * Setter for property graphs.
     * @param graphs the graphs to set
     */
    public void setGraphs(ValidFieldValueGraph[] graphs) {
        super.graphs = graphs;
    }
}
