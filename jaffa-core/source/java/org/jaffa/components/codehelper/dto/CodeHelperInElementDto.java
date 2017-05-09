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

package org.jaffa.components.codehelper.dto;

import java.util.*;

/** The input to the ICodeHelper, will consist of a List of instances of this class.
 * The ICodeHelper will query the 'domainClassName' and use reflection to determine the fields to return.
 * Additional criteria can be specified using instances of CriteriaElementDto.
 * The properties domainClassName, codeFieldName and the descriptionFieldName are mandatory, for the ICodeHelper to work correctly.
 *
 * @author  GautamJ
 */
public class CodeHelperInElementDto {
    
    /** Holds value of property code. */
    private String code;
    
    /** Holds value of property domainClassName. */
    private String domainClassName;
    
    /** Holds value of property codeFieldName. */
    private String codeFieldName;
    
    /** Holds value of property descriptionFieldName. */
    private String descriptionFieldName;
    
    /** Holds value of property criteriaFields. */
    private List criteriaFields;
    
    /** Holds value of property appendCodeAndDescription. */
    private boolean appendCodeAndDescription;
    
    /** Holds value of property appendBeginMarker. */
    private String appendBeginMarker;
    
    /** Holds value of property appendEndMarker. */
    private String appendEndMarker;
    
    /** Creates new CodeHelperInElementDto */
    public CodeHelperInElementDto() {
    }
    
    /** Getter for property code.
     * @return Value of property code.
     */
    public String getCode() {
        return code;
    }
    
    /** Setter for property code.
     * @param code New value of property code.
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /** Getter for property domainClassName.
     * @return Value of property domainClassName.
     */
    public String getDomainClassName() {
        return domainClassName;
    }
    
    /** Setter for property domainClassName.
     * @param domainClassName New value of property domainClassName.
     */
    public void setDomainClassName(String domainClassName) {
        this.domainClassName = domainClassName;
    }
    
    /** Getter for property codeFieldName.
     * @return Value of property codeFieldName.
     */
    public String getCodeFieldName() {
        return codeFieldName;
    }
    
    /** Setter for property codeFieldName.
     * @param codeFieldName New value of property codeFieldName.
     */
    public void setCodeFieldName(String codeFieldName) {
        this.codeFieldName = codeFieldName;
    }
    
    /** Getter for property descriptionFieldName.
     * @return Value of property descriptionFieldName.
     */
    public String getDescriptionFieldName() {
        return descriptionFieldName;
    }
    
    /** Setter for property descriptionFieldName.
     * @param descriptionFieldName New value of property descriptionFieldName.
     */
    public void setDescriptionFieldName(String descriptionFieldName) {
        this.descriptionFieldName = descriptionFieldName;
    }
    
    
    
    /** Add a criteriaField to the list
     * @param criteriaField A criteriaField
     */
    public void addCriteriaField(CriteriaElementDto criteriaField) {
        if (criteriaFields == null)
            criteriaFields = new ArrayList();
        criteriaFields.add(criteriaField);
    }
    
    /** Add a criteriaField at the specified position in the list
     * @param criteriaField A criteriaField
     * @param index The position in the list
     */
    public void setCriteriaField(CriteriaElementDto criteriaField, int index) {
        if (criteriaFields == null)
            criteriaFields = new ArrayList();
        
        //-- check bounds for index
        if (index < 0 || index > criteriaFields.size())
            throw new IndexOutOfBoundsException();
        
        criteriaFields.set(index, criteriaField);
    }
    
    /** Recreate the internal list with the input array of criteriaField
     * @param criteriaFields An array of criteriaField
     */
    public void setCriteriaFields(CriteriaElementDto[] criteriaFields) {
        this.criteriaFields = Arrays.asList(criteriaFields);
    }
    
    /** Clear the list of criteriaField
     */
    public void clearCriteriaFields() {
        if (criteriaFields != null)
            criteriaFields.clear();
    }
    
