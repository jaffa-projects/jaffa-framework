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

package org.jaffa.persistence.engines.jdbcengine.proxy;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.Persistent;
import org.apache.log4j.Logger;
import org.jaffa.rules.RulesEngine;

/** The PersistentInstanceInvocationHandler delegates all calls made to the IPersistent interface, to an instance of this class.
 * It extends the Persistent class and overrides the actualInstance() method to return the proxy object.
 */
public class PersistentDelegate extends Persistent {
    
    private static final Logger log = Logger.getLogger(PersistentDelegate.class);
    
    IPersistent m_proxy;

    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public PersistentDelegate() {
        StaticContext.configure(this);
    }

    /** This method returns the proxy object.
     * @return the persistent instance.
     */
    protected IPersistent actualInstance() {
        return m_proxy;
    }
    
    /** Invokes the update() method on the base class.
     */
    void invokeUpdate()
    throws ReadOnlyObjectException, AlreadyLockedObjectException, IllegalPersistentStateRuntimeException {
        super.update();
    }
    
    /** Invokes the addInitialValue() method on the base class.
     */
    void invokeAddInitialValue(String fieldName, Object initialValue) {
        super.addInitialValue(fieldName, initialValue);
    }
    
    /** This method is triggered by the UOW, before adding this object to the Add-Store, but after a UOW has been associated to the object.
     * This will perform the following tasks:
     *    Will invoke the Rules Engine to perform mandatory field checks.
     * @throws PreAddFailedException if any error occurs during the process.
     */
    public void preAdd() throws PreAddFailedException {
        if (log.isDebugEnabled())
            log.debug("Invoking the Dynamic Rules Engine to perform the mandatory rules on the Persistent object");
        try {
            RulesEngine.doMandatoryValidationsForDomainObject(this, this.getUOW());
        } catch (Exception e) {
            String str = "Exception thrown while invoking the Dynamic Rules Engine to perform the mandatory rules on the Persistent object";
            log.error(str, e);
            throw new PreAddFailedException(null, e);
        }
    }
    
    /** This method is triggered by the UOW, before adding this object to the Update-Store.
     * This will perform the following tasks:
     *    Will invoke the Rules Engine to perform mandatory field checks.
     * @throws PreUpdateFailedException if any error occurs during the process.
     */
    public void preUpdate() throws PreUpdateFailedException {
        if (log.isDebugEnabled())
            log.debug("Invoking the Dynamic Rules Engine to perform the mandatory rules on the Persistent object");
        try {
            RulesEngine.doMandatoryValidationsForDomainObject(this, this.getUOW());
        } catch (Exception e) {
            String str = "Exception thrown while invoking the Dynamic Rules Engine to perform the mandatory rules on the Persistent object";
            log.error(str, e);
            throw new PreUpdateFailedException(null, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        super.validate();
    }
}
