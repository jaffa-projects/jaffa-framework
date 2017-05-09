/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.tx.exceptions;

import org.jaffa.exceptions.ApplicationException;

/** This exception can be thrown for various error conditions that may
 * occur in a typical user maintenance (create/update/delete) component
 *
 * @author GautamJ
 * @version 1.0
 */
public class UserMaintenanceException extends ApplicationException {
    
    public static final String PROP_NO_MAINT_ACCESS = "exception.org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.tx.exceptions.UserMaintenanceException.noMaintAccess";
    public static final String PROP_NO_ACCESS = "exception.org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.tx.exceptions.UserMaintenanceException.noAccess";
    public static final String PROP_CANNOT_UPDATE_OTHERS_PROFILE = "exception.org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.tx.exceptions.UserMaintenanceException.cannotUpdateOthersProfile";
    public static final String PROP_INCLUDED_ROLE_MISSING = "exception.org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.tx.exceptions.UserMaintenanceException.includedRoleMissing";
    public static final String PROP_EXCLUDED_ROLE_PRESENT = "exception.org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.tx.exceptions.UserMaintenanceException.excludedRolePresent";
    
    
    /** Create New Instance of the UserMaintenanceException, passing in no context.
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     */
    public UserMaintenanceException(String propertyName) {
        this(propertyName, null);
    }
    
    /** Create New Instance of the UserMaintenanceException
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     * @param parameter0 Context to be used when displaying the error, referenced as {0}
     */
    public UserMaintenanceException(String propertyName, Object parameter0) {
        this(propertyName, new Object[] {parameter0});
    }
    
    /** Create New Instance of the UserMaintenanceException
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     * @param parameter0 Context to be used when displaying the error, referenced as {0}
     * @param parameter1 Context to be used when displaying the error, referenced as {1}
     */
    public UserMaintenanceException(String propertyName, Object parameter0, Object parameter1) {
        this(propertyName, new Object[] {parameter0, parameter1});
    }
    
    /** Create New Instance of the UserMaintenanceException
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     * @param parameter0 Context to be used when displaying the error, referenced as {0}
     * @param parameter1 Context to be used when displaying the error, referenced as {1}
     * @param parameter2 Context to be used when displaying the error, referenced as {2}
     */
    public UserMaintenanceException(String propertyName, Object parameter0, Object parameter1, Object parameter2) {
        this(propertyName, new Object[] {parameter0, parameter1, parameter2});
    }
    
    /** Create New Instance of the UserMaintenanceException, passing in an array of context
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     * @param parameter0 Context to be used when displaying the error, referenced as {0}
     * @param parameter1 Context to be used when displaying the error, referenced as {1}
     * @param parameter2 Context to be used when displaying the error, referenced as {2}
     * @param parameter3 Context to be used when displaying the error, referenced as {3}
     */
    public UserMaintenanceException(String propertyName, Object parameter0, Object parameter1, Object parameter2, Object parameter3) {
        this(propertyName, new Object[] {parameter0, parameter1, parameter2, parameter3});
    }
    
    /** Create New Instance of the UserMaintenanceException
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     * @param parameters Context to be used when displaying the error. Each element in the array is referened as {0},{1},..
     */
    public UserMaintenanceException(String propertyName, Object[] parameters) {
        super(propertyName, parameters);
    }
}
