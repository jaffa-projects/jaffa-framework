package org.jaffa.modules.setup.apis.data;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.soa.graph.GraphDataObject;

/**
 * The ValidFieldValue Data Object.
 * <p/>
 * This models the data held in the ValidFieldValue record.
 * <p/>
 *
 * @version 1.0
 */
public class ValidFieldValueGraph extends GraphDataObject {

    private String tableName;
    private String fieldName;
    private String legalValue;
    private String description;
    private String remarks;

    /**
     * Default constructor.
     */
    public ValidFieldValueGraph() {
        StaticContext.configure(this);
    }

    /**
     * Getter for property tableName.
     *
     * @return Value of property tableName.
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Setter for property tableName.
     *
     * @param tableName Value of property tableName.
     */
    public void setTableName(String tableName) {
        String oldTableName = this.tableName;
        this.tableName = tableName;
        propertyChangeSupport.firePropertyChange("tableName", oldTableName, tableName);
    }

    /**
     * Getter for property fieldName.
     *
     * @return Value of property fieldName.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Setter for property fieldName.
     *
     * @param fieldName Value of property fieldName.
     */
    public void setFieldName(String fieldName) {
        String oldFieldName = this.fieldName;
        this.fieldName = fieldName;
        propertyChangeSupport.firePropertyChange("fieldName", oldFieldName, fieldName);
    }

    /**
     * Getter for property legalValue.
     *
     * @return Value of property legalValue.
     */
    public String getLegalValue() {
        return legalValue;
    }

    /**
     * Setter for property legalValue.
     *
     * @param legalValue Value of property legalValue.
     */
    public void setLegalValue(String legalValue) {
        String oldLegalValue = this.legalValue;
        this.legalValue = legalValue;
        propertyChangeSupport.firePropertyChange("legalValue", oldLegalValue, legalValue);
    }

    /**
     * Getter for property description.
     *
     * @return Value of property description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for property description.
     *
     * @param description Value of property description.
     */
    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        propertyChangeSupport.firePropertyChange("description", oldDescription, description);
    }

    /**
     * Getter for property remarks.
     *
     * @return Value of property remarks.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Setter for property remarks.
     *
     * @param remarks Value of property remarks.
     */
    public void setRemarks(String remarks) {
        String oldRemarks = this.remarks;
        this.remarks = remarks;
        propertyChangeSupport.firePropertyChange("remarks", oldRemarks, remarks);
    }
}
