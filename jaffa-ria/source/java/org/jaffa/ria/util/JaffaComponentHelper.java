/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2012 JAFFA Development Group
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
 * 1. Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3. The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4. Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5. Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.ria.util;

import java.security.AccessControlException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.ComponentDefinition;
import org.jaffa.presentation.portlet.component.ComponentSecurity;
import org.jaffa.presentation.portlet.component.componentdomain.BooleanTypeParam;
import org.jaffa.presentation.portlet.component.componentdomain.IntTypeParam;
import org.jaffa.presentation.portlet.component.componentdomain.Loader;
import org.jaffa.presentation.portlet.component.componentdomain.ObjectTypeParam;
import org.jaffa.presentation.portlet.component.componentdomain.StringTypeParam;
import org.jaffa.util.URLHelper;

public class JaffaComponentHelper {

    private static final Logger LOGGER = Logger.getLogger(JaffaComponentHelper.class);

    private static final String INTEGER_INVALID_VALUE_PREFIX = "ERROR: The '";
    private static final String INTEGER_INVALID_VALUE__SUFFIX_1 = "' is not a valid value for the '";
    private static final String INTEGER_INVALID_VALUE__SUFFIX_2 = "' parameter.";

    /**
     * The <code>ComponentDefinition</code> class holds the information for a
     * component as defined in the components.xml file.
     */
    private ComponentDefinition component;
    /** The <code>HttpServletRequest</code> HTTP Servlet Request. */
    private HttpServletRequest request;

    /**
     * Constructs the <tt>JaffaComponentHelper</tt> class based on a component
     * name and a servlet request.
     *
     * @param componentName <code>String</code> the component name.
     * @param request <code>HttpServletRequest</code> the servlet request.
     * @throws <code>AccessControlException</code> the exception to indicate
     *         that a requested access (to a critical system resource such as
     *         the file system or the network) is denied.
     */
    public JaffaComponentHelper(final String componentName, final HttpServletRequest request) throws AccessControlException {
        this.request = request;
        final Map<String, ComponentDefinition> pool = Loader.getComponentPool();
        if (pool != null) {
            component = (ComponentDefinition) pool.get(componentName);
        }
    }

    /**
     * Constructs the <tt>JaffaComponentHelper</tt> class based on a component
     * name and a servlet request.
     *
     * @param component <code>ComponentDefinition</code> class holds the
     *            information for a component as defined in the components.xml
     *            file.
     * @param request <code>HttpServletRequest</code> the servlet request.
     * @throws <code>AccessControlException</code> the exception to indicate
     *         that a requested access (to a critical system resource such as
     *         the file system or the network) is denied.
     */
    public JaffaComponentHelper(final ComponentDefinition component, final HttpServletRequest request) throws AccessControlException {
        this.request = request;
        this.component = component;
    }

    /**
     * Returns a JavaScript string to construct a 'params' variable on the page.
     * This will contain an object that has some static parameters in there as
     * well as parameters provided in the Query URL.
     * <p>
     * Query parameters will only be accepted if they are first listed in the
     * component metadata, and that any required validation specified in the
     * metadata (like mandatory, data type, etc) passes.
     * <p>
     * The static params provided are:
     *
     * <pre>
     * - appCtx - This is the context string of
     * the application, i.e. http://localhost:8080/Jaffa
     * </pre>
     *
     * <pre>
     * - currentUser - This is
     * the name of the authenticated user from 'principal.name"
     * </pre>
     *
     * @return <code>String</code> a JavaScript string to construct a 'params'
     *         variable on the page.
     * @throws <code>InvalidParameterException</code> the exception, designed
     *         for use by the <tt>JaffaComponentHelper</tt> class, is thrown
     *         when an invalid parameter is missing.
     * @throws <code>InvalidParameterException</code> the exception, designed
     *         for use by the JCA/JCE engine classes, is thrown when an invalid
     *         parameter is passed to a method.
     */
    public String writeParameterData() throws MissingParameterException, InvalidParameterException {

        if (request == null)
            return null;

        final List<Object> params = component.getParameters();

        if (params == null)
            return null;

        final StringBuilder sb = new StringBuilder();
        sb.append("var params={\n");
        sb.append("appCtx:\"" + request.getContextPath() + "\",\n");
        if (org.jaffa.security.SecurityManager.getPrincipal() != null) {
            sb.append("currentUser:'" + org.jaffa.security.SecurityManager.getPrincipal().getName());
            sb.append("',\n");
        }

        int i = 0;
        for (final Object param : params) {

            String name = null;
            String value = null;

            if (param != null && param instanceof ObjectTypeParam) {
                value = validateObjectParam((ObjectTypeParam) param);
                name = ((ObjectTypeParam) param).getName();
            } else if (param != null && param instanceof StringTypeParam) {
                value = validateStringParam((StringTypeParam) param);
                name = ((StringTypeParam) param).getName();
            } else if (param != null && param instanceof IntTypeParam) {
                value = validateIntParam((IntTypeParam) param);
                name = ((IntTypeParam) param).getName();
            } else if (param != null && param instanceof BooleanTypeParam) {
                value = validateBooleanParam((BooleanTypeParam) param);
                name = ((BooleanTypeParam) param).getName();
            }

            if (name != null) {
                if (i > 0)
                    sb.append(",\n");
                sb.append(name + ":\"" + (value != null ? value.toString() : "") + "\"");
                i++;
            }
        }
        sb.append("\n};");
        return sb.toString();
    }

