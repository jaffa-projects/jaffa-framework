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

package org.jaffa.rules;

import org.apache.log4j.Logger;

/** This class can be used to register and obtain an implementation of the IRulesEngine interface.
 *
 * By default this will use the org.jaffa.rules.meta.MetaDataRulesEngine.
 *
 * To change the default you can either invoke the registerRulesEngine() method prior
 * to the first call to RulesEngineFactory.getRulesEngine(), or set the JVM parameter
 * 'org.jaffa.rules.RulesEngineFactory' with the prefered class.
 * <code>
 * java -Dorg.jaffa.rules.RulesEngineFactory=com.myapp.MyRulesEngine ...
 * </code>
 *
 * @author  GautamJ
 */
public class RulesEngineFactory {

    private static final Logger log = Logger.getLogger(RulesEngineFactory.class);

    // Name of the default implementation for the rules engine
    //private static String c_rulesEngineClassName = "org.jaffa.rules.aop.AopRulesEngine";
    private static String c_rulesEngineClassName = "org.jaffa.rules.meta.MetaDataRulesEngine";
    static {
        // Check the JVM parameter 'org.jaffa.rules.RulesEngineFactory'
        String className = System.getProperty(RulesEngineFactory.class.getName());
        if (className != null) {
            try {
                if (log.isDebugEnabled())
                    log.debug("The value for the JVM parameter 'org.jaffa.rules.RulesEngineFactory' parameter is " + className);
                Class.forName(className);
                c_rulesEngineClassName = className;
            } catch (ClassNotFoundException e) {
                if (log.isDebugEnabled())
                    log.debug("Unable to load class " + className + ". Will use the default " + c_rulesEngineClassName, e);
            }
        }

        try {
            Class.forName(c_rulesEngineClassName);
            if (log.isInfoEnabled())
                log.info("The RulesEngine implementation is " + c_rulesEngineClassName);
        } catch (ClassNotFoundException e) {
            // The default implementation is not available !!
            if (log.isDebugEnabled())
                log.debug("The RulesEngine implementation is not available", e);
            c_rulesEngineClassName = null;
        }
    }

    // A singleton instance of the RulesEngine
    private static IRulesEngine c_rulesEngine = null;



    /** Registers the given rulesEngine with this Factory.
     * It'll create a singleton instance of the input rulesEngine.
     * This method should typically be called only once during application startup.
     * @param rulesEngineClassName A fully qualified classname of an implemenation of the IRulesEngine interface.
     * @throws RulesEngineException if any error occurs in instantiating the rules engine.
     */
    public synchronized static void registerRulesEngine(String rulesEngineClassName) throws RulesEngineException {
        c_rulesEngineClassName = rulesEngineClassName;
        c_rulesEngine = null;
        createRulesEngineInstance();
    }

    /** Returns a singleton instance of the IRulesEngine.
     * If no class has been registered, then an instance of 'org.jaffa.rules.meta.MetaDataRulesEngine' will be returned, provided it is available.
     * Else a null will be returned.
     * @throws RulesEngineException if any error occurs in instantiating the rules engine.
     * @return a singleton instance of the IRulesEngine.
     */
    public static IRulesEngine getRulesEngine() throws RulesEngineException {
        if (c_rulesEngine == null && c_rulesEngineClassName != null)
            createRulesEngineInstance();
        return c_rulesEngine;
    }







    /** This creates a singleton instance of the IRulesEngine.
     * @throws RulesEngineException if any error occurs in instantiating the rules engine.
     */
    private synchronized static void createRulesEngineInstance() throws RulesEngineException {
        if (c_rulesEngine == null && c_rulesEngineClassName != null) {
            try {
                if (log.isDebugEnabled())
                    log.debug("Creating a IRulesEngine instance of " + c_rulesEngineClassName);
                c_rulesEngine = (IRulesEngine) Class.forName(c_rulesEngineClassName).newInstance();
            } catch (Exception e) {
                String str = "Error in instantiating the Rules Engine";
                log.error(str, e);
                throw new RulesEngineException(RulesEngineException.ENGINE_INSTANTIATION_FAILED, null, e);
            }
        }
    }

}
