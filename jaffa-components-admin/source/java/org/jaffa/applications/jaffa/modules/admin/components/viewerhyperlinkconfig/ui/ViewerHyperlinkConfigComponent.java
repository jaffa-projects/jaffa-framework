/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.viewerhyperlinkconfig.ui;

import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.components.dto.HeaderDto;
import org.jaffa.security.VariationContext;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.config.Config;
import org.jaffa.util.URLHelper;
import org.jaffa.presentation.portlet.widgets.taglib.TextTag;
import org.jaffa.applications.jaffa.modules.admin.components.viewerhyperlinkconfig.ui.exceptions.ViewerHyperlinkConfigException;


/** The controller for the ViewerHyperlinkConfig.
 */
public class ViewerHyperlinkConfigComponent extends Component {
    
    private static Logger log = Logger.getLogger(ViewerHyperlinkConfigComponent.class);
    private List m_screens;
    private int m_currentScreenCounter = 0;
    private HeaderDto m_headerDto = null;
    
    
    private java.lang.String m_domainFieldViewerComponentMappingFileName;
    private java.lang.String m_domainFieldViewerComponentMappingContents;
    private java.lang.String m_keyFieldPerViewerComponentFileName;
    private java.lang.String m_keyFieldPerViewerComponentContents;
    private boolean m_file0Updateable;
    private boolean m_file1Updateable;
    
    
    
    
    /** Getter for property domainFieldViewerComponentMappingFileName.
     * @return Value of property domainFieldViewerComponentMappingFileName.
     */
    public java.lang.String getDomainFieldViewerComponentMappingFileName() {
        return m_domainFieldViewerComponentMappingFileName;
    }
    
    /** Getter for property domainFieldViewerComponentMappingContents.
     * @return Value of property domainFieldViewerComponentMappingContents.
     */
    public java.lang.String getDomainFieldViewerComponentMappingContents() {
        return m_domainFieldViewerComponentMappingContents;
    }
    
    /** Setter for property domainFieldViewerComponentMappingContents.
     * @param domainFieldViewerComponentMappingContents New value of property domainFieldViewerComponentMappingContents.
     */
    public void setDomainFieldViewerComponentMappingContents(java.lang.String domainFieldViewerComponentMappingContents) {
        m_domainFieldViewerComponentMappingContents = domainFieldViewerComponentMappingContents;
    }
    
    /** Getter for property keyFieldPerViewerComponentFileName.
     * @return Value of property keyFieldPerViewerComponentFileName.
     */
    public java.lang.String getKeyFieldPerViewerComponentFileName() {
        return m_keyFieldPerViewerComponentFileName;
    }
    
    /** Getter for property keyFieldPerViewerComponentContents.
     * @return Value of property keyFieldPerViewerComponentContents.
     */
    public java.lang.String getKeyFieldPerViewerComponentContents() {
        return m_keyFieldPerViewerComponentContents;
    }
    
    /** Setter for property keyFieldPerViewerComponentContents.
     * @param keyFieldPerViewerComponentContents New value of property keyFieldPerViewerComponentContents.
     */
    public void setKeyFieldPerViewerComponentContents(java.lang.String keyFieldPerViewerComponentContents) {
        m_keyFieldPerViewerComponentContents = keyFieldPerViewerComponentContents;
    }
    
    /** Getter for property file0Updateable.
     * @return Value of property file0Updateable.
     *
     */
    public boolean isFile0Updateable() {
        return m_file0Updateable;
    }
    
    /** Getter for property file1Updateable.
     * @return Value of property file1Updateable.
     *
     */
    public boolean isFile1Updateable() {
        return m_file1Updateable;
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
        // ensure that the properties are correctly set in the framework.properties file
        m_domainFieldViewerComponentMappingFileName = (String) Config.getProperty(Config.PROP_DOMAIN_FIELD_VIEWER_COMPONENT_MAPPING_FILE, null);
        m_keyFieldPerViewerComponentFileName = (String) Config.getProperty(Config.PROP_KEY_FIELD_PER_VIEWER_COMPONENT_FILE, null);
        if (m_domainFieldViewerComponentMappingFileName == null || m_keyFieldPerViewerComponentFileName == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new ViewerHyperlinkConfigException(ViewerHyperlinkConfigException.PROP_INVALID_SETUP));
            throw appExps;
        }
        
