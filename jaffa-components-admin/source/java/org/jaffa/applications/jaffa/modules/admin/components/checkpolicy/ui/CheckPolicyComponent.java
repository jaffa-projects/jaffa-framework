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
package org.jaffa.applications.jaffa.modules.admin.components.checkpolicy.ui;

import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.util.URLHelper;
import org.jaffa.util.StringHelper;
import org.jaffa.applications.jaffa.modules.admin.components.checkpolicy.ui.exceptions.CheckPolicyException;
import org.jaffa.security.businessfunctionsdomain.*;
import org.jaffa.security.PolicyCache;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.jaffa.util.XmlHelper;
import org.jaffa.presentation.portlet.component.ComponentManager;
import org.jaffa.security.securityrolesdomain.Role;
import org.jaffa.security.securityrolesdomain.GrantFunctionAccess;
import org.jaffa.security.securityrolesdomain.Roles;
import org.jaffa.util.JAXBHelper;

/** This is the component controller for the Business Function.
 *
 * @author  Maheshd
 * @version 1.0
 */
public class CheckPolicyComponent extends Component {

    private static Logger log = Logger.getLogger(CheckPolicyComponent.class);
    private static final String SCHEMA = "org/jaffa/security/businessfunctionsdomain/business-functions_1_0.xsd";
    /** Holds value of property fileContentsFormKey. */
    private FormKey m_checkPolicyReportFormKey;
    /** Stores the list of component errors for display */
    private static HashMap m_compErrors = new HashMap();
    /** Stores the list of role errors for display */
    private static HashMap m_roleErrors = new HashMap();
    /** Stores the list of role errors for display */
    private static HashMap m_roleBFMap = new HashMap();

    /** Getter for property roleErrorMap.
     * @return Value of property HashMap.
     *
     */
    public HashMap getRoleErrorMap() {
        return m_roleErrors;
    }

    /** Getter for property componentErrorMap.
     * @return Value of property HashMap.
     *
     */
    public HashMap getComponentErrorMap() {
        return m_compErrors;
    }

    /** Getter for property roleBFMap.
     * @return Value of property HashMap.
     *
     */
    public HashMap getRoleBFMap() {
        return m_roleBFMap;
    }

    /** Getter for property CheckPolicyReportFormKey.
     * @return Value of property CheckPolicyReportFormKey.
     *
     */
    public FormKey getCheckPolicyReportFormKey() {
        if (m_checkPolicyReportFormKey == null) {
            m_checkPolicyReportFormKey = new FormKey(CheckPolicyForm.NAME, getComponentId());
        }
        return m_checkPolicyReportFormKey;
    }

    /** This will retrieve the file contents of the business-function xml file  and return the FormKey for rendering the component.
     * @return the FormKey for rendering the component.
     * @throws FrameworkException if any error ocurrs in obtaining the contents of the business-function xml file.
     */
    public FormKey display() throws FrameworkException, ApplicationExceptions {

        checkPolicy();

        return getCheckPolicyReportFormKey();
    }

    /** This checks the roles.xml for any errors including the validity of the bussiness-functions
     * @throws ApplicationExceptions if any error occurs.
     */
    protected void checkPolicy() throws ApplicationExceptions {
        // Clear the cache before reading the functions
        // Read the business function file
        List bfuncs = readFunctions();
        List errorFuncs = new ArrayList();
        m_roleErrors.clear();
        m_compErrors.clear();

        // Get mandatory functions per component
        Map compList = ComponentManager.getComponentRequirements();

        // For Each component make sure that the business functions are valid
        for (Iterator it = compList.keySet().iterator(); it.hasNext();) {
            errorFuncs = new ArrayList();
            String comp = (String) it.next();
            String[] funcs = (String[]) compList.get(comp);
            if (funcs != null) {
                for (int i = 0; i < funcs.length; i++) {
                    if (!bfuncs.contains(funcs[i])) {
                        errorFuncs.add(funcs[i]);
                    }
                }
            }
            if (!errorFuncs.isEmpty()) {
                m_compErrors.put(comp, errorFuncs);
            }
        }

        // Get list of functions per role
        Map roleMap = getRoleMap();

        // For Each role make sure that the business functions are valid
        for (Iterator it2 = roleMap.keySet().iterator(); it2.hasNext();) {
            errorFuncs = new ArrayList();
            String role = (String) it2.next();
            List roleList = (List) roleMap.get(role);
            for (Iterator it3 = roleList.iterator(); it3.hasNext();) {
                String func = (String) it3.next();
                if (!bfuncs.contains(func)) {
                    errorFuncs.add(func);
                    log.error("Business Function '" + func + "' in Role '" + role + "' is Not Valid!");
                }
            }
            if (!errorFuncs.isEmpty()) {
                m_roleErrors.put(role, errorFuncs);
            }

        }
    }

    /** This reads the bussiness-functions from the bussiness-functions.xml file
     * @throws ApplicationExceptions if any error occurs.
     */
    private List readFunctions() throws ApplicationExceptions {
        ApplicationExceptions appExps = new ApplicationExceptions();
        ArrayList bflist = new ArrayList();
        InputStream stream = null;
        try {
            stream = URLHelper.newExtendedURL("resources/business-functions.xml").openStream();

            // create a JAXBContext capable of handling classes generated into the package
            JAXBContext jc = JAXBContext.newInstance("org.jaffa.security.businessfunctionsdomain");

            // create an Unmarshaller
            Unmarshaller u = jc.createUnmarshaller();

            // enable validation
            u.setSchema(JAXBHelper.createSchema(SCHEMA));

            // unmarshal a document into a tree of Java content objects composed of classes from the package.
            BusinessFunctions businessFunctions = (BusinessFunctions) u.unmarshal(XmlHelper.stripDoctypeDeclaration(stream));
            for (Iterator i = businessFunctions.getBusinessFunction().iterator(); i.hasNext();) {
                bflist.add(((BusinessFunction) i.next()).getName());
            }

        } catch (Exception e) {
            appExps.add(new CheckPolicyException(CheckPolicyException.PROP_FILEREAD_ERROR, StringHelper.convertToHTML(e.getMessage())));
            throw appExps;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
            // do nothing
            }
        }
        return bflist;
    }

    /** This creates a HashMap of role and list bussiness-functions for that role
     */
    static HashMap getRoleMap() {

        // Get the roles, throws exceptions if there are issues
        Roles roles = PolicyCache.getRoles();
        m_roleBFMap.clear();
        List roleList = roles.getRole();
        // Bail if there are no roles....
        if (roleList == null) {
            return m_roleBFMap;
        }

        // Loop of all the role objects
        for (Iterator it = roleList.iterator(); it.hasNext();) {
            Role role = (Role) it.next();
            if (log.isDebugEnabled()) {
                log.debug("Processing Role: " + role.getName());
            }
            List access = role.getGrantFunctionAccess();
            List funcs = null;
            if (access != null) {
                funcs = new ArrayList();
                // Add all the names in all of the GrantAccess objects to the list.
                for (Iterator it2 = access.iterator(); it2.hasNext();) {
                    GrantFunctionAccess gfa = (GrantFunctionAccess) it2.next();
                    funcs.add(gfa.getName());
                    if (log.isDebugEnabled()) {
                        log.debug("Processing Role: " + role.getName() + " has function " + gfa.getName());
                    }
                }
            }
            // If there are some functions, add it to the master hashmap
            if (funcs != null) {
                m_roleBFMap.put(role.getName(), funcs);
            }
        }

        // Return the construsted Map
        return m_roleBFMap;

    }
}
