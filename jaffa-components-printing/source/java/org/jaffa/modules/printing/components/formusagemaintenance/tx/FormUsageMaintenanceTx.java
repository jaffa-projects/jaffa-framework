// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formusagemaintenance.tx;

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
import org.jaffa.components.maint.MaintTx;
import org.jaffa.components.voucher.IVoucherGenerator;
import org.jaffa.components.voucher.VoucherGeneratorException;
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;

import org.jaffa.modules.printing.components.formusagemaintenance.IFormUsageMaintenance;
import org.jaffa.modules.printing.components.formusagemaintenance.dto.*;
import org.jaffa.modules.printing.domain.FormUsage;
import org.jaffa.modules.printing.domain.FormUsageMeta;

import org.jaffa.modules.printing.domain.FormEvent;
import org.jaffa.modules.printing.domain.FormEventMeta;
import org.jaffa.modules.printing.domain.FormGroup;
import org.jaffa.modules.printing.domain.FormGroupMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Maintenance for FormUsage objects.
*/
public class FormUsageMaintenanceTx extends MaintTx implements IFormUsageMaintenance {

    private static Logger log = Logger.getLogger(FormUsageMaintenanceTx.class);

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
    /** This method is used to perform prevalidations before creating a new instance of FormUsage.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormUsageMaintenancePrevalidateOutDto prevalidateCreate(FormUsageMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled())
                log.debug("Input: " + input);

            // create the UOW
            uow = new UOW();

            // Preprocess the input
            preprocess(uow, input);

            // Do not allow a Duplicate record
            duplicateCheck(uow, input);

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Create the domain object
            FormUsage domain = createDomain(uow, input, true);

            // Perform post create processing
            postCreate(uow, input, domain, true);

            // Build the outbound dto
            FormUsageMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Output: " + output);
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
    /** Persists a new instance of FormUsage.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormUsageMaintenanceRetrieveOutDto create(FormUsageMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled())
                log.debug("Input: " + input);

            // create the UOW
            uow = new UOW();

            // Preprocess the input
            preprocess(uow, input);

            // Do not allow a Duplicate record
            duplicateCheck(uow, input);

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Create the domain object
            FormUsage domain = createDomain(uow, input, false);
            uow.add(domain);

            // Perform post create processing
            postCreate(uow, input, domain, false);

            // Commit the changes
            uow.commit();

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Successfully created the domain object. Now retrieving the object details.");

            // Build the outbound dto by performing a retrieve
            FormUsageMaintenanceRetrieveInDto retrieveInDto = new FormUsageMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setFormName(input.getFormName());
            FormUsageMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
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
    /** Returns the details for FormUsage.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */
    public FormUsageMaintenanceRetrieveOutDto retrieve(FormUsageMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled())
                log.debug("Input: " + input);

            // create the UOW
            uow = new UOW();

            // Preprocess the input
            preprocess(uow, input);

            // Retrieve the object
            FormUsage domain = load(uow, input);

