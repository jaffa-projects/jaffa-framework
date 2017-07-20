package org.jaffa.loader;

import java.util.List;
import java.util.Set;

/**
 * Created by pbagirthi on 7/13/2017.
 */
public interface IRepository<K, T> {

    void register(K repositoryKey, T repository, String context);

    void unregister(K repositoryKey, String context);

    T query(K repositoryKey, List<String> contextOrder);

    Set<K> getAllKeys();

    List<T> getAllValues(List<String> contextOrder);

}
