// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.test.modules.time.components.usertimeentryviewer.tx;

import java.util.*;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.Criteria;

import org.jaffa.applications.test.modules.time.components.usertimeentryviewer.IUserTimeEntryViewer;
import org.jaffa.applications.test.modules.time.components.usertimeentryviewer.dto.UserTimeEntryViewerInDto;
import org.jaffa.applications.test.modules.time.components.usertimeentryviewer.dto.UserTimeEntryViewerOutDto;
import org.jaffa.applications.test.modules.time.domain.UserTimeEntry;
import org.jaffa.applications.test.modules.time.domain.UserTimeEntryMeta;



// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Viewer for UserTimeEntry objects.
*/
public class UserTimeEntryViewerTx implements IUserTimeEntryViewer {

    private static Logger log = Logger.getLogger(UserTimeEntryViewerTx.class);
    
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
    /** Returns the details for UserTimeEntry.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */    
    public UserTimeEntryViewerOutDto read(UserTimeEntryViewerInDto input)
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
            UserTimeEntryViewerOutDto output = buildDto(uow, results);
            
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
    private Criteria buildCriteria(UserTimeEntryViewerInDto input, UOW uow) {
        Criteria criteria = new Criteria();
        criteria.setTable( UserTimeEntryMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        criteria.addCriteria(UserTimeEntryMeta.USER_NAME, input.getUserName());
        criteria.addCriteria(UserTimeEntryMeta.PROJECT_CODE, input.getProjectCode());
        criteria.addCriteria(UserTimeEntryMeta.TASK, input.getTask());
        criteria.addCriteria(UserTimeEntryMeta.PERIOD_START, input.getPeriodStart());
        criteria.addCriteria(UserTimeEntryMeta.PERIOD_END, input.getPeriodEnd());
        // .//GEN-END:_buildCriteria_2_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_2


        // .//GEN-LAST:_buildCriteria_2
        // .//GEN-BEGIN:_buildCriteria_3_be
        return criteria;
    }
    // .//GEN-END:_buildCriteria_3_be
    // .//GEN-BEGIN:_buildDto_1_be
    private UserTimeEntryViewerOutDto buildDto(UOW uow, Collection results)
    throws UOWException {
        UserTimeEntryViewerOutDto output = null;
        Iterator itr = results.iterator();
        if (itr.hasNext()) {
            // just return the details for the 1st record retrieved.
            UserTimeEntry userTimeEntry = (UserTimeEntry) itr.next();
            output = new UserTimeEntryViewerOutDto();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_UserName_1_be
            output.setUserName(userTimeEntry.getUserName());
            // .//GEN-END:_buildDto_UserName_1_be
            // .//GEN-BEGIN:_buildDto_ProjectCode_1_be
            output.setProjectCode(userTimeEntry.getProjectCode());
            // .//GEN-END:_buildDto_ProjectCode_1_be
            // .//GEN-BEGIN:_buildDto_Activity_1_be
            output.setActivity(userTimeEntry.getActivity());
            // .//GEN-END:_buildDto_Activity_1_be
            // .//GEN-BEGIN:_buildDto_Task_1_be
            output.setTask(userTimeEntry.getTask());
            // .//GEN-END:_buildDto_Task_1_be
            // .//GEN-BEGIN:_buildDto_PeriodStart_1_be
            output.setPeriodStart(userTimeEntry.getPeriodStart());
            // .//GEN-END:_buildDto_PeriodStart_1_be
            // .//GEN-BEGIN:_buildDto_PeriodEnd_1_be
            output.setPeriodEnd(userTimeEntry.getPeriodEnd());
            // .//GEN-END:_buildDto_PeriodEnd_1_be
            // .//GEN-BEGIN:_buildDto_2_be
            // Add related objects, if required
            addRelatedDtos(uow, output, userTimeEntry);
            // .//GEN-END:_buildDto_2_be
            // Add custom code to pass values to the dto //GEN-FIRST:_buildDto_2


            // .//GEN-LAST:_buildDto_2
            // .//GEN-BEGIN:_buildDto_3_be
        }
        return output;
    }
    // .//GEN-END:_buildDto_3_be
    // .//GEN-BEGIN:_addRelatedDtos_1_be
    private void addRelatedDtos(UOW uow, UserTimeEntryViewerOutDto output, UserTimeEntry userTimeEntry)
    throws UOWException {
        // .//GEN-END:_addRelatedDtos_1_be
        // .//GEN-BEGIN:_addRelatedDtos_2_be
    }
    // .//GEN-END:_addRelatedDtos_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
