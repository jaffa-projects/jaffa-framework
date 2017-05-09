// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.printing.components.formdefinitionlookup.tx;

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

import org.jaffa.modules.printing.components.formdefinitionlookup.IFormDefinitionLookup;
import org.jaffa.modules.printing.components.formdefinitionlookup.dto.FormDefinitionLookupInDto;
import org.jaffa.modules.printing.components.formdefinitionlookup.dto.FormDefinitionLookupOutDto;
import org.jaffa.modules.printing.components.formdefinitionlookup.dto.FormDefinitionLookupOutRowDto;
import org.jaffa.modules.printing.domain.FormDefinition;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Lookup for FormDefinition objects.
*/
public class FormDefinitionLookupTx implements IFormDefinitionLookup {

    private static Logger log = Logger.getLogger(FormDefinitionLookupTx.class);

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
    public FormDefinitionLookupOutDto find(FormDefinitionLookupInDto input)
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
            FormDefinitionLookupOutDto output = buildDto(uow, results, input);

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
    private Criteria buildCriteria(FormDefinitionLookupInDto input, UOW uow) {

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
        FinderTx.addCriteria(input.getFieldLayout(), FormDefinitionMeta.FIELD_LAYOUT, criteria);
        FinderTx.addCriteria(input.getDescription(), FormDefinitionMeta.DESCRIPTION, criteria);
        FinderTx.addCriteria(input.getRemarks(), FormDefinitionMeta.REMARKS, criteria);
        FinderTx.addCriteria(input.getDomFactory(), FormDefinitionMeta.DOM_FACTORY, criteria);
        FinderTx.addCriteria(input.getDomClass(), FormDefinitionMeta.DOM_CLASS, criteria);
        FinderTx.addCriteria(input.getDomKey1(), FormDefinitionMeta.DOM_KEY1, criteria);
        FinderTx.addCriteria(input.getDomKey2(), FormDefinitionMeta.DOM_KEY2, criteria);
        FinderTx.addCriteria(input.getDomKey3(), FormDefinitionMeta.DOM_KEY3, criteria);
        FinderTx.addCriteria(input.getDomKey4(), FormDefinitionMeta.DOM_KEY4, criteria);
        FinderTx.addCriteria(input.getDomKey5(), FormDefinitionMeta.DOM_KEY5, criteria);
        FinderTx.addCriteria(input.getDomKey6(), FormDefinitionMeta.DOM_KEY6, criteria);
        FinderTx.addCriteria(input.getAdditionalDataComponent(), FormDefinitionMeta.ADDITIONAL_DATA_COMPONENT, criteria);
        FinderTx.addCriteria(input.getFollowOnFormName(), FormDefinitionMeta.FOLLOW_ON_FORM_NAME, criteria);
        FinderTx.addCriteria(input.getFollowOnFormAlternate(), FormDefinitionMeta.FOLLOW_ON_FORM_ALTERNATE, criteria);
        FinderTx.addCriteria(input.getCreatedOn(), FormDefinitionMeta.CREATED_ON, criteria);
        FinderTx.addCriteria(input.getCreatedBy(), FormDefinitionMeta.CREATED_BY, criteria);
        FinderTx.addCriteria(input.getLastChangedOn(), FormDefinitionMeta.LAST_CHANGED_ON, criteria);
        FinderTx.addCriteria(input.getLastChangedBy(), FormDefinitionMeta.LAST_CHANGED_BY, criteria);


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
    private FormDefinitionLookupOutDto buildDto(UOW uow, Collection results, FormDefinitionLookupInDto input)
    throws UOWException {

        FormDefinitionLookupOutDto output = new FormDefinitionLookupOutDto();



        int maxRecords = input.getMaxRecords() != null ? input.getMaxRecords().intValue() : 0;

        int counter = 0;
        for (Iterator i = results.iterator(); i.hasNext();) {
            if (++counter > maxRecords && maxRecords > 0) {
                output.setMoreRecordsExist(Boolean.TRUE);
                break;
            }

            FormDefinitionLookupOutRowDto row = new FormDefinitionLookupOutRowDto();
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
            // .//GEN-BEGIN:_buildDto_OutputType_1_be
            row.setOutputType(formDefinition.getOutputType());
            // .//GEN-END:_buildDto_OutputType_1_be
            // .//GEN-BEGIN:_buildDto_FormTemplate_1_be
            row.setFormTemplate(formDefinition.getFormTemplate());
            // .//GEN-END:_buildDto_FormTemplate_1_be
            // .//GEN-BEGIN:_buildDto_FieldLayout_1_be
            row.setFieldLayout(formDefinition.getFieldLayout());
            // .//GEN-END:_buildDto_FieldLayout_1_be
            // .//GEN-BEGIN:_buildDto_Description_1_be
            row.setDescription(formDefinition.getDescription());
            // .//GEN-END:_buildDto_Description_1_be
            // .//GEN-BEGIN:_buildDto_Remarks_1_be
            row.setRemarks(formDefinition.getRemarks());
            // .//GEN-END:_buildDto_Remarks_1_be
            // .//GEN-BEGIN:_buildDto_DomFactory_1_be
            row.setDomFactory(formDefinition.getDomFactory());
            // .//GEN-END:_buildDto_DomFactory_1_be
            // .//GEN-BEGIN:_buildDto_DomClass_1_be
            row.setDomClass(formDefinition.getDomClass());
            // .//GEN-END:_buildDto_DomClass_1_be
            // .//GEN-BEGIN:_buildDto_DomKey1_1_be
            row.setDomKey1(formDefinition.getDomKey1());
            // .//GEN-END:_buildDto_DomKey1_1_be
            // .//GEN-BEGIN:_buildDto_DomKey2_1_be
            row.setDomKey2(formDefinition.getDomKey2());
            // .//GEN-END:_buildDto_DomKey2_1_be
            // .//GEN-BEGIN:_buildDto_DomKey3_1_be
            row.setDomKey3(formDefinition.getDomKey3());
            // .//GEN-END:_buildDto_DomKey3_1_be
            // .//GEN-BEGIN:_buildDto_DomKey4_1_be
            row.setDomKey4(formDefinition.getDomKey4());
            // .//GEN-END:_buildDto_DomKey4_1_be
            // .//GEN-BEGIN:_buildDto_DomKey5_1_be
            row.setDomKey5(formDefinition.getDomKey5());
            // .//GEN-END:_buildDto_DomKey5_1_be
            // .//GEN-BEGIN:_buildDto_DomKey6_1_be
            row.setDomKey6(formDefinition.getDomKey6());
            // .//GEN-END:_buildDto_DomKey6_1_be
            // .//GEN-BEGIN:_buildDto_AdditionalDataComponent_1_be
            row.setAdditionalDataComponent(formDefinition.getAdditionalDataComponent());
            // .//GEN-END:_buildDto_AdditionalDataComponent_1_be
            // .//GEN-BEGIN:_buildDto_FollowOnFormName_1_be
            row.setFollowOnFormName(formDefinition.getFollowOnFormName());
            // .//GEN-END:_buildDto_FollowOnFormName_1_be
            // .//GEN-BEGIN:_buildDto_FollowOnFormAlternate_1_be
            row.setFollowOnFormAlternate(formDefinition.getFollowOnFormAlternate());
            // .//GEN-END:_buildDto_FollowOnFormAlternate_1_be
            // .//GEN-BEGIN:_buildDto_CreatedOn_1_be
            row.setCreatedOn(formDefinition.getCreatedOn());
            // .//GEN-END:_buildDto_CreatedOn_1_be
            // .//GEN-BEGIN:_buildDto_CreatedBy_1_be
            row.setCreatedBy(formDefinition.getCreatedBy());
            // .//GEN-END:_buildDto_CreatedBy_1_be
            // .//GEN-BEGIN:_buildDto_LastChangedOn_1_be
            row.setLastChangedOn(formDefinition.getLastChangedOn());
            // .//GEN-END:_buildDto_LastChangedOn_1_be
            // .//GEN-BEGIN:_buildDto_LastChangedBy_1_be
            row.setLastChangedBy(formDefinition.getLastChangedBy());
            // .//GEN-END:_buildDto_LastChangedBy_1_be

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
