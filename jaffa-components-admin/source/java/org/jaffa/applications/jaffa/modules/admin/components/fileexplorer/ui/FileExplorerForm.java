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
import java.io.File;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.exceptions.FileExplorerException;
import org.jaffa.presentation.portlet.widgets.controller.GridController;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
/** This is the FormBean for the FileExplorer.
 *
 * @author  PaulE
 * @version 1.0
 */
public class FileExplorerForm extends FormBase {

    private static Logger log = Logger.getLogger(FileExplorerForm.class);

    /** The constant which returns the struts-forward for displaying this form */
    public static final String NAME = "jaffa_admin_fileExplorer";

    /** Getter for property fileTree.
     * @return Value of property fileTree.
     */
    public String getPathName() {
        return ( (FileExplorerComponent) getComponent() ).getPathName();
    }


    /** Getter for property fileTree. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property fileTree.
     */
    public GridModel getFileTreeWM() {
        GridModel fileTreeModel = (GridModel) getWidgetCache().getModel("fileTree");
        if (fileTreeModel == null) {
            fileTreeModel = createTree();
            getWidgetCache().addModel("fileTree", fileTreeModel);
        }
        return fileTreeModel;
    }

    /** Setter for property fileTree. This is invoked by the servlet, when a post is done on the main screen.
     * @param value New value of property fileTree.
     */
    public void setFileTreeWV(String value) {
        GridController.updateModel(value, getFileTreeWM());
    }


    /** This method should be invoked to copy the fields from the FormBean to the component after successful validation.
     * @return true of the values are copied successfully
     */
    public boolean doValidate() throws FrameworkException , ApplicationExceptions{
        //@todo
        return true;
    }
    
    
    private static final String COL_DISPLAYED = "isDisplayed";
    private static final String COL_EXPANDED = "isExpanded";
    private static final String COL_PARENT = "isParent";
    private static final String COL_NAME = "name";
    private static final String COL_LEVEL = "level";
    private static final String COL_SELECTED = "selected";
    private static final String COL_LAST_MODIFIED = "lastModified";
    private static final String COL_SIZE = "size";
    private static final String COL_FILE = "file";
    private static final String COL_DOWNLOAD = "canDownload";
    
    
    
    private GridModel createTree() {
        int rootLevel=1;
        GridModel m = new GridModel();
        
        GridModelRow r1 = m.newRow();
        r1.put(COL_NAME,getPathName());
        r1.put(COL_LEVEL, new Integer(rootLevel));
        r1.put(COL_EXPANDED, new Boolean(true) );
        r1.put(COL_PARENT, new Boolean(true) ); 
        r1.put(COL_DISPLAYED, new Boolean(true) );   
       
        addToTree(m,null,1,rootLevel+1);

        return m;
    }

    private boolean addToTree(GridModel m, File path, int row, int level) {
        boolean any = false;
        try {
            FileExplorerBean fb = ( (FileExplorerComponent) getComponent() ).retrieveFiles(path);
            FileExplorerBean.FolderBean[] folders = fb.getFolders();
            if(folders!=null && folders.length >  0) 
                for(int i=0; i<folders.length;i++) {
                    any = true;
                    GridModelRow r = m.newRow(row++);
                    if(log.isDebugEnabled())
                        log.debug("Added Model Row : /" + folders[i].getName());
                    r.put(COL_NAME,folders[i].getName());
                    r.put(COL_LEVEL, new Integer(level));
                    r.put(COL_LAST_MODIFIED, folders[i].getLastModified());
                    r.put(COL_EXPANDED, new Boolean(false) );
                    r.put(COL_PARENT, new Boolean(true) );        
                    r.put(COL_DISPLAYED, new Boolean(true) );   
                    r.put(COL_FILE, folders[i].getFile());
                }

            FileExplorerBean.FileBean[] files = fb.getFiles();
            if(files!=null && files.length > 0) 
                for(int i=0; i<files.length;i++) {
                    any = true;
                    GridModelRow r = m.newRow(row++);
                    if(log.isDebugEnabled())
                        log.debug("Added Model Row : " + files[i].getName());
                    r.put(COL_NAME,files[i].getName());
                    r.put(COL_LEVEL, new Integer(level));
                    r.put(COL_LAST_MODIFIED, files[i].getLastModified());
                    r.put(COL_SIZE, files[i].getFormattedSize());
                    if(files[i].isDeleteAllowed())
                        r.put(COL_SELECTED, new CheckBoxModel());
                    r.put(COL_EXPANDED, new Boolean(false) );
                    r.put(COL_PARENT, new Boolean(false) );        
                    r.put(COL_DISPLAYED, new Boolean(true) );   
                    r.put(COL_FILE, files[i].getFile());
                    r.put(COL_DOWNLOAD, new Boolean(files[i].isAccessable()));
                }
        } catch (ApplicationExceptions e){
            log.error(null, e);
        } catch (FrameworkException e) {
            log.error(null, e);
        }
        return any;
    }
    
    
    public void expandRow(String rowId) {
        GridModel m = (GridModel) getWidgetCache().getModel("fileTree");
        GridModelRow row = m.getRowById(new Integer(rowId).intValue());      
        if ( row.get(COL_EXPANDED)!=null && ((Boolean)row.get(COL_EXPANDED)).booleanValue() ) {
            if(log.isDebugEnabled())
                log.debug("Row already expanded");
            return;
        }        
        row.addElement(COL_EXPANDED, new Boolean(true) );
        row.addElement(COL_DISPLAYED, new Boolean(true) );        
        m.setTarget(row);
        
        int nextRowNum = m.getRowNum(row) + 1;
        int level =  ( (Integer) row.get(COL_LEVEL) ).intValue() + 1;
        addToTree(m,(File)row.get(COL_FILE),nextRowNum, level);
    }

    /**
     * Holds value of property returnMessage.
     */
    private String returnMessage;

    /**
     * Getter for property returnMessage.
     * @return Value of property returnMessage.
     */
    public String getReturnMessage() {
        return this.returnMessage;
    }

    /**
     * Setter for property returnMessage.
     * @param returnMessage New value of property returnMessage.
     */
    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
