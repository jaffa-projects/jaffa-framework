/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.validationruleseditor.ui;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.jaffa.applications.jaffa.modules.admin.exceptions.ConfigException;
import org.jaffa.config.Config;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.datatypes.exceptions.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.metadata.RulesMetaDataService;
import org.jaffa.util.DefaultEntityResolver;
import org.jaffa.util.DefaultErrorHandler;
import org.jaffa.util.StringHelper;
import org.jaffa.util.URLHelper;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/** The FormBean class.
 */
public class ValidationRulesEditorForm extends FormBase {

    private static Logger log = Logger.getLogger(ValidationRulesEditorForm.class);


    /** Getter for property rules. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property rules.
     */
    public GridModel getRulesWM() {
        GridModel w_rules = (GridModel) getWidgetCache().getModel("rules");
        if (w_rules == null) {
            w_rules = populateRulesModel();
            getWidgetCache().addModel("rules", w_rules);
        }
        return w_rules;
    }

    /** Setter for property rules. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property rules.
     */
    public void setRulesWV(String value) {
        GridController.updateModel(value, getRulesWM());
    }

    private GridModel populateRulesModel() {
        GridModel m = new GridModel();

        String variationRulesDirectory = getVariationRulesDir();
        if (variationRulesDirectory != null && variationRulesDirectory.length() > 0)
            try {
                // Look for files in the directory (it will be in URL format)
                URL url = URLHelper.newExtendedURL(variationRulesDirectory);
                if(url!=null) {
                    File dir = new File(url.getPath());
                    if(dir.exists() && dir.isDirectory()) {
                        // Get file list
                        File[] files = dir.listFiles();
                        if(files!=null) {
                            for(int i=0;i<files.length;i++) {
                                if(files[i].getName().endsWith(RulesMetaDataService.VARIATION_FILE_SUFFIX)) {
                                    GridModelRow row = m.newRow();
                                    String var = files[i].getName().substring(0,(files[i].getName().length()-RulesMetaDataService.VARIATION_FILE_SUFFIX.length()));
                                    row.addElement("variation",var);
                                    row.addElement("name", "file://" + files[i].toURI().toURL().getPath());
                                }
                            }
                        }
                    }
                }
            } catch (MalformedURLException e) {
                log.error("Unable to access Rules Variation Directory " + variationRulesDirectory,e);
            }
        return m;
    }

    /** Getter for property validationRulesFile.
     * @return Value of property validationRulesFile.
     */
    public java.lang.String getValidationRulesFile() {
        return ( (ValidationRulesEditorComponent) getComponent() ).getValidationRulesFile();
    }

    /** Setter for property validationRulesFile.
     * @param validationRulesFile New value of property validationRulesFile.
     */
    public void setValidationRulesFile(java.lang.String validationRulesFile) {
        ( (ValidationRulesEditorComponent) getComponent() ).setValidationRulesFile(validationRulesFile);
    }

    /** Getter for property fileContents.
     * @return Value of property fileContents.
     */
    public String getFileContents() {
        return ( (ValidationRulesEditorComponent) getComponent() ).getFileContents();
    }

    /** Setter for property fileContents.
     * @param fileContents New value of property fileContents.
     */
    public void setFileContents(String fileContents) {
        ( (ValidationRulesEditorComponent) getComponent() ).setFileContents(fileContents);
    }

    /** Getter for property fileContents. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property fileContents.
     */
    public EditBoxModel getFileContentsWM() {
        EditBoxModel fileContentsModel = (EditBoxModel) getWidgetCache().getModel("fileContents");
        if (fileContentsModel == null) {
            if (getFileContents() != null)
                fileContentsModel = new EditBoxModel( getFileContents() );
            else
                fileContentsModel = new EditBoxModel();
            getWidgetCache().addModel("fileContents", fileContentsModel);
        }
        return fileContentsModel;
    }

    /** Setter for property fileContents. This is invoked by the servlet, when a post is done on the main screen.
     * @param value New value of property fileContents.
     */
    public void setFileContentsWV(String value) {
        EditBoxController.updateModel(value, getFileContentsWM());
    }

