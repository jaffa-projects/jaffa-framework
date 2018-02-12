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

package org.jaffa.applications.jaffa.modules.admin.components.labeleditor.ui;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.jaffa.applications.jaffa.modules.admin.components.labeleditor.ui.exceptions.LabelEditorException;
import org.jaffa.components.navigation.NavAccessor;
import org.jaffa.components.navigation.NavCache;
import org.jaffa.config.ApplicationResourceLoader;
import org.jaffa.config.Config;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.session.LocaleContext;
import org.jaffa.security.VariationContext;
import org.jaffa.util.FindFiles;
import org.jaffa.util.ListProperties;
import org.jaffa.util.PropertyMessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This is the component controller for the Label Editor.
 *
 * @author  GautamJ
 * @version 1.0
 */
public class LabelEditorComponent extends Component {
    
    private static Logger log = Logger.getLogger(LabelEditorComponent.class);
    
    /** The constant for the label. */
    public static String LABEL = "label";
    /** The constant used for keying the default value in each Map of the 'labels' Map. */
    public static String DEFAULT = "default";
    /** The constant used for keying the override value in each Map of the 'labels' Map. */
    public static String OVERRIDE = "override";
    
    /** This PATTERN is used to extract tokens from a label.
     * For eg: If a label is of the type '111[token1]222[token2]333[token3]444',
     * then the PATTERN can be used to extract 'token1', 'token2', 'token3' from it
     */
    public static final Pattern PATTERN = Pattern.compile("\\[(.+?)\\]"); // This uses the 'reluctant' qualifier '?'
    
    /** Holds value of property labelFilter. */
    private String m_labelFilter;
    
    /** Holds value of property displayOverridesOnly. */
    private Boolean m_displayOverridesOnly;
    
    /** Holds value of property searchPathForSourceFragments. */
    private String m_searchPathForSourceFragments = System.getProperty("java.class.path");
    
    /** Holds value of property sourceFragmentName. */
    private String m_sourceFragmentName = ".*ApplicationResources.pfragment";
    
    /** Holds value of property labels. */
    private Map m_labels;
    
    
    /** Getter for property labelFilter.
     * @return Value of property labelFilter.
     *
     */
    public String getLabelFilter() {
        return m_labelFilter;
    }
    
    /** Setter for property labelFilter.
     * @param labelFilter New value of property labelFilter.
     *
     */
    public void setLabelFilter(String labelFilter) {
        m_labelFilter = labelFilter;
    }
    
    /** Getter for property displayOverridesOnly.
     * @return Value of property displayOverridesOnly.
     *
     */
    public Boolean getDisplayOverridesOnly() {
        return m_displayOverridesOnly;
    }
    
    /** Setter for property displayOverridesOnly.
     * @param displayOverridesOnly New value of property displayOverridesOnly.
     *
     */
    public void setDisplayOverridesOnly(Boolean displayOverridesOnly) {
        m_displayOverridesOnly = displayOverridesOnly;
    }
    
    /** Getter for property searchPathForSourceFragments.
     * @return Value of property searchPathForSourceFragments.
     *
     */
    public String getSearchPathForSourceFragments() {
        return m_searchPathForSourceFragments;
    }
    
    /** Setter for property searchPathForSourceFragments.
     * @param searchPathForSourceFragments New value of property searchPathForSourceFragments.
     *
     */
    public void setSearchPathForSourceFragments(String searchPathForSourceFragments) {
        m_searchPathForSourceFragments = searchPathForSourceFragments;
    }
    
    /** Getter for property sourceFragmentName.
     * @return Value of property sourceFragmentName.
     *
     */
    public String getSourceFragmentName() {
        return m_sourceFragmentName;
    }
    
    /** Setter for property sourceFragmentName.
     * @param sourceFragmentName New value of property sourceFragmentName.
     *
     */
    public void setSourceFragmentName(String sourceFragmentName) {
        m_sourceFragmentName = sourceFragmentName;
    }
    
    /** Getter for property labels.
     * This is a Map of Label/Map objects; each Map containing the DEFAULT, OVERRIDE values.
     * @return Value of property labels.
     *
     */
    public Map getLabels() {
        return m_labels;
    }
    
