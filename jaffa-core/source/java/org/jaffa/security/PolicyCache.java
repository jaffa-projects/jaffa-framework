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
package org.jaffa.security;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jaffa.config.Config;
import org.jaffa.security.securityrolesdomain.Roles;
import java.util.HashMap;
import java.net.URL;
import java.util.List;
import org.jaffa.security.securityrolesdomain.Role;
import org.jaffa.security.securityrolesdomain.GrantFunctionAccess;
import java.net.MalformedURLException;
import org.apache.log4j.Logger;
import org.jaffa.util.URLHelper;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.jaffa.util.XmlHelper;
import java.io.IOException;
import java.io.InputStream;
import org.jaffa.util.JAXBHelper;
import org.xml.sax.SAXException;

/** This class is the interface between the policy domain classes
 * and the Policy manager, it provide the data to the PolicyManager
 * in a more native format then in the domain objects
 * It also caches the policy information for multiple
 * access.
 */
public class PolicyCache {

    private static Logger log = Logger.getLogger(PolicyCache.class);
    private static final String DEFAULT_POLICY_LOCATION = "classpath:///resources/roles.xml";
    private static final String SCHEMA = "org/jaffa/security/securityrolesdomain/security-roles_1_0.xsd";
    private static final ConcurrentMap<String, PolicyCache> c_policyCacheByVariation = new ConcurrentHashMap<String, PolicyCache>();
    private static final String DEFAULT_KEY = ""; //this key will used to put the PolicyCache instance for the default roles.xml file
    private Roles m_roles = null;
    private Map<String, List<String>> m_roleMap = null; // Mapping of roleName and functionLists

    /** Returns the Roles domain objects. This are the objects created from the XML file. This is a cached copy and should not be modified.
     * Changing this will not effect the contents of the loaded policy, as the
     * policy will load on application startup.
     * @return The root Roles domain object for the current policy
     */
    public static Roles getRoles() {
        return getInstance().m_roles;
    }

    /** Get the list of roles and what functions are in each role.
     * @return The returned Map is keyed on Role name (String), and each entry is a List of business function names.
     */
    static Map<String, List<String>> getRoleMap() {
        return getInstance().m_roleMap;
    }

    /** Clear the cached policy. Will be reloaded on the next access.
     */
    static void clearCache() {
        c_policyCacheByVariation.clear();
    }

    /** Creates an instance of PolicyCache, if not already instantiated.
     * @return An instance of the PolicyCache.
     */
    private static PolicyCache getInstance() {
        String variation = VariationContext.getVariation();
        PolicyCache policyCache = c_policyCacheByVariation.get(variation);
        if (policyCache == null) {
            PolicyCache generatedPolicyCache = createPolicyCacheInstance();
            policyCache = c_policyCacheByVariation.putIfAbsent(variation, generatedPolicyCache);
            if (policyCache == null)
                policyCache = generatedPolicyCache;
        }
        return policyCache;
    }

    private static PolicyCache createPolicyCacheInstance() {
        String fileLocation = getFileLocation();
        return fileLocation.equals(getDefaultFileLocation()) ? getDefaultInstance() : new PolicyCache(fileLocation);
    }

    private static PolicyCache getDefaultInstance() {
        String variation = DEFAULT_KEY;
        PolicyCache policyCache = c_policyCacheByVariation.get(variation);
        if (policyCache == null) {
            PolicyCache generatedPolicyCache = new PolicyCache(getDefaultFileLocation());
            policyCache = c_policyCacheByVariation.putIfAbsent(variation, generatedPolicyCache);
            if (policyCache == null)
                policyCache = generatedPolicyCache;
        }
        return policyCache;
    }

