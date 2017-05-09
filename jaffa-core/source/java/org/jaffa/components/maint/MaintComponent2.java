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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.components.dto.HeaderDto;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.DomainObjectChangedException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.presentation.portlet.HistoryNav;
import org.jaffa.security.VariationContext;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.URLHelper;

/** This is the base class for all Maintenance components created by using the object_maintenance_2_0 pattern.
 * It has the following properties -
 *    1- mode : Indicates if the component is in Create, Update or Delete mode.
 *    2- refreshData : Indicates if the data needs to be refreshed. This happens when the data is modified by another user.
 *    3- currentScreenCounter : A maintenance component may consist of more than one screen. This counter is used to identify the current screen being displayed. It will start with count=0
 *
 * A Maintenance class will have to provide an implementation for the doPrevalidateCreate(), doCreate(), doPrevalidateUpdate(), doUpdate(), doDelete(), doRetrieve(), addScreens() methods.
 * @author  GautamJ
 */
public abstract class MaintComponent2 extends Component implements IMaintComponent {
    
    private static final Logger log = Logger.getLogger(MaintComponent2.class);
    
    private static final String PROPERTIES_FILE_NAME = "ComponentDefaultValues.properties";
    private static final String PACKAGE_SUFFIX_TO_REMOVE = "/ui";
    private static final String APPEND_TO_PACKAGE = '/' + PROPERTIES_FILE_NAME;
    
    private int m_mode;
    private boolean m_refreshData;
    private List m_screens;
    private int m_currentScreenCounter = 0;
    private Collection m_displayOnlyFields;
    
    private Collection m_createListeners;
    private Collection m_updateListeners;
    private Collection m_deleteListeners;
    private HeaderDto m_headerDto = null;
    
    
    // **************************************************************
    // Component Properties
    // **************************************************************
    
    /** Getter for property mode.
     * @return Value of property mode.
     */
    public int getMode() {
        return m_mode;
    }
    
    /** Setter for property mode.
     * @param mode New value of property mode.
     */
    public void setMode(int mode) {
        m_mode = mode;
    }
    
    /** Returns true if this is create mode.
     * @return true if this is create mode.
     */
    public boolean isCreateMode() {
        return getMode() == MODE_CREATE;
    }
    
    /** Returns true if this is update mode.
     * @return true if this is update mode.
     */
    public boolean isUpdateMode() {
        return getMode() == MODE_UPDATE;
    }
    
    /** Returns true if this is delete mode.
     * @return true if this is delete mode.
     */
    public boolean isDeleteMode() {
        return getMode() == MODE_DELETE;
    }
    
    /** Getter for property refreshData.
     * @return Value of property refreshData.
     */
    public boolean isRefreshData() {
        return m_refreshData;
    }
    
