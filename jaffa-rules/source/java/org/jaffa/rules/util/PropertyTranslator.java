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

package org.jaffa.rules.util;

import org.jaffa.datatypes.Formatter;

/** This class is used to format a property using the following algorithm.
 *      layout="Pre {0} Post", format("xyz") will return "Pre xyz Post".
 *      layout="Pre {0} Post {0}", format("xyz") will return "Pre xyz Post xyz".
 * <p>
 * The back-slash '\' will be the escape character.
 * Hence, if the layout contains a '\', then it should be replaced by '\\'
 * If the layout contains a '{' or a '}', then it should be replaced by '\{' or '\}'
 * <p>
 * Use the following rule definition for utilising this class
 *      <layout value='dfg fghfhg {0} hjghj' formatterClass='org.jaffa.util.PropertyTranslator'/>
 * Then invoke the format() method of the PropertyRuleIntrospector to format a property as per its layout rule.
 *
 */
public class PropertyTranslator extends AbstractFormatter {
    
    /** Returns the input property formatted as per the given layout.
     * @param property the property to be formatted.
     * @return the input property formatted as per the given layout.
     */
    public String format(Object property) {
        // Use the default formatter to format the input object
        String input = Formatter.format(property);
        
        // Now translate the input as per the layout
        StringBuilder buf = new StringBuilder();
        if (m_layout != null) {
            for (int i = 0; i < m_layout.length(); i++) {
                char c = m_layout.charAt(i);
                if (c == '\\') {
                    if (i+1 < m_layout.length()) {
                        // Ignore the current character and add the next one
                        ++i;
                        c = m_layout.charAt(i);
                    }
                } else if (c == '{') {
                    if (i+2 < m_layout.length() && m_layout.charAt(i+1) == '0' && m_layout.charAt(i+2) == '}') {
                        // Replace the current and the next 2 characters with the input
                        i = i + 2;
                        buf.append(input);
                        continue;
                    }
                }
                buf.append(c);
            }
        }
        return buf.toString();
    }
    
}
