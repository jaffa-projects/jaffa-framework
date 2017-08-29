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
package org.jaffa.rules.testmodels;

import org.jaffa.datatypes.DateOnly;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.exceptions.UOWException;

public class Extension1 {
    private UOW uow = null;
    private String field1 = "Aaaa";
    private String field2 = "Bbbb";
    private String field3 = "Cccc";
    private Double field4 = null;
    private Long field5 = null;
    private DateOnly field6 = null;
    private int field7 = 0;
    private DateOnly field8 = null;
    private double field9 = 0;
    private String field10 = null;
    private Long field11 = null;

    public String getField1() {
        return field1;
    }

    public void setField1(String f) {
        field1 = f;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String f) {
        field2 = f;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String f) {
        field3 = f;
    }

    public Double getField4() {
        return field4;
    }

    public void setField4(Double f) {
        field4 = f;
    }

    public Long getField5() {
        return field5;
    }

    public void setField5(Long f) {
        field5 = f;
    }

    public DateOnly getField6() {
        return field6;
    }

    public void setField6(DateOnly f) {
        field6 = f;
    }

    public int getField7() {
        return field7;
    }

    public void setField7(int f) {
        field7 = f;
    }

    public DateOnly getField8() {
        return field8;
    }

    public void setField8(DateOnly f) {
        field8 = f;
    }

    public double getField9() {
        return field9;
    }

    public void setField9(double f) {
        field9 = f;
    }

    public String getField10() {
        return field10;
    }

    public void setField10(String f) {
        field10 = f;
    }

    public Long getField11() {
        return field11;
    }

    public void setField11(Long f) {
        field11 = f;
    }


    public void validate() {
    }

    /**
     * This method has been added to support rules that require UOW.
     */
    public UOW getUOW() throws UOWException {
        if (uow == null)
            uow = new UOW();
        return uow;
    }
}
