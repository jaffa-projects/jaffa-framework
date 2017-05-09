package org.jaffa.rules.meta;

/**
 * A condition for a rule.
 *
 * @param <T> the type of the object to evaluate.
 */
public interface RuleCondition<T> {
    /**
     * Evaluates the object to see if it matches the condition.
     *
     * @param bean the object to evaluate.
     * @return true if the object matches the condition.
     */
    boolean evaluate(T bean) throws Exception;
}
