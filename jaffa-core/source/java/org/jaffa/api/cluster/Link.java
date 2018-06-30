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

/**
 * This class creates a HATEOAS URL Link object for use with the Jaffa API
 *
 * @author Matthew Wayles
 * @version 1.0
 */
public class Link {
    @Expose
    private String href;
    @Expose
    private String rel;
    @Expose
    private String type;

    /**
     * Create a Link object with provided parameters
     *
     * @param myUrl  The URL to be included in the link
     * @param myRel  The relationship to be included in the link
     * @param myType The HTTP request type to be included in the link
     */
    public Link(String myUrl, String myRel, String myType) {
        this.href = myUrl;
        this.rel = myRel;
        this.type = myType;
    }

    /**
     * Retrieve the value from the URL variable
     *
     * @return url  The url retrieved
     **/
    public String getUrl() {
        return href;
    }

    /**
     * Set the URL value for this link
     *
     * @param url The URL to set
     */
    public void setUrl(String url) {
        this.href = url;
    }

    /**
     * Retrieve the value from the relationship variable
     *
     * @return rel  The relationship retrieved
     **/
    public String getRel() {
        return rel;
    }

    /**
     * Set the relationship value for this link
     *
     * @param rel The relationship to set
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

    /**
     * Retrieve the value from the type variable
     *
     * @return type  The type retrieved
     **/
    public String getType() {
        return type;
    }

    /**
     * Set the type value for this link
     *
     * @param type The type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
