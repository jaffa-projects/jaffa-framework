package org.jaffa.beans.factory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Test rules validator bean
 */
@Configuration
public class RuleValidatorFactoryTestConfig {
    /**
     * Returns a test rule validator.  Does not matter if the validator is prototype or singleton because its does
     * not do anything.
     *
     * @return test rule validator
     */
    @Bean
    public FakeRuleValidatorFactory ruleValidatorFactory() {
        return new FakeRuleValidatorFactory();
    }
}
