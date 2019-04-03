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

/*
 * StringHelper.java
 *
 * Created on September 5, 2001, 10:06 AM
 */

package org.jaffa.util;

import java.beans.Introspector;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import org.apache.log4j.Logger;
import java.lang.reflect.Field;
import java.util.Map;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.util.BeanHelper;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.DateTime;
import org.jaffa.metadata.PropertyRuleIntrospectorUsingFieldMetaData;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.IRulesEngine;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.security.SecurityManager;
import org.owasp.encoder.Encode;

/** Utility Class for Common String Manipulation routines.
 *
 * @author  PaulE
 * @version 1.0
 */
public class StringHelper {

    private static final Logger log = Logger.getLogger(StringHelper.class);
    private static final int DESCRIPTION_LIMIT = 25;
    private static final String DESCRIPTION_TRUNCATE_INDICATOR = "...";
    private static final String DESCRIPTION_BEGIN_MARKER = " (";
    private static final String DESCRIPTION_END_MARKER = ")";
    private static final String LINE_DELIMITER = "<BR>";

    /**
     * Left Pads a number with zeros to take up at least 2 characters
     * @param number the number to pad
     * @return string representation of number padded to 2 characters
     */
    public static String pad(int number) {
        return pad(number, 2);
    }

    /**
     * Left Pads a number with zeros to take up a specified length
     * NOTE: The input will be truncated if it exceeds the specified length.
     * @param number the number to pad
     * @param length length of resulting string
     * @return string representation of number padded to specified length
     */
    public static String pad(int number, int length) {
        return pad(number, length, '0');
    }

    /**
     * Left Pads a number to take up a specified length
     * NOTE: The input will be truncated if it exceeds the specified length.
     * @param number the number to pad
     * @param length length of resulting string
     * @param padChar Character to be used for Padding
     * @return string representation of number padded to specified length
     */
    public static String pad(int number, int length, char padChar) {
        return lpad(String.valueOf(number), length, padChar);
    }

    /**
     * Left Pads the input String with spaces to take up a specified length
     * NOTE: The input will be truncated if it exceeds the specified length.
     * @param input the String to pad
     * @param length length of resulting string
     * @return string representation of the input string padded to specified length.
     */
    public static String lpad(String input, int length) {
        return lpad(input, length, ' ');
    }

    /**
     * Left Pads the input String to take up a specified length
     * NOTE: The input will be truncated if it exceeds the specified length.
     * @param input the String to pad
     * @param length length of resulting string
     * @param padChar Character to be used for Padding
     * @return string representation of the input string padded to specified length.
     */
    public static String lpad(String input, int length, char padChar) {
        return pad(input, length, padChar, false);
    }

    /**
     * Right Pads the input String with spaces to take up a specified length
     * NOTE: The input will be truncated if it exceeds the specified length.
     * @param input the String to pad
     * @param length length of resulting string
     * @return string representation of the input string padded to specified length.
     */
    public static String rpad(String input, int length) {
        return rpad(input, length, ' ');
    }

    /**
     * Right Pads the input String to take up a specified length
     * NOTE: The input will be truncated if it exceeds the specified length.
     * @param input the String to pad
     * @param length length of resulting string
     * @param padChar Character to be used for Padding
     * @return string representation of the input string padded to specified length.
     */
    public static String rpad(String input, int length, char padChar) {
        return pad(input, length, padChar, true);
    }

    /**
     * Pads a String to take up a specified length
     * NOTE: The input will be truncated if it exceeds the specified length.
     * @param input the String to pad
     * @param length length of resulting string
     * @param padChar Character to be used for Padding
     * @param rpad If true, then characters will be padded to the end of the input string. Else they'll be added before the input string.
     * @return string representation of the input padded to specified length
     */
    public static String pad(String input, int length, char padChar, boolean rpad) {
        // Create a buffer with an initial capacity set
        StringBuffer buf = new StringBuffer(length);

        // Determine the number of characters to be padded
        int padCount = length - (input != null ? input.length() : 0);

        // Start with the input, if we are rpad-ing
        if (rpad && input != null)
            buf.append(input);

        // Append with the pad characters
        for (int i = 0; i < padCount; i++)
            buf.append(padChar);

        // End with the input, if we are lpad-ing
        if (!rpad && input != null)
            buf.append(input);

        return buf.length() > length ? buf.substring(0, length) : buf.toString();
    }

    /**
     * Tokenize the Input String with the default tokenizer (whitespace)
     * @param s String to tokenize
     * @return string[] representation tokenized String
     */
    public static String[] parseString(String s) {
        StringTokenizer tokens = new StringTokenizer( s );
        String[] SOut = new String[tokens.countTokens()];
        int i=0;
        while (tokens.hasMoreTokens() ) {
            SOut[i++] = tokens.nextToken();
        }
        return SOut;
    }

