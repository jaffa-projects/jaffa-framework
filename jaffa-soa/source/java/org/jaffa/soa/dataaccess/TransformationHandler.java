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
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

/*
 * TransformationHandler.java
 *
 */
package org.jaffa.soa.dataaccess;

import org.apache.log4j.Logger;
import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.soa.graph.GraphCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for a Transformation Handler so that all the methods in
 * the interface have a default implementation, which is to provide debug logging
 * that each event has been called.
 *
 * @author PaulE
 * @version 1.0
 */
public class TransformationHandler implements ITransformationHandler {

    private static Logger log = Logger.getLogger(TransformationHandler.class);
    private boolean changeDone;
    private boolean cloning = false;
    private boolean loggingActive = true;

    private List<ITransformationHandler> transformationHandlers = new ArrayList<>();

    /**
     * Pass this instance to the StaticContext to be configured.
     */
    public TransformationHandler() {
        StaticContext.configure(this);
    }

    /**
     * Pass this instance to the StaticContext to be configured.
     *
     * @param loggingActive true if this instance of the handler should log on all lifecycle methods.
     *                      In cases where multiple handlers are in a chain, we only want the main handler to log.
     *                      This defaults to true so must be manually set to false for custom handlers.
     */
    public TransformationHandler(boolean loggingActive) {
        this();
        this.loggingActive = loggingActive;
    }

    /**
     * Pass this instance to the StaticContext to be configured.
     */
    public TransformationHandler(UOW uow) {
        this();
    }

    /**
     * Adds a new handler to the beginning of the list of all handlers.
     */
    public void prependTransformationHandlers(List<ITransformationHandler> handlers) {
        transformationHandlers.addAll(0, handlers);
    }

    /**
     * Adds a new handler to the end of the list of all handlers.
     */
    public void appendTransformationHandlers(List<ITransformationHandler> handlers) {
        transformationHandlers.addAll(handlers);
    }

    /**
     * Gets the ordered list of transformation handlers to fire when invoking lifecycle events on this handler.
     */
    public List<ITransformationHandler> getTransformationHandlers() {
        return transformationHandlers;
    }

    /**
     * Getter for the property cloning.
     * This property is set whenever we are in a clone process. This allows the UpdateHandlers to know if they are in clone mode
     * or just a regular update
     *
     * @return value of the property cloning.
     */
    public boolean isCloning() {
        return cloning;
    }

    /**
     * Setter for the property cloning.
     * This property is set whenever we are in a clone process. This allows the UpdateHandlers to know if they are in clone mode
     * or just a regular update
     *
     * @param cloning value of the property changeDone
     */
    public void setCloning(boolean cloning) {
        this.cloning = cloning;
    }

    /**
     * Getter for the property changeDone.
     * This property is set whenever a domain object is added, updated or deleted during the processing of a graph hierarchy.
     *
     * @return value of the property changeDone.
     */
    public boolean isChangeDone() {
        return changeDone;
    }

    /**
     * Setter for the property changeDone.
     * This property is set whenever a domain object is added, updated or deleted during the processing of a graph hierarchy.
     *
     * @param changeDone value of the property changeDone
     */
    public void setChangeDone(boolean changeDone) {
        this.changeDone = changeDone;
    }

