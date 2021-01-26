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

package org.jaffa.datatypes;

import java.util.*;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.exceptions.*;
import java.text.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.session.LocaleContext;
import org.jaffa.util.LocaleHelper;

/** This class has convenience methods to parse a string into the required objects.
 */
public class Parser {

    private static final Logger log = Logger.getLogger(Parser.class);
    private static final Collection c_booleanTrue = new ArrayList();
    private static final Collection c_booleanFalse = new ArrayList();
    static {
        c_booleanTrue.add("true");
        c_booleanTrue.add("t");
        c_booleanTrue.add("yes");
        c_booleanTrue.add("y");
        c_booleanTrue.add("1");

        c_booleanFalse.add("false");
        c_booleanFalse.add("f");
        c_booleanFalse.add("no");
        c_booleanFalse.add("n");
        c_booleanFalse.add("0");
    }

    /** Returns a Boolean object corresponding to the input String.
     * It uses the default layout specified in the corrsponding meta class.
     * If the layout is null, then a TRUE is returned if the input is true, t, yes, y, 1.
     * If the layout is null, then a FALSE is returned if the input is false, f, no, n, 0.
     * If the layout is null, and the input is none of the above, then it uses standard JAVA boolean parsing.
     * If the layout is passed, then it is assumed to have the true/false strings separated by a comma.
     * @param input the input.
     * @return the parsed object.
     */
    public static Boolean parseBoolean(String input) {
        return parseBoolean(input, null);
    }

    /** Returns a Boolean object corresponding to the input String.
     * If the layout is null, then a TRUE is returned if the input is true, t, yes, y, 1.
     * If the layout is null, then a FALSE is returned if the input is false, f, no, n, 0.
     * If the layout is null, and the input is none of the above, then it uses standard JAVA boolean parsing.
     * If the layout is passed, then it is assumed to have the true/false strings separated by a comma.
     * @param input the input.
     * @param layout the layout.
     * @return the parsed object.
     */
    public static Boolean parseBoolean(String input,  String layout) {
        Boolean out = null;
        if (input != null && input.length() > 0) {
            if (layout == null)
                layout = BooleanFieldMetaData.DEFAULT_LAYOUT;
            layout = LocaleHelper.determineLayout(layout);
            if (layout == null) {
                input = input.toLowerCase();
                if (c_booleanTrue.contains(input))
                    out = Boolean.TRUE;
                else if (c_booleanFalse.contains(input))
                    out = Boolean.FALSE;
                else
                    out = Boolean.valueOf(input);
            } else {
                input = input.toUpperCase();
                layout = layout.toUpperCase();

                // Assume that the true/false values are separated by a comma
                String trueString = null;
                int i = layout.indexOf(',');
                trueString = layout.substring(0, i);

                if (input.equals(trueString))
                    out = new Boolean(true);
                else
                    out = new Boolean(false);
            }
        }
        return out;
    }

    /** Returns a Long object corresponding to the input String.
     * It uses the default layout specified in the corrsponding meta class.
     * @param input the input.
     * @throws FormatIntegerException if parsing fails.
     * @return the parsed object.
     */
    public static Long parseInteger(String input) throws FormatIntegerException {
        return parseInteger(input, null);
    }

    /** Returns a Long object corresponding to the input String.
     * @param input the input.
     * @param layout the layout.
     * @throws FormatIntegerException if parsing fails.
     * @return the parsed object.
     */
    public static Long parseInteger(String input, String layout) throws FormatIntegerException {
        try {
            Long out = null;
            if (input != null && input.length() > 0) {
                if (layout == null)
                    layout = IntegerFieldMetaData.getIntegerFormat();
                layout = LocaleHelper.determineLayout(layout);

                NumberFormat fmt;
                if (LocaleContext.getLocale() != null)
                    fmt = NumberFormat.getIntegerInstance(LocaleContext.getLocale());
                else
                    fmt = NumberFormat.getIntegerInstance();
                if (layout != null && fmt instanceof DecimalFormat)
                    ((DecimalFormat) fmt).applyPattern(layout);
                out = new Long(fmt.parse(input).longValue());
            }
            return out;
        } catch (ParseException e) {
            throw new FormatIntegerException(null, null, e);
        }
    }

    /** Returns a Double object corresponding to the input String.
     * It uses the default layout specified in the corrsponding meta class.
     * @param input the input.
     * @throws FormatDecimalException if parsing fails.
     * @return the parsed object.
     */
    public static Double parseDecimal(String input) throws FormatDecimalException {
        return parseDecimal(input, null);
    }

    /** Returns a Double object corresponding to the input String.
     * @param input the input.
     * @param layout the layout.
     * @throws FormatDecimalException if parsing fails.
     * @return the parsed object.
     */
    public static Double parseDecimal(String input, String layout) throws FormatDecimalException {
        try {
            Double out = null;
            if (input != null && input.length() > 0) {
                if (layout == null)
                    layout = DecimalFieldMetaData.getDecimalFormat();
                layout = LocaleHelper.determineLayout(layout);

                NumberFormat fmt;
                if (LocaleContext.getLocale() != null)
                    fmt = NumberFormat.getNumberInstance(LocaleContext.getLocale());
                else
                    fmt = NumberFormat.getNumberInstance();
                if (layout != null && layout.trim().length() > 0 && fmt instanceof DecimalFormat)
                    ((DecimalFormat) fmt).applyPattern(layout);

                //If the input string is having decimal point as ".", then it will format the string based on the user locale,
                //This is required when the locale is Norwegian and European language which use the comma as decimal separator.
                //If we don't use the correct format before parsing, then the input will get parsed wrongly.
                if(input.contains(".")){
                    input = Formatter.format(Double.parseDouble(input));
                }
                out = new Double(fmt.parse(input).doubleValue());
            }
            return out;
        } catch (ParseException e) {
            // ParseException could occur when a decimal value is passed without a currency-sign and when using a currency format
            // Try parsing with the default layout
            if (layout != null && !layout.equals(LocaleHelper.determineLayout(DecimalFieldMetaData.getDecimalFormat()))) {
                if (log.isDebugEnabled())
                    log.debug("The input " + input + " could not be parsed into a Double using the layout " + layout + ". Will try to convert into a Double using the default layout.", e);
                return parseDecimal(input, null);
            } else
                throw new FormatDecimalException(null, null, e);
        }
    }

