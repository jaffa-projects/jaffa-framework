// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.printeroutputtypeviewer.tx;

import java.util.*;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.Criteria;

import org.jaffa.modules.printing.components.printeroutputtypeviewer.IPrinterOutputTypeViewer;
import org.jaffa.modules.printing.components.printeroutputtypeviewer.dto.PrinterOutputTypeViewerInDto;
import org.jaffa.modules.printing.components.printeroutputtypeviewer.dto.PrinterOutputTypeViewerOutDto;
import org.jaffa.modules.printing.domain.PrinterOutputType;
import org.jaffa.modules.printing.domain.PrinterOutputTypeMeta;


import org.jaffa.modules.printing.components.printeroutputtypeviewer.dto.OutputCommandDto;
import org.jaffa.modules.printing.domain.OutputCommand;
import org.jaffa.modules.printing.domain.OutputCommandMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Viewer for PrinterOutputType objects.
*/
public class PrinterOutputTypeViewerTx implements IPrinterOutputTypeViewer {

    private static Logger log = Logger.getLogger(PrinterOutputTypeViewerTx.class);
    
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
    /** Returns the details for PrinterOutputType.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */    
    public PrinterOutputTypeViewerOutDto read(PrinterOutputTypeViewerInDto input)
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
            PrinterOutputTypeViewerOutDto output = buildDto(uow, results);
            
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
    private Criteria buildCriteria(PrinterOutputTypeViewerInDto input, UOW uow) {
        Criteria criteria = new Criteria();
        criteria.setTable( PrinterOutputTypeMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        criteria.addCriteria(PrinterOutputTypeMeta.OUTPUT_TYPE, input.getOutputType());
        // .//GEN-END:_buildCriteria_2_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_2


        // .//GEN-LAST:_buildCriteria_2
        // .//GEN-BEGIN:_buildCriteria_3_be
        return criteria;
    }
    // .//GEN-END:_buildCriteria_3_be
    // .//GEN-BEGIN:_buildDto_1_be
    private PrinterOutputTypeViewerOutDto buildDto(UOW uow, Collection results)
    throws UOWException {
        PrinterOutputTypeViewerOutDto output = null;
        Iterator itr = results.iterator();
        if (itr.hasNext()) {
            // just return the details for the 1st record retrieved.
            PrinterOutputType printerOutputType = (PrinterOutputType) itr.next();
            output = new PrinterOutputTypeViewerOutDto();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_OutputType_1_be
            output.setOutputType(printerOutputType.getOutputType());
            // .//GEN-END:_buildDto_OutputType_1_be
            // .//GEN-BEGIN:_buildDto_Description_1_be
            output.setDescription(printerOutputType.getDescription());
            // .//GEN-END:_buildDto_Description_1_be
            // .//GEN-BEGIN:_buildDto_DirectPrinting_1_be
            output.setDirectPrinting(printerOutputType.getDirectPrinting());
            // .//GEN-END:_buildDto_DirectPrinting_1_be
            // .//GEN-BEGIN:_buildDto_CreatedOn_1_be
            output.setCreatedOn(printerOutputType.getCreatedOn());
            // .//GEN-END:_buildDto_CreatedOn_1_be
            // .//GEN-BEGIN:_buildDto_CreatedBy_1_be
            output.setCreatedBy(printerOutputType.getCreatedBy());
            // .//GEN-END:_buildDto_CreatedBy_1_be
            // .//GEN-BEGIN:_buildDto_LastChangedOn_1_be
            output.setLastChangedOn(printerOutputType.getLastChangedOn());
            // .//GEN-END:_buildDto_LastChangedOn_1_be
            // .//GEN-BEGIN:_buildDto_LastChangedBy_1_be
            output.setLastChangedBy(printerOutputType.getLastChangedBy());
            // .//GEN-END:_buildDto_LastChangedBy_1_be
            // .//GEN-BEGIN:_buildDto_2_be
            // Add related objects, if required
            addRelatedDtos(uow, output, printerOutputType);
            // .//GEN-END:_buildDto_2_be
            // Add custom code to pass values to the dto //GEN-FIRST:_buildDto_2


            // .//GEN-LAST:_buildDto_2
            // .//GEN-BEGIN:_buildDto_3_be
        }
        return output;
    }
    // .//GEN-END:_buildDto_3_be
    // .//GEN-BEGIN:_addRelatedDtos_1_be
    private void addRelatedDtos(UOW uow, PrinterOutputTypeViewerOutDto output, PrinterOutputType printerOutputType)
    throws UOWException {
        // .//GEN-END:_addRelatedDtos_1_be
        // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_1_be
        if (printerOutputType.getOutputType() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(OutputCommandMeta.getName());
            criteria.addCriteria(OutputCommandMeta.OUTPUT_TYPE, printerOutputType.getOutputType());
            criteria.addOrderBy("OutputCommandId", Criteria.ORDER_BY_ASC);
            // .//GEN-END:_addRelatedDtos_OutputCommand_1_be
            // Add custom code to set the criteria before the query //GEN-FIRST:_addRelatedDtos_OutputCommand_1


            // .//GEN-LAST:_addRelatedDtos_OutputCommand_1
            // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_2_be
            Iterator itr = uow.query(criteria).iterator();
            while (itr.hasNext()) {
                OutputCommand outputCommand = (OutputCommand) itr.next();
                OutputCommandDto dto = new OutputCommandDto();
                // .//GEN-END:_addRelatedDtos_OutputCommand_2_be
                // Add custom code before all the setters //GEN-FIRST:_addRelatedDtos_OutputCommand_2


                // .//GEN-LAST:_addRelatedDtos_OutputCommand_2
                // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_OutputCommandId_1_be
                dto.setOutputCommandId(outputCommand.getOutputCommandId());
                // .//GEN-END:_addRelatedDtos_OutputCommand_OutputCommandId_1_be
                // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_OutputType_1_be
                dto.setOutputType(outputCommand.getOutputType());
                // .//GEN-END:_addRelatedDtos_OutputCommand_OutputType_1_be
                // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_SequenceNo_1_be
                dto.setSequenceNo(outputCommand.getSequenceNo());
                // .//GEN-END:_addRelatedDtos_OutputCommand_SequenceNo_1_be
                // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_OsPattern_1_be
                dto.setOsPattern(outputCommand.getOsPattern());
                // .//GEN-END:_addRelatedDtos_OutputCommand_OsPattern_1_be
                // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_CommandLine_1_be
                dto.setCommandLine(outputCommand.getCommandLine());
                // .//GEN-END:_addRelatedDtos_OutputCommand_CommandLine_1_be
                // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_CreatedOn_1_be
                dto.setCreatedOn(outputCommand.getCreatedOn());
                // .//GEN-END:_addRelatedDtos_OutputCommand_CreatedOn_1_be
                // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_CreatedBy_1_be
                dto.setCreatedBy(outputCommand.getCreatedBy());
                // .//GEN-END:_addRelatedDtos_OutputCommand_CreatedBy_1_be
                // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_LastChangedOn_1_be
                dto.setLastChangedOn(outputCommand.getLastChangedOn());
                // .//GEN-END:_addRelatedDtos_OutputCommand_LastChangedOn_1_be
                // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_LastChangedBy_1_be
                dto.setLastChangedBy(outputCommand.getLastChangedBy());
                // .//GEN-END:_addRelatedDtos_OutputCommand_LastChangedBy_1_be
                // Add custom code to pass values to the dto //GEN-FIRST:_addRelatedDtos_OutputCommand_3


                // .//GEN-LAST:_addRelatedDtos_OutputCommand_3
                // .//GEN-BEGIN:_addRelatedDtos_OutputCommand_3_be
                output.addOutputCommand(dto);
            }
        }
        // .//GEN-END:_addRelatedDtos_OutputCommand_3_be
        // .//GEN-BEGIN:_addRelatedDtos_2_be
    }
    // .//GEN-END:_addRelatedDtos_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
