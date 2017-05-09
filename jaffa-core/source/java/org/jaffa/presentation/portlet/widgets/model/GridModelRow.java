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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;


/** A GridModel will contain instances of this class, for each row that gets added to it.
 * @author PaulE
 * @version 1.1 - Added support for the Map interface for use in JSP Expression Language
 *
 * This Map now models the key and value pairs of all the data in a row, it also
 * holds the row number and grid model it belongs to.
 */
public class GridModelRow extends HashMap implements IWidgetModel {

    private static Logger log = Logger.getLogger(GridModelRow.class);

    /** Maintains a reference to the GridModel object */
    private GridModel m_model = null;

    /** A unique id for each row withing a GridModel*/
    private int m_rowId = 0;


    /** Creates new GridModelRow. */
    GridModelRow(GridModel model, int rowId) {
        m_model = model;
        m_rowId = rowId;
    }

    /** Returns the id for this row.
     * @return the id for this row.
     */
    public int getRowId() {
        return m_rowId;
    }

    /** Returns the row element identified by the input element id.
     * @param id The id of the element.
     * @return the row element identified by the input element id.
     */
    public Object getElement(String id) {
        return this.get(id);
    }

    /** Add an element to the row.
     * @param id The id of the element.
     * @param obj The value of the element.
     */
    public void addElement(String id, Object obj) {
        this.put(id, obj);
    }

    /** @deprecated use keySet() */
    Collection getElementNames() {
        return this.keySet();
    }

    /** Returns a true if the row has an element having the input id.
     * @param id The id of the element.
     * @return a true if the row has an element having the input id.
     * @deprecated use containsKey()
     */
    public boolean hasElement(String id) {
        return this.containsKey(id);
    }

}
