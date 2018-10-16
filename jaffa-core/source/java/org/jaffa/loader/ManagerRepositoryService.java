package org.jaffa.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * ManagerRepositoryService - Singleton service to collect all repository managers in the application
 */
public class ManagerRepositoryService {

    /**
     * The singleton ManagerRepositoryService instance
     */
    private static volatile ManagerRepositoryService managerRepositoryService = null;

    /**
     * HashMap to store repositories. The key is the repository name, and the value is
     * the repository instance
     */
    private Map<String, IManager> managerMap = new HashMap<>();

    /**
     * Default constructor for the ManagerRepositoryService class
     */
    private ManagerRepositoryService() {
    }

    /**
     * Public method to retrieve the ManagerRepositoryService instance
     * @return The ManagerRepositoryService instance
     */
    public static ManagerRepositoryService getInstance() {
        if (managerRepositoryService == null) {
            synchronized (ManagerRepositoryService.class) {
                if (managerRepositoryService == null) {
                    managerRepositoryService = new ManagerRepositoryService();
                }
            }
        }
        return managerRepositoryService;
    }

    /**
     * Add managers to the ManagerRepositoryService HashMap
     * @param className The class name of the manager to be added
     * @param manager   The instance of the manager to be added
     */
    public void add(String className, IManager manager) {
        managerMap.put(className, manager);
    }

    /**
     * Retrieve the manager HashMap
     * @return  The manager HashMap
     */
    public Map<String, IManager> getManagerMap() {
        return managerMap;
    }

}