    /** Read the roles in from the XMLdocument and cache them. */
    private PolicyCache(String initFile) {
        if (log.isDebugEnabled())
            log.debug("Creating an instance of PolicyCache using '" + initFile + '\'');
        InputStream stream = null;
        try {
            // Create a URL for the resource file...
            URL roleUrl = URLHelper.newExtendedURL(initFile);

            stream = roleUrl.openStream();

            // create a JAXBContext capable of handling classes generated into the package
            JAXBContext jc = JAXBContext.newInstance("org.jaffa.security.securityrolesdomain");

            // create an Unmarshaller
            Unmarshaller u = jc.createUnmarshaller();

            // enable validation
            u.setSchema(JAXBHelper.createSchema(SCHEMA));

            // unmarshal a document into a tree of Java content objects composed of classes from the package.
            m_roles = (Roles) u.unmarshal(XmlHelper.stripDoctypeDeclaration(stream));
            if (log.isInfoEnabled()) {
                log.info("Loaded Policy File From : " + roleUrl.toExternalForm());
                log.info("Policy Contains " + m_roles.getRole().size() + " Role Entries");
            }

            // build list of functions per role
            m_roleMap = buildRoleMap(m_roles);
        } catch (MalformedURLException e) {
            String s = "Can't Find Security Policy File, Bad URL - " + initFile;
            log.fatal(s, e);
            throw new SecurityException(s, e);
        } catch (JAXBException e) {
            String s = "Can't Load Security Policy File, Malformed XML Document - " + initFile;
            log.fatal(s, e);
            throw new SecurityException(s, e);
        } catch (SAXException e) {
            String s = "Can't Load Security Policy File, Malformed XML Document - " + initFile;
            log.fatal(s, e);
            throw new SecurityException(s, e);
        } catch (IOException e) {
            String s = "Can't Load Security Policy File, I/O Error in reading XML Document - " + initFile;
            log.fatal(s, e);
            throw new SecurityException(s, e);
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                // do nothing
                log.warn("Unable to close the InputStream associated to the URL - " + initFile, e);
            }
        }
    }

    /** Get the list of roles and what functions are in each role. */
    private static Map<String, List<String>> buildRoleMap(Roles roles) {
        Map<String, List<String>> m = new HashMap<String, List<String>>();
        List<Role> roleList = roles.getRole();
        if (roleList != null) {
            // Loop of all the role objects
            for (Role role : roleList) {
                if (log.isDebugEnabled())
                    log.debug("Processing Role: " + role.getName());
                List<GrantFunctionAccess> access = role.getGrantFunctionAccess();
                List<String> funcs = null;
                if (access != null) {
                    funcs = new LinkedList<String>();
                    // Add all the names in all of the GrantAccess objects to the list.
                    for (GrantFunctionAccess gfa : access) {
                        funcs.add(gfa.getName());
                        if (log.isDebugEnabled())
                            log.debug("Processing Role: " + role.getName() + " has function " + gfa.getName());
                    }
                }
                // If there are some functions, add it to the master Map
                if (funcs != null)
                    m.put(role.getName(), funcs);
            }
        }
        return m;
    }

    /** Returns the location of the default roles.xml file. */
    public static String getDefaultFileLocation() {
        return (String) Config.getProperty(Config.PROP_SECURITY_POLICY_URL, DEFAULT_POLICY_LOCATION);
    }

    /**
     * Returns the location of roles_{VAR}.xml file.
     * NOTE: This file may not exist in the file-system.
     */
    public static String getVariationFileLocation() {
        String defaultFileLocation = getDefaultFileLocation();

        //Incorporate the variation into the filename
        String variation = VariationContext.getVariation();
        String variationFileLocation = null;
        final String suffix = ".xml";
        if (defaultFileLocation.endsWith(suffix) && defaultFileLocation.length() > suffix.length())
            variationFileLocation = defaultFileLocation.substring(0, defaultFileLocation.length() - suffix.length()) + '_' + variation + suffix;
        else
            variationFileLocation = defaultFileLocation + '_' + variation;
        return variationFileLocation;
    }

    /**
     * Returns the variation file-location, if it exists in the file-system.
     * Else the default file-location will be returned.
     */
    public static String getFileLocation() {
        String variationFileLocation = getVariationFileLocation();
        try {
            URLHelper.newExtendedURL(variationFileLocation);
            if (log.isDebugEnabled())
                log.debug("FileLocation is '" + variationFileLocation + '\'');
            return variationFileLocation;
        } catch (MalformedURLException e) {
            String defaultFileLocation = getDefaultFileLocation();
            if (log.isDebugEnabled())
                log.debug("FileLocation is the default '" + defaultFileLocation + "', since variation file '" + variationFileLocation + "' does not exist");
            return defaultFileLocation;
        }
    }
}
