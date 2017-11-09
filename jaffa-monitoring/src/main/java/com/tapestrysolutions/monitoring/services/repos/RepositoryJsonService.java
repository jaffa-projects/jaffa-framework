package com.tapestrysolutions.monitoring.services.repos;

import com.google.gson.Gson;
import org.jaffa.loader.*;

import javax.ws.rs.NotFoundException;
import java.util.*;

/**
 * RepositoryJsonService - This class will call the Queue service and serializes the response.
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