    /**
     * Validates the object parameter.
     *
     * @param objectTypeParam <code>ObjectTypeParam</code> the Java class for
     *            objectTypeParam complex type.
     * @return <code>String</code> the parameter value.
     * @throws <code>MissingParameterException</code> the exception is thrown
     *         when a required parameter is missing a value.
     * @throws <code>InvalidParameterException</code> the exception is thrown
     *         when a parameter has invalid value.
     */
    private String validateObjectParam(final ObjectTypeParam objectTypeParam) throws MissingParameterException, InvalidParameterException {

        final String value = validateStringParam(objectTypeParam);

        final String name = objectTypeParam.getName();
        final String className = objectTypeParam.getClassName();
        if (value != null && !value.equals(""))
          validateJSONObject(className, value, INTEGER_INVALID_VALUE_PREFIX + value + INTEGER_INVALID_VALUE__SUFFIX_1 + name + INTEGER_INVALID_VALUE__SUFFIX_2);

        return value;
    }

    /**
     * Validates the string parameter.
     * @param stringTypeParam <code>StringTypeParam</code> the Java class for
     *            stringTypeParam complex type.
     * @return <code>String</code> the parameter value.
     * @throws <code>MissingParameterException</code> the exception is thrown
     *         when a required parameter is missing a value.
     * @throws <code>InvalidParameterException</code> the exception is thrown
     *         when a parameter has invalid value.
     */
    private String validateStringParam(final StringTypeParam stringTypeParam) throws MissingParameterException, InvalidParameterException {

        final String name = stringTypeParam.getName();
        String value = request.getParameter(name);
        if ((value == null || 0 == value.length()) && stringTypeParam.getValue() != null) {
            value = stringTypeParam.getValue();
        }

        if (stringTypeParam.isRequired() != null && stringTypeParam.isRequired())
            checkRequiredParameterValue(name, value);

        try {
            String.valueOf(value);
        } catch (Exception e) {
            throw new InvalidParameterException(INTEGER_INVALID_VALUE_PREFIX + value + INTEGER_INVALID_VALUE__SUFFIX_1 + name + INTEGER_INVALID_VALUE__SUFFIX_2);
        }

        return value;
    }

    /**
     * Validates the integer parameter.
     *
     * @param intTypeParam <code>IntTypeParam</code> the Java class for
     *            intTypeParam complex type.
     * @return <code>String</code> the parameter value.
     * @throws <code>MissingParameterException</code> the exception is thrown
     *         when a required parameter is missing a value.
     * @throws <code>InvalidParameterException</code> the exception is thrown
     *         when a parameter has invalid value.
     */
    private String validateIntParam(final IntTypeParam intTypeParam) throws MissingParameterException, InvalidParameterException {

        final String name = intTypeParam.getName();
        String value = request.getParameter(name);
        if ((value == null || 0 == value.length()) && intTypeParam.getValue() != null) {
            value = intTypeParam.getValue().toString();
        }

        if (intTypeParam.isRequired() != null && intTypeParam.isRequired())
            checkRequiredParameterValue(name, value);

        try {
            Long.valueOf(value);
        } catch (Exception e) {
            throw new InvalidParameterException(INTEGER_INVALID_VALUE_PREFIX + value + INTEGER_INVALID_VALUE__SUFFIX_1 + name + INTEGER_INVALID_VALUE__SUFFIX_2);
        }

        return value;
    }

    /**
     * Validates the integer parameter.
     *
     * @param BooleanTypeParam <code>booleanTypeParam</code> the Java class for
     *            booleanTypeParam complex type.
     * @return <code>String</code> the parameter value.
     * @throws <code>MissingParameterException</code> the exception is thrown
     *         when a required parameter is missing a value.
     * @throws <code>InvalidParameterException</code> the exception is thrown
     *         when a parameter has invalid value.
     */
    private String validateBooleanParam(final BooleanTypeParam booleanTypeParam) throws MissingParameterException, InvalidParameterException {

        final String name = booleanTypeParam.getName();
        String value = request.getParameter(name);

        if (booleanTypeParam.isRequired() != null && booleanTypeParam.isRequired())
            checkRequiredParameterValue(name, value);

        if (value!=null&&!value.equals("")&&!value.equals("true")&&!value.equals("false"))
            throw new InvalidParameterException(INTEGER_INVALID_VALUE_PREFIX + value + INTEGER_INVALID_VALUE__SUFFIX_1 + name + INTEGER_INVALID_VALUE__SUFFIX_2);

        return value;
    }

