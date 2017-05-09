package org.jaffa.beans.factory.config;

import org.jaffa.persistence.Persistent;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Unit test for the Static Context, which tests that a object is able to be configured using Java Config.  The object
 * can either by created by the Spring Java Config or manually created using "new" and then passed to spring for configuration
 * using the same methods the Spring Java Config will use to configure an object.
 */
public class StaticContextTest {

    //   Expected results.   Must match the context file provided
    public static final String TEST_STRING = "Test String Value";
    public static final Integer TEST_INTEGER = 12345;
    private static AnnotationConfigApplicationContext appContext;

    /**
     * Setup StaticContext
     */
    @BeforeClass
    public static void beforeClass() {
        //create an App Context that has scanned a package looking for classes annotated with @Configuration
        appContext = new AnnotationConfigApplicationContext("org.jaffa.config", "org.jaffa.beans.factory.config");
    }

    @Test
    public void testFactoryMethodMappingPersistentBeans() throws Exception {
        String beanFactoryMethod = StaticContext.getFactoryMethodMapFor(Persistent.class);
        assertEquals("persistent", beanFactoryMethod);
    }

    /**
     * Test add fallback pattern to the static context causes the class to initialize
     */
    @Test
    public void testNoBeanConfiguration() {
        //first confirm there is no configuration/bean for TestBeanDerived
        TestBeanDerived bean = StaticContext.configure(new TestBeanDerived());
        assertNull("bean should not be initialized", bean.getTestString());

        //add fallback pattern to the static context and retest that the class is now initialized
        StaticContext.addToFactoryMethodMap(TestBean.class, "testBean");
        StaticContext.configure(bean);
        assertNotNull("bean should be initialized", bean.getTestString());
        StaticContext.removeFromFactoryMethodMap(TestBean.class);
    }

    /**
     * Test configuration of objects created by or just configured by the StaticContext
     */
    @Test
    public void testConfigAndInitialize() throws Exception {
        //get a new object configured from the StaticContext
        TestBean bean1 = StaticContext.configure(new TestBean());
        doCompare(bean1);
    }

    /**
     * The actual test is here,  the only difference is the way that the bean is initialized and populated.
     * Either way the values should come out the same.
     *
     * @param bean The bean to be tested
     */
    private void doCompare(TestBean bean) {

        assertNotNull("bean was null", bean);
        assertEquals("Test String was not configured", bean.getTestString(), TEST_STRING);
        assertEquals("Test Integer was not initialized", bean.getTestInteger(), TEST_INTEGER);
    }
}
