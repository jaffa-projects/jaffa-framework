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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.soa.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.soa.rules.ServiceRulesInterceptor;
import org.jaffa.soa.services.configdomain.InjectDomainFact;
import org.jaffa.soa.services.configdomain.SoaEventInfo;
import org.jaffa.util.ExceptionHelper;

/**
 * This class should be declared as the handler for SOAEventQueueMessage payloads
 * in jaffa-transaction-config.xml. It fires the Drools-based Rules Engine.
 * 
 * @author GautamJ
 */
public class SOAEventHandler {

    private static final Logger log = Logger.getLogger(SOAEventHandler.class);
    private static final String PREFIX_PARAMS = "params.";
    private static final String SOA_EVENT_SERVICE = "soaeventservice";
    private static final String UOW_KEY = "UOW";
    private static final String FIND_BY_PK = "findByPK";

    /**
     * Creates Rules interceptor and sets up the drools-based Rules Engine to handle the input payload.
     * Rules are not fired until commit is called on the associated UOW.
     * @param uow The UOW passed in from the Transaction Consumer
     * @param message The payload being handled.
     */
    public void invoke(UOW uow, SOAEventQueueMessage message) throws FrameworkException, ApplicationExceptions {

        if (log.isDebugEnabled())  log.debug("Handling message " + message);

        synchronized (this) {
            // Create a ServiceRulesInterceptor for firing rules
            ServiceRulesInterceptor interceptor = new ServiceRulesInterceptor(SOA_EVENT_SERVICE);
            uow.addPersistenceLoggingPlugin(0, interceptor);

            // Add the input message and it's header parameters as facts
            interceptor.addFact(message);
            if (message.getHeaderParams() != null) {
                for (Object param : message.getHeaderParams())
                    interceptor.addFact(param);
            }

            // Inject domain facts, based on the configuration file
            injectDomainFacts(uow, interceptor, message);

            //fire drools rules here as we are not firing on uow.commit()
            interceptor.fireRules();
        }
    }

    private void injectDomainFacts(UOW uow, ServiceRulesInterceptor interceptor, SOAEventQueueMessage message) throws FrameworkException, ApplicationExceptions {
        SoaEventInfo soaEventInfo = ConfigurationService.getInstance().getSoaEventInfo(message.getEventName());
        if (soaEventInfo != null) {
            for (InjectDomainFact injectDomainFact : soaEventInfo.getInjectDomainFact()) {
                Object[] domainKeyValues = new Object[injectDomainFact.getDomainKey().size() + 1];
                try {
                    //Create an array of key-fields to be used for retrieving the domain object
                    //NOTE: If support for non-String key-fields is needed, the following logic can be further enhanced
                    //to take the datatype of each parameter into consideration when creating the domainKeyValues array
                    domainKeyValues[0] = uow;
                    int counter = 0;
                    for (String domainKeyName : injectDomainFact.getDomainKey()) {
                        if (domainKeyName.length() > PREFIX_PARAMS.length() && domainKeyName.startsWith(PREFIX_PARAMS)) {
                            domainKeyName = domainKeyName.substring(domainKeyName.length());
                        }
                        domainKeyValues[++counter] = message.getHeaderParam(domainKeyName) != null ? message.getHeaderParam(domainKeyName).getValue() : null;
                    }

                    //Invoke the findByPK method to obtain the domain object
                    Object domainObject = findDomainObject(injectDomainFact.getDomainClass(), domainKeyValues);
                    if (domainObject != null)
                        interceptor.addFact(domainObject);
                    else {
                        if (log.isDebugEnabled())
                            log.debug("Instance of domain class " + injectDomainFact.getDomainClass() + " not found with the arguments " + Arrays.toString(domainKeyValues));
                    }
                } catch (Exception e) {
                    throw ExceptionHelper.throwAFR(e);
                }
                finally {
                    domainKeyValues = null;
                }  
            }
        }
    }

    private Object findDomainObject(String domainClassName, Object[] domainKeyValues) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object domainObject = null;
        Class<?> domainClass = Class.forName(domainClassName);
        for (Method m : domainClass.getMethods()) {
            if (m.getName().equals(FIND_BY_PK) && m.getParameterTypes() != null && m.getParameterTypes().length == domainKeyValues.length && UOW.class.isAssignableFrom(m.getParameterTypes()[0]) && m.getReturnType() == domainClass && Modifier.isStatic(m.getModifiers())) {
                domainObject = m.invoke(null, domainKeyValues);
                break;
            }
        }
        return domainObject;
    }
}
