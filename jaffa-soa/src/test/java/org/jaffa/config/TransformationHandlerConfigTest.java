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

package org.jaffa.config;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.soa.dataaccess.ITransformationHandler;
import org.jaffa.soa.dataaccess.ITransformationHandlerFactory;
import org.jaffa.soa.dataaccess.TransformationHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test the config class that adds registered handlers to Transformation Handlers.
 * <p/>
 * Created by ndzwill on 8/18/2017.
 */
public class TransformationHandlerConfigTest {

    private TransformationHandlerConfig target;
    private ITransformationHandlerFactory factory;

    /**
     * Setup the mock factory and inject it into the target.
     */
    @Before
    public void setup() {
        target = new TransformationHandlerConfig();
        factory = target.transformationHandlerFactory();
    }

    /**
     * This config registers a type with the StaticContext fallback map.
     */
    @Test
    public void testTransformationHandlerRegisterWithStaticContext() {
        String factoryMethod = StaticContext.getFactoryMethodMapFor(TransformationHandler.class);
        assertEquals("Did not register with static context", "transformationHandler", factoryMethod);
    }

    /**
     * If no additional handlers are found, only the input handler should be in the list.
     */
    @Test
    public void testTransformationHandlerNoCustomerHandlers() {
        TransformationHandler handler = new TransformationHandler();
        handler = target.transformationHandler(handler);

        // assert there is only 1 handler in the list and that it is the target handler
        assertEquals("One handler expected", 1, handler.getTransformationHandlers().size());
        assertEquals("Unexpected handler", handler, handler.getTransformationHandlers().get(0));
    }

    /**
     * Prepend handlers should be added before the input handler, and no handlers after that.
     */
    @Test
    public void testTransformationHandlerOnlyPrependedHandlers() {
        TransformationHandler handler = new TransformationHandler();
        List<ITransformationHandler> prependedHandlers = setupMockPrependedHandlers(handler);
        handler = target.transformationHandler(handler);

        // all the prepend handlers should be in order with the target handler last
        assertEquals("Unexpected number of handlers", prependedHandlers.size() + 1, handler.getTransformationHandlers().size());
        for (ITransformationHandler actualHandler : prependedHandlers) {
            int index = prependedHandlers.indexOf(actualHandler);
            assertEquals("Unexpected handler", actualHandler, handler.getTransformationHandlers().get(index));
        }

        // the target handler is last in the list
        assertEquals("Unexpected handler", handler, handler.getTransformationHandlers().get(prependedHandlers.size()));
    }

    /**
     * Append handlers should be added after the input handler, and no handlers before the input handler.
     */
    @Test
    public void testTransformationHandlerOnlyAppendedHandlers() {
        TransformationHandler handler = new TransformationHandler();
        List<ITransformationHandler> appendedHandlers = setupMockAppendedHandlers(handler);
        handler = target.transformationHandler(handler);

        // the target handler is first in the list
        assertEquals("Unexpected number of handlers", appendedHandlers.size() + 1, handler.getTransformationHandlers().size());
        assertEquals("Unexpected handler", handler, handler.getTransformationHandlers().get(0));

        // all the append handlers should be in order at the end of the list
        for (ITransformationHandler actualHandler : appendedHandlers) {
            int index = appendedHandlers.indexOf(actualHandler) + 1;
            assertEquals("Unexpected handler", actualHandler, handler.getTransformationHandlers().get(index));
        }
    }

    /**
     * Prepend handlers should be added before the input handler, and append handlers should be added after the input handler.
     */
    @Test
    public void testTransformationHandlerAllHandlers() {
        TransformationHandler handler = new TransformationHandler();
        List<ITransformationHandler> prependedHandlers = setupMockPrependedHandlers(handler);
        List<ITransformationHandler> appendedHandlers = setupMockAppendedHandlers(handler);
        handler = target.transformationHandler(handler);

        // expected size
        int totalSize = prependedHandlers.size() + appendedHandlers.size() + 1;

        // all the prepend handlers should be in order first in the list
        assertEquals("Unexpected number of handlers", totalSize, handler.getTransformationHandlers().size());
        for (ITransformationHandler actualHandler : prependedHandlers) {
            int index = prependedHandlers.indexOf(actualHandler);
            assertEquals("Unexpected handler", actualHandler, handler.getTransformationHandlers().get(index));
        }

        // the target handler is in the middle of the list
        assertEquals("Unexpected handler", handler, handler.getTransformationHandlers().get(prependedHandlers.size()));

        // all the append handlers should be in order at the end of the list
        int offset = prependedHandlers.size() + 1;
        for (ITransformationHandler actualHandler : appendedHandlers) {
            int index = appendedHandlers.indexOf(actualHandler);
            assertEquals("Unexpected handler", actualHandler, handler.getTransformationHandlers().get(index + offset));
        }
    }

    /**
     * Sets up a list of mock prepended handlers
     */
    private List<ITransformationHandler> setupMockPrependedHandlers(TransformationHandler targetHandler) {
        List<ITransformationHandler> handlers = new ArrayList<>();
        handlers.add(new TransformationHandler());
        handlers.add(new TransformationHandler());
        handlers.add(new TransformationHandler());
        factory.addPrependedHandler(targetHandler.getClass(), handlers.get(2));
        factory.addPrependedHandler(targetHandler.getClass(), handlers.get(1));
        factory.addPrependedHandler(targetHandler.getClass(), handlers.get(0));
        return handlers;
    }

    /**
     * Sets up a list of mock prepended handlers
     */
    private List<ITransformationHandler> setupMockAppendedHandlers(TransformationHandler targetHandler) {
        List<ITransformationHandler> handlers = new ArrayList<>();
        handlers.add(new TransformationHandler());
        handlers.add(new TransformationHandler());
        handlers.add(new TransformationHandler());
        factory.addAppendedHandler(targetHandler.getClass(), handlers.get(0));
        factory.addAppendedHandler(targetHandler.getClass(), handlers.get(1));
        factory.addAppendedHandler(targetHandler.getClass(), handlers.get(2));
        return handlers;
    }
}