    /** Setter for property refreshData.
     * @param refreshData New value of property refreshData.
     */
    protected void setRefreshData(boolean refreshData) {
        m_refreshData = refreshData;
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
    public MaintComponent2.Screen[] getScreens() {
        return (MaintComponent2.Screen[]) m_screens.toArray(new MaintComponent2.Screen[0]);
    }
    
    /** Getter for the current Screen.
     * @return the current screen.
     */
    public MaintComponent2.Screen determineCurrentScreen() {
        return (MaintComponent2.Screen) m_screens.get(m_currentScreenCounter);
    }
    
    /** Getter for the next Screen.
     * This takes into account the mode and if the following screen is available in create or update modes.
     * A null will be returned in case no appropriate next screen is available.
     * @return the next screen.
     */
    public MaintComponent2.Screen determineNextScreen() {
        return determineAndSetNextScreen(false);
    }
    
    /** This sets the currentScreenCounter to point to the next screen.
     * This takes into account the mode and if the following screen is available in create or update modes.
     * A null will be returned in case no appropriate next screen is available.
     * @return the next screen.
     */
    public MaintComponent2.Screen determineAndSetNextScreen() {
        return determineAndSetNextScreen(true);
    }
    
    private MaintComponent2.Screen determineAndSetNextScreen(boolean setCurrentScreenCounter) {
        MaintComponent2.Screen screen = null;
        for (int i = m_currentScreenCounter + 1; i < m_screens.size(); i++) {
            screen = (MaintComponent2.Screen) m_screens.get(i);
            if (isCreateMode() && screen.isAvailableInCreateMode() ||
                    (isUpdateMode() && screen.isAvailableInUpdateMode())) {
                if (setCurrentScreenCounter)
                    m_currentScreenCounter = i;
                break;
            } else
                screen = null;
        }
        return screen;
    }
    
    /** Getter for the previous Screen.
     * This takes into account the mode and if the previous screen is available in create or update modes.
     * A null will be returned in case no appropriate previous screen is available.
     * @return the previous screen.
     */
    public MaintComponent2.Screen determinePreviousScreen() {
        return determineAndSetPreviousScreen(false);
    }
    
    /** This sets the currentScreenCounter to point to the previous screen.
     * This takes into account the mode and if the previous screen is available in create or update modes.
     * A null will be returned in case no appropriate previous screen is available.
     * @return the previous screen.
     */
    public MaintComponent2.Screen determineAndSetPreviousScreen() {
        return determineAndSetPreviousScreen(true);
    }
    
    private MaintComponent2.Screen determineAndSetPreviousScreen(boolean setCurrentScreenCounter) {
        MaintComponent2.Screen screen = null;
        for (int i = m_currentScreenCounter - 1; i >= 0; i--) {
            screen = (MaintComponent2.Screen) m_screens.get(i);
            if (isCreateMode() && screen.isAvailableInCreateMode() ||
                    (isUpdateMode() && screen.isAvailableInUpdateMode())) {
                if (setCurrentScreenCounter)
                    m_currentScreenCounter = i;
                break;
            } else
                screen = null;
        }
        return screen;
    }
    
    /** Getter for the current screen's FormKey.
     * @return the FormKey for the current screen.
     */
    public FormKey determineFormKey() {
        return new FormKey(determineCurrentScreen().getFormName(), getComponentId());
    }
    
    
    /** This will mark a field as 'DisplayOnly'.
     * @param fieldName The field to be marked as 'DisplayOnly'.
     */
    public void addDisplayOnlyField(String fieldName) {
        if (m_displayOnlyFields == null)
            m_displayOnlyFields = new HashSet();
        m_displayOnlyFields.add(fieldName);
    }
    
    /** Returns a true if a field has been marked as 'DisplayOnly'.
     * @param fieldName The field to be checked.
     * @return a true if a field has been marked as 'DisplayOnly'
     */
    public boolean isDisplayOnlyField(String fieldName) {
        return m_displayOnlyFields != null ? m_displayOnlyFields.contains(fieldName) : false;
    }
    
    // **************************************************************
    // Additional methods
    // **************************************************************
    
    /** Adds a listener.
     * @param listener the listener.
     */
    public void addCreateListener(ICreateListener listener) {
        if (m_createListeners == null)
            m_createListeners = new HashSet();
        m_createListeners.add(listener);
    }
    
    /** Removes a listener.
     * @param listener the listener.
     * @return true if the listener was removed.
     */
    public boolean removeCreateListener(ICreateListener listener) {
        if (m_createListeners != null)
            return m_createListeners.remove(listener);
        else
            return false;
    }
    
    /** Adds a listener.
     * @param listener the listener.
     */
    public void addUpdateListener(IUpdateListener listener) {
        if (m_updateListeners == null)
            m_updateListeners = new HashSet();
        m_updateListeners.add(listener);
    }
    
    /** Removes a listener.
     * @param listener the listener.
     * @return true if the listener was removed.
     */
    public boolean removeUpdateListener(IUpdateListener listener) {
        if (m_updateListeners != null)
            return m_updateListeners.remove(listener);
        else
            return false;
    }
    
    /** Adds a listener.
     * @param listener the listener.
     */
    public void addDeleteListener(IDeleteListener listener) {
        if (m_deleteListeners == null)
            m_deleteListeners = new HashSet();
        m_deleteListeners.add(listener);
    }
    
    /** Removes a listener.
     * @param listener the listener.
     * @return true if the listener was removed.
     */
    public boolean removeDeleteListener(IDeleteListener listener) {
        if (m_deleteListeners != null)
            return m_deleteListeners.remove(listener);
        else
            return false;
    }
    
    
    /** Based on the mode and input parameters, this will either delete the domain object, or initialize the screen for updates, or bring up a blank screen.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey.
     */
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        if (m_screens == null) {
            m_screens = new ArrayList();
            addScreens(m_screens);
            
            
            // move to next screen, if the current screen is unavailable in current-mode
            MaintComponent2.Screen screen = determineCurrentScreen();
            if (isCreateMode() && !screen.isAvailableInCreateMode() ||
                    (isUpdateMode() && !screen.isAvailableInUpdateMode()))
                determineAndSetNextScreen();
        }
        
        if(isDeleteMode()) {
            delete(false);
        } else if(isUpdateMode()) {
            initDropDownCodes();
            retrieve();
        } else {
            initDropDownCodes();
            initializeData();
        }
        if(isDeleteMode())
            return quitAndReturnToCallingScreen();
        else
            return determineFormKey();
    }
    
