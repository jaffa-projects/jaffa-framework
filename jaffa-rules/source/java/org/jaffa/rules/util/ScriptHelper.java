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

import org.apache.log4j.Logger;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.StringHelper;
import org.jaffa.util.URLHelper;

import javax.script.ScriptEngine;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * A helper class to invoke scripts via the Java's Scripting support.
 */
public class ScriptHelper {

    private static Logger log = Logger.getLogger(ScriptHelper.class);

    /**
     * The default scripting language. It is initialized to BeanShell.
     */
    public static final String DEFAULT_LANGUAGE = "beanshell";

    /**
     * The name used for adding the global Map in the context of the ScriptEngine.
     * The expressions/scripts of the rules engine can use this to access the global context.
     * The value of this constant is "context".
     */
    public static final String CONTEXT_GLOBAL = "context";

    /**
     * The name used for adding the current bean in the context of the ScriptEngine.
     * The expressions/scripts of the rules engine can use this to invoke methods of the bean.
     * The value of this constant is "bean".
     */
    public static final String CONTEXT_BEAN = "bean";

    /**
     * The name used for adding the current arguments in the context of the ScriptEngine.
     * The expressions/scripts of the rules engine can use this.
     * The value of this constant is "arguments".
     */
    public static final String CONTEXT_ARGUMENTS = "arguments";

    // Pool of script engines
    private static final ScriptEnginePool pool = new ScriptEnginePool();

    // Language supported by this script helper
    private String language = DEFAULT_LANGUAGE;

    /**
     * Returns an instance of the ScriptHelper.
     * Note: A RuntimeException will be thrown if the input language is not supported
     *
     * @param scriptLanguage The language of the script/expression. The default language is 'beanshell'
     * @return an instance of the ScriptHelper.
     */
    public static ScriptHelper instance(String scriptLanguage) {
        return new ScriptHelper(scriptLanguage);
    }

    /**
     * Creates a new instance.
     *
     * @param scriptLanguage The Script Engine.
     */
    private ScriptHelper(String scriptLanguage) {
        language = scriptLanguage;
    }

    /**
     * This will evaluate the input script and/or expression using Java's Scripting support.
     * It'll add the global-context and the input bean to the context of the ScriptEngine.
     * The script/expression can then access the above via 'context' and 'bean' respectively.
     *
     * @param scriptFileName An external file containing the script to evaluate.
     * @param expression     The expression to be evaluated.
     * @param bean           The bean to be added to the context, so that it is available to the script/expression.
     * @param source         This optional parameter contains the origins of the invocation of this method. This may then be used in error messages.
     * @param lineNo         This optional parameter contains the origins of the invocation of this method. This may then be used in error messages.
     * @param columnNo       This optional parameter contains the origins of the invocation of this method. This may then be used in error messages.
     * @return the output from the evaluation of the expression.
     * @throws Exception if any error occurs while evaluating the script/expression.
     */
    public Object evaluate(String scriptFileName, Object expression, Object bean, String source, int lineNo, int columnNo) throws Exception {
        Map beans = new HashMap();
        beans.put(CONTEXT_BEAN, bean);
        return evaluate(scriptFileName, expression, beans, source, lineNo, columnNo);
    }

    /**
     * This will evaluate the input script and/or expression using Java's Scripting support.
     * It'll add the global-context and the input beans to the context of the ScriptEngine.
     * The script/expression can then access the above via 'context' and beanNames (as provided in the input Map) respectively.
     *
     * @param scriptFileName An external file containing the script to evaluate.
     * @param expression     The expression to be evaluated.
     * @param beans          The beans to be added to the context, so that they are available to the script/expression. This Map should contain beanName(String)/bean(Object) pairs.
     * @param source         This optional parameter contains the origins of the invocation of this method. This may then be used in error messages.
     * @param lineNo         This optional parameter contains the origins of the invocation of this method. This may then be used in error messages.
     * @param columnNo       This optional parameter contains the origins of the invocation of this method. This may then be used in error messages.
     * @return the output from the evaluation of the expression.
     * @throws Exception if any error occurs while evaluating the script/expression.
     */
    public Object evaluate(String scriptFileName, Object expression, Map beans, String source, int lineNo, int columnNo) throws Exception {
        // Check the mandatory parameters
        if (scriptFileName == null && expression == null) {
            if (log.isDebugEnabled()) {
                log.debug("'scriptFileName' and/or 'expression' should be passed. Nothing done");
            }
            return null;
        }

        // Add global context
        Map globalContext = ContextManagerFactory.instance().getThreadContext();
        beans.put(CONTEXT_GLOBAL, globalContext);

        ScriptEngine scriptEngine = pool.borrowEngine(language, beans);
        try {
            // Execute the file, so that we can invoke the expression on it
            if (scriptFileName != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Evaluating the script file '" + scriptFileName + '\'');
                }

                String fileContents = StringHelper.getString(new BufferedReader(new InputStreamReader(URLHelper.getInputStream(scriptFileName))));
                scriptEngine.eval(fileContents);
            }

            // Evaluate the expression
            if (expression != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Evaluating the expression '" + expression + '\'');
                }

                Object result = scriptEngine.eval(expression.toString());

                if (log.isDebugEnabled()) {
                    log.debug("Evaluation result is: " + result);
                }

                return result;
            } else {
                return null;
            }
        } finally {
            pool.returnEngine(language, scriptEngine);
        }
    }
}