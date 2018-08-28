/*
 *  ====================================================================
 *  JAFFA - Java Application Framework For All
 *
 *  Copyright (c) 2017 JAFFA Development Group
 *
 *      This library is free software; you can redistribute it and/or
 *      modify it under the terms of the GNU Lesser General Public
 *      License as published by the Free Software Foundation; either
 *      version 2.1 of the License, or (at your option) any later version.
 *
 *      This library is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *      Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public
 *      License along with this library; if not, write to the Free Software
 *      Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  Redistribution and use of this software and associated documentation ("Software"),
 *  with or without modification, are permitted provided that the following conditions are met:
 *  1.	Redistributions of source code must retain copyright statements and notices.
 *          Redistributions must also contain a copy of this document.
 *  2.	Redistributions in binary form must reproduce the above copyright notice,
 *  	this list of conditions and the following disclaimer in the documentation
 *  	and/or other materials provided with the distribution.
 *  3.	The name "JAFFA" must not be used to endorse or promote products derived from
 *  	this Software without prior written permission. For written permission,
 *  	please contact mail to: jaffagroup@yahoo.com.
 *  4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  	appear in their names without prior written permission.
 *  5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 *  THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 *  ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 *  USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 *  OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 *  SUCH DAMAGE.
 *  ====================================================================
 */

package org.jaffa.loader;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.jaffa.beans.factory.ILifecycleHandlerFactory;
import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.config.JaffaRulesConfig;
import org.jaffa.config.PersistentConfig;
import org.jaffa.datatypes.Parser;
import org.jaffa.persistence.ILifecycleHandler;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.rulemeta.ScriptLifecycleHandler;
import org.jaffa.soa.dataaccess.ITransformationHandler;
import org.jaffa.soa.dataaccess.ITransformationHandlerFactory;
import org.jaffa.soa.dataaccess.SOAEventLifecycleHandler;
import org.jaffa.soa.dataaccess.SOAEventTransformationHandler;
import org.jaffa.soa.dataaccess.TransformationHandler;
import org.jaffa.soa.dataaccess.TransformationHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Configuration class used to set {@link TransformationHandler} lists on all Transformation Handlers.
 * The handlers will be added in a specific order as defined by the {@link ITransformationHandlerFactory} to allow for
 * custom code to be executed before or after all lifecycle events.  This config bean can be used to initialize
 * any Transformation Handler with its list of all handlers.
 * <p/>
 * This imports the JaffaRulesConfig to make sure the repos are loaded before we try to read from them.
 * <p/>
 * Created by ndzwill on 8/18/2017.
 */
@Configuration
@Import({JaffaRulesConfig.class, PersistentConfig.class})
@DependsOn("aopFolderWatcher")
public class LifecycleHandlerConfig {

    protected static Logger log = Logger.getLogger(LifecycleHandlerConfig.class);

    private static final String SoaEventRuleName = "raise-soa-event";
    private static final String ScriptRuleName = "script";

    @Autowired
    private ILifecycleHandlerFactory lifecycleHandlerFactory;

    private ITransformationHandlerFactory transformationHandlerFactory = new TransformationHandlerFactory();

    // used to register handlers to different factories
    private enum FactoryType {
        TransformationHandler,
        LifecycleHandler
    }

    // add the bean name for TransformationHandlers to the StaticContext so they can be configured here
    static {
        StaticContext.addToFactoryMethodMap(TransformationHandler.class, "transformationHandler");
    }

    /**
     * Populate factory with SOA events and scripts defined in the rules repository.
     */
    @PostConstruct
    public void init() {
        addLifecycleHandlers(SoaEventRuleName);
        addLifecycleHandlers(ScriptRuleName);
    }

    /**
     * This handler factory can be injected into other beans to register additional handlers.
     */
    @Bean
    public ITransformationHandlerFactory transformationHandlerFactory() {
        init();
        return transformationHandlerFactory;
    }

    /**
     * Takes an instance of a Transformation Handler and sets all registered handlers on it.
     * Handlers are registered in the handler factory by type and order, meaning the lifecycle methods can be fired
     * before or after the target input handler is invoked.  To do this we will add all the handlers into a list
     * on the target input handler.  All of the handler to fire before the target are prepended to the list, then
     * the target itself is added to the list, then all of the handlers to fire after the target are added
     * at the end.  This allows custom handler logic to be injected before or after each lifecycle
     * method of the Transformation Handler.
     *
     * @param handler the target handler to set all customer lifecycle handlers on.
     * @return the input handler after adding any custom lifecycle handlers to it.
     */
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public TransformationHandler transformationHandler(TransformationHandler handler) {
        registerTransformationHandlers(handler);
        return handler;
    }

    /**
     * Add all TransformationHandlers to the input handler.
     */
    private void registerTransformationHandlers(TransformationHandler handler) {

        // register all handlers to fire before the target handler
        List<ITransformationHandler> prependHandlers = transformationHandlerFactory.getPrependedHandlers(handler);
        if (!CollectionUtils.isEmpty(prependHandlers)) {
            handler.prependTransformationHandlers(prependHandlers);
        }

        // register the target handler - this must always happen, even if this build has no factory defined
        handler.getTransformationHandlers().add(handler);

        // register all handlers to fire after the target handler
        List<ITransformationHandler> appendHandlers = transformationHandlerFactory.getAppendedHandlers(handler);
        if (!CollectionUtils.isEmpty(appendHandlers)) {
            handler.appendTransformationHandlers(appendHandlers);
        }
    }

