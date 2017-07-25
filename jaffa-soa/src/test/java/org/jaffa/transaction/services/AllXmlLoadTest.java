package org.jaffa.transaction.services;

import org.jaffa.loader.soa.SoaEventManager;
import org.jaffa.loader.transaction.TransactionManager;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;


/**
 * Created by pbagirthi on 7/14/2017.
 */
public class AllXmlLoadTest {

    private static AnnotationConfigApplicationContext xmlLoaderConfig = new AnnotationConfigApplicationContext(TestConfigLoad.class);

    @Test
    public void testTransactionXmlLoad(){

        TransactionManager transactionManager = xmlLoaderConfig.getBean(TransactionManager.class);
        assertNotNull(transactionManager.getTransactionInfo("org.jaffa.transaction.tester.TestMessageSingleton", null));
    }
    
    @Test
    public void testSoaEventsXmlLoad(){
        SoaEventManager soaEventManager = xmlLoaderConfig.getBean(SoaEventManager.class);
        assertNotNull(soaEventManager.getAllSoaEventInfo(null));
    }    
}
