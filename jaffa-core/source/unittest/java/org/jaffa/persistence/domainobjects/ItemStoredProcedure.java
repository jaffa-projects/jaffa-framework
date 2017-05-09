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

package org.jaffa.persistence.domainobjects;

import helpers.ConnectionHelper;
import org.jaffa.persistence.Persistent;
import org.jaffa.persistence.engines.jdbcengine.IStoredProcedure;
import java.lang.reflect.Method;
import org.jaffa.datatypes.ValidationException;
import org.jaffa.datatypes.FieldValidator;
import org.jaffa.metadata.*;


public class ItemStoredProcedure extends Persistent implements IStoredProcedure {

    /** Holds value of property itemId. */
    private java.lang.String itemId;

    /** Holds value of property items. */
    private Item[] items;


    /** Getter for property itemId.
     * @return Value of property itemId.
     */
    public java.lang.String getItemId() {
        return itemId;
    }

    /** Setter for property itemId.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param itemId New value of property itemId.
     */
    public void setItemId(java.lang.String itemId) {
        this.itemId = itemId;
    }

    /** Use this method to update the property itemId. Validation will be performed on the input value.
     * @param itemId New value of property itemId.
     * @throws ValidationException if an invalid value is passed
     */
    public void updateItemId(java.lang.String itemId)
    throws ValidationException {
        validateItemId(itemId);
        setItemId(itemId);
    }

    /** Use this method to validate a value for the property itemId.
     * @param itemId Value to be validated for the property itemId.
     * @throws ValidationException if an invalid value is passed
     */
    public void validateItemId(java.lang.String itemId)
    throws ValidationException {
        FieldValidator.validate(itemId, (StringFieldMetaData) ItemStoredProcedureMeta.META_ITEM_ID, true);
    }

    /** Getter for property items.
     * @return Value of property items.
     */
    public Item[] getItems() {
        return items;
    }

    /** Setter for property items.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param items New value of property items.
     */
    public void setItems(Item[] items) {
        this.items = items;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<ItemStoredProcedure>");
        buf.append("<itemId>"); if (itemId != null) buf.append(itemId); buf.append("</itemId>");
        buf.append("<items>"); if (items != null) buf.append(items); buf.append("</items>");
        buf.append("</ItemStoredProcedure>");
        return buf.toString();
    }


    public String[] getParameters() {
        return new String[] {"ItemId", "Items"};
    }

    public String prepareCall() {
        String engineType = null;
        try {
            engineType = ConnectionHelper.getEngineType();
        } catch (Exception e) {
            throw new RuntimeException("Unable to determine the database engine type", e);
        }
        if ("oracle".equals(engineType))
            return "{call ZZ_JUT_ITEM_SP.GetItems(?,?)}";
        else if ("mssqlserver".equals(engineType))
            return "{call ZZ_JUT_ITEM_SP_GetItems(?,?)}";
        else
            throw new UnsupportedOperationException("Stored Procedure has not been written to support " + engineType);
    }

    public int[] getParamDirections() {
        return new int[] {IN, OUT};
    }

    public String[] getParamSqlTypes() {
        return new String[] {"VARCHAR", IStoredProcedure.CURSOR};
    }

}
