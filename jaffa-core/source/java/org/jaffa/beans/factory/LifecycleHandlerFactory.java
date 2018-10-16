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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory used to register {@link ILifecycleHandler}s for a specific Lifecycle Handler type.
 * The handlers will be added in a specific order to allow for custom code to be executed before or after
 * all lifecycle events.  This factory bean can be retrieved from the application context or injected into a component
 * or configuration so new handlers can be added - allowing customer builds to add to the base set of handlers.
 * <p/>
 * Created by ndzwill on 9/8/2017.
 */
public class LifecycleHandlerFactory implements ILifecycleHandlerFactory {

    private Map<Class<?>, List<ILifecycleHandlerProvider>> prependedHandlerProviders = new HashMap<>();
    private Map<Class<?>, List<ILifecycleHandlerProvider>> appendedHandlerProviders = new HashMap<>();

    @Override
    public void addPrependedHandlerProvider(Class<?> clazz, ILifecycleHandlerProvider provider) {
        if (!prependedHandlerProviders.containsKey(clazz)) {
            prependedHandlerProviders.put(clazz, new ArrayList<ILifecycleHandlerProvider>());
        }
        prependedHandlerProviders.get(clazz).add(0, provider);
    }

    @Override
    public void addAppendedHandlerProvider(Class<?> clazz, ILifecycleHandlerProvider provider) {
        if (!appendedHandlerProviders.containsKey(clazz)) {
            appendedHandlerProviders.put(clazz, new ArrayList<ILifecycleHandlerProvider>());
        }
        appendedHandlerProviders.get(clazz).add(provider);
    }

    @Override
    public List<ILifecycleHandler> getPrependedHandlers(ILifecycleHandler handler) {
        List<ILifecycleHandler> handlers = null;
        if (prependedHandlerProviders.containsKey(handler.getClass())) {
            handlers = new ArrayList<>();
            for (ILifecycleHandlerProvider provider : prependedHandlerProviders.get(handler.getClass())) {
                handlers.add(provider.getHandler());
            }
        }
        Class clazz = handler.getClass();
        while(clazz!=null){
            clazz = clazz.getSuperclass();
            if(clazz!=null && prependedHandlerProviders.containsKey(clazz)){
                handlers = handlers != null ? handlers : new ArrayList<>();
                for (ILifecycleHandlerProvider provider : prependedHandlerProviders.get(clazz)) {
                    handlers.add(provider.getHandler());
                }
            }
        }
        return handlers;
    }

    @Override
    public List<ILifecycleHandler> getAppendedHandlers(ILifecycleHandler handler) {
        List<ILifecycleHandler> handlers = null;
        if (appendedHandlerProviders.containsKey(handler.getClass())) {
            handlers = new ArrayList<>();
            for (ILifecycleHandlerProvider provider : appendedHandlerProviders.get(handler.getClass())) {
                handlers.add(provider.getHandler());
            }
        }
        Class clazz = handler.getClass();
        while(clazz!=null){
            clazz = clazz.getSuperclass();
            if(clazz!=null && appendedHandlerProviders.containsKey(clazz)){
                handlers = handlers != null ? handlers : new ArrayList<>();
                for (ILifecycleHandlerProvider provider : appendedHandlerProviders.get(clazz)) {
                    handlers.add(provider.getHandler());
                }
            }
        }
        return handlers;
    }
}
