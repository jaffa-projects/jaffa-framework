package org.jaffa.loader.scheduler;

import org.jaffa.loader.SoaLoaderConfig;
import org.jaffa.modules.scheduler.services.configdomain.Task;
import org.jaffa.soa.services.SOAEventPoller;
import org.jaffa.transaction.services.TransactionDependencySweeper;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * MessagingXmlLoadTest - Verifies the Scheduler beans can be loaded from the
 * SchedulerManager implementation.
 */
public class SchedulerXmlLoadTest {

    private static AnnotationConfigApplicationContext xmlLoaderConfig =
            new AnnotationConfigApplicationContext(SoaLoaderConfig.class);

    /**
     * Test load the XML config for the scheduler task repository.
     */
    @Test
    public void testXmlLoad() {
        SchedulerManager schedulerManager = xmlLoaderConfig.getBean(SchedulerManager.class);
        assertNull(schedulerManager.getSchedulerTask("org.jaffa.scheduler.tester.TestMessageSingleton", null));
        assertNotNull(schedulerManager.getSchedulerTask("org.jaffa.soa.services.SOAEventPoller", null));
        assertNotNull(schedulerManager.getSchedulerTask("org.jaffa.transaction.services.TransactionDependencySweeper", null));
        assertNull(schedulerManager.getSchedulerTask("", null));
    }

    /**
     * Verify that the schedulerManager can get all scheduled tasks registered in the repository.
     */
    @Test
    public void testGetAllSchedulerTasks() {
        SchedulerManager schedulerManager = xmlLoaderConfig.getBean(SchedulerManager.class);

        assertEquals(2, schedulerManager.getAllSchedulerTasks(null).length);
        List<String> schedulerTasks = Arrays.asList("org.jaffa.soa.services.SOAEventPoller",
                "org.jaffa.transaction.services.TransactionDependencySweeper");
        Task[] allScheduledTasks = schedulerManager.getAllSchedulerTasks(null);
        assertTrue(schedulerTasks.contains(allScheduledTasks[0].getDataBean()));
        assertTrue(schedulerTasks.contains(allScheduledTasks[1].getDataBean()));
    }

    /**
     * Verify that we can retrieve the scheduler task from the repository by class.
     */
    @Test
    public void testGetSchedulerTaskByClass() {
        SchedulerManager schedulerManager = xmlLoaderConfig.getBean(SchedulerManager.class);

        assertEquals("org.jaffa.soa.services.SOAEventPoller", schedulerManager.getSchedulerTask( SOAEventPoller.class , null).getDataBean());
        assertEquals("org.jaffa.transaction.services.TransactionDependencySweeper",
                schedulerManager.getSchedulerTask( TransactionDependencySweeper.class , null).getDataBean());
    }

    @Test
    public void testGetSchedulerTaskByTaskName() {
        SchedulerManager schedulerManager = xmlLoaderConfig.getBean(SchedulerManager.class);

        assertEquals("org.jaffa.soa.services.SOAEventPoller", schedulerManager.getSchedulerTaskByTypeName( "SOAEventPoller" , null).getDataBean());
        assertEquals("org.jaffa.transaction.services.TransactionDependencySweeper",
                schedulerManager.getSchedulerTaskByTypeName( "TransactionDependencySweeper" , null).getDataBean());
    }
}
