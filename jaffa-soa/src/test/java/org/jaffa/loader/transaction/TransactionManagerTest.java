package org.jaffa.loader.transaction;

import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IRepository;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests the TransactionManager class for all the methods.
 * TransactionManager is exposed to consumers who like to register the configurations.
 */
public class TransactionManagerTest {

    TransactionManager transactionManager = new TransactionManager();
    IRepository transactionRepoMock = mock(IRepository.class);
    IRepository typeInfoRepoMock = mock(IRepository.class);
    PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

    /**
     * Initialize the values
     */
    @Before
    public void setup() {
        transactionManager.setTransactionRepository(transactionRepoMock);
        transactionManager.setTypeInfoRepository(typeInfoRepoMock);
    }

    /**
     * tests the registerXml
     */
    @Test
    public void testRegisterXml() throws Exception {

        Resource resource = pathMatchingResourcePatternResolver.getResource("classpath:META-INF/jaffa-transaction-config.xml");
        //test
        transactionManager.registerResource(resource, "cust-code", "DEF");

        //verify
        verify(transactionRepoMock).register(eq(new ContextKey("org.jaffa.soa.services.SOAEventPoller", null, "DEF", "cust-code")), Matchers.anyObject());
        verify(transactionRepoMock).register(eq(new ContextKey("org.jaffa.soa.services.SOAEventQueueMessage", null, "DEF", "cust-code")), Matchers.anyObject());
        verify(transactionRepoMock).register(eq(new ContextKey("org.jaffa.transaction.services.TransactionDependencySweeper", null, "DEF", "cust-code")), Matchers.anyObject());
        verify(transactionRepoMock).register(eq(new ContextKey("org.jaffa.transaction.tester.TestMessage", null, "DEF", "cust-code")), Matchers.anyObject());
        verify(transactionRepoMock).register(eq(new ContextKey("org.jaffa.transaction.tester.TestMessageSingleton", null, "DEF", "cust-code")), Matchers.anyObject());

        verify(typeInfoRepoMock).register(eq(new ContextKey("Internal", null, "DEF", "cust-code")), Matchers.anyObject());
        verify(typeInfoRepoMock).register(eq(new ContextKey("Poller", null, "DEF", "cust-code")), Matchers.anyObject());
        verify(typeInfoRepoMock).register(eq(new ContextKey("Event", null, "DEF", "cust-code")), Matchers.anyObject());
        verify(typeInfoRepoMock).register(eq(new ContextKey("Locks", null, "DEF", "cust-code")), Matchers.anyObject());
        verify(typeInfoRepoMock).register(eq(new ContextKey("TransactionSingleton", null, "DEF", "cust-code")), Matchers.anyObject());
    }

    /**
     * tests the unRegisterXml
     */
    @Test
    public void testUnregisterXml() throws Exception {
        //test
        transactionManager.unregisterXML("classpath:META-INF/jaffa-transaction-config.xml", "cust-code", "DEF");

        //verify
        verify(transactionRepoMock).unregister(eq(new ContextKey("org.jaffa.soa.services.SOAEventPoller", null, "DEF", "cust-code")));
        verify(transactionRepoMock).unregister(eq(new ContextKey("org.jaffa.soa.services.SOAEventQueueMessage", null, "DEF", "cust-code")));
        verify(transactionRepoMock).unregister(eq(new ContextKey("org.jaffa.transaction.services.TransactionDependencySweeper", null, "DEF", "cust-code")));
        verify(transactionRepoMock).unregister(eq(new ContextKey("org.jaffa.transaction.tester.TestMessage", null, "DEF", "cust-code")));
        verify(transactionRepoMock).unregister(eq(new ContextKey("org.jaffa.transaction.tester.TestMessageSingleton", null, "DEF", "cust-code")));

        verify(typeInfoRepoMock).unregister(eq(new ContextKey("Internal", null, "DEF", "cust-code")));
        verify(typeInfoRepoMock).unregister(eq(new ContextKey("Poller", null, "DEF", "cust-code")));
        verify(typeInfoRepoMock).unregister(eq(new ContextKey("Event", null, "DEF", "cust-code")));
        verify(typeInfoRepoMock).unregister(eq(new ContextKey("Locks", null, "DEF", "cust-code")));
        verify(typeInfoRepoMock).unregister(eq(new ContextKey("TransactionSingleton", null, "DEF", "cust-code")));
    }


    /**
     * tests the registerTransactionInfo
     */
    @Test
    public void testRegisterTransactionInfo() {
        //test
        transactionManager.registerTransactionInfo(new ContextKey("testBean", null, "DEF", "cust-code"), new TransactionInfo());

        //verify
        verify(transactionRepoMock).register(eq(new ContextKey("testBean", null, "DEF", "cust-code")), Matchers.anyObject());
    }

    /**
     * tests the registerTypeInfo
     */
    @Test
    public void testRegisterTypeInfo() {
        //initialize
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setName("typeInfoName");
        ContextKey key = new ContextKey("typeInfoName", null, "DEF", "cust-code");
        //test
        transactionManager.registerTypeInfo(key, typeInfo);

        //verify
        verify(typeInfoRepoMock).register(eq(key), Matchers.anyObject());
    }

    /**
     * tests the unregisterTransactionInfo
     */
    @Test
    public void testUnRegisterTransactionInfo() {
        //test
        transactionManager.unregisterTransactionInfo(new ContextKey("testBean", null, "DEF", "cust-code"));

        //verify
        verify(transactionRepoMock).unregister(eq(new ContextKey("testBean", null, "DEF", "cust-code")));
    }

    /**
     * tests the unregisterTypeInfo
     */
    @Test
    public void testUnRegisterTypeInfo() {
        //initialize
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setName("typeInfoName");
        ContextKey key = new ContextKey("typeInfoName", null, "DEF", "cust-code");
        //test
        transactionManager.unregisterTypeInfo(key);

        //verify
        verify(typeInfoRepoMock).unregister(eq(key));
    }

    /**
     * tests the getResourceFileName
     */
    @Test
    public void testGetxmlFileName() {
        assertEquals("jaffa-transaction-config.xml", transactionManager.getResourceFileName());
    }

    /**
     * tests the getTransactionInfo
     */
    @Test
    public void testGetTransactionInfo() {

        transactionManager.getTransactionInfo("testBean");

        //verify
        verify(transactionRepoMock).query(eq("testBean"));
    }

    /**
     * tests the getTypeInfo
     */
    @Test
    public void testGetTypeInfo() {

        transactionManager.getTypeInfo("testBeanName");

        //verify
        verify(typeInfoRepoMock).query(eq("testBeanName"));
    }

    /**
     * tests the getAllTransactionInfo
     */
    @Test
    public void testGetAllTransactionInfo() {

        transactionManager.getAllTransactionInfo();

        //verify
        verify(transactionRepoMock).getValues();
    }

    /**
     * tests the getTypeNames
     */
    @Test
    public void testGetTypeNames() {

        transactionManager.getTypeNames();

        //verify
        verify(typeInfoRepoMock).getKeyIds();
    }
}
