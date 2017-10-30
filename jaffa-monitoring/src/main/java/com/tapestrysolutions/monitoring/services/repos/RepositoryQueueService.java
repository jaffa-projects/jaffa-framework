package com.tapestrysolutions.monitoring.services.repos;

import org.jaffa.loader.IManager;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.ManagerRepositoryService;
import org.jaffa.loader.MapRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RepositoryQueueService - Provides a service mapping of the managerService with the contents of the repositories in each
 * service.
 */
public class RepositoryQueueService {

    private ManagerRepositoryService managerRepositoryService = ManagerRepositoryService.getInstance();

    /**
     * getRepositoryNames() - Returns a list of the repository names
     * @return
     */
    public List<String> getRepositoryNames() {

        Map<String, IManager> managerMap = managerRepositoryService.getManagerMap();
        List<String> repositoryNames = new ArrayList<>();
        for (Map.Entry managerEntry : managerMap.entrySet()) {
            IManager manager = (IManager) managerEntry.getValue();
            repositoryNames.addAll(manager.getRepositoryNames());
        }

        return repositoryNames;
    }

    /**
     * getRepository() - Returns a repository from the manager service by repository name.
     * @param name
     * @return
     */
    public IRepository<?> getRepository(String name) {

        Map<String, IManager> managerMap = managerRepositoryService.getManagerMap();
        for (Map.Entry managerEntry : managerMap.entrySet()) {
            IManager manager = (IManager) managerEntry.getValue();
            if (manager.getRepositoryNames().contains(name)) {
                return manager.getRepositoryByName(name);
            }
        }
        return new MapRepository();
    }

}
