/*
 * OrderDataBean.java
 *
 * Created on September 3, 2004, 7:17 AM
 */

package org.jaffa.modules.printing.services;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.printing.services.exceptions.DataNotFoundException;
import org.jaffa.modules.printing.services.exceptions.DataNotReadyException;
import org.jaffa.util.Voucher;



/**
 *
 * @author  PaulE
 */
public class InvoiceDataBean implements IDataBean {
    /** Logger reference */
    public final static Logger log = Logger.getLogger(InvoiceDataBean.class);

    public static final String VALID_ORDER_NO = "PO00196757-A"; // 20 Lines
    public static final String PENDING_ORDER_NO = "PO00196757-B";
    public static final String INVALID_ORDER_NO = "PO00196757-C";
    public static final String VALID_ORDER_NO2 = "PO00196757-A2"; // 2 lines
    public static final String VALID_LABEL = "LABEL1";
     
    /** Test field for make sure DocumentPrinted Listener gets called */
    public static int m_printed = 0;
    
    /**
     * Holds value of property vendorCode.
     */
    private String vendorCode;
    
    /**
     * Holds value of property orderNo.
     */
    private String orderNo;
    
    /**
     * Holds value of property vendorName.
     */
    private String vendorName;
    
    /**
     * Holds value of property vendorAddress.
     */
    private String vendorAddress;
    
    /**
     * Holds value of property shippingAddress.
     */
    private String shippingAddress;
    
    /**
     * Holds value of property orderDate.
     */
    private DateOnly orderDate;
    
    /**
     * Holds value of property needDate.
     */
    private DateOnly needDate;
    
    /**
     * Holds value of property orderTotal.
     */
    private double orderTotal;
    
    /**
     * Holds value of property item.
     */
    private List item;
    
    /**
     * Holds value of property remarks.
     */
    private String remarks;
    
    /** Creates a new instance of OrderDataBean */
    public InvoiceDataBean() {
    }
    
    
    public Object getDocumentRoot() {
        if(VALID_LABEL.equals(orderNo))
           return (InvoiceLineItem[])this.getItem().toArray(new InvoiceLineItem[0]);
        else
            return this;
    }
    
    public String getFormAlternateName() {
        return null;
    }
    
    /**
     * Getter for property vendorCode.
     * @return Value of property vendorCode.
     */
    public String getVendorCode() {
        return this.vendorCode;
    }
    
