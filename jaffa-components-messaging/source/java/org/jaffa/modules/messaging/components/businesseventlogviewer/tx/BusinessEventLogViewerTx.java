// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.modules.messaging.components.businesseventlogviewer.tx;

import java.util.*;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.Criteria;

import org.jaffa.modules.messaging.components.businesseventlogviewer.IBusinessEventLogViewer;
import org.jaffa.modules.messaging.components.businesseventlogviewer.dto.BusinessEventLogViewerInDto;
import org.jaffa.modules.messaging.components.businesseventlogviewer.dto.BusinessEventLogViewerOutDto;
import org.jaffa.modules.messaging.domain.BusinessEventLog;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;


import org.jaffa.modules.messaging.components.businesseventlogviewer.dto.AttachmentDto;
import org.jaffa.components.attachment.domain.Attachment;
import org.jaffa.components.attachment.domain.AttachmentMeta;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import org.jaffa.persistence.util.PersistentHelper;



// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Viewer for BusinessEventLog objects.
*/
public class BusinessEventLogViewerTx implements IBusinessEventLogViewer {

    private static Logger log = Logger.getLogger(BusinessEventLogViewerTx.class);
    
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
    /** Returns the details for BusinessEventLog.
     * @param input The criteria based on which an object will be retrieved.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error.
     * @return The object details. A null indicates, the object was not found.
     */    
    public BusinessEventLogViewerOutDto read(BusinessEventLogViewerInDto input)
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
            BusinessEventLogViewerOutDto output = buildDto(uow, results);
            
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
    // .//GEN-END:_read_3_be
    // .//GEN-BEGIN:_buildCriteria_1_be
    private Criteria buildCriteria(BusinessEventLogViewerInDto input, UOW uow) {
        Criteria criteria = new Criteria();
        criteria.setTable( BusinessEventLogMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria//GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        criteria.addCriteria(BusinessEventLogMeta.LOG_ID, input.getLogId());
        // .//GEN-END:_buildCriteria_2_be
        // Add custom criteria//GEN-FIRST:_buildCriteria_2


        // .//GEN-LAST:_buildCriteria_2
        // .//GEN-BEGIN:_buildCriteria_3_be
        return criteria;
    }
    // .//GEN-END:_buildCriteria_3_be
    // .//GEN-BEGIN:_buildDto_1_be
    private BusinessEventLogViewerOutDto buildDto(UOW uow, Collection results)
    throws UOWException {
        BusinessEventLogViewerOutDto output = null;
        Iterator itr = results.iterator();
        if (itr.hasNext()) {
            // just return the details for the 1st record retrieved.
            BusinessEventLog businessEventLog = (BusinessEventLog) itr.next();
            output = new BusinessEventLogViewerOutDto();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters//GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_LogId_1_be
            output.setLogId(businessEventLog.getLogId());
            // .//GEN-END:_buildDto_LogId_1_be
            // .//GEN-BEGIN:_buildDto_CorrelationType_1_be
            output.setCorrelationType(businessEventLog.getCorrelationType());
            // .//GEN-END:_buildDto_CorrelationType_1_be
            // .//GEN-BEGIN:_buildDto_CorrelationKey1_1_be
            output.setCorrelationKey1(businessEventLog.getCorrelationKey1());
            // .//GEN-END:_buildDto_CorrelationKey1_1_be
            // .//GEN-BEGIN:_buildDto_CorrelationKey2_1_be
            output.setCorrelationKey2(businessEventLog.getCorrelationKey2());
            // .//GEN-END:_buildDto_CorrelationKey2_1_be
            // .//GEN-BEGIN:_buildDto_CorrelationKey3_1_be
            output.setCorrelationKey3(businessEventLog.getCorrelationKey3());
            // .//GEN-END:_buildDto_CorrelationKey3_1_be
            // .//GEN-BEGIN:_buildDto_ScheduledTaskId_1_be
            output.setScheduledTaskId(businessEventLog.getScheduledTaskId());
            // .//GEN-END:_buildDto_ScheduledTaskId_1_be
            // .//GEN-BEGIN:_buildDto_MessageId_1_be
            output.setMessageId(businessEventLog.getMessageId());
            // .//GEN-END:_buildDto_MessageId_1_be
            // .//GEN-BEGIN:_buildDto_LoggedOn_1_be
            output.setLoggedOn(businessEventLog.getLoggedOn());
            // .//GEN-END:_buildDto_LoggedOn_1_be
            // .//GEN-BEGIN:_buildDto_LoggedBy_1_be
            output.setLoggedBy(businessEventLog.getLoggedBy());
            // .//GEN-END:_buildDto_LoggedBy_1_be
            // .//GEN-BEGIN:_buildDto_ProcessName_1_be
            output.setProcessName(businessEventLog.getProcessName());
            // .//GEN-END:_buildDto_ProcessName_1_be
            // .//GEN-BEGIN:_buildDto_SubProcessName_1_be
            output.setSubProcessName(businessEventLog.getSubProcessName());
            // .//GEN-END:_buildDto_SubProcessName_1_be
            // .//GEN-BEGIN:_buildDto_MessageType_1_be
            output.setMessageType(businessEventLog.getMessageType());
            // .//GEN-END:_buildDto_MessageType_1_be
            // .//GEN-BEGIN:_buildDto_MessageText_1_be
            output.setMessageText(businessEventLog.getMessageText());
            // .//GEN-END:_buildDto_MessageText_1_be
            // .//GEN-BEGIN:_buildDto_SourceClass_1_be
            output.setSourceClass(businessEventLog.getSourceClass());
            // .//GEN-END:_buildDto_SourceClass_1_be
            // .//GEN-BEGIN:_buildDto_SourceMethod_1_be
            output.setSourceMethod(businessEventLog.getSourceMethod());
            // .//GEN-END:_buildDto_SourceMethod_1_be
            // .//GEN-BEGIN:_buildDto_SourceLine_1_be
            output.setSourceLine(businessEventLog.getSourceLine());
            // .//GEN-END:_buildDto_SourceLine_1_be
            // .//GEN-BEGIN:_buildDto_StackTrace_1_be
            output.setStackTrace(businessEventLog.getStackTrace());
            // .//GEN-END:_buildDto_StackTrace_1_be
            // .//GEN-BEGIN:_buildDto_2_be
            // Add related objects, if required
            addRelatedDtos(uow, output, businessEventLog);
            // .//GEN-END:_buildDto_2_be
            // Add custom code to pass values to the dto//GEN-FIRST:_buildDto_2


            // .//GEN-LAST:_buildDto_2
            // .//GEN-BEGIN:_buildDto_3_be
        }
        return output;
    }
    // .//GEN-END:_buildDto_3_be
    // .//GEN-BEGIN:_addRelatedDtos_1_be
    private void addRelatedDtos(UOW uow, BusinessEventLogViewerOutDto output, BusinessEventLog businessEventLog)
    throws UOWException {
        // .//GEN-END:_addRelatedDtos_1_be
        // .//GEN-BEGIN:_addRelatedDtos_Attachment_1_be
        if (businessEventLog.getLogId() != null) {
            Criteria criteria = new Criteria();
            criteria.setTable(AttachmentMeta.getName());
            criteria.addCriteria(AttachmentMeta.SERIALIZED_KEY, businessEventLog.getLogId());
            criteria.addOrderBy("AttachmentId", Criteria.ORDER_BY_ASC);
            // .//GEN-END:_addRelatedDtos_Attachment_1_be
            // Add custom code to set the criteria before the query//GEN-FIRST:_addRelatedDtos_Attachment_1
            // Create a new Criteria, and pass the correct serializedKey
            try {
                criteria = new Criteria();
                criteria.setTable(AttachmentMeta.getName());
                criteria.addCriteria(AttachmentMeta.SERIALIZED_KEY, PersistentHelper.generateSerializedKey(businessEventLog));
                criteria.addOrderBy(AttachmentMeta.ATTACHMENT_ID, Criteria.ORDER_BY_ASC);
            } catch (Exception e) {
                String str = "Error in generating the serialized key";
                log.error(str, e);
                throw new RuntimeException(str, e);
            }

            // .//GEN-LAST:_addRelatedDtos_Attachment_1
            // .//GEN-BEGIN:_addRelatedDtos_Attachment_2_be
            Iterator itr = uow.query(criteria).iterator();
            while (itr.hasNext()) {
                Attachment attachment = (Attachment) itr.next();
                AttachmentDto dto = new AttachmentDto();
                // .//GEN-END:_addRelatedDtos_Attachment_2_be
                // Add custom code before all the setters//GEN-FIRST:_addRelatedDtos_Attachment_2


                // .//GEN-LAST:_addRelatedDtos_Attachment_2
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_AttachmentId_1_be
                dto.setAttachmentId(attachment.getAttachmentId());
                // .//GEN-END:_addRelatedDtos_Attachment_AttachmentId_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_SerializedKey_1_be
                dto.setSerializedKey(attachment.getSerializedKey());
                // .//GEN-END:_addRelatedDtos_Attachment_SerializedKey_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_OriginalFileName_1_be
                dto.setOriginalFileName(attachment.getOriginalFileName());
                // .//GEN-END:_addRelatedDtos_Attachment_OriginalFileName_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_AttachmentType_1_be
                dto.setAttachmentType(attachment.getAttachmentType());
                // .//GEN-END:_addRelatedDtos_Attachment_AttachmentType_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_ContentType_1_be
                dto.setContentType(attachment.getContentType());
                // .//GEN-END:_addRelatedDtos_Attachment_ContentType_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_Description_1_be
                dto.setDescription(attachment.getDescription());
                // .//GEN-END:_addRelatedDtos_Attachment_Description_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_Remarks_1_be
                dto.setRemarks(attachment.getRemarks());
                // .//GEN-END:_addRelatedDtos_Attachment_Remarks_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_SupercededBy_1_be
                dto.setSupercededBy(attachment.getSupercededBy());
                // .//GEN-END:_addRelatedDtos_Attachment_SupercededBy_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_CreatedOn_1_be
                dto.setCreatedOn(attachment.getCreatedOn());
                // .//GEN-END:_addRelatedDtos_Attachment_CreatedOn_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_CreatedBy_1_be
                dto.setCreatedBy(attachment.getCreatedBy());
                // .//GEN-END:_addRelatedDtos_Attachment_CreatedBy_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_LastChangedOn_1_be
                dto.setLastChangedOn(attachment.getLastChangedOn());
                // .//GEN-END:_addRelatedDtos_Attachment_LastChangedOn_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_LastChangedBy_1_be
                dto.setLastChangedBy(attachment.getLastChangedBy());
                // .//GEN-END:_addRelatedDtos_Attachment_LastChangedBy_1_be
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_Data_1_be
                dto.setData(attachment.getData());
                // .//GEN-END:_addRelatedDtos_Attachment_Data_1_be
                // Add custom code to pass values to the dto//GEN-FIRST:_addRelatedDtos_Attachment_3


                // .//GEN-LAST:_addRelatedDtos_Attachment_3
                // .//GEN-BEGIN:_addRelatedDtos_Attachment_3_be
                output.addAttachment(dto);
            }
        }
        // .//GEN-END:_addRelatedDtos_Attachment_3_be
        // .//GEN-BEGIN:_addRelatedDtos_2_be
    }
    // .//GEN-END:_addRelatedDtos_2_be
    // All the custom code goes here//GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
