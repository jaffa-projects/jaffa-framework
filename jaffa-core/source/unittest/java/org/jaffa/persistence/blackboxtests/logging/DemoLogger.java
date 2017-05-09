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

package org.jaffa.persistence.blackboxtests.logging;

import java.io.StringWriter;
import java.util.Collection;
import java.util.LinkedList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.logging.IPersistenceLoggingPlugin;
import org.jaffa.util.ExceptionHelper;

/** An implementation of the IPersistenceLoggingPlugin for demo purposes only.
 * It logs the add/update/delete events to static collections.
 * A production-level implementation would probably log such events to the database.
 */
public class DemoLogger implements IPersistenceLoggingPlugin {
    
    // These collections hold the domain objects that have been added/updated/deleted in the current transaction
    private Collection<IPersistent> m_addedObjects = null;
    private Collection<IPersistent> m_updatedObjects = null;
    private Collection<IPersistent> m_deletedObjects = null;
    
    
    // These collections hold serialized versions of the domain objects that have been added/updated/deleted in all transactions
    // NOTE: They are not thread-safe and are for demo purposes only!
    public static Collection<String> c_addedSerializedObjects = new LinkedList<String>();
    public static Collection<String> c_updatedSerializedObjects = new LinkedList<String>();
    public static Collection<String> c_deletedSerializedObjects = new LinkedList<String>();
    
    
    
    /** This is called by the Persistence Engine when setting up a UOW. It may be used for initializing resources.
     * @param uow The UOW for a transaction.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void initialize(UOW uow) throws ApplicationExceptions, FrameworkException {
    }
    
    /** This is called by the Persistence Engine after performing an actual Insert.
     * @param obj The persistent object.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void add(IPersistent obj) throws ApplicationExceptions, FrameworkException {
        if (m_addedObjects == null)
            m_addedObjects = new LinkedList<IPersistent>();
        m_addedObjects.add(obj);
    }
    
    /** This is called by the Persistence Engine after performing an actual Update.
     * @param obj The persistent object.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void update(IPersistent obj) throws ApplicationExceptions, FrameworkException {
        if (m_updatedObjects == null)
            m_updatedObjects = new LinkedList<IPersistent>();
        m_updatedObjects.add(obj);
    }
    
    /** This is called by the Persistence Engine after performing an actual Delete.
     * @param obj The persistent object.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void delete(IPersistent obj) throws ApplicationExceptions, FrameworkException {
        if (m_deletedObjects == null)
            m_deletedObjects = new LinkedList<IPersistent>();
        m_deletedObjects.add(obj);
    }
    
    /** This is called by the Persistence Engine just before committing the transaction.
     * If an implementation class wants to log the events for a transaction into the persistent store,
     * it may do so by obtaining the UOW from any of the persistent objects that have been logged so far,
     * and then using that UOW to add log data into the persistent store.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void writeLog()throws ApplicationExceptions, FrameworkException {
        log(m_addedObjects, c_addedSerializedObjects);
        log(m_updatedObjects, c_updatedSerializedObjects);
        log(m_deletedObjects, c_deletedSerializedObjects);
    }
    
    /** This is called by the Persistence Engine no matter what. It may be used for cleaning the resources that may have been acquired/created in the initialize method.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void clearLog() throws ApplicationExceptions, FrameworkException {
    }
    
    private void log(Collection<IPersistent> objectsToLog, Collection<String> serializedObjects)
    throws ApplicationExceptions, FrameworkException {
        // NOTE: This is for demo purposes only.
        // It serializes the domain objects to XML and adds the resulting String to the output collection
        // A production-level implementation may probably add the resulting String to the database
        // In which case, do rememeber to invoke the flush() method on the UOW!
        if (objectsToLog != null) {
            try {
                for (IPersistent obj : objectsToLog) {
                    StringWriter writer = new StringWriter();
                    JAXBContext jc = JAXBContext.newInstance(new Class[] {obj.getClass()});
                    Marshaller marshaller = jc.createMarshaller();
                    marshaller.marshal(obj, writer);
                    serializedObjects.add(writer.toString());
                    writer.close();
                }
            } catch (Exception e) {
                throw ExceptionHelper.throwAFR(e);
            }
        }
    }
    
}
