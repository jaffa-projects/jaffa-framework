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

/*
 * CheckPolicy.java
 *
 * Created on July 25, 2002, 6:20 PM
 */
package org.jaffa.security;

import org.apache.log4j.Logger;
import org.jaffa.loader.policy.BusinessFunctionManager;
import org.jaffa.presentation.portlet.component.ComponentManager;
import org.jaffa.security.securityrolesdomain.GrantFunctionAccess;
import org.jaffa.security.securityrolesdomain.Role;
import org.jaffa.util.StringHelper;
import org.jaffa.util.URLHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This servlet can be used on start-up to make ssure there are no rogue entries
 * in the components and roles files.
 *
 * @author paule
 * @version 1.0
 */
@WebServlet(value="/admin/CheckPolicy", name="CheckPolicy", loadOnStartup=2)
public class CheckPolicy extends HttpServlet {

    /**
     * Set up Logging for Log4J
     */
    private static Logger log = Logger.getLogger(CheckPolicy.class);
    /**
     * Stores the list of component errors for display
     */
    private static HashMap m_compErrors = new HashMap();
    /**
     * Stores the list of role errors for display
     */
    private static HashMap m_roleErrors = new HashMap();

    private static BusinessFunctionManager businessFunctionManager;

    public static BusinessFunctionManager getBusinessFunctionManager() {
        return businessFunctionManager;
    }

    public static void setBusinessFunctionManager(BusinessFunctionManager businessFunctionManager) {
        CheckPolicy.businessFunctionManager = businessFunctionManager;
    }

    /**
     * Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        checkPolicy();
    }

    /**
     * Destroys the servlet.
     */
    public void destroy() {

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Validating Security Policy</title>");
        out.println("<base href='" + URLHelper.getBase(request) + "'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Validating Security Policy</h1>");
        out.println("<h2>Errors in 'components.xml'</h2>");
        if (m_compErrors == null || m_compErrors.size() == 0) {
            out.println("No Errors Detected!");
        } else {
            out.println("<ul>");
            for (Iterator i = m_compErrors.keySet().iterator(); i.hasNext(); ) {
                String comp = (String) i.next();
                out.println("<li>Component <b>" + comp + "</b> has invalid business function <b>" + m_compErrors.get(comp) + "</b> defined.");
            }
            out.println("</ul>");
        }
        out.println("<br>");
        out.println("<h2>Errors in 'roles.xml'</h2>");
        if (m_roleErrors == null || m_roleErrors.size() == 0) {
            out.println("No Errors Detected!");
        } else {
            out.println("<ul>");
            for (Iterator i = m_roleErrors.keySet().iterator(); i.hasNext(); ) {
                String role = (String) i.next();
                out.println("<li>Role <b>" + role + "</b> has invalid business function <b>" + m_roleErrors.get(role) + "</b> specified.");
            }
            out.println("</ul>");
        }
        out.println("<br>");
        out.println("<h2>Current Loaded Policy</h2>");
        out.println("<pre>");
        StringWriter sw = new StringWriter();
        PolicyManager.printPolicy(new PrintWriter(sw, true));
        out.println(StringHelper.convertToHTML(sw.toString()));
        out.println("</pre>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Check Security Policy";
    }

    /**
     * checkPolicy() - Compares the business functions from the businessFunctionManager with the contents of m_compoErrors.
     */
    private static synchronized void checkPolicy() {
        // Read the business function file
        List<String> bfuncs = readFunctions();

        // Get mandatory functions per component
        Map compList = ComponentManager.getComponentRequirements();

        // For Each component make sure that the business functions are valid
        for (Iterator it = compList.keySet().iterator(); it.hasNext(); ) {
            String comp = (String) it.next();
            String[] funcs = (String[]) compList.get(comp);
            if (funcs == null) {
                continue;
            }
            for (int i = 0; i < funcs.length; i++) {
                if (!bfuncs.contains(funcs[i])) {
                    m_compErrors.put(comp, funcs[i]);
                    log.error("Function '" + funcs[i] + "' on Component '" + comp + "' is Not Valid!");
                }
            }
        }

        // For Each role make sure that the business functions are valid
        List<Role> allRoles = PolicyManager.getAllRoles();
        for (Role role: allRoles) {
            for (GrantFunctionAccess grantFunctionAccess: role.getGrantFunctionAccess()) {
                if (!bfuncs.contains(grantFunctionAccess.getName())) {
                    m_roleErrors.put(role.getName(), grantFunctionAccess.getName());
                    log.error("Business Function '" + grantFunctionAccess.getName() + "' in Role '" + role + "' is Not Valid!");
                }
            }
        }

    }

    /**
     * Returns a list of businessFunctions from the businessFunctionManager.
     * @return a list of business functions as strings
     */
    private static List<String> readFunctions() {
        BusinessFunctionManager businessFunctionManager = getBusinessFunctionManager();
        if (null != businessFunctionManager)
            return businessFunctionManager.getAllBusinessFunctionsStrings();
        return Collections.emptyList();
    }

    /**
     * Allows the policy checker to be run from outside of a web container.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Running Policy Checker...");
        checkPolicy();
        System.out.println("Checking Components");
        if (m_compErrors == null || m_compErrors.size() == 0) {
            System.out.println("--- No Errors Detected!");
        } else {
            for (Iterator i = m_compErrors.keySet().iterator(); i.hasNext(); ) {
                String comp = (String) i.next();
                System.out.println("--- Component " + comp + " has invalid business function " + m_compErrors.get(comp) + " defined.");
            }
        }
        System.out.println("Checking Roles");
        if (m_roleErrors == null || m_roleErrors.size() == 0) {
            System.out.println("--- No Errors Detected!");
        } else {
            for (Iterator i = m_roleErrors.keySet().iterator(); i.hasNext(); ) {
                String role = (String) i.next();
                System.out.println("--- Role " + role + " has invalid business function " + m_roleErrors.get(role) + " specified.");
            }
        }
    }

    /**
     * Returns the hashmap of compErrors
     * @return the hash map of CompErrors
     */
    public static HashMap getCompErrors() {
        return m_compErrors;
    }

    /**
     * Returns the hashmap of roleErrors.
     * @return the hash map of role errors
     */
    public static HashMap getRoleErrors() {
        return m_roleErrors;
    }

}
