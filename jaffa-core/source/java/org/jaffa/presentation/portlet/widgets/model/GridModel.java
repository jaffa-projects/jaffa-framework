/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package org.jaffa.presentation.portlet.widgets.model;

import org.apache.log4j.*;
import java.util.*;

/** Model for the Grid widget.
 */
public class GridModel implements IWidgetModel  {
    private static Logger log = Logger.getLogger(GridModel.class);

    /** Holds a List of GridModelRow objects */
    private List m_rows = new ArrayList();

    /** For efficiency resons, this map will hold the GridModelRow objects, keyed by the rowId */
    private Map m_rowsMap = new HashMap();

    /** This is the rowId that will be assigned to the next new row created */
    private int m_maxRowId = 0;

    /** This flag will be set if any error occurs while writing the user-settings to the filesystem. */
    private boolean m_errorInSavingUserSettings = false;

    private GridModelRow m_targetRow = null;
    
    /** Creates and returns a new GridModelRow. This object will be added to the end of the the Grid.
     * @return a new GridModelRow object.
     */
    public GridModelRow newRow() {
        GridModelRow r = new GridModelRow(this, m_maxRowId++);
        m_rows.add(r);
        m_rowsMap.put(new Integer(r.getRowId()), r);
        return r;
    }

    /** Create a new Row at the specfied position in the Grid.
     * rowNum can be from 0 to GridModel.size().
     * if rowNum = GridModel.size(), then this function is the same as newRow();
     * @param rowNum The position at which the row is to be added.
     * @return a new GridModelRow object.
     */
    public GridModelRow newRow(int rowNum) {
        if(rowNum == m_rows.size() )
            return newRow();
        else if (rowNum > m_rows.size() )
            throw new RuntimeException("Array Out Of Bounds. Size=" + m_rows.size() + ", position=" + rowNum);
        else {
            GridModelRow r = new GridModelRow(this,m_maxRowId++);
            m_rows.add(rowNum,r);
            m_rowsMap.put(new Integer(r.getRowId()), r);
            return r;
        }
    }

    /** Empty out all the rows in the Grid */
    public void clearRows() {
        m_rows.clear();
        m_rowsMap.clear();
        m_maxRowId = 0;
    }

    /** Removes the row from the grid.
     * @param row The row to be removed.
     */
    public void removeRow(GridModelRow row) {
        m_rows.remove(row);
        m_rowsMap.remove(new Integer(row.getRowId()));
    }


    /** Returns a Collection of GridModelRow objects.
     * @return a Collection of GridModelRow objects.
     */
    public Collection getRows() {
        return m_rows;
    }

    /** Returns a Row object based on the position or the row in the List.
     * @param rowNum The position of the row.
     * @return a Row object based on the position or the row in the List.
     */
    public GridModelRow getRow(int rowNum) {
        return (GridModelRow) m_rows.get(rowNum);
    }

    /** Returns the position of this row in the list.
     * Useful for trees where you have the row, and you need
     * to insert new rows after it, so you need the number of this row.
     * @param row The row object to lookup.
     * @return row number of that row (0 is the first row, -1 if not found)
     */
    public int getRowNum(GridModelRow row) {
        return m_rows.indexOf(row);
    }

    
    /** Returns a Row object based on the rowId.
     * @param rowId The id of the row.
     * @return Row object based on the rowId.
     */
    public GridModelRow getRowById(int rowId) {
        return (GridModelRow) m_rowsMap.get(new Integer(rowId));
    }

    /** Returns a field value from a given row.
     * @param columnName The name of the field.
     * @param rowNum The position of the row.
     * @return a field value from a given row.
     */
    public Object getElement(String columnName, int rowNum) {
        GridModelRow row = getRow(rowNum);
        if(row == null)
            throw new RuntimeException("No Such Row : " + rowNum);
        return row.get(columnName);
    }

    /** getter for the attribute errorInSavingUserSettings
     */
    public boolean getErrorInSavingUserSettings() {
        return m_errorInSavingUserSettings;
    }

    /** setter for the attribute
     */
    public void setErrorInSavingUserSettings(boolean value) {
        m_errorInSavingUserSettings = value;
    }
    
    public void setTarget(GridModelRow row) {
        m_targetRow = row;
    }
    
    public GridModelRow getTarget() {
        return m_targetRow;
    }

}
