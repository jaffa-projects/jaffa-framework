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

import java.util.Calendar;
import java.util.Date;
import org.jaffa.metadata.*;
import java.text.*;
import org.jaffa.util.LocaleHelper;
import org.jaffa.presentation.portlet.session.LocaleContext;

/** This class has convenience methods for formatting objects based on the datatypes and layouts.
 */
public class Formatter {

    /** Formats the input.
     * This used the default layout specified in the corresponding meta class.
     * @param input the input.
     * @return a String representation of the input.
     */
    public static String format(Boolean input) {
        return format(input, BooleanFieldMetaData.DEFAULT_LAYOUT);
    }

    /** Formats the input based on the input layout.
     * @param input the input.
     * @param layout the layout to be used for formatting the input.
     * @return a String representation of the input.
     */
    public static String format(Boolean input,  String layout) {
        String out = null;
        if (input != null) {
            if (layout == null) {
                out = input.toString();
            } else {
                layout = LocaleHelper.determineLayout(layout);
                // Assume that the true/false values are separated by a comma
                int i = layout.indexOf(',');
                if (input.booleanValue()) {
                    out = layout.substring(0, i);
                } else {
                    if (++i < layout.length())
                        out = layout.substring(i);
                }
            }
        }
        return out;
    }

    /** Formats the input.
     * This used the default layout specified in the corresponding meta class.
     * @param input the input.
     * @return a String representation of the input.
     */
    public static String format(Long input) {
        return format(input, null);
    }

    /** Formats the input based on the input layout.
     * @param input the input.
     * @param layout the layout to be used for formatting the input.
     * @return a String representation of the input.
     */
    public static String format(Long input, String layout) {
        String out = null;
        if (input != null) {
            if (layout == null)
                layout = IntegerFieldMetaData.getIntegerFormat();
            layout = LocaleHelper.determineLayout(layout);

            NumberFormat fmt;
            if (LocaleContext.getLocale() != null)
                fmt = NumberFormat.getIntegerInstance(LocaleContext.getLocale());
            else
                fmt = NumberFormat.getIntegerInstance();
            if (layout != null && layout.length() > 0 && fmt instanceof DecimalFormat)
                ((DecimalFormat) fmt).applyPattern(layout);
            out = fmt.format(input);
        }
        return out;
    }


    /** Formats the input.
     * This used the default layout specified in the corresponding meta class.
     * @param input the input.
     * @return a String representation of the input.
     */
    public static String format(Double input) {
        return format(input, null);
    }

    /** Formats the input based on the input layout.
     * @param input the input.
     * @param layout the layout to be used for formatting the input.
     * @return a String representation of the input.
     */
    public static String format(Double input, String layout) {
        String out = null;
        if (input != null) {
            if (layout == null)
                layout = DecimalFieldMetaData.getDecimalFormat();
            layout = LocaleHelper.determineLayout(layout);

            NumberFormat fmt;
            if (LocaleContext.getLocale() != null)
                fmt = NumberFormat.getNumberInstance(LocaleContext.getLocale());
            else
                fmt = NumberFormat.getNumberInstance();
            if (layout != null && layout.length() > 0 && fmt instanceof DecimalFormat)
                ((DecimalFormat) fmt).applyPattern(layout);
            out = fmt.format(input);
        }
        return out;
    }

    /** Formats the input.
     * This used the default layout specified in the corresponding meta class.
     * @param input the input.
     * @return a String representation of the input.
     */
    public static String format(DateOnly input) {
        return format(input, null);
    }

    /** Formats the input based on the input layout.
     * @param input the input.
     * @param layout the layout to be used for formatting the input.
     * @return a String representation of the input.
     */
    public static String format(DateOnly input, String layout) {
        String out = null;
        if (input != null) {
            if (layout == null)
                layout = DateOnlyFieldMetaData.getDateOnlyFormat();
            layout = LocaleHelper.determineLayout(layout);

            DateFormat fmt = DateBase.obtainDateFormat(layout);
            out = fmt.format(input.getUtilDate());
        }
        return out;
    }

