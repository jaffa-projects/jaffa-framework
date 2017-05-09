// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.printing.components.formdefinitionviewer.tx;

import java.util.*;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.Criteria;

import org.jaffa.modules.printing.components.formdefinitionviewer.IFormDefinitionViewer;
import org.jaffa.modules.printing.components.formdefinitionviewer.dto.FormDefinitionViewerInDto;
import org.jaffa.modules.printing.components.formdefinitionviewer.dto.FormDefinitionViewerOutDto;
import org.jaffa.modules.printing.domain.FormDefinition;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;



// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import org.jaffa.modules.printing.domain.FormTemplate;
import org.jaffa.modules.printing.domain.FormTemplateMeta;
// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Viewer for FormDefinition objects.
*/
public class FormDefinitionViewerTx implements IFormDefinitionViewer {

    private static Logger log = Logger.getLogger(FormDefinitionViewerTx.class);
    
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
    // .//GEN-BEGIN:_read_1_be
    /** Returns the details for FormDefinition.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */    
    public FormDefinitionViewerOutDto read(FormDefinitionViewerInDto input)
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
            // .//GEN-END:_read_1_be
            // Add custom code before the query//GEN-FIRST:_read_1


            // .//GEN-LAST:_read_1
            // .//GEN-BEGIN:_read_2_be
            // Execute The Query
            Collection results = uow.query(criteria);
            // .//GEN-END:_read_2_be
            // Add custom code after the query//GEN-FIRST:_read_2


