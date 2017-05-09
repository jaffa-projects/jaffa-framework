// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.userlookup.ui;

import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.datatypes.*;
import org.jaffa.metadata.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.lookup.*;
import org.jaffa.components.maint.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.components.codehelper.ICodeHelper;
import org.jaffa.components.codehelper.dto.*;

import org.jaffa.applications.jaffa.modules.admin.components.userlookup.IUserLookup;
import org.jaffa.applications.jaffa.modules.admin.components.userlookup.dto.UserLookupInDto;
import org.jaffa.applications.jaffa.modules.admin.components.userlookup.dto.UserLookupOutDto;
import org.jaffa.applications.jaffa.modules.admin.domain.UserMeta;


import org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.ui.UserMaintenanceComponent;
import org.jaffa.applications.jaffa.modules.admin.components.userviewer.ui.UserViewerComponent;
import org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.ui.UserMaintenanceComponent;
import org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.ui.UserMaintenanceComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the UserLookup.
 */
public class UserLookupComponent extends LookupComponent2 {

    private static Logger log = Logger.getLogger(UserLookupComponent.class);

    private String m_userName = null;
    private String m_userNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_firstName = null;
    private String m_firstNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_lastName = null;
    private String m_lastNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_status = null;
    private String m_eMailAddress = null;
    private String m_eMailAddressDd = CriteriaField.RELATIONAL_EQUALS;

    private IUserLookup m_tx = null;
    private UserMaintenanceComponent m_createComponent = null;
    private ICreateListener m_createListener = null;
    private UserMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
    private UserMaintenanceComponent m_deleteComponent = null;
    private IDeleteListener m_deleteListener = null;

    public UserLookupComponent() {
        super();
        super.setSortDropDown("UserName");
    }

