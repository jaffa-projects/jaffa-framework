package org.jaffa.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads the Xml Config files to the Repository.
 */
public class XmlLoader<T extends IManager> {

    private T manager ;

    @Autowired
    @Qualifier("defaultContexts")
    private ArrayList<String> defaultContexts = new ArrayList<>();

    /**
     * gets the Manager
     * @return
     */
    public T getManager() {
        return manager;
    }

    public void setManager(T manager) {
        this.manager = manager;
    }

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

    public List<String> getDefaultContexts() {
        return defaultContexts;
    }

    public void setDefaultContexts(ArrayList<String> defaultContexts) {
        this.defaultContexts = defaultContexts;
    }
}
