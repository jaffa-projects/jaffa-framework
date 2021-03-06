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

package org.jaffa.beans.factory;

import org.jaffa.persistence.ILifecycleHandler;

import java.util.List;

/**
 * Factory used to register {@link ILifecycleHandler}s for a specific Lifecycle Handler type.
 * The handlers will be added in a specific order to allow for custom code to be executed before or after
 * all lifecycle events.  This factory bean can be retrieved from the application context or injected into a component
 * or configuration so new handlers can be added - allowing customer builds to add to the base set of handlers.
 * <p/>
 * Created by ndzwill on 9/8/2017.
 */
public interface ILifecycleHandlerFactory {

    /**
     * Used to get instances of LifecycleHandlers.  This allows the creation of
     * custom handlers on the fly so each handler can have the context of the instance of the class they are on.
     * <p/>
     * Created by ndzwill on 9/12/2017.
     */
    interface ILifecycleHandlerProvider {

        /**
         * Used to get instances of LifecycleHandlers.  This allows the creation of
         * custom handlers on the fly so each handler can have the context of the instance of the class they are on.
         */
        ILifecycleHandler getHandler();
    }

    /**
     * Adds a handler to be fired before the handler with the specified type.
     *
     * @param clazz    the type of handler to prepend this handler to.
     * @param provider provides unique instances of handlers when needed.
     */
    void addPrependedHandlerProvider(Class<?> clazz, ILifecycleHandlerProvider provider);

    /**
     * Adds a handler to be fired after the handler with the specified type.
     *
     * @param clazz    the type of handler to append this handler to.
     * @param provider provides unique instances of handlers when needed.
     */
    void addAppendedHandlerProvider(Class<?> clazz, ILifecycleHandlerProvider provider);

    /**
     * Gets all handlers with custom logic to fire before the input handler.
     *
     * @param handler the handler to get customer handlers for.
     * @return all handlers to be fired before the input handler or null if there are none.
     */
    List<ILifecycleHandler> getPrependedHandlers(ILifecycleHandler handler);

    /**
     * Gets all handlers with custom logic to fire after the input handler.
     *
     * @param handler the handler to get customer handlers for.
     * @return all handlers to be fired after the input handler or null if there are none.
     */
    List<ILifecycleHandler> getAppendedHandlers(ILifecycleHandler handler);
}
