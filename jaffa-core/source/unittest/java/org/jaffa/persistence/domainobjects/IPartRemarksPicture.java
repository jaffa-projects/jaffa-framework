package org.jaffa.persistence.domainobjects;

import org.jaffa.datatypes.*;

/** This is an Interface with mere getters and setters.
 * The Persistence Engine will generate a dynamic proxy for this Interface.
 * The dynamic proxy will be used for persisting data to the database.
 */
public interface IPartRemarksPicture {

    // Field constants
    /** A constant to identity the Part field.*/
    public static final String PART = "Part";
    /** A constant to identity the Remarks field.*/
    public static final String REMARKS = "Remarks";
    /** A constant to identity the Picture field.*/
    public static final String PICTURE = "Picture";


    /** Getter for property part.
     * @return Value of property part.
     */
    public java.lang.String getPart();

    /** Setter for property part.
     * @param part Value of property part.
     */
    public void setPart(java.lang.String part);

    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks();

    /** Setter for property remarks.
     * @param remarks Value of property remarks.
     */
    public void setRemarks(java.lang.String remarks);



    /** Getter for property picture.
     * @return Value of property picture.
     */
    public byte[] getPicture();

    /** Setter for property picture.
     * @param picture Value of property picture.
     */
    public void setPicture(byte[] picture);

}
