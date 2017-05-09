package org.jaffa.modules.messaging.apis.data;

import org.jaffa.soa.graph.GraphUpdateResponse;

/**
 * This class is used to return the response from the update() method of a graph-based service.
 * <p>
 * When an error occurs, this object is returned containing a complete copy of the original source GraphDataObject.
 * It'll also contain one or more instances of ServiceError, indicating
 * some kind of internal system error (like a problem accessing the database),
 * or listing the business logic errors (like mandatory fields, or foreign key validations etc).
 * <p>
 * In CREATE mode, the root GraphDataObject will be returned with just the key-fields.
 */
public class BusinessEventLogUpdateResponse extends GraphUpdateResponse<BusinessEventLogGraph> {

    /**
     * Getter for property source. This is the source GraphDataObject
     * that the error ocurred on.
     * <p>
     * In CREATE mode, the root GraphDataObject will be returned with just the key-fields.
     * @return Value of property source.
     */
    public BusinessEventLogGraph getSource() {
        return super.source;
    }

    /**
     * Setter for property source.
     * @param source New value of property source.
     */
    public void setSource(BusinessEventLogGraph source) {
        super.source = source;
    }
}
