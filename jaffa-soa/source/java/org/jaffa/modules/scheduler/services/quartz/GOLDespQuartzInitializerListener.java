/*
 *
 *  **************************************************TAPESTRY SOLUTIONS PROPRIETARY VER 2.0
 *  *
 *  *  Copyright © 2016 Tapestry Solutions, Inc.
 *  *  THIS PROGRAM IS PROPRIETARY TO TAPESTRY SOLUTIONS, INC.
 *  *  REPRODUCTION, DISCLOSURE, OR USE, IN WHOLE OR IN PART,
 *  *  UNDERTAKEN EXCEPT WITH PRIOR WRITTEN AUTHORIZATION OF
 *  *  TAPESTRY SOLUTIONS IS PROHIBITED.
 *  *
 *  **************************************************TAPESTRY SOLUTIONS PROPRIETARY VER 2.0
 *
 */
package org.jaffa.modules.scheduler.services.quartz;

import org.jaffa.modules.scheduler.services.SchedulerHelperFactory;
import org.apache.log4j.Logger;
import org.quartz.ee.servlet.QuartzInitializerListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class GOLDespQuartzInitializerListener extends QuartzInitializerListener {

    private static final Logger LOGGER = Logger.getLogger(GOLDespQuartzInitializerListener.class);

    private static final String WAIT_ON_SHUTDOWN = System.getProperty(
            "org.quartz.wait.on.shutdown", "true");

    private boolean waitOnShutdown;

    public GOLDespQuartzInitializerListener() {
    }

    public void contextInitialized(final ServletContextEvent sce) {

        LOGGER.info("GOLDespQuartz Initializer Listener loaded, initializing Scheduler...");

        waitOnShutdown = Boolean.valueOf(WAIT_ON_SHUTDOWN);

        final ServletContext servletContext = sce.getServletContext();

        String configFile = servletContext.getInitParameter("quartz:config-file");
        if (configFile == null) {
            configFile = servletContext.getInitParameter("config-file");
        }

        String fileName = System.getProperty("org.quartz.properties", null);
        if ((fileName == null || 0 == fileName.length()) && (configFile != null && 0 < configFile.length())) {
            System.setProperty("org.quartz.properties", configFile);
        }

        LOGGER.info("Quartz Schedule StdSchedulerFactory: org.quartz.properties=" + System.getProperty("org.quartz.properties", null));

        SchedulerHelperFactory.instance().instantiateSchedulerFactory();
        SchedulerHelperFactory.instance().startScheduler();
    }

    public void contextDestroyed(final ServletContextEvent sce) {
        SchedulerHelperFactory.instance().shutdownScheduler(waitOnShutdown);
        LOGGER.info("GOLDQuartzService Scheduler successful shutdown.");
    }
}