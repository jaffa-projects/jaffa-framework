package org.jaffa.loader;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ManagerRepositoryService {

    private static volatile ManagerRepositoryService managerRepositoryService = null;

    private Map<String, IManager> managerMap = new HashMap<>();

    private ManagerRepositoryService() {
    }

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


    public void add(String className, IManager manager) {
        managerMap.put(className, manager);
    }

    public Map<String, IManager> getManagerMap() {
        return managerMap;
    }

}
