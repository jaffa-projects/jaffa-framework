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

package org.jaffa.presentation.portlet.session;

import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.security.JDBCSecurityPlugin;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.ListMap;
import org.jaffa.util.StringHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.io.PrintStream;
import java.util.*;

/** This UserSession Object maintains all the state about the a specific user within the context of the Web Server.
 * This object could be adapted to other context stores (apart from HttpSession) if needed.
 */
public class UserSession implements HttpSessionBindingListener {

    /** Name of the context rule for setting the List of components to be excluded from Garbage Collection */
    public static final String RULE_GC_EXCLUDES = "jaffa.session.componentGarbageCollection.excludes";

    /** Name of the context rule for setting the session timeout value */
    public static final String RULE_TIMEOUT = "jaffa.session.timeoutOverride";

    private static Logger log = Logger.getLogger(UserSession.class);

    /** The key used to add the UserSession object to a HTTP Session.*/
    public static final String USER_ATTRIBUTE = "org.jaffa.presentation.portlet.session.UserInfo";

    /** An Id assigned by the SessionManager */
    private String m_sessionId = null;

    private String m_userId = null;

    // Holds a reference to an object with user data in it
    private Object m_userData = null;

    // Cached so form object can be released from it!
    private HttpSession m_sessionObject = null;

    // Maintain the latest componentId
    private long m_lastComponentId = 0;

    // This will maintain all the components for a UserSession
    private Map m_components = null;

    // This will maintain all the GraphServices implementing ServiceCallback
    private List<ServiceCallback> m_serviceCallbackList = null;

    // Maintain a cache of widgets based on componentId
    private Map m_widgetCache = null;

    //    // Ensures that the user doesn't step out of line
    //    private long m_token = 0;

    // Maintain the image-contents used by the ImageTag when the ImageModel keeps the image 'inMemory'
    private Map m_imageContents = null;

    // Store the address of the remote user.
    private String m_hostAddr = null;


    /** Holds value of property variation. */
    private String m_variation;

    /** Is there a user Session? Returns true if there is a user session.
     * @param request The HTTP request we are processing.
     * @return true if there is a user session.
     */
    public static boolean isUserSession(HttpServletRequest request) {
        return request.getSession(false) != null ? request.getSession().getAttribute(USER_ATTRIBUTE) != null : false;
    }

    /** Get the UserSession object from the servlet session object. If this is the
     * first time this routine is called, a new Session Object will be created and
     * returned. This new object will be added to the session cache, so the next time
     * this is called the same UserSession object will be returned.
     * @param request The HTTP request we are processing.
     * @return This returns either a new, or a current UserSession object.
     */
    public static UserSession getUserSession(HttpServletRequest request) {
        UserSession us = ( UserSession ) request.getSession().getAttribute(USER_ATTRIBUTE);
        if (us == null)
            us = new UserSession(request, true);
        return us;
    }

    /** Get the UserSession object from the servlet session object. If this is the
     * first time this routine is called, a new Session Object will be created and
     * returned. This new object will be added to the session cache, so the next time
     * this is called the same UserSession object will be returned.
     * @param request The HTTP request we are processing.
     * @param register that lets you register the current session to SessionManager
     * @return This returns either a new, or a current UserSession object.
     */
    public static UserSession getUserSession(HttpServletRequest request, boolean register) {
        UserSession us = ( UserSession ) request.getSession().getAttribute(USER_ATTRIBUTE);
        if (us == null)
            us = new UserSession(request, register);
        return us;
    }

    private UserSession(HttpServletRequest req, boolean register) {
        // Initialize Variables
        m_components = new ListMap();
        m_widgetCache = new HashMap();
        m_serviceCallbackList = new ArrayList<ServiceCallback>();

        // Let the UserSession object keep a reference to the session object so it
        // can flush attributed out of the attribute cache. This was added so that when a
        // panel is removed, its associated form object can be dropped from the session cache
        m_sessionObject = req.getSession();

        // Store the Address of this request so we can identify this session easier later on
        // in the session manager tool
        m_hostAddr = req.getRemoteHost();

        // Add the user session into the session
        req.getSession().setAttribute(USER_ATTRIBUTE, this);

        // Register this session
        if(register) {
            SessionManager.addSession(this);
        }
    }


