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

import org.junit.Test;

import java.util.List;

import static org.jaffa.soa.dataaccess.ITransformationHandlerFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test basic functionality of the transformation handler factory.
 * <p/>
 * Created by ndzwill on 8/18/2017.
 */
public class TransformationHandlerFactoryTest {

    private ITransformationHandlerFactory target = new TransformationHandlerFactory();

    /**
     * Adds a handler to list of handlers to prepend.
     * If the class already has handlers, this will be added to the beginning of that list.
     * If this class does not already have handlers, a new list is created with this one.
     */
    @Test
    public void testAddPrependedHandler() {
        TestTransformationHandler handler = new TestTransformationHandler(0);

        // we expect no handlers to be returned yet
        List<ITransformationHandler> handlers = target.getPrependedHandlers(handler);
        assertNull("No handler expected", handlers);

        // add 1 handler and then see it get returned
        ITransformationHandlerProvider handler1 = new TestProvider(1);
        target.addPrependedHandler(handler.getClass(), handler1);
        handlers = target.getPrependedHandlers(handler);
        assertEquals("No handler expected", 1, handlers.size());
        assertEquals("Handler not expected", 1, ((TestTransformationHandler) handlers.get(0)).getId());

        // add 2 more and see that you get all 3
        ITransformationHandlerProvider handler2 = new TestProvider(2);
        ITransformationHandlerProvider handler3 = new TestProvider(3);
        target.addPrependedHandler(handler.getClass(), handler2);
        target.addPrependedHandler(handler.getClass(), handler3);
        handlers = target.getPrependedHandlers(handler);
        assertEquals("No handler expected", 3, handlers.size());
        assertEquals("Handler not expected", 3, ((TestTransformationHandler) handlers.get(0)).getId());
        assertEquals("Handler not expected", 2, ((TestTransformationHandler) handlers.get(1)).getId());
        assertEquals("Handler not expected", 1, ((TestTransformationHandler) handlers.get(2)).getId());
    }

    /**
     * Adds a handler to list of handlers to append.
     * If the class already has handlers, this will be added to the end of that list.
     * If this class does not already have handlers, a new list is created with this one.
     */
    @Test
    public void testAddAppendedHandler() {
        TestTransformationHandler handler = new TestTransformationHandler(0);

        // we expect no handlers to be returned yet
        List<ITransformationHandler> handlers = target.getAppendedHandlers(handler);
        assertNull("No handler expected", handlers);

        // add 1 handler and then see it get returned
        ITransformationHandlerProvider handler1 = new TestProvider(1);
        target.addAppendedHandler(handler.getClass(), handler1);
        handlers = target.getAppendedHandlers(handler);
        assertEquals("No handler expected", 1, handlers.size());
        assertEquals("Handler not expected", 1, ((TestTransformationHandler) handlers.get(0)).getId());

        // add 2 more and see that you get all 3
        ITransformationHandlerProvider handler2 = new TestProvider(2);
        ITransformationHandlerProvider handler3 = new TestProvider(3);
        target.addAppendedHandler(handler.getClass(), handler2);
        target.addAppendedHandler(handler.getClass(), handler3);
        handlers = target.getAppendedHandlers(handler);
        assertEquals("No handler expected", 3, handlers.size());
        assertEquals("Handler not expected", 1, ((TestTransformationHandler) handlers.get(0)).getId());
        assertEquals("Handler not expected", 2, ((TestTransformationHandler) handlers.get(1)).getId());
        assertEquals("Handler not expected", 3, ((TestTransformationHandler) handlers.get(2)).getId());
    }

    /**
     * We want this marker class to test the handler factory but do not need any implementation
     * of the methods.
     */
    private class TestTransformationHandler extends TransformationHandler {

        private int id;

        public TestTransformationHandler(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

    }

    private class TestProvider implements ITransformationHandlerProvider {

        private int id;

        public TestProvider(int id) {
            this.id = id;
        }

        @Override
        public ITransformationHandler getHandler() {
            return new TestTransformationHandler(id);
        }
    }
}
