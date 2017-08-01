package org.jaffa.transaction.services;

import org.jaffa.loader.IRepository;
import org.jaffa.loader.transaction.TransactionManager;
import org.jaffa.transaction.services.configdomain.TransactionInfo;
import org.jaffa.transaction.services.configdomain.TypeInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
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
        transactionManager.registerXML(resource, "jwl");

        //verify
        verify(transactionRepoMock).register(eq("org.jaffa.soa.services.SOAEventPoller"), Matchers.anyObject(), eq("jwl"));
        verify(transactionRepoMock).register(eq("org.jaffa.soa.services.SOAEventQueueMessage"), Matchers.anyObject(), eq("jwl"));
        verify(transactionRepoMock).register(eq("org.jaffa.transaction.services.TransactionDependencySweeper"), Matchers.anyObject(), eq("jwl"));
        verify(transactionRepoMock).register(eq("org.jaffa.transaction.tester.TestMessage"), Matchers.anyObject(), eq("jwl"));
        verify(transactionRepoMock).register(eq("org.jaffa.transaction.tester.TestMessageSingleton"), Matchers.anyObject(), eq("jwl"));

        verify(typeInfoRepoMock).register(eq("Internal"), Matchers.anyObject(), eq("jwl"));
        verify(typeInfoRepoMock).register(eq("Poller"), Matchers.anyObject(), eq("jwl"));
        verify(typeInfoRepoMock).register(eq("Event"), Matchers.anyObject(), eq("jwl"));
        verify(typeInfoRepoMock).register(eq("Locks"), Matchers.anyObject(), eq("jwl"));
        verify(typeInfoRepoMock).register(eq("TransactionSingleton"), Matchers.anyObject(), eq("jwl"));
    }

    /**
     * tests the unRegisterXml
     */
    @Test
    public void testUnregisterXml() throws Exception {
        //test
        transactionManager.unregisterXML("classpath:META-INF/jaffa-transaction-config.xml", "jwl");

        //verify
        verify(transactionRepoMock).unregister(eq("org.jaffa.soa.services.SOAEventPoller"), eq("jwl"));
        verify(transactionRepoMock).unregister(eq("org.jaffa.soa.services.SOAEventQueueMessage"), eq("jwl"));
        verify(transactionRepoMock).unregister(eq("org.jaffa.transaction.services.TransactionDependencySweeper"), eq("jwl"));
        verify(transactionRepoMock).unregister(eq("org.jaffa.transaction.tester.TestMessage"), eq("jwl"));
        verify(transactionRepoMock).unregister(eq("org.jaffa.transaction.tester.TestMessageSingleton"), eq("jwl"));

        verify(typeInfoRepoMock).unregister(eq("Internal"), eq("jwl"));
        verify(typeInfoRepoMock).unregister(eq("Poller"), eq("jwl"));
        verify(typeInfoRepoMock).unregister(eq("Event"), eq("jwl"));
        verify(typeInfoRepoMock).unregister(eq("Locks"), eq("jwl"));
        verify(typeInfoRepoMock).unregister(eq("TransactionSingleton"), eq("jwl"));
    }


    /**
     * tests the registerTransactionInfo
     */
    @Test
    public void testRegisterTransactionInfo() {
        //test
        transactionManager.registerTransactionInfo("testBean", new TransactionInfo(), "jwl");

        //verify
        verify(transactionRepoMock).register(eq("testBean"), Matchers.anyObject(), eq("jwl"));
    }

    /**
     * tests the registerTypeInfo
     */
    @Test
    public void testRegisterTypeInfo() {
        //initialize
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setName("typeInfoName");

        //test
        transactionManager.registerTypeInfo(typeInfo, "jwl");

        //verify
        verify(typeInfoRepoMock).register(eq("typeInfoName"), Matchers.anyObject(), eq("jwl"));
    }

    /**
     * tests the unregisterTransactionInfo
     */
    @Test
    public void testUnRegisterTransactionInfo() {
        //test
        transactionManager.unregisterTransactionInfo("testBean", "jwl");

        //verify
        verify(transactionRepoMock).unregister(eq("testBean"), eq("jwl"));
    }

    /**
     * tests the unregisterTypeInfo
     */
    @Test
    public void testUnRegisterTypeInfo() {
        //initialize
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setName("typeInfoName");

        //test
        transactionManager.unregisterTypeInfo("typeInfoName", "jwl");

        //verify
        verify(typeInfoRepoMock).unregister(eq("typeInfoName"), eq("jwl"));
    }

    /**
     * tests the getXmlFileName
     */
    @Test
    public void testGetxmlFileName() {
        assertEquals("jaffa-transaction-config.xml", transactionManager.getXmlFileName());
    }

    /**
     * tests the getTransactionInfo
     */
    @Test
    public void testGetTransactionInfo() {

        transactionManager.getTransactionInfo("testBean", null);

        //verify
        verify(transactionRepoMock).query(eq("testBean"), eq((List<?>) null));
    }

    /**
     * tests the getTypeInfo
     */
    @Test
    public void testGetTypeInfo() {

        transactionManager.getTypeInfo("testBeanName", null);

        //verify
        verify(typeInfoRepoMock).query(eq("testBeanName"), eq((List<?>) null));
    }

    /**
     * tests the getAllTransactionInfo
     */
    @Test
    public void testGetAllTransactionInfo() {

        transactionManager.getAllTransactionInfo(null);

        //verify
        verify(transactionRepoMock).getAllValues(eq((List<?>) null));
    }

    /**
     * tests the getTypeNames
     */
    @Test
    public void testGetTypeNames() {

        transactionManager.getTypeNames();

        //verify
        verify(typeInfoRepoMock).getAllKeys();
    }
}
