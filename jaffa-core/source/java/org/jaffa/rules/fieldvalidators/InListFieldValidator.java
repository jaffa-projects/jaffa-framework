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

package org.jaffa.rules.fieldvalidators;

import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.FrameworkException;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.exceptions.PatternMismatchException;

/** This Field Validator ensures that the field value is specified in the List. The List will have multiple values separated by the separator character. The default separator character is a comma ','.
 */
public class InListFieldValidator extends AbstractFieldValidator {
    
    private static final Logger log = Logger.getLogger(InListFieldValidator.class);
    private static final String DEFAULT_SEPARATOR = ",";
    private String m_list;
    private String m_separator;
    
    /** Getter for property list.
     * @return Value of property list.
     */
    public String getList() {
        return m_list;
    }
    
    /** Setter for property list.
     * @param list New value of property list.
     */
    public void setList(String list) {
        m_list = list;
    }
    
    /** Getter for property separator.
     * @return Value of property separator.
     */
    public String getSeparator() {
        return m_separator;
    }
    
    /** Setter for property separator.
     * @param separator New value of property separator.
     */
    public void setSeparator(String separator) {
        m_separator = separator;
    }
    
    /** The RulesEngine will invoke this method to perform the field validation.
     * @throws ValidationException if any validation rule fails.
     * @throws FrameworkException if any framework error occurs.
     */
    public void validate() throws ValidationException, FrameworkException {
        Object value = getValue();
        if (value != null) {
            String valueString = value.toString();
            String separator;
            if (m_separator == null || m_separator.trim().length() == 0)
                separator = DEFAULT_SEPARATOR;
            else
                separator = m_separator;
            
            for (StringTokenizer stkzr = new StringTokenizer(m_list, separator); stkzr.hasMoreTokens(); ) {
                String listElement = stkzr.nextToken();
                if (listElement.equals(valueString))
                    return;
            }
            
            // No match found.. throw an exception
            String str = "The value '" + value + "' not found in the List '" + m_list + "'";
            log.error(str);
            throw new PatternMismatchException(getLabelToken(), new Object[] {m_list});
        }
    }
    
}
