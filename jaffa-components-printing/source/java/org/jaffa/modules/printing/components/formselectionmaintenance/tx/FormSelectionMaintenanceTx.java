// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formselectionmaintenance.tx;

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

import org.jaffa.modules.printing.components.formselectionmaintenance.IFormSelectionMaintenance;
import org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceInDto;
import org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutDto;
import org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto;
import org.jaffa.modules.printing.domain.FormUsage;
import org.jaffa.modules.printing.domain.FormUsageMeta;
import org.jaffa.modules.printing.services.FormPrintFactory;

// .//GEN-END:_1_be
//GEN-FIRST:_imports
import org.jaffa.modules.printing.domain.FormGroup;
import org.jaffa.modules.printing.domain.FormGroupMeta;
import org.jaffa.modules.printing.domain.FormDefinition;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;
import org.jaffa.modules.printing.components.formselectionmaintenance.FormSelectionException;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.modules.printing.services.FormPrintRequest;
import org.jaffa.modules.printing.services.FormProcessor;
import org.jaffa.presentation.portlet.widgets.model.*;
import java.util.Properties;
import org.jaffa.session.ContextManager;
import org.jaffa.modules.printing.services.FormPrintingHelper;
import org.jaffa.modules.printing.domain.PrinterDefinition;
import org.jaffa.security.VariationContext;

// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Finder for FormUsage objects.
*/
public class FormSelectionMaintenanceTx implements IFormSelectionMaintenance {

    private static Logger log = Logger.getLogger(FormSelectionMaintenanceTx.class);

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
    // .//GEN-BEGIN:_find_1_be
    /** Searches for FormUsage objects.
     * @param input The criteria based on which the search will be performed.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */
    public FormSelectionMaintenanceOutDto find(FormSelectionMaintenanceInDto input)
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
            // Add custom code before the query//GEN-FIRST:_find_1
            if (log.isDebugEnabled())
                log.debug("The find() method is not supported. Instead use the findOutDto() method");
            if (1 == 1)
                return null;

            // .//GEN-LAST:_find_1
            // .//GEN-BEGIN:_find_2_be
            // Execute The Query
            Collection results = uow.query(criteria);
            // .//GEN-END:_find_2_be
            // Add custom code after the query//GEN-FIRST:_find_2


