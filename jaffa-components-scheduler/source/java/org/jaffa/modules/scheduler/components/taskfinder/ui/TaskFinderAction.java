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

package org.jaffa.modules.scheduler.components.taskfinder.ui;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.HistoryNav;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;


/** The Action class for handling events related to TaskFinder.
 */
public class TaskFinderAction extends ActionBase {
    
    private static final Logger log = Logger.getLogger(TaskFinderAction.class);
    
    /** Determines the currentFormKey
     * This event is invoked when a user changes the settings of the UserGrid in the Results screen.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_refresh() {
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        return myComp.getResultsFormKey();
    }
    
    /** Quits the component and returns the FormKey for the calling screen.
     * @return The FormKey for the caling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey do_Close_Clicked() {
        return ((FormBase) form).getComponent().quitAndReturnToCallingScreen();
    }
    
    /** Re-executes the search, using the same criteria as used before. It invokes the displayResults() method on the component.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_Refresh_Clicked() {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        try {
            fk = myComp.displayResults();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Refresh Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    /** Invokes the Maintenance component.
     * @return The FormKey for the Maintenance screen.
     */
    public FormKey do_CreateFromResults_Clicked() {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        try {
            fk = myComp.createFromResults();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Refresh Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    /** Starts the scheduler and refreshes the screen.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_StartScheduler_Clicked() {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        try {
            myComp.startScheduler();
            fk = myComp.displayResults();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("StartScheduler Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    /** Stops the scheduler and refreshes the screen.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_StopScheduler_Clicked() {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        try {
            myComp.stopScheduler();
            fk = myComp.displayResults();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("StopScheduler Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    /** Pauses the scheduler and refreshes the screen.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_PauseScheduler_Clicked() {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        try {
            myComp.pauseScheduler();
            fk = myComp.displayResults();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("PauseScheduler Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    /** Clears the event log and refreshes the screen.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_ClearEventLog_Clicked() {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        try {
            myComp.clearEventLogs();
            fk = myComp.displayResults();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("PauseScheduler Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    /** Invokes the do_Rows_View_Clicked() method.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the View screen.
     */
    public FormKey do_Rows_Clicked(String rowNum) {
        return do_Rows_View_Clicked(rowNum);
    }
    
    /** Invokes the viewObject() method on the component.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the View screen.
     */
    public FormKey do_Rows_View_Clicked(String rowNum) {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        GridModel model = (GridModel) myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                fk = myComp.viewObject((java.lang.String) selectedRow.get("scheduledTaskId"));
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Viewer Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        
        // The Viewer will be rendered in a new window
        // We don't want to see the existing HistoryNav in that window
        // Hence, initialize the HistoryNav
        HistoryNav.initializeHistoryNav(request);
        
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    /** Invokes the updateObject() method on the component.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the Update screen.
     */
    public FormKey do_Rows_Update_Clicked(String rowNum) {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        GridModel model = (GridModel) myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                fk = myComp.updateObject((java.lang.String) selectedRow.get("scheduledTaskId"));
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Update Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    /** Invokes the deleteObject() method on the component.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the Update screen.
     */
    public FormKey do_Rows_Delete_Clicked(String rowNum) {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        GridModel model = (GridModel) myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                fk = myComp.deleteObject((java.lang.String) selectedRow.get("scheduledTaskId"));
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Update Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    /** Invokes the activateTask() method on the component.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the Update screen.
     */
    public FormKey do_Rows_Activate_Clicked(String rowNum) {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        GridModel model = (GridModel) myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                myComp.activateTask((java.lang.String) selectedRow.get("scheduledTaskId"));
                fk = myComp.displayResults();
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Activate Task Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    /** Invokes the inactivateTask() method on the component.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the Update screen.
     */
    public FormKey do_Rows_Inactivate_Clicked(String rowNum) {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        GridModel model = (GridModel) myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                myComp.inactivateTask((java.lang.String) selectedRow.get("scheduledTaskId"));
                fk = myComp.displayResults();
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Inactivate Task Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
    /** Invokes the viewFailedTasks() method on the component.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the QueueViewer screen.
     */
    public FormKey do_Rows_ViewFailedTasks_Clicked(String rowNum) {
        FormKey fk = null;
        TaskFinderForm myForm = (TaskFinderForm) form;
        TaskFinderComponent myComp = (TaskFinderComponent) myForm.getComponent();
        GridModel model = (GridModel) myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));
        if (selectedRow != null) {
            try {
                fk = myComp.viewFailedTasks((java.lang.String) selectedRow.get("scheduledTaskId"), selectedRow.get("businessObject"));
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("QueueViewer Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        
        // The Viewer will be rendered in a new window
        // We don't want to see the existing HistoryNav in that window
        // Hence, initialize the HistoryNav
        HistoryNav.initializeHistoryNav(request);
        
        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }
    
}