    /**
     * Tokenize the Input String with the given delim
     * @param s String to tokenize
     * @param delim Deliminator to use
     * @return String[] representation tokenized String
     */
    public static String[] parseString(String s, String delim) {
        if(s==null)
            // Null strings were throwing an NPE!
            return new String[0];
        StringTokenizer tokens = new StringTokenizer( s, delim);
        String[] SOut = new String[tokens.countTokens()];
        int i=0;
        while (tokens.hasMoreTokens() ) {
            SOut[i++] = tokens.nextToken();
        }
        return SOut;
    }

    /** Pad each new line of the supplied string with the
     * specified number of spaces
     * @param text String to pad
     * @param indent number of spaces to indent by
     * @return the indented string */
    public static String linePad(String text, int indent) {
        return linePad(text, indent, " ", false);
    }

    /** Pad each new line of the supplied string with the
     * specified number of 'indent Strings'
     * @param text String to pad
     * @param indent number of spaces to indent by
     * @param indentWith String to use for indenting
     * @return the indented string */
    public static String linePad(String text, int indent, String indentWith) {
        return linePad(text, indent, indentWith, false);
    }

    /** Pad each new line of the supplied string with the specified number of the indent string,
     * but allows the suppression of the first line from indentation
     * @param text String to pad
     * @param indent number of spaces to indent by
     * @param indentWith String to use for indenting
     * @param supressFirst true, if the first line should be skipped when indenting
     * @return the indented string
     */
    public static String linePad(String text, int indent, String indentWith, boolean supressFirst) {
        String pad = replicate(indentWith, indent);
        String prefix = "";
        if(!supressFirst)
            prefix = pad;
        if(text != null) {
            text = prefix + replace(text, "\n", "\n" + pad);
        }
        return text;
    }

