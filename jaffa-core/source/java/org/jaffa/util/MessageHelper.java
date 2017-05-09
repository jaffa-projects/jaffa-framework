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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

package org.jaffa.util;

import java.util.NoSuchElementException;
import java.util.Locale;
import org.apache.struts.util.MessageResources;
import org.apache.struts.Globals;
import org.apache.log4j.Logger;
import javax.servlet.jsp.PageContext;
import org.jaffa.presentation.portlet.session.LocaleContext;

/** This is a helper class for getting messages from the Resource. It also performs token replacement
 *
 * @author  GautamJ
 */
public class MessageHelper {
    
    private static final char TOKEN_BEGIN_MARKER = '[';
    private static final char TOKEN_END_MARKER = ']';
    private static final char TOKEN_ESCAPE_MARKER = '\\';
    private static final Logger log = Logger.getLogger(MessageHelper.class);
    
    
    /**
     * Gets the message for the input key from the default resources, as specified in the 'framework.messageResources.bundle' property of framework.properties files.
     * A null will be returned in case an invalid or if no ResourceBundle is specified in the 'framework.messageResources.bundle' property of framework.properties file.
     * It uses the locale for the user from the thread variable, if available, else it uses the default locale for the JVM.
     * It will also perform token replacement.
     * @param key The key of the the message to be looked up.
     * @param args An array of arguments to be added to the message.
     * @return the message with the tokens replaced.
     */
    public static String findMessage(String key, Object[] args) {
        return findMessage(LocaleContext.getLocale(), key, args);
    }
    
    
    /**
     * Gets the message for the input key from the default resources, as specified in the 'framework.messageResources.bundle' property of framework.properties files.
     * A null will be returned in case an invalid or if no ResourceBundle is specified in the 'framework.messageResources.bundle' property of framework.properties file.
     * It will also perform token replacement.
     * @param locale the Locale to be used when looking up the ResourceBundle.
     * @param key The key of the the message to be looked up.
     * @param args An array of arguments to be added to the message.
     * @return the message with the tokens replaced.
     */
    public static String findMessage(Locale locale, String key, Object[] args) {
        if (PropertyMessageResourcesFactory.getDefaultMessageResources() != null)
            return findMessage(PropertyMessageResourcesFactory.getDefaultMessageResources(), locale, key, args);
        else
            return null;
    }
    
    
    /**
     * Gets the message for the input key from the resources (an attribute in the request stream). It will also perform token replacement.
     * This uses the default struts MessageResources and Locale.
     * @param pageContext The servlet pageContext we are processing.
     * @param key The key of the the message to be looked up.
     * @param args An array of arguments to be added to the message.
     * @return the message with the tokens replaced.
     */
    public static String findMessage(PageContext pageContext, String key, Object[] args) {
        return findMessage(pageContext, null, null, key, args);
    }
    
    
    /**
     * Gets the message for the input key from the resources (an attribute in the request stream). It will also perform token replacement.
     * @param pageContext The servlet pageContext we are processing.
     * @param resources The key that identifies that the MessageResources object in the pageContext
     * @param locale The key that identifies that the Locale object in the pageContext
     * @param key The key of the the message to be looked up.
     * @param args An array of arguments to be added to the message.
     * @return the message with the tokens replaced.
     */
    public static String findMessage(PageContext pageContext, String resources, String locale,
            String key, Object[] args) {
        return findMessage(findMessageResources(pageContext, resources), findLocale(pageContext, locale),
                key, args);
    }
    
