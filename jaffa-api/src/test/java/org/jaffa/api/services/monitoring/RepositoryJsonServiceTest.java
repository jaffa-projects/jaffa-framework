package org.jaffa.api.services.monitoring;

import org.jaffa.api.services.monitoring.controller.RepositoryJsonService;
import org.jaffa.loader.*;
import org.jaffa.loader.components.ComponentManager;
import org.jaffa.loader.config.ApplicationResourcesManager;
import org.jaffa.loader.config.ApplicationRulesManager;
import org.jaffa.loader.messaging.JndiJmsManager;
import org.jaffa.loader.messaging.MessagingManager;
import org.jaffa.loader.navigation.NavigationManager;
import org.jaffa.loader.policy.BusinessFunctionManager;
import org.jaffa.loader.policy.RoleManager;
import org.jaffa.loader.scheduler.SchedulerManager;
import org.jaffa.loader.soa.SoaEventManager;
import org.jaffa.loader.transaction.TransactionManager;
import org.jaffa.modules.scheduler.services.configdomain.Task;
import org.jaffa.security.securityrolesdomain.Role;
import org.jaffa.soa.rules.ApplicationRule;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * RepositoryJsonServiceTest - Validates the functionality of com.tapestrysolutions.monitoring.services.repos.RepositoryJsonService
 */
public class RepositoryJsonServiceTest {

    private ContextKey testKey;
    private ManagerRepositoryService managerRepositoryService;
    private RepositoryJsonService repositoryJsonService;
    private SchedulerManager testManager;
    private Task testTask;

    /**
     * This setup function registers resources to each managed repository to produce correct retrieval results
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        managerRepositoryService = ManagerRepositoryService.getInstance();
        ContextKey testKey = new ContextKey("testKey", "file1.xml", "NULL", "1-PRODUCT");
        SchedulerManager testManager = new SchedulerManager();
        Task testTask = new Task();

        repositoryJsonService = new RepositoryJsonService();
        testKey = new ContextKey("testKey", "file1.xml", "NULL", "1-PRODUCT");
        testManager = new SchedulerManager();
        testTask = new Task();

        //Must populate a repository element in order to retrieve data
        testTask.setAutoCreateDataBean(true);
        testTask.setDataBean("DataBeanTest");
        testTask.setType("TestType");
        testManager.registerSchedulerTask(testKey, testTask);

        //Add managers to ManagerRepositoryService
        managerRepositoryService.add("Task", testManager);
        managerRepositoryService.add("ComponentManager", new ComponentManager());
        managerRepositoryService.add("ApplicationRulesManager", new ApplicationRulesManager());
        managerRepositoryService.add("NavigationManager", new NavigationManager());
        managerRepositoryService.add("BusinessFunctionManager", new BusinessFunctionManager());
        managerRepositoryService.add("RoleManager", new RoleManager());
        managerRepositoryService.add("JndiJmsManager", new JndiJmsManager());
        managerRepositoryService.add("MessagingManager", new MessagingManager());
        managerRepositoryService.add("SoaEventManager", new SoaEventManager());
        managerRepositoryService.add("TransactionManager", new TransactionManager());
    }

    /**
     * getRepositoryNames - Validates that a returned list of repository names can be converted to a JSON array
     */
    @Test
    public void getRepositoryNames() throws Exception {
        String actual = repositoryJsonService.getRepositoryNames();
        assertEquals(
                "[\"GlobalMenu\",\"BusinessFunction\",\"Task\",\"Properties\",\"SoaEventInfo\"," +
                    "\"TransactionInfo\",\"TypeInfo\",\"Role\",\"JmsConfig\",\"ComponentDefinition\"," +
                    "\"QueueInfo\",\"MessageFilter\",\"TopicInfo\",\"MessageInfo\"]", actual);

    }

    /**
     * getRepository - Returns a named repository
     */
    @Test
    public void getRepository() throws Exception {
        assertEquals("{\"testKey\":{\"autoCreateDataBean\":true,\"dataBean\":\"DataBeanTest\",\"type\":\"TestType\"}}",
                repositoryJsonService.getRepository("Task", null));
    }