    /** Remove a criteriaField from the list
     * @param criteriaField The criteriaField to be removed
     * @return true if this list contained the specified element
     */
    public boolean removeCriteriaField(CriteriaElementDto criteriaField) {
        if (criteriaFields != null)
            return criteriaFields.remove(criteriaField);
        else
            return false;
    }
    
    /** Return a criteriaField at the specified position in the list
     * @param index The position in the list
     * @return The criteriaField
     */
    public CriteriaElementDto getCriteriaField(int index) {
        //-- check bounds for index
        if (criteriaFields == null || index < 0 || index > criteriaFields.size())
            throw new IndexOutOfBoundsException();
        
        return (CriteriaElementDto) criteriaFields.get(index);
    }
    
    /** Returns an array of criteriaField
     * @return An array of criteriaField
     */
    public CriteriaElementDto[] getCriteriaFields() {
        if (criteriaFields != null)
            return (CriteriaElementDto[]) criteriaFields.toArray(new CriteriaElementDto[0]);
        else
            return null;
    }
    
    /** Returns the number of criteriaField in the list
     * @return The number of criteriaField in the list
     */
    public int getCriteriaFieldCount() {
        if (criteriaFields != null)
            return criteriaFields.size();
        else
            return 0;
    }
    
    /** Getter for property appendCodeAndDescription.
     * @return Value of property appendCodeAndDescription.
     */
    public boolean isAppendCodeAndDescription() {
        return this.appendCodeAndDescription;
    }
    
    /** Setter for property appendCodeAndDescription.
     * @param appendCodeAndDescription New value of property appendCodeAndDescription.
     */
    public void setAppendCodeAndDescription(boolean appendCodeAndDescription) {
        this.appendCodeAndDescription = appendCodeAndDescription;
    }
    
    /** Getter for property appendBeginMarker.
     * @return Value of property appendBeginMarker.
     */
    public String getAppendBeginMarker() {
        return this.appendBeginMarker;
    }
    
    /** Setter for property appendBeginMarker.
     * @param appendBeginMarker New value of property appendBeginMarker.
     */
    public void setAppendBeginMarker(String appendBeginMarker) {
        this.appendBeginMarker = appendBeginMarker;
    }
    
    /** Getter for property appendEndMarker.
     * @return Value of property appendEndMarker.
     */
    public String getAppendEndMarker() {
        return this.appendEndMarker;
    }
    
    /** Setter for property appendEndMarker.
     * @param appendEndMarker New value of property appendEndMarker.
     */
    public void setAppendEndMarker(String appendEndMarker) {
        this.appendEndMarker = appendEndMarker;
    }
    
    
    
    
    /** Returns diagnostic information.
     * @return diagnostic information.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<CodeHelperInElementDto>");
        buf.append("<code>"); if (code != null) buf.append(code); buf.append("</code>");
        buf.append("<domainClassName>"); if (domainClassName != null) buf.append(domainClassName); buf.append("</domainClassName>");
        buf.append("<codeFieldName>"); if (codeFieldName != null) buf.append(codeFieldName); buf.append("</codeFieldName>");
        buf.append("<descriptionFieldName>"); if (descriptionFieldName != null) buf.append(descriptionFieldName); buf.append("</descriptionFieldName>");
        buf.append("<appendCodeAndDescription>"); buf.append(appendCodeAndDescription); buf.append("</appendCodeAndDescription>");
        buf.append("<appendBeginMarker>"); if (appendBeginMarker != null) buf.append(appendBeginMarker); buf.append("</appendBeginMarker>");
        buf.append("<appendEndMarker>"); if (appendEndMarker != null) buf.append(appendEndMarker); buf.append("</appendEndMarker>");
        
        buf.append("<criteriaFields>");
        if (criteriaFields != null) {
            for (Iterator i = criteriaFields.iterator(); i.hasNext(); )
                buf.append(i.next());
        }
        buf.append("</criteriaFields>");
        
        buf.append("</CodeHelperInElementDto>");
        return buf.toString();
    }
}
