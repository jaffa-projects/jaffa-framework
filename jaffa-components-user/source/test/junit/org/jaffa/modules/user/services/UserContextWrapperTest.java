package org.jaffa.modules.user.services;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.session.LocaleContext;
import org.jaffa.presentation.portlet.session.SessionManager;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.security.SecurityManager;
import org.jaffa.security.VariationContext;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.session.IContextManager;

/**
 * Make sure all types of context are being set.
 *
 * @author  PaulE
 */
public class UserContextWrapperTest extends TestCase {
    public final static Logger log = Logger.getLogger(UserContextWrapperTest.class);
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new UserDataWrapper(new TestSuite(UserContextWrapperTest.class));
    }
    
    /** Creates new FormPrintServiceTest
     * @param name The name of the test case.
     */
    public UserContextWrapperTest(String name) {
        super(name);
    }
    
    /** Make sure the Context Manager is set */
    public void testContextManager() throws Exception {
        log.info("RUN: "+getName());
        try {
            
            UserContextWrapper ucw = new UserContextWrapper(UserDataWrapper.USER1,DummyPortletFilter.class);
            
            IContextManager cm = ContextManagerFactory.instance();
            // Test Global Rules
            assertEquals("Bad value in : test.rule.1","global",cm.getProperty("test.rule.1"));
            
            // Test Variation Rules
            assertEquals("Bad value in : test.rule.2","TEST1",cm.getProperty("test.rule.2"));
            
            // Test stuff from the User Rules
            assertEquals("Bad value in : user.id",UserDataWrapper.USER1,cm.getProperty("user.id"));
            assertEquals("Bad value in : user.principal",UserDataWrapper.USER1,((Principal)cm.getProperty("user.principal")).getName());
            assertEquals("Bad value in : user.hostname",null,cm.getProperty("user.hostname"));
            assertNotNull("Bad value in : user.data",cm.getProperty("user.data"));
            assertEquals("Bad value in : user.data:email",UserDataWrapper.EMAIL,((Properties)cm.getProperty("user.data")).getProperty("email"));
            assertEquals("Bad value in : user.sessionId","1",cm.getProperty("user.sessionId"));
            assertEquals("Bad value in : user.locale",Locale.getDefault(),cm.getProperty("user.locale"));
            assertEquals("Bad value in : user.variation",DummyPortletFilter.VARIATION,cm.getProperty("user.variation"));
            
            // Test stuff from the thread rules
            assertNotNull("Bad value in : request",cm.getProperty("request"));
            
            ucw.unsetContext();
            cm = ContextManagerFactory.instance();
            
            // Test Global Rules
            assertNull("Bad value in : test.rule.1",cm.getProperty("test.rule.1"));
            // Test Variation Rules
            assertNull("Bad value in : test.rule.2",cm.getProperty("test.rule.2"));
            // Test stuff from the User Rules
            assertNull("Bad value in : user.id",cm.getProperty("user.id"));
            // Test stuff from the thread rules
            assertNull("Bad value in : request",cm.getProperty("request"));
            
        } catch (Exception e) {
            log.fatal("Test Failed", e);
        }
    }
    
    
    /** Make sure the Security Context is set */
    public void testSecurityContext() throws Exception {
        log.info("RUN: "+getName());
        try {
            
            UserContextWrapper ucw = new UserContextWrapper(UserDataWrapper.USER1,DummyPortletFilter.class);
            assertNotNull("Bad value in : Security Principal", SecurityManager.getPrincipal() );
            assertEquals("Bad value in : Security Principal", UserDataWrapper.USER1, SecurityManager.getPrincipal().getName() );
            List roles = SecurityManager.getUserRoles();
            assertNotNull("Bad value in : Security Roles", roles );
            assertTrue("Missing Security Role: "+UserDataWrapper.USER1_ROLES[0], roles.contains(UserDataWrapper.USER1_ROLES[0]));
            assertTrue("Missing Security Role: "+UserDataWrapper.USER1_ROLES[1], roles.contains(UserDataWrapper.USER1_ROLES[1]));
            
            ucw.unsetContext();
            assertNull("Security Principal not cleared", SecurityManager.getPrincipal() );
            assertTrue("Security Roles not cleared", SecurityManager.getUserRoles() == null || SecurityManager.getUserRoles().isEmpty() );
            
        } catch (Exception e) {
            log.fatal("Test Failed", e);
        }
    }
    
    /** Make sure the Locale Context is set */
    public void testLocaleContext() throws Exception {
        log.info("RUN: "+getName());
        try {
            // Switch the default local, as that is what the MockRequest will use.
            Locale lc = Locale.getDefault();
            Locale.setDefault(Locale.CANADA);
            UserContextWrapper ucw = new UserContextWrapper(UserDataWrapper.USER1,DummyPortletFilter.class);
            assertEquals("Bad value in : Locale", Locale.CANADA, LocaleContext.getLocale() );
            
            // Switch the default back, this should be returned
            Locale.setDefault(lc);
            ucw.unsetContext();
            assertTrue("Locale not cleared", !Locale.CANADA.equals(LocaleContext.getLocale()) );
            
        } catch (Exception e) {
            log.fatal("Test Failed", e);
        }
    }
    
    /** Make sure the Variation Context is set */
    public void testVariationContext() throws Exception {
        log.info("RUN: "+getName());
        try {
            UserContextWrapper ucw = new UserContextWrapper(UserDataWrapper.USER1,DummyPortletFilter.class);
            assertEquals("Bad value in : Variation", DummyPortletFilter.VARIATION, VariationContext.getVariation() );
            
            ucw.unsetContext();
            assertTrue("Variation not cleared", !DummyPortletFilter.VARIATION.equals(VariationContext.getVariation()) );
            assertTrue("Variation not reset", VariationContext.DEFAULT_VARIATION.equals(VariationContext.getVariation()) );
            
        } catch (Exception e) {
            log.fatal("Test Failed", e);
        }
    }
    
    /** Make sure the request / UserRoles are set */
    public void testUserRoles() throws Exception {
        log.info("RUN: "+getName());
        try {
            
            UserContextWrapper ucw = new UserContextWrapper(UserDataWrapper.USER1,DummyPortletFilter.class);
            IContextManager cm = ContextManagerFactory.instance();
            // Test stuff from the thread rules
            assertNotNull("Bad value in : request",cm.getProperty("request"));
            HttpServletRequest req = (HttpServletRequest)cm.getProperty("request");
            assertTrue("User should have access to "+UserDataWrapper.USER1_ROLES[0],req.isUserInRole(UserDataWrapper.USER1_ROLES[0]));
            assertTrue("User should have access to "+UserDataWrapper.USER1_ROLES[1],req.isUserInRole(UserDataWrapper.USER1_ROLES[1]));
            assertFalse("User should NOT have access to DUMMYROLE1",req.isUserInRole("DUMMYROLE1"));
            
            ucw.unsetContext();
            
        } catch (Exception e) {
            log.fatal("Test Failed", e);
        }
    }
    
    /** Make sure the SessionManager adds/drops UserSessions correctly */
    public void testSessionManager() throws Exception {
        log.info("RUN: "+getName());
        try {
            // Clean the SessionManager
            UserSession[] sessions = SessionManager.getSessions();
            if (sessions != null && sessions.length > 0) {
                log.info("Clearing " + sessions.length + " UserSession objects from the SessionManager");
                for (int i = 0; i < sessions.length; i++)
                    SessionManager.removeSession(sessions[i]);
            }
            
            UserContextWrapper ucw = new UserContextWrapper(UserDataWrapper.USER1,DummyPortletFilter.class);
            sessions = SessionManager.getSessions();
            if (sessions != null && sessions.length == 0)
                sessions = null;
            assertNotNull("The SessionManager should have one session", sessions);
            assertEquals("The SessionManager should have one session", 1, sessions.length);
            assertEquals("Wrong user in the UserSession", UserDataWrapper.USER1, sessions[0].getUserId());
            
            
            ucw.unsetContext();
            sessions = SessionManager.getSessions();
            if (sessions != null && sessions.length == 0)
                sessions = null;
            assertNull("The SessionManager should have no residue sessions", sessions);
            
        } catch (Exception e) {
            log.fatal("Test Failed", e);
        }
    }
}
