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

// NOTE: do not use the 'GEN-LINE' as it causes a blank line to be added !!!

package org.jaffa.tools.common;

import org.jaffa.util.StringHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.*;

/** This class will decompose a file into a collection of GuardedBlock, GuardedBorder and PlainText objects.
 * The file will have to be created/views within NetBeans/Forte to appreciate the significance of these different objects.
 * A GuardedBlock is a piece of text which cannot be modified in the NetBeans editor.
 * A GuardedBorder marks a block where custom code can be entered in the NetBeans editor.
 * A PlainText is just some text, which is not guarded.
 */
public class SourceDecomposer {
    static final String LINE = "//GEN-LINE";
    static final String BEGIN = "//GEN-BEGIN";
    static final String END = "//GEN-END";
    static final String FIRST = "//GEN-FIRST";
    static final String LAST = "//GEN-LAST";

    private Collection m_col = new ArrayList();
    private Map m_guardedBorderMap = new HashMap();

    /** This will create an instance of the class. It will decompose the file passed via the input Reader.
     * @param reader The input file passed as a stream.
     * @throws IOException if any IO error occurs
     * @throws SourceDecomposerException if the file is malformed or if it cannot be decomposed.
     */
    public SourceDecomposer(BufferedReader reader)
    throws IOException, SourceDecomposerException {
        decompose(new PushbackReader(reader));
    }

    /** Returns the Collection of the decomposed stream elements.
     * @return the Collection of the decomposed stream elements.
     */
    public Collection getCollection() {
        return m_col;
    }

    /** Returns a GuardedBorder object for the input key. A null will be returned if no such object exists.
     * @param key the input.
     * @return a GuardedBorder object for the input key.
     */
    public GuardedBorder getGuardedBorder(String key) {
        return (GuardedBorder) m_guardedBorderMap.get(key);
    }

    /** decompose the input stream into a Collection containing PlainText, GuardedBlock & GuardedBorder */
    private void decompose(PushbackReader reader)
    throws IOException, SourceDecomposerException {

        StringHelper.Line line = null;
        StringBuffer buf = new StringBuffer();
        boolean gotBegin = false;
        boolean gotFirst = false;
        String key = null;

        while ((line = StringHelper.readLine(reader)) != null) {
            if (line.getContents().indexOf(LINE) >= 0) {
                // check for errors
                if (gotBegin || gotFirst)
                    throw new SourceDecomposerException(buf.toString() + line);

                // create a PlainText if the buffer has contents
                if (buf.length() >= 0) {
                    m_col.add( new PlainText(buf.toString()) );
                    buf.setLength(0);
                }

                // create a GuardedBlock
                m_col.add( new GuardedBlock(getKey(line.getContents(), LINE), line.toString()) );

            } else if (line.getContents().indexOf(BEGIN) >= 0) {
                // check for errors
                if (gotBegin || gotFirst)
                    throw new SourceDecomposerException(buf.toString() + line);

                // create a PlainText if the buffer has contents
                if (buf.length() > 0) {
                    m_col.add( new PlainText(buf.toString()) );
                    buf.setLength(0);
                }

                // add to the buffer & set the context
                buf.append(line);
                gotBegin = true;
                key = getKey(line.getContents(), BEGIN);

            } else if (line.getContents().indexOf(END) >= 0) {
                // check for errors
                if (!gotBegin || (key==null ? getKey(line.getContents(), END) != null : !key.equals(getKey(line.getContents(), END)) ) )
                    throw new SourceDecomposerException(buf.toString() + line);

                buf.append(line);
                m_col.add( new GuardedBlock(key, buf.toString() ) );
                buf.setLength(0);
                gotBegin = false;

            } else if (line.getContents().indexOf(FIRST) >= 0) {
                // check for errors
                if (gotBegin || gotFirst)
                    throw new SourceDecomposerException(buf.toString() + line);

                // create a PlainText if the buffer has contents
                if (buf.length() > 0) {
                    m_col.add( new PlainText(buf.toString()) );
                    buf.setLength(0);
                }

                // add to the buffer & set the context
                buf.append(line);
                gotFirst = true;
                key = getKey(line.getContents(), FIRST);

            } else if (line.getContents().indexOf(LAST) >= 0) {
                // check for errors
                if (!gotFirst || (key==null ? getKey(line.getContents(), LAST) != null : !key.equals(getKey(line.getContents(), LAST)) ) )
                    throw new SourceDecomposerException(buf.toString() + line);

                buf.append(line);
                GuardedBorder gb =  new GuardedBorder(key, buf.toString() );
                m_col.add(gb);
                m_guardedBorderMap.put(key, gb);
                buf.setLength(0);
                gotFirst = false;

            } else {
                buf.append(line);
            }
        }

        if (buf.length() > 0) {
            if (gotBegin || gotFirst)
                throw new SourceDecomposerException(buf.toString() + line);

            m_col.add( new PlainText(buf.toString()) );
            buf.setLength(0);
        }
    }

