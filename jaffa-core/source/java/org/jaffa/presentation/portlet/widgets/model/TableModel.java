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
import org.jaffa.util.ListMap;
import org.jaffa.presentation.portlet.widgets.model.exceptions.ColumnMismatchRuntimeException;

/** Model for the Table widget.
 */
public class TableModel implements IWidgetModel  {
    private static Logger log = Logger.getLogger(TableModel.class);

    /** This maintains a columnName-columnObject pairs */
    private ListMap m_columns = new ListMap();

    /** This maintains a collection of rows */
    private List m_rows = new ArrayList();

    /** This maintains a collection of rows selected by the user */
    private List m_selectedRows = new ArrayList();

    /** A flag to indicate if the model has been modified */
    private boolean m_modelChanged = false;


    /** Clear out all the columns */
    public void clearColumns() {
        m_columns.clear();
        setModelChanged(true);
    }

    /** Add a column to the Table. An exisiting column by the same name will be over-written.
     * @param name The name of the column.
     * @param dataType The data type of the column.
     */
    public void addColumn(String name, String dataType) {
        Column column = new Column(name, dataType);
        m_columns.put(name, column);
        setModelChanged(true);
    }

    /** Returns a List of TableModel.Column objects, in the order of entry.
     * @return a List of TableModel.Column objects, in the order of entry.
     */
    public List getColumns() {
        return new ArrayList(m_columns.values());
    }

    /** Returns a List of columnNames, in the order of entry.
     * @return a List of columnNames, in the order of entry.
     */
    public List getColumnNames() {
        return new ArrayList(m_columns.keySet());
    }

    /** Returns the dataType for the columnName. A null will be returned in case no such column exists.
     * @param columnName The name of the column.
     * @return the dataType for the column.
     */
    public String getColumnDataType(String columnName) {
        Column column = (Column) m_columns.get(columnName);
        if (column != null)
            return column.getDataType();
        else
            return null;
    }

    /** Add a row to the table. A ColumnMismatchRuntimeException is thrown if the no. of fields does not equal the no. of columns.
     * @param fields The List of values.
     */
    public void addRow(List fields) {
        addRow(-1, fields);
    }

    /** Add a row to the table. A ColumnMismatchRuntimeException is thrown if the no. of fields does not equal the no. of columns.
     * @param position The index of the row.
     * @param fields The List of values.
     */
    public void addRow(int position, List fields) {
        if(fields.size() != m_columns.size()) {
            throw new ColumnMismatchRuntimeException("Wrong number of columns in Row, got " + fields.size() + " expected " + m_columns.size());
        }

        if (position == -1)
            m_rows.add(fields);
        else
            m_rows.add(position, fields);

        setModelChanged(true);
    }

    /** Clear out all the rows */
    public void clearRows() {
        m_rows.clear();
        setModelChanged(true);
    }

    /** Returns a Collection, where each element is a List of fields, in the order of the columns.
     * @return a Collection, where each element is a List of fields, in the order of the columns.
     */
    public List getRows() {
        return m_rows;
    }

    /** Returns a List of field values in column order for the input row number.
     * An IndexOutOfBoundsException will be thrown in case the rowNum is senseless.
     * @param rowNum The index of the row to be retrieved.
     * @return a List of field values in column order for the input row number.
     */
    public List getRow(int rowNum) {
        return (List) m_rows.get(rowNum);
    }

    /** Return a field value from a given row.
     * A null will be returned in case the columnName does not exist
     * An IndexOutOfBoundsException will be thrown in case the rowNum is senseless.
     * @param columnName The name of the column.
     * @param rowNum The index of the row to be retrieved.
     * @return a field value from a given row.
     */
    public Object getValue(String columnName, int rowNum) {
        Object obj = null;
        List row = getRow(rowNum);
        if (row != null) {
            int i = m_columns.indexOf(columnName);
            if(i != -1)
                obj = row.get(i);
        }
        return obj;
    }

    /** Returns a List of selected rows, where each row is a List of field values.
     * @return a List of selected rows, where each row is a List of field values.
     */
    public List getSelectedRows() {
        return m_selectedRows;
    }

    /** Sets the selected rows. This will first reset the selected status of all rows.
     * @param rowNums A List of row numbers to be marked as selected.
     */
    public void setSelectedRows(List rowNums) {
        m_selectedRows.clear();
        for(Iterator itr = rowNums.iterator(); itr.hasNext(); ) {
            int i = Integer.parseInt( (String) itr.next() );
            Object row = m_rows.get(i);
            if (row != null)
                m_selectedRows.add(row);
        }
        setModelChanged(true);
    }

    /** Change the state of the model internally */
    private void setModelChanged(boolean setState) {
        m_modelChanged = setState;
    }

    /** See if model has changed, in the process reset the changed flag.
     * @return true if the model was changed.
     */
    public boolean isModelChanged(){
        boolean modified = m_modelChanged;
        m_modelChanged = false;
        return modified;
    }

    /** An instance of this class will be created for each column added to the TableModel.
     */
    public class Column {
        private String m_name;
        private String m_dataType;

        /** Constructor.
         * @param name The name of the column.
         * @param dataType The data type of the column.
         */
        public Column(String name, String dataType) {
            m_name = name;
            m_dataType = dataType;
        }

        /** Getter for property name.
         * @return Value of property name.
         */
        public String getName() {
            return m_name;
        }

        /** Getter for property dataType.
         * @return Value of property dataType.
         */
        public String getDataType() {
            return m_dataType;
        }

        /** Returns diagnostic information.
         * @return diagnostic information.
         */
        public String toString() {
            return "Name=" + m_name + ", DataType=" + m_dataType;
        }
    }


}
