package org.jaffa.loader;

import java.util.List;
import java.util.Set;

/**
 * Interface for storing values in Repository.
 */
public interface IRepository<K, T> {

    /**
     * registers with given key and value in to the repository with the context provided.
     * assumes "default" when context is not provided.
     * @param repositoryKey which is the key of repository.
     * @param value to be stored in repository.
     * @param context with which repository is associated.
     */
    void register(K repositoryKey, T value, String context);

    /**
     * unregisters the given key from the repository with the context provided.
     * assumes "default" when the context is not provided.
     * @param repositoryKey is the key of repository.
     * @param context with which repository is associated.
     */
    void unregister(K repositoryKey, String context);

    /**
     * queries repository with the given key and the contextOrder provided
     * assumes defaultContextOrder from configuration when contextOrder is not provided
     * @param repositoryKey is the key of repository.
     * @param contextOrder order of the contexts to be searched
     * @return repository value
     */
    T query(K repositoryKey, List<String> contextOrder);

    /**
     * retrieves all the keys in the repository
     * @return Set of all keys
     */
    Set<K> getAllKeys();

    /**
     * retrives all the values in the repository based on the contextOrder provided
     * assumes defaultContextOrder from the configuration when contextOrder is not provided
     * @param contextOrder order of the contexts to be searched
     * @return List of all values
     */
    List<T> getAllValues(List<String> contextOrder);

}
