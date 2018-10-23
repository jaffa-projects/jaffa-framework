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
package org.jaffa.presentation.portlet;

import org.apache.log4j.Logger;
import java.lang.reflect.Method;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.MDC;
import org.jaffa.locale.LocalizationHelper;
import org.jaffa.locale.UserLocaleService;
import org.jaffa.locale.UserPrefLocaleService;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.security.SecurityManager;
import org.jaffa.security.UserContext;
import org.jaffa.security.VariationContext;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.presentation.portlet.session.LocaleContext;
import org.jaffa.presentation.portlet.widgets.taglib.FormTag;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.ComponentExpiredException;
import org.jaffa.presentation.portlet.session.UserSessionSetupException;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.LocaleHelper;
import org.jaffa.locale.UserLocaleProvider;
import org.owasp.encoder.Encode;

import static org.jaffa.session.ContextManagerFactory.getApplicationRule;

/** This servlet filter is a replacement for the JAFFA PortletServlet and the portlet.security  package.
 * It will invoke the SecurityManager to execute the rest of the request-processing under a SecurityContext. The SecurityContext is stored as a thread variable, which enables a servlet un-aware program (eg. the Persistence Engine) to get a user's authentication information.
 * <p>
 * The following will be executed under the SecurityContext
 * <ol>
 *  <li> - Validate the componentId
 *  <li> - ReAuthenticate/AutoAuthenticate the user and create a valid UserSession object, if required
 *  <li> - Set the VariationContext
 *  <li> - Set the LocaleContext (The variation of the authenticated user will be appended to a new Locale instance).
 * </ol>
 * <p>
 * Additionally, the PortletFilter puts the following in the Mapping Diagnostic Context (MDC) of Log4J
 * <ol>
 *  <li> - userId
 *  <li> - ip
 *  <li> - componentId
 *  <li> - eventId
 * </ol>
 * You can then display them in the logs by using the %X{key-name} in log4j layouts. For eg: "%d %X{userId},%X{ip}: %m%n"
 * NOTE: MDC is enabled only if the logging level for the PortletFilter has been set to DEBUG
 * <p>
 * An application may use this filter as is, or it may extend it and provide an implementation for the initUserInfo() method, to initialize the UserSession object.
 *
 * @author  GautamJ
 * @version 1.0
 * @since 1.3
 */
public class PortletFilter implements Filter {

    private static final Logger log = Logger.getLogger(PortletFilter.class);
    /** Cache the main method used to execute this filter under security */
    private static Method c_method = null;

    // The following constants are used for setting up the MDC in Log4J
    private static final String MDC_USER_ID = "userId";
    private static final String MDC_IP = "ip";
    private static final String MDC_COMPONENT_ID = "componentId";
    private static final String MDC_EVENT_ID = "eventId";


    /** Called by the web container to indicate to a filter that it is being placed into service.
     * This does nothing.
     * @param filterConfig Config object from web.xml
     * @throws ServletException Should never be thrown by this filter
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /** Called by the web container to indicate to a filter that it is being taken out of service.
     * This does nothing.
     */
    public void destroy() {
    }

