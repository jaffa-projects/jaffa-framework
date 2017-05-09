package org.jaffa.rules.validators;

import org.jaffa.config.PersistentConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Test model config that extends persistent config
 */
@Configuration
public class TestModelPersistentConfig extends PersistentConfig {

    @Bean(name = "org.jaffa.rules.validators.TestModelPersistent")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public TestModelPersistent testModelPersistent(TestModelPersistent testModelPersistent) {
        return super.persistent(testModelPersistent);
    }
}