    /**
     * Gets the message for the input key from the resources. It will also perform token replacement.
     * @param resources The underlying resource bundle to look at.
     * @param locale The requested message Locale.
     * @param key The key of the the message to be looked up.
     * @param args An array of arguments to be added to the message.
     * @return the message with the tokens replaced.
     */
    public static String findMessage(MessageResources resources, Locale locale, String key, Object[] args) {
        // Get the message
        if (log.isDebugEnabled())
            log.debug("Looking up the ResourceBundle for the key - " + key);
        
        if (key == null)
            return null;
        
        String message = null;
        if (args == null)
            message = resources.getMessage(locale, key);
        else
            message = resources.getMessage(locale, key, args);
        
        // now perform token replacement
        if (message != null)
            message = replaceTokens(resources, locale, message);
        
        return message;
    }
    
    
    /**
     * Replace the tokens in the input message with appropriate values from the default resources, as specified in the 'framework.messageResources.bundle' property of framework.properties files.
     * A null will be returned in case an invalid or if no ResourceBundle is specified in the 'framework.messageResources.bundle' property of framework.properties file.
     * It uses the locale for the user from the thread variable, if available, else it uses the default locale for the JVM.
     * If the value for a token, is again a token, then it will recursively find the value.
     * @param message Message to be looked up for token replacement.
     * @return the message with the tokens replaced.
     */
    public static String replaceTokens(String message) {
        return replaceTokens(LocaleContext.getLocale(), message);
    }
    
    
    /**
     * Replace the tokens in the input message with appropriate values from the default resources, as specified in the 'framework.messageResources.bundle' property of framework.properties files.
     * A null will be returned in case an invalid or if no ResourceBundle is specified in the 'framework.messageResources.bundle' property of framework.properties file.
     * If the value for a token, is again a token, then it will recursively find the value.
     * @param locale the Locale to be used when looking up the ResourceBundle.
     * @param message Message to be looked up for token replacement.
     * @return the message with the tokens replaced.
     */
    public static String replaceTokens(Locale locale, String message) {
        if (PropertyMessageResourcesFactory.getDefaultMessageResources() != null)
            return replaceTokens(PropertyMessageResourcesFactory.getDefaultMessageResources(), locale, message);
        else
            return null;
    }
    
    
    /**
     * Replace the tokens in the input message with appropriate values from the resources (an attribute in the request stream).
     * This uses the default struts MessageResources and Locale.
     * If the value for a token, is again a token, then it will recursively find the value.
     * @param pageContext The servlet pageContext we are processing.
     * @param message Message to be looked up for token replacement.
     * @return the message with the tokens replaced.
     */
    public static String replaceTokens(PageContext pageContext, String message) {
        return replaceTokens(pageContext, null, null, message);
    }
    
    
    /**
     * Replace the tokens in the input message with appropriate values from the resources (an attribute in the request stream).
     * If the value for a token, is again a token, then it will recursively find the value.
     * @param pageContext The servlet pageContext we are processing.
     * @param resources The key that identifies that the MessageResources object in the pageContext
     * @param locale The key that identifies that the Locale object in the pageContext
     * @param message Message to be looked up for token replacement.
     * @return the message with the tokens replaced.
     */
    public static String replaceTokens(PageContext pageContext, String resources, String locale, String message) {
        return replaceTokens(findMessageResources(pageContext, resources), findLocale(pageContext, locale), message);
    }
    
    
    /**
     * Replace the tokens in the input message with appropriate values from the resources.
     * If the value for a token, is again a token, then it will recursively find the value.
     * Note: Ensure that are no cyclic references from one token to another to the first token.
     * @param resources The underlying resource bundle to look at.
     * @param locale The requested message Locale.
     * @param message Message to be looked up for token replacement.
     * @return the message with the tokens replaced.
     */
    public static String replaceTokens(MessageResources resources, Locale locale, String message) {
        if (log.isDebugEnabled())
            log.debug("Replacing tokens in: " + message);
        String out = message;
        // no need for token-replacement if the size of the message is less than token-markers
        if (message != null && message.length() > 2) {
            try {
                StringBuilder buf = new StringBuilder();
                Tokenizer t = new Tokenizer(message);
                while (true) {
                    if (t.hasLiteral())
                        buf.append(t.next());
                    else if (t.hasToken()) {
                        String token = t.next();
                        String tokenizedString = new StringBuilder().append(TOKEN_BEGIN_MARKER).append(token).append(TOKEN_END_MARKER).toString();
                        String tokenReplacement = resources.getMessage(locale, token);
                        if (tokenReplacement == null) {
                            // no error will be raised.. just return with TOKEN_MARKERS around the token name
                            tokenReplacement = tokenizedString;
                        } else if (!tokenReplacement.equals(tokenizedString)) {
                            // perform token-replacement recursively
                            tokenReplacement = replaceTokens(resources, locale, tokenReplacement);
                        }
                        buf.append(tokenReplacement);
                    } else
                        break;
                }
                out = buf.toString();
                if (log.isDebugEnabled())
                    log.debug("Output after replacing tokens: " + message);
            } catch (Exception e) {
                if (log.isDebugEnabled())
                    log.debug("Exception thrown while replacing tokens in '" + message + "'. Will return the input as is", e);
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("No need for token replacement in: " + message);
        }
        return out;
    }
    
    
    /** This will add the Token Markers around the input String.
     * @param input the string to be tokenized.
     * @return the string with the Token Markers around it. A null is returned if the input is null. If the input begins or ends with the token marker, then it will be returned as is.
     */
    public static String tokenize(String input) {
        if (input == null)
            return null;
        else if (input.charAt(0) == TOKEN_BEGIN_MARKER)
            return input;
        else
            return new StringBuilder().append(TOKEN_BEGIN_MARKER).append(input).append(TOKEN_END_MARKER).toString();
    }
    
    /** Returns a true if the input String consists of a tokenized string.
     * @param input the string to be checked for presence of tokens
     * @return a true if the input String consists of a tokenized string.
     */
    public static boolean hasTokens(String input) {
        if (input != null && input.length() > 2) {
            Tokenizer t = new Tokenizer(input);
            while (true) {
                if (t.hasToken())
                    return true;
                else if (t.hasLiteral())
                    t.next();
                else
                    break;
            }
        }
        return false;
    }
    
    /** This is a convenience method to remove the token markers from the input string.
     * This method will remove the markers at the beginning and at the end of the string only.
     * Otherwise it'll simply return the input as is.
     * @param input The string from which the token markers are to be removed.
     * @return The input string minus its outer token markers, if any.
     */
    public static String removeTokenMarkers(String input) {
        String output = input;
        if (isSingleToken(input))
            output = input.substring(1, input.length() - 1);
        return output;
    }
    
    
    public static boolean isSingleToken(String input) {
        //@TODO could use a regex? ^\[[^\[]+\]$
        return input != null && input.length() > 0
                && input.charAt(0) == TOKEN_BEGIN_MARKER && input.charAt(input.length() - 1) == TOKEN_END_MARKER
                && input.substring(1, input.length() - 1).indexOf(TOKEN_BEGIN_MARKER) == -1;
    }
    
    
    /** This returns the Struts MessageResources.*/
    private static MessageResources findMessageResources(PageContext pageContext, String resources) {
        return (MessageResources) pageContext.findAttribute((resources != null ? resources : Globals.MESSAGES_KEY));
    }
    
    /** This returns the Locale set by the Struts framework in the Session object.*/
    private static Locale findLocale(PageContext pageContext, String locale) {
        // Obtain locale from the LocaleContext, which may return the default Locale for the JVM
        Locale userLocale = LocaleContext.getLocale();

        // Obtain the user's locale that would have been set by the Struts RequestProcessor
        if (userLocale == null)
            userLocale = (Locale) pageContext.findAttribute((locale != null ? locale : Globals.LOCALE_KEY));

        return userLocale;
    }
    
    
    /** This class can be used to iterate over literals and tokens from an input String.
     */
    public static class Tokenizer {
        
        private String m_input = null;
        private int m_index = -1;
        private StringBuilder m_elementBuf = new StringBuilder();
        private boolean m_isToken = false;
        
        /** Creates an instance.
         * An IllegalArgumentException is thrown if the input is null.
         * @param input The String to tokenize.
         */
        public Tokenizer(String input) {
            if (input == null)
                throw new IllegalArgumentException("Input cannot be null");
            m_input = input;
            parse();
        }
        
        /** Returns true if the next element in the iteration is a literal.
         * @return true if the next element in the iteration is a literal.
         */
        public boolean hasLiteral() {
            return m_elementBuf.length() > 0 && !m_isToken;
        }
        
        /** Returns true if the next element in the iteration is a token.
         * @return true if the next element in the iteration is a token.
         */
        public boolean hasToken() {
            return m_elementBuf.length() > 0 && m_isToken;
        }
        
        /** Returns the next element in the iteration.
         * A NoSuchElementException is thrown if there is no element to return.
         * @return the next element in the iteration.
         */
        public String next() {
            if (m_elementBuf.length() == 0)
                throw new NoSuchElementException("No more elements to return");
            String element = m_elementBuf.toString();
            m_elementBuf.setLength(0);
            parse();
            return element;
        }
        
        /** Parses the input String to create the next element in the iteration.
         * An IllegalArgumentException is thrown if
         * - A TokenBeginMarker is found within another Token.
         * - A TokenEndMarker is found before a TokenBeginMarker.
         * - An empty Token is found.
         * - A TokenEndMarker is not found inside a Token.
         */
        private void parse() {
            m_isToken = false;
            int tokenBeginFoundAt = -1;
            while (++m_index < m_input.length()) {
                char c = m_input.charAt(m_index);
                if (c == TOKEN_BEGIN_MARKER) {
                    if (isEscaped()) {
                        // Simply add to the buffer if the character is escaped
                        m_elementBuf.append(c);
                    } else {
                        if (tokenBeginFoundAt >= 0) {
                            // We are already inside a token
                            throw new IllegalArgumentException("[ found at index " + m_index
                                    + ", while the [ at index " + tokenBeginFoundAt + " is not closed in the String '" + m_input + '\'');
                        } else if (m_elementBuf.length() > 0) {
                            // Literal exists in the buffer. Decrement the index so that the next invocation of parse() starts with the token
                            --m_index;
                            break;
                        } else {
                            // Start of a Token
                            tokenBeginFoundAt = m_index;
                        }
                    }
                } else if (c == TOKEN_END_MARKER) {
                    if (isEscaped()) {
                        // Simply add to the buffer if the character is escaped
                        m_elementBuf.append(c);
                    } else {
                        if (tokenBeginFoundAt < 0) {
                            // We should have been inside a token
                            throw new IllegalArgumentException("] found at index " + m_index + " before any [ in the String '" + m_input + '\'');
                        } else if (m_elementBuf.length() == 0) {
                            // An empty token!
                            throw new IllegalArgumentException("An empty [] found at index " + m_index + " in the String '" + m_input + '\'');
                        } else {
                            // End of token
                            m_isToken = true;
                            break;
                        }
                    }
                } else {
                    m_elementBuf.append(c);
                }
            }
            
            // Ensure that the TokenEndMarker was found for a Token
            if (tokenBeginFoundAt >= 0 && !m_isToken)
                throw new IllegalArgumentException("A matching ] is missing for the [ at index " + tokenBeginFoundAt + " in the String '" + m_input + '\'');
        }
        
        /** Returns true if the character at the end of the element buffer is an ESCAPE character.
         * The escape character, if found, is removed.
         * NOTE: This can be enhanced to support the escaping of the ESCAPE character.
         */
        private boolean isEscaped() {
            int previousIndex = m_elementBuf.length() - 1;
            if (previousIndex >= 0 && m_elementBuf.charAt(previousIndex) == TOKEN_ESCAPE_MARKER) {
                m_elementBuf.setLength(previousIndex);
                return true;
            } else
                return false;
        }
    }
    
}