    /** Getter for property labelEditorFormKey.
     * @return Value of property labelEditorFormKey.
     *
     */
    public FormKey getLabelEditorFormKey() {
        return new FormKey(LabelEditorForm.NAME, getComponentId());
    }
    
    /** This will retrieve the labels and return the FormKey for rendering the component.
     * @return the FormKey for rendering the component.
     * @throws FrameworkException if any error ocurrs in obtaining the labels from ApplicationResources.properties and the default and override files.
     */
    public FormKey display() throws FrameworkException {
        retrieveLabels();
        return getLabelEditorFormKey();
    }
    
    /** This retrieves the labels from ApplicationResources.properties and the default and override files.
     * It then populates the 'labels' Map.
     * @throws FrameworkException if any error occurs.
     */
    protected void retrieveLabels() throws FrameworkException {
        // clear the widget cache
        getUserSession().getWidgetCache(getComponentId()).clear();
        
        // The main map, which will contain Map objects keyed by the label
        m_labels = new LinkedHashMap();
        
        if (getLabelFilter() != null || (getDisplayOverridesOnly() != null && getDisplayOverridesOnly().booleanValue())) {
            try {
                Properties applicationResourcesDefaultProperties = null;
                Properties applicationResourcesOverrideProperties = null;

                String localeKey = localeKey(LocaleContext.getLocale());
                if (ApplicationResourceLoader.getApplicationResourcesManager().getApplicationResourcesLocaleRepository().query(localeKey) != null) {
                    applicationResourcesDefaultProperties = ApplicationResourceLoader.getInstance().getApplicationResourcesLocale(localeKey);
                    applicationResourcesOverrideProperties = ApplicationResourceLoader.getInstance().getApplicationResourcesOverride(localeKey);
                } else{
                    applicationResourcesDefaultProperties = ApplicationResourceLoader.getInstance().getApplicationResourcesDefault();
                    applicationResourcesOverrideProperties = ApplicationResourceLoader.getInstance().getApplicationResourcesOverride(null);
                }
                
				if (applicationResourcesDefaultProperties == null || applicationResourcesDefaultProperties.size() < 1
						|| applicationResourcesOverrideProperties == null) {
					String str = "There is no default, override properties. The Label Editor component cannot be used";
					log.error(str);
					throw new LabelEditorException(LabelEditorException.CONFIG_ERROR);
				}
                
                for (Enumeration defaultLabels = applicationResourcesDefaultProperties.propertyNames(); defaultLabels.hasMoreElements(); ) {
                    String label = (String) defaultLabels.nextElement();
                    
                    // The label should match the labelFilter, if specified
                    if (getLabelFilter() != null && !label.matches(getLabelFilter()))
                        continue;
                    
                    // The label should be overridden, if the 'displayOverridesOnly' property is true
                    if (getDisplayOverridesOnly() != null && getDisplayOverridesOnly().booleanValue() && !applicationResourcesOverrideProperties.containsKey(label))
                        continue;
                    
                    final Map<String, String> map = new HashMap();
                    map.put(DEFAULT, getEncodingValue(applicationResourcesDefaultProperties,label));
                    map.put(OVERRIDE,getEncodingValue(applicationResourcesOverrideProperties,label));
                    m_labels.put(label, map);
                    
                    // Add extra entries if the default label has any tokens!
                    // But do that only if a filter is specified and if the 'displayOverridesOnly' property is false
                    if (getLabelFilter() != null && (getDisplayOverridesOnly() == null || !getDisplayOverridesOnly().booleanValue()))
                        addInnerTokens(map.get(DEFAULT), m_labels, applicationResourcesDefaultProperties, applicationResourcesOverrideProperties);
                }
            } catch (Exception e) {
                String str = "Exception thrown while reading the ApplicationResources default and override files";
                log.error(str, e);
                throw new LabelEditorException(LabelEditorException.READ_FAILED, null, e);
            }
        }
    }

