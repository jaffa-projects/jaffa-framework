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

package org.jaffa.persistence;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.exceptions.*;

/**
 * The base lifecycle methods of a persistent object.
 * <p/>
 * Created by ndzwill on 9/8/2017.
 */
public interface ILifecycleHandler {

    String LIFECYCLE_PRE_ADD = "preAdd()";
    String LIFECYCLE_POST_ADD = "postAdd()";
    String LIFECYCLE_PRE_UPDATE = "preUpdate()";
    String LIFECYCLE_POST_UPDATE = "postUpdate()";
    String LIFECYCLE_PRE_DELETE = "preDelete()";
    String LIFECYCLE_POST_DELETE = "postDelete()";
    String LIFECYCLE_POST_LOAD = "postLoad()";
    String LIFECYCLE_VALIDATE = "validate()";

    /**
     * This method is triggered by the UOW, before adding this object to the Add-Store, but after a UOW has been associated to the object.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PreAddFailedException if any error occurs during the process.
     */
    void preAdd() throws PreAddFailedException;

    /**
     * This method is triggered by the UOW, after adding this object to the Add-Store.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PostAddFailedException if any error occurs during the process.
     */
    void postAdd() throws PostAddFailedException;

    /**
     * This method is triggered by the UOW, before adding this object to the Update-Store.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PreUpdateFailedException if any error occurs during the process.
     */
    void preUpdate() throws PreUpdateFailedException;

    /**
     * This method is triggered by the UOW, after adding this object to the Update-Store.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PostUpdateFailedException if any error occurs during the process.
     */
    void postUpdate() throws PostUpdateFailedException;

    /**
     * This method is triggered by the UOW, before adding this object to the Delete-Store.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PreDeleteFailedException if any error occurs during the process.
     */
    void preDelete() throws PreDeleteFailedException;

    /**
     * This method is triggered by the UOW, after adding this object to the Delete-Store.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PostDeleteFailedException if any error occurs during the process.
     */
    void postDelete() throws PostDeleteFailedException;

    /**
     * This method is triggered by the UOW after a query loads this object.
     * A concrete persistent object should provide the implementation, if its necessary.
     *
     * @throws PostLoadFailedException if any error occurs during the process.
     */
    void postLoad() throws PostLoadFailedException;

    /**
     * This method is invoked before adding/updating a domain object.
     * This will perform the following tasks:
     * Will invoke the performForeignKeyValidations() method to ensure no invalid foreign-keys are set.
     * Will invoke PersistentHelper.checkMandatoryFields() to perform mandatory field checks.
     * Will invoke the Rules Engine to perform mandatory field checks.
     *
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    Indicates some system error
     */
    void validate() throws ApplicationExceptions, FrameworkException;

    /**
     * Sets the target instance of a LifecycleHandler on this instance of the handler.  When we add multiple
     * handlers into a list on the target bean, this will give each handler in the list access to the target
     * instance of the handler itself.
     *
     * @param targetBean the target instance of the lifecycle handler this instance is operating on.
     */
    void setTargetBean(ILifecycleHandler targetBean);
}
