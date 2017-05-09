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

package org.jaffa.components.maint;

import org.jaffa.presentation.portlet.component.Component;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.EventObject;

/** This is an abstract implementation of the IUpdateComponent interface.
 * All the Update components should extend this component.
 *
 * @author  GautamJ
 */
public abstract class UpdateComponent extends Component implements IUpdateComponent {

    private Collection m_updateListeners;


    /** Adds a listener.
     * @param listener the listener.
     */
    public void addUpdateListener(IUpdateListener listener) {
        if (m_updateListeners == null)
            m_updateListeners = new HashSet();
        m_updateListeners.add(listener);
    }

    /** Removes a listener.
     * @param listener the listener.
     * @return true if the listener was removed.
     */
    public boolean removeUpdateListener(IUpdateListener listener) {
        if (m_updateListeners != null)
            return m_updateListeners.remove(listener);
        else
            return false;
    }

    /** This clears the internal collection of listeners.
     * It then invokes the quit() method of the base class.
     */
    public void quit() {
        if (m_updateListeners != null) {
            m_updateListeners.clear();
            m_updateListeners = null;
        }
        super.quit();
    }

    /** Returns a Collection of IUpdateListener objects.
     * @return a Collection of IUpdateListener objects.
     */
    protected Collection getUpdateListeners() {
        return m_updateListeners;
    }

    /** Invokes the updateDone() method of the registered IUpdateListener objects in the same thread.
     */
    protected void invokeUpdateListeners() {
        invokeUpdateListeners(null);
    }

    /** Invokes the updateDone() method of the registered IUpdateListener objects in the same thread.
     * @param eventObject The EventObject which will probably contain the component itself.
     */
    protected void invokeUpdateListeners(EventObject eventObject) {
        if (m_updateListeners != null) {
            if (eventObject == null)
                eventObject = new EventObject(this);

            for (Iterator i = m_updateListeners.iterator(); i.hasNext(); )
                ( (IUpdateListener) i.next() ).updateDone(eventObject);
        }
    }

}
