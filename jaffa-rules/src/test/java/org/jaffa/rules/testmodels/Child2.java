package org.jaffa.rules.testmodels;

import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;

/**
 * @author GautamJ
 */
public class Child2 extends Parent {
    
    /* NOTES:
     * 1- field1 is not overridden
     * 2- field2 is overridden
     * 3- field3 is overridden, but it also invokes the corresponding 'super' method
     */

    private UOW uow = null;
    private String field2 = null;
    private String field4 = null;

    /**
     * Getter for property field2.
     *
     * @return Value of property field2.
     */
    public String getField2() {
        return field2;
    }

    /**
     * Setter for property field2.
     *
     * @param field2 New value of property field2.
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }

    /**
     * Setter for property field3.
     *
     * @param field3 New value of property field3.
     */
    public void setField3(String field3) {
        super.setField3(field3);
    }

    /**
     * Getter for property field4.
     *
     * @return Value of property field4.
     */
    public String getField4() {
        return field4;
    }

    /**
     * Setter for property field4.
     *
     * @param field4 New value of property field4.
     */
    public void setField4(String field4) {
        this.field4 = field4;
    }


    /**
     * This method has been added to support rules that require UOW.
     */
    public UOW getUOW() throws UOWException {
        if (uow == null)
            uow = new UOW();
        return uow;
    }
}
