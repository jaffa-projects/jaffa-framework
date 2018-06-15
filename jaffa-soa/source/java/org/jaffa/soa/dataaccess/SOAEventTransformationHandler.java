/*
 *  ====================================================================
 *  JAFFA - Java Application Framework For All
 *
 *  Copyright (c) 2017 JAFFA Development Group
 *
 *      This library is free software; you can redistribute it and/or
 *      modify it under the terms of the GNU Lesser General Public
 *      License as published by the Free Software Foundation; either
 *      version 2.1 of the License, or (at your option) any later version.
 *
 *      This library is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *      Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public
 *      License along with this library; if not, write to the Free Software
 *      Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  Redistribution and use of this software and associated documentation ("Software"),
 *  with or without modification, are permitted provided that the following conditions are met:
 *  1.	Redistributions of source code must retain copyright statements and notices.
 *          Redistributions must also contain a copy of this document.
 *  2.	Redistributions in binary form must reproduce the above copyright notice,
 *  	this list of conditions and the following disclaimer in the documentation
 *  	and/or other materials provided with the distribution.
 *  3.	The name "JAFFA" must not be used to endorse or promote products derived from
 *  	this Software without prior written permission. For written permission,
 *  	please contact mail to: jaffagroup@yahoo.com.
 *  4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  	appear in their names without prior written permission.
 *  5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 *  THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 *  ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 *  USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 *  OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 *  SUCH DAMAGE.
 *  ====================================================================
 */

/*
 * SOAEventTransformationHandler.java
 *
 */
package org.jaffa.soa.dataaccess;

import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.UOW;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.soa.graph.GraphCriteria;

import java.util.List;

import static org.jaffa.soa.dataaccess.ITransformationHandlerFactory.*;

/**
 * SOA Event Transformation Handler contains rule meta data for a single raise SOA event rule. When a lifecycle
 * event occurs, the handler checks if the rule is applicable to the event and then raises the SOA event
 * if necessary.
 * <p/>
 * Created by BobbyC on 8/26/2017.
 */
public class SOAEventTransformationHandler extends SOAEventBaseHandler implements ITransformationHandler, ITransformationHandlerProvider {

    private ITransformationHandler targetBean;

    /**
     * Set the rule meta data for the handler
     *
     * @param ruleMetaData the rule meta data for the handler
     */
    public SOAEventTransformationHandler(RuleMetaData ruleMetaData) {
        super(ruleMetaData);
    }

    @Override
    public boolean isCloning() {
        try {
            raiseSOAEvent(TransformationHandler.LIFECYCLE_IS_CLONING, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
        return false;
    }

    @Override
    public void setCloning(boolean cloning) {
        try {
            raiseSOAEvent(TransformationHandler.LIFECYCLE_SET_CLONING, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
    }

    @Override
    public boolean isChangeDone() {
        try {
            raiseSOAEvent(TransformationHandler.LIFECYCLE_IS_CHANGE_DONE, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
        return false;
    }

    @Override
    public void setChangeDone(boolean changeDone) {
        try {
            raiseSOAEvent(TransformationHandler.LIFECYCLE_SET_CHANGE_DONE, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
    }

    @Override
    public void changeDone(String path, Object source, Object target, UOW uow) throws ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target, uow};
        raiseSOAEvent(TransformationHandler.LIFECYCLE_CHANGE_DONE, targetBean, uow, args);
    }

    @Override
    public void startBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target};
        raiseSOAEvent(SOAEventTransformationHandler.LIFECYCLE_START_BEAN, targetBean, null, args);
    }

    @Override
    public void endBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target};
        raiseSOAEvent(TransformationHandler.LIFECYCLE_END_BEAN, targetBean, null, args);
    }

    @Override
    public void startBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target};
        raiseSOAEvent(SOAEventTransformationHandler.LIFECYCLE_START_BEAN_DELETE, targetBean, null, args);
    }

    @Override
    public void endBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target};
        raiseSOAEvent(SOAEventTransformationHandler.LIFECYCLE_END_BEAN_DELETE, targetBean, null, args);
    }

    @Override
    public void startBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target};
        raiseSOAEvent(SOAEventTransformationHandler.LIFECYCLE_START_BEAN_ADD, targetBean, null, args);
    }

    @Override
    public void endBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        raiseSOAEvent(SOAEventTransformationHandler.LIFECYCLE_END_BEAN_ADD, targetBean);
    }

    @Override
    public void startBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        raiseSOAEvent(SOAEventTransformationHandler.LIFECYCLE_START_BEAN_UPDATE, targetBean);
    }

    @Override
    public void endBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target};
        raiseSOAEvent(SOAEventTransformationHandler.LIFECYCLE_END_BEAN_UPDATE, targetBean, null, args);
    }

    @Override
    public void startBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target,newGraph};
        raiseSOAEvent(TransformationHandler.LIFECYCLE_START_BEAN_CLONE, targetBean, null, args);
    }

    @Override
    public void endBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target,newGraph};
        raiseSOAEvent(TransformationHandler.LIFECYCLE_END_BEAN_CLONE, targetBean, null, args);
    }

    @Override
    public void startBeanMassUpdate(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target,newGraph};
        raiseSOAEvent(TransformationHandler.LIFECYCLE_START_BEAN_MASS_UPDATE, targetBean, null, args);
    }

    @Override
    public void endBeanMassUpdate(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target, newGraph};
        raiseSOAEvent(TransformationHandler.LIFECYCLE_END_BEAN_MASS_UPDATE, targetBean, null, args);
    }

    @Override
    public Criteria preQuery(String path, Criteria criteria, GraphCriteria originalCriteria, Class domain) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,criteria, originalCriteria, domain};
        raiseSOAEvent(TransformationHandler.LIFECYCLE_PRE_QUERY, targetBean, null, args);
        return null;
    }

    @Override
    public void endBeanLoad(String path, Object source, Object target, MappingFilter filter, GraphCriteria originalCriteria) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target,filter,originalCriteria};
        raiseSOAEvent(TransformationHandler.LIFECYCLE_END_BEAN_LOAD, targetBean, null, args);
    }

    @Override
    public void prevalidateBean(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        Object[] args = {path,source,target};
        raiseSOAEvent(TransformationHandler.LIFECYCLE_PREVALIDATE_BEAN, targetBean, null, args);
    }

    @Override
    public List<ITransformationHandler> getTransformationHandlers() {
        // this is used by the handlers and does not need to check for firing events
        return null;
    }

    @Override
    public void setTargetBean(ITransformationHandler targetBean) {
        this.targetBean = targetBean;
    }

    @Override
    public void startQueryService() throws FrameworkException, ApplicationExceptions {

    }

    @Override
    public void startUpdateService() throws FrameworkException, ApplicationExceptions {

    }

    @Override
    public void endService() throws FrameworkException, ApplicationExceptions {

    }

    @Override
    public ITransformationHandler getHandler() {
        return new SOAEventTransformationHandler(ruleMetaData);
    }
}