    /**
     * getRepository - Returns a named repository
     */
    @Test
    public void getRepositoryValue() throws Exception {
        assertEquals("{\"autoCreateDataBean\":true,\"dataBean\":\"DataBeanTest\"," +
                "\"type\":\"TestType\"}", repositoryJsonService.getRepositoryValue("Task", "testKey"));
    }

    @Test
    public void testRepositoryJsonService_getAllRepoItems() {
        setupRoleManager();
        UriInfo uriInfo = null;
        String result = repositoryJsonService.getRepository("Role", uriInfo);
        assertTrue(result.equals("{\"test.role\":{\"description\":\"p4\"}}"));
    }

    @Test
    public void testRepositoryJsonService_getBaselineRepoItems() {
        setupRoleManager();
        TestUriInfo uriInfo = new TestUriInfo();
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add("baseline", "true");
        uriInfo.setQueryParams(queryParams);
        String result = repositoryJsonService.getRepository("Role", uriInfo);
        assertTrue(result.equals("{\"test.role\":{\"description\":\"p2\"}}"));
    }

    private void setupRoleManager() {
        Map<String, IManager> managers = managerRepositoryService.getManagerMap();
        IManager roleManager = managers.get("RoleManager");
        IRepository<Role> roleRepo = roleManager.getRepositoryByName("Role");
        addRole(roleRepo, "test.role", "file1.xml", "NULL", "1-PLATFORM", "p1");
        addRole(roleRepo, "test.role", "file1.xml", "NULL", "2-PRODUCT", "p2");
        addRole(roleRepo, "test.role", "file1.xml", "NULL", "3-GENERIC", "p3");
        addRole(roleRepo, "test.role", "file1.xml", "NULL", "4-CUSTOMER", "p4");
    }

    private void addRole(IRepository<Role> repo, String id, String file, String variation, String precedence, String description) {
        ContextKey key = new ContextKey(id, file, variation, precedence);
        Role role = new Role();
        role.setDescription(description);
        repo.register(key, role);
    }

    class TestUriInfo implements UriInfo {
        private MultivaluedMap<String, String> queryParams = null;

        @Override
        public String getPath() {
            return null;
        }

        @Override
        public String getPath(boolean b) {
            return null;
        }

        @Override
        public List<PathSegment> getPathSegments() {
            return null;
        }

        @Override
        public List<PathSegment> getPathSegments(boolean b) {
            return null;
        }

        @Override
        public URI getRequestUri() {
            return null;
        }

        @Override
        public UriBuilder getRequestUriBuilder() {
            return null;
        }

        @Override
        public URI getAbsolutePath() {
            return null;
        }

        @Override
        public UriBuilder getAbsolutePathBuilder() {
            return null;
        }

        @Override
        public URI getBaseUri() {
            return null;
        }

        @Override
        public UriBuilder getBaseUriBuilder() {
            return null;
        }

        @Override
        public MultivaluedMap<String, String> getPathParameters() {
            return null;
        }

        @Override
        public MultivaluedMap<String, String> getPathParameters(boolean b) {
            return null;
        }

        @Override
        public MultivaluedMap<String, String> getQueryParameters() {
            return queryParams;
        }

        @Override
        public MultivaluedMap<String, String> getQueryParameters(boolean b) {
            return null;
        }

        @Override
        public List<String> getMatchedURIs() {
            return null;
        }

        @Override
        public List<String> getMatchedURIs(boolean b) {
            return null;
        }

        @Override
        public List<Object> getMatchedResources() {
            return null;
        }

        @Override
        public URI resolve(URI uri) {
            return null;
        }

        @Override
        public URI relativize(URI uri) {
            return null;
        }

        public void setQueryParams(MultivaluedMap<String, String> queryParams) {
            this.queryParams = queryParams;
        }
    }
}