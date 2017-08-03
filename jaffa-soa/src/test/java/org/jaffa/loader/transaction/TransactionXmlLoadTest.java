package org.jaffa.loader.transaction;

import org.jaffa.loader.XmlLoaderConfig;
import org.jaffa.loader.soa.SoaEventManager;
import org.jaffa.loader.transaction.TransactionManager;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Tests the Transaction Xml Loader.
 */
public class TransactionXmlLoadTest {

    private static AnnotationConfigApplicationContext xmlLoaderConfig = new AnnotationConfigApplicationContext(XmlLoaderConfig.class);

    @Test
    public void testTransactionXmlLoad(){

        TransactionManager transactionManager = xmlLoaderConfig.getBean(TransactionManager.class);
        assertNotNull(transactionManager.getAllTransactionInfo(null));
        assertEquals(5, transactionManager.getAllTransactionInfo(null).length);
        assertNotNull(transactionManager.getTypeNames());
        assertEquals(5, transactionManager.getTypeNames().length);

        assertNotNull(transactionManager.getTransactionInfo("org.jaffa.transaction.tester.TestMessageSingleton", null));
        assertNotNull(transactionManager.getTransactionInfo("org.jaffa.soa.services.SOAEventQueueMessage", null));
        assertNotNull(transactionManager.getTransactionInfo("org.jaffa.transaction.tester.TestMessage", null));

        assertNotNull(transactionManager.getTypeInfo("Internal", null));
        assertNotNull(transactionManager.getTypeInfo("Poller", null));
        assertNotNull(transactionManager.getTypeInfo("Locks", null));
    }
    
    @Test
    public void testSoaEventsXmlLoad(){
        SoaEventManager soaEventManager = xmlLoaderConfig.getBean(SoaEventManager.class);
        assertNotNull(soaEventManager.getAllSoaEventInfo(null));
    }    
}
