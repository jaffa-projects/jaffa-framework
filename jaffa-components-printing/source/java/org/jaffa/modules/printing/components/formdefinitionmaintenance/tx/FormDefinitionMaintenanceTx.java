// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formdefinitionmaintenance.tx;

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

import org.jaffa.modules.printing.components.formdefinitionmaintenance.IFormDefinitionMaintenance;
import org.jaffa.modules.printing.components.formdefinitionmaintenance.dto.*;
import org.jaffa.modules.printing.domain.FormDefinition;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;


import org.jaffa.modules.printing.domain.FormTemplate;
import org.jaffa.modules.printing.domain.FormTemplateMeta;

// .//GEN-END:_1_be
import org.jaffa.modules.printing.components.formdefinitionmaintenance.dto.*;//GEN-FIRST:_imports
import org.jaffa.modules.printing.domain.FormDefinition;
import org.jaffa.modules.printing.domain.FormTemplate;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;
import org.jaffa.modules.printing.domain.FormTemplateMeta;
import org.jaffa.modules.printing.domain.FormUsageMeta;
import org.jaffa.modules.printing.domain.FormUsage;
import org.jaffa.modules.printing.domain.FormGroup;
import org.jaffa.modules.printing.domain.FormGroupMeta;

// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Maintenance for FormDefinition objects.
*/
public class FormDefinitionMaintenanceTx extends MaintTx implements IFormDefinitionMaintenance {

