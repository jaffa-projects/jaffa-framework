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

package org.jaffa.presentation.portlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServletWrapper;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.upload.MultipartRequestWrapper;
import org.apache.struts.util.ModuleUtils;
import org.apache.struts.util.RequestUtils;

/** This class extends the TilesRequestProcessor (since we use the Tiles plugin), which in turn extends the struts RequestProcessor.
 * The struts RequestProcessor invokes the reset() method on a form-bean in its processPopulate() method
 * Jaffa sets the component on a form-bean in the reset() method by obtaining the componentId from the request.
 * In MultiPart posts (i.e. when uploading files), the componentId is available only when the RequestUtils.populate() method is invoked.
 * Hence this class overrides the processPopulate() method of the RequestProcessor to not invoke the reset() method.
 * Instead a custom version of the RequestUtils.populate() method has been created to invoke the reset() method.
 * <p>
 * The customRequestUtilsPopulate() method also handles the scenario when the size of the file being uploaded exceeds the maximum allowed.
 * After it detects that condition, the struts' default implementation sets an attribute in the request stream and terminates form population.
 * The event handling in Jaffa depends on the value of the 'eventId' parameter. The 'eventId' cannot be determined since the form population is terminated,
 * and consequently the ActionHandler fails. Hence our customRequestUtilsPopulate() method throws a ServletException if the limit is breached.
 * An alternative would be continue form population and let the FormBase (via its doValidate() method) or the application handle the ATTRIBUTE_MAX_LENGTH_EXCEEDED.
 * But this requires a change to the MultipartRequestHandler implementation as well.
 * <p>
 * In summary, use this class if file-uploads do not work for you.
 * Add the following fragment to the struts-config file after the action-mappings element.
 *   <controller>
 *       <set-property  property="processorClass" value="org.jaffa.presentation.portlet.CustomRequestProcessor"/>
 *   </controller>
 */
public class CustomRequestProcessor extends TilesRequestProcessor {

    /**
     * <p>Populate the properties of the specified <code>ActionForm</code> instance from
     * the request parameters included with this request.  In addition,
     * request attribute <code>Globals.CANCEL_KEY</code> will be set if
     * the request was submitted with a button created by
     * <code>CancelTag</code>.</p>
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param form The ActionForm instance we are populating
     * @param mapping The ActionMapping we are using
     *
     * @exception ServletException if thrown by RequestUtils.populate()
     */
    protected void processPopulate(HttpServletRequest request, HttpServletResponse response,
            ActionForm form, ActionMapping mapping) throws ServletException {
        if (form == null)
            return;

        // Populate the bean properties of this ActionForm instance
        if (log.isDebugEnabled())
            log.debug(" Populating bean properties from this request");

        form.setServlet(this.servlet);

        //*** Jaffa-customization: Moved in the custom populate method ***
        //form.reset(mapping, request);

        if (mapping.getMultipartClass() != null)
            request.setAttribute(Globals.MULTIPART_KEY, mapping.getMultipartClass());

        //*** Jaffa-customization: Invoke a custom version of the RequestUtils.populate() method, which will invoke the reset() method ***
        //RequestUtils.populate(form, mapping.getPrefix(), mapping.getSuffix(), request);
        customRequestUtilsPopulate(form, mapping.getPrefix(), mapping.getSuffix(), request, mapping);

        // Set the cancellation request attribute if appropriate
        if ((request.getParameter(Constants.CANCEL_PROPERTY) != null) || (request.getParameter(Constants.CANCEL_PROPERTY_X) != null))
            request.setAttribute(Globals.CANCEL_KEY, Boolean.TRUE);
    }

