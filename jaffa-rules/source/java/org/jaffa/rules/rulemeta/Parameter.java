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

package org.jaffa.rules.rulemeta;

import org.jaffa.rules.commons.MetaData;

import java.util.Arrays;

/**
 * The base class for the different types of MetaData.
 * <p/>
 * It has the following properties:
 * <ul>mandatory: Optional. Indicates if the parameter is mandatory. Default is false.
 * <ul>default: Optional. The default value for the parameter.
 * <ul>validValues: Optional. A comma-separated list of valid values for the parameter.
 * <ul>caseInsensitive: Optional. Indicates if the parameter is case-insensitive. Default is false.
 * <ul>Check {@link MetaData} for more properties
 */
public class Parameter extends MetaData {

    private Boolean m_mandatory;
    private String m_default;
    private String[] m_validValues;
    private Boolean m_caseInsensitive;

    /**
     * Default constructor.
     */
    public Parameter() {
    }

    /**
     * Constructs a parameter with a name only (not mandatory, null default, no valid values, not case-insensitive).
     *
     * @param name the name.
     */
    public Parameter(String name) {
        setName(name);
    }

    /**
     * Getter for property mandatory.
     *
     * @return Value of property mandatory.
     */
    public Boolean getMandatory() {
        return m_mandatory;
    }

    /**
     * Setter for property mandatory.
     *
     * @param mandatory Value of property mandatory.
     */
    public void setMandatory(Boolean mandatory) {
        m_mandatory = mandatory;
    }

    /**
     * Getter for property default.
     *
     * @return Value of property default.
     */
    public String getDefault() {
        return m_default;
    }

    /**
     * Setter for property default.
     *
     * @param def Value of property default.
     */
    public void setDefault(String def) {
        m_default = def;
    }

    /**
     * Getter for property validValues.
     *
     * @return Value of property validValues.
     */
    public String[] getValidValues() {
        return m_validValues;
    }

    /**
     * Setter for property validValues.
     *
     * @param validValues Value of property validValues.
     */
    public void setValidValues(String[] validValues) {
        if (validValues != null)
            Arrays.sort(validValues);
        m_validValues = validValues;
    }

    /**
     * Getter for property caseInsensitive.
     *
     * @return Value of property caseInsensitive.
     */
    public Boolean getCaseInsensitive() {
        return m_caseInsensitive;
    }

    /**
     * Setter for property caseInsensitive.
     *
     * @param caseInsensitive Value of property caseInsensitive.
     */
    public void setCaseInsensitive(Boolean caseInsensitive) {
        m_caseInsensitive = caseInsensitive;
    }

    /**
     * Returns debug info.
     *
     * @return debug info.
     */
    public String toString() {
        StringBuilder buf = new StringBuilder("<parameter");
        if (getName() != null)
            buf.append(" name='").append(getName()).append('\'');
        if (getMandatory() != null)
            buf.append(" mandatory='").append(getMandatory()).append('\'');
        if (getDefault() != null)
            buf.append(" default='").append(getDefault()).append('\'');
        if (getValidValues() != null && getValidValues().length > 0) {
            buf.append(" valid-values='");
            for (int i = 0; i < getValidValues().length; i++) {
                if (i > 0)
                    buf.append(',');
                buf.append(getValidValues()[i]);
            }
            buf.append('\'');
        }
        if (getCaseInsensitive() != null)
            buf.append(" case-insensitive='").append(getCaseInsensitive()).append('\'');
        buf.append(super.toString());
        return buf.toString();
    }

}
