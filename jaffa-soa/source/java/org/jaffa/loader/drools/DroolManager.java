package org.jaffa.loader.drools;

import org.apache.log4j.Logger;
import org.drools.RuleBaseConfiguration;
import org.drools.agent.AgentEventListener;
import org.drools.agent.RuleAgent;
import org.jaffa.loader.IManager;
import org.jaffa.security.VariationContext;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.soa.rules.RuleAgentKey;
import org.jaffa.soa.rules.ServiceRulesInterceptor;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by pbagirthi on 8/7/2017.
 */
public class DroolManager {

    private static final Logger log = Logger.getLogger(DroolManager.class);
    private static final String DROOLS_COMPILER_PROPERTY = "drools.dialect.java.compiler";
    private static final String DROOLS_JANINO_COMPILER = "JANINO";

    Map<RuleAgentKey, StringBuilder> droolsFiles = new HashMap<>();

    Map<RuleAgentKey, RuleAgent> ruleAgents = new HashMap<>();


    public void registerDrool(Resource resource, String variation) throws JAXBException, SAXException, IOException {

        String serviceName = getServiceName(resource.getURL().getPath());
        RuleAgentKey ruleAgentKey = new RuleAgentKey(serviceName, variation);
        Path newPath = Paths.get(Files.createDirectories(Paths.get(serviceName+ "/" + variation )).toString() + "/" + resource.getFilename()) ;
        try{
            Files.delete(newPath);
        }catch(Exception e){
            log.debug("File does not exist");
        }
        Files.copy(resource.getInputStream(), newPath );

        StringBuilder droolPath = droolsFiles.get(ruleAgentKey);
        if (droolPath == null) {
            droolPath = new StringBuilder();
        }
        droolPath.append(" " + newPath.toString());

        droolsFiles.put(ruleAgentKey, droolPath);
    }

    public RuleAgent getAgent(String serviceName, String variation) {
        return ruleAgents.get(new RuleAgentKey(serviceName, variation));
    }

    public void createAgents() {
        for (Map.Entry<RuleAgentKey, StringBuilder> entry : droolsFiles.entrySet()) {
            RuleAgentKey ruleAgentKey = entry.getKey();
            if(ruleAgentKey.getVariant() != null){
                Properties prop = new Properties();

                prop.setProperty(RuleAgent.FILES, getDroolsFilesRepo(ruleAgentKey, droolsFiles));

                prop.setProperty(RuleAgent.CONFIG_NAME, ruleAgentKey.getServiceName());

                // See if there is a DIR override, and apply it
                String ruleName = "jaffa.soa.droolsAgentConfig." + entry.getKey() + ".dir";
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
                        if (!d.exists()) {
                            log.warn("Rule Directory " + dir + " does not exist, creating it now.");
                            d.mkdirs();
                        }
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
        }
    }

    private String getDroolsFilesRepo(RuleAgentKey ruleAgentKey, Map<RuleAgentKey, StringBuilder> droolsFiles) {
        StringBuilder droolsVariantPath = droolsFiles.get(ruleAgentKey);
        if(droolsVariantPath == null){
            droolsVariantPath = new StringBuilder();
        }
        StringBuilder droolsDefaultPath = droolsFiles.get(new RuleAgentKey(ruleAgentKey.getServiceName(), null));
        if(droolsDefaultPath != null)
            droolsVariantPath.append(droolsDefaultPath.toString());

        return droolsVariantPath.toString();
    }

    /**
     * gets the ServiceName from the context Path
     *
     * @param path
     * @return service name
     */
    public String getServiceName(String path) {
        String temp = path.substring(0, path.lastIndexOf("/"));
        return temp.substring(temp.lastIndexOf("/") + 1);
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

}
