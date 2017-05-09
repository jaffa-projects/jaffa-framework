/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2008 JAFFA Development Group
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
 * HTMLHelper.java
 *
 * Created on November 20, 2008, 5:50 PM
 */

package org.jaffa.util;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Properties;
import org.apache.log4j.Logger;

/** Utility Class for HTML routines.
 *
 * @author  DennisL
 * @version 1.0
 */
public class HTMLHelper {

    private static final Logger log = Logger.getLogger(HTMLHelper.class);    
    private static final String TAG_REPLACE_PROPERTIES = "resources/htmlTagReplace.properties";     
    private static final String ENTITIES = "resources/htmlEntity.properties";
    private static Properties tagReplaceProperties;  
    private static Properties entityProperties;
    private static String newLine = System.getProperty("line.separator");
    
    /**
     * Extracts the text values from the input html String.
     * @param html The HTML String from which to extract text.
     * @return The text extracted from the input html String.
     */
    public static String extractText(String html) {
        String convertedHtml = html;
        
        // Remove all newline characters from the HTML String.         
        if (convertedHtml != null){
            convertedHtml = convertedHtml.replaceAll("\n", "");
        }
        if (convertedHtml != null){
            convertedHtml = convertedHtml.replaceAll("\r", "");
        }         
                 
        // Remove all comments
        if (convertedHtml != null) {
            convertedHtml = removeAllComments(convertedHtml);
        }
        
        // Replace HTML Entities in properties list.
        if (convertedHtml != null){
            convertedHtml = replaceAllEntities(convertedHtml);
        }
      
        // Replace HTML Tags in properties list.
        if (convertedHtml != null){
            convertedHtml = replaceAllTags(convertedHtml);
        }
        
        // Remove all remaining HTML Tags.
        if (convertedHtml != null){
            convertedHtml = removeAllTags(convertedHtml);
        }
        
        // Remove the first newLine if there is one.  This is an unintended occurrence
        // when ordered or unordered lists begin the HTML String.         
        if (convertedHtml != null && convertedHtml.length() > 0 && convertedHtml.startsWith(newLine)){
            convertedHtml = convertedHtml.replaceFirst(newLine,"");
        }
        
        // Trim newlines from the end of the HTML String in order
        // to return just the text portion.  Prevents extra blank lines
        // between two or more concatenated HTML Strings.
        while (convertedHtml != null && convertedHtml.length() > 0 &&
                (convertedHtml.endsWith("\n") || convertedHtml.endsWith("\r"))){
            if (convertedHtml.length() == 1)
                convertedHtml = "";
            else
                convertedHtml = convertedHtml.substring(0,convertedHtml.length()-1);
        }
        
        return convertedHtml;
    }
    
    /**
     * Removes all comments from the input html String.
     * @param html The HTML String from which to remove all tags.
     * @return The input HTML String after all tags have been removed.
     */
    public static String removeAllComments(String html) {
        if (log.isDebugEnabled()){
            log.debug("HTML String before comment removal:\n"+html);
        }
        String pattern = "<!\\-\\-.*?\\-\\->";
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = p.matcher(html);
        String convertedHtml = m.replaceAll("");
        if (log.isDebugEnabled()){
            log.debug("HTML String after comment removal:\n"+convertedHtml);
        }
        return convertedHtml;
    }
    
    /**
     * Removes all HTML tags from the input html String.
     * @param html The HTML String from which to remove all tags.
     * @return The input HTML String after all tags have been removed.
     */
    public static String removeAllTags(String html) {
        String pattern = "<[!\\:\\-/A-Z1-9]+( [^>]*)*>";
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(html);
        return m.replaceAll("");
    }
    
    /**
     * Replaces all HTML tags in the input html String using the specified replaceWith value.
     * @param html The HTML String on which to do the tag replacement.
     * @return The input HTML String after tag replacement has occurred.
     */
    public static String replaceAllTags(String html){
        if (log.isDebugEnabled()){
            log.debug("HTML String before tag replacement:\n"+html);
        }
        
        String convertedHtml = html;
        String replaceTag;
        String replaceWith;
        
        // Load htmlTagReplace.properties.
        if (tagReplaceProperties == null){
            loadTagReplaceProperties();
        }
        
        if (tagReplaceProperties != null){
            if (log.isDebugEnabled()){
                log.debug("==== Convert HTML using Tag Replace Properties ====");
            }
            for(Enumeration e = tagReplaceProperties.keys(); e.hasMoreElements(); ) {
                replaceTag = (String) e.nextElement();
                replaceWith = (String) tagReplaceProperties.getProperty(replaceTag);
                if (log.isDebugEnabled()){
                    log.debug(replaceTag+"="+replaceWith+".");
                }
                // Replace "\n" with actual newline character.
                if (replaceWith.contains("\\n")){
                    replaceWith = replaceWith.replaceAll("\\\\n", newLine);
                }
                convertedHtml = replaceTag(convertedHtml, replaceTag, replaceWith);
            }
        }else{
            log.error("Failed to find Properties File: " + TAG_REPLACE_PROPERTIES);
        }
        
        if (log.isDebugEnabled()){
            log.debug("HTML String after tag replacement:\n"+convertedHtml);
        }
        return convertedHtml;
    }

