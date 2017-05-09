/*
 * SavedQueryGraph.java
 *
 * Created on Oct 27, 2011
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.jaffa.ria.finder.apis.data;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.soa.graph.GraphDataObject;

public class SavedQueryGraph extends GraphDataObject {

    /**
     * Holds value of property queryId.
     */
    private java.lang.String queryId;
    /**
     * Holds value of property userId.
     */
    private java.lang.String userId;
    /**
     * Holds value of property componentRef.
     */
    private java.lang.String componentRef;
    /**
     * Holds value of property contextRef.
     */
    private java.lang.String contextRef;
    /**
     * Holds value of property queryName.
     */
    private java.lang.String queryName;
    /**
     * Holds value of property isDefault.
     */
    private java.lang.Boolean isDefault;
    /**
     * Holds value of property criteria.
     */
    private java.lang.String criteria;

    /**
     * Default constructor.
     */
    public SavedQueryGraph() {
        StaticContext.configure(this);
    }

    /**
     * @return the queryId
     */
    public java.lang.String getQueryId() {
        return queryId;
    }

    /**
     * @param queryId the queryId to set
     */
    public void setQueryId(java.lang.String queryId) {
        String oldQueryId = this.queryId;
        this.queryId = queryId;
        propertyChangeSupport.firePropertyChange("queryId", oldQueryId, queryId);
    }

    /**
     * @return the userId
     */
    public java.lang.String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(java.lang.String userId) {
        String oldUserId = this.userId;
        this.userId = userId;
        propertyChangeSupport.firePropertyChange("userId", oldUserId, userId);
    }

    /**
     * @return the componentRef
     */
    public java.lang.String getComponentRef() {
        return componentRef;
    }

    /**
     * @param componentRef the componentRef to set
     */
    public void setComponentRef(java.lang.String componentRef) {
        String oldComponentRef = this.componentRef;
        this.componentRef = componentRef;
        propertyChangeSupport.firePropertyChange("componentRef", oldComponentRef, componentRef);
    }

    /**
     * @return the contextRef
     */
    public java.lang.String getContextRef() {
        return contextRef;
    }

    /**
     * @param contextRef the contextRef to set
     */
    public void setContextRef(java.lang.String contextRef) {
        String oldContextRef = this.contextRef;
        this.contextRef = contextRef;
        propertyChangeSupport.firePropertyChange("contextRef", oldContextRef, contextRef);
    }

    /**
     * @return the queryName
     */
    public java.lang.String getQueryName() {
        return queryName;
    }

    /**
     * @param queryName the queryName to set
     */
    public void setQueryName(java.lang.String queryName) {
        String oldQueryName = this.queryName;
        this.queryName = queryName;
        propertyChangeSupport.firePropertyChange("queryName", oldQueryName, queryName);
    }

    /**
     * @return the isDefault
     */
    public java.lang.Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(java.lang.Boolean isDefault) {
        Boolean oldIsDefault = this.isDefault;
        this.isDefault = isDefault;
        propertyChangeSupport.firePropertyChange("isDefault", oldIsDefault,
                isDefault);
    }

    /**
     * @return the criteria
     */
    public java.lang.String getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(java.lang.String criteria) {
        String oldCriteria = this.criteria;
        this.criteria = criteria;
        propertyChangeSupport.firePropertyChange("criteria", oldCriteria, criteria);
    }

}
