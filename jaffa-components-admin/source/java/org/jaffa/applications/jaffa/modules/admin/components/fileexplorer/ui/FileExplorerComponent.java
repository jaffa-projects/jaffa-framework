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

package org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.exceptions.FileExplorerException;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.StringHelper;
/** This is the component controller for the FileExplorer Editor.
 *
 * @author  PaulE
 * @version 1.0
 */
public class FileExplorerComponent extends Component {

    private static Logger log = Logger.getLogger(FileExplorerComponent.class);

    // Properties for the Explorer that can be initialized via AOP, or a super-class
    protected String m_docRoot = null;
    protected String m_pathName = null;
    protected boolean m_canCreateFolders = false;
    protected boolean m_canDeleteFiles = false;
    protected boolean m_canDeleteFolders = false;
    protected boolean m_canDownloadFiles = false;
    protected boolean m_canRecurseFolders = false;
    protected boolean m_canRenameFiles = false;
    protected boolean m_canRenameFolders = false;
    protected boolean m_canShowFolders = false;
    protected boolean m_canUploadFiles = false;
    protected String[] m_deleteAllowedRoles = null;
    protected String[] m_deleteDisallowedRoles = null;
    protected String[] m_downloadAllowedRoles = null;
    protected String[] m_downloadDisallowedRoles = null;
    protected String[] m_fileExcludes = null;
    protected String[] m_fileIncludes = null;
    protected String[] m_folderExcludes = null;
    protected String[] m_folderIncludes = null;
    protected String[] m_renameAllowedRoles = null;
    protected String[] m_renameDisallowedRoles = null;
    protected String[] m_uploadAllowedRoles = null;
    protected String[] m_uploadDisallowedRoles = null;

    // Internal State variables
    private File m_root = null;
    private FormKey m_currentFormKey = null;
    
    
    public String getPathName() {
        return m_pathName;
    }

    public void setPathName(String pathName) {
        m_pathName = pathName;
    }

    public FormKey getCurrentFormKey() {
        if(m_currentFormKey==null) 
            m_currentFormKey = new FormKey(FileExplorerForm.NAME, getComponentId());
        return m_currentFormKey;
    }
    /** This will retrieve the file contents of the log4j xml file  and return the FormKey for rendering the component.
     * @return the FormKey for rendering the component.
     * @throws FrameworkException if any error ocurrs in obtaining the contents of the log4j xml file.
     */
    public FormKey display() throws FrameworkException,ApplicationExceptions {
        if(m_docRoot==null) {
            m_docRoot=System.getProperty("user.home");
            m_pathName="Home";
        }
        if(m_docRoot!=null && m_root==null)
            m_root=new File(m_docRoot);
        if(m_docRoot==null || m_root==null || !m_root.exists())
            throw new ApplicationExceptions(new FileExplorerException(FileExplorerException.INVALID_ROOT, m_root));
        if(!m_root.isDirectory())
            throw new ApplicationExceptions( new FileExplorerException(FileExplorerException.ROOT_NOT_FOLDER, m_root));
        return getCurrentFormKey();
    }

