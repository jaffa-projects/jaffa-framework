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
package org.jaffa.api.cluster;

import com.google.gson.annotations.Expose;
import org.jaffa.api.ConfigApiCore;
import org.jaffa.api.FileContents;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * NodeInformation - A custom object for storing the name, href, contents, and links for a node contained
 * in the Ignite cache
 *
 * @author Matthew Wayles
 * @version 1.0
 */
public class NodeInformation {
    @Expose
    private String name;
    @Expose
    private String href;
    @Expose
    private List<FileContents> config;
    @Expose
    private List<Link> links;

    /**
     * Creates a NodeInformation with no parameters provided
     *
     * @throws UnknownHostException If the hostname search returns an unknown host or null
     */
    public NodeInformation() throws UnknownHostException {
        this.name = InetAddress.getLocalHost().getHostName();
        this.href = System.getProperty("app.base.url");
        this.links = new ArrayList<>();
        this.config = addNodeCustomConfigFiles();
    }

    /**
     * Retrieve the value from the name variable
     *
     * @return name The name retrieved
     **/
    public String getName() {
        return this.name;
    }

    /**
     * Set the name value
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieve the value from the href variable
     *
     * @return href The href retrieved
     **/
    public String getHref() {
        return href;
    }

    /**
     * Set the href value
     *
     * @param href The href to set
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * Retrieve the list of custom configuration files
     *
     * @return config   The list of custom configuration files
     **/
    public List<FileContents> getConfig() {
        return this.config;
    }

    /**
     * Create a list to hold the custom configuration files
     *
     * @param config A List<FileContents> of custom configuration file contents
     */
    public void setConfig(List<FileContents> config) {
        this.config = config;
    }

    /**
     * Retrieve the links associated with the node
     *
     * @return links  The links associated with the node
     **/
    public List<Link> getLinks() {
        return this.links;
    }

    /**
     * Create a Link object list to hold the node links
     *
     * @param links A List<Link> object
     */
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    /**
     * Add a link to a node information object
     *
     * @param link The link to add to the node information object
     */
    public void addLink(Link link) {
        if (this.links == null) {
            setLinks(new ArrayList<>());
        }
        links.add(link);
    }

    /**
     * Retrieve a list of custom configuration files for this node
     *
     * @return A list of custom configuration files for this node
     */
    private List<FileContents> addNodeCustomConfigFiles() {
        List<FileContents> nodeCustomConfigFiles = new ArrayList<>();
        File dataDirectory = new File(System.getProperty("data.directory") + File.separator + "config");
        if(dataDirectory!=null && dataDirectory.exists()) {
            for (File zipFile : dataDirectory.listFiles()) {
                if (zipFile.getName().endsWith(".zip")) {
                    FileContents fileInformation = null;
                    try {
                        fileInformation = ConfigApiCore.getFileContents(zipFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    nodeCustomConfigFiles.add(fileInformation);
                }
            }
        }
        return nodeCustomConfigFiles;
    }
}