            // .//GEN-LAST:_read_2
            // .//GEN-BEGIN:_read_3_be
            // Convert the domain objects into the outbound dto
            FormDefinitionViewerOutDto output = buildDto(uow, results);
            
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
    // .//GEN-END:_read_3_be
    // .//GEN-BEGIN:_buildCriteria_1_be
    private Criteria buildCriteria(FormDefinitionViewerInDto input, UOW uow) {
        Criteria criteria = new Criteria();
        criteria.setTable( FormDefinitionMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria//GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        criteria.addCriteria(FormDefinitionMeta.FORM_ID, input.getFormId());
        // .//GEN-END:_buildCriteria_2_be
        // Add custom criteria//GEN-FIRST:_buildCriteria_2


        // .//GEN-LAST:_buildCriteria_2
        // .//GEN-BEGIN:_buildCriteria_3_be
        return criteria;
    }
    // .//GEN-END:_buildCriteria_3_be
    // .//GEN-BEGIN:_buildDto_1_be
    private FormDefinitionViewerOutDto buildDto(UOW uow, Collection results)
    throws UOWException {
        FormDefinitionViewerOutDto output = null;
        Iterator itr = results.iterator();
        if (itr.hasNext()) {
            // just return the details for the 1st record retrieved.
            FormDefinition formDefinition = (FormDefinition) itr.next();
            output = new FormDefinitionViewerOutDto();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters//GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_FormId_1_be
            output.setFormId(formDefinition.getFormId());
            // .//GEN-END:_buildDto_FormId_1_be
            // .//GEN-BEGIN:_buildDto_FormName_1_be
            output.setFormName(formDefinition.getFormName());
            // .//GEN-END:_buildDto_FormName_1_be
            // .//GEN-BEGIN:_buildDto_FormAlternate_1_be
            output.setFormAlternate(formDefinition.getFormAlternate());
            // .//GEN-END:_buildDto_FormAlternate_1_be
            // .//GEN-BEGIN:_buildDto_FormVariation_1_be
            output.setFormVariation(formDefinition.getFormVariation());
            // .//GEN-END:_buildDto_FormVariation_1_be
            // .//GEN-BEGIN:_buildDto_Description_1_be
            output.setDescription(formDefinition.getDescription());
            // .//GEN-END:_buildDto_Description_1_be
            // .//GEN-BEGIN:_buildDto_Remarks_1_be
            output.setRemarks(formDefinition.getRemarks());
            // .//GEN-END:_buildDto_Remarks_1_be
            // .//GEN-BEGIN:_buildDto_OutputType_1_be
            output.setOutputType(formDefinition.getOutputType());
            // .//GEN-END:_buildDto_OutputType_1_be
            // .//GEN-BEGIN:_buildDto_FollowOnFormName_1_be
            output.setFollowOnFormName(formDefinition.getFollowOnFormName());
            // .//GEN-END:_buildDto_FollowOnFormName_1_be
            // .//GEN-BEGIN:_buildDto_FormTemplate_1_be
            output.setFormTemplate(formDefinition.getFormTemplate());
            // .//GEN-END:_buildDto_FormTemplate_1_be
            // .//GEN-BEGIN:_buildDto_FieldLayout_1_be
            output.setFieldLayout(formDefinition.getFieldLayout());
            // .//GEN-END:_buildDto_FieldLayout_1_be
            // .//GEN-BEGIN:_buildDto_DomClass_1_be
            output.setDomClass(formDefinition.getDomClass());
            // .//GEN-END:_buildDto_DomClass_1_be
            // .//GEN-BEGIN:_buildDto_DomKey1_1_be
            output.setDomKey1(formDefinition.getDomKey1());
            // .//GEN-END:_buildDto_DomKey1_1_be
            // .//GEN-BEGIN:_buildDto_DomKey2_1_be
            output.setDomKey2(formDefinition.getDomKey2());
            // .//GEN-END:_buildDto_DomKey2_1_be
            // .//GEN-BEGIN:_buildDto_DomKey3_1_be
            output.setDomKey3(formDefinition.getDomKey3());
            // .//GEN-END:_buildDto_DomKey3_1_be
            // .//GEN-BEGIN:_buildDto_DomKey4_1_be
            output.setDomKey4(formDefinition.getDomKey4());
            // .//GEN-END:_buildDto_DomKey4_1_be
            // .//GEN-BEGIN:_buildDto_DomKey5_1_be
            output.setDomKey5(formDefinition.getDomKey5());
            // .//GEN-END:_buildDto_DomKey5_1_be
            // .//GEN-BEGIN:_buildDto_DomKey6_1_be
            output.setDomKey6(formDefinition.getDomKey6());
            // .//GEN-END:_buildDto_DomKey6_1_be
            // .//GEN-BEGIN:_buildDto_DomFactory_1_be
            output.setDomFactory(formDefinition.getDomFactory());
            // .//GEN-END:_buildDto_DomFactory_1_be
            // .//GEN-BEGIN:_buildDto_AdditionalDataComponent_1_be
            output.setAdditionalDataComponent(formDefinition.getAdditionalDataComponent());
            // .//GEN-END:_buildDto_AdditionalDataComponent_1_be
            // .//GEN-BEGIN:_buildDto_FollowOnFormAlternate_1_be
            output.setFollowOnFormAlternate(formDefinition.getFollowOnFormAlternate());
            // .//GEN-END:_buildDto_FollowOnFormAlternate_1_be
            // .//GEN-BEGIN:_buildDto_2_be
            // Add related objects, if required
            addRelatedDtos(uow, output, formDefinition);
            // .//GEN-END:_buildDto_2_be
            // Add custom code to pass values to the dto//GEN-FIRST:_buildDto_2


            // .//GEN-LAST:_buildDto_2
            // .//GEN-BEGIN:_buildDto_3_be
        }
        return output;
    }
    // .//GEN-END:_buildDto_3_be
    // .//GEN-BEGIN:_addRelatedDtos_1_be
    private void addRelatedDtos(UOW uow, FormDefinitionViewerOutDto output, FormDefinition formDefinition)
    throws UOWException {
        // .//GEN-END:_addRelatedDtos_1_be
        // .//GEN-BEGIN:_addRelatedDtos_2_be
    }
    // .//GEN-END:_addRelatedDtos_2_be
    // All the custom code goes here//GEN-FIRST:_custom

    /** Holds value of property formTemplateContents. */
    private byte[] formTemplateContents;
    
    public byte[] loadFormTemplate(FormDefinitionViewerInDto input) throws FrameworkException,ApplicationExceptions{
        UOW uow=new UOW();
        try{
            Criteria criteria = new Criteria();
            criteria.setTable( FormTemplateMeta.getName() );
            criteria.addCriteria(FormTemplateMeta.FORM_ID, input.getFormId());
            Iterator itr = uow.query(criteria).iterator();
            if(itr.hasNext()){
                FormTemplate formTemplate = (FormTemplate) itr.next();
                formTemplateContents=formTemplate.getTemplateData();
            }
        }finally {
            if (uow != null)
                uow.rollback();
        }
        return formTemplateContents;
    }
    
    // .//GEN-LAST:_custom
}