    /** The doFilter method of the Filter is called by the container each time a request/response pair is passed through the chain due to a client request for a resource at the end of the chain.
     * It will invoke the SecurityManager to execute the doFilterUnderSecurityContext() method under a SecurityContext.
     * @param request Request to process
     * @param response Response to return from filter
     * @param chain Chain of other filters to call
     * @throws IOException Standard Exception For Filter
     * @throws ServletException Standard Exception For Filter
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Get the main method to run
        try {
            if (c_method == null)
                determineMainMethod();
        } catch (Exception e) {
            String str = "Cannot find main method for Filter";
            log.fatal(str, e);
            throw new ServletException(str, e);
        }

        // This flag will indicate if the MDC was setup for Log4J.
        // We could have used log.isDebugEnabled(), but then its quite possible that the logging-level may change during the execution of a thread
        boolean hasMdc = false;

        // Set the SecurityContext and execute the rest of the process under that context
        try {
            hasMdc = setupLog4jMdc((HttpServletRequest) request);
            SecurityManager.runWithContext((HttpServletRequest) request, this, c_method, new Object[]{request, response, chain});
        } catch (IOException e) {
            throw e;
        } catch (ServletException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                if (cause instanceof IOException)
                    throw (IOException) cause;
                else if (cause instanceof ServletException)
                    throw (ServletException) cause;
                else if (cause instanceof RuntimeException)
                    throw (RuntimeException) cause;
            }
            String str = "Unknown Exception Processing Filter";
            log.error(str, e);
            throw new ServletException(str, e);
        } finally {
            if (hasMdc)
                cleanupLog4jMdc();
        }

    }

    /** This method cotains the main process code and should be invoked only under a SecurityContext.
     * The following will be executed under the SecurityContext
     *  1- Validate the componentId
     *  2- ReAuthenticate/AutoAuthenticate the user and create a valid UserSession object, if required
     *  3- Set the LocaleContext
     *  4- Set the VariationContext
     * Finally, it will pass control to the next Filter in the chain.
     * @param request Request to process
     * @param response Response to return from filter
     * @param chain Chain of other filters to call
     * @throws IOException Standard Exception For Filter
     * @throws ServletException Standard Exception For Filter
     */
    public void doFilterUnderSecurityContext(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (log.isDebugEnabled())
            log.debug("Executing the Filter under a Security Context having the UserPrincipal: " + request.getUserPrincipal());

        // Validate the componentId
        validateComponentId(request);

        // ReAuthenticate/AutoAuthenticate the user and create a valid UserSession object, if required
        performSecurityCheck(request);

        // Set the Locale and Variation contexts. Clear the "Condition Evaluation Cache"
        setContexts(request);

        // Continue normal processing of the request
        chain.doFilter(request, response);
    }

    /** Validate the componentId passed in the request stream. It is possible that the user can hit the back button and perform actions on an inactive component. This method will prevent such cases.
     * @param request Request to process
     * @throws IOException Standard Exception For Filter
     * @throws ServletException Standard Exception For Filter
     */
    protected void validateComponentId(HttpServletRequest request)
            throws IOException, ServletException {
        String componentId = request.getParameter(FormTag.PARAMETER_COMPONENT_ID);
        if (componentId != null && componentId.length() > 0) {
            if (!UserSession.isUserSession(request) || UserSession.getUserSession(request).getComponent(componentId) == null) {
                String str = "Component " + componentId + " has expired";
                log.error(str);
                // This exception should be picked up in web.xml and directed to the appropriate JSP, eg. 'pageExpired.jsp'
                // escape the message for html to prevent XSS
                throw new ServletException(Encode.forHtml(str), new ComponentExpiredException());
            } else {
                UserSession.getUserSession(request).getComponent(componentId).updateLastActivityDate();
            }
        }
    }

    /** For an authenticated principal, a UserSession, if one doesn't exist, will be created automatically.
     * If the UserSession already exists, then it will be re-authenticated against the principal. If the existing UserSession is invalid for the principal, then it will be destroyed and a new UserSession will be created for the Principal.
     * The UserSession, if it already exists, will be destroyed, in case the principal is un-authenticated.
     * This will destroy the UserSession and HttpSession, in case any error occurs.
     * @param request Request to process
     * @throws IOException Standard Exception For Filter
     * @throws ServletException Standard Exception For Filter
     */
    protected void performSecurityCheck(HttpServletRequest request)
            throws IOException, ServletException {
        try {
            if (UserSession.isUserSession(request)) {
                if (log.isDebugEnabled())
                    log.debug("Security: Re-Authenticate");
                reAuthenticate(request);
            } else {
                if (log.isDebugEnabled())
                    log.debug("Security: Auto-Authenticate");
                autoAuthenticate(request);
            }
        } catch (IOException e) {
            // Kill the user session if one exists.
            if (UserSession.isUserSession(request))
                UserSession.getUserSession(request).kill();

            // Kill the real session if one exists.
            if (request.getSession(false) != null)
                request.getSession().invalidate();

            throw e;
        } catch (ServletException e) {
            // Kill the user session if one exists.
            if (UserSession.isUserSession(request))
                UserSession.getUserSession(request).kill();

            // Kill the real session if one exists.
            if (request.getSession(false) != null)
                request.getSession().invalidate();

            throw e;
        }
    }

