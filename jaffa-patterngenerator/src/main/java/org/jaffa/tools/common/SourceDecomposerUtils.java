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

package org.jaffa.tools.common;

import org.jaffa.tools.common.SourceDecomposer;
import org.jaffa.util.StringHelper;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/** Utilities class for the SourceDecomposer.
 */
public class SourceDecomposerUtils {
    
    private static final String JAFFA_OVERWRITE = "//JAFFA-OVERWRITE";
    
    /** This method will list the customizations added to code generated files.
     * @param dir the directory containing the the code generated files.
     * @param fileFilter the files will be selected based on this filter. if no value is specified, then all files will be picked up.
     * @param recursive if true, then the sub-folders will be checked too.
     * @param writer the customizations will be written to this writer.
     * @param customizationFilter the customizations will be filtered based on this paramter. If no value is passed, then all the customizations will be listed.
     * @throws IOException if any IO error occurs.
     * @throws SourceDecomposerException if the file is malformed or if it cannot be decomposed.
     */
    public static void listCustomizations(String dir, String fileFilter, boolean recursive, BufferedWriter writer, String customizationFilter)
    throws IOException, SourceDecomposerException {
        listCustomizations(new File(dir), fileFilter, recursive, writer, customizationFilter);
    }
    
    /** This method will list the customizations added to code generated files.
     * @param dir the directory containing the the code generated files.
     * @param fileFilter the files will be selected based on this filter. if no value is specified, then all files will be picked up.
     * @param recursive if true, then the sub-folders will be checked too.
     * @param writer the customizations will be written to this writer.
     * @param customizationFilter the customizations will be filtered based on this paramter. If no value is passed, then all the customizations will be listed.
     * @throws IOException if any IO error occurs.
     * @throws SourceDecomposerException if the file is malformed or if it cannot be decomposed.
     */
    public static void listCustomizations(File dir, final String fileFilter, boolean recursive, BufferedWriter writer, String customizationFilter)
    throws IOException, SourceDecomposerException {
        if (!dir.exists() || !dir.isDirectory())
            throw new IllegalArgumentException("Invalid directory passed: " + dir);
        
        File[] files = null;
        if (fileFilter != null && fileFilter.length() > 0) {
            // create an anonymous class that implements the FileFilter interface, to obtain the files from the directory
            files = dir.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (pathname.isDirectory())
                        return true;
                    else {
                        try {
                            return Pattern.matches(fileFilter, pathname.getPath());
                        } catch (PatternSyntaxException e) {
                            e.printStackTrace();
                            throw new IllegalArgumentException("Invalid fileFilter passed: " + fileFilter);
                        }
                    }
                }
                
            });
        } else {
            files = dir.listFiles();
        }
        
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isFile() && !file.isHidden())
                listCustomizations(file, writer, customizationFilter);
            else if (recursive && file.isDirectory() && !file.isHidden())
                listCustomizations(file, fileFilter, recursive, writer, customizationFilter);
        }
    }
    
    /** This method will list the customizations added to a code generated file.
     * @param file the code generated file.
     * @param writer the customizations will be written to this writer.
     * @param customizationFilter the customizations will be filtered based on this paramter. If no value is passed, then all the customizations will be listed.
     * @throws IOException if any IO error occurs.
     * @throws SourceDecomposerException if the file is malformed or if it cannot be decomposed.
     */
    public static void listCustomizations(File file, BufferedWriter writer, String customizationFilter)
    throws IOException, SourceDecomposerException {
        System.out.println("Processing file... " + file);
        SourceDecomposer sd = new SourceDecomposer(new BufferedReader(new FileReader(file)));
        Collection elements = sd.getCollection();
        boolean headerWritten = false;
        if (elements != null) {
            for (Iterator itr = elements.iterator(); itr.hasNext();) {
                Object obj = itr.next();
                if (obj instanceof SourceDecomposer.GuardedBorder) {
                    SourceDecomposer.GuardedBorder gb = (SourceDecomposer.GuardedBorder) obj;
                    
                    // Check the name of the customization block against the filter
                    if (customizationFilter != null && customizationFilter.length() > 0) {
                        try {
                            // Ignore this customization, if it does not match the filter
                            if (!Pattern.matches(customizationFilter, gb.getKey()))
                                continue;
                        } catch (PatternSyntaxException e) {
                            e.printStackTrace();
                            throw new IllegalArgumentException("Invalid customizationFilter passed: " + customizationFilter);
                        }
                    }
                    
                    // Strip the borders from the contents
                    StringHelper.Line line = null;
                    StringBuffer buf = new StringBuffer();
                    PushbackReader reader = new PushbackReader(new StringReader(gb.getContents()));
                    while ((line = StringHelper.readLine(reader)) != null) {
                        if (line.getContents().indexOf(SourceDecomposer.FIRST) < 0 && line.getContents().indexOf(SourceDecomposer.LAST) < 0)
                            buf.append(line);
                    }
                    String contents = buf.toString().trim();
                    
                    // Only write if the contents are not empty
                    if (contents.length() > 0) {
                        if (!headerWritten) {
                            writer.write("===============================================================================\n");
                            writer.write(file.getPath());
                            writer.write("\n===============================================================================\n");
                            headerWritten = true;
                        }
                        writer.write("* " + gb.getKey() + '\n');
                        writer.write(contents);
                        writer.write("\n\n");
                        writer.flush();
                    }
                }
            }
        }
    }
    
    /** This method will check the code generated file for the '//JAFFA-OVERWRITE' marker.
     * Returns a true if the marker is found.
     * @param file the code generated file.
     * @throws IOException if any IO error occurs.
     * @return true if the '//JAFFA-OVERWRITE' marker is found in the input file.
     */
    public static boolean isJaffaOverwriteMarkerPresent(File file) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ( ( line = reader.readLine() ) != null ) {
                if (line.indexOf(JAFFA_OVERWRITE) >= 0)
                    return true;
            }
            return false;
        } finally {
            if (reader != null)
                reader.close();
        }
    }
    
    /*
    public static void main(String[] args) {
        try {
            // example usage of the 'listCustomizations' utility
            String dir = "C:/Sandbox/MyProject/source/java";
            String fileFilter = ".*finder.*java";
            boolean recursive = true;
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:/listCustomizations.txt"));
            String customizationFilter = ".*query.*";
            listCustomizations(dir, fileFilter, recursive, writer, customizationFilter);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
     */
}
