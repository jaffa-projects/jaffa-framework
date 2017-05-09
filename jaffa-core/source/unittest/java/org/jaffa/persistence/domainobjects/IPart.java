package org.jaffa.persistence.domainobjects;

import org.jaffa.datatypes.*;

/** This is an Interface with mere getters and setters.
 * The Persistence Engine will generate a dynamic proxy for this Interface.
 * The dynamic proxy will be used for persisting data to the database.
 */
public interface IPart {

    // Field constants
    /** A constant to identity the Part field.*/
    public static final String PART = "Part";
    /** A constant to identity the Noun field.*/
    public static final String NOUN = "Noun";
    /** A constant to identity the CategoryInstrument field.*/
    public static final String CATEGORY_INSTRUMENT = "CategoryInstrument";


    /** Getter for property part.
     * @return Value of property part.
     */
    public java.lang.String getPart();

    /** Setter for property part.
     * @param part Value of property part.
     */
    public void setPart(java.lang.String part);

    /** Getter for property noun.
     * @return Value of property noun.
     */
    public java.lang.String getNoun();

    /** Setter for property noun.
     * @param noun Value of property noun.
     */
    public void setNoun(java.lang.String noun);



    /** Getter for property categoryInstrument.
     * @return Value of property categoryInstrument.
     */
    public java.lang.String getCategoryInstrument();

    /** Setter for property categoryInstrument.
     * @param categoryInstrument Value of property categoryInstrument.
     */
    public void setCategoryInstrument(java.lang.String categoryInstrument);

}
