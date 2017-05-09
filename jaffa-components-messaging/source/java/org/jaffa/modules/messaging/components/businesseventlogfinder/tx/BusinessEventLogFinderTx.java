// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.messaging.components.businesseventlogfinder.tx;

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

import org.jaffa.modules.messaging.components.businesseventlogfinder.IBusinessEventLogFinder;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderInDto;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutDto;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto;
import org.jaffa.modules.messaging.domain.BusinessEventLog;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Finder for BusinessEventLog objects.
*/
public class BusinessEventLogFinderTx implements IBusinessEventLogFinder {

    private static Logger log = Logger.getLogger(BusinessEventLogFinderTx.class);

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
    /** Searches for BusinessEventLog objects.
     * @param input The criteria based on which the search will be performed.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */
    public BusinessEventLogFinderOutDto find(BusinessEventLogFinderInDto input)
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
            BusinessEventLogFinderOutDto output = buildDto(uow, results, input);

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
    private Criteria buildCriteria(BusinessEventLogFinderInDto input, UOW uow) {

        Criteria criteria = new Criteria();
        criteria.setTable( BusinessEventLogMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        FinderTx.addCriteria(input.getLogId(), BusinessEventLogMeta.LOG_ID, criteria);
        FinderTx.addCriteria(input.getCorrelationType(), BusinessEventLogMeta.CORRELATION_TYPE, criteria);
        FinderTx.addCriteria(input.getCorrelationKey1(), BusinessEventLogMeta.CORRELATION_KEY1, criteria);
        FinderTx.addCriteria(input.getCorrelationKey2(), BusinessEventLogMeta.CORRELATION_KEY2, criteria);
        FinderTx.addCriteria(input.getCorrelationKey3(), BusinessEventLogMeta.CORRELATION_KEY3, criteria);
        FinderTx.addCriteria(input.getScheduledTaskId(), BusinessEventLogMeta.SCHEDULED_TASK_ID, criteria);
        FinderTx.addCriteria(input.getMessageId(), BusinessEventLogMeta.MESSAGE_ID, criteria);
        FinderTx.addCriteria(input.getLoggedOn(), BusinessEventLogMeta.LOGGED_ON, criteria);
        FinderTx.addCriteria(input.getLoggedBy(), BusinessEventLogMeta.LOGGED_BY, criteria);
        FinderTx.addCriteria(input.getProcessName(), BusinessEventLogMeta.PROCESS_NAME, criteria);
        FinderTx.addCriteria(input.getSubProcessName(), BusinessEventLogMeta.SUB_PROCESS_NAME, criteria);
        FinderTx.addCriteria(input.getMessageType(), BusinessEventLogMeta.MESSAGE_TYPE, criteria);
        FinderTx.addCriteria(input.getMessageText(), BusinessEventLogMeta.MESSAGE_TEXT, criteria);
        FinderTx.addCriteria(input.getSourceClass(), BusinessEventLogMeta.SOURCE_CLASS, criteria);
        FinderTx.addCriteria(input.getSourceMethod(), BusinessEventLogMeta.SOURCE_METHOD, criteria);
        FinderTx.addCriteria(input.getSourceLine(), BusinessEventLogMeta.SOURCE_LINE, criteria);
        FinderTx.addCriteria(input.getStackTrace(), BusinessEventLogMeta.STACK_TRACE, criteria);


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
    private BusinessEventLogFinderOutDto buildDto(UOW uow, Collection results, BusinessEventLogFinderInDto input)
    throws UOWException {

        BusinessEventLogFinderOutDto output = new BusinessEventLogFinderOutDto();



        int maxRecords = input.getMaxRecords() != null ? input.getMaxRecords().intValue() : 0;

        int counter = 0;
        for (Iterator i = results.iterator(); i.hasNext();) {
            if (++counter > maxRecords && maxRecords > 0) {
                output.setMoreRecordsExist(Boolean.TRUE);
                break;
            }

            BusinessEventLogFinderOutRowDto row = new BusinessEventLogFinderOutRowDto();
            BusinessEventLog businessEventLog = (BusinessEventLog) i.next();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_LogId_1_be
            row.setLogId(businessEventLog.getLogId());
            // .//GEN-END:_buildDto_LogId_1_be
            // .//GEN-BEGIN:_buildDto_CorrelationType_1_be
            row.setCorrelationType(businessEventLog.getCorrelationType());
            // .//GEN-END:_buildDto_CorrelationType_1_be
            // .//GEN-BEGIN:_buildDto_CorrelationKey1_1_be
            row.setCorrelationKey1(businessEventLog.getCorrelationKey1());
            // .//GEN-END:_buildDto_CorrelationKey1_1_be
            // .//GEN-BEGIN:_buildDto_CorrelationKey2_1_be
            row.setCorrelationKey2(businessEventLog.getCorrelationKey2());
            // .//GEN-END:_buildDto_CorrelationKey2_1_be
            // .//GEN-BEGIN:_buildDto_CorrelationKey3_1_be
            row.setCorrelationKey3(businessEventLog.getCorrelationKey3());
            // .//GEN-END:_buildDto_CorrelationKey3_1_be
            // .//GEN-BEGIN:_buildDto_ScheduledTaskId_1_be
            row.setScheduledTaskId(businessEventLog.getScheduledTaskId());
            // .//GEN-END:_buildDto_ScheduledTaskId_1_be
            // .//GEN-BEGIN:_buildDto_MessageId_1_be
            row.setMessageId(businessEventLog.getMessageId());
            // .//GEN-END:_buildDto_MessageId_1_be
            // .//GEN-BEGIN:_buildDto_LoggedOn_1_be
            row.setLoggedOn(businessEventLog.getLoggedOn());
            // .//GEN-END:_buildDto_LoggedOn_1_be
            // .//GEN-BEGIN:_buildDto_LoggedBy_1_be
            row.setLoggedBy(businessEventLog.getLoggedBy());
            // .//GEN-END:_buildDto_LoggedBy_1_be
            // .//GEN-BEGIN:_buildDto_ProcessName_1_be
            row.setProcessName(businessEventLog.getProcessName());
            // .//GEN-END:_buildDto_ProcessName_1_be
            // .//GEN-BEGIN:_buildDto_SubProcessName_1_be
            row.setSubProcessName(businessEventLog.getSubProcessName());
            // .//GEN-END:_buildDto_SubProcessName_1_be
            // .//GEN-BEGIN:_buildDto_MessageType_1_be
            row.setMessageType(businessEventLog.getMessageType());
            // .//GEN-END:_buildDto_MessageType_1_be
            // .//GEN-BEGIN:_buildDto_MessageText_1_be
            row.setMessageText(businessEventLog.getMessageText());
            // .//GEN-END:_buildDto_MessageText_1_be
            // .//GEN-BEGIN:_buildDto_SourceClass_1_be
            row.setSourceClass(businessEventLog.getSourceClass());
            // .//GEN-END:_buildDto_SourceClass_1_be
            // .//GEN-BEGIN:_buildDto_SourceMethod_1_be
            row.setSourceMethod(businessEventLog.getSourceMethod());
            // .//GEN-END:_buildDto_SourceMethod_1_be
            // .//GEN-BEGIN:_buildDto_SourceLine_1_be
            row.setSourceLine(businessEventLog.getSourceLine());
            // .//GEN-END:_buildDto_SourceLine_1_be
            // .//GEN-BEGIN:_buildDto_StackTrace_1_be
            row.setStackTrace(businessEventLog.getStackTrace());
            // .//GEN-END:_buildDto_StackTrace_1_be

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
