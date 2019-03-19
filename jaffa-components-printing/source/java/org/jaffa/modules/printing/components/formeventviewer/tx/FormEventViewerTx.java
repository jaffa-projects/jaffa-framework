// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.formeventviewer.tx;

import java.util.*;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.Criteria;

import org.jaffa.modules.printing.components.formeventviewer.IFormEventViewer;
import org.jaffa.modules.printing.components.formeventviewer.dto.FormEventViewerInDto;
import org.jaffa.modules.printing.components.formeventviewer.dto.FormEventViewerOutDto;
import org.jaffa.modules.printing.domain.FormEvent;
import org.jaffa.modules.printing.domain.FormEventMeta;


import org.jaffa.modules.printing.components.formeventviewer.dto.FormUsageDto;
import org.jaffa.modules.printing.domain.FormUsage;
import org.jaffa.modules.printing.domain.FormUsageMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Viewer for FormEvent objects.
*/
public class FormEventViewerTx implements IFormEventViewer {

    private static Logger log = Logger.getLogger(FormEventViewerTx.class);
    
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
    /** Returns the details for FormEvent.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */    
    public FormEventViewerOutDto read(FormEventViewerInDto input)
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
            FormEventViewerOutDto output = buildDto(uow, results);
            
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
    private Criteria buildCriteria(FormEventViewerInDto input, UOW uow) {
        Criteria criteria = new Criteria();
        criteria.setTable( FormEventMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        criteria.addCriteria(FormEventMeta.EVENT_NAME, input.getEventName());
        // .//GEN-END:_buildCriteria_2_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_2


        // .//GEN-LAST:_buildCriteria_2
        // .//GEN-BEGIN:_buildCriteria_3_be
        return criteria;
    }
    // .//GEN-END:_buildCriteria_3_be
    // .//GEN-BEGIN:_buildDto_1_be
    private FormEventViewerOutDto buildDto(UOW uow, Collection results)
    throws UOWException {
        FormEventViewerOutDto output = null;
        Iterator itr = results.iterator();
        if (itr.hasNext()) {
            // just return the details for the 1st record retrieved.
            FormEvent formEvent = (FormEvent) itr.next();
            output = new FormEventViewerOutDto();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_EventName_1_be
            output.setEventName(formEvent.getEventName());
            // .//GEN-END:_buildDto_EventName_1_be
            // .//GEN-BEGIN:_buildDto_Description_1_be
            output.setDescription(formEvent.getDescription());
            // .//GEN-END:_buildDto_Description_1_be
            // .//GEN-BEGIN:_buildDto_CreatedOn_1_be
            output.setCreatedOn(formEvent.getCreatedOn());
            // .//GEN-END:_buildDto_CreatedOn_1_be
            // .//GEN-BEGIN:_buildDto_CreatedBy_1_be
            output.setCreatedBy(formEvent.getCreatedBy());
            // .//GEN-END:_buildDto_CreatedBy_1_be
            // .//GEN-BEGIN:_buildDto_LastChangedOn_1_be
            output.setLastChangedOn(formEvent.getLastChangedOn());
            // .//GEN-END:_buildDto_LastChangedOn_1_be
            // .//GEN-BEGIN:_buildDto_LastChangedBy_1_be
            output.setLastChangedBy(formEvent.getLastChangedBy());
            // .//GEN-END:_buildDto_LastChangedBy_1_be
            // .//GEN-BEGIN:_buildDto_2_be
            // Add related objects, if required
            addRelatedDtos(uow, output, formEvent);
            // .//GEN-END:_buildDto_2_be
            // Add custom code to pass values to the dto //GEN-FIRST:_buildDto_2


            // .//GEN-LAST:_buildDto_2
            // .//GEN-BEGIN:_buildDto_3_be
        }
        return output;
    }
    // .//GEN-END:_buildDto_3_be
    // .//GEN-BEGIN:_addRelatedDtos_1_be
    private void addRelatedDtos(UOW uow, FormEventViewerOutDto output, FormEvent formEvent)
    throws UOWException {
        // .//GEN-END:_addRelatedDtos_1_be
        // .//GEN-BEGIN:_addRelatedDtos_FormUsage_1_be
        if (formEvent.getEventName() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(FormUsageMeta.getName());
            criteria.addCriteria(FormUsageMeta.EVENT_NAME, formEvent.getEventName());
            criteria.addOrderBy("FormName", Criteria.ORDER_BY_ASC);
            // .//GEN-END:_addRelatedDtos_FormUsage_1_be
            // Add custom code to set the criteria before the query //GEN-FIRST:_addRelatedDtos_FormUsage_1


            // .//GEN-LAST:_addRelatedDtos_FormUsage_1
            // .//GEN-BEGIN:_addRelatedDtos_FormUsage_2_be
            Iterator itr = uow.query(criteria).iterator();
            while (itr.hasNext()) {
                FormUsage formUsage = (FormUsage) itr.next();
                FormUsageDto dto = new FormUsageDto();
                // .//GEN-END:_addRelatedDtos_FormUsage_2_be
                // Add custom code before all the setters //GEN-FIRST:_addRelatedDtos_FormUsage_2


                // .//GEN-LAST:_addRelatedDtos_FormUsage_2
                // .//GEN-BEGIN:_addRelatedDtos_FormUsage_FormName_1_be
                dto.setFormName(formUsage.getFormName());
                // .//GEN-END:_addRelatedDtos_FormUsage_FormName_1_be
                // .//GEN-BEGIN:_addRelatedDtos_FormUsage_EventName_1_be
                dto.setEventName(formUsage.getEventName());
                // .//GEN-END:_addRelatedDtos_FormUsage_EventName_1_be
                // .//GEN-BEGIN:_addRelatedDtos_FormUsage_FormAlternate_1_be
                dto.setFormAlternate(formUsage.getFormAlternate());
                // .//GEN-END:_addRelatedDtos_FormUsage_FormAlternate_1_be
                // .//GEN-BEGIN:_addRelatedDtos_FormUsage_Copies_1_be
                dto.setCopies(formUsage.getCopies());
                // .//GEN-END:_addRelatedDtos_FormUsage_Copies_1_be
                // .//GEN-BEGIN:_addRelatedDtos_FormUsage_CreatedOn_1_be
                dto.setCreatedOn(formUsage.getCreatedOn());
                // .//GEN-END:_addRelatedDtos_FormUsage_CreatedOn_1_be
                // .//GEN-BEGIN:_addRelatedDtos_FormUsage_CreatedBy_1_be
                dto.setCreatedBy(formUsage.getCreatedBy());
                // .//GEN-END:_addRelatedDtos_FormUsage_CreatedBy_1_be
                // .//GEN-BEGIN:_addRelatedDtos_FormUsage_LastChangedOn_1_be
                dto.setLastChangedOn(formUsage.getLastChangedOn());
                // .//GEN-END:_addRelatedDtos_FormUsage_LastChangedOn_1_be
                // .//GEN-BEGIN:_addRelatedDtos_FormUsage_LastChangedBy_1_be
                dto.setLastChangedBy(formUsage.getLastChangedBy());
                // .//GEN-END:_addRelatedDtos_FormUsage_LastChangedBy_1_be
                // Add custom code to pass values to the dto //GEN-FIRST:_addRelatedDtos_FormUsage_3


                // .//GEN-LAST:_addRelatedDtos_FormUsage_3
                // .//GEN-BEGIN:_addRelatedDtos_FormUsage_3_be
                output.addFormUsage(dto);
            }
        }
        // .//GEN-END:_addRelatedDtos_FormUsage_3_be
        // .//GEN-BEGIN:_addRelatedDtos_2_be
    }
    // .//GEN-END:_addRelatedDtos_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
