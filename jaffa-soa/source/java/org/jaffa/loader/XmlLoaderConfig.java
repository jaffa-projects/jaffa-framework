package org.jaffa.loader;

import org.jaffa.transaction.services.ConfigurationService;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Contains all the Beans related to the Loader Architecture for the Jaffa-SOA
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

    private MapRepository<String, TransactionInfo> transactionInfoRepository(){
        MapRepository<String, TransactionInfo> mapRepository= new MapRepository<>();
        return mapRepository;
    }

    private MapRepository<String, TypeInfo> typeInfoRepository(){
        MapRepository<String, TypeInfo> mapRepository= new MapRepository<>();
        return mapRepository;
    }

    @PostConstruct
    public void loadXmls(){
        transactionManagerXmlLoader().loadXmls();
    }

}

