package org.jaffa.persistence.domainobjects;

import org.jaffa.datatypes.*;

/** This is an Interface with mere getters and setters.
 * The Persistence Engine will generate a dynamic proxy for this Interface.
 * The dynamic proxy will be used for persisting data to the database.
 */
public interface ICategoryOfInstrument {

    // Field constants
    /** A constant to identity the CategoryInstrument field.*/
    public static final String CATEGORY_INSTRUMENT = "CategoryInstrument";
    /** A constant to identity the Description field.*/
    public static final String DESCRIPTION = "Description";
    /** A constant to identity the SupportEquip field.*/
    public static final String SUPPORT_EQUIP = "SupportEquip";
    /** A constant to identity the CalculateMtbf field.*/
    public static final String CALCULATE_MTBF = "CalculateMtbf";


    /** Getter for property categoryInstrument.
     * @return Value of property categoryInstrument.
     */
    public java.lang.String getCategoryInstrument();

    /** Setter for property categoryInstrument.
     * @param categoryInstrument Value of property categoryInstrument.
     */
    public void setCategoryInstrument(java.lang.String categoryInstrument);


    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription();

    /** Setter for property description.
     * @param description Value of property description.
     */
    public void setDescription(java.lang.String description);



    /** Getter for property supportEquip.
     * @return Value of property supportEquip.
     */
    public java.lang.Boolean getSupportEquip();

    /** Setter for property supportEquip.
     * @param supportEquip Value of property supportEquip.
     */
    public void setSupportEquip(java.lang.Boolean supportEquip);



    /** Getter for property calculateMtbf.
     * @return Value of property calculateMtbf.
     */
    public java.lang.Boolean getCalculateMtbf();

    /** Setter for property calculateMtbf.
     * @param calculateMtbf Value of property calculateMtbf.
     */
    public void setCalculateMtbf(java.lang.Boolean calculateMtbf);

}
