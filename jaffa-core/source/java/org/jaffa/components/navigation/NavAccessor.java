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
package org.jaffa.components.navigation;

import org.apache.log4j.Logger;
import org.jaffa.components.navigation.domain.MenuOption;
import org.jaffa.presentation.portlet.component.ComponentManager;
import org.jaffa.presentation.portlet.session.LocaleContext;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.LoggerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** This class allows access to a navigation system, based on a users access.
 *  When this object is constructed, it looks at the full navigation system, and
 *  filters out any options the current user does not have access to.
 *
 * @author JonnyR
 * @author PaulE
 * @version 1.0
 */
public class NavAccessor {
    private static final Logger log = Logger.getLogger(NavAccessor.class);

    /** This field will hold a reference to the 'parseMenuOptions(List menuOptions, List navOptions)' method.
     * It'll be passed to the SecurityManager, if the method needs to be executed under a security context.
     */
    private static Method c_parseMenuOptionsMethod = null;
    static {
        try {
            c_parseMenuOptionsMethod = NavAccessor.class.getDeclaredMethod("parseMenuOptions", new Class[] {List.class, List.class});
            c_parseMenuOptionsMethod.setAccessible(true); // since the method has package-level access
        } catch (Exception e) {
            // This should never happen !!
            String str = "Error in obtaining a handle on the 'NavAccessor.parseMenuOptions(List menuOptions, List navOptions)' method";
            log.fatal(str, e);
            throw new RuntimeException(str, e);
        }
    }

    List m_globalNavOptions = new ArrayList();
    Map m_desktopNavOptions = new HashMap();
    int m_desktopCounter = 1;

    /**
     * This is a static method to allow a synchronized Access to the NavAccessor object
     * caching it on creation into the HttpSession.
     * NOTE: This method will create a HttpSession, if one doesn't already exist.
     * @param request The HTTP request we are processing
     * @return a NavAccessor instance, unique to the current HttpSession.
     */
    public static NavAccessor getNavAccessor(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // Determine the current locale
        Locale locale = findLocale();
        // Check the what locale was used when the nav accessor was set
        Locale navLocale = (Locale) session.getAttribute(NavAccessor.class.getName() + ".locale");
        NavAccessor na;

        // If locale has changed since the last time nav accessor was set then reset
        if (!locale.equals(navLocale)){
            log.debug("Locale changes, resetting nav accessor");
            na = null;
            session.setAttribute(NavAccessor.class.getName(), null);
        } else {
            na = (NavAccessor) session.getAttribute(NavAccessor.class.getName());
        }
        if(na == null) {
            synchronized(session.getId().intern()) {
                na = (NavAccessor) session.getAttribute(NavAccessor.class.getName());
                if (na == null) {
                    na = new NavAccessor();
                    List menuOptions = NavCache.getInstance().getGlobalMenu().getMenuOption();
                    /*
                    Invoke parseMenuOptions() directly, if a security context exists; else invoke it via the SecurityManager.
                    The PortletFilter executes its doFilter under a security context, which gets cleared in the finally section of the SecurityManager.
                    The PortletFilter throws a ComponentExpiredException when an action is invoked on an expired component.
                    So when a user invokes an action on a session which has timed out, the pageExpired.jsp will be rendered outside a security context.
                    Hence the need to set the security context, if one doesn't exist, before invoking the parseMenuOptions() method.
                     */
                    if (SecurityManager.getPrincipal() != null) {
                        if (log.isDebugEnabled())
                            log.debug("Executing the parseMenuOptions() method");
                        na.parseMenuOptions(menuOptions, na.m_globalNavOptions);
                    } else {
                        try {
                            if (log.isDebugEnabled())
                                log.debug("Executing the parseMenuOptions() method under a Security Context having the UserPrincipal: " + request.getUserPrincipal());
                            SecurityManager.runWithContext(request, na, c_parseMenuOptionsMethod, new Object[] {menuOptions, na.m_globalNavOptions});
                        } catch (Exception e) {
                            String str = "Error in execution of NavAccessor.parseMenuOptions() method";
                            log.error(str, e);
                            throw new RuntimeException(str, e);
                        }
                    }

                    // Add it to the Session only for an authenticated user
                    if (SecurityManager.getPrincipal() != null || request.getUserPrincipal() != null){
                        session.setAttribute(NavAccessor.class.getName(), na);
                        // Store locale when setting the nav accessor
                        session.setAttribute(NavAccessor.class.getName() + ".locale", locale);
                    }
                }
            }
        }
        return na;
    }



