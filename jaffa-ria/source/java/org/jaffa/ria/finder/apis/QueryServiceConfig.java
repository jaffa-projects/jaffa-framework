package org.jaffa.ria.finder.apis;

public class QueryServiceConfig {
    private String serviceClassName;
    private String serviceClassMethodName;
    private String criteriaClassName;
    private String criteriaObject;
    private String[] masterKeyFieldNames;
    private Object[] columnModel;

    /**
     * @return the serviceClassName
     */
    public String getServiceClassName() {
        return serviceClassName;
    }

    /**
     * @param serviceClassName the serviceClassName to set
     */
    public void setServiceClassName(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    /**
     * @return the serviceClassMethodName
     */
    public String getServiceClassMethodName() {
        return serviceClassMethodName;
    }

    /**
     * @param serviceClassMethodName the serviceClassMethodName to set
     */
    public void setServiceClassMethodName(String serviceClassMethodName) {
        this.serviceClassMethodName = serviceClassMethodName;
    }

    /**
     * @return the criteriaClassName
     */
    public String getCriteriaClassName() {
        return criteriaClassName;
    }

    /**
     * @param criteriaClassName the criteriaClassName to set
     */
    public void setCriteriaClassName(String criteriaClassName) {
        this.criteriaClassName = criteriaClassName;
    }

    /**
     * @return the criteriaObject
     */
    public String getCriteriaObject() {
        return criteriaObject;
    }

    /**
     * @param criteriaObject the criteriaObject to set
     */
    public void setCriteriaObject(String criteriaObject) {
        this.criteriaObject = criteriaObject;
    }

    /**
     * @return the masterKeyFieldNames
     */
    public String[] getMasterKeyFieldNames() {
        return masterKeyFieldNames;
    }

    /**
     * @param masterKeyFieldNames the masterKeyFieldNames to set
     */
    public void setMasterKeyFieldNames(Object[] masterKeyFieldNames) {
        this.masterKeyFieldNames = new String[masterKeyFieldNames.length];
        for (int i=0; i<masterKeyFieldNames.length; i++) 
            this.masterKeyFieldNames[i] = (String) masterKeyFieldNames[i];
    }

    /**
     * @return the columnModel
     */
    public Object[] getColumnModel() {
        return columnModel;
    }

    /**
     * @param columnModel the columnModel to set
     */
    public void setColumnModel(Object[] columnModel) {
        this.columnModel = columnModel;
    }

}
