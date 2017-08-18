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
package org.jaffa.soa.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.drools.FactHandle;
import org.drools.ObjectFilter;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.agent.RuleAgent;
import org.drools.event.DebugAgendaEventListener;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.drools.spi.ConsequenceException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.loader.drools.DroolsManager;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.logging.IPersistenceLoggingPlugin;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.security.VariationContext;
import org.jaffa.soa.events.ProcessEventGraph;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.ExceptionHelper;

/**
 * This ServiceRulesInterceptor can be used to inject
 * JBoss Rules engine behavior into a SOA service via an Update Handler. It is based
 * on the IPersistenceLoggingPlugin interface so that it can monitor tranaction changes
 * and then inject those changes into the WorkingMemory of the rules engine.
 * <p>
 * The basic life cycle of this class is to
 * <ol> <li>Extend this class and implement the getRuleBase() method which will give you
 * the rule base that this service will use.
 *      <li>In the constructor of your sevice update handler, register your custom ServiceRulesInterceptor
 * <code>
 *    private WorkOrderRulesInterceptor m_uowLsnr = null;
 *    public WorkOrderUpdateHandler(UOW uow) throws ApplicationExceptions, FrameworkException {
 *        if(uow!=null) {
 *            m_uowLsnr = new WorkOrderRulesInterceptor();
 *            uow.addPersistenceLoggingPlugin(m_uowLsnr);
 *        }
 *    }
 * </code>
 *      <li>Use the addFact() method to add in any other objects your rules engine may want
 *      <li>This class will add in every domain object that has been added or updated,
 * and for each modified field on those object it will also add a FieldChanged fact.
 *      <li>This class will fire the rule engine prior to a commit, and store any additional
 *  changed made withing the scope of this transaction before it does the database commit
 * </ol>
 * 
 * @author paule
 * @version 1.0
 * @TODO, we should have a static Map of all created Agents based on service name
 * we should not need a new Agent for the same service if the rules base is already loaded
 * We could also each time we create one of these classes and the agent exists, tell the
 * Agent to refreshRuleBase(), so we don't need to worry about how often it polls.
 */
public class ServiceRulesInterceptor implements IPersistenceLoggingPlugin {

    private static final Logger log = Logger.getLogger(ServiceRulesInterceptor.class);
    private static final String FACT_NOT_ADDED_MESSAGE = "Session Not Initialized. FACT [%s] NOT ADDED...";

    private static final ObjectFilter PROCESS_EVENT_GRAPH_OBJECT_FILTER = new ObjectFilter() {
        public boolean accept(Object object) {
            return object instanceof ProcessEventGraph;
        }
    };

    private static DroolsManager droolsManager;

    /**
     * This is the rules session that may be used by super-classes
     */
    protected ThreadLocal<StatefulSession> m_session = null;
    private final RuleBase ruleBase;

    /**
     * This is the UnitOfWork associated to this listener
     */
    protected UOW m_uow = null;
    /**
     * This is the list of pending events that have been raised during the rules engine execution
     */
    protected ArrayList<PendingEventException> m_pendingEvents = new ArrayList<PendingEventException>();
    private String m_serviceName = null;

    /**
     * Create this with a service name, the name determines what Agent configuration
     * will be used to load the rules.
     * <p>
     * It will expect the name of the agent properties file to be in the BusinessRule named
     * <b><CODE>jaffa.soa.droolsAgentConfigFile.{name}=xxxx</CODE></b>
     * 
     * @param name The name of this service, used in the {@link getRuleBase()} method for configuring what RuleBase will be loaded
     */
    public ServiceRulesInterceptor(String name) {
        m_serviceName = name;
        ruleBase = getRuleBase();

        if (ruleBase != null) {
            if (log.isDebugEnabled()) log.debug("Create Rules Session");
            m_session = new ThreadLocal<StatefulSession>() {
                @Override
                public StatefulSession initialValue() {
                    return ruleBase.newStatefulSession();
                }
            };
        }
    }

