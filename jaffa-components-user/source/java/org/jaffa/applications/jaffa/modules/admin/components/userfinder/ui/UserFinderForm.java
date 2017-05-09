// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.userfinder.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.*;
import org.jaffa.components.finder.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.util.StringHelper;
import org.jaffa.applications.jaffa.modules.admin.components.userfinder.dto.UserFinderOutDto;
import org.jaffa.applications.jaffa.modules.admin.components.userfinder.dto.UserFinderOutRowDto;
import org.jaffa.applications.jaffa.modules.admin.domain.UserMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support UserFinder.
 */
public class UserFinderForm extends FinderForm {

    private static Logger log = Logger.getLogger(UserFinderForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:userName_1_be
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public String getUserName() {
        return ( (UserFinderComponent) getComponent() ).getUserName();
    }

    /** Setter for property userName.
     * @param userName New value of property userName.
     */
    public void setUserName(String userName) {
        ( (UserFinderComponent) getComponent() ).setUserName(userName);
    }

    /** Getter for property userName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property userName.
     */
    public EditBoxModel getUserNameWM() {
        EditBoxModel userNameModel = (EditBoxModel) getWidgetCache().getModel("userName");
        if (userNameModel == null) {
            if (getUserName() != null)
                userNameModel = new EditBoxModel( getUserName() );
            else
                userNameModel = new EditBoxModel();
            userNameModel.setStringCase( ((StringFieldMetaData) UserMeta.META_USER_NAME).getCaseType() );

            // .//GEN-END:userName_1_be
            // Add custom code //GEN-FIRST:userName_1


            // .//GEN-LAST:userName_1
            // .//GEN-BEGIN:userName_2_be
            getWidgetCache().addModel("userName", userNameModel);
        }
        return userNameModel;
    }

    /** Setter for property userName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property userName.
     */
    public void setUserNameWV(String value) {
        EditBoxController.updateModel(value, getUserNameWM());
    }

    /** Getter for DropDown property userName.
     * @return Value of property userNameDd.
     */
    public String getUserNameDd() {
        return ( (UserFinderComponent) getComponent() ).getUserNameDd();
    }

    /** Setter for DropDown property userName.
     * @param userNameDd New value of property userNameDd.
     */
    public void setUserNameDd(String userNameDd) {
        ( (UserFinderComponent) getComponent() ).setUserNameDd(userNameDd);
    }

    /** Getter for DropDown property userName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property userNameDd.
     */
    public DropDownModel getUserNameDdWM() {
        DropDownModel userNameDdModel = (DropDownModel) getWidgetCache().getModel("userNameDd");
        if (userNameDdModel == null) {
            if (getUserNameDd() != null)
                userNameDdModel = new DropDownModel( getUserNameDd() );
            else
                userNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                userNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("userNameDd", userNameDdModel);
        }
        return userNameDdModel;
    }

    /** Setter for DropDown property userName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property userNameDd.
     */
    public void setUserNameDdWV(String value) {
        DropDownController.updateModel(value, getUserNameDdWM());
    }

    // .//GEN-END:userName_2_be
    // .//GEN-BEGIN:firstName_1_be
    /** Getter for property firstName.
     * @return Value of property firstName.
     */
    public String getFirstName() {
        return ( (UserFinderComponent) getComponent() ).getFirstName();
    }

    /** Setter for property firstName.
     * @param firstName New value of property firstName.
     */
    public void setFirstName(String firstName) {
        ( (UserFinderComponent) getComponent() ).setFirstName(firstName);
    }

    /** Getter for property firstName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property firstName.
     */
    public EditBoxModel getFirstNameWM() {
        EditBoxModel firstNameModel = (EditBoxModel) getWidgetCache().getModel("firstName");
        if (firstNameModel == null) {
            if (getFirstName() != null)
                firstNameModel = new EditBoxModel( getFirstName() );
            else
                firstNameModel = new EditBoxModel();
            firstNameModel.setStringCase( ((StringFieldMetaData) UserMeta.META_FIRST_NAME).getCaseType() );

            // .//GEN-END:firstName_1_be
            // Add custom code //GEN-FIRST:firstName_1


            // .//GEN-LAST:firstName_1
            // .//GEN-BEGIN:firstName_2_be
            getWidgetCache().addModel("firstName", firstNameModel);
        }
        return firstNameModel;
    }

    /** Setter for property firstName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property firstName.
     */
    public void setFirstNameWV(String value) {
        EditBoxController.updateModel(value, getFirstNameWM());
    }

    /** Getter for DropDown property firstName.
     * @return Value of property firstNameDd.
     */
    public String getFirstNameDd() {
        return ( (UserFinderComponent) getComponent() ).getFirstNameDd();
    }

    /** Setter for DropDown property firstName.
     * @param firstNameDd New value of property firstNameDd.
     */
    public void setFirstNameDd(String firstNameDd) {
        ( (UserFinderComponent) getComponent() ).setFirstNameDd(firstNameDd);
    }

    /** Getter for DropDown property firstName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property firstNameDd.
     */
    public DropDownModel getFirstNameDdWM() {
        DropDownModel firstNameDdModel = (DropDownModel) getWidgetCache().getModel("firstNameDd");
        if (firstNameDdModel == null) {
            if (getFirstNameDd() != null)
                firstNameDdModel = new DropDownModel( getFirstNameDd() );
            else
                firstNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                firstNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("firstNameDd", firstNameDdModel);
        }
        return firstNameDdModel;
    }

    /** Setter for DropDown property firstName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property firstNameDd.
     */
    public void setFirstNameDdWV(String value) {
        DropDownController.updateModel(value, getFirstNameDdWM());
    }

    // .//GEN-END:firstName_2_be
    // .//GEN-BEGIN:lastName_1_be
    /** Getter for property lastName.
     * @return Value of property lastName.
     */
    public String getLastName() {
        return ( (UserFinderComponent) getComponent() ).getLastName();
    }

    /** Setter for property lastName.
     * @param lastName New value of property lastName.
     */
    public void setLastName(String lastName) {
        ( (UserFinderComponent) getComponent() ).setLastName(lastName);
    }

    /** Getter for property lastName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastName.
     */
    public EditBoxModel getLastNameWM() {
        EditBoxModel lastNameModel = (EditBoxModel) getWidgetCache().getModel("lastName");
        if (lastNameModel == null) {
            if (getLastName() != null)
                lastNameModel = new EditBoxModel( getLastName() );
            else
                lastNameModel = new EditBoxModel();
            lastNameModel.setStringCase( ((StringFieldMetaData) UserMeta.META_LAST_NAME).getCaseType() );

            // .//GEN-END:lastName_1_be
            // Add custom code //GEN-FIRST:lastName_1


            // .//GEN-LAST:lastName_1
            // .//GEN-BEGIN:lastName_2_be
            getWidgetCache().addModel("lastName", lastNameModel);
        }
        return lastNameModel;
    }

    /** Setter for property lastName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastName.
     */
    public void setLastNameWV(String value) {
        EditBoxController.updateModel(value, getLastNameWM());
    }

    /** Getter for DropDown property lastName.
     * @return Value of property lastNameDd.
     */
    public String getLastNameDd() {
        return ( (UserFinderComponent) getComponent() ).getLastNameDd();
    }

    /** Setter for DropDown property lastName.
     * @param lastNameDd New value of property lastNameDd.
     */
    public void setLastNameDd(String lastNameDd) {
        ( (UserFinderComponent) getComponent() ).setLastNameDd(lastNameDd);
    }

    /** Getter for DropDown property lastName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property lastNameDd.
     */
    public DropDownModel getLastNameDdWM() {
        DropDownModel lastNameDdModel = (DropDownModel) getWidgetCache().getModel("lastNameDd");
        if (lastNameDdModel == null) {
            if (getLastNameDd() != null)
                lastNameDdModel = new DropDownModel( getLastNameDd() );
            else
                lastNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                lastNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("lastNameDd", lastNameDdModel);
        }
        return lastNameDdModel;
    }

    /** Setter for DropDown property lastName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property lastNameDd.
     */
    public void setLastNameDdWV(String value) {
        DropDownController.updateModel(value, getLastNameDdWM());
    }

    // .//GEN-END:lastName_2_be
    // .//GEN-BEGIN:status_1_be
    /** Getter for property status.
     * @return Value of property status.
     */
    public String getStatus() {
        return ( (UserFinderComponent) getComponent() ).getStatus();
    }

    /** Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(String status) {
        ( (UserFinderComponent) getComponent() ).setStatus(status);
    }

    /** Getter for property status. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property status.
     */
    public GridModel getStatusWM() {
        GridModel statusModel = (GridModel) getWidgetCache().getModel("status");
        if (statusModel == null) {
            statusModel = new GridModel();
            Collection existingValues = null;
            Object code = null;
            GridModelRow row = null;
            if (getStatus() != null)
                existingValues = StringHelper.convertToList(getStatus());
            row = statusModel.newRow();
            code = "N";
            row.addElement("code", code);
            row.addElement("description", "[label.Jaffa.Admin.User.Status.N]");
            if (existingValues != null && existingValues.contains(code))
                row.addElement("checked", new CheckBoxModel(true));
            else
                row.addElement("checked", new CheckBoxModel(false));
            row = statusModel.newRow();
            code = "A";
            row.addElement("code", code);
            row.addElement("description", "[label.Jaffa.Admin.User.Status.A]");
            if (existingValues != null && existingValues.contains(code))
                row.addElement("checked", new CheckBoxModel(true));
            else
                row.addElement("checked", new CheckBoxModel(false));
            row = statusModel.newRow();
            code = "I";
            row.addElement("code", code);
            row.addElement("description", "[label.Jaffa.Admin.User.Status.I]");
            if (existingValues != null && existingValues.contains(code))
                row.addElement("checked", new CheckBoxModel(true));
            else
                row.addElement("checked", new CheckBoxModel(false));

            // .//GEN-END:status_1_be
            // Add custom code //GEN-FIRST:status_1


            // .//GEN-LAST:status_1
            // .//GEN-BEGIN:status_2_be
            getWidgetCache().addModel("status", statusModel);
        }
        return statusModel;
    }

    /** Setter for property status. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property status.
     */
    public void setStatusWV(String value) {
        GridController.updateModel(value, getStatusWM());
    }
    // .//GEN-END:status_2_be
    // .//GEN-BEGIN:eMailAddress_1_be
    /** Getter for property eMailAddress.
     * @return Value of property eMailAddress.
     */
    public String getEMailAddress() {
        return ( (UserFinderComponent) getComponent() ).getEMailAddress();
    }

    /** Setter for property eMailAddress.
     * @param eMailAddress New value of property eMailAddress.
     */
    public void setEMailAddress(String eMailAddress) {
        ( (UserFinderComponent) getComponent() ).setEMailAddress(eMailAddress);
    }

    /** Getter for property eMailAddress. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property eMailAddress.
     */
    public EditBoxModel getEMailAddressWM() {
        EditBoxModel eMailAddressModel = (EditBoxModel) getWidgetCache().getModel("eMailAddress");
        if (eMailAddressModel == null) {
            if (getEMailAddress() != null)
                eMailAddressModel = new EditBoxModel( getEMailAddress() );
            else
                eMailAddressModel = new EditBoxModel();
            eMailAddressModel.setStringCase( ((StringFieldMetaData) UserMeta.META_E_MAIL_ADDRESS).getCaseType() );

            // .//GEN-END:eMailAddress_1_be
            // Add custom code //GEN-FIRST:eMailAddress_1


            // .//GEN-LAST:eMailAddress_1
            // .//GEN-BEGIN:eMailAddress_2_be
            getWidgetCache().addModel("eMailAddress", eMailAddressModel);
        }
        return eMailAddressModel;
    }

    /** Setter for property eMailAddress. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property eMailAddress.
     */
    public void setEMailAddressWV(String value) {
        EditBoxController.updateModel(value, getEMailAddressWM());
    }

    /** Getter for DropDown property eMailAddress.
     * @return Value of property eMailAddressDd.
     */
    public String getEMailAddressDd() {
        return ( (UserFinderComponent) getComponent() ).getEMailAddressDd();
    }

    /** Setter for DropDown property eMailAddress.
     * @param eMailAddressDd New value of property eMailAddressDd.
     */
    public void setEMailAddressDd(String eMailAddressDd) {
        ( (UserFinderComponent) getComponent() ).setEMailAddressDd(eMailAddressDd);
    }

    /** Getter for DropDown property eMailAddress. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property eMailAddressDd.
     */
    public DropDownModel getEMailAddressDdWM() {
        DropDownModel eMailAddressDdModel = (DropDownModel) getWidgetCache().getModel("eMailAddressDd");
        if (eMailAddressDdModel == null) {
            if (getEMailAddressDd() != null)
                eMailAddressDdModel = new DropDownModel( getEMailAddressDd() );
            else
                eMailAddressDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                eMailAddressDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("eMailAddressDd", eMailAddressDdModel);
        }
        return eMailAddressDdModel;
    }

    /** Setter for DropDown property eMailAddress. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property eMailAddressDd.
     */
    public void setEMailAddressDdWV(String value) {
        DropDownController.updateModel(value, getEMailAddressDdWM());
    }

    // .//GEN-END:eMailAddress_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        String value = null;
        StringBuffer buf = null;

        value = getUserNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setUserName(value);
        setUserNameDd(getUserNameDdWM().getValue());

        value = getFirstNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setFirstName(value);
        setFirstNameDd(getFirstNameDdWM().getValue());

        value = getLastNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLastName(value);
        setLastNameDd(getLastNameDdWM().getValue());

        buf = new StringBuffer();
        if (getStatusWM().getRows() != null) {
            for (Iterator i = getStatusWM().getRows().iterator(); i.hasNext(); ) {
                GridModelRow row = (GridModelRow) i.next();
                boolean checked = ((CheckBoxModel) row.getElement("checked")).getState();
                if (checked) {
                    if (buf.length() > 0)
                        buf.append(',');
                    buf.append(row.getElement("code"));
                }
            }
        }
        if (buf.length() > 0)
            value = buf.toString();
        else
            value = null;
        if (value != null && value.trim().length() == 0)
            value = null;
        setStatus(value);

        value = getEMailAddressWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setEMailAddress(value);
        setEMailAddressDd(getEMailAddressDdWM().getValue());

        // .//GEN-END:_doValidate_1_be
        // Add custom code //GEN-FIRST:_doValidate_1



        // .//GEN-LAST:_doValidate_1
        // .//GEN-BEGIN:_doValidate_2_be
        return valid;
    }
    // .//GEN-END:_doValidate_2_be
    // .//GEN-BEGIN:_populateRows_1_be
    /** This will populate the input GridModel with the data in the finderOutDto of the Component.
     * @param rows The GridModel object to populate.
     */
    public void populateRows(GridModel rows) {
        rows.clearRows();
        UserFinderOutDto outputDto = (UserFinderOutDto) ((UserFinderComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                UserFinderOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement("userName", rowDto.getUserName());
                row.addElement("firstName", rowDto.getFirstName());
                row.addElement("lastName", rowDto.getLastName());
                row.addElement("status", rowDto.getStatus());
                row.addElement("statusDescription", rowDto.getStatusDescription());
                row.addElement("eMailAddress", rowDto.getEMailAddress());
                row.addElement("createdOn", rowDto.getCreatedOn());
                row.addElement("createdBy", rowDto.getCreatedBy());
                row.addElement("lastUpdatedOn", rowDto.getLastUpdatedOn());
                row.addElement("lastUpdatedBy", rowDto.getLastUpdatedBy());
                // .//GEN-END:_populateRows_1_be
                // Add custom code for the row //GEN-FIRST:_populateRows_1
                if (rowDto.getStatus() != null)
                    row.addElement("statusDescription", "[label.Jaffa.Admin.User.Status." + rowDto.getStatus() + ']');

                // .//GEN-LAST:_populateRows_1
                // .//GEN-BEGIN:_populateRows_2_be
            }
        }
    }
    // .//GEN-END:_populateRows_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
