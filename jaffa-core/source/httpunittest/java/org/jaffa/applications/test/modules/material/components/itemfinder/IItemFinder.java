// .//GEN-BEGIN:_1_be
package org.jaffa.applications.test.modules.material.components.itemfinder;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderInDto;
import org.jaffa.applications.test.modules.material.components.itemfinder.dto.ItemFinderOutDto;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** Interface for ItemFinder components.
 */
public interface IItemFinder {

    /** Searches for Item objects.
     * @param input The criteria based on which the search will be performed.
     * @throws ApplicationExceptions This will be thrown if the criteria contains invalid data.
     * @throws FrameworkException Indicates some system error
     * @return The search results.
     */    
    public ItemFinderOutDto find(ItemFinderInDto input) throws FrameworkException, ApplicationExceptions;
    
    /**
     * This should be invoked, when done with the component.
     */
    public void destroy();

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
