package org.apache.log4j.jdbcplus;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.janino.ExpressionEvaluator;

/**
 * An Expression Cache that holds the cooked Evaluator Expressions
 * 
 * @author rickk
 * 
 */
public final class ExpressionCache {

	private static final ExpressionCache INSTANCE = new ExpressionCache();
	private Map<String, ExpressionEvaluator> expCache;

	private ExpressionCache() {
		expCache = new HashMap<String, ExpressionEvaluator>();
	}

	public static ExpressionCache getInstance() {
		return INSTANCE;
	}

	public synchronized void put(final String key, final ExpressionEvaluator value) {
		if ((!expCache.containsKey(key) || expCache.get(key) == null)
				&& value != null)
			expCache.put(key, value);
	}

	public synchronized ExpressionEvaluator get(final String key) {
		return expCache.get(key);
	}
}