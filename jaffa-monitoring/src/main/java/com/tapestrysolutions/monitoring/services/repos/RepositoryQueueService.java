package com.tapestrysolutions.monitoring.services.repos;

import org.jaffa.loader.IManager;
import org.jaffa.loader.ResourceLoader;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rcastellow on 9/15/2017.
 */
public class RepositoryQueueService implements PropertyChangeListener {

    private static volatile RepositoryQueueService repositoryQueueServiceInstance = null;

    public RepositoryQueueService(ResourceLoader<IManager> resourceLoader) {
        resourceLoader.addPropertyChangeListener(this);
    }

    private RepositoryQueueService() {}

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

    public static List<String> getRepositoryNames() {
        List<String> testList = new ArrayList<>();
        testList.add("Hello, world");
        return testList;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("I have successfully registered an event.");
    }
}
