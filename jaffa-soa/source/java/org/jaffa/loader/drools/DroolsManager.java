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
package org.jaffa.loader.drools;

import org.apache.log4j.Logger;
import org.drools.RuleBaseConfiguration;
import org.drools.agent.AgentEventListener;
import org.drools.agent.RuleAgent;
import org.jaffa.security.VariationContext;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.soa.rules.RuleAgentKey;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Manager for Drools Loading and encapsulates all the methods required for Drools Management
 * Any spring config can use this class to register/unregister drool files.
 */
public class DroolsManager {

    private static final Logger log = Logger.getLogger(DroolsManager.class);
    private static final String DROOLS_COMPILER_PROPERTY = "drools.dialect.java.compiler";
    private static final String DROOLS_JANINO_COMPILER = "JANINO";
    public static final String DROOLS_FILE_DIRECTORY = System.getProperty("java.io.tmpdir") + File.separator +"rules" + File.separator;

    /**
     * Map holding service name with list of RuleAgentKeys, used in refreshAgent method.
     */
    private Map<String, Set<RuleAgentKey>> serviceNameMap = new HashMap<>();

    /**
     * Map containing RuleAgentKey and StringBuilder, used by registerDrool method.
     */
    private Map<RuleAgentKey, StringBuilder> droolsFiles = new HashMap<>();

    /**
     * Map containing RuleAgentKey and RuleAgent, used by createAgents.
     */
    private Map<RuleAgentKey, RuleAgent> ruleAgents = new HashMap<>();

    /**
     * register Drool File using the Resource parameter
     *
     * @param resource  the object that contains the drool file.
     * @param variation key with which drool file to be registered.
     * @throws IOException if drool file is not found.
     */
    public synchronized void registerDrool(Resource resource, String variation) throws IOException {

        String serviceName = getServiceName(resource.getURL().getPath());
        String moduleName = getModuleName(resource.getURL().getPath());

        Path newPath = Paths.get(createDroolDirectoryPath(moduleName, serviceName,variation) + File.separator + resource.getFilename());

        try {
            Files.delete(newPath);
        } catch (Exception e) {
            log.debug("File does not exist");
        }
        Files.copy(resource.getInputStream(), newPath);

        registerDrool(serviceName, newPath.toString(), variation);
    }

    /**
     * register Drool File using the serviceName
     *
     * @param serviceName with which Drool File will be registered
     * @param path        the full path of the drool file
     * @param variation   key with which drool file to be registered.
     * @throws IOException if drool file is not found.
     */
    public synchronized void registerDrool(String serviceName, String path, String variation) throws IOException {

        RuleAgentKey ruleAgentKey = new RuleAgentKey(serviceName, variation);

        StringBuilder droolPath = droolsFiles.get(ruleAgentKey);
        if (droolPath == null) {
            droolPath = new StringBuilder();
        }
        droolPath.append(" " + path);

        droolsFiles.put(ruleAgentKey, droolPath);
        Set<RuleAgentKey> ruleAgentKeys = serviceNameMap.get(serviceName);
        if (ruleAgentKeys == null) {
            ruleAgentKeys = new HashSet<>();
        }

        ruleAgentKeys.add(ruleAgentKey);
        serviceNameMap.put(serviceName, ruleAgentKeys);
    }


    /**
     * unregisters a particular drool file resource from the Drool Repository
     *
     * @param resource  containing the location of drool file
     * @param variation key with which drool file to be registered.
     * @throws IOException if drool file is not found.
     */
    public synchronized void unRegisterDrool(Resource resource, String variation) throws IOException {

        String serviceName = getServiceName(resource.getURL().getPath());
        String moduleName = getModuleName(resource.getURL().getPath());

        RuleAgentKey ruleAgentKey = new RuleAgentKey(serviceName, variation);
        Path newPath = Paths.get(createDroolDirectoryPath(moduleName, serviceName,variation) + File.separator + resource.getFilename());

        StringBuilder droolPath = droolsFiles.get(ruleAgentKey);
        if (droolPath != null) {
            int index = droolPath.indexOf(newPath.toString());
            droolPath = droolPath.replace(index, newPath.toString().length() + 1, "");
        }

        droolsFiles.put(ruleAgentKey, droolPath);

        refreshAgent(serviceName);
    }

    /**
     * Returns a RuleAgent from the Drool repository
     *
     * @param serviceName drool file service name
     * @param variation   String representing the customer variation
     * @return RuleAgent
     */
    public synchronized RuleAgent getAgent(String serviceName, String variation) {
        RuleAgentKey ruleAgentKey = new RuleAgentKey(serviceName, variation);
        if (ruleAgents.get(ruleAgentKey) == null)
            createAgent(ruleAgentKey);

        return ruleAgents.get(ruleAgentKey);
    }