    /**
     * Setter for property vendorCode.
     * @param vendorCode New value of property vendorCode.
     */
    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }
    
    /**
     * Getter for property orderNo.
     * @return Value of property orderNo.
     */
    public String getOrderNo() {
        return this.orderNo;
    }
    
    /**
     * Setter for property orderNo.
     * @param orderNo New value of property orderNo.
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    /**
     * Getter for property vendorName.
     * @return Value of property vendorName.
     */
    public String getVendorName() {
        return this.vendorName;
    }
    
    /**
     * Setter for property vendorName.
     * @param vendorName New value of property vendorName.
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    /**
     * Getter for property vendorAddress.
     * @return Value of property vendorAddress.
     */
    public String getVendorAddress() {
        return this.vendorAddress;
    }
    
    /**
     * Setter for property vendorAddress.
     * @param vendorAddress New value of property vendorAddress.
     */
    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }
    
    /**
     * Getter for property shippingAddress.
     * @return Value of property shippingAddress.
     */
    public String getShippingAddress() {
        return this.shippingAddress;
    }
    
    /**
     * Setter for property shippingAddress.
     * @param shippingAddress New value of property shippingAddress.
     */
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    /**
     * Getter for property orderDate.
     * @return Value of property orderDate.
     */
    public DateOnly getOrderDate() {
        return this.orderDate;
    }
    
    /**
     * Setter for property orderDate.
     * @param orderDate New value of property orderDate.
     */
    public void setOrderDate(DateOnly orderDate) {
        this.orderDate = orderDate;
    }
    
    /**
     * Getter for property needDate.
     * @return Value of property needDate.
     */
    public DateOnly getNeedDate() {
        return this.needDate;
    }
    
    /**
     * Setter for property needDate.
     * @param needDate New value of property needDate.
     */
    public void setNeedDate(DateOnly needDate) {
        this.needDate = needDate;
    }
    
    /**
     * Getter for property orderTotal.
     * @return Value of property orderTotal.
     */
    public double getOrderTotal() {
        return this.orderTotal;
    }
    
    /**
     * Setter for property orderTotal.
     * @param orderTotal New value of property orderTotal.
     */
    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
    
    /** Getter for property item.
     * @return Value of property item.
     */
    public List getItem() {
        return this.item;
    }
    
    /** Setter for property item.
     * @param item New value of property item.
     */
    public void setItem(List item) {
        this.item = item;
    }
    
    
    private static int[] quantityList =
    {1,2,3,5,2,
     2,5,4,2,5,
     2,2,1,1,2000,
     1,6,5,5,5,
     1,5,5,1,1,
     5};
    private static double[] priceList =
    {1500,225,300,5000,250,
     2000,225,392.74,42322.22,322.5,
     223.2,2250,1500,150,2,
     1000,6000,500,500,500,
     1000,500,50000,1000,1000,
     500};
    private static String[] partList = 
    {"EC-NAT-6874-1","EC-NGC-687T","EC-AUT-687T","EC-AUT-ASSET-1","FLUKE-25",
     "OU812","E25","000011764","EC-CR36511","FLUKE-300",
     "CR36521","001082","P1000-CTNEW","VCR21-LG","HAVE-STARE",
     "P1000REPAIR","ECMDA-06MR-KIT","ECMDA-06MR-P1","ECMDA-06MR-P2","ECMDA-06MR-A1",
     "CAM-100","ECMDA          -06MR -TL   -1      -    -",
     "BB0000000000000-11111-22222-3333333-4444-555-66666",
     "UAE-0101010101010101010101010101",
     "12345678901234567890123456789012",
     "E25-TEST1"};

    public void populate() throws FrameworkException, ApplicationExceptions {
        if(PENDING_ORDER_NO.equals(orderNo))
            throw new ApplicationExceptions(new
                    DataNotReadyException(new String[] {"OrderNo",PENDING_ORDER_NO}) );
        
        int lines = 1;
        if(VALID_ORDER_NO.equals(orderNo))
            lines = 20;
        else if(VALID_ORDER_NO2.equals(orderNo) || VALID_LABEL.equals(orderNo))
            lines = 2;
        else throw new ApplicationExceptions(new
                DataNotFoundException(new String[] {"OrderNo",VALID_ORDER_NO}));
        
        
        this.orderTotal=0;
        //this.orderNo="PO0019657-A";
        //this.shippingAddress="A building 2building 3building 4building\nSome Road 2SomeRoad 3SomeRoad 4SomeRoad\nSome City 1SomeCity 2SomeCity 3SomeCity\nUtah 1Utah 2Utah 3Utah 4Utah 5Utah 6Utah 7Utah\nUT 98765 123412353tew5tgwe523451235125125";
        this.shippingAddress="A building\nSome Road\nSome City\nUtah\nUT 98765";
        this.vendorAddress="Acme Road\nAcmeville\nUtah\nUT 98765";
        this.vendorCode="AM001-2";
        this.vendorName="ACME Inc.";
        this.orderDate = DateOnly.addDay(new DateOnly(), -7);
        this.needDate = DateOnly.addDay(new DateOnly(), 2);
        this.remarks = "See additional pages";
        
        this.item = new ArrayList();
        for (int i=0; i < lines && i < partList.length; i++) {
            InvoiceLineItem it = new InvoiceLineItem();
            this.item.add(it);
            it.setItemNo(i+1);
            long qty = quantityList[i];
            double price = priceList[i];
            it.setQuantity(new Long(qty));
            it.setDescription(partList[i]);
            it.setUnitPrice(new Double(price));
            it.setExtendedPrice(new Double(price*qty));
            this.orderTotal+=price*qty;
            System.out.println(it.toString());
        }
    }
    
    /**
     * Getter for property remarks.
     * @return Value of property remarks.
     */
    public String getRemarks() {
        return this.remarks;
    }
    
    /**
     * Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /** No listerners available */
    public DocumentPrintedListener getDocumentPrintedListener() {
        return new DocumentPrintedListener() {
            public void documentPrinted(EventObject request) {
                m_printed++;
                log.debug("Document Printed :" + m_printed);
            }
        };
    }
    
}
