/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.defaultvalueeditor.ui;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.apache.struts.action.ActionMessages;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.EventHandlerMissingRuntimeException;
import org.jaffa.presentation.portlet.widgets.model.*;

/** The Action class for handling events.
 */
public class DefaultValueEditorAction extends ActionBase {

    private static final Logger log = Logger.getLogger(DefaultValueEditorAction.class);


    /** Clicked event handler for the field Components.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey.
     */
    public FormKey do_Components_Clicked(String rowNum) {
        return do_Components_Update_Clicked(rowNum);
    }

    /** Clicked event handler for the field Components.
     * @param rowNum The selected row on the Results screen.
     * @return The FormKey.
     */
    public FormKey do_Components_Update_Clicked(String rowNum) {
        FormKey fk = null;
        DefaultValueEditorForm myForm = (DefaultValueEditorForm) form;
        DefaultValueEditorComponent myComp = (DefaultValueEditorComponent) myForm.getComponent();
        try {
            if (invokeDoValidateForScreen(myComp.getCurrentScreenCounter())) {
                GridModelRow selectedRow = myForm.getComponentsWM().getRow(Integer.parseInt(rowNum));
                if (selectedRow != null) {
                    String defaultValueFile = (String) selectedRow.get("DefaultValueFile");
                    if (defaultValueFile != null && !defaultValueFile.equals(myForm.getDefaultValueFile())) {
                        myForm.setDefaultValueFile(defaultValueFile);
                        myForm.setDefaultValues(null);
                        myComp.obtainDefaultValues();
                    }
                    myComp.determineAndSetNextScreen();
                }
            }
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }

        fk = myComp.determineFormKey();
        return fk;
    }

    /** Clicked event handler for the field Save.
     * @return The FormKey.
     */
    public FormKey do_Save_Clicked() {
        FormKey fk = null;
        DefaultValueEditorForm myForm = (DefaultValueEditorForm) form;
        DefaultValueEditorComponent myComp = (DefaultValueEditorComponent) myForm.getComponent();
        try {
            if (invokeDoValidateForScreen(myComp.getCurrentScreenCounter())) {
                myComp.saveDefaultValues();
            }
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }

        fk = myComp.determineFormKey();
        return fk;
    }

    /** Clicked event handler for the field Finish.
     * @return The FormKey.
     */
    public FormKey do_Finish_Clicked() {
        FormKey fk = null;
        DefaultValueEditorForm myForm = (DefaultValueEditorForm) form;
        DefaultValueEditorComponent myComp = (DefaultValueEditorComponent) myForm.getComponent();

        fk = do_Save_Clicked();
        if (!myForm.hasErrors(request))
            fk = myComp.quitAndReturnToCallingScreen();

        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }

    /** Clicked event handler for the field Refresh.
     * @return The FormKey.
     */
    public FormKey do_Refresh_Clicked() {
        FormKey fk = null;
        DefaultValueEditorForm myForm = (DefaultValueEditorForm) form;
        DefaultValueEditorComponent myComp = (DefaultValueEditorComponent) myForm.getComponent();
        myComp.setDefaultValues(null);
        try {
            myComp.obtainDefaultValues();
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }

        fk = myComp.determineFormKey();
        return fk;
    }



    /** Determines the currentFormKey
     * This event is invoked when a user changes the settings of the UserGrid in the Results screen.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_refresh() {
        DefaultValueEditorForm myForm = (DefaultValueEditorForm) form;
        DefaultValueEditorComponent myComp = (DefaultValueEditorComponent) myForm.getComponent();
        return myComp.determineFormKey();
    }

    /** Quits the component and returns the FormKey for the calling screen.
     * @return The FormKey for the calling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey do_Cancel_Clicked() {
        return component.quitAndReturnToCallingScreen();
    }

    /** This will invoke the doValidate() method for the current screen.
     * It then moves to the next screen
     * @return The FormKey for the next screen.
     */
    public FormKey do_Next_Clicked() {
        DefaultValueEditorForm myForm = (DefaultValueEditorForm) form;
        DefaultValueEditorComponent myComp = (DefaultValueEditorComponent) myForm.getComponent();

        try {
            if (invokeDoValidateForScreen(myComp.getCurrentScreenCounter())) {
                if (myComp.getCurrentScreenCounter() == 0)
                    myComp.obtainDefaultValues();
                myComp.determineAndSetNextScreen();
            }
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }

        return myComp.determineFormKey();
    }

    /** It moves to the previous screen
     * @return The FormKey for the previous screen.
     */
    public FormKey do_Previous_Clicked() {
        DefaultValueEditorForm myForm = (DefaultValueEditorForm) form;
        DefaultValueEditorComponent myComp = (DefaultValueEditorComponent) myForm.getComponent();
        myComp.determineAndSetPreviousScreen();
        return myComp.determineFormKey();
    }

    /* This will invoke the 'public boolean doValidate{n}(HttpServletRequest request)' method
     */
    private boolean invokeDoValidateForScreen(int screenCounter) {
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

}
