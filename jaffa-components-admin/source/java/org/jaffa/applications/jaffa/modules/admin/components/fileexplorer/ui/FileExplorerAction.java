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
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;
import org.jaffa.util.MessageHelper;

/** This class has event handlers for the FileExplorer.
 *
 * @author  PaulE
 * @version 1.0
 */
public class FileExplorerAction extends ActionBase {
    
    private static Logger log = Logger.getLogger(FileExplorerAction.class);
    
    /** Event Handler for the 'Save' clicked event.
     * @return the FormKey for this screen.
     */
    public FormKey do_Delete_Clicked() {
        FileExplorerForm myForm = (FileExplorerForm) form;
        FileExplorerComponent myComp = (FileExplorerComponent) component;
        
        // Delete Selected Files
        myForm.setReturnMessage( performDelete(myForm.getFileTreeWM()) );
        
        return myComp.getCurrentFormKey();
    }
    
    /** This will re-render the screen with the original values.
     * @return the FormKey for this screen.
     */
    public FormKey do_Refresh_Clicked() {
        FileExplorerForm myForm = (FileExplorerForm) form;
        FileExplorerComponent myComp = (FileExplorerComponent) component;
        
        // Clear tree widget so it is rebuild on re-render of the screen
        myForm.getWidgetCache().clear();
        return myComp.getCurrentFormKey();
    }
    
    /** This closes the component and returns the FormKey for the calling screen.
     * @return the FormKey for the calling screen.
     */
    public FormKey do_Close_Clicked() {
        return component.quitAndReturnToCallingScreen();
    }
    
    
    public FormKey do_FileTree_GetChildren(String rowId) {
        if (log.isDebugEnabled())
            log.debug("Executing the method 'do_FileTree_Clicked'"
                    + " with rowNum=" + rowId);
        FileExplorerForm myForm = (FileExplorerForm) form;
        FileExplorerComponent myComp = (FileExplorerComponent) component;
        myForm.expandRow(rowId);
        // just return to the same screen
        return myComp.getCurrentFormKey();
    }
    
    public FormKey do_FileTree_Download_Clicked(String rowId) {
        FileExplorerForm myForm = (FileExplorerForm) form;
        FileExplorerComponent myComp = (FileExplorerComponent) component;
        GridModel m = (GridModel) myForm.getWidgetCache().getModel("fileTree");
        GridModelRow row = m.getRowById(new Integer(rowId).intValue());
        File f = (File) row.get("file");
        if (f == null || !f.exists()) {
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "message.Jaffa.Admin.FileExplorer.fileNotFound");
            return myComp.getCurrentFormKey();
        } else {
            request.setAttribute("org.jaffa.applications.jaffa.modules.admin.components.fileexplorer", f);
            return new FormKey("jaffa_admin_fileExplorer_download",null);
        }
    }
    
    
    
    /** This will delete all selected files
     * @throws ApplicationExceptions if any error occurs while writing the file.
     * @throws FrameworkException if any error occurs.
     */
    protected String performDelete(GridModel model) {
        int deletes = 0;
        int deleted = 0;
        for(Iterator it = model.getRows().iterator(); it.hasNext();) {
            GridModelRow row = (GridModelRow) it.next();
            SimpleWidgetModel value = (SimpleWidgetModel) row.get("selected");
            if(value!=null && Boolean.TRUE.equals(value.getBooleanValue())) {
                // Delete this file
                deletes++;
                File f = (File) row.get("file");
                try {
                    if(f==null||!f.exists())
                        row.put("error", MessageHelper.findMessage("message.Jaffa.Admin.FileExplorer.fileNotFound", null));
                    else {
                        if(f.delete()) {
                            // Delete OK, remove row
                            it.remove();
                            deleted++;
                        } else {
                            // Did not delete
                            row.put("error", MessageHelper.findMessage("message.Jaffa.Admin.FileExplorer.fileNotDeleted", null));
                        }
                    }
                } catch(Exception e) {
                    log.error("Delete failed on " + (f==null?"NULL":f.getAbsolutePath()),e);
                    row.put("error", MessageHelper.findMessage("message.Jaffa.Admin.FileExplorer.deleteError", new Object[] {e.getLocalizedMessage()}));
                }
            }
        }
        String token = "message.Jaffa.Admin.FileExplorer.";
        if(deletes==0)
            token+="noFilesSelected";
        else if(deletes==deleted)
            token+="deleteOK";
        else
            token+="deleteErrors";
        
        return MessageHelper.findMessage(token,new String[] {""+deletes,""+deleted});
    }
    
}
