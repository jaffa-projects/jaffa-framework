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

package org.jaffa.rules;

import java.util.Map;
import java.util.Properties;

/** IPropertyRuleIntrospector is used to lookup property-level rules.
 *
 * @author  GautamJ
 */
public interface IPropertyRuleIntrospector {

    /** Returns a Class object that identifies the declared type for the property.
     * @return a Class object that identifies the declared type for the property.
     */
    public Class getPropertyType();

    /** Returns a true if the property is marked as Hidden.
     * @return a true if the property is marked as Hidden.
     */
    public boolean isHidden();

    /** Returns a true if the property is marked as ReadOnly.
     * @return a true if the property is marked as ReadOnly.
     */
    public boolean isReadOnly();

    /** Returns the annotation for the property.
     * @return the annotation for the property.
     */
    public String getAnnotation();

    /** Returns the audit info for the property.
     * @return the audit info for the property.
     */
    public Properties getAuditInfo();

    /** Returns the flex info for the property.
     * @return the flex info for the property.
     */
    public Properties getFlexInfo();

    /** Returns the foreignkey info for the property.
     * @return the foreignkey info for the property.
     */
    public Properties getForeignKeyInfo();

    /** Returns the hyperlink info for the property.
     * @return the hyperlink info for the property.
     */
    public Properties getHyperlinkInfo();

    /** Returns the info for the property.
     * @return the info for the property.
     */
    public Properties getPropertyInfo();

    /** Returns the minLength for the property.
     * @return the minLength for the property.
     */
    public Integer getMinLength();

    /** Returns the maxLength for the property.
     * @return the maxLength for the property.
     */
    public Integer getMaxLength();

    /** Returns the maxFracLength for the property.
     * @return the maxFracLength for the property.
     */
    public Integer getMaxFracLength();

    /** Returns the minValue for the property.
     * @return the minValue for the property.
     */
    public Object getMinValue();

    /** Returns the maxValue for the property.
     * @return the maxValue for the property.
     */
    public Object getMaxValue();

    /** Returns the caseType for the property.
     * @return the caseType for the property.
     */
    public String getCaseType();

    /** Returns the label for the property.
     * @return the label for the property.
     */
    public String getLabel();

    /** Returns the layout for the property.
     * @return the layout for the property.
     */
    public String getLayout();

    /** Looks up the layout rules defined for the property and formats the input object using the associated formatterClass.
     * @param property the property instance to be formatted.
     * @return the formatted property.
     */
    public String format(Object property);

    /** Returns the pattern(s) for the property.
     * @return the pattern(s) for the property.
     */
    public String[] getPattern();

    /** Returns a true if the property is marked as mandatory.
     * @return a true if the property is marked as mandatory.
     */
    public boolean isMandatory();

    /** Returns the client rule for the property.
     * @return the client rule for the property.
     */
    public String getClientRule();

    /** Returns the comment style for the property.
     * @return the comment style for the property.
     */
    public String getCommentStyle();

    /** Returns the in-list values for the property.
     * Each entry in the Map will be a value/label pair.
     * @return the in-list values for the property.
     */
    public Map<String, String> getInListValues();

    /** Returns the in-list values for the property.
     * Each entry in the Map will be a value/label pair.
     * @return the service values for the property.
     */
    public Map<String, Object> getServiceInfo();

	/** Molds the parameters for the first applicable rule into a Properties object. 
    * @return the Properties object
    */
    public Properties findInfo(String className, String propertyName, Object obj, String ruleName);
}
