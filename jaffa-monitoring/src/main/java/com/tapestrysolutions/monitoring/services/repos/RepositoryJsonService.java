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
package com.tapestrysolutions.monitoring.services.repos;

import com.google.gson.Gson;
import org.jaffa.loader.*;

import javax.ws.rs.NotFoundException;
import java.util.*;

/**
 * RepositoryJsonService - This class will call the Queue service and serializes the response.
 * @author Matthew Wayles
 * @version 1.0
 */
public class RepositoryJsonService implements IRepositoryJsonService {

    /**
     * Retrieve the ManagerRepositoryService singleton instance
     */
    private Gson gson = new Gson();
    private ManagerRepositoryService managerRepositoryService = ManagerRepositoryService.getInstance();

    /**
     * getRepositoryNames() - Returns a JSON list of all currently registered repositories that have been loaded
     * from the ResourceLoader.
     * @return String JSON value of the repository list
     */
    @Override
    public String getRepositoryNames() {
        Map<String, IManager> managerMap = managerRepositoryService.getManagerMap();
        ArrayList<Set> repositoryNames = new ArrayList<>();
        for (Map.Entry managerEntry : managerMap.entrySet()) {
            IManager manager = (IManager) managerEntry.getValue();
            Set names = manager.getRepositoryNames();
            repositoryNames.addAll(names);
        }
        String json = gson.toJson(repositoryNames);
        return json;
    }

    /**
     * getRepository() - Returns the serialized repository in JSON objects representing the named repository.
     * @param name  The name of the repository to retrieve data from
     * @return String repository data in JSON format
     */
    @Override
    public String getRepository(String name) {
        Map repository = new HashMap<>();
        Map<String, IManager> managerMap = managerRepositoryService.getManagerMap();
        for (Map.Entry managerEntry : managerMap.entrySet()) {
            IManager manager = (IManager) managerEntry.getValue();
            if (manager.getRepositoryNames().contains(name)) {
               repository = createRepositoryMap(name, repository, manager);
            }
        }
        if (repository.isEmpty()) {
            throw new NotFoundException();
        }
        String json = gson.toJson(repository);
        return json;
    }

    /**
     * createRepositoryMap() - Add values to local repository map for access by web services
     * @param name  The repository name
     * @param repository    The local repository to be populated
     * @param manager   The manager hosting the requested repository
     */
    private Map createRepositoryMap(String name, Map repository, IManager manager) {
        for (Object repositoryKey : manager.getRepositoryByName(name).getAllKeys()) {
            ContextKey contextKey = (ContextKey) repositoryKey;
            repository.put(contextKey.getId(), manager.getRepositoryByName(name).query(contextKey.getId()));
        }
        return repository;
    }

    /**
     * getRepositoryValue() - Returns the serialized value of the provided query key from the named repository.
     * @param name  The name of the repository to retrieve the data from
     * @param id    The ID of the key whose value is to be retrieved
     * @return  The corresponding map value
     */
    @Override
    public String getRepositoryValue(String name, String id) {
        Object queryResponse = null;
        Map<String, IManager> managerMap = managerRepositoryService.getManagerMap();
        for (Map.Entry managerEntry : managerMap.entrySet()) {
            IManager manager = (IManager) managerEntry.getValue();
            if (manager.getRepositoryNames().contains(name)) {
                ContextKey key = manager.getRepositoryByName(name).findKey(id);
                if (key != null) {
                    queryResponse = manager.getRepositoryByName(name).query(id);
                }
                break;
            }
        }
        if (queryResponse == null) {
            throw new NotFoundException();
        }
        String json = gson.toJson(queryResponse);
        return json;
    }
}