    /** Returns the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected String getCriteriaFormName() {
        return "admin_userLookupCriteria";
    }
    
    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "admin_userLookupResults";
    }
    
    /** Returns the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected String getConsolidatedCriteriaAndResultsFormName() {
        return "admin_userLookupConsolidatedCriteriaAndResults";
    }
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_quit_1_be
    /** This should be invoked when done with the component.
     */
    public void quit() {
        // .//GEN-END:_quit_1_be
        // Add custom code before processing the method //GEN-FIRST:_quit_1


        // .//GEN-LAST:_quit_1
        // .//GEN-BEGIN:_quit_2_be
        if (m_tx != null) {
            m_tx.destroy();
            m_tx = null;
        }
        if (m_createComponent != null) {
            m_createComponent.quit();
            m_createComponent = null;
        }
        m_createListener = null;
        if (m_updateComponent != null) {
            m_updateComponent.quit();
            m_updateComponent = null;
        }
        m_updateListener = null;
        if (m_deleteComponent != null) {
            m_deleteComponent.quit();
            m_deleteComponent = null;
        }
        m_deleteListener = null;

        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:userName_1_be
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public String getUserName() {
        return m_userName;
    }

    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(String userName) {
        m_userName = userName;
    }

    /** Getter for property userNameDd.
     * @return Value of property userNameDd.
     */
    public String getUserNameDd() {
        return m_userNameDd;
    }

    /** Setter for property userNameDd.
     * @param userNameDd New value of property userNameDd.
     */
    public void setUserNameDd(String userNameDd) {
        m_userNameDd = userNameDd;
    }

    // .//GEN-END:userName_1_be
    // .//GEN-BEGIN:firstName_1_be
    /** Getter for property firstName.
     * @return Value of property firstName.
     */
    public String getFirstName() {
        return m_firstName;
    }

    /** Setter for property firstName.
     * @param firstName New value of property firstName.
     */
    public void setFirstName(String firstName) {
        m_firstName = firstName;
    }

    /** Getter for property firstNameDd.
     * @return Value of property firstNameDd.
     */
    public String getFirstNameDd() {
        return m_firstNameDd;
    }

    /** Setter for property firstNameDd.
     * @param firstNameDd New value of property firstNameDd.
     */
    public void setFirstNameDd(String firstNameDd) {
        m_firstNameDd = firstNameDd;
    }

    // .//GEN-END:firstName_1_be
    // .//GEN-BEGIN:lastName_1_be
    /** Getter for property lastName.
     * @return Value of property lastName.
     */
    public String getLastName() {
        return m_lastName;
    }

    /** Setter for property lastName.
     * @param lastName New value of property lastName.
     */
    public void setLastName(String lastName) {
        m_lastName = lastName;
    }

    /** Getter for property lastNameDd.
     * @return Value of property lastNameDd.
     */
    public String getLastNameDd() {
        return m_lastNameDd;
    }

    /** Setter for property lastNameDd.
     * @param lastNameDd New value of property lastNameDd.
     */
    public void setLastNameDd(String lastNameDd) {
        m_lastNameDd = lastNameDd;
    }

    // .//GEN-END:lastName_1_be
    // .//GEN-BEGIN:status_1_be
    /** Getter for property status.
     * @return Value of property status.
     */
    public String getStatus() {
        return m_status;
    }

    /** Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(String status) {
        m_status = status;
    }

    // .//GEN-END:status_1_be
    // .//GEN-BEGIN:eMailAddress_1_be
    /** Getter for property eMailAddress.
     * @return Value of property eMailAddress.
     */
    public String getEMailAddress() {
        return m_eMailAddress;
    }

    /** Setter for property eMailAddress.
     * @param eMailAddress New value of property eMailAddress.
     */
    public void setEMailAddress(String eMailAddress) {
        m_eMailAddress = eMailAddress;
    }

    /** Getter for property eMailAddressDd.
     * @return Value of property eMailAddressDd.
     */
    public String getEMailAddressDd() {
        return m_eMailAddressDd;
    }

    /** Setter for property eMailAddressDd.
     * @param eMailAddressDd New value of property eMailAddressDd.
     */
    public void setEMailAddressDd(String eMailAddressDd) {
        m_eMailAddressDd = eMailAddressDd;
    }

    // .//GEN-END:eMailAddress_1_be
    // .//GEN-BEGIN:_doInquiry_1_be
    /** This performs the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    protected FinderOutDto doInquiry() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        UserLookupInDto inputDto = new UserLookupInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method //GEN-FIRST:_doInquiry_1


        // .//GEN-LAST:_doInquiry_1
        // .//GEN-BEGIN:_doInquiry_2_be
        inputDto.setMaxRecords(getMaxRecords());

        if (getUserName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getUserNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getUserNameDd() ) )
            inputDto.setUserName(StringCriteriaField.getStringCriteriaField(getUserNameDd(), getUserName(), null));

        if (getFirstName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getFirstNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getFirstNameDd() ) )
            inputDto.setFirstName(StringCriteriaField.getStringCriteriaField(getFirstNameDd(), getFirstName(), null));

        if (getLastName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getLastNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getLastNameDd() ) )
            inputDto.setLastName(StringCriteriaField.getStringCriteriaField(getLastNameDd(), getLastName(), null));

        if (getStatus() != null) {
            if (getStatus().indexOf(',') >= 0)
                inputDto.setStatus(StringCriteriaField.getStringCriteriaField(CriteriaField.RELATIONAL_IN, getStatus(), null));
            else
                inputDto.setStatus(StringCriteriaField.getStringCriteriaField(CriteriaField.RELATIONAL_EQUALS, getStatus(), null));
        }

        if (getEMailAddress() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getEMailAddressDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getEMailAddressDd() ) )
            inputDto.setEMailAddress(StringCriteriaField.getStringCriteriaField(getEMailAddressDd(), getEMailAddress(), null));


        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        inputDto.setHeaderDto(getHeaderDto());
        addSortCriteria(inputDto);


        // perform the inquiry
        if (m_tx == null)
            m_tx = (IUserLookup) Factory.createObject(IUserLookup.class);
        FinderOutDto finderOutDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction //GEN-FIRST:_doInquiry_2


        // .//GEN-LAST:_doInquiry_2
        // .//GEN-BEGIN:_doInquiry_3_be
        return finderOutDto;
    }
    // .//GEN-END:_doInquiry_3_be
    // .//GEN-BEGIN:_createObject_1_be
    /** Calls the Jaffa.Admin.UserMaintenance component for creating a new User object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromCriteria() throws ApplicationExceptions, FrameworkException {
        return createObject(getCriteriaFormKey());
    }

    /** Calls the Jaffa.Admin.UserMaintenance component for creating a new User object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createFromResults() throws ApplicationExceptions, FrameworkException {
        return createObject(getResultsFormKey());
    }

    /** Calls the Jaffa.Admin.UserMaintenance component for creating a new User object.
     * @param formKey The FormKey object for the screen invoking this method
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Create screen.
     */
    public FormKey createObject(FormKey formKey) throws ApplicationExceptions, FrameworkException {
        if (m_createComponent == null || !m_createComponent.isActive())
            m_createComponent = (UserMaintenanceComponent) run("Jaffa.Admin.UserMaintenance");
        m_createComponent.setReturnToFormKey(formKey);
        // Add the Listener only if a search has been done
        if (getFinderOutDto() != null)
            addListeners(m_createComponent);
        if (m_createComponent instanceof IMaintComponent)
            ((IMaintComponent) m_createComponent).setMode(IMaintComponent.MODE_CREATE);
        // .//GEN-END:_createObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_createObject_1


        // .//GEN-LAST:_createObject_1
        // .//GEN-BEGIN:_createObject_2_be
        return m_createComponent.display();
    }

    private ICreateListener getCreateListener() {
        if (m_createListener == null) {
            m_createListener = new ICreateListener() {
                public void createDone(EventObject source) {
                    try {
                        // .//GEN-END:_createObject_2_be
                        // Add custom code //GEN-FIRST:_createObject_2


                        // .//GEN-LAST:_createObject_2
                        // .//GEN-BEGIN:_createObject_3_be
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Create", e);
                    }
                }
            };
        }
        return m_createListener;
    }
    // .//GEN-END:_createObject_3_be
    // .//GEN-BEGIN:_viewObject_1_be
    /** Calls the Jaffa.Admin.UserViewer component for viewing the User object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey viewObject(java.lang.String userName) throws ApplicationExceptions, FrameworkException {
        UserViewerComponent viewComponent = (UserViewerComponent) run("Jaffa.Admin.UserViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setUserName(userName);
        // .//GEN-END:_viewObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_viewObject_1


        // .//GEN-LAST:_viewObject_1
        // .//GEN-BEGIN:_viewObject_2_be
        return viewComponent.display();
    }
    // .//GEN-END:_viewObject_2_be
    // .//GEN-BEGIN:_updateObject_1_be
    /** Calls the Jaffa.Admin.UserMaintenance component for updating the User object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateObject(java.lang.String userName) throws ApplicationExceptions, FrameworkException {
        if (m_updateComponent == null || !m_updateComponent.isActive()) {
            m_updateComponent = (UserMaintenanceComponent) run("Jaffa.Admin.UserMaintenance");
            m_updateComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_updateComponent);
        }
        m_updateComponent.setUserName(userName);
        if (m_updateComponent instanceof IMaintComponent)
            ((IMaintComponent) m_updateComponent).setMode(IMaintComponent.MODE_UPDATE);
        // .//GEN-END:_updateObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_updateObject_2


        // .//GEN-LAST:_updateObject_2
        // .//GEN-BEGIN:_updateObject_2_be
        return m_updateComponent.display();
    }

    private IUpdateListener getUpdateListener() {
        if (m_updateListener == null) {
            m_updateListener = new IUpdateListener() {
                public void updateDone(EventObject source) {
                    try {
                        // .//GEN-END:_updateObject_2_be
                        // Add custom code //GEN-FIRST:_updateObject_1


                        // .//GEN-LAST:_updateObject_1
                        // .//GEN-BEGIN:_updateObject_3_be
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Update", e);
                    }
                }
            };
        }
        return m_updateListener;
    }
    // .//GEN-END:_updateObject_3_be
    // .//GEN-BEGIN:_deleteObject_1_be
    /** Calls the Jaffa.Admin.UserMaintenance component for deleting the User object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteObject(java.lang.String userName)  throws ApplicationExceptions, FrameworkException {
        if (m_deleteComponent == null || !m_deleteComponent.isActive()) {
            m_deleteComponent = (UserMaintenanceComponent) run("Jaffa.Admin.UserMaintenance");
            m_deleteComponent.setReturnToFormKey(getResultsFormKey());
            addListeners(m_deleteComponent);
        }
        m_deleteComponent.setUserName(userName);
        if (m_deleteComponent instanceof IMaintComponent)
            ((IMaintComponent) m_deleteComponent).setMode(IMaintComponent.MODE_DELETE);
        // .//GEN-END:_deleteObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_deleteObject_2


        // .//GEN-LAST:_deleteObject_2
        // .//GEN-BEGIN:_deleteObject_2_be
        return m_deleteComponent.display();
    }

    private IDeleteListener getDeleteListener() {
        if (m_deleteListener == null) {
            m_deleteListener = new IDeleteListener() {
                public void deleteDone(EventObject source) {
                    try {
                        // .//GEN-END:_deleteObject_2_be
                        // Add custom code //GEN-FIRST:_deleteObject_1


                        // .//GEN-LAST:_deleteObject_1
                        // .//GEN-BEGIN:_deleteObject_3_be
                        performInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Delete", e);
                    }
                }
            };
        }
        return m_deleteListener;
    }
    // .//GEN-END:_deleteObject_3_be
    // .//GEN-BEGIN:_addListeners_1_be
    private void addListeners(Component comp) {
        if (comp  instanceof ICreateComponent)
            ((ICreateComponent) comp).addCreateListener(getCreateListener());
        if (comp  instanceof IUpdateComponent)
            ((IUpdateComponent) comp).addUpdateListener(getUpdateListener());
        if (comp  instanceof IDeleteComponent)
            ((IDeleteComponent) comp).addDeleteListener(getDeleteListener());
    }
    // .//GEN-END:_addListeners_1_be
    // .//GEN-BEGIN:_initializeCriteriaScreen_1_be
    /** This will retrieve the set of codes for dropdowns, if any are required
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void initializeCriteriaScreen() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        CodeHelperInDto input = null;




    }
    // .//GEN-END:_initializeCriteriaScreen_1_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
