package com.tapestrysolutions.monitoring.services.repos;

import com.google.gson.Gson;

/**
 * RepositoryJsonService - This class will call the Queue service and serializes the response.
 */
public class RepositoryJsonService implements IRepositoryJsonService {

    RepositoryQueueService repositoryQueueService = new RepositoryQueueService();

    /**
     * getRepositoryNames() - Returns a JSON list of all currently registered repositories that have been loaded
     * from the ResourceLoader.
     * @return String
     */
    @Override
    public String getRepositoryNames() {
        Gson gson = new Gson();
        String json = gson.toJson(repositoryQueueService.getRepositoryNames());
        return json;
    }

    /**
     * getRespository() - Returns the serialized repository in JSON objects representing the named repository.
     * @param name
     * @return String
     */
    @Override
    public String getRepository(String name) {
        Gson gson = new Gson();
        String json = gson.toJson(repositoryQueueService.getRepository(name));
        return json;
    }
}