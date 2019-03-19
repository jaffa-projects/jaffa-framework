// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formgroupmaintenance.tx;

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

import org.jaffa.modules.printing.components.formgroupmaintenance.IFormGroupMaintenance;
import org.jaffa.modules.printing.components.formgroupmaintenance.dto.*;
import org.jaffa.modules.printing.domain.FormGroup;
import org.jaffa.modules.printing.domain.FormGroupMeta;


import org.jaffa.modules.printing.domain.FormUsage;
import org.jaffa.modules.printing.domain.FormUsageMeta;
import org.jaffa.modules.printing.domain.FormEvent;
import org.jaffa.modules.printing.domain.FormEventMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports

import org.jaffa.modules.printing.domain.FormDefinition;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;

// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Maintenance for FormGroup objects.
*/
public class FormGroupMaintenanceTx extends MaintTx implements IFormGroupMaintenance {

    private static Logger log = Logger.getLogger(FormGroupMaintenanceTx.class);

    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_destroy_1_be
    /**
     * This should be invoked, when done with the component.
     */
    public void destroy() {
        // .//GEN-END:_destroy_1_be
        // Add custom code//GEN-FIRST:_destroy_1


        // .//GEN-LAST:_destroy_1
        // .//GEN-BEGIN:_destroy_2_be
    }
    // .//GEN-END:_destroy_2_be
    // .//GEN-BEGIN:_prevalidateCreate_1_be
    /** This method is used to perform prevalidations before creating a new instance of FormGroup.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormGroupMaintenancePrevalidateOutDto prevalidateCreate(FormGroupMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormGroup domain = createDomain(uow, input, true);

            // Perform post create processing
            postCreate(uow, input, domain, true);

            // Build the outbound dto
            FormGroupMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

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
    /** Persists a new instance of FormGroup.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormGroupMaintenanceRetrieveOutDto create(FormGroupMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormGroup domain = createDomain(uow, input, false);
            uow.add(domain);

            // Perform post create processing
            postCreate(uow, input, domain, false);

            // Commit the changes
            uow.commit();

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Successfully created the domain object. Now retrieving the object details.");

            // Build the outbound dto by performing a retrieve
            FormGroupMaintenanceRetrieveInDto retrieveInDto = new FormGroupMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setFormName(input.getFormName());
            FormGroupMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
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
    /** Returns the details for FormGroup.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */
    public FormGroupMaintenanceRetrieveOutDto retrieve(FormGroupMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormGroup domain = load(uow, input);

            // Convert the domain objects into the outbound dto
            FormGroupMaintenanceRetrieveOutDto output = buildRetrieveOutDto(uow, input, domain);

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
    /** This method is used to perform prevalidations before updating an existing instance of FormGroup.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormGroupMaintenancePrevalidateOutDto prevalidateUpdate(FormGroupMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormGroup domain = load(uow, input);

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Update the domain object
            updateDomain(uow, input, domain, true);


            // Build the outbound dto
            FormGroupMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

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
    /** Updates an existing instance of FormGroup.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormGroupMaintenanceRetrieveOutDto update(FormGroupMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormGroup domain = load(uow, input);

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
            FormGroupMaintenanceRetrieveInDto retrieveInDto = new FormGroupMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setFormName(input.getFormName());
            FormGroupMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
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
    /** Deletes an existing instance of FormGroup.
     * @param input The key values for the domain object to be deleted.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(FormGroupMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
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

    /** Deletes an existing instance of FormGroup.
     * @param input The key values for the domain object to be deleted.
     * @param uow The delete will be performed using the input UOW.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(FormGroupMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions {
        // Print Debug Information for the input
        if (log.isDebugEnabled())
            log.debug("Input: " + input);

        // Preprocess the input
        preprocess(uow, input);

        // Retrieve the object
        FormGroup domain = load(uow, input);

        // Delete the domain object
        deleteDomain(uow, input, domain);

        // Print Debug Information for the output
        if (log.isDebugEnabled())
            log.debug("The domain object has been marked for deletion. It will be deleted when the UOW is committed");
    }
    // .//GEN-END:_delete_1_be
    // .//GEN-BEGIN:_preprocessCreate_1_be
    /** Preprocess the input for the create method. */
    private void preprocess(UOW uow, FormGroupMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessCreate_1_be
        // Add custom code//GEN-FIRST:_preprocessCreate_1


        // .//GEN-LAST:_preprocessCreate_1
        // .//GEN-BEGIN:_preprocessCreate_2_be
    }
    // .//GEN-END:_preprocessCreate_2_be
    // .//GEN-BEGIN:_duplicateCheck_1_be
    /** Ensure that a duplicate record is not created. */
    private void duplicateCheck(UOW uow, FormGroupMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_duplicateCheck_1_be
        // Add custom code//GEN-FIRST:_duplicateCheck_1


        // .//GEN-LAST:_duplicateCheck_1
        // .//GEN-BEGIN:_duplicateCheck_2_be
        if (input.getFormName() == null)
            return;
        Criteria criteria = new Criteria();
        criteria.setTable( FormGroupMeta.getName() );
        // .//GEN-END:_duplicateCheck_2_be
        // Add custom criteria//GEN-FIRST:_duplicateCheck_2


        // .//GEN-LAST:_duplicateCheck_2
        // .//GEN-BEGIN:_duplicateCheck_3_be
        criteria.addCriteria(FormGroupMeta.FORM_NAME, input.getFormName());
        Collection col = uow.query(criteria);
        // .//GEN-END:_duplicateCheck_3_be
        // Add custom code//GEN-FIRST:_duplicateCheck_3


        // .//GEN-LAST:_duplicateCheck_3
        // .//GEN-BEGIN:_duplicateCheck_4_be
        if (col != null && !col.isEmpty()) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DuplicateKeyException(FormGroupMeta.getLabelToken()));
            throw appExps;
        }
    }
    // .//GEN-END:_duplicateCheck_4_be
    // .//GEN-BEGIN:_createDomain_1_be
    /** Create the domain object. */
    private FormGroup createDomain(UOW uow, FormGroupMaintenanceCreateInDto input, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        FormGroup domain = new FormGroup();
        ApplicationExceptions appExps = null;
        // .//GEN-END:_createDomain_1_be
        // Add custom code//GEN-FIRST:_createDomain_1


        // .//GEN-LAST:_createDomain_1
        // .//GEN-BEGIN:_createDomain_2_be
        try {
            domain.updateFormName(input.getFormName());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDescription(input.getDescription());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFormType(input.getFormType());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        // .//GEN-END:_createDomain_2_be
        // Add custom code//GEN-FIRST:_createDomain_2


        // .//GEN-LAST:_createDomain_2
        // .//GEN-BEGIN:_createDomain_3_be
        if (appExps != null && appExps.size() > 0)
            throw appExps;
        return domain;
    }
    // .//GEN-END:_createDomain_3_be
    // .//GEN-BEGIN:_postCreate_1_be
    /** This method is invoked after the domain object has been created.*/
    private void postCreate(UOW uow, FormGroupMaintenanceCreateInDto input, FormGroup domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_postCreate_1_be
        // Add custom code//GEN-FIRST:_postCreate_1
        //createFormUsageEntry(uow, input, fromPrevalidate);

        // .//GEN-LAST:_postCreate_1
        // .//GEN-BEGIN:_postCreate_2_be
    }
    // .//GEN-END:_postCreate_2_be
    // .//GEN-BEGIN:_preprocessRetrieve_1_be
    /** Preprocess the input for the retrieve method. */
    private void preprocess(UOW uow, FormGroupMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessRetrieve_1_be
        // Add custom code//GEN-FIRST:_preprocessRetrieve_1


        // .//GEN-LAST:_preprocessRetrieve_1
        // .//GEN-BEGIN:_preprocessRetrieve_2_be
    }
    // .//GEN-END:_preprocessRetrieve_2_be
    // .//GEN-BEGIN:_loadRetrieve_1_be
    /** Retrieve the domain object. */
    private FormGroup load(UOW uow, FormGroupMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        FormGroup domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormGroupMeta.getName() );
        // .//GEN-END:_loadRetrieve_1_be
        // Add custom criteria//GEN-FIRST:_loadRetrieve_1


        // .//GEN-LAST:_loadRetrieve_1
        // .//GEN-BEGIN:_loadRetrieve_2_be
        criteria.addCriteria(FormGroupMeta.FORM_NAME, input.getFormName());
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormGroup) itr.next();
        // .//GEN-END:_loadRetrieve_2_be
        // Add custom code//GEN-FIRST:_loadRetrieve_2


        // .//GEN-LAST:_loadRetrieve_2
        // .//GEN-BEGIN:_loadRetrieve_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormGroupMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadRetrieve_3_be
    // .//GEN-BEGIN:_buildRetrieveOutDto_1_be
    /** Create the RetrieveOutDto. */
    private FormGroupMaintenanceRetrieveOutDto buildRetrieveOutDto(UOW uow, FormGroupMaintenanceRetrieveInDto input, FormGroup domain) throws FrameworkException, ApplicationExceptions {
        FormGroupMaintenanceRetrieveOutDto output = new FormGroupMaintenanceRetrieveOutDto();
        // .//GEN-END:_buildRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_buildRetrieveOutDto_1


        // .//GEN-LAST:_buildRetrieveOutDto_1
        // .//GEN-BEGIN:_buildRetrieveOutDto_2_be
        output.setFormName(domain.getFormName());
        output.setDescription(domain.getDescription());
        output.setFormType(domain.getFormType());
        addForeignObjectsToRetrieveOut(uow, domain, output);
        addRelatedDtosToRetrieveOut(uow, domain, output);
        // .//GEN-END:_buildRetrieveOutDto_2_be
        // Add custom code//GEN-FIRST:_buildRetrieveOutDto_2


        // .//GEN-LAST:_buildRetrieveOutDto_2
        // .//GEN-BEGIN:_buildRetrieveOutDto_3_be
        return output;
    }
    // .//GEN-END:_buildRetrieveOutDto_3_be
    // .//GEN-BEGIN:_preprocessUpdate_1_be
    /** Preprocess the input for the update method. */
    private void preprocess(UOW uow, FormGroupMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessUpdate_1_be
        // Add custom code//GEN-FIRST:_preprocessUpdate_1


        // .//GEN-LAST:_preprocessUpdate_1
        // .//GEN-BEGIN:_preprocessUpdate_2_be
    }
    // .//GEN-END:_preprocessUpdate_2_be
    // .//GEN-BEGIN:_loadUpdate_1_be
    /** Retrieve the domain object. */
    private FormGroup load(UOW uow, FormGroupMaintenanceUpdateInDto input)
    throws FrameworkException, ApplicationExceptions {
        FormGroup domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormGroupMeta.getName() );
        // .//GEN-END:_loadUpdate_1_be
        // Add custom criteria//GEN-FIRST:_loadUpdate_1


        // .//GEN-LAST:_loadUpdate_1
        // .//GEN-BEGIN:_loadUpdate_2_be
        criteria.addCriteria(FormGroupMeta.FORM_NAME, input.getFormName());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormGroup) itr.next();
        // .//GEN-END:_loadUpdate_2_be
        // Add custom code//GEN-FIRST:_loadUpdate_2


        // .//GEN-LAST:_loadUpdate_2
        // .//GEN-BEGIN:_loadUpdate_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormGroupMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadUpdate_3_be
    // .//GEN-BEGIN:_updateDomain_1_be
    /** Update the domain object and add it to the UOW. */
    private void updateDomain(UOW uow, FormGroupMaintenanceUpdateInDto input, FormGroup domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_updateDomain_1_be
        // Add custom code//GEN-FIRST:_updateDomain_1


        // .//GEN-LAST:_updateDomain_1
        // .//GEN-BEGIN:_updateDomain_2_be
        try {
            domain.updateDescription(input.getDescription());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFormType(input.getFormType());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        // .//GEN-END:_updateDomain_2_be
        // Add custom code//GEN-FIRST:_updateDomain_2
        // creating the FormUsage entries.
        createFormUsageEntry(uow, input,fromPrevalidate);

        // .//GEN-LAST:_updateDomain_2
        // .//GEN-BEGIN:_updateDomain_3_be
        if (appExps != null && appExps.size() > 0)
            throw appExps;
    }
    // .//GEN-END:_updateDomain_3_be
    // .//GEN-BEGIN:_preprocessDelete_1_be
    /** Preprocess the input for the delete method. */
    private void preprocess(UOW uow, FormGroupMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessDelete_1_be
        // Add custom code//GEN-FIRST:_preprocessDelete_1

        if (input.getFormName() != null){
            Criteria criteria = new Criteria();
            criteria.setTable( FormDefinitionMeta.getName() );
            criteria.addCriteria(FormDefinitionMeta.FORM_NAME, input.getFormName());
            Collection col = uow.query(criteria);
            Iterator itr = col.iterator();
            if(itr.hasNext()) {
                throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormGroupMaintenance.formNameExistInFormDefinitions") {});
            }
        }
        // .//GEN-LAST:_preprocessDelete_1
        // .//GEN-BEGIN:_preprocessDelete_2_be
    }
    // .//GEN-END:_preprocessDelete_2_be
    // .//GEN-BEGIN:_loadDelete_1_be
    /** Retrieve the domain object. */
    private FormGroup load(UOW uow, FormGroupMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        FormGroup domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormGroupMeta.getName() );
        // .//GEN-END:_loadDelete_1_be
        // Add custom criteria//GEN-FIRST:_loadDelete_1


        // .//GEN-LAST:_loadDelete_1
        // .//GEN-BEGIN:_loadDelete_2_be
        criteria.addCriteria(FormGroupMeta.FORM_NAME, input.getFormName());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormGroup) itr.next();
        // .//GEN-END:_loadDelete_2_be
        // Add custom code//GEN-FIRST:_loadDelete_2


        // .//GEN-LAST:_loadDelete_2
        // .//GEN-BEGIN:_loadDelete_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormGroupMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadDelete_3_be
    // .//GEN-BEGIN:_deleteDomain_1_be
    /** Delete the domain object from the domain. */
    private void deleteDomain(UOW uow, FormGroupMaintenanceDeleteInDto input, FormGroup domain) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_deleteDomain_1_be
        // Add custom code//GEN-FIRST:_deleteDomain_1


        // .//GEN-LAST:_deleteDomain_1
        // .//GEN-BEGIN:_deleteDomain_2_be
        deleteRelatedObjects(uow, input, domain);
        uow.delete(domain);
        // .//GEN-END:_deleteDomain_2_be
        // Add custom code//GEN-FIRST:_deleteDomain_2


        // .//GEN-LAST:_deleteDomain_2
        // .//GEN-BEGIN:_deleteDomain_3_be
    }
    // .//GEN-END:_deleteDomain_3_be
    // .//GEN-BEGIN:_addForeignObjectsToRetrieveOut_1_be
    /** Add foreign objects to FormGroupMaintenanceRetrieveOutDto */
    private void addForeignObjectsToRetrieveOut(UOW uow, FormGroup domain, FormGroupMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {
    }
    // .//GEN-END:_addForeignObjectsToRetrieveOut_1_be
    // .//GEN-BEGIN:_validateForeignObjects_1_be
    /** This will validate the Foreign Objects. */
    private void validateForeignObjects(UOW uow, FormGroupMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_validateForeignObjects_1_be
        // .//GEN-BEGIN:_validateForeignObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_validateForeignObjects_2_be
    // .//GEN-BEGIN:_createPrevalidateOutDto_1_be
    private FormGroupMaintenancePrevalidateOutDto createPrevalidateOutDto(UOW uow, FormGroup domain, FormGroupMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        FormGroupMaintenancePrevalidateOutDto output = new FormGroupMaintenancePrevalidateOutDto();
        output.setFormName(domain.getFormName());
        output.setDescription(domain.getDescription());
        output.setFormType(domain.getFormType());
        addForeignObjectsToRetrieveOut(uow, domain, output);
        addRelatedDtosToRetrieveOut(uow, domain, output);
        // .//GEN-END:_createPrevalidateOutDto_1_be
        // Add custom code//GEN-FIRST:_createPrevalidateOutDto_1


        // .//GEN-LAST:_createPrevalidateOutDto_1
        // .//GEN-BEGIN:_createPrevalidateOutDto_2_be
        return output;
    }
    // .//GEN-END:_createPrevalidateOutDto_2_be
    // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_1_be
    /** Add related objects to FormGroupMaintenanceRetrieveOutDto */
    private void addRelatedDtosToRetrieveOut(UOW uow, FormGroup formGroup, FormGroupMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_addRelatedDtosToRetrieveOut_1_be
        // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormUsage_1_be
        if (formGroup.getFormName() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(FormUsageMeta.getName());
            criteria.addCriteria(FormUsageMeta.FORM_NAME, formGroup.getFormName());
            criteria.addOrderBy("EventName", Criteria.ORDER_BY_ASC);
            // .//GEN-END:_addRelatedDtosToRetrieveOut_FormUsage_1_be
            // Add custom code to set the criteria before the query//GEN-FIRST:_addRelatedDtosToRetrieveOut_FormUsage_1


            // .//GEN-LAST:_addRelatedDtosToRetrieveOut_FormUsage_1
            // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormUsage_2_be
            Iterator itrMany = uow.query(criteria).iterator();
            while (itrMany.hasNext()) {
                FormUsage formUsage = (FormUsage) itrMany.next();
                FormUsageDto dto = new FormUsageDto();
                // .//GEN-END:_addRelatedDtosToRetrieveOut_FormUsage_2_be
                // Add custom code before all the setters//GEN-FIRST:_addRelatedDtosToRetrieveOut_FormUsage_2


                // .//GEN-LAST:_addRelatedDtosToRetrieveOut_FormUsage_2
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormUsage_EventName_1_be
                dto.setEventName(formUsage.getEventName());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_FormUsage_EventName_1_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormUsage_FormAlternate_1_be
                dto.setFormAlternate(formUsage.getFormAlternate());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_FormUsage_FormAlternate_1_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormUsage_Copies_1_be
                dto.setCopies(formUsage.getCopies());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_FormUsage_Copies_1_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormUsage_Description_1_be
                FormEvent chainFormEventEventNameEventName = null;
                {
                    if (formUsage.getEventName() != null) {
                        Criteria criteriaFormEvent = new Criteria();
                        criteriaFormEvent.setTable(FormEventMeta.getName());
                        criteriaFormEvent.addCriteria(FormEventMeta.EVENT_NAME, formUsage.getEventName());
                        // .//GEN-END:_addRelatedDtosToRetrieveOut_FormUsage_Description_1_be
                        // Add custom code to modify the criteria//GEN-FIRST:_addRelatedDtosToRetrieveOut_FormUsage_Description_1


                        // .//GEN-LAST:_addRelatedDtosToRetrieveOut_FormUsage_Description_1
                        // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormUsage_Description_2_be
                        Iterator i = uow.query(criteriaFormEvent).iterator();
                        if (i.hasNext() )
                            chainFormEventEventNameEventName = (FormEvent) i.next();
                    }
                }
                // .//GEN-END:_addRelatedDtosToRetrieveOut_FormUsage_Description_2_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormUsage_Description_3_be
                if (dto != null && chainFormEventEventNameEventName != null) {
                    dto.setDescription(chainFormEventEventNameEventName.getDescription());
                }
                // .//GEN-END:_addRelatedDtosToRetrieveOut_FormUsage_Description_3_be
                // Add custom code to pass values to the dto//GEN-FIRST:_addRelatedDtosToRetrieveOut_FormUsage_3


                // .//GEN-LAST:_addRelatedDtosToRetrieveOut_FormUsage_3
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormUsage_3_be
                output.addFormUsage(dto);
            }
            // .//GEN-END:_addRelatedDtosToRetrieveOut_FormUsage_3_be
            // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormUsage_6_be
        }

        // .//GEN-END:_addRelatedDtosToRetrieveOut_FormUsage_6_be
        // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_2_be
    }
    // .//GEN-END:_addRelatedDtosToRetrieveOut_2_be
    // .//GEN-BEGIN:_deleteRelatedObjects_1_be
    /** Delete the related domain objects if the 'Cascading' constraint is specified. Throw an exception in case 'Restricted' constraint is specified. */
    private void deleteRelatedObjects(UOW uow, FormGroupMaintenanceDeleteInDto input, FormGroup formGroup) throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_deleteRelatedObjects_1_be
        // .//GEN-BEGIN:_deleteRelatedObjects_FormUsage_1_be
        if (formGroup.getFormName() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(FormUsageMeta.getName());
            criteria.addCriteria(FormUsageMeta.FORM_NAME, formGroup.getFormName());
            // .//GEN-END:_deleteRelatedObjects_FormUsage_1_be
            // Add custom code to set the criteria before the query//GEN-FIRST:_deleteRelatedObjects_FormUsage_1


            // .//GEN-LAST:_deleteRelatedObjects_FormUsage_1
            // .//GEN-BEGIN:_deleteRelatedObjects_FormUsage_5_be
            Collection col = uow.query(criteria);
            Iterator itr = col.iterator();
            if (itr.hasNext()) {
                col.clear();
                if (log.isDebugEnabled())
                    log.debug("The related formUsage object having 'Restricted Delete Constraint' was found. Delete cannot be performed");
                if (appExps == null)
                    appExps = new ApplicationExceptions();
                appExps.add(new RelatedDomainObjectFoundException(FormUsageMeta.getLabelToken()));
            }
            // .//GEN-END:_deleteRelatedObjects_FormUsage_5_be
        // .//GEN-BEGIN:_deleteRelatedObjects_FormUsage_6_be
        }
        // .//GEN-END:_deleteRelatedObjects_FormUsage_6_be
        // Add custom code//GEN-FIRST:_deleteRelatedObjects_1


        // .//GEN-LAST:_deleteRelatedObjects_1
        // .//GEN-BEGIN:_deleteRelatedObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_deleteRelatedObjects_2_be
    // All the custom code goes here//GEN-FIRST:_custom
    // Add the related FormUsage
    private void createFormUsageEntry(UOW uow, FormGroupMaintenanceUpdateInDto input, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        try {
            if (input.getFormName() != null) {
                Criteria criteria = new Criteria();
                criteria.setTable(FormUsageMeta.getName());
                criteria.addCriteria(FormUsageMeta.FORM_NAME, input.getFormName());
                Collection col = uow.query(criteria);
                //Iterator itr = col.iterator();
                for (int i = 0; i < input.getFormUsageCount(); i++) {
                    boolean found=false;
                    FormUsageDto inputFormUsageDto = input.getFormUsage(i);
                    for(Iterator itr=col.iterator();itr.hasNext();){
                        FormUsage formusage = (FormUsage)itr.next();
                        if(inputFormUsageDto.getEventName().equals(formusage.getEventName())){
                            try {
                                formusage.updateFormAlternate(inputFormUsageDto.getFormAlternate());
                            } catch (ValidationException e) {
                                if (appExps == null)
                                    appExps = new ApplicationExceptions();
                                appExps.add(e);
                            }
                            try {
                                formusage.updateCopies(inputFormUsageDto.getCopies());
                            } catch (ValidationException e) {
                                if (appExps == null)
                                    appExps = new ApplicationExceptions();
                                appExps.add(e);
                            }
                            if (appExps != null && appExps.size() > 0)
                                throw appExps;

                            if (!fromPrevalidate) {
                                if (log.isDebugEnabled())
                                    log.debug("Creating FormUsage: " + formusage);
                                uow.update(formusage);
                                found=true;
                            }
                            break;
                        }else{
                            continue;
                        }
                    }
                    if(!found){
                        FormUsage formUsage = new FormUsage();
                        formUsage.setEventName(inputFormUsageDto.getEventName());
                        formUsage.setFormName(input.getFormName());
                        formUsage.setFormAlternate(inputFormUsageDto.getFormAlternate());
                        formUsage.setCopies(inputFormUsageDto.getCopies());
                        if (!fromPrevalidate) {
                            if (log.isDebugEnabled())
                                log.debug("Creating FormUsage: " + formUsage);
                            uow.add(formUsage);
                        }
                    }
                }
                for(Iterator itr=col.iterator();itr.hasNext();){
                    boolean found = false;
                    FormUsage formusage = (FormUsage)itr.next();
                    for (int i = 0; i < input.getFormUsageCount(); i++) {
                        FormUsageDto inputFormUsageDto = input.getFormUsage(i);
                        if(formusage.getEventName().equals(inputFormUsageDto.getEventName())){
                            found=true;
                            break;
                        }
                    }
                    if(!found){
                        if (!fromPrevalidate) {
                            if (log.isDebugEnabled())
                                log.debug("Creating FormUsage: " + formusage);
                            uow.delete(formusage);
                        }
                    }
                }
            }
        } catch (ValidationException e) {
            throw new ApplicationExceptions(e);
        }
    }

    // for FormDefinition validations;
    public boolean doValidations(String formName , String formAlternate) throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions ae = new ApplicationExceptions();
        UOW uow=null;
        boolean flag=true;
        try{
            if(formName != null){
                uow=new UOW();
                Criteria criteria = new Criteria();
                criteria.setTable(FormDefinitionMeta.getName());
                criteria.addCriteria(FormDefinitionMeta.FORM_NAME, formName);
                if(formAlternate != null)
                    criteria.addCriteria(FormDefinitionMeta.FORM_ALTERNATE, formAlternate);
                else
                    criteria.addCriteria(FormDefinitionMeta.FORM_ALTERNATE,Criteria.RELATIONAL_IS_NULL);

                Iterator itr = uow.query(criteria).iterator();
                if(!itr.hasNext())
                    flag=false;
            }else{
                flag=false;
            }
        }finally {
            if (uow != null) {
                uow.rollback();
            }
        }
        return flag;
    }


    // .//GEN-LAST:_custom
}
