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
package org.jaffa.soa.events;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.ExceptionHelper;
import org.jaffa.util.OrderedPathMatchingResourcePatternResolver;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** ProcessEventHandler
 *
 * @author PavitraM
 * @version 1.0
 */
public class ProcessEventHandler {

    private static Logger log = Logger.getLogger(ProcessEventHandler.class);
    private static final String SOA_EVENT_LOCATION = "META-INF/soa-event.properties";
    public static String EXCEPTION_NO_EVENT_LOCATION_FILE = "exception." + ProcessEventHandler.class.getName() + ".noEventLocationFile";
    public static String EXCEPTION_CANNOT_FIND_PROPERTY = "exception." + ProcessEventHandler.class.getName() + ".cannotFindProperty";

    /**
     * Instantiates the input class
     * @param className Should be an instance of IProcessEvent
     * @return an instance of input class.
     * @throws ApplicationExceptions
     * @throws FrameworkException
     */
    private static IProcessEvent createProcessEvent(String className) throws ApplicationExceptions, FrameworkException {
        if (className != null) {
            try {
                return (IProcessEvent) Class.forName(className).newInstance();
            } catch (Exception e) {
                throw ExceptionHelper.throwAFR(e);
            }
        } else {
            throw new ApplicationExceptions(new ApplicationException(EXCEPTION_CANNOT_FIND_PROPERTY, new String[]{className}));
        }

    }

    /**
     * Returns a IProcessEvent instance, initialized with the values from the input graph.
     * @param processEventGraph
     * @return a IProcessEvent instance, initialized with the values from the input graph.
     * @throws ApplicationExceptions
     * @throws FrameworkException
     */
    public static IProcessEvent createProcessEvent(ProcessEventGraph processEventGraph) throws ApplicationExceptions, FrameworkException {
        // Do nothing if the 'cancelEvent' flag is true
        if (processEventGraph.getCancelEvent() != null && processEventGraph.getCancelEvent())
            return null;

        if (processEventGraph.getEventName() == null)
            throw new ApplicationExceptions(new ApplicationException(EXCEPTION_CANNOT_FIND_PROPERTY, new String[]{"null"}));

        try {
            Properties props = new Properties();
            InputStream input = null;
            try {
                OrderedPathMatchingResourcePatternResolver resolver = OrderedPathMatchingResourcePatternResolver.getInstance();
                Resource[] resources = resolver.getResources("classpath*:"+SOA_EVENT_LOCATION);
                if(resources!=null && resources.length > 0) {
                    for (Resource resource : resources) {
                        input = resource.getInputStream();
                        if (input != null) {
                            props.load(input);
                            if (log.isDebugEnabled())
                                log.debug("Loaded " + props.size() + " rule(s) from " + SOA_EVENT_LOCATION);
                        }
                    }
                }else{
                    throw new ApplicationExceptions(new ApplicationException(EXCEPTION_NO_EVENT_LOCATION_FILE, new String[]{SOA_EVENT_LOCATION}));
                }
            } catch (IOException e) {
                throw new ApplicationExceptions(new ApplicationException(EXCEPTION_NO_EVENT_LOCATION_FILE, new String[]{SOA_EVENT_LOCATION}));
            } finally {
                if (input != null)
                    input.close();
            }

            String className = props.getProperty(processEventGraph.getEventName());
            if (className != null) {
                //get the event class
                IProcessEvent processEvent = createProcessEvent(className);

                //reflect params
                if (processEventGraph.getParams() != null) {
                    Param[] params = processEventGraph.getParams();
                    for (Param param : params) {
                        if (log.isDebugEnabled())
                            log.debug("Processing " + param.getName() + " = " + param.getValue());
                        BeanHelper.setField(processEvent, param.getName(), param.getValue());
                    }
                }
                return processEvent;
            } else {
                throw new ApplicationExceptions(new ApplicationException(EXCEPTION_CANNOT_FIND_PROPERTY, new String[]{processEventGraph.getEventName()}));
            }
        } catch (Exception e) {
            throw ExceptionHelper.throwAFR(e);
        }
    }

    //call the execute method on the event
    public static void process(UOW uow, ProcessEventGraph processEventGraph) throws ApplicationExceptions, FrameworkException {
        try {
            if (log.isDebugEnabled())
                log.debug("Run the createEvent method ");
            if (processEventGraph != null) {
                IProcessEvent processEvent = createProcessEvent(processEventGraph);
                if (processEvent != null) {
                    if (log.isDebugEnabled())
                        log.debug("Run the execute method for " + processEventGraph.getEventName());

                    processEvent.execute(uow);
                    if (log.isDebugEnabled())
                        log.debug("ProcessEventGraph for " + processEventGraph.getEventName() + " executed...");
                }
            } else {
                throw new ApplicationExceptions(new ApplicationException(EXCEPTION_CANNOT_FIND_PROPERTY));
            }
        } catch (Exception e) {
            throw ExceptionHelper.throwAFR(e);
        }
    }
}
