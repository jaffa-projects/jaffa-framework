package com.tapestrysolutions.monitoring.services.repos;

import org.jaffa.loader.ContextKey;
import org.jaffa.loader.MapRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * RepositoryJsonServiceTest - Validates the functionality of com.tapestrysolutions.monitoring.services.repos.RepositoryJsonService
 */
public class RepositoryJsonServiceTest {

    @Mock
    RepositoryQueueService repositoryQueueService;

    @InjectMocks
    private RepositoryJsonService repositoryJsonService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * getRepositoryNames - Validates that a returned list of repository names can be converted to a JSON array
     */
    @Test
    public void getRepositoryNames() throws Exception {

        List<String> repositoryList = new ArrayList<>();
        repositoryList.add("repos1");
        repositoryList.add("repos2");
        repositoryList.add("repos3");
        when(repositoryQueueService.getRepositoryNames()).thenReturn(repositoryList);

        assertEquals("[\"repos1\",\"repos2\",\"repos3\"]", repositoryJsonService.getRepositoryNames());

    }

    /**
     * getRepository - Returns a named repository
     */
    @Test
    public void getRepository() throws Exception {

        ContextKey contextKey1 = new ContextKey("key1", "file1.xml", "cust-code1", "1-PRODUCT");
        ContextKey contextKey2 = new ContextKey("key2", "file2.xml", "cust-code2", "2-CUST");
        ContextKey contextKey3 = new ContextKey("key3", "file3.xml", "cust-code3", "0-PLATFORM");
        ContextKey contextKey4 = new ContextKey("key4", "file4.xml", "cust-code4", "3-CUST");
        ContextKey contextKey5 = new ContextKey("key5", "file5.xml", "cust-code5", "1-PRODUCT");
        ContextKey contextKey6 = new ContextKey("key6", "file6.xml", "cust-code6", "1-PRODUCT");

        MapRepository mapRepository = new MapRepository();
        mapRepository.register(contextKey1, "value1");
        mapRepository.register(contextKey2, "value2");
        mapRepository.register(contextKey3, "value3");
        mapRepository.register(contextKey4, "value6");
        mapRepository.register(contextKey5, "valueX");
        mapRepository.register(contextKey6, "valueY");

        when(repositoryQueueService.getRepository("test")).thenReturn(mapRepository);

        assertEquals("{\"repositoryMap\":{\"ContextKey{id\\u003d\\u0027key1\\u0027, " +
                "variation\\u003d\\u0027cust-code1\\u0027, fileName\\u003d\\u0027file1.xml\\u0027, " +
                "precedence\\u003d\\u00271-PRODUCT\\u0027}\":\"value1\",\"ContextKey{id\\u003d" +
                "\\u0027key2\\u0027, variation\\u003d\\u0027cust-code2\\u0027, fileName\\u003d" +
                "\\u0027file2.xml\\u0027, precedence\\u003d\\u00272-CUST\\u0027}\":\"value2\"," +
                "\"ContextKey{id\\u003d\\u0027key3\\u0027, variation\\u003d\\u0027cust-code3\\u0027, " +
                "fileName\\u003d\\u0027file3.xml\\u0027, precedence\\u003d\\u00270-PLATFORM\\u0027}\":" +
                "\"value3\",\"ContextKey{id\\u003d\\u0027key4\\u0027, variation\\u003d\\u0027cust-code4\\u0027," +
                " fileName\\u003d\\u0027file4.xml\\u0027, precedence\\u003d\\u00273-CUST\\u0027}\":\"value6\"," +
                "\"ContextKey{id\\u003d\\u0027key5\\u0027, variation\\u003d\\u0027cust-code5\\u0027," +
                " fileName\\u003d\\u0027file5.xml\\u0027, precedence\\u003d\\u00271-PRODUCT\\u0027}\"" +
                ":\"valueX\",\"ContextKey{id\\u003d\\u0027key6\\u0027, variation\\u003d\\u0027cust-code6\\u0027, " +
                "fileName\\u003d\\u0027file6.xml\\u0027, precedence\\u003d\\u00271-PRODUCT\\u0027}\":\"valueY\"}," +
                "\"contextKeyCache\":{\"key4\":[{\"id\":\"key4\",\"variation\":\"cust-code4\"," +
                "\"fileName\":\"file4.xml\",\"precedence\":\"3-CUST\"}],\"key3\":[{\"id\":\"key3\"," +
                "\"variation\":\"cust-code3\",\"fileName\":\"file3.xml\",\"precedence\":\"0-PLATFORM\"}]," +
                "\"key6\":[{\"id\":\"key6\",\"variation\":\"cust-code6\",\"fileName\":\"file6.xml\"," +
                "\"precedence\":\"1-PRODUCT\"}],\"key5\":[{\"id\":\"key5\",\"variation\":\"cust-code5\"," +
                "\"fileName\":\"file5.xml\",\"precedence\":\"1-PRODUCT\"}],\"key2\":[{\"id\":\"key2\"," +
                "\"variation\":\"cust-code2\",\"fileName\":\"file2.xml\",\"precedence\":\"2-CUST\"}]," +
                "\"key1\":[{\"id\":\"key1\",\"variation\":\"cust-code1\",\"fileName\":\"file1.xml\",\"precedence\":" +
                "\"1-PRODUCT\"}]}}", repositoryJsonService.getRepository("test"));
    }

}