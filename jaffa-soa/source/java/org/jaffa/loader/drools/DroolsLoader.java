package org.jaffa.loader.drools;

import org.jaffa.util.ContextHelper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.PostConstruct;

/**
 * A Loader class for handling all the Drools Files
 * A Drool file is any file that ends with .drl extension.
 */
public class DroolsLoader {

    private DroolsManager manager ;

    /**
     * gets the Manager from the DroolsLoader
     * @return Manager Object identified ny T
     */
    public DroolsManager getManager() {
        return manager;
    }

    /**
     * sets the manager on the DroolsLoader
     * @param manager Object identified by T
     *
     */
    public void setManager(DroolsManager manager) {
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
            Resource[] resources = resolver.getResources("classpath*:META-INF/rules/**/*.drl");
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
