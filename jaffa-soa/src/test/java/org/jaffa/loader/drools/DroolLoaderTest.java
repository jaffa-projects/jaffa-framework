package org.jaffa.loader.drools;

import org.jaffa.loader.XmlLoaderConfig;
import org.jaffa.security.VariationContext;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * Integration Test for Drools Loader
 */
public class DroolLoaderTest {

    private static AnnotationConfigApplicationContext droolLoaderConfig = new AnnotationConfigApplicationContext(XmlLoaderConfig.class);

    /**
     * tests the drools files are loaded correctly
     */
    @Test
    public void testDroolLoading(){
        DroolManager droolManager = droolLoaderConfig.getBean(DroolManager.class);
        assertNotNull(droolManager.getAgent("testservice", VariationContext.getVariation()));
    }
}
