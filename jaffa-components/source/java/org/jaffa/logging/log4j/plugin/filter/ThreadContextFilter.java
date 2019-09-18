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
package org.jaffa.logging.log4j.plugin.filter;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.ThreadContextMapFilter;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.codehaus.janino.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.Parser;
import org.codehaus.janino.Scanner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This custom plugin filters the logging based on the KeyValuePair given on the log4j2.xml.
 * The key used here is Name of the context key and value would be an expression.
 * Example expressions are LoggedBy!=null and LoggedBy==null.
 * ThreadContextFilter also support 'and' or 'or' operators.
 * The default is 'and' operator.
 * E.g. - if either of the condition meets then it will logs the message.
 *    <ThreadContextFilter onMatch="ACCEPT" onMismatch="DENY" operator="or">
 *      <KeyValuePair key="LoggedBy" value="LoggedBy!=null" />
 *      <KeyValuePair key="CorrelationType" value="CorrelationType!=null" />
 *    </ThreadContextFilter>
 * E.g.
 * <p>
 *     <JDBC name="JDBCAppender" tableName="J_BUSINESS_EVENT_LOGS">
 *       <filters>
 *         <ThreadContextFilter onMatch="ACCEPT" onMismatch="DENY">
 *           <KeyValuePair key="LoggedBy" value="LoggedBy!=null" />
 *         </ThreadContextFilter>
 *       </filters>
 *       <DataSource jndiName="java:/comp/env/jdbc/GOLDDatasource" />
 *       <Column name="LOG_ID" pattern="%u" />
 *       <Column name="LOGGED_ON" isEventTimestamp="true" />
 *       <Column name="MESSAGE_TYPE" pattern="%level" />
 *       <Column name="MESSAGE_TEXT" pattern="%m" />
 *       <Column name="SOURCE_CLASS" pattern="%logger" />
 *       <Column name="STACK_TRACE" pattern="%throwable " />
 *       <Column name="CORRELATION_TYPE" pattern="%X{CorrelationType}" />
 *       ................
 *       <Column name="SUB_PROCESS_NAME" pattern="%X{SubProcessName}" />
 *     </JDBC>
 */
@Plugin(
        name = "ThreadContextFilter",
        category = "Core",
        elementType = "filter",
        printObject = true
)
public class ThreadContextFilter extends ThreadContextMapFilter {

    private String expression;

    public ThreadContextFilter(Map<String, List<String>> pairs, boolean oper, Result onMatch, Result onMismatch) {
        super(pairs, oper, onMatch, onMismatch);
    }

    @Override
    public Result filter(LogEvent event) {

        ReadOnlyStringMap data = event.getContextData();
        boolean match = false;

        List<String> keyValues = new ArrayList<String>();
        List<Class<String>> keyTypes = new ArrayList<Class<String>>();
        List<Object> mdcValues = new ArrayList<Object>();
        for (int i = 0; i < this.getStringMap().size(); ++i) {
            keyValues.add(this.getStringMap().getKeyAt(i));
            keyTypes.add((Class<String>) this.getStringMap().getKeyAt(i).getClass());
            final Object mdcValue = data.getValue(this.getStringMap().getKeyAt(i));
            List value = this.getStringMap().getValueAt(i);

            if (value != null && value.size() > 0) {
                setExpression((String)value.get(0));
            }
            mdcValues.add(mdcValue);
            match = evaluate(getExpressionEvaluator(keyTypes, keyValues.toArray(new String[0])), mdcValues);
            if (!this.isAnd() && match || this.isAnd() && !match) {
                break;
            }
            keyValues.clear();
            keyTypes.clear();
            mdcValues.clear();
        }

        return match ? this.onMatch : this.onMismatch;
    }

    /**
     *
     * @param pairs
     * @param oper
     * @param match
     * @param mismatch
     * @return
     */
    @PluginFactory
    public static org.apache.logging.log4j.core.filter.ThreadContextMapFilter createFilter(@PluginElement("Pairs") KeyValuePair[] pairs, @PluginAttribute("operator") String oper, @PluginAttribute("onMatch") Result match, @PluginAttribute("onMismatch") Result mismatch) {
        if (pairs != null && pairs.length != 0) {
            Map<String, List<String>> map = new HashMap();
            KeyValuePair[] keyValuePairs = pairs;

            for (int i$ = 0; i$ < pairs.length; ++i$) {
                KeyValuePair pair = keyValuePairs[i$];
                String key = pair.getKey();
                if (key == null) {
                    LOGGER.error("A null key is not valid in MapFilter");
                } else {
                    String value = pair.getValue();
                    if (value == null) {
                        LOGGER.error("A null value for key " + key + " is not allowed in MapFilter");
                    } else {
                        List<String> list = (List) map.get(pair.getKey());
                        if (list != null) {
                            list.add(value);
                        } else {
                            list = new ArrayList();
                            list.add(value);
                            map.put(pair.getKey(), list);
                        }
                    }
                }
            }

            if (map.isEmpty()) {
                LOGGER.error("ThreadContextFilter is not configured with any valid key value pairs");
                return null;
            } else {
                boolean isAnd = oper == null || !oper.equalsIgnoreCase("or");
                return new ThreadContextFilter(map, isAnd, match, mismatch);
            }
        } else {
            LOGGER.error("key and value pairs must be specified for the ThreadContextFilter");
            return null;
        }
    }

    /**
     *
     * @param types
     * @param values
     * @return
     */
    private ExpressionEvaluator getExpressionEvaluator(final List<Class<String>> types, final String[] values) {

        ExpressionEvaluator evaluator = ExpressionCache.getInstance().get(getExpression());

        if (evaluator == null) {
            evaluator = new ExpressionEvaluator();
            evaluator.setParameters(values, types.toArray(new Class[0]));
            try {
                evaluator.cook(getExpression());
                ExpressionCache.getInstance().put(getExpression(), evaluator);
            } catch (CompileException e) {
                LOGGER.error(e);
            } catch (Parser.ParseException e) {
                LOGGER.error(e);
            } catch (Scanner.ScanException e) {
                LOGGER.error(e);
            }
        }
        return evaluator;
    }


    /**
     *
     * @param evaluator
     * @param list
     * @return
     */
    private boolean evaluate(final ExpressionEvaluator evaluator, final List<Object> list) {
        boolean ret = false;
        if (evaluator != null && list != null && 0 < list.size()) {
            try {
                final Object object = evaluator.evaluate(list.toArray());
                ret = (object != null && (Boolean) object);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     *
     * @return expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     *
     * @param expression
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }
}