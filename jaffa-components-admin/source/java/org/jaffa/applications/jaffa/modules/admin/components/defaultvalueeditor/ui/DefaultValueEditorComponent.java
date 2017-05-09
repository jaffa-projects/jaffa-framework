/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.defaultvalueeditor.ui;

import java.util.*;
import java.io.*;
import java.net.URL;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.component.ComponentDefinition;
import org.jaffa.presentation.portlet.component.componentdomain.Loader;
import org.jaffa.components.maint.MaintComponent2;
import org.jaffa.util.StringHelper;
import org.jaffa.util.URLHelper;
import org.jaffa.applications.jaffa.modules.admin.components.defaultvalueeditor.ui.exceptions.DefaultValueEditorException;


/** The controller for the DefaultValueEditor.
 */
public class DefaultValueEditorComponent extends Component {
    
    private static Logger log = Logger.getLogger(DefaultValueEditorComponent.class);
    private List m_screens;
    private int m_currentScreenCounter = 0;
    
    
    private List[] m_components;
    private java.lang.String m_defaultValueFile;
    private java.lang.String m_defaultValues;
    private boolean m_fileUpdateable;
    
    
    
    
    /** Getter for property components.
     * A List will contain information for a component having default value file.
     * Each List will contain the String values for: ComponentName, ComponentClass, ComponentType and DefaultValueFile.
     * @return Value of property components.
     */
    public List[] getComponents() {
        return m_components;
    }
    
    /** Getter for property defaultValueFile.
     * @return Value of property defaultValueFile.
     */
    public java.lang.String getDefaultValueFile() {
        return m_defaultValueFile;
    }
    
    /** Setter for property defaultValueFile.
     * @param defaultValueFile New value of property defaultValueFile.
     */
    public void setDefaultValueFile(java.lang.String defaultValueFile) {
        m_defaultValueFile = defaultValueFile;
    }
    
    /** Getter for property defaultValues.
     * @return Value of property defaultValues.
     */
    public java.lang.String getDefaultValues() {
        return m_defaultValues;
    }
    
    /** Setter for property defaultValues.
     * @param defaultValues New value of property defaultValues.
     */
    public void setDefaultValues(java.lang.String defaultValues) {
        m_defaultValues = defaultValues;
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
        obtainComponents();
        
        // Initialize the defaultValueFile to the 1st row in the List
        if (m_components != null && m_components.length > 0 && m_components[0].size() >= 4)
            setDefaultValueFile((String) m_components[0].get(3));
        
        return determineFormKey();
    }
    
    /** This sets up the screen information.
     * @param screens The component should add String objects to this list; each String representing a struts-forward name.
     */
    protected void addScreens(List screens) {
        screens.add("admin_defaultValueEditor_componentList");
        screens.add("admin_defaultValueEditor_propertyEditor");
    }
    
    
    
    /** Create a List of Lists, each List representing a component definition, having a corresponding default value file.
     * Each List will contain the String values for: ComponentName, ComponentClass, ComponentType and DefaultValueFile.
     */
    protected void obtainComponents() {
        if (m_components == null) {
            List components = new ArrayList();
            Map componentPool = Loader.getComponentPool();
            if (componentPool != null) {
                // Iterate through the SORTED componentNames
                // Create a List for each ComponentDefinition
                for (Iterator i = (new TreeSet(componentPool.keySet())).iterator(); i.hasNext(); ) {
                    String componentName = (String) i.next();
                    ComponentDefinition cd = (ComponentDefinition) componentPool.get(componentName);
                    try {
                        URL url = MaintComponent2.determineDefaultValuesUrl(Class.forName(cd.getComponentClass()));
                        if (url != null) {
                            List component = new ArrayList();
                            component.add(componentName);
                            component.add(cd.getComponentClass());
                            component.add(cd.getComponentType());
                            component.add(url.toExternalForm());
                            components.add(component);
                        } else {
                            if (log.isDebugEnabled())
                                log.debug("No default value file found for component: " + componentName);
                        }
                    } catch (ClassNotFoundException e) {
                        log.warn("Class Not Found", e);
                    }
                }
            }
            m_components = (List[]) components.toArray(new List[0]);
        }
    }
    
    /** Reads the contents of the selected file.
     */
    protected void obtainDefaultValues() throws FrameworkException {
        Reader reader = null;
        try {
            if (m_defaultValueFile != null && m_defaultValues == null) {
                URL url = new URL(m_defaultValueFile);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer buf = new StringBuffer();
                int i;
                while ((i = reader.read()) != -1)
                    buf.append((char) i);
                m_defaultValues = buf.toString();
                if (log.isDebugEnabled())
                    log.debug("Obtained contents from the default value file " + m_defaultValueFile);
                File f = new File(url.getPath());
                m_fileUpdateable = f.exists() && f.isFile();
                // remove the EditBoxModel from the WidgetCache
                getUserSession().getWidgetCache(getComponentId()).removeModel("defaultValues");
            } else {
                if (log.isDebugEnabled())
                    log.debug("Contents have already been obtained from the default value file " + m_defaultValueFile);
            }
        } catch (IOException e) {
            String str = "Exception thrown while reading the default value file: " + m_defaultValueFile;
            log.error(str, e);
            throw new DefaultValueEditorException(DefaultValueEditorException.READ_FAILED, new Object[] {m_defaultValueFile}, e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }
    
    /** Writes the default values to file.
     */
    protected void saveDefaultValues() throws FrameworkException {
        Writer writer = null;
        try {
            if (m_fileUpdateable) {
                URL url = new URL(m_defaultValueFile);
                File f = new File(url.getPath());
                writer = new BufferedWriter(new FileWriter(f, false));
                writer.write(m_defaultValues != null ? m_defaultValues : "");
                writer.flush();
                if (log.isDebugEnabled())
                    log.debug("Saved contents to the default value file " + m_defaultValueFile);
            } else {
                if (log.isDebugEnabled())
                    log.debug("File cannot be updated since it cannot be read using File I/O. It is probably part of a JAR: " + m_defaultValueFile);
            }
        } catch (IOException e) {
            String str = "Exception thrown while writing to the default value file: " + m_defaultValueFile;
            log.error(str, e);
            throw new DefaultValueEditorException(DefaultValueEditorException.WRITE_FAILED, new Object[] {m_defaultValueFile}, e);
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
