package org.jaffa.applications.jaffa.modules.admin.components.userselfupdate.ui;

import org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.ui.UserMaintenanceComponent;
import java.util.List;
import java.security.PrivilegedAction;
import org.jaffa.components.maint.MaintComponent2;
import org.jaffa.security.SecurityManager;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

/** The controller for the UserSelfUpdateComponent.
 * This component reuses the Form and Action of the UserMaintenance component.
 */
public class UserSelfUpdateComponent extends UserMaintenanceComponent {

    /** This will display the logged-in user's information.
     * The UserMaintenance screen will be rendered if the current user has access to the business-function 'Jaffa.Admin.User.Maintenance'.
     * Else the UserSelfUpdateComponent screen, with far lesser updateable fields, will be rendered.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey.
     */
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        setMode(MODE_UPDATE);

        // determine the current userid
        setUserName(getUserSession().getUserId());

        return super.display();
    }

    /** This sets up the screen information.
     * The UserMaintenance screen will be added to the input List if the current user has access to the business-function 'Jaffa.Admin.User.Maintenance'.
     * Else the UserSelfUpdateComponent screen, with far lesser updateable fields, will be added to the input List.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        if (hasMaintenanceAccess())
            super.addScreens(screens);
        else
            screens.add(new MaintComponent2.Screen("admin_userSelfUpdate_main", true, false, false, false));
    }



    /** Check if the current user has access to the business-function 'Jaffa.Admin.User.Maintenance' */
    private boolean hasMaintenanceAccess() {
        boolean access;
        try {
            SecurityManager.runFunction("Jaffa.Admin.User.Maintenance", new PrivilegedAction() {
                public Object run() {
                    return null;
                }
            });
            access = true;
        } catch(Exception e) {
            access = false;
        }
        return access;
    }
}