    /**
     * <p>Populate the properties of the specified JavaBean from the specified
     * HTTP request, based on matching each parameter name (plus an optional
     * prefix and/or suffix) against the corresponding JavaBeans "property
     * setter" methods in the bean's class. Suitable conversion is done for
     * argument types as described under <code>setProperties</code>.</p>
     *
     * <p>If you specify a non-null <code>prefix</code> and a non-null
     * <code>suffix</code>, the parameter name must match <strong>both</strong>
     * conditions for its value(s) to be used in populating bean properties.
     * If the request's content type is "multipart/form-data" and the
     * method is "POST", the <code>HttpServletRequest</code> object will be wrapped in
     * a <code>MultipartRequestWrapper</code object.</p>
     *
     * @param bean The JavaBean whose properties are to be set
     * @param prefix The prefix (if any) to be prepend to bean property
     *               names when looking for matching parameters
     * @param suffix The suffix (if any) to be appended to bean property
     *               names when looking for matching parameters
     * @param request The HTTP request whose parameters are to be used
     *                to populate bean properties
     *
     * @exception ServletException if an exception is thrown while setting
     *            property values
     */
    protected static void customRequestUtilsPopulate(
            Object bean,
            String prefix,
            String suffix,
            HttpServletRequest request,
            ActionMapping mapping)
            throws ServletException {

        // Build a list of relevant request parameters from this request
        HashMap properties = new HashMap();
        // Iterator of parameter names
        Enumeration names = null;
        // Map for multipart parameters
        Map multipartParameters = null;

        String contentType = request.getContentType();
        String method = request.getMethod();
        boolean isMultipart = false;

        if ((contentType != null)
            && (contentType.startsWith("multipart/form-data"))
            && (method != null)
            && method.equalsIgnoreCase("POST")) {

            // Get the ActionServletWrapper from the form bean
            ActionServletWrapper servlet;
            if (bean instanceof ActionForm) {
                servlet = ((ActionForm) bean).getServletWrapper();
            } else {
                throw new ServletException(
                        "bean that's supposed to be "
                        + "populated from a multipart request is not of type "
                        + "\"org.apache.struts.action.ActionForm\", but type "
                        + "\""
                        + bean.getClass().getName()
                        + "\"");
            }

            // Obtain a MultipartRequestHandler
            MultipartRequestHandler multipartHandler = getMultipartHandler(request);

            // Set the multipart request handler for our ActionForm.
            // If the bean isn't an ActionForm, an exception would have been
            // thrown earlier, so it's safe to assume that our bean is
            // in fact an ActionForm.
            ((ActionForm) bean).setMultipartRequestHandler(multipartHandler);

            if (multipartHandler != null) {
                isMultipart = true;
                // Set servlet and mapping info
                servlet.setServletFor(multipartHandler);
                multipartHandler.setMapping(
                        (ActionMapping) request.getAttribute(Globals.MAPPING_KEY));
                // Initialize multipart request class handler
                multipartHandler.handleRequest(request);
                //stop here if the maximum length has been exceeded
                Boolean maxLengthExceeded =
                        (Boolean) request.getAttribute(
                        MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
                if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())) {
                    // *** Jaffa-customization: Do not just terminate form population. Instead throw a ServletException ***
                    //return;
                    throw new ServletException("The size of the file being uploaded exceeds the maximum allowed " + ModuleUtils.getInstance().getModuleConfig(request).getControllerConfig().getMaxFileSize());
                }
                //retrieve form values and put into properties
                multipartParameters = getAllParametersForMultipartRequest(
                        request, multipartHandler);
                names = Collections.enumeration(multipartParameters.keySet());
            }
        }

        if (!isMultipart) {
            names = request.getParameterNames();
        }

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String stripped = name;
            if (prefix != null) {
                if (!stripped.startsWith(prefix)) {
                    continue;
                }
                stripped = stripped.substring(prefix.length());
            }
            if (suffix != null) {
                if (!stripped.endsWith(suffix)) {
                    continue;
                }
                stripped = stripped.substring(0, stripped.length() - suffix.length());
            }
            Object parameterValue = null;
            if (isMultipart) {
                parameterValue = multipartParameters.get(name);
            } else {
                parameterValue = request.getParameterValues(name);
            }

