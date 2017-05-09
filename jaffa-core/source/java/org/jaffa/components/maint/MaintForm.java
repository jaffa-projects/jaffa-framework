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

package org.jaffa.components.maint;

import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import javax.servlet.http.HttpServletRequest;

/** This is the base class for all Maintenance Component FormBeans.
 *
 * @author GautamJ
 */
public class MaintForm extends FormBase {
    
    private static final Logger log = Logger.getLogger(MaintForm.class);
    
    /** Constant for the parameter currentScreenCounter.*/
    public static final String PARAMETER_CURRENT_SCREEN_COUNTER = "currentScreenCounter";
    
    /** Is this update mode, if so the key is display only, else this is create mode and the key is editable.
     * @return true if record is being updated, false if created.
     */
    public boolean isUpdateMode() {
        return ((MaintComponent2) getComponent()).isUpdateMode();
    }
    
    /** Getter for property currentScreenCounter.
     * @return Value of property currentScreenCounter.
     */
    public int getCurrentScreenCounter() {
        return ((MaintComponent2) getComponent()).getCurrentScreenCounter();
    }

    /** Setter for property currentScreenCounter.
     * @param currentScreenCounter New value of property currentScreenCounter.
     */
    public void setCurrentScreenCounter(int currentScreenCounter) {
        ((MaintComponent2) getComponent()).setCurrentScreenCounter(currentScreenCounter);
    }
    
    /** Returns a true if a field has been marked as 'DisplayOnly'.
     * @param fieldName The field to be checked.
     * @return a true if a field has been marked as 'DisplayOnly'
     */    
    public boolean isDisplayOnlyField(String fieldName) {
        return ((MaintComponent2) getComponent()).isDisplayOnlyField(fieldName);
    }
    
    /** Returns true if Clear is allowed in the current screen.
     * @return true if Clear is allowed in the current screen.
     */
    public boolean isClearActionAvailable() {
        return ((MaintComponent2) getComponent()).isCreateMode();
    }
    
    /** Returns true if Save is allowed in the current screen.
     * Save action is not allowed in the following cases
     *   1- If refreshData is set to true
     *   2- If the SaveAction is not allowed in a screen in create-mode
     * @return true if Save is allowed in the current screen.
     */
    public boolean isSaveActionAvailable() {
        MaintComponent2 comp = (MaintComponent2) getComponent();
        return comp.isRefreshData() || (comp.isCreateMode() && !comp.determineCurrentScreen().isSaveActionAvailableInCreateMode()) ? false : true;
    }
    
    /** Returns true if Finish is allowed in the current screen.
     * The base implementation returns the same value as the 
     * <code>isSaveActionAvailable()</code> method. It is provided so that
     * a component can override this and impact how the.
     * It may be goot to have a Finish, but not have a save button on a
     * create screen if
     *   1- all fields are not editable once the new record is saved
     *   2- the key field is generated by the database, and hence we can't
     *      re-retrieve the new record
     * @return true if Finish is allowed in the current screen.
     */
    public boolean isFinishActionAvailable() {
        MaintComponent2 comp = (MaintComponent2) getComponent();
        return comp.isRefreshData() || (comp.isCreateMode() && !comp.determineCurrentScreen().isSaveActionAvailableInCreateMode()) ? false : true;
    }

    /** Returns true if Delete is allowed in the current screen.
     * Delete action is not allowed if refreshData is set to true. It is only allowed in the update mode.
     * @return true if Delete is allowed in the current screen.
     */
    public boolean isDeleteActionAvailable() {
        MaintComponent2 comp = (MaintComponent2) getComponent();
        return comp.isUpdateMode() && !comp.isRefreshData() ? true : false;
    }
    
    /** Returns true if Refresh is allowed in the current screen.
     * Refresh action is only allowed in the update mode.
     * @return true if Refresh is allowed in the current screen.
     */
    public boolean isRefreshActionAvailable() {
        MaintComponent2 comp = (MaintComponent2) getComponent();
        return comp.isUpdateMode() ? true : false;
    }
    
    /** Returns true if there is a Next screen after the current screen.
     * @return true if there is a Next screen after the current screen.
     */
    public boolean isNextActionAvailable() {
        return ((MaintComponent2) getComponent()).determineNextScreen() != null ? true : false;
    }
    
    /** Returns true if there is a Previous screen before the current screen.
     * @return true if there is a Previous screen before the current screen.
     */
    public boolean isPreviousActionAvailable() {
        return ((MaintComponent2) getComponent()).determinePreviousScreen() != null ? true : false;
    }
    
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully.
     */
    public boolean doValidate(HttpServletRequest request) {
        return true;
    }
    
}
