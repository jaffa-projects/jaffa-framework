// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.userlookup.tx;

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

import org.jaffa.applications.jaffa.modules.admin.components.userlookup.IUserLookup;
import org.jaffa.applications.jaffa.modules.admin.components.userlookup.dto.UserLookupInDto;
import org.jaffa.applications.jaffa.modules.admin.components.userlookup.dto.UserLookupOutDto;
import org.jaffa.applications.jaffa.modules.admin.components.userlookup.dto.UserLookupOutRowDto;
import org.jaffa.applications.jaffa.modules.admin.domain.User;
import org.jaffa.applications.jaffa.modules.admin.domain.UserMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Lookup for User objects.
*/
public class UserLookupTx implements IUserLookup {

    private static Logger log = Logger.getLogger(UserLookupTx.class);

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
    /** Searches for User objects.
     * @param input The criteria based on which the search will be performed.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */
    public UserLookupOutDto find(UserLookupInDto input)
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
            UserLookupOutDto output = buildDto(uow, results, input);

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
    // .//GEN-END:_find_3_be
    // .//GEN-BEGIN:_buildCriteria_1_be
    private Criteria buildCriteria(UserLookupInDto input, UOW uow) {

        Criteria criteria = new Criteria();
        criteria.setTable( UserMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        FinderTx.addCriteria(input.getUserName(), UserMeta.USER_NAME, criteria);
        FinderTx.addCriteria(input.getFirstName(), UserMeta.FIRST_NAME, criteria);
        FinderTx.addCriteria(input.getLastName(), UserMeta.LAST_NAME, criteria);
        FinderTx.addCriteria(input.getStatus(), UserMeta.STATUS, criteria);
        FinderTx.addCriteria(input.getEMailAddress(), UserMeta.E_MAIL_ADDRESS, criteria);


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
    private UserLookupOutDto buildDto(UOW uow, Collection results, UserLookupInDto input)
    throws UOWException {

        UserLookupOutDto output = new UserLookupOutDto();



        int maxRecords = input.getMaxRecords() != null ? input.getMaxRecords().intValue() : 0;

        int counter = 0;
        for (Iterator i = results.iterator(); i.hasNext();) {
            if (++counter > maxRecords && maxRecords > 0) {
                output.setMoreRecordsExist(Boolean.TRUE);
                break;
            }

            UserLookupOutRowDto row = new UserLookupOutRowDto();
            User user = (User) i.next();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_UserName_1_be
            row.setUserName(user.getUserName());
            // .//GEN-END:_buildDto_UserName_1_be
            // .//GEN-BEGIN:_buildDto_FirstName_1_be
            row.setFirstName(user.getFirstName());
            // .//GEN-END:_buildDto_FirstName_1_be
            // .//GEN-BEGIN:_buildDto_LastName_1_be
            row.setLastName(user.getLastName());
            // .//GEN-END:_buildDto_LastName_1_be
            // .//GEN-BEGIN:_buildDto_Status_1_be
            row.setStatus(user.getStatus());
            // .//GEN-END:_buildDto_Status_1_be
            // .//GEN-BEGIN:_buildDto_StatusDescription_1_be
            row.setStatusDescription(user.getStatus());
            // .//GEN-END:_buildDto_StatusDescription_1_be
            // .//GEN-BEGIN:_buildDto_EMailAddress_1_be
            row.setEMailAddress(user.getEMailAddress());
            // .//GEN-END:_buildDto_EMailAddress_1_be
            // .//GEN-BEGIN:_buildDto_CreatedOn_1_be
            row.setCreatedOn(user.getCreatedOn());
            // .//GEN-END:_buildDto_CreatedOn_1_be
            // .//GEN-BEGIN:_buildDto_CreatedBy_1_be
            row.setCreatedBy(user.getCreatedBy());
            // .//GEN-END:_buildDto_CreatedBy_1_be
            // .//GEN-BEGIN:_buildDto_LastUpdatedOn_1_be
            row.setLastUpdatedOn(user.getLastUpdatedOn());
            // .//GEN-END:_buildDto_LastUpdatedOn_1_be
            // .//GEN-BEGIN:_buildDto_LastUpdatedBy_1_be
            row.setLastUpdatedBy(user.getLastUpdatedBy());
            // .//GEN-END:_buildDto_LastUpdatedBy_1_be

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
