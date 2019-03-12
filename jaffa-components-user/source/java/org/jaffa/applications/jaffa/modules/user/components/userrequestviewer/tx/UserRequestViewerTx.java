// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.tx;

import java.util.*;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.Criteria;

import org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.IUserRequestViewer;
import org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.dto.UserRequestViewerInDto;
import org.jaffa.applications.jaffa.modules.user.components.userrequestviewer.dto.UserRequestViewerOutDto;
import org.jaffa.applications.jaffa.modules.user.domain.UserRequest;
import org.jaffa.applications.jaffa.modules.user.domain.UserRequestMeta;



// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Viewer for UserRequest objects.
*/
public class UserRequestViewerTx implements IUserRequestViewer {

    private static Logger log = Logger.getLogger(UserRequestViewerTx.class);
    
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
    /** Returns the details for UserRequest.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */    
    public UserRequestViewerOutDto read(UserRequestViewerInDto input)
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
            UserRequestViewerOutDto output = buildDto(uow, results);
            
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
    private Criteria buildCriteria(UserRequestViewerInDto input, UOW uow) {
        Criteria criteria = new Criteria();
        criteria.setTable( UserRequestMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        criteria.addCriteria(UserRequestMeta.REQUEST_ID, input.getRequestId());
        // .//GEN-END:_buildCriteria_2_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_2


        // .//GEN-LAST:_buildCriteria_2
        // .//GEN-BEGIN:_buildCriteria_3_be
        return criteria;
    }
    // .//GEN-END:_buildCriteria_3_be
    // .//GEN-BEGIN:_buildDto_1_be
    private UserRequestViewerOutDto buildDto(UOW uow, Collection results)
    throws UOWException {
        UserRequestViewerOutDto output = null;
        Iterator itr = results.iterator();
        if (itr.hasNext()) {
            // just return the details for the 1st record retrieved.
            UserRequest userRequest = (UserRequest) itr.next();
            output = new UserRequestViewerOutDto();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_RequestId_1_be
            output.setRequestId(userRequest.getRequestId());
            // .//GEN-END:_buildDto_RequestId_1_be
            // .//GEN-BEGIN:_buildDto_UserName_1_be
            output.setUserName(userRequest.getUserName());
            // .//GEN-END:_buildDto_UserName_1_be
            // .//GEN-BEGIN:_buildDto_FirstName_1_be
            output.setFirstName(userRequest.getFirstName());
            // .//GEN-END:_buildDto_FirstName_1_be
            // .//GEN-BEGIN:_buildDto_LastName_1_be
            output.setLastName(userRequest.getLastName());
            // .//GEN-END:_buildDto_LastName_1_be
            // .//GEN-BEGIN:_buildDto_Password_1_be
            output.setPassword(userRequest.getPassword());
            // .//GEN-END:_buildDto_Password_1_be
            // .//GEN-BEGIN:_buildDto_EMailAddress_1_be
            output.setEMailAddress(userRequest.getEMailAddress());
            // .//GEN-END:_buildDto_EMailAddress_1_be
            // .//GEN-BEGIN:_buildDto_SecurityQuestion_1_be
            output.setSecurityQuestion(userRequest.getSecurityQuestion());
            // .//GEN-END:_buildDto_SecurityQuestion_1_be
            // .//GEN-BEGIN:_buildDto_SecurityAnswer_1_be
            output.setSecurityAnswer(userRequest.getSecurityAnswer());
            // .//GEN-END:_buildDto_SecurityAnswer_1_be
            // .//GEN-BEGIN:_buildDto_Remarks_1_be
            output.setRemarks(userRequest.getRemarks());
            // .//GEN-END:_buildDto_Remarks_1_be
            // .//GEN-BEGIN:_buildDto_CreatedOn_1_be
            output.setCreatedOn(userRequest.getCreatedOn());
            // .//GEN-END:_buildDto_CreatedOn_1_be
            // .//GEN-BEGIN:_buildDto_ProcessedDatetime_1_be
            output.setProcessedDatetime(userRequest.getProcessedDatetime());
            // .//GEN-END:_buildDto_ProcessedDatetime_1_be
            // .//GEN-BEGIN:_buildDto_ProcessedUserId_1_be
            output.setProcessedUserId(userRequest.getProcessedUserId());
            // .//GEN-END:_buildDto_ProcessedUserId_1_be
            // .//GEN-BEGIN:_buildDto_Status_1_be
            output.setStatus(userRequest.getStatus());
            // .//GEN-END:_buildDto_Status_1_be
            // .//GEN-BEGIN:_buildDto_2_be
            // Add related objects, if required
            addRelatedDtos(uow, output, userRequest);
            // .//GEN-END:_buildDto_2_be
            // Add custom code to pass values to the dto //GEN-FIRST:_buildDto_2


            // .//GEN-LAST:_buildDto_2
            // .//GEN-BEGIN:_buildDto_3_be
        }
        return output;
    }
    // .//GEN-END:_buildDto_3_be
    // .//GEN-BEGIN:_addRelatedDtos_1_be
    private void addRelatedDtos(UOW uow, UserRequestViewerOutDto output, UserRequest userRequest)
    throws UOWException {
        // .//GEN-END:_addRelatedDtos_1_be
        // .//GEN-BEGIN:_addRelatedDtos_2_be
    }
    // .//GEN-END:_addRelatedDtos_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
