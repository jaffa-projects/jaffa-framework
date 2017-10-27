package org.jaffa.loader.drools;

import org.jaffa.loader.CoreLoaderConfig;
import org.jaffa.loader.SoaLoaderConfig;
import org.jaffa.security.VariationContext;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * Integration Test for Drools Loader
 */
public class DroolsLoaderTest {

    private static AnnotationConfigApplicationContext droolLoaderConfig = new AnnotationConfigApplicationContext(CoreLoaderConfig.class,
            CoreLoaderConfig.class, SoaLoaderConfig.class);

    /**
     * tests the drools files are loaded correctly
     */
    @Test
    public void testDroolLoading(){
        DroolsManager droolsManager = droolLoaderConfig.getBean(DroolsManager.class);
        assertNotNull(droolsManager.getAgent("testservice", VariationContext.getVariation()));
    }
}