    /**
     * Static method that can be used to tell any cached agent that they should
     * refresh there rules base. This can be used by any code that is specifically changing
     * rule files that the agent may have cached. If not used the Agent will automatically
     * refresh the rules based on its polling period and out-of-date file stamps
     */
    public synchronized void refreshAgent(String serviceName) {
        if (VariationContext.getVariation().equals(VariationContext.NULL_VARIATION)) {
            if (serviceNameMap.get(serviceName) != null) {
                for (RuleAgentKey ruleAgentKey1 : serviceNameMap.get(serviceName)) {
                    synchronized (ruleAgents) {
                        ruleAgents.remove(ruleAgentKey1);
                    }
                }
            }
        } else {
            RuleAgentKey key = new RuleAgentKey(serviceName, VariationContext.getVariation());
            synchronized (ruleAgents) {
                ruleAgents.remove(key);
            }
        }
    }

    /**
     * This method is called from DroolsLoader when all the drools files are loaded in Repository
     * This method creates all the RuleAgents and adds it to the ruleAgents Map
     */
    public void createAgents() {
        for (Map.Entry<RuleAgentKey, StringBuilder> entry : droolsFiles.entrySet()) {
            RuleAgentKey ruleAgentKey = entry.getKey();
            if (ruleAgentKey.getVariant() != null) {
                createAgent(ruleAgentKey);
            }
        }
    }

