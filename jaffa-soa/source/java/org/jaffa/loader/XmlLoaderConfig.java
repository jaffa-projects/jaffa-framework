package org.jaffa.loader;

import org.jaffa.transaction.services.ConfigurationService;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pbagirthi on 7/14/2017.
 */
@Configuration
public class XmlLoaderConfig {


    @Bean
    public XmlLoader<TransactionManager> transactionManagerXmlLoader() {
        XmlLoader transactionManagerXmlLoader = new XmlLoader() ;
        transactionManagerXmlLoader.setManager(transactionManager());
        return  transactionManagerXmlLoader;
    }

    @Bean
    public TransactionManager transactionManager() {
        TransactionManager transactionManager = new TransactionManager();
        ConfigurationService.setTransactionManager(transactionManager);
        transactionManager.setTransactionRepository(transactionInfoRepository());
        transactionManager.setTypeInfoRepository(typeInfoRepository());
        return transactionManager;
    }

    @Bean
    public MapRepository<String, TransactionInfo> transactionInfoRepository(){
        MapRepository<String, TransactionInfo> mapRepository= new MapRepository<>();
        return mapRepository;
    }

    @Bean
    public MapRepository<String, TypeInfo> typeInfoRepository(){
        MapRepository<String, TypeInfo> mapRepository= new MapRepository<>();
        return mapRepository;
    }

    @PostConstruct
    public void loadXmls(){
        transactionManagerXmlLoader().loadXmls();
    }


}

