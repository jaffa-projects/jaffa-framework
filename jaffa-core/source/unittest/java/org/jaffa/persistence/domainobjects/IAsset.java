package org.jaffa.persistence.domainobjects;

import org.jaffa.datatypes.*;

/** This is an Interface with mere getters and setters.
 * The Persistence Engine will generate a dynamic proxy for this Interface.
 * The dynamic proxy will be used for persisting data to the database.
 */
public interface IAsset {

    // Field constants
    /** A constant to identity the AssetTk field.*/
    public static final String ASSET_TK = "AssetTk";
    /** A constant to identity the AssetId field.*/
    public static final String ASSET_ID = "AssetId";
    /** A constant to identity the Part field.*/
    public static final String PART = "Part";
    /** A constant to identity the Custodian field.*/
    public static final String CUSTODIAN = "Custodian";
    /** A constant to identity the Condition field.*/
    public static final String CONDITION = "Condition";
    /** A constant to identity the CreatedDatetime field.*/
    public static final String CREATED_DATETIME = "CreatedDatetime";
    /** A constant to identity the Qty field.*/
    public static final String QTY = "Qty";
    /** A constant to identity the KeyRef field.*/
    public static final String KEY_REF = "KeyRef";


    /** Getter for property assetTk.
     * @return Value of property assetTk.
     */
    public java.lang.Long getAssetTk();

    /** Setter for property assetTk.
     * @param assetTk Value of property assetTk.
     */
    public void setAssetTk(java.lang.Long assetTk);


    /** Getter for property assetId.
     * @return Value of property assetId.
     */
    public java.lang.String getAssetId();

    /** Setter for property assetId.
     * @param assetId Value of property assetId.
     */
    public void setAssetId(java.lang.String assetId);



    /** Getter for property part.
     * @return Value of property part.
     */
    public java.lang.String getPart();

    /** Setter for property part.
     * @param part Value of property part.
     */
    public void setPart(java.lang.String part);


    /** Getter for property custodian.
     * @return Value of property custodian.
     */
    public java.lang.String getCustodian();

    /** Setter for property custodian.
     * @param custodian Value of property custodian.
     */
    public void setCustodian(java.lang.String custodian);



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

}