    /**
     *This method is called from DroolsLoader before all the drools files are loaded in Repository
     * This method clears all the existing drools directory
     */
    public void clearDroolsDirectory(){
        try {
            Path pathToBeDeleted = Paths.get(DROOLS_FILE_DIRECTORY);

            Files.walk(pathToBeDeleted)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (Exception e) {
            log.debug("File does not exist");
        }
    }

    /**
     * creates an RuleAgent based on the ruleAgentKey and adds it to the ruleAgents Map
     *
     * @param ruleAgentKey
     */
    private void createAgent(RuleAgentKey ruleAgentKey) {
        Properties prop = new Properties();

        prop.setProperty(RuleAgent.FILES, getDroolsFilesRepo(ruleAgentKey));

        prop.setProperty(RuleAgent.CONFIG_NAME, ruleAgentKey.getServiceName());

        // See if there is a DIR override, and apply it
        String ruleName = "jaffa.soa.droolsAgentConfig." + ruleAgentKey.getServiceName() + ".dir";
        String dir = (String) ContextManagerFactory.instance().getProperty(ruleName);
        if (dir != null)
            prop.setProperty(RuleAgent.DIRECTORY, dir);

        // Make sure this is a vaild directory and create if it does not exist, else the Agent will fail!
        if (prop.containsKey(RuleAgent.DIRECTORY)) {
            File d = new File(prop.getProperty(RuleAgent.DIRECTORY));
            if (d.exists()) {
                if (!d.isDirectory())
                    log.error("Rule Directory " + dir + " is not a directory, can't add it to agent");
            } else {
                log.warn("Rule Directory " + dir + " does not exist, creating it now.");
                d.mkdirs();
            }
        }

        // Set the Polling Interval if not set for DIR or URL
        if (!prop.containsKey(RuleAgent.POLL_INTERVAL) && (prop.containsKey(RuleAgent.DIRECTORY) || prop.containsKey(RuleAgent.URLS)))
            prop.setProperty(RuleAgent.POLL_INTERVAL, "60");


        // We can now create the Agent, based on the properties
        try {
            // To avoid conflict with Tomcat's JDT compiler, override the default compiler with JANINO (see http://wiki.jboss.org/wiki/RulesTomcat)
            String droolsCompiler = System.getProperty(DROOLS_COMPILER_PROPERTY);
            if (droolsCompiler == null || droolsCompiler.length() == 0) {
                System.setProperty(DROOLS_COMPILER_PROPERTY, DROOLS_JANINO_COMPILER);
                if (log.isDebugEnabled())
                    log.debug("To avoid conflicts with an existing JDT compiler, drools has been configured to use " + System.getProperty(DROOLS_COMPILER_PROPERTY));
            }

            // Turn off Shadow Rules Proxy Objects, and enable object Equality checks for uniqueness
            RuleBaseConfiguration conf = new RuleBaseConfiguration();
            conf.setShadowProxy(false);
            conf.setAssertBehaviour(RuleBaseConfiguration.AssertBehaviour.EQUALITY);

            // Now load the Agent, based on the properties
            if (log.isDebugEnabled())
                log.debug("Create Agent Based on properties - " + prop);
            RuleAgent agent = RuleAgent.newRuleAgent(prop, getAgentEventListener(), conf);
            if (agent == null) {
                log.error("Didn't create agent from property file: " + ruleAgentKey.getServiceName());
                throw new RuntimeException("Didn't create agent from property file: " + ruleAgentKey.getServiceName());
            }
            ruleAgents.put(ruleAgentKey, agent);
        } catch (Exception e) {
            log.error("Can't create agent from property file: " + ruleAgentKey.getServiceName(), e);
            throw new RuntimeException("Can't create agent from property file: " + ruleAgentKey.getServiceName(), e);
        }
    }

    /**
     * gets the combined drools file path by appending default and variant drool path
     *
     * @param ruleAgentKey
     * @return String containing various drool file paths for that service name
     */
    private String getDroolsFilesRepo(RuleAgentKey ruleAgentKey) {

        StringBuilder droolsVariantPath = droolsFiles.get(ruleAgentKey);
        if (droolsVariantPath == null) {
            droolsVariantPath = new StringBuilder();
        }
        if (!ruleAgentKey.getVariant().equals(VariationContext.NULL_VARIATION)) {
            StringBuilder droolsDefaultPath = droolsFiles.get(new RuleAgentKey(ruleAgentKey.getServiceName(), VariationContext.NULL_VARIATION));
            if (droolsDefaultPath != null)
                droolsVariantPath.append(droolsDefaultPath.toString());
        }

        return droolsVariantPath.toString();
    }

    /**
     * gets the ServiceName from the context Path
     *
     * @param path
     * @return service name
     */
    public String getServiceName(String path) {
        if (path != null && !"".equals(path)) {
            String temp = new File(path).getParent();
            return temp.substring(temp.lastIndexOf(File.separator) + 1);
        }

        return null;
    }

    /**
     * gets the Module name from the context Path
     *
     * @param path
     * @return module name
     */
    public String getModuleName(String path) {
        try {
            if (path != null && !"".equals(path)) {
                String temp = new File(path).getParent();
                temp = temp.substring(0, temp.indexOf("!" + File.separator + "META-INF" + File.separator + "rules"));
                return temp.substring(temp.lastIndexOf(File.separator) + 1, temp.length() - 4);
            }
        }catch(Exception e){
            log.error("Error in getting module name from path " + path);
        }

        return "default";
    }

    /**
     * creates the drool directory path where drool files to be placed
     * @param serviceName
     * @param variation
     * @return drool Directory path
     * @throws IOException
     */
    private String createDroolDirectoryPath(String moduleName, String serviceName, String variation) throws IOException {
        if(variation.equals(VariationContext.NULL_VARIATION)) {
            return Files.createDirectories(Paths.get(DROOLS_FILE_DIRECTORY + moduleName + File.separator + serviceName)).toString();
        }else{
            return Files.createDirectories(Paths.get(DROOLS_FILE_DIRECTORY + moduleName + File.separator + serviceName + File.separator + variation)).toString();
        }
    }

    /**
     * Returns a custom AgentEventListener implementation.
     * The default AgentEventListener implementation in Drools writes the various Events to the System.err stream.
     * This custom implementation instead logs the various Events via Log4J.
     *
     * @return a custom AgentEventListener implementation.
     */
    private AgentEventListener getAgentEventListener() {
        return new AgentEventListener() {

            private String prefix;

            public void setAgentName(String name) {
                prefix = "RuleAgent(" + name + "): ";
            }

            public void debug(String message) {
                if (log.isDebugEnabled())
                    log.debug(prefix + message);
            }

            public void info(String message) {
                if (log.isInfoEnabled())
                    log.info(prefix + message);
            }

            public void warning(String message) {
                log.warn(prefix + message);
            }

            public void exception(Exception e) {
                log.error(prefix + e.getMessage(), e);
            }
        };
    }

    /**
     * getter for droolFiles
     *
     * @return
     */
    public Map<RuleAgentKey, StringBuilder> getDroolsFiles() {
        return droolsFiles;
    }

    /**
     * setter for droolFiles
     *
     * @param droolsFiles
     */
    public void setDroolsFiles(Map<RuleAgentKey, StringBuilder> droolsFiles) {
        this.droolsFiles = droolsFiles;
    }

    /**
     * getter method for ruleAgents
     *
     * @return
     */
    public Map<RuleAgentKey, RuleAgent> getRuleAgents() {
        return ruleAgents;
    }

    /**
     * setter method for ruleAgents
     *
     * @param ruleAgents
     */
    public void setRuleAgents(Map<RuleAgentKey, RuleAgent> ruleAgents) {
        this.ruleAgents = ruleAgents;
    }
}