    /** Sets the LocaleContext and the VariationContext.
     * Note that the variation of the authenticated user will be appended to a new Locale instance.
     * However this new Locale instance will not be set on the input request, since there is no standard way to do so.
     * Additionally, the "Condition Evaluation Cache" will be cleared.
     * @param request Request to process
     * @throws IOException Standard Exception For Filter
     * @throws ServletException Standard Exception For Filter
     */
    public void setContexts(HttpServletRequest request)
            throws IOException, ServletException {
        // Set the VariationContext
        if (UserSession.isUserSession(request)) {
            if (log.isDebugEnabled())
                log.debug("Setting the VariationContext to: " + UserSession.getUserSession(request).getVariation());
            VariationContext.setVariation(UserSession.getUserSession(request).getVariation());
        }


        //Save the locale on the UserPrefLocaleService
        saveUserPrefLocale(request);

        // Find locale and set on LocaleContext
        Locale locale = LocaleHelper.string2Locale(getLocale(request));
        if (log.isDebugEnabled())
            log.debug("Setting the Locale to: " + locale);
        LocaleContext.setLocale(locale);

        // Clears the "Condition Evaluation Cache" of the Rules Engine
        try {
            if (log.isDebugEnabled())
                log.debug("Clearing the Condition Evaluation Cache");
            RulesEngineFactory.getRulesEngine().clearConditionEvaluationCache();
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Error in clearing the Condition Evaluation Cache", e);
        }
    }

    /** This is invoked by the performSecurityCheck() method, when it automatically creates a new UserSession object for an authenticated user.
     * By default, this method does nothing. However, a subclass can override it and use it to load any additional user context into the UserSession object.
     * The developer can assume that at the point this is called, the UserSession.getUserId() will return the name of the authenticated user.
     * The developer is responsible for calling the UserSession.setUserData() in this method to store any additional user context.
     *
     * @param us The new UserSession object to be intialized
     * @throws UserSessionSetupException if any error occurs in initializing the UserSession.
     */
    public void initUserInfo(UserSession us) throws UserSessionSetupException {
        // this is to be overridden by derived classes
    }

    /** This is invoked by the performSecurityCheck() method, when it automatically creates a new UserSession object for an authenticated user.
     * By default, this method does nothing. However, a subclass can override it and use it to load any additional user context into the UserSession object.
     * The developer can assume that at the point this is called, the UserSession.getUserId() will return the name of the authenticated user.
     * The developer is responsible for calling the UserSession.setUserData() in this method to store any additional user context.
     *
     * @param us The new UserSession object to be initialized
     * @param userContext contains user information
     * @throws UserSessionSetupException if any error occurs in initializing the UserSession.
     */
    public void initUserInfo(UserSession us, UserContext userContext) throws UserSessionSetupException {
        // this is to be overridden by derived classes
    }

    /** This method sets up the Mapped Diagnostic Context (MDC) in Log4J.
     * The context is created only if the level for PortletFilter is declared to be DEBUG, in which case a true is returned.
     * @param request The request stream.
     * @return true if the level for PortletFilter is declared to be DEBUG.
     */
    protected boolean setupLog4jMdc(HttpServletRequest request) {
        if (log.isInfoEnabled()) {
            StringBuffer buf = new StringBuffer();
            String value = null;

            value = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : null;
            if (value != null) {
                if (buf.length() > 0)
                    buf.append(',');
                buf.append(MDC_USER_ID).append('=').append(value);
                MDC.put(MDC_USER_ID, value);
            }

            value = request.getRemoteAddr();
            if (value != null) {
                if (buf.length() > 0)
                    buf.append(',');
                buf.append(MDC_IP).append('=').append(value);
                MDC.put(MDC_IP, value);
            }

            value = request.getParameter(MDC_COMPONENT_ID);
            if (value != null) {
                if (buf.length() > 0)
                    buf.append(',');
                buf.append(MDC_COMPONENT_ID).append('=').append(value);
                MDC.put(MDC_COMPONENT_ID, value);
            }

            value = request.getParameter(MDC_EVENT_ID);
            if (value != null) {
                if (buf.length() > 0)
                    buf.append(',');
                buf.append(MDC_EVENT_ID).append('=').append(value);
                MDC.put(MDC_EVENT_ID, value);
            }

            log.debug("Setting log4j.MDC: " + buf.toString());
            return true;
        } else
            return false;
    }