            // Populate parameters, except "standard" struts attributes
            // such as 'org.apache.struts.action.CANCEL'
            if (!(stripped.startsWith("org.apache.struts."))) {
                properties.put(stripped, parameterValue);
            }
        }

        // *** Jaffa-customization: Reset the bean ***
        try {
            if(log.isDebugEnabled())
                log.debug("Calling FormBean reset()");
            ((ActionForm) bean).reset(mapping, request);
        } catch (Exception e) {
            throw new ServletException("FormBean.reset", e);
        }

        // Set the corresponding properties of our bean
        try {
            BeanUtils.populate(bean, properties);
        } catch(Exception e) {
            throw new ServletException("BeanUtils.populate", e);
        }

    }



    /***********************************************************************************************/
    /* The following methods are straight copies of the respective private methods of RequestUtils */
    /***********************************************************************************************/


    /**
     * <p>Try to locate a multipart request handler for this request. First, look
     * for a mapping-specific handler stored for us under an attribute. If one
     * is not present, use the global multipart handler, if there is one.</p>
     *
     * @param request The HTTP request for which the multipart handler should
     *                be found.
     * @return the multipart handler to use, or null if none is
     *         found.
     *
     * @exception ServletException if any exception is thrown while attempting
     *                             to locate the multipart handler.
     */
    private static MultipartRequestHandler getMultipartHandler(HttpServletRequest request)
            throws ServletException {

        MultipartRequestHandler multipartHandler = null;
        String multipartClass = (String) request.getAttribute(Globals.MULTIPART_KEY);
        request.removeAttribute(Globals.MULTIPART_KEY);

        // Try to initialize the mapping specific request handler
        if (multipartClass != null) {
            try {
                multipartHandler = (MultipartRequestHandler) RequestUtils.applicationInstance(multipartClass);
            } catch(ClassNotFoundException cnfe) {
                log.error(
                        "MultipartRequestHandler class \""
                        + multipartClass
                        + "\" in mapping class not found, "
                        + "defaulting to global multipart class");
            } catch(InstantiationException ie) {
                log.error(
                        "InstantiationException when instantiating "
                        + "MultipartRequestHandler \""
                        + multipartClass
                        + "\", "
                        + "defaulting to global multipart class, exception: "
                        + ie.getMessage());
            } catch(IllegalAccessException iae) {
                log.error(
                        "IllegalAccessException when instantiating "
                        + "MultipartRequestHandler \""
                        + multipartClass
                        + "\", "
                        + "defaulting to global multipart class, exception: "
                        + iae.getMessage());
            }

            if (multipartHandler != null) {
                return multipartHandler;
            }
        }

        ModuleConfig moduleConfig =
                ModuleUtils.getInstance().getModuleConfig(request);

        multipartClass = moduleConfig.getControllerConfig().getMultipartClass();

        // Try to initialize the global request handler
        if (multipartClass != null) {
            try {
                multipartHandler = (MultipartRequestHandler) RequestUtils.applicationInstance(multipartClass);

            } catch(ClassNotFoundException cnfe) {
                throw new ServletException(
                        "Cannot find multipart class \""
                        + multipartClass
                        + "\""
                        + ", exception: "
                        + cnfe.getMessage());

            } catch(InstantiationException ie) {
                throw new ServletException(
                        "InstantiationException when instantiating "
                        + "multipart class \""
                        + multipartClass
                        + "\", exception: "
                        + ie.getMessage());

            } catch(IllegalAccessException iae) {
                throw new ServletException(
                        "IllegalAccessException when instantiating "
                        + "multipart class \""
                        + multipartClass
                        + "\", exception: "
                        + iae.getMessage());
            }

            if (multipartHandler != null) {
                return multipartHandler;
            }
        }

        return multipartHandler;
    }

    /**
     *<p>Create a <code>Map</code> containing all of the parameters supplied for a multipart
     * request, keyed by parameter name. In addition to text and file elements
     * from the multipart body, query string parameters are included as well.</p>
     *
     * @param request The (wrapped) HTTP request whose parameters are to be
     *                added to the map.
     * @param multipartHandler The multipart handler used to parse the request.
     *
     * @return the map containing all parameters for this multipart request.
     */
    private static Map getAllParametersForMultipartRequest(
            HttpServletRequest request,
            MultipartRequestHandler multipartHandler) {

        Map parameters = new HashMap();
        Hashtable elements = multipartHandler.getAllElements();
        Enumeration e = elements.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            parameters.put(key, elements.get(key));
        }

        if (request instanceof MultipartRequestWrapper) {
            request = ((MultipartRequestWrapper) request).getRequest();
            e = request.getParameterNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                parameters.put(key, request.getParameterValues(key));
            }
        } else {
            log.debug("Gathering multipart parameters for unwrapped request");
        }

        return parameters;
    }

}
