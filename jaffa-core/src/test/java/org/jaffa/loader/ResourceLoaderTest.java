package org.jaffa.loader;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests the functionality od ResourceLoader.
 */
public class ResourceLoaderTest {

    IManager iManager = mock(IManager.class);
    ResourceLoader resourceLoader = new ResourceLoader();
    ArrayList<String> defaultContexts = new ArrayList<>();

    /**
     * initialize defaultContexts and resourceLoader
     * use mockito to return "components.xml" when getResourceFileName() is invoked
     */
    @Before
    public void setup() {
        when(iManager.getResourceFileName()).thenReturn("components.xml");
        resourceLoader.setManager(iManager);
    }

    /**
     * tests the loadXmls method of ResourceLoader
     */
    @Test
    public void testLoadXmls() throws Exception {
        resourceLoader.loadXmls();
        verify(iManager).registerResource((Resource) anyObject(), anyString(), anyString());
    }

    /**
     * tests the method for getting Context
     */
    @Test
    public void testGetContext(){
        //assertEquals("blueprint-icp",resourceLoader.getContext("/abc/blueprint-icp/xyz/aty.xml"));
        //assertEquals("customer-saf",resourceLoader.getContext("/abc/customer-saf/xyz/aty.xml"));
        //assertEquals("platform",resourceLoader.getContext("/abc/platform/xyz/aty.xml"));
    }
}