    /** Getter for property fileUpdateable.
     * @return Value of property fileUpdateable.
     *
     */
    public boolean isFileUpdateable() {
        return ( (ValidationRulesEditorComponent) getComponent() ).isFileUpdateable();
    }


    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) throws FrameworkException , ApplicationExceptions {
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
        return valid;
    }
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate1(HttpServletRequest request) throws FrameworkException , ApplicationExceptions {
        String value = null;
        ApplicationExceptions appExps = new ApplicationExceptions();
        value = getFileContentsWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;

        if (value != null) {
            try {
                // Create a factory object for creating DOM parsers
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                // Specifies that the parser produced by this factory will validate documents as they are parsed.
                factory.setValidating(true);
                // Now use the factory to create a DOM parser
                DocumentBuilder parser = factory.newDocumentBuilder();
                // Specifies the EntityResolver onceo resolve DTD used in XML documents
                parser.setEntityResolver(new DefaultEntityResolver());
                // Specifies the ErrorHandler to handle warning/error/fatalError conditions
                parser.setErrorHandler(new DefaultErrorHandler());
                parser.parse(new InputSource(new StringReader(value)));
            } catch(ParserConfigurationException e){
                // Cannot pass e.toString() and pass as parameter as the the meesage contains quotes
                // which dows not work properly with displaying the messages
                appExps.add(new ConfigException(ConfigException.PROP_XML_FILE_PARSE_ERROR,StringHelper.convertToHTML(e.getMessage())));
                throw appExps;
            } catch(SAXException e){
                appExps.add(new ConfigException(ConfigException.PROP_XML_FILE_PARSE_ERROR,StringHelper.convertToHTML(e.getMessage())));
                throw appExps;
            } catch(IOException e){
                appExps.add(new ConfigException(ConfigException.PROP_XML_FILE_PARSE_ERROR,StringHelper.convertToHTML(e.getMessage())));
                throw appExps;
            }
        }
        setFileContents(value);
        return true;
    }


    /** Getter for property currentScreenCounter.
     * @return Value of property currentScreenCounter.
     */
    public int getCurrentScreenCounter() {
        return ((ValidationRulesEditorComponent) getComponent()).getCurrentScreenCounter();
    }

    /** Setter for property currentScreenCounter.
     * @param currentScreenCounter New value of property currentScreenCounter.
     */
    public void setCurrentScreenCounter(int currentScreenCounter) {
        ((ValidationRulesEditorComponent) getComponent()).setCurrentScreenCounter(currentScreenCounter);
    }

    /** Returns true if there is a Next screen after the current screen.
     * @return true if there is a Next screen after the current screen.
     */
    public boolean isNextActionAvailable() {
        return ((ValidationRulesEditorComponent) getComponent()).determineNextScreen() != null ? true : false;
    }

    /** Returns true if there is a Previous screen before the current screen.
     * @return true if there is a Previous screen before the current screen.
     */
    public boolean isPreviousActionAvailable() {
        return ((ValidationRulesEditorComponent) getComponent()).determinePreviousScreen() != null ? true : false;
    }


    public String getCoreRulesUrl() {
        return (String) Config.getProperty(Config.PROP_RULES_ENGINE_CORE_RULES_URL,null);
    }

    public GridModel getValidatorsUrlsWM() {
        GridModel w_validatorsUrls = (GridModel) getWidgetCache().getModel("validatorsUrls");
        if (w_validatorsUrls == null) {
            w_validatorsUrls = populateValidatorsUrlsModel();
            getWidgetCache().addModel("validatorsUrls", w_validatorsUrls);
        }
        return w_validatorsUrls;
    }

    /** Setter for property rules. This is invoked by the servlet, when a post is done on the corresponding JSP.
     * @param value New value of property rules.
     */
    public void setValidatorsUrlsWV(String value) {
        GridController.updateModel(value, getValidatorsUrlsWM());
    }

    private GridModel populateValidatorsUrlsModel() {
        GridModel m = new GridModel();
        String s = (String) Config.getProperty(Config.PROP_RULES_ENGINE_VALIDATORS_URL_LIST,null);
        if(s!=null) {
            List l = StringHelper.convertToList(s);
            if(l!=null)
                for (Iterator i=l.iterator(); i.hasNext(); ) {
                    String s1 = (String) i.next();
                    if(s1!=null && s1.length()>0) {
                        GridModelRow row = m.newRow();
                        row.addElement("validatorsUrl", s1);
                    }
                }
        }
        return m;
    }

    public String getVariationRulesDir() {
        return (String) Config.getProperty(Config.PROP_RULES_ENGINE_VARIATIONS_DIR,null);
    }

}
