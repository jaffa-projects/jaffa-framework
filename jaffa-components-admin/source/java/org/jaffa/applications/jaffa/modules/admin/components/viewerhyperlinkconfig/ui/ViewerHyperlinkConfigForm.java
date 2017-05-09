/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.viewerhyperlinkconfig.ui;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.datatypes.exceptions.*;




/** The FormBean class.
 */
public class ViewerHyperlinkConfigForm extends FormBase {

    private static Logger log = Logger.getLogger(ViewerHyperlinkConfigForm.class);

    /** Getter for property domainFieldViewerComponentMappingFileName.
     * @return Value of property domainFieldViewerComponentMappingFileName.
     */
    public java.lang.String getDomainFieldViewerComponentMappingFileName() {
        return ( (ViewerHyperlinkConfigComponent) getComponent() ).getDomainFieldViewerComponentMappingFileName();
    }

    /** Getter for property domainFieldViewerComponentMappingContents.
     * @return Value of property domainFieldViewerComponentMappingContents.
     */
    public java.lang.String getDomainFieldViewerComponentMappingContents() {
        return ( (ViewerHyperlinkConfigComponent) getComponent() ).getDomainFieldViewerComponentMappingContents();
    }

    /** Setter for property domainFieldViewerComponentMappingContents.
     * @param domainFieldViewerComponentMappingContents New value of property domainFieldViewerComponentMappingContents.
     */
    public void setDomainFieldViewerComponentMappingContents(java.lang.String domainFieldViewerComponentMappingContents) {
        ( (ViewerHyperlinkConfigComponent) getComponent() ).setDomainFieldViewerComponentMappingContents(domainFieldViewerComponentMappingContents);
    }

    /** Getter for property domainFieldViewerComponentMappingContents. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property domainFieldViewerComponentMappingContents.
     */
    public EditBoxModel getDomainFieldViewerComponentMappingContentsWM() {
        EditBoxModel w_domainFieldViewerComponentMappingContents = (EditBoxModel) getWidgetCache().getModel("domainFieldViewerComponentMappingContents");
        if (w_domainFieldViewerComponentMappingContents == null) {
            if (getDomainFieldViewerComponentMappingContents() != null)
                w_domainFieldViewerComponentMappingContents = new EditBoxModel(getDomainFieldViewerComponentMappingContents());
            else
                w_domainFieldViewerComponentMappingContents = new EditBoxModel();
            getWidgetCache().addModel("domainFieldViewerComponentMappingContents", w_domainFieldViewerComponentMappingContents);
        }
        return w_domainFieldViewerComponentMappingContents;
    }

    /** Setter for property domainFieldViewerComponentMappingContents. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property domainFieldViewerComponentMappingContents.
     */
    public void setDomainFieldViewerComponentMappingContentsWV(String value) {
        EditBoxController.updateModel(value, getDomainFieldViewerComponentMappingContentsWM());
    }

    /** Getter for property keyFieldPerViewerComponentFileName.
     * @return Value of property keyFieldPerViewerComponentFileName.
     */
    public java.lang.String getKeyFieldPerViewerComponentFileName() {
        return ( (ViewerHyperlinkConfigComponent) getComponent() ).getKeyFieldPerViewerComponentFileName();
    }

    /** Getter for property keyFieldPerViewerComponentContents.
     * @return Value of property keyFieldPerViewerComponentContents.
     */
    public java.lang.String getKeyFieldPerViewerComponentContents() {
        return ( (ViewerHyperlinkConfigComponent) getComponent() ).getKeyFieldPerViewerComponentContents();
    }

    /** Setter for property keyFieldPerViewerComponentContents.
     * @param keyFieldPerViewerComponentContents New value of property keyFieldPerViewerComponentContents.
     */
    public void setKeyFieldPerViewerComponentContents(java.lang.String keyFieldPerViewerComponentContents) {
        ( (ViewerHyperlinkConfigComponent) getComponent() ).setKeyFieldPerViewerComponentContents(keyFieldPerViewerComponentContents);
    }