    /** This retrieves the files for the base directory
     * @throws ApplicationExceptions if any error occurs while reading the files.
     * @throws FrameworkException if any error occurs.
     */
    protected FileExplorerBean retrieveFiles(File path) throws FrameworkException , ApplicationExceptions {
        String relPath = null; // Path relative to the document root folder
        if(path==null) {
            path=m_root;
            relPath="";
        } else {
            if(!path.getAbsolutePath().startsWith( m_root.getAbsolutePath() ) )
                throw new ApplicationExceptions(
                      new FileExplorerException( FileExplorerException.ILLEGAL_ACCESS, path.getAbsolutePath() ) );
            else
                relPath = path.getAbsolutePath().substring(m_root.getAbsolutePath().length()+1);
        }
        if(log.isDebugEnabled())
            log.debug("Get List of Folders/Files For " + relPath + "["+path.getAbsolutePath()+"]");
        
        FileExplorerBean bean = new FileExplorerBean();
        //@todo - implement uploads
        //bean.setUploadAllowed(canUpload(relPath,path));

        // Add in sub-folders
        File[] dirs = path.listFiles( new FolderFilter(m_root) );
        if(dirs!=null)
            for(int i = 0; i<dirs.length; i++) {
                String name = relPath + "/" + dirs[i].getName();
                if(log.isDebugEnabled())
                    log.debug(i + ") Found Folder " + name + " -> " + dirs[i].getAbsolutePath());
                bean.addFolder(dirs[i], canDownload(name,dirs[i]),
                                        false /* @todo canRename(name,dirs[i])*/,
                                        canDelete(name,dirs[i]));
            }

        // Add in files
        File[] files = path.listFiles( new FileFilter(m_root) );
        if(files!=null)
            for(int i = 0; i<files.length; i++) {
                String name = relPath + "/" + files[i].getName();
                if(log.isDebugEnabled())
                    log.debug(i + ") Found File " + name + " -> " + files[i].getAbsolutePath());
                bean.addFile(files[i], canDownload(name,files[i]),
                                       false /* @todo canRename(name,files[i]) */,
                                       canDelete(name,files[i]));
            }

        return bean;
    }

    
    /**
     * Can this file be deleted
     * @param relName
     * @param f
     * @return boolean
     */
    public boolean canDelete(String relName, File f) {
        boolean allowed = false;
        String[] includes,excludes;
        if( f.isDirectory() ) {
            allowed = m_canDeleteFolders;
            includes = m_folderIncludes;
            excludes = m_folderExcludes;
        } else {
            allowed = m_canDeleteFiles;
            includes = m_fileIncludes;
            excludes = m_fileExcludes;
        }
        if(allowed) {
            allowed = inRange(relName, includes, excludes);
            if(allowed) {
                allowed = inRole(m_deleteAllowedRoles, m_deleteDisallowedRoles);
                if(allowed) {
                    if(log.isDebugEnabled())
                        log.debug("canDelete("+relName+")=YES");
                    return true;
                } else
                    if(log.isDebugEnabled())
                        log.debug("canDelete("+relName+")=NO - not in role. Includes="+StringHelper.printArray(m_deleteAllowedRoles)+", Excludes="+StringHelper.printArray(m_deleteDisallowedRoles));
                
            } else
                if(log.isDebugEnabled())
                    log.debug("canDelete("+relName+")=NO - not in range. Includes="+StringHelper.printArray(includes)+", Excludes="+StringHelper.printArray(excludes));
        } else
            if(log.isDebugEnabled())
                log.debug("canDelete("+relName+")=NO - not enabled");
        return false;
    }

    /**
     * Can this file be downloaded, or traversed if a folder
     * @param relName
     * @param f
     * @return boolean
     */
    public boolean canDownload(String relName, File f) {
        boolean allowed = false;
        String[] includes,excludes;
        if( f.isDirectory() ) {
            allowed = m_canRecurseFolders;
            includes = m_folderIncludes;
            excludes = m_folderExcludes;
        } else {
            allowed = m_canDownloadFiles;
            includes = m_fileIncludes;
            excludes = m_fileExcludes;
        }
        if(allowed) {
            allowed = inRange(relName, includes, excludes);
            if(allowed) {
//                allowed = inRole(m_downloadAllowedRoles, m_downloadDisallowedRoles);
//                if(allowed) {
                    if(log.isDebugEnabled())
                        log.debug("canDownload("+relName+")=YES");
                    return true;
//                } else
//                    if(log.isDebugEnabled())
//                        log.debug("canDownload("+relName+")=NO - not in role. Includes="+StringHelper.printArray(m_downloadAllowedRoles)+", Excludes="+StringHelper.printArray(m_downloadDisallowedRoles));
            } else
                if(log.isDebugEnabled())
                    log.debug("canDownload("+relName+")=NO - not in range. Includes="+StringHelper.printArray(includes)+", Excludes="+StringHelper.printArray(excludes));
        } else
            if(log.isDebugEnabled())
                log.debug("canDownload("+relName+")=NO - not enabled");
        return false;
    }

    /**
     * Can this file be renamed
     * @param relName
     * @param f
     * @return
     * @TODO Implement this rename feature
     */
//    public boolean canRename(String relName, File f) {
//        return (f.isDirectory()? m_canRenameFolders && inRange(relName,m_folderIncludes,m_folderExcludes)
//        : m_canRenameFiles && inRange(relName, m_fileIncludes,m_fileExcludes) )
//        && inRole(m_renameAllowedRoles, m_renameDisallowedRoles);
//    }

    /**
     * Can this folder be uploaded to
     * @param relName
     * @param f
     * @return
     * @TODO Implement this upload feature
     */
//    public boolean canUpload(String relName, File f) {
//        return (f.isDirectory()? m_canUploadFiles && inRole(m_uploadAllowedRoles, m_uploadDisallowedRoles)
//        : false);
//    }

