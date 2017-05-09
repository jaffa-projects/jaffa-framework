/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.jaffa.soa.dataaccess;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jaffa.datatypes.Formatter;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.soa.graph.GraphCriteria;
import org.jaffa.soa.graph.GraphDataObject;
import org.jaffa.soa.graph.GraphQueryResponse;
import org.jaffa.soa.graph.GraphUpdateResponse;
import org.jaffa.soa.graph.ServiceError;
import org.jaffa.util.ExceptionHelper;

/**
 * Provides functions for Creating, Retrieving, Updating and
 * Deleting data in the set of objects within a specific Graph.
 */
public class GraphService<C extends GraphCriteria, G extends GraphDataObject, Q extends GraphQueryResponse<G>, U extends GraphUpdateResponse<G>, H extends ITransformationHandler> {

    private static Logger log = Logger.getLogger(GraphService.class);
    private static final Field QUERY_GRAPHS_FIELD;
    private static final Field UPDATE_SOURCE_FIELD;
    private static final int RETRY_COUNT = 3;
    protected Class<C> graphCriteriaClass;
    protected Class<G> graphDataClass;
    protected Class<Q> graphQueryResponseClass;
    protected Class<U> graphUpdateResponseClass;
    protected Class<H> handlerClass;
    protected H handler;


    static {
        try {
            // Get a handle on the 'graphs' field of GraphQueryResponse
            QUERY_GRAPHS_FIELD = GraphQueryResponse.class.getDeclaredField("graphs");
            QUERY_GRAPHS_FIELD.setAccessible(true);
        } catch (Exception e) {
            // This should never happen !!
            String str = "Error in obtaining a handle on the 'graphs' field of GraphQueryResponse";
            log.fatal(str, e);
            throw new RuntimeException(str, e);
        }
        try {
            // Get a handle on the 'source' field of GraphUpdateResponse
            UPDATE_SOURCE_FIELD = GraphUpdateResponse.class.getDeclaredField("source");
            UPDATE_SOURCE_FIELD.setAccessible(true);
        } catch (Exception e) {
            // This should never happen !!
            String str = "Error in obtaining a handle on the 'source' field of GraphUpdateResponse";
            log.fatal(str, e);
            throw new RuntimeException(str, e);
        }
    }

    /**
     * Constructs a new instance of the GraphService.
     */
    protected GraphService() {
        Type[] typeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        this.graphCriteriaClass = typeArguments[0] instanceof Class ? (Class<C>) typeArguments[0] : null;
        this.graphDataClass = typeArguments[1] instanceof Class ? (Class<G>) typeArguments[1] : null;
        this.graphQueryResponseClass = typeArguments[2] instanceof Class ? (Class<Q>) typeArguments[2] : null;
        this.graphUpdateResponseClass = typeArguments[3] instanceof Class ? (Class<U>) typeArguments[3] : null;
        this.handlerClass = typeArguments[4] instanceof Class ? (Class<H>) typeArguments[4] : null;
    }

    /**
     * Constructs a new instance of the GraphService.
     * @param graphCriteriaClass type token for creating GraphCriteria instances.
     * @param graphDataClass type token for creating GraphDataObject instances.
     * @param graphQueryResponseClass type token for creating GraphQueryResponse instances.
     * @param graphUpdateResponseClass type token for creating GraphUpdateResponse instances.
     * @param handlerClass type token for creating ITransformationHandler instances.
     *
     * NOTE: From a subclass, there is no need to explicitly pass the Class instances for the already defined type arguments.
     * Instead use the default constructor, which determines the various Class instances using the standard API. This constructor
     * should however be used when constructing an instance directly.
     */
    protected GraphService(Class<C> graphCriteriaClass, Class<G> graphDataClass, Class<Q> graphQueryResponseClass, Class<U> graphUpdateResponseClass, Class<H> handlerClass) {
        this.graphCriteriaClass = graphCriteriaClass;
        this.graphDataClass = graphDataClass;
        this.graphQueryResponseClass = graphQueryResponseClass;
        this.graphUpdateResponseClass = graphUpdateResponseClass;
        this.handlerClass = handlerClass;
    }

