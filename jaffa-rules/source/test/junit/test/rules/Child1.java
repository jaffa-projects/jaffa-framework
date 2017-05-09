package test.rules;

/**
 *
 * @author  GautamJ
 */
public class Child1 extends Parent {
    
    /* NOTES:
     * 1- field1 is not overridden
     * 2- field2 is overridden
     * 3- field3 is overridden, but it also invokes the corresponding 'super' method
     */
    
    private String field2 = null;
    
    /**
     * Getter for property field2.
     * @return Value of property field2.
     */
    public String getField2() {
        return field2;
    }
    
    /**
     * Setter for property field2.
     * @param field2 New value of property field2.
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }
    
    /**
     * Setter for property field3.
     * @param field3 New value of property field3.
     */
    public void setField3(String field3) {
        super.setField3(field3);
    }
    
}
