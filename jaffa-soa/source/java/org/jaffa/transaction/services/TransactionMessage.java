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
package org.jaffa.transaction.services;

import javax.xml.bind.annotation.XmlRootElement;
import org.jaffa.modules.messaging.services.IHasDestinationName;
import org.jaffa.transaction.domain.Transaction;
import org.jaffa.transaction.services.configdomain.TypeInfo;

/**
 * This class is used to send Transaction messages to a Queue.
 * It needs to be mapped to a Queue in the jaffa-messaging-config.xml file.
 */
@XmlRootElement
public class TransactionMessage implements IHasDestinationName {

    private String id;
    private String type;
    private String queueName;
    private String topicName;

    /**
     * Default constructor.
     */
    public TransactionMessage() {
    }

    /**
     * Constructs an instance initialized to the properties of the input object.
     * @param input the object whose properties are to be used for initialization.
     */
    public TransactionMessage(Transaction input) {
        this.id = input.getId();
        this.type = input.getType();
        if (input.getType() != null) {
            TypeInfo typeInfo = ConfigurationService.getInstance().getTypeInfo(input.getType());
            if (typeInfo != null)
                this.queueName = typeInfo.getQueueName();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    /**
     * Returns debug info.
     * @return debug info.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("<TransactionMessage>");
        buf.append("<id>").append(getId()).append("</id>");
        buf.append("<type>").append(getType()).append("</type>");
        buf.append("<queueName>").append(getQueueName()).append("</queueName>");
        buf.append("<topicName>").append(getTopicName()).append("</topicName>");
        buf.append("</TransactionMessage>");
        return buf.toString();
    }
}
