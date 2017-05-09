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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.soa.services;

import org.apache.log4j.Logger;
import org.jaffa.security.VariationContext;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.session.IContextManager;
import org.jaffa.soa.domain.SOAEvent;

import java.io.*;
import java.util.*;

/**
 * Tracks if SOA events have been marked as disabled or enabled.  This class will not contain the entire list of events,
 * but will contain a subset.  Events not listed in this class will default to enabled
 */
public class SOAEventEnabledConfigurationImpl implements SOAEventEnabledConfiguration {

    private static final Logger log = Logger.getLogger(SOAEventEnabledConfiguration.class);

    // File name constants
    private static final String variationPlaceholder = "{VAR}";
    private static final String fileName = "soa-events-config_" + variationPlaceholder + ".properties";

    // Enabled and disabled constants
    private static final String enabled = "enabled";
    private static final String disabled = "disabled";

    // Name of the property in the application rules file to set the default event state
    private static final String propertyName = "jaffa.soa.events.defaultEventState";

    // True if events are enabled by default
    private final boolean areEventsEnabledByDefault;

    private final WeakHashMap<String, Properties> propertiesPerVariation = new WeakHashMap<String, Properties>();

    /**
     * Constructs the SOAEventEnabledConfigurationImpl and initializes its state from the ApplicationRules
     * configuration
     */
    public SOAEventEnabledConfigurationImpl() {
        Object property = ContextManagerFactory.instance().getProperty(propertyName);

        // If the property is not set or not marked as disabled, the value will be enabled
        areEventsEnabledByDefault = (property == null || !property.toString().equalsIgnoreCase(disabled));

        if(log.isInfoEnabled()){
            log.info("Events are " + (areEventsEnabledByDefault ? enabled : disabled) + " by default");
        }
    }

    /**
     * Specifies if events not in the configuration are to be considered enabled or disabled
     * If true any event that is not marked as disabled is considered enabled
     * If false an event that is not marked as enabled is considered disabled
     *
     * @return Default value for enabled/disabled state for events
     */
    public boolean areEventsEnabledByDefault() {
        return areEventsEnabledByDefault;
    }

    /**
     * Check if an event type is enabled by the name of the event
     *
     * @param eventName Name of the event to check
     * @return True if the event is enabled
     */
    public boolean isEnabled(String eventName) {
        Object value = getProperties().getProperty(eventName);

        String defaultValue = areEventsEnabledByDefault ? enabled : disabled;
        String nonDefaultValue = areEventsEnabledByDefault ? disabled : enabled;

        // If the the property doesn't exist or doesn't matches the non-default value
        // then return the default value flag
        if (value == null || !value.toString().equalsIgnoreCase(nonDefaultValue)) {
            if (log.isDebugEnabled()) {
                log.debug("Event " + defaultValue + " -> " + eventName);
            }

            return areEventsEnabledByDefault;
        }

        if (log.isDebugEnabled()) {
            log.debug("Event " + nonDefaultValue + " -> " + eventName);
        }

        return !areEventsEnabledByDefault;
    }

    /**
     * Check if an event type is enabled for a SOAEventQueueMessage
     *
     * @param event SOAEventQueueMessage to check if enabled
     * @return True if the event type is enabled
     */
    public boolean isEnabled(SOAEventQueueMessage event) {
        return isEnabled(event.getEventName());
    }

    /**
     * Check if an event type is enabled for a SOAEvent
     *
     * @param event SOAEvent to check if enabled
     * @return True if the event type is enabled
     */
    public boolean isEnabled(SOAEvent event) {
        return isEnabled(event.getEventName());
    }

    /**
     * Check if an event type is enabled for an object
     *
     * @param event Object to check if enabled
     * @return True if the event type is enabled
     */
    public boolean isEnabled(Object event) {
        // If the event type is not taken care of by a overloaded method then assume default
        return areEventsEnabledByDefault;
    }

    /**
     * Set an event type (by event name) as enabled/disabled
     *
     * @param eventName Name of the event to enable/disable
     * @param isEnabled True to enable or false to disable
     */
    public void setEnabled(String eventName, boolean isEnabled) {
        setEnabled(eventName, isEnabled, true);
    }

    /**
     * Set multiple event types (by event name) as enabled/disabled
     *
     * @param events Map of String/Boolean mapping to eventName/isEnabled
     */
    public void setEnabled(Map<String, Boolean> events) {
        for (Map.Entry<String, Boolean> event : events.entrySet()) {
            setEnabled(event.getKey(), event.getValue(), false);
        }
        savePropertiesToFile();
    }

