// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formtemplatemaintenance.tx;

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

import org.jaffa.modules.printing.components.formtemplatemaintenance.IFormTemplateMaintenance;
import org.jaffa.modules.printing.components.formtemplatemaintenance.dto.*;
import org.jaffa.modules.printing.domain.FormTemplate;
import org.jaffa.modules.printing.domain.FormTemplateMeta;

import org.jaffa.modules.printing.domain.FormDefinition;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Maintenance for FormTemplate objects.
*/
public class FormTemplateMaintenanceTx extends MaintTx implements IFormTemplateMaintenance {

    private static Logger log = Logger.getLogger(FormTemplateMaintenanceTx.class);

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
    /** This method is used to perform prevalidations before creating a new instance of FormTemplate.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormTemplateMaintenancePrevalidateOutDto prevalidateCreate(FormTemplateMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormTemplate domain = createDomain(uow, input, true);

            // Perform post create processing
            postCreate(uow, input, domain, true);

            // Build the outbound dto
            FormTemplateMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

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
    /** Persists a new instance of FormTemplate.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormTemplateMaintenanceRetrieveOutDto create(FormTemplateMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormTemplate domain = createDomain(uow, input, false);
            uow.add(domain);

            // Perform post create processing
            postCreate(uow, input, domain, false);

            // Commit the changes
            uow.commit();

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Successfully created the domain object. Now retrieving the object details.");

            // Build the outbound dto by performing a retrieve
            FormTemplateMaintenanceRetrieveInDto retrieveInDto = new FormTemplateMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setFormId(input.getFormId());
            FormTemplateMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
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
    /** Returns the details for FormTemplate.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */
    public FormTemplateMaintenanceRetrieveOutDto retrieve(FormTemplateMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormTemplate domain = load(uow, input);

            // Convert the domain objects into the outbound dto
            FormTemplateMaintenanceRetrieveOutDto output = buildRetrieveOutDto(uow, input, domain);

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
    /** This method is used to perform prevalidations before updating an existing instance of FormTemplate.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormTemplateMaintenancePrevalidateOutDto prevalidateUpdate(FormTemplateMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormTemplate domain = load(uow, input);

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Update the domain object
            updateDomain(uow, input, domain, true);


            // Build the outbound dto
            FormTemplateMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

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
    /** Updates an existing instance of FormTemplate.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormTemplateMaintenanceRetrieveOutDto update(FormTemplateMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormTemplate domain = load(uow, input);

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
            FormTemplateMaintenanceRetrieveInDto retrieveInDto = new FormTemplateMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setFormId(input.getFormId());
            FormTemplateMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
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
    /** Deletes an existing instance of FormTemplate.
     * @param input The key values for the domain object to be deleted.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(FormTemplateMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
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

    /** Deletes an existing instance of FormTemplate.
     * @param input The key values for the domain object to be deleted.
     * @param uow The delete will be performed using the input UOW.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(FormTemplateMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions {
        // Print Debug Information for the input
        if (log.isDebugEnabled())
            log.debug("Input: " + input);

        // Preprocess the input
        preprocess(uow, input);

        // Retrieve the object
        FormTemplate domain = load(uow, input);

        // Delete the domain object
        deleteDomain(uow, input, domain);

        // Print Debug Information for the output
        if (log.isDebugEnabled())
            log.debug("The domain object has been marked for deletion. It will be deleted when the UOW is committed");
    }
    // .//GEN-END:_delete_1_be
    // .//GEN-BEGIN:_preprocessCreate_1_be
    /** Preprocess the input for the create method. */
    private void preprocess(UOW uow, FormTemplateMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessCreate_1_be
        // Add custom code //GEN-FIRST:_preprocessCreate_1


        // .//GEN-LAST:_preprocessCreate_1
        // .//GEN-BEGIN:_preprocessCreate_2_be
    }
    // .//GEN-END:_preprocessCreate_2_be
    // .//GEN-BEGIN:_duplicateCheck_1_be
    /** Ensure that a duplicate record is not created. */
    private void duplicateCheck(UOW uow, FormTemplateMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_duplicateCheck_1_be
        // Add custom code //GEN-FIRST:_duplicateCheck_1


        // .//GEN-LAST:_duplicateCheck_1
        // .//GEN-BEGIN:_duplicateCheck_2_be
        if (input.getFormId() == null)
            return;
        Criteria criteria = new Criteria();
        criteria.setTable( FormTemplateMeta.getName() );
        // .//GEN-END:_duplicateCheck_2_be
        // Add custom criteria //GEN-FIRST:_duplicateCheck_2


        // .//GEN-LAST:_duplicateCheck_2
        // .//GEN-BEGIN:_duplicateCheck_3_be
        criteria.addCriteria(FormTemplateMeta.FORM_ID, input.getFormId());
        Collection col = uow.query(criteria);
        // .//GEN-END:_duplicateCheck_3_be
        // Add custom code //GEN-FIRST:_duplicateCheck_3


        // .//GEN-LAST:_duplicateCheck_3
        // .//GEN-BEGIN:_duplicateCheck_4_be
        if (col != null && !col.isEmpty()) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DuplicateKeyException(FormTemplateMeta.getLabelToken()));
            throw appExps;
        }
    }
    // .//GEN-END:_duplicateCheck_4_be
    // .//GEN-BEGIN:_createDomain_1_be
    /** Create the domain object. */
    private FormTemplate createDomain(UOW uow, FormTemplateMaintenanceCreateInDto input, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        FormTemplate domain = new FormTemplate();
        ApplicationExceptions appExps = null;
        // .//GEN-END:_createDomain_1_be
        // Add custom code //GEN-FIRST:_createDomain_1


        // .//GEN-LAST:_createDomain_1
        // .//GEN-BEGIN:_createDomain_2_be
        try {
            domain.updateTemplateData(input.getTemplateData());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateLayoutData(input.getLayoutData());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFormId(input.getFormId());
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
    private void postCreate(UOW uow, FormTemplateMaintenanceCreateInDto input, FormTemplate domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_postCreate_1_be
        // Add custom code //GEN-FIRST:_postCreate_1


        // .//GEN-LAST:_postCreate_1
        // .//GEN-BEGIN:_postCreate_2_be
    }
    // .//GEN-END:_postCreate_2_be
    // .//GEN-BEGIN:_preprocessRetrieve_1_be
    /** Preprocess the input for the retrieve method. */
    private void preprocess(UOW uow, FormTemplateMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessRetrieve_1_be
        // Add custom code //GEN-FIRST:_preprocessRetrieve_1


        // .//GEN-LAST:_preprocessRetrieve_1
        // .//GEN-BEGIN:_preprocessRetrieve_2_be
    }
    // .//GEN-END:_preprocessRetrieve_2_be
    // .//GEN-BEGIN:_loadRetrieve_1_be
    /** Retrieve the domain object. */
    private FormTemplate load(UOW uow, FormTemplateMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        FormTemplate domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormTemplateMeta.getName() );
        // .//GEN-END:_loadRetrieve_1_be
        // Add custom criteria //GEN-FIRST:_loadRetrieve_1


        // .//GEN-LAST:_loadRetrieve_1
        // .//GEN-BEGIN:_loadRetrieve_2_be
        criteria.addCriteria(FormTemplateMeta.FORM_ID, input.getFormId());
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormTemplate) itr.next();
        // .//GEN-END:_loadRetrieve_2_be
        // Add custom code //GEN-FIRST:_loadRetrieve_2


        // .//GEN-LAST:_loadRetrieve_2
        // .//GEN-BEGIN:_loadRetrieve_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormTemplateMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadRetrieve_3_be
    // .//GEN-BEGIN:_buildRetrieveOutDto_1_be
    /** Create the RetrieveOutDto. */
    private FormTemplateMaintenanceRetrieveOutDto buildRetrieveOutDto(UOW uow, FormTemplateMaintenanceRetrieveInDto input, FormTemplate domain) throws FrameworkException, ApplicationExceptions {
        FormTemplateMaintenanceRetrieveOutDto output = new FormTemplateMaintenanceRetrieveOutDto();
        // .//GEN-END:_buildRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_buildRetrieveOutDto_1


        // .//GEN-LAST:_buildRetrieveOutDto_1
        // .//GEN-BEGIN:_buildRetrieveOutDto_2_be
        output.setTemplateData(domain.getTemplateData());
        output.setLayoutData(domain.getLayoutData());
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
    private void preprocess(UOW uow, FormTemplateMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessUpdate_1_be
        // Add custom code //GEN-FIRST:_preprocessUpdate_1


        // .//GEN-LAST:_preprocessUpdate_1
        // .//GEN-BEGIN:_preprocessUpdate_2_be
    }
    // .//GEN-END:_preprocessUpdate_2_be
    // .//GEN-BEGIN:_loadUpdate_1_be
    /** Retrieve the domain object. */
    private FormTemplate load(UOW uow, FormTemplateMaintenanceUpdateInDto input)
    throws FrameworkException, ApplicationExceptions {
        FormTemplate domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormTemplateMeta.getName() );
        // .//GEN-END:_loadUpdate_1_be
        // Add custom criteria //GEN-FIRST:_loadUpdate_1


        // .//GEN-LAST:_loadUpdate_1
        // .//GEN-BEGIN:_loadUpdate_2_be
        criteria.addCriteria(FormTemplateMeta.FORM_ID, input.getFormId());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormTemplate) itr.next();
        // .//GEN-END:_loadUpdate_2_be
        // Add custom code //GEN-FIRST:_loadUpdate_2


        // .//GEN-LAST:_loadUpdate_2
        // .//GEN-BEGIN:_loadUpdate_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormTemplateMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadUpdate_3_be
    // .//GEN-BEGIN:_updateDomain_1_be
    /** Update the domain object and add it to the UOW. */
    private void updateDomain(UOW uow, FormTemplateMaintenanceUpdateInDto input, FormTemplate domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_updateDomain_1_be
        // Add custom code //GEN-FIRST:_updateDomain_1


        // .//GEN-LAST:_updateDomain_1
        // .//GEN-BEGIN:_updateDomain_2_be
        try {
            domain.updateTemplateData(input.getTemplateData());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateLayoutData(input.getLayoutData());
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
    private void preprocess(UOW uow, FormTemplateMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessDelete_1_be
        // Add custom code //GEN-FIRST:_preprocessDelete_1


        // .//GEN-LAST:_preprocessDelete_1
        // .//GEN-BEGIN:_preprocessDelete_2_be
    }
    // .//GEN-END:_preprocessDelete_2_be
    // .//GEN-BEGIN:_loadDelete_1_be
    /** Retrieve the domain object. */
    private FormTemplate load(UOW uow, FormTemplateMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        FormTemplate domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormTemplateMeta.getName() );
        // .//GEN-END:_loadDelete_1_be
        // Add custom criteria //GEN-FIRST:_loadDelete_1


        // .//GEN-LAST:_loadDelete_1
        // .//GEN-BEGIN:_loadDelete_2_be
        criteria.addCriteria(FormTemplateMeta.FORM_ID, input.getFormId());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormTemplate) itr.next();
        // .//GEN-END:_loadDelete_2_be
        // Add custom code //GEN-FIRST:_loadDelete_2


        // .//GEN-LAST:_loadDelete_2
        // .//GEN-BEGIN:_loadDelete_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormTemplateMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadDelete_3_be
    // .//GEN-BEGIN:_deleteDomain_1_be
    /** Delete the domain object from the domain. */
    private void deleteDomain(UOW uow, FormTemplateMaintenanceDeleteInDto input, FormTemplate domain) throws FrameworkException, ApplicationExceptions {
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
    /** Add foreign objects to FormTemplateMaintenanceRetrieveOutDto */
    private void addForeignObjectsToRetrieveOut(UOW uow, FormTemplate domain, FormTemplateMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {

        {
            output.setFormId(domain.getFormId());
        }
    }
    // .//GEN-END:_addForeignObjectsToRetrieveOut_1_be
    // .//GEN-BEGIN:_validateForeignObjects_1_be
    /** This will validate the Foreign Objects. */
    private void validateForeignObjects(UOW uow, FormTemplateMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_validateForeignObjects_1_be
        // .//GEN-BEGIN:_validateForeignObjects_FormDefinition_1_be
        {
            if (input.getFormId() != null) {
                Criteria criteriaFormDefinition = new Criteria();
                criteriaFormDefinition.setTable(FormDefinitionMeta.getName());
                criteriaFormDefinition.addCriteria(FormDefinitionMeta.FORM_ID, input.getFormId());
                // .//GEN-END:_validateForeignObjects_FormDefinition_1_be
                // Add custom code //GEN-FIRST:_validateForeignObjects_FormDefinition_1


                // .//GEN-LAST:_validateForeignObjects_FormDefinition_1
                // .//GEN-BEGIN:_validateForeignObjects_FormDefinition_2_be
                if (criteriaFormDefinition != null) {
                    Collection col = uow.query(criteriaFormDefinition);
                    if (col.isEmpty()) {
                        if (appExps == null)
                            appExps = new ApplicationExceptions();
                        appExps.add(new InvalidForeignKeyException(FormTemplateMeta.META_FORM_ID.getLabelToken(), new Object[] {FormDefinitionMeta.getLabelToken(), FormDefinitionMeta.META_FORM_ID.getLabelToken()}));
                    } else if (col.size() != 1) {
                        if (appExps == null)
                            appExps = new ApplicationExceptions();
                        appExps.add(new MultipleDomainObjectsFoundException(FormDefinitionMeta.getLabelToken()));
                    }
                }
            }
        }
        // .//GEN-END:_validateForeignObjects_FormDefinition_2_be
        // .//GEN-BEGIN:_validateForeignObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_validateForeignObjects_2_be
    // .//GEN-BEGIN:_createPrevalidateOutDto_1_be
    private FormTemplateMaintenancePrevalidateOutDto createPrevalidateOutDto(UOW uow, FormTemplate domain, FormTemplateMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        FormTemplateMaintenancePrevalidateOutDto output = new FormTemplateMaintenancePrevalidateOutDto();
        output.setTemplateData(domain.getTemplateData());
        output.setLayoutData(domain.getLayoutData());
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
    /** Add related objects to FormTemplateMaintenanceRetrieveOutDto */
    private void addRelatedDtosToRetrieveOut(UOW uow, FormTemplate formTemplate, FormTemplateMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_addRelatedDtosToRetrieveOut_1_be
        // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_2_be
    }
    // .//GEN-END:_addRelatedDtosToRetrieveOut_2_be
    // .//GEN-BEGIN:_deleteRelatedObjects_1_be
    /** Delete the related domain objects if the 'Cascading' constraint is specified. Throw an exception in case 'Restricted' constraint is specified. */
    private void deleteRelatedObjects(UOW uow, FormTemplateMaintenanceDeleteInDto input, FormTemplate formTemplate) throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_deleteRelatedObjects_1_be
        // Add custom code //GEN-FIRST:_deleteRelatedObjects_1


        // .//GEN-LAST:_deleteRelatedObjects_1
        // .//GEN-BEGIN:_deleteRelatedObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_deleteRelatedObjects_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
