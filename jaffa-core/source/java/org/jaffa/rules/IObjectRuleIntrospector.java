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

import java.util.List;
import java.util.Map;
import java.util.Properties;

/** IObjectRuleIntrospector is used to lookup class-level rules.
 *
 * @author  GautamJ
 */
public interface IObjectRuleIntrospector {
    
    /** Returns the annotation for the Class.
     * @return the annotation for the Class.
     */
    public String getAnnotation();

    /** Returns the audit info for the Class.
     * @return the audit info for the Class.
     */
    public Properties getAuditInfo();

    /** Returns the audit info for all the properties within a Class.
     * @return the audit info for all the properties within a Class.
     */
    public Map<String, Properties> getAuditInfoForProperties();
    
    /** Returns the domain info for the Class.
     * @return the domain info for the Class.
     */
    public Properties getDomainInfo();
    
    /** Returns the list of rulemetadata properties for the Class.
     * @return the rulemetadata properties for the Class..
     * @param  ruleName.
     */
    public List<Properties> getMetaDataByRule(String ruleName);
    
    
    /** Returns the property-info for all the properties within a Class.
     * @return the property-info for all the properties within a Class.
     */
    public Map<String, Properties> getInfoForProperties();

    /** Returns the flex info for the Class.
     * @return the flex info for the Class.
     */
    public Properties getFlexInfo();

    /** Returns all declared flex info for the Class.
     * This method will disregard conditions, if any.
     * @return all declared flex info for the Class.
     */
    public Properties[] getDeclaredFlexInfo();

    /** Returns the flex info for all the properties within a Class.
     * @return the flex info for all the properties within a Class.
     */
    public Map<String, Properties> getFlexInfoForProperties();

    /** Returns the label for the Class.
     * @return the label for the Class.
     */
    public String getLabel();
    
    /** Returns the primary-key for the Class.
     * @return the primary-key for the Class.
     */
    public String[] getPrimaryKey();
    
}
