// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.userfinder.dto;

import java.util.*;
import org.jaffa.components.finder.*;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The output for the UserFinder.
 */
public class UserFinderOutDto extends FinderOutDto {

    /** Holds an array of rows to be returned. */
    private List rows;

    /** Default Constructor.*/    
    public UserFinderOutDto() {
        rows = new ArrayList();
    }

    /** Add rows.
     * @param rows Rows.
     */    
    public void addRows(UserFinderOutRowDto rows) {
        this.rows.add(rows);
    }
    
    /** Add rows at the specified position.
     * @param rows Rows.
     * @param index The position at which the rows is to be added.
     */    
    public void setRows(UserFinderOutRowDto rows, int index) {
        //-- check bounds for index
        if ((index < 0) || (index > this.rows.size()))
            throw new IndexOutOfBoundsException();
        
        this.rows.set(index, rows);
    }
    
    /** Add an array of rows. This will overwrite existing rows.
     * @param rows An array of rows.
     */    
    public void setRows(UserFinderOutRowDto[] rows) {
        this.rows = Arrays.asList(rows);
    }
    
    /** Clear existing rows.
     */    
    public void clearRows() {
        rows.clear();
    }
    
    /** Remove rows.
     * @param rows Rows.
     * @return A true indicates a rows was removed. A false indicates, the rows was not found.
     */    
    public boolean removeRows(UserFinderOutRowDto rows) {
        return this.rows.remove(rows);
    }
    
    /** Returns a rows from the specified position.
     * @param index The position index.
     * @return Rows.
     */    
    public UserFinderOutRowDto getRows(int index) {
        //-- check bounds for index
        if ((index < 0) || (index > rows.size()))
            throw new IndexOutOfBoundsException();
        
        return (UserFinderOutRowDto) rows.get(index);
    }
    
    /** Returns an array of rows.
     * @return An array of rows.
     */    
    public UserFinderOutRowDto[] getRows() {
        return (UserFinderOutRowDto[]) rows.toArray(new UserFinderOutRowDto[0]);
    }
    
    /** Returns a count of rows.
     * @return The count of rows.
     */    
    public int getRowsCount() {
        return rows.size();
    }


    /** Returns the debug information
     * @return The debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserFinderOutDto>");
        buf.append("<moreRecordsExist>"); if (getMoreRecordsExist() != null) buf.append(getMoreRecordsExist()); buf.append("</moreRecordsExist>");

        buf.append("<rows>");
        UserFinderOutRowDto[] rows = getRows();
        for (int i = 0; i < rows.length; i++) {
            buf.append(rows[i].toString());
        }
        buf.append("</rows>");
        
        buf.append("</UserFinderOutDto>");
        return buf.toString();
    }

    // .//GEN-END:_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
