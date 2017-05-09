package org.jaffa.components.audit.apis.data;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.soa.graph.GraphDataObject;

public class AuditTransactionOverflowGraph extends GraphDataObject {

    private String fieldId;
    private String fieldName;
    private String fromValue;
    private String toValue;

    /**
     * Default constructor.
     */
    public AuditTransactionOverflowGraph() {
        StaticContext.configure(this);
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFromValue() {
        return fromValue;
    }

    public void setFromValue(String fromValue) {
        this.fromValue = fromValue;
    }

    public String getToValue() {
        return toValue;
    }

    public void setToValue(String toValue) {
        this.toValue = toValue;
    }

}