    /** Produce a string by replicating the specific string the specified
     * number of times
     * @param text String to replicate
     * @param count number of times to replicate string
     * @return final replicated string
     */
    public static String replicate(String text, int count) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i<count; i++)
            sb.append(text);
        return sb.toString();
    }


    /** Converts a comma delimmitered string into an array of strings
     * @param commaList Source string
     * @return List of strings
     */
    public static ArrayList convertToList(String commaList) {

        ArrayList outputList = new ArrayList();

        StringTokenizer str = new StringTokenizer(commaList, ",");
        while (str.hasMoreTokens()) {
            String value = str.nextToken();
            outputList.add(value);
        }

        return outputList;
    }

    /** Convert a string that has XML (and HTML) entities in it, into a regular string
     *
     * It will convert the following...
     *     &amp;   to &
     *     &lt;    to <
     *     &gt;    to >
     *     &apos;  to '
     *     &quot;  to "
	 *     &nbsp;  to  
     * @param s String to convert
     * @return  converted string
     */
    public static String convertFromHTML(String s) {
        if (s == null)
            return "";
        else
            return replace(replace(replace(replace(replace(replace(s, "&nbsp;", " "), "&amp;","&"),"&lt;","<"),"&gt;",">"),"&apos;","'"),"&quot;","\"");
    }

    /** Convert a regular string into an XML (and HTML) based string
     *
     * It will convert the following...
     *     & to &amp;
     *     < to &lt;
     *     > to &gt;
     *     " to &quot;
     * @param s String to convert
     * @return  converted string
     */
    public static String convertToHTML(String s) {
        if (s == null)
            return "";
        else
            return replace(replace(replace(replace(s, "&","&amp;"),"<","&lt;"),">","&gt;"),"\"","&quot;");
    }

    /**
     * Convenience method to ensure that a scripting attack is not mounted
     * by passing malicious code in HTTP Request parameters.
     * @param s String to convert.
     * @return  converted string.
     */
    public static String escapeJavascript(String s) {
        return s!=null ? Encode.forJavaScript(s) : "";
    }

    /**
     * Convenience method to ensure that a scripting attack is not mounted by escaping the input.
     * This is intended for use where the target UI is html.
     *
     * @param s String to convert.
     * @return  converted string.
     */
    public static String escapeHtml(String s) {
        return Encode.forHtml(s);
    }

    /**
     * This method will add HTML line breaks in the input String at the specified limits.
     * This is useful for wrapping long lines of text.
     * @param s String to modify
     * @param lineLimit The interval at which the line breaks will be added.
     * @return the input string with HTML line breaks at specified limits.
     */
    public static String addHTMLLineBreak(String s, int lineLimit) {
        if (s != null && s.length() > lineLimit) {
            int i = lineLimit;
            while (s.length() > i) {
                s = s.substring(0, i) + LINE_DELIMITER + s.substring(i);
                i += lineLimit + LINE_DELIMITER.length();
            }
        }
        return s;
    }

    /** Basic find and replace on a string, it will replace ALL occurences of the string
     * @param source original string
     * @param find string to search for
     * @param replace string to replace found strings with
     * @return resultant string from the replace
     */
    public static String replace(String source, String find, String replace) {
        if (source==null) return null;
        StringBuffer sb = null;
        int pos;
        do {
            if( (pos=source.indexOf(find)) < 0)
                break; // No more matches
            if(sb==null)
                sb=new StringBuffer();
            if(pos>0)
                sb.append(source.substring(0,pos));
            sb.append(replace);
            source = source.substring(pos + find.length());
        } while (source.length() > 0);
        if(sb==null)
            return source;
        else {
            sb.append(source);
            return sb.toString();
        }
    }


    /**
     * A convenience method to translate the input String's 1st character to LowerCase,
     * the rest of the string is left the same
     *
     * @param input the string to process
     * @return the modified string
     */
    public static String getLower1(String input) {
        if (input != null && input.length() > 0) {
            return Character.toLowerCase( input.charAt(0) ) +
            ( input.length() == 1 ? "" : input.substring(1) );
        } else
            return input;
    }

    /**
     * Convenience method to compare two strings, ignoring case for the first character
     * @param first the first string
     * @param second the second string
     * @return true if the two strings are equal, ignoring case on the first character
     */
    public static boolean equalsIgnoreCaseFirstChar(String first, String second) {
        if (first == null && second == null) {
            return true;
        }
        if (first == null || second == null || first.length() != second.length()) {
            return false;
        }
        if (first.length() == 0) {
            return true;
        }
        if (first.length() == 1) {
            return first.equalsIgnoreCase(second);
        }
        return (Character.toLowerCase(first.charAt(0)) == Character.toLowerCase(second.charAt(0)))
                && first.regionMatches(1, second, 1, first.length() - 1);
    }

    /**
     * A convenience method to translate the input String's 1st character to UpperCase,
     * the rest of the string is left the same
     *
     * @param input the string to process
     * @return the modified string
     */
    public static String getUpper1(String input) {
        if (input != null && input.length() > 0) {
            return Character.toUpperCase( input.charAt(0) ) +
            ( input.length() == 1 ? "" : input.substring(1) );
        } else
            return input;
    }

    /**
     * A convenience method to translate the input String to LowerCase
     *
     * @param input the string to process
     * @return the modified string
     */
    public static String getLower(String input) {
        if (input != null && input.length() > 0) {
            return input.toLowerCase();
        } else
            return input;
    }

    /**
     * A convenience method to translate the input String to UpperCase
     *
     * @param input the string to process
     * @return the modified string
     */
    public static String getUpper(String input) {
        if (input != null && input.length() > 0) {
            return input.toUpperCase();
        } else
            return input;
    }

    /**
     * A convenience method to translate the input String to all UpperCase with underscores separating the words
     * For eg: "abcDef" would be translated to "ABC_DEF"
     *
     * @param input the string to process
     * @return the modified string
     */
    public static String getStatic(String input) {
        if (input != null && input.length() > 0) {
            return replace(input, true, '_');
        } else
            return input;
    }

    /**
     * A convenience method to translate the input String to words separated by spaces
     * For eg: "abcDef" would be translated to "abc Def"
     *
     * @param input the string to process
     * @return the modified string
     */
    public static String getSpace(String input) {
        if (input != null && input.length() > 0) {
            return replace(input, false, ' ');
        } else
            return input;
    }

    /**
     * A convenience method to translate the input String to a Java styled property name
     * For eg: "ABC_DEF" would be translated to "AbcDef".
     * "Abc_def" would be translated to "AbcDef".
     * "Abc def" would be translated to "AbcDef".
     *
     * @param input the string to process
     * @return the modified string
     */
    public static String getJavaStyle(String input) {
        if (input != null && input.length() > 0) {
            input = input.trim();
            StringBuffer buf = new StringBuffer(input.length());
            boolean toUpper = true; // This will make the 1st character uppercase
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (ch == '_' || ch == ' ') {
                    // ignore this character and make sure that the next valid character is converted to uppercase
                    toUpper = true;
                } else if (toUpper) {
                    buf.append(Character.toUpperCase(ch));
                    toUpper = false;
                } else {
                    buf.append(Character.toLowerCase(ch));
                }
            }
            return buf.toString();
        } else
            return input;
    }

    /**
     * A convenience method to translate the input String to a JavaBean styled property name
     * For eg: "AbcDef" would be translated to "abcDef".
     * "abcDef" would be translated to "abcDef".
     * "ABcDef" would be translated to "ABcDef".
     * This uses the java.beans.Introspector.decapitalize() method to perform the conversion
     *
     * @param input the string to process
     * @return the modified string
     */
    public static String getJavaBeanStyle(String input) {
        // change the first character to uppercase and then invoke the decapitalize method
        if (input != null && input.length() > 0) {
            return Introspector.decapitalize(getUpper1(input));
        } else
            return input;
    }

    /** This returns the contents of a Reader in a StringBuffer.
     * @param reader the input source.
     * @throws IOException if any error occurs in reading the contents of the input.
     * @return a StringBuffer with the contents of the source. A null is returned if the input is null.
     */
    public static StringBuffer getStringBuffer(Reader reader) throws IOException {
        StringBuffer buf = null;
        if (reader != null) {
            buf = new StringBuffer();
            BufferedReader bReader = new BufferedReader(reader);
            String str = bReader.readLine();
            if (str != null) {
                buf.append(str);
                while ((str = bReader.readLine()) != null) {
                    buf.append('\n');
                    buf.append(str);
                }
            }
        }
        return buf;
    }

    /** This returns the contents of a Reader as a String.
     * @param reader the input source.
     * @throws IOException if any error occurs in reading the contents of the input.
     * @return a String with the contents of the source. A null is returned if the input is null.
     */
    public static String getString(Reader reader) throws IOException {
        StringBuffer buf = getStringBuffer(reader);
        if (buf == null)
            return null;
        else
            return buf.toString();
    }

    /** 
     * A custom split utility, which is marginally better than the regex-based split method of the String class.
     */
    public static String[] split(String input, char separator) {
        if (input == null)
            return null;
        final int length = input.length();
        
        //Compute the number of elements in the output. There will be at least be one.
        int elementCount = 1;
        for (int i = 0; i < length; i++) {
            if (input.charAt(i) == separator)
                ++elementCount;
        }

        //Generate the output
        String[] output = new String[elementCount];
        if (elementCount == 1)
            output[0] = input;
        else {
            int counter = -1;
            int beginIndex = 0, endIndex = input.indexOf(separator);
            while (endIndex > -1 || beginIndex < length) {
                output[++counter] = endIndex > -1 ? input.substring(beginIndex, endIndex) : input.substring(beginIndex);
                beginIndex = endIndex > -1 ? (endIndex + 1) : length;
                endIndex = input.indexOf(separator, beginIndex);
            }
            if (output[elementCount - 1] == null)
                output[elementCount - 1] = "";
        }
        return output;
    }


    /** This will parse the input String, separating the uppercase characters with the input separator.
     * If the 'toUpper' is true, then all characters will be converted to uppercase.
     */
    private static String replace(String input, boolean toUpper, char separator) {
        if (input != null && input.length() > 0) {
            StringBuffer out = new StringBuffer();
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if ( Character.isUpperCase(ch) ) {
                    //// do not append the separator for the 1st character !!!
                    //if (i > 0)
                    //    out.append(separator);

                    // append the separator only if the preceding character is lowercase, or if the next character is lowercase
                    if ((i - 1 >= 0 && Character.isLowerCase(input.charAt(i - 1)))
                    || (i > 0 && i + 1 < input.length() && Character.isLowerCase(input.charAt(i + 1))))
                        out.append(separator);

                    out.append(ch);
                } else {
                    if (toUpper)
                        out.append( Character.toUpperCase(ch) );
                    else
                        out.append(ch);
                }
            }
            return out.toString();
        } else
            return input;
    }


    /** This method is invoked in cases where 'description' field is appended to a 'code' field.
     * This method formats the input dexcriptionField based on the passed layout.
     * The layout can be passed directly or can be determined from the parameters domainClassWithPackage and domainField, through the appropriate FieldMetaData object.
     * It then truncates the formatted String to the input limit. If the String is truncated, then the truncateIndicator will be appended.
     * Finally the String will be packaged between the beginMarker and endMarker.
     * If the toHtml flag is true, then the result will made HTML safe.
     * This method will use the default values beginMarker=' (', endMarker=')', limit=25, truncateIndicator='...'
     * @param field The description of the field.
     * @param layout The layout, if any to be used for formatting
     * @param domainClassWithPackage The domainClass to determine the FieldMetaData object, to get a handle on the layout.
     * @param domainField The domainField to determine the FieldMetaData object, to get a handle on the layout.
     * @param toHtml if true, then the output will be converted to HTML.
     * @return the formatted string packed between the markers. An empty String will be returned, in case the input field is null.
     */
    public static String formatDescription(Object field, String layout,
    String domainClassWithPackage, String domainField, boolean toHtml) {
        return formatDescription(field, layout, domainClassWithPackage, domainField, toHtml,
        DESCRIPTION_BEGIN_MARKER, DESCRIPTION_END_MARKER);
    }

    /** This method is invoked in cases where 'description' field is appended to a 'code' field.
     * This method formats the input dexcriptionField based on the passed layout.
     * The layout can be passed directly or can be determined from the parameters domainClassWithPackage and domainField, through the appropriate FieldMetaData object.
     * It then truncates the formatted String to the input limit. If the String is truncated, then the truncateIndicator will be appended.
     * Finally the String will be packaged between the beginMarker and endMarker.
     * If the toHtml flag is true, then the result will made HTML safe.
     * This method will use the default values limit=25, truncateIndicator='...'
     * @param field The description of the field.
     * @param layout The layout, if any to be used for formatting
     * @param domainClassWithPackage The domainClass to determine the FieldMetaData object, to get a handle on the layout.
     * @param domainField The domainField to determine the FieldMetaData object, to get a handle on the layout.
     * @param toHtml if true, then the output will be converted to HTML.
     * @param beginMarker The marker at the start of the output. Default is ' ('
     * @param endMarker The marker at the end of the output. Default is ')'
     * @return the formatted string packed between the markers. An empty String will be returned, in case the input field is null.
     */
    public static String formatDescription(Object field, String layout,
    String domainClassWithPackage, String domainField, boolean toHtml,
    String beginMarker, String endMarker) {
        return formatDescription(field, layout, domainClassWithPackage, domainField, toHtml,
        beginMarker, endMarker, DESCRIPTION_LIMIT, DESCRIPTION_TRUNCATE_INDICATOR);
    }

    /** This method is invoked in cases where 'description' field is appended to a 'code' field.
     * This method formats the input dexcriptionField based on the passed layout.
     * The layout can be passed directly or can be determined from the parameters domainClassWithPackage and domainField, through the appropriate FieldMetaData object.
     * It then truncates the formatted String to the input limit. If the String is truncated, then the truncateIndicator will be appended.
     * No truncation will be performed if the limit <= 0
     * Finally the String will be packaged between the beginMarker and endMarker.
     * If the toHtml flag is true, then the result will made HTML safe.
     * @param field The description of the field.
     * @param layout The layout, if any to be used for formatting
     * @param domainClassWithPackage The domainClass to determine the FieldMetaData object, to get a handle on the layout.
     * @param domainField The domainField to determine the FieldMetaData object, to get a handle on the layout.
     * @param toHtml if true, then the output will be converted to HTML.
     * @param beginMarker The marker at the start of the output. Default is ' ('
     * @param endMarker The marker at the end of the output. Default is ')'
     * @param limit The limit for the formatted decription. Default is 25.
     * @param truncateIndicator The string to append tot he formatted description, if exceeds the limit and is truncated.
     * @return the formatted string packed between the markers. An empty String will be returned, in case the input field is null.
     */
    public static String formatDescription(Object field, String layout,
    String domainClassWithPackage, String domainField, boolean toHtml,
    String beginMarker, String endMarker, int limit, String truncateIndicator) {
        String out = null;
        if (field == null)
            out = "";
        else {
            StringBuffer buf = new StringBuffer();

            String str = null;
            if (layout == null && domainClassWithPackage != null && domainField != null) {
                try {
                    IRulesEngine rulesEngine = RulesEngineFactory.getRulesEngine();
                    IPropertyRuleIntrospector propertyRuleIntrospector = null;
                    if (rulesEngine != null)
                        propertyRuleIntrospector = rulesEngine.getPropertyRuleIntrospector(Class.forName(domainClassWithPackage), domainField);

                    // Wrap the propertyRuleIntrospector with the FieldMetaData
                    FieldMetaData meta = PersistentHelper.getFieldMetaData(domainClassWithPackage, domainField);
                    propertyRuleIntrospector = new PropertyRuleIntrospectorUsingFieldMetaData(propertyRuleIntrospector, meta);
                    str = propertyRuleIntrospector.format(field);
                } catch (Exception e) {
                    String s = "Exception thrown while formatting the field " + domainField;
                    log.error(s, e);
                    throw new RuntimeException(s, e);
                }
            } else if (layout != null)
                str = Formatter.format(field, layout);
            else
                str = Formatter.format(field);

            // Build the Output
            if (str != null && str.length() > 0) {
                if (beginMarker != null)
                    buf.append(beginMarker);

                // Truncate the formatted string, if needed, and append the truncateIndicator
                if (limit > 0 && str.length() > limit) {
                    buf.append(str.substring(0, limit - 1));
                    if (truncateIndicator != null)
                        buf.append(truncateIndicator);
                } else {
                    buf.append(str);
                }

                if (endMarker != null)
                    buf.append(endMarker);
            }


            out = buf.toString();
            if (toHtml)
                out = org.jaffa.util.StringHelper.convertToHTML(out);
        }
        return out;
    }

    /** Returns the default String used in the formatDescription() methods placed at the beginning.
     * @return  the default String used in the formatDescription() methods placed at the beginning.
     */
    public static String getDefaultDescriptionBeginMarker() {
        return DESCRIPTION_BEGIN_MARKER;
    }

    /** Returns the default String used in the formatDescription() methods placed at the end.
     * @return  the default String used in the formatDescription() methods placed at the end.
     */
    public static String getDefaultDescriptionEndMarker() {
        return DESCRIPTION_END_MARKER;
    }


    /** This is a helper for combining old and additional comments.
     * A stamp containing the userId and current time will be inserted before the additional comment.
     * The additional comment will be inserted before the old comment if 'lifo' is true, otherwise it'll be appended.
     * If the input userId is null, then the userId will be obtained from the SecurityManager.
     *
     * @deprecated This should not be called from the domain object that implement
     *             blog style comments, as this is now down through aspects.
     *             This will be removed when the v2.0 and earlier patterns that use
     *             this are removed
     *
     * @param originalComment The original comment.
     * @param additionalComment The additional comment.
     * @param lifo This determines if the additional comment is inserted before or appended after the old comment.
     * @param userId The userId.
     * @return The combination of the old comment and the additional comment.
     */
    public static String addCommentWithStamp(String originalComment, String additionalComment, boolean lifo, String userId) {
        if (additionalComment == null || additionalComment.trim().length() == 0)
            return originalComment;

        // create a buffer for the generated comment, by estimating an initial capacity
        int capacity = (originalComment != null ? originalComment.length() : 0)
        + (additionalComment != null ? additionalComment.length() : 0)
        + 100;
        StringBuffer buffer = new StringBuffer(capacity);

        // create the stamp
        if (originalComment != null)
            buffer.append("------- Additional ");
        else
            buffer.append("------- ");
        buffer.append("Comments ");
        if (userId == null && SecurityManager.getPrincipal() != null)
            userId = SecurityManager.getPrincipal().getName();
        if (userId != null) {
            buffer.append("From ");
            buffer.append(userId);
            buffer.append(' ');
        }
        buffer.append(Formatter.format(new DateTime()));
        buffer.append(" -------");
        buffer.append('\n');

        // add the new comments
        buffer.append(additionalComment);

        // add the original comments before or after the new comments, based on the 'lifo' flag
        if (originalComment != null) {
            if (lifo) {
                buffer.append('\n');
                buffer.append('\n');
                buffer.append(originalComment);
            } else {
                buffer.insert(0, '\n');
                buffer.insert(0, '\n');
                buffer.insert(0, originalComment);
            }
        }

        return buffer.toString();
    }


    /**
     * Returns the EOL character for the input String, if any.
     * The EOL character can be '\r', '\n' or '\r\n'.
     * @param input the input string.
     * @return the EOL character for the input String. A null string will be returned in case no EOL charcater is found.
     */
    public static String findEol(String input) {
        String output = null;
        if (input != null) {
            if (input.endsWith("\r\n"))
                output = "\r\n";
            else if (input.endsWith("\n"))
                output = "\n";
            else if (input.endsWith("\r"))
                output = "\r";
        }
        return output;
    }

    /**
     * Reads a line from the input reader.
     * It'll return an instance of StringHelper.Line object, which has the contents and EOL of the line.
     * The EOL character can be '\r', '\n' or '\r\n'.
     * This method is used instead of the readLine() method of the BufferedReader, since there is no other way of finding out if the last line in a file ends with an EOL.
     * This also has the added benefit of knowing the exact EOL character.
     * A null will be returned if the EOF is reached.
     * @param reader the input.
     * @return a StringHelper.Line object containing the line contents and the EOL character. A null is returned if the end of file is reached.
     * @throws IOException if any IO error occurs.
     */
    public static Line readLine(PushbackReader reader) throws IOException {
        Line line = null;
        StringBuffer contents = new StringBuffer();
        StringBuffer eol = new StringBuffer();
        int i;
        while ((i = reader.read()) != -1) {
            char ch = (char) i;
            if (ch == '\r') {
                eol.append(ch);
                if ((i = reader.read()) != -1) {
                    ch = (char) i;
                    if (ch == '\n')
                        eol.append(ch);
                    else
                        reader.unread(i);
                }
                break;
            } else if (ch == '\n') {
                eol.append(ch);
                break;
            } else {
                contents.append(ch);
            }
        }
        if (contents.length() > 0 || eol.length() > 0) {
            line = new Line();
            line.m_contents = contents.toString();
            line.m_eol = eol.toString();
        }
        return line;
    }

    /** This class contains the contents of a line and the EOL character.
     * The EOL character can be '\n', '\r' or '\r\n'.
     */
    public static class Line {
        private String m_contents;
        private String m_eol;

        /** Returns the contents of the line.
         * @return The contents.
         */
        public String getContents() {
            return m_contents;
        }

        /** Returns the EOL (End of Line) character of the line.
         * @return The EOL.
         */
        public String getEol() {
            return m_eol;
        }

        /** Returns a String by concatenating the contents and the EOL character.
         * @return a String by concatenating the contents and the EOL character.
         */
        public String toString() {
            StringBuffer buf = new StringBuffer();
            if (m_contents != null)
                buf.append(m_contents);
            if (m_eol != null)
                buf.append(m_eol);
            return buf.toString();
        }
    }

    /**
     * Characters used for Base 36 encoding
     */
    public static final String BASE36_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Characters used for Base 64 encoding
     * (See http://www.ietf.org/rfc/rfc2045.txt Table 1)
     */
    public static final String BASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    /**
     * Convert number to base 16 (Hex)
     * @param num Number to convert
     * @return value as a Hex string
     */
    public static String toBase16(long num) {
        return toBase(num, BASE36_CHARS.substring(0,16) );
    }

    /**
     * Convert number to base 36 (0-9A-Z)
     * @param num Number to convert
     * @return encoded value as a string
     */
    public static String toBase36(long num) {
        return toBase(num, BASE36_CHARS);
    }

    /**
     * Encode number to base64 (based on http://www.ietf.org/rfc/rfc2045.txt Table 1)
     * @param num Number to convert
     * @return encoded value as a string
     */
    public static String toBase64(long num) {
        return toBase(num, BASE64_CHARS);
    }

    /**
     * Encode number to any base
     * @param num Number to convert
     * @param baseChars Alphabet of characters used to decode. Size of string implies base,
     * ie "01" would be binary (base 2)
     * @return encoded value as a string
     */
    public static String toBase(long num, String baseChars) {
        int base = baseChars.length();
        if(num<0) throw new RuntimeException("toBase() only works for positive integers");
        if(num==0) return ""+baseChars.charAt(0);
        StringBuffer sb = new StringBuffer();
        for(;num>0;) {
            int mod = (int)(num%base);
            if(mod<0)
                System.out.println(num+"%"+base+"="+mod);
            sb.append(baseChars.charAt(mod));
            num = num/base;
        }
        sb.reverse();
        return sb.toString();
    }

    /**
     * Decode number from any base.
     * @param num Encoded String representation of number
     * @param baseChars Alphabet of characters used to decode. Size of string implies base,
     * ie "01" would be binary (base 2)
     * @return Decoded based 10 number
     */
    public static long fromBase(String num, String baseChars) {
        int base = baseChars.length();
        long x = 0;
        if(num!=null)
            for(int i=0;i<num.length();i++) {
                char c=num.charAt(i);
                int p=baseChars.indexOf(c);
                if(p<0) throw new RuntimeException("fromBase() Invalid character '"+c+"' at position " + i);
                //System.out.println("["+c+"] x=("+x+"*"+base+")+"+p);
                x=(x*base)+p;
            }
        //System.out.println(num+"[Base "+base+"] = " + x);
        return x;
    }

    /**
     * Get the name of a class without the package name.
     * @param clazz Class to inspect.
     * @return the name of a class without the package name.
     */
    public static String getShortClassName(Class clazz) {
        return clazz != null ? getShortClassName(clazz.getName()) : null;
    }

    /**
     * Get the name of a class without the package name.
     * @param className Class to inspect.
     * @return the name of a class without the package name.
     */
    public static String getShortClassName(String className) {
        int i = className != null ? className.lastIndexOf('.') : -1;
        return i >= 0 && (i + 1) < className.length() ? className.substring(i + 1) : className;
    }

    /**
     * Return a representation of a String array in the format of
     * "['{1}','{2}',...]". A null is return as the the string "null",
     * and empty array is returned as "[]"
     * @param s String array to convert
     * @return Single String representing the Array.
     */
    public static String printArray(String[] s) {
        StringBuffer sb = new StringBuffer("[");
        if(s==null)
            sb.append("null");
        else for(int i=0;i<s.length;i++)
            sb.append(i==0?"":",").append('"').append(s[i]).append('"');
        return sb.append("]").toString();
    }

    /**
     * Extract name+value pairs, separated by the comma character, from the input String.
     * For example, the input String could be of the type "n1=v1,n2=v2,n3=v3", will result
     * in a Map containing 3 entries.
     * @param input the input String.
     * @return a Map containing name+value pairs.
     */
    public static Map<String, String> extractNameValuePairs(String input) {
        return extractNameValuePairs(input, ',');
    }

    /**
     * Extract name+value pairs from the input String.
     * For example, the input String could be of the type "n1=v1,n2=v2,n3=v3", will result
     * in a Map containing 3 entries.
     * @param input the input String.
     * @param separator the character used to separate name+value pairs in the input String.
     * @return a Map containing name+value pairs.
     */
    public static Map<String, String> extractNameValuePairs(String input, char separator) {
        Map<String, String> output = null;
        if (input != null && input.trim().length() > 0) {
            output = new LinkedHashMap<String, String>();
            String[] inputElements = split(input.trim(), separator);
            for (String inputElement : inputElements) {
                String[] nameAndValue = split(inputElement, '=');
                if (nameAndValue.length == 2) {
                    String name = nameAndValue[0].trim();
                    String value = nameAndValue[1].trim();

                    // Remove the enclosing quotes, if present
                    if (value.length() >= 2 && ((value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'')
                            || (value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"')))
                        value = value.substring(1, value.length() - 1);

                    output.put(name, value != null && value.length() > 0 ? value : null);
                } else {
                    log.warn("Invalid element '" + inputElement + "' passed in the input '" + input + '\'');
                }
            }
        }
        return output != null && !output.isEmpty() ? output : null;
    }

    /**
     * Converts an array of Strings into a single string, useful for debugging.
     * @param strings
     * @return the easy-to-read string
     */
    public static String stringArrayToString(String[] strings) {
        String result = null;
        if (strings != null) {
            StringBuilder buf = new StringBuilder();
            buf.append("[");
            String separator = ""; // the first element won't have a preceding comma
            for (String key : strings) { // make a comma-separated list
                buf.append(separator);
                separator = ",";
                buf.append(key);
            }
            buf.append("]");
            result = buf.toString();
        }
        return result;
    }


    /**
	 * Split the input String into lines at each newline character.
	 * For each split line, word wrap at the maxLength or closest word boundary.
	 * Return the newly created lines in a String array.
	 * @param str String to be split into lines.
	 * @param maxLength The maximum length of a returned line.
	 * @return A String array of lines resulting from splitting and wrapping the input string.
	 */
	public static String[] splitLines(String str, int maxLength) {
	    List list = new LinkedList();
	    if (maxLength > 0 && str != null) {
	        Pattern p = Pattern.compile("(.{1," + maxLength + "}\\b\\s*)|(.{" + maxLength + "}\\B)");
	        String[] lines = str.split("\n");
	        for (String line : lines) {
	            Matcher m = p.matcher(line.trim());
	            while (m.find()) {
	                list.add(m.group());
	            }
	        }
	    }
	    return (String[]) list.toArray(new String[list.size()]);
    }

    /** Test Routine
     * @param args no args required
     */
    public static void main(String[] args) {
        // @todo: Main method used to run simple tests, should be moved to unit test package
        String source, find, replace;

        System.out.println("\nSearch/Replace Test\n");
        source="To be or not to be, that is the question";
        find="be";
        replace="exist";
        System.out.println("Source:" + source);
        System.out.println("Find:" + find);
        System.out.println("Replace:" + replace);
        System.out.println("Result:" + replace(source,find,replace));

        System.out.println("\nXML Decode Test\n");
        source="&lt; &apos;1&apos; &amp; &apos;2&apos; = &quote;3&quote; &gt;";
        System.out.println("Source:" + source);
        System.out.println("Result:" + convertFromHTML(source));

        System.out.println("\nHTML Encode Test\n");
        source="< '1' & '2' = \"3\">";
        System.out.println("Source:" + source);
        System.out.println("Result:" + convertToHTML(source));

        System.out.println("\nLine Pad Test 1\n");
        source="Every Rose Has A Thorn\nEvery Cowboy\nSings A Sad, Sad Song";
        System.out.println(linePad(source,3));
        System.out.println(linePad(source,2,"\t"));
        System.out.println(linePad(source,4,"-", true));

        System.out.println("To Hex 32 = " + toBase16(32) );

        long a=0,b=0;
        for(int i=0;i<10;i++) {
            a = (long) (Math.random()*Long.MAX_VALUE);
            System.out.print("Value=" + a);
            b = fromBase(toBase16(a),BASE36_CHARS.substring(0,16));
            System.out.print(", Base16= " + toBase16(a) + (a==b?" OK":" ERROR="+b));
            b = fromBase(toBase36(a),BASE36_CHARS);
            System.out.print(", Base36= " + toBase36(a) + (a==b?" OK":" ERROR="+b));
            b = fromBase(toBase64(a),BASE64_CHARS);
            System.out.print(", Base64= " + toBase64(a) + (a==b?" OK":" ERROR="+b));
            b = fromBase(toBase(a,"01"),"01");
            System.out.println(", Base2= " + toBase(a,"01") + (a==b?" OK":" ERROR="+b));
        }

        Class c = Map.class;
        System.out.println("Short Name of " + c + " is " + getShortClassName(c));
        c = Map.Entry.class;
        System.out.println("Short Name of " + c + " is " + getShortClassName(c));


    }
}
