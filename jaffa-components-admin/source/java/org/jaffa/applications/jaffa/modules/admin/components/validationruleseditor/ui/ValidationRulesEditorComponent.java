/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.validationruleseditor.ui;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.components.dto.HeaderDto;
import org.jaffa.security.VariationContext;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.applications.jaffa.modules.admin.exceptions.ConfigException;
import org.jaffa.rules.metadata.RulesMetaDataService;
import org.jaffa.util.StringHelper;
import org.jaffa.util.URLHelper;


/** The controller for the ValidationRulesEditor.
 */
public class ValidationRulesEditorComponent extends Component {
    
    private static Logger log = Logger.getLogger(ValidationRulesEditorComponent.class);
    private List m_screens;
    private int m_currentScreenCounter = 0;
    private HeaderDto m_headerDto = null;
    private String m_fileContents;
    private String m_validationRulesFile;
    private boolean m_fileUpdateable;
    
    
    /** Getter for property validationRulesFile.
     * @return Value of property validationRulesFile.
     */
    public java.lang.String getValidationRulesFile() {
        return m_validationRulesFile;
    }
    
    /** Setter for property validationRulesFile.
     * @param validationRulesFile New value of property validationRulesFile.
     */
    public void setValidationRulesFile(java.lang.String validationRulesFile) {
        m_validationRulesFile = validationRulesFile;
    }
    
    /** Getter for property fileContents.
     * @return Value of property fileContents.
     */
    public String getFileContents() {
        return m_fileContents;
    }
    
    /** Setter for property fileContents.
     * @param fileContents New value of property fileContents.
     */
    public void setFileContents(String fileContents) {
        m_fileContents = fileContents;
    }
    
    /** Getter for property fileUpdateable.
     * @return Value of property fileUpdateable.
     *
     */
    public boolean isFileUpdateable() {
        return m_fileUpdateable;
    }
    
    
    
    /** Getter for property currentScreenCounter.
     * @return Value of property currentScreenCounter.
     */
    public int getCurrentScreenCounter() {
        return m_currentScreenCounter;
    }
    
    /** Setter for property currentScreenCounter.
     * @param currentScreenCounter New value of property currentScreenCounter.
     */
    public void setCurrentScreenCounter(int currentScreenCounter) {
        m_currentScreenCounter = currentScreenCounter;
    }
    
    /** Getter for the Screens.
     * @return the screens.
     */
    public String[] getScreens() {
        return (String[]) m_screens.toArray(new String[0]);
    }
    
    /** Getter for the current Screen.
     * @return the current screen.
     */
    public String determineCurrentScreen() {
        return (String) m_screens.get(m_currentScreenCounter);
    }
    
    /** Getter for the next Screen.
     * This takes into account the mode and if the following screen is available in create or update modes.
     * A null will be returned in case no appropriate next screen is available.
     * @return the next screen.
     */
    public String determineNextScreen() {
        return determineAndSetNextScreen(false);
    }
    
    /** This sets the currentScreenCounter to point to the next screen.
     * This takes into account the mode and if the following screen is available in create or update modes.
     * A null will be returned in case no appropriate next screen is available.
     * @return the next screen.
     */
    public String determineAndSetNextScreen() {
        return determineAndSetNextScreen(true);
    }
    
    private String determineAndSetNextScreen(boolean setCurrentScreenCounter) {
        String screen = null;
        int i = m_currentScreenCounter + 1;
        if (i < m_screens.size()) {
            screen = (String) m_screens.get(i);
            if (setCurrentScreenCounter)
                m_currentScreenCounter = i;
        }
        return screen;
    }
    
    /** Getter for the previous Screen.
     * This takes into account the mode and if the previous screen is available in create or update modes.
     * A null will be returned in case no appropriate previous screen is available.
     * @return the previous screen.
     */
    public String determinePreviousScreen() {
        return determineAndSetPreviousScreen(false);
    }
    
    /** This sets the currentScreenCounter to point to the previous screen.
     * This takes into account the mode and if the previous screen is available in create or update modes.
     * A null will be returned in case no appropriate previous screen is available.
     * @return the previous screen.
     */
    public String determineAndSetPreviousScreen() {
        return determineAndSetPreviousScreen(true);
    }
    
