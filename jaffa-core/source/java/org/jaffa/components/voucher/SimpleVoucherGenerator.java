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

package org.jaffa.components.voucher;

import java.rmi.dgc.VMID;
import java.util.Iterator;
import java.util.Map;
import org.jaffa.persistence.UOW;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.util.StringHelper;

/** This is a very simple implementation of the IVoucherGenerator interface.
 * This implementation will merely perform the following query:
 *  'SELECT max([fieldname]) FROM [domainclassname]'
 * It will then generate the voucher by
 *  1- Incrementing the number portion from the value retrieved
 *  2- Appending the number to the prefix with appropriate padding
 * <p>
 * If either the domainClassName or the fieldName are NULL, then it simply returns the value of a new VMID instance.
 * <p>
 * NOTE:
 * This implementation suffers from a very serious drawback - 2 THREADS CAN OBTAIN THE SAME VOUCHER
 * This class is intended for demo purposes only.
 * It SHOULD NOT be used in a production environment.
 *
 * @author  GautamJ
 */
public class SimpleVoucherGenerator extends AbstractVoucherGenerator {
    
    private static final String MAX_VALUE = "maxValue";
    
    /** The client will invoke this method to generate a voucher.
     * @throws FrameworkException if any framework error occurs.
     * @throws ApplicationExceptions if any application error occurs.
     * @return a unique voucher.
     */
    public String generate() throws FrameworkException, ApplicationExceptions {
        String voucher = null;
        if (getDomainClassName() == null || getFieldName() == null) {
            voucher = new VMID().toString();
        } else {
            String numericPortion = null;
            UOW uow = getUow();
            boolean localUow = (uow == null);
            try {
                if (localUow)
                    uow = new UOW();
                Criteria c = new Criteria();
                c.setTable(getDomainClassName());
                c.addFunction(Criteria.FUNCTION_MAX, StringHelper.getUpper1(getFieldName()), MAX_VALUE);
                Iterator itr = uow.query(c).iterator();
                if (itr.hasNext()) {
                    Map row = (Map) itr.next();
                    Object maxValue = row.get(MAX_VALUE);
                    if (maxValue != null) {
                        numericPortion = getPrefix() != null ? maxValue.toString().substring(getPrefix().length()) : maxValue.toString();
                        numericPortion = getLength() != null ? StringHelper.pad(Integer.parseInt(numericPortion) + 1, getLength()) : "" + (Integer.parseInt(numericPortion) + 1);
                    }
                }
                if (numericPortion == null)
                    numericPortion = getLength() != null ? StringHelper.pad(0, getLength()) : "0";
                voucher = getPrefix() != null ? getPrefix() + numericPortion : numericPortion;
            } finally {
                if (localUow && uow != null)
                    uow.rollback();
            }
        }
        return voucher;
    }
    
}