    private static Logger log = Logger.getLogger(FormDefinitionMaintenanceTx.class);

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
    /** This method is used to perform prevalidations before creating a new instance of FormDefinition.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormDefinitionMaintenancePrevalidateOutDto prevalidateCreate(FormDefinitionMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormDefinition domain = createDomain(uow, input, true);
            domain = postCreate(uow, input, domain, true);
            FormTemplate formTemplate = createOrLoadFormTemplate(uow, input, domain, true);

            // Build the outbound dto
            FormDefinitionMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

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
    /** Persists a new instance of FormDefinition.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormDefinitionMaintenanceRetrieveOutDto create(FormDefinitionMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormDefinition domain = createDomain(uow, input, false);
            uow.add(domain);
            domain = postCreate(uow, input, domain, false);
            FormTemplate formTemplate = createOrLoadFormTemplate(uow, input, domain, false);
            if (formTemplate != null && formTemplate.isModified()) {
                if (formTemplate.isDatabaseOccurence())
                    uow.update(formTemplate);
                else
                    uow.add(formTemplate);
            }

            // Commit the changes
            uow.commit();

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Successfully created the domain object. Now retrieving the object details.");

            if (input.getFormId() == null) {
                if (log.isDebugEnabled())
                    log.debug("The Key is not set completely. Probably using AutoGenerated keys. Cannot re-retrieve the object details. Returning NULL instead");
                return null;
            }

            // Build the outbound dto by performing a retrieve
            FormDefinitionMaintenanceRetrieveInDto retrieveInDto = new FormDefinitionMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setFormId(input.getFormId());
            FormDefinitionMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
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
    /** Returns the details for FormDefinition.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */
    public FormDefinitionMaintenanceRetrieveOutDto retrieve(FormDefinitionMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormDefinition domain = load(uow, input);

            // Convert the domain objects into the outbound dto
            FormDefinitionMaintenanceRetrieveOutDto output = buildRetrieveOutDto(uow, input, domain);

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
    /** This method is used to perform prevalidations before updating an existing instance of FormDefinition.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormDefinitionMaintenancePrevalidateOutDto prevalidateUpdate(FormDefinitionMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormDefinition domain = load(uow, input);

            // Ensure the domain object has not been modified
            domainObjectChangedTest(input.getPerformDirtyReadCheck(), domain, input.getLastChangedOn());

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Update the domain object
            updateDomain(uow, input, domain, true);

            FormTemplate formTemplate = createOrLoadFormTemplate(uow, input, domain, true);

            // Build the outbound dto
            FormDefinitionMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

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
    /** Updates an existing instance of FormDefinition.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public FormDefinitionMaintenanceRetrieveOutDto update(FormDefinitionMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
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
            FormDefinition domain = load(uow, input);

            // Ensure the domain object has not been modified
            domainObjectChangedTest(input.getPerformDirtyReadCheck(), domain, input.getLastChangedOn());

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Update the domain object
            updateDomain(uow, input, domain, false);
            uow.update(domain);
            FormTemplate formTemplate = createOrLoadFormTemplate(uow, input, domain, false);
            if (formTemplate != null && formTemplate.isModified()) {
                if (formTemplate.isDatabaseOccurence())
                    uow.update(formTemplate);
                else
                    uow.add(formTemplate);
            }

            // Commit the changes
            uow.commit();

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Successfully updated the domain object. Now retrieving the object details.");

            // Build the outbound dto by performing a retrieve
            FormDefinitionMaintenanceRetrieveInDto retrieveInDto = new FormDefinitionMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setFormId(input.getFormId());
            FormDefinitionMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
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
    /** Deletes an existing instance of FormDefinition.
     * @param input The key values for the domain object to be deleted.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(FormDefinitionMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
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

    /** Deletes an existing instance of FormDefinition.
     * @param input The key values for the domain object to be deleted.
     * @param uow The delete will be performed using the input UOW.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(FormDefinitionMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions {
        // Print Debug Information for the input
        if (log.isDebugEnabled())
            log.debug("Input: " + input);

        // Preprocess the input
        preprocess(uow, input);

        // Retrieve the object
        FormDefinition domain = load(uow, input);

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
    private void preprocess(UOW uow, FormDefinitionMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessCreate_1_be
        // Add custom code//GEN-FIRST:_preprocessCreate_1
        // Not allowed to create a form definition without a template
        if(input.getFormTemplate() == null)
            throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormDefinitionMaintenance.FormTemplateMandatory") {});
        
        
        if(input.getFollowOnFormName()!=null){
            Criteria criteria = new Criteria();
            criteria.setTable(FormGroupMeta.getName());
            criteria.addCriteria(FormGroupMeta.FORM_NAME, input.getFollowOnFormName());
            Iterator it = uow.query(criteria).iterator();
            if(!it.hasNext())
                throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormDefinitionMaintenance.ValidFollowOnForm") {});                    
        }

        // .//GEN-LAST:_preprocessCreate_1
        // .//GEN-BEGIN:_preprocessCreate_2_be
    }
    // .//GEN-END:_preprocessCreate_2_be
    // .//GEN-BEGIN:_duplicateCheck_1_be
    /** Ensure that a duplicate record is not created. */
    private void duplicateCheck(UOW uow, FormDefinitionMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_duplicateCheck_1_be
        // Add custom code//GEN-FIRST:_duplicateCheck_1


        // .//GEN-LAST:_duplicateCheck_1
        // .//GEN-BEGIN:_duplicateCheck_2_be
        if (input.getFormId() == null)
            return;
        Criteria criteria = new Criteria();
        criteria.setTable( FormDefinitionMeta.getName() );
        // .//GEN-END:_duplicateCheck_2_be
        // Add custom criteria//GEN-FIRST:_duplicateCheck_2


        // .//GEN-LAST:_duplicateCheck_2
        // .//GEN-BEGIN:_duplicateCheck_3_be
        criteria.addCriteria(FormDefinitionMeta.FORM_ID, input.getFormId());
        Collection col = uow.query(criteria);
        // .//GEN-END:_duplicateCheck_3_be
        // Add custom code//GEN-FIRST:_duplicateCheck_3


        // .//GEN-LAST:_duplicateCheck_3
        // .//GEN-BEGIN:_duplicateCheck_4_be
        if (col != null && !col.isEmpty()) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DuplicateKeyException(FormDefinitionMeta.getLabelToken()));
            throw appExps;
        }
    }
    // .//GEN-END:_duplicateCheck_4_be
    // .//GEN-BEGIN:_createDomain_1_be
    /** Create the domain object. */
    private FormDefinition createDomain(UOW uow, FormDefinitionMaintenanceCreateInDto input, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        FormDefinition domain = new FormDefinition();
        ApplicationExceptions appExps = null;
        // .//GEN-END:_createDomain_1_be
        // Add custom code//GEN-FIRST:_createDomain_1


        // .//GEN-LAST:_createDomain_1
        // .//GEN-BEGIN:_createDomain_2_be
        try {
            domain.updateFormId(input.getFormId());
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
        try {
            domain.updateFormAlternate(input.getFormAlternate());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFormVariation(input.getFormVariation());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateOutputType(input.getOutputType());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFormTemplate(input.getFormTemplate());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFieldLayout(input.getFieldLayout());
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
            domain.updateRemarks(input.getRemarks());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomFactory(input.getDomFactory());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomClass(input.getDomClass());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey1(input.getDomKey1());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey2(input.getDomKey2());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey3(input.getDomKey3());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey4(input.getDomKey4());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey5(input.getDomKey5());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey6(input.getDomKey6());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateAdditionalDataComponent(input.getAdditionalDataComponent());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFollowOnFormName(input.getFollowOnFormName());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFollowOnFormAlternate(input.getFollowOnFormAlternate());
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
    private FormDefinition postCreate(UOW uow, FormDefinitionMaintenanceCreateInDto input, FormDefinition domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_postCreate_1_be
        // Add custom code//GEN-FIRST:_postCreate_1
        if (!fromPrevalidate) {
            domain = FormDefinition.findByCK(uow, input.getFormName(), input.getFormAlternate(), input.getFormVariation(), input.getOutputType());
            if (domain != null)
                input.setFormId(domain.getFormId());
            else
                throw new ApplicationExceptions(new DomainObjectNotFoundException(FormDefinitionMeta.getLabelToken()));
        }

        // .//GEN-LAST:_postCreate_1
        // .//GEN-BEGIN:_postCreate_2_be
        return domain;
    }
    // .//GEN-END:_postCreate_2_be
    // .//GEN-BEGIN:_preprocessRetrieve_1_be
    /** Preprocess the input for the retrieve method. */
    private void preprocess(UOW uow, FormDefinitionMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessRetrieve_1_be
        // Add custom code//GEN-FIRST:_preprocessRetrieve_1


        // .//GEN-LAST:_preprocessRetrieve_1
        // .//GEN-BEGIN:_preprocessRetrieve_2_be
    }
    // .//GEN-END:_preprocessRetrieve_2_be
    // .//GEN-BEGIN:_loadRetrieve_1_be
    /** Retrieve the domain object. */
    private FormDefinition load(UOW uow, FormDefinitionMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        FormDefinition domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormDefinitionMeta.getName() );
        // .//GEN-END:_loadRetrieve_1_be
        // Add custom criteria//GEN-FIRST:_loadRetrieve_1


        // .//GEN-LAST:_loadRetrieve_1
        // .//GEN-BEGIN:_loadRetrieve_2_be
        criteria.addCriteria(FormDefinitionMeta.FORM_ID, input.getFormId());
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormDefinition) itr.next();
        // .//GEN-END:_loadRetrieve_2_be
        // Add custom code//GEN-FIRST:_loadRetrieve_2


        // .//GEN-LAST:_loadRetrieve_2
        // .//GEN-BEGIN:_loadRetrieve_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormDefinitionMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadRetrieve_3_be
    // .//GEN-BEGIN:_buildRetrieveOutDto_1_be
    /** Create the RetrieveOutDto. */
    private FormDefinitionMaintenanceRetrieveOutDto buildRetrieveOutDto(UOW uow, FormDefinitionMaintenanceRetrieveInDto input, FormDefinition domain) throws FrameworkException, ApplicationExceptions {
        FormDefinitionMaintenanceRetrieveOutDto output = new FormDefinitionMaintenanceRetrieveOutDto();
        // .//GEN-END:_buildRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_buildRetrieveOutDto_1


        // .//GEN-LAST:_buildRetrieveOutDto_1
        // .//GEN-BEGIN:_buildRetrieveOutDto_2_be
        output.setFormId(domain.getFormId());
        output.setFormName(domain.getFormName());
        output.setFormAlternate(domain.getFormAlternate());
        output.setFormVariation(domain.getFormVariation());
        output.setOutputType(domain.getOutputType());
        output.setFormTemplate(domain.getFormTemplate());
        output.setFieldLayout(domain.getFieldLayout());
        output.setDescription(domain.getDescription());
        output.setRemarks(domain.getRemarks());
        output.setDomFactory(domain.getDomFactory());
        output.setDomClass(domain.getDomClass());
        output.setDomKey1(domain.getDomKey1());
        output.setDomKey2(domain.getDomKey2());
        output.setDomKey3(domain.getDomKey3());
        output.setDomKey4(domain.getDomKey4());
        output.setDomKey5(domain.getDomKey5());
        output.setDomKey6(domain.getDomKey6());
        output.setAdditionalDataComponent(domain.getAdditionalDataComponent());
        output.setFollowOnFormName(domain.getFollowOnFormName());
        output.setFollowOnFormAlternate(domain.getFollowOnFormAlternate());
        output.setCreatedOn(domain.getCreatedOn());
        output.setCreatedBy(domain.getCreatedBy());
        output.setLastChangedOn(domain.getLastChangedOn());
        output.setLastChangedBy(domain.getLastChangedBy());
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
    private void preprocess(UOW uow, FormDefinitionMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessUpdate_1_be
        // Add custom code//GEN-FIRST:_preprocessUpdate_1
        
        if(input.getFollowOnFormName()!=null){
            Criteria criteria = new Criteria();
            criteria.setTable(FormGroupMeta.getName());
            criteria.addCriteria(FormGroupMeta.FORM_NAME, input.getFollowOnFormName());
            Iterator it = uow.query(criteria).iterator();
            if(!it.hasNext())
                throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormDefinitionMaintenance.ValidFollowOnForm") {});                    
        }

        // .//GEN-LAST:_preprocessUpdate_1
        // .//GEN-BEGIN:_preprocessUpdate_2_be
    }
    // .//GEN-END:_preprocessUpdate_2_be
    // .//GEN-BEGIN:_loadUpdate_1_be
    /** Retrieve the domain object. */
    private FormDefinition load(UOW uow, FormDefinitionMaintenanceUpdateInDto input)
    throws FrameworkException, ApplicationExceptions {
        FormDefinition domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormDefinitionMeta.getName() );
        // .//GEN-END:_loadUpdate_1_be
        // Add custom criteria//GEN-FIRST:_loadUpdate_1



        // .//GEN-LAST:_loadUpdate_1
        // .//GEN-BEGIN:_loadUpdate_2_be
        criteria.addCriteria(FormDefinitionMeta.FORM_ID, input.getFormId());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormDefinition) itr.next();
        // .//GEN-END:_loadUpdate_2_be
        // Add custom code//GEN-FIRST:_loadUpdate_2


        // .//GEN-LAST:_loadUpdate_2
        // .//GEN-BEGIN:_loadUpdate_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormDefinitionMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadUpdate_3_be
    // .//GEN-BEGIN:_updateDomain_1_be
    /** Update the domain object and add it to the UOW. */
    private void updateDomain(UOW uow, FormDefinitionMaintenanceUpdateInDto input, FormDefinition domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_updateDomain_1_be
        // Add custom code//GEN-FIRST:_updateDomain_1

        // if FormTemplate and FieldLayout fields are null, set them to their current domain values, so
        // the generated code does not set them to null.
        String origFormTemplate = input.getFormTemplate();
        String origFieldLayout = input.getFieldLayout();
        if(input.getFormTemplate() == null)
            input.setFormTemplate(domain.getFormTemplate());
        if(input.getFieldLayout() == null)
            input.setFieldLayout(domain.getFieldLayout());
        
        // .//GEN-LAST:_updateDomain_1
        // .//GEN-BEGIN:_updateDomain_2_be
        try {
            domain.updateFormName(input.getFormName());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFormAlternate(input.getFormAlternate());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFormVariation(input.getFormVariation());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateOutputType(input.getOutputType());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFormTemplate(input.getFormTemplate());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFieldLayout(input.getFieldLayout());
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
            domain.updateRemarks(input.getRemarks());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomFactory(input.getDomFactory());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomClass(input.getDomClass());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey1(input.getDomKey1());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey2(input.getDomKey2());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey3(input.getDomKey3());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey4(input.getDomKey4());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey5(input.getDomKey5());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateDomKey6(input.getDomKey6());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateAdditionalDataComponent(input.getAdditionalDataComponent());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFollowOnFormName(input.getFollowOnFormName());
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }
        try {
            domain.updateFollowOnFormAlternate(input.getFollowOnFormAlternate());
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
        // .//GEN-END:_updateDomain_2_be
        // Add custom code//GEN-FIRST:_updateDomain_2
        // Restore the original value in the input object incase any other logic relies on them
        input.setFormTemplate(origFormTemplate);
        input.setFieldLayout(origFieldLayout);

        // .//GEN-LAST:_updateDomain_2
        // .//GEN-BEGIN:_updateDomain_3_be
        if (appExps != null && appExps.size() > 0)
            throw appExps;
    }
    // .//GEN-END:_updateDomain_3_be
    // .//GEN-BEGIN:_preprocessDelete_1_be
    /** Preprocess the input for the delete method. */
    private void preprocess(UOW uow, FormDefinitionMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessDelete_1_be
        // Add custom code//GEN-FIRST:_preprocessDelete_1




        // .//GEN-LAST:_preprocessDelete_1
        // .//GEN-BEGIN:_preprocessDelete_2_be
    }
    // .//GEN-END:_preprocessDelete_2_be
    // .//GEN-BEGIN:_loadDelete_1_be
    /** Retrieve the domain object. */
    private FormDefinition load(UOW uow, FormDefinitionMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        FormDefinition domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( FormDefinitionMeta.getName() );
        // .//GEN-END:_loadDelete_1_be
        // Add custom criteria//GEN-FIRST:_loadDelete_1


        // .//GEN-LAST:_loadDelete_1
        // .//GEN-BEGIN:_loadDelete_2_be
        criteria.addCriteria(FormDefinitionMeta.FORM_ID, input.getFormId());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (FormDefinition) itr.next();
        // .//GEN-END:_loadDelete_2_be
        // Add custom code//GEN-FIRST:_loadDelete_2


        // .//GEN-LAST:_loadDelete_2
        // .//GEN-BEGIN:_loadDelete_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(FormDefinitionMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadDelete_3_be
    // .//GEN-BEGIN:_deleteDomain_1_be
    /** Delete the domain object from the domain. */
    private void deleteDomain(UOW uow, FormDefinitionMaintenanceDeleteInDto input, FormDefinition domain) throws FrameworkException, ApplicationExceptions {
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
    /** Add foreign objects to FormDefinitionMaintenanceRetrieveOutDto */
    private void addForeignObjectsToRetrieveOut(UOW uow, FormDefinition domain, FormDefinitionMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {
    }
    // .//GEN-END:_addForeignObjectsToRetrieveOut_1_be
    // .//GEN-BEGIN:_validateForeignObjects_1_be
    /** This will validate the Foreign Objects. */
    private void validateForeignObjects(UOW uow, FormDefinitionMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_validateForeignObjects_1_be
        // .//GEN-BEGIN:_validateForeignObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_validateForeignObjects_2_be
    // .//GEN-BEGIN:_createPrevalidateOutDto_1_be
    private FormDefinitionMaintenancePrevalidateOutDto createPrevalidateOutDto(UOW uow, FormDefinition domain, FormDefinitionMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        FormDefinitionMaintenancePrevalidateOutDto output = new FormDefinitionMaintenancePrevalidateOutDto();
        output.setFormId(domain.getFormId());
        output.setFormName(domain.getFormName());
        output.setFormAlternate(domain.getFormAlternate());
        output.setFormVariation(domain.getFormVariation());
        output.setOutputType(domain.getOutputType());
        output.setFormTemplate(domain.getFormTemplate());
        output.setFieldLayout(domain.getFieldLayout());
        output.setDescription(domain.getDescription());
        output.setRemarks(domain.getRemarks());
        output.setDomFactory(domain.getDomFactory());
        output.setDomClass(domain.getDomClass());
        output.setDomKey1(domain.getDomKey1());
        output.setDomKey2(domain.getDomKey2());
        output.setDomKey3(domain.getDomKey3());
        output.setDomKey4(domain.getDomKey4());
        output.setDomKey5(domain.getDomKey5());
        output.setDomKey6(domain.getDomKey6());
        output.setAdditionalDataComponent(domain.getAdditionalDataComponent());
        output.setFollowOnFormName(domain.getFollowOnFormName());
        output.setFollowOnFormAlternate(domain.getFollowOnFormAlternate());
        output.setCreatedOn(domain.getCreatedOn());
        output.setCreatedBy(domain.getCreatedBy());
        output.setLastChangedOn(domain.getLastChangedOn());
        output.setLastChangedBy(domain.getLastChangedBy());
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
    /** Add related objects to FormDefinitionMaintenanceRetrieveOutDto */
    private void addRelatedDtosToRetrieveOut(UOW uow, FormDefinition formDefinition, FormDefinitionMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_addRelatedDtosToRetrieveOut_1_be
        // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormTemplate_1_be
        if (formDefinition.getFormId() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(FormTemplateMeta.getName());
            criteria.addCriteria(FormTemplateMeta.FORM_ID, formDefinition.getFormId());
            // .//GEN-END:_addRelatedDtosToRetrieveOut_FormTemplate_1_be
            // Add custom code to set the criteria before the query//GEN-FIRST:_addRelatedDtosToRetrieveOut_FormTemplate_1


            // .//GEN-LAST:_addRelatedDtosToRetrieveOut_FormTemplate_1
            // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormTemplate_4_be
            Iterator itrOne = uow.query(criteria).iterator();
            if (itrOne.hasNext()) {
                FormTemplate formTemplate = (FormTemplate) itrOne.next();
                // .//GEN-END:_addRelatedDtosToRetrieveOut_FormTemplate_4_be
                // Add custom code to pass values to the dto//GEN-FIRST:_addRelatedDtosToRetrieveOut_FormTemplate_5


                // .//GEN-LAST:_addRelatedDtosToRetrieveOut_FormTemplate_5
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormTemplate_5_be
            }
            // .//GEN-END:_addRelatedDtosToRetrieveOut_FormTemplate_5_be
            // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_FormTemplate_6_be
        }

        // .//GEN-END:_addRelatedDtosToRetrieveOut_FormTemplate_6_be
        // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_2_be
    }
    // .//GEN-END:_addRelatedDtosToRetrieveOut_2_be
    // .//GEN-BEGIN:_deleteRelatedObjects_1_be
    /** Delete the related domain objects if the 'Cascading' constraint is specified. Throw an exception in case 'Restricted' constraint is specified. */
    private void deleteRelatedObjects(UOW uow, FormDefinitionMaintenanceDeleteInDto input, FormDefinition formDefinition) throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_deleteRelatedObjects_1_be
        // .//GEN-BEGIN:_deleteRelatedObjects_FormTemplate_1_be
        if (formDefinition.getFormId() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(FormTemplateMeta.getName());
            criteria.addCriteria(FormTemplateMeta.FORM_ID, formDefinition.getFormId());
            criteria.setLocking(Criteria.LOCKING_PARANOID);
            // .//GEN-END:_deleteRelatedObjects_FormTemplate_1_be
            // Add custom code to set the criteria before the query//GEN-FIRST:_deleteRelatedObjects_FormTemplate_1


            // .//GEN-LAST:_deleteRelatedObjects_FormTemplate_1
            // .//GEN-BEGIN:_deleteRelatedObjects_FormTemplate_4_be
            Iterator itr = uow.query(criteria).iterator();
            while (itr.hasNext()) {
                FormTemplate formTemplate = (FormTemplate) itr.next();
                if (log.isDebugEnabled())
                    log.debug("Deleting the related object " + formTemplate);
                uow.delete(formTemplate);
            }
            // .//GEN-END:_deleteRelatedObjects_FormTemplate_4_be
        // .//GEN-BEGIN:_deleteRelatedObjects_FormTemplate_6_be
        }
        // .//GEN-END:_deleteRelatedObjects_FormTemplate_6_be
        // Add custom code//GEN-FIRST:_deleteRelatedObjects_1
        // Stop the delete if this is the only record for the given formName/formAlternate combination, and if any FormUsage exists
        Criteria criteria = new Criteria();
        criteria.setTable(FormDefinitionMeta.getName());
        criteria.addCriteria(FormDefinitionMeta.FORM_ID, Criteria.RELATIONAL_NOT_EQUALS, formDefinition.getFormId());
        if (formDefinition.getFormName() != null)
            criteria.addCriteria(FormDefinitionMeta.FORM_NAME, formDefinition.getFormName());
        else
            criteria.addCriteria(FormDefinitionMeta.FORM_NAME, Criteria.RELATIONAL_IS_NULL);
        if (formDefinition.getFormAlternate() != null)
            criteria.addCriteria(FormDefinitionMeta.FORM_ALTERNATE, formDefinition.getFormAlternate());
        else
            criteria.addCriteria(FormDefinitionMeta.FORM_ALTERNATE, Criteria.RELATIONAL_IS_NULL);
        Iterator itr = uow.query(criteria).iterator();
        if (!itr.hasNext()) {
            // No other FormDefinition exists for the given formName/formAlternate combination
            // Raise an error if any FormUsage exists for that formName/formAlternate combination.
            criteria = new Criteria();
            criteria.setTable(FormUsageMeta.getName());
            if (formDefinition.getFormName() != null)
                criteria.addCriteria(FormUsageMeta.FORM_NAME, formDefinition.getFormName());
            else
                criteria.addCriteria(FormUsageMeta.FORM_NAME, Criteria.RELATIONAL_IS_NULL);
            if (formDefinition.getFormAlternate() != null)
                criteria.addCriteria(FormUsageMeta.FORM_ALTERNATE, formDefinition.getFormAlternate());
            else
                criteria.addCriteria(FormUsageMeta.FORM_ALTERNATE, Criteria.RELATIONAL_IS_NULL);
            itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormDefinitionMaintenance.ReferrentialEUsage") {});
        }

        // .//GEN-LAST:_deleteRelatedObjects_1
        // .//GEN-BEGIN:_deleteRelatedObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_deleteRelatedObjects_2_be
    // .//GEN-BEGIN:_domainObjectChangedTest_1_be
    /** Ensure the domain object has not been modified. */
    private void domainObjectChangedTest(Boolean performDirtyReadCheck, FormDefinition domain, org.jaffa.datatypes.DateTime lastChangedOn)
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
    // .//GEN-BEGIN:_createOrLoadFormTemplate_1_be
    /** Create Or Load an existing instance of FormTemplate which has a 1-to-1 relationship with the domain object.*/
    private FormTemplate createOrLoadFormTemplate(UOW uow, FormDefinitionMaintenanceCreateInDto input, FormDefinition domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        FormTemplate formTemplate = null;
        ApplicationExceptions appExps = null;
        // .//GEN-END:_createOrLoadFormTemplate_1_be
        // Add custom code//GEN-FIRST:_createOrLoadFormTemplate_1


        // .//GEN-LAST:_createOrLoadFormTemplate_1
        // .//GEN-BEGIN:_createOrLoadFormTemplate_2_be
        if (domain.getFormId() != null) {
            // load an existing instance
            Criteria criteria = new Criteria();
            criteria.setTable(FormTemplateMeta.getName());
            criteria.addCriteria(FormTemplateMeta.FORM_ID, domain.getFormId());
            // .//GEN-END:_createOrLoadFormTemplate_2_be
            // Add custom code//GEN-FIRST:_createOrLoadFormTemplate_2



            // .//GEN-LAST:_createOrLoadFormTemplate_2
            // .//GEN-BEGIN:_createOrLoadFormTemplate_3_be
            Iterator itrOne = uow.query(criteria).iterator();
            if (itrOne.hasNext())
                formTemplate = (FormTemplate) itrOne.next();
        }
        
        // create if it doesn't already exist
        if (formTemplate == null)
            formTemplate = new FormTemplate();
        // .//GEN-END:_createOrLoadFormTemplate_3_be
        // Add custom code//GEN-FIRST:_createOrLoadFormTemplate_4
        // Update the template if a file has been uploaded
        if(input.getFormTemplate() != null){
            try{
                formTemplate.setTemplateData(input.getTemplateData());
            } catch(ValidationException e) {
                throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormDefinitionMaintenance.UploadTemplateData") {});
            }
        }
        if(input.getFieldLayout() != null){
            try{
                formTemplate.setLayoutData(input.getLayoutData());
            } catch(ValidationException e) {
                throw new ApplicationExceptions(new ApplicationException("exception.Jaffa.Printing.FormDefinitionMaintenance.UploadLayOutData") {});
            }
        }

        // .//GEN-LAST:_createOrLoadFormTemplate_4
        // .//GEN-BEGIN:_createOrLoadFormTemplate_4_be

        if (!formTemplate.isDatabaseOccurence() && formTemplate.isModified()) {
            // Finally set the join-fields
            try {
                formTemplate.updateFormId(domain.getFormId());
            } catch (ValidationException e) {
                if (appExps == null)
                    appExps = new ApplicationExceptions();
                appExps.add(e);
            }
        }
        // .//GEN-END:_createOrLoadFormTemplate_4_be
        // Add custom code//GEN-FIRST:_createOrLoadFormTemplate_3

        

        // .//GEN-LAST:_createOrLoadFormTemplate_3
        // .//GEN-BEGIN:_createOrLoadFormTemplate_5_be
        if (appExps != null && appExps.size() > 0)
            throw appExps;
        return formTemplate;
    }
    // .//GEN-END:_createOrLoadFormTemplate_5_be
    // All the custom code goes here//GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
