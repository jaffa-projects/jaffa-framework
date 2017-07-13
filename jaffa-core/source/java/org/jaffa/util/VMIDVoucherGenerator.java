package org.jaffa.util;


import java.rmi.dgc.VMID;
import org.jaffa.components.voucher.AbstractVoucherGenerator;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

/** This implementation of the IVoucherGenerator interface returns a VMID.
 */
public class VMIDVoucherGenerator extends AbstractVoucherGenerator {
    
    /** Returns the String representation of a VMID.
     *@throws FrameworkException if any framework error occurs.
     *@throws ApplicationExceptions if any application error occurs.
     *@return the String representation of a VMID.
     */
    public String generate() throws FrameworkException, ApplicationExceptions {
        return new VMID().toString();
    }
    
}