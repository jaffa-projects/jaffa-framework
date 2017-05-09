package org.jaffa.rules.validators;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.config.JaffaRulesConfig;
import org.jaffa.persistence.Persistent;
import org.jaffa.rules.fieldvalidators.Validator;
import org.jaffa.rules.fieldvalidators.ValidatorFactory;
import org.jaffa.rules.meta.ClassMetaData;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.realm.Realm;
import org.jaffa.rules.realm.RealmRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test that Persistent Config can set the Rule Validator in a Persistent object
 */
public class PersistentConfigValidatorTest {

    /**
     * Common setup
     *
     * @throws Exception
     */
    @Before
    public void setup() throws Exception {
        //create app context, do not need to include PersistentContext directly
        AnnotationConfigApplicationContext appContext =
                new AnnotationConfigApplicationContext(JaffaRulesConfig.class, TestModelPersistentConfig.class);
        assertNotNull(appContext);
        new StaticContext().setApplicationContext(appContext);

        // get validator factory bean from combined app context
        ValidatorFactory target = appContext.getBean(ValidatorFactory.class);
        assertNotNull(target);

        Realm realm = new Realm("realm");
        realm.setSource("Test");
        realm.regex(TestModelPersistent.class.getName());
        realm.register();

        ClassMetaData classMetaData = new ClassMetaData();
        classMetaData.setName(TestModelPersistent.class.getName());
        classMetaData.setVariation("DEF");
        classMetaData.setExecutionRealm(realm.getName());
        classMetaData.register();
    }

    /**
     * Test Persistent has a validator pushed into it during construction
     */
    @Test
    public void testPersistentConfig() throws NoSuchFieldException, IllegalAccessException {
        //creating a new instance of a persistent object should have its validator pushed into it
        //automatically by persistent config
        Persistent persistent = new TestModelPersistent();

        Field f = persistent.getClass().getSuperclass().getDeclaredField("validator");
        f.setAccessible(true);
        Validator validator = (Validator) f.get(persistent);

        assertNotNull(validator);
    }
}