    private String determineAndSetPreviousScreen(boolean setCurrentScreenCounter) {
        String screen = null;
        int i = m_currentScreenCounter - 1;
        if (i >= 0) {
            screen = (String) m_screens.get(i);
            if (setCurrentScreenCounter)
                m_currentScreenCounter = i;
        }
        return screen;
    }
    
    /** Getter for the current screen's FormKey.
     * @return the FormKey for the current screen.
     */
    public FormKey determineFormKey() {
        return new FormKey(determineCurrentScreen(), getComponentId());
    }
    
    /** Brings up the first screen of the component.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey.
     */
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        if (m_screens == null) {
            m_screens = new ArrayList();
            addScreens(m_screens);
        }
        return determineFormKey();
    }
    
    /** This sets up the screen information.
     * @param screens The component should add String objects to this list; each String representing a struts-forward name.
     */
    protected void addScreens(List screens) {
        screens.add("admin_validationRulesEditor_rulesList");
        screens.add("admin_validationRulesEditor_editor");
    }
    
    
    /** This will save the contents of the current file.
     * @throws ApplicationExceptions if any error occurs while writing the file.
     * @throws FrameworkException if any error occurs.
     */
    void performSave() throws FrameworkException , ApplicationExceptions{
        BufferedWriter writer = null;
        try {
            if (m_fileUpdateable) {
                URL url = URLHelper.newExtendedURL(m_validationRulesFile);
                File f = new File(url.getPath());
                writer = new BufferedWriter(new FileWriter(f, false));
                writer.write(getFileContents());
                writer.flush();
                if (log.isDebugEnabled())
                    log.debug("Saved contents to the file " + m_validationRulesFile);
                // Clear cache so new rules are loaded.
                RulesMetaDataService.clearCache();
            } else {
                if (log.isDebugEnabled())
                    log.debug("File cannot be updated since it cannot be read using File I/O. It is probably part of a JAR: " + m_validationRulesFile);
            }
        } catch (MalformedURLException e) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new ConfigException(ConfigException.PROP_FILEREAD_ERROR,StringHelper.convertToHTML(e.getMessage())));
            throw appExps;
        } catch (IOException e) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new ConfigException(ConfigException.PROP_FILEREAD_ERROR,StringHelper.convertToHTML(e.getMessage())));
            throw appExps;
        } finally {
            if (writer != null) {
                try{
                    writer.close();
                } catch (IOException e) {
                    String str = "Exception thrown while closing the Writer Stream";
                    log.error(str, e);
                    ApplicationExceptions appExps = new ApplicationExceptions();
                    appExps.add(new ConfigException(ConfigException.PROP_FILEREAD_ERROR,StringHelper.convertToHTML(e.getMessage())));
                    throw appExps;
                }
            }
        }
        
    }
    
    /** Load a file from a URL, which could be an Extended URL
     * @throws ApplicationExceptions if any error occurs while reading the file.
     * @throws FrameworkException if any error occurs.
     */
    void loadFileContents() throws FrameworkException , ApplicationExceptions {
        Reader reader = null;
        try {
            URL url = URLHelper.newExtendedURL(m_validationRulesFile);
            File f = new File(url.getPath());
            m_fileUpdateable = f.exists() && f.isFile();
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buf = new StringBuffer();
            int i;
            while ((i = reader.read()) != -1)
                buf.append((char) i);
            m_fileContents = buf.toString();
            if (log.isDebugEnabled())
                log.debug("Obtained contents from the file " + m_validationRulesFile);
            // remove the EditBoxModel from the WidgetCache
            getUserSession().getWidgetCache(getComponentId()).removeModel("fileContents");
        } catch (MalformedURLException e) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new ConfigException(ConfigException.PROP_FILEREAD_ERROR,StringHelper.convertToHTML(e.getMessage())));
            throw appExps;
        } catch (IOException e) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new ConfigException(ConfigException.PROP_FILEREAD_ERROR,StringHelper.convertToHTML(e.getMessage())));
            throw appExps;
        } finally {
            if (reader != null) {
                try{
                    reader.close();
                } catch (IOException e) {
                    String str = "Exception thrown while closing the Reader Stream";
                    log.error(str, e);
                    ApplicationExceptions appExps = new ApplicationExceptions();
                    appExps.add(new ConfigException(ConfigException.PROP_FILEREAD_ERROR,StringHelper.convertToHTML(e.getMessage())));
                    throw appExps;
                }
            }
        }
    }
    
}