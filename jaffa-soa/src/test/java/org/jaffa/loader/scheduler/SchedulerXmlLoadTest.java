package org.jaffa.loader.scheduler;

import org.jaffa.loader.SoaLoaderConfig;
import org.jaffa.modules.scheduler.services.configdomain.Task;
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
        assertNull(schedulerManager.getSchedulerTask("org.jaffa.scheduler.tester.TestMessageSingleton"));
        assertNotNull(schedulerManager.getSchedulerTask("org.jaffa.soa.services.SOAEventPoller"));
        assertNotNull(schedulerManager.getSchedulerTask("org.jaffa.transaction.services.TransactionDependencySweeper"));
        assertNull(schedulerManager.getSchedulerTask(""));
    }

    /**
     * Verify that the schedulerManager can get all scheduled tasks registered in the repository.
     */
    @Test
    public void testGetAllSchedulerTasks() {
        SchedulerManager schedulerManager = xmlLoaderConfig.getBean(SchedulerManager.class);

        assertEquals(2, schedulerManager.getAllSchedulerTasks().length);
        List<String> schedulerTasks = Arrays.asList("org.jaffa.soa.services.SOAEventPoller",
                "org.jaffa.transaction.services.TransactionDependencySweeper");
        Task[] allScheduledTasks = schedulerManager.getAllSchedulerTasks();
        assertTrue(schedulerTasks.contains(allScheduledTasks[0].getDataBean()));
        assertTrue(schedulerTasks.contains(allScheduledTasks[1].getDataBean()));
    }


    @Test
    public void testGetSchedulerTaskByTaskName() {
        SchedulerManager schedulerManager = xmlLoaderConfig.getBean(SchedulerManager.class);

        assertEquals("org.jaffa.soa.services.SOAEventPoller", schedulerManager.getSchedulerTaskByTypeName( "SOAEventPoller").getDataBean());
        assertEquals("org.jaffa.transaction.services.TransactionDependencySweeper",
                schedulerManager.getSchedulerTaskByTypeName( "TransactionDependencySweeper").getDataBean());
    }

    /**
     * Tests the ability of this IManager to retrieve a repository when given its String name
     */
    @Test
    public void testGetRepositoryByName() throws Exception {
        SchedulerManager schedulerManager = xmlLoaderConfig.getBean(SchedulerManager.class);

        String repo = "Task";
        assertEquals(repo, schedulerManager.getRepositoryByName(repo).getName());
    }

    /**
     * Test the retrieval of the list of repositories managed by this class
     */
    @Test
    public void testGetRepositoryNames() {
        SchedulerManager schedulerManager = xmlLoaderConfig.getBean(SchedulerManager.class);
        for (Object repositoryName : schedulerManager.getRepositoryNames()) {
            assertEquals(repositoryName, schedulerManager.getRepositoryByName((String)repositoryName).getName());
        }
    }
}
