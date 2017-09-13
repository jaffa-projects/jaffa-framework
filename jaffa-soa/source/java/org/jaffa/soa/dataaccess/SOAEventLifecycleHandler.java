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

package org.jaffa.soa.dataaccess;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.ILifecycleHandler;
import org.jaffa.persistence.exceptions.PostAddFailedException;
import org.jaffa.persistence.exceptions.PostDeleteFailedException;
import org.jaffa.persistence.exceptions.PostLoadFailedException;
import org.jaffa.persistence.exceptions.PostUpdateFailedException;
import org.jaffa.persistence.exceptions.PreAddFailedException;
import org.jaffa.persistence.exceptions.PreDeleteFailedException;
import org.jaffa.persistence.exceptions.PreUpdateFailedException;
import org.jaffa.rules.meta.RuleMetaData;

/**
 * SOA Event Lifecycle Handler contains rule meta data for a single raise SOA event rule. When a lifecycle
 * event occurs, the handler checks if the rule is applicable to the event and then raises the SOA event
 * if necessary.
 * <p/>
 * Created by ndzwill on 9/8/2017.
 */
public class SOAEventLifecycleHandler extends SOAEventBaseHandler implements ILifecycleHandler {

    private ILifecycleHandler targetBean;

    /**
     * Set the rule meta data for the handler
     *
     * @param ruleMetaData the rule meta data for the handler
     */
    public SOAEventLifecycleHandler(RuleMetaData ruleMetaData) {
        super(ruleMetaData);
    }

    @Override
    public void preAdd() throws PreAddFailedException {
        try {
            raiseSOAEvent(ILifecycleHandler.LIFECYCLE_PRE_ADD, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
    }

    @Override
    public void postAdd() throws PostAddFailedException {
        try {
            raiseSOAEvent(ILifecycleHandler.LIFECYCLE_POST_ADD, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
    }

    @Override
    public void preUpdate() throws PreUpdateFailedException {
        try {
            raiseSOAEvent(ILifecycleHandler.LIFECYCLE_PRE_UPDATE, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
    }

    @Override
    public void postUpdate() throws PostUpdateFailedException {
        try {
            raiseSOAEvent(ILifecycleHandler.LIFECYCLE_POST_UPDATE, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
    }

    @Override
    public void preDelete() throws PreDeleteFailedException {
        try {
            raiseSOAEvent(ILifecycleHandler.LIFECYCLE_PRE_DELETE, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
    }

    @Override
    public void postDelete() throws PostDeleteFailedException {
        try {
            raiseSOAEvent(ILifecycleHandler.LIFECYCLE_POST_DELETE, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
    }

    @Override
    public void postLoad() throws PostLoadFailedException {
        try {
            raiseSOAEvent(ILifecycleHandler.LIFECYCLE_POST_LOAD, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
    }

    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        try {
            raiseSOAEvent(ILifecycleHandler.LIFECYCLE_VALIDATE, targetBean);
        } catch (ApplicationExceptions | FrameworkException e) {
            // swallow this exception, we don't want to prevent the handler chain from executing
        }
    }

    @Override
    public void setTargetBean(ILifecycleHandler iLifecycleHandler) {
        this.targetBean = iLifecycleHandler;
    }
}
