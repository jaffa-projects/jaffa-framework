/*
 * PurchaseOrderLine.java
 *
 * Created on September 3, 2004, 9:12 AM
 */

package org.jaffa.modules.printing.services;

/**
 *
 * @author  PaulE
 */
public class InvoiceLineItem {
    
    /**
     * Holds value of property itemNo.
     */
    private int itemNo;
    
    /**
     * Holds value of property quantity.
     */
    private Long quantity;
    
    /**
     * Holds value of property unitPrice.
     */
    private Double unitPrice;
    
    /**
     * Holds value of property extendedPrice.
     */
    private Double extendedPrice;
    
    /**
     * Holds value of property description.
     */
    private String description;
    
    /** Creates a new instance of PurchaseOrderLine */
    public InvoiceLineItem() {
    }
    
    /**
     * Getter for property itemNo.
     * @return Value of property itemNo.
     */
    public int getItemNo() {
        return itemNo;
    }
    
    /**
     * Setter for property itemNo.
     * @param itemNo New value of property itemNo.
     */
    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }
    
    /**
     * Getter for property quantity.
     * @return Value of property quantity.
     */
    public Long getQuantity() {
        return this.quantity;
    }
    
    /**
     * Setter for property quantity.
     * @param quantity New value of property quantity.
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Getter for property unitPrice.
     * @return Value of property unitPrice.
     */
    public Double getUnitPrice() {
        return this.unitPrice;
    }
    
    /**
     * Setter for property unitPrice.
     * @param unitPrice New value of property unitPrice.
     */
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    /**
     * Getter for property extendedPrice.
     * @return Value of property extendedPrice.
     */
    public Double getExtendedPrice() {
        return this.extendedPrice;
    }
    
    /**
     * Setter for property extendedPrice.
     * @param extendedPrice New value of property extendedPrice.
     */
    public void setExtendedPrice(Double extendedPrice) {
        this.extendedPrice = extendedPrice;
    }
    
    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "Item#:"+this.getItemNo() +
               ", Qty:"+this.getQuantity() +
               ", Decription:"+this.getDescription() +
               ", Price:"+this.getUnitPrice() +
               ", Cost:"+this.getExtendedPrice();
    }
    
}