    /** This method cleans up the Mapped Diagnostic Context (MDC) in Log4J.
     * It is invoked only if the setupLog4jMdc() had successfully created the MDC.
     */
    protected void cleanupLog4jMdc() {
        if (log.isDebugEnabled())
            log.debug("Cleaning log4j.MDC");
        MDC.remove(MDC_USER_ID);
        MDC.remove(MDC_IP);
        MDC.remove(MDC_COMPONENT_ID);
        MDC.remove(MDC_EVENT_ID);
    }

    /** On entry it is assumed that a UserSession object exists. The purpose of this
     * function is to implement any required logic to re-validate that this UserSession is
     * still ok.
     *
     * On exit, if the UserSession object still exists in the HttpSession it is assumed that
     * it has been re-validated (regardless of whether it has been updated, or re-used for
     * another user). If it has been removed from the session, the assumtion is that
     * the reAuthentication failed.
     *
     * @param request HttpRequest that holds any log in context information
     */
    private void reAuthenticate(HttpServletRequest request)
            throws IOException, ServletException {
        // Get the Current Session
        UserSession us = UserSession.getUserSession(request);

        // If we have an authenticated user ...
        if (request.getUserPrincipal() != null) {
            // ...and it is the same user that the valid session is for, we are ok
            if (us.isValid() && us.getUserId().equals(request.getUserPrincipal().getName())) {
                // no nothing, life is peachy!
                return;
            } else {
                // this is a differnt user, or an invalid session, so kill this UserSession and try an auto-authenticate
                us.kill();
                autoAuthenticate(request);
                return;
            }
        }/* else {
    // We have reached the security manager, with out and authenticatic user,
    // but we have a user session. We must therefore just kill it and continue.
    us.kill();
    }*/
    }

    /** On entry it is assumed that there is no UserSession record. If there are some
     * special reasons for a UserSession to be automatically created, this is the place
     * to do it.
     *
     * On exit from this method, if a UserSession object has been created, it assumes that
     * this is an authenticated Session.
     *
     *
     * @param request HttpRequest that holds any log in context information
     */
    public void autoAuthenticate(HttpServletRequest request) throws IOException, ServletException {
        autoAuthenticate(request, null);
    }

    /**
     * On entry it is assumed that there is no UserSession record. If there are some
     * special reasons for a UserSession to be automatically created, this is the place
     * to do it.
     *
     * On exit from this method, if a UserSession object has been created, it assumes that
     * this is an authenticated Session.
     *
     * @param request HttpRequest that holds any log in context information
     * @param userContext holds any log in context information
     */
    public void autoAuthenticate(HttpServletRequest request, UserContext userContext) throws IOException, ServletException {
        autoAuthenticate(request, userContext, true);
    }
    /**
     * On entry it is assumed that there is no UserSession record. If there are some
     * special reasons for a UserSession to be automatically created, this is the place
     * to do it.
     *
     * On exit from this method, if a UserSession object has been created, it assumes that
     * this is an authenticated Session.
     *
     * @param request HttpRequest that holds any log in context information
     * @param userContext holds any log in context information
     * @param register that lets you register the current session to SessionManager
     */
    public void autoAuthenticate(HttpServletRequest request, UserContext userContext, boolean register) throws IOException, ServletException {

        // Make sure there is an authenticated user
        if (request.getUserPrincipal() != null) {

            // This will create a new session if one doesn't exist
            UserSession us = UserSession.getUserSession(request, register);
            us.setUserId(request.getUserPrincipal().getName());
            try {
                if (userContext == null) {
                    initUserInfo(us);
                } else {
                    initUserInfo(us, userContext);
                }
            } catch (UserSessionSetupException e) {
                ApplicationExceptions appExps = new ApplicationExceptions();
                appExps.add(e);

                // This exception can be picked by some generic error-handling JSP, which will loop thru the ApplicationException objects inside the ApplicationExceptions, printing the corresponding error message.
                throw new ServletException("Error in initializing the UserSession. " + e, appExps);
            }
        }
    }

    /** Uses reflection to create a Method object for the doFilterUnderSecurityContext() method.
     */
    private synchronized void determineMainMethod() throws Exception {
        if (c_method == null)
            c_method = getClass().getMethod("doFilterUnderSecurityContext", new Class[]{HttpServletRequest.class, HttpServletResponse.class, FilterChain.class});
    }

