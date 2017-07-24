package org.jaffa.transaction.services;

import org.jaffa.loader.XmlLoaderConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;

/**
 * Created by pbagirthi on 7/24/2017.
 */
@Configuration
@Import(XmlLoaderConfig.class)
public class TestConfigLoad {

    @Bean
    public ArrayList<String> contextOrder(){
        ArrayList<String> contextList = new ArrayList<>();
        contextList.add("SAF-CUST");
        contextList.add("supply-blueprint");
        contextList.add("default");
        return contextList;
    }

    @Bean
    public ArrayList<String> defaultContexts(){
        ArrayList<String> defaultContexts = new ArrayList<>();
        defaultContexts.add("SAF-CUST");
        defaultContexts.add("supply-blueprint");
        defaultContexts.add("maintenance-blueprint");
        defaultContexts.add("default");
        return defaultContexts;
    }
}
