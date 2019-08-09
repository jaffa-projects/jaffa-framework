package org.jaffa.rules;

import org.apache.log4j.Logger;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.rules.realm.RealmRepository;
import org.jaffa.security.VariationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates and caches "actors" that use meta-data rules to manipulate objects.
 *
 * @param <ActorT> the type of actor to create and cache.
 */
public abstract class RuleActorFactory<ActorT> {
    private static final Logger logger = Logger.getLogger(RuleActorFactory.class);
    protected final Object syncObject = new Object();
    protected Map<KeySet, ActorT> cache = Collections.synchronizedMap(new HashMap<KeySet, ActorT>());

    /**
     * Gets the actor for this object that matches with the object's classname, realm and
     * variation within the current thread context.
     *
     * @param object the object to act upon.
     * @return an actor, or null if none can be found or created.
     */
    public ActorT getActor(Object object) {
        if (object == null) {
            throw new NullPointerException("RuleActorFactory: Cannot create an actor for a null object");
        }
        String clazz;

        // Flex Field Support:
        // For instances of FlexBeans, the rules apply to the dyanmic class name
        // which generally consists of the classname + "$Flex".  If this is a flexbean, use that instead.
        boolean isFlexClass = object instanceof FlexBean && ((FlexBean) object).getDynaClass() != null;
        if (isFlexClass) {
            clazz = ((FlexBean) object).getDynaClass().getName();
        } else {
            clazz = object.getClass().getName() != null ? object.getClass().getName() : "";
        }

        String realm = RealmRepository.instance().find(clazz) != null ? RealmRepository.instance().find(clazz) : "";
        String variation = VariationContext.getVariation() != null ? VariationContext.getVariation() : "";
        KeySet key = new KeySet(clazz, realm, variation);

        // look up in cache first, but if not exist then create from repository
        ActorT actor = cache.get(key);
        if (actor == null) {
            synchronized (syncObject) {
                actor = createActor(object);
                if (actor != null) {
                    cache.put(key, actor);
                }
            }
        }
        return actor;
    }

    /**
     * Creates an actor.
     *
     * @param object the object to act upon.
     * @return an actor.
     */
    protected abstract ActorT createActor(Object object);

}