    /**
     * Does file name match the include/exclude clause
     * @param name
     * @param includes
     * @param excludes
     * @return boolean
     */
    public static boolean inRange(String name, String[] includes, String[] excludes) {
        if(name==null)
            return false;
        if(includes!=null && includes.length>0)
            for(int i=0;i<includes.length;i++)
                if(name.matches(includes[i])) {
                    if(log.isDebugEnabled())
                        log.debug("inRange()=YES, " + name + " matched ["+i+"] " + includes[i]);
                    return true;
                }
        if(excludes!=null && excludes.length>0)
            for(int i=0;i<excludes.length;i++)
                if(name.matches(excludes[i])) {
                    if(log.isDebugEnabled())
                        log.debug("inRange()=NO, " + name + " matched ["+i+"] " + excludes[i]);
                    return false;
                }
        // Include if not explicitly included
        if(includes==null || includes.length==0) {
            if(log.isDebugEnabled())
                log.debug("inRange()=YES, " + name + " by default");
            return true;
        } else {
            if(log.isDebugEnabled())
                log.debug("inRange()=NO, " + name + " by default");
            return false;
        }
            
    }

    /**
     * Does the users current access name match the include/exclude role clause
     * @param includes
     * @param excludes
     * @return boolean
     */
    public static boolean inRole(String[] includes, String[] excludes) {
        List l = SecurityManager.getUserRoles();
        if(l==null) return false;
        for(Iterator it=l.iterator(); it.hasNext();) {
            String role = (String)it.next();
            if(includes!=null && includes.length>0)
                for(int i=0;i<includes.length;i++)
                    if(role.matches(includes[i])) 
                        return true;
        }
        for(Iterator it=l.iterator(); it.hasNext();) {
            String role = (String)it.next();
            if(excludes!=null && excludes.length>0)
                for(int i=0;i<excludes.length;i++)
                    if(role.matches(excludes[i]))
                        return false;
        }
        // Include if not explicitly included or excluded
        return true;
    }

    /**
     * Convert comma seperated 'ant like' filter list, and make it a list of regular expressions
     * <ul>
     * <li>replace * with [^/\\]*
     * <li>replace ** with .*
     * <li>replace . with \.
     * </ul>
     * @param list of 'ant style' regex expressions
     * @return array of java compatible regex expressions
     */
    public static String[] parseRegex(String list) {
        String[] x = null;
        if(list!=null && list.length() > 0) {
            x = StringHelper.parseString(list,",");
            if(x!=null && x.length>0)
                for(int i=0;i<x.length;i++) {
                    // replace * with [^/\\]*
                    // replace ** with .*
                    // replace . with \.
                    x[i] = StringHelper.replace(x[i], ".", "\0x01");
                    x[i] = StringHelper.replace(x[i], "**", "\0x02");
                    x[i] = StringHelper.replace(x[i], "*", "[^/\\\\]*");
                    x[i] = StringHelper.replace(x[i], "\0x01", "\\.");
                    x[i] = StringHelper.replace(x[i], "\0x02", ".*");
                }
        }
        return x;
    }
    
    
     /** class used to filter for directories */
    class FolderFilter implements FilenameFilter {
        private int rootLength = 0;
        public FolderFilter(File root) {
            if(root!=null)
                rootLength = root.getAbsolutePath().length()+1;
            if(log.isDebugEnabled())
                log.debug("Created FolderFilter: Root=" + root + ", Includes=" + StringHelper.printArray(m_folderIncludes) + ", Excludes=" + StringHelper.printArray(m_folderExcludes) );
        }
        /** Tests if a specified file should be included in a file list.
         *
         * @param dir the directory in which the file was found
         * @param name the name of the file
         * @return <CODE>true</CODE> if and only if the name should be included in the file list; <CODE>false</CODE> otherwise
         */
        public boolean accept(File dir, String name) {
            File f = new File(dir,name);
            if(!f.isDirectory()) return false;
            String relName = f.getPath().substring(rootLength);
            boolean accept = m_canShowFolders && inRange(relName,m_folderIncludes,m_folderExcludes);
            if(log.isDebugEnabled())
                log.debug("Folder " + name + " included? " + accept);
            return accept;
        }
    }

    /** class used to filter for images */
    class FileFilter implements FilenameFilter {
        private int rootLength = 0;
        public FileFilter(File root) {
            if(root!=null)
                rootLength = root.getAbsolutePath().length()+1;
            if(log.isDebugEnabled())
                log.debug("Created FileFilter: Root=" + root + ", Includes=" + StringHelper.printArray(m_fileIncludes) + ", Excludes=" + StringHelper.printArray(m_fileExcludes) );
        }
        /** Tests if a specified file should be included in a file list.
         *
         * @param dir the directory in which the file was found
         * @param name the name of the file
         * @return <CODE>true</CODE> if and only if the name should be included in the file list; <CODE>false</CODE> otherwise
         */
        public boolean accept(File dir, String name) {
            File f = new File(dir,name);
            if(f.isDirectory()) return false;
            String relName = f.getPath().substring(rootLength);
            boolean accept = inRange(relName, m_fileIncludes,m_fileExcludes);
            if(log.isDebugEnabled())
                log.debug("File " + name + " included? " + accept);
            return accept;
        }
    }
   
}