package org.jaffa.config;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by szhou on 8/31/2016.
 */
public class InitAppTest {
    private final static String UTF8 = "UTF-8";
    @Before
    public void setUp() throws Exception {
        Config.setProperty(Config.PROP_APPLICATION_RESOURCES_DEFAULT_LOCATION, "source/test/resources/application-resources/ApplicationResources.default");
        Config.setProperty(Config.PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION, "source/test/resources/application-resources/ApplicationResources.override");
        Config.setProperty(Config.PROP_APPLICATION_RESOURCES_LOCATION, "source/test/resources/application-resources/ApplicationResources.properties");
    }

    @Test
    public void generateApplicationResourcesTest() throws Exception {
        InitApp.generateApplicationResources();

        String applicationResourcesLocation = (String) Config.getProperty(Config.PROP_APPLICATION_RESOURCES_LOCATION, null);
        String applicationResourcesDefaultLocation = (String) Config.getProperty(Config.PROP_APPLICATION_RESOURCES_DEFAULT_LOCATION, null);
        String applicationResourcesOverrideLocation = (String) Config.getProperty(Config.PROP_APPLICATION_RESOURCES_OVERRIDE_LOCATION, null);

        BufferedReader applicationResourcesOutputReader = null;
        BufferedReader applicationResourcesDefaultReader = null;
        BufferedReader applicationResourcesOverrideReader = null;
        try {
            // Load the 2 properties file, such that the Override file overrides the Default file
            final Properties properties = new Properties();

            if (applicationResourcesDefaultLocation != null) {
                applicationResourcesDefaultReader = new BufferedReader(new InputStreamReader(new FileInputStream(applicationResourcesDefaultLocation), UTF8));
                properties.load(applicationResourcesDefaultReader);
            }
            if (applicationResourcesOverrideLocation != null) {
                final File applicationResourcesOverrideFile = new File(applicationResourcesOverrideLocation);
                if (applicationResourcesOverrideFile.exists()) {
                    applicationResourcesOverrideReader = new BufferedReader(new InputStreamReader(new FileInputStream(applicationResourcesOverrideFile), UTF8));
                    properties.load(applicationResourcesOverrideReader);
                }
            }
            // load the target properties
            Properties targetProperties = new Properties();
            applicationResourcesOutputReader = new BufferedReader(new InputStreamReader(new FileInputStream(applicationResourcesLocation), UTF8));
            targetProperties.load(applicationResourcesOutputReader);

            // run the assertions
            for (String pName : targetProperties.stringPropertyNames()) {
                assertTrue(pName, targetProperties.getProperty(pName).equals(properties.getProperty(pName)));
            }
        } finally {
            if (applicationResourcesOutputReader != null)
                applicationResourcesOutputReader.close();
            if (applicationResourcesDefaultReader != null)
                applicationResourcesDefaultReader.close();
            if (applicationResourcesOverrideReader != null)
                applicationResourcesOverrideReader.close();
        }

    }

}