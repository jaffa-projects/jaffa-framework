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
package org.jaffa.persistence.logging;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;

/** A plugin implementing this interface will be invoked by the Persistence Engine to log ADD, UPDATE and DELETE events.
 */
public interface IPersistenceLoggingPlugin {

    /** This is called by the Persistence Engine when setting up a UOW. It may be used for initializing resources.
     * @param uow The UOW for a transaction.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void initialize(UOW uow) throws ApplicationExceptions, FrameworkException;

    /** This is called by the Persistence Engine after performing an actual Insert.
     * @param object The persistent object.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void add(IPersistent object) throws ApplicationExceptions, FrameworkException;

    /** This is called by the Persistence Engine after performing an actual Update.
     * @param object The persistent object.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void update(IPersistent object) throws ApplicationExceptions, FrameworkException;

    /** This is called by the Persistence Engine after performing an actual Delete.
     * @param object The persistent object.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void delete(IPersistent object) throws ApplicationExceptions, FrameworkException;

    /** This is called by the Persistence Engine just before committing the transaction.
     *
     * NOTE: If an implementation class wants to log the events for a transaction into the persistent store,
     * it may do so by obtaining the UOW from any of the persistent objects that have been logged so far,
     * and then using that UOW to add log data into the persistent store.
     * To persist the logs, the implementation class will need to exlicitly call the flush() method on the UOW.
     *
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void writeLog() throws ApplicationExceptions, FrameworkException;

    /** This is called by the Persistence Engine no matter what. It may be used for cleaning the resources that may have been acquired/created in the initialize method.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void clearLog() throws ApplicationExceptions, FrameworkException;
}