    /** Creates a new instance of NavAccessor
     */
    private NavAccessor() {
    }

    /** @return the number of menu items added under this option */
    int parseMenuOptions(List menuOptions, List navOptions) {
        int counter = 0;
        for(Iterator it = menuOptions.iterator(); it.hasNext(); ) {
            MenuOption mOpt = (MenuOption) it.next();
            try {
                NavOption nOpt = new NavOption(this, mOpt);
                navOptions.add(nOpt);
                counter++;
                if(log.isDebugEnabled())
                    log.debug("Added Option - " + nOpt.getLabel());
            } catch (SecurityException se) {
                if(log.isDebugEnabled())
                    log.debug("Access Denied to Menu Option " + mOpt.getLabel());
            }
        }
        return counter;
    }

    /** Holder a counter for assigning unique ids to options within this NavAccessor
     */
    int getNextDesktopId() {
        return m_desktopCounter++;
    }

    /** Based on the UserId this method will return the list of variables available to the user.
     * @return This returns a List of every menu NavOption available to the user based on their UserId
     */
    public List getGlobalNavOptions() {
        return m_globalNavOptions;
    }

    /** Based on the DesktopId passed a list will be build of child nodes based on security and parent node ID.
     * @param desktopId Desktop Id that will one verified be used to return a list of NavOptions that are children that have this desktop Id
     * @return List of NavOptions based on a DektopId is passed back , for use in building desktop navs
     */
    public NavOption getDesktopNavOptions(String desktopId) {
        return (NavOption) m_desktopNavOptions.get(desktopId);
    }


    void addDesktopNavOption(String desktopId, NavOption option) {
        m_desktopNavOptions.put(desktopId, option);
    }

    /**
     * Clear the NavAccessor attribute for this session.
     * @param session The session.
     */
    public void clearSession(HttpSession session){
        session.setAttribute(NavAccessor.class.getName(), null);
    }

    //-----------------------------------------------------------------------------------------------
    // For Testing
    //-----------------------------------------------------------------------------------------------

    /** This is just a test rig
     * @param args no arguments need to be passed.
     */
    public static void main(String[] args) {
        LoggerHelper.init();
        NavAccessor n = new NavAccessor();

        log.info("Display Global Menu...");
        NavAccessor.printMenuOptions(n.getGlobalNavOptions(),"");

        for(int i = 1; i < n.m_desktopCounter; i++) {
            NavOption o = n.getDesktopNavOptions(""+i);
            log.info("Display DeskTop... ["+ o.getDesktopId() + "] - " + o.getLabel());
            NavAccessor.printMenuOptions(o.getChildren(),"  ");
        }
    }

    private static void printMenuOptions(List l, String prefix) {
        for(Iterator it = l.iterator(); it.hasNext(); ) {
            NavOption n = (NavOption) it.next();
            log.info(prefix + n.getType() + " - " + n.getLabel() + "  " + (n.isDesktop()?n.getDesktopId():"") );
            if(n.isSubMenu())
                NavAccessor.printMenuOptions(n.getChildren(), prefix + "    ");
            else if(n.isComponent()) {
                log.info(prefix + "...... Component:" + n.getComponent());
                log.info(prefix + "...... Params:" + n.getParameters() + " / " + n.getURL());
            }
        }
    }

    // Private method to determine the current locale
    private static Locale findLocale() {
        // Obtain locale from the LocaleContext, which may return the default Locale for the JVM
        Locale userLocale = LocaleContext.getLocale();
        return userLocale;
    }
}
