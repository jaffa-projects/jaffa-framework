// .//GEN-BEGIN:_1_be
package org.jaffa.applications.test.modules.material.components.itemfinder.tx;

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

import org.jaffa.applications.test.modules.material.components.itemfinder.IItemFinder;
import org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderInDto;
import org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderOutDto;
import org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderOutRowDto;
import org.jaffa.applications.test.modules.material.domain.Item;
import org.jaffa.applications.test.modules.material.domain.ItemMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Finder for Item objects.
*/
public class ItemFinderTx implements IItemFinder {

    private static Logger log = Logger.getLogger(ItemFinderTx.class);

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
    /** Searches for Item objects.
     * @param input The criteria based on which the search will be performed.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */
    public ItemFinderOutDto find(ItemFinderInDto input)
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
            ItemFinderOutDto output = buildDto(uow, results, input);

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
    private Criteria buildCriteria(ItemFinderInDto input, UOW uow) {

        Criteria criteria = new Criteria();
        criteria.setTable( ItemMeta.getName() );
        // .//GEN-END:_buildCriteria_1_be
        // Add custom criteria //GEN-FIRST:_buildCriteria_1


        // .//GEN-LAST:_buildCriteria_1
        // .//GEN-BEGIN:_buildCriteria_2_be
        FinderTx.addCriteria(input.getSegregationCode(), ItemMeta.SC, criteria);
        FinderTx.addCriteria(input.getPartNo(), ItemMeta.PART, criteria);
        FinderTx.addCriteria(input.getSerial(), ItemMeta.SERIAL, criteria);
        FinderTx.addCriteria(input.getQty(), ItemMeta.QTY, criteria);


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
    private ItemFinderOutDto buildDto(UOW uow, Collection results, ItemFinderInDto input)
    throws UOWException {

        ItemFinderOutDto output = new ItemFinderOutDto();

        int maxRecords = input.getMaxRecords() != null ? input.getMaxRecords().intValue() : 0;

        int counter = 0;
        for (Iterator i = results.iterator(); i.hasNext();) {
            if (++counter > maxRecords && maxRecords > 0) {
                output.setMoreRecordsExist(Boolean.TRUE);
                break;
            }

            ItemFinderOutRowDto row = new ItemFinderOutRowDto();
            Item item = (Item) i.next();
            // .//GEN-END:_buildDto_1_be
            // Add custom code before all the setters //GEN-FIRST:_buildDto_1


            // .//GEN-LAST:_buildDto_1
            // .//GEN-BEGIN:_buildDto_SegregationCode_1_be
            row.setSegregationCode(item.getSc());
            // .//GEN-END:_buildDto_SegregationCode_1_be
            // .//GEN-BEGIN:_buildDto_ItemId_1_be
            row.setItemId(item.getItemId());
            // .//GEN-END:_buildDto_ItemId_1_be
            // .//GEN-BEGIN:_buildDto_PartNo_1_be
            row.setPartNo(item.getPart());
            // .//GEN-END:_buildDto_PartNo_1_be
            // .//GEN-BEGIN:_buildDto_Serial_1_be
            row.setSerial(item.getSerial());
            // .//GEN-END:_buildDto_Serial_1_be
            // .//GEN-BEGIN:_buildDto_Qty_1_be
            row.setQty(item.getQty());
            // .//GEN-END:_buildDto_Qty_1_be

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
