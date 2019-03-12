// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formdefinitionfinder.tx;

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

import org.jaffa.modules.printing.components.formdefinitionfinder.IFormDefinitionFinder;
import org.jaffa.modules.printing.components.formdefinitionfinder.dto.FormDefinitionFinderInDto;
import org.jaffa.modules.printing.components.formdefinitionfinder.dto.FormDefinitionFinderOutDto;
import org.jaffa.modules.printing.components.formdefinitionfinder.dto.FormDefinitionFinderOutRowDto;
import org.jaffa.modules.printing.domain.FormDefinition;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Finder for FormDefinition objects.
*/
public class FormDefinitionFinderTx implements IFormDefinitionFinder {

    private static Logger log = Logger.getLogger(FormDefinitionFinderTx.class);

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
    /** Searches for FormDefinition objects.
     * @param input The criteria based on which the search will be performed.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */
    public FormDefinitionFinderOutDto find(FormDefinitionFinderInDto input)
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
            FormDefinitionFinderOutDto output = buildDto(uow, results, input);

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
    private Criteria buildCriteria(FormDefinitionFinderInDto input, UOW uow) {

        Criteria criteria = new Criteria();
        criteria.setTable( FormDefinitionMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        FinderTx.addCriteria(input.getFormId(), FormDefinitionMeta.FORM_ID, criteria);
        FinderTx.addCriteria(input.getFormName(), FormDefinitionMeta.FORM_NAME, criteria);
        FinderTx.addCriteria(input.getFormAlternate(), FormDefinitionMeta.FORM_ALTERNATE, criteria);
        FinderTx.addCriteria(input.getFormVariation(), FormDefinitionMeta.FORM_VARIATION, criteria);
        FinderTx.addCriteria(input.getOutputType(), FormDefinitionMeta.OUTPUT_TYPE, criteria);
        FinderTx.addCriteria(input.getFormTemplate(), FormDefinitionMeta.FORM_TEMPLATE, criteria);


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
    private FormDefinitionFinderOutDto buildDto(UOW uow, Collection results, FormDefinitionFinderInDto input)
    throws UOWException {

        FormDefinitionFinderOutDto output = new FormDefinitionFinderOutDto();



        int maxRecords = input.getMaxRecords() != null ? input.getMaxRecords().intValue() : 0;

        int counter = 0;
        for (Iterator i = results.iterator(); i.hasNext();) {
            if (++counter > maxRecords && maxRecords > 0) {
                output.setMoreRecordsExist(Boolean.TRUE);
                break;
            }

            FormDefinitionFinderOutRowDto row = new FormDefinitionFinderOutRowDto();
            FormDefinition formDefinition = (FormDefinition) i.next();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_FormId_1_be
            row.setFormId(formDefinition.getFormId());
            // .//GEN-END:_buildDto_FormId_1_be
            // .//GEN-BEGIN:_buildDto_FormName_1_be
            row.setFormName(formDefinition.getFormName());
            // .//GEN-END:_buildDto_FormName_1_be
            // .//GEN-BEGIN:_buildDto_FormAlternate_1_be
            row.setFormAlternate(formDefinition.getFormAlternate());
            // .//GEN-END:_buildDto_FormAlternate_1_be
            // .//GEN-BEGIN:_buildDto_FormVariation_1_be
            row.setFormVariation(formDefinition.getFormVariation());
            // .//GEN-END:_buildDto_FormVariation_1_be
            // .//GEN-BEGIN:_buildDto_Description_1_be
            row.setDescription(formDefinition.getDescription());
            // .//GEN-END:_buildDto_Description_1_be
            // .//GEN-BEGIN:_buildDto_FormTemplate_1_be
            row.setFormTemplate(formDefinition.getFormTemplate());
            // .//GEN-END:_buildDto_FormTemplate_1_be
            // .//GEN-BEGIN:_buildDto_OutputType_1_be
            row.setOutputType(formDefinition.getOutputType());
            // .//GEN-END:_buildDto_OutputType_1_be

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