    /** This clears the internal collection of listeners.
     * It then invokes the quit() method of the base class.
     */
    public void quit() {
        if (m_createListeners != null) {
            m_createListeners.clear();
            m_createListeners = null;
        }
        if (m_updateListeners != null) {
            m_updateListeners.clear();
            m_updateListeners = null;
        }
        if (m_deleteListeners != null) {
            m_deleteListeners.clear();
            m_deleteListeners = null;
        }
        super.quit();
    }
    
    
    /** This will invoke the doPrevalidateCreate() method to perform prevalidations before creating a domain object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    public void prevalidateCreate() throws ApplicationExceptions, FrameworkException {
        doPrevalidateCreate();
    }
    
    /** This will invoke the doCreate() method to create a new domain object. It will then invoke all the Create Listeners.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    public void create() throws ApplicationExceptions, FrameworkException {
        doCreate();
        invokeCreateListeners();
    }
    
    /** This will invoke the doPrevalidateUpdate() method to perform prevalidations before updating a domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to an update.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    public void prevalidateUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        try {
            doPrevalidateUpdate(performDirtyReadCheck);
        } catch (ApplicationExceptions e) {
            // check for the DomainObjectChangedException and then rethrow the ApplicationExceptions
            for (Iterator i = e.iterator(); i.hasNext(); ) {
                if (i.next() instanceof DomainObjectChangedException) {
                    if (log.isDebugEnabled())
                        log.debug("DomainObjectChangedException has been thrown during the update. The JSP should now show the Refresh button instead of the Save/Delete/Finish buttons");
                    setRefreshData(true);
                    break;
                }
            }
            throw e;
        }
    }
    
    /** This will invoke the doUpdate() method to update the domain object. It will then invoke all the Update Listeners.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to an update.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    public void update(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        try {
            doUpdate(performDirtyReadCheck);
        } catch (ApplicationExceptions e) {
            // check for the DomainObjectChangedException and then rethrow the ApplicationExceptions
            for (Iterator i = e.iterator(); i.hasNext(); ) {
                if (i.next() instanceof DomainObjectChangedException) {
                    if (log.isDebugEnabled())
                        log.debug("DomainObjectChangedException has been thrown during the update. The JSP should now show the Refresh button instead of the Save/Delete/Finish buttons");
                    setRefreshData(true);
                    break;
                }
            }
            throw e;
        }
        invokeUpdateListeners();
    }
    
    /** This will invoke the doDelete() method to delete the domain object. It will then invoke all the Delete Listeners.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to a delete.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    public void delete(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        try {
            doDelete(performDirtyReadCheck);
        } catch (ApplicationExceptions e) {
            // check for the DomainObjectChangedException and then rethrow the ApplicationExceptions
            for (Iterator i = e.iterator(); i.hasNext(); ) {
                if (i.next() instanceof DomainObjectChangedException) {
                    if (log.isDebugEnabled())
                        log.debug("DomainObjectChangedException has been thrown during the delete. The JSP should now show the Refresh button instead of the Save/Delete/Finish buttons");
                    setRefreshData(true);
                    break;
                }
            }
            throw e;
        }
        invokeDeleteListeners();
    }
    
    /** This will invoke the doRetrieve() method to retrieve the domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    public void retrieve() throws ApplicationExceptions, FrameworkException {
        doRetrieve();
    }
    
    
    
    /** Returns a Collection of ICreateListener objects.
     * @return a Collection of ICreateListener objects.
     */
    protected Collection getCreateListeners() {
        return m_createListeners;
    }
    
    /** Returns a Collection of IUpdateListener objects.
     * @return a Collection of IUpdateListener objects.
     */
    protected Collection getUpdateListeners() {
        return m_updateListeners;
    }
    
    /** Returns a Collection of IDeleteListener objects.
     * @return a Collection of IDeleteListener objects.
     */
    protected Collection getDeleteListeners() {
        return m_deleteListeners;
    }
    
    /** Invokes the createDone() method of the registered ICreateListener objects in the same thread.
     */
    protected void invokeCreateListeners() {
        invokeCreateListeners(null);
    }
    
    /** Invokes the createDone() method of the registered ICreateListener objects in the same thread.
     * @param eventObject The EventObject which will probably contain the component itself.
     */
    protected void invokeCreateListeners(EventObject eventObject) {
        if (m_createListeners != null) {
            if (eventObject == null)
                eventObject = new EventObject(this);
            
            for (Iterator i = m_createListeners.iterator(); i.hasNext(); )
                ( (ICreateListener) i.next() ).createDone(eventObject);
        }
    }
    