            // Convert the domain objects into the outbound dto
            FormUsageMaintenanceRetrieveOutDto output = buildRetrieveOutDto(uow, input, domain);

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Output: " + output);
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
    /** This method is used to perform prevalidations before updating an existing instance of FormUsage.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormUsageMaintenancePrevalidateOutDto prevalidateUpdate(FormUsageMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled())
                log.debug("Input: " + input);

            // create the UOW
            uow = new UOW();

            // Preprocess the input
            preprocess(uow, input);

            // Retrieve the object
            FormUsage domain = load(uow, input);

            // Ensure the domain object has not been modified
            domainObjectChangedTest(input.getPerformDirtyReadCheck(), domain, input.getLastChangedOn());

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Update the domain object
            updateDomain(uow, input, domain, true);


            // Build the outbound dto
            FormUsageMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Output: " + output);
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
    /** Updates an existing instance of FormUsage.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormUsageMaintenanceRetrieveOutDto update(FormUsageMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled())
                log.debug("Input: " + input);

            // create the UOW
            uow = new UOW();

            // Preprocess the input
            preprocess(uow, input);

            // Retrieve the object
            FormUsage domain = load(uow, input);

            // Ensure the domain object has not been modified
            domainObjectChangedTest(input.getPerformDirtyReadCheck(), domain, input.getLastChangedOn());

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
            FormUsageMaintenanceRetrieveInDto retrieveInDto = new FormUsageMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setFormName(input.getFormName());
            FormUsageMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
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
    /** Deletes an existing instance of FormUsage.
     * @param input The key values for the domain object to be deleted.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(FormUsageMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
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

    /** Deletes an existing instance of FormUsage.
     * @param input The key values for the domain object to be deleted.
     * @param uow The delete will be performed using the input UOW.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(FormUsageMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions {
        // Print Debug Information for the input
        if (log.isDebugEnabled())
            log.debug("Input: " + input);

        // Preprocess the input
        preprocess(uow, input);

        // Retrieve the object
        FormUsage domain = load(uow, input);

        // Ensure the domain object has not been modified
        domainObjectChangedTest(input.getPerformDirtyReadCheck(), domain, input.getLastChangedOn());

        // Delete the domain object
        deleteDomain(uow, input, domain);

        // Print Debug Information for the output
        if (log.isDebugEnabled())
            log.debug("The domain object has been marked for deletion. It will be deleted when the UOW is committed");
    }
    // .//GEN-END:_delete_1_be
    // .//GEN-BEGIN:_preprocessCreate_1_be
    /** Preprocess the input for the create method. */
    private void preprocess(UOW uow, FormUsageMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessCreate_1_be
        // Add custom code //GEN-FIRST:_preprocessCreate_1


        // .//GEN-LAST:_preprocessCreate_1
        // .//GEN-BEGIN:_preprocessCreate_2_be
    }
    // .//GEN-END:_preprocessCreate_2_be
    // .//GEN-BEGIN:_duplicateCheck_1_be
    /** Ensure that a duplicate record is not created. */
    private void duplicateCheck(UOW uow, FormUsageMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_duplicateCheck_1_be
        // Add custom code //GEN-FIRST:_duplicateCheck_1


        // .//GEN-LAST:_duplicateCheck_1
        // .//GEN-BEGIN:_duplicateCheck_2_be
        if (input.getFormName() == null)
            return;
        Criteria criteria = new Criteria();
        criteria.setTable( FormUsageMeta.getName() );
        // .//GEN-END:_duplicateCheck_2_be
        // Add custom criteria //GEN-FIRST:_duplicateCheck_2


        // .//GEN-LAST:_duplicateCheck_2
        // .//GEN-BEGIN:_duplicateCheck_3_be
        criteria.addCriteria(FormUsageMeta.FORM_NAME, input.getFormName());
        Collection col = uow.query(criteria);
        // .//GEN-END:_duplicateCheck_3_be
        // Add custom code //GEN-FIRST:_duplicateCheck_3


        // .//GEN-LAST:_duplicateCheck_3
        // .//GEN-BEGIN:_duplicateCheck_4_be
        if (col != null && !col.isEmpty()) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DuplicateKeyException(FormUsageMeta.getLabelToken()));
            throw appExps;
        }
    }
    // .//GEN-END:_duplicateCheck_4_be
    // .//GEN-BEGIN:_createDomain_1_be
    /** Create the domain object. */
    private FormUsage createDomain(UOW uow, FormUsageMaintenanceCreateInDto input, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        FormUsage domain = new FormUsage();
        ApplicationExceptions appExps = null;
        // .//GEN-END:_createDomain_1_be
        // Add custom code //GEN-FIRST:_createDomain_1


        // .//GEN-LAST:_createDomain_1
        // .//GEN-BEGIN:_createDomain_2_be
        try {
            domain.updateFormAlternate(input.getFormAlternate());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateCopies(input.getCopies());
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
        try {
            domain.updateEventName(input.getEventName());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFormName(input.getFormName());
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
    private void postCreate(UOW uow, FormUsageMaintenanceCreateInDto input, FormUsage domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_postCreate_1_be
        // Add custom code //GEN-FIRST:_postCreate_1


        // .//GEN-LAST:_postCreate_1
        // .//GEN-BEGIN:_postCreate_2_be
    }
    // .//GEN-END:_postCreate_2_be
    // .//GEN-BEGIN:_preprocessRetrieve_1_be
    /** Preprocess the input for the retrieve method. */
    private void preprocess(UOW uow, FormUsageMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessRetrieve_1_be
        // Add custom code //GEN-FIRST:_preprocessRetrieve_1


        // .//GEN-LAST:_preprocessRetrieve_1
        // .//GEN-BEGIN:_preprocessRetrieve_2_be
    }
    // .//GEN-END:_preprocessRetrieve_2_be
    // .//GEN-BEGIN:_loadRetrieve_1_be
    /** Retrieve the domain object. */
    private FormUsage load(UOW uow, FormUsageMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        FormUsage domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormUsageMeta.getName() );
        // .//GEN-END:_loadRetrieve_1_be
        // Add custom criteria //GEN-FIRST:_loadRetrieve_1


        // .//GEN-LAST:_loadRetrieve_1
        // .//GEN-BEGIN:_loadRetrieve_2_be
        criteria.addCriteria(FormUsageMeta.FORM_NAME, input.getFormName());
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormUsage) itr.next();
        // .//GEN-END:_loadRetrieve_2_be
        // Add custom code //GEN-FIRST:_loadRetrieve_2


        // .//GEN-LAST:_loadRetrieve_2
        // .//GEN-BEGIN:_loadRetrieve_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormUsageMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadRetrieve_3_be
    // .//GEN-BEGIN:_buildRetrieveOutDto_1_be
    /** Create the RetrieveOutDto. */
    private FormUsageMaintenanceRetrieveOutDto buildRetrieveOutDto(UOW uow, FormUsageMaintenanceRetrieveInDto input, FormUsage domain) throws FrameworkException, ApplicationExceptions {
        FormUsageMaintenanceRetrieveOutDto output = new FormUsageMaintenanceRetrieveOutDto();
        // .//GEN-END:_buildRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_buildRetrieveOutDto_1


        // .//GEN-LAST:_buildRetrieveOutDto_1
        // .//GEN-BEGIN:_buildRetrieveOutDto_2_be
        output.setFormAlternate(domain.getFormAlternate());
        output.setCopies(domain.getCopies());
        output.setCreatedOn(domain.getCreatedOn());
        output.setCreatedBy(domain.getCreatedBy());
        output.setLastChangedOn(domain.getLastChangedOn());
        output.setLastChangedBy(domain.getLastChangedBy());
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
    private void preprocess(UOW uow, FormUsageMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessUpdate_1_be
        // Add custom code //GEN-FIRST:_preprocessUpdate_1


        // .//GEN-LAST:_preprocessUpdate_1
        // .//GEN-BEGIN:_preprocessUpdate_2_be
    }
    // .//GEN-END:_preprocessUpdate_2_be
    // .//GEN-BEGIN:_loadUpdate_1_be
    /** Retrieve the domain object. */
    private FormUsage load(UOW uow, FormUsageMaintenanceUpdateInDto input)
    throws FrameworkException, ApplicationExceptions {
        FormUsage domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormUsageMeta.getName() );
        // .//GEN-END:_loadUpdate_1_be
        // Add custom criteria //GEN-FIRST:_loadUpdate_1


        // .//GEN-LAST:_loadUpdate_1
        // .//GEN-BEGIN:_loadUpdate_2_be
        criteria.addCriteria(FormUsageMeta.FORM_NAME, input.getFormName());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormUsage) itr.next();
        // .//GEN-END:_loadUpdate_2_be
        // Add custom code //GEN-FIRST:_loadUpdate_2


        // .//GEN-LAST:_loadUpdate_2
        // .//GEN-BEGIN:_loadUpdate_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormUsageMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadUpdate_3_be
    // .//GEN-BEGIN:_updateDomain_1_be
    /** Update the domain object and add it to the UOW. */
    private void updateDomain(UOW uow, FormUsageMaintenanceUpdateInDto input, FormUsage domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_updateDomain_1_be
        // Add custom code //GEN-FIRST:_updateDomain_1


        // .//GEN-LAST:_updateDomain_1
        // .//GEN-BEGIN:_updateDomain_2_be
        try {
            domain.updateFormAlternate(input.getFormAlternate());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateCopies(input.getCopies());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            if (!fromPrevalidate)
                domain.updateLastChangedOn(new DateTime());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            if (!fromPrevalidate && input.getHeaderDto() != null && input.getHeaderDto().getUserId() != null)
                domain.updateLastChangedBy(input.getHeaderDto().getUserId());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateEventName(input.getEventName());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        // .//GEN-END:_updateDomain_2_be
        // Add custom code //GEN-FIRST:_updateDomain_2


        // .//GEN-LAST:_updateDomain_2
        // .//GEN-BEGIN:_updateDomain_3_be
        if (appExps != null && appExps.size() > 0)
            throw appExps;
    }
    // .//GEN-END:_updateDomain_3_be
    // .//GEN-BEGIN:_preprocessDelete_1_be
    /** Preprocess the input for the delete method. */
    private void preprocess(UOW uow, FormUsageMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessDelete_1_be
        // Add custom code //GEN-FIRST:_preprocessDelete_1


        // .//GEN-LAST:_preprocessDelete_1
        // .//GEN-BEGIN:_preprocessDelete_2_be
    }
    // .//GEN-END:_preprocessDelete_2_be
    // .//GEN-BEGIN:_loadDelete_1_be
    /** Retrieve the domain object. */
    private FormUsage load(UOW uow, FormUsageMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        FormUsage domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormUsageMeta.getName() );
        // .//GEN-END:_loadDelete_1_be
        // Add custom criteria //GEN-FIRST:_loadDelete_1


        // .//GEN-LAST:_loadDelete_1
        // .//GEN-BEGIN:_loadDelete_2_be
        criteria.addCriteria(FormUsageMeta.FORM_NAME, input.getFormName());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormUsage) itr.next();
        // .//GEN-END:_loadDelete_2_be
        // Add custom code //GEN-FIRST:_loadDelete_2


        // .//GEN-LAST:_loadDelete_2
        // .//GEN-BEGIN:_loadDelete_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormUsageMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadDelete_3_be
    // .//GEN-BEGIN:_deleteDomain_1_be
    /** Delete the domain object from the domain. */
    private void deleteDomain(UOW uow, FormUsageMaintenanceDeleteInDto input, FormUsage domain) throws FrameworkException, ApplicationExceptions {
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
    /** Add foreign objects to FormUsageMaintenanceRetrieveOutDto */
    private void addForeignObjectsToRetrieveOut(UOW uow, FormUsage domain, FormUsageMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {

        {
            output.setEventName(domain.getEventName());
        }

        {
            output.setFormName(domain.getFormName());
        }
    }
    // .//GEN-END:_addForeignObjectsToRetrieveOut_1_be
    // .//GEN-BEGIN:_validateForeignObjects_1_be
    /** This will validate the Foreign Objects. */
    private void validateForeignObjects(UOW uow, FormUsageMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_validateForeignObjects_1_be
        // .//GEN-BEGIN:_validateForeignObjects_FormEvent_1_be
        {
            if (input.getEventName() != null) {
                Criteria criteriaFormEvent = new Criteria();
                criteriaFormEvent.setTable(FormEventMeta.getName());
                criteriaFormEvent.addCriteria(FormEventMeta.EVENT_NAME, input.getEventName());
                // .//GEN-END:_validateForeignObjects_FormEvent_1_be
                // Add custom code //GEN-FIRST:_validateForeignObjects_FormEvent_1


                // .//GEN-LAST:_validateForeignObjects_FormEvent_1
                // .//GEN-BEGIN:_validateForeignObjects_FormEvent_2_be
                if (criteriaFormEvent != null) {
                    Collection col = uow.query(criteriaFormEvent);
                    if (col.isEmpty()) {
                        if (appExps == null)
                            appExps = new ApplicationExceptions();
                        appExps.add(new InvalidForeignKeyException(FormUsageMeta.META_EVENT_NAME.getLabelToken(), new Object[] {FormEventMeta.getLabelToken(), FormEventMeta.META_EVENT_NAME.getLabelToken()}));
                    } else if (col.size() != 1) {
                        if (appExps == null)
                            appExps = new ApplicationExceptions();
                        appExps.add(new MultipleDomainObjectsFoundException(FormEventMeta.getLabelToken()));
                    }
                }
            }
        }
        // .//GEN-END:_validateForeignObjects_FormEvent_2_be
        // .//GEN-BEGIN:_validateForeignObjects_FormGroup_1_be
        {
            if (input.getFormName() != null) {
                Criteria criteriaFormGroup = new Criteria();
                criteriaFormGroup.setTable(FormGroupMeta.getName());
                criteriaFormGroup.addCriteria(FormGroupMeta.FORM_NAME, input.getFormName());
                // .//GEN-END:_validateForeignObjects_FormGroup_1_be
                // Add custom code //GEN-FIRST:_validateForeignObjects_FormGroup_1


                // .//GEN-LAST:_validateForeignObjects_FormGroup_1
                // .//GEN-BEGIN:_validateForeignObjects_FormGroup_2_be
                if (criteriaFormGroup != null) {
                    Collection col = uow.query(criteriaFormGroup);
                    if (col.isEmpty()) {
                        if (appExps == null)
                            appExps = new ApplicationExceptions();
                        appExps.add(new InvalidForeignKeyException(FormUsageMeta.META_FORM_NAME.getLabelToken(), new Object[] {FormGroupMeta.getLabelToken(), FormGroupMeta.META_FORM_NAME.getLabelToken()}));
                    } else if (col.size() != 1) {
                        if (appExps == null)
                            appExps = new ApplicationExceptions();
                        appExps.add(new MultipleDomainObjectsFoundException(FormGroupMeta.getLabelToken()));
                    }
                }
            }
        }
        // .//GEN-END:_validateForeignObjects_FormGroup_2_be
        // .//GEN-BEGIN:_validateForeignObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_validateForeignObjects_2_be
    // .//GEN-BEGIN:_createPrevalidateOutDto_1_be
    private FormUsageMaintenancePrevalidateOutDto createPrevalidateOutDto(UOW uow, FormUsage domain, FormUsageMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        FormUsageMaintenancePrevalidateOutDto output = new FormUsageMaintenancePrevalidateOutDto();
        output.setFormAlternate(domain.getFormAlternate());
        output.setCopies(domain.getCopies());
        output.setCreatedOn(domain.getCreatedOn());
        output.setCreatedBy(domain.getCreatedBy());
        output.setLastChangedOn(domain.getLastChangedOn());
        output.setLastChangedBy(domain.getLastChangedBy());
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
    /** Add related objects to FormUsageMaintenanceRetrieveOutDto */
    private void addRelatedDtosToRetrieveOut(UOW uow, FormUsage formUsage, FormUsageMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_addRelatedDtosToRetrieveOut_1_be
        // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_2_be
    }
    // .//GEN-END:_addRelatedDtosToRetrieveOut_2_be
    // .//GEN-BEGIN:_deleteRelatedObjects_1_be
    /** Delete the related domain objects if the 'Cascading' constraint is specified. Throw an exception in case 'Restricted' constraint is specified. */
    private void deleteRelatedObjects(UOW uow, FormUsageMaintenanceDeleteInDto input, FormUsage formUsage) throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_deleteRelatedObjects_1_be
        // Add custom code //GEN-FIRST:_deleteRelatedObjects_1


        // .//GEN-LAST:_deleteRelatedObjects_1
        // .//GEN-BEGIN:_deleteRelatedObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_deleteRelatedObjects_2_be
    // .//GEN-BEGIN:_domainObjectChangedTest_1_be
    /** Ensure the domain object has not been modified. */
    private void domainObjectChangedTest(Boolean performDirtyReadCheck, FormUsage domain, org.jaffa.datatypes.DateTime lastChangedOn)
    throws FrameworkException, ApplicationExceptions {
        if (performDirtyReadCheck != null && performDirtyReadCheck.booleanValue()) {
            if (lastChangedOn == null ? domain.getLastChangedOn() != null : !lastChangedOn.equals(domain.getLastChangedOn()) ) {
                DomainObjectChangedException e = new DomainObjectChangedException(domain.getLastChangedBy(), Formatter.format(domain.getLastChangedOn()));
                ApplicationExceptions aes = new ApplicationExceptions();
                aes.add(e);
                throw aes;
            }
        }
    }
    // .//GEN-END:_domainObjectChangedTest_1_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
