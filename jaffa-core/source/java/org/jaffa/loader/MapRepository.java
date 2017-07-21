package org.jaffa.loader;

import java.util.*;

/**
 * Java Map Implementation of IRepository
 */
public class MapRepository<K, T> implements IRepository<K, T> {

    Map<K, Map<String,T>> repositoryMap = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(K repositoryKey, T repository, String context) {
        context = (context == null || context.length() ==0) ? "default" : context;
        Map<String, T> infoMap = repositoryMap.get(repositoryKey);
        if(infoMap == null){
            infoMap = new HashMap<>();
        }
        infoMap.put(context, repository);
        repositoryMap.put(repositoryKey , infoMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregister(K repositoryKey, String context) {
        context = (context == null || context.length() ==0) ? "default" : context;
        Map<String, T> infoMap = repositoryMap.get(repositoryKey);
        if(infoMap != null){
            infoMap.remove(context);
        }
        if(infoMap != null && infoMap.isEmpty()){
            repositoryMap.remove(repositoryKey);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T query(K repositoryKey, List<String> contextOrder) {
        if(contextOrder == null || contextOrder.isEmpty()){
            contextOrder = new ArrayList<>();
            contextOrder.add("default");
        }

        Map<String, T> infoMap = repositoryMap.get(repositoryKey);
        if(infoMap != null) {
            for (String context : contextOrder) {
                if(infoMap.get(context) != null)
                    return infoMap.get(context);
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> getAllKeys() {
        return repositoryMap.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAllValues(List<String> contextOrder) {
        List<T> repositoryInfos = new ArrayList<>();
        for(K key:repositoryMap.keySet()){
            repositoryInfos.add(query(key, contextOrder));
        }
        return repositoryInfos;
    }
}