  /**
   * Searches for the property with the specified key in this property list.
   * If the key is not found in this property list, the default property list,
   * and its defaults, recursively, are then checked. The method returns
   * <code>null</code> if the property is not found.
   *
   * @param properties the property
   * @param   key the property key.
   * @return <code>String</code>
   */
  private String getEncodingValue(final Properties properties, final String key){
      final String value = properties.getProperty(key);
      if (value == null)
        return null;
      try {
        return new String(value.getBytes("ISO-8859-1"), "UTF-8");
        } catch (final UnsupportedEncodingException e) {
          throw new RuntimeException("Encoding not supported", e);
        }
    }
    
    /** This will perform the following tasks.
     * - Add an entry for each label having an override value, to the ApplicationResources.override file.
     * - Remove all entries from the ApplicationResources.override file, for which the override value is blank
     * - Migrate all changes to the ApplicationResources.properties file by invoking InitApp.generateApplicationResources()
     * - Flush the struts properties cache by invoking the flushCache() method on the MessageResources, provided its an instance of 'org.jaffa.util.PropertyMessageResources'
     * @param request The request we are processing.
     * @param messageResources The MessageResources object, which will be flushed, if its an instance of 'org.jaffa.util.PropertyMessageResources'.
     * @throws FrameworkException if any error occurs.
     */
    protected void performSave(HttpServletRequest request, MessageResources messageResources) throws FrameworkException {
        String applicationResourcesOverrideLocation = (String) Config.getProperty(Config.PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION, null);
        
        try {
            // load the ApplicationResources.override file
            Properties applicationResourcesOverrideProperties = loadPropertiesFromFile(applicationResourcesOverrideLocation, true);
            
            ApplicationResourceLoader appResourceLoader = ApplicationResourceLoader.getInstance();
            String localeKey = localeKey(LocaleContext.getLocale());

            // Either update or remove a property
            for (Iterator itr = m_labels.keySet().iterator(); itr.hasNext(); ) {
                String label = (String) itr.next();
                Map map = (Map) m_labels.get(label);
                String override = (String) map.get(OVERRIDE);
				if (override != null) {
					//setting the override label into override file
					applicationResourcesOverrideProperties.setProperty(label, override);
					//Applying the changes into ApplicationResources in memory
					appResourceLoader.getLocaleProperties(localeKey).setProperty(label, override);
				} else {
					//removing the override from file if there is any
					applicationResourcesOverrideProperties.remove(label);
					//remove it from memory
                    if (ApplicationResourceLoader.getApplicationResourcesManager().getApplicationResourcesLocaleRepository().query(localeKey) != null) {
                        appResourceLoader.getApplicationResourcesOverride(localeKey).remove(label);
                    } else {
                        appResourceLoader.getApplicationResourcesOverride(null);
                    }
                    //reverting/leaving the default value if the override removed.
					appResourceLoader.getLocaleProperties(LocaleContext.getLocale().toString())
							.setProperty(label, appResourceLoader.getApplicationResourcesDefault().getProperty(label));
				}
            }
            
            // Sort the  ApplicationResources.override file
            if (applicationResourcesOverrideProperties instanceof ListProperties)
                ((ListProperties) applicationResourcesOverrideProperties).sort();
            
            // Now save the ApplicationResources.override file into user data directory
            storePropertiesToFile(applicationResourcesOverrideProperties, applicationResourcesOverrideLocation);
            
            // Migrate all changes to the ApplicationResources.properties file by invoking InitApp.generateApplicationResources()
            //InitApp.generateApplicationResources();            
            
            // Flush the struts properties cache by invoking the flushCache() method on the MessageResources, provided its an instance of 'org.jaffa.util.PropertyMessageResources'
            if (messageResources != null && messageResources instanceof PropertyMessageResources) {
                ((PropertyMessageResources) messageResources).flushCache();
                if (log.isDebugEnabled())
                    log.debug("Flushed the struts message cache");
            } else {
                if (log.isDebugEnabled())
                    log.debug("The struts message cache has not been implemented using the org.jaffa.util.PropertyMessageResources class. Reload the webapp to see the changes to the labels.");
            }
            
            clearCacheAndRefreshMenu(request);
            
            
        } catch (IOException e) {
            String str = "Exception thrown while storing the override values";
            log.error(str, e);
            throw new LabelEditorException(LabelEditorException.WRITE_FAILED, null, e);
        }
    }
    
    
    /** This will perform the following tasks.
     * - Clears the cache
     * - Refreshes the menu for current User session.
     */
    private void clearCacheAndRefreshMenu(HttpServletRequest request){
        // Clear the cache
        //NavCache.getInstance().clearCache();
        //get the HttpSession from User Session
        HttpSession session = (HttpSession)getUserSession().getHttpSession();
        //refresh the Menu for current session
        NavAccessor.getNavAccessor(request).clearSession(session);
    }
    
