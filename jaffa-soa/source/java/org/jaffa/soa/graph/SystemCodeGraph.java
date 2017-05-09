package org.jaffa.soa.graph;

import org.jaffa.beans.factory.config.StaticContext;

/**
 * The SystemCodeGraph is a base class that can be used and extended for specific system codes.
 *
 * @version 1.0
 */
public abstract class SystemCodeGraph extends GraphDataObject {

    /**
     * Holds value of property code.
     */
    private String code;
    /**
     * Holds value of property description.
     */
    private String description;

    /**
     * Getter for property code.
     *
     * @return Value of property code.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Setter for property code.
     *
     * @param code New value of property code.
     */
    public void setCode(String code) {
        String oldCode = this.code;
        this.code = code;
        propertyChangeSupport.firePropertyChange("code", oldCode, code);
    }

    /**
     * Getter for property description.
     *
     * @return Value of property description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for property description.
     *
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        propertyChangeSupport.firePropertyChange("description", oldDescription, description);
    }

}
