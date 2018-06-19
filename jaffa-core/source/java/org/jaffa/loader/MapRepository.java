/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package org.jaffa.loader;

import org.jaffa.security.VariationContext;

import java.util.*;

/**
 * Java Map Implementation of IRepository
 */
public class MapRepository<T> implements IRepository<T> {

    /**
     * Repository Map holds ContextKey and associated Repository Element
     */
    private Map<ContextKey, T> repositoryMap = new TreeMap<>();

    /**
     * ContextKeyCache is the cache of Repository key(id) element and its associated Contexts
     */
    private Map<String, TreeSet<ContextKey>> contextKeyCache = new HashMap<>();

    /**
     * Cache for Repositories
     */
    private Map<String, Map<String, T>> repositoryCache = new HashMap<>();


    /**
     * Cache for Repositories by Variation
     */
    private Map<String, Map<String, T>> repositoryCacheByVariation = new HashMap<>();

    /**
     * Lock Object
     */
    private final Object lockObject1 = new Object();
    private final Object lockObject2 = new Object();

    /**
     * The name of the repository
     */
    private String repositoryName;

    /**
     * Create a MapRepository object and set its name value
     * @param name
     */
    public MapRepository(String name) {
        repositoryName = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void register(ContextKey repositoryKey, T repositoryElement) {
        repositoryMap.put(repositoryKey, repositoryElement);

        addToContextKeyCache(repositoryKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void unregister(ContextKey contextKey) {
        repositoryMap.remove(contextKey);

        removeFromContextKeyCache(contextKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized T query(ContextKey contextKey) {
        return repositoryMap.get(contextKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T query(String id) {
        if(id!=null) {
            TreeSet<ContextKey> contextKeysForId = contextKeyCache.get(id);
            if (contextKeysForId!=null && contextKeysForId.size() > 0) {
                for (ContextKey contextKey : contextKeysForId) {
                    if ((contextKey.getVariation() != null && contextKey.getVariation().equals(VariationContext.getVariation()))
                            || contextKey.getVariation().equals(VariationContext.NULL_VARIATION)) {
                        return repositoryMap.get(contextKey);
                    }
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T queryByVariation(String id, String variation) {
        if(id!=null) {
            TreeSet<ContextKey> contextKeysForId = contextKeyCache.get(id);
            if (contextKeysForId!=null && contextKeysForId.size() > 0) {
                for (ContextKey contextKey : contextKeysForId) {
                    if ((contextKey.getVariation() != null && contextKey.getVariation().equals(variation))) {
                        return repositoryMap.get(contextKey);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Retrieve the name of the repository
     * @return  The name of the repository
     */
    @Override
    public String getName() {
        return repositoryName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContextKey findKey(String id){
        if(id!=null) {
            TreeSet<ContextKey> contextKeysForId = contextKeyCache.get(id);
            if (contextKeysForId!=null) {
                for (ContextKey contextKey : contextKeysForId) {
                    if ((contextKey.getVariation() != null && contextKey.getVariation().equals(VariationContext.getVariation()))
                            || contextKey.getVariation().equals(VariationContext.NULL_VARIATION)) {
                        return contextKey;
                    }

                }
            }
        }
        return null;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ContextKey> getAllKeys() {
        return repositoryMap.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getAllKeyIds() {
        Set<ContextKey> contextKeys = repositoryMap.keySet();
        Set<String> ids = new HashSet<>();
        for(ContextKey contextKey : contextKeys){
            ids.add(contextKey.getId());
        }
        return ids;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAllValues() { return new ArrayList(repositoryMap.values());}

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ContextKey> getKeys() {
        Set<ContextKey> contextKeys = new HashSet<>();
        for (String id : getKeyIds()) {
            contextKeys.add(findKey(id));
        }
        return contextKeys;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getKeyIds() {
        return getMyRepository().keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getValues() {
        return new ArrayList<>(getMyRepository().values());
    }


    /**
     * Adds repositoryKey to contextKeyCache
     * @param repositoryKey The ContextKey to be added to ContextKeyCache
     */
    private void addToContextKeyCache(ContextKey repositoryKey){
        TreeSet<ContextKey> contextKeyCacheValue = contextKeyCache.get(repositoryKey.getId());
        if(contextKeyCacheValue == null){
            contextKeyCacheValue = new TreeSet<>(Collections.<ContextKey>reverseOrder());
        }
        contextKeyCacheValue.add(repositoryKey);
        contextKeyCache.put(repositoryKey.getId(), contextKeyCacheValue);

        //Update Cached Repositories
        synchronized (lockObject1){
            if(repositoryCache!=null && repositoryCache.get(VariationContext.getVariation())!=null){
                Map<String, T> myRepository = repositoryCache.get(VariationContext.getVariation());
                myRepository.put(repositoryKey.getId(), query(repositoryKey.getId()));
            }
        }
        synchronized (lockObject2){
            if(repositoryCacheByVariation!=null && repositoryCacheByVariation.get(VariationContext.getVariation())!=null){
                Map<String, T> myRepository = repositoryCacheByVariation.get(VariationContext.getVariation());
                myRepository.put(repositoryKey.getId(), queryByVariation(repositoryKey.getId(), VariationContext.getVariation()));
            }
        }
    }

    /**
     * Removes repositoryKey from contextKeyCache
     * @param repositoryKey The ContextKey to be removed from ContextKeyCache
     */
    private void removeFromContextKeyCache(ContextKey repositoryKey) {
        if (contextKeyCache.get(repositoryKey.getId()) != null) {
            if (contextKeyCache.get(repositoryKey.getId()).size() > 1) {
                contextKeyCache.get(repositoryKey.getId()).remove(repositoryKey);
            } else {
                contextKeyCache.remove(repositoryKey.getId());
            }
        }

        //Update Cached Repositories
        synchronized (lockObject1){
            if(repositoryCache!=null && repositoryCache.get(VariationContext.getVariation())!=null){
                Map<String, T> myRepository = repositoryCache.get(VariationContext.getVariation());
                myRepository.remove(repositoryKey.getId());
            }
        }
        synchronized (lockObject2){
            if(repositoryCacheByVariation!=null && repositoryCacheByVariation.get(VariationContext.getVariation())!=null){
                Map<String, T> myRepository = repositoryCacheByVariation.get(VariationContext.getVariation());
                myRepository.remove(repositoryKey.getId());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, T> getMyRepository(){
        if(repositoryCache.get(VariationContext.getVariation())!=null){
            return repositoryCache.get(VariationContext.getVariation());
        }else {
            synchronized (lockObject1) {
                Map<String, T> myRepository = new HashMap<>();
                Set<String> repoKeys = contextKeyCache.keySet();
                for (String repoKey : repoKeys) {
                    T value = query(repoKey);
                    if (value != null) {
                        myRepository.put(repoKey, value);
                    }
                }
                if(myRepository.size() > 0) {
                    repositoryCache.put(VariationContext.getVariation(), myRepository);
                }
                return myRepository;
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, T> getRepositoryByVariation(String variation){
        if(repositoryCacheByVariation.get(VariationContext.getVariation())!=null){
            return repositoryCacheByVariation.get(VariationContext.getVariation());
        }else {
            synchronized (lockObject2) {
                Map<String, T> variationRepository = new HashMap<>();
                Set<String> repoKeys = contextKeyCache.keySet();
                for (String repoKey : repoKeys) {
                    T value = queryByVariation(repoKey, variation);
                    if (value != null) {
                        variationRepository.put(repoKey, value);
                    }
                }
                if(variationRepository.size() > 0) {
                    repositoryCacheByVariation.put(VariationContext.getVariation(), variationRepository);
                }
                return variationRepository;
            }
        }
    }
}