    /** Formats the input.
     * This used the default layout specified in the corresponding meta class.
     * @param input the input.
     * @return a String representation of the input.
     */
    public static String format(DateTime input) {
        return format(input, null);
    }

    /** Formats the input based on the input layout.
     * @param input the input.
     * @param layout the layout to be used for formatting the input.
     * @return a String representation of the input.
     */
    public static String format(DateTime input, String layout) {
        String out = null;
        if (input != null) {
            if (layout == null)
                layout = DateTimeFieldMetaData.getDateTimeFormat();
            layout = LocaleHelper.determineLayout(layout);

            DateFormat fmt = DateBase.obtainDateFormat(layout);
            out = fmt.format(input.getUtilDate());
        }
        return out;
    }

    /** Formats the input.
     * This used the default layout specified in the corresponding meta class.
     * @param input the input.
     * @return a String representation of the input.
     */
    public static String format(Currency input) {
        return format(input, CurrencyFieldMetaData.getCurrencyFormat());
    }

    /** Formats the input based on the input layout.
     * @param input the input.
     * @param layout the layout to be used for formatting the input.
     * @return a String representation of the input.
     */
    public static String format(Currency input, String layout) {
        String out = null;
        if (input != null) {
            if (layout == null)
                layout = CurrencyFieldMetaData.getCurrencyFormat();
            layout = LocaleHelper.determineLayout(layout);

            NumberFormat fmt;
            if (LocaleContext.getLocale() != null)
                fmt = NumberFormat.getCurrencyInstance(LocaleContext.getLocale());
            else
                fmt = NumberFormat.getCurrencyInstance();
            fmt.setCurrency(java.util.Currency.getInstance(input.getCurrencyType()));
            if (layout != null && layout.length() > 0 && fmt instanceof DecimalFormat)
                ((DecimalFormat) fmt).applyPattern(layout);
            out = fmt.format(input.getValue());
        }
        return out;
    }


    /** Formats the input.
     * @param input the input.
     * @return a String representation of the input.
     */
    public static String format(String input) {
        // Note: there is no layout for a String... just return
        return input;
    }

    /** Formats the input.
     * @param input the input.
     * @return a String representation of the input.
     */
    public static String format(byte[] input) {
        // Note: there is no layout for a Raw... just return
        if (input == null) {
            return null;
        } else {
            return new String(input);
        }
    }

    /** Formats the input.
     * @param input the input.
     * @return a String representation of the input.
     */
    public static String format(Object input) {
        return format(input, null);
    }

    /** Formats the input.
     * @param input the input.
     * @param layout the layout to be used for formatting the input.
     * @return a String representation of the input.
     */
    public static String format(Object input, String layout) {
        String out = null;
        if (input != null) {
            if (input instanceof String)
                out =  format((String) input);
            else if (input instanceof Boolean)
                out = format((Boolean) input, layout);
            else if (input instanceof Long)
                out =  format((Long) input, layout);
            else if (input instanceof Double)
                out =  format((Double) input, layout);
            else if (input instanceof DateOnly)
                out =  format((DateOnly) input, layout);
            else if (input instanceof DateTime)
                out =  format((DateTime) input, layout);
            else if (input instanceof Currency)
                out =  format((Currency) input, layout);
            else if (input instanceof byte[])
                out =  format((byte[]) input);
            else if (input instanceof Date)
                out =  format(new DateTime((Date) input), layout);
            else if (input instanceof Calendar)
                out =  format(new DateTime((Calendar) input), layout);
            else if (input instanceof Number) {
                if (input instanceof Float)
                    out =  format(new Double(((Float) input).doubleValue()), layout);
                else
                    out =  format(new Long(((Number) input).longValue()), layout);
            } else
                out =  input.toString();
        }
        return out;
    }
}
