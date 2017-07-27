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

import java.util.*;

/**
 * Java Map Implementation of IRepository
 */
public class MapRepository<K, T> implements IRepository<K, T> {

    Map<K, Map<String, T>> repositoryMap = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(K repositoryKey, T repository, String context) {
        context = (context == null || context.length() == 0) ? "default" : context;
        Map<String, T> infoMap = repositoryMap.get(repositoryKey);
        if (infoMap == null) {
            infoMap = new HashMap<>();
        }
        infoMap.put(context, repository);
        repositoryMap.put(repositoryKey, infoMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregister(K repositoryKey, String context) {
        context = (context == null || context.length() == 0) ? "default" : context;
        Map<String, T> infoMap = repositoryMap.get(repositoryKey);
        if (infoMap != null) {
            infoMap.remove(context);
        }
        if (infoMap != null && infoMap.isEmpty()) {
            repositoryMap.remove(repositoryKey);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T query(K repositoryKey, List<String> contextOrder) {
        if (contextOrder == null || contextOrder.isEmpty()) {
            contextOrder = new ArrayList<>();
            contextOrder.add("default");
        }

        Map<String, T> infoMap = repositoryMap.get(repositoryKey);
        if (infoMap != null) {
            for (String context : contextOrder) {
                if (infoMap.get(context) != null)
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
        for (K key : repositoryMap.keySet()) {
            T value = query(key, contextOrder);
            if (value != null)
                repositoryInfos.add(value);
        }
        return repositoryInfos;
    }
}
