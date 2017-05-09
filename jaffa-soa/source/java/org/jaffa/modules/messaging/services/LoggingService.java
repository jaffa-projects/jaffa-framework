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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.modules.messaging.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;
import org.jaffa.modules.messaging.services.configdomain.MessageInfo;
import org.jaffa.modules.messaging.services.configdomain.Param;

/** A helper class to set/unset the Log4J context based on the configuration of a POJO.
 */
public class LoggingService {

    private static final Logger log = Logger.getLogger(LoggingService.class);
    /** A thread variable to hold a stack of loggingContexts. Each loggingContext is a Map of key/value pairs. */
    private static ThreadLocal<Stack<Map<String, Object>>> t_loggingContext = new ThreadLocal<Stack<Map<String, Object>>>();

    /** Adds the appropriate elements to the Message Driven Context (MDC) of Log4J, as specified in the input messageConfig.
     * @param payload Any serializable object.
     * @param messageInfo the corresponding MessageInfo object, as specified in the configuration file.
     * @param userId the userId to be added to the MDC.
     * @param scheduledTaskId the scheduledTaskId to be added to the MDC.
     * @param messageId the messageId to be added to the MDC.
     */
    public static void setLoggingContext(Object payload, MessageInfo messageInfo, String userId, String scheduledTaskId, String messageId) {
        //Push the current MDC context into the thread-level stack
        Map<String, Object> currentLoggingContext = MDC.getContext() != null ? new HashMap<String, Object>(MDC.getContext()) : new HashMap<String, Object>();
        Stack<Map<String, Object>> stack = t_loggingContext.get();
        if (stack == null) {
            stack = new Stack<Map<String, Object>>();
            t_loggingContext.set(stack);
        }
        stack.push(currentLoggingContext);

        // Add elements to the MDC, based on the presence of loggingNames in the header information of the MessageConfig
        if (messageInfo.getHeader() != null && messageInfo.getHeader().getParam() != null) {
            for (Param param : messageInfo.getHeader().getParam()) {
                if (param.getLoggingName() != null) {
                    String key = param.getLoggingName().value();
                    try {
                        Object value = InitialContextFactrory.obtainParamValue(param, payload);
                        if (value != null)
                            MDC.put(key, value);
                        else
                            MDC.remove(key);
                    } catch (Exception e) {
                        if (log.isDebugEnabled())
                            log.debug("Error in obtaining value for the LoggingName " + key, e);
                    }
                }
            }
        }

        // Add some standard elements to the MDC
        if (userId != null)
            MDC.put(BusinessEventLogMeta.LOGGED_BY, userId);
        if (scheduledTaskId != null)
            MDC.put(BusinessEventLogMeta.SCHEDULED_TASK_ID, scheduledTaskId);
        if (messageId != null)
            MDC.put(BusinessEventLogMeta.MESSAGE_ID, messageId);
    }

    /** Remove the elements from the Message Driven Context (MDC) of Log4J, that may have been added by the call to setLoggingContext().
     * @param payload Any serializable object.
     * @param messageInfo the corresponding MessageInfo object, as specified in the configuration file.
     */
    public static void unsetLoggingContext(Object payload, MessageInfo messageInfo) {
        Stack<Map<String, Object>> stack = t_loggingContext.get();
        if (stack == null || stack.size() == 0)
            throw new UnsupportedOperationException("The unsetLoggingContext() method can only be called after a setLoggingContext()");

        // Remove the current context
        if (MDC.getContext() != null) {
            Set<String> keys = new HashSet<String>(MDC.getContext().keySet());
            for (String key : keys)
                MDC.remove(key);
        }

        // Now add the elements of the previous logging context into the MDC
        Map<String, Object> previousLoggingContext = stack.pop();
        for (Map.Entry<String, Object> me : previousLoggingContext.entrySet())
            MDC.put(me.getKey(), me.getValue());
    }
}