    /** This will perform the following tasks.
     * - Migrate all the displayed override values to the appropriate fragment file
     * - Migrate all the displayed override values to the ApplicationResources.default file
     * - Delete all displayed override entries from the ApplicationResources.override file
     * - Migrate all changes to the ApplicationResources.properties file by invoking InitApp.generateApplicationResources()
     * - Flush the struts properties cache by invoking the flushCache() method on the MessageResources, provided its an instance of 'org.jaffa.util.PropertyMessageResources'
     * @param messageResources The MessageResources object, which will be flushed, if its an instance of 'org.jaffa.util.PropertyMessageResources'.
     * @throws FrameworkException if any error occurs.
     */
    protected void performSyncToSource(MessageResources messageResources) throws FrameworkException {
        String applicationResourcesDefaultLocation = (String) Config.getProperty(Config.PROP_APPLICATION_RESOURCES_DEFAULT_LOCATION, null);
        String applicationResourcesOverrideLocation = (String) Config.getProperty(Config.PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION, null);
        
        try {
            // load the ApplicationResources.default and ApplicationResources.override files
            Properties applicationResourcesDefaultProperties = null;
            Properties applicationResourcesOverrideProperties = null;

            String localeKey = localeKey(LocaleContext.getLocale());
            if (ApplicationResourceLoader.getApplicationResourcesManager().getApplicationResourcesLocaleRepository().query(localeKey) != null) {
                applicationResourcesDefaultProperties = ApplicationResourceLoader.getInstance().getApplicationResourcesLocale(localeKey);
                applicationResourcesOverrideProperties = ApplicationResourceLoader.getInstance().getApplicationResourcesOverride(localeKey);
            } else{
                applicationResourcesDefaultProperties = ApplicationResourceLoader.getInstance().getApplicationResourcesDefault();
                applicationResourcesOverrideProperties = ApplicationResourceLoader.getInstance().getApplicationResourcesOverride(null);
            }
            
            // Migrate all the displayed override values to the appropriate fragment file
            if (m_searchPathForSourceFragments != null && m_sourceFragmentName != null) {
                FindFiles ff = new FindFiles(m_searchPathForSourceFragments, m_sourceFragmentName);
                List files = ff.getList();
                if (files != null) {
                    for (Iterator i = files.iterator(); i.hasNext(); ) {
                        File file = (File) i.next();
                        Properties properties = loadPropertiesFromFile(file.getPath(), true);
                        boolean modified = false;
                        for (Iterator itr = m_labels.keySet().iterator(); itr.hasNext(); ) {
                            String label = (String) itr.next();
                            if (properties.containsKey(label)) {
                                Map map = (Map) m_labels.get(label);
                                String override = (String) map.get(OVERRIDE);
                                if (override != null) {
                                    // Migrate to the source file and ApplicationResources.default file
                                    // Delete from the ApplicationResources.override file
                                    properties.setProperty(label, override);
                                    //applicationResourcesDefaultProperties.setProperty(label, override);
                                    applicationResourcesOverrideProperties.remove(label);
                                    if (log.isInfoEnabled())
                                        log.info(file.getPath() + ": Migrated the override value: " + label + '=' + override);
                                    modified = true;
                                }
                            }
                        }
                        // Update the fragment file, maintaining existing comments and order
                        if (modified)
                            storePropertiesToFile(properties, file.getPath());
                    }
                }
            }
            
            // Save the ApplicationResources.default and ApplicationResources.override files
            //storePropertiesToFile(applicationResourcesDefaultProperties, applicationResourcesDefaultLocation);
            storePropertiesToFile(applicationResourcesOverrideProperties, applicationResourcesOverrideLocation);
            
            // Migrate all changes to the ApplicationResources.properties file by invoking InitApp.generateApplicationResources()
            //InitApp.generateApplicationResources();
            
            // Flush the struts properties cache by invoking the flushCache() method on the MessageResources, provided its an instance of 'org.jaffa.util.PropertyMessageResources'
            if (messageResources != null && messageResources instanceof PropertyMessageResources) {
                ((PropertyMessageResources) messageResources).flushCache();
                if (log.isDebugEnabled())
                    log.debug("Flushed the struts message cache");
            } else {
                if (log.isDebugEnabled())
                    log.debug("The struts message cache has not been implemented using the org.jaffa.util.PropertyMessageResources class. Reload the webapp to see the changes to the labels.");
            }
            
        } catch (IOException e) {
            String str = "Sync to Source failed";
            log.error(str, e);
            throw new LabelEditorException(LabelEditorException.SYNC_TO_SOURCE_FAILED, null, e);
        }
    }
    
    
    /** Reads the input file and loads its properties into a Properties object.
     * @param fileName The file to be loaded.
     * @param maintainFileFormat If true, then the comments and order for each property will be maintained.
     * @throws IOException if any error occurs.
     * @return a Properties object loaded with the properties from the input file.
     */
    protected static Properties loadPropertiesFromFile(String fileName, boolean maintainFileFormat) throws IOException {
        InputStreamReader is = null;
        Properties properties;
        if (maintainFileFormat)
            properties = new ListProperties();
        else
            properties = new Properties();
        try {
            final File file = new File(fileName);
            if (file.exists()) {
                is = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
                properties.load(is);
                if (log.isDebugEnabled())
                    log.debug("Loaded properties from " + fileName);
            } else {
                if (log.isDebugEnabled())
                    log.debug("No properties loaded, since file does not exist " + fileName);
            }
            return properties;
        } finally {
            if (is != null)
                is.close();
        }
    }
    
