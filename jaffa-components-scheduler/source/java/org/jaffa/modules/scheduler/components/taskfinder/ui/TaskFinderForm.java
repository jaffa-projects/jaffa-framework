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

import org.jaffa.modules.scheduler.components.taskfinder.dto.TaskFinderOutDto;
import org.jaffa.modules.scheduler.components.taskfinder.dto.TaskFinderOutRowDto;
import org.jaffa.modules.scheduler.services.SchedulerHelper;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.controller.GridController;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;


/** The FormBean class to support TaskFinder.
 */
public class TaskFinderForm extends FormBase {
    
    /** Getter for property schedulerStatus.
     * @return Value of property schedulerStatus.
     */
    public SchedulerHelper.SchedulerStatusEnumeration getSchedulerStatus(){
        return ((TaskFinderComponent) getComponent()).getFinderOutDto().getSchedulerStatus();
    }
    
    /** Getter for property rows. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * This gets the current data from the component.
     * @return Value of property userRef1.
     */
    public GridModel getRowsWM() {
        GridModel rows = (GridModel) getWidgetCache().getModel("rows");
        if (rows == null) {
            rows = new GridModel();
            getWidgetCache().addModel("rows", rows);
            populateRows(rows);
        }
        return rows;
    }
    
    /** Setter for property rows. This is invoked by the servlet, when a post is done on the Results screen.
     * It sets the selected rows on the model.
     * @param value New value of property userRef1.
     */
    public void setRowsWV(String value) {
        GridController.updateModel(value, getRowsWM());
    }
    
    
    /** This will populate the input GridModel with the data in the finderOutDto of the Component.
     * @param rows The GridModel object to populate.
     */
    public void populateRows(GridModel rows) {
        rows.clearRows();
        TaskFinderOutDto finderOutDto = ((TaskFinderComponent) getComponent()).getFinderOutDto();
        if (finderOutDto != null && finderOutDto.getRows() != null) {
            for (TaskFinderOutRowDto rowDto : finderOutDto.getRows()) {
                GridModelRow row = rows.newRow();
                row.addElement("scheduledTaskId", rowDto.getScheduledTaskId());
                row.addElement("taskType", rowDto.getTaskType());
                row.addElement("description", rowDto.getDescription());
                row.addElement("runAs", rowDto.getRunAs());
                row.addElement("startOn", rowDto.getStartOn());
                row.addElement("endOn", rowDto.getEndOn());
                row.addElement("recurrence", rowDto.getRecurrence());
                row.addElement("misfireRecovery", rowDto.getMisfireRecovery());
                row.addElement("businessObject", rowDto.getBusinessObject());
                row.addElement("createdOn", rowDto.getCreatedOn());
                row.addElement("createdBy", rowDto.getCreatedBy());
                row.addElement("lastChangedOn", rowDto.getLastChangedOn());
                row.addElement("lastChangedBy", rowDto.getLastChangedBy());
                row.addElement("nextDue", rowDto.getNextDue());
                row.addElement("lastRunOn", rowDto.getLastRunOn());
                row.addElement("status", rowDto.getStatus());
                row.addElement("lastError", rowDto.getLastError());
                row.addElement("failedTaskCount", rowDto.getFailedTaskCount());
                row.addElement("hasAdminTaskAccess", rowDto.getHasAdminTaskAccess());
            }
        }
    }
    
}