    /**
     * Old initialize method, use non-deprecated one
     * @deprecated Must use the <code>&lt;cinit&gt;(UOW)</code> constructor
     * @throws org.jaffa.exceptions.ApplicationExceptions Thrown if there is an application error
     * @throws org.jaffa.exceptions.FrameworkException Throw if there is a framework error
     */
    public void initialize() throws ApplicationExceptions, FrameworkException {
        throw new UnsupportedOperationException("Must use constructor that passes in the UOW");
    }

    /** Initialization will load the rules and create the rules session
     * @param uow This is the UOW that the listener is attached to
     * @throws ApplicationExceptions When there is an Application Error
     * @throws FrameworkException When there is a Infrastructure or System Error
     */
    public synchronized void initialize(UOW uow) throws ApplicationExceptions, FrameworkException {
        m_uow = uow;
        if (hasRulesSession()) {
            // need to get a handle on the session at this point
            // ContextManagerFactory.instance().setProperty("rulesSession",m_session);
            // setup the debug listeners
            if (log.isDebugEnabled()) {
                m_session.get().addEventListener(new DebugAgendaEventListener());
                m_session.get().addEventListener(new DebugWorkingMemoryEventListener());
                // setup the audit logging
                //WorkingMemoryConsoleLogger logger = new WorkingMemoryConsoleLogger(m_session.get());
            }

            // Add array that can be used to pass back pending events
            //@TODO - check before this is added as it could throw errors
            try {
                m_session.get().setGlobal("pendingEvents", m_pendingEvents);
            } catch (RuntimeException e) {
                if (e.getMessage().startsWith("Unexpected global [")) {
                    if (log.isDebugEnabled())
                        log.debug("Unable to add Global Variable 'pendingEvents'. The variable may not be defined in the rule file");
                } else {
                    destroyRulesSession();
                    throw e;
                }
            }

            // Add the UOW as a global. This allows a rule to perform database i/o even when working with non-domain objects
            //@TODO - check before this is added as it could throw errors
            try {
                m_session.get().setGlobal("uow", m_uow);
            } 
            catch (RuntimeException e) {
                if (e.getMessage().startsWith("Unexpected global [")) {
                    if (log.isDebugEnabled())
                        log.debug("Unable to add Global Variable 'uow'. The variable may not be defined in the rule file");
                } else {
                    destroyRulesSession();
                    throw e;
                }
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("RuleBase is null. No session created");
        }
    }

    /**
     * Returns Rules session created for this Interceptor
     * @return Returns the rules session
     */
    public StatefulSession getSession(){
        return hasRulesSession() ? m_session.get() : null;
    }

    /**
     * Allow an additional fact to be added to the rule engine, in addition to the ones
     * all ready inserted by this listener
     * @param fact The object to add to working memory as a fact for the rules engine
     */
    public synchronized void addFact(Object fact) {
        if (hasRulesSession())
            m_session.get().insert(fact);
    }

    /**
     * Insert this added object as a fact, each field change will be added as a fact,
     * and a special "object added" fact will be added as well
     *
     * @param domainObject Object being added by the persistence engine
     * @throws ApplicationExceptions When there is an Application Error
     * @throws FrameworkException When there is a Infrastructure or System Error
     */
    public synchronized void add(IPersistent domainObject) throws ApplicationExceptions, FrameworkException {
        update(domainObject);
    //@TODO - insert object added fact
    }

    /**
     * Insert this modified object as a fact, each field change will be added as a fact
     *
     * @param domainObject Object being updated by the persistence engine
     * @throws ApplicationExceptions When there is an Application Error
     * @throws FrameworkException When there is a Infrastructure or System Error
     */
    public synchronized void update(IPersistent domainObject) throws ApplicationExceptions, FrameworkException {
        if (hasRulesSession()) {
            // Add all field changes to the session
            FieldChanged[] fieldChanges = getFieldChanges(domainObject);
            if (fieldChanges != null) {
                for (FieldChanged fc : fieldChanges)
                    m_session.get().insert(fc);
            }

            // See if fact needs to be inserted or modified
            FactHandle handle = m_session.get().getFactHandle(domainObject);
            if (handle == null) {
                if (log.isDebugEnabled()) log.debug("DO FACT ADDED - " + domainObject);
                m_session.get().insert(domainObject);
            } 
            else {
                if (log.isDebugEnabled()) log.debug("*** DO FACT MODIFIED - " + domainObject);
                //m_session.modifyInsert(h, domainObject);
                m_session.get().update(handle, domainObject);
            }
        } 
        else {
            if (log.isDebugEnabled())
                log.debug(String.format(FACT_NOT_ADDED_MESSAGE,domainObject.getClass().getSimpleName()));
        }
    }

    /**
     * Insert a special "object deleted" fact to rules engine
     *
     * @param domainObject Object being deleted by the persistence engine
     * @throws ApplicationExceptions When there is an Application Error
     * @throws FrameworkException When there is a Infrastructure or System Error
     */
    public synchronized void delete(IPersistent domainObject) throws ApplicationExceptions, FrameworkException {
        if (hasRulesSession()) {
            //@TODO - add a fact that represents object deletion
        } else {
            if (log.isDebugEnabled())
                log.debug(String.format(FACT_NOT_ADDED_MESSAGE, domainObject.getClass().getSimpleName()));
        }
    }

    /**
     * Invoked prior to the commit, so this is the correct place to start invoking
     * the Rules for this transaction
     * @throws ApplicationExceptions When there is an Application Error
     * @throws FrameworkException When there is a Infrastructure or System Error
     */
    public synchronized void writeLog() throws ApplicationExceptions, FrameworkException {
        if (hasRulesSession()) {
            try {
                // Fire Rule Engine
                if (log.isDebugEnabled()) log.debug("Fire Rules");
                m_session.get().fireAllRules();
                if (log.isDebugEnabled()) log.debug("All Rules Fired");
                
                //Check for pending events
                if (!m_pendingEvents.isEmpty()) {
                    checkForPendingEvents();
                }
            }
            catch (ConsequenceException consequenceException) {
                log.error(consequenceException.getMessage());
                destroyRulesSession();
                throw ExceptionHelper.throwAFR(consequenceException);
            }
            catch (Exception ce) {
                log.error(ce.getMessage());
                destroyRulesSession();
                throw ExceptionHelper.throwAFR(ce);
            }
            finally {
                //Flush any DB updates as a result of the changes
                m_uow.flush();
            }
        } 
        else {
            if (log.isDebugEnabled())
                log.debug("Session Not Initialized. Rules not fired by writeLog()");
        }
    }

    /**
     * Called when a rollback is done, in this case just clean up rules engine
     * @throws ApplicationExceptions When there is an Application Error
     * @throws FrameworkException When there is a Infrastructure or System Error
     */
    public synchronized void clearLog() throws ApplicationExceptions, FrameworkException {
        // Clean up rules engine as we are done with it.
        destroyRulesSession();
    }

    /**
     * Called to initialize rule base for this service
     * <p>
     *  It can be overriden, but by default it loads the rules via an agent
     *  The properties file to use is specified in the business rule
     *  <b>jaffa.soa.droolsAgentConfigFile.{name}=xxxx</b>
     *  <p>
     *  The file referenced by this rule must be in the classpath to be loaded.
     *  If this rule is not set, or the file can be loaded/found an RuntimeException will occur
     * @return Returns the rule base loaded by the agent
     */
    public synchronized RuleBase getRuleBase() {
        RuleAgent agent = getAgent();
        return (agent != null) ? agent.getRuleBase() : null;
    }

    /** Static method that can be used to tell any cached agent that they should
     * refresh there rules base. This can be used by any code that is specifically changing
     * rule files that the agent may have cached. If not used the Agent will automatically
     * refresh the rules based on its polling period and out-of-date file stamps
     */
    public static synchronized void refreshAgent(String serviceName) {
        droolsManager.refreshAgent(serviceName);
    }

    //***************************************************************
    //
    //  P R O T E C T E D   M E T H O D S
    //
    //***************************************************************
    /** Get the RuleAgent used to supply the RuleBase
     */
    protected synchronized RuleAgent getAgent() {
    	return droolsManager.getAgent(m_serviceName, VariationContext.getVariation());
    }



    //***************************************************************
    //
    //  P R I V A T  E    M E T H O D S
    //
    //***************************************************************
    /** Convert all modified fields of the object into FieldChanged facts
     */
    private FieldChanged[] getFieldChanges(IPersistent domain) {
        Map<String, Object> map = domain.getModifiedFields();
        if (map != null) {
            FieldMetaData[] fmd = null;
            try {
                fmd = PersistentHelper.getKeyFields(domain.getClass().getName());
                if (fmd.length > 3) {
                    if (log.isDebugEnabled())
                        log.debug("Key fields cannot be stamped on FieldChanged objects for the domain class " + domain.getClass().getName() + ", since the FieldChanged class limits the keys to 3, while the requirement is " + fmd.length);
                    fmd = null;
                }
            } catch (Exception e) {
                log.error("Error in determining the key fields for the domain class " + domain.getClass(), e);
            }
            FieldChanged[] fcList = new FieldChanged[map.size()];
            int fcIdx = 0;
            for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext(); fcIdx++) {
                Map.Entry<String, Object> e = it.next();
                FieldChanged fc = new FieldChanged();
                fc.setObject(domain.getClass().getSimpleName());
                fc.setField(e.getKey());
                fc.setOldValue(e.getValue());

                // Set Key Fields
                if (fmd != null) {
                    try {
                        for (int idx = 0; idx < fmd.length; idx++)
                            BeanHelper.setField(fc, "key" + (idx + 1), BeanHelper.getField(domain, fmd[idx].getName()));
                    } catch (Exception ex) {
                        log.error("Can't Set The Keys for FieldChanged Fact - " + fc.toString(), ex);
                    }
                }

                fcList[fcIdx] = fc;
                if (log.isDebugEnabled()) {
                    try {
                        log.debug("CREATED FieldChanged -> " + fc + " : New Value -> " + BeanHelper.getField(domain, fc.getField()));
                    } catch (Exception x) {
                    }
                }
            }
            return fcList;
        }
        return null;
    }

