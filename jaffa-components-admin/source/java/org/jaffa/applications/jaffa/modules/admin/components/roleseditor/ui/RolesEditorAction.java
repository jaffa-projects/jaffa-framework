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

package org.jaffa.applications.jaffa.modules.admin.components.roleseditor.ui;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.applications.jaffa.modules.admin.components.checkpolicy.ui.CheckPolicyComponent;
import org.jaffa.applications.jaffa.modules.admin.components.checkpolicy.ui.CheckPolicyForm;

/** This class has event handlers for the RolesEditor.
 *
 * @author  Maheshd
 * @version 1.0
 */
public class RolesEditorAction extends ActionBase {

    private static Logger log = Logger.getLogger(RolesEditorAction.class);

    /** Event Handler for the 'Save' clicked event.
     * This will copy the values from the Form to the Component and then invoke the performSave() method on the component.
     * Finally it'll get the latest file contents of the roles.xml file.
     * @return the FormKey for this screen.
     */
    public FormKey do_Save_Clicked() {
        FormKey fk = null;
        RolesEditorForm myForm = (RolesEditorForm) form;
        RolesEditorComponent myComp = (RolesEditorComponent) component;
        try {
            // Copy the values from Form to Component
            myForm.doValidate();
            // save the file contents
            myComp.performSave(request);
            // Get the latest values
            fk = do_Refresh_Clicked();

        } catch (ApplicationExceptions e){
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        if (fk == null)
            fk = myComp.getRolesEditorFormKey();
        return fk;
    }

    /** This will re-render the screen with the original values.
     * @return the FormKey for this screen.
     */
    public FormKey do_Refresh_Clicked() {
        RolesEditorForm myForm = (RolesEditorForm) form;
        RolesEditorComponent myComp = (RolesEditorComponent) component;
        try {
            myComp.retrieveFileContents();
        }catch (ApplicationExceptions e){
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        }catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        return myComp.getRolesEditorFormKey();
    }

    /** This will re-render the screen with the original values.
     * @return the FormKey for this screen.
     */
    public FormKey do_CheckPolicy_Clicked() {
        FormKey fk = null;
        RolesEditorForm myForm = (RolesEditorForm) form;
        RolesEditorComponent myComp = (RolesEditorComponent) component;
        try {
            fk = myComp.runCheckPolicy();
        }catch (ApplicationExceptions e){
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        }catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        if (fk == null)
            fk = myComp.getRolesEditorFormKey();
        return fk;
    }

    /** This closes the component and returns the FormKey for the calling screen.
     * @return the FormKey for the calling screen.
     */
    public FormKey do_Close_Clicked() {
        return component.quitAndReturnToCallingScreen();
    }

}