    private String getKey(String line, String searchStr) {
        String key = null;
        searchStr = searchStr + ":";
        int i = line.indexOf(searchStr);
        if ( i >= 0 && (i + searchStr.length()) < line.length() ) {
            //key = line.substring( i + searchStr.length() );
            StringTokenizer stz = new StringTokenizer( line.substring( i + searchStr.length() ) );
            if ( stz.hasMoreTokens() )
                key = stz.nextToken();
        }
        return key;
    }




    /** For the input file, a PlainText object will represent text, which is not guarded.
     */
    public static class PlainText {
        private String m_contents = null;

        /** Creates an instance.
         * @param contents The text that this object represents.
         */
        public PlainText(String contents) {
            m_contents = contents;
        }

        /** Returns the text that this object represents.
         * @return the text that this object represents.
         */
        public String getContents() {
            return m_contents;
        }

        /** Sets the contents.
         * @param contents the contents.
         */
        public void setContents(String contents) {
            m_contents = contents;
        }

        /** Returns debug info.
         * @return debug info.
         */
        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append("<PlainText>");
            buf.append("<contents>"); if (m_contents != null) buf.append(m_contents); buf.append("</contents>");
            buf.append("</PlainText>");
            return buf.toString();
        }
    }

    /** For the input file, a GuardedBlock object will represent text,  which cannot be modified in the NetBeans editor.
     */
    public static class GuardedBlock {
        private String m_key = null;
        private String m_contents = null;

        /** Creates an instance.
         * @param key The key which indentifies this guarded block of text.
         * @param contents The contents of the guarded block.
         */
        public GuardedBlock(String key, String contents) {
            m_key = key;
            m_contents = contents;
        }

        /** Returns the key.
         * @return the key.
         */
        public String getKey() {
            return m_key;
        }

        /** Returns the contents.
         * @return the contents.
         */
        public String getContents() {
            return m_contents;
        }

        /** Sets the contents.
         * @param contents the contents.
         */
        public void setContents(String contents) {
            m_contents = contents;
        }

        /** Returns debug info.
         * @return debug info.
         */
        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append("<GuardedBlock>");
            buf.append("<key>"); if (m_key != null) buf.append(m_key); buf.append("</key>");
            buf.append("<contents>"); if (m_contents != null) buf.append(m_contents); buf.append("</contents>");
            buf.append("</GuardedBlock>");
            return buf.toString();
        }
    }

    /** For the input file, a GuardedBorder object will represent a block where custom code can be entered in the NetBeans editor.
     */
    public static class GuardedBorder {
        private String m_key = null;
        private String m_contents = null;

        /** Creates an instance.
         * @param key The key which indentifies this guarded border
         * @param contents The contents of the guarded border.
         */
        public GuardedBorder(String key, String contents) {
            m_key = key;
            m_contents = contents;
        }

        /** Returns the key.
         * @return the key.
         */
        public String getKey() {
            return m_key;
        }

        /** Returns the contents.
         * @return the contents.
         */
        public String getContents() {
            return m_contents;
        }

        /** Sets the contents.
         * @param contents the contents.
         */
        public void setContents(String contents) {
            m_contents = contents;
        }

        /** Returns debug info.
         * @return debug info.
         */
        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append("<GuardedBorder>");
            buf.append("<key>"); if (m_key != null) buf.append(m_key); buf.append("</key>");
            buf.append("<contents>"); if (m_contents != null) buf.append(m_contents); buf.append("</contents>");
            buf.append("</GuardedBorder>");
            return buf.toString();
        }
    }

}
