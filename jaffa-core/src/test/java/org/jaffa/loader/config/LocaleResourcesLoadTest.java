package org.jaffa.loader.config;

import org.jaffa.loader.CoreLoaderConfig;
import org.jaffa.presentation.portlet.session.LocaleContext;
import org.jaffa.util.LocaleHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class LocaleResourcesLoadTest {
    private static AnnotationConfigApplicationContext resourceLoaderConfig = new AnnotationConfigApplicationContext(CoreLoaderConfig.class);


    @BeforeClass
    public static void setup(){
    }
    /**
     * Verifies that we can load the locale properties from the locale*.properties file into resource repositories.
     */
    @Test
    public void testResourceLoad() {
        LocaleResourcesManager localeResourcesManager = resourceLoaderConfig.getBean(LocaleResourcesManager.class);
        Map localeRepository = localeResourcesManager.getLocalePropertiesRepository().getMyRepository();
        assertNotNull(localeRepository);
        assertEquals(31, localeRepository.size());
        assertEquals("yyyyMMdd'T'HHmmssSSS", LocaleHelper.getProperty("ISO.datetime.short.format"));
        assertEquals("MM/dd/yyyy", LocaleHelper.getProperty("dateonly.format"));
    }

    @AfterClass
    public static void tearDown(){
        LocaleContext.setLocale(null);
    }
}