    /** Stores the input properties into the given file.
     * The order in which the properties are written to the file is not guaranteed. Comments in an existing file will be lost.
     * @param properties The properties to be stored.
     * @param fileName The file into which the properties will be stored.
     * @throws IOException if any error occurs.
     */
    protected static void storePropertiesToFile(Properties properties, String fileName) throws IOException {
        OutputStream os = null;
        try {
            // Create the directory for the input fileName, if it doesn't already exist.
            File f = new File(fileName);
            if (!f.exists()) {
                if (log.isDebugEnabled())
                    log.debug("File " + fileName + " does not exist. Check the existence of its directory");
                File dir = f.getParentFile();
                if (dir != null && !dir.exists()) {
                    if (log.isDebugEnabled())
                        log.debug("Directory " + dir.getPath() + " does not exist. Creating it..");
                    dir.mkdirs();
                }
            }
            os = new BufferedOutputStream(new FileOutputStream(f, false));
            properties.store(os, null);
            if (log.isDebugEnabled())
                log.debug("Saved properties to " + fileName);
        } finally {
            if (os != null)
                os.close();
        }
    }
    
    /** Add extra entries if the default label has any tokens.
     */
    protected static void addInnerTokens(String defaultLabel, Map mainMap, Properties applicationResourcesDefaultProperties
            , Properties applicationResourcesOverrideProperties) {
        if (defaultLabel != null) {
            Matcher matcher = PATTERN.matcher(defaultLabel);
            while (matcher.find()) {
                String s = matcher.group(1);
                // Ensure that we do not get into an infinite loop
                if (!mainMap.containsKey(s)) {
                    Map map = new HashMap();
                    map.put(DEFAULT, applicationResourcesDefaultProperties.getProperty(s));
                    map.put(OVERRIDE, applicationResourcesOverrideProperties.getProperty(s));
                    mainMap.put(s, map);
                    
                    // Add extra entries if the default label has any tokens!
                    addInnerTokens((String) map.get(DEFAULT), mainMap, applicationResourcesDefaultProperties, applicationResourcesOverrideProperties);
                }
            }
        }
    }

    private String localeKey(Locale locale) {
        if (locale == null)
            return "";
        else if (locale.getVariant() != null && locale.getVariant().length() > 0)
            return locale.toString();
        else
            return locale.toString() + '_' + VariationContext.getVariation();
    }
    
}

