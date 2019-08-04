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

import org.jaffa.soa.domain.SOAEvent;

import java.util.List;

/**
 * Tracks if SOA events have been marked as disabled or enabled.  This class will not contain the entire list of events,
 * but will contain a subset.  Events not listed in this class will default to enabled
 */
public interface SOAEventEnabledConfiguration {

    /**
     * Specifies if events not in the configuration are to be considered enabled or disabled
     * If true any event that is not marked as disabled is considered enabled
     * If false an event that is not marked as enabled is considered disabled
     *
     * @return Default value for enabled/disabled state for events
     */
    public boolean areEventsEnabledByDefault();

    /**
     * Check if an event type is enabled by the name of the event
     *
     * @param eventName Name of the event to check
     * @return True if the event is enabled
     */
    public boolean isEnabled(String eventName);

    /**
     * Check if an event type is enabled for a SOAEventQueueMessage
     *
     * @param event SOAEventQueueMessage to check if enabled
     * @return True if the event type is enabled
     */
    public boolean isEnabled(SOAEventQueueMessage event);

    /**
     * Check if an event type is enabled for a SOAEvent
     *
     * @param event SOAEvent to check if enabled
     * @return True if the event type is enabled
     */
    public boolean isEnabled(SOAEvent event);

    /**
     * Check if an event type is enabled for an object
     *
     * @param event Object to check if enabled
     * @return True if the event type is enabled
     */
    public boolean isEnabled(Object event);

    /**
     * Set an event type (by event name) as enabled/disabled
     *
     * @param eventName Name of the event to enable/disable
     * @param isEnabled True to enable or false to disable
     */
    public void setEnabled(String eventName, boolean isEnabled);


    /**
     * Gets a list of all the events which are in the non-default state
     * If isDefaultEnabled returns true, this is the list disabled events
     * If isDefaultEnabled returns false, this is the list enabled events
     *
     * @return List of all the events which are disabled
     */
    public List<String> getEventsInNonDefaultState();
}
