package org.jaffa.rules.initializers;

import org.apache.log4j.Logger;
import org.jaffa.beans.factory.InitializerFactory;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.flexfields.FlexClass;
import org.jaffa.rules.RuleActorFactory;

import java.util.ArrayList;
import java.util.List;

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
            initializer.initializeRuleMapFromMetaData(getClassNameList(object));
        } catch (Exception e) {
            logger.error("Exception while creating an initializer for " + object.getClass().getSimpleName(), e);
        }
        return initializer;
    }

    /**
     * Gets a list of all class names in the hierarchy from the input class up to but not including Object.
     * The list is ordered so subclasses come after their immediate super.
     */
    private List<String> getClassNameList(Object object) {
        List<String> classNames = new ArrayList<>();
        /** If object is FlexBean and there is no persistence object associated with it then use the FlexClass to find
         *  metadata details.
         */
        if (object instanceof FlexBean) {
            FlexBean flexBean = (FlexBean) object;
            if (flexBean.getPersistentObject() == null) {
                FlexClass flexClass = flexBean.getDynaClass() != null ? (FlexClass) flexBean.getDynaClass() : null;
                if (flexClass != null) {
                    classNames.add(flexClass.getName());
                    return classNames;
                }
            }
        }
        classNames.add(object.getClass().getName());
        Class superclass = object.getClass().getSuperclass();

        // stop iterating when we hit object
        while ((superclass != null) && (superclass != Object.class)) {
            classNames.add(0, superclass.getName());
            superclass = superclass.getSuperclass();
        }
        return classNames;
    }

    /**
     * Clears Initializer Actor Cache
     */
    public void flushInitializerCache(){
        cache.clear();
    }
}
