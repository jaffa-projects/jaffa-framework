// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.jaffa.modules.admin.components.userviewer.tx;

import java.util.*;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.Criteria;

import org.jaffa.applications.jaffa.modules.admin.components.userviewer.IUserViewer;
import org.jaffa.applications.jaffa.modules.admin.components.userviewer.dto.UserViewerInDto;
import org.jaffa.applications.jaffa.modules.admin.components.userviewer.dto.UserViewerOutDto;
import org.jaffa.applications.jaffa.modules.admin.domain.User;
import org.jaffa.applications.jaffa.modules.admin.domain.UserMeta;


import org.jaffa.applications.jaffa.modules.admin.components.userviewer.dto.UserRoleDto;
import org.jaffa.applications.jaffa.modules.admin.domain.UserRole;
import org.jaffa.applications.jaffa.modules.admin.domain.UserRoleMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Viewer for User objects.
*/
public class UserViewerTx implements IUserViewer {

    private static Logger log = Logger.getLogger(UserViewerTx.class);
    
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
    /** Returns the details for User.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */    
    public UserViewerOutDto read(UserViewerInDto input)
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
            UserViewerOutDto output = buildDto(uow, results);
            
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
    private Criteria buildCriteria(UserViewerInDto input, UOW uow) {
        Criteria criteria = new Criteria();
        criteria.setTable( UserMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        criteria.addCriteria(UserMeta.USER_NAME, input.getUserName());
        // .//GEN-END:_buildCriteria_2_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_2


        // .//GEN-LAST:_buildCriteria_2
        // .//GEN-BEGIN:_buildCriteria_3_be
        return criteria;
    }
    // .//GEN-END:_buildCriteria_3_be
    // .//GEN-BEGIN:_buildDto_1_be
    private UserViewerOutDto buildDto(UOW uow, Collection results)
    throws UOWException {
        UserViewerOutDto output = null;
        Iterator itr = results.iterator();
        if (itr.hasNext()) {
            // just return the details for the 1st record retrieved.
            User user = (User) itr.next();
            output = new UserViewerOutDto();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_UserName_1_be
            output.setUserName(user.getUserName());
            // .//GEN-END:_buildDto_UserName_1_be
            // .//GEN-BEGIN:_buildDto_FirstName_1_be
            output.setFirstName(user.getFirstName());
            // .//GEN-END:_buildDto_FirstName_1_be
            // .//GEN-BEGIN:_buildDto_LastName_1_be
            output.setLastName(user.getLastName());
            // .//GEN-END:_buildDto_LastName_1_be
            // .//GEN-BEGIN:_buildDto_Status_1_be
            output.setStatus(user.getStatus());
            // .//GEN-END:_buildDto_Status_1_be
            // .//GEN-BEGIN:_buildDto_StatusDescription_1_be
            output.setStatusDescription(user.getStatus());
            // .//GEN-END:_buildDto_StatusDescription_1_be
            // .//GEN-BEGIN:_buildDto_EMailAddress_1_be
            output.setEMailAddress(user.getEMailAddress());
            // .//GEN-END:_buildDto_EMailAddress_1_be
            // .//GEN-BEGIN:_buildDto_CreatedOn_1_be
            output.setCreatedOn(user.getCreatedOn());
            // .//GEN-END:_buildDto_CreatedOn_1_be
            // .//GEN-BEGIN:_buildDto_CreatedBy_1_be
            output.setCreatedBy(user.getCreatedBy());
            // .//GEN-END:_buildDto_CreatedBy_1_be
            // .//GEN-BEGIN:_buildDto_LastUpdatedOn_1_be
            output.setLastUpdatedOn(user.getLastUpdatedOn());
            // .//GEN-END:_buildDto_LastUpdatedOn_1_be
            // .//GEN-BEGIN:_buildDto_LastUpdatedBy_1_be
            output.setLastUpdatedBy(user.getLastUpdatedBy());
            // .//GEN-END:_buildDto_LastUpdatedBy_1_be
            // .//GEN-BEGIN:_buildDto_2_be
            // Add related objects, if required
            addRelatedDtos(uow, output, user);
            // .//GEN-END:_buildDto_2_be
            // Add custom code to pass values to the dto //GEN-FIRST:_buildDto_2


            // .//GEN-LAST:_buildDto_2
            // .//GEN-BEGIN:_buildDto_3_be
        }
        return output;
    }
    // .//GEN-END:_buildDto_3_be
    // .//GEN-BEGIN:_addRelatedDtos_1_be
    private void addRelatedDtos(UOW uow, UserViewerOutDto output, User user)
    throws UOWException {
        // .//GEN-END:_addRelatedDtos_1_be
        // .//GEN-BEGIN:_addRelatedDtos_UserRole_1_be
        if (user.getUserName() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(UserRoleMeta.getName());
            criteria.addCriteria(UserRoleMeta.USER_NAME, user.getUserName());
            criteria.addOrderBy("UserName", Criteria.ORDER_BY_ASC);
            criteria.addOrderBy("RoleName", Criteria.ORDER_BY_ASC);
            // .//GEN-END:_addRelatedDtos_UserRole_1_be
            // Add custom code to set the criteria before the query //GEN-FIRST:_addRelatedDtos_UserRole_1


            // .//GEN-LAST:_addRelatedDtos_UserRole_1
            // .//GEN-BEGIN:_addRelatedDtos_UserRole_2_be
            Iterator itr = uow.query(criteria).iterator();
            while (itr.hasNext()) {
                UserRole userRole = (UserRole) itr.next();
                UserRoleDto dto = new UserRoleDto();
                // .//GEN-END:_addRelatedDtos_UserRole_2_be
                // Add custom code before all the setters //GEN-FIRST:_addRelatedDtos_UserRole_2


                // .//GEN-LAST:_addRelatedDtos_UserRole_2
                // .//GEN-BEGIN:_addRelatedDtos_UserRole_UserName_1_be
                dto.setUserName(userRole.getUserName());
                // .//GEN-END:_addRelatedDtos_UserRole_UserName_1_be
                // .//GEN-BEGIN:_addRelatedDtos_UserRole_RoleName_1_be
                dto.setRoleName(userRole.getRoleName());
                // .//GEN-END:_addRelatedDtos_UserRole_RoleName_1_be
                // Add custom code to pass values to the dto //GEN-FIRST:_addRelatedDtos_UserRole_3


                // .//GEN-LAST:_addRelatedDtos_UserRole_3
                // .//GEN-BEGIN:_addRelatedDtos_UserRole_3_be
                output.addUserRole(dto);
            }
        }
        // .//GEN-END:_addRelatedDtos_UserRole_3_be
        // .//GEN-BEGIN:_addRelatedDtos_2_be
    }
    // .//GEN-END:_addRelatedDtos_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
