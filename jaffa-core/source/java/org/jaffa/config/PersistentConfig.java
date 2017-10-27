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
package org.jaffa.config;

import org.jaffa.beans.factory.ILifecycleHandlerFactory;
import org.jaffa.beans.factory.LifecycleHandlerFactory;
import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.ILifecycleHandler;
import org.jaffa.persistence.Persistent;
import org.jaffa.rules.fieldvalidators.ValidatorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Base class for all persistent configuration objects.
 */
@Configuration
public abstract class PersistentConfig implements ApplicationContextAware {
    private ApplicationContext appContext;
    private ILifecycleHandlerFactory lifecycleHandlerFactory = new LifecycleHandlerFactory();

    static {
        StaticContext.addToFactoryMethodMap(Persistent.class, "persistent");
    }

    /**
     * This handler factory can be injected into other beans to register additional handlers.
     */
    @Bean
    public ILifecycleHandlerFactory lifecycleHandlerFactory() {
        return lifecycleHandlerFactory;
    }

    /**
     * Initializes the passed in object
     *
     * @param persistent the object to initialize
     * @return the initialized object
     */
    protected <T extends Persistent> T persistent(T persistent) throws FrameworkException, ApplicationExceptions {
        if (appContext != null && persistent != null) {
            // Add validations for the persistent object
            ValidatorFactory validatorFactory = (ValidatorFactory) appContext.getBean("ruleValidatorFactory");
            if (validatorFactory != null) {
                persistent.setValidator(validatorFactory.getValidator(persistent));
            }

            // register lifecycle handlers
            registerLifecycleHandlers(persistent);
        }
        return persistent;
    }

    /**
     * {@inheritDoc}
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.appContext = applicationContext;
    }

    /**
     * Add all LifecycleHandlers to the input handler.
     */
    private void registerLifecycleHandlers(Persistent persistent) {

        // register all handlers to fire before the target handler
        List<ILifecycleHandler> prependHandlers = lifecycleHandlerFactory.getPrependedHandlers(persistent);
        if (!CollectionUtils.isEmpty(prependHandlers)) {
            persistent.prependLifecycleHandlers(prependHandlers);
        }

        // register the target handler - this must always happen, even if this build has no factory defined
        persistent.getLifecycleHandlers().add(persistent);

        // register all handlers to fire after the target handler
        List<ILifecycleHandler> appendHandlers = lifecycleHandlerFactory.getAppendedHandlers(persistent);
        if (!CollectionUtils.isEmpty(appendHandlers)) {
            persistent.appendLifecycleHandlers(appendHandlers);
        }
    }
}
