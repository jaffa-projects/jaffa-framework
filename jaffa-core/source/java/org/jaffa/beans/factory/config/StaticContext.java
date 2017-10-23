/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2015 JAFFA Development Group
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

package org.jaffa.beans.factory.config;

import org.apache.log4j.Logger;
import org.jaffa.beans.factory.InitializerFactory;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.initializers.Initializer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * A helper class that provides a method to inject dependencies on instances of objects who's lifecycle is not
 * managed by a Spring IoC container.
 * <p/>
 * It is expected that this object be used as a static implementation only
 * <p/>
 * The implementation requires a reference to a spring context which is injected automatically during initialization.
 */
@Component
public class StaticContext implements ApplicationContextAware {
    private static final Logger logger = Logger.getLogger(StaticContext.class.getName());

    private static ApplicationContext appContext;
    private static Map<Class<?>, String> factoryMethodMap = new HashMap<>();
    private static InitializerFactory initializerFactory;

    /**
     * Configures the object passed in.
     *
     * @param object the object to configure
     */
    public static <T> T configure(T object) {
        if (appContext == null) {
            if (object != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Cannot configure object because Application Context is null for: " + object.getClass().getName());
                    logger.debug("This could be because the ApplicationContext is not yet set and Spring tries to initialize the object (as part of @GeneratedValue).");
                }
            } else {
                logger.debug("Cannot configure object because Application Context is null and object is null");
            }
            return object;
        }
        if (object == null) {
            logger.error("Cannot configure object because it's null");
            return object;
        }

        Class<?> fallback = null;
        try {
            appContext.getBean(object.getClass(), object);
        } catch (BeansException exception) {
            fallback = findFallback(object.getClass());
        }

        if (fallback != null) {
            try {
                appContext.getBean(factoryMethodMap.get(fallback), object);
            } catch (BeansException exception) {
                logger.error("An error occurred while attempting to configure the instances of type " +
                        object.getClass().getName() +
                        ".  This occurred because the bean definition could not be found.", exception);
            }
        }

        initialize(object);

        return object;
    }

    public static boolean hasAppContext() {
        return appContext != null;
    }

    /**
     * Calls the initializer for the provided object if one exists
     *
     * @param object the object to initialize
     * @return the pass in object
     */
    public static <T> T initialize(T object) {
        if (initializerFactory == null && appContext != null) {
            initializerFactory = appContext.getBean(InitializerFactory.class);
        }

        if (initializerFactory != null) {
            try {
                Initializer<T> initializer = initializerFactory.getInitializer(object);
                if (initializer != null) {
                    initializer.initialize(object);
                }
            } catch (FrameworkException exception) {
                logger.error("Could not initialize object: " + object.getClass().getName() + ". " + exception.getMessage());
            }
        }

        return object;
    }

    /**
     * Method to find class for which there may be a bean with configuration already defined.
     *
     * @param original
     * @return
     */
    private static Class<?> findFallback(Class<?> original) {
        for (Class<?> clazz : factoryMethodMap.keySet()) {
            if (clazz != null && clazz.isAssignableFrom(original)) {
                return clazz;
            }
        }
        return null;
    }

    /**
     * Specify a class and bean factory method and add the pair to the mapping.
     *
     * @param clazz
     * @param beanFactoryMethod
     */
    public static void addToFactoryMethodMap(Class<?> clazz, String beanFactoryMethod) {
        factoryMethodMap.put(clazz, beanFactoryMethod);
    }

    /**
     * Remove a class/bean factory method mapping.
     *
     * @param clazz
     */
    public static void removeFromFactoryMethodMap(Class<?> clazz) {
        factoryMethodMap.remove(clazz);
    }

    /**
     * Get the name of the bean factory method for this class.
     *
     * @param clazz
     * @return
     */
    public static String getFactoryMethodMapFor(Class<?> clazz) {
        return factoryMethodMap.get(clazz);
    }

    /**
     * This method allows jsp and GOLDesp code to pull a Spring configured bean.
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> beanClass) {
        return appContext.getBean(beanClass);
    }

    /**
     * This method allows jsp and GOLDesp code to pull a Spring configured bean by identifier
     *
     * @param beanName
     * @return The bean configured by Spring or null if not found.
     */
    public static Object getBean(String beanName) {
        return appContext.getBean(beanName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        StaticContext.appContext = applicationContext;
    }
}
