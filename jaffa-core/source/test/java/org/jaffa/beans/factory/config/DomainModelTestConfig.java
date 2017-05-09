package org.jaffa.beans.factory.config;

import org.jaffa.beans.factory.InitializerFactory;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.engines.jdbcengine.IStoredProcedure;
import org.jaffa.rules.initializers.Initializer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;

/**
 * Test for configuration of classes using Spring Java Config
 */
@Configuration
@ComponentScan(basePackages = {"org.jaffa.**.domain", "com.mirotechnologies.**.domain"}, useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = IStoredProcedure.class))
public class DomainModelTestConfig {

    /**
     * The one-and-only static context.
     * @return the static context.
     */
    @Bean
    public StaticContext staticContext() {
        return new StaticContext();
    }

    /**
     * The one-and-only initializer context
     * @return the initializer context
     */
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public InitializerFactory initializerFactory() {
        return new InitializerFactory() {

            @Override
            public <T> Initializer<T> getInitializer(T object) {

                return new Initializer<T>() {
                    @Override
                    public T initialize(T object) throws FrameworkException {
                        if (object instanceof TestBean) {
                            ((TestBean) object).setTestInteger(StaticContextTest.TEST_INTEGER);
                        }

                        return object;
                    }
                };
            }
        };
    }

    /**
     * Configures an object of type TestBean
     *
     * @param instance the object to be configured
     * @return the configured object
     */
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public TestBean testBean(TestBean instance) {
        return testBeanInit(instance);
    }

    /**
     * Configures an object of type TestBean
     *
     * @param instance the object to be configured
     * @return the configured object
     */
    private TestBean testBeanInit(TestBean instance) {
        instance.setTestString(StaticContextTest.TEST_STRING);
        instance.setTestInteger(StaticContextTest.TEST_INTEGER);
        return instance;
    }
}