    /**
     * Replaces all occurrences of the input replaceTag in the input html String 
     * using the input replaceWith value.
     * @param html The HTML String on which to do the tag replacement.
     * @param replaceTag The HTML tag to replace.
     * @return The input HTML String after tag replacement has occurred.
     */
    public static String replaceTag(String html, String replaceTag, String replaceWith){
        String pattern = "<"+replaceTag+"( [^>]*)*>";
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(html);
        return m.replaceAll(replaceWith);
    }
    
    /**
     * Replaces all HTML entities in the input html String.  
     * Uses the entities defined in htmlEntity.properties file.
     * @param html The HTML String on which to do the Entity replacement.
     * @return The input HTML String after Entity replacement has occurred.
     */
    public static String replaceAllEntities(String html){
        if (log.isDebugEnabled()){
            log.debug("HTML String before entity replacement:\n"+html);
        }
        String convertedHtml = html;
        String replaceEntity;
        String replaceWith;
        // This match is for a pattern of only two or three digits such as "60" or "163" which
        // represents the decimal value of the Entity character such as &copy;=169.
        String pattern = "[0-9]{2,3}";
        Pattern p = Pattern.compile(pattern);
        
        // Load htmlEntities.properties.
        if (entityProperties == null){
            loadEntityProperties();
        }
        
        if (entityProperties != null){
            if (log.isDebugEnabled()){
                log.debug("==== Convert HTML using Entity Properties ====");
            }
            for(Enumeration e = entityProperties.keys(); e.hasMoreElements(); ) {
                replaceEntity = (String) e.nextElement();
                replaceWith = (String) entityProperties.getProperty(replaceEntity);
                if (log.isDebugEnabled()){
                    log.debug(replaceEntity+"="+replaceWith+".");
                }
                if (p.matcher(replaceWith).matches()){
                    replaceWith = ""+(char)Integer.parseInt(replaceWith);
                }
                convertedHtml = convertedHtml.replaceAll(replaceEntity, replaceWith);
            }
        }else{
            log.error("Failed to find Properties File: " + ENTITIES);
        }
        
        if (log.isDebugEnabled()){
            log.debug("HTML String after tag replacement:\n"+convertedHtml);
        }        
        return convertedHtml;
    }
   
    // Cache htmlTagReplace.properties in LinkedHashMap to retain input order.
    private static synchronized void loadTagReplaceProperties() {
        if (tagReplaceProperties == null) {
            if (log.isDebugEnabled()){
                log.debug("Creating Tag Replace Properties Cache");
            }
            InputStream in = null;
            try {
                in = URLHelper.getInputStream(TAG_REPLACE_PROPERTIES);
                if (in == null){
                    log.error("Failed to find Properties File: " + TAG_REPLACE_PROPERTIES);
                }else{
                    Properties properties = new ListProperties();
                    properties.load(in);
                    tagReplaceProperties = properties;
                }
            } catch (IOException e) {
                log.error("Failed to Load Properties from : " + TAG_REPLACE_PROPERTIES, e);
            } finally {
                try {
                    if (in != null)
                        in.close();
                } catch (Exception e) {
                    // Do nothing.
                    if (log.isDebugEnabled())
                        log.debug("Exception closing the InputStream for " + TAG_REPLACE_PROPERTIES, e);
                }
            }
        }
        
    }
           
    // Cache htmlEntity.properties.
    private static synchronized void loadEntityProperties() {
        if (entityProperties == null) {
            if (log.isDebugEnabled()){
                log.debug("Creating Entity Properties Cache");
            }
            InputStream in = null;
            try {
                in = URLHelper.getInputStream(ENTITIES);
                if (in == null){
                    log.error("Failed to find Properties File: " + ENTITIES);
                }else{
                    Properties properties = new ListProperties();
                    properties.load(in);
                    entityProperties = properties;
                }
            } catch (IOException e) {
                log.error("Failed to Load Properties from : " + ENTITIES, e);
            } finally {
                try {
                    if (in != null)
                        in.close();
                } catch (Exception e) {
                    // Do nothing.
                    if (log.isDebugEnabled())
                        log.debug("Exception closing the InputStream for " + ENTITIES, e);
                }
            }
        }
    }
    
}
