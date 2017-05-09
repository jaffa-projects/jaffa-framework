/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.validationruleseditor.ui;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;
import org.apache.struts.action.ActionMessages;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.EventHandlerMissingRuntimeException;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.config.Config;

/** The Action class for handling events.
 */
public class ValidationRulesEditorAction extends ActionBase {

    private static final Logger log = Logger.getLogger(ValidationRulesEditorAction.class);


    /** Clicked event handler for the field Save.
     * @return The FormKey.
     */
    public FormKey do_Save_Clicked() {
        FormKey fk = null;
        ValidationRulesEditorForm myForm = (ValidationRulesEditorForm) form;
        ValidationRulesEditorComponent myComp = (ValidationRulesEditorComponent) myForm.getComponent();
        // Add Event Handling logic

        try {
            // Copy the values from Form to Component
            myForm.doValidate(request);

            // save the file contents
            myComp.performSave();

        } catch (ApplicationExceptions e){
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }

    /** Clicked event handler for the field Finish.
     * @return The FormKey.
     */
    public FormKey do_Finish_Clicked() {
        FormKey fk = null;
        ValidationRulesEditorForm myForm = (ValidationRulesEditorForm) form;
        ValidationRulesEditorComponent myComp = (ValidationRulesEditorComponent) myForm.getComponent();

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
        ValidationRulesEditorForm myForm = (ValidationRulesEditorForm) form;
        ValidationRulesEditorComponent myComp = (ValidationRulesEditorComponent) myForm.getComponent();
        myComp.setFileContents(null);
        try {
            myComp.loadFileContents();
        } catch (ApplicationExceptions e){
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }

        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }



    /** Determines the currentFormKey
     * This event is invoked when a user changes the settings of the UserGrid in the Results screen.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_refresh() {
        ValidationRulesEditorForm myForm = (ValidationRulesEditorForm) form;
        ValidationRulesEditorComponent myComp = (ValidationRulesEditorComponent) myForm.getComponent();
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
        ValidationRulesEditorForm myForm = (ValidationRulesEditorForm) form;
        ValidationRulesEditorComponent myComp = (ValidationRulesEditorComponent) myForm.getComponent();

        if (invokeDoValidateForScreen(myComp.getCurrentScreenCounter())) {
            myComp.determineAndSetNextScreen();
        }
        return myComp.determineFormKey();
    }

    /** It moves to the previous screen
     * @return The FormKey for the previous screen.
     */
    public FormKey do_Previous_Clicked() {
        ValidationRulesEditorForm myForm = (ValidationRulesEditorForm) form;
        ValidationRulesEditorComponent myComp = (ValidationRulesEditorComponent) myForm.getComponent();
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




    /** Invoked if a row is selected.
     * @param rowId The selected row on the screen.
     * @return The FormKey for the Update screen.
     */
    public FormKey do_Rules_Clicked(String rowId) {
        return do_Rules_Update_Clicked(rowId);
    }

    /** Invoked if a update button on a row is selected.
     * @param rowId The selected row on the screen.
     * @return The FormKey for the Update screen.
     */
    public FormKey do_Rules_Update_Clicked(String rowId) {
        ValidationRulesEditorForm myForm = (ValidationRulesEditorForm) form;
        ValidationRulesEditorComponent myComp = (ValidationRulesEditorComponent) myForm.getComponent();
        FormKey fk = null;
        GridModel model = (GridModel) myForm.getRulesWM();
        GridModelRow selectedRow = model.getRowById(Integer.parseInt(rowId));
        if (selectedRow != null) {
            try {
                String fileName = (String) selectedRow.get("name");
                if (!fileName.equals(myComp.getValidationRulesFile())) {
                    myComp.setFileContents(null);
                    myComp.setValidationRulesFile(fileName);
                    myComp.loadFileContents();
                }
                myComp.determineAndSetNextScreen();
            } catch (ApplicationExceptions e){
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }

    /** Invoked if the core-rules URL is clicked
     * @return The FormKey for the Update screen.
     */
    public FormKey do_CoreRulesUrl_Clicked() {
        ValidationRulesEditorForm myForm = (ValidationRulesEditorForm) form;
        ValidationRulesEditorComponent myComp = (ValidationRulesEditorComponent) myForm.getComponent();
        FormKey fk = null;
        try {
            String fileName = (String) Config.getProperty(Config.PROP_RULES_ENGINE_CORE_RULES_URL, null);
            if (!fileName.equals(myComp.getValidationRulesFile())) {
                myComp.setFileContents(null);
                myComp.setValidationRulesFile(fileName);
                myComp.loadFileContents();
            }
            myComp.determineAndSetNextScreen();
        } catch (ApplicationExceptions e){
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }

    /** Invoked if a row is selected.
     * @param rowId The selected row on the screen.
     * @return The FormKey for the Update screen.
     */
    public FormKey do_ValidatorsUrls_Clicked(String rowId) {
        return do_ValidatorsUrls_Update_Clicked(rowId);
    }

    /** Invoked if a update button on a row is selected.
     * @param rowId The selected row on the screen.
     * @return The FormKey for the Update screen.
     */
    public FormKey do_ValidatorsUrls_Update_Clicked(String rowId) {
        ValidationRulesEditorForm myForm = (ValidationRulesEditorForm) form;
        ValidationRulesEditorComponent myComp = (ValidationRulesEditorComponent) myForm.getComponent();
        FormKey fk = null;
        GridModel model = (GridModel) myForm.getValidatorsUrlsWM();
        GridModelRow selectedRow = model.getRowById(Integer.parseInt(rowId));
        if (selectedRow != null) {
            try {
                String fileName = (String) selectedRow.get("validatorsUrl");
                if (!fileName.equals(myComp.getValidationRulesFile())) {
                    myComp.setFileContents(null);
                    myComp.setValidationRulesFile(fileName);
                    myComp.loadFileContents();
                }
                myComp.determineAndSetNextScreen();
            } catch (ApplicationExceptions e){
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }
        // Direct User back to current form
        if (fk == null)
            fk = myComp.determineFormKey();
        return fk;
    }

}
