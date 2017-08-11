package org.jaffa.loader.drools;

import org.jaffa.loader.XmlLoaderConfig;
import org.jaffa.security.VariationContext;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * Created by pbagirthi on 8/8/2017.
 */
public class DroolLoaderTest {

    private static AnnotationConfigApplicationContext droolLoaderConfig = new AnnotationConfigApplicationContext(XmlLoaderConfig.class);

    @Test
    public void testRegisterDrool(){
        DroolManager droolManager = droolLoaderConfig.getBean(DroolManager.class);
        assertNotNull(droolManager.getAgent("testservice", VariationContext.getVariation()));
    }
}
