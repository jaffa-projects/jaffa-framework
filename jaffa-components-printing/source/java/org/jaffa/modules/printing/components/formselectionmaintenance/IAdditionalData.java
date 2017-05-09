/*
 * IAdditionalData.java
 *
 * Created on May 23, 2006, 3:43 PM
 *
 * This is the interface which every additional data component called from FormSelection
 * should implement. 
 * 
 */

package org.jaffa.modules.printing.components.formselectionmaintenance;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;



/**
 *
 * @author YamitM
 */
public interface IAdditionalData {
    public void validate() throws ApplicationExceptions, FrameworkException;
}
