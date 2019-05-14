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
 * FindFiles.java
 *
 * Created on October 29, 2001, 2:16 PM
 */

package org.jaffa.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FilenameFilter;

/**This class is used to locate files with the specified set of root search directories. <p>
 * The root directorities and all of its sub folders will be searched for the specified file.<p>
 * This has been optimized not to search the same directories if the several root paths intersect.<p>
 * <p>
 * It currently does not support wildcard charaters in the filename parameter.<p>
 * <p>
 * <B>Example</B>
 * <pre>
 *     FindFiles x = new FindFiles( System.getProperty("java.class.path") , "ComponentDefinition.xml");
 *     List files = x.getList();
 *     if(files == null)
 *         System.out.println("No Files Found");
 *     else {
 *         for(Iterator it = files.iterator(); it.hasNext();) {
 *             File file = (File) it.next();
 *             System.out.println( file.getPath() );
 *         }
 *         System.out.println(files.size() + " File(s) Found");
 *     }
 * </pre>
 *
 * @author PaulE
 * @version 1.0
 */
public class FindFiles {

    private ArrayList m_filesFound = null;
    private String m_filter = null;
    private String m_pathList = null;

    /** Keep a list of the directories bein searched. Only search again if an entry is not in here...*/
    private static final boolean DIR_CACHING = true;
    private ArrayList m_dirCache = new ArrayList();

    /** Create a file search object specifying the search root directories and the filename to seach for. <p>
     * Note: the file name is case sensitive and does not support wild card characters.<p>
     * @param pathList A semicolon seperated list of root directories top be searched
     * @param filename The name of the file to search for
     */
    public FindFiles(String pathList, String filename) {
        m_filter = filename;
        m_pathList = pathList;
    }


    /** Resursivly search the speecified pathList for any file with the specified name
     * @return A List of Files (<CODE>java.io.File</CODE> Objects) that matched the search criteria in the constructor
     */
    public List getList() {
        if(m_filesFound == null) {
            m_filesFound = new ArrayList();
            findFiles();
        }
        return m_filesFound;
    }


    /* Look for the files in question */
    private void findFiles() {

        // Create the filter to use when searching for files
        // This could be enhanced to use a wildcard filter object if needed!!!!
        FilenameFilter filter = new DirFilter();

        // Tokenize the list of directories and loop through each one.
        StringTokenizer st = new StringTokenizer(m_pathList,File.pathSeparator);
        while (st.hasMoreTokens()) {
            String path = st.nextToken();
            File f = new File(path);
            // Only process this entry if it's a directory
            if(f.isDirectory()) {

                // Get all sub-directories under this root.
                DirList structure = new DirList(path);

                // For each sub directory, search it for any of the required file names
                for (Object o : structure.getList()) {
                    File dir = (File) o;
                    File[] list = dir.listFiles(filter);

                    if (list != null) {
                        // For any matching files, add it to the search results
                        // (assuming it is not in there already!)
                        for (File file : list) {
                            if (!m_filesFound.contains(file)) {
                                m_filesFound.add(file);
                            }
                        }   // for
                    }   // list != null
                }   // for each subdirectory
            }   // if is a directory
        }   // while
    }

    /** Inner class used to filter the search results */
    private class DirFilter implements FilenameFilter {
        /** Tests if a specified file should be included in a file list.
         *
         * @param dir the directory in which the file was found
         * @param name the name of the file
         * @return <CODE>true</CODE> if and only if the name should be included in the file list; <CODE>false</CODE> otherwise
         */
        public boolean accept(File dir, String name) {
            //System.out.println("Checking : " + name);
            //return m_filter.equalsIgnoreCase(name);
            return name.matches(m_filter);
        }
    }


    /** Inner Class Created For Traversing a Directory Structure */
    private class DirList {
        ArrayList m_files = null;
        String m_root;

        /** Create the tree specifying the starting point */
        DirList(String file) {
            m_root = file;
        }

        /** Recursive function to go down the tree */
        private void buildTree(File start) {
            if(start == null || !start.isDirectory())
                return;

            // Check the caching before continuing...
            if(DIR_CACHING == true) {
                if(m_dirCache.contains(start))
                    return;
                else
                    m_dirCache.add(start);
            }
            m_files.add(start);
            File[] contents = start.listFiles();
            for(int i = 0; i < contents.length; i++) {
                if(contents[i].isDirectory())
                    buildTree(contents[i]);
            }
        }

        /* Get the directory list */
        List getList() {
            if(m_files == null) {
                m_files = new ArrayList();
                buildTree(new File(m_root));
            }
            return m_files;
        }
    }
}
