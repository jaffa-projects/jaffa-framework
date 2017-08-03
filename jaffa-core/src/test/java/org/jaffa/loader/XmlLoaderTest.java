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
 * Tests the functionality od XmlLoader.
 */
public class XmlLoaderTest {

    IManager iManager = mock(IManager.class);
    XmlLoader xmlLoader = new XmlLoader();
    ArrayList<String> defaultContexts = new ArrayList<>();

    /**
     * initialize defaultContexts and xmlLoader
     * use mockito to return "components.xml" when getXmlFileName() is invoked
     */
    @Before
    public void setup() {
        when(iManager.getXmlFileName()).thenReturn("components.xml");
        xmlLoader.setManager(iManager);
    }

    /**
     * tests the loadXmls method of XmlLoader
     */
    @Test
    public void testLoadXmls() throws Exception {
        xmlLoader.loadXmls();
        verify(iManager).registerXML((Resource) anyObject(), anyString());
    }

    /**
     * tests the method for getting Context
     */
    @Test
    public void testGetContext(){
        //assertEquals("blueprint-icp",xmlLoader.getContext("/abc/blueprint-icp/xyz/aty.xml"));
        //assertEquals("customer-saf",xmlLoader.getContext("/abc/customer-saf/xyz/aty.xml"));
        //assertEquals("platform",xmlLoader.getContext("/abc/platform/xyz/aty.xml"));
    }
}