    /**
     * Method to return locale by identifying for it in the respective contexts in the following order.
     * 1. Find from request session attribute "jaffa.user.prefLocale"
     * 2. UserLocaleProvider implementation
     * 3. Find from application rule "commons.default.language"
     * 4. From cookie "jaffa.user.prefLocale"
     * 5. Browser locale (eg:- en_US_DEF)
     * 6. Default locale en_US
     * @param request
     * @return
     */
    public String getLocale(HttpServletRequest request) {
        String locale = "";
        if (request != null) {
            ContextManagerFactory.instance().setThreadContext(request);
            String sessionlocale = (String) request.getSession().getAttribute(UserLocaleProvider.PREF_LOCALE_ID);
            if (sessionlocale!=null && !sessionlocale.isEmpty()){
                locale = sessionlocale;
                if (log.isDebugEnabled())
                    log.debug("Found locale based on session attribute: " + locale);
            }else if(UserLocaleService.getUserLocaleProvider()!=null && UserLocaleService.getUserLocaleProvider().getLocale()!=null
                    && !UserLocaleService.getUserLocaleProvider().getLocale().isEmpty()) {
                locale = UserLocaleService.getUserLocaleProvider().getLocale();
                if (log.isDebugEnabled())
                    log.debug("Found the Locale from User Locale Provider: " + locale);
            }else if (getApplicationRule(UserLocaleProvider.DEF_LOCALE_RULE)!=null && !getApplicationRule(UserLocaleProvider.DEF_LOCALE_RULE).isEmpty()){
                locale = getApplicationRule(UserLocaleProvider.DEF_LOCALE_RULE);
                if(log.isDebugEnabled()){
                    log.debug("Locale found from Application Rule: "+ UserLocaleProvider.DEF_LOCALE_RULE +" :"+locale);
                }
            }
            if (locale == null || locale.isEmpty()){
                //Set locale using cookie
                Cookie[] cookies = request.getCookies();
                if (cookies != null){
                    for (int i = 0; i < cookies.length; i++) {
                        String name = cookies[i].getName();
                        if (log.isDebugEnabled())
                            log.debug("cookie name: " + name);
                        String value = cookies[i].getValue();
                        if (log.isDebugEnabled())
                            log.debug("cookie value: " + value);
                        if (name.equals(UserLocaleProvider.PREF_LOCALE_ID)){
                            if (log.isDebugEnabled())
                                log.debug("Setting the locale based on jaffa.user.prefLocale cookie: " + value);
                            locale = value;
                        }
                    }
                }
            }


            if (locale == null || locale.isEmpty()){
                // Set the LocaleContext with a Locale instance containing the current variation
                Locale browserLocale = request.getLocale();
                if (browserLocale != null && (browserLocale.getVariant() == null || browserLocale.getVariant().length() == 0)) {
                    if (log.isDebugEnabled())
                        log.debug("Adding the variation to the locale based on request locale: " + locale);
                    locale = browserLocale.getLanguage()+"_"+browserLocale.getCountry()+(VariationContext.getVariation()!=null && !VariationContext.NULL_VARIATION.equals(VariationContext.getVariation()) ? "_"+VariationContext.getVariation() : "");
                }
            }

            if(locale == null || locale.isEmpty()){
                locale = UserLocaleProvider.DEF_LOCALE;
            }
        }
        return locale;
    }


    /**
     * Saves user selected locale on UserLocaleProvider Implementation if the user selected language is different from what is stored in the implementation
     * @param request
     */
    private void saveUserPrefLocale(HttpServletRequest request){
        ContextManagerFactory.instance().setThreadContext(request);
        String userSelectedLanguage = LocalizationHelper.getLanguageFromRequestParam(request);
        if(userSelectedLanguage!=null && !userSelectedLanguage.isEmpty() && UserLocaleService.getUserLocaleProvider()!=null
            && UserLocaleService.getUserLocaleProvider().getLocale()!=null && !userSelectedLanguage.equals(UserLocaleService.getUserLocaleProvider().getLocale().toString())){
            UserLocaleService.getUserLocaleProvider().saveLocale(userSelectedLanguage);
        }
    }
}
