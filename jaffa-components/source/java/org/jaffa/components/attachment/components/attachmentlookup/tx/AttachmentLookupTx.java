// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.components.attachment.components.attachmentlookup.tx;

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

import org.jaffa.components.attachment.components.attachmentlookup.IAttachmentLookup;
import org.jaffa.components.attachment.components.attachmentlookup.dto.AttachmentLookupInDto;
import org.jaffa.components.attachment.components.attachmentlookup.dto.AttachmentLookupOutDto;
import org.jaffa.components.attachment.components.attachmentlookup.dto.AttachmentLookupOutRowDto;
import org.jaffa.components.attachment.domain.Attachment;
import org.jaffa.components.attachment.domain.AttachmentMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Lookup for Attachment objects.
*/
public class AttachmentLookupTx implements IAttachmentLookup {

    private static Logger log = Logger.getLogger(AttachmentLookupTx.class);

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
    /** Searches for Attachment objects.
     * @param input The criteria based on which the search will be performed.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */
    public AttachmentLookupOutDto find(AttachmentLookupInDto input)
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
            AttachmentLookupOutDto output = buildDto(uow, results, input);

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
    private Criteria buildCriteria(AttachmentLookupInDto input, UOW uow) {

        Criteria criteria = new Criteria();
        criteria.setTable( AttachmentMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        FinderTx.addCriteria(input.getAttachmentId(), AttachmentMeta.ATTACHMENT_ID, criteria);
        FinderTx.addCriteria(input.getSerializedKey(), AttachmentMeta.SERIALIZED_KEY, criteria);
        FinderTx.addCriteria(input.getOriginalFileName(), AttachmentMeta.ORIGINAL_FILE_NAME, criteria);
        FinderTx.addCriteria(input.getAttachmentType(), AttachmentMeta.ATTACHMENT_TYPE, criteria);
        FinderTx.addCriteria(input.getContentType(), AttachmentMeta.CONTENT_TYPE, criteria);
        FinderTx.addCriteria(input.getDescription(), AttachmentMeta.DESCRIPTION, criteria);
        FinderTx.addCriteria(input.getRemarks(), AttachmentMeta.REMARKS, criteria);
        FinderTx.addCriteria(input.getSupercededBy(), AttachmentMeta.SUPERCEDED_BY, criteria);
        FinderTx.addCriteria(input.getCreatedOn(), AttachmentMeta.CREATED_ON, criteria);
        FinderTx.addCriteria(input.getCreatedBy(), AttachmentMeta.CREATED_BY, criteria);
        FinderTx.addCriteria(input.getLastChangedOn(), AttachmentMeta.LAST_CHANGED_ON, criteria);
        FinderTx.addCriteria(input.getLastChangedBy(), AttachmentMeta.LAST_CHANGED_BY, criteria);


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
    private AttachmentLookupOutDto buildDto(UOW uow, Collection results, AttachmentLookupInDto input)
    throws UOWException {

        AttachmentLookupOutDto output = new AttachmentLookupOutDto();



        int maxRecords = input.getMaxRecords() != null ? input.getMaxRecords().intValue() : 0;

        int counter = 0;
        for (Iterator i = results.iterator(); i.hasNext();) {
            if (++counter > maxRecords && maxRecords > 0) {
                output.setMoreRecordsExist(Boolean.TRUE);
                break;
            }

            AttachmentLookupOutRowDto row = new AttachmentLookupOutRowDto();
            Attachment attachment = (Attachment) i.next();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_AttachmentId_1_be
            row.setAttachmentId(attachment.getAttachmentId());
            // .//GEN-END:_buildDto_AttachmentId_1_be
            // .//GEN-BEGIN:_buildDto_SerializedKey_1_be
            row.setSerializedKey(attachment.getSerializedKey());
            // .//GEN-END:_buildDto_SerializedKey_1_be
            // .//GEN-BEGIN:_buildDto_OriginalFileName_1_be
            row.setOriginalFileName(attachment.getOriginalFileName());
            // .//GEN-END:_buildDto_OriginalFileName_1_be
            // .//GEN-BEGIN:_buildDto_AttachmentType_1_be
            row.setAttachmentType(attachment.getAttachmentType());
            // .//GEN-END:_buildDto_AttachmentType_1_be
            // .//GEN-BEGIN:_buildDto_ContentType_1_be
            row.setContentType(attachment.getContentType());
            // .//GEN-END:_buildDto_ContentType_1_be
            // .//GEN-BEGIN:_buildDto_Description_1_be
            row.setDescription(attachment.getDescription());
            // .//GEN-END:_buildDto_Description_1_be
            // .//GEN-BEGIN:_buildDto_Remarks_1_be
            row.setRemarks(attachment.getRemarks());
            // .//GEN-END:_buildDto_Remarks_1_be
            // .//GEN-BEGIN:_buildDto_SupercededBy_1_be
            row.setSupercededBy(attachment.getSupercededBy());
            // .//GEN-END:_buildDto_SupercededBy_1_be
            // .//GEN-BEGIN:_buildDto_CreatedOn_1_be
            row.setCreatedOn(attachment.getCreatedOn());
            // .//GEN-END:_buildDto_CreatedOn_1_be
            // .//GEN-BEGIN:_buildDto_CreatedBy_1_be
            row.setCreatedBy(attachment.getCreatedBy());
            // .//GEN-END:_buildDto_CreatedBy_1_be
            // .//GEN-BEGIN:_buildDto_LastChangedOn_1_be
            row.setLastChangedOn(attachment.getLastChangedOn());
            // .//GEN-END:_buildDto_LastChangedOn_1_be
            // .//GEN-BEGIN:_buildDto_LastChangedBy_1_be
            row.setLastChangedBy(attachment.getLastChangedBy());
            // .//GEN-END:_buildDto_LastChangedBy_1_be
            // .//GEN-BEGIN:_buildDto_Data_1_be
            row.setData(attachment.getData());
            // .//GEN-END:_buildDto_Data_1_be

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
