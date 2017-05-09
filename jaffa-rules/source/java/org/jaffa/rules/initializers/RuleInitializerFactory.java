package org.jaffa.rules.initializers;

import org.apache.log4j.Logger;
import org.jaffa.beans.factory.InitializerFactory;
import org.jaffa.rules.RuleActorFactory;

/**
 * A factory for object initializers that are based on meta-data rules.
 */
public class RuleInitializerFactory extends RuleActorFactory<Initializer> implements InitializerFactory {
    private static final Logger logger = Logger.getLogger(RuleInitializerFactory.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Initializer<T> getInitializer(T object) {
        return getActor(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Initializer createActor(Object object) {
        FieldInitializer initializer = new FieldInitializer();
        try {
            initializer.initializeRuleMapFromMetaData(object.getClass().getName());
        } catch (Exception e) {
            logger.error("Exception while creating an initializer for " + object.getClass().getSimpleName(), e);
        }
        return initializer;
    }
}