    /** Getter for property sessionId.
     * @return Value of property sessionId.
     */
    public String getSessionId() {
        return m_sessionId;
    }

    /** Setter for property sessionId.
     * @param sessionId New value of property sessionId.
     */
    public void setSessionId(String sessionId) {
        m_sessionId = sessionId;
    }

    /** Getter for property userId.
     * @return Value of property userId.
     */
    public String getUserId() {
        return m_userId;
    }

    /** Setter for property userId.
     * @param userId New value of property userId.
     */
    public void setUserId(String userId) {
        m_userId = userId;
    }

    /** Check to see if the UserSession object is valid. A UserSession is valid if the userId is not null.
     * @return true if the userId is not null.
     */
    public boolean isValid() {
        return m_userId != null;
    }

    /** Returns an object that should contain user data useful to the consuming application.
     * @return an object that should contain user data useful to the consuming application.
     */
    public Object getUserData() {
        return m_userData;
    }


    /** This stores a user reslated object in the frameworks UserSession object.
     * @param userData This is the application specific object that contains extra user information.
     */
    public void setUserData(Object userData) {
        m_userData = userData;
    }


    /** Adds a component to the internal cache.
     * @param comp the component to be added.
     */
    public void addComponent(Component comp) {
        m_components.put(comp.getComponentId(), comp);
    }

    /** Get a Component object based on a componentId from the list of created components that have been created by this user.
     * @param compId the componentId.
     * @return the Component object based on the componentId.
     */
    public Component getComponent(String compId) {
        Component c = (Component) m_components.get(compId);
        return c;
    }

    /** Remove the component from the internal cache.
     * @param comp The component to be removed.
     */
    public void removeComponent(Component comp) {
        String componentId = comp.getComponentId();
        m_components.remove(componentId);
        m_widgetCache.remove(componentId);
    }

    /** Get a named attribute from the servlet session.
     * @param name The attribute name.
     * @return The named object from HttpSession associated to the UserSession. Returns null if named obejct is not found.
     */
    public Object getSessionObject(String name) {
        return m_sessionObject.getAttribute(name);
    }

    /** Drop a named attribute from the servlet session.
     * @param name The attribute name.
     */
    public void dropSessionObject(String name) {
        if(m_sessionObject != null) {
            Object obj = m_sessionObject.getAttribute(name);

            // invoke the cleanup() method for a form-bean
            if (obj != null && obj instanceof FormBase)
                ((FormBase) obj).cleanup();

            // remove the object from the session
            m_sessionObject.removeAttribute(name);

            if (log.isDebugEnabled())
                log.debug("Removed Object '" + name + "' from Session Cache");
        }
    }


    /** This function kills the user session object.
     * It remove itself from the HttpSession cache.
     * It will kill all related components.
     * It will de-register from the Session Manager.
     * It will null out all internal references.
     */
    public void kill() {
        // Remove from HttpSession
        if (m_sessionObject != null) {
            if (log.isDebugEnabled())
                log.debug("Removing the UserSession from the HttpSession. This should in turn call the valueUnbound method.");
            m_sessionObject.removeAttribute(USER_ATTRIBUTE);
        }
    }

    /** This function kills all related components.
     */
    public void killAllComponents() {
        if (m_components != null) {
            // Create an array of componentIds
            String[] componentIds = new String[m_components.size()];
            Iterator itr = m_components.keySet().iterator();
            for (int i = 0; itr.hasNext(); i++)
                componentIds[i] = (String) itr.next();

            // Now loop thru the array.. Get the component.. & kill it (if its still alive)
            // Note: its quite possible that a component could have been killed by another !!!
            for (int i = 0; i < componentIds.length; i++) {
                Component c = (Component) m_components.get(componentIds[i]);
                if (c != null) {
                    if (log.isDebugEnabled())
                        log.debug("Killing Component : " + c.getComponentId() + " - " + c.getComponentDefinition().getComponentName() );
                    c.quit();
                }
            }
        }
        if(m_serviceCallbackList != null && m_serviceCallbackList.size() > 0){
            for(ServiceCallback serviceCallBack : m_serviceCallbackList){
                serviceCallBack.quitService();
            }
            m_serviceCallbackList.clear();
        }
        //        Object[] components = m_components.values().toArray();
        //        for(int i = 0; i < components.length; i++) {
        //            Component c = (Component) components[i];
        //            if (log.isDebugEnabled())
        //                log.debug("Killing Component : " + c.getComponentId() + " - " + c.getComponentDefinition().getComponentName() );
        //            // Quit Component
        //            c.quit();
        //        }
    }


