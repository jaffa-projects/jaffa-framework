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
package org.jaffa.session;

import java.util.Date;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

/**
 * This listener is used to set the MaxInactiveInterval for HttpSession, thus ensuring that HttpSessions
 * are not kept open un-necessarily after WebService invocations.
 * <p>
 * A servlet container, for example JBoss, shows the number of 'activeSessions' in
 * the mbean identified by 'path=/{WebServiceApp},type=Manager'.
 * <p>
 * Example use in web.xml
 * <code><pre>
 * &lt;listener&gt; 
 *    &lt;listener-class&gt;com.mirotechnologies.interfaces.transformation.helper.WebServiceSessionListener&lt;/listener-class&gt; 
 * &lt;/listener&gt;  
 * </pre></code>
 */
public class WebServiceSessionListener implements HttpSessionListener {

    private static Logger log = Logger.getLogger(WebServiceSessionListener.class);

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        // session has been invalidated and all session data (except Id)is no longer available
        if (log.isDebugEnabled()) {
            log.debug(getTime() + " (session) Destroyed:ID=" + session.getId());
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {

        HttpSession session = event.getSession();
        if (log.isDebugEnabled()) {
            log.debug(getTime() + " (session) Created: Session ID=" + session.getId() + "Default MaxInactiveInterval=" + session.getMaxInactiveInterval());
        }
        // Setting MaxInactiveInterval to 1 sec to make sure the ideal sessions will get invalidated in 1 sec.
        session.setMaxInactiveInterval(1);

        if (log.isDebugEnabled()) {
            log.debug("Session ID=" + session.getId() + "Modified MaxInactiveInterval=" + session.getMaxInactiveInterval());
        }
    }

    private String getTime() {
        return new Date(System.currentTimeMillis()).toString();
    }
}
