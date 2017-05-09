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
package org.jaffa.soa.services;

import org.jaffa.soa.services.event.HeaderParam;
import org.jaffa.soa.services.event.EventMessage;
import org.jaffa.soa.transformation.helper.MappingHelper;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;
import org.jaffa.soa.transformation.helper.XMLDateConverter;


/*
 * This class is for converting passed payloads to the EventMessage payload to publish
 * 
 */
public class EventMessageService {

    private static final Logger log = Logger.getLogger(EventMessageService.class);

    /**
     * This method will map the SOAEventQueueMessage to EventMessage.
     *
     * @param SOAEventQueueMessage
     * @return EventMessage
     */
    public static EventMessage getEventMessage(SOAEventQueueMessage eventQueueMessage) {
        if (log.isDebugEnabled()) {
            log.debug("Converting SOAEventQueueMessage to EventMessage");
        }
        EventMessage eventMessage = new EventMessage();
        eventMessage.setEventId(eventQueueMessage.getEventId());
        eventMessage.setEventName(eventQueueMessage.getEventName());
        eventMessage.setDescription(eventQueueMessage.getDescription());
        eventMessage.setCategory(eventQueueMessage.getCategory());
        eventMessage.setCreatedBy(eventQueueMessage.getCreatedBy());
        eventMessage.setCreatedOn(XMLDateConverter.getCalendarFromDate(eventQueueMessage.getCreatedOn() != null ? eventQueueMessage.getCreatedOn().getUtilDate() : new DateTime().getUtilDate()));
        org.jaffa.modules.messaging.services.HeaderParam[] headerParams = eventQueueMessage.getHeaderParams();
        if (headerParams != null) {
            for (org.jaffa.modules.messaging.services.HeaderParam headerParam : headerParams) {
                HeaderParam eventHeaderParams = new HeaderParam();
                eventHeaderParams.setName(headerParam.getName());
                eventHeaderParams.setValue(headerParam.getValue());
                eventMessage.getHeaderParams().add(eventHeaderParams);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Successfully converted SOAEventQueueMessage to EventMessage");
        }

        return eventMessage;
    }
}
