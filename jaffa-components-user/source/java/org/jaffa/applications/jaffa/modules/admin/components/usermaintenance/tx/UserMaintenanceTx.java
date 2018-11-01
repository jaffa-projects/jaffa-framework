// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.tx;

import org.apache.log4j.Logger;
import java.util.*;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.Criteria;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.DomainObjectNotFoundException;
import org.jaffa.exceptions.DuplicateKeyException;
import org.jaffa.exceptions.MultipleDomainObjectsFoundException;
import org.jaffa.exceptions.IncompleteKeySpecifiedException;
import org.jaffa.exceptions.RelatedDomainObjectFoundException;
import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.DomainObjectChangedException;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Formatter;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.ExceptionHelper;
import org.jaffa.components.maint.MaintTx;
import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;

import org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.IUserMaintenance;
import org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.dto.*;
import org.jaffa.applications.jaffa.modules.admin.domain.User;
import org.jaffa.applications.jaffa.modules.admin.domain.UserMeta;


import org.jaffa.applications.jaffa.modules.admin.domain.UserRole;
import org.jaffa.applications.jaffa.modules.admin.domain.UserRoleMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import java.util.*;
import org.jaffa.security.SecurityManager;
import java.security.PrivilegedAction;
import org.jaffa.security.PolicyManager;
import org.jaffa.loader.policy.RoleManager;
import org.jaffa.security.securityrolesdomain.Roles;
import org.jaffa.security.securityrolesdomain.Role;
import org.jaffa.security.securityrolesdomain.Include;
import org.jaffa.security.securityrolesdomain.Exclude;
import org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.tx.exceptions.UserMaintenanceException;
import org.jaffa.util.EmailerBean;
import org.jaffa.applications.jaffa.modules.user.domain.UserRequest;
import org.jaffa.applications.jaffa.modules.user.domain.UserRequestMeta;
// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Maintenance for User objects.
*/
public class UserMaintenanceTx extends MaintTx implements IUserMaintenance {

    private static Logger log = Logger.getLogger(UserMaintenanceTx.class);

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
    // .//GEN-BEGIN:_prevalidateCreate_1_be
    /** This method is used to perform prevalidations before creating a new instance of User.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public UserMaintenancePrevalidateOutDto prevalidateCreate(UserMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled())
                log.debug("Input: " + (input != null ? input.toString() : null));

            // create the UOW
            uow = new UOW();

            // Preprocess the input
            preprocess(uow, input);

            // Do not allow a Duplicate record
            duplicateCheck(uow, input);

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Create the domain object
            User domain = createDomain(uow, input, true);
            domain = postCreate(uow, input, domain, true);

            // Build the outbound dto
            UserMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Output: " + (output != null ? output.toString() : null));
            return output;
        } catch (FrameworkException e) {
            // It is possible the FrameworkException is wrapping an ApplicationException.
            // If it is, then re-throw as ApplicationsExceptions, else throw the FrameworkException.
            if (e.getCause() != null && e.getCause() instanceof ApplicationExceptions) {
                throw (ApplicationExceptions) e.getCause();
            } else if (e.getCause() != null && e.getCause() instanceof ApplicationException) {
                ApplicationExceptions appExps = new ApplicationExceptions();
                appExps.add((ApplicationException) e.getCause());
                throw appExps;
            } else
                throw e;
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:_prevalidateCreate_1_be
    // .//GEN-BEGIN:_create_1_be
    /** Persists a new instance of User.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public UserMaintenanceRetrieveOutDto create(UserMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled())
                log.debug("Input: " + (input != null ? input.toString() : null));

            // create the UOW
            uow = new UOW();

            // Preprocess the input
            preprocess(uow, input);

            // Do not allow a Duplicate record
            duplicateCheck(uow, input);

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Create the domain object
            User domain = createDomain(uow, input, false);
            uow.add(domain);
            domain = postCreate(uow, input, domain, false);

            // Commit the changes
            uow.commit();

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Successfully created the domain object. Now retrieving the object details.");

            if (input.getUserName() == null) {
                if (log.isDebugEnabled())
                    log.debug("The Key is not set completely. Probably using AutoGenerated keys. Cannot re-retrieve the object details. Returning NULL instead");
                return null;
            }

            // Build the outbound dto by performing a retrieve
            UserMaintenanceRetrieveInDto retrieveInDto = new UserMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setUserName(input.getUserName());
            UserMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
            return output;
        } catch (FrameworkException e) {
            // It is possible the FrameworkException is wrapping an ApplicationException.
            // If it is, then re-throw as ApplicationsExceptions, else throw the FrameworkException.
            if (e.getCause() != null && e.getCause() instanceof ApplicationExceptions) {
                throw (ApplicationExceptions) e.getCause();
            } else if (e.getCause() != null && e.getCause() instanceof ApplicationException) {
                ApplicationExceptions appExps = new ApplicationExceptions();
                appExps.add((ApplicationException) e.getCause());
                throw appExps;
            } else
                throw e;
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:_create_1_be
    // .//GEN-BEGIN:_retrieve_1_be
    /** Returns the details for User.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */
    public UserMaintenanceRetrieveOutDto retrieve(UserMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled())
                log.debug("Input: " + (input != null ? input.toString() : null));

