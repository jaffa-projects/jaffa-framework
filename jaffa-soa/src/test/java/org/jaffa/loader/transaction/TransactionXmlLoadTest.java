package org.jaffa.loader.transaction;

import org.jaffa.loader.CoreLoaderConfig;
import org.jaffa.loader.SoaLoaderConfig;
import org.jaffa.loader.soa.SoaEventManager;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Tests the Transaction Xml Loader.
 */
public class TransactionXmlLoadTest {

    private static AnnotationConfigApplicationContext xmlLoaderConfig =
            new AnnotationConfigApplicationContext(CoreLoaderConfig.class, SoaLoaderConfig.class);

    @Test
    public void testTransactionXmlLoad(){

        TransactionManager transactionManager = xmlLoaderConfig.getBean(TransactionManager.class);
        assertNotNull(transactionManager.getAllTransactionInfo());
        assertEquals(5, transactionManager.getAllTransactionInfo().length);
        assertNotNull(transactionManager.getTypeNames());
        assertEquals(5, transactionManager.getTypeNames().length);

        assertNotNull(transactionManager.getTransactionInfo("org.jaffa.transaction.tester.TestMessageSingleton"));
        assertNotNull(transactionManager.getTransactionInfo("org.jaffa.soa.services.SOAEventQueueMessage"));
        assertNotNull(transactionManager.getTransactionInfo("org.jaffa.transaction.tester.TestMessage"));

        assertNotNull(transactionManager.getTypeInfo("Internal"));
        assertNotNull(transactionManager.getTypeInfo("Poller"));
        assertNotNull(transactionManager.getTypeInfo("Locks"));
    }
    
    @Test
    public void testSoaEventsXmlLoad(){
        SoaEventManager soaEventManager = xmlLoaderConfig.getBean(SoaEventManager.class);
        assertNotNull(soaEventManager.getAllSoaEventInfo(null));
    }

    /**
     * Tests the ability of this IManager to retrieve a repository when given its String name
     */
    @Test
    public void testGetRepositoryByName() throws Exception {
        TransactionManager transactionManager = xmlLoaderConfig.getBean(TransactionManager.class);

        String repo = "TransactionInfo";
        assertEquals(repo, transactionManager.getRepositoryByName(repo).getName());
        repo = "TypeInfo";
        assertEquals(repo, transactionManager.getRepositoryByName(repo).getName());
    }

    /**
     * Test the retrieval of the list of repositories managed by this class
     */
    @Test
    public void testGetRepositoryNames() {
        TransactionManager transactionManager = xmlLoaderConfig.getBean(TransactionManager.class);
        for (Object repositoryName : transactionManager.getRepositoryNames()) {
            assertEquals(repositoryName, transactionManager.getRepositoryByName((String) repositoryName).getName());
        }
    }
}