    /** Invokes the updateDone() method of the registered IUpdateListener objects in the same thread.
     */
    protected void invokeUpdateListeners() {
        invokeUpdateListeners(null);
    }
    
    /** Invokes the updateDone() method of the registered IUpdateListener objects in the same thread.
     * @param eventObject The EventObject which will probably contain the component itself.
     */
    protected void invokeUpdateListeners(EventObject eventObject) {
        if (m_updateListeners != null) {
            if (eventObject == null)
                eventObject = new EventObject(this);
            
            for (Iterator i = m_updateListeners.iterator(); i.hasNext(); )
                ( (IUpdateListener) i.next() ).updateDone(eventObject);
        }
    }
    
    /** Invokes the deleteDone() method of the registered IDeleteListener objects in the same thread.
     */
    protected void invokeDeleteListeners() {
        invokeDeleteListeners(null);
    }
    
    /** Invokes the deleteDone() method of the registered IDeleteListener objects in the same thread.
     * @param eventObject The EventObject which will probably contain the component itself.
     */
    protected void invokeDeleteListeners(EventObject eventObject) {
        if (m_deleteListeners != null) {
            if (eventObject == null)
                eventObject = new EventObject(this);
            
            for (Iterator i = m_deleteListeners.iterator(); i.hasNext(); )
                ( (IDeleteListener) i.next() ).deleteDone(eventObject);
        }
    }
    
    /** Clears the WidgetCache, removing all the WidgetModels. It also resets the flags.
     */
    protected void uncacheWidgetModels() {
        getUserSession().getWidgetCache(getComponentId()).clear();
        setRefreshData(false);
    }
    
    /** Returns the HeaderDto. This can be used for passing the header info to the Tx, where required.
     * @return the HeaderDto.
     */
    protected HeaderDto getHeaderDto() {
        if (m_headerDto == null) {
            m_headerDto = new HeaderDto();
            m_headerDto.setUserId(getUserSession().getUserId());
            m_headerDto.setVariation(VariationContext.getVariation());
        }
        return m_headerDto;
    }
    
    /** This method is invoked by the display() method when the component is run in the CREATE_MODE.
     * It sets the default values in the component by reading them from the ComponentDefaultValues.properties file.
     * This file should be in the base package of the component.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void initializeData() throws ApplicationExceptions, FrameworkException {
        // NOTE: This can be enhanced to load the default values for a base component too !!
        
        InputStream inStream = null;
        try {
            // Load the file containing the default values for the component
            try {
                URL defaultValuesUrl = determineDefaultValuesUrl(getClass());
                inStream = defaultValuesUrl.openStream();
            } catch (Exception e) {
                if (log.isDebugEnabled())
                    log.debug("File to load the default component values was not found");
                return;
            }
            Properties defaultValues = new Properties();
            defaultValues.load(inStream);
            
            // Iterate through the properties and set the value on this component
            if (defaultValues.isEmpty()) {
                if (log.isDebugEnabled())
                    log.debug("No default component values found");
            } else {
                for (Iterator i = defaultValues.entrySet().iterator(); i.hasNext(); ) {
                    Map.Entry defaultValueEntry = (Map.Entry) i.next();
                    String fieldName = (String) defaultValueEntry.getKey();
                    String defaultValue = (String) defaultValueEntry.getValue();
                    BeanHelper.setField(this, fieldName, defaultValue);
                    if (log.isDebugEnabled())
                        log.debug("Initialized " + fieldName + " to " + defaultValue);
                }
            }
        } catch (Exception e) {
            log.warn("Error in initializing data on the component", e);
        } finally {
            try {
                if (inStream != null)
                    inStream.close();
            } catch (Exception e) {
                log.warn("Error in initializing data on the component", e);
            }
        }
    }
    
    /** The Component should override this method to retrieve the set of codes for dropdowns in a screen, if any are required.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void initDropDownCodes() throws ApplicationExceptions, FrameworkException {
    }
    
    
    
    /** The Component should provide an implementation for this method to perform prevalidations before creating a domain object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected abstract void doPrevalidateCreate() throws ApplicationExceptions, FrameworkException;
    
    /** The Component should provide an implementation for this method to create a domain object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected abstract void doCreate() throws ApplicationExceptions, FrameworkException;
    
    /** The Component should provide an implementation for this method to perform prevalidations before updating a domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to an update.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected abstract void doPrevalidateUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException;
    
    /** The Component should provide an implementation for this method to update the domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to an update.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected abstract void doUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException;
    
    /** The Component should provide an implementation for this method to delete the domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to a delete.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected abstract void doDelete(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException;
    
    /** The Component should provide an implementation for this method to retrieve the domain object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected abstract void doRetrieve() throws ApplicationExceptions, FrameworkException;
    
    /** The Component should provide an implementation for this method to provide the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected abstract void addScreens(List screens);
    
    
    
    
    /** A maintenance component may consist of more than one screen. An instance of this class will represent each screen of the component
     */
    public class Screen {
        private String m_formName;
        private boolean m_availableInUpdateMode;
        private boolean m_availableInCreateMode;
        private boolean m_saveActionAvailableInCreateMode;
        private boolean m_performTxValidationOnNextAction;
        
