package org.jaffa.soa.graph;

import org.jaffa.beans.factory.config.StaticContext;

/**
 * The CommentGraph is a base class that can be used and extended for specific comment tables.
 *
 * @version 1.0
 */
public abstract class CommentGraph extends GraphDataObject {

    /**
     * Holds value of property comments.
     */
    private String comments;

    /**
     * Default constructor.
     */
    public CommentGraph() {
        StaticContext.configure(this);
    }

    /**
     * Getter for property comments.
     *
     * @return Value of property comments.
     */
    public String getComments() {
        return this.comments;
    }

    /**
     * Setter for property comments.
     *
     * @param comments New value of property comments.
     */
    public void setComments(String comments) {
        String oldComments = this.comments;
        this.comments = comments;
        propertyChangeSupport.firePropertyChange("comments", oldComments, comments);
    }

}
