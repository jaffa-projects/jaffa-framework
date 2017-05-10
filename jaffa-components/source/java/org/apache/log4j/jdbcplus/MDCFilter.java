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

package org.apache.log4j.jdbcplus;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.codehaus.janino.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.Parser.ParseException;
import org.codehaus.janino.Scanner.ScanException;

import sun.rmi.runtime.Log;

/**
 * This is a filter based on string matching MDC values.
 * 
 * The filter is set up with keys values. keys = These are the keys to the MDC
 * values that will be used in the compare expression = A Java expression
 * evaluated on the keys passed to it, loaded from the MDC Context
 * 
 * If the rule is TRUE for the event being filtered then the filter returns
 * NEUTRAL, allowing further filters to evaluate. If the the rule is FALSE then
 * a DENY stops the event from being logged.
 * 
 * @author RickK
 */
public class MDCFilter extends Filter {

	private static final Logger LOG = Logger.getLogger(MDCFilter.class);
	private static final String SPLIT_EXPRESSION = ",";

	private String keys;
	private String expression;

	/**
	 * Sets the keys value.
	 * 
	 * @param keys
	 *          <code>String</code> the keys value.
	 */
	public void setKeys(final String keys) {
		this.keys = keys;
	}

	/**
	 * Gets the keys value.
	 * 
	 * @return <code>String</code> the keys value.
	 */
	public String getKeys() {
		return keys;
	}

	/**
	 * Gets the expression value.
	 * 
	 * @return <code>String</code> the expression value.
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Sets the expression value.
	 * 
	 * @param expression
	 *          <code>String</code> the expression value.
	 */
	public void setExpression(final String expression) {
		this.expression = expression;
	}

	/**
	 * Returns Filter#DENY if Expression returns false based on the values
	 * returned from the MDC Context.
	 * 
	 * @param event
	 *          <code>LoggingEvent</code>
	 */
	public int decide(final LoggingEvent event) {

		int ret = DENY;

		if (keys != null && expression != null) {
			final List<Object> mdcValues = new ArrayList<Object>();
			final List<Class<String>> keyTypes = new ArrayList<Class<String>>();
			final String[] keyValues = keys.split(SPLIT_EXPRESSION);
			if (keyValues != null) {
				for (final String keyValue : keyValues) {
					final Object mdcValue = MDC.get(keyValue);
					keyTypes.add((Class<String>) keyValue.getClass());
					mdcValues.add(mdcValue);
				}
			}
			ret = evaluate(getExpressionEvaluator(keyTypes, keyValues), mdcValues);
		}

		return ret;
	}

	/**
	 * Create "ExpressionEvaluator" object.
	 * 
	 * @param types
	 *          <code>List</code>
	 * @param values
	 *          <code>String</code>
	 * @return <code>ExpressionEvaluator</code>
	 */
	private ExpressionEvaluator getExpressionEvaluator(
			final List<Class<String>> types, final String[] values) {

		ExpressionEvaluator evaluator = ExpressionCache.getInstance().get(
				expression);

		if (evaluator == null) {
			evaluator = new ExpressionEvaluator();
			evaluator.setParameters(values, types.toArray(new Class[0]));
			try {
				evaluator.cook(expression);
				ExpressionCache.getInstance().put(expression, evaluator);
			} catch (CompileException e) {
				LOG.error(e);
			} catch (ParseException e) {
				LOG.error(e);
			} catch (ScanException e) {
				LOG.error(e);
			}
		}
		return evaluator;
	}

	/**
	 * Evaluate expression with actual parameter values.
	 * 
	 * @param evaluator
	 *          <code>ExpressionEvaluator</code>
	 * @param mdcValues
	 *          <code>List</code> a collection of the MDC values.
	 * @return <code>int</code>
	 */
	private int evaluate(final ExpressionEvaluator evaluator,
			final List<Object> list) {
		int ret = DENY;
		if (evaluator != null && list != null && 0 < list.size()) {
			try {
				final Object object = evaluator.evaluate(list.toArray());
				ret = (object != null && (Boolean) object) ? NEUTRAL : DENY;
			} catch (InvocationTargetException e) {
				LOG.error(e);
			}
		}
		return ret;
	}
}