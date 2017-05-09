package org.jaffa.persistence.domainobjects;

import org.jaffa.datatypes.*;

/** This is an Interface with mere getters and setters.
 * The Persistence Engine will generate a dynamic proxy for this Interface.
 * The dynamic proxy will be used for persisting data to the database.
 */
public interface ICondition {

    // Field constants
    /** A constant to identity the Condition field.*/
    public static final String CONDITION = "Condition";
    /** A constant to identity the Description field.*/
    public static final String DESCRIPTION = "Description";


    /** Getter for property condition.
     * @return Value of property condition.
     */
    public java.lang.String getCondition();

    /** Setter for property condition.
     * @param condition Value of property condition.
     */
    public void setCondition(java.lang.String condition);


    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription();

    /** Setter for property description.
     * @param description Value of property description.
     */
    public void setDescription(java.lang.String description);

}
