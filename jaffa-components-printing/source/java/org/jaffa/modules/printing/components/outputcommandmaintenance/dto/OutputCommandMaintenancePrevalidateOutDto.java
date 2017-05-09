// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.outputcommandmaintenance.dto;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the OutputCommandMaintenance prevalidations.
 */
public class OutputCommandMaintenancePrevalidateOutDto extends OutputCommandMaintenanceRetrieveOutDto {



    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<OutputCommandMaintenancePrevalidateOutDto>");
        buf.append("<outputCommandId>"); if (getOutputCommandId() != null) buf.append(getOutputCommandId()); buf.append("</outputCommandId>");
        buf.append("<sequenceNo>"); if (getSequenceNo() != null) buf.append(getSequenceNo()); buf.append("</sequenceNo>");
        buf.append("<osPattern>"); if (getOsPattern() != null) buf.append(getOsPattern()); buf.append("</osPattern>");
        buf.append("<commandLine>"); if (getCommandLine() != null) buf.append(getCommandLine()); buf.append("</commandLine>");
        buf.append("<outputType>"); if (getOutputType() != null) buf.append(getOutputType()); buf.append("</outputType>");

        buf.append("</OutputCommandMaintenancePrevalidateOutDto>");
        return buf.toString();
    }
    
    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
