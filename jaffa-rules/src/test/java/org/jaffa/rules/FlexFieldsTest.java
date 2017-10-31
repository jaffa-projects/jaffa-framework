package org.jaffa.rules;

import junit.framework.TestCase;
import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.flexfields.domain.FlexField;
import org.jaffa.persistence.UOW;
import org.jaffa.rules.testmodels.User;
import org.jaffa.rules.testmodels.UserNoFlex;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;

import java.util.Date;

import static org.mockito.Mockito.*;

public class FlexFieldsTest extends TestCase {

    private ApplicationContext ctx;

    /**
     * Creates new FlexFieldsTest
     *
     * @param name The name of the test case.
     */
    public FlexFieldsTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.setupRepos();
    }

    public void testNoFlexFields() throws FrameworkException, ApplicationExceptions {
        UserNoFlex user = new UserNoFlex();

        StaticContext.initialize(user);

        assertNull(user.getFlexBean());
    }

    public void testHasFlexFields() throws FrameworkException, ApplicationExceptions {
        User user = new User();

        StaticContext.initialize(user);
        assertNotNull(user.getFlexBean());
    }

    public void testIsModified() throws FrameworkException, ApplicationExceptions {
        User user = new User();

        assertFalse(user.isModified());
        assertNotNull(user.getFlexBean());

        user.getFlexBean().set("supervisorPhone", "1234567890");

        assertTrue(user.isModified());
    }

    public void testValidate() throws FrameworkException, ApplicationExceptions {
        User user = new User();
        StaticContext.initialize(user);
        assertNotNull(user.getFlexBean());
        user.setId("1234");

        // should pass
        user.validate();

        // remarks is upper case and limit 10
        user.getFlexBean().set("remarks", "abcedfghijklmnopqrstuvwxyz");

        try {
            user.validate();
        } catch (ApplicationExceptions e) {
            assertEquals(e.getMessage(), "[\"exception.org.jaffa.exceptions.ValidationException.tooMuchData\"]");
        }

        // and a positive
        user.getFlexBean().set("remarks", "HE IS A GREAT GUY");

        user.validate();
    }

    public void testPostAdd() throws FrameworkException, ApplicationExceptions {
        User user = new User();
        StaticContext.initialize(user);
        assertNotNull(user.getFlexBean());

        UOW mockUow = Mockito.mock(UOW.class);
        user.setUOW(mockUow);
        user.setId("1234");

        // flexbean should not have been updated since the values haven't changed
        user.postAdd();
        verify(mockUow, never()).add(new Object());

        user.getFlexBean().set("reinstateDate", new Date());
        user.postAdd();


        // the flextfield for the date should have attempted to "add" in the uow
        verify(mockUow).add(argThat(new ArgumentMatcher<FlexField>() {
            @Override
            public boolean matches(Object emp) {
                return ((FlexField) emp).getFieldName().equals("reinstateDate");
            }
        }));
    }

    public void testDelete() throws FrameworkException, ApplicationExceptions {
        User user = new User();
        StaticContext.initialize(user);
        assertNotNull(user.getFlexBean());

        UOW mockUow = Mockito.mock(UOW.class);

        // we need to trick the flex field into thinking its a db instance
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                FlexField field = (FlexField) args[0];
                field.setDatabaseOccurence(true);
                field.setModified(false);
                return null;
            }
        }).when(mockUow).add(any(FlexField.class));

        when(mockUow.getActualPersistentClass(any(FlexField.class))).thenCallRealMethod();


        user.setUOW(mockUow);
        user.setId("1111");
        user.getFlexBean().set("reinstateDate", new Date());

        // simulate a save
        user.getFlexBean().update();


        user.preDelete();
        // the flextfield for the date should have attempted to "add" in the uow
        verify(mockUow).delete(Matchers.any(FlexField.class));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.shutdownRepos();
    }
}

