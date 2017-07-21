package org.jaffa.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads the Xml Config files and registers them to the Repository.
 */
public class XmlLoader<T extends IManager> {

    private T manager ;

    @Autowired
    @Qualifier("defaultContexts")
    private ArrayList<String> defaultContexts = new ArrayList<>();

    /**
     * gets the Manager from the XmlLoader
     * @return Manager Object identified ny T
     */
    public T getManager() {
        return manager;
    }

    /**
     * sets the manager on the XmlLoader
     * @param manager Object identified by T
     */
    public void setManager(T manager) {
        this.manager = manager;
    }

    /**
     * loads all the Xml files with xml file name in the manager from all the jars
     * where package contains META-INF/*
      */
    public void loadXmls() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:META-INF/" + manager.getXmlFileName());
            if (resources != null) {
                for (Resource resource : resources) {
                    if (resource == null) {
                        continue;
                    }
                    manager.registerXML(resource, getContext(resource.getURL().getPath()));
                }
            }
        }catch(Exception w){
            throw new RuntimeException(w.getCause());
        }
    }

    /**
     * gets the context associated with the path
     * @param path where the resource is located
     * @return context associated with the path
     */
    public String getContext(String path){
        String context = "default";

        if(defaultContexts != null) {
            for (String defaultContext : defaultContexts) {
                if (path.contains(defaultContext)) {
                    context = defaultContext;
                    break;
                }
            }
        }

        return context;
    }

    /**
     * gets the defaultContexts for configuration
     * @return List of String
     */
    public List<String> getDefaultContexts() {
        return defaultContexts;
    }

    /**
     * sets the defaultContexts on XmlLoader
     * @param defaultContexts List containing defaultContexts
     */
    public void setDefaultContexts(ArrayList<String> defaultContexts) {
        this.defaultContexts = defaultContexts;
    }
}