    /**
     * Query domain objects, returning an array of Graphs in the response.
     * By default no related objects are returned, and any foreign objects will only
     * contain key information. Use the <code>setResultGraphRules()</code> method on
     * the criteria to change this.
     * When an error occurs, one or more instances of ServiceError, indicating
     * some kind of internal system error (like a problem accessing the database),
     * or listing the business logic errors will be returned in the response.
     * @param graphCriteria the graph criteria.
     * @return An array of Graphs, the content of this graph is based on the result filter 'rules', as provided in the criteria object.
     * while processing. When an error occurs, an array of ServiceError instances will be returned
     * with details about the root cause of the problem.
     */
    //@WebMethod - to expose as web service
    //@DirectRemoteMethod - use dwr.xml to expose this to prevent a compile dependency on this annotation
    protected Q _query(C graphCriteria) {
        UOW uow = null;
        try {
            if (log.isDebugEnabled())
                log.debug("Input to _query: " + graphCriteria);

            // create the UOW
            uow = new UOW();

            // Build the Criteria

            Criteria c = createCriteria(graphCriteria);

            // Execute the query and obtain the results
            G[] graphs = runQuery(graphCriteria, c, uow);

            // create the response
            Q response = createGraphQueryResponse(graphs, null);

            // Find the total number of records
            stampTotalRecords(graphCriteria, c, uow, response, graphs.length);

            if (log.isDebugEnabled())
                log.debug("Output from _query: " + response);
            return response;
        } catch (Exception e) {
            // add errors to the response
            ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
            if (appExps != null) {
                if (log.isDebugEnabled())
                    log.debug("Error in query execution", appExps);
                return createGraphQueryResponse(null, appExps);
            } else {
                log.error("Internal Error in query execution", e);
                return createGraphQueryResponse(null, e);
            }
        } finally {
            if (uow != null && uow.isActive()) {
                try {
                    uow.rollback();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Create/Update a domain object and/or its related objects. This allows a set of
     * Graphs to be supplied with either existing records to be updated, or new
     * entries that will be created.
     * <p>
     * Each domain object graph in the array is processed in its own ACID Transaction.
     * Any error in processing any part of a single Graph will cause all updates for that
     * graph to be rolled back, and the graph will be returned in a response object
     * along with a list of the errors. In the case of a runtime error (as opposed to
     * and data/validation error) the update is retried before it is treated as an error.
     * @param graphs The set of Object Graphs to Update/Create.
     * @return An array of response objects, each response contains a copy of the Graph
     * that failed and the list of errors as to why it failed. In case the domain object is created,
     * the response will contain the root Graph with the key-fields only.
     */
    //@WebMethod - to expose as web service
    //@DirectRemoteMethod - use dwr.xml to expose this to prevent a compile dependency on this annotation
    protected U[] _update(G[] graphs) {
        if (graphs == null) {
            if (log.isDebugEnabled())
                log.debug("Input to _update() is null. Nothing done.");
            return null;
        }

        // Loop through each graph
        Collection<U> response = new LinkedList<U>();
        GraphMapping graphMapping = MappingFactory.getInstance(graphDataClass);
        for (int i = 0; i < graphs.length; i++) {
            String path = graphMapping.getDomainClassShortName() + '[' + i + ']';
            int retries = 0;
            while (true) {
                UOW uow = null;
                try {
                    uow = new UOW();
                    G output = localUpdate(path, graphs[i], uow);
                    uow.commit();
                    if (output != null)
                        response.add(createGraphUpdateResponse(output, null));
                    break;
                } catch (Exception e) {
                    ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                    if (appExps != null) {
                        if (log.isDebugEnabled())
                            log.debug("Logic Error Processing " + path, appExps);
                        //Add a context; but only if we are processing multiple graphs
                        if (graphs.length > 1)
                            appExps.addContext(createContextForApplicationExceptions(graphs[i], path, i, graphMapping));
                        response.add(createGraphUpdateResponse(graphs[i], appExps));
                        break;
                    } else if (++retries <= RETRY_COUNT) {
                        if (log.isDebugEnabled())
                            log.debug("Internal Error Processing " + path + ". Retrying " + retries + " of " + RETRY_COUNT, e);
                        continue;
                    } else {
                        log.error("Internal Error Processing " + path + ". No more retries!", e);
                        response.add(createGraphUpdateResponse(graphs[i], e));
                        break;
                    }
                } finally {
                    /* Ensure that a new handler is used for each Graph,
                     * since a handler may instantiate the ServiceRulesInterceptor
                     * and plug it into the UOW for that Graph. The plugin can then
                     * be used to intercept database i/o and fire Drools rules.
                     */
                    handler = null;

                    if (uow != null && uow.isActive()) {
                        try {
                            uow.rollback();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        if (log.isDebugEnabled())
            log.debug(toString(graphs, response));
        return response.size() > 0 ? response.toArray((U[]) Array.newInstance(graphUpdateResponseClass, response.size())) : null;
    }

    /** Prevalidates a Graph. This method can be used to default data into a Graph.
     * @param graph The graph to prevalidate.
     * @return The input graph with defaulted data.
     */
    //@DirectRemoteMethod - use dwr.xml to expose this to prevent a compile dependency on this annotation
    protected U _prevalidate(G graph) {
        UOW uow = null;
        try {
            uow = new UOW();
            GraphMapping graphMapping = MappingFactory.getInstance(graphDataClass);
            String path = graphMapping.getDomainClassShortName() + "[0]";
            if (log.isDebugEnabled())
                log.debug("Prevalidating Input Graph Object: " + path + '\n' + TransformerUtils.printGraph(graph));

            G output = (G) DataTransformer.prevalidateGraph(path, graph, uow, createHandler(uow));
            if (log.isDebugEnabled())
                log.debug("Output after prevalidate\n" + TransformerUtils.printGraph(output));
            uow.rollback();
            return createGraphUpdateResponse(output, null);
        } catch (Exception e) {
            return createGraphUpdateResponse(graph, e);
        } finally {
            if (uow != null && uow.isActive()) {
                try {
                    uow.rollback();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Clone domain objects that match the input criteria.
     * The newGraph is used to supply values for the key fields and others.
     * When an error occurs, one or more instances of ServiceError, indicating
     * some kind of internal system error (like a problem accessing the database),
     * or listing the business logic errors will be returned in the response.
     * NOTE: All domain objects will be cloned in the same transaction.
     * @param graphCriteria the graph criteria.
     * @param newGraph supplies values for the key fields and others.
     * @return An array of response objects, each response contains a copy of the Graph
     * that failed and the list of errors as to why it failed. In case the domain object is created,
     * the response will contain the root Graph with the key-fields only.
     */
    //@WebMethod - to expose as web service
    //@DirectRemoteMethod - use dwr.xml to expose this to prevent a compile dependency on this annotation
    protected U[] _clone(C graphCriteria, G newGraph) {
        return cloneOrMassUpdate(graphCriteria, newGraph, true);
    }

    /**
     * Mass-Update domain objects that match the input criteria.
     * The newGraph is used to supply new non-key values.
     * When an error occurs, one or more instances of ServiceError, indicating
     * some kind of internal system error (like a problem accessing the database),
     * or listing the business logic errors will be returned in the response.
     * NOTE: All domain objects will be mass-updated in the same transaction.
     * @param graphCriteria the graph criteria.
     * @param newGraph supplies new non-key values.
     * @return An array of response objects, each response contains a copy of the Graph
     * that failed and the list of errors as to why it failed.
     */
    //@WebMethod - to expose as web service
    //@DirectRemoteMethod - use dwr.xml to expose this to prevent a compile dependency on this annotation
    protected U[] _massUpdate(C graphCriteria, G newGraph) {
        return cloneOrMassUpdate(graphCriteria, newGraph, false);
    }

    /**
     * Clone/Mass-Update domain objects that match the input criteria.
     * The newGraph is used to supply new values.
     * When an error occurs, one or more instances of ServiceError, indicating
     * some kind of internal system error (like a problem accessing the database),
     * or listing the business logic errors will be returned in the response.
     * NOTE: All domain objects will be cloned/mass-updated in the same transaction.
     * @param graphCriteria the graph criteria.
     * @param newGraph supplies new values.
     * @param clone if true, cloning will be performed. Else a mass-update will be performed.
     * @return An array of response objects, each response contains a copy of the Graph
     * that failed and the list of errors as to why it failed. In case the domain object is created,
     * the response will contain the root Graph with the key-fields only.
     */
    protected U[] cloneOrMassUpdate(C graphCriteria, G newGraph, boolean clone) {
        UOW uow = null;
        Collection<U> responseCol = new LinkedList<U>();
        try {
            if (log.isDebugEnabled())
                log.debug("Input to " + (clone ? "clone" : "mass-update") + ": criteria=" + graphCriteria + ", newGraph=" + newGraph);

            // create the UOW
            uow = new UOW();

            // Build the Criteria
            Criteria c = createCriteria(graphCriteria);

            // Execute the query and obtain the results
            G[] graphs = runQuery(graphCriteria, c, uow);

            if (log.isDebugEnabled())
                log.debug("Objects to be " + (clone ? "cloned" : "mass-updated") + ": " + Arrays.toString(graphs));

            // Loop through each graph and clone/mass-update it
            if (graphs != null && graphs.length > 0) {
                addContext();
                GraphMapping graphMapping = MappingFactory.getInstance(graphDataClass);
                boolean error = false;
                for (int i = 0; i < graphs.length; i++) {
                    String path = graphMapping.getDomainClassShortName() + '[' + i + ']';
                    try {
                        if (clone) {
                            H handler = createHandler(uow);
                            handler.setCloning(true);
                            G output = (G) DataTransformer.cloneGraph(path, graphs[i], uow, handler, newGraph);
                            if (output != null)
                                responseCol.add(createGraphUpdateResponse(output, null));
                        } else
                            DataTransformer.massUpdateGraph(path, graphs[i], uow, createHandler(uow), newGraph);
                    } catch (Exception e) {
                        if (e instanceof ApplicationExceptions) {
                            if (log.isDebugEnabled())
                                log.debug("Logic Error Processing " + path, e);
                        } else
                            log.error("Internal Error Processing " + path, e);
                        responseCol.clear();
                        responseCol.add(createGraphUpdateResponse(graphs[i], e));
                        error = true;
                        break;
                    }
                }
                if (!error)
                    uow.commit();
            }
        } catch (Exception e) {
            log.error("Error in retrieving objects for " + (clone ? "clonng" : "mass-updating"), e);
            responseCol.clear();
            responseCol.add(createGraphUpdateResponse(newGraph, e));
        } finally {
            unsetContext();
            if (uow != null && uow.isActive()) {
                try {
                    uow.rollback();
                } catch (Exception e) {
                }
            }
        }
        U[] response = responseCol.size() > 0 ? responseCol.toArray((U[]) Array.newInstance(graphUpdateResponseClass, responseCol.size())) : null;
        if (log.isDebugEnabled())
            log.debug("Output from " + (clone ? "clone" : "mass-update") + ": " + Arrays.toString(response));
        return response;
    }

    /**
     * Provides the ability to clone using a calling service UOW
     *
     * Refer to Clone/Mass-Update domain objects that match the input criteria.
     * The newGraph is used to supply new values.
     * When an error occurs, one or more instances of ServiceError, indicating
     * some kind of internal system error (like a problem accessing the database),
     * or listing the business logic errors will be returned in the response.
     *
     * @param graphCriteria the graph criteria.
     * @param newGraph supplies new values.
     * @param clone if true, cloning will be performed. Else a mass-update will be performed.
     * @return An array of response objects, each response contains a copy of the Graph
     * @throws FrameworkException
     * @throws ApplicationExceptions
     */
    public U[] localCloneOrMassUpdate(C graphCriteria, G newGraph, boolean clone, UOW uow) throws FrameworkException, ApplicationExceptions {

        Collection<U> responseCol = new LinkedList<U>();
        U[] response = null;
        if (log.isDebugEnabled())
            log.debug("Input to " + (clone ? "clone" : "mass-update") + ": criteria=" + graphCriteria + ", newGraph=" + newGraph);
        try {

            // Build the Criteria
            Criteria c = createCriteria(graphCriteria);

            // Execute the query and obtain the results
            G[] graphs = runQuery(graphCriteria, c, uow);

            if (log.isDebugEnabled())
                log.debug("Objects to be " + (clone ? "cloned" : "mass-updated") + ": " + Arrays.toString(graphs));
            // Loop through each graph and clone/mass-update it
            if (graphs != null && graphs.length > 0) {
                addContext();
                GraphMapping graphMapping = MappingFactory.getInstance(graphDataClass);
                boolean error = false;
                for (int i = 0; i < graphs.length; i++) {
                    String path = graphMapping.getDomainClassShortName() + '[' + i + ']';

                    if (clone) {
                        H handler = createHandler(uow);
                        handler.setCloning(true);
                        G output = (G) DataTransformer.cloneGraph(path, graphs[i], uow, handler, newGraph);
                        if (output != null)
                            responseCol.add(createGraphUpdateResponse(output, null));
                    } else
                        DataTransformer.massUpdateGraph(path, graphs[i], uow, createHandler(uow), newGraph);
                }
            }
            response = responseCol.size() > 0 ? responseCol.toArray((U[]) Array.newInstance(graphUpdateResponseClass, responseCol.size())) : null;

        }finally {
            unsetContext();
        }
        if (log.isDebugEnabled())
            log.debug("Output from " + (clone ? "clone" : "mass-update") + ": " + Arrays.toString(response));
        return response;
    }


    /**
     * Query method with same functionality as {@link #_query(C)}
     * <p>
     * Only difference is this requires you to pass in a UOW so that you can scope this
     * methods execution within as existing transaction. For this reason it is not expected
     * that this service will be exposed as an external service.
     * @param graphCriteria the graph criteria.
     * @param uow <b>MANDATORY</b> Unit of work that this process will use
     * @return An array of Graphs, the content of this graph is based on the result filter 'rules', as provided in the criteria object.
     * @throws FrameworkException Thrown if there is some kind of runtime or architecture problem
     * @throws ApplicationExceptions Thrown if there are any application logic errors
     * while processing. This exception contains a list of exceptions which detail what the
     * root cause of the problem is.
     */
    public G[] localQuery(C graphCriteria, UOW uow) throws FrameworkException, ApplicationExceptions {
        if (log.isDebugEnabled())
            log.debug("Input to localQuery: " + graphCriteria);

        // Build the Criteria
        Criteria c = createCriteria(graphCriteria);

        // Execute the query and obtain the results
        G[] output = runQuery(graphCriteria, c, uow);

        if (log.isDebugEnabled())
            log.debug("Output from localQuery: " + Arrays.toString(output));
        return output;
    }

    /**
     * Update method with same functionality as {@link #_update(G[])}
     * <p>
     * The differences are <ul>
     * <li>It requires you to pass in a UOW so that you can scope this
     * methods execution within as existing transaction. For this reason it is not expected
     * that this service will be exposed as an external service.
     * <li>It only works on a single graph at once, so you have to call it multiple times
     * if you want to update multiple graphs.
     * <li>It throws normal exceptions, it does not create the response with
     * the exceptions wrapped inside.
     * </ul>
     * @param path This is the source path of this graph, used when processing a more complex
     * tree, where this is the path to get to this root object being processed
     * @param graph <b>(MANDATORY)</b> The object graph being used to Create/Update the domain objects
     * @param uow <b>(MANDATORY)</b> Unit of work that this process will use
     * @return A GraphDataObject with just the key-fields of the root object will be returned in CREATE mode. Else a null will be returned.
     * @throws FrameworkException Thrown if there is some kind of runtime or architecture problem
     * @throws ApplicationExceptions Thrown if there are any application logic errors
     * while processing. This exception contains a list of exceptions which detail what the
     * root cause of the problem is.
     */
    public G localUpdate(String path, G graph, UOW uow) throws FrameworkException, ApplicationExceptions {
        G output = null;
        if (log.isDebugEnabled())
            log.debug("Create/Update Input Graph Object: " + path + '\n' + TransformerUtils.printGraph(graph));
        try {
            addContext();
            output = (G) DataTransformer.updateGraph(path, graph, uow, createHandler(uow));
            if (log.isDebugEnabled())
                log.debug(output != null ? "Entry created " + output : "Entry updated");
        }finally {
            unsetContext();
        }
        return output;
    }

    /**
     * Update method with same functionality as {@link #_update(G[])}
     * <p>
     * The differences are <ul>
     * <li>It requires you to pass in a UOW so that you can scope this
     * methods execution within as existing transaction. For this reason it is not expected
     * that this service will be exposed as an external service.
     * <li>All graphs are scoped within the same ACID transaction.
     * <li>It throws normal exceptions, it does not create the response with
     * the exceptions wrapped inside.
     * </ul>
     * @param path This is the source path upto the input graphs, used when processing a more complex
     * tree, where this is the path to get to the root object being processed.
     * @param graphs <b>(MANDATORY)</b> The object graphs being used to Create/Update the domain objects
     * @param uow <b>(MANDATORY)</b> Unit of work that this process will use
     * @return An array of GraphDataObject instances with just the key-fields of the root object will be returned in CREATE mode. Else a null will be returned.
     * @throws FrameworkException Thrown if there is some kind of runtime or architecture problem
     * @throws ApplicationExceptions Thrown if there are any application logic errors
     * while processing. This exception contains a list of exceptions which detail what the
     * root cause of the problem is.
     */
    public G[] localUpdate(String path, G[] graphs, UOW uow) throws FrameworkException, ApplicationExceptions {
        // Loop through each graph
        Collection<G> outputCol = new LinkedList<G>();
        GraphMapping graphMapping = MappingFactory.getInstance(graphDataClass);
        for (int i = 0; i < graphs.length; i++) {
            String p = (path != null ? path + '.' : "") + graphMapping.getDomainClassShortName() + '[' + i + ']';
            G output = localUpdate(p, graphs[i], uow);
            if (output != null)
                outputCol.add(output);
        }
        G[] response = outputCol.size() > 0 ? outputCol.toArray((G[]) Array.newInstance(graphDataClass, outputCol.size())) : null;
        if (log.isDebugEnabled())
            log.debug(toString(graphs, response));
        return response;
    }

    /**
     * Builds a Criteria object for use with the persistence engine, based on the input graph criteria.
     * @param graphCriteria the input graph criteria.
     * @return a Criteria object for use with the persistence engine, based on the input graph criteria.
     */
    protected Criteria createCriteria(C graphCriteria) {
        try {
            // Build the Criteria Object, use a new one if one was not supplied
            if (graphCriteria == null)
                graphCriteria = graphCriteriaClass.newInstance();

            return graphCriteria.returnQueryClause(null);
        } catch (Exception e) {
            String s = "Error in creating instance of GraphCriteria";
            log.error(s, e);
            throw new RuntimeException(s, e);
        }
    }

    /**
     * Runs the query based on the inputs.
     * @param graphCriteria the graph criteria.
     * @param criteria the criteria to be used for generating the database query.
     * @param uow <b>MANDATORY</b> Unit of work that this process will use
     * @return An array of Graphs, the content of this graph is based on the result filter 'rules', as provided in the graph criteria.
     * @throws FrameworkException Thrown if there is some kind of runtime or architecture problem
     * @throws ApplicationExceptions Thrown if there are any application logic errors
     * while processing. This exception contains a list of exceptions which detail what the
     * root cause of the problem is.
     */
    protected G[] runQuery(C graphCriteria, Criteria criteria, UOW uow) throws FrameworkException, ApplicationExceptions {
        try {

            Collection<G> graphs = new LinkedList<G>();
            GraphMapping graphMapping = MappingFactory.getInstance(graphDataClass);
            MappingFilter mappingFilter = null; //create an instance only if a row is found
            createHandler(uow);

            if (handler != null)
                handler.preQuery(null, criteria, graphCriteria, graphMapping.getDomainClass());

            for (Object domain : uow.query(criteria)) {
                if (mappingFilter == null)
                    mappingFilter = MappingFilter.getInstance(graphMapping, graphCriteria.getResultGraphRules());
                G graph = graphDataClass.newInstance();
                DataTransformer.buildGraphFromDomain(domain, graph, graphMapping, mappingFilter, null, false, graphCriteria, handler);
                graphs.add(graph);
            }
            return graphs.toArray((G[]) Array.newInstance(graphDataClass, graphs.size()));
        } catch (Exception e) {
            throw ExceptionHelper.throwAFR(e);
        }

    }

    /**
     * Runs a query to find the total number of records returned by the input criteria in the absence of the output-limiting parameters.
     * @param graphCriteria the graph criteria.
     * @param criteria the criteria used for generating the database query.
     * @param uow The UOW.
     * @param response The response being generated by the query.
     * @param rowsCount The number of rows retrieved by the query in the presence of output-limiting parameters.
     * @throws FrameworkException if any system error occurs.
     */
    protected void stampTotalRecords(C graphCriteria, Criteria criteria, UOW uow, Q response, int rowsCount) throws FrameworkException {
        // do not find total records, only if not asked to
        if (graphCriteria.getFindTotalRecords() == null || graphCriteria.getFindTotalRecords()) {
            int objectStart = graphCriteria.getObjectStart() == null ? 0 : graphCriteria.getObjectStart();
            int objectLimit = graphCriteria.getObjectLimit() == null ? 0 : graphCriteria.getObjectLimit();
            if (objectStart > 0 || objectLimit > 0) {
                if (objectStart <= 0 && rowsCount < objectLimit) {
                    response.setTotalRecords(rowsCount);
                } else {
                    criteria.setFirstResult(null);
                    criteria.setMaxResults(null);
                    criteria.clearOrderBy();
                    criteria.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
                    Iterator itr = uow.query(criteria).iterator();
                    if (itr.hasNext()) {
                        Number count = (Number) ((Map) itr.next()).get(Criteria.ID_FUNCTION_COUNT);
                        response.setTotalRecords(count != null ? count.intValue() : 0);
                    }
                }
            } else {
                response.setTotalRecords(rowsCount);
            }
        }
    }

    /**
     * Creates a GraphQueryResponse instance.
     * @param graphs the graphs to be added to the response.
     * @param error error, if any.
     * @return a GraphQueryResponse instance.
     */
    protected Q createGraphQueryResponse(G[] graphs, Throwable error) {
        try {
            Q graphQueryResponse = graphQueryResponseClass.newInstance();
            if (graphs != null)
                QUERY_GRAPHS_FIELD.set(graphQueryResponse, graphs); //graphQueryResponse.setGraphs(graphs);
            if (error != null)
                graphQueryResponse.setErrors(ServiceError.generate(error));
            return graphQueryResponse;
        } catch (Exception e) {
            String s = "Error in creating instance of GraphQueryResponse";
            log.error(s, e);
            throw new RuntimeException(s, e);
        }
    }

    /**
     * Creates a GraphUpdateResponse instance.
     * @param source the source Graph.
     * @param error error, if any.
     * @return a GraphUpdateResponse instance.
     */
    protected U createGraphUpdateResponse(G source, Throwable error) {
        try {
            U graphUpdateResponse = graphUpdateResponseClass.newInstance();
            if (source != null)
                UPDATE_SOURCE_FIELD.set(graphUpdateResponse, source); //graphUpdateResponse.setSource(source);
            if (error != null)
                graphUpdateResponse.setErrors(ServiceError.generate(error));
            return graphUpdateResponse;
        } catch (Exception e) {
            String s = "Error in creating instance of GraphUpdateResponse";
            log.error(s, e);
            throw new RuntimeException(s, e);
        }
    }

    /**
     * Creates an ITransformationHandler instance.
     * The handler instance is stored internally, so that subsequent invocations reuse the same instance.
     * @param uow <b>(MANDATORY)</b> Unit of work that this process will use.
     * @return an ITransformationHandler instance.
     */
    protected H createHandler(UOW uow) {
        try {
            if (handler == null && handlerClass != null)
                handler = (H) handlerClass.getConstructor(UOW.class).newInstance(uow);
            return handler;
        } catch (Exception e) {
            throw new RuntimeException("Can't Create Update Handler Object : " + handlerClass, e);
        }
    }

    /** Returns a text version of the response.
     * @param graphs The set of Object Graphs to Update/Create.
     * @param response The response.
     * @return a text version of the response.
     */
    protected String toString(G[] graphs, Collection<U> response) {
        int createCount = 0, updateCount = 0, errorCount = 0;
        for (U responseElement : response) {
            if (responseElement.getErrors() == null || responseElement.getErrors().length == 0)
                ++createCount;
            else
                ++errorCount;
        }
        updateCount = graphs.length - createCount - errorCount;
        StringBuilder buf = new StringBuilder();
        if (createCount > 0)
            buf.append("Create completed on ").append(createCount).append(" row").append(createCount == 1 ? "" : "s");
        if (updateCount > 0)
            buf.append(buf.length() > 0 ? ", " : "").append("Update completed on ").append(updateCount).append(" row").append(updateCount == 1 ? "" : "s");
        if (errorCount > 0)
            buf.append(buf.length() > 0 ? ", " : "").append("Update failed on ").append(errorCount).append(" row").append(errorCount == 1 ? "" : "s");
        return buf.toString();
    }

    /** Returns a text version of the response.
     * @param graphs The set of Object Graphs to Update/Create.
     * @param response The response.
     * @return a text version of the response.
     */
    protected String toString(G[] graphs, G[] response) {
        int createCount = response != null ? response.length : 0;
        int updateCount = graphs.length - createCount;
        StringBuilder buf = new StringBuilder();
        if (createCount > 0)
            buf.append("Create completed on ").append(createCount).append(" row").append(createCount == 1 ? "" : "s");
        if (updateCount > 0)
            buf.append(buf.length() > 0 ? ", " : "").append("Update completed on ").append(updateCount).append(" row").append(updateCount == 1 ? "" : "s");
        return buf.toString();
    }

    /**
     * Adds context information to Log4J's MDC object.
     * The simple classname of the current object is added as the ProcessName.
     * However, if a ProcessName already exists, then the simply classname is added as the SubProcessName.
     */
    protected void addContext() {
        String name = this.getClass().getSimpleName();
        String existingName = (String) MDC.get(BusinessEventLogMeta.PROCESS_NAME);
        if (existingName == null)
            MDC.put(BusinessEventLogMeta.PROCESS_NAME, name);
        else if (!existingName.equals(name))
            MDC.put(BusinessEventLogMeta.SUB_PROCESS_NAME, name);
    }

    /**
     * Unsets context information from Log4J's MDC object.
     * if a SubProcessName exists, them simply remove SubProcessName from MDC
     * However, if the SubProcessName doesn't exists, then remove ProcessName from MDC
     */
    protected void unsetContext() {
        if(MDC.get(BusinessEventLogMeta.SUB_PROCESS_NAME)!=null)
            MDC.remove(BusinessEventLogMeta.SUB_PROCESS_NAME);
        else
            MDC.remove(BusinessEventLogMeta.PROCESS_NAME);
    }

    /**
     * Creates a context to be added to the ApplicationExceptions object
     * that may have been thrown while processing the input Graph.
     */
    protected String createContextForApplicationExceptions(G graph, String path, int i, GraphMapping graphMapping) {
        StringBuilder context = new StringBuilder();

        //Add domain label and row-no to the context
        context.append(TransformerUtils.findDomainLabel(graphMapping.getDomainClass())).append('[').append(i).append(']');

        //Add key information to the context
        try {
            Map<String, Object> keys = new LinkedHashMap<String, Object>();
            if (TransformerUtils.fillInKeys(path, graph, graphMapping, keys)) {
                for (Map.Entry<String, Object> me : keys.entrySet()) {
                    String name = PersistentHelper.getLabelToken(graphMapping.getDomainClassName(), graphMapping.getDomainFieldName(me.getKey()));
                    String value = Formatter.format(me.getValue());
                    context.append(',').append(' ').append(name != null ? name : me.getKey()).append('=').append(value);
                }
            }
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Error in determining keys for creating the ApplicationExceptionContext", e);
        }
        return context.toString();
    }
}