    /**
     * Gets a list of all the events which are in the non-default state
     * If areEventsEnabledByDefault returns true, this is the list disabled events
     * If areEventsEnabledByDefault returns false, this is the list enabled events
     *
     * @return List of all the events which are disabled
     */
    public List<String> getEventsInNonDefaultState() {
        String nonDefaultValue = areEventsEnabledByDefault ? disabled : enabled;

        List<String> disabledEvents = new ArrayList<String>();
        for (Map.Entry<Object, Object> property : getProperties().entrySet()) {
            if (property.getValue().toString().equalsIgnoreCase(nonDefaultValue)) {
                disabledEvents.add(property.getKey().toString());
            }
        }
        return disabledEvents;
    }

    /**
     * Set an event type (by event name) as enabled/disabled
     *
     * @param eventName   Name of the event to enable/disable
     * @param isEnabled   True to enable or false to disable
     * @param saveChanges If true the changes will be immediately applied to the properties file, otherwise
     *                    the changes will not be applied to file until this method is called with saveChanges
     *                    set to true or savePropertiesToFile is called
     */
    private void setEnabled(String eventName, boolean isEnabled, boolean saveChanges) {
        if (isEnabled) {
            getProperties().setProperty(eventName, enabled);
        } else {
            getProperties().setProperty(eventName, disabled);
        }

        if (saveChanges) {
            savePropertiesToFile();
        }
    }

    /**
     * Gets the properties for the current variation of the product
     *
     * @return The properties for the current variation of the product
     */
    private Properties getProperties() {
        Properties properties = propertiesPerVariation.get(VariationContext.getVariation());
        if (properties == null) {
            properties = loadPropertiesFromFile();
            propertiesPerVariation.put(VariationContext.getVariation(), properties);
        }

        return properties;
    }

    /**
     * Loads the contents of the properties file used to store the enabled/disabled SOA events
     *
     * @return Properties containing SOA event name and enabled/disabled state.  If there is an error the an empty
     *         instance of Properties will be returned
     */
    private Properties loadPropertiesFromFile() {
        // Get or create the properties file
        File propertiesFile = getPropertiesFile();
        if (propertiesFile == null) {
            log.error("Error getting the SOA event config properties file");
            return new Properties();
        }

        // Read the file into the Properties object
        Properties properties = new Properties();
        try {
            FileReader reader = new FileReader(propertiesFile);
            properties.load(reader);
        } catch (FileNotFoundException fileNotFoundException) {
            log.error("SOA event config properties file not found", fileNotFoundException);
            return new Properties();
        } catch (IOException ioException) {
            log.error("Error reading the SOA event config properties file", ioException);
            return new Properties();
        }

        return properties;
    }

    /**
     * Saves the event properties to disk
     */
    private void savePropertiesToFile() {
        // Get or create the properties file
        File propertiesFile = getPropertiesFile();
        if (propertiesFile == null) {
            log.error("Error getting the SOA event config properties file");
            return;
        }

        // Read the file into the Properties object
        try {
            FileWriter writer = new FileWriter(propertiesFile);
            getProperties().store(writer, "");
        } catch (IOException ioException) {
            log.error("Error writing the SOA event config properties file", ioException);
            return;
        }
    }

    /**
     * Gets the handle to the properties file to read and write to
     *
     * @return Gets the handle to the properties file to read and write to
     */
    private File getPropertiesFile() {
        // Build the file name
        StringBuilder builder = new StringBuilder(ContextManagerFactory.instance().getProperty(IContextManager.PROPERTY_USER_PREFERENCES_FOLDER).toString());
        if (builder.length() > 0 && builder.charAt(builder.length() - 1) != File.separatorChar) {
            builder.append(File.separatorChar);
        }
        builder.append(fileName.replace(variationPlaceholder, VariationContext.getVariation()));

        // Get the file and create it if needed
        File propertiesFile = new File(builder.toString());
        if (!propertiesFile.exists()) {
            try {

                // check that the parent file directory exists first
                if (!propertiesFile.getParentFile().exists()) {
                    propertiesFile.getParentFile().mkdirs();
                    if (log.isDebugEnabled()) {
                        log.debug("Creating properties parent directory: "
                                + propertiesFile.getParentFile().getAbsolutePath());
                    }
                }

                // the parent file directory exists, create the file
                if (log.isDebugEnabled()) {
                    log.debug("Creating properties file: " + propertiesFile.getAbsolutePath());
                }
                propertiesFile.createNewFile();
            } catch (IOException ioException) {
                log.error("Failed to create the SOA event config properties file", ioException);
                return null;
            }
        }

        return propertiesFile;
    }
}
