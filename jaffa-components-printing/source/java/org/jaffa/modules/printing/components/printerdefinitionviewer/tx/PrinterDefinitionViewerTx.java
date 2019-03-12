// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.printerdefinitionviewer.tx;

import java.util.*;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.Criteria;

import org.jaffa.modules.printing.components.printerdefinitionviewer.IPrinterDefinitionViewer;
import org.jaffa.modules.printing.components.printerdefinitionviewer.dto.PrinterDefinitionViewerInDto;
import org.jaffa.modules.printing.components.printerdefinitionviewer.dto.PrinterDefinitionViewerOutDto;
import org.jaffa.modules.printing.domain.PrinterDefinition;
import org.jaffa.modules.printing.domain.PrinterDefinitionMeta;



// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Viewer for PrinterDefinition objects.
*/
public class PrinterDefinitionViewerTx implements IPrinterDefinitionViewer {

    private static Logger log = Logger.getLogger(PrinterDefinitionViewerTx.class);
    
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_destroy_1_be
    /**
     * This should be invoked, when done with the component.
     */
    public void destroy() {
        // .//GEN-END:_destroy_1_be
        // Add custom code //GEN-FIRST:_destroy_1


        // .//GEN-LAST:_destroy_1
        // .//GEN-BEGIN:_destroy_2_be
    }
    // .//GEN-END:_destroy_2_be
    // .//GEN-BEGIN:_read_1_be
    /** Returns the details for PrinterDefinition.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */    
    public PrinterDefinitionViewerOutDto read(PrinterDefinitionViewerInDto input)
    throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled()) {
                log.debug("Input: " + input);
            }
            
            // create the UOW
            uow = new UOW();
            
            // Build the Criteria Object
            Criteria criteria = buildCriteria(input, uow);
            // .//GEN-END:_read_1_be
            // Add custom code before the query //GEN-FIRST:_read_1


            // .//GEN-LAST:_read_1
            // .//GEN-BEGIN:_read_2_be
            // Execute The Query
            Collection results = uow.query(criteria);
            // .//GEN-END:_read_2_be
            // Add custom code after the query //GEN-FIRST:_read_2


            // .//GEN-LAST:_read_2
            // .//GEN-BEGIN:_read_3_be
            // Convert the domain objects into the outbound dto
            PrinterDefinitionViewerOutDto output = buildDto(uow, results);
            
            // Print Debug Information for the output
            if (log.isDebugEnabled()) {
                log.debug("Output: " + output);
            }
            
            return output;
            
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:_read_3_be
    // .//GEN-BEGIN:_buildCriteria_1_be
    private Criteria buildCriteria(PrinterDefinitionViewerInDto input, UOW uow) {
        Criteria criteria = new Criteria();
        criteria.setTable( PrinterDefinitionMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        criteria.addCriteria(PrinterDefinitionMeta.PRINTER_ID, input.getPrinterId());
        // .//GEN-END:_buildCriteria_2_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_2


        // .//GEN-LAST:_buildCriteria_2
        // .//GEN-BEGIN:_buildCriteria_3_be
        return criteria;
    }
    // .//GEN-END:_buildCriteria_3_be
    // .//GEN-BEGIN:_buildDto_1_be
    private PrinterDefinitionViewerOutDto buildDto(UOW uow, Collection results)
    throws UOWException {
        PrinterDefinitionViewerOutDto output = null;
        Iterator itr = results.iterator();
        if (itr.hasNext()) {
            // just return the details for the 1st record retrieved.
            PrinterDefinition printerDefinition = (PrinterDefinition) itr.next();
            output = new PrinterDefinitionViewerOutDto();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_PrinterId_1_be
            output.setPrinterId(printerDefinition.getPrinterId());
            // .//GEN-END:_buildDto_PrinterId_1_be
            // .//GEN-BEGIN:_buildDto_Description_1_be
            output.setDescription(printerDefinition.getDescription());
            // .//GEN-END:_buildDto_Description_1_be
            // .//GEN-BEGIN:_buildDto_SiteCode_1_be
            output.setSiteCode(printerDefinition.getSiteCode());
            // .//GEN-END:_buildDto_SiteCode_1_be
            // .//GEN-BEGIN:_buildDto_LocationCode_1_be
            output.setLocationCode(printerDefinition.getLocationCode());
            // .//GEN-END:_buildDto_LocationCode_1_be
            // .//GEN-BEGIN:_buildDto_Remote_1_be
            output.setRemote(printerDefinition.getRemote());
            // .//GEN-END:_buildDto_Remote_1_be
            // .//GEN-BEGIN:_buildDto_RealPrinterName_1_be
            output.setRealPrinterName(printerDefinition.getRealPrinterName());
            // .//GEN-END:_buildDto_RealPrinterName_1_be
            // .//GEN-BEGIN:_buildDto_PrinterOption1_1_be
            output.setPrinterOption1(printerDefinition.getPrinterOption1());
            // .//GEN-END:_buildDto_PrinterOption1_1_be
            // .//GEN-BEGIN:_buildDto_PrinterOption2_1_be
            output.setPrinterOption2(printerDefinition.getPrinterOption2());
            // .//GEN-END:_buildDto_PrinterOption2_1_be
            // .//GEN-BEGIN:_buildDto_OutputType_1_be
            output.setOutputType(printerDefinition.getOutputType());
            // .//GEN-END:_buildDto_OutputType_1_be
            // .//GEN-BEGIN:_buildDto_ScaleToPageSize_1_be
            output.setScaleToPageSize(printerDefinition.getScaleToPageSize());
            // .//GEN-END:_buildDto_ScaleToPageSize_1_be
            // .//GEN-BEGIN:_buildDto_2_be
            // Add related objects, if required
            addRelatedDtos(uow, output, printerDefinition);
            // .//GEN-END:_buildDto_2_be
            // Add custom code to pass values to the dto //GEN-FIRST:_buildDto_2


            // .//GEN-LAST:_buildDto_2
            // .//GEN-BEGIN:_buildDto_3_be
        }
        return output;
    }
    // .//GEN-END:_buildDto_3_be
    // .//GEN-BEGIN:_addRelatedDtos_1_be
    private void addRelatedDtos(UOW uow, PrinterDefinitionViewerOutDto output, PrinterDefinition printerDefinition)
    throws UOWException {
        // .//GEN-END:_addRelatedDtos_1_be
        // .//GEN-BEGIN:_addRelatedDtos_2_be
    }
    // .//GEN-END:_addRelatedDtos_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
