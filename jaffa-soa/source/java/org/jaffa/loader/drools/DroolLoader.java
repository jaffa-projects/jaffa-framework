package org.jaffa.loader.drools;

import org.jaffa.util.ContextHelper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.PostConstruct;

/**
 * Created by pbagirthi on 8/7/2017.
 */
public class DroolLoader {

    private DroolManager manager ;

    /**
     * gets the Manager from the DroolLoader
     * @return Manager Object identified ny T
     */
    public DroolManager getManager() {
        return manager;
    }

    /**
     * sets the manager on the DroolLoader
     * @param manager Object identified by T
     */
    public void setManager(DroolManager manager) {
        this.manager = manager;
    }

    /**
     * loads all the Xml files with xml file name in the manager from all the jars
     * where package contains META-INF/*
     */
    @PostConstruct
    public void loadDrools() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:META-INF/**/*.drl");
            if (resources != null) {
                for (Resource resource : resources) {
                    if (resource == null) {
                        continue;
                    }
                    manager.registerDrool(resource, ContextHelper.getVariationSalience(resource.getURI().toString()));
                }
            }
            manager.createAgents();
        }catch(Exception w){
            w.printStackTrace();
            throw new RuntimeException(w.getCause());

        }
    }

}