    private void checkForPendingEvents() throws ApplicationExceptions {
        ApplicationExceptions applicationExceptions = new ApplicationExceptions();
        // Build up set of all events that have been executed
        Set<String> events = new HashSet<String>();
        Iterator<ProcessEventGraph> pegIterator = m_session.get().iterateObjects(PROCESS_EVENT_GRAPH_OBJECT_FILTER);
        while(pegIterator.hasNext()) {
            events.add((pegIterator.next()).getEventName());
        }

        for (PendingEventException event : m_pendingEvents) {
            //@TODO - See if there was a ProcessEvent for this pending event,
            //If there was, don't raise it
            if (!events.contains(event.getArguments()[0])) {
                if (log.isDebugEnabled()) log.debug("ABORT due to pending event", event);
                    applicationExceptions.add(event);
            } 
            else {
                if (log.isDebugEnabled()) log.debug("Pending event satisfied by Process Event - " + event.getArguments()[0]);
            }
        }
        // Only throw if there are pending events
        if (applicationExceptions.getApplicationExceptionArray().length > 0) {
            throw applicationExceptions;
        }
    }

    private synchronized boolean hasRulesSession() {
        return (m_session != null && m_session.get() != null);
    }

    private void destroyRulesSession() {
        if (hasRulesSession()) {
            if (log.isDebugEnabled()) log.debug("Destroy Rules Session");
            m_session.get().clearAgenda();
            m_session.get().dispose();
            m_session.set(null);
            m_session = null;
        }
    }

    public static DroolsManager getDroolsManager() {
        return droolsManager;
    }

    public static void setDroolsManager(DroolsManager droolsManager) {
        ServiceRulesInterceptor.droolsManager = droolsManager;
    }
}