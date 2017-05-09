// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.formdefinitionviewer;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.printing.components.formdefinitionviewer.dto.FormDefinitionViewerOutDto;
import org.jaffa.modules.printing.components.formdefinitionviewer.dto.FormDefinitionViewerInDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Interface for FormDefinitionViewer components.
 */
public interface IFormDefinitionViewer {

    /** Returns the details for FormDefinition.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */    
    public FormDefinitionViewerOutDto read(FormDefinitionViewerInDto input) throws FrameworkException, ApplicationExceptions;
    
    /**
     * This should be invoked, when done with the component.
     */
    public void destroy();

    // .//GEN-END:_2_be
    // All the custom code goes here//GEN-FIRST:_custom


    public byte[] loadFormTemplate(FormDefinitionViewerInDto input) throws FrameworkException,ApplicationExceptions;



    // .//GEN-LAST:_custom
}
