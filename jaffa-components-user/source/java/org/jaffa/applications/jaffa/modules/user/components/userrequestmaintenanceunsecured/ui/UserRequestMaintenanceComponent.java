 package org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenanceunsecured.ui;

 import java.util.List;
 import org.jaffa.components.maint.MaintComponent2; 
 
/** The controller for the UserRequestMaintenance.
 */
public class UserRequestMaintenanceComponent extends org.jaffa.applications.jaffa.modules.user.components.userrequestmaintenance.ui.UserRequestMaintenanceComponent {

    /** This sets up the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        screens.add(new MaintComponent2.Screen("nonsecure_userRequestMaintenance_main", true, true, true, true));
    }
}
