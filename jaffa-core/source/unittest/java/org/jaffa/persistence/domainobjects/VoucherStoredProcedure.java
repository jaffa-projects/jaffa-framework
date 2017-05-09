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


public class VoucherStoredProcedure extends Persistent implements IStoredProcedure {

    /** Holds value of property prefix. */
    private java.lang.String prefix;

    /** Holds value of property length. */
    private java.lang.Long length;

    /** Holds value of property voucher. */
    private java.lang.String voucher;


    /** Getter for property prefix.
     * @return Value of property prefix.
     */
    public java.lang.String getPrefix() {
        return prefix;
    }

    /** Setter for property prefix.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param prefix New value of property prefix.
     */
    public void setPrefix(java.lang.String prefix) {
        this.prefix = prefix;
    }

    /** Use this method to update the property prefix. Validation will be performed on the input value.
     * @param prefix New value of property prefix.
     * @throws ValidationException if an invalid value is passed
     */
    public void updatePrefix(java.lang.String prefix)
    throws ValidationException {
        validatePrefix(prefix);
        setPrefix(prefix);
    }

    /** Use this method to validate a value for the property prefix.
     * @param prefix Value to be validated for the property prefix.
     * @throws ValidationException if an invalid value is passed
     */
    public void validatePrefix(java.lang.String prefix)
    throws ValidationException {
        FieldValidator.validate(prefix, (StringFieldMetaData) VoucherStoredProcedureMeta.META_PREFIX, true);
    }

    /** Getter for property length.
     * @return Value of property length.
     */
    public java.lang.Long getLength() {
        return length;
    }

    /** Setter for property length.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param length New value of property length.
     */
    public void setLength(java.lang.Long length) {
        this.length = length;
    }

    /** Use this method to update the property length. Validation will be performed on the input value.
     * @param length New value of property length.
     * @throws ValidationException if an invalid value is passed
     */
    public void updateLength(java.lang.Long length)
    throws ValidationException {
        validateLength(length);
        setLength(length);
    }

    /** Use this method to validate a value for the property length.
     * @param length Value to be validated for the property length.
     * @throws ValidationException if an invalid value is passed
     */
    public void validateLength(java.lang.Long length)
    throws ValidationException {
        FieldValidator.validate(length, (IntegerFieldMetaData) VoucherStoredProcedureMeta.META_LENGTH, true);
    }

    /** Getter for property voucher.
     * @return Value of property voucher.
     */
    public java.lang.String getVoucher() {
        return voucher;
    }

    /** Setter for property voucher.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param voucher New value of property voucher.
     */
    public void setVoucher(java.lang.String voucher) {
        this.voucher = voucher;
    }

    /** Use this method to update the property voucher. Validation will be performed on the input value.
     * @param voucher New value of property voucher.
     * @throws ValidationException if an invalid value is passed
     */
    public void updateVoucher(java.lang.String voucher)
    throws ValidationException {
        validateVoucher(voucher);
        setVoucher(voucher);
    }

    /** Use this method to validate a value for the property voucher.
     * @param voucher Value to be validated for the property voucher.
     * @throws ValidationException if an invalid value is passed
     */
    public void validateVoucher(java.lang.String voucher)
    throws ValidationException {
        FieldValidator.validate(voucher, (StringFieldMetaData) VoucherStoredProcedureMeta.META_VOUCHER, true);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<VoucherStoredProcedure>");
        buf.append("<prefix>"); if (prefix != null) buf.append(prefix); buf.append("</prefix>");
        buf.append("<length>"); if (length != null) buf.append(length); buf.append("</length>");
        buf.append("<voucher>"); if (voucher != null) buf.append(voucher); buf.append("</voucher>");
        buf.append("</VoucherStoredProcedure>");
        return buf.toString();
    }


    public String[] getParameters() {
        return new String[] {"Prefix", "Length", "Voucher"};
    }

    public String prepareCall() {
        String engineType = null;
        try {
            engineType = ConnectionHelper.getEngineType();
        } catch (Exception e) {
            throw new RuntimeException("Unable to determine the database engine type", e);
        }
        if ("oracle".equals(engineType))
            return "{call ZZ_JUT_VOUCHER.GetVoucher(?,?,?)}";
        else if ("mssqlserver".equals(engineType))
            return "{call ZZ_JUT_VOUCHER_GetVoucher(?,?,?)}";
        else
            throw new UnsupportedOperationException("Stored Procedure has not been written to support " + engineType);
    }

    public int[] getParamDirections() {
        return new int[] {IN, IN, OUT};
    }

    public String[] getParamSqlTypes() {
        return new String[] {"VARCHAR", "BIGINT", "VARCHAR"};
    }

}
