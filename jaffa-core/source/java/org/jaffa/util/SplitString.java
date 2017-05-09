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

package org.jaffa.util;

/** This class splits a String based on a separator. It then sets the prefix and suffix.
 */
public class SplitString {
    private String pre = null;
    private String post = null;
    private boolean split = false;

    /** Creates an instance with the source String and separator. The source String will be split based on the separator.
     * This a short hand version of <code>SplitString(source, separator, true)</code>
     * @param source The source String.
     * @param separator The separator used for splitting the source String.
     */
    public SplitString(String source, String separator) {
        this(source, separator, true);
    }

    /** Creates an instance with the source String and separator. The source String will be split based on the separator.
     * @param source The source String.
     * @param separator The separator used for splitting the source String.
     * @param findFirst If true splits based on first occurence of seperator, if false
     *        split is done on the last occurence.
     */
    public SplitString(String source, String separator, boolean findFirst) {
        int i;
        if(findFirst)
            i = source.indexOf(separator);
        else
            i = source.lastIndexOf(separator);
        if(i==-1) {
            // Not found
            pre = source;
            post = null;
        } else {
            // Found
            split = true;
            if(i==0) {
               // Found at start of string
               pre = null;
               // Extract data after separator
               post = source.substring(separator.length());
            } else {
                // Found in middle
                // Extract Start
                pre = source.substring(0,i);
                if(i+separator.length() >= source.length())
                    // Found at end :-(
                    post = null;
                else
                    post = source.substring(i+separator.length());
            }
        }
    }

    /** The definition of a valid split is one where either there was
     * no split in the string, or the splitter resulted in two new
     * strings. (i.e the separator should not start or end the string)
     * @return a true if the split is valid.
     */
    public boolean isValid() {
        return (!split || (split && pre!=null && post!=null));
    }

    /** Returns the prefix String that was created as a result of the split.
     * @return the prefix String that was created as a result of the split.
     */
    public String getPrefix() {
        return pre;
    }

    /** Returns the suffix String that was created as a result of the split.
     * @return the suffix String that was created as a result of the split.
     */
    public String getSuffix() {
        return post;
    }
}
