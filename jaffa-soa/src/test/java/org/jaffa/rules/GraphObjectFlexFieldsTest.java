package org.jaffa.rules;

import junit.framework.TestCase;
import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.testmodels.UserGraph;
import org.jaffa.rules.testmodels.UserGraphNoFlex;
import org.springframework.context.ApplicationContext;

public class GraphObjectFlexFieldsTest extends TestCase {

    private ApplicationContext ctx;

    /**
     * Creates new GraphObjectFlexFieldsTest
     *
     * @param name The name of the test case.
     */
    public GraphObjectFlexFieldsTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.setupRepos();
    }

    public void testNoFlexFields() throws FrameworkException, ApplicationExceptions {
        UserGraphNoFlex userGraphNoFlex = new UserGraphNoFlex();

        StaticContext.initialize(userGraphNoFlex);
        assertNull(userGraphNoFlex.getFlexBean());
    }

    public void testHasFlexFields() throws FrameworkException, ApplicationExceptions {
        UserGraph userGraph = new UserGraph();
        StaticContext.initialize(userGraph);
        assertNotNull(userGraph.getFlexBean());
    }

    public void testClearChanges() throws FrameworkException, ApplicationExceptions {
        UserGraph userGraph = new UserGraph();
        StaticContext.initialize(userGraph);
        assertNotNull(userGraph.getFlexBean());

        userGraph.setId("123");
        userGraph.setName("name");

        userGraph.getFlexBean().set("remarks", "test remarks");

        assertTrue(userGraph.getFlexBean().hasChanged());

        userGraph.clearChanges();

        assertFalse(userGraph.getFlexBean().hasChanged());
    }

    public void testHasChanged() throws FrameworkException, ApplicationExceptions {
        UserGraph userGraph = new UserGraph();
        StaticContext.initialize(userGraph);
        assertNotNull(userGraph.getFlexBean());

        assertFalse(userGraph.hasChanged());
        userGraph.getFlexBean().set("remarks", "test remarks");

        assertTrue(userGraph.hasChanged());
    }

    public void testValidate() throws FrameworkException, ApplicationExceptions {
        UserGraph userGraph = new UserGraph();
        StaticContext.initialize(userGraph);
        assertNotNull(userGraph.getFlexBean());
        userGraph.setId("1234");

        // should pass
        userGraph.validate();

        // remarks is upper case and limit 10
        userGraph.getFlexBean().set("remarks", "abcedfghijklmnopqrstuvwxyz");

        try {
            userGraph.validate();
        } catch (ApplicationExceptions e) {
            assertEquals(e.getMessage(), "[\"exception.org.jaffa.exceptions.ValidationException.tooMuchData\"]");
        }

        // and a positive
        userGraph.getFlexBean().set("remarks", "HE IS A GREAT GUY");

        userGraph.validate();
    }

}

