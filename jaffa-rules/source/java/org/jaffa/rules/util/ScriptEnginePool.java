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
package org.jaffa.rules.util;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.PooledSoftReference;
import org.apache.commons.pool2.impl.SoftReferenceObjectPool;
import org.apache.log4j.Logger;
import org.jaffa.session.ContextManagerFactory;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pool for script engines to provide caching
 */
public class ScriptEnginePool {

    private static Logger log = Logger.getLogger(ScriptEnginePool.class);

    // BeanShell language name
    private static final String BEANSHELL = "beanshell";

    // Name of the BeanShell engine in the ScriptEngine Bindings
    private static final String BEANSHELL_ENGINE_NAME = "bsh";

    // Name of the property to be set in the application to set if the pool is to be used
    private static final String POOL_SCRIPT_ENGINE_PROPERTY = "jaffa.rules.poolScriptEngines";

    // Hash map of pools based on the language of the ScriptEngine
    private final HashMap<String, SoftReferenceObjectPool<ScriptEngine>> pools =
            new HashMap<String, SoftReferenceObjectPool<ScriptEngine>>();

    // Script engine manager to create instance of ScriptEngines
    private final ScriptEngineManager engineManager = new ScriptEngineManager();

    // If true the ScriptEngines will be pooled
    private final boolean poolScriptEngine;

    /**
     * Creates an instance of the ScriptEnginePool and initializes its settings
     */
    public ScriptEnginePool() {
        // Set poolScriptEngine to enable pooling unless explicitly set to false
        Object poolScriptEngineProperty = ContextManagerFactory.instance().getProperty(POOL_SCRIPT_ENGINE_PROPERTY);
        if (poolScriptEngineProperty != null &&
                Boolean.FALSE.toString().equalsIgnoreCase(poolScriptEngineProperty.toString())) {
            poolScriptEngine = false;
        } else {
            poolScriptEngine = true;
        }
    }

    /**
     * Gets a script engine from the pool
     *
     * @param language The language the engine executes
     * @return A script engine for executing code
     * @throws Exception Exceptions thrown by the pool or creating the script engine
     */
    public ScriptEngine borrowEngine(String language, Map<String, Object> vars) throws Exception {
        // Get the engine
        ScriptEngine scriptEngine;

        // Get a script engine
        if (poolScriptEngine) {
            scriptEngine = getPool(language).borrowObject();
        } else {
            scriptEngine = engineManager.getEngineByName(language);
        }

        initEngine(scriptEngine, vars);

        return scriptEngine;
    }

    /**
     * Returns the engine that was being utilizes
     *
     * @param language     The language the engine executes
     * @param scriptEngine The script engine to return
     * @throws Exception Exceptions thrown by the pool
     */
    public void returnEngine(String language, ScriptEngine scriptEngine) throws Exception {
        if (poolScriptEngine) {
            clearEngine(language, scriptEngine);
            getPool(language).returnObject(scriptEngine);
        }
    }

    /**
     * Gets the pool of script engines for a specific language
     *
     * @param language Language of the script engine pool
     */
    synchronized SoftReferenceObjectPool<ScriptEngine> getPool(String language) {
        SoftReferenceObjectPool<ScriptEngine> pool = pools.get(language);
        if (pool == null) {
            pool = new SoftReferenceObjectPool<ScriptEngine>(new ScriptEngineInstanceFactory(language, engineManager));
            pools.put(language, pool);
        }

        return pool;
    }

    /**
     * Initializes the script engine with the global context and provided variables
     *
     * @param scriptEngine The script engine to initialize
     * @param vars         Variables to add to the script engine
     */
    private void initEngine(ScriptEngine scriptEngine, Map<String, Object> vars) {
        if (vars == null) {
            return;
        }

        // Set the variable values
        for (Map.Entry<String, Object> var : vars.entrySet()) {
            try {
                scriptEngine.put(var.getKey(), var.getValue());
            } catch (Exception e) {
                log.error("Error adding bean to interpreter", e);
            }
        }
    }

    /**
     * Clears the script engine of all previous state except, if it is a BeanShell interpreter,
     * the engine is left
     *
     * @param scriptEngine ScriptEngine to clear
     */
    private void clearEngine(String language, ScriptEngine scriptEngine) {
        // Clear everything except the BeanShell engine ("bsh" in the binding)
        // This will clear all imported class, methods, and variables
        List<String> itemsToRemove = new ArrayList<String>();
        for (Map.Entry<String, Object> bindingEntry : scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE).entrySet()) {
            if (language.equalsIgnoreCase(BEANSHELL) && bindingEntry.getKey().equalsIgnoreCase(BEANSHELL_ENGINE_NAME)) {
                continue;
            }
            itemsToRemove.add(bindingEntry.getKey());
        }
        for (String value : itemsToRemove) {
            scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE).remove(value);
        }

        // Clear entire global scope
        scriptEngine.getBindings(ScriptContext.GLOBAL_SCOPE).clear();
    }

    /**
     * Factory for making ScriptEngine instances for the pool
     */
    private class ScriptEngineInstanceFactory extends BasePooledObjectFactory<ScriptEngine> {
        // Script engine manager for making ScriptEngine instances
        private final ScriptEngineManager scriptEngineManager;

        // The language the factory creates instance for
        private final String language;

        /**
         * Creates a ScriptEngineInstanceFactory
         *
         * @param language            Language the Script engine interprets
         * @param scriptEngineManager Manager to create instances of script managers
         */
        public ScriptEngineInstanceFactory(String language, ScriptEngineManager scriptEngineManager) {
            this.language = language;
            this.scriptEngineManager = scriptEngineManager;
        }

        /**
         * Creates and instance of a Script engine
         *
         * @return An instance of a ScriptEngine
         * @throws RuntimeException Throws a runtime exception if the language is not supported
         */
        @Override
        public ScriptEngine create() throws RuntimeException {
            ScriptEngine engine = scriptEngineManager.getEngineByName(language);
            if (engine == null) {
                String error = "Java's scripting support does not support the language " + language;
                log.error(error);
                throw new RuntimeException(error);
            }

            return engine;
        }

        /**
         * Wraps the script engine as a PooledObject
         *
         * @param scriptEngine Script engine to wrap
         * @return PooledObject containing a script engine
         */
        @Override
        public PooledObject<ScriptEngine> wrap(ScriptEngine scriptEngine) {
            return new PooledSoftReference<ScriptEngine>(new SoftReference<ScriptEngine>(scriptEngine));
        }
    }
}
