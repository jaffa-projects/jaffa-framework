/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002-2008 JAFFA Development Group
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

import org.jaffa.util.StringHelper;
import org.jaffa.datatypes.Formatter;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * This class is a helper class to be used by velocity template scripts to
 * format data.  This class can be added to the VelocityContext and then
 * utilized within the velocity template script.
 * @author DennisL
 */
public class FormFormatHelper {

    /** Creates a new instance of FormFormatHelper */
    public FormFormatHelper() {
    }
    /** Logger reference */
    private final static Logger log = Logger.getLogger(FormFormatHelper.class);
    private HashMap numberMap = new HashMap();
    private BigDecimal m_bigDecimal = new BigDecimal("0.00");
    private String str_num;

    /**
     * Initialize the numeric value of the key.
     * @param key The key with which the numeric value is associated.
     * @param num The numeric value to associate with the key.
     */
    public void setNum(String key, int num) {
        if (key != null) {
            numberMap.put(key, new BigDecimal("" + num));
        }
    }

    /**
     * Initialize the numeric value of the key.
     * @param key The key with which the numeric value is associated.
     * @param num The numeric value to associate with the key.
     */
    public void setNum(String key, Double num) {
        if (key != null && num != null) {
            numberMap.put(key, new BigDecimal("" + num));
        }
    }

    /**
     * Get the formatted String representation of the numeric value associated
     * with the key.
     * @param key The key with which the numeric value is associated.
     * @return The formatted String representation of the numeric value associated
     * with the key.  Applies the standard formatting.
     */
    public String getNum(String key) {
        return (getNum(key, null));
    }

    /**
     * Get the formatted String representation of the numeric value associated
     * with the key.
     * @param key The key with which the numeric value is associated.
     * @param format The format to apply to the returned numeric value.
     * @return The formatted String representation of the numeric value associated
     * with the key.  Applies the format identified by the input param.
     */
    public String getNum(String key, String format) {
        str_num = "";
        if (key != null) {
            m_bigDecimal = (BigDecimal) numberMap.get(key);
            if (m_bigDecimal != null) {
                if (format != null) {
                    // Apply format
                    str_num = Formatter.format(m_bigDecimal.doubleValue(), format);
                } else {
                    // Use standard formatting
                    str_num = Formatter.format(m_bigDecimal.doubleValue());
                }

            }
        }
        return (str_num);
    }

    /**
     * Adds the input numeric value to the the current value associated with
     * the key.  If there is no current value, then the input numeric value
     * is set as the initial key value.
     * @param key The key with which the numeric value is associated.
     * @param num The numeric value to add to the current value associated
     * with the key.
     */
    public void addNum(String key, Double num) {
        if (key != null && num != null) {
            m_bigDecimal = (BigDecimal) numberMap.get(key);
            if (m_bigDecimal != null) {
                numberMap.put(key, m_bigDecimal.add(new BigDecimal("" + num)));
            } else {
                numberMap.put(key, new BigDecimal("" + num));
            }
        }
    }

    /**
     * Returns a substring of the input string starting at the beginning Index for the specified length.
     * The output string is always of the specified length and if necessary right padded with spaces.
     * @param str The input string to be acted upon.
     * @param begIdx The index of the input string where the substring should begin.
     * @param length The length of the output string.
     * @return a substring of the input string, right padded with spaces.
     */
    public String substr(String str, int begIdx, int length) {
        str = StringHelper.rpad(str, length);
        return str.substring(begIdx, length);
    }

    /**
     * Returns a substring of the input String starting at Index 0 for the specified length.
     * The output String is always of the specified length and if necessary right padded with spaces.
     * @param str The input String to be acted upon.
     * @param length The length of the output String.
     * @return a substring of the input String, right padded with spaces.
     */
    public String block(String str, int length) {
        str = StringHelper.rpad(str, length);
        return str.substring(0, length);
    }

