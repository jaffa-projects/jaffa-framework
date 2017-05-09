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
package org.jaffa.persistence.engines;

import org.apache.log4j.Logger;
import org.jaffa.config.Config;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.engines.IPersistenceEngine;
import org.jaffa.persistence.engines.IPersistenceEngineFactory;
import org.jaffa.persistence.exceptions.EngineInstantiationException;

/** A factory class that returns an instance of a Persistence Engine. */
public class PersistenceEngineFactory {

    private static final Logger log = Logger.getLogger(PersistenceEngineFactory.class);
    private static final String ENGINE_KEY = "framework.persistence.Persistence_Engine";
    private static final String FAILED_ENGINE_INSTANTIATION = "The Persistence Engine '%s' could not be instantiated";
    private static IPersistenceEngineFactory persistenceEngineFactory;

    /** 
     * Sets the instance of the persistence engine to use.
     *
     * @param persistenceEngineFactory the persistence engine instance to use.
     */
    public static void setFactory(IPersistenceEngineFactory persistenceEngineFactory) {
        PersistenceEngineFactory.persistenceEngineFactory = persistenceEngineFactory;
    }

    /** Returns an instance of IPersistenceEngine.
     * It reads the 'framework.persistence.Persistence_Engine' property from the framework.properties file.
     * It then instantiates the class returned by the property.
     * @param uow The UOW being processed.
     * @return an instance of IPersistenceEngine.
     * @throws EngineInstantiationException.
     */
    public static IPersistenceEngine newInstance(UOW uow) throws EngineInstantiationException {
        IPersistenceEngine engine = createPersistenceEngine();
        if (uow != null) {
            try {
                engine.initialize(uow);
            }
            catch (Exception e) {
                throw new EngineInstantiationException(null, e);
            }
        }
        return engine;
    }

    /**
     * Returns an instance of IPersistenceEngine. Caller must initialize the engine.
     *
     * @return an instance if IPersistenceEngine
     * @throws EngineInstantiationException the engine instantiation exception
     */
    public static IPersistenceEngine newInstance() throws EngineInstantiationException {
        return createPersistenceEngine();
    }

    /**
     * Creates a new PersistenceEngine object.
     * Reads the 'framework.persistence.Persistence_Engine' property from the framework.properties file.
     * Instantiates the class returned by the property.
     *
     * @return an instance of IPersistenceEngine.
     * @throws EngineInstantiationException when the property is not defined, the class cannot be found, 
     * or the class cannot be instantiated.
     */
    private static IPersistenceEngine createPersistenceEngine() throws EngineInstantiationException {
        String engineName = null;
        try {
            IPersistenceEngine engine;
            if (persistenceEngineFactory != null) {
                engine = persistenceEngineFactory.newPersistenceEngine();
            } else {
                engineName = (String) Config.getProperty(ENGINE_KEY);
                if (log.isDebugEnabled())
                    log.debug("Creating an instance of the Persistence Engine: " + engineName);
                Class engineClass = Class.forName(engineName);
                engine = (IPersistenceEngine) engineClass.newInstance();
            }
            return engine;
        } catch (Exception e) {
            log.error(String.format(FAILED_ENGINE_INSTANTIATION, engineName), e);
            throw new EngineInstantiationException(null, e);
        }
    }
}
