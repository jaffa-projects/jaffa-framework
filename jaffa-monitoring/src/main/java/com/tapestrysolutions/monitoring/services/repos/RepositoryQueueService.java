package com.tapestrysolutions.monitoring.services.repos;

/**
 * Created by rcastellow on 9/15/2017.
 */
public class RepositoryQueueService {

    private static volatile RepositoryQueueService repositoryQueueServiceInstance = null;

    private RepositoryQueueService() {
    }

    public static RepositoryQueueService getInstance() {
        if (repositoryQueueServiceInstance == null) {
            synchronized (RepositoryQueueService.class) {
                if (repositoryQueueServiceInstance == null) {
                    repositoryQueueServiceInstance = new RepositoryQueueService();
                }
            }
        }
        return repositoryQueueServiceInstance;
    }



}
