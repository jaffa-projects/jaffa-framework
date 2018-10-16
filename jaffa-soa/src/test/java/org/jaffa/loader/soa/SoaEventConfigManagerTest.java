package org.jaffa.loader.soa;

import org.jaffa.loader.SoaLoaderConfig;
import org.jaffa.security.VariationContext;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.Assert.*;

import java.util.Properties;

/**
 * Test for SoaEventConfig Manager.
 */
public class SoaEventConfigManagerTest {

    SoaEventConfigManager soaEventConfigManger = new SoaEventConfigManager();
    private static AnnotationConfigApplicationContext resourceLoaderConfig = new AnnotationConfigApplicationContext(SoaLoaderConfig.class);

    PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    private static final String CORE_SOA_EVENTCONFIG_PATH = "classpath:META-INF/soa-events/core/soa-events-config.properties";
    private static final String CUSTOMER_SOA_EVENTCONFIG_PATH = "classpath:META-INF/soa-events/def/soa-events-config.properties";

    /**
     * tests the registering of soa event config method
     * @throws Exception
     */
    @Test
    public void testSoaEventConfig() throws Exception{
        SoaEventConfigManager soaEventConfigManager = resourceLoaderConfig.getBean(SoaEventConfigManager.class);
        Resource resource = pathMatchingResourcePatternResolver.getResource(CORE_SOA_EVENTCONFIG_PATH);
        //Test
        soaEventConfigManager.registerResource(resource,"0", VariationContext.NULL_VARIATION);
        //verify
        Properties defaultProperties = soaEventConfigManager.getAllSoaEvents();
        assertEquals("enabled", defaultProperties.getProperty("Requisitioning.Forecast.Alert"));
        assertEquals("disabled", defaultProperties.getProperty("SNUDShelfLifeCodeChangeEvent"));
        Resource customerResource = pathMatchingResourcePatternResolver.getResource(CUSTOMER_SOA_EVENTCONFIG_PATH);

        soaEventConfigManager.registerResource(customerResource,"1", VariationContext.DEFAULT_VARIATION);
        defaultProperties = soaEventConfigManager.getAllSoaEvents();
        assertEquals("disabled", defaultProperties.getProperty("Requisitioning.Forecast.Alert"));
        assertEquals("enabled", defaultProperties.getProperty("SNUDShelfLifeCodeChangeEvent"));

    }

    /**
     * tests the unregistered method of Soaevent config
     * @throws Exception
     */
    @Test
    public void testUnregisterResource() throws Exception{
        Resource resource = pathMatchingResourcePatternResolver.getResource(CORE_SOA_EVENTCONFIG_PATH);

        soaEventConfigManger.registerResource(resource,"0", VariationContext.NULL_VARIATION);
        Properties defaultProperties = soaEventConfigManger.getSoaEventConfigVariation(VariationContext.NULL_VARIATION);
        assertEquals("enabled", defaultProperties.getProperty("Requisitioning.Forecast.Alert"));
        //Test
        soaEventConfigManger.unregisterResource(resource,"0", VariationContext.NULL_VARIATION);
        defaultProperties = soaEventConfigManger.getSoaEventConfigVariation(VariationContext.NULL_VARIATION);

        assertEquals(0, defaultProperties.size());
    }

}
