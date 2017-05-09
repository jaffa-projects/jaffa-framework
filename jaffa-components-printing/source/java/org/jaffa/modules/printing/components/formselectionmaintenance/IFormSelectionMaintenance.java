// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.formselectionmaintenance;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceInDto;
import org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import org.jaffa.modules.printing.services.FormPrintRequest;


// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Interface for FormSelectionMaintenance components.
 */
public interface IFormSelectionMaintenance {

    /** Searches for FormUsage objects.
     * @param input The criteria based on which the search will be performed.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */    
    public FormSelectionMaintenanceOutDto find(FormSelectionMaintenanceInDto input) throws FrameworkException, ApplicationExceptions;
    
    /**
     * This should be invoked, when done with the component.
     */
    public void destroy();

    // .//GEN-END:_2_be
    // All the custom code goes here//GEN-FIRST:_custom
    //just like find() method. Create to add additional code.
    public FormSelectionMaintenanceOutDto findOutDto(FormSelectionMaintenanceInDto input) throws FrameworkException, ApplicationExceptions;
    /**
     * This method invokes creates a FormPrintRequest object and passes
     * it to the FormPrinting engine for processing.
     */
    public void dispatchPrintRequest(FormPrintRequest formPrintRequest) throws FrameworkException, ApplicationExceptions;
    // .//GEN-LAST:_custom
}
