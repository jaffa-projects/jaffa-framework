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

package org.jaffa.soa.dataaccess;

import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.soa.graph.GraphCriteria;

import java.util.List;

/**
 * Interface of an Transformation Handler that can be used to link in
 * additional functionallity to the transformation process when a
 * object graph is being transformed to either create, update or delete
 * domain objects.
 *
 * @author PaulE
 * @version 1.0
 */
public interface ITransformationHandler {

    String LIFECYCLE_CHANGE_DONE = "changeDone(..)";
    String LIFECYCLE_END_BEAN = "endBean(..)";
    String LIFECYCLE_END_BEAN_ADD = "endBeanAdd(..)";
    String LIFECYCLE_END_BEAN_CLONE = "endBeanClone(..)";
    String LIFECYCLE_END_BEAN_DELETE = "endBeanDelete(..)";
    String LIFECYCLE_END_BEAN_LOAD = "endBeanLoad(..)";
    String LIFECYCLE_END_BEAN_MASS_UPDATE = "endBeanMassUpdate(..)";
    String LIFECYCLE_END_BEAN_UPDATE = "endBeanUpdate(..)";
    String LIFECYCLE_IS_CHANGE_DONE = "isChangeDone()";
    String LIFECYCLE_IS_CLONING = "isCloning()";
    String LIFECYCLE_PRE_QUERY = "preQuery(..)";
    String LIFECYCLE_PREVALIDATE_BEAN = "prevalidateBean(..)";
    String LIFECYCLE_SET_CHANGE_DONE = "setChangeDone(..)";
    String LIFECYCLE_SET_CLONING = "setCloning(..)";
    String LIFECYCLE_START_BEAN = "startBean(..)";
    String LIFECYCLE_START_BEAN_ADD = "startBeanAdd(..)";
    String LIFECYCLE_START_BEAN_CLONE = "startBeanClone(..)";
    String LIFECYCLE_START_BEAN_DELETE = "startBeanDelete(..)";
    String LIFECYCLE_START_BEAN_MASS_UPDATE = "startBeanMassUpdate(..)";
    String LIFECYCLE_START_BEAN_UPDATE = "startBeanUpdate(..)";

    /**
     * Getter for the property cloning.
     * This property is set whenever we are in a clone process. This allows the UpdateHandlers to know if they are in clone mode
     * or just a regular update
     *
     * @return value of the property cloning.
     */
    boolean isCloning();

    /**
     * Setter for the property cloning.
     * This property is set whenever we are in a clone process. This allows the UpdateHandlers to know if they are in clone mode
     * or just a regular update
     *
     * @param cloning value of the property cloning
     */
    void setCloning(boolean cloning);

    /**
     * Getter for the property changeDone.
     * This property is set whenever a domain object is added, updated or deleted during the processing of a graph hierarchy.
     *
     * @return value of the property changeDone.
     */
    boolean isChangeDone();

    /**
     * Setter for the property changeDone.
     * This property is set whenever a domain object is added, updated or deleted during the processing of a graph hierarchy.
     *
     * @param changeDone value of the property changeDone
     */
    void setChangeDone(boolean changeDone);

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
    void changeDone(String path, Object source, Object target, UOW uow) throws ApplicationExceptions, FrameworkException;

    /**
     * Called prior to any processing of the target bean.
     *
     * @param path   This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationException  if any Application error occurs. SkipTransformException may be thrown to stop the processing of this graph.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    void startBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException;

    /**
     * Called after all processing has been completed on the target bean, this included its related beans.
     *
     * @param path   This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationException  if any Application error occurs.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    void endBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException;

    /**
     * Called prior to deleting the target bean from the persistent store.
     *
     * @param path   This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationException  if any Application error occurs. SkipTransformException may be thrown to stop the processing of this graph.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    void startBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException;

    /**
     * Called after deleting the target bean from the persistent store.
     *
     * @param path   This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationException  if any Application error occurs.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    void endBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException;

    /**
     * Called prior to adding the target bean to the persistent store.
     *
     * @param path   This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationException  if any Application error occurs. SkipTransformException may be thrown to stop the processing of this graph.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    void startBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException;

    /**
     * Called after adding the target bean to the persistent store.
     *
     * @param path   This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationException  if any Application error occurs.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    void endBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException;

    /**
     * Called prior to updating the target bean in the persistent store.
     *
     * @param path   This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationException  if any Application error occurs. SkipTransformException may be thrown to stop the processing of this graph.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    void startBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException;

    /**
     * Called after updating the target bean in the persistent store.
     *
     * @param path   This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationException  if any Application error occurs.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException    if an internal error occurs.
     */
    void endBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException;

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
    void startBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException;

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
    void endBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException;

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
    void startBeanMassUpdate(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException;

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
    void endBeanMassUpdate(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException;

    /**
     * Called prior to invoking a Query.
     * A handler may modify the input Criteria.
     * It may return a null to stop the query.
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
    Criteria preQuery(String path, Criteria criteria, GraphCriteria originalCriteria, Class domain) throws ApplicationException, ApplicationExceptions, FrameworkException;

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
    void endBeanLoad(String path, Object source, Object target, MappingFilter filter, GraphCriteria originalCriteria) throws ApplicationException, ApplicationExceptions, FrameworkException;

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
    void prevalidateBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException;

    /**
     * Gets all transformation handlers set on a concrete transformation handler.  This allows custom code to be
     * injected before or after lifecycle methods.
     */
    List<ITransformationHandler> getTransformationHandlers();

    /**
     * Sets the target instance of a TransformationHandler on this instance of the handler.  When we add multiple
     * handlers into a list on the target bean, this will give each handler in the list access to the target
     * instance of the handler itself.
     *
     * @param targetBean the target instance of the transformation handler this instance is operating on.
     */
    void setTargetBean(ITransformationHandler targetBean);

    /**
     * Called before the query is being executed.
     */
    void startQueryService() throws ApplicationException, ApplicationExceptions, FrameworkException;

    /**
     * Called before the update is being executed.
     */
    void startUpdateService() throws ApplicationException, ApplicationExceptions, FrameworkException;

    /**
     * Called after Service execution is completed.
     */
    void endService() throws ApplicationException, ApplicationExceptions, FrameworkException;

}
