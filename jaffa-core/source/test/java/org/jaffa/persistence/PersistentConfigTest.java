package org.jaffa.persistence;

import org.jaffa.beans.factory.config.DomainModelTestConfig;
import org.jaffa.beans.factory.config.RuleValidatorFactoryTestConfig;
import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.beans.factory.config.TestPersistentBean;
import org.jaffa.config.PersistentBeanConfig;
import org.jaffa.config.PersistentConfig;
import org.jaffa.persistence.exceptions.PreAddFailedException;
import org.jaffa.rules.fieldvalidators.Validator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertNotNull;

/**
 * Persistence Java Configuration Initialization test
 */
public class PersistentConfigTest {

    /**
     * Setup StaticContext
     */
    @BeforeClass
    public static void beforeClass() {
        //create an App Context that has scanned a package looking for classes annotated with @Configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(PersistentBeanConfig.class,
                RuleValidatorFactoryTestConfig.class, DomainModelTestConfig.class);
    }

    /**
     * Test add fallback pattern to the static context causes the class to initialize
     */
    @Test
    public void testFallBackToPersistent() throws PreAddFailedException, NoSuchFieldException, IllegalAccessException {
        TestPersistentBean persistent = StaticContext.configure(new TestPersistentBean());

        Field f = persistent.getClass().getSuperclass().getDeclaredField("validator");
        f.setAccessible(true);
        Validator validator = (Validator) f.get(persistent);

        assertNotNull("bean should be initialized without a config for TestPersistentBean", validator);
    }
}
