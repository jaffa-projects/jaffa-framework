package com.tapestrysolutions.monitoring.services.repos;

import org.springframework.stereotype.Service;

import javax.json.Json;

/**
 * RepositoryJsonService
 */
@Service("repositoryJsonService")
public class RepositoryJsonService implements IRepositoryJsonService {

    RepositoryQueueService repositoryQueueService = RepositoryQueueService.getInstance();

    @Override
    public String getRepositoryNames() {

//        Gson gson = new Gson();
//        String json = gson.toJson(repositoryQueueService.getRepositoryNames());
        return "";

    }

    @Override
    public String getRepository(String name) {

//        repositoryQueueService.getRepository(name);

        return Json.createObjectBuilder()
                .add("name", "X")
                .add("name2", "Y")
                .add("name3", "Z")
                .build().toString();
    }
}