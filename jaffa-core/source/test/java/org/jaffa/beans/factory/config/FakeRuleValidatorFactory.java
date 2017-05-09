package org.jaffa.beans.factory.config;

import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.fieldvalidators.Validator;
import org.jaffa.rules.fieldvalidators.ValidatorFactory;

/**
 * Test rule validator that has a minimum implementation that does nothing
 */
public class FakeRuleValidatorFactory implements ValidatorFactory {
    /**
     * {@inheritDoc}
     */
    @Override
    public Validator getValidator(Object object) {
        return new Validator() {
            @Override
            public void validate(Object object) throws ApplicationException, FrameworkException {
            }
        };
    }
}
