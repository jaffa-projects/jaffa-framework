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

import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.services.HeaderParam;
import org.jaffa.persistence.UOW;
import org.jaffa.security.SecurityManager;

public class RaiseEventService {

    private static final Logger log = Logger.getLogger(RaiseEventService.class);

    // TODO-SWAT add script events here

    public void raiseSoaEvent(UOW uow, String eventName, String description, String category, List<HeaderParam> headerParams) throws FrameworkException, ApplicationExceptions {
        // TODO-SWAT fire custom handler here

        // Check if the SOA event is enabled.  If it isn't drop it and return.
        // This is an optimization. If a SOAEventQueueMessage gets submitted to a UOW as a message it will checked
        // there too.
        if(!SOAEventEnabledConfigurationFactory.instance().isEnabled(eventName)){
			if (log.isDebugEnabled()) log.debug("SOA event disabled: "+eventName);
            return;
        }

        UOW localUow = null;
        try {
            if (uow == null || !uow.isActive()) {
                localUow = new UOW();
            }
            SOAEventQueueMessage message = new SOAEventQueueMessage();
            message.setEventName(eventName);
            message.setDescription(description);
            message.setCreatedOn(new DateTime());
            message.setCreatedBy(SecurityManager.getPrincipal() != null ? SecurityManager.getPrincipal().getName() : null);
            
            if (category != null) {
                message.setCategory(category);
            }
            
            if (headerParams != null && headerParams.size() > 0) {
                message.setHeaderParams(headerParams.toArray(new HeaderParam[0]));
            }

            if (log.isDebugEnabled()) {
                log.debug("Creating SoaEvent Message" + message);
            }

            if (localUow != null) {
                localUow.addMessage(message);
                localUow.commit();
            } else {
                uow.addMessage(message);
            }
        } finally {
            if (localUow != null && localUow.isActive()) {
                try {
                    localUow.rollback();
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }

    public void raiseSoaEvent(UOW uow, String eventName, String description, HeaderParam[] headerParams) throws FrameworkException, ApplicationExceptions {
        raiseSoaEvent(uow, eventName, description, null, headerParams!=null ? Arrays.asList(headerParams) : null);
    }
}