    /**
     * First, the input int value is converted to a String.  Then a substring of
     * the String starting at Index 0 for the specified length, is returned.
     * If the specified length is greater than the length of the String, the
     * String is right-padded with spaces to the specified length.
     * @param iStr The input int value to be acted upon.
     * @param length The length of the output String.
     * @return a substring of the String value of the input int value,
     * right padded with spaces.
     */
    public String block(int iStr, int length) {
        String str = String.valueOf(iStr);
        str = StringHelper.rpad(str, length);
        return str.substring(0, length);
    }

    /**
     * Returns a substring of the input String starting at Index 0 for the specified length.
     * The output String is always of the specified length,
     * right-justified, left padded with spaces.
     * @param str The input String to be acted upon.
     * @param length The length of the output String.
     * @return a substring of the input String, right-justified, left padded with spaces.
     */
    public String blockr(String str, int length) {
        str = StringHelper.lpad(str, length);
        return str.substring(0, length);
    }

    /**
     * Returns a String of spaces of the specified length.
     * @param length The length of the output String.
     * @return a String of spaces of the specified length.
     */
    public String pad(int length) {
        return StringHelper.rpad(" ", length);
    }

    /**
     * Returns a newline character.
     * @return a newline character.
     */
    public String newLine() {
        return "\n";
    }

    /**
     * Returns the number of newline characters specified by the quantity input parameter.
     * @return newline character(s).
     */
    public String newLine(int quantity) {
        StringBuffer newlines = new StringBuffer();
        for (int i = 0; i < quantity; i++) {
            newlines.append("\n");
        }
        return newlines.toString();
    }

    /**
     * Returns the length of the input String array.
     *@return the length of the input String array.
     */
    public int length(String[] array) {
        if (array != null) {
            return array.length;
        } else {
            return 0;
        }
    }

    /**
     * Returns the size of the input List.
     *@return the size of the input List.
     */
    public int length(List list) {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    /**
     * Returns the input string split into a string array.  The string splits occur at
     * all newline characters provided that the resulting strings do not exceed the lineLength.
     * Otherwise string splits will occur at the last space character positioned inside the lineLength.
     * When no newline or space characters occur within the string of the given lineLength then the
     * string is split at the lineLength position.
     * Each resulting string array element will have a length no longer than the input lineLength.
     * When the input lineLength is less than 1, the returned string array will have one element
     * consisting of the entire input string.
     *@return the input string split into a string array.
     */
    public String[] split(String str1, int lineLength) {
        List<String> strList = new ArrayList<String>();
        String[] lines;
        String str2 = null;
        int idx = -1;
        if (lineLength < 1) {
            lines = new String[1];
            lines[0] = str1;
            return (lines);
        }

        while (str1 != null && str1.length() > 0) {
            idx = str1.indexOf('\n');
            if (idx > -1 && idx <= lineLength) {
                strList.add(str1.substring(0, idx));
                idx += 1;
            } else {
                if (str1.length() > lineLength) {
                    str2 = str1.substring(0, lineLength + 1);
                    idx = str2.lastIndexOf(' ');
                    if (idx < 0) {
                        idx = lineLength;
                        strList.add(str1.substring(0, idx));
                    } else if (idx == lineLength) {
                        //Keep idx as is
                        strList.add(str1.substring(0, idx));
                        //Reset idx to skip the space
                        idx += 1;
                    } else {
                        idx += 1;
                        strList.add(str1.substring(0, idx));
                    }
                } else {
                    idx = str1.length();
                    strList.add(str1.substring(0, idx));
                }
            }
            if (str1.length() > idx) {
                str1 = (str1.substring(idx));
            } else {
                str1 = null;
            }
        }

        lines = new String[strList.size()];
        Iterator itr = strList.iterator();
        for (int i = 0; i < strList.size(); i++) {
            lines[i] = (String) itr.next();
        }

        return (lines);
    }
}
