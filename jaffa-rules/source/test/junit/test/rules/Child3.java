package test.rules;

/** This class is used to test the chained-inheritance
 *
 * @author  GautamJ
 */
public class Child3 {
    
    /**
     * Holds value of property field1.
     */
    private String field1;
    
    /**
     * Holds value of property field2.
     */
    private String field2;
    
    /**
     * Holds value of property field3.
     */
    private String field3;
    
    /**
     * Holds value of property field4.
     */
    private String field4;
    
    /**
     * Getter for property field1.
     * @return Value of property field1.
     */
    public String getField1() {
        return this.field1;
    }
    
    /**
     * Setter for property field1.
     * @param field1 New value of property field1.
     */
    public void setField1(String field1) {
        this.field1 = field1;
    }
    
    /**
     * Getter for property field2.
     * @return Value of property field2.
     */
    public String getField2() {
        return this.field2;
    }
    
    /**
     * Setter for property field2.
     * @param field2 New value of property field2.
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }
    
    /**
     * Getter for property field3.
     * @return Value of property field3.
     */
    public String getField3() {
        return this.field3;
    }
    
    /**
     * Setter for property field3.
     * @param field3 New value of property field3.
     */
    public void setField3(String field3) {
        this.field3 = field3;
    }
    
    /**
     * Getter for property field4.
     * @return Value of property field4.
     */
    public String getField4() {
        return field4;
    }
    
    /**
     * Setter for property field4.
     * @param field4 New value of property field4.
     */
    public void setField4(String field4) {
        this.field4 = field4;
    }
    
    /** This method is used for binding various validation interceptors. */
    public void validate() {
    }
}
