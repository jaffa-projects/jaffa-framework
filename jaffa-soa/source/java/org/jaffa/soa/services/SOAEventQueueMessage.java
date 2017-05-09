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

import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import org.jaffa.datatypes.DateTime;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.messaging.services.HeaderParam;
import org.jaffa.modules.messaging.services.IHasHeaderParams;
import org.jaffa.soa.domain.SOAEvent;
import org.jaffa.soa.domain.SOAEventParam;

/**
 * This class is used to publish SOAEvent messages to a Queue.
 * It needs to be mapped to a Queue in the jaffa-transaction-config.xml file.
 */
@XmlRootElement
public class SOAEventQueueMessage implements IHasHeaderParams {

    private String eventId;
    private String eventName;
    private String description;
    private String category;
    private DateTime createdOn;
    private String createdBy;
    private Map<String, HeaderParam> headerParams;

    /**
     * Default constructor.
     */
    public SOAEventQueueMessage() {
    }

    /**
     * Constructs an instance initialized to the properties of the input object.
     * @param input the object whose properties are to be used for initialization.
     */
    public SOAEventQueueMessage(SOAEvent input) throws FrameworkException {
        this.eventId = input.getEventId();
        this.eventName = input.getEventName();
        this.description = input.getDescription();
        this.createdOn = input.getCreatedOn();
        this.createdBy = input.getCreatedBy();
        SOAEventParam[] inputParams = input.getSOAEventParamArray();
        if (inputParams != null) {
            this.headerParams = new LinkedHashMap<String, HeaderParam>();
            for (SOAEventParam inputParam : inputParams)
                this.headerParams.put(inputParam.getName(), new HeaderParam(inputParam.getName(), inputParam.getValue()));
        }
    }

    /**
     * Constructs an instance initialized to the properties of the input object.
     * @param input the object whose properties are to be used for initialization.
     */
    public SOAEventQueueMessage(SOAEventQueueMessage input) {
        this.eventId = input.getEventId();
        this.eventName = input.getEventName();
        this.description = input.getDescription();
        this.category = input.getCategory();
        this.createdOn = input.getCreatedOn();
        this.createdBy = input.getCreatedBy();
        if (input.headerParams != null)
            this.headerParams = new LinkedHashMap<String, HeaderParam>(input.headerParams);
    }

    /** Returns the eventId property.
     * @return the eventId property.
     */
    public String getEventId() {
        return eventId;
    }

    /** Sets the eventId property.
     * @param eventId the eventId.
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /** Returns the eventName property.
     * @return the eventName property.
     */
    public String getEventName() {
        return eventName;
    }

    /** Sets the eventName property.
     * @param eventName the eventName.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /** Returns the description property.
     * @return the description property.
     */
    public String getDescription() {
        return description;
    }

    /** Sets the description property.
     * @param description the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the category property.
     *
     * @return the category property.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category property.
     *
     * @param category the category.
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /** Returns the createdOn property.
     * @return the createdOn property.
     */
    public DateTime getCreatedOn() {
        return createdOn;
    }

    /** Sets the createdOn property.
     * @param createdOn the createdOn.
     */
    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }

    /** Returns the createdBy property.
     * @return the createdBy property.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Sets the createdBy property.
     * @param createdBy the createdBy.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Returns the headerParams property.
     * @return the headerParams property.
     */
    public HeaderParam[] getHeaderParams() {
        return this.headerParams != null ? this.headerParams.values().toArray(new HeaderParam[this.headerParams.size()]) : null;
    }

    /** Sets the headerParams property.
     * @param headerParams the headerParams.
     */
    public void setHeaderParams(HeaderParam[] headerParams) {
        if (headerParams != null) {
            this.headerParams = new LinkedHashMap<String, HeaderParam>();
            for (HeaderParam p : headerParams)
                this.headerParams.put(p.getName(), p);
        } else
            this.headerParams = null;
    }

    /** Returns the headerParam having the input name.
     * @param name the parameter name.
     * @return the headerParam having the input name.
     */
    public HeaderParam getHeaderParam(String name) {
        return this.headerParams != null ? this.headerParams.get(name) : null;
    }
}