            // .//GEN-LAST:_find_2
            // .//GEN-BEGIN:_find_3_be
            // Convert the domain objects into the outbound dto
            FormSelectionMaintenanceOutDto output = buildDto(uow, results, input);

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
    private Criteria buildCriteria(FormSelectionMaintenanceInDto input, UOW uow) {

        Criteria criteria = new Criteria();
        criteria.setTable( FormUsageMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria//GEN-FIRST:_buildCriteria_1
        FinderTx.addCriteria(input.getEvent(), FormUsageMeta.EVENT_NAME, criteria);

        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be


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
        // Add custom criteria//GEN-FIRST:_buildCriteria_2


        // .//GEN-LAST:_buildCriteria_2
        // .//GEN-BEGIN:_buildCriteria_3_be
        return criteria;
    }
    // .//GEN-END:_buildCriteria_3_be
    // .//GEN-BEGIN:_buildDto_1_be
    private FormSelectionMaintenanceOutDto buildDto(UOW uow, Collection results, FormSelectionMaintenanceInDto input)
    throws UOWException {

        FormSelectionMaintenanceOutDto output = new FormSelectionMaintenanceOutDto();



        int maxRecords = input.getMaxRecords() != null ? input.getMaxRecords().intValue() : 0;

        int counter = 0;
        for (Iterator i = results.iterator(); i.hasNext();) {
            if (++counter > maxRecords && maxRecords > 0) {
                output.setMoreRecordsExist(Boolean.TRUE);
                break;
            }

            FormSelectionMaintenanceOutRowDto row = new FormSelectionMaintenanceOutRowDto();
            FormUsage formUsage = (FormUsage) i.next();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters//GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_FormName_1_be
            row.setFormName(formUsage.getFormName());
            // .//GEN-END:_buildDto_FormName_1_be

            // Add custom code to pass values to the dto//GEN-FIRST:_buildDto_2
            row.setCopies(formUsage.getCopies());

            // .//GEN-LAST:_buildDto_2
            // .//GEN-BEGIN:_buildDto_3_be
            output.addRows(row);
        }
        return output;
    }
    // .//GEN-END:_buildDto_3_be
    // All the custom code goes here//GEN-FIRST:_custom

    // TODO-SWAT add script event here

    /** Searches for FormUsage objects.
     * @param input The criteria based on which the search will be performed.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */
    public FormSelectionMaintenanceOutDto findOutDto(FormSelectionMaintenanceInDto input)
    throws FrameworkException, ApplicationExceptions {
        // TODO-SWAT fire script event here

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
            
            // Execute The Query
            Collection results = uow.query(criteria);
            
            // Convert the domain objects into the outbound dto
            FormSelectionMaintenanceOutDto output = buildOutDto(uow, results, input);
            
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
    
    private FormSelectionMaintenanceOutDto buildOutDto(UOW uow, Collection results, FormSelectionMaintenanceInDto input)
    throws FrameworkException {
        FormSelectionMaintenanceOutDto output = new FormSelectionMaintenanceOutDto();
        Map<String, List<String>> printersPerOutputType = null; //key=outputType, value=List of Printers
        String variation = VariationContext.getVariation();
        String outputType = null;
        String[] keys = new String [6];
        if (input.getKey1() != null) {
            String[] keyArray = input.getKey1().getValues();
            keys[0] = keyArray[0];
        }        
        if (input.getKey2() != null) {
            String[] keyArray = input.getKey2().getValues();
            keys[1] = keyArray[0];
        }        
        if (input.getKey3() != null) {
            String[] keyArray = input.getKey3().getValues();
            keys[2] = keyArray[0];
        }
        if (input.getKey4() != null) {
            String[] keyArray = input.getKey4().getValues();
            keys[3] = keyArray[0];
        }
        if (input.getKey5() != null) {
            String[] keyArray = input.getKey5().getValues();
            keys[4] = keyArray[0];
        }
        if (input.getKey6() != null) {
            String[] keyArray = input.getKey6().getValues();
            keys[5] = keyArray[0];
        }
        
        if(log.isDebugEnabled())
            log.debug("Form Selection building OutDto using Event=" + input.getEvent() + ", key1=" + keys[0]
                    + ", key2=" + keys[1] + ", key3=" + keys[2] + ", key4=" + keys[3]
                    + ", key5=" + keys[4] + ", key6=" + keys[5] + ", variation=" + variation);

        for (Iterator i = results.iterator(); i.hasNext();) {
            FormUsage formUsage = (FormUsage) i.next();                                             
            FormDefinition formDefinition = FormProcessor.findFormDefinition(uow,formUsage.getFormName(),formUsage.getFormAlternate(),variation,outputType,keys);
            if (formDefinition != null) {
                FormSelectionMaintenanceOutRowDto row = new FormSelectionMaintenanceOutRowDto();
                row.setFormName(formDefinition.getFormName());
                row.setCopies(formUsage.getCopies());               
                if (input.getEvent()!=null){
                    String mEvent[] = input.getEvent().getValues();
                    row.setEvent(mEvent[0]);
                }                
                if (keys[0] != null) {
                    row.setKey1(keys[0]);
                }
                if (keys[1] != null) {
                    row.setKey2(keys[1]);
                }
                if (keys[2] != null) {
                    row.setKey3(keys[2]);
                }
                if (keys[3] != null) {
                    row.setKey4(keys[3]);
                }
                if (keys[4] != null) {
                    row.setKey5(keys[4]);
                }
                if (keys[5] != null) {
                    row.setKey6(keys[5]);
                }
                if (input.getValue1()!=null){
                    String mValue1[] = input.getValue1().getValues();
                    row.setValue1(mValue1[0]);
                }
                if (input.getValue2()!=null){
                    String mValue2[] = input.getValue2().getValues();
                    row.setValue2(mValue2[0]);
                }
                if (input.getValue3()!=null){
                    String mValue3[] = input.getValue3().getValues();
                    row.setValue3(mValue3[0]);
                }
                if (input.getValue4()!=null){
                    String mValue4[] = input.getValue4().getValues();
                    row.setValue4(mValue4[0]);
                }
                if (input.getValue5()!=null){
                    String mValue5[] = input.getValue5().getValues();
                    row.setValue5(mValue5[0]);
                }
                if (input.getValue6()!=null){
                    String mValue6[] = input.getValue6().getValues();
                    row.setValue6(mValue6[0]);
                }
                // Only use the form alternate if one was specified in Form Usage.
                // Use the form definition selected because it may have selected the "-RL" right-to-left alternate.
                if (formUsage.getFormAlternate() != null) {
                    row.setFormAlternateName(formDefinition.getFormAlternate());
                }
                row.setVariation(formDefinition.getFormVariation());
                row.setAdditionalDataComponent(formDefinition.getAdditionalDataComponent());
                
                //Query on FormGroup to find FormType.
                Criteria criteria = new Criteria();
                criteria.setTable(FormGroupMeta.getName());
                criteria.addCriteria(FormGroupMeta.FORM_NAME , formUsage.getFormName());                              
                Collection col = uow.query(criteria);
                Iterator itr = col.iterator();
                if(itr.hasNext()) {
                    FormGroup formGroup=(FormGroup) itr.next();
                    row.setFormType(formGroup.getFormType());
                }
                // Default the printer if possible and only if it has not already been initialized
                if (row.getPrinter() == null && input.getDefaultPrinters() != null) {
                    // Construct a Map of valid printers per outputType
                    if (printersPerOutputType == null)
                        printersPerOutputType = determinePrintersPerOutputType(uow, input.getDefaultPrinters());                   
                    // Get the first printer for an outputType
                    List<String> printers = printersPerOutputType.get(formDefinition.getOutputType());
                    if (printers != null && printers.size() >= 0)
                        row.setPrinter(printers.get(0));
                }

                if (row.getEmail() == null
                        && (FormPrintFactory.ENGINE_TYPE_ITEXT.equals(row.getFormType())
                        || FormPrintFactory.ENGINE_TYPE_FOP.equals(row.getFormType())))
                    row.setEmail(getUserEmail());

                output.addRows(row);
            }
        }
        return output;
    }
   
    /**
     * This method invokes creates a FormPrintRequest object and passes
     * it to the FormPrinting engine for processing.
     */
    public void dispatchPrintRequest(FormPrintRequest formPrintRequest) throws FrameworkException, ApplicationExceptions{
        if(log.isDebugEnabled())
            log.debug("Just before dispatching Print request finally");
        FormPrintingHelper.dispatchPrintRequest(formPrintRequest);
    }
    
    /** Creates a Map of printers keyed by the outputType.
     * @param uow The UOW.
     * @param defaultPrinters The printers to be added to the Map. If a printer is invalid, it'll be ignored.
     * @return Map of printers keyed by the outputType.
     * @throws FrameworkException Indicates some system error
     */
    private Map<String, List<String>> determinePrintersPerOutputType(UOW uow, String[] defaultPrinters) throws FrameworkException {
        // Log the input
        if (log.isDebugEnabled()) {
            StringBuffer buf = new StringBuffer("Input to determinePrintersPerOutputType(): ");
            for (int i = 0; i < defaultPrinters.length; i++) {
                if (i > 0)
                    buf.append(',');
                buf.append(defaultPrinters[i]);
            }
            log.debug(buf.toString());
        }
        
        // Generate the Map
        Map<String, List<String>> printersPerOutputType = new HashMap<String, List<String>>();
        for (String printer : defaultPrinters) {
            PrinterDefinition pd = PrinterDefinition.findByPK(uow, printer);
            if (pd != null && pd.getOutputType() != null) {
                List<String> printers = printersPerOutputType.get(pd.getOutputType());
                if (printers == null) {
                    printers = new LinkedList<String>();
                    printersPerOutputType.put(pd.getOutputType(), printers);
                }
                printers.add(printer);
            } else {
                if (log.isDebugEnabled())
                    log.debug("The default printer '" + printer + "' has not been setup for JaffaComponentsPrinting. It'll be ignored");
            }
        }
        
        // Log the output
        if (log.isDebugEnabled()) {
            StringBuffer buf = new StringBuffer("Output from determinePrintersPerOutputType():\n");
            for (Map.Entry<String, List<String>> me : printersPerOutputType.entrySet()) {
                buf.append(me.getKey()).append('=');
                boolean printerAdded = false;
                for (String printer : me.getValue()) {
                    if (!printerAdded)
                        printerAdded = true;
                    else
                        buf.append(',');
                    buf.append(printer);
                }
                buf.append('\n');
            }
            log.debug(buf.toString());
        }
        return printersPerOutputType;
    }
    
    private String userEmail = null;
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String email) {
        userEmail = email;
    }
    
    // .//GEN-LAST:_custom
}
