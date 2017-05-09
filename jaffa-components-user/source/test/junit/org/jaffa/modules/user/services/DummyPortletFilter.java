/*
 * PortletFilterTest.java
 *
 * Created on September 7, 2006, 11:06 PM
 *
 */
package org.jaffa.modules.user.services;

import org.jaffa.presentation.portlet.PortletFilter;
import org.jaffa.presentation.portlet.session.UserSession;
import java.util.Properties;

/** Used to test the custom setup of a user session object with a variation
 *
 * @author PaulE
 */
public class DummyPortletFilter extends PortletFilter {
    public static final String VARIATION = "TEST1";
    public void initUserInfo(UserSession us) {
        Properties p = new Properties();
        p.put("email",UserDataWrapper.EMAIL);
        us.setUserData(p);
        us.setVariation(VARIATION);
    }
}
