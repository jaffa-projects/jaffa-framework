package org.jaffa.transaction.tester;


import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;



import org.apache.log4j.Logger;


import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jaffa.modules.user.services.UserContextWrapperFactory;
import org.jaffa.modules.user.services.UserContextWrapper;
import org.jaffa.presentation.portlet.session.UserSessionSetupException;
import org.jaffa.soa.dataaccess.GraphService;
import org.jaffa.soa.graph.GraphDataObject;
import org.jaffa.soa.graph.GraphUpdateResponse;
import org.jaffa.soa.graph.ServiceError;

/**
 * @author srinivastuta
 */
public class CreateGraphTest {
    private static final Logger log = Logger.getLogger(CreateGraphTest.class);

    public List testGraphCreate(String userId, long count, int poolSize, GraphDataObject[] graph, Class serviceClazz) {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        for (int i = 0; i < count; i++) {
            GraphCreateThread tt = new GraphCreateThread(this, userId, graph[i], serviceClazz);

            executor.execute(tt);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getUpdateResponses();
    }

    public List<GraphUpdateResponse> updateResponses = new ArrayList<GraphUpdateResponse>();

    public List<GraphUpdateResponse> getUpdateResponses() {
        return updateResponses;
    }

    public void setUpdateResponses(List<GraphUpdateResponse> updateResponses) {
        this.updateResponses = updateResponses;
    }




}

class GraphCreateThread implements Runnable {

    private static final Logger log = Logger.getLogger(GraphCreateThread.class);

    private String userId;
    private GraphDataObject graph;
    private Class serviceClazz;
    private CreateGraphTest test;

    GraphCreateThread(CreateGraphTest test,String userId, GraphDataObject graph, Class serviceClazz) {
        this.test = test;
        this.userId = userId;
        this.graph = graph;
        this.serviceClazz = serviceClazz;
    }

    @Override
    public void run() {
        UserContextWrapper ucw = null;
        ApplicationExceptions appExps = new ApplicationExceptions();
        try {
            synchronized (this) {
                ucw = UserContextWrapperFactory.instance(userId);
            }
            List<GraphDataObject> graphs = new ArrayList<GraphDataObject>();
            graphs.add(this.graph);
            GraphService service = (GraphService)serviceClazz.newInstance();
            Object graphArray = Array.newInstance(graph.getClass(), 1);
            Array.set(graphArray, 0, graph);
            Method m = serviceClazz.getDeclaredMethod("update", graphArray.getClass());
            GraphUpdateResponse[] responses = (GraphUpdateResponse[])m.invoke(serviceClazz.newInstance(), graphArray);

            if (responses != null && responses.length > 0) {
                for (GraphUpdateResponse response : responses) {
                    ServiceError[] faults = response.getErrors();
                    if(faults!=null && faults.length > 0){
                        for(ServiceError fault: faults){
                            appExps.add(new ApplicationException("error", new String[] {fault.getLocalizedMessage()}));
                        }
                    }
                    synchronized (this){
                        test.getUpdateResponses().add(response);
                    }
                }
            }
            if (appExps.size() > 0) throw appExps;
        } catch (ApplicationExceptions | UserSessionSetupException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.error(e);
        } finally {
            synchronized (this) {
                if (ucw != null)
                    ucw.unsetContext();
            }
        }
    }
}
