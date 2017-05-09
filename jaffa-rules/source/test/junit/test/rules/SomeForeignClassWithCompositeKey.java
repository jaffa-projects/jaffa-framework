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

package test.rules;

import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Persistent;
import org.jaffa.persistence.UOW;

/** This is an example of a domain object which will be used for testing the foreign-key rule.
 * Instance of this class will be created without any database i/o.
 * NOTE: The ForeignKey rule requires the 'exists()' method on the domain class.
 */
public class SomeForeignClassWithCompositeKey extends Persistent {
    
    // This is the list of legal values for the keyfield of this class
    private static Object[][] c_legalValues = new Object[][] {
        {"KEY1", new Long(1)},
        {"KEY1", new Long(2)},
        {"KEY1", new Long(3)},
        {"KEY2", new Long(1)},
    };
    
    
    
    /**
     * Holds value of property someKeyField1.
     */
    private String m_someKeyField1;
    
    /**
     * Holds value of property someKeyField2.
     */
    private Long m_someKeyField2;
    
    /**
     * Holds value of property field1.
     */
    private String m_field1;
    
    /**
     * Holds value of property field2.
     */
    private String m_field2;
    
    /**
     * Getter for property someKeyField1.
     * @return Value of property someKeyField1.
     */
    public String getSomeKeyField1() {
        return m_someKeyField1;
    }
    
    /**
     * Setter for property someKeyField1.
     * @param someKeyField1 New value of property someKeyField1.
     */
    public void setSomeKeyField1(String someKeyField1) {
        m_someKeyField1 = someKeyField1;
    }
    
    /**
     * Getter for property someKeyField2.
     * @return Value of property someKeyField2.
     */
    public Long getSomeKeyField2() {
        return m_someKeyField2;
    }
    
    /**
     * Setter for property someKeyField2.
     * @param someKeyField2 New value of property someKeyField2.
     */
    public void setSomeKeyField2(Long someKeyField2) {
        m_someKeyField2 = someKeyField2;
    }
    
    /**
     * Getter for property field1.
     * @return Value of property field1.
     */
    public String getField1() {
        return m_field1;
    }
    
    /**
     * Setter for property field1.
     * @param field1 New value of property field1.
     */
    public void setField1(String field1) {
        m_field1 = field1;
    }
    
    /**
     * Getter for property field2.
     * @return Value of property field2.
     */
    public String getField2() {
        return m_field2;
    }
    
    /**
     * Setter for property field2.
     * @param field2 New value of property field2.
     */
    public void setField2(String field2) {
        m_field2 = field2;
    }
    
    
    
    
    
    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, String someKeyField1, Long someKeyField2) throws FrameworkException {
        return findByPK(uow, someKeyField1, someKeyField2) != null ? true : false;
    }
    
    /** Returns the domain object for the input Primary Key.
     * @return the domain object for the input Primary Key. A null is returned if the domain object is not found.
     * @throws FrameworkException Indicates some system error
     */
    public static SomeForeignClassWithCompositeKey findByPK(UOW uow, String someKeyField1, Long someKeyField2) throws FrameworkException {
        SomeForeignClassWithCompositeKey obj = null;
        for (int i = 0; i < c_legalValues.length; i++) {
            Object[] records = c_legalValues[i];
            if (records[0].equals(someKeyField1) && records[1].equals(someKeyField2)) {
                obj = new SomeForeignClassWithCompositeKey();
                obj.setSomeKeyField1(someKeyField1);
                obj.setSomeKeyField2(someKeyField2);
                obj.setField1("field1-" + i);
                obj.setField2("field2-" + i);
                obj.setUOW(uow);
            }
        }
        
        return obj;
    }
    
}