        /** This will create an instance of a maintenance Screen.
         * @param formName the Struts GlobalForward for the screen.
         * @param availableInUpdateMode determines if this screen is available in update mode.
         * @param availableInCreateMode determines if this screen is available in create mode.
         * @param saveActionAvailableInCreateMode determines if save action is available for this screen.
         * @param performTxValidationOnNextAction determines if Tx validation will be performed when the Next action is invoked on this screen.
         */
        public Screen(String formName, boolean availableInUpdateMode, boolean availableInCreateMode, boolean saveActionAvailableInCreateMode, boolean performTxValidationOnNextAction) {
            m_formName = formName;
            m_availableInUpdateMode = availableInUpdateMode;
            m_availableInCreateMode = availableInCreateMode;
            m_saveActionAvailableInCreateMode = saveActionAvailableInCreateMode;
            m_performTxValidationOnNextAction = performTxValidationOnNextAction;
        }
        
        /** Getter for property formName.
         * @return Value of property formName.
         */
        public String getFormName() {
            return m_formName;
        }
        
        /** Getter for property availableInUpdateMode.
         * @return Value of property availableInUpdateMode.
         */
        public boolean isAvailableInUpdateMode() {
            return m_availableInUpdateMode;
        }
        
        /** Getter for property availableInCreateMode.
         * @return Value of property availableInCreateMode.
         */
        public boolean isAvailableInCreateMode() {
            return m_availableInCreateMode;
        }
        
        /** Getter for property saveActionAvailableInCreateMode.
         * @return Value of property saveActionAvailableInCreateMode.
         */
        public boolean isSaveActionAvailableInCreateMode() {
            return m_saveActionAvailableInCreateMode;
        }
        
        /** Getter for property performTxValidationOnNextAction.
         * @return Value of property performTxValidationOnNextAction.
         */
        public boolean isPerformTxValidationOnNextAction() {
            return m_performTxValidationOnNextAction;
        }
    }
    
    /** A helper routine to return the URL for the properties file containing default values for a component.
     * The properties file is assumed to be in the base package of the component with the name ComponentDefaultValues.properties'.
     * This method will try to locate the file on the filesystem.
     * This allows the application to pick up any changes to the file.
     * However, if the file is part of a jar, then it'll be loaded by the classloader.
     * A null will be returned if no file is found.
     * @param componentClass The component class.
     * @return the URL for the properties file containing default values for a component.
     */
    public static URL determineDefaultValuesUrl(Class componentClass) {
        URL url = null;
        
        // Determine the name of the file containing the default values
        String defaultValuesFileName = null;
        String packageName = componentClass.getPackage().getName().replace('.', '/');
        if (packageName.endsWith(PACKAGE_SUFFIX_TO_REMOVE))
            defaultValuesFileName = packageName.substring(0, packageName.lastIndexOf(PACKAGE_SUFFIX_TO_REMOVE)) + APPEND_TO_PACKAGE;
        else
            defaultValuesFileName = packageName + APPEND_TO_PACKAGE;
        
        // Obtain the URL for the file by locating it relative to the classpath
        try {
            url = URLHelper.newExtendedURL(defaultValuesFileName);
        } catch (MalformedURLException e) {
            // do nothing
        }
        
        return url;
    }
    
    /** Setter for property returnToFormKey.
     * This FormKey determines the screen to display when quitting from a component.
     * @param returnToFormKey New value of property returnToFormKey.
     */
    public void setReturnToFormKey(FormKey returnToFormKey) {
        super.setReturnToFormKey(HistoryNav.threadHasHistory() ? returnToFormKey : FormKey.getCloseBrowserFormKey());
    }
    
}