    //    public String getCurrentToken() {
    //        return Long.toString(m_token);
    //    }
    //
    //    public String getNewToken() {
    //        return Long.toString(++m_token);
    //    }

    /** Returns a new componentId.
     * @return a new componentId.
     */
    public String getNextComponentId() {
        return Long.toString(++m_lastComponentId);
    }

    /** Return an existing WidgetCache for the key.
     * Create a new WidgetCache if it doesnt already exist.
     * The key will preferrably be a componentId.
     * @param key The key to be used for the widget cache.
     * @return a WidgetCache for the key.
     */
    public WidgetCache getWidgetCache(String key){
        WidgetCache wc = (WidgetCache) m_widgetCache.get(key);
        if (wc == null) {
            wc = new WidgetCache();
            m_widgetCache.put(key, wc);
        }
        return wc;
    }

    /** Returns the image for the input key.
     * The ImageModel may store the image contents into the UserSession (instead of the local filesystem).
     * The getImage.jsp will then use this method to display the image.
     * @param key The key.
     * @return the image for the input key.
     */
    public byte[] getImageContents(String key) {
        if (m_imageContents != null) {
            Object[] image = (Object[]) m_imageContents.get(key);
            return (byte[]) image[0];
        } else {
            return null;
        }
    }

    /** Returns the image mime-type for the input key.
     * The ImageModel may store the image contents into the UserSession (instead of the local filesystem).
     * The getImage.jsp will then use this method to display the image.
     * @param key The key.
     * @return the image mime-type for the input key.
     */
    public String getImageMimeType(String key) {
        if (m_imageContents != null) {
            Object[] image = (Object[]) m_imageContents.get(key);
            return (String) image[1];
        } else {
            return null;
        }
    }

    /** Adds the image and its mime-type to the UserSession.
     * The ImageModel may store the image contents into the UserSession (instead of the local filesystem).
     * The getImage.jsp will then use this method to display the image.
     * @param key The key to be used for storing the image.
     * @param imageContents The image.
     * @param mimeType The mime-type.
     */
    public void addImage(String key, byte[] imageContents, String mimeType) {
        if (m_imageContents == null)
            m_imageContents = new HashMap();
        Object[] image = new Object[] {imageContents, mimeType};
        m_imageContents.put(key, image);
    }

    /** Removes the image for the input key.
     * @param key The key.
     */
    public void removeImage(String key) {
        if (m_imageContents != null)
            m_imageContents.remove(key);
    }

    /** Display all internal Session Info in the System.out stream.
     */
    public void showInfo() {
        PrintStream p = System.out;
        p.println("---User Info----");
        p.println("User Id: " + m_userId);

        p.println("Components...");
        for(Iterator i = m_components.keySet().iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            Component c = (Component) m_components.get(key);
            p.println("   Id: " + c.getComponentId() );
            p.println("   Name: " + c.getClass().getName() );
            p.println("");
        }
    }

    /** Added for the administration tool to get data about the session
     * @return The HttpSession to which this object belongs.
     */
    public HttpSession getHttpSession() {
        return m_sessionObject;
    }

    /** Added for the administration tool to get data about the components
     * @return The list of components for the user.
     */
    public Collection getComponents() {
        if(m_components == null)
            return null;
        else
            return m_components.values();
    }

    /** Get the host address of this user, this is based on the host obtained in the first request
     * that was used to establish the session
     * @return host address of this user.
     */
    public String getUserHostAddr() {
        return m_hostAddr;
    }
    
