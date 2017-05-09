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

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.MDC;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;
import org.jaffa.polling.DatabasePoller;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.soa.domain.SOAEvent;
import org.jaffa.soa.domain.SOAEventMeta;

/**
 * This class polls the J_SOA_EVENTS table for new records.
 * Whenever a new record is picked up, a SOAEventQueueMessage payload is sent
 * to the Messaging system, and the record is then cleaned up.
 *
 * @author GautamJ
 */
@XmlRootElement
public class SOAEventPoller extends DatabasePoller {

    private String scheduleTaskId;
    //private static final String LOCAL_ID = "jaffa.soa.events.localId";

    /**
     * Polls the database for the presence of new rows in the J_SOA_EVENTS table.
     * @param databean an instance of this class.
     * This is the initial payload submitted by the Scheduler to the Messaging system.
     * This initial payload is ignored.
     */
    public void poll(UOW uow, SOAEventPoller databean) throws FrameworkException, ApplicationException, ApplicationExceptions {
        super.poll(uow);
    }

    /**
     * Add the following to the input Criteria:
     * <ul>
     * <li>The table to be queried.</li>
     * <li>Filters to pick up unprocessed rows.</li>
     * <li>OrderBy clause to pick up the appropriate row first.</li>
     * </ul>
     * @param criteria The Criteria to be customized.
     * @throws FrameworkException if any error occurs.
     * @throws ApplicationExceptions if multiple application error occurs.
     * @throws ApplicationException if any application error occurs.
     */
    protected void customizeCriteria(Criteria criteria) throws FrameworkException, ApplicationExceptions, ApplicationException {
        //String localId = (String) ContextManagerFactory.instance().getProperty(LOCAL_ID);
        criteria.setTable(SOAEventMeta.getName());
        //if (localId != null && localId.length() > 0) {
        //    criteria.addCriteria(SOAEventMeta.LOCAL_ID, localId);
        //} else {
        //    criteria.addCriteria(SOAEventMeta.LOCAL_ID, Criteria.RELATIONAL_IS_NULL);
        //}
        criteria.addOrderBy(SOAEventMeta.CREATED_ON);
        //criteria.addOrderBy(SOAEventMeta.EVENT_ID);
    }

    /**
     * Returns the userId of the person who created the row in the database.
     * The userId will be used to setup the appropriate context, within which
     * the processing of the domain object is to happen.
     * @param domain The domain object.
     * @return the userId of the person who created the row in the database.
     * @throws FrameworkException if any error occurs.
     * @throws ApplicationExceptions if multiple application error occurs.
     * @throws ApplicationException if any application error occurs.
     */
    protected String findCreatedBy(IPersistent domain) throws FrameworkException, ApplicationExceptions, ApplicationException {
        return ((SOAEvent) domain).getCreatedBy();
    }

    /**
     * Molds the input domain object into a databean, which is to be submitted
     * to the Messaging system.
     * <p>
     * It is recommended that the key-information of the domain object
     * be stamped on the payload, so that the corresponding handler can
     * update/delete the domain object after it's done processing.
     * @param domain The domain object.
     * @return the databean representing the domain object.
     * @throws FrameworkException if any error occurs.
     * @throws ApplicationExceptions if multiple application error occurs.
     * @throws ApplicationException if any application error occurs.
     */
    protected Object buildPayload(IPersistent domain) throws FrameworkException, ApplicationExceptions, ApplicationException {
        return new SOAEventQueueMessage((SOAEvent) domain);
    }

    /**
     * Modifies the input domain object, such that the corresponding row
     * is not picked up in subsequent polls.
     * <p>
     * A concrete implementation is expected to either update some flag
     * on the row, or delete the row entirely.
     * @param domain The domain object.
     * @param e indicates if the processing of the row has been a failure.
     * @throws FrameworkException if any error occurs.
     * @throws ApplicationExceptions if multiple application error occurs.
     * @throws ApplicationException if any application error occurs.
     */
    protected void cleanup(IPersistent domain, Exception e) throws FrameworkException, ApplicationExceptions, ApplicationException {
        domain.getUOW().delete(domain);
    }


    /**
     * Returns the scheduleTaskId. If one hasn't been set it will return the one from the MDC context
     * @return scheduleTaskId
     */
    public String getScheduleTaskId() {
        return scheduleTaskId !=null ? scheduleTaskId : (String)MDC.get(BusinessEventLogMeta.SCHEDULED_TASK_ID);
    }

    /**
     * Sets the scheduleTaskId
     * @param scheduleTaskId
     */
    public void setScheduleTaskId(String scheduleTaskId) {
        this.scheduleTaskId = scheduleTaskId;
    }
}