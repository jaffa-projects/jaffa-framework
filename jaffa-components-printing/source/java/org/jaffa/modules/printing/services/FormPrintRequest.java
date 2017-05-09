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

package org.jaffa.modules.printing.services;

import java.util.LinkedHashMap;
public class FormPrintRequest extends PrintRequest {

    /**
     * Holds value of property formName.
     */
    private String m_formName;

    /**
     * Holds value of property formAlternateName.
     */
    private String m_formAlternateName;

    /**
     * Holds value of property variation.
     */
    private String m_variation;

    /**
     * Holds value of property keys.
     */
    private LinkedHashMap m_keys;

    /**
     * Holds value of property additionalDataObject.
     */
    private Object additionalDataObject;
    
    /**
     * Getter for property formName.
     * @return Value of property formName.
     */
    public String getFormName() {
        return m_formName;
    }

    /**
     * Setter for property formName.
     * @param formName New value of property formName.
     */
    public void setFormName(String formName) {
        m_formName = formName;
    }

    /**
     * Getter for property formAlternateName.
     * @return Value of property formAlternateName.
     */
    public String getFormAlternateName() {
        return m_formAlternateName;
    }

    /**
     * Setter for property formAlternateName.
     * @param formAlternateName New value of property formAlternateName.
     */
    public void setFormAlternateName(String formAlternateName) {
        m_formAlternateName = formAlternateName;
    }


    /**
     * Getter for property variation.
     * @return Value of property variation.
     */
    public String getVariation() {
        return m_variation;
    }

    /**
     * Setter for property variation.
     * @param variation New value of property variation.
     */
    public void setVariation(String variation) {
        m_variation = variation;
    }

    /**
     * Getter for property keys.
     * @return Value of property keys.
     */
    public LinkedHashMap getKeys() {
        return m_keys;
    }

    /**
     * Setter for property keys.
     * @param keys New value of property keys.
     */
    public void setKeys(LinkedHashMap keys) {
        m_keys = keys;
    }
    
    /**
     * Setter for property additionalDataObject.
     * @param additionalDataObject New value of property additionalDataObject.
     */
    public void setAdditionalDataObject(java.lang.Object additionalDataObject) {
        this.additionalDataObject = additionalDataObject;
    }
    
    /**
     * Getter for property additionalDataObject.
     * @return Value of property additionalDataObject.
     */
    public java.lang.Object getAdditionalDataObject() {
        return additionalDataObject;
    }

}