        // load the 2 files
        loadDomainFieldViewerComponentMappingFile();
        loadKeyFieldPerViewerComponentFile();
        
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
        screens.add("admin_viewerHyperlinkConfig_domainFieldViewerComponentMapping");
        screens.add("admin_viewerHyperlinkConfig_keyFieldPerViewerComponent");
    }
    
    protected void loadDomainFieldViewerComponentMappingFile() throws ApplicationExceptions {
        m_domainFieldViewerComponentMappingContents = loadFile(m_domainFieldViewerComponentMappingFileName);
        try {
            File f = new File(URLHelper.newExtendedURL(m_domainFieldViewerComponentMappingFileName).getPath());
            m_file0Updateable = f.exists() && f.isFile();
        } catch (MalformedURLException e) {
            m_file0Updateable = false;
        }
        getUserSession().getWidgetCache(getComponentId()).removeModel("domainFieldViewerComponentMappingContents");
    }
    
    protected void loadKeyFieldPerViewerComponentFile() throws ApplicationExceptions {
        m_keyFieldPerViewerComponentContents = loadFile(m_keyFieldPerViewerComponentFileName);
        try {
            File f = new File(URLHelper.newExtendedURL(m_keyFieldPerViewerComponentFileName).getPath());
            m_file1Updateable = f.exists() && f.isFile();
        } catch (MalformedURLException e) {
            m_file1Updateable = false;
        }
        getUserSession().getWidgetCache(getComponentId()).removeModel("keyFieldPerViewerComponentContents");
    }
    
    protected void storeDomainFieldViewerComponentMappingFile() throws ApplicationExceptions {
        if (m_file0Updateable) {
            storeFile(m_domainFieldViewerComponentMappingFileName, m_domainFieldViewerComponentMappingContents);
        } else {
            if (log.isDebugEnabled())
                log.debug("File cannot be updated since it cannot be read using File I/O. It is probably part of a JAR: " + m_domainFieldViewerComponentMappingFileName);
        }
    }
    
    protected void storeKeyFieldPerViewerComponentFile() throws ApplicationExceptions {
        if (m_file1Updateable) {
            storeFile(m_keyFieldPerViewerComponentFileName, m_keyFieldPerViewerComponentContents);
        } else {
            if (log.isDebugEnabled())
                log.debug("File cannot be updated since it cannot be read using File I/O. It is probably part of a JAR: " + m_keyFieldPerViewerComponentFileName);
        }
    }
    
    private String loadFile(String fileName) throws ApplicationExceptions {
        Reader reader = null;
        try {
            InputStream is = URLHelper.getInputStream(fileName);
            if (is == null) {
                ApplicationExceptions appExps = new ApplicationExceptions();
                appExps.add(new ViewerHyperlinkConfigException(ViewerHyperlinkConfigException.PROP_FILE_READ_ERROR, fileName));
                throw appExps;
            }
            reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer buf = new StringBuffer();
            int i;
            while ((i = reader.read()) != -1)
                buf.append((char) i);
            
            if (log.isDebugEnabled())
                log.debug("Loaded the properties from file: " + fileName);
            return buf.toString();
        } catch (IOException e) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new ViewerHyperlinkConfigException(ViewerHyperlinkConfigException.PROP_FILE_READ_ERROR, fileName));
            throw appExps;
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }
    
    private void storeFile(String fileName, String contents) throws ApplicationExceptions {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(URLHelper.newExtendedURL(fileName).getPath(), false));
            writer.write(contents);
            writer.flush();
            
            if (log.isDebugEnabled())
                log.debug("Stored to the file: " + fileName + " and then flushing the cache in the TextTag");
            
            // flush the properties cache in the TextTag
            TextTag.reloadViewerHyperlinkConfig();
            
        } catch (MalformedURLException e) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new ViewerHyperlinkConfigException(ViewerHyperlinkConfigException.PROP_FILE_STORE_ERROR, fileName));
            throw appExps;
        } catch (IOException e) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new ViewerHyperlinkConfigException(ViewerHyperlinkConfigException.PROP_FILE_STORE_ERROR, fileName));
            throw appExps;
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }
    
}