    /** Returns a DateOnly object corresponding to the input String.
     * It uses the default layout specified in the corrsponding meta class.
     * @param input the input.
     * @throws FormatDateOnlyException if parsing fails.
     * @return the parsed object.
     */
    public static DateOnly parseDateOnly(String input) throws FormatDateOnlyException {
        return parseDateOnly(input, null);
    }

    /** Returns a DateOnly object corresponding to the input String.
     * @param input the input.
     * @param layout the layout.
     * @throws FormatDateOnlyException if parsing fails.
     * @return the parsed object.
     */
    public static DateOnly parseDateOnly(String input, String layout)
    throws FormatDateOnlyException {
        try {
            return DateOnly.parse(input, layout);
        } catch (ParseException e) {
            throw new FormatDateOnlyException(null, new Object[] {(layout == null ? DateOnlyFieldMetaData.getDateOnlyParse()[0] : layout)}, e);
        }
    }

    /** Returns a DateTime object corresponding to the input String.
     * It uses the default layout specified in the corrsponding meta class.
     * @param input the input.
     * @throws FormatDateTimeException if parsing fails.
     * @return the parsed object.
     */
    public static DateTime parseDateTime(String input) throws FormatDateTimeException {
        return parseDateTime(input, null);
    }

    /** Returns a DateTime object corresponding to the input String.
     * @param input the input.
     * @param layout the layout.
     * @throws FormatDateTimeException if parsing fails.
     * @return the parsed object.
     */
    public static DateTime parseDateTime(String input, String layout)
    throws FormatDateTimeException {
        DateTime out = null;
        try {
            out = DateTime.parse(input, layout);
            return out;
        } catch (ParseException e) {
            try {
                if (log.isDebugEnabled())
                    log.debug("The input " + input + " could not be parsed into a DateTime using the layout " + layout + ". Will try to convert into a DateOnly using the default layout.", e);
                DateOnly date = parseDateOnly(input);
                out = DateOnly.toDateTime(date);
                return out;
            } catch (FormatDateOnlyException e1) {
                throw new FormatDateTimeException(null, new Object[] {(layout == null ? DateTimeFieldMetaData.getDateTimeParse()[0] : layout)}, e1);
            }
        }
    }

    /** Returns a Currency object corresponding to the input String.
     * It uses the default layout specified in the corrsponding meta class.
     * @param input the input.
     * @throws FormatCurrencyException if parsing fails.
     * @return the parsed object.
     */
    public static Currency parseCurrency(String input) throws FormatCurrencyException {
        return parseCurrency(input, null);
    }

    /** Returns a Currency object corresponding to the input String.
     * @param input the input.
     * @param layout the layout.
     * @throws FormatCurrencyException if parsing fails.
     * @return the parsed object.
     */
    public static Currency parseCurrency(String input, String layout) throws FormatCurrencyException {
        try {
            Currency out = null;
            if (input != null && input.length() > 0) {
                if (layout == null)
                    layout = CurrencyFieldMetaData.getCurrencyFormat();
                layout = LocaleHelper.determineLayout(layout);
                
                NumberFormat fmt;
                if (LocaleContext.getLocale() != null)
                    fmt = NumberFormat.getCurrencyInstance(LocaleContext.getLocale());
                else
                    fmt = NumberFormat.getCurrencyInstance();
                if (layout != null && fmt instanceof DecimalFormat)
                    ((DecimalFormat) fmt).applyPattern(layout);
                out = new Currency(fmt.parse(input).doubleValue());
            }
            return out;
        } catch (ParseException e) {
            // ParseException could occur when a decimal value is passed without a currency-sign and when using a currency format
            // Try parsing with the default decimal layout
            if (layout != null && !layout.equals(LocaleHelper.determineLayout(DecimalFieldMetaData.getDecimalFormat()))) {
                if (log.isDebugEnabled())
                    log.debug("The input " + input + " could not be parsed into a Currency using the layout " + layout + ". Will try to convert into a Currency using the default decimal layout.", e);
                return parseCurrency(input, DecimalFieldMetaData.getDecimalFormat());
            } else
                throw new FormatCurrencyException(null, null, e);
        }
    }

    /** Just returns the String as is.
     * @param input the input.
     * @return the String as is.
     */
    public static String parseString(String input) {
        // Note: there is no layout for a String... just return
        return input;
    }

    /** Converts the input into an array of bytes
     * @param input the input.
     * @return the input as an array of bytes.
     */
    public static byte[] parseRaw(String input) {
        // Note: there is no layout for a Raw... just return
        if (input == null) {
            return null;
        } else {
            return input.getBytes();
        }
    }

}
