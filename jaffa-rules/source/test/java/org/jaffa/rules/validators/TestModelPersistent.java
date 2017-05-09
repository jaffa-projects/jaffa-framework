package org.jaffa.rules.validators;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.persistence.Persistent;

/**
 * Test model that extends persistent
 */
public class TestModelPersistent extends Persistent {

    /**
     * Default constructor that calls static context to configure the object
     */
    public TestModelPersistent() {
        StaticContext.configure(this);
    }
}
