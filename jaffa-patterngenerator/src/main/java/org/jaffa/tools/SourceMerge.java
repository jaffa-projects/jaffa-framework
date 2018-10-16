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

package org.jaffa.tools;

import org.jaffa.tools.common.SourceDecomposer;
import org.jaffa.tools.common.SourceDecomposerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/** This class is used for merging an existing file with a new code generated file.
 * It will merge the GuardedBorder contents (i.e the custom code) of the existing file into the new file.
 */
public class SourceMerge {

    /** Merges the existing File into the new code generated file returning the contents as a String
     * @param newTemplate the new code generated file.
     * @param existingFile the existing file.
     * @throws IOException if any IO error occurs.
     * @throws SourceDecomposerException if the file is malformed or if it cannot be decomposed.
     * @return the merged contents.
     */
    public static String performMerge(BufferedReader newTemplate, BufferedReader existingFile)
    throws IOException, SourceDecomposerException {
        StringBuffer buf = new StringBuffer();
        SourceDecomposer sdNew = new SourceDecomposer(newTemplate);
        SourceDecomposer sdExisting = new SourceDecomposer(existingFile);
        Collection elements = sdNew.getCollection();
        if (elements != null) {
            for (Iterator itr = elements.iterator(); itr.hasNext();) {
                Object obj = itr.next();
                if (obj instanceof SourceDecomposer.PlainText) {
                    buf.append(((SourceDecomposer.PlainText) obj).getContents());
                } else if (obj instanceof SourceDecomposer.GuardedBlock) {
                    buf.append(((SourceDecomposer.GuardedBlock) obj).getContents());
                } else if (obj instanceof SourceDecomposer.GuardedBorder) {
                    SourceDecomposer.GuardedBorder newGB = (SourceDecomposer.GuardedBorder) obj;

                    // check if the existing file has this piece of code
                    SourceDecomposer.GuardedBorder existingGB = sdExisting.getGuardedBorder( newGB.getKey() );

                    if (existingGB != null)
                        buf.append(existingGB.getContents());
                    else
                        buf.append(newGB.getContents());
                } else {
                    // do nothing
                }

            }
        }
        return buf.toString();
    }

}
