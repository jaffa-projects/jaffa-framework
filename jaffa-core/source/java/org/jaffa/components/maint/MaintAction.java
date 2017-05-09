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
import java.lang.reflect.Method;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.presentation.portlet.EventHandlerMissingRuntimeException;
import org.jaffa.presentation.portlet.HistoryNav;
/** This is the base class for all Maintenance Component Actions.
 * @author  GautamJ
 */
public class MaintAction extends ActionBase {
    
    private static final Logger log = Logger.getLogger(MaintAction.class);
    
    /** Quits the component and returns the FormKey for the calling screen.
     * @return The FormKey for the calling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey do_Cancel_Clicked() {
        return ((MaintForm) form).getComponent().quitAndReturnToCallingScreen();
    }
    
    /** Invokes the create() or update() method on the component, depending on the mode. It then quits the component.
     * @return The FormKey for the calling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey do_Finish_Clicked() {
        MaintForm myForm = (MaintForm) form;
        MaintComponent2 myComp = (MaintComponent2) myForm.getComponent();
        
        FormKey fk = do_Save_Clicked();
        if (myForm.hasErrors(request))
            return fk;
        else
            return myComp.quitAndReturnToCallingScreen();
    }
    
    /** Invokes the create() or update() method on the component, depending on the mode.
     * @return The FormKey for the screen.
     */
    public FormKey do_Save_Clicked() {
        MaintForm myForm = (MaintForm) form;
        MaintComponent2 myComp = (MaintComponent2) myForm.getComponent();
        
        try {
            // This will stop double submits
            performTokenValidation(request);
            
            // First validate the current screen
            if (invokeDoValidateForScreen(myComp.getCurrentScreenCounter())) {
                // Now validate all the other screens
                if (invokeDoValidateForScreensOtherThanCurrent(myComp)) {
                    if (myComp.isCreateMode()) {
                        myComp.create();
                        myComp.setMode(IMaintComponent.MODE_UPDATE);
                        
                        // move to next screen, if the current screen is unavailable in update mode
                        if (!myComp.determineCurrentScreen().isAvailableInUpdateMode())
                            myComp.determineAndSetNextScreen();
                        
                        // Remove links from the HistoryNav, which are not supported in the update mode
                        fixHistoryNavList(myComp);
                    } else {
                        myComp.update(true);
                    }
                }
            }
        } catch (ApplicationExceptions e){
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        
        return myComp.determineFormKey();
    }
    
    /** Invokes the delete() method on the component. It then quits the component.
     * @return The FormKey for the calling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey do_Delete_Clicked() {
        MaintForm myForm = (MaintForm) form;
        MaintComponent2 myComp = (MaintComponent2) myForm.getComponent();
        
        try {
            // This will stop double submits
            performTokenValidation(request);
            
            myComp.delete(true);
            return myComp.quitAndReturnToCallingScreen();
        } catch (ApplicationExceptions e) {
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            e.printStackTrace();
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general");
        }
        
        return myComp.determineFormKey();
    }
    
    /** Invokes the retrieve() method on the component.
     * @return The FormKey for the screen.
     */
    public FormKey do_Refresh_Clicked() {
        MaintForm myForm = (MaintForm) form;
        MaintComponent2 myComp = (MaintComponent2) myForm.getComponent();
        
        try {
            myComp.retrieve();
        } catch (ApplicationExceptions e) {
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            e.printStackTrace();
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general");
        }
        
        return myComp.determineFormKey();
    }
    
    /** Determines the currentFormKey
     * This event is invoked when a user changes the settings of the UserGrid in the Results screen.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_refresh() {
        MaintForm myForm = (MaintForm) form;
        MaintComponent2 myComp = (MaintComponent2) myForm.getComponent();
        return myComp.determineFormKey();
    }
    
    /** This will invoke the prevalidate method on the component if required for the current screen.
     * It then moves to the next screen
     * @return The FormKey for the next screen.
     */
    public FormKey do_Next_Clicked() {
        MaintForm myForm = (MaintForm) form;
        MaintComponent2 myComp = (MaintComponent2) myForm.getComponent();
        
        if (invokeDoValidateForScreen(myComp.getCurrentScreenCounter())) {
            try {
                MaintComponent2.Screen currentScreen = myComp.determineCurrentScreen();
                if (currentScreen.isPerformTxValidationOnNextAction()) {
                    if (myComp.isCreateMode())
                        myComp.prevalidateCreate();
                    else
                        myComp.prevalidateUpdate(true);
                }
                myComp.determineAndSetNextScreen();
            } catch (ApplicationExceptions e){
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        return myComp.determineFormKey();
    }
    
    /** It moves to the previous screen
     * @return The FormKey for the previous screen.
     */
    public FormKey do_Previous_Clicked() {
        MaintForm myForm = (MaintForm) form;
        MaintComponent2 myComp = (MaintComponent2) myForm.getComponent();
        
        //if (invokeDoValidateForScreen(myComp.getCurrentScreenCounter()))
        //    myComp.determineAndSetPreviousScreen();
        
        myComp.determineAndSetPreviousScreen();
        return myComp.determineFormKey();
    }
    
    
    
    /* This will invoke the 'public boolean doValidate{n}(HttpServletRequest request)' method
     */
    protected boolean invokeDoValidateForScreen(int screenCounter) {
        String methodName = "doValidate" + screenCounter;
        Method method = null;
        try {
            method = form.getClass().getMethod(methodName, new Class[] {HttpServletRequest.class});
            if (method.getReturnType() != Boolean.TYPE)
                throw new NoSuchMethodException();
        } catch (NoSuchMethodException e) {
            String str = "The method 'public boolean doValidate" + screenCounter + "(HttpServletRequest request) needs to be defined in the class " + form.getClass().getName();
            log.error(str, e);
            throw new EventHandlerMissingRuntimeException(str);
        }
        
        try {
            Boolean output = (Boolean) method.invoke(form, new Object[] {request});
            return output.booleanValue();
        } catch (Exception e) {
            // should never happen
            throw new RuntimeException(e);
        }
    }
    
    /* This will invoke the 'public boolean doValidate{n}(HttpServletRequest request)' method
     * for all the screens of the component except the current screen.
     */
    protected boolean invokeDoValidateForScreensOtherThanCurrent(MaintComponent2 myComp) {
        boolean valid = true;
        for (int i = 0; i < myComp.getScreens().length; i++) {
            if (i != myComp.getCurrentScreenCounter()) {
                if (!invokeDoValidateForScreen(i))
                    valid = false;
            }
        }
        return valid;
    }
    
    /** Remove links from the HistoryNav, which are not supported in the update mode.
     */
    protected void fixHistoryNavList(MaintComponent2 myComp) {
        try {
            List historyNavList = HistoryNav.obtainHistoryNav(request);
            if (historyNavList != null && historyNavList.size() > 0) {
                // create a Map of Screen.FormName/Screen objects
                MaintComponent2.Screen[] screens = myComp.getScreens();
                if (screens != null && screens.length > 0) {
                    Map screenMap = new HashMap();
                    for (int i = 0; i < screens.length; i++) {
                        MaintComponent2.Screen screen = screens[i];
                        screenMap.put(screen.getFormName(), screen);
                    }
                    
                    for (Iterator itr = historyNavList.iterator(); itr.hasNext(); ) {
                        FormKey fk = (FormKey) itr.next();
                        // Check the FormKeys for our component only
                        if (myComp.getComponentId().equals(fk.getComponentId()) && fk.getFormName() != null) {
                            MaintComponent2.Screen screen = (MaintComponent2.Screen) screenMap.get(fk.getFormName());
                            if (screen != null && !screen.isAvailableInUpdateMode())
                                itr.remove();
                        }
                    }
                }
            }
            request.setAttribute(HistoryNav.HISTORY_NAV_PARAMETER, historyNavList);
        } catch (Exception e) {
            // do nothing
        }
    }
}