    /** Getter for property keyFieldPerViewerComponentContents. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property keyFieldPerViewerComponentContents.
     */
    public EditBoxModel getKeyFieldPerViewerComponentContentsWM() {
        EditBoxModel w_keyFieldPerViewerComponentContents = (EditBoxModel) getWidgetCache().getModel("keyFieldPerViewerComponentContents");
        if (w_keyFieldPerViewerComponentContents == null) {
            if (getKeyFieldPerViewerComponentContents() != null)
                w_keyFieldPerViewerComponentContents = new EditBoxModel(getKeyFieldPerViewerComponentContents());
            else
                w_keyFieldPerViewerComponentContents = new EditBoxModel();
            getWidgetCache().addModel("keyFieldPerViewerComponentContents", w_keyFieldPerViewerComponentContents);
        }
        return w_keyFieldPerViewerComponentContents;
    }

    /** Setter for property keyFieldPerViewerComponentContents. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property keyFieldPerViewerComponentContents.
     */
    public void setKeyFieldPerViewerComponentContentsWV(String value) {
        EditBoxController.updateModel(value, getKeyFieldPerViewerComponentContentsWM());
    }


    /** Getter for property file0Updateable.
     * @return Value of property file0Updateable.
     *
     */
    public boolean isFile0Updateable() {
        return ( (ViewerHyperlinkConfigComponent) getComponent() ).isFile0Updateable();
    }

    /** Getter for property file1Updateable.
     * @return Value of property file1Updateable.
     *
     */
    public boolean isFile1Updateable() {
        return ( (ViewerHyperlinkConfigComponent) getComponent() ).isFile1Updateable();
    }



    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = true;
        if (!doValidate0(request))
            valid = false;
        if (!doValidate1(request))
            valid = false;
        return valid;
    }
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate0(HttpServletRequest request) {
        boolean valid = true;
        String htmlString = null;
        htmlString = getDomainFieldViewerComponentMappingContentsWM().getValue();
        if (htmlString != null && htmlString.length() == 0)
            htmlString = null;
        java.lang.String domainFieldViewerComponentMappingContentsValue = Parser.parseString(htmlString);
        setDomainFieldViewerComponentMappingContents(domainFieldViewerComponentMappingContentsValue);

        return valid;
    }
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate1(HttpServletRequest request) {
        boolean valid = true;
        String htmlString = null;
        htmlString = getKeyFieldPerViewerComponentContentsWM().getValue();
        if (htmlString != null && htmlString.length() == 0)
            htmlString = null;
        java.lang.String keyFieldPerViewerComponentContentsValue = Parser.parseString(htmlString);
        setKeyFieldPerViewerComponentContents(keyFieldPerViewerComponentContentsValue);

        return valid;
    }


    /** Getter for property currentScreenCounter.
     * @return Value of property currentScreenCounter.
     */
    public int getCurrentScreenCounter() {
        return ((ViewerHyperlinkConfigComponent) getComponent()).getCurrentScreenCounter();
    }

    /** Setter for property currentScreenCounter.
     * @param currentScreenCounter New value of property currentScreenCounter.
     */
    public void setCurrentScreenCounter(int currentScreenCounter) {
        ((ViewerHyperlinkConfigComponent) getComponent()).setCurrentScreenCounter(currentScreenCounter);
    }

    /** Returns true if there is a Next screen after the current screen.
     * @return true if there is a Next screen after the current screen.
     */
    public boolean isNextActionAvailable() {
        return ((ViewerHyperlinkConfigComponent) getComponent()).determineNextScreen() != null ? true : false;
    }

    /** Returns true if there is a Previous screen before the current screen.
     * @return true if there is a Previous screen before the current screen.
     */
    public boolean isPreviousActionAvailable() {
        return ((ViewerHyperlinkConfigComponent) getComponent()).determinePreviousScreen() != null ? true : false;
    }

}
