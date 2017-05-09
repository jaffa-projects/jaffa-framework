// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.tx;

import java.util.*;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.AtomicCriteria;
import org.jaffa.components.finder.CriteriaField;
import org.jaffa.components.finder.OrderByField;
import org.jaffa.components.finder.FinderTx;

import org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.IUserRequestLookup;
import org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.dto.UserRequestLookupInDto;
import org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.dto.UserRequestLookupOutDto;
import org.jaffa.applications.jaffa.modules.user.components.userrequestlookup.dto.UserRequestLookupOutRowDto;
import org.jaffa.applications.jaffa.modules.user.domain.UserRequest;
import org.jaffa.applications.jaffa.modules.user.domain.UserRequestMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Lookup for UserRequest objects.
*/
public class UserRequestLookupTx implements IUserRequestLookup {

    private static Logger log = Logger.getLogger(UserRequestLookupTx.class);

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
    // .//GEN-BEGIN:_find_1_be
    /** Searches for UserRequest objects.
     * @param input The criteria based on which the search will be performed.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */
    public UserRequestLookupOutDto find(UserRequestLookupInDto input)
    throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled()) {
                log.debug("Input: " + (input != null ? input.toString() : null));
            }

            // create the UOW
            uow = new UOW();

            // Build the Criteria Object
            Criteria criteria = buildCriteria(input, uow);
            // .//GEN-END:_find_1_be
            // Add custom code before the query //GEN-FIRST:_find_1


            // .//GEN-LAST:_find_1
            // .//GEN-BEGIN:_find_2_be
            // Execute The Query
            Collection results = uow.query(criteria);
            // .//GEN-END:_find_2_be
            // Add custom code after the query //GEN-FIRST:_find_2


            // .//GEN-LAST:_find_2
            // .//GEN-BEGIN:_find_3_be
            // Convert the domain objects into the outbound dto
            UserRequestLookupOutDto output = buildDto(uow, results, input);

            // Print Debug Information for the output
            if (log.isDebugEnabled()) {
                log.debug("Output: " + (output != null ? output.toString() : null));
            }

            return output;

        } finally {
            if (uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:_find_3_be
    // .//GEN-BEGIN:_buildCriteria_1_be
    private Criteria buildCriteria(UserRequestLookupInDto input, UOW uow) {

        Criteria criteria = new Criteria();
        criteria.setTable( UserRequestMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        FinderTx.addCriteria(input.getRequestId(), UserRequestMeta.REQUEST_ID, criteria);
        FinderTx.addCriteria(input.getUserName(), UserRequestMeta.USER_NAME, criteria);
        FinderTx.addCriteria(input.getFirstName(), UserRequestMeta.FIRST_NAME, criteria);
        FinderTx.addCriteria(input.getLastName(), UserRequestMeta.LAST_NAME, criteria);
        FinderTx.addCriteria(input.getPassword(), UserRequestMeta.PASSWORD, criteria);
        FinderTx.addCriteria(input.getEMailAddress(), UserRequestMeta.E_MAIL_ADDRESS, criteria);
        FinderTx.addCriteria(input.getSecurityQuestion(), UserRequestMeta.SECURITY_QUESTION, criteria);
        FinderTx.addCriteria(input.getSecurityAnswer(), UserRequestMeta.SECURITY_ANSWER, criteria);
        FinderTx.addCriteria(input.getRemarks(), UserRequestMeta.REMARKS, criteria);
        FinderTx.addCriteria(input.getCreatedOn(), UserRequestMeta.CREATED_ON, criteria);
        FinderTx.addCriteria(input.getProcessedDatetime(), UserRequestMeta.PROCESSED_DATETIME, criteria);
        FinderTx.addCriteria(input.getProcessedUserId(), UserRequestMeta.PROCESSED_USER_ID, criteria);
        FinderTx.addCriteria(input.getStatus(), UserRequestMeta.STATUS, criteria);


        // append an orderBy clause to the criteria
        OrderByField[] orderByFields = input.getOrderByFields();
        if (orderByFields != null) {
            for (int i = 0; i < orderByFields.length; i++) {
                OrderByField orderByField = orderByFields[i];
                int sort = Criteria.ORDER_BY_ASC;
                if (orderByField.getSortAscending() != null && !orderByField.getSortAscending().booleanValue() )
                    sort = Criteria.ORDER_BY_DESC;
                criteria.addOrderBy(orderByField.getFieldName(), sort);
            }
        }

        // .//GEN-END:_buildCriteria_2_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_2


        // .//GEN-LAST:_buildCriteria_2
        // .//GEN-BEGIN:_buildCriteria_3_be
        return criteria;
    }
    // .//GEN-END:_buildCriteria_3_be
    // .//GEN-BEGIN:_buildDto_1_be
    private UserRequestLookupOutDto buildDto(UOW uow, Collection results, UserRequestLookupInDto input)
    throws UOWException {

        UserRequestLookupOutDto output = new UserRequestLookupOutDto();



        int maxRecords = input.getMaxRecords() != null ? input.getMaxRecords().intValue() : 0;

        int counter = 0;
        for (Iterator i = results.iterator(); i.hasNext();) {
            if (++counter > maxRecords && maxRecords > 0) {
                output.setMoreRecordsExist(Boolean.TRUE);
                break;
            }

            UserRequestLookupOutRowDto row = new UserRequestLookupOutRowDto();
            UserRequest userRequest = (UserRequest) i.next();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_RequestId_1_be
            row.setRequestId(userRequest.getRequestId());
            // .//GEN-END:_buildDto_RequestId_1_be
            // .//GEN-BEGIN:_buildDto_UserName_1_be
            row.setUserName(userRequest.getUserName());
            // .//GEN-END:_buildDto_UserName_1_be
            // .//GEN-BEGIN:_buildDto_FirstName_1_be
            row.setFirstName(userRequest.getFirstName());
            // .//GEN-END:_buildDto_FirstName_1_be
            // .//GEN-BEGIN:_buildDto_LastName_1_be
            row.setLastName(userRequest.getLastName());
            // .//GEN-END:_buildDto_LastName_1_be
            // .//GEN-BEGIN:_buildDto_Password_1_be
            row.setPassword(userRequest.getPassword());
            // .//GEN-END:_buildDto_Password_1_be
            // .//GEN-BEGIN:_buildDto_EMailAddress_1_be
            row.setEMailAddress(userRequest.getEMailAddress());
            // .//GEN-END:_buildDto_EMailAddress_1_be
            // .//GEN-BEGIN:_buildDto_SecurityQuestion_1_be
            row.setSecurityQuestion(userRequest.getSecurityQuestion());
            // .//GEN-END:_buildDto_SecurityQuestion_1_be
            // .//GEN-BEGIN:_buildDto_SecurityAnswer_1_be
            row.setSecurityAnswer(userRequest.getSecurityAnswer());
            // .//GEN-END:_buildDto_SecurityAnswer_1_be
            // .//GEN-BEGIN:_buildDto_Remarks_1_be
            row.setRemarks(userRequest.getRemarks());
            // .//GEN-END:_buildDto_Remarks_1_be
            // .//GEN-BEGIN:_buildDto_CreatedOn_1_be
            row.setCreatedOn(userRequest.getCreatedOn());
            // .//GEN-END:_buildDto_CreatedOn_1_be
            // .//GEN-BEGIN:_buildDto_ProcessedDatetime_1_be
            row.setProcessedDatetime(userRequest.getProcessedDatetime());
            // .//GEN-END:_buildDto_ProcessedDatetime_1_be
            // .//GEN-BEGIN:_buildDto_ProcessedUserId_1_be
            row.setProcessedUserId(userRequest.getProcessedUserId());
            // .//GEN-END:_buildDto_ProcessedUserId_1_be
            // .//GEN-BEGIN:_buildDto_Status_1_be
            row.setStatus(userRequest.getStatus());
            // .//GEN-END:_buildDto_Status_1_be

            // Add custom code to pass values to the dto //GEN-FIRST:_buildDto_2


            // .//GEN-LAST:_buildDto_2
            // .//GEN-BEGIN:_buildDto_3_be
            output.addRows(row);
        }
        return output;
    }
    // .//GEN-END:_buildDto_3_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
