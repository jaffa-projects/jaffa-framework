package org.jaffa.loader.config;

import org.jaffa.loader.CoreLoaderConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

public class ApplicationResourcesLoadTest {
    private static AnnotationConfigApplicationContext resourceLoaderConfig = new AnnotationConfigApplicationContext(CoreLoaderConfig.class);

    /**
     * Verifies that we can load the resources from the ApplicationResources*.properties file into resource repositories.
     */
    @Test
    public void testResourceLoad() {
        ApplicationResourcesManager applicationResourcesManager = resourceLoaderConfig.getBean(ApplicationResourcesManager.class);
        Properties defaultProperty = applicationResourcesManager.getApplicationResources();
        Properties arlocaleProperty = applicationResourcesManager.getApplicationResourcesLocale("ar");
        Properties arOmlocaleProperty = applicationResourcesManager.getApplicationResourcesLocale("ar_OM");
        assertNotNull(defaultProperty);
        assertNull(arlocaleProperty);
        assertNotNull(arOmlocaleProperty);
        assertEquals(arOmlocaleProperty.size(),6);
    }


}