    /** This will perform garbage collection of idle components. Idle components are those components which have had no activity performed in the last 'timeOutMinutes' minutes.
     * @param timeOutMinutes The minutes used to determine idle components.
     */
    public void garbageCollectIdleComponents(int timeOutMinutes) {
        List excludeComponents = null;
        String[] ec = StringHelper.parseString((String) ContextManagerFactory.instance().getProperty(RULE_GC_EXCLUDES) ,",");
        if(ec==null || ec.length==0)
            excludeComponents = new ArrayList();
        else
            excludeComponents = Arrays.asList(ec);
        
        
        // create an array of Component objects
        Component[] components = new Component[m_components.size()];
        Iterator itr = m_components.values().iterator();
        for (int i = 0; itr.hasNext(); i++)
            components[i] = (Component) itr.next();
        
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            
            if (component.isActive() && !excludeComponents.contains(component.getComponentDefinition().getComponentName())) {
                // Collection of componentIds. This will avoid circular references
                Collection checkedComponentIdsList = new ArrayList();
                
                if (hasComponentTimedOut(component, DateTime.addMinute(new DateTime(), -timeOutMinutes), checkedComponentIdsList)) {
                    if (log.isInfoEnabled())
                        log.info("Garbage collecting component " + component.getComponentDefinition().getComponentName() + " having id " + component.getComponentId());
                    component.quit();
                }
            }
        }
    }

    private boolean hasComponentTimedOut(Component component, DateTime time, Collection checkedComponentIdsList) {
        boolean timedOut;
        if (!component.isActive()) {
            // assume Inactive component = TimedOut component
            timedOut = true;
        } else {
            timedOut = component.returnLastActivityDate().isBefore(time);
            if (timedOut && !checkedComponentIdsList.contains(component.getComponentId())) {
                checkedComponentIdsList.add(component.getComponentId());
                Collection childComponents = component.returnChildComponents();
                if (childComponents != null) {
                    for (Iterator i = childComponents.iterator(); i.hasNext();) {
                        Component childComponent = (Component) i.next();
                        timedOut = hasComponentTimedOut(childComponent, time, checkedComponentIdsList);
                        if (!timedOut)
                            break;
                    }
                }
            }
        }
        return timedOut;
    }

    /** This is invoked, whenever an instance of this class is added to the HttpSession object.
     * Currently, this method will set the session timeout value
     * @param httpSessionBindingEvent the event that identifies the session.
     */
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
       if (log.isDebugEnabled())
          log.debug("valueBound event");
     
       if(ContextManagerFactory.instance().getProperty(UserSession.RULE_TIMEOUT) != null ) {
          if (log.isDebugEnabled())
             log.debug("Setting Timeout to "+(String) ContextManagerFactory.instance().getProperty(UserSession.RULE_TIMEOUT));
          httpSessionBindingEvent.getSession().setMaxInactiveInterval(Integer.parseInt((String) ContextManagerFactory.instance().getProperty(UserSession.RULE_TIMEOUT)));
       }
    }

    /** This is invoked, whenever an instance of this class is removed from the HttpSession object.
     * This can happen by an explicit session.removeAttribute(), or if the HttpSession is invalidated or if the HttpSession times out.
     * It will kill all related components.
     * It will de-register from the Session Manager.
     * It will null out all internal references.
     * @param httpSessionBindingEvent the event that identifies the session.
     */
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        if (log.isDebugEnabled())
            log.debug("The valueUnbound method has been invoked. This will kill the UserSession.");

        // kill this usersession
        killUserSession();
    }

    /** This function kills the user session object.
     * It will kill all related components.
     * It will de-register from the Session Manager.
     * It will null out all internal references.
     */
    private void killUserSession() {
        log.debug("Killing User Session : " + m_userId);

        // kill all related components
        killAllComponents();

        // Remove from Session Manager
        SessionManager.removeSession(this);
        
        // Remove the User Cache from the SecurityPlugin
        JDBCSecurityPlugin.clearCacheForUser(m_userId);
        
        // Remove extra references.
        m_userId = null;
        m_userData = null;
        m_sessionObject = null;
        m_components = null;
        m_widgetCache = null;
        m_imageContents = null;
        m_sessionObject = null;
        m_serviceCallbackList = null;
    }

    /** Getter for property variation.
     * @return Value of property variation.
     */
    public String getVariation() {
        return m_variation;
    }

    /** Setter for property variation.
     * @param variation New value of property variation.
     */
    public void setVariation(String variation) {
        m_variation = variation;
    }

    /**
     * adds serviceCallback to list
     * @param serviceCallback
     */
    public void addCallbackHandler(ServiceCallback serviceCallback){
        m_serviceCallbackList.add(serviceCallback);
    }

}
