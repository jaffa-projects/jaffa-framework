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

package org.jaffa.components.lookup;

import java.lang.reflect.Method;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.components.finder.FinderAction;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.exceptions.ApplicationExceptions;


/** This is the base class for all LookupComponent Actions. It extends the FinderAction.
 * @author  GautamJ
 */
public class LookupAction extends FinderAction {

    private static final Logger log = Logger.getLogger(LookupAction.class);


    /** Quits the component and closes the browser window.
     * This method overrides the 'Close' handler of the base class.
     * @return The FormKey object for the generic lookup jsp.
     */
    public FormKey do_Close_Clicked() {
        LookupForm myForm = (LookupForm) form;
        LookupComponent2 myComp = (LookupComponent2) myForm.getComponent();
        return myComp.quitLookup(request);
    }

    /** Invokes the do_Rows_Select_Clicked() method.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the generic lookup jsp.
     */
    public FormKey do_Rows_Clicked(String rowNum) {
        return do_Rows_Select_Clicked(rowNum);
    }

    /** This will add the 'lookup' attribute on the request stream, with a Map containing the fieldnames (from the targetFields property) and values (from the selectedRow).
     * It will then invoke the quit() method on the component.
     * Finally it will return a FormKey object for the generic lookup jsp.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey for the generic lookup jsp.
     */
    public FormKey do_Rows_Select_Clicked(String rowNum) {
        FormKey fk = null;
        LookupForm myForm = (LookupForm) form;
        LookupComponent2 myComp = (LookupComponent2) myForm.getComponent();

        GridModel model = myForm.getRowsWM();
        GridModelRow selectedRow = model.getRow(Integer.parseInt(rowNum));

        if (myComp.isInSelectLookupMode()) {
            try {
                Object finderOutDto = myComp.getFinderOutDto();
                Class finderOutDtoClass = finderOutDto.getClass();
                Method method = finderOutDtoClass.getMethod("getRows", new Class[] {Integer.TYPE});
                Object selectedRowDto = method.invoke(finderOutDto, new Object[] {new Integer(selectedRow.getRowId())});
                SelectLookupEvent event = new SelectLookupEvent(myComp, selectedRowDto);
                fk = myComp.performSelectLookup(request, event);
            } catch (ApplicationExceptions e){
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
                // This could only be caused by the listeners throwing and error, so we want to quit the component and return
                fk = myComp.quitLookup(request);
            } catch (Exception e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        } else
            fk = myComp.performLookup(request, selectedRow);
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }

    /** This will generate the MultiSelectLookupEvent object comprising the DTOs for the selected rows.
     * It will then invoke the performMultiSelectLookup() method on the component.
     * @return The FormKey for the calling screen.
     */
    public FormKey do_MultiSelect_Clicked() {
        FormKey fk = null;
        LookupForm myForm = (LookupForm) form;
        LookupComponent2 myComp = (LookupComponent2) myForm.getComponent();

        try {
            // Create a collection of 'selectedRows'
            // It will assume that each row in the GridModel in the form has a CheckBoxModel keyed by LookupComponent2.MULTI_SELECT_CHECKBOX
            // Additionally, it'll assume that the FinderOutDto returned by the component has the method: 'public SomeDtoClass getRows(int i)'
            Collection selectedRows = new LinkedList();
            GridModel model = myForm.getRowsWM();
            Object finderOutDto = myComp.getFinderOutDto();
            if (finderOutDto != null && model != null && model.getRows() != null) {
                Class finderOutDtoClass = finderOutDto.getClass();
                Method method = finderOutDtoClass.getMethod("getRows", new Class[] {Integer.TYPE});
                for (Iterator itr = model.getRows().iterator(); itr.hasNext(); ) {
                    GridModelRow row = (GridModelRow) itr.next();
                    CheckBoxModel checkBoxModel = (CheckBoxModel) row.get(LookupComponent2.MULTI_SELECT_CHECKBOX);
                    if (checkBoxModel != null && checkBoxModel.getState()) {
                        Object selectedRow = method.invoke(finderOutDto, new Object[] {new Integer(row.getRowId())});
                        selectedRows.add(selectedRow);
                    }
                }
            }
            MultiSelectLookupEvent event = new MultiSelectLookupEvent(myComp, selectedRows.toArray());
            fk = myComp.performMultiSelectLookup(request, event);
        } catch (ApplicationExceptions e){
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (Exception e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }

}
