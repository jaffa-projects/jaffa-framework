package org.jaffa.persistence.domainobjects;

import org.jaffa.datatypes.*;

/** This is an Interface with mere getters and setters.
 * The Persistence Engine will generate a dynamic proxy for this Interface.
 * The dynamic proxy will be used for persisting data to the database.
 */
public interface IItem {

    // Field constants
    /** A constant to identity the ItemId field.*/
    public static final String ITEM_ID = "ItemId";
    /** A constant to identity the ReceivedItemId field.*/
    public static final String RECEIVED_ITEM_ID = "ReceivedItemId";
    /** A constant to identity the Part field.*/
    public static final String PART = "Part";
    /** A constant to identity the Prime field.*/
    public static final String PRIME = "Prime";
    /** A constant to identity the Sc field.*/
    public static final String SC = "Sc";
    /** A constant to identity the Condition field.*/
    public static final String CONDITION = "Condition";
    /** A constant to identity the CreatedDatetime field.*/
    public static final String CREATED_DATETIME = "CreatedDatetime";
    /** A constant to identity the Qty field.*/
    public static final String QTY = "Qty";
    /** A constant to identity the KeyRef field.*/
    public static final String KEY_REF = "KeyRef";
    /** A constant to identity the Status1 field.*/
    public static final String STATUS1 = "Status1";
    /** A constant to identity the Status2 field.*/
    public static final String STATUS2 = "Status2";
    /** A constant to identity the Status3 field.*/
    public static final String STATUS3 = "Status3";
    /** A constant to identity the Price field.*/
    public static final String PRICE = "Price";


    /** Getter for property itemId.
     * @return Value of property itemId.
     */
    public java.lang.String getItemId();

    /** Setter for property itemId.
     * @param itemId Value of property itemId.
     */
    public void setItemId(java.lang.String itemId);


    /** Getter for property receivedItemId.
     * @return Value of property receivedItemId.
     */
    public java.lang.String getReceivedItemId();

    /** Setter for property receivedItemId.
     * @param receivedItemId Value of property receivedItemId.
     */
    public void setReceivedItemId(java.lang.String receivedItemId);



    /** Getter for property part.
     * @return Value of property part.
     */
    public java.lang.String getPart();

    /** Setter for property part.
     * @param part Value of property part.
     */
    public void setPart(java.lang.String part);


    /** Getter for property prime.
     * @return Value of property prime.
     */
    public java.lang.String getPrime();

    /** Setter for property prime.
     * @param prime Value of property prime.
     */
    public void setPrime(java.lang.String prime);



    /** Getter for property sc.
     * @return Value of property sc.
     */
    public java.lang.String getSc();

    /** Setter for property sc.
     * @param sc Value of property sc.
     */
    public void setSc(java.lang.String sc);



    /** Getter for property condition.
     * @return Value of property condition.
     */
    public java.lang.String getCondition();

    /** Setter for property condition.
     * @param condition Value of property condition.
     */
    public void setCondition(java.lang.String condition);


    /** Getter for property createdDatetime.
     * @return Value of property createdDatetime.
     */
    public org.jaffa.datatypes.DateTime getCreatedDatetime();

    /** Setter for property createdDatetime.
     * @param createdDatetime Value of property createdDatetime.
     */
    public void setCreatedDatetime(org.jaffa.datatypes.DateTime createdDatetime);



    /** Getter for property qty.
     * @return Value of property qty.
     */
    public java.lang.Long getQty();

    /** Setter for property qty.
     * @param qty Value of property qty.
     */
    public void setQty(java.lang.Long qty);



    /** Getter for property keyRef.
     * @return Value of property keyRef.
     */
    public java.lang.String getKeyRef();

    /** Setter for property keyRef.
     * @param keyRef Value of property keyRef.
     */
    public void setKeyRef(java.lang.String keyRef);



    /** Getter for property status1.
     * @return Value of property status1.
     */
    public java.lang.String getStatus1();

    /** Setter for property status1.
     * @param status1 Value of property status1.
     */
    public void setStatus1(java.lang.String status1);



    /** Getter for property status2.
     * @return Value of property status2.
     */
    public java.lang.String getStatus2();

    /** Setter for property status2.
     * @param status2 Value of property status2.
     */
    public void setStatus2(java.lang.String status2);



    /** Getter for property status3.
     * @return Value of property status3.
     */
    public java.lang.String getStatus3();

    /** Setter for property status3.
     * @param status3 Value of property status3.
     */
    public void setStatus3(java.lang.String status3);


    /** Getter for property price.
     * @return Value of property price.
     */
    public java.lang.Double getPrice();

    /** Setter for property price.
     * @param price Value of property price.
     */
    public void setPrice(java.lang.Double price);

}