            // create the UOW
            uow = new UOW();

            // Preprocess the input
            preprocess(uow, input);

            // Retrieve the object
            User domain = load(uow, input);

            // Convert the domain objects into the outbound dto
            UserMaintenanceRetrieveOutDto output = buildRetrieveOutDto(uow, input, domain);

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Output: " + (output != null ? output.toString() : null));
            return output;
        } catch (FrameworkException e) {
            // It is possible the FrameworkException is wrapping an ApplicationException.
            // If it is, then re-throw as ApplicationsExceptions, else throw the FrameworkException.
            if (e.getCause() != null && e.getCause() instanceof ApplicationExceptions) {
                throw (ApplicationExceptions) e.getCause();
            } else if (e.getCause() != null && e.getCause() instanceof ApplicationException) {
                ApplicationExceptions appExps = new ApplicationExceptions();
                appExps.add((ApplicationException) e.getCause());
                throw appExps;
            } else
                throw e;
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:_retrieve_1_be
    // .//GEN-BEGIN:_prevalidateUpdate_1_be
    /** This method is used to perform prevalidations before updating an existing instance of User.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public UserMaintenancePrevalidateOutDto prevalidateUpdate(UserMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled())
                log.debug("Input: " + (input != null ? input.toString() : null));

            // create the UOW
            uow = new UOW();

            // Preprocess the input
            preprocess(uow, input);

            // Retrieve the object
            User domain = load(uow, input);

            // Ensure the domain object has not been modified
            domainObjectChangedTest(input.getPerformDirtyReadCheck(), domain, input.getLastUpdatedOn());

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Update the domain object
            updateDomain(uow, input, domain, true);


            // Build the outbound dto
            UserMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Output: " + (output != null ? output.toString() : null));
            return output;
        } catch (FrameworkException e) {
            // It is possible the FrameworkException is wrapping an ApplicationException.
            // If it is, then re-throw as ApplicationsExceptions, else throw the FrameworkException.
            if (e.getCause() != null && e.getCause() instanceof ApplicationExceptions) {
                throw (ApplicationExceptions) e.getCause();
            } else if (e.getCause() != null && e.getCause() instanceof ApplicationException) {
                ApplicationExceptions appExps = new ApplicationExceptions();
                appExps.add((ApplicationException) e.getCause());
                throw appExps;
            } else
                throw e;
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:_prevalidateUpdate_1_be
    // .//GEN-BEGIN:_update_1_be
    /** Updates an existing instance of User.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public UserMaintenanceRetrieveOutDto update(UserMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled())
                log.debug("Input: " + (input != null ? input.toString() : null));

            // create the UOW
            uow = new UOW();

            // Preprocess the input
            preprocess(uow, input);

            // Retrieve the object
            User domain = load(uow, input);

            // Ensure the domain object has not been modified
            domainObjectChangedTest(input.getPerformDirtyReadCheck(), domain, input.getLastUpdatedOn());

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Update the domain object
            updateDomain(uow, input, domain, false);
            uow.update(domain);

            // Commit the changes
            uow.commit();

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Successfully updated the domain object. Now retrieving the object details.");

            // Build the outbound dto by performing a retrieve
            UserMaintenanceRetrieveInDto retrieveInDto = new UserMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setUserName(input.getUserName());
            UserMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
            return output;
        } catch (FrameworkException e) {
            // It is possible the FrameworkException is wrapping an ApplicationException.
            // If it is, then re-throw as ApplicationsExceptions, else throw the FrameworkException.
            if (e.getCause() != null && e.getCause() instanceof ApplicationExceptions) {
                throw (ApplicationExceptions) e.getCause();
            } else if (e.getCause() != null && e.getCause() instanceof ApplicationException) {
                ApplicationExceptions appExps = new ApplicationExceptions();
                appExps.add((ApplicationException) e.getCause());
                throw appExps;
            } else
                throw e;
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }
    // .//GEN-END:_update_1_be
    // .//GEN-BEGIN:_delete_1_be
    /** Deletes an existing instance of User.
     * @param input The key values for the domain object to be deleted.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(UserMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // create the UOW
            uow = new UOW();
            
            // invoke the delete passing the UOW
            delete(input, uow);

            // Commit the changes
            uow.commit();

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Successfully deleted the domain object");

        } catch (FrameworkException e) {
            // It is possible the FrameworkException is wrapping an ApplicationException.
            // If it is, then re-throw as ApplicationsExceptions, else throw the FrameworkException.
            if (e.getCause() != null && e.getCause() instanceof ApplicationExceptions) {
                throw (ApplicationExceptions) e.getCause();
            } else if (e.getCause() != null && e.getCause() instanceof ApplicationException) {
                ApplicationExceptions appExps = new ApplicationExceptions();
                appExps.add((ApplicationException) e.getCause());
                throw appExps;
            } else
                throw e;
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }

    /** Deletes an existing instance of User.
     * @param input The key values for the domain object to be deleted.
     * @param uow The delete will be performed using the input UOW.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(UserMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions {
        // Print Debug Information for the input
        if (log.isDebugEnabled())
            log.debug("Input: " + (input != null ? input.toString() : null));

        // Preprocess the input
        preprocess(uow, input);

        // Retrieve the object
        User domain = load(uow, input);

        // Ensure the domain object has not been modified
        domainObjectChangedTest(input.getPerformDirtyReadCheck(), domain, input.getLastUpdatedOn());

        // Delete the domain object
        deleteDomain(uow, input, domain);

        // Print Debug Information for the output
        if (log.isDebugEnabled())
            log.debug("The domain object has been marked for deletion. It will be deleted when the UOW is committed");
    }
    // .//GEN-END:_delete_1_be
    // .//GEN-BEGIN:_preprocessCreate_1_be
    /** Preprocess the input for the create method. */
    private void preprocess(UOW uow, UserMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessCreate_1_be
        // Add custom code //GEN-FIRST:_preprocessCreate_1
        // ensure that the user has access to the business function - Jaffa.Admin.User.Maintenance
        if (!hasMaintenanceAccess()) {
            String str = "Create aborted. User has no access to the Business Function: 'Jaffa.Admin.User.Maintenance'";
            log.error(str);
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new UserMaintenanceException(UserMaintenanceException.PROP_NO_MAINT_ACCESS));
            throw appExps;
        }



        // .//GEN-LAST:_preprocessCreate_1
        // .//GEN-BEGIN:_preprocessCreate_2_be
    }
    // .//GEN-END:_preprocessCreate_2_be
    // .//GEN-BEGIN:_duplicateCheck_1_be
    /** Ensure that a duplicate record is not created. */
    private void duplicateCheck(UOW uow, UserMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_duplicateCheck_1_be
        // Add custom code //GEN-FIRST:_duplicateCheck_1


        // .//GEN-LAST:_duplicateCheck_1
        // .//GEN-BEGIN:_duplicateCheck_2_be
        if (input.getUserName() == null)
            return;
        Criteria criteria = new Criteria();
        criteria.setTable( UserMeta.getName() );
        // .//GEN-END:_duplicateCheck_2_be
        // Add custom criteria //GEN-FIRST:_duplicateCheck_2


        // .//GEN-LAST:_duplicateCheck_2
        // .//GEN-BEGIN:_duplicateCheck_3_be
        criteria.addCriteria(UserMeta.USER_NAME, input.getUserName());
        Collection col = uow.query(criteria);
        // .//GEN-END:_duplicateCheck_3_be
        // Add custom code //GEN-FIRST:_duplicateCheck_3


        // .//GEN-LAST:_duplicateCheck_3
        // .//GEN-BEGIN:_duplicateCheck_4_be
        if (col != null && !col.isEmpty()) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DuplicateKeyException(UserMeta.getLabelToken()));
            throw appExps;
        }
    }
    // .//GEN-END:_duplicateCheck_4_be
    // .//GEN-BEGIN:_createDomain_1_be
    /** Create the domain object. */
    private User createDomain(UOW uow, UserMaintenanceCreateInDto input, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        User domain = new User();
        ApplicationExceptions appExps = null;
        // .//GEN-END:_createDomain_1_be
        // Add custom code //GEN-FIRST:_createDomain_1
        try {
            if (input.getPassword1() != null)
                domain.updatePassword(input.getPassword1());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }


        // .//GEN-LAST:_createDomain_1
        // .//GEN-BEGIN:_createDomain_2_be
        try {
            domain.updateUserName(input.getUserName());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFirstName(input.getFirstName());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateLastName(input.getLastName());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateStatus(input.getStatus());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateEMailAddress(input.getEMailAddress());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateSecurityQuestion(input.getSecurityQuestion());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateSecurityAnswer(input.getSecurityAnswer());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            if (!fromPrevalidate)
                domain.updateCreatedOn(new DateTime());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            if (!fromPrevalidate && input.getHeaderDto() != null && input.getHeaderDto().getUserId() != null)
                domain.updateCreatedBy(input.getHeaderDto().getUserId());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        // .//GEN-END:_createDomain_2_be
        // Add custom code //GEN-FIRST:_createDomain_2


        // .//GEN-LAST:_createDomain_2
        // .//GEN-BEGIN:_createDomain_3_be
        if (appExps != null && appExps.size() > 0)
            throw appExps;
        return domain;
    }
    // .//GEN-END:_createDomain_3_be
    // .//GEN-BEGIN:_postCreate_1_be
    /** This method is invoked after the domain object has been created.*/
    private User postCreate(UOW uow, UserMaintenanceCreateInDto input, User domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_postCreate_1_be
        // Add custom code //GEN-FIRST:_postCreate_1
        ApplicationExceptions appExps = null;

        String[] userRole = null;
        // Build an array of roles
        userRole = new String[input.getUserRoleCount()];
        UserRoleDto[] userRoleDtos = input.getUserRole();
        for(int i = 0; i < userRoleDtos.length; i++)
            userRole[i] = userRoleDtos[i].getRoleName();
        Arrays.sort(userRole);
        performUserRoleValidations(userRole, domain);

        if (!fromPrevalidate) {
            // add the the roles
            for(int i = 0; i < userRole.length; i++) {
                try {
                    UserRole ur = new UserRole();
                    ur.updateRoleName(userRole[i]);
                    ur.updateUserName(input.getUserName());
                    uow.add(ur);
                } catch (ValidationException e) {
                    if (appExps == null)
                        appExps = new ApplicationExceptions();
                    appExps.add(e);
                }
            }
        }



        if (input.getNotifyUser().booleanValue()) {
            try {
                EmailerBean email = new EmailerBean();
                String[] to = new String[] {domain.getEMailAddress()};
                String body = "Your UserName is " + domain.getUserName() + " and your password is " + domain.getPassword() + ". Your account is currently " + domain.getStatus() + ".";
                email.sendMail(to, "Account Information" , body);
            } catch  (javax.mail.MessagingException e) {
                e.printStackTrace();
            }
        }

        if (input.getRequestId() != null && input.getRequestId().length() > 0) {
            try {
                UserRequest userRequest = UserRequest.findByPK(uow , new Long(input.getRequestId()));
                userRequest.setStatus("S");
                uow.update(userRequest);


            } catch (ValidationException e) {
                if (appExps == null)
                    appExps = new ApplicationExceptions();
                appExps.add(e);
            }
        }
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        // .//GEN-LAST:_postCreate_1
        // .//GEN-BEGIN:_postCreate_2_be
        return domain;
    }
    // .//GEN-END:_postCreate_2_be
    // .//GEN-BEGIN:_preprocessRetrieve_1_be
    /** Preprocess the input for the retrieve method. */
    private void preprocess(UOW uow, UserMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessRetrieve_1_be
        // Add custom code //GEN-FIRST:_preprocessRetrieve_1


        // .//GEN-LAST:_preprocessRetrieve_1
        // .//GEN-BEGIN:_preprocessRetrieve_2_be
    }
    // .//GEN-END:_preprocessRetrieve_2_be
    // .//GEN-BEGIN:_loadRetrieve_1_be
    /** Retrieve the domain object. */
    private User load(UOW uow, UserMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        User domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( UserMeta.getName() );
        // .//GEN-END:_loadRetrieve_1_be
        // Add custom criteria //GEN-FIRST:_loadRetrieve_1


        // .//GEN-LAST:_loadRetrieve_1
        // .//GEN-BEGIN:_loadRetrieve_2_be
        criteria.addCriteria(UserMeta.USER_NAME, input.getUserName());
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (User) itr.next();
        // .//GEN-END:_loadRetrieve_2_be
        // Add custom code //GEN-FIRST:_loadRetrieve_2


        // .//GEN-LAST:_loadRetrieve_2
        // .//GEN-BEGIN:_loadRetrieve_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(UserMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadRetrieve_3_be
    // .//GEN-BEGIN:_buildRetrieveOutDto_1_be
    /** Create the RetrieveOutDto. */
    private UserMaintenanceRetrieveOutDto buildRetrieveOutDto(UOW uow, UserMaintenanceRetrieveInDto input, User domain) throws FrameworkException, ApplicationExceptions {
        UserMaintenanceRetrieveOutDto output = new UserMaintenanceRetrieveOutDto();
        // .//GEN-END:_buildRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_buildRetrieveOutDto_1


        // .//GEN-LAST:_buildRetrieveOutDto_1
        // .//GEN-BEGIN:_buildRetrieveOutDto_2_be
        output.setUserName(domain.getUserName());
        output.setFirstName(domain.getFirstName());
        output.setLastName(domain.getLastName());
        output.setStatus(domain.getStatus());
        output.setEMailAddress(domain.getEMailAddress());
        output.setSecurityQuestion(domain.getSecurityQuestion());
        output.setSecurityAnswer(domain.getSecurityAnswer());
        output.setCreatedOn(domain.getCreatedOn());
        output.setCreatedBy(domain.getCreatedBy());
        output.setLastUpdatedOn(domain.getLastUpdatedOn());
        output.setLastUpdatedBy(domain.getLastUpdatedBy());
        addForeignObjectsToRetrieveOut(uow, domain, output);
        addRelatedDtosToRetrieveOut(uow, domain, output);
        // .//GEN-END:_buildRetrieveOutDto_2_be
        // Add custom code //GEN-FIRST:_buildRetrieveOutDto_2


        // .//GEN-LAST:_buildRetrieveOutDto_2
        // .//GEN-BEGIN:_buildRetrieveOutDto_3_be
        return output;
    }
    // .//GEN-END:_buildRetrieveOutDto_3_be
    // .//GEN-BEGIN:_preprocessUpdate_1_be
    /** Preprocess the input for the update method. */
    private void preprocess(UOW uow, UserMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessUpdate_1_be
        // Add custom code //GEN-FIRST:_preprocessUpdate_1
        // determine the user access levels
        boolean selfUpdateAccess;
        boolean maintenanceAccess = hasMaintenanceAccess();
        if (maintenanceAccess) {
            // maintenanceAccess implies selfUpdateAccess
            selfUpdateAccess = true;
        } else {
            selfUpdateAccess = hasSelfUpdateAccess();
        }

        if (!maintenanceAccess && !selfUpdateAccess) {
            String str = "Update aborted. User has no access to either of the Business Functions: 'Jaffa.Admin.User.Maintenance' & 'Jaffa.Admin.User.SelfUpdate'";
            log.error(str);
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new UserMaintenanceException(UserMaintenanceException.PROP_NO_ACCESS));
            throw appExps;
        } else if (!maintenanceAccess && selfUpdateAccess) {
            if (!input.getHeaderDto().getUserId().equals(input.getUserName())) {
                String str = "Update aborted. User has no privilege to update someone else's profile";
                log.error(str);
                ApplicationExceptions appExps = new ApplicationExceptions();
                appExps.add(new UserMaintenanceException(UserMaintenanceException.PROP_CANNOT_UPDATE_OTHERS_PROFILE));
                throw appExps;
            }
        }


        // .//GEN-LAST:_preprocessUpdate_1
        // .//GEN-BEGIN:_preprocessUpdate_2_be
    }
    // .//GEN-END:_preprocessUpdate_2_be
    // .//GEN-BEGIN:_loadUpdate_1_be
    /** Retrieve the domain object. */
    private User load(UOW uow, UserMaintenanceUpdateInDto input)
    throws FrameworkException, ApplicationExceptions {
        User domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( UserMeta.getName() );
        // .//GEN-END:_loadUpdate_1_be
        // Add custom criteria //GEN-FIRST:_loadUpdate_1


        // .//GEN-LAST:_loadUpdate_1
        // .//GEN-BEGIN:_loadUpdate_2_be
        criteria.addCriteria(UserMeta.USER_NAME, input.getUserName());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (User) itr.next();
        // .//GEN-END:_loadUpdate_2_be
        // Add custom code //GEN-FIRST:_loadUpdate_2
        oldPassword = domain.getPassword();
        oldStatus = domain.getStatus();
        // .//GEN-LAST:_loadUpdate_2
        // .//GEN-BEGIN:_loadUpdate_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(UserMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadUpdate_3_be
    // .//GEN-BEGIN:_updateDomain_1_be
    /** Update the domain object and add it to the UOW. */
    private void updateDomain(UOW uow, UserMaintenanceUpdateInDto input, User domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_updateDomain_1_be
        // Add custom code //GEN-FIRST:_updateDomain_1


        // .//GEN-LAST:_updateDomain_1
        // .//GEN-BEGIN:_updateDomain_2_be
        try {
            domain.updateFirstName(input.getFirstName());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateLastName(input.getLastName());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateStatus(input.getStatus());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateEMailAddress(input.getEMailAddress());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateSecurityQuestion(input.getSecurityQuestion());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateSecurityAnswer(input.getSecurityAnswer());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            if (!fromPrevalidate)
                domain.updateLastUpdatedOn(new DateTime());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            if (!fromPrevalidate && input.getHeaderDto() != null && input.getHeaderDto().getUserId() != null)
                domain.updateLastUpdatedBy(input.getHeaderDto().getUserId());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        // .//GEN-END:_updateDomain_2_be
        // Add custom code //GEN-FIRST:_updateDomain_2
        try {
            if (input.getPassword1() != null)
                domain.updatePassword(input.getPassword1());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        String[] userRole = null;
        if (appExps == null || appExps.size() == 0) {
            // Build an array of roles
            userRole = new String[input.getUserRoleCount()];
            UserRoleDto[] userRoleDtos = input.getUserRole();
            for(int i = 0; i < userRoleDtos.length; i++)
                userRole[i] = userRoleDtos[i].getRoleName();
            Arrays.sort(userRole);
            performUserRoleValidations(userRole, domain);
        }

        if (!fromPrevalidate) {
            // These keep track of whats added and removed
            Collection rolesAdded = new ArrayList();
            Collection rolesRemoved = new ArrayList();
            Collection processed = new ArrayList();

            if (appExps == null || appExps.size() == 0) {
                // Roles can be updated only if the user has access to the maintenance function
                if (hasMaintenanceAccess()) {
                    // Get the current roles
                    Criteria crit = new Criteria();
                    crit.setTable(UserRoleMeta.getName());
                    crit.addCriteria(UserRoleMeta.USER_NAME, input.getUserName());
                    Collection currRoles = uow.query(crit);

                    // Loop through current records and remove from database if not in list,
                    // if in database, add to the processed list
                    for(Iterator it = currRoles.iterator(); it.hasNext(); ) {
                        UserRole ur = (UserRole) it.next();
                        if(Arrays.binarySearch(userRole, ur.getRoleName()) >= 0) {
                            processed.add(ur.getRoleName());
                            if(log.isDebugEnabled())
                                log.debug("Keeping Role - " + ur.getRoleName());
                        } else {
                            rolesRemoved.add(ur.getRoleName());
                            uow.delete(ur);
                            if(log.isDebugEnabled())
                                log.debug("Deleting Role - " + ur.getRoleName());
                        }
                    }

                    // Now add the new ones
                    for(int i = 0; i < userRole.length; i++) {
                        String rolename = userRole[i];
                        if(!processed.contains(rolename)) {
                            try {
                                UserRole ur = new UserRole();
                                ur.updateRoleName(rolename);
                                ur.updateUserName(input.getUserName());
                                rolesAdded.add(ur.getRoleName());
                                uow.add(ur);
                                if(log.isDebugEnabled())
                                    log.debug("Adding Role - " + ur.getRoleName());
                            } catch (ValidationException e) {
                                if (appExps == null)
                                    appExps = new ApplicationExceptions();
                                appExps.add(e);
                            }
                        }
                    }
                }
            }
        }
       if (input.getNotifyUser().booleanValue()) {
            try {
                EmailerBean email = new EmailerBean();
                String[] to = new String[] {domain.getEMailAddress()};
                StringBuffer body = new StringBuffer();
                if ((oldPassword != null && !oldPassword.equals(domain.getPassword())) ||(oldStatus != null && !oldStatus.equals(domain.getStatus())))  {
                    body.append("Your UserName is " + domain.getUserName());
                    if (oldPassword != null && !oldPassword.equals(domain.getPassword()))
                        body.append(" and your password is " + domain.getPassword() + ".");
                    if (oldStatus != null && !oldStatus.equals(domain.getStatus()))
                        body.append("Your account is currently " + domain.getStatus() + ".");
                    email.sendMail(to, "Account Information" , body.toString());
                }
            } catch  (javax.mail.MessagingException e) {
                e.printStackTrace();
            }
        }

   /*     try {
            UserRequest userRequest = UserRequest.findByPK(uow , new Long(input.getRequestId()));
            userRequest.setStatus("S");
            uow.update(userRequest);


        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
*/
        if (appExps != null && appExps.size() > 0)
            throw appExps;
        // .//GEN-LAST:_updateDomain_2
        // .//GEN-BEGIN:_updateDomain_3_be
        if (appExps != null && appExps.size() > 0)
            throw appExps;
    }
    // .//GEN-END:_updateDomain_3_be
    // .//GEN-BEGIN:_preprocessDelete_1_be
    /** Preprocess the input for the delete method. */
    private void preprocess(UOW uow, UserMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessDelete_1_be
        // Add custom code //GEN-FIRST:_preprocessDelete_1
        // ensure that the user has access to the business function - Jaffa.Admin.User.Maintenance
        if (!hasMaintenanceAccess()) {
            String str = "Delete aborted. User has no access to the Business Function: 'Jaffa.Admin.User.Maintenance'";
            log.error(str);
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new UserMaintenanceException(UserMaintenanceException.PROP_NO_MAINT_ACCESS));
            throw appExps;
        }


        // .//GEN-LAST:_preprocessDelete_1
        // .//GEN-BEGIN:_preprocessDelete_2_be
    }
    // .//GEN-END:_preprocessDelete_2_be
    // .//GEN-BEGIN:_loadDelete_1_be
    /** Retrieve the domain object. */
    private User load(UOW uow, UserMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        User domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( UserMeta.getName() );
        // .//GEN-END:_loadDelete_1_be
        // Add custom criteria //GEN-FIRST:_loadDelete_1


        // .//GEN-LAST:_loadDelete_1
        // .//GEN-BEGIN:_loadDelete_2_be
        criteria.addCriteria(UserMeta.USER_NAME, input.getUserName());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (User) itr.next();
        // .//GEN-END:_loadDelete_2_be
        // Add custom code //GEN-FIRST:_loadDelete_2


        // .//GEN-LAST:_loadDelete_2
        // .//GEN-BEGIN:_loadDelete_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(UserMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadDelete_3_be
    // .//GEN-BEGIN:_deleteDomain_1_be
    /** Delete the domain object from the domain. */
    private void deleteDomain(UOW uow, UserMaintenanceDeleteInDto input, User domain) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_deleteDomain_1_be
        // Add custom code //GEN-FIRST:_deleteDomain_1


        // .//GEN-LAST:_deleteDomain_1
        // .//GEN-BEGIN:_deleteDomain_2_be
        deleteRelatedObjects(uow, input, domain);
        uow.delete(domain);
        // .//GEN-END:_deleteDomain_2_be
        // Add custom code //GEN-FIRST:_deleteDomain_2


        // .//GEN-LAST:_deleteDomain_2
        // .//GEN-BEGIN:_deleteDomain_3_be
    }
    // .//GEN-END:_deleteDomain_3_be
    // .//GEN-BEGIN:_addForeignObjectsToRetrieveOut_1_be
    /** Add foreign objects to UserMaintenanceRetrieveOutDto */
    private void addForeignObjectsToRetrieveOut(UOW uow, User domain, UserMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {
    }
    // .//GEN-END:_addForeignObjectsToRetrieveOut_1_be
    // .//GEN-BEGIN:_validateForeignObjects_1_be
    /** This will validate the Foreign Objects. */
    private void validateForeignObjects(UOW uow, UserMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_validateForeignObjects_1_be
        // .//GEN-BEGIN:_validateForeignObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_validateForeignObjects_2_be
    // .//GEN-BEGIN:_createPrevalidateOutDto_1_be
    private UserMaintenancePrevalidateOutDto createPrevalidateOutDto(UOW uow, User domain, UserMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        UserMaintenancePrevalidateOutDto output = new UserMaintenancePrevalidateOutDto();
        output.setUserName(domain.getUserName());
        output.setFirstName(domain.getFirstName());
        output.setLastName(domain.getLastName());
        output.setPassword1(input.getPassword1());
        output.setRequestId(input.getRequestId());
        output.setPassword2(input.getPassword2());
        output.setStatus(domain.getStatus());
        output.setEMailAddress(domain.getEMailAddress());
        output.setSecurityQuestion(domain.getSecurityQuestion());
        output.setSecurityQuestion1(input.getSecurityQuestion1());
        output.setSecurityAnswer(domain.getSecurityAnswer());
        output.setCreatedOn(domain.getCreatedOn());
        output.setAutoPassword(input.getAutoPassword());
        output.setNotifyUser(input.getNotifyUser());
        output.setCreatedBy(domain.getCreatedBy());
        output.setLastUpdatedOn(domain.getLastUpdatedOn());
        output.setLastUpdatedBy(domain.getLastUpdatedBy());
        addForeignObjectsToRetrieveOut(uow, domain, output);
        addRelatedDtosToRetrieveOut(uow, domain, output);
        // .//GEN-END:_createPrevalidateOutDto_1_be
        // Add custom code //GEN-FIRST:_createPrevalidateOutDto_1


        // .//GEN-LAST:_createPrevalidateOutDto_1
        // .//GEN-BEGIN:_createPrevalidateOutDto_2_be
        return output;
    }
    // .//GEN-END:_createPrevalidateOutDto_2_be
    // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_1_be
    /** Add related objects to UserMaintenanceRetrieveOutDto */
    private void addRelatedDtosToRetrieveOut(UOW uow, User user, UserMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_addRelatedDtosToRetrieveOut_1_be
        // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_UserRole_1_be
        if (user.getUserName() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(UserRoleMeta.getName());
            criteria.addCriteria(UserRoleMeta.USER_NAME, user.getUserName());
            // .//GEN-END:_addRelatedDtosToRetrieveOut_UserRole_1_be
            // Add custom code to set the criteria before the query //GEN-FIRST:_addRelatedDtosToRetrieveOut_UserRole_1


            // .//GEN-LAST:_addRelatedDtosToRetrieveOut_UserRole_1
            // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_UserRole_2_be
            Iterator itrMany = uow.query(criteria).iterator();
            while (itrMany.hasNext()) {
                UserRole userRole = (UserRole) itrMany.next();
                UserRoleDto dto = new UserRoleDto();
                // .//GEN-END:_addRelatedDtosToRetrieveOut_UserRole_2_be
                // Add custom code before all the setters //GEN-FIRST:_addRelatedDtosToRetrieveOut_UserRole_2


                // .//GEN-LAST:_addRelatedDtosToRetrieveOut_UserRole_2
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_UserRole_RoleName_1_be
                dto.setRoleName(userRole.getRoleName());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_UserRole_RoleName_1_be
                // Add custom code to pass values to the dto //GEN-FIRST:_addRelatedDtosToRetrieveOut_UserRole_3


                // .//GEN-LAST:_addRelatedDtosToRetrieveOut_UserRole_3
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_UserRole_3_be
                output.addUserRole(dto);
            }
            // .//GEN-END:_addRelatedDtosToRetrieveOut_UserRole_3_be
            // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_UserRole_6_be
        }

        // .//GEN-END:_addRelatedDtosToRetrieveOut_UserRole_6_be
        // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_2_be
    }
    // .//GEN-END:_addRelatedDtosToRetrieveOut_2_be
    // .//GEN-BEGIN:_deleteRelatedObjects_1_be
    /** Delete the related domain objects if the 'Cascading' constraint is specified. Throw an exception in case 'Restricted' constraint is specified. */
    private void deleteRelatedObjects(UOW uow, UserMaintenanceDeleteInDto input, User user) throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_deleteRelatedObjects_1_be
        // .//GEN-BEGIN:_deleteRelatedObjects_UserRole_1_be
        if (user.getUserName() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(UserRoleMeta.getName());
            criteria.addCriteria(UserRoleMeta.USER_NAME, user.getUserName());
            criteria.setLocking(Criteria.LOCKING_PARANOID);
            // .//GEN-END:_deleteRelatedObjects_UserRole_1_be
            // Add custom code to set the criteria before the query //GEN-FIRST:_deleteRelatedObjects_UserRole_1


            // .//GEN-LAST:_deleteRelatedObjects_UserRole_1
            // .//GEN-BEGIN:_deleteRelatedObjects_UserRole_4_be
            Iterator itr = uow.query(criteria).iterator();
            while (itr.hasNext()) {
                UserRole userRole = (UserRole) itr.next();
                if (log.isDebugEnabled())
                    log.debug("Deleting the related object " + userRole);
                uow.delete(userRole);
            }
            // .//GEN-END:_deleteRelatedObjects_UserRole_4_be
        // .//GEN-BEGIN:_deleteRelatedObjects_UserRole_6_be
        }
        // .//GEN-END:_deleteRelatedObjects_UserRole_6_be
        // Add custom code //GEN-FIRST:_deleteRelatedObjects_1


        // .//GEN-LAST:_deleteRelatedObjects_1
        // .//GEN-BEGIN:_deleteRelatedObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_deleteRelatedObjects_2_be
    // .//GEN-BEGIN:_domainObjectChangedTest_1_be
    /** Ensure the domain object has not been modified. */
    private void domainObjectChangedTest(Boolean performDirtyReadCheck, User domain, org.jaffa.datatypes.DateTime lastUpdatedOn)
    throws FrameworkException, ApplicationExceptions {
        if (performDirtyReadCheck != null && performDirtyReadCheck.booleanValue()) {
            if (lastUpdatedOn == null ? domain.getLastUpdatedOn() != null : !lastUpdatedOn.equals(domain.getLastUpdatedOn()) ) {
                DomainObjectChangedException e = new DomainObjectChangedException(domain.getLastUpdatedBy(), Formatter.format(domain.getLastUpdatedOn()));
                ApplicationExceptions aes = new ApplicationExceptions();
                aes.add(e);
                throw aes;
            }
        }
    }
    // .//GEN-END:_domainObjectChangedTest_1_be
    // All the custom code goes here //GEN-FIRST:_custom
    private boolean hasMaintenanceAccess() {
        return hasAccess("Jaffa.Admin.User.Maintenance");
    }

    private boolean hasSelfUpdateAccess() {
        return hasAccess("Jaffa.Admin.User.SelfUpdate");
    }

    private boolean hasAccess(String functionName) {
        boolean access;
        try {
            SecurityManager.runFunction(functionName, new PrivilegedAction() {
                public Object run() {
                    return null;
                }
            });
            access = true;
        } catch(Exception e) {
            access = false;
        }
        return access;
    }

    private void performUserRoleValidations(String[] userRole, User domain)
    throws ApplicationExceptions {
        ApplicationExceptions appExps = null;

        boolean foundExcludedRole = false;
        RoleManager roleManager = PolicyManager.getRoleManager();
        Roles root = (null != roleManager) ? roleManager.getRoles() : null;
        if(root != null) {
            List roleObjects = root.getRole();
            if(roleObjects != null) {
                for (Iterator it = roleObjects.iterator(); it.hasNext(); ) {
                    Role role = (Role) it.next();
                    if (Arrays.binarySearch(userRole, role.getName()) >= 0) {
                        List includes = role.getInclude();
                        if (includes != null) {
                            for(Iterator it2 = includes.iterator(); it2.hasNext(); ) {
                                Include includedObject = (Include) it2.next();
                                String includeName = includedObject.getName();
                                if (Arrays.binarySearch(userRole, includeName) < 0) {
                                    if (appExps == null)
                                        appExps = new ApplicationExceptions();
                                    appExps.add(new UserMaintenanceException(UserMaintenanceException.PROP_INCLUDED_ROLE_MISSING, role.getName(), includeName));
                                }
                            }
                        }

                        List excludes = role.getExclude();
                        if (excludes != null && !foundExcludedRole) {
                            for(Iterator it2 = excludes.iterator(); it2.hasNext(); ) {
                                Exclude excludedObject = (Exclude)it2.next();
                                String  excludeName = excludedObject.getName();
                                if (Arrays.binarySearch(userRole, excludeName) >= 0) {
                                    if (appExps == null)
                                        appExps = new ApplicationExceptions();
                                    appExps.add(new UserMaintenanceException(UserMaintenanceException.PROP_EXCLUDED_ROLE_PRESENT, role.getName(), excludeName));
                                    foundExcludedRole = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (appExps != null && appExps.size() > 0)
            throw appExps;

    }

    private String oldPassword = new String();
    private String oldStatus = new String();

    // .//GEN-LAST:_custom
}
