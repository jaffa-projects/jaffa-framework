// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.printeroutputtypemaintenance.tx;

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

import org.jaffa.modules.printing.components.printeroutputtypemaintenance.IPrinterOutputTypeMaintenance;
import org.jaffa.modules.printing.components.printeroutputtypemaintenance.dto.*;
import org.jaffa.modules.printing.domain.PrinterOutputType;
import org.jaffa.modules.printing.domain.PrinterOutputTypeMeta;


import org.jaffa.modules.printing.domain.OutputCommand;
import org.jaffa.modules.printing.domain.OutputCommandMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Maintenance for PrinterOutputType objects.
*/
public class PrinterOutputTypeMaintenanceTx extends MaintTx implements IPrinterOutputTypeMaintenance {

    private static Logger log = Logger.getLogger(PrinterOutputTypeMaintenanceTx.class);

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
    /** This method is used to perform prevalidations before creating a new instance of PrinterOutputType.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public PrinterOutputTypeMaintenancePrevalidateOutDto prevalidateCreate(PrinterOutputTypeMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
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
            PrinterOutputType domain = createDomain(uow, input, true);
            domain = postCreate(uow, input, domain, true);

            // Build the outbound dto
            PrinterOutputTypeMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

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
    /** Persists a new instance of PrinterOutputType.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public PrinterOutputTypeMaintenanceRetrieveOutDto create(PrinterOutputTypeMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
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
            PrinterOutputType domain = createDomain(uow, input, false);
            uow.add(domain);
            domain = postCreate(uow, input, domain, false);

            // Commit the changes
            uow.commit();

            // Print Debug Information for the output
            if (log.isDebugEnabled())
                log.debug("Successfully created the domain object. Now retrieving the object details.");

            if (input.getOutputType() == null) {
                if (log.isDebugEnabled())
                    log.debug("The Key is not set completely. Probably using AutoGenerated keys. Cannot re-retrieve the object details. Returning NULL instead");
                return null;
            }

            // Build the outbound dto by performing a retrieve
            PrinterOutputTypeMaintenanceRetrieveInDto retrieveInDto = new PrinterOutputTypeMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setOutputType(input.getOutputType());
            PrinterOutputTypeMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
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
    /** Returns the details for PrinterOutputType.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */
    public PrinterOutputTypeMaintenanceRetrieveOutDto retrieve(PrinterOutputTypeMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
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
            PrinterOutputType domain = load(uow, input);

            // Convert the domain objects into the outbound dto
            PrinterOutputTypeMaintenanceRetrieveOutDto output = buildRetrieveOutDto(uow, input, domain);

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
    /** This method is used to perform prevalidations before updating an existing instance of PrinterOutputType.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public PrinterOutputTypeMaintenancePrevalidateOutDto prevalidateUpdate(PrinterOutputTypeMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
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
            PrinterOutputType domain = load(uow, input);

            // Ensure the domain object has not been modified
            domainObjectChangedTest(input.getPerformDirtyReadCheck(), domain, input.getLastChangedOn());

            // Validate the foreign objects
            validateForeignObjects(uow, input);

            // Update the domain object
            updateDomain(uow, input, domain, true);


            // Build the outbound dto
            PrinterOutputTypeMaintenancePrevalidateOutDto output = createPrevalidateOutDto(uow, domain, input);

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
    /** Updates an existing instance of PrinterOutputType.
     * @param input The new values for the domain object.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details.
     */
    public PrinterOutputTypeMaintenanceRetrieveOutDto update(PrinterOutputTypeMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
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
            PrinterOutputType domain = load(uow, input);

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
            PrinterOutputTypeMaintenanceRetrieveInDto retrieveInDto = new PrinterOutputTypeMaintenanceRetrieveInDto();
            retrieveInDto.setHeaderDto(input.getHeaderDto());
            retrieveInDto.setOutputType(input.getOutputType());
            PrinterOutputTypeMaintenanceRetrieveOutDto output = retrieve(retrieveInDto);
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
    /** Deletes an existing instance of PrinterOutputType.
     * @param input The key values for the domain object to be deleted.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(PrinterOutputTypeMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
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

    /** Deletes an existing instance of PrinterOutputType.
     * @param input The key values for the domain object to be deleted.
     * @param uow The delete will be performed using the input UOW.
     * @throws ApplicationExceptions This will be thrown if the input contains invalid data.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(PrinterOutputTypeMaintenanceDeleteInDto input, UOW uow) throws FrameworkException, ApplicationExceptions {
        // Print Debug Information for the input
        if (log.isDebugEnabled())
            log.debug("Input: " + (input != null ? input.toString() : null));

        // Preprocess the input
        preprocess(uow, input);

        // Retrieve the object
        PrinterOutputType domain = load(uow, input);

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
    private void preprocess(UOW uow, PrinterOutputTypeMaintenanceCreateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessCreate_1_be
        // Add custom code //GEN-FIRST:_preprocessCreate_1


        // .//GEN-LAST:_preprocessCreate_1
        // .//GEN-BEGIN:_preprocessCreate_2_be
    }
    // .//GEN-END:_preprocessCreate_2_be
    // .//GEN-BEGIN:_duplicateCheck_1_be
    /** Ensure that a duplicate record is not created. */
    private void duplicateCheck(UOW uow, PrinterOutputTypeMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_duplicateCheck_1_be
        // Add custom code //GEN-FIRST:_duplicateCheck_1


        // .//GEN-LAST:_duplicateCheck_1
        // .//GEN-BEGIN:_duplicateCheck_2_be
        if (input.getOutputType() == null)
            return;
        Criteria criteria = new Criteria();
        criteria.setTable( PrinterOutputTypeMeta.getName() );
        // .//GEN-END:_duplicateCheck_2_be
        // Add custom criteria //GEN-FIRST:_duplicateCheck_2


        // .//GEN-LAST:_duplicateCheck_2
        // .//GEN-BEGIN:_duplicateCheck_3_be
        criteria.addCriteria(PrinterOutputTypeMeta.OUTPUT_TYPE, input.getOutputType());
        Collection col = uow.query(criteria);
        // .//GEN-END:_duplicateCheck_3_be
        // Add custom code //GEN-FIRST:_duplicateCheck_3


        // .//GEN-LAST:_duplicateCheck_3
        // .//GEN-BEGIN:_duplicateCheck_4_be
        if (col != null && !col.isEmpty()) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DuplicateKeyException(PrinterOutputTypeMeta.getLabelToken()));
            throw appExps;
        }
    }
    // .//GEN-END:_duplicateCheck_4_be
    // .//GEN-BEGIN:_createDomain_1_be
    /** Create the domain object. */
    private PrinterOutputType createDomain(UOW uow, PrinterOutputTypeMaintenanceCreateInDto input, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        PrinterOutputType domain = new PrinterOutputType();
        ApplicationExceptions appExps = null;
        // .//GEN-END:_createDomain_1_be
        // Add custom code //GEN-FIRST:_createDomain_1


        // .//GEN-LAST:_createDomain_1
        // .//GEN-BEGIN:_createDomain_2_be
        try {
            domain.updateOutputType(input.getOutputType());
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
            domain.updateDirectPrinting(input.getDirectPrinting());
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
    private PrinterOutputType postCreate(UOW uow, PrinterOutputTypeMaintenanceCreateInDto input, PrinterOutputType domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_postCreate_1_be
        // Add custom code //GEN-FIRST:_postCreate_1


        // .//GEN-LAST:_postCreate_1
        // .//GEN-BEGIN:_postCreate_2_be
        return domain;
    }
    // .//GEN-END:_postCreate_2_be
    // .//GEN-BEGIN:_preprocessRetrieve_1_be
    /** Preprocess the input for the retrieve method. */
    private void preprocess(UOW uow, PrinterOutputTypeMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessRetrieve_1_be
        // Add custom code //GEN-FIRST:_preprocessRetrieve_1


        // .//GEN-LAST:_preprocessRetrieve_1
        // .//GEN-BEGIN:_preprocessRetrieve_2_be
    }
    // .//GEN-END:_preprocessRetrieve_2_be
    // .//GEN-BEGIN:_loadRetrieve_1_be
    /** Retrieve the domain object. */
    private PrinterOutputType load(UOW uow, PrinterOutputTypeMaintenanceRetrieveInDto input) throws FrameworkException, ApplicationExceptions {
        PrinterOutputType domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( PrinterOutputTypeMeta.getName() );
        // .//GEN-END:_loadRetrieve_1_be
        // Add custom criteria //GEN-FIRST:_loadRetrieve_1


        // .//GEN-LAST:_loadRetrieve_1
        // .//GEN-BEGIN:_loadRetrieve_2_be
        criteria.addCriteria(PrinterOutputTypeMeta.OUTPUT_TYPE, input.getOutputType());
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (PrinterOutputType) itr.next();
        // .//GEN-END:_loadRetrieve_2_be
        // Add custom code //GEN-FIRST:_loadRetrieve_2


        // .//GEN-LAST:_loadRetrieve_2
        // .//GEN-BEGIN:_loadRetrieve_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(PrinterOutputTypeMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadRetrieve_3_be
    // .//GEN-BEGIN:_buildRetrieveOutDto_1_be
    /** Create the RetrieveOutDto. */
    private PrinterOutputTypeMaintenanceRetrieveOutDto buildRetrieveOutDto(UOW uow, PrinterOutputTypeMaintenanceRetrieveInDto input, PrinterOutputType domain) throws FrameworkException, ApplicationExceptions {
        PrinterOutputTypeMaintenanceRetrieveOutDto output = new PrinterOutputTypeMaintenanceRetrieveOutDto();
        // .//GEN-END:_buildRetrieveOutDto_1_be
        // Add custom code //GEN-FIRST:_buildRetrieveOutDto_1


        // .//GEN-LAST:_buildRetrieveOutDto_1
        // .//GEN-BEGIN:_buildRetrieveOutDto_2_be
        output.setOutputType(domain.getOutputType());
        output.setDescription(domain.getDescription());
        output.setDirectPrinting(domain.getDirectPrinting());
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
    private void preprocess(UOW uow, PrinterOutputTypeMaintenanceUpdateInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessUpdate_1_be
        // Add custom code //GEN-FIRST:_preprocessUpdate_1


        // .//GEN-LAST:_preprocessUpdate_1
        // .//GEN-BEGIN:_preprocessUpdate_2_be
    }
    // .//GEN-END:_preprocessUpdate_2_be
    // .//GEN-BEGIN:_loadUpdate_1_be
    /** Retrieve the domain object. */
    private PrinterOutputType load(UOW uow, PrinterOutputTypeMaintenanceUpdateInDto input)
    throws FrameworkException, ApplicationExceptions {
        PrinterOutputType domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( PrinterOutputTypeMeta.getName() );
        // .//GEN-END:_loadUpdate_1_be
        // Add custom criteria //GEN-FIRST:_loadUpdate_1


        // .//GEN-LAST:_loadUpdate_1
        // .//GEN-BEGIN:_loadUpdate_2_be
        criteria.addCriteria(PrinterOutputTypeMeta.OUTPUT_TYPE, input.getOutputType());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (PrinterOutputType) itr.next();
        // .//GEN-END:_loadUpdate_2_be
        // Add custom code //GEN-FIRST:_loadUpdate_2


        // .//GEN-LAST:_loadUpdate_2
        // .//GEN-BEGIN:_loadUpdate_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(PrinterOutputTypeMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadUpdate_3_be
    // .//GEN-BEGIN:_updateDomain_1_be
    /** Update the domain object and add it to the UOW. */
    private void updateDomain(UOW uow, PrinterOutputTypeMaintenanceUpdateInDto input, PrinterOutputType domain, boolean fromPrevalidate)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_updateDomain_1_be
        // Add custom code //GEN-FIRST:_updateDomain_1


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
            domain.updateDirectPrinting(input.getDirectPrinting());
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
        // Add custom code //GEN-FIRST:_updateDomain_2


        // .//GEN-LAST:_updateDomain_2
        // .//GEN-BEGIN:_updateDomain_3_be
        if (appExps != null && appExps.size() > 0)
            throw appExps;
    }
    // .//GEN-END:_updateDomain_3_be
    // .//GEN-BEGIN:_preprocessDelete_1_be
    /** Preprocess the input for the delete method. */
    private void preprocess(UOW uow, PrinterOutputTypeMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_preprocessDelete_1_be
        // Add custom code //GEN-FIRST:_preprocessDelete_1


        // .//GEN-LAST:_preprocessDelete_1
        // .//GEN-BEGIN:_preprocessDelete_2_be
    }
    // .//GEN-END:_preprocessDelete_2_be
    // .//GEN-BEGIN:_loadDelete_1_be
    /** Retrieve the domain object. */
    private PrinterOutputType load(UOW uow, PrinterOutputTypeMaintenanceDeleteInDto input) throws FrameworkException, ApplicationExceptions {
        PrinterOutputType domain = null;
        Criteria criteria = new Criteria();
        criteria.setTable( PrinterOutputTypeMeta.getName() );
        // .//GEN-END:_loadDelete_1_be
        // Add custom criteria //GEN-FIRST:_loadDelete_1


        // .//GEN-LAST:_loadDelete_1
        // .//GEN-BEGIN:_loadDelete_2_be
        criteria.addCriteria(PrinterOutputTypeMeta.OUTPUT_TYPE, input.getOutputType());
        criteria.setLocking(Criteria.LOCKING_PARANOID);
        Iterator itr = uow.query(criteria).iterator();
        if (itr.hasNext())
            domain = (PrinterOutputType) itr.next();
        // .//GEN-END:_loadDelete_2_be
        // Add custom code //GEN-FIRST:_loadDelete_2


        // .//GEN-LAST:_loadDelete_2
        // .//GEN-BEGIN:_loadDelete_3_be
        if (domain == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(PrinterOutputTypeMeta.getLabelToken()));
            throw appExps;
        }
        return domain;
    }
    // .//GEN-END:_loadDelete_3_be
    // .//GEN-BEGIN:_deleteDomain_1_be
    /** Delete the domain object from the domain. */
    private void deleteDomain(UOW uow, PrinterOutputTypeMaintenanceDeleteInDto input, PrinterOutputType domain) throws FrameworkException, ApplicationExceptions {
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
    /** Add foreign objects to PrinterOutputTypeMaintenanceRetrieveOutDto */
    private void addForeignObjectsToRetrieveOut(UOW uow, PrinterOutputType domain, PrinterOutputTypeMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {
    }
    // .//GEN-END:_addForeignObjectsToRetrieveOut_1_be
    // .//GEN-BEGIN:_validateForeignObjects_1_be
    /** This will validate the Foreign Objects. */
    private void validateForeignObjects(UOW uow, PrinterOutputTypeMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_validateForeignObjects_1_be
        // .//GEN-BEGIN:_validateForeignObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_validateForeignObjects_2_be
    // .//GEN-BEGIN:_createPrevalidateOutDto_1_be
    private PrinterOutputTypeMaintenancePrevalidateOutDto createPrevalidateOutDto(UOW uow, PrinterOutputType domain, PrinterOutputTypeMaintenanceCreateInDto input)
    throws FrameworkException, ApplicationExceptions {
        PrinterOutputTypeMaintenancePrevalidateOutDto output = new PrinterOutputTypeMaintenancePrevalidateOutDto();
        output.setOutputType(domain.getOutputType());
        output.setDescription(domain.getDescription());
        output.setDirectPrinting(domain.getDirectPrinting());
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
    /** Add related objects to PrinterOutputTypeMaintenanceRetrieveOutDto */
    private void addRelatedDtosToRetrieveOut(UOW uow, PrinterOutputType printerOutputType, PrinterOutputTypeMaintenanceRetrieveOutDto output)
    throws FrameworkException, ApplicationExceptions {
        // .//GEN-END:_addRelatedDtosToRetrieveOut_1_be
        // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_1_be
        if (printerOutputType.getOutputType() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(OutputCommandMeta.getName());
            criteria.addCriteria(OutputCommandMeta.OUTPUT_TYPE, printerOutputType.getOutputType());
            criteria.addOrderBy("SequenceNo", Criteria.ORDER_BY_ASC);
            // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_1_be
            // Add custom code to set the criteria before the query //GEN-FIRST:_addRelatedDtosToRetrieveOut_OutputCommand_1


            // .//GEN-LAST:_addRelatedDtosToRetrieveOut_OutputCommand_1
            // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_2_be
            Iterator itrMany = uow.query(criteria).iterator();
            while (itrMany.hasNext()) {
                OutputCommand outputCommand = (OutputCommand) itrMany.next();
                OutputCommandDto dto = new OutputCommandDto();
                // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_2_be
                // Add custom code before all the setters //GEN-FIRST:_addRelatedDtosToRetrieveOut_OutputCommand_2


                // .//GEN-LAST:_addRelatedDtosToRetrieveOut_OutputCommand_2
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_OutputCommandId_1_be
                dto.setOutputCommandId(outputCommand.getOutputCommandId());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_OutputCommandId_1_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_OutputType_1_be
                dto.setOutputType(outputCommand.getOutputType());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_OutputType_1_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_SequenceNo_1_be
                dto.setSequenceNo(outputCommand.getSequenceNo());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_SequenceNo_1_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_OsPattern_1_be
                dto.setOsPattern(outputCommand.getOsPattern());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_OsPattern_1_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_CommandLine_1_be
                dto.setCommandLine(outputCommand.getCommandLine());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_CommandLine_1_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_CreatedOn_1_be
                dto.setCreatedOn(outputCommand.getCreatedOn());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_CreatedOn_1_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_CreatedBy_1_be
                dto.setCreatedBy(outputCommand.getCreatedBy());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_CreatedBy_1_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_LastChangedOn_1_be
                dto.setLastChangedOn(outputCommand.getLastChangedOn());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_LastChangedOn_1_be
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_LastChangedBy_1_be
                dto.setLastChangedBy(outputCommand.getLastChangedBy());
                // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_LastChangedBy_1_be
                // Add custom code to pass values to the dto //GEN-FIRST:_addRelatedDtosToRetrieveOut_OutputCommand_3


                // .//GEN-LAST:_addRelatedDtosToRetrieveOut_OutputCommand_3
                // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_3_be
                output.addOutputCommand(dto);
            }
            // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_3_be
            // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_OutputCommand_6_be
        }

        // .//GEN-END:_addRelatedDtosToRetrieveOut_OutputCommand_6_be
        // .//GEN-BEGIN:_addRelatedDtosToRetrieveOut_2_be
    }
    // .//GEN-END:_addRelatedDtosToRetrieveOut_2_be
    // .//GEN-BEGIN:_deleteRelatedObjects_1_be
    /** Delete the related domain objects if the 'Cascading' constraint is specified. Throw an exception in case 'Restricted' constraint is specified. */
    private void deleteRelatedObjects(UOW uow, PrinterOutputTypeMaintenanceDeleteInDto input, PrinterOutputType printerOutputType) throws FrameworkException, ApplicationExceptions {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_deleteRelatedObjects_1_be
        // .//GEN-BEGIN:_deleteRelatedObjects_OutputCommand_1_be
        if (printerOutputType.getOutputType() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(OutputCommandMeta.getName());
            criteria.addCriteria(OutputCommandMeta.OUTPUT_TYPE, printerOutputType.getOutputType());
            criteria.setLocking(Criteria.LOCKING_PARANOID);
            // .//GEN-END:_deleteRelatedObjects_OutputCommand_1_be
            // Add custom code to set the criteria before the query //GEN-FIRST:_deleteRelatedObjects_OutputCommand_1


            // .//GEN-LAST:_deleteRelatedObjects_OutputCommand_1
            // .//GEN-BEGIN:_deleteRelatedObjects_OutputCommand_2_be
            Iterator itr = uow.query(criteria).iterator();
            while (itr.hasNext()) {
                OutputCommand outputCommand = (OutputCommand) itr.next();
                if (log.isDebugEnabled())
                    log.debug("Invoking the org.jaffa.modules.printing.components.outputcommandmaintenance.tx.OutputCommandMaintenanceTx class to cascade delete the related object " + outputCommand);
                org.jaffa.modules.printing.components.outputcommandmaintenance.dto.OutputCommandMaintenanceDeleteInDto deleteInDto = new org.jaffa.modules.printing.components.outputcommandmaintenance.dto.OutputCommandMaintenanceDeleteInDto();
                deleteInDto.setHeaderDto(input.getHeaderDto());
                deleteInDto.setOutputCommandId(outputCommand.getOutputCommandId());
                org.jaffa.modules.printing.components.outputcommandmaintenance.tx.OutputCommandMaintenanceTx tx = new org.jaffa.modules.printing.components.outputcommandmaintenance.tx.OutputCommandMaintenanceTx();
                // .//GEN-END:_deleteRelatedObjects_OutputCommand_2_be
                // Add custom code to set any addtional criteria before the delete //GEN-FIRST:_deleteRelatedObjects_OutputCommand_2


                // .//GEN-LAST:_deleteRelatedObjects_OutputCommand_2
                // .//GEN-BEGIN:_deleteRelatedObjects_OutputCommand_3_be
                tx.delete(deleteInDto, uow);
                tx.destroy();
            }
            // .//GEN-END:_deleteRelatedObjects_OutputCommand_3_be
        // .//GEN-BEGIN:_deleteRelatedObjects_OutputCommand_6_be
        }
        // .//GEN-END:_deleteRelatedObjects_OutputCommand_6_be
        // Add custom code //GEN-FIRST:_deleteRelatedObjects_1


        // .//GEN-LAST:_deleteRelatedObjects_1
        // .//GEN-BEGIN:_deleteRelatedObjects_2_be
        if (appExps != null)
            throw appExps;
    }
    // .//GEN-END:_deleteRelatedObjects_2_be
    // .//GEN-BEGIN:_domainObjectChangedTest_1_be
    /** Ensure the domain object has not been modified. */
    private void domainObjectChangedTest(Boolean performDirtyReadCheck, PrinterOutputType domain, org.jaffa.datatypes.DateTime lastChangedOn)
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
