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
package org.jaffa.api.services.git;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.jaffa.api.FileContents;
import org.jaffa.api.cluster.Link;
import org.jaffa.api.cluster.NodeInformation;

/**
 * LinkResolver - A class to consolidate the links that will be appended to FileContents and NodeInformation
 * objects.
 *
 * @author Matthew Wayles
 * @version 1.0
 */
public class LinkResolver {

    /**
     * Add links to a FileContents object. Currently, the only operations that can be performed on these resources
     * are download and delete. The HTTP message base path is used to avoid hard-coding the URI to the service endpoint.
     *
     * @param fileContents The FileContents object to add the links to
     * @return The same FileContents object sent to this method, with its links variable populated.
     */
    public static FileContents addLinks(FileContents fileContents) {
        String href = "";
        Message message = PhaseInterceptorChain.getCurrentMessage();

        if (message != null) {
            href += fileContents.getUrl() + message.get(Message.BASE_PATH) + "/config/" + fileContents.getName();
        }

        if (fileContents.getLinks() == null) {
            fileContents.addLink(new Link(href, "download", "GET"));
            fileContents.addLink(new Link(href, "delete", "DELETE"));
        }

        return fileContents;
    }

    /**
     * Add links to a NodeInformation object. Currently, the only operations that can be performed on these resources
     * are self, list, and add. The HTTP message base path is used to avoid hard-coding the URI to the service endpoint.
     *
     * @param nodeInformation The NodeInformation object to add the links to
     * @return The same NodeInformation object sent to this method, with its links variable populated.
     */
    public static NodeInformation addLinks(NodeInformation nodeInformation) {
        String url = "";
        Message message = PhaseInterceptorChain.getCurrentMessage();

        if (message != null) {
            url += nodeInformation.getHref() + message.get(Message.BASE_PATH) + "/config/";

        }

        if (nodeInformation.getLinks() != null && nodeInformation.getLinks().isEmpty()) {
            nodeInformation.addLink(new Link(url + "clusterMetadata", "self", "GET"));
            nodeInformation.addLink(new Link(url, "list", "GET"));
            nodeInformation.addLink(new Link(url + "{fileName}", "add", "POST"));
        }

        return nodeInformation;
    }
}