    /**
     * Called after all processing has been completed on the root graph.
     * This method will be invoked only if any domain object was actually added, updated or deleted.
     *
     * @param path   This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    public void changeDone(String path, Object source, Object target, UOW uow) throws ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : changeDone for " + path + " (Target=" + shortClassName(target) + ")");
    }

    public void endBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : endBean for " + path + " (Target=" + shortClassName(target) + ")");
    }

    public void endBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : endBeanAdd for " + path + " (Target=" + shortClassName(target) + ")");
    }

    public void endBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : endBeanDelete for " + path + " (Target=" + shortClassName(target) + ")");
    }

    public void endBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : endBeanUpdate for " + path + " (Target=" + shortClassName(target) + ")");
    }

    public void startBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : startBean for " + path + " (Target=" + shortClassName(target) + ")");
    }

    public void startBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : startBeanAdd for " + path + " (Target=" + shortClassName(target) + ")");
    }

    public void startBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : startBeanDelete for " + path + " (Target=" + shortClassName(target) + ")");
    }

    public void startBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : startBeanUpdate for " + path + " (Target=" + shortClassName(target) + ")");
    }

    /**
     * Called prior to adding the target bean to the persistent store during a clone process.
     *
     * @param path     This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source   the graph object being processed.
     * @param target   the corresponding domain object.
     * @param newGraph an additional graph object that contains override values for the properties in the source object.
     * @throws ApplicationException  if any Application error occurs. SkipTransformException may be thrown to stop the processing of this graph.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    public void startBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : startBeanClone for " + path + " (Target=" + shortClassName(target) + ")");
    }

    /**
     * Called after adding the target bean to the persistent store during a clone process.
     *
     * @param path     This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source   the graph object being processed.
     * @param target   the corresponding domain object.
     * @param newGraph an additional graph object that contains override values for the properties in the source object.
     * @throws ApplicationException  if any Application error occurs.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    public void endBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : endBeanClone for " + path + " (Target=" + shortClassName(target) + ")");
    }

    /**
     * Called prior to updating the target bean in the persistent store during a MassUpdate process.
     *
     * @param path     This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source   the graph object being processed.
     * @param target   the corresponding domain object.
     * @param newGraph an additional graph object that contains override values for the properties in the source object.
     * @throws ApplicationException  if any Application error occurs. SkipTransformException may be thrown to stop the processing of this graph.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    public void startBeanMassUpdate(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : startBeanMassUpdate for " + path + " (Target=" + shortClassName(target) + ")");
    }

    /**
     * Called after updating the target bean in the persistent store during a MassUpdate process.
     *
     * @param path     This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source   the graph object being processed.
     * @param target   the corresponding domain object.
     * @param newGraph an additional graph object that contains override values for the properties in the source object.
     * @throws ApplicationException  if any Application error occurs.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    public void endBeanMassUpdate(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : endBeanMassUpdate for " + path + " (Target=" + shortClassName(target) + ")");
    }

    /**
     * Called prior to invoking a Query.
     * A handler may modify the input Criteria.
     * It may return a null to stop the query.
     * The default implementation returns the input criteria as is.
     *
     * @param path             This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param criteria         the Criteria to be modified.
     * @param originalCriteria the original graph criteria.
     * @param domain           the domain class being queried.
     * @return a modified Criteria instance.
     * @throws ApplicationException  if any Application error occurs.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    public Criteria preQuery(String path, Criteria criteria, GraphCriteria originalCriteria, Class domain) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : preQuery for " + path);
        return criteria;
    }

    /**
     * Called after a bean has been loaded.
     *
     * @param path             This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source           the domain object that was just queried.
     * @param target           the graph object being molded.
     * @param filter           Filter object that it is used to control what fields are populated or the target objects.
     * @param originalCriteria the original graph criteria.
     * @throws ApplicationException  if any Application error occurs.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    public void endBeanLoad(String path, Object source, Object target, MappingFilter filter, GraphCriteria originalCriteria) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : endBeanLoad for " + path + " (Target=" + shortClassName(target) + ")");
    }

    /**
     * Called after the source graph has been validated and molded into the target domain object.
     * This handler may then be used to perform additional validations and/or set default data on the source graph.
     *
     * @param path   This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being validated.
     * @param target the domain object molded from the graph.
     * @throws ApplicationException  if any Application error occurs.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    public void prevalidateBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (log.isDebugEnabled() && loggingActive)
            log.debug("Handle Event : prevalidateBean for " + path + " (Target=" + shortClassName(target) + ")");
    }

    private String shortClassName(Object o) {
        return o != null ? o.getClass().getSimpleName() : null;
    }
}