    /**
     * Validates the JSONObject class name.
     *
     * @param className <code>String</code> the class name.
     * @param value <code>String</code> the request parameter value.
     * @param error <code>String</code> the error message.
     * @throws <code>InvalidParameterException</code> the exception, designed
     *         for use by the JCA/JCE engine classes, is thrown when an invalid
     *         parameter is passed to a method.
     */
    private void validateJSONObject(final String className, final String value, final String error) throws InvalidParameterException {
        try {
            final Class<?> clazz = Class.forName(className);
            clazz.newInstance();
            try {
                final JSONObject json = JSONObject.fromObject(value);
                final JsonConfig jsonConfig = new JsonConfig();
                jsonConfig.setRootClass(clazz);
                JSONSerializer.toJava(json, jsonConfig);
            } catch (Exception e) {
                throw new InvalidParameterException(error);
            }
        } catch (ClassNotFoundException e) {
            throw new InvalidParameterException(error);
        } catch (InstantiationException e1) {
            throw new InvalidParameterException(error);
        } catch (IllegalAccessException e1) {
            throw new InvalidParameterException(error);
        }
    }

    /**
     * Validates if required parameter has value.
     * @param parameterName <code>String</code> the parameter name.
     * @param value <code>String</code> the request parameter value.
     * @throws <code>MissingParameterException</code> the exception, designed
     *         for use by the JCA/JCE engine classes, is thrown when an invalid
     *         parameter is passed to a method.
     */
    private void checkRequiredParameterValue(final String parameterName, final String value) throws MissingParameterException {
        if (value == null || 0 == value.length())
            throw new MissingParameterException(parameterName);
    }

    /**
     * Returns a javaScript string that constructs a 'security' variable on the
     * page.
     * <p>
     * The security object will contain a property for each security setting
     * listed in the component definition.
     * <p>
     * The definition allows and 'id' to be specified for the propertyName, else
     * it will generate one from the component/function name with the prefix of
     * 'has' and any special characters (like .) removed.
     *
     * @return <code>String</code> a javaScript string that constructs a
     *         'security' variable on the page.
     */
    public String writeSecurityData() {
        final StringBuilder sb = new StringBuilder();
        sb.append("var security={\n");
        if (component.getRefFunctions() != null && 0 < component.getRefFunctions().size()) {
            for (final ComponentSecurity securityComponent : component.getRefFunctions()) {

                if (securityComponent.isComponentRef()) {
                    sb.append(securityComponent.getRef() + ":" + org.jaffa.security.SecurityManager.checkComponentAccess(securityComponent.getName()));
                } else {
                    sb.append(securityComponent.getRef() + ":" + org.jaffa.security.SecurityManager.checkFunctionAccess(securityComponent.getName()));
                }
                sb.append(",\n");

            }
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("\n};");
        return sb.toString();
    }

    /**
     * Returns the complete path reference for this component, it is uses as a
     * reference for storing state for this page.
     * <p>
     * For example accessing the
     * "http://localhost:8080/jaffa/module/sub/component/start.jsp?param=help"
     * would give a Path Ref of "module/sub/component/start.jsp".
     *
     * @return <code>String</code> the complete path reference for this
     *         component, it is uses as a reference for storing state for this
     *         page.
     */
    public String getPathRef() {
        return request.getRequestURI().substring(request.getContextPath().length() + 1);
    }

    /**
     * Returns the base path for this application, it is uses typically in a web
     * page for setting the base of the page.
     * <p>
     * For example accessing the
     * "http://localhost:8080/jaffa/module/sub/component/start.jsp?param=help"
     * would give a Base Ref of "http://localhost:8080/jaffa"
     *
     * @return <code>String</code> the base path for this application, it is
     *         uses typically in a web page for setting the base of the page.
     */
    public String getBaseRef() {
        return URLHelper.getBase((HttpServletRequest) request);
    }

    /**
     * This is a replacement for including the classMetaData.jsp into the page,
     * to generate the JavaScript code that will create an object in
     * ClassMetaData['objectName'] defining the class level and property level
     * metadata rules.
     *
     * @return <code>String</code> the JavaScript code that will create an
     *         object in ClassMetaData['objectName'].
     */
    public String getClassMetaData() {
        return null;
    }
}