    /**
     * Get list of classes that contain rules with the input name and adds them to transformation handler classes
     * and lifecycle event classes.
     */
    private void addLifecycleHandlers(String ruleName) {

        // get list of classes that contain rules of the input type
        String[] classNames = MetaDataRepository.instance().getClassNamesByRuleName(ruleName);
        if (classNames == null) {
            return;
        }

        // for each class, try to add the rules to appropriate factory
        for (String className : classNames) {
            try {
                // if class is a transformation handler then add it's rules to the transformationHandlerFactory
                // else if a class is a lifecycle handler then add it's rule to the lifecycleHandlerFactory
                if (ITransformationHandler.class.isAssignableFrom(Class.forName(className))) {
                    addHandlerToFactory(className, FactoryType.TransformationHandler, ruleName);
                } else if (ILifecycleHandler.class.isAssignableFrom(Class.forName(className))) {
                    addHandlerToFactory(className, FactoryType.LifecycleHandler, ruleName);
                }
            } catch (ClassNotFoundException e) {
                log.debug(e.getLocalizedMessage());
            }
        }
    }

    /**
     * Generates handlers based on the factory type and rule meta data in the rule repository for the supplied
     * class and inserts them into the appropriate factory.
     *
     * @param targetClassName The target Class
     */
    private void addHandlerToFactory(String targetClassName, FactoryType factoryType, String ruleName) {
        try {
            if (targetClassName == null) {
                return;
            }

            // get rules for this class from rule repository
            Map<String, List<RuleMetaData>> map = MetaDataRepository.instance().getPropertyRuleMap(targetClassName, ruleName);
            if (map == null) {
                return;
            }

            // for each rule, create the appropriate handler and add it to the factory
            for (Map.Entry<String, List<RuleMetaData>> me : map.entrySet()) {
                List<RuleMetaData> rules = me.getValue();
                for (RuleMetaData rule : rules) {
                    switch (factoryType) {
                        case TransformationHandler:
                            addTransformationHandler(ruleName, rule, targetClassName);
                            break;
                        case LifecycleHandler:
                            addLifecycleHandler(ruleName, rule, targetClassName);
                            break;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getLocalizedMessage());
            }
        }
    }

    /**
     * Create an instance of a transformation handler and add it to the factory as a prepended or appended handler.
     * We only expect SoaEvent rules for transformation handlers.
     */
    private void addTransformationHandler(String ruleName, final RuleMetaData rule, String targetClassName) throws ClassNotFoundException {
        ITransformationHandlerFactory.ITransformationHandlerProvider provider = null;
        if (ruleName.equals(SoaEventRuleName)) {
            provider = new ITransformationHandlerFactory.ITransformationHandlerProvider() {
                @Override
                public ITransformationHandler getHandler() {
                    return new SOAEventTransformationHandler(rule);
                }
            };
        }

        // if the handler is not defined, we are done here
        if (provider == null) {
            log.debug("No handler defined for rule: " + rule);
            return;
        }

        // append or prepend based on the input flag
        Boolean executeOnReturn = Parser.parseBoolean(rule.getParameter(RuleMetaData.PARAMETER_EXECUTE_ON_RETURN));
        if (executeOnReturn) {
            transformationHandlerFactory.addAppendedHandler(Class.forName(targetClassName), provider);
        } else {
            transformationHandlerFactory.addPrependedHandler(Class.forName(targetClassName), provider);
        }

    }

    /**
     * Create an instance of a lifecycle handler and add it to the factory as a prepended or appended handler.
     */
    private void addLifecycleHandler(String ruleName, final RuleMetaData rule, String targetClassName) throws ClassNotFoundException {
        ILifecycleHandlerFactory.ILifecycleHandlerProvider provider = null;
        if (ruleName.equals(SoaEventRuleName)) {
            provider = new ILifecycleHandlerFactory.ILifecycleHandlerProvider() {
                @Override
                public ILifecycleHandler getHandler() {
                    return new SOAEventLifecycleHandler(rule);
                }
            };
        } else if (ruleName.equals(ScriptRuleName)) {
            provider = new ILifecycleHandlerFactory.ILifecycleHandlerProvider() {
                @Override
                public ILifecycleHandler getHandler() {
                    return new ScriptLifecycleHandler(rule);
                }
            };
        }

        // if the handler is not defined, we are done here
        if (provider == null) {
            log.debug("No handler provider defined for rule: " + rule);
            return;
        }

        // append or prepend based on the input flag
        Boolean executeOnReturn = Parser.parseBoolean(rule.getParameter(RuleMetaData.PARAMETER_EXECUTE_ON_RETURN));
        if (executeOnReturn) {
            lifecycleHandlerFactory.addAppendedHandlerProvider(Class.forName(targetClassName), provider);
        } else {
            lifecycleHandlerFactory.addPrependedHandlerProvider(Class.forName(targetClassName), provider);
        }
    }
}
